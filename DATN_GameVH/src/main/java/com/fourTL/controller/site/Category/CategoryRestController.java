package com.fourTL.controller.site.Category;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fourTL.DTO.ProductDTO;
import com.fourTL.DTO.impl.ProductDTOImpl;
import com.fourTL.dao.CategoryDAO;
import com.fourTL.dao.ProductDAO;
import com.fourTL.entities.Category;
import com.fourTL.entities.CategoryDTO;
import com.fourTL.entities.Product;

@CrossOrigin("*")
@RestController
@RequestMapping("category")
public class CategoryRestController {

	@Autowired
	CategoryDAO cDAO;

	@Autowired
	ProductDAO pDAO;

	@GetMapping("/countProduct")
	private ResponseEntity<HashMap<String, CategoryDTO>> getByCateGory() {
		HashMap<String, CategoryDTO> listCateAndCountProduct = new HashMap<>();
		for (Category categories : cDAO.findAll()) {
			CategoryDTO cDTO = new CategoryDTO();
			cDTO.setIdCategory(categories.getId().trim());
			cDTO.setNameCategory(categories.getName());
			cDTO.setQtyProduct((int) pDAO.countByCategoryId(categories.getId()));

			listCateAndCountProduct.put(categories.getId(), cDTO);
		}
		return ResponseEntity.ok(listCateAndCountProduct);
	}
	
	@RequestMapping("/getAll")
	public ResponseEntity<Page<ProductDTO>> getAllProducts(
			@RequestParam("page") Optional<Integer> page, 
			@RequestParam("size") Optional<Integer> size) {
		Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(9));
		List<ProductDTO> productsDisplay = pDAO.findProductFeedBack(); // Danh sách productDTO dạng List
		List<Product> productsFindAll = pDAO.findAll();
		// Check để thêm những sản phẩm chưa có feedback vào list
		addMissingAccessories(productsFindAll, productsDisplay);
		// Gán lại List đã check vào Danh sách productDTO dạng pageable 
		int startIndex = (int) pageable.getOffset();
		int endIndex = Math.min((startIndex + pageable.getPageSize()), productsDisplay.size());
		List<ProductDTO> pageContent = productsDisplay.subList(startIndex, endIndex);
		Page<ProductDTO> productsDisplayPageable = new PageImpl<>(pageContent, pageable, productsDisplay.size());
	    return ResponseEntity.ok(productsDisplayPageable);
	}
	
	public void addMissingAccessories(List<Product> productsFindAll, List<ProductDTO> productsDisplay) {
		for (Product product : productsFindAll) {
			if (!isAccessoryInList(productsDisplay, product.getId())) {
				ProductDTO productDTO = new ProductDTOImpl(product.getId(), product.getName(),
						product.getPoster(), product.getThumbnail(), product.getSalePrice(), product.getOffer(),
						product.getDetails(), null, null, product.getCategory().getName(), product.getCategory().getId(), product.getCreateDate());
				productsDisplay.add(productDTO);
			}
		}
	}

	public boolean isAccessoryInList(List<ProductDTO> productsDisplay, int productId) {
		for (ProductDTO productDTO : productsDisplay) {
			if (productDTO.getId() == productId) {
				return true;
			}
		}
		return false;
	}

	@RequestMapping("/findByCategoryId/{categoryId}")
	public ResponseEntity<Page<ProductDTO>> viewProductsByCategoryId(
			@PathVariable("categoryId") String categoryId,
			@RequestParam("page") Optional<Integer> page, 
			@RequestParam("size") Optional<Integer> size) {
		Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(9));
		Page<ProductDTO> products;
		List<ProductDTO> listProductCategoryDTO = pDAO.findProductFeedBack();
		List<Product> productsFindAll = pDAO.findAll();
		// Check để thêm những sản phẩm chưa có feedback vào list
		addMissingAccessories(productsFindAll, listProductCategoryDTO);
		if (categoryId == null || categoryId.isBlank()) {
			// Tạo Page<ProductDTO> products để gửi lại phía client
			int startIndex = (int) pageable.getOffset();
			int endIndex = Math.min((startIndex + pageable.getPageSize()), listProductCategoryDTO.size());
			List<ProductDTO> pageContent = listProductCategoryDTO.subList(startIndex, endIndex);
			products = new PageImpl<>(pageContent, pageable, listProductCategoryDTO.size());
		} else {
			for (int i = listProductCategoryDTO.size() - 1; i >= 0; i--) {
			    ProductDTO productDTO = listProductCategoryDTO.get(i);
			    if (!productDTO.getCategoryId().equals(categoryId)) {
			        // Check nếu không thuộc categoryId được gửi lên thì xóa khỏi list
			        listProductCategoryDTO.remove(i);
			    }
			}
			// Tạo Page<ProductDTO> products để gửi lại phía client
			int startIndex = (int) pageable.getOffset();
			int endIndex = Math.min((startIndex + pageable.getPageSize()), listProductCategoryDTO.size());
			List<ProductDTO> pageContent = listProductCategoryDTO.subList(startIndex, endIndex);
			products = new PageImpl<>(pageContent, pageable, listProductCategoryDTO.size());
		}
		
		return ResponseEntity.ok(products);
	}
	
	@RequestMapping("/findBySource/{source}")
	public ResponseEntity<Page<Product>> viewProductsBySource(
			@PathVariable("source") String source,
			@RequestParam("page") Optional<Integer> page, 
			@RequestParam("size") Optional<Integer> size) {
		Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(9));
		Page<Product> products;
		if (source == null || source.isBlank()) {
			products = pDAO.findAll(pageable);
		} else {
			products = pDAO.findBySource(source, pageable);
		}
		return ResponseEntity.ok(products);
	}

}
