package io.github.pedropizzutti.acervo_referencias.domain.entity;

import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Entity
@Table(name = "tb_usuario")
public class Usuario {

    @Id
    @Column(name = "id_usuario")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "login_usuario", length = 50, nullable = false, unique = true)
    private String login;

    @Column(name = "senha_usuario", length = 150, nullable = false)
    private String senha;

    @Column(name = "nome_usuario", length = 50, nullable = false)
    private String nome;

    @Column(name = "email_usuario", length = 100, nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "usuario")
    private List<Livro> livros;

}
