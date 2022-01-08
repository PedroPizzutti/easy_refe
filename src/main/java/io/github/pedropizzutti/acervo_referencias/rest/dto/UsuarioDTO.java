package io.github.pedropizzutti.acervo_referencias.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @NotEmpty(message = "Campo Email Obrigatório!")
    private String email;

    private List<LivroDTO> livros;

}
