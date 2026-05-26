package com.sena.bloodbank.domain;

import com.sena.bloodbank.enums.TipoSangre;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "blood_inventory")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class InventarioSangre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true, length = 20)
    private TipoSangre tipoSangre;

    @Column(nullable = false)
    private Integer cantidadDisponibleML;

    @Column(nullable = false)
    private LocalDateTime ultimaActualizacion;

    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        this.ultimaActualizacion = LocalDateTime.now();
    }
}
