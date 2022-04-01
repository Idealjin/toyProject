<jsp:directive.page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>

	/* 비지니스 로직 성공 alert 메시지 처리 */
	const message = '${ requestScope.message }';
	if(message != null && message !== '') {
		alert(message);
	}
</script>
</head>
<body>
	<jsp:include page="../common/menubar.jsp"/>
	<div class="outer outer-thumbnail-list">
		<br>
		<h2 align="center">사진게시판</h2>
		
		<div class="thumbnail-area" id="thumbnailArea">
		
		<c:forEach var="thumbnail" items="${ requestScope.thumbnailList }">
			<div class="thumb-list" align="center">
				<div>
					<img src="${ pageContext.servletContext.contextPath }${ thumbnail.attachmentList[0].thumbnailPath }" width="200px" height="150px">
				</div>
				<p>No. <label><c:out value="${ thumbnail.no }"/></label> <c:out value="${ thumbnail.title }"/><br>
				조회수 : <c:out value="${ thumbnail.count }"/>
				</p>	
			</div>
		</c:forEach>
		
		</div>
		
		<div class="search-area" align="center">
			<select id="searchCondition" name="searchCondition">
				<option value="writer">작성자</option>
				<option value="title">제목</option>
				<option value="content">내용</option>
			</select>
			<input type="search">
			<button class="btn btn-bs" type="submit">검색하기</button>
			
			<!-- 로그인 한 경우에만 버튼이 보여짐 -->
			<c:if test="${ !empty sessionScope.loginMember }">
				<button class="btn btn-or" id="writeThumbnail">작성하기</button>
			</c:if>
		</div>
		
	</div>
	
	<script>
		$("#thumbnailArea > div").click(function(){
			const no = $(this).find("label").text();
			console.log(no);
			location.href = "${ pageContext.servletContext.contextPath }/thumbnail/detail?no=" + no;
		});
	</script>
</body>
</html>
