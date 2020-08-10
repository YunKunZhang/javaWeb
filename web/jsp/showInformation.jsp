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
<c:set value="${pageContext.request.session.getAttribute('identity')}" var="identity"></c:set>
<div class="cztable">
    <table width="100%" cellpadding="0" cellspacing="0">
        <caption>个人信息</caption>
        <tr>
            <c:choose>
                <c:when test="${identity=='student'}">
                    <td align="right" width="140">学号：</td>
                </c:when>
                <c:when test="${identity=='teacher'}">
                    <td align="right" width="140">教师号：</td>
                </c:when>
                <c:when test="${identity=='administrator'}">
                    <td align="right" width="140">工作号：</td>
                </c:when>
            </c:choose>
            <td><c:out value="${pageContext.request.getAttribute('info').num}"/></td>
            <td align="right" width="140">姓名：</td>
            <td id="stuName"><c:out value="${pageContext.request.getAttribute('info').name}"/></td>
        </tr>
        <tr>
            <td align="right">性别：</td>
            <td id="stuSex"><c:out value="${pageContext.request.getAttribute('info').sex}"/></td>
            <c:choose>
                <c:when test="${identity!='administrator'}">
                    <td align="right">所在院系:</td>
                    <td id="stuDep"><c:out value="${pageContext.request.getAttribute('info').department}"/></td>
                </c:when>
                <c:when test="${identity=='administrator'}">
                    <td align="right">身份:</td>
                    <td id="stuIde"><c:out value="${pageContext.request.getAttribute('info').identity}"/></td>
                </c:when>
            </c:choose>
        </tr>
        <c:if test="${identity=='student'}">
            <tr>
                <td align="right">学习专业:</td>
                <td id="stuMaj"><c:out value="${pageContext.request.getAttribute('info').major}"/></td>
                <td align="right">入学日期:</td>
                <td><c:out value="${pageContext.request.getAttribute('info').enterDate}"/></td>
            </tr>
        </c:if>
        <tr>
            <td align="right">出生日期:</td>
            <td id="stuBir"><c:out value="${pageContext.request.getAttribute('info').birthday}"/></td>
            <td align="right">联系电话:</td>
            <td id="stuPho"><c:out value="${pageContext.request.getAttribute('info').phone}"/></td>
        </tr>
        <c:if test="${identity=='teacher'}">
            <td align="right">职称:</td>
            <td><c:out value="${pageContext.request.getAttribute('info').rank}"/></td>
        </c:if>
        <tr align="center">
            <td colspan="5" height="40">
                <div align="center">
                    <button id="changeInfo" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal">
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
                    <c:if test="${identity!='administrator'}">
                        <div class="form-group">
                            <label>所在院系</label>
                            <div class="form-group">
                                <label>所在院系</label>
                                <div class="input-group">
                                    <div class="input-group-btn">
                                        <button type="button" class="btn btn-default dropdown-toggle"
                                                data-toggle="dropdown">
                                            院系
                                            <span class="caret"></span>
                                        </button>
                                        <ul class="dropdown-menu" id="department">
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
                                    <input type="text" id="dep" name="department" class="form-control" autocomplete="off"
                                           readonly="readonly">
                                </div>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${identity=='student'}">
                        <div class="form-group">
                            <lebel>所学专业</lebel>
                            <div class="input-group">
                                <div class="input-group-btn">
                                    <button type="button" class="btn btn-default dropdown-toggle"
                                            data-toggle="dropdown" id="majorButtnon">
                                        专业
                                        <span class="caret"></span>
                                    </button>
                                    <ul class="dropdown-menu" id="profession">
                                    </ul>
                                </div><!-- /btn-group -->
                                <input type="text" id="major" name="major" class="form-control" autocomplete="off"
                                       readonly="readonly">
                            </div>
                        </div>
                    </c:if>
                    <div class="form-group">
                        <lebel>出生日期</lebel>
                        <div class="input-group date form_date" data-date="" data-date-format="yyyy-mm-dd">
                            <input type="text" id="birthday" class="form-control" name="birthday" readonly="readonly">
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
</body>
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

    window.onload = function () {
        //获取表单初始数据
        let TextInit;
        //正则表达式：只能是汉字
        let reg1 = /^[\u0391-\uFFE5]+$/;
        //正则表达式：验证手机号
        let reg2 = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;

        <c:if test="${identity!='administrator'}">
        //为院系按钮添加鼠标监听
        $('#department a').click(function () {
            $('#dep').val($(this).text());
            ajaxMajor($('#dep').val());
        });
        </c:if>

        //为“修改信息”按钮添加鼠标监听
        $('#changeInfo').click(function () {
            addInfo();
            TextInit = $("#form").serialize();
        });


        //将表格中的数据填入修改表格中(学生端)
        function addInfo() {
            $('#name').val($('#stuName').html());
            $('#stuSex').html() == "男" ? $('#male-person').attr("checked", "checked") : $('#female-person').attr("checked", "checked");
            <c:if test="${identity!='administrator'}">
            $('#dep').val($('#stuDep').html());
            </c:if>
            <c:if test="${identity=='student'}">
            $('#major').val($('#stuMaj').html());
            </c:if>
            $('#birthday').val($('#stuBir').html());
            $('#phone').val($('#stuPho').html());
        }

        //将修改表格中的数据填入表格中
        function updateInfo() {
            $('#stuName').html($('#name').val());
            $('#stuSex').html($("input[name='sex']:checked").val());
            <c:if test="${identity!='administrator'}">
            $('#stuDep').html($('#dep').val());
            </c:if>
            <c:if test="${identity=='student'}">
            $('#stuMaj').html($('#major').val());
            </c:if>
            $('#stuBir').html($('#birthday').val());
            $('#stuPho').html($('#phone').val());
        }

        //为“提交”按钮添加鼠标监听
        $('#change').click(function () {
            let Text = $('#form').serialize();
            if (Text == TextInit) {
                alert("没有更改无需提交");
            } else {
                verify(Text);
            }
        });

        //对修改表格中的数据进行验证
        function verify(Text) {
            let flag1 = reg1.test($('#name').val());
            let flag2 = reg2.test($('#phone').val());
            let date = new Date(Date.parse($('#birthday').val()));
            let today = new Date();
            if (!flag1) {
                alert("姓名格式错误，请重新填写");
            } else if (!flag2) {
                alert("电话格式错误，请重新填写");
            } else if (date == null) {
                alert("日期不能为空");
            } else if (date > today) {
                alert("日期不合法");
            } else {
                //ajax发送请求，修改信息
                $('#change').attr("disabled", true);
                ajaxAmend(decodeURIComponent(Text));
                $('#change').attr("disabled", false);
            }
        }

        //Ajax发送请求修改数据库中的数据
        function ajaxAmend(data) {
            $.ajax({
                type: 'POST',
                url: "alertInfo",
                async: false,
                data: {"data": data},
                success: function (data) {
                    if (data == 0) {
                        alert("修改失败");
                    } else {
                        alert("修改成功");
                        $('#myModal').modal('hide');
                        updateInfo();
                    }
                },
                error: function () {
                    alert("错误");
                }
            });
        }

        //发送ajax请求获取专业信息
        function ajaxMajor(department) {
            $.ajax({
                type: "GET",
                url: "management",
                async: true,
                data: {"department": department},
                success: function (result) {
                    if (result == 0) {
                        $('#profession').children().remove();
                        $('#major').val("");
                        alert("没有获取到相应的专业信息");
                    } else {
                        //分离请求中的json数据
                        result = JSON.parse(result);
                        $('#profession').children().remove();
                        for (let i = 0; i < result.length; i++) {
                            $('#profession').append("<li><a href='javascript:;'>" + result[i] + "</a></li>");
                            //为专业按钮添加鼠标监听
                            $('#profession a').click(function () {
                                $('#major').val($(this).text());
                            });
                        }
                    }
                },
                error: function () {
                    alert("请求失败，请稍后再试");
                }
            });
        }

    };
</script>
</html>
