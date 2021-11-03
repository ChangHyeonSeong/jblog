package com.douzone.jblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.douzone.jblog.service.BlogService;

@Controller
@RequestMapping("/{id:(?!main|assets|user|admin).*}")
public class BlogController {
	@Autowired
	private BlogService blogService;
	//"/{catergoryNo}","/{catergoryNo}/{postNo}"
	@GetMapping("")
	public String blog(@PathVariable("id") String blogId) {
		return "blog/blog-main";
	}
	
}
