package com.sena.bloodbank.dto.response;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class InventarioResponse {

    private Long id;
    private String tipoSangre;
    private Integer cantidadDisponibleML;
    private LocalDateTime ultimaActualizacion;
}
