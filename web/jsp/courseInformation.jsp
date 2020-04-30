<%--
  Created by IntelliJ IDEA.
  User: 12697
  Date: 2020/4/25
  Time: 18:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>课程信息</title>
    <link rel="stylesheet" href="com/css/bootstrap.min.css">
</head>
<body>
<div class="cztable">
    <table class="table table-hover table-condensed table-bordered">
        <caption>
            课程信息
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
                data: {"semester": semester,"info":"course"},
                success: function (result) {
                    if (result == 0) {
                        $("option[value=" + semester + "]").attr("selected", false);
                        $("option[value=" + nowSemester + "]").attr("selected", true);
                        alert("没有找到");
                    } else {
                        nowSemester = semester;
                        result=JSON.parse(result);
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
                    "<td>"+result[i].name+"</td>" +
                    "<td>"+result[i].credit+"</td>" +
                    "<td>"+result[i].period+"</td>" +
                    "<td>"+result[i].variety+"</td>" +
                    "<td>"+result[i].teachingMethod+"</td>" +
                    "<td>"+result[i].evaluationMode+"</td>" +
                    "<td>"+result[i].teacherName+"</td>" +
                    "</tr>";
                $('table>tbody').append(line);
            }
        }
    });
</script>
</html>

