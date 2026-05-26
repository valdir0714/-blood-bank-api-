package com.sena.bloodbank.enums;

public enum TipoSangre {
    O_POSITIVO("O+"),
    O_NEGATIVO("O-"),
    A_POSITIVO("A+"),
    A_NEGATIVO("A-"),
    B_POSITIVO("B+"),
    B_NEGATIVO("B-"),
    AB_POSITIVO("AB+"),
    AB_NEGATIVO("AB-");

    private final String etiqueta;

    TipoSangre(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public static TipoSangre fromEtiqueta(String etiqueta) {
        for (TipoSangre t : values()) {
            if (t.etiqueta.equalsIgnoreCase(etiqueta)) {
                return t;
            }
        }
        throw new IllegalArgumentException("Tipo de sangre invalido: " + etiqueta);
    }
}
