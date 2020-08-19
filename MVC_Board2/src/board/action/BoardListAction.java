package board.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.svc.BoardListService;
import board.vo.ActionForward;
import board.vo.BoardBean;
import board.vo.PageInfo;

public class BoardListAction implements Action {

	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		
//		System.out.println("BoardListAction호출");
		
		//1. BoardListService클래스를 통해 게시물 전체 목록 조회하여
		//  조회된 결과를 request객체에 저장 후 qna_board_list.jsp페이지로 포워딩 할 것임. <=dispatch방식(jsp노출안시킬거)
		ActionForward forward = null;

		//2.페이징 처리를 위한 변수선언.
		int page = 1;	//현재 페이지번호를 저장할 변수
		int limit = 10; //한 페이지에 표시할 게시물 수
		
		//3. 전달된 리퀘스트 객체의 파라미터 중 'page'파라미터가 null이 아닐 경우
		//해당 파라미터값을 page 변수에 저장
		
		if(request.getParameter("page")!=null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		
		//4. BoardListService클래스의 인스턴스 생성
		BoardListService boardListService = new BoardListService();
		
		//5. 전체 게시물 수 조회를 위해 BoardListService객체의 getListCount()메서드를 호출
		//=> 파라미터 없음. 리턴타입 : int(listCount)
		int listCount = boardListService.getListCount(); //11. boardListService로 이동
		
		//6. 전체 게시물 목록 조회를 위해 boardListService객체의 getArticleList()메서드를 호출할것임.
		//파라미터 : 현재 페이지번호(page), 페이지당 게시물 수(limit)
		//리턴타입 : ArrayList<BoardBean> 이름 : articleList
		ArrayList<BoardBean> articleList = boardListService.getArticleList(page, limit);
		
		
		//7. 페이지 목록 처리를 위한 계산작업.
		// 7-1) 전체 페이지 수 계산(총 게시물 수/ 페이지 당 게시물 수 + 0.95(반올림하는것임))
		int maxPage = (int)((double)listCount / limit + 0.95); //소수점으로 나와야 하니 double. 결과값이 int여야하니 int
		
		// 7-2) 현재 페이지에서 보여줄 시작 페이지 번호(1, 11, 21...)
		int startPage = ((int)((double)page / 10 + 0.9)-1) * 10 + 1 ;
		
		// 7-3) 현재 페이지에서 보여줄 끝 페이지 번호(10, 20, 30..)
		int endPage = startPage + 10 - 1;
		
		// 7-4) 마지막 페이지가 현재 페이지에서 표시할 최대 페이지 수(전체 페이지 수)보다 클 경우
		//		마지막 페이지 번호를 전체 페이지 번호로 교체
		if(endPage>maxPage) {
			endPage = maxPage;
		}
		
		//8. 계산된 페이지 정보들을 PageInfo객체에 저장
		PageInfo pageInfo = new PageInfo(page, maxPage, startPage, endPage, listCount);
		
		//9. 페이지 정보 객체(PageInfO)와 게시물 목록 저장된 객체(ArrayList<BoardBean>)를 
		//	request 객체의 setAttribute()메서드로 저장
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("articleList", articleList);
		
		//10.ActionForward객체 생성 후  board폴더의 qna_board_list.jsp 페이지로 포워딩.
		//=> 서블릿 주소를 유지하고 request객체를 유지해야하므로 dispatch 방식으로 포워딩 할 것임
		forward = new ActionForward();
//		forward.setRedirect(false);//기본값이므로 생략 가능하다
		forward.setPath("/board/qna_board_list.jsp");
		return forward;
	}

}







































