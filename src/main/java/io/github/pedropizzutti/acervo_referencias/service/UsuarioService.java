package io.github.pedropizzutti.acervo_referencias.service;

import io.github.pedropizzutti.acervo_referencias.domain.entity.Usuario;
import io.github.pedropizzutti.acervo_referencias.exception.RegraNegocioException;
import io.github.pedropizzutti.acervo_referencias.rest.dto.UsuarioDTO;

import java.util.List;

public interface UsuarioService {

    public Usuario salvar(UsuarioDTO usuarioDTO) throws RegraNegocioException;

    public List<UsuarioDTO> listarUsuarios();

    public boolean verificarDisponibilidadeDoLogin(String login);

    public boolean verificarDisponibilidadeDoEmail(String email);

}
