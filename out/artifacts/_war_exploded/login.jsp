<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>登录页面</title>
    <link rel="stylesheet" type="text/css" href="com/css/loginStyle.css">
    <script type="text/javascript" src="com/js/loginJS/jquery.min.js"></script>
</head>
<body>
<div class="container">
    <div class="info">
        <h1>学生信息管理系统</h1><span>张玉坤 <i class="fa fa-heart"></i> 制作</span>
    </div>
</div>
<div class="form">
    <div class="thumbnail"><img src="com/image/loginImage/hat.svg"/></div>
    <form class="register-form" method="post">
        <input type="text" name="num" autocomplete="off" placeholder="账号(工作号、教师号、学号)"/>
        <input type="password" name="password" autocomplete="off" placeholder="新的密码(任意数字、字母6-18位)"/>
        <input type="password" name="confirm" autocomplete="off" placeholder="确认密码"/>
        <input type="password" name="answer" autocomplete="off" placeholder="你最喜欢的食物(密保答案)"/>
        <select name="identity">
            <option value="administrator">管理员</option>
            <option value="student">学生</option>
            <option value="teacher">教师</option>
        </select>
        <button type="button" value="修改">修改</button>
        <p class="message">想起来了？ <a href="#">返回</a></p>
    </form>
    <form class="login-form" method="post">
        <input type="text" name="num" autocomplete="on" placeholder="账号(工号、教师号、学号)"/>
        <input type="password" name="password" autocomplete="off" placeholder="密码"/>
        <select name="identity">
            <option value="administrator">管理员</option>
            <option value="student">学生</option>
            <option value="teacher">教师</option>
        </select>
        <button type="button" value="登录">登录</button>
        <p class="message">忘记密码了？<a href="#">密码找回</a></p>
    </form>
</div>
</body>
<script type="text/javascript" src="com/js/loginJS/login.js"></script>
</html>
