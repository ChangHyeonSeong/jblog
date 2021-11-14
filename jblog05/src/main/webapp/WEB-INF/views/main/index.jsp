<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
<style type="text/css">

table {
  table-layout: fixed;
  width: 100%;
  border-collapse: collapse;
  border: 3px solid #3879D9;
}

caption { font-weight: bold }

th, td {
  padding: 20px;
}

div#img			{ background-color:#F9F9F9; margin:0 0 0 10px }
div#img  img	{ width:80px }
</style>
</head>
<body>
	<div class="center-content">
		<h1 class="logo" style="background:url(${pageContext.request.contextPath}/assets/images/logo.jpg) no-repeat 0 0">JBlog</h1>
		<c:import url="/WEB-INF/views/includes/menu.jsp" />
		<form class="search-form">
			<fieldset>
				<input type="text" name="keyword" />
				<input type="submit" value="검색" />
			</fieldset>
			<fieldset>
				<input type="radio" name="which" value="blog-title"> <label>블로그 제목</label>
				<input type="radio" name="which" value="tag"> <label>태그</label>
				<input type="radio" name="which" value="blog-user"> <label>블로거</label>
			</fieldset>
		</form>
		
		<table>
            <caption>블로그 목록</caption>
            <tr>
            	<th>프로필</th>
                <th>블로그제목</th>
                <th>ID</th>
            </tr>
            <c:forEach items="${blogList }" var="blogVo">
	            <tr>
	                <td>
	                	<div id="img">
		                	<a href="${pageContext.request.contextPath}/${blogVo.id }">
		               			<img src="${pageContext.request.contextPath}${blogVo.logo }">
		                	</a>
	                	</div>
	                </td>
	                <td>
		                <a href="${pageContext.request.contextPath}/${blogVo.id }">
		                	${blogVo.title }
		                </a>
	                </td>
	                <td>${blogVo.id }</td>
	            </tr>
            </c:forEach>
        </table>
	</div>
</body>
</html>