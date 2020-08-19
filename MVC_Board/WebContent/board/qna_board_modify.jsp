<%@page import="board.vo.BoardBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
BoardBean article = (BoardBean)request.getAttribute("article");
String nowPage = (String)request.getAttribute("page");

%>
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
<body>
<section id="registForm">
<h2>게시판글 수정</h2>
<form action ="BoardModifyPro.bo?board_num=<%=article.getBoard_num()%>&page=<%=nowPage %>" method="post" name="boardform">
<table>
<tr>
<td class="td_left"><label for ="BOARD_NAME">글쓴이</label></td>
<td class="td_right"><input type="text" name="board_name" id="BOARD_NAME" value="<%=article.getBoard_name()%>" readonly="readonly"></td>
</tr>

<tr>
<td class="td_left"><label for ="BOARD_PASS">비밀번호</label></td>
<td class="td_right"><input name="board_pass" type="password" id="BOARD_PASS" ></td>
</tr>

<tr>
<td class="td_left"><label for ="BOARD_SUBJECT">제목</label></td>
<td class="td_right"><input type="text" name="board_subject" id="BOARD_SUBJECT" value="<%=article.getBoard_subject()%>"></td>
</tr>

<tr>
<td class="td_left"><label for ="BOARD_CONTENT">내용</label></td>
<td class="td_right"><textarea id="BOARD_CONTENT" name="board_content" cols="40" rows="15"><%=article.getBoard_content() %></textarea></td>
</tr>

<tr>
<td class="td_left"><label for ="BOARD_FILE">파일첨부</label></td>
<td class="td_right"><%=article.getBoard_file()%></td>
</tr>

<section id="commandCell">
<input type="submit"value="[수정]">&nbsp;&nbsp;
<input type="button"value="[뒤로]" onclick="history.back()">
</section>

</table>
</form>
</section>
</body>
</html>