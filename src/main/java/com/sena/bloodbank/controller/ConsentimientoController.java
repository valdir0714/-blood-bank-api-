package com.sena.bloodbank.controller;

import com.sena.bloodbank.dto.request.ConsentimientoRequest;
import com.sena.bloodbank.dto.response.DonanteResponse;
import com.sena.bloodbank.service.DonanteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Consentimientos", description = "Gestion de consentimientos informados")
@RestController
@RequestMapping("/api/consentimientos")
@RequiredArgsConstructor
public class ConsentimientoController {

    private final DonanteService donanteService;

    @Operation(summary = "Firmar consentimiento informado")
    @PostMapping
    public ResponseEntity<DonanteResponse> firmar(@Valid @RequestBody ConsentimientoRequest request) {
        return ResponseEntity.ok(donanteService.firmarConsentimiento(request));
    }

    @Operation(summary = "Verificar si un donante tiene consentimiento valido")
    @GetMapping("/{donanteId}/valido")
    public ResponseEntity<Boolean> verificarConsentimiento(@PathVariable Long donanteId) {
        return ResponseEntity.ok(donanteService.tieneConsentimientoValido(donanteId));
    }
}
