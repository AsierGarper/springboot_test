package org.agarcia.test.springboot.app;

import org.agarcia.test.springboot.app.models.Banco;
import org.agarcia.test.springboot.app.models.Cuenta;

import java.math.BigDecimal;
import java.util.Optional;

public class Datos {

//    public static final Cuenta CUENTA_001 = new Cuenta(1L, "Asier", new BigDecimal(1000));
//    public static final Cuenta CUENTA_002 = new Cuenta(2L, "Maria", new BigDecimal(800));
//    public static final Banco BANCO = new Banco(3L, "El Banco financiero", 0);

    public static Optional<Cuenta> crearCuenta001(){
        return Optional.of(new Cuenta(1L, "Asier", new BigDecimal(1000)));
    }

    public static Optional<Cuenta> crearCuenta002(){
        return Optional.of(new Cuenta(2L, "Maria", new BigDecimal(800)));
    }

    public static Optional<Banco> crearBanco(){
       return Optional.of(new Banco(3L, "El Banco financiero", 0));
    }
}
