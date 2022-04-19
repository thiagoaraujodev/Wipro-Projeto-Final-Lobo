package com.squadlobo.api.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.squadlobo.api.model.Cliente;
import com.squadlobo.api.repository.ClienteRepository;
import com.squadlobo.api.service.ClienteService;

@RestController
@RequestMapping("/cliente")
@CrossOrigin("*")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@GetMapping
	public ResponseEntity<List<Cliente>> GetAll() {
		List<Cliente> list = clienteService.findAll();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping("/{cpf}")
	public ResponseEntity<Cliente> GetById(@PathVariable @Valid String cpf) {
		Cliente obj = clienteService.findById(cpf);
		return ResponseEntity.ok().body(obj);
	}

	@PostMapping("/{criar}")
	public ResponseEntity<String> Post(@RequestBody @Valid Cliente cliente) {
//		return ResponseEntity.status(HttpStatus.CREATED).body(service.create(cliente));

		Cliente newObj = clienteService.create(cliente);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{cpf}").buildAndExpand(newObj.getCpf())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}

	@DeleteMapping("/{cpf}")
	public ResponseEntity<Void> Delete(@PathVariable String cpf) {
		clienteService.deletar(cpf);
		return ResponseEntity.noContent().build();
	}

	
	
	@PutMapping("/{cpf}")
	public ResponseEntity<Cliente> Put(@PathVariable String cpf, @RequestBody Cliente obj) {
		Cliente obj1 = clienteService.update(cpf, obj);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(obj1);
	}
}
