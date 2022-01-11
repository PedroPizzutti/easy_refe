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

    @NotEmpty(message = "Campo Login Obrigatório!")
    @Length(min = 4, max = 30)
    private String login;

    @NotEmpty(message = "Campo Senha Obrigatório!")
    @Length(min = 8, max = 70)
    private String senha;

    @NotEmpty(message = "Campo Nome Obrigatório!")
    @Length(min = 4, max = 100)
    private String nome;

    @NotEmpty(message = "Campo Email Obrigatório!")
    @Email(message = "Insira um Email Válido.")
    private String email;

    @JsonIgnore
    private List<LivroDTO> livros;

}
