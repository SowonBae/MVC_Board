package board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.vo.ActionForward;

public interface Action {
	/*
	 * XXXAction클래스를 통해 각 요청에 대한  작업을 처리하기 위해서
	 * 클라이언트로부터의 요청이 들어올 떄 서로 다른 클래스에 대한 요청이므로 
	 * 각 Action클래스들이 구현해야하는 execute()메서드를
	 * 공통된 형태로 정의하기 위해서 다형성을 활용할 수 있도록 Action 인터페이스 설계
	 * 이때, 각 요청을 받아들일 execute() 메서드를 통해 요청(request)와 응답(response)객체를 전달받고
	 * , 포워딩 정보를 저장하는 ActionForward객체를 리턴하도록 정의
	 * =>각 XXXAction클래스에서는 Action인터페이스를 상속받아 구현하도록 강제.
	 */
	//책 574p
	ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
	//↑default라서 앞에 뭐 붙은거 없음.
	//다하면 ActionForward라는 객체를 리턴할거다.
	

}
