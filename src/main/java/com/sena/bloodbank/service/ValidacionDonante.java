package com.sena.bloodbank.service;

import com.sena.bloodbank.domain.Donante;

@FunctionalInterface
public interface ValidacionDonante {
    void validar(Donante donante);
}
