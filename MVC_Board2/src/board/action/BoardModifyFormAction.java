package board.action;

import static board.db.JdbcUtil.*;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import board.svc.BoardDetailService;
import board.vo.ActionForward;
import board.vo.BoardBean;

public class BoardModifyFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("BoardModifyFormAction!");
		ActionForward forward = null;
		
//		.bo?board_num=6
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		
		BoardDetailService boardDetailService = new BoardDetailService();
		BoardBean article = boardDetailService.getArticle(board_num);
		
		
		request.setAttribute("article",article);
		request.setAttribute("page",request.getParameter("page"));
		System.out.println(article.getBoard_num());
		
		
		forward = new ActionForward();
		forward.setPath("/board/qna_board_modify.jsp");
		
		
		
		return forward;
	}

}
