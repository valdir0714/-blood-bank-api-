package com.sena.bloodbank.service.impl;

import com.sena.bloodbank.domain.Donante;
import com.sena.bloodbank.exception.DonanteNoAptoException;
import com.sena.bloodbank.service.ValidacionDonante;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.Period;

@Component
@Order(3)
public class ValidacionPeriodoDonacion implements ValidacionDonante {

    @Override
    public void validar(Donante donante) {
        if (donante.getFechaUltimaDonacion() != null) {
            long meses = Period.between(donante.getFechaUltimaDonacion(), LocalDate.now()).toTotalMonths();
            if (meses < 3) {
                throw new DonanteNoAptoException(
                        "Han pasado menos de 3 meses desde la ultima donacion (ultima: "
                                + donante.getFechaUltimaDonacion() + ")");
            }
        }
    }
}
