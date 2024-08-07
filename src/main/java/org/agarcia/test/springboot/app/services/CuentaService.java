package org.agarcia.test.springboot.app.services;

import org.agarcia.test.springboot.app.models.Cuenta;

import java.math.BigDecimal;

public interface CuentaService {
    Cuenta findById(Long id);
    int revisarTotalTransferencia(Long bancoId);
    BigDecimal revisarSaldo(Long cuentaId);
    void tranferir(Long numeroCuentaOrigen, Long numeroCuentaDestino, BigDecimal monto, Long BancoId);
}
