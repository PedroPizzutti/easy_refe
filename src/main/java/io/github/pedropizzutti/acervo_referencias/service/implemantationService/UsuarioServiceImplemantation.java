package io.github.pedropizzutti.acervo_referencias.service.implemantationService;

import io.github.pedropizzutti.acervo_referencias.domain.entity.Usuario;
import io.github.pedropizzutti.acervo_referencias.domain.repository.UsuarioRepository;
import io.github.pedropizzutti.acervo_referencias.exception.RegraNegocioException;
import io.github.pedropizzutti.acervo_referencias.rest.dto.UsuarioDTO;
import io.github.pedropizzutti.acervo_referencias.service.UsuarioService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioServiceImplemantation implements UsuarioService {

    private UsuarioRepository usuarioRepository;

    public UsuarioServiceImplemantation(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }


    @Override
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
            usuario.setSenha(usuarioDTO.getSenha());
            usuario.setEmail(usuarioDTO.getEmail());
            usuario.setNome(usuarioDTO.getNome());

            Usuario usuarioSalvo = usuarioRepository.save(usuario);

            return usuarioSalvo;
        }

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
