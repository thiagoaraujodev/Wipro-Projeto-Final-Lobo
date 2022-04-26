package com.squadlobo.api.config;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.squadlobo.api.model.Cliente;
import com.squadlobo.api.model.ContaCorrente;
import com.squadlobo.api.model.ContaEspecial;
import com.squadlobo.api.model.TipoConta;
import com.squadlobo.api.repository.ContaRepository;

@Configuration
@Profile("test")
public class TestConfig {

	@Autowired
	private ContaRepository contaRepository;

	@Bean
	public void iniciandoDB() {
//		Cliente c1 = new Cliente("Bryan Isaac Pedro Henrique Baptista", "90584196229", LocalDate.parse("2001-04-23"),
//				"48982874478", 1000.0);
//		Cliente c2 = new Cliente("Elias Henry Leonardo da Silva", "44286938344", LocalDate.parse("1958-04-12"),
//				"96997871720", 2000.0);
//
//		ContaCorrente contaCorrente = new ContaCorrente();
//		contaCorrente.setNumeroConta("00001");
//		contaCorrente.setTipoConta(TipoConta.CORRENTE);
//		contaCorrente.setSaldo(1000d);
//		contaCorrente.setCartaoCredito("5555 4444 3333 2222");
//		contaCorrente.setLimiteCartaoCredito(500d);
//		contaCorrente.setCliente(c1);
//
//		ContaEspecial contaEspecial = new ContaEspecial();
//		contaEspecial.setNumeroConta("00002");
//		contaEspecial.setTipoConta(TipoConta.ESPECIAL);
//		contaEspecial.setSaldo(1000d);
//		contaEspecial.setCartaoCredito("5555 6666 7777 8888");
//		contaEspecial.setLimiteCartaoCredito(500d);
//		contaEspecial.setCliente(c2);
//		contaEspecial.setLimiteContaEspecial(200d);
//		contaEspecial.setLimiteUtilizado(0d);
//
//		contaRepository.saveAll(List.of(contaCorrente, contaEspecial));

	}

}
