package io.github.pedropizzutti.acervo_referencias.service;

import io.github.pedropizzutti.acervo_referencias.domain.entity.Usuario;
import io.github.pedropizzutti.acervo_referencias.exception.RegraNegocioException;
import io.github.pedropizzutti.acervo_referencias.rest.dto.UsuarioDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    public UsuarioDTO salvar(UsuarioDTO usuarioDTO) throws RegraNegocioException;

    public void deletarUsuario(Integer id) throws RegraNegocioException;

    UsuarioDTO atualizarUsuario(UsuarioDTO usuarioDTO, Integer id) throws RegraNegocioException;

    void atualizarEmailUsuario(String novoEmail, Integer id) throws RegraNegocioException;

    void atualizarSenhaUsuario(String senhaAtual, String novaSenha, String confirmacaoNovaSenha, Integer id) throws RegraNegocioException;

    public List<UsuarioDTO> listarUsuarios(Integer numeroPaginacao);

    List<UsuarioDTO> listarUsuariosFiltro(UsuarioDTO usuarioDTOFiltro, Integer numeroPaginacao) throws RegraNegocioException;

    Usuario puxarUsuarioPeloId(Integer id) throws RegraNegocioException;

    public UsuarioDTO converterUsuarioParaUsuarioDTO(Usuario usuario);

    public boolean verificarDisponibilidadeDoLogin(String login);

    public boolean verificarDisponibilidadeDoEmail(String email);


}
