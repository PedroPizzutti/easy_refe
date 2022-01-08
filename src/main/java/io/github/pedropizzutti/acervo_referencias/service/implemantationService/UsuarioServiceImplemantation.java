package io.github.pedropizzutti.acervo_referencias.service.implemantationService;

import io.github.pedropizzutti.acervo_referencias.domain.entity.Usuario;
import io.github.pedropizzutti.acervo_referencias.domain.repository.UsuarioRepository;
import io.github.pedropizzutti.acervo_referencias.rest.dto.UsuarioDTO;
import io.github.pedropizzutti.acervo_referencias.service.UsuarioService;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImplemantation implements UsuarioService {

    private UsuarioRepository usuarioRepository;

    public UsuarioServiceImplemantation(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }


    @Override
    public Usuario salvar(UsuarioDTO usuarioDTO) {

        Usuario usuario = new Usuario();
        usuario.setLogin(usuarioDTO.getLogin());
        usuario.setSenha(usuarioDTO.getSenha());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setNome(usuarioDTO.getNome());

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return usuarioSalvo;

    }
}
