package com.fourTL.controller.site.Blog;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BlogController {
	
	@RequestMapping("/blog")
	private String blog(Model model) {
		return "site/blog-list";
	}
	
	@RequestMapping("/blog-detail")
	private String blogDetail(Model model) {
		return "site/blog-detail";
	}
}
