<%--
  Created by IntelliJ IDEA.
  User: DeepBlue
  Date: 2019/8/18
  Time: 17:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>时间</title>
</head>
<body>
<form id="form" action="/date">
    <table>
        <tr>
            <td>日期</td>
            <td>
                <label for="date"></label><input id="date" name="date1" type="text" value="2019-01-20">
            </td>
        </tr>
        <tr>
            <td>日期</td>
            <td>
                <label for="amount"></label><input id="amount" name="amount1" type="text" value="200000">
            </td>
        </tr>
        <tr>
            <td>
            </td>
            <td align="right"><input id="commit" type="submit" value="提交"></td>
        </tr>
    </table>
</form>
</body>
</html>
