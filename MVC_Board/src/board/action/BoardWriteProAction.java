package board.action;

import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import board.svc.BoardWriteProService;
import board.vo.ActionForward;
import board.vo.BoardBean;

public class BoardWriteProAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//게시판 글 작성 후 DB작업을 위한 준비 작업
		// => 준비 완료 후 BoardWriteProService클래스 인스턴스 생성하여 작업 수행
		System.out.println("BoardWriteProAction!");
		
		//ActionForward객체 타입으로 리턴하니까 미리 객체 생성
		ActionForward forward = null;
		
		int size = 10*1024*1024;
		//최대 업로드 가능 크기 지정. 바이트 단위를 기준으로 원하는 사이즈를 단위별로 계산.
		//1024 = 1KByte * 1024 = 1Mbyte * 10 = 10MB
		
		//request객체로부터 현재 서블릿 컨텍스트 객체 가져오기
		ServletContext context = request.getServletContext();
		
		//파일이 업로드 될 폴더 설정
		String saveFolder = "/boardUpload";//업로드 폴더(가상의 폴더) 이름 지정
		
		
		//지정된 이름의 실제 폴더 위치 가져오기. 
		//이클립스 실제 업로드 폴더는 
		/*
		 * /D:\JSP_Model2\workspace_jsp_model2\.metadata\.plugins
		 * \org.eclipse.wst.server.core\tmp0\wtpwebapps\MVC_Board 
		 */
		//프로젝트 내에 실제 폴더 구조가 만들어짐.
		String realFolder = context.getRealPath(saveFolder);
		
//		System.out.println("가상폴더 : "+saveFolder); //가상폴더 : /boardUpload
//		System.out.println("실제 폴더 : "+realFolder); 
		/*
		 * 실제 폴더 : D:\JSP_Model2\workspace_jsp_model2\.metadata\.plugins
		 * \org.eclipse.wst.server.core\tmp0\wtpwebapps\MVC_Board\boardUpload
		 */
		
		//파일업로드를 다루기 위한 MultipartRequest객체 생성
		MultipartRequest multi = new MultipartRequest(
				request, //request객체
				realFolder, //계산된 실제 업로드 폴더 위치 전달
				size, //최대 업로드 가능 파일 크기
				"UTF-8",//캐릭터셋
				new DefaultFileRenamePolicy()
		);//동일한 파일 존재 시 이름 변경 기능. 객체 이름 같으면 숫자 붙여서 이름 다르게 만들어줌.	
		
		String name = multi.getParameter("board_name");
		String pass = multi.getParameter("board_pass");
		String subject = multi.getParameter("board_subject");
		String content = multi.getParameter("board_content");
		
		//BoardBean객체 생성	
		BoardBean boardBean = new BoardBean();
		//일반적인 텍스트는 multi.getParameter()메서드로 가져온다
		boardBean.setBoard_name(name);
		boardBean.setBoard_pass(pass);
		boardBean.setBoard_subject(subject);
		boardBean.setBoard_content(content);

		//업로드 될 파일명의 경우 getOriginameFileName()또는 getFileSystemName()으로 가져온다.
//		System.out.println(multi.getParameter("board_file"));
//		System.out.println(multi.getOriginalFileName((String)multi.getFileNames().nextElement()));//등록하는파일명
//		System.out.println(multi.getFilesystemName((String)multi.getFileNames().nextElement()));//실제 등록되는 파일 명
		//1)업로드하는 파일 원본이름을 사용하는 경우
		boardBean.setBoard_file(multi.getOriginalFileName((String)multi.getFileNames().nextElement()));
		
		//2)업로드하는 파일이 중복될 때, 이름이 변경된 실제 업로드 된 파일명을 사용할 경우.
//		boardBean.setBoard_file(multi.getFilesystemName((String)multi.getFileNames().nextElement()));
		//BoardWriteProService클래스의 인스턴스를 생성하여
		//registArticle()메서드를 호출하고, 글쓰기를 위한 BoardBean객체 전달
		//=>글쓰기 작업 요청 처리후 결과를 boolean타입으로 리턴받아 포워딩 처리.
		
		BoardWriteProService boardWriteProService= new BoardWriteProService();
		boolean isWriteSuccess = boardWriteProService.registArticle(boardBean);
		//registArticle은 여기서 자동으로 생성하였음.

		//ip주소가져오는작업
//		String ip = request.getRemoteAddr();
//		boardWriteProService.registIP(ip);
		
//		System.out.println("isWriteSuccess : "+isWriteSuccess);
		
		
		//글쓰기 작업 요청 처리 결과(isWriteSuccess)를 통해 오류 메세지 출력 또는 포워딩 수행.
		//isWriteSuceess가 false인지 판별
		if(!isWriteSuccess) {//요청 실패했을 경우
			//자바스크립트 사용하여 오류 메시지 출력 후 이전 페이지로 이동.
			//자바스크립트 사용을 위해 printWrite객체 사용.
			//printWrite객체 사용하여 자바스크립트 코드를 출력한다.
			//response객체를 이용하여 문서타입 설정 및 printWriter객체 가져오기.
			
			response.setContentType("text/html;charset=UTF-8");//문서 타입 설정. jsp맨위에 있음.
			PrintWriter out = response.getWriter();//응답에 관련된 객체를 사용한다. 
			//자바스크립트를 println메서드를 사용하여 문자열로 출력.
			out.println("<script>");//자바스크립트 시작
			out.println("alert('글 등록 실패!')");//오류메시지 출력
			out.println("history.back()");//이전페이지로 이동
			out.println("</script>");//자바스크립트 끝
			
		}else { //요청 성공했을 경우
			//ActionForward객체를 생성하여 포워딩할 방식 및 URL지정 
			forward = new ActionForward();
			//BoardList.bo서블릿 주소를 새로 요청하고, request객체 유지할 필요가 없음.
			//=> Redirect방식으로 포워딩 처리.
			forward.setRedirect(true);//Redirect방식 지정.
			forward.setPath("BoardList.bo");
		}
		
		
		return forward;
	}

}



























































