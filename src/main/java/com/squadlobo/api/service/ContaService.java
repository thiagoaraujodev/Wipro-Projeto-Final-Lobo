package com.squadlobo.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.squadlobo.api.model.Conta;
import com.squadlobo.api.repository.ContaRepository;
import com.squadlobo.api.service.exceptions.NotFoundException;

@Service
public class ContaService {
	
	@Autowired
	private ContaRepository contaRepository;

	public List<Conta> findAll() {
		return contaRepository.findAll();
	}

	public Conta findById(String numeroConta) {
		return contaRepository.findById(numeroConta)
				.orElseThrow(() -> new NotFoundException("Conta: " + numeroConta + " não encontada!"));
	}

	public Conta create(Conta obj) {	
		
		if (contaRepository.findById(obj.getNumeroConta()) == null) {
			return contaRepository.save(obj);
		}
		return contaRepository.save(obj);
	}

}
