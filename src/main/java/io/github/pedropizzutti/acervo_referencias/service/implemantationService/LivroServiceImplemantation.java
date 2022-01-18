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
