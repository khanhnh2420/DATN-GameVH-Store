package com.fourTL.controller.site.Accessory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fourTL.DTO.AccessoryDTO;
import com.fourTL.DTO.impl.AccessoryDTOImpl;
import com.fourTL.dao.AccessoryDAO;
import com.fourTL.entities.Accessory;
import com.fourTL.entities.FeedBack;
import com.fourTL.service.AccessoryService;
import com.fourTL.service.FeedBackService;

@Controller
public class AccessoryController {

	@Autowired
	AccessoryService accessoryService;

	@Autowired
	AccessoryDAO accessoryDAO;
	
	@Autowired
	FeedBackService feedBackService;

	@RequestMapping("/accessory/detail/{id}")
	private String index(Model model, @PathVariable("id") Integer id) {
		// Lấy sản phẩm theo ID
		Accessory item = accessoryService.findById(id);
		model.addAttribute("item", item);
		// Xử lí chuỗi hình ảnh thành từng hình
		String[] itemIMG = item.getThumbnail().split("-\\*-");
		model.addAttribute("itemIMG", itemIMG);

		// List accessories random 6 product
		List<AccessoryDTO> sameProduct = accessoryService.findAccessoryFeedBack();
		List<Accessory> listAccessoriesFindAll = accessoryService.findAll();
		addMissingAccessories(listAccessoriesFindAll, sameProduct);
		model.addAttribute("sameProduct", getRandom(sameProduct, 5));

		// Next and previous accessory
		List<Accessory> listAccessories = accessoryService.findAll();
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
		
		List<FeedBack> listFeedBacks = feedBackService.findByAccessoryId(id);
		Double accessoryRatePoint = 0.0;
		Double sumStar = 0.0;
		int totalFeedBack = 0;
		for (FeedBack feedBack : listFeedBacks) {
			sumStar += (double)feedBack.getStar();
			if(feedBack.getStatus()) {
				totalFeedBack += 1;
			}
		}
		accessoryRatePoint = (double) (sumStar / listFeedBacks.size());
		model.addAttribute("accessoryRatePoint", accessoryRatePoint * 20);
		model.addAttribute("totalFeedBack", totalFeedBack);
		
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
	
	public void addMissingAccessories(List<Accessory> accessoriesFindAll, List<AccessoryDTO> accessoriesDisplay) {
		for (Accessory accessory : accessoriesFindAll) {
			if (!isAccessoryInList(accessoriesDisplay, accessory.getId())) {
				AccessoryDTO accessoryDTO = new AccessoryDTOImpl(accessory.getId(), accessory.getName(),
						accessory.getPoster(), accessory.getThumbnail(), accessory.getSalePrice(), accessory.getOffer(),
						accessory.getDetails(), 0.0, null, accessory.getCreateDate());
				accessoriesDisplay.add(accessoryDTO);
			}
		}
	}

	public boolean isAccessoryInList(List<AccessoryDTO> accessoriesDisplay, int accessoryId) {
		for (AccessoryDTO accessoryDTO : accessoriesDisplay) {
			if (accessoryDTO.getId() == accessoryId) {
				return true;
			}
		}
		return false;
	}
}
