package board.svc;

import static board.db.JdbcUtil.*;

import java.sql.Connection;

import board.dao.BoardDAO;

public class BoardDeleteProService {

	// 패스워드 일치 여부 조회 요청을 위한 isArticleWriter() 메서드 정의
	public boolean isArticleWriter(int board_num, String board_pass) {
		boolean isArticleWriter = false;
		
		Connection con = getConnection();
		
		BoardDAO boardDAO = BoardDAO.getInstance();
		
		boardDAO.setConnection(con);
		
		// BoardDAO 클래스의 isArticleBoardWriter() 메서드를 호출하여
		// 전달받은 패스워드와 board_num 에 해당하는 게시물의 패스워드를 판별
		// => 파라미터 : 글번호(board_num), 패스워드(board_pass)  
		// => 리턴타입 : boolean(isArticleWriter)
		isArticleWriter = boardDAO.isArticleBoardWriter(board_num, board_pass);
		
		close(con);
		
		return isArticleWriter;
	}

	// 글 삭제 작업 요청을 위한 deleteArticle() 메서드 정의
	public boolean removeArticle(int board_num) {
		boolean isDeleteSuccess = false;
		
		Connection con = getConnection();
		
		BoardDAO boardDAO = BoardDAO.getInstance();
		
		boardDAO.setConnection(con);
		
		// 글 삭제 작업 요청을 위해 BoardDAO 클래스의 deleteArticle() 메서드 호출
		// => 파라미터 : 글번호(board_num), 리턴타입 : int(deleteCount)
		int deleteCount = boardDAO.deleteArticle(board_num); 
		
		// 글 삭제 성공 시 commit 작업 수행, isDeleteSuccess 를 true 로 변경
		// 글 삭제 실패 시 rollback 작업 수행
		if(deleteCount > 0) {
			commit(con);
			isDeleteSuccess = true;
		} else {
			rollback(con);
		}
		
		close(con);
		
		return isDeleteSuccess;
	}
}

















