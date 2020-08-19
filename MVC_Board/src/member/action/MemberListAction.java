package member.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.svc.MemberListService;
import member.vo.MemberBean;

public class MemberListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("memberListAction접근");
		ActionForward forward = null;
		
		
		//MemberListService클래스의 getMemberList() 메서드를 호출하여
		//전체 회원 목록 조회 요청 작업을 수행
		//->파라미터 : 없음, 리턴타입 : ArrayList<MemberBean>
		MemberListService service = new MemberListService();
		ArrayList<MemberBean> memberList = null;
		
			//파라미터로 전달되는 항목이 하나라도 존재하면
			//getMemberList()메서드에 파라미터를 전달하여 SELECT구문의 조건을 설정하고
			//파라미터가 없을 경우 SELECT구문의 조건 설정 없이 기본 조회 수행
			String orderTarget = request.getParameter("orderTarget");
			String orderType = request.getParameter("orderType");
			System.out.println(orderTarget);
			System.out.println(orderType);
			if(orderTarget==null) {
				memberList = service.getMemberList();
			}else {
				memberList = service.getMemberList(orderTarget, orderType);
			}
		
		
		
		
		System.out.println(memberList);
		//조회된 전체 목록을 ArrayList<MemberBean>객체로 전달받아
		//request객체에 저장(memberList)
		request.setAttribute("memberList", memberList);
		
		forward = new ActionForward();
		forward.setPath("/member/member_list.jsp");
		
		
		return forward;
	}

}
