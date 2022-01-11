package io.github.pedropizzutti.acervo_referencias.domain.repository;

import io.github.pedropizzutti.acervo_referencias.domain.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    public Optional<Usuario> findById(Integer id);

    public boolean existsByLogin(String login);

    public boolean existsByEmail(String email);

}
