package member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MemberLogoutAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		//HttpSession객체 가져와서 invalidate() 메서드 호출하여 세션 제거
		HttpSession session = request.getSession();
//정해진걸 지우고자 할 때에는 removeAttribute
//session.removeAttribute(id);
		session.invalidate();
		
		//index.jsp페이지로 포워딩
//		forward = new ActionForward();
//		forward.setRedirect(true);
//		forward.setPath("./");
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<script>");
		out.println("alert('로그아웃 되었습니다')");
		out.println("location.href='./'");
		out.println("</script>");
		
		
		
		return forward;
	}

}
