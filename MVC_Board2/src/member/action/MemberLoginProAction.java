package member.action;

import java.io.PrintWriter;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.svc.MemberLoginProService;
import member.vo.MemberBean;

public class MemberLoginProAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("actionForward 입쟝");
		ActionForward forward = null;

		String id = request.getParameter("id");
		String passwd = request.getParameter("passwd");
//		System.out.println(id);
//		System.out.println(passwd);
		MemberBean member = new MemberBean();
		member.setId(id);
		member.setPasswd(passwd);

		MemberLoginProService memberLoginProService = new MemberLoginProService();
		int isLoginSuccess = memberLoginProService.loginMember(member);
		String memStr = "";
		if (isLoginSuccess == 0 || isLoginSuccess == -1) {
			if (isLoginSuccess == 0) {
				memStr="존재하지 않는 아이디 입니다!";
			} else if (isLoginSuccess == -1) {
				memStr="비번이 틀렸습니다!";
			}
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('"+memStr+"')");
			out.println("history.back()");
			out.println("</script>");
		} else if (isLoginSuccess == 1) {
			//로그인 작업 수행 성공 시 세션 처리
			//=>HttpSession 객체를 request객체로부터 전달받아 저장
			HttpSession session = request.getSession();
			//HttpSession객체의 setAttribute()메서드를 호출하여 세션 정보 저장
			session.setAttribute("id", id);
			forward = new ActionForward();
			forward.setRedirect(true);
			forward.setPath("./");//현재 프로젝트의 최상위 위치로 이동(index.jsp)
		}

		return forward;
	}

}
