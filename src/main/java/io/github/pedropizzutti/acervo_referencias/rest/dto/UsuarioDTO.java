package io.github.pedropizzutti.acervo_referencias.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

    private Integer id;

    @NotEmpty(message = "{campo.usuario.login.obrigatorio}")
    @Length(min = 4, max = 30, message = "{campo.usuario.login.comprimento}")
    private String login;

    @NotEmpty(message = "{campo.usuario.senha.obrigatorio}")
    @Length(min = 8, max = 50, message = "{campo.usuario.senha.comprimento}")
    private String senha;

    @NotEmpty(message = "{campo.usuario.nome.obrigatorio}")
    @Length(min = 4, max = 100, message = "{campo.usuario.nome.comprimento}")
    private String nome;

    @NotEmpty(message = "{campo.usuario.login.obrigatorio}")
    @Email(message = "Insira um Email Válido.")
    private String email;

    @JsonIgnore
    private List<LivroDTO> livros;

}
