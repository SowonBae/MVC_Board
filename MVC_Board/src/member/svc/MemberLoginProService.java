package member.svc;

import java.sql.Connection;


import member.dao.MemberDAO;
import member.vo.MemberBean;
import static board.db.JdbcUtil.*;
public class MemberLoginProService {

	public int loginMember(MemberBean member) {
		
		Connection con = getConnection();
		MemberDAO memberDao =MemberDAO.getInstance();
		memberDao.setConnection(con);
		
		int loginResult = memberDao.loginMember(member);
		
		
		close(con);
		
		return loginResult;
	}


}
