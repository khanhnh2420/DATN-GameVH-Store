package com.gamevh.restcontroller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gamevh.dto.ProductDTO;
import com.gamevh.dto.impl.ProductDTOImpl;
import com.gamevh.entities.Feedback;
import com.gamevh.entities.OrderDetail;
import com.gamevh.entities.Product;
import com.gamevh.reponsitory.OrderDetailRepository;
import com.gamevh.service.FeedbackService;
import com.gamevh.service.ProductService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/product")
public class ProductRC {
	@Autowired
	ProductService productService;

	@Autowired
	OrderDetailRepository orderDetailsDAO;

	@Autowired
	FeedbackService feedBackService;

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

	@GetMapping("/{productId}")
	public ResponseEntity<Product> getProductById(@PathVariable("productId") Integer productId) {
		return ResponseEntity.ok(productService.findById(productId));
	}
	
	@GetMapping("topselling")
	public ResponseEntity<List<ProductDTO>> getTop6Selling() {
		// List Top Selling Products
		List<OrderDetail> listOrderDetail = orderDetailsDAO.findTopSellingProducts();
		List<ProductDTO> listTopSelling = new ArrayList<>();
		for (OrderDetail orderDetail : listOrderDetail) {
			if (listTopSelling.size() == 6) {
				break;
			} else {
				if (orderDetail.getProduct().getAvailable()) {
					listTopSelling.add(convertProductToProductDTO(orderDetail.getProduct()));
				}
			}
		}
		return ResponseEntity.ok(listTopSelling);
	}

	@GetMapping("newrelease")
	public ResponseEntity<List<ProductDTO>> getTop6NewRelease() {
		// List Product New Releases
		List<ProductDTO> listProductNewReleases = new ArrayList<>();
		List<Product> listProductFindAll = productService.findAll();
		for (Product product : listProductFindAll) {
			if (product.getAvailable()) {
				listProductNewReleases.add(convertProductToProductDTO(product));
			}
		}
		// Order By Z-A CreateDate
		Comparator<ProductDTO> newReleasesComparator = Comparator.comparing(ProductDTO::getCreateDate);
		Collections.sort(listProductNewReleases, newReleasesComparator.reversed());
		// Get Limit 6 Product
		listProductNewReleases = listProductNewReleases.subList(0, Math.min(6, listProductNewReleases.size()));
		return ResponseEntity.ok(listProductNewReleases);
	}

	@GetMapping("toprate")
	public ResponseEntity<List<ProductDTO>> getTop6Rated() {
		// List Product TopRated
		List<ProductDTO> listProductTopRated = new ArrayList<>();
		List<Product> listProductFindAll = productService.findAll();
		for (Product product : listProductFindAll) {
			if (product.getAvailable()) {
				listProductTopRated.add(convertProductToProductDTO(product));
			}
		}
		// Order By Z-A ratePoint
		Comparator<ProductDTO> newReleasesComparator = Comparator.comparing(ProductDTO::getRate);
		Collections.sort(listProductTopRated, newReleasesComparator.reversed());
		// Get Limit 6 Product

		listProductTopRated = listProductTopRated.subList(0, Math.min(6, listProductTopRated.size()));
		return ResponseEntity.ok(listProductTopRated);
	}

	@GetMapping("getListGame")
	public ResponseEntity<List<ProductDTO>> getListGame() {
		// List Product New Releases
		List<ProductDTO> listProductGame = new ArrayList<>();
		List<Product> listProductFindAll = productService.findAll();
		for (Product product : listProductFindAll) {
			if (product.getType().equalsIgnoreCase("Game")) {
				if (product.getAvailable()) {
					listProductGame.add(convertProductToProductDTO(product));
				}

			}
		}

		return ResponseEntity.ok(listProductGame);
	}

	@GetMapping("getListAccessory")
	public ResponseEntity<List<ProductDTO>> getListAccessory() {
		// List Product New Releases
		List<ProductDTO> listProductAccessory = new ArrayList<>();
		List<Product> listProductFindAll = productService.findAll();
		for (Product product : listProductFindAll) {
			if (product.getType().equalsIgnoreCase("PK")) {
				if (product.getAvailable()) {
					listProductAccessory.add(convertProductToProductDTO(product));
				}
			}
		}

		return ResponseEntity.ok(listProductAccessory);
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
}
