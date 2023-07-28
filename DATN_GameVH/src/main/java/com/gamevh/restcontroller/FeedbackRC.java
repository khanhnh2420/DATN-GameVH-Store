package com.gamevh.restcontroller;

import java.util.Collections;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gamevh.entities.Feedback;
import com.gamevh.repository.FeedbackRepository;
import com.gamevh.service.FeedbackService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/feedback")
public class FeedbackRC {
	@Autowired
	FeedbackService feedBackService;
	
	@Autowired
	FeedbackRepository feedbackRepository;

	@GetMapping("/getProduct/{productId}")
	public ResponseEntity<List<Feedback>> getAllFeedBackByProductId(@PathVariable("productId") Integer productId) {
		List<Feedback> listFeedBacks = feedBackService.findByProductId(productId);
		// Order By Z-A CreateDate
		Comparator<Feedback> dateComparator = Comparator.comparing(Feedback::getCreateDate);
		Collections.sort(listFeedBacks, dateComparator.reversed());
		return ResponseEntity.ok(listFeedBacks);
	}
	
}
