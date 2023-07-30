package com.gamevh.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gamevh.entities.FeedbackWebsite;
import com.gamevh.service.FeedbackWebsiteService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/feedbackwebsite")
public class FeedbackWebsiteRC {
	@Autowired
	FeedbackWebsiteService feedbackWebsiteService;
	
	@PostMapping("create")
	public ResponseEntity<FeedbackWebsite> feedbackSite(@RequestBody FeedbackWebsite feedbackWebsite) {
		if(feedbackWebsite != null) {
			feedbackWebsite = feedbackWebsiteService.add(feedbackWebsite);
			feedbackWebsite.setAccount(null);
			return ResponseEntity.ok(feedbackWebsite);
		}
		return null;
	}
}
