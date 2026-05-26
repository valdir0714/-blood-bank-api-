package com.sena.bloodbank.dto.response;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DonanteResponse {

    private Long id;
    private String nombres;
    private String apellidos;
    private String documento;
    private LocalDate fechaNacimiento;
    private String tipoSangre;
    private Double peso;
    private String telefono;
    private String correo;
    private String direccion;
    private LocalDate fechaUltimaDonacion;
    private Boolean aceptaConsentimiento;
    private String firmaConsentimiento;
    private LocalDateTime fechaCreacion;
}
