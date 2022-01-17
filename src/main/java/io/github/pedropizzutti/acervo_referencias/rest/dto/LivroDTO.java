package io.github.pedropizzutti.acervo_referencias.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LivroDTO {

    private Integer idRegistro;

    @NotNull
    private Integer idUsuario;

    @NotEmpty
    @Length(min = 4, max = 100)
    private String autor;

    @NotEmpty
    @Length(min = 4, max = 200)
    private String titulo;

    private Integer ano;

    @NotEmpty
    @Length(min = 4, max = 300)
    private String referencia;

    private String anotacao;

}
