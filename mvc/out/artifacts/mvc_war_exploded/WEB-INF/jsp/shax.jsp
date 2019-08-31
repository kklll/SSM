<%@ page import="com.Pojo.Person" %><%--
  Created by IntelliJ IDEA.
  User: DeepBlue
  Date: 2019/7/31
  Time: 17:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<% Person p= (Person) session.getAttribute("person");
out.println(p.getName());
out.println(p.getMoney());
out.println(p.getBirth());
int id= (int) session.getAttribute("id");
out.println(id);
%>
</body>
</html>
