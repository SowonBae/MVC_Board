package member.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class JdbcUtil {
	public static Connection getConnection() {
		Connection con= null;
		
		try {
			Context initCtx = new InitialContext();
			DataSource ds = (DataSource)initCtx.lookup("java:comp/env/jdbc/MySQL");
			
			con = ds.getConnection();
			con.setAutoCommit(false);
		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return con;		
	}
	
	public static void close(Connection con) {
		if(con!=null) {	try {con.close();} catch (Exception e) {}}
	}
	
	public static void close(PreparedStatement pstmt) {
		if(pstmt!=null) {	try {pstmt.close();} catch (Exception e) {}}
	}
	public static void close(ResultSet rs) {
		if(rs!=null) {	try {rs.close();} catch (Exception e) {}}
	}
	
	public static void commit(Connection con) {
		try {
			con.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void rollback(Connection con) {
		try {
			con.rollback();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
}
