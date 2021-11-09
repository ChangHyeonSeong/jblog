package com.douzone.jblog.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.jblog.security.AuthUser;
import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.service.CategoryService;
import com.douzone.jblog.service.FileUploadService;
import com.douzone.jblog.service.PostService;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.PostVo;
import com.douzone.jblog.vo.UserVo;

@Controller
@RequestMapping("/{id:(?!main|assets|user|logoImgs).*}")
public class BlogController {
	@Autowired
	private BlogService blogService;
	@Autowired
	private FileUploadService fileUploadService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private PostService postService;
	
	@GetMapping({"","/{catergoryNo:(?!admin).*}","/{catergoryNo:(?!admin).*}/{postNo}"})
	public String blogMain(
			Model model,
			@PathVariable(value = "id", required = true) String blogId,
			@PathVariable(value = "catergoryNo", required = false) Long catergoryNo,
			@PathVariable(value = "postNo", required = false) Long postNo,
			@ModelAttribute @AuthUser UserVo authUser) {
		
		if(blogId == null) {
			return "redirect:/";
		}
		BlogVo blogVo = blogService.getBlog(blogId);
		model.addAttribute("blogVo", blogVo);
		model.addAttribute("blogId", blogId);
		
		
		List<CategoryVo> categoryList =  categoryService.getCategory(blogId);
		model.addAttribute("categoryList", categoryList);

		//id 만 넘어올때
		if (catergoryNo == null) {
			//List<PostVo> postAllList = postService.getAll(blogId);
			PostVo lastPostVo = postService.getLastPostVo();
			
			List<PostVo> postList = postService.getAllByCategoryNo(blogId, lastPostVo.getCategoryNo());
			PostVo postVo = postList.get(0);
			model.addAttribute("postList", postList);
			model.addAttribute("postVo", postVo);
			
			return "blog/blog-main";
		}
		
		// id/catergoryNo
		if (postNo == null) {
			List<PostVo> postList = postService.getAllByCategoryNo(blogId,catergoryNo);
			model.addAttribute("postList", postList);
			
			PostVo postVo = postList.get(0);
			model.addAttribute("postVo", postVo);
			
			return "blog/blog-main";
		}
		
		
		// id/catergoryNo/postNo
		List<PostVo> postList = postService.getAllByCategoryNo(blogId,catergoryNo);
		model.addAttribute("postList", postList);
		
		PostVo postVo = null;
		for(PostVo vo : postList) {
			if(vo.getNo() == postNo) {
				postVo = vo;
				break;
			}
		}
		model.addAttribute("postVo", postVo);
		
		return "blog/blog-main";
	}
	
	//admin Auth 구현 해야함
	
	@GetMapping("/admin/basic")
	public String adminBasic(Model model,@ModelAttribute @AuthUser UserVo authUser) {
		BlogVo blogVo = blogService.getBlog(authUser.getId());
		model.addAttribute("blogVo", blogVo);
		return "blog/blog-admin-basic";
	}
	
	@PostMapping("/admin/basic")
	public String adminBasic(
			Model model, BlogVo blogVo,
			@RequestParam(value="logo-file") MultipartFile multipartFile,
			@ModelAttribute @AuthUser UserVo authUser) {
		String logo = fileUploadService.restoreImg(multipartFile);
		if(logo != null ) {
			blogVo.setLogo(logo);
		}
		blogService.updateBlog(blogVo);
		
		return "redirect:/" + authUser.getId();
	}
	
	@GetMapping("/admin/category")
	public String adminCategory(Model model,@ModelAttribute @AuthUser UserVo authUser) {
		BlogVo blogVo = blogService.getBlog(authUser.getId());
		model.addAttribute("blogVo", blogVo);
		
		List<CategoryVo> list =  categoryService.getCategory(authUser.getId());
		model.addAttribute("list", list);
		return "blog/blog-admin-category";
	}
	
	@PostMapping("/admin/category")
	public String adminCategory(Model model, CategoryVo categoryVo,
			@ModelAttribute @AuthUser UserVo authUser) {
		
		categoryService.addCategory(categoryVo);
		
		return "redirect:/" + authUser.getId() + "/admin/category";
	}
	
	@DeleteMapping("/admin/category/{no}")
	public String adminCategory(Model model,
			@PathVariable(value="no") Long no,
			@ModelAttribute @AuthUser UserVo authUser) {
		
		categoryService.deleteCategory(no);
		
		return "redirect:/" + authUser.getId() + "/admin/category";
	}
	

	@GetMapping("/admin/write")
	public String adminWrite(Model model,
			@ModelAttribute @AuthUser UserVo authUser) {
		BlogVo blogVo = blogService.getBlog(authUser.getId());
		model.addAttribute("blogVo", blogVo);
		
		List<CategoryVo> list =  categoryService.getCategory(authUser.getId());
		model.addAttribute("list", list);
		return "blog/blog-admin-write";
	}
	
	@PostMapping("/admin/write")
	public String adminWrite(Model model,PostVo postVo,
			@ModelAttribute @AuthUser UserVo authUser) {
		
		postService.addPost(postVo);
		
		BlogVo blogVo = blogService.getBlog(authUser.getId());
		model.addAttribute("blogVo", blogVo);
		List<CategoryVo> list =  categoryService.getCategory(authUser.getId());
		model.addAttribute("list", list);
		return "blog/blog-admin-write";
	}

	
}
