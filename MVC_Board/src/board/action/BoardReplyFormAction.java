package board.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.svc.BoardDetailService;
import board.vo.ActionForward;
import board.vo.BoardBean;

public class BoardReplyFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		System.out.println("boardModifyFormAction");
		
		//답변글 작성을 위한 폼 생성을 위해 기존 게시물의 상세 내용을 가져와서
		//파라미터로 전달받은 board_nam, page가져오기
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		String page = request.getParameter("page");
		
		BoardDetailService boardDetailService = new BoardDetailService();
		
		BoardBean article = boardDetailService.getArticle(board_num);
		
		request.setAttribute("article",article);
		request.setAttribute("page",page);
		
		forward = new ActionForward();
		forward.setPath("/board/qna_board_reply.jsp");

		return forward;
	}

}
