package com.douzone.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.BlogRepository;

@Service
public class BlogService {
	@Autowired
	private BlogRepository blogRepository;
}
