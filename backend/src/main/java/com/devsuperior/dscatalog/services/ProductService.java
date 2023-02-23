package com.devsuperior.dscatalog.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.services.exceptions.ControllerNotFoundException;
import com.devsuperior.dscatalog.services.exceptions.DatabaseException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Transactional(readOnly = true)
	public Page<ProductDTO> findAllPaged(PageRequest pageRequest){
		
		Page<Product> list = repository.findAll(pageRequest);
		Page<ProductDTO> listDTO = list.map((c) -> new ProductDTO(c));
		return listDTO;
	}
	
	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		
		Optional<Product> product = repository.findById(id);
		Product entity = product.orElseThrow(() -> new ControllerNotFoundException("Entity not found"));
		ProductDTO productDTO = new ProductDTO(entity, entity.getCategories());
		return productDTO;
	}

	@Transactional
	public ProductDTO insert(ProductDTO product) {
		
		Product productSave = new Product();
		productSave.setName(product.getName());
		productSave = repository.save(productSave);
		return new ProductDTO(productSave);
		
	}

	@Transactional
	public ProductDTO update(Long id, ProductDTO product) {
		
		try {
			
			Product entity = repository.getReferenceById(id);
			entity.setName(product.getName());
			copyDtoToEntity(product, entity);
			entity = repository.save(entity);
			return new ProductDTO(entity);
		}
		
		catch(EntityNotFoundException e) {
			
			throw new ControllerNotFoundException("ID not found: "+ id);
		}
		
	}
	
	private void copyDtoToEntity(ProductDTO dto, Product entity) {
		
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setDate(dto.getDate());
		entity.setImgUrl(dto.getImgUrl());
		entity.setPrice(entity.getPrice());
		
		entity.getCategories().clear();
		
		for(CategoryDTO catDto: dto.getCategories()) {
			
			Optional<Category> category = categoryRepository.findById(catDto.getId());
			Category categoryN = category.orElseThrow(() -> new ControllerNotFoundException("Not found"));
			entity.getCategories().add(categoryN);
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
}
