package com.fourTL.controller.admin.product;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fourTL.dao.CategoryDAO;
import com.fourTL.dao.ProductDAO;
import com.fourTL.dao.RoleDAO;
import com.fourTL.entities.Category;
import com.fourTL.entities.Product;

@CrossOrigin("*")
@RestController
@RequestMapping("/admin/api/product")
public class ProductRestController {
	@Autowired
	ProductDAO productsDAO;
	
	@Autowired
	RoleDAO rolesDAO;
	
	@Autowired
	CategoryDAO categoriesDAO;
	
	@GetMapping("")
	public ResponseEntity<List<Product>> getAll() {
		List<Product> products = productsDAO.findAll();
	    Collections.reverse(products);
		return ResponseEntity.ok(productsDAO.findAll());
	}
	
	@GetMapping("/search/{search}")
	public ResponseEntity<List<Product>> search(@PathVariable("search") String search) {
		return ResponseEntity.ok(productsDAO.findByNameContaining(search));
	}
	
	@GetMapping("/categories")
	public ResponseEntity<List<Category>> getAllCategories() {
		return ResponseEntity.ok(categoriesDAO.findAll());
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Product> getOne(@PathVariable("id") Integer id) {
		if(!productsDAO.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(productsDAO.findById(id).get());
	}
	
	@PostMapping("")
	public ResponseEntity<Product> post(@RequestBody Product product) {
		productsDAO.save(product);
		return ResponseEntity.ok(product);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<Product> put(@PathVariable("id") Integer id, @RequestBody Product product) {
		if(!productsDAO.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		productsDAO.save(product);
		return ResponseEntity.ok(product);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
		if(!productsDAO.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		productsDAO.deleteById(id);
		return ResponseEntity.ok().build();
	}
}
