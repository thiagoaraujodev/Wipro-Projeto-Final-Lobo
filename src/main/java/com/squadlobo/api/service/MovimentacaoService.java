package com.squadlobo.api.service;

import com.squadlobo.api.model.*;
import com.squadlobo.api.repository.ContaCorrenteRepository;
import com.squadlobo.api.repository.ContaEspecialRepository;
import com.squadlobo.api.repository.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
public class MovimentacaoService {

    @Autowired
    private ContaEspecialRepository contaEspecialRepository;

    @Autowired
    private ContaCorrenteRepository contaCorrenteRepository;

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;


   public void criarMovimentacao(Conta conta, TipoMovimentacao tipoMovimentacao, Double valor) {
        Movimentacao movimentacao = null;
        if (conta instanceof ContaCorrente) {
            MovimentacaoContaCorrente mcc = new MovimentacaoContaCorrente();
            mcc.setContaCorrente((ContaCorrente) conta);
            movimentacao = mcc;
        } else {
            MovimentacaoContaEspecial mce = new MovimentacaoContaEspecial();
            mce.setContaEspecial((ContaEspecial) conta);
            movimentacao = mce;
        }
        movimentacao.setTipoMovimentacao(tipoMovimentacao);
        movimentacao.setDataHoraMovimentacao(ZonedDateTime.now());
        movimentacao.setValor(valor);
        movimentacaoRepository.save(movimentacao);
    }
}
