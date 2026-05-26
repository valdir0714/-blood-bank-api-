package com.sena.bloodbank.mapper;

import com.sena.bloodbank.domain.Donante;
import com.sena.bloodbank.dto.request.DonanteRequest;
import com.sena.bloodbank.dto.response.DonanteResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DonanteMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaUltimaDonacion", ignore = true)
    @Mapping(target = "tipoSangre", expression = "java(com.sena.bloodbank.enums.TipoSangre.fromEtiqueta(request.getTipoSangre()))")
    Donante toEntity(DonanteRequest request);

    @Mapping(target = "tipoSangre", expression = "java(donante.getTipoSangre().getEtiqueta())")
    DonanteResponse toResponse(Donante donante);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaUltimaDonacion", ignore = true)
    @Mapping(target = "documento", ignore = true)
    @Mapping(target = "tipoSangre", expression = "java(com.sena.bloodbank.enums.TipoSangre.fromEtiqueta(request.getTipoSangre()))")
    void updateEntity(DonanteRequest request, @MappingTarget Donante donante);
}
