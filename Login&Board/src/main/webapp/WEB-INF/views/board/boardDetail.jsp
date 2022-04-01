<jsp:directive.page language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<jsp:include page="../common/menubar.jsp" />

	<div class="outer outer-board-detail">
		<br>
		<h2 align="center">게시판 내용</h2>
		<div class="table-area">
			<table>
				<tr>
					<td>분야</td>
					<td><p>
							<c:out value="${ requestScope.board.category.name }" />
						</p></td>
				</tr>
				<tr>
					<td>제목</td>
					<td colspan="3"><p>
							<c:out value="${ requestScope.board.title }" />
						</p></td>
				</tr>
				<tr>
					<td>내용</td>
					<td colspan="3"><textarea name="body" cols="60" rows="15"
							style="resize: none;" readonly><c:out
								value="${ requestScope.board.body }" /></textarea></td>
				</tr>
			</table>
			<br>
			<div align="center">
				<button type="button" class="btn btn-or" onclick="location.href='${ pageContext.servletContext.contextPath }/board/list'">돌아가기</button>
			</div>

			<!-- 아래 태그들은 댓글 관련 내용 -->
			<!-- 댓글 작성용 table -->
			<table id="replyWrite">
				<input type="hidden" id="boardNo" value="${ requestScope.board.no }">
				<tr>
					<td>댓글</td>
					<td><textarea cols="40" rows="3" id="replyBody"
							style="resize: none;"></textarea></td>
					<td>
						<button type="button" id="registReply">작성하기</button>
					</td>
				</tr>
			</table>
			
			<!-- 댓글 내용 출력용 table -->
			<table id="replyResult">
				<c:if test="${ not empty requestScope.replyList }">
					<c:forEach var="reply" items="${requestScope.replyList}"
						varStatus="st">
						<tr>
							<input type="hidden" id="${ reply.no }" value="${ reply.no }">
							<td>${ reply.body }</td>
							<td>${ reply.writer.nickname }</td>
							<td>${ reply.createdDate }</td>
							<td>
								<c:if test="${ reply.writer.no eq sessionScope.loginMember.no }">
									<button type="button" onclick="removeReply(${ reply.no })">댓글삭제</button>
								</c:if>
							</td>
						</tr>
					</c:forEach>

				</c:if>
			</table>
		</div>
	</div>
	<script>
	
		/* 댓글 작성 이벤트 처리 */
		if(document.getElementById("registReply")) {
			const $registReply = document.getElementById("registReply");
			$registReply.onclick = function() {
				let boardNo = document.getElementById("boardNo").value;
				let replyBody = document.getElementById("replyBody").value;
				
				if(replyBody.trim() == ""){
					$("#replyBody").val("");
					alert('댓글을 입력해 주십시오');
				} else {
					$.ajax({
						url:"/spring/board/registReply",
						type:"post",
						data:{refBoardNo:boardNo, body:replyBody},
						success:function(data){
							console.table(data);
							
							$("#replyBody").val("");
							
							const $table = $("#replyResult");
							$table.html("");
							
							for(var index in data){
								$tr = $("<tr>");
								$bodyTd = $("<td>").text(data[index].body);
								$writerTd = $("<td>").text(data[index].writer.nickname);
								$createDateTd = $("<td>").text(data[index].createdDate);
								//로그인멤버 넘버랑 작성자 넘버랑 일치하면 댓글삭제버튼 보이게
								if(data[index].writer.no == ${sessionScope.loginMember.no}){
									$removeTd = $("<td>").append("<button type='button' onclick='removeReply(" + data[index].no + ")'>댓글삭제</button>");
								} else {
									$removeTd = $("<td>");
								}
								
								$tr.append("<input type='hidden' id=" + ${ data[index].no } + " value='" + data[index].no + "'>");
								$tr.append($bodyTd);
								$tr.append($writerTd);
								$tr.append($createDateTd);
								$tr.append($removeTd);
								
								$table.append($tr);
							}
						}, error:function(data){
							console.log(data);
						}
					});
				}
			}
		}
		
		/* 댓글 삭제 이벤트 처리 함수*/
		function removeReply(replyNo){
			let boardNo = document.getElementById("boardNo").value;
			$.ajax({
				url:"/spring/board/removeReply",
				type:"post",
				data:{refBoardNo:boardNo, no:replyNo},
				success:function(data){
					console.table(data);
					const $table = $("#replyResult");
					$table.html("");
					
					for(var index in data){
						$tr = $("<tr>");
						$bodyTd = $("<td>").text(data[index].body);
						$writerTd = $("<td>").text(data[index].writer.nickname);
						$createDateTd = $("<td>").text(data[index].createdDate);
						if(data[index].writer.no == ${sessionScope.loginMember.no}){
							$removeTd = $("<td>").append("<button type='button' onclick='removeReply(" + data[index].no + ")'>댓글삭제</button>");
						} else {
							$removeTd = $("<td>");
						}
						
						$tr.append("<input type='hidden' id=" + ${ data[index].no } + " value='" + data[index].no + "'>");
						$tr.append($bodyTd);
						$tr.append($writerTd);
						$tr.append($createDateTd);
						$tr.append($removeTd);
						
						$table.append($tr);
					}
				}, error:function(data){
					console.log(data);
				}
			});
		}
	</script>
</body>
</html>
