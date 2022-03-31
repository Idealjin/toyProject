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
	
	<!-- 회원 정보 수정 양식 outer 영역 -->
	<div class="outer outer-member-detail">
		<br>
		<h2 align="center">회원 정보 수정</h2>
		
		<!-- 회원 정보 수정 폼 -->
		<form id="joinForm" action="${ pageContext.servletContext.contextPath }/member/update" method="post">
			<table align="center">
				<tr>
					<td width="200px">* 아이디 </td>
					<td><p><c:out value="${ sessionScope.loginMember.id }"/></p></td>
				</tr>
				<tr>
					<td>* 비밀번호</td>
					<td><input type="password" maxlength="13" name="pwd" required></td>
					<td></td>
				</tr>
				<tr>
					<td>* 비밀번호확인</td>
					<td><input type="password" maxlength="13" name="pwd2"></td>
					<td></td>
				</tr>
				<tr>
					<td>* 닉네임</td>
					<td><input type="text" maxlength="5" name="nickname" required></td>
					<td></td>
				</tr>
				<tr>
					<td>연락처 </td>
					<td><input type="tel" name="phone"></td>
					<td></td>
				</tr>
				<tr>
					<td>이메일 </td>
					<td><input type="email" name="email"></td>
					<td></td>
				</tr>
				<tr>
					<td>우편번호</td>
					<td><input type="text" name="zipCode" id="zipCode" readonly></td>
					<td><input type="button" value="검색" class="btn btn-yg" id="searchZipCode"></td>
				</tr>
				<tr>
					<td>주소</td>
					<td><input type="text" name="address1" id="address1" readonly></td>
					<td></td>
				</tr>
				<tr>
					<td>상세주소</td>
					<td><input type="text" name="address2" id="address2"></td>
					<td></td>
				</tr>
			</table>
			<br>
			<div class="btns" align="center">
				<button type="button" class="btn btn-pk" onclick="location.href='${ pageContext.servletContext.contextPath }'">돌아가기</button>
				<input type="submit" value="수정하기" class="btn btn-or">
				<input type="button" value="탈퇴하기" class="btn btn-bs" onclick="location.href='${ pageContext.servletContext.contextPath }/member/delete?id=${ sessionScope.loginMember.id }'">
			</div>
		</form>
	</div>	<!-- outer end -->
	
	<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	<script>
		const $searchZipCode = document.getElementById("searchZipCode");
		const $goMain = document.getElementById("goMain");
		
		$searchZipCode.onclick = function() {
		
			new daum.Postcode({
				oncomplete: function(data){
					document.getElementById("zipCode").value = data.zonecode;
					document.getElementById("address1").value = data.address;
					document.getElementById("address2").focus();
				}
			}).open();
		}
		
		$goMain.onclick = function() {
			location.href = "${ pageContext.servletContext.contextPath }";
		}
	</script>
</body>
</html>
