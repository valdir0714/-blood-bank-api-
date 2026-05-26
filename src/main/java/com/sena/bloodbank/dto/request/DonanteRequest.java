package com.sena.bloodbank.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DonanteRequest {

    @NotBlank(message = "Los nombres son obligatorios")
    @Size(max = 100)
    private String nombres;

    @NotBlank(message = "Los apellidos son obligatorios")
    @Size(max = 100)
    private String apellidos;

    @NotBlank(message = "El documento es obligatorio")
    @Size(max = 20)
    private String documento;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser una fecha pasada")
    private LocalDate fechaNacimiento;

    @NotBlank(message = "El tipo de sangre es obligatorio")
    private String tipoSangre;

    @NotNull(message = "El peso es obligatorio")
    @Positive(message = "El peso debe ser positivo")
    private Double peso;

    @NotBlank(message = "El telefono es obligatorio")
    @Size(max = 15)
    private String telefono;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo debe tener un formato valido")
    @Size(max = 150)
    private String correo;

    @NotBlank(message = "La direccion es obligatoria")
    @Size(max = 200)
    private String direccion;

    @NotNull(message = "Debe aceptar el consentimiento")
    @AssertTrue(message = "Debe aceptar el consentimiento para donar")
    private Boolean aceptaConsentimiento;

    private String firmaConsentimiento;
}
