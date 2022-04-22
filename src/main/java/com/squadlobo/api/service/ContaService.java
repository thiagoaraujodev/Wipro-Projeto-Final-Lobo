package com.squadlobo.api.service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.squadlobo.api.dto.ContaRequestDTO;
import com.squadlobo.api.dto.MovimentacaoDTO;
import com.squadlobo.api.mapper.ContaMapper;
import com.squadlobo.api.model.Conta;
import com.squadlobo.api.model.ContaCorrente;
import com.squadlobo.api.model.ContaEspecial;
import com.squadlobo.api.model.Movimentacao;
import com.squadlobo.api.model.MovimentacaoContaCorrente;
import com.squadlobo.api.model.MovimentacaoContaEspecial;
import com.squadlobo.api.model.TipoMovimentacao;
import com.squadlobo.api.repository.ClienteRepository;
import com.squadlobo.api.repository.ContaCorrenteRepository;
import com.squadlobo.api.repository.ContaEspecialRepository;
import com.squadlobo.api.repository.ContaRepository;
import com.squadlobo.api.repository.MovimentacaoRepository;
import com.squadlobo.api.service.exceptions.NotFoundException;

@Service
public class ContaService {
	
	Random random = new Random();

    @Value("${wipro.banco.teto.conta.especial}")
    private Double tetoContaEspecial;

    @Autowired
    private ContaRepository contaRepository;
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private ContaCorrenteRepository contaCorrenteRepository;

    @Autowired
    private ContaEspecialRepository contaEspecialRepository;
    
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @Autowired
    private ContaMapper mapper;
    
    
    public List<ContaCorrente> listarContacorrente() {
		return contaCorrenteRepository.findAll();
	}    

    public List<ContaEspecial> listarContaEspecial() {
    	return contaEspecialRepository.findAll();
    }    
    
    public Conta buscarConta(String numeroConta) {
    	Conta conta = null;
    	Optional<ContaCorrente> contaCorrente = contaCorrenteRepository.findById(numeroConta);
    	if (contaCorrente.isPresent()) {
    		conta = contaCorrente.get();
    	} else {
    		Optional<ContaEspecial> contaEspecial = contaEspecialRepository.findById(numeroConta);
    		if (contaEspecial.isPresent()) {
    			conta = contaEspecial.get();
    		} else {
    			throw new IllegalArgumentException("Conta: " + numeroConta + " não encontada!");
    		}
    	}
    	return conta;
    }
    
    public Conta criarConta(ContaRequestDTO contaDTO) {
    	localizaCpf(contaDTO.getCliente().getCpf());
    	
    	Conta novaConta = null;        
    	if (contaDTO.getCliente().getRendaMensal() >= tetoContaEspecial) {
    		ContaEspecial contaEspecial = mapper.toContaEspecial(contaDTO);
    		contaEspecial.setLimiteContaEspecial(gerarLimiteContaEspecial
    				(contaDTO.getCliente().getRendaMensal()));
    		contaEspecial.setLimiteUtilizado(0d);
    		novaConta = contaEspecial;
    		novaConta.setNumeroConta(gerarNumeroConta(contaEspecialRepository));
    	} else {
    		novaConta = mapper.toContaCorrente(contaDTO);
    		novaConta.setNumeroConta(gerarNumeroConta(contaCorrenteRepository));
    	}
    	novaConta.setSaldo(0d);        
    	novaConta.setCartaoCredito(gerarNumeroCartao());
    	novaConta.setLimiteCartaoCredito(gerarLimiteCartao(contaDTO.getCliente().getRendaMensal()));
    	contaRepository.save(novaConta);
    	return novaConta;
    }
    
    public void depositar(String numeroConta, MovimentacaoDTO deposito) {
    	Conta conta = buscarConta(numeroConta);
    	conta.depositar(deposito.getValor());
    	contaRepository.save(conta);
    	criarMovimentacao(conta, TipoMovimentacao.DEPOSITO, deposito.getValor());
    }

    public void sacar(String numeroConta, MovimentacaoDTO saque) {
        Conta conta = buscarConta(numeroConta);
        conta.sacar(saque.getValor());
        contaRepository.save(conta);
        criarMovimentacao(conta, TipoMovimentacao.SAQUE, saque.getValor());
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
    
    public Conta create(ContaRequestDTO contaDTO) {
        localizaCpf(contaDTO.getCliente().getCpf());
        
        Conta novaConta = null;        
        if (contaDTO.getCliente().getRendaMensal() >= tetoContaEspecial) {
            ContaEspecial contaEspecial = mapper.toContaEspecial(contaDTO);
            contaEspecial.setLimiteContaEspecial(gerarLimiteContaEspecial
                    (contaDTO.getCliente().getRendaMensal()));
            contaEspecial.setLimiteUtilizado(0.0);
            novaConta = contaEspecial;
            novaConta.setNumeroConta(gerarNumeroConta(contaEspecialRepository));
        } else {
            novaConta = mapper.toContaCorrente(contaDTO);
            novaConta.setNumeroConta(gerarNumeroConta(contaCorrenteRepository));
        }
        novaConta.setSaldo(0d);        
        novaConta.setCartaoCredito(gerarNumeroCartao());
		novaConta.setLimiteCartaoCredito(gerarLimiteCartao(contaDTO.getCliente().getRendaMensal()));
        contaRepository.save(novaConta);
        return novaConta;
    }

    private void localizaCpf(String cpf) {
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
        Double limite = 0d;
        if (renda >= tetoContaEspecial) {
            limite = renda * 0.1;
        }
        return limite;
    }

    private String gerarNumeroCartao() {		
		String numeracao = "55";
		for (int i = 0; i < 14; i++) {
			numeracao += Integer.toString(random.nextInt(9));
			if(i == 1 || i == 5 || i == 9) {
				numeracao += " ";
			}
		}
		return numeracao;
	}

    public Double gerarLimiteCartao(Double renda) {
		return renda*0.30;		
	}
}
