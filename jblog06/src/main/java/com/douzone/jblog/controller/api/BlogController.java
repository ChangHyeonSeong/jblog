package com.douzone.jblog.controller.api;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.douzone.jblog.dto.JsonResult;
import com.douzone.jblog.security.Auth;
import com.douzone.jblog.service.CategoryService;
import com.douzone.jblog.service.PostService;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.PostVo;


@RestController("BlogApiController")
@RequestMapping("/blog/api")
public class BlogController {
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private PostService postService;
	
	@Auth
	@GetMapping("/check")
	public JsonResult checkCategory(
			@RequestParam(value="id", required=true, defaultValue="") String id,
			@RequestParam(value="no", required=true) Long no) {
		Map<String, Object> map = new HashMap<>();
		boolean lastCategory = false;
		boolean existPost = false;
		
		List<CategoryVo> categorylist = categoryService.getCategory(id);
		
		if(categorylist.size() == 1) {
			lastCategory = true;
		}
		
		List<PostVo> postlist = postService.getAllByCategoryNo(id, no);
	
		if(postlist.size() != 0) {
			existPost = true;
		}
		
		map.put("lastCategory", lastCategory);
		map.put("existPost", existPost);
		
		return JsonResult.success(map);
	}
	
	@Auth
	@DeleteMapping("/category/{no}")
	public JsonResult deleteCategory(
			@PathVariable(value="no", required=true) Long no,String id) {
		System.out.println("deleteCategory() called......");
		boolean result = false;
		List<PostVo> postList = postService.getAllByCategoryNo(id, no);
		
		//post가 없다면
		if(postList.isEmpty()) {
			result = categoryService.deleteCategory(id,no);
		}
		
		
		return JsonResult.success(result);
	}
	
}
