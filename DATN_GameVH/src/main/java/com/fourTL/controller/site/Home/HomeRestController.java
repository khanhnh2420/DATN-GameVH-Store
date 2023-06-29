package com.fourTL.controller.site.Home;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fourTL.DTO.ProductDTO;
import com.fourTL.DTO.impl.ProductDTOImpl;
import com.fourTL.dao.OrderDetailDAO;
import com.fourTL.entities.Feedback;
import com.fourTL.entities.OrderDetail;
import com.fourTL.entities.Product;
import com.fourTL.service.FeedBackService;
import com.fourTL.service.ProductService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/homepage")
public class HomeRestController {
	@Autowired
	ProductService productService;

	@Autowired
	OrderDetailDAO orderDetailsDAO;

	@Autowired
	FeedBackService feedBackService;

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
