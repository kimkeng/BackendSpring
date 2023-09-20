package sp1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;



public class air_sql {
	
	dbconfig db = new dbconfig();
	String sql = null;
	PreparedStatement ps = null;
	Connection con = null;
	int msg = 0;	//결과값
	Statement st = null;	//sql 구문 실행
	//여러가지 테이블에 대한 총 갯수 값 파악하는 메소드
	public int total_sum(String tablename)throws Exception {
		this.con = this.db.info();
		this.sql = "select count(*) as ctn from " + tablename;
		this.st = this.con.createStatement();
		ResultSet rs = this.st.executeQuery(this.sql);
		rs.next();
		int sum = Integer.parseInt(rs.getString("ctn"));
		
		this.st.close();
		this.con.close();
		return sum;
	}
	
	
	protected ArrayList<ArrayList<String>> person_list(int vpage) throws Exception {
		ArrayList<ArrayList<String>> alldata = new ArrayList<ArrayList<String>>();
		
		this.con = this.db.info();
		try {
			this.sql="select * from air_person order by pidx desc limit ?,2";	//?대신 '"+vpage+"' 써도됨
			this.ps = this.con.prepareStatement(this.sql);
			this.ps.setInt(1, vpage);
			ResultSet rs = this.ps.executeQuery();
			dto_air da = new dto_air();	//dto 1차원 배열을 로드하기 위한 호출
			while(rs.next()) {
				da.setPidx(rs.getString("pidx"));
				da.setPid(rs.getString("pid"));
				da.setPnm(rs.getString("pnm"));
				da.setPassport(rs.getString("passport"));
				da.setPtel(rs.getString("ptel"));
				da.setRcode(rs.getString("rcode"));
				da.setRair(rs.getString("rair"));
				da.setPerson(rs.getString("person"));
				da.setMoney(rs.getString("money"));
				da.setPdata(rs.getString("pdata"));
				alldata.add(da.listdata());
			}
			this.ps.close();
			this.con.close();
		}catch(Exception e) {
			System.out.println("SQL 오류발생!!");
		}
		
		return alldata;
	}
	
	
	//예매자 등록
	protected int perinsert(String rcode,String rair,String mid,String mname,String mpost,String mtel,String mperson,String totalmoney)throws Exception {
		this.con = this.db.info();
		this.con.setAutoCommit(false);	//transaction을 사용
		try {
			//SQL문 : 인원수를 확인하기 위한 sql 문법
			String count = "select count(rperson) as cnt from air_reserve where rcode = '"+rcode+"' and rperson >= '"+mperson+"'";
			this.st = this.con.createStatement();
			ResultSet rs = this.st.executeQuery(count);
			rs.next();
			if(rs.getString("cnt").equals("1")) {	//해당 요청 인원과 여유 인원이 맞을 경우
				//사용자 정보를 입력받아서 SQL 저장시킴
				this.sql ="insert into air_person values('0',?,?,?,?,?,?,?,?,now())";
				this.ps = this.con.prepareStatement(this.sql);
				this.ps.setString(1, mid);
				this.ps.setString(2, mname);
				this.ps.setString(3, mpost);
				this.ps.setString(4, mtel);
				this.ps.setString(5, rcode);
				this.ps.setString(6, rair);
				this.ps.setString(7, mperson);
				this.ps.setString(8, totalmoney);
				this.msg = this.ps.executeUpdate();
				if(this.msg == 1) {	//해당 정보가 정상 입력이 되었을 경우 기존 인원수를 조정
					this.sql = "update air_reserve set rperson=rperson-"+mperson+" where rcode='"+rcode+"'";
					this.st = this.con.createStatement();
					this.st.executeUpdate(this.sql);	//update 진행 후 종료
				}
				this.ps.close();
			}else {
				this.msg = 3;	//여유좌석이 없음
			}			
			this.con.commit();	//transaction 완료
			
		}catch(Exception e) {
			System.out.println(e);
			System.out.println("데이터 입력시 조건에 문제가 발생하여 입력취소");
			this.con.rollback();	//입력사항 취소
		}
		this.st.close();
		//this.ps.close();
		this.con.close();
		return this.msg;	//Controller로 값을 넘겨줌
	}
	
	
	
	//항공정보 입력파트
	protected int insert(String rcode,String rair, String rland, String rperson, String rpay, String rstart_date,String rdate, String rend_date) {
		try {
			this.con = this.db.info();
			this.sql = "insert into air_reserve values('0',?,?,?,?,?,?,?,?,now())";
			this.ps = this.con.prepareStatement(this.sql);
			this.ps.setString(1, rcode);
			this.ps.setString(2, rair);
			this.ps.setString(3, rland);
			this.ps.setString(4, rperson);
			this.ps.setString(5, rpay);
			this.ps.setString(6, rstart_date);
			this.ps.setString(7, rend_date);
			this.ps.setString(8, rdate);
			this.msg = this.ps.executeUpdate();
			this.ps.close();
			this.con.close();
			
		}catch(Exception e) {
			System.out.println("Database Connection Error");
			System.out.println(e);
		}
		return this.msg;
	}
	
}

