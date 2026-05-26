package com.sena.bloodbank.service;

import com.sena.bloodbank.dto.request.ConsentimientoRequest;
import com.sena.bloodbank.dto.request.DonanteRequest;
import com.sena.bloodbank.dto.response.DonanteResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DonanteService {
    DonanteResponse registrar(DonanteRequest request);
    DonanteResponse actualizar(Long id, DonanteRequest request);
    DonanteResponse obtenerPorId(Long id);
    DonanteResponse obtenerPorDocumento(String documento);
    Page<DonanteResponse> listarTodos(Pageable pageable);
    void eliminar(Long id);
    DonanteResponse firmarConsentimiento(ConsentimientoRequest request);
    boolean tieneConsentimientoValido(Long donanteId);
}
