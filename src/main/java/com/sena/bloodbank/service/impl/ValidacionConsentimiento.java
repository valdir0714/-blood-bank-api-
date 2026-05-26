package com.sena.bloodbank.service.impl;

import com.sena.bloodbank.domain.Donante;
import com.sena.bloodbank.exception.ConsentimientoNoFirmadoException;
import com.sena.bloodbank.service.ValidacionDonante;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(4)
public class ValidacionConsentimiento implements ValidacionDonante {

    @Override
    public void validar(Donante donante) {
        if (Boolean.FALSE.equals(donante.getAceptaConsentimiento())
                || donante.getFirmaConsentimiento() == null
                || donante.getFirmaConsentimiento().isBlank()) {
            throw new ConsentimientoNoFirmadoException(donante.getId());
        }
    }
}
