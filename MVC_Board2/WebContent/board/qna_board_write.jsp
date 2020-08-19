<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
#registForm{
width:500px;
height: 600px;
border: 1px solid red;
margin : auto;
}
h2{
text-align: center;
}
table{
margin: auto;
width: 450px;
}
.td_left{
width:150px;
background: orange;
}
.td_right{
width:300px;
background: skyblue;
}

#commandCell{
text-align: center;
}


</style>
</head>
<%
//생성할 파일
//BoardListAction.java			//Action class = Controller
//BoardWriteProAction.java
//qua_board_list.jsp
//qna_board_write.jsp
%>
<body>
<h2>게시판 등록</h2>
<form action ="BoardWritePro.bo" method="post" enctype="multipart/form-data" name="boardform">
<table>
<tr>
<td><label for ="BOARD_NAME">글쓴이</label></td>
<td class="td_left"><input type="text" name="board_name" id="BOARD_NAME" required="required"></td>
</tr>

<tr>
<td><label for ="BOARD_PASS">비밀번호</label></td>
<td class="td_right"><input name="board_pass" type="password" id="BOARD_PASS" required="required"></td>
</tr>

<tr>
<td><label for ="BOARD_SUBJECT">제목</label></td>
<td class="td_right"><input type="text" name="board_subject" id="BOARD_SUBJECT" required="required"></td>
</tr>

<tr>
<td><label for ="BOARD_CONTENT">내용</label></td>
<td><textarea id="BOARD_CONTENT" name="board_content" cols="40" rows="15" required="required"></textarea></td>
</tr>

<tr>
<td><label for ="BOARD_FILE">파일첨부</label></td>
<td class="td_right"><input type="file" name="board_file" id="BOARD_FILE" required="required"></td>
</tr>

<section>
<input type="submit"value="등록">&nbsp;&nbsp;
<input type="reset"value="다시쓰기">
</section>

</table>
</form>
</body>
</html>