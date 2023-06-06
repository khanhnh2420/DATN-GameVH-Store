package com.fourTL.controller.site.Accessory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fourTL.dao.AccessoryDAO;
import com.fourTL.dao.ProductDAO;
import com.fourTL.entities.Accessory;
import com.fourTL.entities.Product;
import com.fourTL.service.AccessoryService;
import com.fourTL.service.ProductService;

@Controller
public class AccessoryController {

	@Autowired
	AccessoryService accessoryService;

	@Autowired
	AccessoryDAO accessoryDAO;

	@RequestMapping("/accessory/detail/{id}")
	private String index(Model model, @PathVariable("id") Integer id) {
		// Lấy sản phẩm theo ID
		Accessory item = accessoryService.findById(id);
		model.addAttribute("item", item);
		// Xử lí chuỗi hình ảnh thành từng hình
		String[] itemIMG = item.getThumbnail().split("-\\*-");
		model.addAttribute("itemIMG", itemIMG);

		// List accessories random 6 product
		List<Accessory> listAccessories = accessoryService.findAll();
		model.addAttribute("sameProduct", getRandom(listAccessories, 5));

		// Next and previous accessory
		listAccessories = accessoryService.findAll();
		int currentIndex = -1;
		int nextIndex;
		int previousIndex;

		for (int i = 0; i < listAccessories.size(); i++) {
			if (listAccessories.get(i).getId() == id) {
				currentIndex = i;
				nextIndex = i;
				previousIndex = i;

				if ((currentIndex + 1) < listAccessories.size()) {
					nextIndex++;
				} else {
					nextIndex = 0;
				}

				if (currentIndex > 0) {
					previousIndex--;
				} else {
					previousIndex = listAccessories.size() - 1;
				}

				model.addAttribute("previousProduct", listAccessories.get(previousIndex).getId());
				model.addAttribute("nextProduct", listAccessories.get(nextIndex).getId());

				break;
			}
		}
		return "site/accessory-detail";
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
