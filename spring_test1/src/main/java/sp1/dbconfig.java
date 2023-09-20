package sp1;

import java.sql.Connection;
import java.sql.DriverManager;

public class dbconfig {
	
	public static Connection info() throws Exception{
		String db_driver = "com.mysql.jdbc.Driver";
		String db_url = "jdbc:mysql://umj7-003.cafe24.com/tiggersant";
		//String db_url = "jdbc:mysql:/localhost:3306/tiggersant";	//배포할때는 localhost 사용
		
		String db_user = "tiggersant";
		String db_pass = "Qwqw5425";
		Class.forName(db_driver);
		Connection con = DriverManager.getConnection(db_url,db_user,db_pass);
		return con;
	}
	
}