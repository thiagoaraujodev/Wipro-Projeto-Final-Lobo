package com.squadlobo.api.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.squadlobo.api.model.Conta;
import com.squadlobo.api.service.ContaService;

@RestController
@RequestMapping("/conta")
@CrossOrigin("*")
public class ContaController {

	@Autowired
	private ContaService contaService;

	@GetMapping
	public ResponseEntity<List<Conta>> GetAll() {
		List<Conta> list = contaService.findAll();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping("/{numeroConta}")
	public ResponseEntity<Conta> GetById(@PathVariable @Valid String numeroConta) {
		Conta obj = contaService.findById(numeroConta);
		return ResponseEntity.ok().body(obj);
	}

	@PostMapping("/criar")
	public ResponseEntity<String> Post(@RequestBody @Valid Conta conta) {
//		return ResponseEntity.status(HttpStatus.CREATED).body(contaService.create(conta));

		Conta newObj = contaService.create(conta);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{numeroConta}")
				.buildAndExpand(newObj.getNumeroConta()).toUri();
		return ResponseEntity.created(uri).build();
	}

	

}
