package io.github.pedropizzutti.acervo_referencias.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

    private Integer id;

    @NotEmpty(message = "Campo Login Obrigatório!")
    private String login;

    @NotEmpty(message = "Campo Senha Obrigatório!")
    private String senha;

    @NotEmpty(message = "Campo Nome Obrigatório!")
    private String nome;

    @Email(message = "Campo Email Obrigatório!")
    private String email;

    @JsonIgnore
    private List<LivroDTO> livros;

}
