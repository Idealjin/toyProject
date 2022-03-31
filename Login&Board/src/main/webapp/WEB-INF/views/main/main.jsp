<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
</body>
</html>