<%--
  Created by IntelliJ IDEA.
  User: 12697
  Date: 2020/4/25
  Time: 11:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>修改密码</title>
    <link rel="stylesheet" href="com/css/bootstrap.min.css">
    <script src="com/js/studentJS/jquery.min.js"></script>
    <script src="com/js/studentJS/bootstrap.min.js"></script>
    <style type="text/css">
        * {
            margin: 0;
            padding: 0;
            word-break: break-all;
            word-wrap: break-word;
            font-family: "Microsoft Yahei", Verdana, Arial, Helvetica, sans-serif;
        }

        h2 {
            padding: 20px 0;
        }

        div {
            margin: 0 auto;
            padding: 100px 100px 0px;
            width: 90%;
        }

        button {
            position: absolute;
            left: 46%;
        }

        #changeAnswer {
            position: absolute;
            left: 5%;
            top: 0;
            z-index: 2;
            display: none;
        }
    </style>
</head>
<body>
<c:set var="num" value="${pageContext.request.getAttribute('info')}"></c:set>
<div id="changePassword">
    <h2 class="text-center">修改密码</h2>
    <form class="bs-example bs-example-form" role="form">
        <div class="input-group input-group-lg">
            <span class="input-group-addon">新密码&nbsp;&nbsp;&nbsp;</span>
            <input type="password" class="form-control" id="password" autocomplete="off" placeholder="8-16位任意数字字母">
        </div>
        <br>
        <div class="input-group input-group-lg">
            <span class="input-group-addon">确认密码</span>
            <input type="password" class="form-control" id="confirm" autocomplete="off" placeholder="8-16位任意数字字母">
        </div>
        <br>
        <div class="input-group input-group-lg">
            <span class="input-group-addon">密保答案</span>
            <input type="password" class="form-control" id="answer" autocomplete="off" placeholder="你最喜欢的食物">
        </div>
        <div class="input-group">
            <a href="javascript:;" style="position:relative; left: 35%">修改密保答案(一个账号只能修改一次)</a>
        </div>
        <div class="input-group input-group-lg">
            <button type="button" class="btn btn-primary btn-lg" id="modifyPassword">修改</button>
        </div>
    </form>
</div>
<div id="changeAnswer">
    <h2 class="text-center">修改密保</h2>
    <form class="bs-example bs-example-form" role="form">
        <div class="input-group input-group-lg">
            <span class="input-group-addon">旧密保答案&nbsp;</span>
            <input type="password" class="form-control" id="oldAnswer" autocomplete="off" placeholder="你最喜欢的食物">
        </div>
        <br>
        <div class="input-group input-group-lg">
            <span class="input-group-addon">新密保答案</span>
            <input type="password" class="form-control" id="newAnswer" autocomplete="off">
        </div>
        <br>
        <div class="input-group input-group-lg">
            <span class="input-group-addon">确认密保答案</span>
            <input type="password" class="form-control" id="confirmAnswer" autocomplete="off">
        </div>
        <div class="input-group">
            <a href="javascript:;" style="position:relative; left: 47%">返回</a>
        </div>
        <div class="input-group input-group-lg">
            <button type="button" class="btn btn-primary btn-lg" id="modifyAnswer">修改</button>
        </div>
    </form>
</div>
</body>
<script type="text/javascript">
    $(document).ready(function () {
        //正则表达式：验证密码
        const reg = new RegExp("^[0-9-A-Z-a-z]{6,18}$");

        //为修改密码按钮添加事件监听
        $('#modifyPassword').click(function () {
            let password = $('#password').val();
            let answer = $('#answer').val();

            let flag1 = reg.test(password);
            let flag2 = (password == $('#confirm').val());
            if (!flag1) {
                alert("密码格式错误");
            } else if (!flag2) {
                alert("前后密码不一致");
            } else {
                //发送请求，修改密码
                $('#modifyPassword').attr("disabled", true);
                ajaxChange(password, answer);
                $('#modifyPassword').attr("disabled", false);
            }
        });

        //ajax发送请求修改密码
        function ajaxChange(password, answer) {
            $.ajax({
                type: "POST",
                url: "change",
                async: false,
                data: {"password": password, "answer": answer, "num":${num}},
                success: function (result) {
                    if (result == 0) {
                        alert("密保错误");
                    } else if (result == 2) {
                        alert("后台异常，请稍后重试");
                    } else {
                        alert("修改成功");
                    }
                },
                error: function () {
                    alert("修改异常，请重新修改");
                }
            });
        }

        //为“修改密保答案”添加事件监听器
        $('#changePassword a').click(function () {
            $('#changePassword').css("display", "none");
            $('#changeAnswer').css("display", "block");
        });

        //为“返回”添加事件监听
        $("#changeAnswer a").click(function () {
            $('#changeAnswer').css("display", "none");
            $('#changePassword').css("display", "block");
        });

        //为修改密保按钮添加事件监听
        $('#modifyAnswer').click(function () {
            let oldAnswer = $('#oldAnswer').val();
            let newAnswer = $('#newAnswer').val();
            let confirmAnswer = $('#confirmAnswer').val();

            if (oldAnswer == "" || newAnswer == "" || confirmAnswer == "") {
                alert("请输入数据");
            } else if (newAnswer != confirmAnswer) {
                alert("前后密保不一致");
            } else {
                $('#modifyAnswer').attr("disabled", true);
                ajaxModify(oldAnswer, newAnswer);
                $('#modifyAnswer').attr("disabled", false);
            }
        });

        //发送ajax请求修改密保
        function ajaxModify(oldAnswer, newAnswer) {
            $.ajax({
                type: "POST",
                url: "change",
                async: false,
                data: {"answer": oldAnswer, "newAnswer": newAnswer, "num":${num}},
                success: function (result) {
                    if (result == 0) {
                        alert("原密保答案错误");
                    } else if (result == -1) {
                        alert("后台异常，请稍后重试");
                    } else {
                        alert("修改成功");
                    }
                },
                error: function () {
                    alert("修改异常，请重新修改");
                }
            });
        }
    });
</script>
</html>
