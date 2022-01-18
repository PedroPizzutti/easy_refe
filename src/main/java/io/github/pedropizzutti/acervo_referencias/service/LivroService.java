package io.github.pedropizzutti.acervo_referencias.service;

import io.github.pedropizzutti.acervo_referencias.domain.entity.Livro;
import io.github.pedropizzutti.acervo_referencias.exception.RegraNegocioException;
import io.github.pedropizzutti.acervo_referencias.rest.dto.LivroDTO;
import org.springframework.transaction.annotation.Transactional;

public interface LivroService {

    LivroDTO salvarLivro(LivroDTO livroDTO) throws RegraNegocioException;

    LivroDTO atualizarLivro(LivroDTO livroDTO) throws RegraNegocioException;
}
