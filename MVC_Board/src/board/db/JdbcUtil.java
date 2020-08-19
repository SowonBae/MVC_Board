package board.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class JdbcUtil {
	//JDBC관련 작업 처리 기능을 제공하는클래스
	//=>모든 메서드를 인스턴스 생성없이 호출 가능하도록 static으로 선언.
	
	//1.JDBC연결 후 Connection객체를 리턴하는 getConnection()메서드 정의.
	public static Connection getConnection() {//매번 인스턴스 생성없이 호출하기 위해서 static으로 만들것임
		Connection con = null;
		
		try {
			//톰캣 Context객체(context.xml) 가져오기.
			Context initCtx = new InitialContext();
			
			//context.xml내의 <Resource>태그 부분 가져오기.
//			Context envCtx = (Context) initCtx.lookup("java:comp/env");//Object를 Context로 형변환해야함.
			
			//context.xml내의 지정된 name 속성 항목을 찾아 DataSource객체(커넥션풀) 반환.
//			DataSource ds = (DataSource)envCtx.lookup("jdbc/MySQL");//context.xml에 name부분 가져옴.
			
			//위의 lookup()메서드 호출을 하나로 결합할 경우(
			DataSource ds = (DataSource)initCtx.lookup("java:comp/env/jdbc/MySQL");
			
			//커넥션 풀로부터 Connection객체를 가져오기.
			con = ds.getConnection();//context.xml 내에 username과 password입력 시
//			con = ds.getConnection("root","1234");//context.xml 내에 username과 password 미입력 시
			
			//DB의 트랜젝션 처리를 위해서 AutoCommit기능을 해제
			//->작업 성공 시commit(), 실패 시 rollback()메서드 수동으로 호출해야함.
			con.setAutoCommit(false);
			
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}
	
	//사용한 Connection객체를 반환하기 위한 close()메서드 정의.
	public static void close(Connection con) {
		if(con!=null) try{con.close();}catch (Exception e) {}
	}
	//사용한 PreparedStatement객체를 반환하기 위한 close()메서드 정의.
	public static void close(PreparedStatement pstmt) {
		if(pstmt!=null) try{pstmt.close();}catch (Exception e) {}
	}
	//사용한 ResultSet객체를 반환하기 위한 close()메서드 정의.
	public static void close(ResultSet rs) {
		if(rs!=null) try{rs.close();}catch (Exception e) {}
	}
	
	//DB조작 완료 후 commit()메서드 호출을 위한 commit()메서드 정의
	//=>트랜잭션 처리를 위해 Auto Commit기능을 해제했을 떄 필요
	public static void commit(Connection con) {
		try {
			con.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//DB조작 완료 후 rollback()메서드 호출을 위한 rollback()메서드 정의
		//=>트랜잭션 처리를 위해 Auto Commit기능을 해제했을 떄 필요
	public static void rollback(Connection con) {
		try {
			con.rollback();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}















































