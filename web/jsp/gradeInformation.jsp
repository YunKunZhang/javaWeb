<%--
  Created by IntelliJ IDEA.
  User: 12697
  Date: 2020/4/25
  Time: 20:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>成绩信息</title>
    <link href="com/css/common_style.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="com/css/bootstrap.min.css">
    <script src="com/js/studentJS/jquery.min.js"></script>
    <script src="com/js/studentJS/bootstrap.min.js"></script>
</head>
<body>
<c:set value="${pageContext.request.session.getAttribute('identity')}" var="identity"></c:set>
<div class="cztable">
    <table class="table table-hover table-condensed table-bordered">
        <caption>
            成绩信息
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
        </caption>
        <thead>
        <tr>
            <th>课程</th>
            <c:if test="${identity=='teacher'}">
                <th>学号</th>
                <th>姓名</th>
            </c:if>
            <th>学分</th>
            <th>类别</th>
            <th>考核方式</th>
            <th>修读性质</th>
            <th>成绩</th>
            <c:if test="${identity=='teacher'}">
                <th>操作</th>
            </c:if>
        </tr>
        </thead>
        <tbody>
        <c:choose>
            <c:when test="${identity!='teacher'}">
                <c:forEach items="${pageContext.request.getAttribute('info')}" var="temp" begin="0" step="1">
                    <tr>
                        <td><c:out value="${temp.name}"/></td>
                        <td><c:out value="${temp.credit}"/></td>
                        <td><c:out value="${temp.variety}"/></td>
                        <td><c:out value="${temp.evaluationMode}"/></td>
                        <td><c:out value="${temp.character}"/></td>
                        <td><c:out value="${temp.score}"/></td>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <c:forEach items="${pageContext.request.getAttribute('info')}" var="temp" begin="0" step="1" end="14">
                    <tr>
                        <td><c:out value="${temp.courseName}"/></td>
                        <td><c:out value="${temp.stuNum}"/></td>
                        <td><c:out value="${temp.stuName}"/></td>
                        <td><c:out value="${temp.credit}"/></td>
                        <td><c:out value="${temp.variety}"/></td>
                        <td><c:out value="${temp.evaluationMode}"/></td>
                        <td><c:out value="${temp.character}"/></td>
                        <td><c:out value="${temp.score}"/></td>
                        <td><a href='#' data-toggle="modal" data-target="#myModal" class='edit'>操作</a></td>
                    </tr>
                </c:forEach>
            </c:otherwise>
        </c:choose>
        </tbody>
    </table>
    <c:if test="${identity=='teacher'}">
        <div class="ui_tb_h30">
            <div class="ui_flt" style="height: 30px; line-height: 30px;">
                共有
                <span class="numberSum">90</span>
                条记录，当前第
                <span id="pageNow">1</span>
                /
                <span class="pageSum">90</span>
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
<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">成绩录入</h4>
            </div>
            <div class="modal-body">
                <ul class="list-group">
                    <label>成绩</label>
                    <li class="list-group-item">
                        <input type="text" id="score" name="grade" autocomplete="off">
                    </li>
                </ul>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary">提交更改</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.m-->
</div>
</body>

<script src="com/js/studentJS/jquery.min.js"></script>
<script type="text/javascript">
    $(document).ready(function () {

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
                data: {"semester": semester, "info": "grade"},
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

        <c:if test="${identity!='teacher'}">

        //加载获取到的课程信息
        function addInfo(result) {
            $('table>tbody').empty();
            for (let i = 0; i < result.length; i++) {
                let line = "<tr>" +
                    "<td>" + result[i].name + "</td>" +
                    "<td>" + result[i].credit + "</td>" +
                    "<td>" + result[i].variety + "</td>" +
                    "<td>" + result[i].evaluationMode + "</td>" +
                    "<td>" + result[i].character + "</td>" +
                    "<td>" + result[i].score + "</td>" +
                    "</tr>";
                $('table>tbody').append(line);
            }
        }

        </c:if>

        <c:if test="${identity=='teacher'}">

        //加载获取到的课程信息
        function addInfo(result) {
            $('table>tbody').empty();
            for (let i = 0; i < result.length; i++) {
                let line = "<tr>" +
                    "<td>" + result[i].courseName + "</td>" +
                    "<td>" + result[i].stuNum + "</td>" +
                    "<td>" + result[i].stuName + "</td>" +
                    "<td>" + result[i].credit + "</td>" +
                    "<td>" + result[i].variety + "</td>" +
                    "<td>" + result[i].evaluationMode + "</td>" +
                    "<td>" + result[i].character + "</td>" +
                    "<td>" + result[i].score + "</td>" +
                    "<td> <a href='#' data-toggle='modal' data-target='#myModal' class='edit'>操作</a></td>" +
                    "</tr>";
                $('table>tbody').append(line);
            }
        }

        </c:if>

        //教师端录入成绩时数据分页
        <c:if test="${identity=='teacher'}">
        let optionSum;//选课学生总数
        let nowPage = 1;//当前页码

        //在页面加载完毕就发送ajax请求，获取学生总数
        $.ajax({
            type: "GET",
            url: "paging",
            async: false,
            success: function (result) {
                if (result == -1) {
                    alert("后台异常");
                } else if (result == 0) {
                    alert("没有找到");
                } else {
                    optionSum = result;
                    $('.numberSum').html(result);
                    $('.pageSum').html(Math.ceil(result / 15));
                }
            },
            error: function () {
                alert("请求异常，请稍后重试");
            }
        });

        //为不同的跳转按钮添加事件监听
        //"首页"按钮
        $('#homePage').click(function () {
            if (nowPage == 1 || optionSum <= 15) {
                return;
            } else {
                setPage(1);
            }
        });

        //"上一页"按钮
        $('#lastPage').click(function () {
            if (nowPage == 1 || optionSum <= 15) {
                return;
            } else {
                setPage(nowPage - 1);
            }
        });

        //"下一页"按钮
        $('#nextPage').click(function () {
            if (nowPage == optionSum || optionSum < 15) {
                return;
            } else {
                setPage(nowPage + 1);
            }
        });

        //"尾页"按钮
        $('#trailerPage').click(function () {
            if (nowPage == optionSum || optionSum < 15) {
                return;
            } else {
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
            }
            else {
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
                data: {"pageNum": pageNum, "semester": $('#choose').find("option:selected").text()},
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
                    "<td>" + result[i].CourseName + "</td>" +
                    "<td>" + result[i].stuNum + "</td>" +
                    "<td>" + result[i].stuName + "</td>" +
                    "<td>" + result[i].credit + "</td>" +
                    "<td>" + result[i].variety + "</td>" +
                    "<td>" + result[i].evaluationMode + "</td>" +
                    "<td>" + result[i].character + "</td>" +
                    "<td>" + result[i].score + "</td>" +
                    "<td> <a href='#' data-toggle='modal' data-target='#myModal' class='edit'>操作</a></td>" +
                    "</tr>";
                $('#elect>tbody').append(line);
            }
        }

        </c:if>

    });
</script>

</html>
