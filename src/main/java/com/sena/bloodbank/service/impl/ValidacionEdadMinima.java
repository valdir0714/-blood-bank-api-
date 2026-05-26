package com.sena.bloodbank.service.impl;

import com.sena.bloodbank.domain.Donante;
import com.sena.bloodbank.exception.DonanteNoAptoException;
import com.sena.bloodbank.service.ValidacionDonante;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.Period;

@Component
@Order(1)
public class ValidacionEdadMinima implements ValidacionDonante {

    @Override
    public void validar(Donante donante) {
        int edad = Period.between(donante.getFechaNacimiento(), LocalDate.now()).getYears();
        if (edad < 18) {
            throw new DonanteNoAptoException("El donante es menor de edad (edad: " + edad + " anos)");
        }
    }
}
