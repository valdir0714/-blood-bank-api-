package com.sena.bloodbank.service.impl;

import com.sena.bloodbank.domain.InventarioSangre;
import com.sena.bloodbank.dto.response.InventarioResponse;
import com.sena.bloodbank.enums.TipoSangre;
import com.sena.bloodbank.exception.ResourceNotFoundException;
import com.sena.bloodbank.mapper.InventarioMapper;
import com.sena.bloodbank.repository.InventarioRepository;
import com.sena.bloodbank.service.InventarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventarioServiceImpl implements InventarioService {

    private final InventarioRepository inventarioRepository;
    private final InventarioMapper inventarioMapper;

    @Override
    @Transactional(readOnly = true)
    public List<InventarioResponse> obtenerInventarioCompleto() {
        return inventarioRepository.findAll()
                .stream()
                .map(inventarioMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public InventarioResponse obtenerPorTipoSangre(String tipoSangre) {
        TipoSangre tipo = TipoSangre.fromEtiqueta(tipoSangre);
        InventarioSangre inventario = inventarioRepository.findByTipoSangre(tipo)
                .orElseThrow(() -> new ResourceNotFoundException("Inventario", "tipoSangre", tipoSangre));
        return inventarioMapper.toResponse(inventario);
    }
}
