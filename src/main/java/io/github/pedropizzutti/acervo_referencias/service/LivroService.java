package io.github.pedropizzutti.acervo_referencias.service;

import io.github.pedropizzutti.acervo_referencias.exception.RegraNegocioException;
import io.github.pedropizzutti.acervo_referencias.rest.dto.LivroDTO;

public interface LivroService {

    LivroDTO salvarLivro(LivroDTO livroDTO) throws RegraNegocioException;

}
