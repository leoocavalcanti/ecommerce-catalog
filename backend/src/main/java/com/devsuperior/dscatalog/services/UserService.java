package com.devsuperior.dscatalog.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.dto.RoleDTO;
import com.devsuperior.dscatalog.dto.UserDTO;
import com.devsuperior.dscatalog.dto.UserInsertDTO;
import com.devsuperior.dscatalog.dto.UserUpdateDTO;
import com.devsuperior.dscatalog.entities.Role;
import com.devsuperior.dscatalog.entities.User;
import com.devsuperior.dscatalog.repositories.RoleRepository;
import com.devsuperior.dscatalog.repositories.UserRepository;
import com.devsuperior.dscatalog.services.exceptions.ControllerNotFoundException;
import com.devsuperior.dscatalog.services.exceptions.DatabaseException;

@Service
public class UserService implements UserDetailsService {
	
	private static Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Transactional(readOnly = true)
	public Page<UserDTO> findAllPaged(Pageable pageable){
		
		Page<User> list = repository.findAll(pageable);
		Page<UserDTO> listDTO = list.map((c) -> new UserDTO(c));
		return listDTO;
	}
	
	@Transactional(readOnly = true)
	public UserDTO findById(Long id) {
		
		Optional<User> user = repository.findById(id);
		User entity = user.orElseThrow(() -> new ControllerNotFoundException("Entity not found"));
		UserDTO userDTO = new UserDTO(entity);
		return userDTO;
	}

	@Transactional
	public UserDTO insert(UserInsertDTO user) {
		
		User userSave = new User();
		copyDtoToEntity(user, userSave);
		userSave.setPassword(passwordEncoder.encode(user.getPassword()));
		userSave = repository.save(userSave);
		return new UserDTO(userSave);
		
	}

	@Transactional
	public UserDTO update(Long id, UserUpdateDTO user) {
		
		try {
			
			User entity = repository.getReferenceById(id);
			copyDtoToEntity(user, entity);
			entity = repository.save(entity);
			return new UserDTO(entity);
		}
		
		catch(EntityNotFoundException e) {
			
			throw new ControllerNotFoundException("ID not found: "+ id);
		}
		
	}
	
	private void copyDtoToEntity(UserDTO dto, User entity) {
		
		entity.setEmail(dto.getEmail());
		entity.setFirstName(dto.getFirstName());
		entity.setLastName(dto.getLastName());
		entity.setRoles(entity.getRoles());
		
		entity.getRoles().clear();
		
		for(RoleDTO roleDto: dto.getRoles()) {
			
			Optional<Role> role = roleRepository.findById(roleDto.getId());
			Role roleNew = role.orElseThrow(() -> new ControllerNotFoundException("Not found"));
			entity.getRoles().add(roleNew);
		}
	}

	@Transactional
	public void delete(Long id) {
		
		try {
			
			repository.deleteById(id);
		}
		
		catch(EmptyResultDataAccessException e) {
			
			throw new ControllerNotFoundException("ID not found: "+ id);
		}
		
		catch(DataIntegrityViolationException e) {
			
			throw new DatabaseException("Integrity violation");
		}
				
		
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = repository.findByEmail(username);
		if(user == null) {
			
			logger.error("User not found: " + username);
			throw new UsernameNotFoundException("Email not found");
		}
		logger.info("User found: " + username);
		return user;
	}
}
