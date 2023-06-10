package com.fourTL.controller.site.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fourTL.DTO.ProductDTO;
import com.fourTL.DTO.impl.ProductDTOImpl;
import com.fourTL.dao.ProductDAO;
import com.fourTL.entities.FeedBack;
import com.fourTL.entities.Product;
import com.fourTL.service.FeedBackService;
import com.fourTL.service.ProductService;

@Controller
public class ProductController {

	@Autowired
	ProductService pService;

	@Autowired
	ProductDAO pDAO;

	@Autowired
	FeedBackService feedBackService;

	@RequestMapping("/product/detail/{id}")
	private String index(Model model, @PathVariable("id") Integer id) {
		// Lấy sản phẩm theo ID
		Product item = pService.findById(id);
		model.addAttribute("item", item);
		// Xử lí chuỗi hình ảnh thành từng hình
		String[] itemIMG = item.getThumbnail().split("-\\*-");
		model.addAttribute("itemIMG", itemIMG);
		// Lấy danh sách sản phẩm cùng loại
		List<ProductDTO> sameProduct = pDAO.findProductFeedBack();
		List<Product> listProductFindAll = pDAO.findAll();
		addMissingAccessories(listProductFindAll, sameProduct);
		for (int i = sameProduct.size() - 1; i >= 0; i--) {
			ProductDTO productDTO = sameProduct.get(i);
			if (!productDTO.getCategoryId().equals(item.getCategory().getId())) {
				// Check nếu không thuộc categoryId được gửi lên thì xóa khỏi list
				sameProduct.remove(i);
			}
		}
		model.addAttribute("sameProduct", getRandom(sameProduct, 5));

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
		
		List<FeedBack> listFeedBacks = feedBackService.findByProductId(id);
		Double productRatePoint = 0.0;
		Double sumStar = 0.0;
		int totalFeedBack = 0;
		for (FeedBack feedBack : listFeedBacks) {
			sumStar += (double)feedBack.getStar();
			if(feedBack.getStatus()) {
				totalFeedBack += 1;
			}
		}
		productRatePoint = (double) (sumStar / listFeedBacks.size());
		model.addAttribute("productRatePoint", productRatePoint * 20);
		model.addAttribute("totalFeedBack", totalFeedBack);
		
		return "site/product-detail";
	}

	public void addMissingAccessories(List<Product> productsFindAll, List<ProductDTO> productsDisplay) {
		for (Product product : productsFindAll) {
			if (!isAccessoryInList(productsDisplay, product.getId())) {
				ProductDTO productDTO = new ProductDTOImpl(product.getId(), product.getName(), product.getPoster(),
						product.getThumbnail(), product.getSalePrice(), product.getOffer(), product.getDetails(), 0.0,
						null, product.getCategory().getName(), product.getCategory().getId(), product.getCreateDate());
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
