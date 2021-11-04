package com.douzone.jblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.jblog.security.AuthUser;
import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.UserVo;

@Controller
@RequestMapping("/{id:(?!main|assets|user|admin).*}")
public class BlogController {
	@Autowired
	private BlogService blogService;
	//"/{catergoryNo}","/{catergoryNo}/{postNo}"
	@GetMapping("")
	public String blog(
			Model model,
			@PathVariable("id") String blogId, 
			@ModelAttribute @AuthUser UserVo authUser) {
		
		
		
		return "blog/blog-main";
	}
	
	@GetMapping("/admin/basic")
	public String adminBasic(@ModelAttribute @AuthUser UserVo authUser) {
		
		return "blog/blog-admin-basic";
	}
	
	@PostMapping("/admin/basic")
	public String adminBasic(
			Model model, BlogVo blogVo,
			@RequestParam(value="file") MultipartFile multipartFile,
			@AuthUser UserVo authUser) {
		
		return "blog/blog-admin-basic";
	}
	
	@GetMapping("/admin/category")
	public String adminCategory(@AuthUser UserVo authUser) {
	
		return "blog/blog-admin-category";
	}
	
	@GetMapping("/admin/write")
	public String adminWrite(@AuthUser UserVo authUser) {
	
		return "blog/blog-admin-write";
	}
	
}
