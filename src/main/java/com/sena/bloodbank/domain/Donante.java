package com.sena.bloodbank.domain;

import com.sena.bloodbank.enums.TipoSangre;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "donors")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Donante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombres;

    @Column(nullable = false, length = 100)
    private String apellidos;

    @Column(nullable = false, unique = true, length = 20)
    private String documento;

    @Column(nullable = false)
    private LocalDate fechaNacimiento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoSangre tipoSangre;

    @Column(nullable = false)
    private Double peso;

    @Column(nullable = false, length = 15)
    private String telefono;

    @Column(nullable = false, length = 150)
    private String correo;

    @Column(nullable = false, length = 200)
    private String direccion;

    private LocalDate fechaUltimaDonacion;

    @Column(nullable = false)
    private Boolean aceptaConsentimiento;

    @Column(columnDefinition = "TEXT")
    private String firmaConsentimiento;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = LocalDateTime.now();
    }
}
