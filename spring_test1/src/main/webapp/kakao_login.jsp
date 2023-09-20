<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>카카오 로그인 API 활용</title>
<script src="https://t1.kakaocdn.net/kakao_js_sdk/v1/kakao.js"></script>
</head>
<body>
	<p>로그인</p>
	<form id="f" method="post" action="./kakao_loginok.do">
		<input type="hidden" name="part" value="web">
		<input type="hidden" name="kakao_id" value="">
		<input type="hidden" name="kakao_email" value="">
		<input type="hidden" name="kakao_nick" value="">
		
		아이디 : <input type="text" name="mname">
		패스워드 : <input type="password" name="mpass">
		<input type="submit" value="로그인">
	</form>
	<br><br>
	<div id="kako_login" style="width:400px; height:70px; background-color:orange; cursor:pointer;">
	
	<span style="width:50px; display:inline-block;">
		<img src="./img/talk.png" style="width:100%">
	</span>
	
	<span style="display:inline-block;">카카오 로그인</span>
	</div>
</body>
<script>
document.querySelector("#kako_login").addEventListener("click",function(){
	Kakao.Auth.login({
		success: function(response){
			Kakao.API.request({
				url : '/v2/user/me',
				success:function(response){
					let id = response["id"];
					let email = response["kakao_account"]["email"];
					let nickname = response["properties"]["nickname"];
					
					f.part.value = 'kakao';
					f.kakao_id.value = id;
					f.kakao_email.value = email;
					f.kakao_nick.value = nickname;
					//f.submit();
					console.log(response);
				},
				fail:function(error){
					console.log('카카오 API 접속 오류');
				}
			});
		},
		fail:function(error){
			console.log("카카오 key 접속 오류");
		}
	});
});
	Kakao.init('fe4df00cd1fe2686b9df3b82dad46a46');
</script>
</html>