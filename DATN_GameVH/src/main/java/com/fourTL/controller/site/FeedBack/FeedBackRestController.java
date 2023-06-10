package com.fourTL.controller.site.FeedBack;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fourTL.entities.FeedBack;
import com.fourTL.service.FeedBackService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/feedback")
public class FeedBackRestController {

	@Autowired
	FeedBackService feedBackService;

	@RequestMapping("/getProduct/{productId}")
	public ResponseEntity<List<FeedBack>> getAllFeedBackByProductId(@PathVariable("productId") Integer productId) {
		List<FeedBack> listFeedBacks = feedBackService.findByProductId(productId);
		// Order By Z-A CreateDate
		Comparator<FeedBack> dateComparator = Comparator.comparing(FeedBack::getCreateDate);
		Collections.sort(listFeedBacks, dateComparator.reversed());
		return ResponseEntity.ok(listFeedBacks);
	}
	
	@RequestMapping("/getAccessory/{accessoryId}")
	public ResponseEntity<List<FeedBack>> getAllFeedBackByAccessoryId(@PathVariable("accessoryId") Integer accessoryId) {
		List<FeedBack> listFeedBacks = feedBackService.findByAccessoryId(accessoryId);
		// Order By Z-A CreateDate
		Comparator<FeedBack> dateComparator = Comparator.comparing(FeedBack::getCreateDate);
		Collections.sort(listFeedBacks, dateComparator.reversed());
		return ResponseEntity.ok(listFeedBacks);
	}
}
