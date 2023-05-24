package com.fourTL.controller.site.Blog;

import com.fourTL.entities.Blogs;
import com.fourTL.service.BlogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


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
