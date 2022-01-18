package io.github.pedropizzutti.acervo_referencias.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LivroDTO {

    private Integer idRegistro;

    @NotNull(message = "{campo.livro.usuario.obrigatorio}")
    private Integer idUsuario;

    @NotEmpty(message = "{campo.livro.autor.obrigatorio}")
    @Length(min = 4, max = 100, message = "{campo.livro.autor.comprimento}")
    private String autor;

    @NotEmpty(message = "{campo.livro.titulo.obrigatorio}")
    @Length(min = 4, max = 200, message = "{campo.livro.titulo.comprimento}")
    private String titulo;

    @Max(value = 9999, message = "{campo.livro.ano.comprimento}")
    private Integer ano;

    @NotEmpty(message = "{campo.livro.referencia.obrigatorio}")
    @Length(min = 30, max = 300, message = "{campo.livro.referencia.comprimento}")
    private String referencia;

    @Size(max = 300, message = "{campo.livro.anotacao.comprimento}")
    private String anotacao;

}
