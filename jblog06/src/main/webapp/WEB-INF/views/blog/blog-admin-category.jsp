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
button.btn-delete {
	  
    width: 18px;
    height: 18px;
   
 	 
}
</style>
</head>
<script src="${pageContext.request.contextPath }/assets/js/jquery/jquery-3.6.0.js"></script>
<script>
$(function(){
	//$(".form-delete").submit(function(event) {
	$(".btn-delete").click(function(event) {
		//event.preventDefault();
		//var _this = this;
		var id = "${authUser.id }";
		//var no = this.elements[0].value; //카테고리 넘버 가져오기
		console.log(this);
		var tr = $(this).parent().parent();
		var no = $(this).val(); //카테고리 넘버 가져오기
		console.log("no : " +  no );
		var url = "${pageContext.request.contextPath }/blog/api/check?id=" + id + "&no=" + no;
		if(id == '') {
			return;
		}
		
		console.log(id);
		$.ajax({
			url: url,
			type: "get",
			dataType: 'json',
			error: function(xhr, status, e) {
				console.log(status, e);
			},
			success: function(response) {
				if(response.result != "success") {
					console.error("response message : " + response.message);
					return;
				}
				
				if(response.data['existPost']) {
					alert("해당 카테고리에 글이 존재합니다.");
					return;
				}
				
				if(response.data['lastCategory']) {
					alert("카테고리는 1개 이상이어야 합니다.");
					return;
				}
				
				//삭제가 가능한경우 
				$.ajax({
					url: "${pageContext.request.contextPath }/blog/api/category/" + no ,
					type: 'delete',
					dataType: 'json',
					data: 'id=' + id,
					success: function(response) {
						console.log(response);
						
						// 삭제가 안된 경우						
						if(response.data == false) {
							console.log("delete fail : " + response.data );
							return;
						}
						
						// 삭제가 된 경우
						tr.remove();
						
						var tdIndex = $(".td-index");
						console.log(tdIndex);
						for(var i =tdIndex.length ; i > 0 ;i--){
							tdIndex[i].innerText = i;
						}
					}
				});
				//_this.submit();
			}
		});		
	});	
});
</script>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/blog-header.jsp" />
		<div id="wrapper">
			<div id="content" class="full-screen">
				<c:import url="/WEB-INF/views/includes/blog-admin-menu.jsp" />
		      	<table class="admin-cat">
		      		<tr>
		      			<th>번호</th>
		      			<th>카테고리명</th>
		      			<th>포스트 수</th>
		      			<th>설명</th>
		      			<th>삭제</th>      			
		      		</tr>
					<c:forEach items='${list }' var='categoryVo' varStatus='status'>
						<tr>
							<td class='td-index'>${list.size() - status.index }</td>
							<td>${categoryVo.name }</td>
							<td>${categoryVo.postCount }</td>
							<td>${categoryVo.desc }</td>
							<td>
								<!-- <form class="form-delete" method="post" 
								      action="${pageContext.request.contextPath}/${authUser.id }/admin/category/${categoryVo.no }">
									<input type="hidden" name="no" value="${categoryVo.no }" >
									<input type="hidden" name="_method" value="DELETE" >
									<input class="btn-delete" type="submit" value="삭제">	
								</form> -->
								<button class="btn-delete" value="${categoryVo.no }">
									<img src="${pageContext.request.contextPath}/assets/images/delete.jpg">
								</button>
							</td>
						</tr>
					</c:forEach>					  
				</table>
      	
      			<h4 class="n-c">새로운 카테고리 추가</h4>
      			<form method="post" action="${pageContext.request.contextPath}/${authUser.id }/admin/category" >
			      	<table id="admin-cat-add">
			      		<tr>
			      			<td class="t">카테고리명</td>
			      			<td><input type="text" name="name"></td>
			      		</tr>
			      		<tr>
			      			<td class="t">설명</td>
			      			<td><input type="text" name="desc"></td>
			      		</tr>
			      		<tr>
			      			<td class="s">&nbsp;</td>
			      			<td><input type="submit" value="카테고리 추가"></td>
			      		</tr>      		      		
			      	</table>
		      	</form>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/blog-footer.jsp" />
	</div>
</body>
</html>