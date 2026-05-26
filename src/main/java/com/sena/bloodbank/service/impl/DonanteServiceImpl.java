package com.sena.bloodbank.service.impl;

import com.sena.bloodbank.domain.Donante;
import com.sena.bloodbank.dto.request.ConsentimientoRequest;
import com.sena.bloodbank.dto.request.DonanteRequest;
import com.sena.bloodbank.dto.response.DonanteResponse;
import com.sena.bloodbank.exception.*;
import com.sena.bloodbank.mapper.DonanteMapper;
import com.sena.bloodbank.repository.DonanteRepository;
import com.sena.bloodbank.service.DonanteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DonanteServiceImpl implements DonanteService {

    private final DonanteRepository donanteRepository;
    private final DonanteMapper donanteMapper;

    @Override
    @Transactional
    public DonanteResponse registrar(DonanteRequest request) {
        if (donanteRepository.existsByDocumento(request.getDocumento())) {
            throw new BusinessException("Ya existe un donante con el documento: " + request.getDocumento());
        }

        Donante donante = donanteMapper.toEntity(request);
        donante = donanteRepository.save(donante);
        log.info("Donante registrado con id: {}", donante.getId());
        return donanteMapper.toResponse(donante);
    }

    @Override
    @Transactional
    public DonanteResponse actualizar(Long id, DonanteRequest request) {
        Donante donante = donanteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Donante", id));

        donanteMapper.updateEntity(request, donante);
        donante = donanteRepository.save(donante);
        log.info("Donante actualizado con id: {}", id);
        return donanteMapper.toResponse(donante);
    }

    @Override
    @Transactional(readOnly = true)
    public DonanteResponse obtenerPorId(Long id) {
        Donante donante = donanteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Donante", id));
        return donanteMapper.toResponse(donante);
    }

    @Override
    @Transactional(readOnly = true)
    public DonanteResponse obtenerPorDocumento(String documento) {
        Donante donante = donanteRepository.findByDocumento(documento)
                .orElseThrow(() -> new ResourceNotFoundException("Donante", "documento", documento));
        return donanteMapper.toResponse(donante);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DonanteResponse> listarTodos(Pageable pageable) {
        return donanteRepository.findAll(pageable)
                .map(donanteMapper::toResponse);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!donanteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Donante", id);
        }
        donanteRepository.deleteById(id);
        log.info("Donante eliminado con id: {}", id);
    }

    @Override
    @Transactional
    public DonanteResponse firmarConsentimiento(ConsentimientoRequest request) {
        Donante donante = donanteRepository.findById(request.getDonanteId())
                .orElseThrow(() -> new ResourceNotFoundException("Donante", request.getDonanteId()));

        if (Boolean.FALSE.equals(request.getAceptaConsentimiento())) {
            throw new BusinessException("Debe aceptar el consentimiento informado");
        }

        donante.setAceptaConsentimiento(true);
        donante.setFirmaConsentimiento(request.getFirmaConsentimiento());
        donante = donanteRepository.save(donante);
        log.info("Consentimiento firmado para donante id: {}", donante.getId());
        return donanteMapper.toResponse(donante);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean tieneConsentimientoValido(Long donanteId) {
        Donante donante = donanteRepository.findById(donanteId)
                .orElseThrow(() -> new ResourceNotFoundException("Donante", donanteId));
        return Boolean.TRUE.equals(donante.getAceptaConsentimiento())
                && donante.getFirmaConsentimiento() != null
                && !donante.getFirmaConsentimiento().isBlank();
    }
}
