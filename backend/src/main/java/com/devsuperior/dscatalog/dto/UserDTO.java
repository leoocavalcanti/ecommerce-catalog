package com.devsuperior.dscatalog.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.devsuperior.dscatalog.entities.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@NotBlank(message = "Campo obrigatório")
	private String firstName;
	
	private String lastName;
	
	@Email(message = "Por favor, insira um email válido")
	private String email;
	
	Set<RoleDTO> roles = new HashSet<>();
	
	public UserDTO(User entity) {
		
		this.id = entity.getId();
		this.firstName = entity.getFirstName();
		this.lastName = entity.getLastName();
		this.email = entity.getEmail();
		entity.getRoles().forEach((r) -> this.roles.add(new RoleDTO(r)));
	}
	
	
}