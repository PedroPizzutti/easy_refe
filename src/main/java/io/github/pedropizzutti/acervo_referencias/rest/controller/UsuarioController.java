package io.github.pedropizzutti.acervo_referencias.rest.controller;

import io.github.pedropizzutti.acervo_referencias.domain.entity.Usuario;
import io.github.pedropizzutti.acervo_referencias.exception.RegraNegocioException;
import io.github.pedropizzutti.acervo_referencias.rest.dto.UsuarioDTO;
import io.github.pedropizzutti.acervo_referencias.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    private UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public String salvarNovoUsuario(@RequestBody @Valid UsuarioDTO usuarioDTO) throws RegraNegocioException {

            Usuario usuarioSalvo = usuarioService.salvar(usuarioDTO);

            String loginUsuario = usuarioSalvo.getLogin();

            return loginUsuario;
        }
    }
