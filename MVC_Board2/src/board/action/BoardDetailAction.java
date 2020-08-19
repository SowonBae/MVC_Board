package board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.svc.BoardDetailService;
import board.vo.ActionForward;
import board.vo.BoardBean;

public class BoardDetailAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("boardDetailAction!!");
		ActionForward forward = null;
		
		//게시물 1개의 정보를 조회하기 위한 준비작업 수행(주소창에 파라미터 가져와야함)
		//=>파라미터로 전달 된 board_num과 현재페이지(page)를 읽어와서 
		// 	글번호를 BoardDetailService클래스에 전달.
		 int board_num = Integer.parseInt(request.getParameter("board_num"));
		 String page = request.getParameter("page");
		 
		 //BoardDetailService클래스의  인스턴스생성 후 
		 //getArticle()메서드 호출하여 게시물 상세내역 조회작업 요청
		 //파라미터 : board_num, 리턴타입 : BoardBean() - article
		 
		 BoardDetailService boardDetailService = new BoardDetailService();
		 BoardBean article =  boardDetailService.getArticle(board_num);
		 
		 //request객체에 BoardBean객체와 page를 저장
		 request.setAttribute("article", article);
		 request.setAttribute("page", page);
		 
		 //ActionForward 객체 생성 및 qna_board_view.jsp로 포워딩
		 //=>dispatch방식으로 포워딩
		 forward = new ActionForward();
		 forward.setPath("/board/qna_board_view.jsp");
		
		return forward;
	}
}
