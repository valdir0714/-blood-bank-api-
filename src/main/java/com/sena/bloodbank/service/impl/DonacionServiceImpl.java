package com.sena.bloodbank.service.impl;

import com.sena.bloodbank.domain.Donacion;
import com.sena.bloodbank.domain.Donante;
import com.sena.bloodbank.domain.InventarioSangre;
import com.sena.bloodbank.dto.request.DonacionRequest;
import com.sena.bloodbank.dto.response.DonacionResponse;
import com.sena.bloodbank.exception.*;
import com.sena.bloodbank.mapper.DonacionMapper;
import com.sena.bloodbank.repository.DonacionRepository;
import com.sena.bloodbank.repository.DonanteRepository;
import com.sena.bloodbank.repository.InventarioRepository;
import com.sena.bloodbank.service.DonacionService;
import com.sena.bloodbank.service.ValidacionDonante;
import com.sena.bloodbank.util.CodigoGenerador;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sena.bloodbank.enums.TipoSangre;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DonacionServiceImpl implements DonacionService {

    private final DonacionRepository donacionRepository;
    private final DonanteRepository donanteRepository;
    private final InventarioRepository inventarioRepository;
    private final DonacionMapper donacionMapper;
    private final List<ValidacionDonante> validaciones;

    @Override
    @Transactional
    public DonacionResponse registrar(DonacionRequest request) {
        Donante donante = donanteRepository.findById(request.getDonanteId())
                .orElseThrow(() -> new ResourceNotFoundException("Donante", request.getDonanteId()));

        validarDonante(donante);

        Donacion donacion = donacionMapper.toEntity(request);
        donacion.setDonante(donante);
        donacion.setCodigoUnico(CodigoGenerador.generarCodigoDonacion());

        donacion = donacionRepository.save(donacion);

        actualizarFechaUltimaDonacion(donante);
        actualizarInventario(donante.getTipoSangre(), request.getCantidadML());

        log.info("Donacion registrada con codigo: {}", donacion.getCodigoUnico());
        return donacionMapper.toResponse(donacion);
    }

    @Override
    @Transactional(readOnly = true)
    public DonacionResponse obtenerPorId(Long id) {
        Donacion donacion = donacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Donacion", id));
        return donacionMapper.toResponse(donacion);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DonacionResponse> listarTodas(Pageable pageable) {
        return donacionRepository.findAll(pageable)
                .map(donacionMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DonacionResponse> historialPorDonante(Long donanteId) {
        if (!donanteRepository.existsById(donanteId)) {
            throw new ResourceNotFoundException("Donante", donanteId);
        }
        return donacionRepository.findByDonanteIdOrderByFechaDonacionDesc(donanteId)
                .stream()
                .map(donacionMapper::toResponse)
                .toList();
    }

    private void validarDonante(Donante donante) {
        for (ValidacionDonante validacion : validaciones) {
            validacion.validar(donante);
        }
    }

    private void actualizarFechaUltimaDonacion(Donante donante) {
        donante.setFechaUltimaDonacion(LocalDate.now());
        donanteRepository.save(donante);
    }

    private void actualizarInventario(TipoSangre tipoSangre, Integer cantidadML) {
        InventarioSangre inventario = inventarioRepository.findByTipoSangre(tipoSangre)
                .orElseGet(() -> {
                    InventarioSangre nuevo = InventarioSangre.builder()
                            .tipoSangre(tipoSangre)
                            .cantidadDisponibleML(0)
                            .build();
                    return inventarioRepository.save(nuevo);
                });

        inventario.setCantidadDisponibleML(inventario.getCantidadDisponibleML() + cantidadML);
        inventarioRepository.save(inventario);
    }
}
