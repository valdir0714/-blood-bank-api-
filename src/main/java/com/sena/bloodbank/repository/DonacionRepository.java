package com.sena.bloodbank.repository;

import com.sena.bloodbank.domain.Donacion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface DonacionRepository extends JpaRepository<Donacion, Long> {
    List<Donacion> findByDonanteIdOrderByFechaDonacionDesc(Long donanteId);
    Page<Donacion> findByDonanteId(Long donanteId, Pageable pageable);
    long countByDonanteIdAndFechaDonacionAfter(Long donanteId, LocalDate fecha);
}
