package io.github.pedropizzutti.acervo_referencias.rest.controller;

import io.github.pedropizzutti.acervo_referencias.domain.entity.Usuario;
import io.github.pedropizzutti.acervo_referencias.rest.dto.UsuarioDTO;
import io.github.pedropizzutti.acervo_referencias.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    private UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public String save(@RequestBody UsuarioDTO usuarioDTO){

        Usuario usuarioSalvo = usuarioService.salvar(usuarioDTO);

        String loginUsuario = usuarioSalvo.getLogin();

        return loginUsuario;
    }
}
