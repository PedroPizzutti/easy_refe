package io.github.pedropizzutti.acervo_referencias.service.implemantationService;

import io.github.pedropizzutti.acervo_referencias.domain.entity.Livro;
import io.github.pedropizzutti.acervo_referencias.domain.entity.Usuario;
import io.github.pedropizzutti.acervo_referencias.domain.repository.LivroRepository;
import io.github.pedropizzutti.acervo_referencias.domain.repository.UsuarioRepository;
import io.github.pedropizzutti.acervo_referencias.exception.RegraNegocioException;
import io.github.pedropizzutti.acervo_referencias.rest.dto.LivroDTO;
import io.github.pedropizzutti.acervo_referencias.service.LivroService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LivroServiceImplemantation implements LivroService {

    private LivroRepository livroRepository;
    private UsuarioRepository usuarioRepository;

    public LivroServiceImplemantation(LivroRepository livroRepository, UsuarioRepository usuarioRepository){
        this.livroRepository = livroRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional
    public LivroDTO salvarLivro(LivroDTO livroDTO) throws RegraNegocioException {

        Livro livroSalvar = converterLivroDTOParaLivro(livroDTO);

        Livro livroSalvo = livroRepository.save(livroSalvar);

        LivroDTO livroDTOSalvo = converterLivroParaLivroDTO(livroSalvo);

        return livroDTOSalvo;

    }

    // Métodos Auxiliares

    private Livro converterLivroDTOParaLivro(LivroDTO livroDTO) throws RegraNegocioException {

        Integer idUsuario = livroDTO.getIdUsuario();

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RegraNegocioException("Código de usuário inválido."));

        Livro livro = new Livro();
        livro.setUsuario(usuario);
        livro.setAutor(livroDTO.getAutor());
        livro.setTitulo(livroDTO.getTitulo());
        livro.setAno(livroDTO.getAno());
        livro.setReferencia(livroDTO.getReferencia());
        livro.setAnotacao(livroDTO.getAnotacao());

        return livro;

    }

    private LivroDTO converterLivroParaLivroDTO(Livro livro){

        LivroDTO livroDTO = new LivroDTO();
        livroDTO.setIdRegistro(livro.getId());
        livroDTO.setIdUsuario(livro.getUsuario().getId());
        livroDTO.setAutor(livro.getAutor());
        livroDTO.setTitulo(livro.getTitulo());
        livroDTO.setAno(livro.getAno());
        livroDTO.setReferencia(livro.getReferencia());
        livroDTO.setAnotacao(livro.getAnotacao());

        return livroDTO;
    }

}
