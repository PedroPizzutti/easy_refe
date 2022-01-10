package io.github.pedropizzutti.acervo_referencias.service.implemantationService;

import io.github.pedropizzutti.acervo_referencias.domain.entity.Usuario;
import io.github.pedropizzutti.acervo_referencias.domain.repository.UsuarioRepository;
import io.github.pedropizzutti.acervo_referencias.exception.RegraNegocioException;
import io.github.pedropizzutti.acervo_referencias.exception.UsuarioNaoEncontradoException;
import io.github.pedropizzutti.acervo_referencias.rest.dto.UsuarioDTO;
import io.github.pedropizzutti.acervo_referencias.service.UsuarioService;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImplemantation implements UsuarioService {

    private final Integer ELEMENTOS_POR_PAGINA = 10;
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

        Usuario usuario = encontrarVerificarUsuarioPeloId(id);

        usuarioRepository.delete(usuario);

    }

    @Override
    public UsuarioDTO atualizarUsuario(UsuarioDTO usuarioDTO, Integer id) throws RegraNegocioException {

        Usuario usuario = encontrarVerificarUsuarioPeloId(id);

        usuario.setLogin(usuarioDTO.getLogin());
        usuario.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());

        Usuario usuarioAtualizado = usuarioRepository.save(usuario);

        UsuarioDTO usuarioDTOAtualizado = converterUsuarioParaUsuarioDTO(usuarioAtualizado);

        return usuarioDTOAtualizado;
    }

    @Override
    public void atualizarEmailUsuario(@Valid String novoEmail, Integer idUsuario) throws RegraNegocioException{

        Usuario usuario = encontrarVerificarUsuarioPeloId(idUsuario);

        usuario.setEmail(novoEmail);

        usuarioRepository.save(usuario);

    }

    @Override
    public List<UsuarioDTO> listarUsuarios(Integer paginaAtual){
        List<Usuario> listaUsuariosBanco = new ArrayList<>();
        List<UsuarioDTO> listaUsuariosDTO = new ArrayList<>();
        Pageable pageable = PageRequest.of(paginaAtual,ELEMENTOS_POR_PAGINA, Sort.by("id"));

        listaUsuariosBanco = usuarioRepository.findAll(pageable).toList();
        listaUsuariosDTO =
            listaUsuariosBanco.stream().map(usuario -> {
            UsuarioDTO usuarioDTO = converterUsuarioParaUsuarioDTO(usuario);
            return usuarioDTO;
        }).collect(Collectors.toList());

        return listaUsuariosDTO;
    }

    @Override
    public List<UsuarioDTO> listarUsuariosFiltro(UsuarioDTO usuarioDTOFiltro, Integer numeroPaginacao) throws RegraNegocioException {

        List<Usuario> listaUsuarioBanco = new ArrayList<>();
        List<UsuarioDTO> listaUsuarioDTO = new ArrayList<>();

        String login = usuarioDTOFiltro.getLogin();
        String nome = usuarioDTOFiltro.getNome();
        String email = usuarioDTOFiltro.getEmail();

        if((login.equals("") || login == null) &&
                (nome.equals("") || nome == null) &&
                (email.equals("") || email == null)
        ){

            throw new RegraNegocioException("Nenhum dado informado para consulta...");

        } else {

            Usuario usuario = new Usuario();
            usuario.setLogin(login);
            usuario.setNome(nome);
            usuario.setEmail(email);

                ExampleMatcher matcher =
                        ExampleMatcher
                                .matching()
                                .withIgnoreCase()
                                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

            Example exampleFiltro = Example.of(usuario, matcher);

            Pageable configuracaoPagina = PageRequest.of(numeroPaginacao, ELEMENTOS_POR_PAGINA, Sort.by("login"));

            listaUsuarioBanco = usuarioRepository.findAll(exampleFiltro, configuracaoPagina).toList();

            listaUsuarioDTO =
                    listaUsuarioBanco.stream().map(user -> {
                        UsuarioDTO usuarioDTO = converterUsuarioParaUsuarioDTO(user);
                        return usuarioDTO;
                    }).collect(Collectors.toList());

            return listaUsuarioDTO;

        }

    }

    // Métodos Auxiliares

    @Override
    public Usuario encontrarVerificarUsuarioPeloId(Integer id) throws RegraNegocioException {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado!"));

        return usuario;
    }

    @Override
    public UsuarioDTO converterUsuarioParaUsuarioDTO(Usuario usuario){

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setLogin(usuario.getLogin());
        usuarioDTO.setSenha(usuario.getSenha());
        usuarioDTO.setNome(usuario.getNome());
        usuarioDTO.setEmail(usuario.getEmail());

        return usuarioDTO;
    }

    @Override
    public boolean verificarDisponibilidadeDoLogin(String login){

        boolean loginDisponivel = usuarioRepository.existsByLogin(login);

        return loginDisponivel;

    }

    @Override
    public boolean verificarDisponibilidadeDoEmail(String email){

        boolean emailDisponivel = usuarioRepository.existsByEmail(email);

        return emailDisponivel;

    }

}
