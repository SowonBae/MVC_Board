package member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import member.vo.MemberBean;

import static board.db.JdbcUtil.close;
import static member.db.JdbcUtil.*;

public class MemberDAO {
	private static MemberDAO instance = new MemberDAO();

	private MemberDAO(){}

	public static MemberDAO getInstance() {
		if(instance==null) {
			instance = new MemberDAO();
		}
		return instance;
	}
	
	Connection con;

	public void setConnection(Connection con) {
		this.con = con;
	}

	//회원 중복 여부 체크를 위한 dupCheckMember()메서드 정의
	public int dupCheckmember(MemberBean memberBean) {
		int checkResult = 1;
	
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		//아이디 또는 이메일이 중복되는 레코드의 아이디와 이메일을 조회
		//=> 조회결과와 memberbean객체의 id, email을 검사하여
		//아이디가 중복일 경우 checkResult를 0, 이메일이 중복일 경우 -1로 변경
		//만약, 중복되는 데이터가 없을 경우 checkResult를 1로 변경
		
		try {
			String sql = "select id,email from member where id=? or email=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, memberBean.getId());
			pstmt.setString(2, memberBean.getEmail());
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				if(memberBean.getId().equals(rs.getString("id"))) {
					checkResult = 0;
				}else if(memberBean.getEmail().equals(rs.getString("email"))) {
					checkResult = -1;
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return checkResult;
	}
	
	public int insertMember(MemberBean memberBean) {
		// NullPointException뜰 때 con을 return해주지 않았는지 확인
//		System.out.println("memberBean접근");
		int insertCount = 0;
		PreparedStatement pstmt = null;		
		try {
			String sql = "insert into member values(null,?,?,?,?,now())";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, memberBean.getName());
			pstmt.setString(2, memberBean.getId());
			pstmt.setString(3, memberBean.getPasswd());
			pstmt.setString(4, memberBean.getEmail());
			
			insertCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("MemberDAO-insertMember()요류!"+e.getMessage());
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return insertCount;
	}

	public int loginMember(MemberBean member) {
		int loginResult = 0;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			//id를 사용하여 passwd검색
			String sql = "select passwd from member where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member.getId());

			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				if(member.getPasswd().equals(rs.getString("passwd"))) {
					loginResult = 1;//1이면 비번같음
				}else {
					loginResult = -1;//-1이면 비번 안같음
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			close(rs);
			close(pstmt);
		}
		
		return loginResult;
	}

	public ArrayList<MemberBean> selectMemberList() {

		ArrayList<MemberBean> arr = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "select * from member";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			arr = new ArrayList<MemberBean>();
			while(rs.next()) {
				MemberBean memberBean = new MemberBean();
				memberBean.setIdx(rs.getInt("idx"));
				memberBean.setName(rs.getString("name"));
				memberBean.setId(rs.getString("id"));
				memberBean.setPasswd(rs.getString("passwd"));
				memberBean.setEmail(rs.getString("email"));
				memberBean.setRegDate(rs.getDate("regDate"));
				arr.add(memberBean);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return arr;
	}

	public ArrayList<MemberBean> selectMemberList(String orderTarget, String orderType) {

		ArrayList<MemberBean> arr = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			//OrderBy절의 항목을 문자열 결합으로 생성
			String sql = "select * from member order by "+ orderTarget +" "+ orderType;
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			arr = new ArrayList<MemberBean>();
			while(rs.next()) {
				MemberBean memberBean = new MemberBean();
				memberBean.setIdx(rs.getInt("idx"));
				memberBean.setName(rs.getString("name"));
				memberBean.setId(rs.getString("id"));
				memberBean.setPasswd(rs.getString("passwd"));
				memberBean.setEmail(rs.getString("email"));
				memberBean.setRegDate(rs.getDate("regDate"));
				arr.add(memberBean);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return arr;
	}

	
}
