package com.fourTL.controller.site.Home;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fourTL.dao.OrderDetailDAO;
import com.fourTL.entities.Accessory;
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
		List<Product> listProduct = productService.findAll();
		model.addAttribute("products", getRandom(listProduct, 6));
		// List Top Selling Products
		List<OrderDetail> listProductTrending = orderDetailsDAO.findTopSellingProducts();
		model.addAttribute("productsTrending", listProductTrending);

		// List accessories random 6 product
		List<Accessory> listAccessories = accessoryService.findAll();
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
}
