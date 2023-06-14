package com.fourTL.controller.site.MyAccount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fourTL.dao.AccountDAO;
import com.fourTL.dao.OrderDataDAO;
import com.fourTL.entities.Account;

@Controller
public class MyAccountController {
	@Autowired
	AccountDAO accountsDAO;
	
	@Autowired
	OrderDataDAO ordersDAO;
	
	@RequestMapping("/account")
	public String account(@RequestParam("username") String username, Model model) {
		Account user = accountsDAO.findById(username).get();
		user.getOrderData().sort((o1, o2) -> o2.getCreateDate().compareTo(o1.getCreateDate()));
		model.addAttribute("user", user);
		return "site/dashboard";
	}
}
