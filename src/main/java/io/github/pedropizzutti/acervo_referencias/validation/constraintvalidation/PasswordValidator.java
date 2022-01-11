package io.github.pedropizzutti.acervo_referencias.validation.constraintvalidation;

import io.github.pedropizzutti.acervo_referencias.validation.Password;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {
    @Override
    public void initialize(Password constraintAnnotation) {
        constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String senha, ConstraintValidatorContext constraintValidatorContext) {

        boolean achouNumero = false;
        boolean achouLetraMinuscula = false;
        boolean achouLetraMaiscula = false;
        boolean achouSimbolo = false;

        for(char caracter : senha.toCharArray()){

            if(caracter >= '0' && caracter <= '9'){
                achouNumero = true;

            } else if(caracter >= 'A' && caracter <= 'Z'){
                achouLetraMaiscula = true;

            } else if(caracter >= 'a' && caracter <= 'z'){
                achouLetraMinuscula = true;

            } else {
                achouSimbolo = true;

            }
        }

        boolean senhaValida = achouNumero && achouLetraMaiscula && achouLetraMinuscula && achouSimbolo;

        return senhaValida;
    }
}
