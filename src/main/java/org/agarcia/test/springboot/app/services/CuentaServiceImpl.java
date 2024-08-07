package org.agarcia.test.springboot.app.services;

import org.agarcia.test.springboot.app.models.Banco;
import org.agarcia.test.springboot.app.models.Cuenta;
import org.agarcia.test.springboot.app.repositories.BancoRepository;
import org.agarcia.test.springboot.app.repositories.CuentaRepository;

import java.math.BigDecimal;

public class CuentaServiceImpl implements CuentaService {

    private CuentaRepository cuentaRepository;
    private BancoRepository bancoRepository;

    public CuentaServiceImpl(CuentaRepository cuentaRepository, BancoRepository bancoRepository) {
        this.cuentaRepository = cuentaRepository;
        this.bancoRepository = bancoRepository;
    }

    @Override
    public Cuenta findById(Long id) {

        return cuentaRepository.findById(id);
    }

    @Override
    public int revisarTotalTransferencia(Long bancoId) {
        Banco banco = bancoRepository.findById(bancoId);
        return banco.getTotalTransferencias();
    }

    @Override
    public BigDecimal revisarSaldo(Long cuentaId) {
        Cuenta cuenta = cuentaRepository.findById(cuentaId);
        return cuenta.getSaldo();
    }

    @Override
    public void tranferir(Long numeroCuentaOrigen, Long numeroCuentaDestino, BigDecimal monto, Long bancoId) {
        Banco banco = bancoRepository.findById(bancoId);
        int totalTranferencia = banco.getTotalTransferencias();
        banco.setTotalTransferencias(++totalTranferencia);
        bancoRepository.update(banco);

        Cuenta cuentaOrigen = cuentaRepository.findById(numeroCuentaOrigen);
        cuentaOrigen.debito(monto);
        cuentaRepository.update(cuentaOrigen);

        Cuenta cuentaDestino = cuentaRepository.findById(numeroCuentaDestino);
        cuentaDestino.credito(monto);
        cuentaRepository.update(cuentaDestino);
    }
}
