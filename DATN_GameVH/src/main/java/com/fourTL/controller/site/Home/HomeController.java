package com.fourTL.controller.site.Home;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fourTL.DTO.AccessoryDTO;
import com.fourTL.DTO.ProductDTO;
import com.fourTL.DTO.impl.ProductDTOImpl;
import com.fourTL.dao.OrderDetailDAO;
import com.fourTL.entities.OrderDetail;
import com.fourTL.entities.Product;
import com.fourTL.service.AccessoryService;
import com.fourTL.service.ProductService;

@Controller
public class HomeController {

	@Autowired
	ProductService productService;

	@Autowired
	AccessoryService accessoryService;

	@Autowired
	OrderDetailDAO orderDetailsDAO;

	@RequestMapping("/")
	private String index(Model model) {
		// List all product
		List<ProductDTO> listAllProductDTO = productService.findProductFeedBack();
		// List 6 Product Random
		model.addAttribute("products", getRandom(listAllProductDTO, 6));

		// List Top Selling Products
		List<OrderDetail> listProductTrendingOD = orderDetailsDAO.findTopSellingProducts();
		// List Top Selling Products
		List<ProductDTO> listProductDTO = productService.findProductFeedBack();
		List<ProductDTO> listProductTopTrending = new ArrayList<>();
		for (ProductDTO productDTO : listProductDTO) {
			for (OrderDetail orderDetail : listProductTrendingOD) {
				if(productDTO.getId() == orderDetail.getProduct().getId()) {
					listProductTopTrending.add(productDTO);
				}
			}
		}
		model.addAttribute("productsTrending", listProductTopTrending);

		// List Top Rated
		List<ProductDTO> listProductTopRated = productService.findProductFeedBack();
		// Order By Z-A getRate()
		Comparator<ProductDTO> rateComparator = Comparator.comparing(ProductDTO::getRate);
		Collections.sort(listProductTopRated, rateComparator.reversed());
		// Get Limit 6 Product
		listProductTopRated = listProductTopRated.subList(0, Math.min(6, listProductTopRated.size()));
		model.addAttribute("productTopRated", listProductTopRated);

		// List Product New Releases
		List<ProductDTO> listProductNewReleases = productService.findProductFeedBack();
		List<Product> listProductFindAll = productService.findAll();
		addMissingAccessories(listProductFindAll, listProductNewReleases);
		// Order By Z-A CreateDate
		Comparator<ProductDTO> newReleasesComparator = Comparator.comparing(ProductDTO::getCreateDate);
		Collections.sort(listProductNewReleases, newReleasesComparator.reversed());
		// Get Limit 6 Product
		listProductNewReleases = listProductNewReleases.subList(0, Math.min(6, listProductNewReleases.size()));
		model.addAttribute("productNewReleases", listProductNewReleases);

		// List accessories random 6 product
		List<AccessoryDTO> listAccessories = accessoryService.findAccessoryFeedBack();
		model.addAttribute("accessories", getRandom(listAccessories, 5));

		return "site/home";
	}

	public static <T> List<T> getRandom(List<T> list, int n) {
		// Hàm trả list sản phẩm random theo số lượng
		List<T> randomList = new ArrayList<>();
		Random random = new Random();
		while (randomList.size() < n && !list.isEmpty()) {
			int index = random.nextInt(list.size());
			T randomElement = list.get(index);
			if (!randomList.contains(randomElement)) {
				randomList.add(randomElement);
			}
			list.remove(index);
		}
		return randomList;
	}
	
	public void addMissingAccessories(List<Product> productsFindAll, List<ProductDTO> productsDisplay) {
		for (Product product : productsFindAll) {
			if (!isAccessoryInList(productsDisplay, product.getId())) {
				ProductDTO productDTO = new ProductDTOImpl(product.getId(), product.getName(),
						product.getPoster(), product.getThumbnail(), product.getSalePrice(), product.getOffer(),
						product.getDetails(), 0.0, null, product.getCategory().getName(), product.getCategory().getId(), product.getCreateDate());
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
}
