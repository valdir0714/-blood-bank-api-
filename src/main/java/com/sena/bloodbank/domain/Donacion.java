package com.sena.bloodbank.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "donations")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Donacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "donante_id", nullable = false)
    private Donante donante;

    @Column(nullable = false)
    private Integer cantidadML;

    @Column(nullable = false)
    private LocalDate fechaDonacion;

    @Column(length = 500)
    private String observaciones;

    @Column(nullable = false, unique = true, length = 20)
    private String codigoUnico;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaRegistro;

    @PrePersist
    protected void onCreate() {
        this.fechaRegistro = LocalDateTime.now();
        if (this.fechaDonacion == null) {
            this.fechaDonacion = LocalDate.now();
        }
    }
}
