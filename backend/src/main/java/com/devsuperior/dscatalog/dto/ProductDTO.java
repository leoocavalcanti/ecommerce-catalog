package com.devsuperior.dscatalog.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.entities.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	@Size(min = 5, max = 60, message = "Deve ter entre 5 e 60 caracteres")
	@NotBlank(message = "Campo obrigatório")
	private String name;
	@NotBlank(message = "Campo obrigatório")
	private String description;
	@Positive(message = "O preço não pode ser negativo")
	private Double price;
	private String imgUrl;
	@PastOrPresent
	private Instant date;
	private List<CategoryDTO> categories = new ArrayList<>();
	
	
	public ProductDTO(Product product) {
		
		this.id = product.getId();
		this.name = product.getName();
		this.description = product.getDescription();
		this.price = product.getPrice();
		this.imgUrl = product.getImgUrl();
		this.date = product.getDate();
	}
	
	public ProductDTO(Product entity, Set<Category> categories) {
		
		this(entity);
		categories.forEach((c) -> this.categories.add(new CategoryDTO(c)));
	}
}
