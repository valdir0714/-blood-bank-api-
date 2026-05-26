package com.sena.bloodbank.controller;

import com.sena.bloodbank.dto.request.DonacionRequest;
import com.sena.bloodbank.dto.response.DonacionResponse;
import com.sena.bloodbank.service.DonacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "Donaciones", description = "Gestion de donaciones de sangre")
@RestController
@RequestMapping("/api/donaciones")
@RequiredArgsConstructor
public class DonacionController {

    private final DonacionService donacionService;

    @Operation(summary = "Registrar una nueva donacion")
    @PostMapping
    public ResponseEntity<DonacionResponse> registrar(@Valid @RequestBody DonacionRequest request) {
        return new ResponseEntity<>(donacionService.registrar(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Obtener todas las donaciones (paginado)")
    @GetMapping
    public ResponseEntity<Page<DonacionResponse>> listarTodas(
            @PageableDefault(size = 10, sort = "fechaRegistro") Pageable pageable) {
        return ResponseEntity.ok(donacionService.listarTodas(pageable));
    }

    @Operation(summary = "Obtener donacion por ID")
    @GetMapping("/{id}")
    public ResponseEntity<DonacionResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(donacionService.obtenerPorId(id));
    }

    @Operation(summary = "Historial de donaciones por donante")
    @GetMapping("/donante/{donanteId}")
    public ResponseEntity<List<DonacionResponse>> historialPorDonante(@PathVariable Long donanteId) {
        return ResponseEntity.ok(donacionService.historialPorDonante(donanteId));
    }
}
