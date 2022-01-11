package io.github.pedropizzutti.acervo_referencias.validation;

import io.github.pedropizzutti.acervo_referencias.validation.constraintvalidation.PasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = PasswordValidator.class)
public @interface Password {

    String message() default  "A senha deve conter ao menos, um Número," +
            " uma Letra Minúscula, uma Letra Maiúscula e um Símbolo.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default  {};

}
