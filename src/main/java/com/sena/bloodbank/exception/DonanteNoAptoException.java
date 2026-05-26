package com.sena.bloodbank.exception;

public class DonanteNoAptoException extends BusinessException {
    public DonanteNoAptoException(String motivo) {
        super("El donante no es apto para donar: " + motivo);
    }
}
