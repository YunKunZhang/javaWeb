<%--
  Created by IntelliJ IDEA.
  User: 12697
  Date: 2020/4/22
  Time: 20:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="com/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="com/js/studentJS/jquery.min.js"></script>
    <script src="com/js/studentJS/bootstrap.min.js"></script>
    <script type="text/javascript" src="com/js/studentJS/bootstrap-datetimepicker.js" charset="UTF-8"></script>
    <style type="text/css">
        * {
            margin: 0;
            padding: 0;
            word-break: break-all;
            word-wrap: break-word;
            font-family: "Microsoft Yahei", Verdana, Arial, Helvetica, sans-serif;
        }

        .cztable {
            padding: 50px;
        }

        .cztable table {
            border-bottom: #cfe1f9 solid 4px;
            border-right: #cfe1f9 solid 1px;
        }

        .cztable td, .cztable th {
            font: 2em bold white;
            border: #cfe1f9 solid 1px;
            padding: 10px 8px;
        }

        .cztable td {
            height: 90px;
        }

        .cztable td a {
            color: #185697;
        }

        .cztable .xxleft {
            text-align: left;
        }
    </style>
</head>
<body>
<div class="cztable">
    <table width="100%" cellpadding="0" cellspacing="0">
        <caption>个人信息</caption>
        <tr>
            <td align="right" width="140">学号：</td>
            <td><c:out value="${pageContext.request.getAttribute('info').num}"/></td>
            <td align="right" width="140">姓名：</td>
            <td id="stuName"><c:out value="${pageContext.request.getAttribute('info').name}"/></td>
        </tr>
        <tr>
            <td align="right">性别：</td>
            <td id="stuSex"><c:out value="${pageContext.request.getAttribute('info').sex}"/></td>
            <td align="right">所在院系:</td>
            <td id="stuDep"><c:out value="${pageContext.request.getAttribute('info').department}"/></td>
        </tr>
        <tr>
            <td align="right">学习专业:</td>
            <td id="stuMaj"><c:out value="${pageContext.request.getAttribute('info').major}"/></td>
            <td align="right">出生日期:</td>
            <td id="stuBir"><c:out value="${pageContext.request.getAttribute('info').birthday}"/></td>
        </tr>
        <tr>
            <td align="right">入学日期:</td>
            <td ><c:out value="${pageContext.request.getAttribute('info').enterDate}"/></td>
            <td align="right">联系电话:</td>
            <td id="stuPho"><c:out value="${pageContext.request.getAttribute('info').phone}"/></td>
        </tr>
        <tr align="center">
            <td colspan="5" height="40">
                <div align="center">
                    <button id="changeInfo"class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal">
                        修改信息
                    </button>
                </div>
            </td>
        </tr>
    </table>
</div>
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" id="myModalLabel">
                    信息修改
                </h4>
            </div>
            <div class="modal-body">
                <form class="form" role="form" id="form">
                    <div class="form-group">
                        <lebel>姓名</lebel>
                        <input type="text" id="name" name="name" class="form-control" autocomplete="off">
                    </div>
                    <div class="form-group">
                        <label>性别</label>
                        <lebel class="radio-inline">
                            <input type="radio" name="sex" id="male-person" value="男">男
                        </lebel>
                        <lebel class="radio-inline">
                            <input type="radio" name="sex" id="female-person" value="女">女
                        </lebel>
                    </div>
                    <div class="form-group">
                        <label>所在院系</label>
                        <div class="input-group">
                            <div class="input-group-btn">
                                <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                    院系
                                    <span class="caret"></span>
                                </button>
                                <ul class="dropdown-menu">
                                    <li><a href="javascript:;">化学化工学院</a></li>
                                    <li><a href="javascript:;">信息工程学院</a></li>
                                    <li><a href="javascript:;">体育学院</a></li>
                                    <li><a href="javascript:;">园艺园林学院</a></li>
                                    <li><a href="javascript:;">食品学院</a></li>
                                    <li><a href="javascript:;">经管学院</a></li>
                                    <li><a href="javascript:;">机电学院</a></li>
                                    <li><a href="javascript:;">资源与环境学院</a></li>
                                    <li><a href="javascript:;">文法学院</a></li>
                                    <li><a href="javascript:;">体育学院</a></li>
                                    <li><a href="javascript:;">外国语学院</a></li>
                                    <li><a href="javascript:;">数学学院</a></li>
                                    <li><a href="javascript:;">服装学院</a></li>
                                    <li><a href="javascript:;">教育学院</a></li>
                                </ul>
                            </div>
                            <input type="text" id="dep" name="department" class="form-control" autocomplete="off">
                        </div>
                    </div>
                    <div class="form-group">
                        <lebel>所学专业</lebel>
                        <div class="input-group">
                            <div class="input-group-btn">
                                <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                    专业
                                    <span class="caret"></span>
                                </button>
                                <ul class="dropdown-menu">
                                    <!--                                    <li><a href="javascript:;">专业1</a></li>-->
                                    <!--                                    <li><a href="javascript:;">专业2</a></li>-->
                                    <!--                                    <li><a href="javascript:;">专业3</a></li>-->
                                    <!--                                    <li><a href="javascript:;">专业4</a></li>-->
                                </ul>
                            </div><!-- /btn-group -->
                            <input type="text" id="major" name="major" class="form-control" autocomplete="off">
                        </div>
                    </div>
                    <div class="form-group">
                        <lebel>出生日期</lebel>
                        <div class="input-group date form_date" data-date="" data-date-format="yyyy-mm-dd">
                            <input type="text" id="birthday" class="form-control" name="birthday" disabled="disabled">
                            <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                            <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <lebel>联系电话</lebel>
                        <input type="text" id="phone" name="phone" class="form-control" placeholder="请输入11位的号码"
                               autocomplete="off">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                </button>
                <button type="button" id="change" class="btn btn-primary">
                    提交
                </button>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $('.form_date').datetimepicker({
        language: 'en',
        weekStart: 1,
        todayBtn: 1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 3,
        minView: 2,
        forceParse: 0
    });

    window.onload=function () {
        //获取表单初始数据
        let TextInit;

        //为“修改信息”按钮添加鼠标监听
        $('#changeInfo').click(function () {
            addInfo();
            TextInit=$("#form").serialize();
        });

        //将表格中的数据填入修改表格中
        function addInfo(){
            $('#name').val($('#stuName').html());
            $('#stuSex').html()=="男"?$('#male-person').attr("checked","checked"):$('#female-person').attr("checked","checked");
            $('#dep').val($('#stuDep').html());
            $('#major').val($('#stuMaj').html());
            $('#birthday').val($('#stuBir').html());
            $('#phone').val($('#stuPho').html());
        }

        //为“提交”按钮添加鼠标监听
        $('#change').click(function () {
            let Text=$('#form').serialize();
            if(Text==TextInit){
                alert("没有更改无需提交");
            }else{
                //ajax发送请求，修改信息
                ajaxAmend(decodeURIComponent(Text));
                $('#myModal').modal('hide');
            }
        });

        //Ajax发送请求修改数据库中的数据
        function ajaxAmend(data){
            $.ajax({
                type:'POST',
                url:"modify",
                async:true,
                data:{"data":data,"birthday":$('#birthday').val()},
                success:function (data) {
                    if("0"==data){
                        alert("修改失败");
                    }else{
                        alert("修改成功");
                    }
                },
                error:function () {
                    alert("错误");
                }
            });
        }
    };
</script>
</body>
</html>
