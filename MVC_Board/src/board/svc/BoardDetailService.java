package board.svc;

import static board.db.JdbcUtil.*;

import java.sql.Connection;

import board.dao.BoardDAO;
import board.vo.BoardBean;
public class BoardDetailService {

	public BoardBean getArticle(int board_num) {
		
		
		//1.
		Connection con = getConnection();
		
		//2.
		BoardDAO boardDAO = BoardDAO.getInstance();
		
		//3.
		boardDAO.setConnection(con);		
		
		//5.BoardDAO객체의 XXX메서드를 호출하여 XXX작업 수행 및 결과 리턴받기
		//	selectArticle()메서드를 호출하여 글 상세내용 조회 작업 수행 및 결과 리턴받아 처리.
		//	->파라미터 : 글번호(board_num), 리턴타입 : BoardBean객체
		//글 상세내용 조회 작업 요청 처리 결과를 저장할 BoardBean타입 변수 선언
		BoardBean article = boardDAO.selectArticle(board_num);
		
		//조회된 게시물이 존재할 경우 조회수 업데이트 수행
		if(article!=null) {
			//조회수 증가를 위해 BoardDAO객체의 updateReadcount()메서드 호출
			//=>파라미터 : 글번호(board_num), 리턴타입 : int(updateCount)
			int updateCount = boardDAO.updateReadcount(board_num);
			
			//조회수 증가에 성공했을 경우 commit, 실패했을 경우 rollback수행
			if(updateCount>0) {
				commit(con);
			}else {
				rollback(con);
			}
		}
		
		//4.
		close(con);
		
		
		return article;
		
	}

}
