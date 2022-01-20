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

        Livro livroParaAtualizacao = puxarLivroPeloId(livroDTO.getIdRegistro());
        Usuario usuarioLivro = usuarioService.puxarUsuarioPeloId(livroDTO.getIdUsuario());

        if(livroParaAtualizacao.getUsuario().getId() == usuarioLivro.getId()){

            livroParaAtualizacao.setUsuario(usuarioLivro);
            livroParaAtualizacao.setAutor(livroDTO.getAutor());
            livroParaAtualizacao.setTitulo(livroDTO.getTitulo());
            livroParaAtualizacao.setAno(livroDTO.getAno());
            livroParaAtualizacao.setReferencia(livroDTO.getReferencia());
            livroParaAtualizacao.setAnotacao(livroDTO.getAnotacao());

            Livro livroAtualizado = livroRepository.save(livroParaAtualizacao);

            LivroDTO livroAtualizadoDTO = converterLivroParaLivroDTO(livroAtualizado);

            return livroAtualizadoDTO;

        } else {

            throw new RegraNegocioException("Problemas com autentificação da relação usuário-livro");

        }

    }

    @Override
    @Transactional
    public void deletarLivro(Integer idRegistroLivro) throws RegraNegocioException {

        Livro livroParaDeletacao = puxarLivroPeloId(idRegistroLivro);

        livroRepository.delete(livroParaDeletacao);

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
