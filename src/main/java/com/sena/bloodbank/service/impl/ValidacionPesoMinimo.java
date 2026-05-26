package com.sena.bloodbank.service.impl;

import com.sena.bloodbank.domain.Donante;
import com.sena.bloodbank.exception.DonanteNoAptoException;
import com.sena.bloodbank.service.ValidacionDonante;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class ValidacionPesoMinimo implements ValidacionDonante {

    @Override
    public void validar(Donante donante) {
        if (donante.getPeso() < 50.0) {
            throw new DonanteNoAptoException(
                    "El donante pesa menos de 50 kg (peso: " + donante.getPeso() + " kg)");
        }
    }
}
