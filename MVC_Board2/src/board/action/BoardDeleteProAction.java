package board.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.svc.BoardDeleteProService;
import board.vo.ActionForward;

public class BoardDeleteProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("BoardDeleteProAction!");
		
		ActionForward forward = null;
		
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		String board_pass = request.getParameter("board_pass");
		
//		System.out.println(board_num);
//		System.out.println(board_pass);
		
		// 입력받은 패스워드가 게시물의 패스워드와 일치하는지(본인 여부) 확인 작업 요청
		// => BoardDeleteProService 클래스의 isArticleWriter() 메서드를 호출하여
		//    패스워드 일치 여부 판별 결과 리턴
		//    => 파라미터 : 글번호(board_num), 패스워드(board_pass)
		//       리턴타입 : boolean(isArticleWriter)
		BoardDeleteProService boardDeleteProService = new BoardDeleteProService();
		boolean isArticleWriter = 
				boardDeleteProService.isArticleWriter(board_num, board_pass);
		
		// 리턴받은 boolean 타입 결과값을 사용하여 본인 여부 판별 결과 처리
		// => isArticleWriter 가 false 이면 자바스크립트를 사용하여 
		//    "삭제 권한이 없습니다." 출력 후 이전페이지로 이동
		// => 아니면, "패스워드 일치!" 출력
		if(!isArticleWriter) {
			response.setContentType("text/html;charset=UTF-8"); // 문서 타입 설정
			PrintWriter out = response.getWriter(); // PrintWriter 객체 가져오기
			// println() 메서드를 사용하여 자바스크립트를 문자열로 출력
			out.println("<script>"); // 자바스크립트 시작
			out.println("alert('글 삭제 권한이 없습니다!')"); // 오류 메세지 출력
			out.println("history.back()"); // 이전 페이지로 이동
			out.println("</script>"); // 자바스크립트 끝
		} else {
			// BoardDeleteProService 클래스의 deleteArticle() 메서드 호출하여 삭제 작업 요청
			// => 파라미터 : 글번호(board_num), 리턴타입 : boolean(isDeleteSuccess)
			boolean isDeleteSuccess = boardDeleteProService.removeArticle(board_num);
			
			// 삭제 작업 요청 결과 판별
			// isDeleteSuccess 가 false 일 경우 자바스크립트를 사용하여
			// "삭제 실패!" 출력 후 이전페이지로 이동
			// 아니면, BoardList.bo 서블릿 주소 요청(Redirect 방식 포워딩)
			// => 파라미터로 페이지번호(page) 전달
			if(!isDeleteSuccess) {
				response.setContentType("text/html;charset=UTF-8"); // 문서 타입 설정
				PrintWriter out = response.getWriter(); // PrintWriter 객체 가져오기
				// println() 메서드를 사용하여 자바스크립트를 문자열로 출력
				out.println("<script>"); // 자바스크립트 시작
				out.println("alert('글 삭제 실패!')"); // 오류 메세지 출력
				out.println("history.back()"); // 이전 페이지로 이동
				out.println("</script>"); // 자바스크립트 끝
			} else {
				forward = new ActionForward();
				forward.setRedirect(true);
				forward.setPath("BoardList.bo?page=" + request.getParameter("page"));
			}
			
		}
		
		return forward;
	}

}

















