package com.squadlobo.api.service;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.Random;

import com.squadlobo.api.dto.ContaRequestDTO;
import com.squadlobo.api.dto.MovimentacaoDTO;
import com.squadlobo.api.mapper.ContaMapper;
import com.squadlobo.api.model.*;
import com.squadlobo.api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import com.squadlobo.api.service.exceptions.NotFoundException;

@Service
public class ContaService {
	
	Random random = new Random();

    @Value("${wipro.banco.teto.conta.especial}")
    private Double tetoContaEspecial;

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @Autowired
    private ContaEspecialRepository contaEspecialRepository;

    @Autowired
    private ContaCorrenteRepository contaCorrenteRepository;

    @Autowired
    private ContaMapper mapper;

    public void sacar(String numeroConta, MovimentacaoDTO saque) {
        Conta conta = recuperarConta(numeroConta);
        conta.sacar(saque.getValor());
        contaRepository.save(conta);
        criarMovimentacao(conta, TipoMovimentacao.SAQUE, saque.getValor());
    }

    public void depositar(String numeroConta, MovimentacaoDTO deposito) {
        Conta conta = recuperarConta(numeroConta);
        conta.depositar(deposito.getValor());
        contaRepository.save(conta);
        criarMovimentacao(conta, TipoMovimentacao.DEPOSITO, deposito.getValor());
    }

    private void criarMovimentacao(Conta conta, TipoMovimentacao tipoMovimentacao, Double valor) {
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

    @Autowired
    private ClienteRepository clienteRepository;

    public Conta findById(String numeroConta) {
        return contaRepository.findById(numeroConta)
                .orElseThrow(() -> new NotFoundException("Conta: " + numeroConta + " não encontada!"));
    }

    public Conta recuperarConta(String numeroConta) {
        Conta conta = null;
        Optional<ContaCorrente> contaCorrente = contaCorrenteRepository.findById(numeroConta);
        if (contaCorrente.isPresent()) {
            conta = contaCorrente.get();
        } else {
            Optional<ContaEspecial> contaEspecial = contaEspecialRepository.findById(numeroConta);
            if (contaEspecial.isPresent()) {
                conta = contaEspecial.get();
            } else {
                throw new IllegalArgumentException("Conta inexistente");
            }
        }
        return conta;
    }

    public Conta create(ContaRequestDTO conta) {
        Conta contaNova = null;
        validarCpf(conta.getCliente().getCpf());
        
        if (conta.getCliente().getRendaMensal() >= tetoContaEspecial) {
            ContaEspecial contaEspecial = mapper.toContaEspecial(conta);
            contaEspecial.setLimiteUtilizado(0.0);
            contaEspecial.setLimiteContaEspecial(gerarLimiteContaEspecial
                    (conta.getCliente().getRendaMensal()));
            contaNova = contaEspecial;
            contaNova.setNumeroConta(gerarNumeroConta(contaEspecialRepository));
        } else {
            contaNova = mapper.toContaCorrente(conta);
            contaNova.setNumeroConta(gerarNumeroConta(contaCorrenteRepository));
        }
        contaNova.setSaldo(0d);
        contaRepository.save(contaNova);
        return contaNova;
    }

    private void validarCpf(String cpf) {
        if (clienteRepository.countByCpf(cpf) >= 1) {
            throw new NotFoundException("CPF já cadastrado!");
        }
    }
    
    private String gerarNumeroConta(JpaRepository repository) {
        String numeracao = "";
        for (int i = 0; i < 5; i++) {
            numeracao += Integer.toString(random.nextInt(9));
        }
        if (repository.existsById(numeracao)) {
            numeracao = gerarNumeroConta(repository);
        }
        return numeracao;
    }

    public Double gerarLimiteContaEspecial(Double renda) {
        Double limite = 0.0;
        if (renda >= tetoContaEspecial) {
            limite = renda * 0.4;
        }
        return limite;
    }

//    private String gerarNumeroCartao() {
//		
//		String numeracao = "55";
//		for (int i = 0; i < 14; i++) {
//			numeracao += Integer.toString(random.nextInt(9));
//			if(i == 1 || i == 5 || i == 9) {
//				numeracao += " ";
//			}
//		}
//		return numeracao;
//	}

//    public Double gerarLimiteCartao(double renda) {
//		return renda*0.30;		
//	}
}
