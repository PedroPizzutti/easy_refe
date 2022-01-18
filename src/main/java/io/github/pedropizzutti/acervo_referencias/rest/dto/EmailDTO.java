package io.github.pedropizzutti.acervo_referencias.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailDTO {

    @Email(message = "Insira um Email Válido.")
    private String novoEmail;

}
