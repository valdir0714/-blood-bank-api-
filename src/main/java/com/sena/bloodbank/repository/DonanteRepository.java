package com.sena.bloodbank.repository;

import com.sena.bloodbank.domain.Donante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface DonanteRepository extends JpaRepository<Donante, Long> {
    Optional<Donante> findByDocumento(String documento);
    boolean existsByDocumento(String documento);
}
