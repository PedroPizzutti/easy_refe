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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImplemantation implements UsuarioService {

    private final Integer ELEMENTOS_POR_PAGINA = 10;
    private UsuarioRepository usuarioRepository;
    private PasswordEncoder passwordEncoder;

    public UsuarioServiceImplemantation(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder){
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UsuarioDTO salvar(UsuarioDTO usuarioDTO) throws RegraNegocioException {

        boolean loginIndisponivel = verificarDisponibilidadeDoLogin(usuarioDTO.getLogin());

        boolean emailIndisponivel = verificarDisponibilidadeDoEmail(usuarioDTO.getEmail());

        if(loginIndisponivel) {

            throw new RegraNegocioException("Login não disponível!");

        } else if(emailIndisponivel){

            throw new RegraNegocioException("Já há uma conta para este email!");

        } else {

            Usuario usuario = converterUsuarioDTOParaUsuario(usuarioDTO);

            Usuario usuarioSalvo = usuarioRepository.save(usuario);

            UsuarioDTO usuarioSalvoDTO = converterUsuarioParaUsuarioDTO(usuarioSalvo);

            return usuarioSalvoDTO;
        }
    }


    @Override
    @Transactional
    public void deletarUsuario(Integer id) throws RegraNegocioException {

        Usuario usuario = puxarUsuarioPeloId(id);

        usuarioRepository.delete(usuario);

    }

    @Override
    @Transactional
    public UsuarioDTO atualizarUsuario(UsuarioDTO usuarioDTO, Integer id) throws RegraNegocioException {

        if(usuarioRepository.existsById(id)){

            Usuario usuario = converterUsuarioDTOParaUsuario(usuarioDTO);

            Usuario usuarioAtualizado = usuarioRepository.save(usuario);

            UsuarioDTO usuarioDTOAtualizado = converterUsuarioParaUsuarioDTO(usuarioAtualizado);

            return usuarioDTOAtualizado;

        } else {

            throw new RegraNegocioException("Problema com a autentificação do usuário a ser atualizado.");

        }

    }

    @Override
    @Transactional
    public void atualizarEmailUsuario(String novoEmail, Integer id) throws RegraNegocioException {

            Usuario usuario = puxarUsuarioPeloId(id);

            usuario.setEmail(novoEmail);

            usuarioRepository.save(usuario);

    }

    @Override
    @Transactional
    public void atualizarSenhaUsuario(String senhaAtual, String novaSenha, String confirmacaoNovaSenha, Integer id)
            throws RegraNegocioException {

        Usuario usuario = puxarUsuarioPeloId(id);

        boolean senhaAtualCorreta = passwordEncoder.matches(senhaAtual, usuario.getSenha());

        if(senhaAtualCorreta){

            boolean novaSenhaConfirmada = novaSenha.equals(confirmacaoNovaSenha);

            if(novaSenhaConfirmada){

                usuario.setSenha(passwordEncoder.encode(novaSenha));

                usuarioRepository.save(usuario);

            } else {

                throw new RegraNegocioException("A nova senha não foi confirmada.");

            }
        } else {

            throw new RegraNegocioException("Senha atual incorreta.");

        }

    }

    @Override
    public List<UsuarioDTO> listarUsuarios(Integer paginaAtual){

        List<Usuario> listaUsuariosBanco = new ArrayList<>();
        List<UsuarioDTO> listaUsuariosDTO = new ArrayList<>();
        Pageable configPaginacao = PageRequest.of(paginaAtual,ELEMENTOS_POR_PAGINA, Sort.by("id"));

        listaUsuariosBanco = usuarioRepository.findAll(configPaginacao).toList();
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

            Pageable configuracaoPagina = PageRequest.of(numeroPaginacao, ELEMENTOS_POR_PAGINA, Sort.by("login"));

            ExampleMatcher configMatcher =
                    ExampleMatcher
                            .matching()
                            .withIgnorePaths("admin")
                            .withIgnoreCase()
                            .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

            Usuario usuario = new Usuario();
            usuario.setLogin(login);
            usuario.setNome(nome);
            usuario.setEmail(email);

            Example filtroExample = Example.of(usuario, configMatcher);

            listaUsuarioBanco = usuarioRepository.findAll(filtroExample, configuracaoPagina).toList();

            listaUsuarioDTO =
                    listaUsuarioBanco.stream().map(user -> {
                        UsuarioDTO usuarioDTO = converterUsuarioParaUsuarioDTO(user);
                        return usuarioDTO;
                    }).collect(Collectors.toList());

            return listaUsuarioDTO;

        }

    }

    // Métodos Auxiliares

    private Usuario puxarUsuarioPeloId(Integer id) throws RegraNegocioException {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado!"));

        return usuario;
    }

    private UsuarioDTO converterUsuarioParaUsuarioDTO(Usuario usuario){

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setLogin(usuario.getLogin());
        usuarioDTO.setSenha(usuario.getSenha());
        usuarioDTO.setNome(usuario.getNome());
        usuarioDTO.setEmail(usuario.getEmail());
        usuarioDTO.setAdmin(usuario.isAdmin());

        return usuarioDTO;
    }

    private Usuario converterUsuarioDTOParaUsuario(UsuarioDTO usuarioDTO){

        Usuario usuario = new Usuario();
        usuario.setLogin(usuarioDTO.getLogin());
        usuario.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setAdmin(usuarioDTO.isAdmin());

        return usuario;

    }

    private boolean verificarDisponibilidadeDoLogin(String login){

        boolean loginIndisponivel = usuarioRepository.existsByLogin(login);

        return loginIndisponivel;

    }

    private boolean verificarDisponibilidadeDoEmail(String email){

        boolean emailIndisponivel = usuarioRepository.existsByEmail(email);

        return emailIndisponivel;

    }

}
