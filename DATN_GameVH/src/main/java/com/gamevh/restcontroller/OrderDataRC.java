package com.gamevh.restcontroller;

import java.util.List;

import com.gamevh.dto.FullOrderDTO;
import com.gamevh.service.OrderService;
import com.gamevh.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gamevh.entities.Category;
import com.gamevh.entities.MailInfo;
import com.gamevh.entities.OrderData;
import com.gamevh.entities.OrderDetail;
import com.gamevh.reponsitory.CategoryRepository;
import com.gamevh.reponsitory.OrderDataRepository;
import com.gamevh.service.MailService;
import com.gamevh.util.mail_CONSTANT;

import jakarta.mail.MessagingException;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/orderdata")
public class OrderDataRC {
	mail_CONSTANT mailBody = new mail_CONSTANT();
	@Autowired
	OrderDataRepository ordersdao;

	@Autowired
	OrderServiceImpl orderService;
	@Autowired
	CategoryRepository categoriesDAO;
	
	@Autowired
	OrderDataRepository orderDetailsDAO;
	
	@Autowired
	MailService mailService;

	@GetMapping("")
	public ResponseEntity<List<OrderData>> getAll(Model model) {
		return ResponseEntity.ok(ordersdao.findAll());
	}

	@GetMapping("/full/{orderId}")
	public FullOrderDTO findOne(@PathVariable("orderId") String orderId){
		return orderService.findOne(orderId);
	}

	@GetMapping("/categories")
	public ResponseEntity<List<Category>> getAllCategories(Model model) {
		return ResponseEntity.ok(categoriesDAO.findAll());
	}
	
	@GetMapping("/search/{search}")
	public ResponseEntity<List<OrderData>> search(Model model, @PathVariable("search") String search) {
		return ResponseEntity.ok(ordersdao.findByUsernameContaining(search));
	}
	
	@GetMapping("{id}")
	public ResponseEntity<OrderData> getOne(@PathVariable("id") Long id) {
		if(!ordersdao.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(ordersdao.findById(id).get());
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		if(!ordersdao.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		ordersdao.deleteById(id);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/sendEmail")
	public ResponseEntity<MailInfo> sendMail(@RequestBody MailInfo mailInfo) throws MessagingException {
		MailInfo mail = new MailInfo();
		mail.setTo(mailInfo.getTo());
		mail.setSubject("Thông Báo Tạo Tài Khoản Thành Công");
		mail.setBody(mailBody.mail_order(mailInfo.getTo(), mailInfo.getSource()));
		mailService.send(mail);
		return ResponseEntity.ok(mailInfo);
	}
	
	@GetMapping("/orderdetail/{id}")
	public ResponseEntity<List<OrderDetail>> srcgame(Model model, @PathVariable("id") Long id) {
		return ResponseEntity.ok(ordersdao.findById(id).get().getOrderDetail());
	}
}
