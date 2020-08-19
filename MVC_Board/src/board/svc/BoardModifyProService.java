package board.svc;

import static board.db.JdbcUtil.*;

import java.sql.Connection;

import board.dao.BoardDAO;
import board.vo.BoardBean;

public class BoardModifyProService {

	public boolean isArticleWriter(int board_num, String board_pass) {
		boolean isArticleWriter = false;

		// 1.
		Connection con = getConnection();

		// 2.
		BoardDAO boardDAO = BoardDAO.getInstance();

		// 3.
		boardDAO.setConnection(con);

		// 5.
		// BoardDAO클래스의 isArticleBoardWriter() 메서드를 호출하여
		// 전달받은 패스워드와 board_num에 해당하는 게시물의 패스워드를 판별
		// =>파라미터 : board_num, board_pass 리턴타입 : boolean isArticleWriter

		isArticleWriter = boardDAO.isArticleBoardWriter(board_num, board_pass);

		// 4.
		close(con);
		return isArticleWriter;
	}

	//게시물 수정 작업 요청을 위한 modifyArticle()메서드 정의
	public boolean modifyArticle(BoardBean article) {
		boolean isModifySuccess = false;
		// 1.
		Connection con = getConnection();

		// 2.
		BoardDAO boardDAO = BoardDAO.getInstance();

		// 3.
		boardDAO.setConnection(con);

		// 5.
		// 파라미터 : boardBean객체 //리턴타입 : int형의 updateCount
		
		int updateCount = boardDAO.updateArticle(article);
		if(updateCount>0) {
			commit(con);
			isModifySuccess = true;
		}else {
			rollback(con);
		}
		
		// 4.
		close(con);
		return isModifySuccess;
	}

}
