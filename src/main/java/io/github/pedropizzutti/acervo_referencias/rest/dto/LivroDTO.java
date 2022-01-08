package io.github.pedropizzutti.acervo_referencias.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LivroDTO {

    private Integer id;
    private String autor;
    private String titulo;
    private Integer ano;
    private String referencia;
    private String anotacao;
    private UsuarioDTO usuarioDTO;

}
