package io.github.pedropizzutti.acervo_referencias.domain.repository;

import io.github.pedropizzutti.acervo_referencias.domain.entity.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<Livro, Integer> {


}
