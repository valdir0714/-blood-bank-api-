package com.sena.bloodbank.mapper;

import com.sena.bloodbank.domain.Donacion;
import com.sena.bloodbank.dto.request.DonacionRequest;
import com.sena.bloodbank.dto.response.DonacionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DonacionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "codigoUnico", ignore = true)
    @Mapping(target = "fechaRegistro", ignore = true)
    @Mapping(target = "donante", ignore = true)
    Donacion toEntity(DonacionRequest request);

    @Mapping(target = "donanteId", source = "donante.id")
    @Mapping(target = "donanteNombre", expression = "java(donacion.getDonante().getNombres() + \" \" + donacion.getDonante().getApellidos())")
    DonacionResponse toResponse(Donacion donacion);
}
