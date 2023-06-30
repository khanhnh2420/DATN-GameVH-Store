package com.fourTL.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class adminController {
	@RequestMapping("/admin/dashboard")
	public String dashboard() {
		return "admin/product-list";
	}
	
	@RequestMapping("/admin/product")
	public String product() {
		return "admin/product-list";
	}
	
	@RequestMapping("/admin/order")
	public String orders() {
		return "admin/order-list";
	}
	
	@RequestMapping("/admin/account")
	public String maneger() {
		return "admin/account-list";
	}
}
