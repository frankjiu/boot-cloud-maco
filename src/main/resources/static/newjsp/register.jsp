<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <base href="<%=basePath%>">

        <title>My JSP 'index.jsp' starting page</title>
        <meta http-equiv="pragma" content="no-cache">
        <meta http-equiv="cache-control" content="no-cache">
        <meta http-equiv="expires" content="0">
        <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
        <meta http-equiv="description" content="This is my page">
        <!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->
    </head>

    <body> 
        <center>
            <fieldset style="width: 500px;">
                <legend> 
                    注册 
                </legend>
                <form action="RegisterServlet" method="post">
                    <table>
                        <tr>
                            <td>
                                用户名:
                            </td>
                            <td>
                                <input type="text" name="sname"/>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                密码:
                            </td>
                            <td>
                                <input type="password" name="password"/>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                性别:
                            </td>
                            <td>
                                    <input type="radio" name="sex" value="男" checked="checked"/>男
                                    <input type="radio" name="sex" value="女"/>女
                            </td>
                        </tr>
                        <tr>
                            <td>
                                班级编号:
                            </td>
                            <td>
                                <select name="cid">
                                  <option value="1">TB07</option>
                                  <option value="2">TB13</option>
                                  <option value="3">TB24</option>
                                  <option value="4">TB31</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <input type="submit" value="注册" />
                            </td>
                            <td>
                                <input type="reset" value="重置" />
                            </td>
                        </tr>
                    </table>
                </form>
            </fieldset>
        </center>
    </body>
</html>