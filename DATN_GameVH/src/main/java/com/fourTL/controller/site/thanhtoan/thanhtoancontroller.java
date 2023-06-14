package com.fourTL.controller.site.thanhtoan;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fourTL.dao.OrderDetailDAO;
import com.fourTL.dao.Order_dataDAO;
import com.fourTL.entities.OrderDetail;
import com.fourTL.entities.OrderData;

@RestController
@RequestMapping("/api")
public class thanhtoancontroller {

	@Autowired
	Order_dataDAO ordersdao;

	@Autowired
	OrderDetailDAO orderDetailsDAO;

	@GetMapping("/vieworders")
	public ResponseEntity<List<OrderData>> getAll(Model model) {
		return ResponseEntity.ok(ordersdao.findAll());
	}
	
	@GetMapping("/vieworderdetail")
	public ResponseEntity<List<OrderDetail>> getAll1(Model model) {
		return ResponseEntity.ok(orderDetailsDAO.findAll());
	}

	@PostMapping("/createorders")
	public ResponseEntity<OrderData> createOrder(@RequestBody OrderData orders) {
		ordersdao.save(orders);
		return ResponseEntity.ok(orders);
	}
	
	@PostMapping("/createorderdetail")
	public ResponseEntity<OrderDetail> createOrder(@RequestBody OrderDetail orderDetails) {
		orderDetailsDAO.save(orderDetails);
		return ResponseEntity.ok(orderDetails);
	}
	
//	@PostMapping("/createorders")
//    public ResponseEntity<Orders> createOrder(@RequestBody Orders order) {
//        order = ordersdao.save(order);
//        for (OrderDetails detail : order.getOrderDetails()) {
//            detail.setOrder(order);
//        }
//        ordersdao.save(order);
//        return ResponseEntity.ok(order);
//    }

}
