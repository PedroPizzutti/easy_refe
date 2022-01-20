package io.github.pedropizzutti.acervo_referencias.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailDTO {

    @Email(message = "{campo.email.valido}")
    private String emailAtual;

    @Email(message = "{campo.email.valido}")
    private String novoEmail;

    @NotEmpty(message = "{campo.att-email.confirma-novo-email-obrigatorio}")
    private String confirmacaoEmailNovo;

}
