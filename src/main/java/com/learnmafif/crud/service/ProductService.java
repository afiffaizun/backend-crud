package com.learnmafif.crud.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.learnmafif.crud.model.Product;
import com.learnmafif.crud.repository.ProductRepository;

@Service
public class ProductService {
	private final ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	//get All Product
	public List<Product> getAllProduct() {
		return productRepository.findAll();
	}
	
	//getProduct by id
	public Optional<Product> getProductById(Long id) {
		return productRepository.findById(id);
	}
	
	//getProduct by category
	public List<Product> getProductByCategory(String categoryName) {
		return productRepository.findProductByCategory(categoryName);
	}
	
	public List<Product> getProductsByPriceLessThanEqual(Double price) {
		return productRepository.findProductsByPriceLessThanEqual(price);
	}
	
	public List<Product> getProductsByProductName(String name) {
		return productRepository.findProductsByNameContainingIgnoreCase(name);
	}
	
	public Product saveProduct(Product product) {
		return productRepository.save(product);
	}
	
	public void deleteProduct(Product product) {
		productRepository.delete(product);
	}
}
