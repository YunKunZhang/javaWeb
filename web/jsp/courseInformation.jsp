<%--
  Created by IntelliJ IDEA.
  User: 12697
  Date: 2020/4/25
  Time: 18:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<c:set value="${pageContext.request.session.getAttribute('identity')}" var="identity"></c:set>
<head>
    <title>课程信息</title>
    <link rel="stylesheet" href="com/css/bootstrap.min.css">
    <c:if test="${identity=='administrator'}">
        <link href="com/css/common_style.css" rel="stylesheet" type="text/css">
    </c:if>
    <script src="com/js/studentJS/jquery.min.js"></script>
    <script src="com/js/studentJS/bootstrap.min.js"></script>
</head>
<body>
<div class="cztable">
    <table class="table table-hover table-condensed table-bordered">
        <caption>
            课程信息
            <c:if test="${identity!='administrator'}">
                <select name="query" id="choose">
                    <option value="2019-2020学年第二学期">2019-2020学年第二学期</option>
                    <option value="2019-2020学年第一学期">2019-2020学年第一学期</option>
                    <option value="2018-2019学年第二学期">2018-2019学年第二学期</option>
                    <option value="2018-2019学年第一学期">2018-2019学年第一学期</option>
                    <option value="2017-2018学年第二学期">2017-2018学年第二学期</option>
                    <option value="2017-2018学年第一学期">2017-2018学年第一学期</option>
                    <option value="2016-2017学年第二学期">2016-2017学年第二学期</option>
                    <option value="2016-2017学年第一学期">2016-2017学年第一学期</option>
                </select>
            </c:if>
            <c:if test="${identity=='administrator'}">
                <select name="query" id="condition1" class="ui_select01">
                    <option value="条件">--条件--</option>
                    <option value="课程号">课程号</option>
                    <option value="课程">课程</option>
                    <option value="类别">类别</option>
                    <option value="任课老师">任课老师</option>
                </select>
                <select name="query" id="condition2" class="ui_select01">
                    <option value="附加条件">--附加条件--</option>
                    <option class='extra' value='精确'>精确</option>
                    <option class='extra' value='模糊'>模糊</option>
                </select>
                <input type="text" id="import" class="ui_input_txt02" autocomplete="off"/>
                <button class="ui_input_btn01" id="query">查询</button>
                <button class="ui_input_btn01" id="recovery">重置</button>
                <span style="float: right;">
                <button class="ui_input_btn01" id="add" data-toggle="modal" data-target="#myModal">添加</button>
                </span>
            </c:if>
        </caption>
        <thead>
        <tr>
            <c:if test="${identity=='administrator'}">
                <th>课程号</th>
            </c:if>
            <th>课程</th>
            <th>学分</th>
            <th>总学时</th>
            <th>类别</th>
            <th>授课方式</th>
            <th>考核方式</th>
            <th>任课老师</th>
            <c:if test="${identity=='administrator'}">
                <th>可选人数</th>
                <th>操作</th>
            </c:if>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${pageContext.request.getAttribute('info')}" var="temp" begin="0" step="1">
            <tr>
                <c:if test="${identity=='administrator'}">
                    <td><c:out value="${temp.num}"/></td>
                </c:if>
                <td><c:out value="${temp.name}"/></td>
                <td><c:out value="${temp.credit}"/></td>
                <td><c:out value="${temp.period}"/></td>
                <td><c:out value="${temp.variety}"/></td>
                <td><c:out value="${temp.teachingMethod}"/></td>
                <td><c:out value="${temp.evaluationMode}"/></td>
                <td><c:out value="${temp.teacherName}"/></td>
                <c:if test="${identity=='administrator'}">
                    <td><c:out value="${temp.allowed}"/></td>
                    <td><a href='#' class="remove">删除</a>
                        <a href='#' data-toggle="modal" data-target="#myModal"
                           class='edit'>操作</a></td>
                </c:if>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <c:if test="${identity=='administrator'}">
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
                <!--    如果是第一页，则只显示下一页、尾页 -->

                <input type="button" value="首页" class="ui_input_btn01" id="homePage"/>
                <input type="button" value="上一页" class="ui_input_btn01" id="lastPage"/>
                <input type="button" value="下一页" class="ui_input_btn01" id="nextPage"/>
                <input type="button" value="尾页" class="ui_input_btn01" id="trailerPage"/>
                <!--     如果是最后一页，则只显示首页、上一页 -->

                转到第<input type="text" id="jumpNumTxt" class="ui_input_txt01" autocomplete="off"/>页
                <input type="button" class="ui_input_btn01" id="jump" value="跳转"/>
            </div>
        </div>

        <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
             aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="myModalLabel">模态框（Modal）标题</h4>
                    </div>
                    <div class="modal-body">
                        <form class="form" role="form" id="form">
                            <div class="form-group">
                                <lebel>课程号码</lebel>
                                <input type="text" id="courseNum" name="courseNum" class="form-control"
                                       autocomplete="off">
                            </div>
                            <div class="form-group">
                                <lebel>课程名</lebel>
                                <input type="text" id="courseName" name="courseName" class="form-control"
                                       autocomplete="off">
                            </div>
                            <div class="form-group">
                                <lebel>学分</lebel>
                                <input type="text" id="credit" name="credit" class="form-control" autocomplete="off">
                            </div>
                            <div class="form-group">
                                <lebel>总学时</lebel>
                                <input type="text" id="period" name="period" class="form-control" autocomplete="off">
                            </div>
                            <div class="form-group">
                                <lebel>类别</lebel>
                                <div class="input-group">
                                    <div class="input-group-btn">
                                        <button type="button" class="btn btn-default dropdown-toggle"
                                                data-toggle="dropdown">
                                            类别
                                            <span class="caret"></span>
                                        </button>
                                        <ul class="dropdown-menu" id="varietySelect">
                                            <li><a href="javascript:;">公共课/必修课</a></li>
                                            <li><a href="javascript:;">专业课/必修课</a></li>
                                            <li><a href="javascript:;">公共课/任选课</a></li>
                                            <li><a href="javascript:;">专业课/任选课</a></li>
                                            <li><a href="javascript:;">专业基础课/必修课</a></li>
                                        </ul>
                                    </div><!-- /btn-group -->
                                    <input type="text" id="variety" name="variety" class="form-control"
                                           autocomplete="off"
                                           readonly="readonly">
                                </div>
                            </div>
                            <div class="form-group" id="none" style="display: none;">
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
                            <div class="form-group" id="none1" style="display:none;">
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
                            <div class="form-group">
                                <lebel>授课方式</lebel>
                                <div class="input-group">
                                    <div class="input-group-btn">
                                        <button type="button" class="btn btn-default dropdown-toggle"
                                                data-toggle="dropdown" id="teachButton">
                                            授课方式
                                            <span class="caret"></span>
                                        </button>
                                        <ul class="dropdown-menu" id="teachSelect">
                                            <li><a href="javascript:;">讲授</a></li>
                                            <li><a href="javascript:;">多媒体</a></li>
                                            <li><a href="javascript:;">体育</a></li>
                                        </ul>
                                    </div><!-- /btn-group -->
                                    <input type="text" id="teach" name="teach" class="form-control"
                                           autocomplete="off"
                                           readonly="readonly">
                                </div>
                            </div>
                            <div class="form-group">
                                <lebel>考核方式</lebel>
                                <div class="input-group">
                                    <div class="input-group-btn">
                                        <button type="button" class="btn btn-default dropdown-toggle"
                                                data-toggle="dropdown" id=evaluationButton">
                                            考核方式
                                            <span class="caret"></span>
                                        </button>
                                        <ul class="dropdown-menu" id="evaluationSelect">
                                            <li><a href="javascript:;">考试</a></li>
                                            <li><a href="javascript:;">考查</a></li>
                                        </ul>
                                    </div><!-- /btn-group -->
                                    <input type="text" id="evaluation" name="evaluation" class="form-control"
                                           autocomplete="off"
                                           readonly="readonly">
                                </div>
                            </div>
                            <div class="form-group">
                                <lebel>任课老师</lebel>
                                <input type="text" id="teacherName" name="teacherName" class="form-control"
                                       autocomplete="off">
                            </div>
                            <div class="form-group">
                                <lebel>可选人数</lebel>
                                <input type="text" id="personAllowed" name="personAllowed" class="form-control"
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
    </c:if>
</div>
</body>
<script type="text/javascript">
    $(document).ready(function () {
        <c:if test="${identity!='administrator'}">
        //选择学期
        let date = new Date();
        let year = date.getFullYear();
        let month = date.getMonth();

        if (month < 7) {
            $("option[value*=" + year + "学年第二学期" + "]").attr("selected", true);
        } else {
            $("option[value*=" + (year + 1) + "学年第一学期" + "]").attr("selected", true);
        }

        //存储当前选中的学期
        let nowSemester = $('#choose').find("option:selected").text();

        //为学期选择添加事件监听
        $('#choose').change(function () {
            let semester = $('#choose').find("option:selected").text();
            ajaxSemester(semester);
        });

        //ajax发送请求获取不同学期的数据
        function ajaxSemester(semester) {
            $.ajax({
                type: 'POST',
                url: 'semester',
                async: false,
                data: {"semester": semester, "info": "course"},
                success: function (result) {
                    if (result == 0) {
                        $("option[value=" + semester + "]").attr("selected", false);
                        $("option[value=" + nowSemester + "]").attr("selected", true);
                        alert("没有找到");
                    } else {
                        nowSemester = semester;
                        result = JSON.parse(result);
                        addInfo(result);
                    }
                },
                error: function () {
                    alert("请求异常，请稍后重试");
                }
            });
        }

        //加载获取到的课程信息
        function addInfo(result) {
            $('table>tbody').empty();
            for (let i = 0; i < result.length; i++) {
                let line = "<tr>" +
                    "<td>" + result[i].name + "</td>" +
                    "<td>" + result[i].credit + "</td>" +
                    "<td>" + result[i].period + "</td>" +
                    "<td>" + result[i].variety + "</td>" +
                    "<td>" + result[i].teachingMethod + "</td>" +
                    "<td>" + result[i].evaluationMode + "</td>" +
                    "<td>" + result[i].teacherName + "</td>" +
                    "</tr>";
                $('table>tbody').append(line);
            }
        }

        </c:if>

        <c:if test="${identity=='administrator'}">
        $('.pageSum').html(Math.ceil($('.pageSum').html()));
        let optionSum = $('.numberSum').html();//课程总数
        let TextInit;//表单内容
        let ifModify = false;
        let nowPage = 1;//当前页面
        let ifQuery = false;//是否查询过了
        let condition1;//查询主条件
        let condition2;//查询附加条件
        let condition3;//输入框内容

        //正则表达式：只能是汉字
        let reg1 = /^[\u0391-\uFFE5]+$/;

        //为不同的跳转按钮添加事件监听
        //"首页"按钮
        $('#homePage').click(function () {
            if (nowPage == 1 || optionSum <= 15) {
                return;
            } else {
                if (ifQuery) {
                    ajaxQuery(condition1, condition2, condition3, 1);
                } else {
                    ajaxPaging(1);
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
                    ajaxQuery(condition1, condition2, condition3, nowPage - 1);
                } else {
                    ajaxPaging(nowPage - 1);
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
                    ajaxQuery(condition1, condition2, condition3, nowPage + 1);
                } else {
                    ajaxPaging(nowPage + 1);
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
                    ajaxQuery(condition1, condition2, condition3, Math.ceil(optionSum / 15));
                } else {
                    ajaxPaging(Math.ceil(optionSum / 15));
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
                    ajaxQuery(condition1, condition2, condition3, input);
                } else {
                    ajaxPaging(input);
                }
                setPage(input);
            }
        });

        //将设置当前页码功能封装为函数
        function setPage(pageNum) {
            $('#pageNow').html(pageNum);
        }

        //ajax发送请求获取分页数据
        function ajaxPaging(pageNum) {
            $.ajax({
                type: "POST",
                url: "paging",
                async: false,
                data: {"pageNum": pageNum, "pageName": "course"},
                success: function (result) {
                    if (result == 0) {
                        alert("后台异常，请稍后重试");
                    } else {
                        result = JSON.parse(result);
                        addInfo(result);
                    }
                },
                error: function () {
                    alert("请求异常，请稍后重试");
                }
            });
        };

        //加载获取到的可选课程信息
        function addInfo(result) {
            $('table>tbody').empty();
            for (let i = 0; i < result.length; i++) {
                let line = "<tr>" +
                    "<td>" + result[i].num + "</td>" +
                    "<td>" + result[i].name + "</td>" +
                    "<td>" + result[i].credit + "</td>" +
                    "<td>" + result[i].period + "</td>" +
                    "<td>" + result[i].variety + "</td>" +
                    "<td>" + result[i].teachingMethod + "</td>" +
                    "<td>" + result[i].evaluationMode + "</td>" +
                    "<td>" + result[i].teacherName + "</td>" +
                    "<td>" + result[i].allowed + "</td>" +
                    "<td><a href='#' class='remove'>删除</a>\n" +
                    " <a href='#' data-toggle=\"modal\" data-target=\"#myModal\"\n" +
                    " class='edit'>操作</a></td>" +
                    "</tr>";
                $('table>tbody').append(line);
                //为“清除”按钮添加事件监听
                $('.remove').click(function () {
                    removeCourse("removeCourse", $(this).parent().siblings().eq(0).text());
                });
                //为“操作”按钮添加事件监听
                $('.edit').click(function () {
                    ifModify = true;
                    addInfoToModal($(this));
                });
            }
        }

        //    条件查询区域

        //为“查询”按钮添加事件监听
        $('#query').click(function () {
                condition1 = $('#condition1').val();
                condition2 = $('#condition2').val();
                if (condition1 == "条件" || condition2 == "附加条件") {
                    alert("请选择查询条件");
                } else {
                    condition3 = $('#import').val();
                    if (condition3 == "") {
                        alert("输入框不能为空");
                    } else {
                        ajaxQuery(condition1, condition2, condition3, 1);
                    }
                }
            }
        );

        //为“重置”按钮添加事件监听
        $('#recovery').click(function () {
            if (ifQuery) {
                $('.numberSum').html(optionSum);
                $('.pageSum').html(Math.ceil(optionSum / 15));
                ajaxPaging(1);
                ifQuery = !ifQuery;
            } else if (!ifQuery) {
                alert("没有查询，无需重置");
            }
        });

        //ajax请求获取查询结果
        function ajaxQuery(condition, extraCondition, input, pageNum) {
            $.ajax({
                type: "POST",
                url: "query",
                async: true,
                data: {
                    "condition": condition,
                    "extraCondition": extraCondition,
                    "input": input,
                    "pageNum": pageNum,
                    "pageName": "course"
                },
                success: function (result) {
                    alert(condition);
                    //分离请求中包含的符合条件的课程总数
                    let temp = result.substring(0, result.indexOf("["));
                    if (temp == 0) {
                        alert("未找到所要查询的课程");
                    } else {
                        ifQuery = true;
                        nowPage = 1;
                        //分离请求中的json数据
                        result = JSON.parse(result.substring(result.indexOf("[")));
                        addInfo(result);

                        $('.numberSum').html(temp);
                        $('.pageSum').html(Math.ceil(temp / 15));
                    }
                },
                error: function () {
                    alert("请求异常，请稍后重试");
                }
            });
        }

        //为“添加”按钮添加事件监听
        $('#add').click(function () {
            TextInit = $('#form').serialize();
        });

        //为“清除”按钮添加事件监听
        $('.remove').click(function () {
            removeCourse("removeCourse", $(this).parent().siblings().eq(0).text());
        });

        //为“操作”按钮添加事件监听
        $('.edit').click(function () {
            ifModify = true;
            addInfoToModal($(this));
        });

        //为“类别”添加事件监听器
        $('#varietySelect a').click(function () {
            $('#variety').val($(this).text());
            if ($(this).text().indexOf("专业") >= 0) {
                $('#none').css("display", "block");
                $('#none1').css("display", "block");
            } else {
                $('#none').css("display", "none");
                $('#none1').css("display", "none");
            }
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

        //为“授课方式”添加事件监听器
        $('#teachSelect a').click(function () {
            $('#teach').val($(this).text());
        });

        //为“考核类别”添加事件监听器
        $('#evaluationSelect a').click(function () {
            $('#evaluation').val($(this).text());
        });

        //拟态框消失时重置表单
        $('#myModal').on('hidden.bs.modal', function () {
            $('#form')[0].reset();
            ifModify = false;
        });

        //为“提交”按钮添加事件监听
        $('#submit').click(function () {
            let Text = $('#form').serialize();
            if (Text == TextInit && ifModify == false) {
                alert("请填写内容");
            } else if (ifModify == true && Text == TextInit) {
                alert("请修改内容");
            } else if (ifModify) {
                verify(Text);
            } else {
                verify(Text);
            }
        });

        //数据验证
        function verify(information) {
            let inputAll = decodeURIComponent(information).split('&');
            let flag = true;
            let tem = $('#variety').val().indexOf("专业");
            for (let i = 0; i < inputAll.length; i++) {
                let input = inputAll[i].split('=');
                if (input[1] == "" && i != 5 && i != 6) {
                    alert("信息不能为空");
                    flag = false;
                    return;
                } else if (i == 5 || i == 6) {
                    if (tem > 0 && input[1] == "") {
                        alert("信息不能为空");
                        flag = false;
                        return;
                    }
                } else if (i == 2 || i == 3) {
                    if (parseInt(input[1]) <= 0) {
                        alert("学分、学时必须大于0");
                        flag = false;
                        return;
                    }
                } else if (i == 9) {
                    flag = reg1.test(input[1]);
                    if (!flag) {
                        alert("教师姓名值有误");
                        return;
                    }
                } else if (i == 10) {
                    if (input[1] <= 0) {
                        alert("可选人数必须大于0");
                        flag = false;
                        return;
                    }
                }
            }
            if (flag && ifModify == false) {
                addCourse("addCourse", information);
            } else {
                ajaxModifyCourse("modifyCourse", information);
            }
        }

        //发送ajax请求添加课程信息
        function addCourse(operation, information) {
            $.ajax({
                type: "POST",
                url: "management",
                async: true,
                data: {"type": "course", "operation": operation, "information": decodeURIComponent(information)},
                success: function (result) {
                    //分离请求中包含的符合条件的课程总数
                    let temp = result.substring(0, result.indexOf("["));
                    if (temp == 0 || temp == "") {
                        alert("添加失败");
                    } else {
                        alert("添加成功");
                        ajaxPaging(1);
                        $('#myModal').modal('hide');
                    }
                },
                error: function () {
                    alert("请求失误，请稍后再试");
                }
            });
        }

        //发送ajax请求删除课程信息
        function removeCourse(operation, num) {
            $.ajax({
                type: "POST",
                url: "management",
                async: true,
                data: {type: "course", "operation": operation, "courseNum": num},
                success: function (result) {
                    //分离请求中包含的符合条件的课程总数
                    let temp = result.substring(0, result.indexOf("["));
                    if (temp == 0) {
                        alert("删除失败");
                    } else if (temp == -1) {
                        alert("该课程被人选择，无法删除");
                    } else {
                        alert("删除成功");
                        ajaxPaging(1);
                    }
                },
                error: function () {
                    alert("请求失误，请稍后再试");
                }
            })
            ;
        }

        //发送ajax请求向数据库中修改教师（学生）信息
        function ajaxModifyCourse(operation, information) {
            $.ajax({
                type: "POST",
                url: "management",
                async: true,
                data: {"type": "modifyCourse", "operation": operation, "information": decodeURIComponent(information)},
                success: function (result) {
                    result = result.substring(0, result.indexOf("["));
                    if (result == 1) {
                        alert("修改成功");
                        ajaxPaging(1);
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

        //将表格中数据加入到拟态框
        function addInfoToModal(node) {
            $('#courseNum').val(node.parent().siblings().eq(0).text());
            $('#courseName').val(node.parent().siblings().eq(1).text());
            $('#credit').val(node.parent().siblings().eq(2).text());
            $('#period').val(node.parent().siblings().eq(3).text());
            $('#variety').val(node.parent().siblings().eq(4).text());
            $('#teach').val(node.parent().siblings().eq(5).text());
            $('#evaluation').val(node.parent().siblings().eq(6).text());
            $('#teacherName').val(node.parent().siblings().eq(7).text());
            $('#personAllowed').val(node.parent().siblings().eq(8).text());
            $('#none').css("display", "none");
            $('#none1').css("display", "none");
            TextInit = $('#form').serialize();
            if (node.parent().siblings().eq(4).text().indexOf("专业") >= 0) {
                selectMajor(node.parent().siblings().eq(0).text());
            }
        }

        //发送ajax请求查询专业类课程所属院系以及专业
        function selectMajor(courseNum) {
            $.ajax({
                type: "GET",
                url: "management",
                async: true,
                data: {"courseNum": courseNum},
                success: function (result) {
                    if (result == 0) {
                        alert("没有获取该课程所属的专业信息");
                    } else {
                        //分离请求中的json数据
                        result = JSON.parse(result);
                        $('#dep').val(result[0]);
                        $('#majorInput').val(result[1]);
                        $('#none').css("display", "block");
                        $('#none1').css("display", "block");
                        TextInit = $('#form').serialize();
                    }
                },
                error: function () {
                    alert("请求失败，请稍后再试");
                }
            });
        }

        </c:if>
    })
    ;
</script>
</html>

