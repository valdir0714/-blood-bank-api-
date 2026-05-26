package com.sena.bloodbank.service;

import com.sena.bloodbank.dto.response.InventarioResponse;
import java.util.List;

public interface InventarioService {
    List<InventarioResponse> obtenerInventarioCompleto();
    InventarioResponse obtenerPorTipoSangre(String tipoSangre);
}
