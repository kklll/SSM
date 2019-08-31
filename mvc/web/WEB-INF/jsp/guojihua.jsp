<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: DeepBlue
  Date: 2019/8/20
  Time: 17:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>国际化</title>
</head>
<body>
<h2>
    <spring:message code="welcome"/>
</h2>
Locale:${pageContext.request.locale}
</body>
</html>
