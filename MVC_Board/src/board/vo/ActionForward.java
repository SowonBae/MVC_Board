package board.vo;

public class ActionForward {
	/*
	 * 서블릿에서 클라이언트로부터 요청을 전달받아 처리한 후
	 * 지정한 View페이지로 포워딩 할 때
	 * 포워딩 할 View페이지의 주소(URL)와 포워딩 방식(Redirect or Dispatch)을 공통으로 다루기 위한 클래스.
	 */
	private String path;//주소
	private boolean isRedirect;//포워딩방식
	//=> true : redirect //false : dispatch
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public boolean isRedirect() {
		return isRedirect;
	}
	public void setRedirect(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}
	
	
}
