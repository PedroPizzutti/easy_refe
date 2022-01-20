package io.github.pedropizzutti.acervo_referencias.service;

import io.github.pedropizzutti.acervo_referencias.domain.entity.Livro;
import io.github.pedropizzutti.acervo_referencias.exception.RegraNegocioException;
import io.github.pedropizzutti.acervo_referencias.rest.dto.LivroDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface LivroService {

    LivroDTO salvarLivro(LivroDTO livroDTO) throws RegraNegocioException;

    LivroDTO atualizarLivro(LivroDTO livroDTO) throws RegraNegocioException;

    void deletarLivro(LivroDTO livroDTO) throws RegraNegocioException;

    List<LivroDTO> listarLivrosUsuario(String loginUsuario, Integer paginaAtual) throws RegraNegocioException;

    List<LivroDTO> listarLivrosFiltro(LivroDTO livroDTOFiltrado, Integer paginaAtual) throws RegraNegocioException;
}
