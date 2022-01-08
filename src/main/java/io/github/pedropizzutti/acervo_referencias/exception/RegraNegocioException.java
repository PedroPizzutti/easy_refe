package io.github.pedropizzutti.acervo_referencias.exception;

public class RegraNegocioException extends Exception {

    public RegraNegocioException() {
    }

    public RegraNegocioException(String message) {
        super(message);
    }

    public RegraNegocioException(String message, Throwable cause) {
        super(message, cause);
    }

    public RegraNegocioException(Throwable cause) {
        super(cause);
    }

    public RegraNegocioException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
