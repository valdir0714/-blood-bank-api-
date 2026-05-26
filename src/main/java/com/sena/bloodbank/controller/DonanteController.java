package com.sena.bloodbank.controller;

import com.sena.bloodbank.dto.request.ConsentimientoRequest;
import com.sena.bloodbank.dto.request.DonanteRequest;
import com.sena.bloodbank.dto.response.DonanteResponse;
import com.sena.bloodbank.service.DonanteService;
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

@Tag(name = "Donantes", description = "Gestion de donantes de sangre")
@RestController
@RequestMapping("/api/donantes")
@RequiredArgsConstructor
public class DonanteController {

    private final DonanteService donanteService;

    @Operation(summary = "Registrar un nuevo donante")
    @PostMapping
    public ResponseEntity<DonanteResponse> registrar(@Valid @RequestBody DonanteRequest request) {
        return new ResponseEntity<>(donanteService.registrar(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Obtener todos los donantes (paginado)")
    @GetMapping
    public ResponseEntity<Page<DonanteResponse>> listarTodos(
            @PageableDefault(size = 10, sort = "fechaCreacion") Pageable pageable) {
        return ResponseEntity.ok(donanteService.listarTodos(pageable));
    }

    @Operation(summary = "Obtener donante por ID")
    @GetMapping("/{id}")
    public ResponseEntity<DonanteResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(donanteService.obtenerPorId(id));
    }

    @Operation(summary = "Obtener donante por documento")
    @GetMapping("/documento/{documento}")
    public ResponseEntity<DonanteResponse> obtenerPorDocumento(@PathVariable String documento) {
        return ResponseEntity.ok(donanteService.obtenerPorDocumento(documento));
    }

    @Operation(summary = "Actualizar donante")
    @PutMapping("/{id}")
    public ResponseEntity<DonanteResponse> actualizar(
            @PathVariable Long id, @Valid @RequestBody DonanteRequest request) {
        return ResponseEntity.ok(donanteService.actualizar(id, request));
    }

    @Operation(summary = "Eliminar donante")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        donanteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
