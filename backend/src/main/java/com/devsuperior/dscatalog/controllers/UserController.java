package com.devsuperior.dscatalog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.dscatalog.dto.UserDTO;
import com.devsuperior.dscatalog.dto.UserInsertDTO;
import com.devsuperior.dscatalog.dto.UserUpdateDTO;
import com.devsuperior.dscatalog.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/users")
public class UserController {
	
	@Autowired
	private UserService service;

	@GetMapping
	public ResponseEntity<Page<UserDTO>> findAll(Pageable pageable){
			
		Page<UserDTO> list = service.findAllPaged(pageable);
		return ResponseEntity.ok(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<UserDTO> findById(@PathVariable Long id){
		
			UserDTO userDTO = service.findById(id);
			return ResponseEntity.ok(userDTO);
	}
	
	@PostMapping
	public ResponseEntity<UserDTO> insert(@Valid @RequestBody UserInsertDTO user){
		
		UserDTO userDTO = service.insert(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<UserDTO> update(@PathVariable Long id, @Valid  @RequestBody UserUpdateDTO user){
		
		UserDTO userDTO = service.update(id, user);
		return ResponseEntity.status(HttpStatus.OK).body(userDTO);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<UserDTO> delete(@PathVariable Long id){
		
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
