package io.github.pedropizzutti.acervo_referencias.service;

import io.github.pedropizzutti.acervo_referencias.domain.entity.Usuario;
import io.github.pedropizzutti.acervo_referencias.exception.RegraNegocioException;
import io.github.pedropizzutti.acervo_referencias.rest.dto.UsuarioDTO;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    UsuarioDTO salvar(UsuarioDTO usuarioDTO) throws RegraNegocioException;

    void deletarUsuario(Integer id) throws RegraNegocioException;

    UsuarioDTO atualizarUsuario(UsuarioDTO usuarioDTO) throws RegraNegocioException;

    void atualizarEmailUsuario(String novoEmail, Integer id) throws RegraNegocioException;

    void atualizarSenhaUsuario(String senhaAtual, String novaSenha, String confirmacaoNovaSenha, Integer id) throws RegraNegocioException;

    List<UsuarioDTO> listarUsuarios(Integer numeroPaginacao);

    List<UsuarioDTO> listarUsuariosFiltro(UsuarioDTO usuarioDTOFiltro, Integer numeroPaginacao) throws RegraNegocioException;

    Usuario puxarUsuarioPeloId(Integer id) throws RegraNegocioException;

    Usuario puxarUsuarioPeloILogin(String login) throws RegraNegocioException;
}
