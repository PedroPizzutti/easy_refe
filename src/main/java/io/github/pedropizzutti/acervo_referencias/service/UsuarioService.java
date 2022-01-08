package io.github.pedropizzutti.acervo_referencias.service;

import io.github.pedropizzutti.acervo_referencias.domain.entity.Usuario;
import io.github.pedropizzutti.acervo_referencias.rest.dto.UsuarioDTO;

public interface UsuarioService {

    Usuario salvar(UsuarioDTO usuarioDTO);

}
