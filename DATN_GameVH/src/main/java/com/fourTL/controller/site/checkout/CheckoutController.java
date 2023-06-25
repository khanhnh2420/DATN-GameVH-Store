package com.fourTL.controller.site.checkout;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fourTL.dao.OrderDetailDAO;
import com.fourTL.dao.OrderDataDAO;
import com.fourTL.entities.OrderDetail;
import com.fourTL.entities.OrderData;






@Controller
public class CheckoutController {
	
	@Autowired
    private OrderDataDAO ordersDAO;
	
	@Autowired
    private OrderDetailDAO detailsDAO;
	
	@RequestMapping("/checkout")
	public String checkout() {
		return "site/checkout";
	}
	

	@PostMapping("/createorders")
    public String addCustomer(@ModelAttribute("customer") OrderDetail details, OrderData orders, Model model) {
        ordersDAO.save(orders);
        detailsDAO.save(details);
        model.addAttribute("message", "Customer added successfully");
        return "site/checkout";
    }
}
