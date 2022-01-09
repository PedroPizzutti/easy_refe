package io.github.pedropizzutti.acervo_referencias.service;

import io.github.pedropizzutti.acervo_referencias.domain.entity.Usuario;
import io.github.pedropizzutti.acervo_referencias.exception.RegraNegocioException;
import io.github.pedropizzutti.acervo_referencias.rest.dto.UsuarioDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UsuarioService {

    public Usuario salvar(UsuarioDTO usuarioDTO) throws RegraNegocioException;

    public List<UsuarioDTO> listarUsuarios(Integer pagina);

    public UsuarioDTO converterUsuarioParaUsuarioDTO(Usuario usuario);

    public boolean verificarDisponibilidadeDoLogin(String login);

    public boolean verificarDisponibilidadeDoEmail(String email);

}
