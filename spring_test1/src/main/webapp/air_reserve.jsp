<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	long time = System.currentTimeMillis();
	SimpleDateFormat today = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	String check = today.format(time);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>항공리스트 등록 페이지 - JS + ES</title>
</head>
<body>
	<h2>항공 리스트</h2>
	<form id="f" method="post" action="./air_rserveok.do" enctype="application/x-www-form-urlencoded">
		<input type="text" name="rcode" placeholder="비행기 코드 넘버(10~14자리)" maxlength="14"> <br>
		<select name="rair">
			<option value="ANA항공">ANA항공</option>
			<option value="오케이항공">오케이항공</option>
			<option value="인디고항공">인디고항공</option>
			<option value="중화항공">중화항공</option>
			<option value="에어아시아">에어아시아</option>
			<option value="대한항공">대한항공</option>
			<option value="아시아나항공">아시아나항공</option>
			<option value="제주항공">제주항공</option>
		</select> <br>
		<select name="rland">
			<option value="한국">한국</option>
			<option value="일본">일본</option>
			<option value="중국">중국</option>
			<option value="베트남">베트남</option>
			<option value="말레시아">말레시아</option>
			<option value="크로아티아">크로아티아</option>
			<option value="이집트">이집트</option>
			<option value="터키">터키</option>
		</select> <br>
		<input type="datetime-local" name="rdate"> <br>
		<input type="text" name="rperson" placeholder="예약 가능 인원수를 입력하세요" onkeyup="air_abc(event)" maxlength="3"> <br>
		<input type="text" name="rpay" placeholder="1인 기준 항공료" onkeyup="air_abc(event)"><br>
		예매시작시간 : <input type="datetime-local" name="rstart_date"> <br>
		예매종료시간 : <input type="datetime-local" name="rend_date"> <br>
		<input type="button" value="여행항공 정보 입력완료" id="btn">
	</form>
</body>
<!-- 
onkeypress : keydown 되었을 때 값을 가져옴 (특수키 X)
onkeydown : keydown 되었을 떄 값을 가져옴 (특수키 O)
onkeyup : keydown 후 ketup이 되었을 경우 값을 가져옴
 -->
<script>
window.onload = function(){
	var bw = navigator.userAgent.toUpperCase();
	if(bw.indexOf("FIREFOX") >= 0){
		alert("해당 브라우져로는 접속을 차단합니다.");
	}
}
	function air_abc(event){
		if(event.key >= 0 || event.key <= 9){
			return;
		}else{
			alert("숫자값만 입력하세요.");
			f.rpay.value = "";
		}
	}
	/*
	var today = new Date();
	console.log(today.getFullYear());
	console.log(today.getMonth()+ 1);
	console.log(today.getDate());
	console.log(today.getHours());
	console.log(today.getMinutes());
	console.log(today.getSeconds());
	*/
	
	const times = "<%=check%>";
	console.log(times);
	
	document.querySelector("#btn").addEventListener("click",function(){
		/*
			/내용/g : g = Global
			정규표현식 방법으로 데이터값을 체크, 치환, 삭제 등 삭제 사능
		*/
		var s = f.rstart_date.value.replaceAll(/-|T|:/g,"");
		var e = f.rend_date.value.replaceAll(/-|T|:/g,"");
		console.log(s);
		
		if(f.rcode.value == ""){
			alert("비행기 코드 넘버 10~14자리 입력하세요");
		}else if(f.rdate.value == ""){
			alert("출발일자를 선택하세요.");
		}else if(f.rdate.value <= times){
			alert("출발일자 시간이 정확하지 않습니다.");
		}else if(Number(s) >= Number(e)){
			alert("예매시간이 잘못 셋팅 되었습니다.")
			f.rstart_date.value="";
			f.rend_date.value="";
		}else{
			f.submit();
		}
			
	});
</script>

</html>