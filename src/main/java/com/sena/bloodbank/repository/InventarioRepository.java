package com.sena.bloodbank.repository;

import com.sena.bloodbank.domain.InventarioSangre;
import com.sena.bloodbank.enums.TipoSangre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface InventarioRepository extends JpaRepository<InventarioSangre, Long> {
    Optional<InventarioSangre> findByTipoSangre(TipoSangre tipoSangre);
}
