package io.github.pedropizzutti.acervo_referencias.service;

import io.github.pedropizzutti.acervo_referencias.domain.entity.Usuario;
import io.github.pedropizzutti.acervo_referencias.exception.RegraNegocioException;
import io.github.pedropizzutti.acervo_referencias.rest.dto.EmailDTO;
import io.github.pedropizzutti.acervo_referencias.rest.dto.SenhaDTO;
import io.github.pedropizzutti.acervo_referencias.rest.dto.UsuarioDTO;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    UsuarioDTO salvar(UsuarioDTO usuarioDTO) throws RegraNegocioException;

    void deletarUsuario(Integer idUsuario) throws RegraNegocioException;

    UsuarioDTO atualizarUsuario(UsuarioDTO usuarioDTO) throws RegraNegocioException;

    void atualizarEmailUsuario(EmailDTO emailDTO, Integer idUsuario) throws RegraNegocioException;

    void atualizarSenhaUsuario(SenhaDTO senhaDTO, Integer idUsuario) throws RegraNegocioException;

    List<UsuarioDTO> listarUsuarios(Integer numeroPaginacao);

    List<UsuarioDTO> listarUsuariosFiltro(UsuarioDTO usuarioDTOFiltro, Integer numeroPaginacao) throws RegraNegocioException;

    Usuario puxarUsuarioPeloId(Integer id) throws RegraNegocioException;

    Usuario puxarUsuarioPeloILogin(String login) throws RegraNegocioException;
}
