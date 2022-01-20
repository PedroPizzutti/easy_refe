package io.github.pedropizzutti.acervo_referencias.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_usuario")
public class Usuario {

    @Id
    @Column(name = "id_usuario")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "login_usuario", length = 30, nullable = false, unique = true)
    private String login;

    @Column(name = "senha_usuario", length = 150, nullable = false)
    private String senha;

    @Column(name = "nome_usuario", length = 50, nullable = false)
    private String nome;

    @Column(name = "email_usuario", length = 100, nullable = false, unique = true)
    private String email;

    @Column(name = "admin_usuario")
    private boolean admin;

    @JsonIgnore
    @OneToMany(mappedBy = "usuario")
    private List<Livro> livros;

}
