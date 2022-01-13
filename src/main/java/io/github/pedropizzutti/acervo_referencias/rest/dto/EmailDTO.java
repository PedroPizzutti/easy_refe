package io.github.pedropizzutti.acervo_referencias.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

@Data
@NoArgsConstructor
public class EmailDTO {

    @Email(message = "Insira um Email Válido.")
    private String novoEmail;

}
