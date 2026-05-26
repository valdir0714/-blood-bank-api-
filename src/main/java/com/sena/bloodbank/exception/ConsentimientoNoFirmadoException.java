package com.sena.bloodbank.exception;

public class ConsentimientoNoFirmadoException extends BusinessException {
    public ConsentimientoNoFirmadoException() {
        super("El donante no ha firmado el consentimiento informado");
    }

    public ConsentimientoNoFirmadoException(Long donanteId) {
        super("El donante con id " + donanteId + " no ha firmado el consentimiento informado");
    }
}
