package com.sena.bloodbank.dto.response;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DonacionResponse {

    private Long id;
    private Long donanteId;
    private String donanteNombre;
    private Integer cantidadML;
    private LocalDate fechaDonacion;
    private String observaciones;
    private String codigoUnico;
    private LocalDateTime fechaRegistro;
}
