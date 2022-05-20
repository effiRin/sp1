<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Title</title>
</head>
<body>
<h1> 커스텀 로그인 페이지 </h1>
<form action="/login" method="post">
  <input name="username">
  <input name="password">
  <input type="checkbox" name="remember-me"> 로그인 기억하기
  <button>LOGIN</button>
</form>
</body>
</html>