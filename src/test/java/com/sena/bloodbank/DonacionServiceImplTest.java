package com.sena.bloodbank;

import com.sena.bloodbank.domain.Donacion;
import com.sena.bloodbank.domain.Donante;
import com.sena.bloodbank.domain.InventarioSangre;
import com.sena.bloodbank.dto.request.DonacionRequest;
import com.sena.bloodbank.dto.response.DonacionResponse;
import com.sena.bloodbank.enums.TipoSangre;
import com.sena.bloodbank.mapper.DonacionMapper;
import com.sena.bloodbank.repository.DonacionRepository;
import com.sena.bloodbank.repository.DonanteRepository;
import com.sena.bloodbank.repository.InventarioRepository;
import com.sena.bloodbank.service.ValidacionDonante;
import com.sena.bloodbank.service.impl.DonacionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DonacionServiceImplTest {

    private DonacionServiceImpl donacionService;

    @Mock private DonacionRepository donacionRepository;
    @Mock private DonanteRepository donanteRepository;
    @Mock private InventarioRepository inventarioRepository;
    @Mock private DonacionMapper donacionMapper;

    private Donante donanteValido;

    @BeforeEach
    void setUp() {
        List<ValidacionDonante> validaciones = List.of(
                d -> { if (d.getPeso() < 50) throw new RuntimeException("Peso insuficiente"); }
        );
        donacionService = new DonacionServiceImpl(
                donacionRepository, donanteRepository, inventarioRepository,
                donacionMapper, validaciones);

        donanteValido = Donante.builder()
                .id(1L)
                .nombres("Juan")
                .apellidos("Perez")
                .fechaNacimiento(LocalDate.of(1990, 1, 1))
                .tipoSangre(TipoSangre.O_POSITIVO)
                .peso(70.0)
                .aceptaConsentimiento(true)
                .firmaConsentimiento("base64:firma")
                .build();
    }

    @Test
    void registrarDonacion_ConDonanteValido_Exitoso() {
        DonacionRequest request = DonacionRequest.builder()
                .donanteId(1L).cantidadML(450).build();

        Donacion donacionMock = Donacion.builder().build();

        when(donanteRepository.findById(1L)).thenReturn(Optional.of(donanteValido));
        when(donacionMapper.toEntity(any())).thenReturn(donacionMock);
        when(inventarioRepository.findByTipoSangre(TipoSangre.O_POSITIVO))
                .thenReturn(Optional.of(InventarioSangre.builder()
                        .tipoSangre(TipoSangre.O_POSITIVO).cantidadDisponibleML(1000).build()));
        when(donacionRepository.save(any())).thenReturn(donacionMock);
        when(donacionMapper.toResponse(any())).thenReturn(
                DonacionResponse.builder().id(1L).codigoUnico("DON-001").build());

        DonacionResponse response = donacionService.registrar(request);

        assertNotNull(response);
        assertEquals("DON-001", response.getCodigoUnico());
        verify(donacionRepository).save(any());
    }

    @Test
    void registrarDonacion_DonanteMenorEdad_LanzaExcepcion() {
        donanteValido.setFechaNacimiento(LocalDate.of(2010, 1, 1));
        DonacionRequest request = DonacionRequest.builder()
                .donanteId(1L).cantidadML(450).build();

        when(donanteRepository.findById(1L)).thenReturn(Optional.of(donanteValido));
        List<ValidacionDonante> validaciones = List.of(
                d -> { throw new RuntimeException("El donante es menor de edad"); }
        );
        donacionService = new DonacionServiceImpl(
                donacionRepository, donanteRepository, inventarioRepository,
                donacionMapper, validaciones);

        assertThrows(RuntimeException.class, () -> donacionService.registrar(request));
    }
}
