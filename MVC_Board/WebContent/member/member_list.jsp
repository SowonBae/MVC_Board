<%@page import="member.vo.MemberBean"%>
<%@page import="board.vo.BoardBean"%>
<%@page import="board.vo.PageInfo"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	ArrayList<MemberBean> memberList = (ArrayList<MemberBean>)request.getAttribute("memberList");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원목록</title>
<style type="text/css">
#registForm{
width:500px;
height: 600px;
border : 1px solid red;
margin : auto;
}
h2{
text-align:  center
}

table{
margin: auto;
text-align: center;

border-collapse:collapse;
}
#tr_top{
background: #DACAE0;
}

#pageList{
margin: auto;
width: 500px;
text-align: center;
}
td{
text-align:  center;
border: 1px dashed #CACDE0;
}

</style>
</head>
<body>
<section id="listForm">
<h2>회원목록</h2>
<table>

<tr id="tr_top">
<td width="200">
<a href="MemberList.me?orderTarget=idx&orderType=asc">▲</a>
번호
<a href="MemberList.me?orderTarget=idx&orderType=desc">▼</a>
</td>
<td width="250">
<a href="MemberList.me?orderTarget=name&orderType=asc">▲</a>
이름
<a href="MemberList.me?orderTarget=name&orderType=desc">▼</a>
</td>

<td width="150">아이디</td>
<td width="150">패스워드</td>
<td width="250">이메일</td>
<td width="400">가입일</td>
</tr>
<%
if(memberList != null){
for(MemberBean member : memberList){
%>
<tr>
<td>
<input type="button" name="delete" value="삭제" onclick="location.href='MemberDelete.me?idx=<%=member.getIdx()%>'">
<!-- 	관리자 멤버 관리할떄 삭제하는 방법  -->
	<%=member.getIdx() %></td>
<td><%=member.getName() %></td>
<td><%=member.getId() %></td>
<td><%=member.getPasswd() %></td>
<td><%=member.getEmail() %></td>
<td><%=member.getRegDate() %></td>
</tr>
<%
}
}
%>

</table>
</section>
</body>
</html>
