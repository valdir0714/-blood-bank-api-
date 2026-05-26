package com.sena.bloodbank.controller;

import com.sena.bloodbank.dto.response.InventarioResponse;
import com.sena.bloodbank.service.InventarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "Inventario", description = "Consulta de inventario de sangre")
@RestController
@RequestMapping("/api/inventario")
@RequiredArgsConstructor
public class InventarioController {

    private final InventarioService inventarioService;

    @Operation(summary = "Obtener inventario completo")
    @GetMapping
    public ResponseEntity<List<InventarioResponse>> obtenerInventario() {
        return ResponseEntity.ok(inventarioService.obtenerInventarioCompleto());
    }

    @Operation(summary = "Obtener inventario por tipo de sangre")
    @GetMapping("/{tipoSangre}")
    public ResponseEntity<InventarioResponse> obtenerPorTipo(@PathVariable String tipoSangre) {
        return ResponseEntity.ok(inventarioService.obtenerPorTipoSangre(tipoSangre));
    }
}
