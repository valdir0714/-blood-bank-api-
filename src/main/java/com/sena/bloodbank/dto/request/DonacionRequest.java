package com.sena.bloodbank.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DonacionRequest {

    @NotNull(message = "El ID del donante es obligatorio")
    @Positive(message = "El ID del donante debe ser positivo")
    private Long donanteId;

    @NotNull(message = "La cantidad en ML es obligatoria")
    @Positive(message = "La cantidad debe ser positiva")
    @Max(value = 500, message = "La cantidad maxima por donacion es 500 ML")
    private Integer cantidadML;

    private LocalDate fechaDonacion;

    @Size(max = 500)
    private String observaciones;
}
