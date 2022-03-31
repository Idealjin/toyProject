<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<jsp:include page="../common/menubar.jsp"/>

	<div class="outer outer-notice-update">
		<br>
		<h2 align="center">공지 사항 수정</h2>
		<div class="table-area">
			<form action="${ pageContext.servletContext.contextPath }/notice/update" method="post">
				<input type="hidden" name="no" value="${ requestScope.notice.no }">
				<table>
					<tr>
						<td>제목 </td>
						<td><input type="text" size="50" name="title" value="${ requestScope.notice.title }"></td>
					</tr>
					<tr>
						<td>작성자 </td>
						<td><p><c:out value="${ requestScope.notice.writer.nickname }"/></p></td>
					</tr>
					<tr>
						<td>내용 </td>
					</tr>
					<tr>
						<td colspan="2">
							<textarea name="body" cols="60" rows="15" style="resize:none;"><c:out value="${ requestScope.notice.body }"/></textarea>
						</td>
					</tr>
				</table>
				<br>
				<div align="center">
					<button type="button" class="btn btn-pk" onclick="location.href='${ pageContext.servletContext.contextPath }/notice/detail?no=${ requestScope.notice.no }'">돌아가기</button>
					<button type="reset" class="btn btn-or">작성내용 되돌리기</button>
					<button type="submit" class="btn btn-bs">수정하기</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>
