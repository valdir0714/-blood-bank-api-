package com.sena.bloodbank.service;

import com.sena.bloodbank.dto.request.DonacionRequest;
import com.sena.bloodbank.dto.response.DonacionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface DonacionService {
    DonacionResponse registrar(DonacionRequest request);
    DonacionResponse obtenerPorId(Long id);
    Page<DonacionResponse> listarTodas(Pageable pageable);
    List<DonacionResponse> historialPorDonante(Long donanteId);
}
