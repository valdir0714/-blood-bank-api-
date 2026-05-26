package com.sena.bloodbank.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ConsentimientoRequest {

    @NotNull(message = "El ID del donante es obligatorio")
    @Positive
    private Long donanteId;

    @NotNull(message = "Debe indicar si acepta el consentimiento")
    @AssertTrue(message = "Debe aceptar el consentimiento")
    private Boolean aceptaConsentimiento;

    @NotBlank(message = "La firma es obligatoria")
    private String firmaConsentimiento;
}
