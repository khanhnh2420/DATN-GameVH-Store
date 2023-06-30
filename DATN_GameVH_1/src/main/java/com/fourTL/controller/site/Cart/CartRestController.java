//package com.fourTL.controller.site.Cart;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.fourTL.entities.Accessory;
//import com.fourTL.entities.Cart;
//import com.fourTL.entities.Product;
//import com.fourTL.service.ProductService;
//
//import jakarta.servlet.http.HttpSession;
//
//@CrossOrigin("*")
//@RestController
//@RequestMapping("restCart")
//public class CartRestController {
//
//	@Autowired
//	ProductService pService;
//	
//	@PostMapping("/addCart/{id}")
//	private HashMap<String, Cart> addItem(HttpSession ss, @PathVariable("id") Integer productID) {
//		HashMap<String, Cart> cartItems = (HashMap<String, Cart>) ss.getAttribute("myCart");
//		if (cartItems == null) {
//			cartItems = new HashMap<>();
//		}
//		Product products = pService.findById(productID);
//		if (products != null) {
//			if (cartItems.containsKey("SP" + productID)) {
//				Cart item = cartItems.get("SP" + productID);
//				item.setProduct(products);
//				item.setQuantity(1);
//				cartItems.put("SP" + productID, item);
//			} else {
//				Cart item = new Cart();
//				item.setProduct(products);
//				item.setQuantity(1);
//				cartItems.put("SP" + productID, item);
//			}
//		}
//		ss.setAttribute("myCart", cartItems);
//		ss.setAttribute("myCartTotal", totalPrice(cartItems));
//		ss.setAttribute("myCartLength", cartItems.size());
//		return null;
//	}
//	
//	@PostMapping("/addCart/accessory/{id}")
//	private HashMap<String, Cart> addAccessory(HttpSession ss, @PathVariable("id") Integer accessoryId) {
//		HashMap<String, Cart> cartItems = (HashMap<String, Cart>) ss.getAttribute("myCart");
//		if (cartItems == null) {
//			cartItems = new HashMap<>();
//		}
//		Accessory accessory = accessoryService.findById(accessoryId);
//		if (accessory != null) {
//			if (cartItems.containsKey("PK" + accessoryId)) {
//				Cart item = cartItems.get("PK" + accessoryId);
//				item.setAccessory(accessory);
//				if((item.getQuantity() + 1) <= accessory.getQty()) {
//					item.setQuantity(cartItems.get("PK" + accessoryId).getQuantity() + 1);
//				}
//				cartItems.put("PK" + accessoryId, item);
//			} else {
//				Cart item = new Cart();
//				item.setAccessory(accessory);
//				item.setQuantity(1);
//				cartItems.put("PK" + accessoryId, item);
//			}
//		}
//		ss.setAttribute("myCart", cartItems);
//		ss.setAttribute("myCartTotal", totalPrice(cartItems));
//		ss.setAttribute("myCartLength", cartItems.size());
//		return null;
//	}
//
//	@PostMapping("/removeCart/{id}")
//	private HashMap<String, Cart> removeItem(HttpSession ss, @PathVariable("id") Integer productID) {
//		HashMap<String, Cart> cartItems = (HashMap<String, Cart>) ss.getAttribute("myCart");
//		if (cartItems == null) {
//			cartItems = new HashMap<>();
//		}
//		if (cartItems.containsKey("SP" + productID)) {
//			cartItems.remove("SP" + productID);
//		}
//		ss.setAttribute("myCart", cartItems);
//		ss.setAttribute("myCartTotal", totalPrice(cartItems));
//		ss.setAttribute("myCartLength", cartItems.size());
//		return null;
//	}
//	
//	@PostMapping("/removeCart/accessory/{id}")
//	private HashMap<String, Cart> removeAccessory(HttpSession ss, @PathVariable("id") Integer accessoryId) {
//		HashMap<String, Cart> cartItems = (HashMap<String, Cart>) ss.getAttribute("myCart");
//		if (cartItems == null) {
//			cartItems = new HashMap<>();
//		}
//		if (cartItems.containsKey("PK" + accessoryId)) {
//			cartItems.remove("PK" + accessoryId);
//		}
//		ss.setAttribute("myCart", cartItems);
//		ss.setAttribute("myCartTotal", totalPrice(cartItems));
//		ss.setAttribute("myCartLength", cartItems.size());
//		return null;
//	}
//
//	@PostMapping("/clearCart")
//	private HashMap<String, Cart> clearCart(HttpSession ss) {
//		HashMap<String, Cart> cartItems = (HashMap<String, Cart>) ss.getAttribute("myCart");
//		cartItems = new HashMap<>();
//		ss.setAttribute("myCart", cartItems);
//		ss.setAttribute("myCartTotal", totalPrice(cartItems));
//		ss.setAttribute("myCartLength", cartItems.size());
//		return null;
//	}
//
//	@GetMapping("/getCart")
//	private HashMap<String, Cart> getCart(HttpSession ss) {
//		HashMap<String, Cart> cartItems = (HashMap<String, Cart>) ss.getAttribute("myCart");
//		if (cartItems == null) {
//			cartItems = new HashMap<>();
//		}
//		ss.setAttribute("myCart", cartItems);
//		return cartItems;
//	}
//
//	@GetMapping("/getCartTotal")
//	private double getCartTotal(HttpSession ss) {
//		Double cartTotalObj = (Double) ss.getAttribute("myCartTotal");
//		double cartTotal = cartTotalObj != null ? cartTotalObj : 0;
//		return cartTotal;
//	}
//
//	@GetMapping("/getCartLength")
//	private int getCartLength(HttpSession ss) {
//		Integer cartLenghtObj = (Integer) ss.getAttribute("myCartLength");
//		int cartLenght = cartLenghtObj != null ? cartLenghtObj : 0;
//		return cartLenght;
//	}
//
//	private double totalPrice(HashMap<String, Cart> cartItems) {
//		int count = 0;
//		for (Map.Entry<String, Cart> list : cartItems.entrySet()) {
//			if(list.getValue().getProduct() != null) {
//				if(list.getValue().getProduct().getOffer() == 0) {
//					count += list.getValue().getProduct().getSalePrice() * list.getValue().getQuantity();
//				} else {
//					count += (list.getValue().getProduct().getSalePrice()- (list.getValue().getProduct().getSalePrice() * list.getValue().getProduct().getOffer())) * list.getValue().getQuantity();
//				}	
//			} else {
//				if(list.getValue().getAccessory().getOffer() == 0) {
//					count += list.getValue().getAccessory().getSalePrice() * list.getValue().getQuantity();
//				} else {
//					count += (list.getValue().getAccessory().getSalePrice()- (list.getValue().getAccessory().getSalePrice() * list.getValue().getAccessory().getOffer())) * list.getValue().getQuantity();
//				}
//			}	
//		}
//		return count;
//	}
//}
