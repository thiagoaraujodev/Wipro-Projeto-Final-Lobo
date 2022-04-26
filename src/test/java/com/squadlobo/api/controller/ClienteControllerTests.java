package com.squadlobo.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.squadlobo.api.dto.ClienteDTO;
import com.squadlobo.api.model.Cliente;
import com.squadlobo.api.service.ClienteService;

@SpringBootTest
public class ClienteControllerTests {
	
	private static final LocalDate DATA_NASCIMENTO = LocalDate.parse("2001-04-23");

	private static final double RENDA_CC = 1000.0;

	private static final String TELEFONE = "48982874478";

	private static final String NOME = "Bryan Isaac";

	private static final String CPF = "90584196229";

	private static final int INDEX = 0;
	

	@InjectMocks
	private ClienteController resource;

	@Mock
	private ClienteService service;
	
	private Cliente cliente;
	private ClienteDTO clienteDTO;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		startUser();
	}

	@Test
	void quandoListarClientesRetorneUmaListaDeClientes() {		
		when(service.listarClientes()).thenReturn(List.of(cliente));

		ResponseEntity<List<Cliente>> response = resource.listarClientes();
		
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(List.of(cliente).getClass(), response.getBody().getClass());
		assertEquals(Cliente.class, response.getBody().get(INDEX).getClass());
		
		assertEquals(CPF, response.getBody().get(INDEX).getCpf());
		assertEquals(NOME, response.getBody().get(INDEX).getNome());
		assertEquals(DATA_NASCIMENTO, response.getBody().get(INDEX).getDataNascimento());
		assertEquals(TELEFONE, response.getBody().get(INDEX).getTelefone());
		assertEquals(RENDA_CC, response.getBody().get(INDEX).getRendaMensal());		
	}
	
	@Test
	void quandoBuscarCpfRetorneSucesso() {
		when(service.buscarCpf(anyString())).thenReturn(cliente);
		
		ResponseEntity<Cliente> response = resource.buscarCpf(CPF);
		
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(Cliente.class, response.getBody().getClass());
		
		assertEquals(CPF, response.getBody().getCpf());
		assertEquals(NOME, response.getBody().getNome());
		assertEquals(DATA_NASCIMENTO, response.getBody().getDataNascimento());
		assertEquals(TELEFONE, response.getBody().getTelefone());
		assertEquals(RENDA_CC, response.getBody().getRendaMensal());		
	}
	
	private void startUser() {
		cliente = new Cliente(NOME, CPF, DATA_NASCIMENTO, TELEFONE, RENDA_CC);
		clienteDTO = new ClienteDTO(cliente);
	}
}
