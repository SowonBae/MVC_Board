<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%
String sid = (String)session.getAttribute("id");
%>
<style type="text/css">
#main{
text-align: center;
}
header{
	text-align: right;
}
header a:link{
	text-decoration: none;
	color: black;
}
header a:visited{
	text-decoration: none;
	color: black;
}
header a:active{
	text-decoration: none;
	color: black;
}

</style>
</head>
<body>
<header>
<%
if(sid==null){//sid.length라고 했을 때 null값이 들어오면 null.length가 안되니까 nullpointException뜸
%>
<a href ="MemberLoginForm.me">로그인</a> | <a href ="MemberJoinForm.me">회원가입</a>
<%}else{%>
	<%=sid %>님 | <a href ="MemberLogout.me">로그아웃</a>
	<% if(sid.equals("admin")){ %>
	 | <a href="MemberList.me">관리자 페이지</a><%
	}
}
%>
</header>
<div id="main">
<h1>Main</h1>
<!-- <a href="Controller.bo">BoardFrontController Test</a> -->
<!-- /Controller.bo라고 쓰면 맨 꼭대기로 올라가기때문에 Controller.bo 라고 써주면 BoardFrontController.java에 써둔 어노테이션 찾음. -->
<h1><a href="BoardWriteForm.bo">글쓰기</a></h1>
<h1><a href="BoardList.bo">글목록</a></h1>
</div>
</body>
</html>	