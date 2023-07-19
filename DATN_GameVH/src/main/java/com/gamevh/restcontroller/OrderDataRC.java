package com.gamevh.restcontroller;

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

import com.gamevh.dto.CartDTO;
import com.gamevh.dto.MailInfoDTO;
import com.gamevh.dto.OrderRequestDTO;
import com.gamevh.entities.Category;
import com.gamevh.entities.CouponOwner;
import com.gamevh.entities.OrderData;
import com.gamevh.entities.OrderDetail;
import com.gamevh.entities.Product;
import com.gamevh.repository.CategoryRepository;
import com.gamevh.repository.OrderDataRepository;
import com.gamevh.service.CouponOwnerService;
import com.gamevh.service.MailService;
import com.gamevh.service.OrderDetailService;
import com.gamevh.service.OrderService;
import com.gamevh.service.ProductService;
import com.gamevh.util.mail_CONSTANT;

import jakarta.mail.MessagingException;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/orderdata")
public class OrderDataRC {
	mail_CONSTANT mailBody = new mail_CONSTANT();
	@Autowired
	OrderDataRepository orderDataRepository;

	@Autowired
	CategoryRepository categoriesDAO;

	@Autowired
	OrderDataRepository orderDetailsDAO;

	@Autowired
	MailService mailService;

	@Autowired
	OrderService orderService;

	@Autowired
	CouponOwnerService couponOwnerService;
	
	@Autowired
	OrderDetailService orderDetailService;
	
	@Autowired
	ProductService productService;

	@GetMapping("")
	public ResponseEntity<List<OrderData>> getAll(Model model) {
		return ResponseEntity.ok(orderDataRepository.findAll());
	}

	@GetMapping("/categories")
	public ResponseEntity<List<Category>> getAllCategories(Model model) {
		return ResponseEntity.ok(categoriesDAO.findAll());
	}

	@GetMapping("/search/{search}")
	public ResponseEntity<List<OrderData>> search(Model model, @PathVariable("search") String search) {
		return ResponseEntity.ok(orderDataRepository.findByUsernameContaining(search));
	}

	@GetMapping("{id}")
	public ResponseEntity<OrderData> getOne(@PathVariable("id") Long id) {
		if (!orderDataRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(orderDataRepository.findById(id).get());
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		if (!orderDataRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		orderDataRepository.deleteById(id);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/sendEmail")
	public ResponseEntity<MailInfoDTO> sendMail(@RequestBody MailInfoDTO mailInfo) throws MessagingException {
		MailInfoDTO mail = new MailInfoDTO();
		mail.setTo(mailInfo.getTo());
		mail.setSubject("Thông Báo Tạo Tài Khoản Thành Công");
		mail.setBody(mailBody.mail_order(mailInfo.getTo(), mailInfo.getSource()));
		mailService.send(mail);
		return ResponseEntity.ok(mailInfo);
	}

	@GetMapping("/orderdetail/{id}")
	public ResponseEntity<List<OrderDetail>> srcgame(Model model, @PathVariable("id") Long id) {
		return ResponseEntity.ok(orderDataRepository.findById(id).get().getOrderDetail());
	}

	@PostMapping("/create")
	public ResponseEntity<OrderData> createOrderAndOrderDetailAndUpdateCouponOwner(@RequestBody OrderRequestDTO orderRequestDTO) {
		if (orderRequestDTO.getOrderData() != null) {
			if (orderRequestDTO.getOrderData().getId() == null && orderService.findByOrderId(orderRequestDTO.getOrderData().getOrderId()).isEmpty()) {
				OrderData orderDataResult = orderService.add(orderRequestDTO.getOrderData());
				if (orderDataResult != null && orderRequestDTO.getOrderData().getCouponCode() != null
						&& !orderRequestDTO.getOrderData().getCouponCode().equals("")) {
					CouponOwner couponOwner = couponOwnerService
							.findCouponByAccount(orderRequestDTO.getOrderData().getAccount().getUsername(), orderRequestDTO.getOrderData().getCouponCode());
					couponOwner.setStatus(false);
					couponOwnerService.update(couponOwner);
				}
				
				if(orderDataResult != null) {
					for (CartDTO cartDTO : orderRequestDTO.getListCartDTO()) {
						Product product = productService.findById(cartDTO.getId());
						OrderDetail orderDetail = new OrderDetail();
						orderDetail.setOrderData(orderDataResult);
						orderDetail.setPrice(product.getSalePrice());
						orderDetail.setProduct(product);
						orderDetail.setQty(cartDTO.getQty());
						
						orderDetailService.add(orderDetail);
					}
				}
				return ResponseEntity.ok(orderRequestDTO.getOrderData());
			}
		}

		return null;
	}
}
