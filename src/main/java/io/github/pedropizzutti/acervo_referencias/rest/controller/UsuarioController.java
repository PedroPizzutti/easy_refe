package io.github.pedropizzutti.acervo_referencias.rest.controller;

import io.github.pedropizzutti.acervo_referencias.domain.entity.Usuario;
import io.github.pedropizzutti.acervo_referencias.exception.RegraNegocioException;
import io.github.pedropizzutti.acervo_referencias.rest.dto.UsuarioDTO;
import io.github.pedropizzutti.acervo_referencias.service.UsuarioService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @GetMapping("/{pagina}")
    @ResponseStatus(HttpStatus.OK)
    public List<UsuarioDTO> listarUsuarios(@PathVariable Integer pagina){

        List<UsuarioDTO> listaPaginada = usuarioService.listarUsuarios(pagina-1);

        return listaPaginada;

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarUsuario(@PathVariable Integer id) throws RegraNegocioException {

        usuarioService.deletarUsuario(id);

    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioDTO atualizarUsuario(@RequestBody UsuarioDTO usuarioDTO, @PathVariable Integer id) throws RegraNegocioException {

        UsuarioDTO usuarioAtualizado = usuarioService.atualizarUsuario(usuarioDTO, id);

        return usuarioAtualizado;

    }

}
