package com.learnmafif.crud.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.learnmafif.crud.model.Product;
import com.learnmafif.crud.service.ProductService;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
	
	private final ProductService productService;
	
	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping
	public ResponseEntity<List<Product>> getAllProducts() {
		return ResponseEntity.ok(productService.getAllProduct());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable Long id) {
		Product product = productService.getProductById(id)
				.orElseThrow(
						() -> new RuntimeException("Product Not Found")
				);
		return ResponseEntity.ok(product);
	}
	
	@GetMapping("/category/{categoryName}")
	public ResponseEntity<List<Product>> getProductsByCategory(
			@PathVariable String categoryName
	) {
		return ResponseEntity.ok(productService.getProductByCategory(categoryName));
	}
	
	@GetMapping("/price/{maxPrice}")
	public ResponseEntity<List<Product>> getProductByMaxPrice(
			@PathVariable Double maxPrice) {
		return ResponseEntity.ok(
				productService.getProductsByPriceLessThanEqual(maxPrice)
		);
	}
	
	@GetMapping("/search")
	public ResponseEntity<List<Product>> searchProduct(@RequestParam String name) {
		return ResponseEntity.ok(
				productService.getProductsByProductName(name)
		);
	}
	
	@PostMapping
	public ResponseEntity<Product> createProduct(@RequestBody Product product) {
		Product newProduct = productService.saveProduct(product);
		
		return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
	}
	
	
	//Update
	@PutMapping("/{id}")
	public ResponseEntity<Product> updateProduct(
			@PathVariable Long id,
			@RequestBody Product product
			
	) {
		Product productToUpdate = productService.getProductById(id)
				.orElseThrow(
						() -> new RuntimeException("Product not found")
				);
		
		productToUpdate.setName(product.getName());
		productToUpdate.setDescription(product.getDescription());
		productToUpdate.setPrice(product.getPrice());
		productToUpdate.setCategory(product.getCategory());
		
		Product updatedProduct = productService.saveProduct(productToUpdate);
		return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
	}
	
	//Delete
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
		Product productToDelete = productService.getProductById(id)
				.orElseThrow(
						() -> new RuntimeException("Product not found")
				);
		productService.deleteProduct(productToDelete);
		
		return ResponseEntity.ok("Product Deleted");
	}
	
}
