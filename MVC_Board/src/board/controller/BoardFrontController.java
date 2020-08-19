package board.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.action.Action;
import board.action.BoardDeleteProAction;
import board.action.BoardDetailAction;
import board.action.BoardListAction;
import board.action.BoardModifyFormAction;
import board.action.BoardModifyProAction;
import board.action.BoardReplyFormAction;
import board.action.BoardReplyProAction;
import board.action.BoardWriteProAction;
import board.vo.ActionForward;

// URL 에 요청된 서블릿 주소가 XXX.bo 로 끝날 경우
// 해당 주소를 서블릿 클래스인 BoardFrontController 클래스로 연결(매핑)
@WebServlet("*.bo")
public class BoardFrontController extends HttpServlet {
	// 서블릿 클래스 정의 시 HttpServlet 클래스를 상속받아 정의함.
	// GET 방식 요청, POST 방식 요청에 해당하는 doGet(), doPost() 메서드 오버라이딩함.
	// => 두 방식을 공통으로 처리하기 위해 doProcess() 메서드를 별도로 정의하여 호출함.
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("BoardFrontController");
		
		// POST 방식 요청에 대한 한글 처리
		request.setCharacterEncoding("UTF-8");
		
		// 요청 URL 에 대한 판별(서블릿주소가져오기)
		//->예전방식
		// 1. 요청 URL 중 서버 주소와 포트번호를 제외한 프로젝트명/서블릿주소 부분 추출
//		String requestURI = request.getRequestURI();
//		System.out.println("Requsted URI : " + requestURI); 
		// 출력내용 : /MVC_Board/BoardFrontController.bo
		
		// 2. 요청 URL 중 프로젝트명 부분 추출
//		String contextPath = request.getContextPath();
//		System.out.println("Context Path : " + contextPath);
		// 출력내용 : /MVC_Board
		// 3. 1번과 2번 문자열을 사용하여 서블릿주소 부분 추출
		// => 프로젝트명 문자열 길이에 해당하는 부분부터 마지막까지 추출
//		String command = requestURI.substring(contextPath.length());
//		System.out.println("Command : " + command);		
		// ===============================================================
		// 위의 1 ~ 3번 과정을 결합한 메서드 : getServletPath()
		String command = request.getServletPath();
		System.out.println("Command : " + command);	
		
		
		//여러객체(XXXAction)를 동일한 타입으로 다루기 위한 변수 선언
		Action action = null;
		ActionForward forward = null;
		
		// 추출된 서블릿 주소에 따라 각각 다른 작업을 수행하도록 제어
		if(command.equals("/BoardWriteForm.bo")) {
			//글쓰기 폼을 위한 View페이지(JSP) 요청은 별도의 비즈니스 로직(Model) 없이 JSP페이지로 바로 연결
			//->이때, JSP페이지의 URL이 노출되지 않아야 하며 또한, request객체가 유지되어야 하므로 
			//Dispatch방식으로 포워딩
			//따라서, ActionForward객체를 생성하고 URL전달 및 포워딩 타입을 false로 지정할것임.
			//-> forwad = new ActionForward()
			forward = new ActionForward();
			forward.setPath("/board/qna_board_write.jsp");
//			forward.setRedirect(false);//생략가능하다. 기본값이 false니까!!!!그래서 주석처리할거에요
			
		}else if(command.equals("/BoardWritePro.bo")) {
//			System.out.println("BoardWritePro.bo 감지!");
			//글쓰기 비즈니스로직을 위한 Action클래스 인스턴스 생성.
			//->BoardWriteProAction클래스 인스턴스 생성 및 공통메서드 execute()호출
			//->로직 수행 후 ActionForward객체를 리턴받아 포워딩 작업 수행.
			action = new BoardWriteProAction();//이게이동에 대한 부분인가보다
			try {
				forward = action.execute(request, response);//execute호출을 forward로 받아온다.
				//ActionForward의 execute로 감.
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(command.equals("/BoardList.bo")){
			//글목록 요청 비즈니스 로직을 위한 Action클래스 인스턴스 생성
			// => BoardListAction클래스가 필요함.
			// BoardListAction클래스의 인스턴스를 생성 및 공통 메서드 execute()메서드 호출할것임.
			// 로직 수행 후 ActionForward객체를 리턴받아 포워딩 작업 수행.
			action = new BoardListAction();
			try {
				forward = action.execute(request,response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}else if(command.equals("/BoardDetail.bo")) {
			action = new BoardDetailAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(command.equals("/BoardModifyForm.bo")) {
			action = new BoardModifyFormAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(command.equals("/BoardModifyPro.bo")) {
			action = new BoardModifyProAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(command.equals("/BoardReplyForm.bo")) {
			action = new BoardReplyFormAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(command.equals("/BoardReplyPro.bo")) {
			action = new BoardReplyProAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(command.equals("/BoardDeleteForm.bo")) {
			// 글삭제 폼을 위한 View 페이지(JSP) 요청은
			// 별도의 비즈니스 로직(Model) 없이 JSP 페이지로 바로 연결
			// => 이 때, JSP 페이지의 URL 이 노출되지 않아야하며
			//    또한, request 객체가 유지되어야 하므로 Dispatch 방식으로 포워딩
			// 따라서, ActionForward 객체를 생성하고 URL 전달 및 포워딩 타입 false 로 지정
			forward = new ActionForward();
			forward.setPath("/board/qna_board_delete.jsp");
//			forward.setRedirect(false); // 생략 가능(기본값 false)
		} else if(command.equals("/BoardDeletePro.bo")) {
			// 글 삭제 요청 비즈니스 로직을 위한 Action 클래스 인스턴스 생성
			// => BoardDeletePro 클래스 인스턴스 생성 및 공통 메서드 execute() 호출
			// => 로직 수행 후 ActionForward 객체를 리턴받아 포워딩 작업 수행
			action = new BoardDeleteProAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if(command.equals("/BoardFileDown.bo")) {
			// 파일 다운로드 페이지를 위한 View 페이지(JSP) 요청
			forward = new ActionForward();
			forward.setPath("/board/file_down.jsp");
//			forward.setRedirect(false); // 생략 가능(기본값 false)
		} 
			
		
		//---------------------------------------------------------
		//Redirect방식과 Dispatch방식에 대한 포워딩(이동)을 처리하기 위한 영역
		//1.ActionForward객체가 null이 아닐 때만 포워딩 작업 수행.
		if(forward!=null) {
			//2. ActionForward객체가 null이 아닐 때
			// 	 Redirect방식인지 여부 판별!
			if(forward.isRedirect()) {//redirect방식일 경우!
				//response객체의 sendRedirect()메서드를 호출하여
				//ActionForward객체에 저장되어 있는 URL정보 전달
				response.sendRedirect(forward.getPath());
			}else { //dispatch방식일 경우!
				//1)RequestDispatcher객체를 리턴받기 위해 request객체의 getRequestDispatcher()메서드를 호출함.
				  //이 때, 파라미터에 ActionForward객체에 저장되어 있는 URL정보 전달
				RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());
				
				//2)만들어진 RequestDispatcher객체의 forward()메서드를 호출하여
				//	전달된 URL로 포워딩 시킴. 
				//	=> 이 때, 파라미터로 request객체와 response객체 전달할것임.
				dispatcher.forward(request, response);
			}
			
		}
		
		
		
		//---------------------------------------------------------
		
	}
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// GET 방식 요청에 의해 doGet() 메서드가 호출되면 doProcess() 메서드를 호출하여
		// request 객체와 response 객체를 파라미터로 전달
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// POST 방식 요청에 의해 doPost() 메서드가 호출되면 doProcess() 메서드를 호출하여
		// request 객체와 response 객체를 파라미터로 전달
		doProcess(request, response);
	}
	

}



















