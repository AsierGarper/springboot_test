package org.agarcia.test.springboot.app;

import org.agarcia.test.springboot.app.models.Cuenta;
import org.agarcia.test.springboot.app.repositories.CuentaRepository;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@DataJpaTest
public class IntegracionJpaTest {
    @Autowired
    CuentaRepository cuentaRepository;

    @Test
    void testFindById(){
        Optional<Cuenta> cuenta = cuentaRepository.findById(1L);
        assertTrue(cuenta.isPresent());
        assertEquals("Asier", cuenta.orElseThrow().getPersona());
    }

    @Test
    void findByPersona(){
        Optional<Cuenta> cuenta = cuentaRepository.findByPersona("Asier");
        assertTrue(cuenta.isPresent());
        assertEquals("Asier", cuenta.orElseThrow().getPersona());
        assertEquals("1000.00", cuenta.orElseThrow().getSaldo().toPlainString());
    }

    @Test
    void findByPersonaThrowException(){
        Optional<Cuenta> cuenta = cuentaRepository.findByPersona("Arroz");
        assertThrows(NoSuchElementException.class, cuenta::orElseThrow);
//        assertThrows(NoSuchElementException.class, ()-> {
//            cuenta.orElseThrow();
//        });
        assertFalse(cuenta.isPresent());
    }

    @Test
    void testFindAll(){
        List<Cuenta> cuentas = cuentaRepository.findAll();
        assertFalse(cuentas.isEmpty());
        assertEquals(2, cuentas.size());
    }

    @Test
    void testSave(){
        Cuenta cuentaNueva = new Cuenta(3L, "Xabi", new BigDecimal(3000));
        cuentaRepository.save(cuentaNueva);
        List<Cuenta> cuentas = cuentaRepository.findAll();
        assertEquals(3, cuentas.size());
        assertEquals("Xabi", cuentaRepository.findById(3L).orElseThrow().getPersona());
    }

    @Test
    void testUpdate(){
        Cuenta cuentaPepe = new Cuenta(null, "Pepe", new BigDecimal("3000"));

        //When
        Cuenta cuenta = cuentaRepository.save(cuentaPepe);

        //Then
        assertEquals("Pepe", cuenta.getPersona());
        assertEquals("3000", cuenta.getSaldo().toPlainString());

        //When
        cuenta.setSaldo(new BigDecimal("3800"));
        Cuenta cuentaActualizada = cuentaRepository.save(cuenta);

        //Then
        assertEquals("Pepe", cuentaActualizada.getPersona());
        assertEquals("3800", cuentaActualizada.getSaldo().toPlainString());
    }

    @Test
    void testDelete() {
        Cuenta cuentaAEliminar = cuentaRepository.findById(2L).orElseThrow();
        assertEquals("Maria", cuentaAEliminar.getPersona());

        cuentaRepository.delete(cuentaAEliminar);
        assertThrows(NoSuchElementException.class, () -> {
            cuentaRepository.findById(2L).orElseThrow();
        });

        assertThrows(NoSuchElementException.class, () -> {
            cuentaRepository.findByPersona("Maria").orElseThrow();
        });

    }
}
