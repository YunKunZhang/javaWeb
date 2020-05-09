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
            </c:if>
        </caption>
        <thead>
        <tr>
            <th>课程</th>
            <th>学分</th>
            <th>总学时</th>
            <th>类别</th>
            <th>授课方式</th>
            <th>考核方式</th>
            <th>任课老师</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${pageContext.request.getAttribute('info')}" var="temp" begin="0" step="1">
            <tr>
                <td><c:out value="${temp.name}"/></td>
                <td><c:out value="${temp.credit}"/></td>
                <td><c:out value="${temp.period}"/></td>
                <td><c:out value="${temp.variety}"/></td>
                <td><c:out value="${temp.teachingMethod}"/></td>
                <td><c:out value="${temp.evaluationMode}"/></td>
                <td><c:out value="${temp.teacherName}"/></td>
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
        let nowPage = 1;//当前页面
        let ifQuery = false;//是否查询过了
        let condition1;//查询主条件
        let condition2;//查询附加条件
        let condition3;//输入框内容

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

        </c:if>
    });
</script>
</html>

