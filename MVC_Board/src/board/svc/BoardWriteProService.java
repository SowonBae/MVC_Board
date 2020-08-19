package board.svc;

import static board.db.JdbcUtil.*;

import java.sql.Connection;

import board.dao.BoardDAO;
import board.vo.BoardBean;
public class BoardWriteProService {

	public boolean registArticle(BoardBean boardBean) {
		//글쓰기 작업 요청 후 registArticle()메서드 정의
//		System.out.println("BoardWriteProService - registArticle()");
		
		//1. 글쓰기 작업 요청 처리 결과를 저장할 boolean타입 변수
		boolean isWriteSuccess = false;
		
		//2. JdbcUtil-connection 가져와야함.
		Connection con = getConnection();	//*공통작업 
		//import static db.JdbcUtil.getConnection을 통해 getConnection()만으로 호출 가능
		//import static db.JdbcUtil.* 로 하여 JdbcUtil아래에 있는메서드 다 가져옴.
		
		//3. BoardDAO클래스로부터 BoardDAO객체 가져오기.
		BoardDAO boardDAO = BoardDAO.getInstance();	//*공통작업
		
		//4. BoardDAO 객체의 setConnection()메서드를 호출하여 Connection객체를 전달
		boardDAO.setConnection(con);	//*공통작업
		
		//5. BoardDAO객체의 XXX메서드를 호출하여 XXX작업 수행 및 결과 리턴받기.
		//insertArticle()메서드를 호출하여 글 등록 작업을 수행 및 결과 리턴받아 처리.
		// =>파라미터 : BoardBean 객체, 리턴값 : int(insertCount)
		int insertCount = boardDAO.insertArticle(boardBean);
		
		//리턴값에 대한 결과 처리.
		if(insertCount>0) {//작업이 성공했을 경우
			//insert작업이 성공했을 경우 트랜잭션 적용을 위해
			//JdbcUtil클래스의 commit()메서드를 호출하여 commit작업 수행
			commit(con); //JdbcUtil.commit(con)
			
			//작업처리 결과를 성공으로 표시하기 위해 isWriteSuccess를 true로 지정
			isWriteSuccess = true;
		}else {//작업이 실패했을 경우
			//insert작업이 실패했을 경우 트랜잭션 적용을 위해
			//JdbcUtil클래스의 rollback()메서드를 호출하여 rollback작업 수행
			rollback(con); //JdbcUtil.rollback(con)
		}
		
		//6. JdbcUtil객체로부터 가져온 Connection객체를 반환.
		close(con);	//*공통작업 //JdbcUtil.close(con)

		//7. 작업처리결과 리턴
		return isWriteSuccess;
	}

	public void registIP(String ip) {
		// TODO Auto-generated method stub
		
	}
}



























































