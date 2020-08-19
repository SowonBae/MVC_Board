package board.svc;

import static board.db.JdbcUtil.*;

import java.sql.Connection;

import board.dao.BoardDAO;
import board.vo.BoardBean;

public class BoardReplyProService {

	public boolean registReplyArticle(BoardBean article) {
		boolean isReplySuccess = false;
		
		Connection con = getConnection();
		BoardDAO boardDAO = BoardDAO.getInstance();
		boardDAO.setConnection(con);
		
		//BoardDAO클래스의 insertReplyArticle()메서드를 호출하여 답글 등록 작업 수행
		//=>파라미터 : BoardBean, 리턴타입 : int (insertCount)
		int insertCount = boardDAO.insertReplyArticle(article);
		
		if(insertCount>0) {
			commit(con);
			isReplySuccess = true;
		}else {
			rollback(con);
		}
		
		close(con);
		
		return isReplySuccess;
	}

}
