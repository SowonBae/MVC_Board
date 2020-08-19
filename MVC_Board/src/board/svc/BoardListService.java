package board.svc;

import static board.db.JdbcUtil.*;

import java.sql.Connection;
import java.util.ArrayList;

import board.dao.BoardDAO;
import board.vo.BoardBean;
public class BoardListService {
	
	//전체 게시물 수 조회를 위한 getListCount()메서드 정의
	public int getListCount() {
		int listCount = 0;
		//1. jdbcUtil클래스의 getConnection()메서드를 호출하여 Connection 객체 가져오기
		Connection con =  getConnection();
		
		//2. BoardDAO클래스의 getInstance()메서드를 호출하여 BoardDAO객체 가져오기
		BoardDAO boardDAO = BoardDAO.getInstance();
		
		//3. BoardDAO클래스의 setConnection()메서드를 호출하여 Connection객체 전달하기
		boardDAO.setConnection(con);
		
		
		//5. BoardDAO클래스의 selectListCount()메서드를 호출하여 전체 게시물 수 가져오기
		//->파라미터 없음. 리턴타입 : int(listCount)
		listCount = boardDAO.selectListCount();
//		System.out.println("전체 게시물 수 : "+listCount);
		
		//4. JdbcUtil클래스의 close()메서드를 호출하여  Connection객체 반환
		close(con);
		
		return listCount;
	}

	//전체 게시물 목록 조회 요청을 위한 getArticleList()메서드 정의
	public ArrayList<BoardBean> getArticleList(int page, int limit) {
		ArrayList<BoardBean> articleList = null;
		
		System.out.println("BoardListService - getArticleList()");
		
		//1.
		Connection con = getConnection();
		//2.
		BoardDAO boardDAO = BoardDAO.getInstance();
		//3.
		boardDAO.setConnection(con);
		
		//5. boardDAO클래스의 selectArticleList()메서드를 호출하여 전체 게시물 목록 조회해야함.
		//파라미터 : page, limit
		//리턴타입 : ArrayList<BoardBean> : articleList
		articleList =  boardDAO.selectArticleList(page, limit);
		
		//4.
		close(con);
		
		return articleList;
	}
	
}
