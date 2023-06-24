//package com.fourTL.controller.site.ShopAccessory;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.fourTL.DTO.AccessoryDTO;
//import com.fourTL.DTO.impl.AccessoryDTOImpl;
//import com.fourTL.dao.AccessoryDAO;
//import com.fourTL.entities.Accessory;
//
//@CrossOrigin("*")
//@RestController
//@RequestMapping("/api/accessory")
//public class ShopAccessoryRestController {
//
//	@Autowired
//	AccessoryDAO accessoryDAO;
//
//	@RequestMapping("/getAll")
//	public ResponseEntity<Page<AccessoryDTO>> getAllAccessories(@RequestParam("page") Optional<Integer> page,
//			@RequestParam("size") Optional<Integer> size) {
//		Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(9));
//		List<AccessoryDTO> accessoriesDisplay = accessoryDAO.findAccessoryFeedBack(); // Danh sách accessoryDTO dạng List
//		List<Accessory> accessoriesFindAll = accessoryDAO.findAll();
//		// Check để thêm những sản phẩm chưa có feedback vào list
//		addMissingAccessories(accessoriesFindAll, accessoriesDisplay);
//		// Gán lại List đã check vào Danh sách accessoryDTO dạng pageable 
//		int startIndex = (int) pageable.getOffset();
//		int endIndex = Math.min((startIndex + pageable.getPageSize()), accessoriesDisplay.size());
//		List<AccessoryDTO> pageContent = accessoriesDisplay.subList(startIndex, endIndex);
//		Page<AccessoryDTO> accessoriesDisplayPageable = new PageImpl<>(pageContent, pageable, accessoriesDisplay.size());
//		return ResponseEntity.ok(accessoriesDisplayPageable);
//	}
//
//	public void addMissingAccessories(List<Accessory> accessoriesFindAll, List<AccessoryDTO> accessoriesDisplay) {
//		for (Accessory accessory : accessoriesFindAll) {
//			if (!isAccessoryInList(accessoriesDisplay, accessory.getId())) {
//				AccessoryDTO accessoryDTO = new AccessoryDTOImpl(accessory.getId(), accessory.getName(),
//						accessory.getPoster(), accessory.getThumbnail(), accessory.getSalePrice(), accessory.getOffer(),
//						accessory.getDetails(), null, null, accessory.getCreateDate());
//				accessoriesDisplay.add(accessoryDTO);
//			}
//		}
//	}
//
//	public boolean isAccessoryInList(List<AccessoryDTO> accessoriesDisplay, int accessoryId) {
//		for (AccessoryDTO accessoryDTO : accessoriesDisplay) {
//			if (accessoryDTO.getId() == accessoryId) {
//				return true;
//			}
//		}
//		return false;
//	}
//}
