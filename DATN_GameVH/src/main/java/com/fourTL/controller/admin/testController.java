package com.fourTL.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fourTL.dao.AccountDAO;
import com.fourTL.dao.AuthorityDAO;
import com.fourTL.dao.BannerDAO;
import com.fourTL.dao.BlogDAO;
import com.fourTL.dao.CategoryDAO;
import com.fourTL.dao.CommentDAO;
import com.fourTL.dao.CouponDAO;
import com.fourTL.dao.CouponOwnerDAO;
import com.fourTL.dao.FavoriteDAO;
import com.fourTL.dao.FeedBackDAO;
import com.fourTL.dao.LocationDAO;
import com.fourTL.dao.OrderDataDAO;
import com.fourTL.dao.OrderDetailDAO;
import com.fourTL.dao.ProductDAO;
import com.fourTL.dao.RoleDAO;
import com.fourTL.entities.Account;
import com.fourTL.entities.Authority;
import com.fourTL.entities.Banner;
import com.fourTL.entities.Blog;
import com.fourTL.entities.Category;
import com.fourTL.entities.Comment;
import com.fourTL.entities.Coupon;
import com.fourTL.entities.CouponOwner;
import com.fourTL.entities.Favorite;
import com.fourTL.entities.Feedback;
import com.fourTL.entities.Location;
import com.fourTL.entities.OrderData;
import com.fourTL.entities.OrderDetail;
import com.fourTL.entities.Product;
import com.fourTL.entities.Role;

@RestController
public class testController {

	@Autowired
	OrderDetailDAO ad;
	
	@GetMapping("/t")
	public ResponseEntity<List<OrderDetail>> getIndex() {
		return ResponseEntity.ok(ad.findAll());
	}

	
}
