package com.fourTL.controller.admin.product;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fourTL.DTO.ProductDTO;
import com.fourTL.DTO.impl.ProductDTOImpl;
import com.fourTL.dao.CategoryDAO;
import com.fourTL.dao.ProductDAO;
import com.fourTL.dao.RoleDAO;
import com.fourTL.entities.Feedback;
import com.fourTL.entities.Product;
import com.fourTL.service.FeedBackService;
import com.fourTL.service.ProductService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/product")
public class ProductRestController {
	@Autowired
	ProductDAO productsDAO;

	@Autowired
	RoleDAO rolesDAO;

	@Autowired
	CategoryDAO categoriesDAO;

	@Autowired
	ProductService productService;

	@Autowired
	FeedBackService feedBackService;

	@GetMapping("getProductDTO/{productId}")
	public ResponseEntity<ProductDTO> getProductDTOById(@PathVariable("productId") Integer productId) {
		return ResponseEntity.ok(convertProductToProductDTO(productService.findById(productId)));
	}

	@GetMapping("getAllProductDTO")
	public ResponseEntity<List<ProductDTO>> getAllProductDTO() {
		List<ProductDTO> listProductFindAllDTO = new ArrayList<>();
		List<Product> listProductFindAll = productService.findAll();
		for (Product product : listProductFindAll) {
			if (product.getAvailable()) {
				listProductFindAllDTO.add(convertProductToProductDTO(product));
			}
		}
		return ResponseEntity.ok(listProductFindAllDTO);
	}

	@GetMapping("getAllProductDTO/{type}")
	public ResponseEntity<List<ProductDTO>> getAllProductDTOByType(@PathVariable("type") String type) {
		List<ProductDTO> listProductFindAllDTO = new ArrayList<>();
		List<Product> listProductFindAll = productService.findAll();
		for (Product product : listProductFindAll) {
			if (product.getType().equalsIgnoreCase(type)) {
				if (product.getAvailable()) {
					listProductFindAllDTO.add(convertProductToProductDTO(product));
				}
			}
		}
		return ResponseEntity.ok(listProductFindAllDTO);
	}

	@GetMapping("getAllProductDTO/{type}/{category}")
	public ResponseEntity<List<ProductDTO>> getAllProductDTOByTypeAndCategory(@PathVariable("type") String type,
			@PathVariable("category") String category) {
		List<ProductDTO> listProductFindAllDTO = new ArrayList<>();
		List<Product> listProductFindAll = productService.findAll();
		for (Product product : listProductFindAll) {
			if (product.getType().equalsIgnoreCase(type)) {
				if (product.getCategory().getCategoryId().equalsIgnoreCase(category)) {
					if (product.getAvailable()) {
						listProductFindAllDTO.add(convertProductToProductDTO(product));
					}
				}

			}
		}
		return ResponseEntity.ok(listProductFindAllDTO);
	}

	private ProductDTO convertProductToProductDTO(Product product) {
		if (product != null) {
			if (product.getAvailable()) {
				List<Feedback> listFeedBackByProduct = feedBackService.findByProductId(product.getId());
				Double sum = 0.0;
				Double avgStar = 0.0;
				Integer countFeedBack = 0;

				if (!listFeedBackByProduct.isEmpty()) {
					for (Feedback f : listFeedBackByProduct) {
						sum += f.getStar();
					}
					avgStar = sum / listFeedBackByProduct.size();
					countFeedBack = listFeedBackByProduct.size();
				}

				ProductDTO productDTO = new ProductDTOImpl(product.getId(), product.getName(), product.getPoster(),
						product.getThumbnail(), product.getSalePrice(), product.getOffer(), product.getDetails(),
						avgStar, countFeedBack, product.getCategory().getName(), product.getCategory().getCategoryId(),
						product.getType(), product.getCreateDate());
				return productDTO;
			}
		}
		return null;
	}

	@GetMapping("/{productId}")
	public ResponseEntity<Product> getProductById(@PathVariable("productId") Integer productId) {
		return ResponseEntity.ok(productService.findById(productId));
	}

//	@GetMapping("")
//	public ResponseEntity<List<Product>> getAll() {
//		List<Product> products = productsDAO.findAll();
//	    Collections.reverse(products);
//		return ResponseEntity.ok(productsDAO.findAll());
//	}
//	
//	@GetMapping("/search/{search}")
//	public ResponseEntity<List<Product>> search(@PathVariable("search") String search) {
//		return ResponseEntity.ok(productsDAO.findByNameContaining(search));
//	}
//	
//	@GetMapping("/categories")
//	public ResponseEntity<List<Category>> getAllCategories() {
//		return ResponseEntity.ok(categoriesDAO.findAll());
//	}
//	
//	@GetMapping("{id}")
//	public ResponseEntity<Product> getOne(@PathVariable("id") Integer id) {
//		if(!productsDAO.existsById(id)) {
//			return ResponseEntity.notFound().build();
//		}
//		return ResponseEntity.ok(productsDAO.findById(id).get());
//	}
//	
//	@PostMapping("")
//	public ResponseEntity<Product> post(@RequestBody Product product) {
//		productsDAO.save(product);
//		return ResponseEntity.ok(product);
//	}
//	
//	@PutMapping("{id}")
//	public ResponseEntity<Product> put(@PathVariable("id") Integer id, @RequestBody Product product) {
//		if(!productsDAO.existsById(id)) {
//			return ResponseEntity.notFound().build();
//		}
//		productsDAO.save(product);
//		return ResponseEntity.ok(product);
//	}
//	
//	@DeleteMapping("{id}")
//	public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
//		if(!productsDAO.existsById(id)) {
//			return ResponseEntity.notFound().build();
//		}
//		productsDAO.deleteById(id);
//		return ResponseEntity.ok().build();
//	}
}
