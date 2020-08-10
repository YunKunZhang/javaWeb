<%--
  Created by IntelliJ IDEA.
  User: 12697
  Date: 2020/5/7
  Time: 9:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>人员管理</title>
    <link rel="stylesheet" href="com/css/bootstrap.min.css">
    <link href="com/css/common_style.css" rel="stylesheet" type="text/css">
    <script src="com/js/studentJS/jquery.min.js"></script>
    <script src="com/js/studentJS/bootstrap.min.js"></script>
    <script type="text/javascript" src="com/js/studentJS/bootstrap-datetimepicker.js" charset="UTF-8"></script>
</head>
<body>
<c:set value="${pageContext.request.getAttribute('type')}" var="identity"></c:set>
<div class="cztable">
    <table class="table table-hover table-condensed table-bordered">
        <caption>
            人员管理
            <select name="query" id="choose">
                <option value="student">学生</option>
                <option value="teacher">教师</option>
            </select>

            <select name="query" id="condition1" class="ui_select01">
                <option value="条件">--条件--</option>
                <option value="账号">账号</option>
                <option value="姓名">姓名</option>
                <option value="院系">院系</option>
            </select>
            <select name="query" id="condition2" class="ui_select01">
                <option value="附加条件">--附加条件--</option>
                <option class='extra' value='精确'>精确</option>
                <option class='extra' value='模糊'>模糊</option>
            </select>
            <input type="text" id="import" class="ui_input_txt02" autocomplete="off"/>
            <span id="box_bottom">
                <button class="ui_input_btn01" id="query">查询</button>
                <button class="ui_input_btn01" id="recovery">重置</button>
                <span style="float: right;">
                <button class="ui_input_btn01" id="add" data-toggle="modal" data-target="#myModal">添加</button>
                </span>
            </span>
        </caption>
        <thead>
        <tr>
            <th>账号</th>
            <th>姓名</th>
            <th>性别</th>
            <c:if test="${identity=='student'}">
                <th id="major">专业</th>
            </c:if>
            <th id="department">学院</th>
            <th>出生日期</th>
            <c:if test="${identity=='student'}">
                <th id="enterDate">入学日期</th>
            </c:if>
            <th id="phoneTh">联系电话</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${pageContext.request.getAttribute('info')}" var="temp" begin="0" step="1">
            <tr>
                <td><c:out value="${temp.num}"/></td>
                <td><c:out value="${temp.name}"/></td>
                <td><c:out value="${temp.sex}"/></td>
                <c:if test="${identity=='student'}">
                    <td><c:out value="${temp.major}"/></td>
                </c:if>
                <c:if test="${identity=='teacher'}">
                    <td><c:out value="${temp.rank}"/></td>
                </c:if>
                <td><c:out value="${temp.department}"/></td>
                <td><c:out value="${temp.birthday}"/></td>
                <c:if test="${identity=='student'}">
                    <td><c:out value="${temp.enterDate}"/></td>
                </c:if>
                <td><c:out value="${temp.phone}"/></td>
                <td><a href='#' class="remove">删除</a>
                    <a href='#' data-toggle="modal" data-target="#myModal"
                       class='edit'>操作</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <c:set value="${pageContext.request.getAttribute('amount')}" var="amount"></c:set>
    <div class="ui_tb_h30">
        <div class="ui_flt" style="height: 30px; line-height: 30px;">
            共有
            <span class="numberSum"><c:out value="${amount}"/></span>
            条记录，当前第
            <span id="pageNow">1</span>
            /
            <span class="pageSum"><c:out value="${amount/14}"/></span>
            页
        </div>
        <div class="ui_frt">
            <!-- 如果是第一页，则只显示下一页、尾页 -->

            <input type="button" value="首页" class="ui_input_btn01" id="homePage"/>
            <input type="button" value="上一页" class="ui_input_btn01" id="lastPage"/>
            <input type="button" value="下一页" class="ui_input_btn01" id="nextPage"/>
            <input type="button" value="尾页" class="ui_input_btn01" id="trailerPage"/>
            <!-- 如果是最后一页，则只显示首页、上一页 -->

            转到第<input type="text" id="jumpNumTxt" class="ui_input_txt01" autocomplete="off"/>页
            <input type="button" class="ui_input_btn01" id="jump" value="跳转"/>
        </div>
    </div>

    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel">模态框（Modal）标题</h4>
                </div>
                <div class="modal-body">
                    <form class="form" role="form" id="form">
                        <div class="form-group">
                            <lebel>账号</lebel>
                            <input type="text" id="account" name="account" class="form-control" autocomplete="off"
                                   placeholder="学号（工作号）">
                        </div>
                        <div class="form-group">
                            <lebel>姓名</lebel>
                            <input type="text" id="name" name="name" class="form-control" autocomplete="off"
                                   placeholder="仅支持中文">
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
                                    <button type="button" class="btn btn-default dropdown-toggle"
                                            data-toggle="dropdown">
                                        院系
                                        <span class="caret"></span>
                                    </button>
                                    <ul class="dropdown-menu" id="departmentSelect">
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
                                <input type="text" id="dep" name="department" class="form-control"
                                       autocomplete="off"
                                       readonly="readonly">
                            </div>
                        </div>
                        <div class="form-group" id="none1">
                            <lebel>所学专业</lebel>
                            <div class="input-group">
                                <div class="input-group-btn">
                                    <button type="button" class="btn btn-default dropdown-toggle"
                                            data-toggle="dropdown" id="majorButton">
                                        专业
                                        <span class="caret"></span>
                                    </button>
                                    <ul class="dropdown-menu" id="profession">
                                    </ul>
                                </div><!-- /btn-group -->
                                <input type="text" id="majorInput" name="major" class="form-control"
                                       autocomplete="off"
                                       readonly="readonly">
                            </div>
                        </div>
                        <div class="form-group" id="none2">
                            <lebel>职称</lebel>
                            <div class="input-group">
                                <div class="input-group-btn">
                                    <button type="button" class="btn btn-default dropdown-toggle"
                                            data-toggle="dropdown" id="rankButton">
                                        职称
                                        <span class="caret"></span>
                                    </button>
                                    <ul class="dropdown-menu" id="rankSelect">
                                        <li><a href="javascript:;">助教</a></li>
                                        <li><a href="javascript:;">讲师</a></li>
                                        <li><a href="javascript:;">副教授</a></li>
                                        <li><a href="javascript:;">教授</a></li>
                                    </ul>
                                </div><!-- /btn-group -->
                                <input type="text" id="rankInput" name="rank" class="form-control"
                                       autocomplete="off"
                                       readonly="readonly">
                            </div>
                        </div>
                        <div class="form-group">
                            <lebel>出生日期</lebel>
                            <div class="input-group date form_date" data-date="" data-date-format="yyyy-mm-dd">
                                <input type="text" id="birthdayInput" class="form-control" name="birthday"
                                       readonly="readonly">
                                <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                                <span class="input-group-addon"><span
                                        class="glyphicon glyphicon-calendar"></span></span>
                            </div>
                        </div>
                        <div class="form-group" id="none3">
                            <lebel>入学日期</lebel>
                            <div class="input-group date form_date" data-date="" data-date-format="yyyy-mm-dd">
                                <input type="text" id="enterDayInput" class="form-control" name="enterDay"
                                       readonly="readonly">
                                <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                                <span class="input-group-addon"><span
                                        class="glyphicon glyphicon-calendar"></span></span>
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
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" id="submit">提交</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
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

    $(document).ready(function () {
        $('.pageSum').html(Math.ceil($('.pageSum').html()));
        let identity = "student";//默认学生客户端
        let optionSum = $('.numberSum').html();//课程总数
        let TextInit;//表单内容
        let nowPage = 1;//当前页面
        let ifQuery = false;//是否查询过了
        let ifModify = false;
        let condition1;//查询主条件
        let condition2;//查询附加条件
        let condition3;//输入框内容

        //正则表达式：学号（工作号）验证
        let reg = /^20(\d){9}$/;
        //正则表达式：只能是汉字
        let reg1 = /^[\u0391-\uFFE5]+$/;
        //正则表达式：验证手机号
        let reg2 = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;

        //为“客户端选择”按钮添加事件监听器
        $('#choose').change(function () {
            let type = $(this).find("option:selected").text();
            if ("学生" == type) {
                identity = "student";
                ajaxPerson("student", "selectAll", 1)
            } else {
                identity = "teacher";
                ajaxPerson("teacher", "selectAll", 1)
            }
        });

        //为不同的跳转按钮添加事件监听
        //"首页"按钮
        $('#homePage').click(function () {
            if (nowPage == 1 || optionSum <= 15) {
                return;
            } else {
                if (ifQuery) {
                    ajaxQuery(identity, "query", condition1, condition2, condition3, 1);
                } else {
                    ajaxPerson(identity, "selectAll", 1);
                }
                setPage(1);
            }
        });

        //"上一页"按钮
        $('#lastPage').click(function () {
            if (nowPage == 1 || optionSum <= 15) {
                return;
            } else {
                if (ifQuery) {
                    ajaxQuery(identity, "query", condition1, condition2, condition3, nowPage - 1);
                } else {
                    ajaxPerson(identity, "selectAll", nowPage - 1);
                }
                setPage(nowPage - 1);
            }
        });

        //"下一页"按钮
        $('#nextPage').click(function () {
            if (nowPage == optionSum || optionSum < 15) {
                return;
            } else {
                if (ifQuery) {
                    ajaxQuery(identity, "query", condition1, condition2, condition3, nowPage + 1);
                } else {
                    ajaxPerson(identity, "selectAll", nowPage + 1);
                }
                setPage(nowPage + 1);
            }
        });

        //"尾页"按钮
        $('#trailerPage').click(function () {
            if (nowPage == optionSum || optionSum < 15) {
                return;
            } else {
                if (ifQuery) {
                    ajaxQuery(identity, "query", condition1, condition2, condition3, Math.ceil(optionSum / 15));
                } else {
                    ajaxPerson(identity, "selectAll", Math.ceil(optionSum / 15));
                }
                setPage(Math.ceil(optionSum / 15));
            }
        });

        //"跳转到 页"输入框
        $('#jump').click(function () {
            let input = $('#jumpNumTxt').val();
            if (input < 1 || input > Math.ceil(optionSum / 15)) {
                alert("输入的数据无效，请重新输入");
                $('#jumpNumTxt').val("");
            } else if (input == nowPage) {
                return;
            } else {
                if (ifQuery) {
                    ajaxQuery(identity, "query", condition1, condition2, condition3, input);
                } else {
                    ajaxPerson(identity, "selectAll", input);
                }
                setPage(input);
            }
        });

        //将设置当前页码功能封装为函数
        function setPage(pageNum) {
            $('#pageNow').html(pageNum);
        }

        // 条件查询区域

        //为“查询”按钮添加事件监听
        $('#query').click(function () {
            condition1 = $('#condition1').val();
            condition2 = $('#condition2').val();
            if (condition1 == "条件" && condition2 == "附加条件") {
                alert("请选择查询条件");
            } else {
                condition3 = $('#import').val();
                if (condition3 == "") {
                    alert("输入框不能为空");
                } else {
                    ajaxQuery(identity, "query", condition1, condition2, condition3, 1);
                }
            }
        });

        //为“重置”按钮添加事件监听
        $('#recovery').click(function () {
            if (ifQuery) {
                $("#condition1 option[value='条件']").attr("selected",true);
                $("#condition2 option[value='附加条件']").attr("selected",true);
                $('#import').val("");
                ajaxPerson(identity, "selectAll", 1);
                ifQuery = !ifQuery;
                $('.numberSum').html(optionSum);
                $('.pageSum').html(Math.ceil(optionSum / 15));
            } else if (!ifQuery) {
                alert("没有查询，无需重置");
            }
        });

        //为“添加”按钮添加事件监听器
        $('#add').click(function () {
            TextInit = $('#form').serialize();
            if ("student" == $("#choose").val()) {
                $('#none1').css("display", "block");
                $('#none2').css("display", "none");
                $('#none3').css("display", "block");
            } else {
                $('#none3').css("display", "none");
                $('#none2').css("display", "block");
                $('#none1').css("display", "none");
            }
        });

        //拟态框消失时重置表单
        $('#myModal').on('hidden.bs.modal', function () {
            $('#form')[0].reset();
            ifModify = false;
        });

        //为院系按钮添加鼠标监听
        $('#departmentSelect a').click(function () {
            $('#dep').val($(this).text());
            ajaxMajor($('#dep').val());
        });

        //为职称按钮添加鼠标监听
        $('#rankSelect a').click(function () {
            $('#rankInput').val($(this).text());
        });

        //为“提交”按钮添加事件监听器
        $('#submit').click(function () {
            let Text = $('#form').serialize();
            if (Text == TextInit && ifModify == false) {
                alert("请填写内容");
            } else if (ifModify == true && Text == TextInit) {
                alert("修改内容");
            } else if (ifModify) {
                verify(Text);

            } else {
                verify(Text);
            }
        });

        //提交前信息验证
        function verify(information) {
            let inputAll = decodeURIComponent(information).split('&');
            let flag = true;
            for (let i = 0; i < inputAll.length; i++) {//对表单内容进行验证（不能为空、符合正则表达式）
                let input = inputAll[i].split('=');
                if (input[1] == "" && (identity == 'student' && i != 5) || (identity == 'teacher' && i != 4 && i != 7)) {
                    alert("信息不能为空");
                    flag = false;
                    return;
                } else if (i == 0) {//学号（工作号）验证
                    flag = reg.test(input[1]);
                    if (!flag) {
                        alert("账号格式错误");
                        return;
                    }
                } else if (i == 1) {//姓名验证
                    flag = reg1.test(input[1]);
                    if (!flag) {
                        alert(input[1]);
                        alert("姓名只能为汉字");
                        return;
                    }
                } else if (i == 6 || i == 7) {//日期验证
                    let today = new Date();
                    let date = new Date(Date.parse(input[1]));
                    if (date > today) {
                        flag = false;
                        alert("日期不合法，选取日期不能大于今天");
                        return;
                    }
                } else if (i == inputAll.length - 1) {//电话号码验证
                    flag = reg2.test(input[1]);
                    if (!flag) {
                        alert("电话格式错误");
                        return;
                    }
                }
            }
            if (flag && ifModify == false) {
                ajaxAddPerson(identity, "addPerson", decodeURIComponent(information));
            } else {
                ajaxModifyPerson(identity, "modifyPerson", information);
            }
        }

        //为“删除”按钮添加事件监听
        $('.remove').click(function () {
            ajaxRemove(identity, "remove", $(this).parent().siblings().eq(0).text());
        });

        //为“操作”按钮添加事件监听
        $('.edit').click(function () {
            ifModify = true;
            if ("student" == $("#choose").val()) {
                $('#none1').css("display", "block");
                $('#none2').css("display", "none");
                $('#none3').css("display", "block");
            } else {
                $('#none3').css("display", "none");
                $('#none2').css("display", "block");
                $('#none1').css("display", "none");
            }
            addInfoToModal($(this));
        });

        //发送ajax请求获取不同客户端用户信息
        function ajaxPerson(type, operation, pageNum) {
            $.ajax({
                type: "POST",
                url: "management",
                async: true,
                data: {"type": type, "operation": operation, "pageNum": pageNum},
                success: function (result) {
                    //分离请求中包含的符合条件的课程总数
                    let temp = result.substring(0, result.indexOf("["));
                    if (temp == 0 || temp == "") {
                        $("option[value=" + type + "]").attr("selected", false);
                        alert("没有找到");
                    } else {
                        //分离请求中的json数据
                        result = JSON.parse(result.substring(result.indexOf("[")));
                        addInfo(type, result);

                        $('.numberSum').html(temp);
                        $('.pageSum').html(Math.ceil(temp / 15));
                    }
                },
                error: function () {
                    alert("请求失误，请稍后再试");
                }
            });
        }

        //发送ajax请求查询信息
        function ajaxQuery(type, operation, condition, extra, input, pageNum) {
            $.ajax({
                type: "POST",
                url: "management",
                async: true,
                data: {
                    "type": type,
                    "operation": operation,
                    "condition": condition,
                    "extra": extra,
                    "input": input,
                    "pageNum": pageNum
                },
                success: function (result) {
                    //分离请求中包含的符合条件的课程总数
                    let temp = result.substring(0, result.indexOf("["));
                    if (temp == 0 || temp == "") {
                        $("option[value=" + type + "]").attr("selected", false);
                        alert("没有找到");
                    } else {
                        ifQuery = true;
                        //分离请求中的json数据
                        result = JSON.parse(result.substring(result.indexOf("[")));
                        addInfo(type, result);

                        $('.numberSum').html(temp);
                        $('.pageSum').html(Math.ceil(temp / 15));
                    }
                },
                error: function () {
                    alert("请求失误，请稍后再试");
                }
            });
        }

        //发送ajax请求删除用户信息
        function ajaxRemove(type, operation, num) {
            $.ajax({
                type: "POST",
                url: "management",
                async: true,
                data: {
                    "type": type,
                    "operation": operation,
                    "num": num
                },
                success: function (result) {
                    alert(result);
                    result = result.substring(0, result.indexOf("["));
                    if (result == 0) {
                        alert("删除成功");
                    } else {
                        alert("删除失败，请稍后再试");
                    }
                },
                error: function () {
                    alert("请求失误，请稍后再试");
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
                        $('#majorInput').val("");
                        alert("没有获取到相应的专业信息");
                    } else {
                        //分离请求中的json数据
                        result = JSON.parse(result);
                        $('#profession').children().remove();
                        for (let i = 0; i < result.length; i++) {
                            $('#profession').append("<li><a href='javascript:;'>" + result[i] + "</a></li>");
                            //为专业按钮添加鼠标监听
                            $('#profession a').click(function () {
                                $('#majorInput').val($(this).text());
                            });
                        }
                    }
                },
                error: function () {
                    alert("请求失败，请稍后再试");
                }
            });
        }

        //发送ajax请求向数据库中加入教师（学生）信息
        function ajaxAddPerson(type, operation, information) {
            $.ajax({
                type: "POST",
                url: "management",
                async: true,
                data: {"type": type, "operation": operation, "information": information},
                success: function (result) {
                    result = result.substring(0, result.indexOf("["));
                    if (result == 1) {
                        alert("添加成功");
                        ajaxPerson(identity, "selectAll", 1);
                        $('#myModal').modal('hide');
                    } else {
                        alert("添加失败，请稍后再试");
                    }
                },
                error: function () {
                    alert("请求异常，请稍后再试");
                }
            });
        }

        //发送ajax请求向数据库中修改教师（学生）信息
        function ajaxModifyPerson(type, operation, information) {
            $.ajax({
                type: "POST",
                url: "management",
                async: true,
                data: {"type": type, "operation": operation, "information": decodeURIComponent(information)},
                success: function (result) {
                    result = result.substring(0, result.indexOf("["));
                    if (result == 1) {
                        alert("修改成功");
                        ajaxPerson(identity, "selectAll", 1);
                        $('#myModal').modal('hide');
                    } else {
                        alert("修改失败，请稍后再试");
                    }
                },
                error: function () {
                    alert("请求异常，请稍后再试");
                }
            });
        }

        //添加“教师（学生）”节点信息
        function addInfo(identity, result) {
            $('table>tbody').empty();
            if ("student" == identity) {
                if (!ifQuery) {
                    $('#major').remove();
                    $('#rank').remove();
                    $('#enterDate').remove();
                    $('#department').before(' <th id="major">专业</th>');
                    $('#phoneTh').before(' <th id="enterDate">入学日期</th>');
                }
                for (let i = 0; i < result.length; i++) {
                    let line = "<tr>" +
                        "<td>" + result[i].num + "</td>" +
                        "<td>" + result[i].name + "</td>" +
                        "<td>" + result[i].sex + "</td>" +
                        "<td>" + result[i].major + "</td>" +
                        "<td>" + result[i].department + "</td>" +
                        "<td>" + result[i].birthday + "</td>" +
                        "<td>" + result[i].enterDate + "</td>" +
                        "<td>" + result[i].phone + "</td>" +
                        "<td><a href='#' class='remove'>删除</a>\n" +
                        " <a href='#' data-toggle=\"modal\" data-target=\"#myModal\"\n" +
                        " class='edit'>操作</a></td>" +
                        "</tr>";
                    $('table>tbody').append(line);
                    $('.remove').click(function () {
                        ajaxRemove(identity, "remove", $(this).parent().siblings().eq(0).text());
                    });
                    $('.edit').click(function () {
                        ifModify = true;
                        if ("student" == $("#choose").val()) {
                            $('#none1').css("display", "block");
                            $('#none2').css("display", "none");
                            $('#none3').css("display", "block");
                        } else {
                            $('#none3').css("display", "none");
                            $('#none2').css("display", "block");
                            $('#none1').css("display", "none");
                        }
                        addInfoToModal($(this));
                    });
                }
            } else {
                if (!ifQuery) {
                    $('#major').remove();
                    $('#rank').remove();
                    $('#enterDate').remove();
                    $('#department').before(' <th id="rank">职称</th>');
                }
                for (let i = 0; i < result.length; i++) {
                    let line = "<tr>" +
                        "<td>" + result[i].num + "</td>" +
                        "<td>" + result[i].name + "</td>" +
                        "<td>" + result[i].sex + "</td>" +
                        "<td>" + result[i].rank + "</td>" +
                        "<td>" + result[i].department + "</td>" +
                        "<td>" + result[i].birthday + "</td>" +
                        "<td>" + result[i].phone + "</td>" +
                        "<td><a href='#' class='remove'>删除</a>\n" +
                        " <a href='#' data-toggle=\"modal\" data-target=\"#myModal\"\n" +
                        " class='edit'>操作</a></td>" +
                        "</tr>";
                    $('table>tbody').append(line);
                    $('.remove').click(function () {
                        ajaxRemove(identity, "remove", $(this).parent().siblings().eq(0).text());
                    });
                    $('.edit').click(function () {
                        ifModify = true;
                        if ("student" == $("#choose").val()) {
                            $('#none1').css("display", "block");
                            $('#none2').css("display", "none");
                            $('#none3').css("display", "block");
                        } else {
                            $('#none3').css("display", "none");
                            $('#none2').css("display", "block");
                            $('#none1').css("display", "none");
                        }
                        addInfoToModal($(this));
                    });
                }
            }
        }

        //将表格中数据加入到拟态框
        function addInfoToModal(node) {
            $('#account').val(node.parent().siblings().eq(0).text());
            $('#name').val(node.parent().siblings().eq(1).text());
            node.parent().siblings().eq(2).text() == "男" ? $('#male-person').attr("checked", "checked") : $('#female-person').attr("checked", "checked");
            $('#dep').val(node.parent().siblings().eq(4).text());
            if ("student" == identity) {
                $('#majorInput').val(node.parent().siblings().eq(3).text());
                $('#birthdayInput').val(node.parent().siblings().eq(5).text());
                $('#enterDayInput').val(node.parent().siblings().eq(6).text());
                $('#phone').val(node.parent().siblings().eq(7).text());
            } else {
                $('#rankInput').val(node.parent().siblings().eq(3).text());
                $('#birthdayInput').val(node.parent().siblings().eq(5).text());
                $('#phone').val(node.parent().siblings().eq(6).text());
            }
            TextInit = $('#form').serialize();
        }

    });
</script>
</html>
