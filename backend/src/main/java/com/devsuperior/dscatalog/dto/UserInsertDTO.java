package com.devsuperior.dscatalog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInsertDTO extends UserDTO {


	private static final long serialVersionUID = 1L;
	
	private String password;
	
	

}
