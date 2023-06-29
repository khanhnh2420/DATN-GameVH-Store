package com.fourTL.controller.site.WishList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WishlistController {
	@RequestMapping("/viewWishlist")
	private String Wishlist(Model model) {
		return "site/wishlist";
	}
	
}
