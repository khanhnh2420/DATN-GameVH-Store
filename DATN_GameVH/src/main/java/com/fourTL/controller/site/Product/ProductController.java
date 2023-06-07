package com.fourTL.controller.site.Product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fourTL.dao.ProductDAO;
import com.fourTL.entities.Product;
import com.fourTL.service.ProductService;

@Controller
public class ProductController {

	@Autowired
	ProductService pService;

	@Autowired
	ProductDAO pDAO;

	@RequestMapping("/product/detail/{id}")
	private String index(Model model, @PathVariable("id") Integer id) {
		// Lấy sản phẩm theo ID
		Product item = pService.findById(id);
		model.addAttribute("item", item);
		// Xử lí chuỗi hình ảnh thành từng hình
		String[] itemIMG = item.getThumbnail().split("-\\*-");
		model.addAttribute("itemIMG", itemIMG);
		// Lấy danh sách sản phẩm cùng loại
		List<Product> sameProduct = pDAO.findByCategoryId(item.getCategory().getId());
		model.addAttribute("sameProduct", sameProduct);

		// Next and previous product
		List<Product> listProducts = pService.findAll();
		int currentIndex = -1;
		int nextIndex;
		int previousIndex;

		for (int i = 0; i < listProducts.size(); i++) {
			if (listProducts.get(i).getId() == id) {
				currentIndex = i;
				nextIndex = i;
				previousIndex = i;

				if ((currentIndex + 1) < listProducts.size()) {
					nextIndex++;
				} else {
					nextIndex = 0;
				}

				if (currentIndex > 0) {
					previousIndex--;
				} else {
					previousIndex = listProducts.size() - 1;
				}

				model.addAttribute("previousProduct", listProducts.get(previousIndex).getId());
				model.addAttribute("nextProduct", listProducts.get(nextIndex).getId());

				break;
			}
		}
		return "site/product-detail";
	}
}
