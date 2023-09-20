<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String db_driver = "com.mysql.jdbc.Driver";
String db_url = "jdbc:mysql://umj7-003.cafe24.com/tiggersant";
//String db_url = "jdbc:mysql:/localhost:3306/tiggersant";
String db_user = "tiggersant";
String db_pass = "Qwqw5425";

ResultSet rs=null;

try {
	Class.forName(db_driver);
	Connection con = DriverManager.getConnection(db_url,db_user,db_pass);
	
	String sql = "select * from air_reserve order by rno desc";
	PreparedStatement ps = con.prepareStatement(sql);
	rs = ps.executeQuery();
}
catch(Exception e) {
	out.print("DB접속 오류 발생!!");
}

%>    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비행기 예매 페이지</title>
</head>
<body>
<form id="f" method="post" action="./air_personok.do">
<input type="hidden" name="rcode" value="">
<input type="hidden" name="rair" value="">
<p>비행기 예매</p>
<input type="text" name="mid" placeholder="아이디를 입력하세요"><br>
<input type="text" name="mname" placeholder="고객명을 입력하세요"><br>
<input type="text" name="mpost" placeholder="여권번호를 입력하세요"><br>
<input type="text" name="mtel" placeholder="고객 연락처('-' 미입력)" maxlength="11"><br>
<select name="aircorp" onchange="data(this.value)">
	<option value="">항공사를 선택하세요</option>
	<%while(rs.next()){ %>
	<option value="<%=rs.getString("rcode") %>|<%=rs.getString("rair") %>|<%=rs.getString("rpay") %>"><%=rs.getString("rair") %></option>
	<%} %>
</select><br>
<input type="text" name="mperson" placeholder="인원수를 입력하세요" maxlength="3" onkeyup="person(this.value)"><br>
<p>총 항공료</p>
<input type="text" name="totalmoney" readonly="readonly" value="0"><br>
<input type="button" value="예매완료" id="btn">
</form>
</body>
<script>
var ori_money;
function data(z){
	var a = z.split("|");
	//console.log(a);
	f.rcode.value = a[0];	//비행기코드
	f.rair.value = a[1];	//총 금액
	f.totalmoney.value = a[2];	//항공사
	ori_money = a[2];	//1인 기준 금액
	f.mperson.value=1;	//해당 항공사 변경시 인원 초기화
}
function person(p){
	var sum = Number(p) * Number(ori_money);
	f.totalmoney.value = sum;
}
document.querySelector("#btn").addEventListener("click",function(){
	if(confirm("예약을 확정 하시겠습니까>")){
		f.submit();
	};
});

</script>
</html>
