package member.action;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import member.svc.MemberJoinProService;
import member.vo.MemberBean;

public class MemberJoinProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("MemberJoinProAction접근!");
		ActionForward forward = null;

		String name = request.getParameter("name");
		String id = request.getParameter("id");
		String passwd = request.getParameter("passwd");
		String email = request.getParameter("email");

		MemberBean memberBean = new MemberBean();
		memberBean.setName(name);
		memberBean.setId(id);
		memberBean.setPasswd(passwd);
		memberBean.setEmail(email);

//		System.out.println(name);
//		System.out.println(id);
//		System.out.println(passwd);
//		System.out.println(email);

		// MemberJoinProService클래스의 DupChekcMember()메서드를 호출하여
		// 회원 가입 전 중복 여부 확인 요청 작업 수행
		// =>파라미터 : MemberBean, 리턴타입 : int
		MemberJoinProService memberJoinProService = new MemberJoinProService();
		int checkResult = memberJoinProService.DupCheckMember(memberBean);

		// 중복체크 결과가 0이면 '아이디 중복', -1이면 '이메일중복' 메시지를
		// 문자열(resultStr)에 저장 후 자바스크립트를 사용하여
		// 메시지 출력 후 이전 페이지로 이동
		// 중복 체크 결과가 1이면 회원 가입 처리 요청 수행

		String resultStr = "";

		if (checkResult == 0 || checkResult == -1) {
			if (checkResult == 0) {
				resultStr = "아이디 중복";
			} else if (checkResult == -1) {
				resultStr = "이메일 중복";
			}
			
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('"+resultStr+"')");
			out.println("history.back()");
			out.println("</script>");
			
		} else{
			boolean isJoinSuccess = memberJoinProService.joinMember(memberBean);

			if (!isJoinSuccess) {
				response.setContentType("text/html;charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('회원가입실패!')");
				out.println("history.back()");
				out.println("</script>");
			} else {
				forward = new ActionForward();
				forward.setRedirect(true);
				forward.setPath("MemberLoginForm.me");
			}
		}

		return forward;
	}

}
