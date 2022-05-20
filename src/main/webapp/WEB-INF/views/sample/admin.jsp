<%--
  Created by IntelliJ IDEA.
  User: cooki
  Date: 2022-05-13
  Time: 오전 10:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<sec:authorize access="isAnonymous()">
    <a href="/customLogin">로그인</a>
</sec:authorize>

<sec:authorize access="isAuthenticated()">
    <a href="/customLogout">로그아웃</a>
</sec:authorize>

<sec:authorize access="hasRole('ROLE_ADMIN')">
    <a href="/customLogout">관리자 링크 </a>
</sec:authorize>

    <h1>ADMIN</h1>
<h1><sec:authentication property="principal.nickname"/></h1>
<h1><sec:authentication property="principal"/></h1>
</body>
</html>
