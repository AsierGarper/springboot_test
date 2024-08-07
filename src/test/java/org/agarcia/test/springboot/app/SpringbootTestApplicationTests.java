package org.agarcia.test.springboot.app;

import static org.agarcia.test.springboot.app.Datos.*;
import org.agarcia.test.springboot.app.exceptions.DineroInsuficienteExceptions;
import org.agarcia.test.springboot.app.models.Banco;
import org.agarcia.test.springboot.app.models.Cuenta;
import org.agarcia.test.springboot.app.repositories.BancoRepository;
import org.agarcia.test.springboot.app.repositories.CuentaRepository;
import org.agarcia.test.springboot.app.services.CuentaService;
import org.agarcia.test.springboot.app.services.CuentaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;

@SpringBootTest
class SpringbootTestApplicationTests {

	@MockBean
	CuentaRepository cuentaRepository;
	@MockBean
	BancoRepository bancoRepository;
	@Autowired
	CuentaService service;

	@BeforeEach
	void setUp(){
//		cuentaRepository = mock(CuentaRepository.class);
//		bancoRepository = mock(BancoRepository.class);
//		service = new CuentaServiceImpl(cuentaRepository, bancoRepository);
//		Datos.CUENTA_001.setSaldo(new BigDecimal("1000"));
//		Datos.CUENTA_002.setSaldo(new BigDecimal("800"));
//		Datos.BANCO.setTotalTransferencias(0);
	}

	@Test
	void contextLoads() {
		when(cuentaRepository.findById(1L)).thenReturn(crearCuenta001());
		when(cuentaRepository.findById(2L)).thenReturn(crearCuenta002());
		when(bancoRepository.findById(1L)).thenReturn(crearBanco());

		BigDecimal saldoOrigen = service.revisarSaldo(1L);
		BigDecimal saldoDestino = service.revisarSaldo(2L);
		assertEquals("1000", saldoOrigen.toPlainString());
		assertEquals("800", saldoDestino.toPlainString());

		service.tranferir(1L, 2L, new BigDecimal(300), 1L);

		saldoOrigen = service.revisarSaldo(1L);
		saldoDestino = service.revisarSaldo(2L);
		assertEquals("700", saldoOrigen.toPlainString());
		assertEquals("1100", saldoDestino.toPlainString());

		int totalTranferencia = service.revisarTotalTransferencia(1L);
		assertEquals(1, totalTranferencia);

		verify(cuentaRepository, times(3)).findById(1L);
		verify(cuentaRepository, times(2)).update(any(Cuenta.class));

		verify(bancoRepository, times(2)).findById(1L);
		verify(bancoRepository).update(any(Banco.class));

		verify(cuentaRepository, times(6)).findById(anyLong());
		verify(cuentaRepository, never()).findAll();

	}

	@Test
	void contextLoads2() {
		when(cuentaRepository.findById(1L)).thenReturn(crearCuenta001());
		when(cuentaRepository.findById(2L)).thenReturn(crearCuenta002());
		when(bancoRepository.findById(1L)).thenReturn(crearBanco());

		BigDecimal saldoOrigen = service.revisarSaldo(1L);
		BigDecimal saldoDestino = service.revisarSaldo(2L);
		assertEquals("1000", saldoOrigen.toPlainString());
		assertEquals("800", saldoDestino.toPlainString());

		assertThrows(DineroInsuficienteExceptions.class, () -> {
			service.tranferir(1L, 2L, new BigDecimal(1800), 1L);
		});


		saldoOrigen = service.revisarSaldo(1L);
		saldoDestino = service.revisarSaldo(2L);
		assertEquals("1000", saldoOrigen.toPlainString());
		assertEquals("800", saldoDestino.toPlainString());

		int totalTranferencia = service.revisarTotalTransferencia(1L);
		assertEquals(0, totalTranferencia);

		verify(cuentaRepository, times(3)).findById(1L);
		verify(cuentaRepository, times(2)).findById(2L);
		verify(cuentaRepository, never()).update(any(Cuenta.class));

		verify(bancoRepository, times(1)).findById(1L);
		verify(bancoRepository, never()).update(any(Banco.class));
		verify(cuentaRepository, times(5)).findById(anyLong());
		verify(cuentaRepository, never()).findAll();

	}
	@Test
	void contexLoads3(){
		when(cuentaRepository.findById(1L)).thenReturn(crearCuenta001());

		Cuenta cuenta1 = service.findById(1L);
		Cuenta cuenta2 = service.findById(1L);

		assertSame(cuenta1, cuenta2);
		assertTrue(cuenta1 == cuenta2);
		assertEquals("Asier", cuenta1.getPersona());
		assertEquals("Asier", cuenta2.getPersona());

		verify(cuentaRepository, times(2)).findById(1L);
	}



}
