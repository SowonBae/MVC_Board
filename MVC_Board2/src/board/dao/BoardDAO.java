package board.dao;

import static board.db.JdbcUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import board.vo.BoardBean;
import member.vo.MemberBean;

public class BoardDAO {

	//--------------BoardDAO인스턴스 생성 및 리턴을 위한 싱글톤 패턴--------------
	//기존 BoardDAO인스턴스가 없을 때만 생성
	private static BoardDAO instance;

	private BoardDAO(){	}

	public static final BoardDAO getInstance() {
		//기존 BoardDAO인스턴스가 없을 떄만 생성하고, 있을 경우 생성하지 않음.
		if(instance == null) {
			instance = new BoardDAO();
		}
		
		return instance;
	}	
	//------------------------------------------------------------------
	//Service클래스로부터 JdbcUtil에서 제공받은 Connection객체를 전달받기.
	Connection con;

	public void setConnection(Connection con) {
		this.con = con;
	}
	
	//------------------------------------------------------------------
	
	//글쓰기 작업 수행을 위한 insertArticle()메서드 정의
	//=->파라미터로 BoardBean객체(article)// 리턴타입 : int(insertCount)
	public int insertArticle(BoardBean article) {
		//새 게시물 저장을 위해 현재 게시글 최대 글번호를 조회 후 
		//새 게시물 번호를 최대 글번호 + 1을 하여 INSERT작업을 수행할 것임. 그 후 결과값을 리턴.
		
		int insertCount = 0; // INSERT 작업 결과를 저장할 변수
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			//새 게시물 번호 생성을 위해 기존 게시물 중 최대 글번호 조회
			String sql = "select max(board_num) from board";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			//조회된 글번호가 있을 경우 해당 글 번호를 새 글 번호로 지정.
			int num=1;//새 글 번호 저장
			if(rs.next()) {
				num = rs.getInt(1)+1;//max(board_num)하나밖에 없을테니까 1번 가져오면됨.
			}
			
			
			//board테이블에 전달받은 게시물 정보를 insert할것임.
			
			sql = "insert into board values(?,?,?,?,?,?,?,?,?,?,now())";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.setString(2, article.getBoard_name());
			pstmt.setString(3, article.getBoard_pass());
			pstmt.setString(4, article.getBoard_subject());
			pstmt.setString(5, article.getBoard_content());
			pstmt.setString(6, article.getBoard_file());
			pstmt.setInt(7, num); //board_re_ref(참조글번호) - 나 자신은 내가 원본글이니까 글 번호로 지정함. 
			pstmt.setInt(8, 0);//board_re_lev(들여쓰기 레벨) - 원본글이므로 들여쓰기 없음
			pstmt.setInt(9, 0);//board_re_seq(글 순서 번호) - 원본글이므로 순서 0으로 지정
			pstmt.setInt(10, 0); //조회수
			
			//SQL구문 실행 및 결과값 리턴받기
			insertCount = pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
//			e.printStackTrace();
			System.out.println("BoardDAO - insertArticle()에러 ! : "+e.getMessage());
		}finally {
			//PreparedStatement, ResultSet객체 반환
			close(rs); 	//JdbcUtil.cose(rs)
			close(pstmt);//JdbcUtil.cose(pstmt)
		}
		
		
		
		
		return insertCount;
	}

	public int selectListCount() {
		int listCount = 0;
		
		PreparedStatement pstmt= null;
		ResultSet rs = null;
		
		try {
			String sql = "select count(board_num) from board";
			pstmt = con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			//게시물 수가 조회될 경우(게시물이 하나라도 존재할 경우)
			//=>listCount에 조회된 게시물 수 저장.
			if(rs.next()) {
				listCount = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			System.out.println("BoardDAO - selectListCount() 에러!");
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		
		//들고 service로 이동함
		return listCount;
	}

	//전체 게시물 조회를 위한 selectArticleList()메서드 정의
	public ArrayList<BoardBean> selectArticleList(int page, int limit) {
		ArrayList<BoardBean> articleList =null;
		
		PreparedStatement pstmt= null;
		ResultSet rs = null;
		try {
			//board테이블의 모든 레코드 조회. 조건 : 정렬 (board_re_ref기준 내림차순, board_re_seq기준 오름차순>
											//제한(limit) : X번 레코드부터 limit개까지
			//읽어올 시작 레코드 번호 계산
			//현재 페이지번호에서 1을 뺀 결과에 10을 곱하면 시작 레코드 번호가 나온다.
			int startRow = (page-1)*10;
			
			
			String sql = "SELECT * FROM board ORDER BY board_re_ref DESC, board_re_seq ASC LIMIT ?,?;";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startRow); //시작레코드번호 전달
			pstmt.setInt(2, limit); 	//게시물 수 전달
			
			rs=pstmt.executeQuery();
			
			//조회된 레코드가 존재할 경우 BoardBean객체에 레코드 정보 저장 반복.
			// ArrayList객체에BoardBean객체 추가 작업 반복
			
			articleList = new ArrayList<BoardBean>();
			while(rs.next()) {
				BoardBean article = new BoardBean();
				//조회된 레코드 정보 BoardBean객체에 저장.
				article.setBoard_num(rs.getInt("board_num"));
				article.setBoard_name(rs.getString("board_name"));
				article.setBoard_subject(rs.getString("board_subject"));
				article.setBoard_content(rs.getString("board_content"));
				article.setBoard_file(rs.getString("board_file"));
				article.setBoard_re_ref(rs.getInt("board_re_ref"));
				article.setBoard_re_lev(rs.getInt("board_re_lev"));
				article.setBoard_re_seq(rs.getInt("board_re_seq"));
				article.setBoard_readcount(rs.getInt("board_readcount"));
				article.setBoard_date(rs.getDate("board_date"));
				
				
				
				//BoardBean객체를 ArrayList객체에 추가.
				articleList.add(article);
			}
		
		} catch (SQLException e) {
			System.out.println("BoardDAO - selectArticleList() 에러!");
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return articleList;
	}

	//게시물 1개에 대한 상세정보를 조회하는 작업을 수행하는  selectArticle()메서드 정의
	public BoardBean selectArticle(int board_num) {
		BoardBean article = null;
		PreparedStatement  pstmt = null;
		ResultSet rs = null;

		//board_num에 해당하는 게시물의 모든 정보를 조회하여 BoardBean객체에 저장
		try {
			String sql = "select * from board where board_num =?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, board_num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				article = new BoardBean();
				article.setBoard_num(rs.getInt("board_num"));
				article.setBoard_name(rs.getString("board_name"));
				article.setBoard_subject(rs.getString("board_subject"));
				article.setBoard_content(rs.getString("board_content"));
				article.setBoard_file(rs.getString("board_file"));
				article.setBoard_re_ref(rs.getInt("board_re_ref"));
				article.setBoard_re_lev(rs.getInt("board_re_lev"));
				article.setBoard_re_seq(rs.getInt("board_re_seq"));
				article.setBoard_readcount(rs.getInt("board_readcount"));
				article.setBoard_date(rs.getDate("board_date"));
			}
		
		} catch (SQLException e) {
			System.out.println("BoardDAO의 selectArticle실패! : "+e.getMessage());
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
			
		}
			
		return article;
	}

	
	public int updateReadcount(int board_num) {
		int updateCount = 0;
		PreparedStatement pstmt = null;
		
		try {
			String sql = "update board set board_readcount = board_readcount+1 where board_num =?";
			pstmt  = con.prepareStatement(sql);
			pstmt.setInt(1, board_num);
			updateCount = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		
		return updateCount;
	}

	
	//전달받은 board_num 에 해당하는 게시물의 패스워드와 전달받은 board_pass를 비교하여
	//수정가능여부를 판별하는 isArticleBOardWriter()메서드 정의
	public boolean isArticleBoardWriter(int board_num, String board_pass) {
		System.out.println(board_pass);
		boolean isArticleWriter = false;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {
			String sql = "select board_pass from board where board_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, board_num);
			rs = pstmt.executeQuery();
			//board_num에 해당하는 board_pass가 존재할경우.
			if(rs.next()) {
				//비밀번호 비교
				if(board_pass.equals(rs.getString("board_pass"))) {
					isArticleWriter = true;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return isArticleWriter;
	}

	public int updateArticle(BoardBean article) {
		int updateCount = 0;
		
		
		PreparedStatement pstmt = null;
		
		
		try {
			String sql = "update board set board_subject=?, board_content=? where board_num=?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, article.getBoard_subject());
			pstmt.setString(2, article.getBoard_content());
			pstmt.setInt(3, article.getBoard_num());
			
			updateCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		
		return updateCount;
	}

	public int insertReplyArticle(BoardBean article) {
		int insertCount = 0;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int num = 1;
		int ref = article.getBoard_re_ref();
		int lev = article.getBoard_re_lev();
		int seq = article.getBoard_re_seq();
		
		try {
			String sql = "select max(board_num) from board";
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {//최대 글 번호가 조회될 경우 최대글번호 +1값을 글 번호로 저장
				num = rs.getInt(1) + 1;
			}
			
			//답변 글 등록 전 기존 답변글이 있을 경우 순서번호(board_re_seq)정리
			//기존에 동일한 참조글번호(board_re_ref)의 답글들의 순서번호를 전부 +1할것임.
			//단, 원본 글 제외를 위해 기존 seq번호가 원본글보다 큰 순서번호만 증가시킬것임. 
			sql = "update board set board_re_seq=board_re_seq+1 "
					+ "where board_re_ref=? and board_re_seq>?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, ref); //원본글의 참조글번호
			pstmt.setInt(2, seq); //원본글의 순서번호
			//순서번호 업데이트 결과를 변수에 저장
//			int updateCount = pstmt.executeUpdate();
//			
//			if(updateCount>0) {
//				commit(con);//insert수행 후 최종적으로 commit()조정을 위해 주석 처리
//			}
			
			pstmt.executeUpdate();
			//순서번호와(board_re_seq)와 들여쓰기 레벨(board_re_lev)을
			//기존번호(답글을 등록할 원본글의 번호)보다 1만큼 증가시킴
			seq++;
			lev++;
			
			//계산된 번호들을 포함하여 답변글 게시물 정보를 추가 작업 수행
			//=>단, 답변글에는 파일업로드가 없으므로 파일은 제외(""으로 전달)
			sql = "insert into board values(?,?,?,?,?,?,?,?,?,?,now())";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.setString(2, article.getBoard_name());
			pstmt.setString(3, article.getBoard_pass());
			pstmt.setString(4, article.getBoard_subject());
			pstmt.setString(5, article.getBoard_content());
			pstmt.setString(6, "");
			pstmt.setInt(7, ref);
			pstmt.setInt(8, lev);
			pstmt.setInt(9, seq);
			pstmt.setInt(10, 0);
			insertCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("틀렀따"+e.getMessage());
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return insertCount;
	}

	public int deleteArticle(int board_num) {
		int deleteCount = 0;
		
		PreparedStatement pstmt = null;
		
		try {
			// board_num 에 해당하는 게시물 삭제
			String sql = "DELETE FROM board WHERE board_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, board_num);
			deleteCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("BoardDAO - deleteArticle() 오류!");
		} finally {
			close(pstmt);
		}
		
		return deleteCount;
	}

	


	
	
	
	
	
	
}
