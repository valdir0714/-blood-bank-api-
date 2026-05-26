package com.sena.bloodbank.mapper;

import com.sena.bloodbank.domain.InventarioSangre;
import com.sena.bloodbank.dto.response.InventarioResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InventarioMapper {

    @Mapping(target = "tipoSangre", expression = "java(inventario.getTipoSangre().getEtiqueta())")
    InventarioResponse toResponse(InventarioSangre inventario);
}
