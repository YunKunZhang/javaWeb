<%--
  Created by IntelliJ IDEA.
  User: 12697
  Date: 2020/4/26
  Time: 10:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>选课信息</title>
    <link href="com/css/common_style.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="com/css/bootstrap.min.css" type="text/css">
    <script src="com/js/studentJS/jquery.min.js"></script>
    <script src="com/js/studentJS/bootstrap.min.js"></script>
    <style type="text/css">
        #container {
            z-index: 2;
            display: none;
        }
    </style>
</head>
<body>
<c:set value="${pageContext.request.session.getAttribute('identity')}" var="identity"></c:set>
<div class="cztable">
    <table class="table table-hover table-condensed table-bordered" id="table">
        <caption>
            <span class="pull-left">选课信息-</span>
            <span id="info">我的课程</span>
            <c:if test="${identity!='teacher'}">
                <a href="javascript:;"><span class="pull-right" style="margin-left: 10px;" id="sum">选课</span></a>
            </c:if>
        </caption>
        <thead>
        <tr>
            <th>序号</th>
            <th>课程</th>
            <th>学分</th>
            <th>总学时</th>
            <th>类别</th>
            <th>任课老师</th>
            <th>考核方式</th>
            <c:if test="${identity=='teacher'}">
                <th>人数</th>
            </c:if>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${pageContext.request.getAttribute('info')}" var="temp" begin="0" step="1">
            <tr>
                <td><c:out value="${temp.num}"/></td>
                <td><c:out value="${temp.name}"/></td>
                <td><c:out value="${temp.credit}"/></td>
                <td><c:out value="${temp.period}"/></td>
                <td><c:out value="${temp.variety}"/></td>
                <td><c:out value="${temp.teacherName}"/></td>
                <td><c:out value="${temp.evaluationMode}"/></td>
                <c:if test="${identity=='teacher'}">
                    <td><c:out value="${temp.people}"></c:out></td>
                </c:if>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <c:if test="${pageContext.request.getAttribute('identity')!='teacher'}">
        <div id="container">
            <div class="ui_content">
                <div class="ui_text_indent">
                    <div id="box_border">
                        <div id="box_top"><a href="javascript:;" id="back">返回</a></div>
                        <div id="box_center">
                            查询
                            <select name="query" id="condition1" class="ui_select01">
                                <option value="条件">--条件--</option>
                                <option value="序号">序号</option>
                                <option value="课程">课程</option>
                                <option value="类别">类别</option>
                                <option value="可选">可选</option>
                            </select>
                            <select name="query" id="condition2" class="ui_select01">
                                <option value="附加条件">--附加条件--</option>
                            </select>
                            <input type="text" id="import" class="ui_input_txt02" autocomplete="off"/>
                            <button class="ui_input_btn01" id="query">查询</button>
                            <button class="ui_input_btn01" id="recovery">重置</button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="ui_content">
                <div class="ui_tb">
                    <table class="table" id="elect" cellspacing="0" cellpadding="0" width="100%" align="center"
                           border="0">
                        <thead>
                        <tr>
                            <th>序号</th>
                            <th>课程</th>
                            <th>学分</th>
                            <th>总学时</th>
                            <th>类别</th>
                            <th>授课老师</th>
                            <th>考核方式</th>
                            <th>人数</th>
                            <th>状态</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
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
            </div>

            <!-- 模态框（Modal） -->
            <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
                 aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal"
                                    aria-hidden="true">×
                            </button>
                            <h4 class="modal-title" id="myModalLabel">
                                选课
                            </h4>
                        </div>
                        <div class="modal-body">
                            <ul class="list-group">
                                <label>序号</label>
                                <li class="list-group-item" id="number"></li>
                                <label>课程</label>
                                <li class="list-group-item" id="courseName"></li>
                                <label>学分</label>
                                <li class="list-group-item" id="credit"></li>
                                <label>总学时</label>
                                <li class="list-group-item" id="period"></li>
                                <label>类别</label>
                                <li class="list-group-item" id="variety"></li>
                                <label>授课老师</label>
                                <li class="list-group-item" id="teacherName"></li>
                                <label>考核方式</label>
                                <li class="list-group-item" id="evaluationMode"></li>
                                <label>已选人数/名额</label>
                                <li class="list-group-item" id="people"></li>
                                <label>状态</label>
                                <li class="list-group-item">
                                    <div class="input-group">
                                        <div class="input-group-btn">
                                            <button type="button" class="btn btn-default dropdown-toggle"
                                                    data-toggle="dropdown">
                                                选择
                                                <span class="caret"></span>
                                            </button>
                                            <ul class="dropdown-menu" id="choose">
                                                <li><a href="javascript:;">报名</a></li>
                                                <li><a href="javascript:;">退选</a></li>
                                            </ul>
                                        </div>
                                        <input type="text" id="status" name="choose" class="form-control"
                                               autocomplete="off"
                                               readonly="readonly">
                                    </div>
                                </li>
                            </ul>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default"
                                    data-dismiss="modal">关闭
                            </button>
                            <button type="button" class="btn btn-primary" id="submit">
                                提交
                            </button>
                        </div>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
        </div>
    </c:if>
</div>
</body>
<c:if test="${identity!='teacher'}">

    <script type="text/javascript">
        $(document).ready(function () {
            let ifTakeCourse;//是否可选
            let optionSum;//可选课程总数
            let nowPage = 1;//当前页码
            let status;//课程状态
            let selected = false;//是否选过课了
            let ifQuery = false;//是否查询过了
            let condition1;//查询主条件
            let condition2;//查询附加条件
            let condition3;//输入框内容

            //为“返回”添加事件监听
            $('#back').click(function () {
                if (selected) {
                    location.reload();
                } else {
                    $('#table').css("display", "table");
                    $('#container').css("display", "none");
                }
            });

            //在页面加载完毕就发送ajax请求，确认是否可以选课，以及可选课程的数目
            $.ajax({
                type: "GET",
                url: "paging",
                async: true,
                success: function (result) {
                    if (result == 0 || result == -1) {
                        ifTakeCourse = false;
                    } else {
                        ifTakeCourse = true;
                        optionSum = result;
                        $('.numberSum').html(result);
                        $('.pageSum').html(Math.ceil(result / 15));
                    }
                },
                error: function () {
                    alert("请求异常，请稍后重试");
                }
            });

            //为“所有课程”添加事件监听
            $('#sum').click(function () {
                if (ifTakeCourse) {
                    ajaxPaging(1);
                    $('#table').css("display", "none");
                    $('#container').css("display", "block");
                } else {
                    alert("非正选时间");
                }
            });

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
                    data: {"pageNum": pageNum},
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
                $('#elect>tbody').empty();
                for (let i = 0; i < result.length; i++) {
                    let line = "<tr>" +
                        "<td>" + result[i].num + "</td>" +
                        "<td>" + result[i].name + "</td>" +
                        "<td>" + result[i].credit + "</td>" +
                        "<td>" + result[i].period + "</td>" +
                        "<td>" + result[i].variety + "</td>" +
                        "<td>" + result[i].teacherName + "</td>" +
                        "<td>" + result[i].evaluationMode + "</td>" +
                        "<td>" + result[i].people + "</td>" +
                        "<td>" + result[i].status + "</td>" +
                        "<td> <a href='#' data-toggle='modal' data-target='#myModal' class='edit'>操作</a></td>" +
                        "</tr>";
                    $('#elect>tbody').append(line);

                    //为所有"操作"链接添加事件监听
                    $('.edit').click(function () {
                        //获取当前行中的所有td节点
                        let td = $(this).parent().prevAll();

                        //将表格中的数据加入到拟态框
                        $('#number').html(td.eq(8).text());
                        $('#courseName').html(td.eq(7).text());
                        $('#credit').html(td.eq(6).text());
                        $('#period').html(td.eq(5).text());
                        $('#variety').html(td.eq(4).text());
                        $('#teacherName').html(td.eq(3).text());
                        $('#evaluationMode').html(td.eq(2).text());
                        $('#people').html(td.eq(1).text());

                        $('#status').val(td.eq(0).text());
                        status = $('#status').val();
                    });
                }
            }

            //为状态按钮添加鼠标监听
            $('#choose a').click(function () {
                //获取初始化的状态值,进行判断
                if (status == "已选" && $(this).text() == "报名") {
                    $('#status').val(status);
                } else {
                    $('#status').val($(this).text());
                }
            });

            //为“提交”按钮添加事件监听
            $('#submit').click(function () {
                //获取输入框中的状态
                let status = $('#status').val();
                if (status == "已选") {
                    alert("该课程已经报名，无法重复提交");
                } else if (status == "报名") {
                    //可选课程报名
                    operation("register");
                } else {
                    //已选课程的退选
                    operation("exit");
                }
            });

            //将"操作"封装为函数
            function operation(operate) {
                $.ajax({
                    type: "POST",
                    url: "register",
                    async: false,
                    data: {"num": $('#number').text(), "operation": operate},
                    success: function (result) {
                        if ("register" == operate) {
                            if (result == -2) {
                                alert("不在正选时间，无法选课");
                            } else if (result == -1) {
                                alert("后台操作异常，请稍后重试");
                            } else if (result == 0) {
                                alert("该课程已没有名额，无法报名");
                            } else if (result == 1) {
                                selected = true;
                                $('#myModal').modal('hide');
                                alert("选课成功");
                                ajaxPaging(nowPage);//刷新当前页面
                            }
                        } else {
                            if (result == -1) {
                                alert("后台操作异常，请稍后重试");
                            } else if (result == 0) {
                                alert("不在正选时间，无法退选");
                            } else if (result == 1) {
                                selected = true;
                                $('#myModal').modal('hide');
                                alert("退选成功");
                                ajaxPaging(nowPage);//刷新当前页面
                            }
                        }
                    },
                    error: function () {
                        alert("请求异常，请稍后重试");
                    }
                });
            }

            //    条件查询区域

            //为第一个条件下拉列表添加事件监听
            $('#condition1').change(function () {
                let value = $(this).val();
                let dom = $('#condition2');
                dom.empty();
                dom.append("<option value='附加条件'>--附加条件--</option>");
                if (value != "条件" || value != "可选") {
                    let extraCondition1 = "<option class='extra' value='精确'>精确</option>";
                    let extraCondition2 = "<option class='extra' value='模糊'>模糊</option>";
                    dom.append(extraCondition1, extraCondition2);
                }
            });

            //为“查询”按钮添加事件监听
            $('#query').click(function () {
                condition1 = $('#condition1').val();
                condition2 = $('#condition2').val();
                if (condition1 == "条件" && condition2 == "附加条件") {
                    alert("请选择查询条件");
                } else if (condition1 != "可选" && condition2 == "附加条件") {
                    alert("请选择附加查询条件");
                } else {
                    condition3 = $('#import').val();
                    if (condition3 == "" && condition1 != "可选") {
                        alert("输入框不能为空");
                    } else {
                        ajaxQuery(condition1, condition2, condition3, 1);
                    }
                }
            });

            //为“重置”按钮添加事件监听
            $('#recovery').click(function () {
                if (ifQuery && ifTakeCourse) {
                    ajaxPaging(1);
                    ifQuery = !ifQuery;
                } else if (!ifTakeCourse) {
                    alert("非正选时间，无法重置");
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
                        "pageNum": pageNum
                    },
                    success: function (result) {
                        //分离请求中包含的符合条件的课程总数
                        let temp = result.substring(0, result.indexOf("["));
                        if (temp == 0) {
                            alert("未找到所要查询的课程");
                        } else {
                            ifQuery = true;
                            optionSum = temp;
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

        });
    </script>
</c:if>
</html>
