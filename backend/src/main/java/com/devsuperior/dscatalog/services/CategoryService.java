package com.devsuperior.dscatalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.services.exceptions.ControllerNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repository;
	
	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll(){
		
		List<Category> list = repository.findAll();
		List<CategoryDTO> listDTO = list.stream().map((c) -> new CategoryDTO(c)).collect(Collectors.toList());
		return listDTO;
	}
	
	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
		
		Optional<Category> category = repository.findById(id);
		Category entity = category.orElseThrow(() -> new ControllerNotFoundException("Entity not found"));
		CategoryDTO categoryDTO = new CategoryDTO(entity);
		return categoryDTO;
	}

	@Transactional
	public CategoryDTO insert(CategoryDTO category) {
		
		Category categorySave = new Category();
		categorySave.setName(category.getName());
		categorySave = repository.save(categorySave);
		return new CategoryDTO(categorySave);
		
	}

	@Transactional
	public CategoryDTO update(Long id, CategoryDTO category) {
		
		try {
			
			Category entity = repository.getReferenceById(id);
			entity.setName(category.getName());
			entity = repository.save(entity);
			return new CategoryDTO(entity);
		}
		
		catch(EntityNotFoundException e) {
			
			throw new ControllerNotFoundException("ID not found: "+ id);
		}
		
	}
}
