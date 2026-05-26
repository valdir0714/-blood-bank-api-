package com.sena.bloodbank.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String recurso, Long id) {
        super(recurso + " no encontrado con id: " + id);
    }

    public ResourceNotFoundException(String recurso, String criterio, String valor) {
        super(recurso + " no encontrado con " + criterio + ": " + valor);
    }
}
