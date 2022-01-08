package io.github.pedropizzutti.acervo_referencias.domain.repository;

import io.github.pedropizzutti.acervo_referencias.domain.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {


}
