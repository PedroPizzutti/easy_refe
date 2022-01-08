package io.github.pedropizzutti.acervo_referencias.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

    private Integer id;
    private String login;
    private String senha;
    private String nome;
    private String email;
    private List<LivroDTO> livros;

}
