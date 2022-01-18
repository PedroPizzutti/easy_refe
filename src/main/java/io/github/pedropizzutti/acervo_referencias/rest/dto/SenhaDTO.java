package io.github.pedropizzutti.acervo_referencias.rest.dto;

import io.github.pedropizzutti.acervo_referencias.validation.Password;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SenhaDTO {

    @NotEmpty
    private String senhaAtual;

    @NotEmpty(message = "{campo.att-senha.nova-senha.obrigatorio}")
    @Length(min = 8, max = 50, message = "{campo.usuario.senha.comprimento}")
    @Password
    private String novaSenha;

    @NotEmpty(message = "{campo.att-senha.confirma-nova-senha-obrigatorio}")
    private String confirmacaoNovaSenha;

}
