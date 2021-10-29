package com.douzone.jblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.douzone.jblog.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	
	public String login() {
		
		return "";
	}
	
}
