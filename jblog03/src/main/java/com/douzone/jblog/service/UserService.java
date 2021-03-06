package com.douzone.jblog.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.douzone.jblog.repository.UserRepository;
import com.douzone.jblog.vo.UserVo;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	public boolean join(UserVo vo) {
		return userRepository.insert(vo);
	}

	public UserVo getUser(String id) {
		return userRepository.findById(id);
	}

	public UserVo getUser(String id, String password) {
		return userRepository.findByIdAndPassword(id,password);
	}


	
}
