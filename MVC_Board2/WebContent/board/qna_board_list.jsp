<%@page import="board.vo.BoardBean"%>
<%@page import="board.vo.PageInfo"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
ArrayList<BoardBean> articleList = (ArrayList<BoardBean>)request.getAttribute("articleList");
PageInfo pageInfo = (PageInfo)request.getAttribute("pageInfo");
int listCount = pageInfo.getListCount();
int nowPage = pageInfo.getPage();//내장 객체인 page이름과  중복되므로 nowPage라는 변수 사용
int maxPage = pageInfo.getMaxPage();
int startPage = pageInfo.getStartpage();
int endPage = pageInfo.getEndPage();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MVC게시판</title>
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
width: 450px;
}
#tr_top{
background: orange;
text-align: center;
}
#pageList{
margin: auto;
width: 500px;
text-align: center;
}
#emptyArea{
margin: auto;
width: 500px;
text-align: center;
}

</style>
</head>
<body>
<section id="listForm">
<h2>글목록<a href="BoardWriteForm.bo">게시판글쓰기</a></h2>
<table>
<%
if(articleList != null&& listCount > 0) {
%>
<tr id="tr_top">
<td>번호</td>
<td>제목</td>
<td>작성자</td>
<td>날짜</td>
<td>조회수</td>
</tr>

<%	

for(int i  = 0 ; i<articleList.size();i++){
	%>
	<tr>
	<td><%=articleList.get(i).getBoard_num() %></td>
	<td>
	<%if(articleList.get(i).getBoard_re_lev()!=0){ %>
	<%for(int a=0;a<articleList.get(i).getBoard_re_lev()*2;a++){%>
	&nbsp;
	<%}%>▶
	<%}else {%>▶<%} %>
		<a href="BoardDetail.bo?board_num=<%=articleList.get(i).getBoard_num() %>&page=<%=nowPage%>"><%=articleList.get(i).getBoard_subject() %></a>
	</td>
	<td><%=articleList.get(i).getBoard_name()%></td>
	<td><%=articleList.get(i).getBoard_date()%></td>
	<td><%=articleList.get(i).getBoard_readcount()%></td>
	
	</tr>
		
	<%
}
%>

</table>

</section>
	<section id="writeButton">
		<a href="BoardWriteForm.bo"><input type="button" value="글쓰기"></a>
	</section>
	
	<!-- 페이지 목록 버튼 표시 -->
	<!-- 이전 페이지 또는 다음 페이지가 존재할 경우에만 해당 하이퍼링크 활성화 -->
	<section id="pageList">
	<%if(nowPage <= 1) { %>
			[이전]&nbsp;
	<%} else {%>
			<a href="BoardList.bo?page=<%=nowPage - 1%>">[이전]</a>&nbsp;
	<%} %>
	
	<!-- 존재하는 페이지 번호만 표시(현재 페이지는 번호만 표시하고, 나머지 페이지는 하이퍼링크 활성화) -->
	<%for(int i = startPage; i <= endPage; i++) {
		    if(i == nowPage) {%>
				[<%=i %>]&nbsp;
		<%} else {%>
				<a href="BoardList.bo?page=<%=i %>">[<%=i %>]</a>&nbsp;
		<%} %>
	<%} %>
	
	<%if(nowPage >= maxPage) {%>
			&nbsp;[다음]
	<%} else { %>
			<a href="BoardList.bo?page=<%=nowPage + 1%>">&nbsp;[다음]</a>
	<%} %>
	</section>
<%} else {%>
	<section id="emptyArea">등록된 글이 없습니다.</section>
<%} %>
</body>
</html>
