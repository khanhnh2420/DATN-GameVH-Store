package com.fourTL.controller.admin.Invoicemanagement;

import java.util.List;

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

import com.fourTL.controller.mail.contact.mail_CONSTANT;
import com.fourTL.dao.CategoryDAO;
import com.fourTL.dao.OrderDetailDAO;
import com.fourTL.dao.Order_dataDAO;
import com.fourTL.entities.Category;
import com.fourTL.entities.MailInfo;
import com.fourTL.entities.OrderDetail;
import com.fourTL.entities.Order_data;
import com.fourTL.service.MailService;

import jakarta.mail.MessagingException;




@CrossOrigin("*")
@RestController
@RequestMapping("/admin/api/order")
public class InvoiceManagementRestController {
	mail_CONSTANT mailBody = new mail_CONSTANT();
	@Autowired
	Order_dataDAO ordersdao;

	@Autowired
	CategoryDAO categoriesDAO;
	
	@Autowired
	OrderDetailDAO orderDetailsDAO;
	
	@Autowired
	MailService mailService;

	@GetMapping("")
	public ResponseEntity<List<Order_data>> getAll(Model model) {
		return ResponseEntity.ok(ordersdao.findAll());
	}

	@GetMapping("/categories")
	public ResponseEntity<List<Category>> getAllCategories(Model model) {
		return ResponseEntity.ok(categoriesDAO.findAll());
	}
	
	@GetMapping("/search/{search}")
	public ResponseEntity<List<Order_data>> search(Model model, @PathVariable("search") String search) {
		return ResponseEntity.ok(ordersdao.findByUsernameContaining(search));
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Order_data> getOne(@PathVariable("id") Long id) {
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
		return ResponseEntity.ok(ordersdao.findById(id).get().getOrderDetails());
	}
}
