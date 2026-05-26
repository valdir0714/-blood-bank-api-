package com.sena.bloodbank.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

public class CodigoGenerador {

    private CodigoGenerador() {}

    public static String generarCodigoDonacion() {
        String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmm"));
        int aleatorio = ThreadLocalRandom.current().nextInt(1000, 9999);
        return "DON-" + fecha + "-" + aleatorio;
    }
}
