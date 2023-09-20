package sp1;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;

/*
 Thread 을 사용할땐 필수적으로 "동기화 형태 프로그램 개발"되어 있어야 한다
 
 Web Programmer
 Thread : X (Block, Lock)
 
 APP Programmer
 Thread : O 
*/
public class login_etc extends Thread {
	Connection con = null;
	PreparedStatement ps = null;
	String a_id = ""; //web : mname
	String b_email = ""; //web : mpass
	String c_nick = "";
	String part = "";
	int oksign = 0;		//처리 유/무
	
	//setter
	public login_etc(String a,String b,String c,String part) {
		this.part = part;
		if(part=="web") {
			this.a_id = a ;
			this.b_email = b;
		}
		else {
			this.a_id = a ;
			this.b_email = b;
			this.c_nick = c;
		}
		this.start();    //Thread 실행으로 Database 저장시킴
		this.interrupt();  //로스 나는것을 방지, 로그인 할떈 Thread 쓰지 말자
	}
	
	//getter
	protected int result() {
		//System.out.println("return method 값 : " + this.oksign);
		return this.oksign;
	}
	
	@Override
	public void run() { //data 저장
		//MessageDigest : 해당 문자열을 암호화 형태 구성 클래스
		//md5, sha-1, sha-224, sha-256, sha-384, sha-512
		//System.out.println(this.a_id+" / "+this.b_email+" / "+this.c_nick+" / "+this.part);
		try {
			String pw = this.b_email;
			
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update(pw.getBytes());	//해당 암호화 bit로 변경작업
			byte[] se = md.digest();	//byte로 저장
			StringBuilder sb = new  StringBuilder();
			for(byte s: se) {
				sb.append(String.format("%02x", s));  
				//(%02x)2자리 문자로 변환 : 0숫자를 넣어서 2자리를 제작 -> 헥사코드
				//(%01x)1자리 문자로 변환 : 1자리 출력 (1,2,3,4)
			}
			//insert시 패스워드 저장은 sb 객체를 저장시킴
			
			//가입
			this.con = new dbconfig().info();
			String sql = "insert into login values('0',?,?,?,?,?,?,now())";
			this.ps = this.con.prepareStatement(sql);
			/* 직접 코드를 작성 해보시길 바랍니다 */
			this.ps.setString(1, this.part);
			this.ps.setString(2, this.a_id);
			this.ps.setString(3, this.c_nick);
			this.ps.setString(4, sb.toString());
			this.ps.setString(5, this.b_email);
			this.ps.setString(6, this.c_nick);
			
			this.oksign = this.ps.executeUpdate();  //getter에 return값을 만들어 주는 코드
			//System.out.println("Thread값 : " + this.oksign);
			this.ps.close();
			this.con.close();

		}catch(Exception e) {
			System.out.println("Database 오류발생");
		}
	}
}
