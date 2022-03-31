<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<jsp:include page="../common/menubar.jsp"/>
	
	<!-- 회원 가입 양식 outer 영역 -->
	<div class="outer outer-member-insert">
		<br>
		<h2 align="center">회원 가입</h2>
		
		<!-- 회원 가입 폼 -->
		<form id="joinForm" action="${ pageContext.servletContext.contextPath }/member/regist" method="post">
			<table align="center">
				<tr>
					<td width="200px">* 아이디 </td>
					<td><input type="text" maxlength="13" name="id" id="memberId" required></td>
					<td width="100px"><input type="button" value="중복확인" class="btn btn-bs" id="duplicationCheck"></td>
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
				<input type="submit" value="가입하기" class="btn btn-or">
			</div>
		</form>
	</div>	<!-- outer end -->
	
	<!-- 다음 우편번호 api -->
	<!-- key 발급 없이 무제한으로 이용이 가능하다. -->
	<!-- 아래 스크립트를 불러온 후 사용해야 한다. -->
	<!-- 참고 링크 : http://postcode.map.daum.net/guide -->
	<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	<script>
		const $searchZipCode = document.getElementById("searchZipCode");
		const $goMain = document.getElementById("goMain");
		
		$searchZipCode.onclick = function() {
		
			/* 다음 우편번호 검색 창을 오픈하면서 동작할 콜백 메소드를 포함한 객체를 매개변수로 전달한다. */
			new daum.Postcode({
				oncomplete: function(data){
					
					/* 팝업에서 검색결과 항목을 클릭했을 시 실행할 코드를 작성하는 부분 */
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
