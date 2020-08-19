package board.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.svc.BoardModifyProService;
import board.vo.ActionForward;
import board.vo.BoardBean;

public class BoardModifyProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("BoardModifyProAction!!");
		ActionForward forward = null;
		
		boolean isModifySuccess = false; //수정성공여부를 저장할 변수
		
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		
		String page = request.getParameter("page");
		
		System.out.println(board_num);
		System.out.println(page);
		//글 수정 작업 요청을 위한 BoardModifyProService클래스의  modifyArticle()메서드 호출 
		//->단, 먼저 본인 여부를 확인하여 본인일 경우에만 수정 가능하도록 작업 수행   (isArticleWriter()메서드를 호출하여 패스워드 일치 여부 판별)
		//BoardModifyProService인스턴스 생성
		BoardModifyProService boardModifyProService = new BoardModifyProService();
		//isArticleWriter()메서드를 호출하여 패스워드 일치여부 판별
		//파라미터 : 글번호(board_num), 비밀번호
		//리턴타입 : booelan(isRightUser)
		System.out.println( request.getParameter("board_pass"));
		boolean isRightUser = boardModifyProService.isArticleWriter(board_num, request.getParameter("board_pass"));
		
		//패스워드가 일치하지 않는 경우
		if (!isRightUser) {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			out.println("<script>");
			out.println("alert('글 수정 권한이 없습니다!!')");
			out.println("history.back()");
			out.println("</script>");
		}else {
//			System.out.println("패스워드 일치");
			//수정 폼에서 입력된 데이터를 BoardBean객체(article)에 저장 후
			//BoardModifyProService클래스의 modifyArticle()메서드를 호출하여
			//게시물 수정(Update)작업 요청
			//=>파라미터 : BoardBean(article)	//리턴타입 : boolean isModifySuccess
			BoardBean article = new BoardBean();
			article.setBoard_num(board_num);
			article.setBoard_subject((request.getParameter("board_subject")));
			article.setBoard_content(request.getParameter("board_content"));
			isModifySuccess = boardModifyProService.modifyArticle(article);
			
			
			//수정작업 결과가 실패했을 경우 자바 스크립트 사용하여 '글 수정 실패' 출력 후 이전페이지로 이동
			if (!isModifySuccess) {
				response.setContentType("text/html;charset=UTF-8");
				PrintWriter out = response.getWriter();
				
				out.println("<script>");
				out.println("alert('글 수정 실패!!')");
				out.println("history.back()");
				out.println("</script>");
			}else {
				//수정 작업이 성공했을 경우 게시물 상세 내용 요청을 위해 
				//BoardDetail.bo 주소로 포워딩(Redirect방식)
				//=> 파라미터로 게시물 번호와 페이지 전달
				forward = new ActionForward();
				forward.setRedirect(true);
				forward.setPath("BoardDetail.bo?board_num="+board_num+"&page="+page);
			}
			
		}
		
		return forward;
	}

}
