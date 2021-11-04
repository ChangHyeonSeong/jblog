package com.douzone.jblog.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.BlogRepository;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.UserVo;

@Service
public class BlogService {
	
	@Autowired
	private BlogRepository blogRepository;

	public boolean createBlog(UserVo userVo) {
		String url = "/assets/images/spring-logo.jpg";
		
		BlogVo blogVo = new BlogVo(); 
		blogVo.setId(userVo.getId());
		blogVo.setTitle(userVo.getName() + "'s Blog");
		blogVo.setLogo(url);
		
		return blogRepository.insert(blogVo);
	}
	
	
}
