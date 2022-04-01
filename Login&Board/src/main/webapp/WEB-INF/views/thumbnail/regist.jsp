<jsp:directive.page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<jsp:include page="../common/menubar.jsp"/>
	
	<div class="outer outer-thumbnail-insert">
		<br>
		<h2 align="center">사진게시판 작성</h2>
		<form action="${ pageContext.servletContext.contextPath }/thumbnail/regist" method="post" encType="multipart/form-data">
			<div class="thumbnail-insert-area">
				<table align="center">
					<tr>
						<td width="100px">제목</td>
						<td colspan="3"><input type="text" size="45" name="title"></td>
					</tr>
					<tr>
						<td>대표 이미지</td>
						<td colspan="3">
							<div class="title-img-area" id="titleImgArea">
								<img id="titleImg" width="350" height="200">
							</div>
						</td>
					</tr>
					<tr>
						<td>내용 사진</td>
						<td>
							<div class="content-img-area1" id="contentImgArea1">
								<img id="contentImg1" width="120" height="100">
							</div>
						</td>
						<td>
							<div class="content-img-area2" id="contentImgArea2">
								<img id="contentImg2" width="120" height="100">
							</div>
						</td>
						<td>
							<div class="content-img-area3" id="contentImgArea3">
								<img id="contentImg3" width="120" height="100">
							</div>
						</td>
						
					</tr>
					<tr>
						<td width="100px">사진 메모</td>
						<td colspan="3">
							<textarea name="body" rows="5" cols="50" style="resize:none;"></textarea>
						</td>
					</tr>
				</table>
				<div class="thumbnail-file-area">
					<input type="file" id="thumbnailImg1" name="thumbnailImg1" onchange="loadImg(this,1)">
					<input type="file" id="thumbnailImg2" name="thumbnailImg2" onchange="loadImg(this,2)">
					<input type="file" id="thumbnailImg3" name="thumbnailImg3" onchange="loadImg(this,3)">
					<input type="file" id="thumbnailImg4" name="thumbnailImg4" onchange="loadImg(this,4)">
				</div>
			</div>
			<br>
			<div align="center">
				<button type="button" class="btn btn-pk" onclick="location.href='${ pageContext.servletContext.contextPath }/thumbnail/list'">돌아가기</button>
				<button type="reset" class="btn btn-or">취소하기</button>
				<button type="submit" class="btn btn-bs">작성완료</button>
			</div>
		</form>
			
		<script>
			
			/* 이미지 미리보기 관련 스크립트 */
			const $titleImgArea = document.getElementById("titleImgArea");
			const $contentImgArea1 = document.getElementById("contentImgArea1");
			const $contentImgArea2 = document.getElementById("contentImgArea2");
			const $contentImgArea3 = document.getElementById("contentImgArea3");
			
			$titleImgArea.onclick = function() { 
				document.getElementById("thumbnailImg1").click(); 
			}
			
			$contentImgArea1.onclick = function() {
				document.getElementById("thumbnailImg2").click();
			}
			
			$contentImgArea2.onclick = function() {
				document.getElementById("thumbnailImg3").click();
			}
			
			$contentImgArea3.onclick = function() {
				document.getElementById("thumbnailImg4").click();
			}
			
			/* 이미지 미리보기 관련 함수(File API 활용하기) */
			function loadImg(value, num) {
				
				/* https://developer.mozilla.org/ko/docs/Web/API/FileReader 참고 (MDN 사이트 참고)*/
				if (value.files && value.files[0]) {			// value.files[0]는 파일 이름
					const reader = new FileReader();
				
					/*
						FileReader 객체는 웹 애플리케이션이 비동기적으로 데이터를 읽기 위하여 읽을 파일을 가리키는 File 혹은 Blob 객체를
						이용해 파일의 내용을(혹은 raw data버퍼로) 읽고 사용자의 컴퓨터에 저장하는 것을 가능하게 하는 것	
						Blob(Binary large object) : 파일류의 불변하는 미가공 데이터로 텍스트와 이진 데이터의 형태로 읽을 수 있음
					*/
//					reader.readAsText(value.files[0]);			// 텍스트 파일을 읽을 때 사용 (이미지 파일을 텍스트로 읽어서 글자가 깨짐)			
//					reader.readAsArrayBuffer(value.files[0]);	// 데이터를 일정한 크기로 조금씩(파일을 표현하는 ArrayBuffer) 서버로 보낼 때
//					reader.readAsBinaryString(value.files[0]);	// 이진 데이터를 서버로 보낼 때 사용
					reader.readAsDataURL(value.files[0]);		// Base64방식으로 파일을 FileReader에 전달 (Base64로 인코딩한 것은 브라우저가 원래 데이터로 만들게 됨)
					
					/* load 이벤트의 핸들러로 읽기 동작이 성공적으로 완료 되었을 때마다 발생한다. */
					reader.onload = function(e) {				// load EventListener에 function 등록 (FileReader에서 전달 받은 파일을 다 읽으면 리스너에 등록한 function이 호출 됨)
						switch(num){
						case 1:
							console.log(e.target.result);		// e.target.result는 인코딩한 결과로 img태그의 src로 사용할 수 있음
							document.getElementById("titleImg").src = e.target.result;
							break;
						case 2:
							document.getElementById("contentImg1").src = e.target.result;
							break;
						case 3:
							document.getElementById("contentImg2").src = e.target.result;
							break;
						case 4:
							document.getElementById("contentImg3").src = e.target.result;
							break;
						}
					}
				}
			}
		</script>
	</div>

</body>
</html>