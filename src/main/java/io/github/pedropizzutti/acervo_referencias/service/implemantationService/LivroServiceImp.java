package io.github.pedropizzutti.acervo_referencias.service.implemantationService;

import io.github.pedropizzutti.acervo_referencias.domain.entity.Livro;
import io.github.pedropizzutti.acervo_referencias.domain.entity.Usuario;
import io.github.pedropizzutti.acervo_referencias.domain.repository.LivroRepository;
import io.github.pedropizzutti.acervo_referencias.exception.RegraNegocioException;
import io.github.pedropizzutti.acervo_referencias.rest.dto.LivroDTO;
import io.github.pedropizzutti.acervo_referencias.service.LivroService;
import io.github.pedropizzutti.acervo_referencias.service.UsuarioService;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LivroServiceImp implements LivroService {

    private final Integer ELEMENTOS_POR_PAGINA = 10;
    private LivroRepository livroRepository;
    private UsuarioService usuarioService;

    public LivroServiceImp(LivroRepository livroRepository, UsuarioService usuarioService){
        this.livroRepository = livroRepository;
        this.usuarioService = usuarioService;
    }

    @Override
    @Transactional
    public LivroDTO salvarLivro(LivroDTO livroDTO) throws RegraNegocioException {

        Livro livroSalvar = converterLivroDTOParaLivro(livroDTO);

        Livro livroSalvo = livroRepository.save(livroSalvar);

        LivroDTO livroDTOSalvo = converterLivroParaLivroDTO(livroSalvo);

        return livroDTOSalvo;

    }

    @Override
    @Transactional
    public LivroDTO atualizarLivro(LivroDTO livroDTO) throws RegraNegocioException {

        Livro livroBanco = puxarLivroPeloId(livroDTO.getIdRegistro());

        boolean livroAutenticado = verificarLivroBancoMatchsLivroBanco(livroDTO, livroBanco);

        if(livroAutenticado){

            livroBanco.setAutor(livroDTO.getAutor());
            livroBanco.setTitulo(livroDTO.getTitulo());
            livroBanco.setAno(livroDTO.getAno());
            livroBanco.setReferencia(livroDTO.getReferencia());
            livroBanco.setAnotacao(livroDTO.getAnotacao());

            Livro livroAtualizado = livroRepository.save(livroBanco);

            LivroDTO livroAtualizadoDTO = converterLivroParaLivroDTO(livroAtualizado);

            return livroAtualizadoDTO;

        } else {

            throw new RegraNegocioException("Problemas com atualização do livro.");

        }

    }

    @Override
    @Transactional
    public void deletarLivro(LivroDTO livroDTO) throws RegraNegocioException {

        Livro livroBanco = puxarLivroPeloId(livroDTO.getIdRegistro());

        boolean livroBancoEqualsLivroDTO = verificarLivroBancoMatchsLivroBanco(livroDTO, livroBanco);

        if(livroBancoEqualsLivroDTO){

            livroRepository.delete(livroBanco);

        } else {

            throw new RegraNegocioException("Problemas com a deletação do livro.");

        }

    }

    @Override
    public List<LivroDTO> listarLivrosUsuario(String loginUsuario, Integer paginaAtual) throws RegraNegocioException {

        Usuario usuario = usuarioService.puxarUsuarioPeloILogin(loginUsuario);

        Pageable configPaginacao = PageRequest.of(paginaAtual-1, ELEMENTOS_POR_PAGINA, Sort.by("autor"));

        List<Livro> livrosUsuario = livroRepository.findByUsuario(usuario);

        List<LivroDTO> livrosDTOUsuario = livrosUsuario.stream()
                .map(livro -> {
                    LivroDTO livroDTO = converterLivroParaLivroDTO(livro);
                    return livroDTO;
                }).collect(Collectors.toList());

        return livrosDTOUsuario;

    }

    @Override
    public List<LivroDTO> listarLivrosFiltro(LivroDTO livroDTOFiltrado, Integer paginaAtual) throws RegraNegocioException {

        boolean dadosInformados = verificarDadosLivroDTOFiltrado(livroDTOFiltrado);

        if(dadosInformados){

            Pageable configPaginacao = PageRequest.of(paginaAtual-1, ELEMENTOS_POR_PAGINA, Sort.by("autor"));
            Example configExemplar = configurarExemplar(livroDTOFiltrado);

            List<Livro> livrosBanco = livroRepository.findAll(configExemplar, configPaginacao).toList();

            List<LivroDTO> livrosDTO = livrosBanco.stream()
                   .map(livro -> {
                       LivroDTO livroDTO = converterLivroParaLivroDTO(livro);
                       return livroDTO;
                   }).collect(Collectors.toList());

            return livrosDTO;

        } else {

            throw new RegraNegocioException("Nenhum dado informado para consulta...");

        }
    }

    // Métodos Auxiliares

    private Livro puxarLivroPeloId(Integer idRegistroLivro) throws RegraNegocioException {

        Livro livro = livroRepository.findById(idRegistroLivro)
                .orElseThrow(() -> new RegraNegocioException("Livro não encontrado no banco de dados."));

        return livro;

    }

    private Example configurarExemplar(LivroDTO livroDTOFiltrado) throws RegraNegocioException {

        ExampleMatcher configMatcher =
                ExampleMatcher
                        .matching()
                        .withIgnoreCase()
                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Usuario usuario = usuarioService.puxarUsuarioPeloId(livroDTOFiltrado.getIdUsuario());

        Livro livro = Livro.builder()
                .usuario(usuario)
                .titulo(livroDTOFiltrado.getTitulo())
                .autor(livroDTOFiltrado.getAutor())
                .ano(livroDTOFiltrado.getAno())
                .build();

        Example exemplar = Example.of(livro, configMatcher);

        return exemplar;

    }

    private boolean verificarLivroBancoMatchsLivroBanco(LivroDTO livroDTO, Livro livroBanco) throws RegraNegocioException {

        boolean livroBancoAutenticado;

        Usuario usuario = usuarioService.puxarUsuarioPeloId(livroDTO.getIdUsuario());

        if(livroDTO.getIdUsuario() == livroBanco.getUsuario().getId()){

            livroBancoAutenticado = true;

        } else {

            livroBancoAutenticado = false;

        }

        return livroBancoAutenticado;

    }

    private boolean verificarDadosLivroDTOFiltrado(LivroDTO livroDTOFiltrado){

        boolean dadosInformados;

        String titulo = livroDTOFiltrado.getTitulo();
        String autor = livroDTOFiltrado.getAutor();
        Integer ano = livroDTOFiltrado.getAno();

        if((titulo == null || titulo.equals(""))
                && (autor == null || autor.equals(""))
                && (ano == null || ano == 0))
        {

            dadosInformados = false;

        } else {

            dadosInformados = true;

        }

        return dadosInformados;

    }

    public Livro converterLivroDTOParaLivro(LivroDTO livroDTO) throws RegraNegocioException {

        Integer idUsuario = livroDTO.getIdUsuario();

        Usuario usuario = usuarioService.puxarUsuarioPeloId(idUsuario);

        Livro livro = Livro.builder()
                .usuario(usuario)
                .autor(livroDTO.getAutor())
                .titulo(livroDTO.getTitulo())
                .ano(livroDTO.getAno())
                .referencia(livroDTO.getReferencia())
                .anotacao(livroDTO.getAnotacao())
                .build();

        return livro;

    }

    private LivroDTO converterLivroParaLivroDTO(Livro livro){

        LivroDTO livroDTO = LivroDTO.builder()
                    .idRegistro(livro.getId())
                    .idUsuario(livro.getUsuario().getId())
                    .autor(livro.getAutor())
                    .titulo(livro.getTitulo())
                    .ano(livro.getAno())
                    .referencia(livro.getReferencia())
                    .anotacao(livro.getAnotacao())
                    .build();

        return livroDTO;
    }

}
