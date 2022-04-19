package com.squadlobo.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.squadlobo.api.model.Cliente;
import com.squadlobo.api.repository.ClienteRepository;
import com.squadlobo.api.service.exceptions.NotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}

	public Cliente findById(String cpf) {
		return clienteRepository.findById(cpf)
				.orElseThrow(() -> new NotFoundException("CPF: " + cpf + " não encontado!"));
	}

	public Cliente create(Cliente obj) {		
		return clienteRepository.save(obj);		
	}
	public Cliente update(String cpf, Cliente obj) {
		Cliente cliente = findById(cpf);
		cliente.setNome(obj.getNome());
		cliente.setTelefone(obj.getTelefone());
		cliente.setDataNascimento(obj.getDataNascimento());
		
			return clienteRepository.save(cliente);
		}
	
	public void deletar(String cpf) {
		findById(cpf);
		clienteRepository.deleteById(cpf);
	}

}
