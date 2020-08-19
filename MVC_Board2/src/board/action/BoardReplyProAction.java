package board.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.svc.BoardReplyProService;
import board.vo.ActionForward;
import board.vo.BoardBean;

public class BoardReplyProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		System.out.println("boardReplyProAction접근");
		
		String page = request.getParameter("page");
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		int board_re_ref = Integer.parseInt(request.getParameter("board_re_ref"));
		int board_re_lev = Integer.parseInt(request.getParameter("board_re_lev"));
		int board_re_seq = Integer.parseInt(request.getParameter("board_re_seq"));
		String board_name = request.getParameter("board_name");
		String board_pass = request.getParameter("board_pass");
		String board_subject = request.getParameter("board_subject");
		String board_content = request.getParameter("board_content");
		
//		System.out.println(page);
//		System.out.println(board_name);
//		System.out.println(board_re_ref);
//		System.out.println(board_re_lev);
//		System.out.println(board_re_seq);
//		System.out.println(board_content);
		
		//BoardBean객체(article)에 전달받은 파라미터 저장.
		BoardBean article = new BoardBean();
		article.setBoard_num(board_num);
		article.setBoard_name(board_name);
		article.setBoard_pass(board_pass);
		article.setBoard_subject(board_subject);
		article.setBoard_content(board_content);
		article.setBoard_re_ref(board_re_ref);
		article.setBoard_re_lev(board_re_lev);
		article.setBoard_re_seq(board_re_seq);
		
		
		//BoardReplyProService클래스의 registReplyArticle() 메서드를 호출하여
		//답변글 등록 작업 요청 후 boolean타입으로 처리 결과 받기
		//=>파라미터 : BoardBean, 리턴타입 : boolean(isReplySuccess)
		BoardReplyProService boardReplyProService = new BoardReplyProService();
		boolean isReplySuccess = boardReplyProService.registReplyArticle(article);
		
		if(!isReplySuccess) {
		//isReplySuccess = false -> 답변글등록실패
		//자바스크립트 사용하여 '답변등록실패'메시지출력 후 이전페이지로 이동
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<script>");
		out.println("alert('답변등록실패')");
		out.println("history.back();");
		out.println("</script>");
			
		}else {
		//isReplySuccess = true -> 답변글등록성공
		//BoardList.bo주소로 포워딩(파라미터로 페이지번호(page)를 전달)
			forward = new ActionForward();
			forward.setRedirect(true);//페이지가 바뀌어서 redirect방식 true로 변경
			forward.setPath("BoardList.bo?page="+page);
		}
		
		
		
		return forward;
	}

}
