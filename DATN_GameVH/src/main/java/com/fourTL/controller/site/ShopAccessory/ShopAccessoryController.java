package com.fourTL.controller.site.ShopAccessory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ShopAccessoryController {
	
	@GetMapping("/accessory")
	private String viewAccessory() {
		return "site/accessory";
	}
	
	@GetMapping("/accessory-2cols")
	private String viewAccessory2Col() {
		return "site/accessory-2cols";
	}
	@GetMapping("/accessory-4cols")
	private String viewAccessory4Col() {
		return "site/accessory-4cols";
	}
	@GetMapping("/accessory-list")
	private String viewAccessoryList() {
		return "site/accessory-list";
	}
}
