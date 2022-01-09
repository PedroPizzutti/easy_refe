package io.github.pedropizzutti.acervo_referencias.service.implemantationService;

import io.github.pedropizzutti.acervo_referencias.domain.entity.Usuario;
import io.github.pedropizzutti.acervo_referencias.domain.repository.UsuarioRepository;
import io.github.pedropizzutti.acervo_referencias.exception.RegraNegocioException;
import io.github.pedropizzutti.acervo_referencias.exception.UsuarioNaoEncontradoException;
import io.github.pedropizzutti.acervo_referencias.rest.dto.UsuarioDTO;
import io.github.pedropizzutti.acervo_referencias.service.UsuarioService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImplemantation implements UsuarioService {

    private UsuarioRepository usuarioRepository;
    private PasswordEncoder passwordEncoder;

    public UsuarioServiceImplemantation(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Usuario salvar(UsuarioDTO usuarioDTO) throws RegraNegocioException {

        boolean loginNaoDisponivel = verificarDisponibilidadeDoLogin(usuarioDTO.getLogin());

        boolean emailNaoDisponivel = verificarDisponibilidadeDoEmail(usuarioDTO.getEmail());

        if(loginNaoDisponivel) {

            throw new RegraNegocioException("Login não disponível!");

        } else if(emailNaoDisponivel){

            throw new RegraNegocioException("Já há uma conta para este email!");

        } else {

            Usuario usuario = new Usuario();
            usuario.setLogin(usuarioDTO.getLogin());
            usuario.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
            usuario.setEmail(usuarioDTO.getEmail());
            usuario.setNome(usuarioDTO.getNome());

            Usuario usuarioSalvo = usuarioRepository.save(usuario);

            return usuarioSalvo;
        }
    }


    @Override
    public void deletarUsuario(Integer id) throws RegraNegocioException {

        Usuario usuario = encontrarUsuarioPeloId(id);

        usuarioRepository.delete(usuario);

    }

    @Override
    public UsuarioDTO atualizarUsuario(UsuarioDTO usuarioDTO) {
        return null;
    }


    @Override
    public List<UsuarioDTO> listarUsuarios(Integer paginaAtual){
        List<Usuario> listaUsuariosBanco = new ArrayList<>();
        List<UsuarioDTO> listaUsuariosDTO = new ArrayList<>();
        Pageable pageable = PageRequest.of(paginaAtual,10);

        listaUsuariosBanco = usuarioRepository.findAll(pageable).toList();
        listaUsuariosDTO =
            listaUsuariosBanco.stream().map(usuario -> {
            UsuarioDTO usuarioDTO = converterUsuarioParaUsuarioDTO(usuario);
            return usuarioDTO;
        }).collect(Collectors.toList());

        return listaUsuariosDTO;
    }

    // Métodos Auxiliares

    @Override
    public Usuario encontrarUsuarioPeloId(Integer id) throws RegraNegocioException {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado!"));

        return usuario;
    }

    public UsuarioDTO converterUsuarioParaUsuarioDTO(Usuario usuario){

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setLogin(usuario.getLogin());
        usuarioDTO.setSenha(usuario.getSenha());
        usuarioDTO.setNome(usuario.getNome());
        usuarioDTO.setEmail(usuario.getEmail());

        return usuarioDTO;
    }


    public boolean verificarDisponibilidadeDoLogin(String login){

        boolean loginDisponivel = usuarioRepository.existsByLogin(login);

        return loginDisponivel;

    }

    public boolean verificarDisponibilidadeDoEmail(String email){

        boolean emailDisponivel = usuarioRepository.existsByEmail(email);

        return emailDisponivel;

    }

}
