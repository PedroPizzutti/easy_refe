package io.github.pedropizzutti.acervo_referencias.rest.controller;

import io.github.pedropizzutti.acervo_referencias.exception.RegraNegocioException;
import io.github.pedropizzutti.acervo_referencias.rest.dto.EmailDTO;
import io.github.pedropizzutti.acervo_referencias.rest.dto.SenhaDTO;
import io.github.pedropizzutti.acervo_referencias.rest.dto.UsuarioDTO;
import io.github.pedropizzutti.acervo_referencias.service.UsuarioService;
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

    @PostMapping("/newUser")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody UsuarioDTO salvarNovoUsuario(@RequestBody @Valid UsuarioDTO usuarioDTO) throws RegraNegocioException {

            UsuarioDTO usuarioSalvoDTO = usuarioService.salvar(usuarioDTO);

            return usuarioSalvoDTO;

        }

    @GetMapping("/admin/page{pagina}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<UsuarioDTO> listarUsuarios(@PathVariable Integer pagina){

        List<UsuarioDTO> listaUsuarios = usuarioService.listarUsuarios(pagina-1);

        return listaUsuarios;

    }

    @GetMapping("/admin/filterPage{paginaFiltro}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<UsuarioDTO> pesquisarUsuarios(
            @RequestBody UsuarioDTO usuarioDTOFiltro, @PathVariable Integer paginaFiltro) throws RegraNegocioException {

        List<UsuarioDTO> listaFiltradaUsuarios =
                usuarioService.listarUsuariosFiltro(usuarioDTOFiltro, paginaFiltro-1);

        return listaFiltradaUsuarios;

    }

    @DeleteMapping("/admin/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarUsuario(@PathVariable Integer id) throws RegraNegocioException {

        usuarioService.deletarUsuario(id);

    }

    @PutMapping("/admin/{id}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody UsuarioDTO atualizarUsuario(@RequestBody @Valid UsuarioDTO usuarioDTO, @PathVariable Integer id) throws RegraNegocioException {

        UsuarioDTO usuarioAtualizadoDTO = usuarioService.atualizarUsuario(usuarioDTO, id);

        return usuarioAtualizadoDTO;

    }

    @PatchMapping("/att/userEmail{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarEmailUsuario(@RequestBody @Valid EmailDTO emailDTO, @PathVariable Integer id) throws RegraNegocioException {

        String novoEmail = emailDTO.getNovoEmail();

        usuarioService.atualizarEmailUsuario(novoEmail, id);

    }

    @PatchMapping("/att/userPass{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarSenhaUsuario(@RequestBody @Valid SenhaDTO senhaDTO, @PathVariable Integer id) throws RegraNegocioException{

        String senhaAtual = senhaDTO.getSenhaAtual();
        String novaSenha = senhaDTO.getNovaSenha();
        String confirmacaoNovaSenha = senhaDTO.getConfirmacaoNovaSenha();

        usuarioService.atualizarSenhaUsuario(senhaAtual, novaSenha, confirmacaoNovaSenha, id);


    }

}
