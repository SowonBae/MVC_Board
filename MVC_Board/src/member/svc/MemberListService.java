package member.svc;

import java.sql.Connection;
import java.util.ArrayList;

import member.dao.MemberDAO;
import member.vo.MemberBean;
import static board.db.JdbcUtil.*;
public class MemberListService {

	public ArrayList<MemberBean> getMemberList() {
		Connection con = getConnection();
		MemberDAO memberDao = MemberDAO.getInstance();
		memberDao.setConnection(con);
		
		ArrayList<MemberBean> memberList = memberDao.selectMemberList();
		
		close(con);
		
		return memberList;
	}

	public ArrayList<MemberBean> getMemberList(String orderTarget, String orderType) {
		Connection con = getConnection();
		MemberDAO memberDao = MemberDAO.getInstance();
		memberDao.setConnection(con);
		//memberDAO클래스의 selectMemberList() 메서드를 호출하여
		//회원목록 조회 요청 작업 수행
		//=>파라미터 : 정렬대상(orderTarget), 정렬방식(orderType)
		//  리턴타입 : ArrayList<memberBean>
		
		ArrayList<MemberBean> memberList = memberDao.selectMemberList(orderTarget, orderType);
		
		close(con);
		
		return memberList;
	}
}
