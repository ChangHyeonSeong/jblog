package com.douzone.jblog.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


import com.douzone.jblog.vo.UserVo;

public class AuthInterceptor extends HandlerInterceptorAdapter {
    
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		System.out.println("AuthInterceptor.preHandle(...)  called ");
		//1. handler 종류 확인
		if(handler instanceof HandlerMethod == false) {
			return true;
		}
		   
		//2. casting
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		
		//3. Handler Method의 @Auth 받아오기
		Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
		
		//4. Handler Method에 @Auth가 없으면 Type에 있는 지 확인
		if(auth == null) {
			//과제
			auth = handlerMethod.getMethod().getDeclaringClass().getAnnotation(Auth.class);
		}
		
		//5. Type과 Method에 @Auth가 적용이 안되어 있는 경우
		if(auth == null) {
			return true;
		}
		
		//6. @Auth가 적용이 되어 있기 때문에 인증(Authenfication) 여부 확인
		HttpSession session = request.getSession();
		if(session == null) {
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false;
		}
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false;
		}
		
		
		//7. 현재 로그인 유저가 블로그 주인(현재 페이지 url에 있는 블로그 아이디)과 같은지 확인
		String urlID = request.getRequestURI();
		String[] splitID = urlID.split("/");
		if(authUser.getId().equals(splitID[2]) == false) {
			response.sendRedirect(request.getContextPath() + "/");
			return false;
		}
		

		return true;
	}
	
	

}
