package io.github.pedropizzutti.acervo_referencias.rest.controller;

import io.github.pedropizzutti.acervo_referencias.exception.RegraNegocioException;
import io.github.pedropizzutti.acervo_referencias.rest.apiErrors.ApiErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationControllerAdvice {


    @ExceptionHandler(RegraNegocioException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleRegraNegocioException(RegraNegocioException ex){

        String mensagemErro = ex.getMessage();

        return new ApiErrors(mensagemErro);

    }

}
