<%--
  Created by IntelliJ IDEA.
  User: DeepBlue
  Date: 2019/8/17
  Time: 14:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>文件上传</title>
</head>
<body>
<form method="post" action="./file" enctype="multipart/form-data">
    <input type="file" name="file" value="选择要上传的文件">
    <input type="submit" value="提交">
</form>
</body>
</html>
