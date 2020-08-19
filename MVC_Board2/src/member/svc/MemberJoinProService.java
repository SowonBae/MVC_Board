package member.svc;

import java.sql.Connection;

import member.dao.MemberDAO;
import member.vo.MemberBean;
import static member.db.JdbcUtil.*;

public class MemberJoinProService {

	//회원 중복 여부 확인 요청 작업을 수행하기 위한 dupCheckMember()메서드 정의
	public int DupCheckMember(MemberBean memberBean) {
		
		boolean isJoinSuccess = false;
		
		Connection con = getConnection();
		
		MemberDAO memberDao = MemberDAO.getInstance();
		
		memberDao.setConnection(con);
		
		int checkResult = memberDao.dupCheckmember(memberBean);
		close(con);
		
		return checkResult;
	}

	
	public boolean joinMember(MemberBean memberBean) {
		System.out.println("proService접근");
		boolean isJoinSuccess = false;
		Connection con = getConnection();
		MemberDAO memberDao = MemberDAO.getInstance();
		
		memberDao.setConnection(con);
//		System.out.println(memberBean.getEmail());
//		System.out.println(memberBean.getName());
//		System.out.println(memberBean.getPasswd());
//		System.out.println(memberBean.getId());
		
		int insertCount = memberDao.insertMember(memberBean);
		
		if(insertCount>0) {
			commit(con);
			isJoinSuccess = true;
		}else {
			rollback(con);
		}
		
		close(con);
		return isJoinSuccess;
	}

	
}
