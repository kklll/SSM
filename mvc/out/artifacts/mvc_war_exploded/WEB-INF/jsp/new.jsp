<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8" http-equiv="Content-Type" content="text/html">
    <title>Title</title>
</head>
<body>
123
<%
    request.setAttribute("id", 34);
    request.getRequestDispatcher("./new").forward(request, response);
    response.sendRedirect("./new");
%>
</body>
</html>
