package io.github.pedropizzutti.acervo_referencias.domain.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@Entity
@Table(name = "tb_usuario")
public class Usuario {

    @Id
    @Column(name = "id_usuario")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "login_usuario", length = 50, nullable = false)
    @NotEmpty(message = "Login obrigatório.")
    private String login;

    @Column(name = "senha_usuario", length = 75, nullable = false)
    @NotEmpty(message = "senha obrigatória.")
    private String senha;

    @Column(name = "nome_usuario", length = 50, nullable = false)
    @NotEmpty(message = "nome obrigatório.")
    private String nome;

    @Column(name = "email_usuario", length = 100, nullable = false)
    @NotEmpty(message = "email obrigatório.")
    private String email;

}
