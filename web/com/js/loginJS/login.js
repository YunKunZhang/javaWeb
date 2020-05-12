window.onload = function () {
    $('.message a').click(function () {
        $('form').animate({height: "toggle", opacity: "toggle"}, "slow");
    });

    //定义正则表达式，验证账号、密码
    const reg1 = new RegExp("^20([0-9]{9})$");
    const reg2 = new RegExp("^[0-9-A-Z-a-z]{6,18}$");

    //登录按钮添加点击事件监听
    $('.login-form>button').click(function () {
        login();
    });

    //修改按钮添加事件监听
    $('.register-form>button').click(function () {
        verify();
    });

    //登陆验证
    function login() {
        let num = $('.login-form>input[name="num"]').val();
        let password = $('.login-form>input[name="password"]').val();
        //正则表达式，验证账号
        let flag1 = reg1.test(num);
        let flag2 = reg2.test(password);
        if (flag1 == true && flag2 == true) {
            let tem = $('.login-form>select').val();
            $('.login-form>button').attr("disabled", true);
            ajaxLogin(num, password, tem);
            $('.login-form>button').attr("disabled", false);
        } else if (!flag1) {
            alert("账号不符合规范，请重新输入");
        } else if (!flag2) {
            alert("密码不符合规范，请重新输入");
        }
    }

    //修改验证
    function verify() {
        let num = $('.register-form>input[name="num"]').val();
        let password = $('.register-form>input[name="password"]').val();
        let confirm = $('.register-form>input[name="confirm"]').val();

        let flag1 = reg1.test(num);
        let flag2 = reg2.test(password);
        let flag3 = (password === confirm);

        if (flag1 == true && flag2 == true && flag3 == true) {
            let answer = $('.register-form>input[name="answer"]').val();
            let identity = $('.register-form>select').val();
            $('.register-form>button').attr("disabled", false);
            ajaxVerify(num, password, answer, identity);
            $('.register-form>button').attr("disabled", true);
        } else if (!flag1) {
            alert("账号不符合规范，请重新输入");
        } else if (!flag2) {
            alert("密码不符合规范，请重新输入");
        } else if (!flag3) {
            alert("前后密码不一致，请重新输入");
        }
    }

    //Ajax发送登录请求验证数据
    function ajaxLogin(account, password, identity) {
        $.ajax({
            type: "POST",
            url: "login",
            async: false,
            data: {'num': account, 'password': password, 'identity': identity},
            success: function (data) {
                if (data === "0") {
                    alert("密码错误");
                } else if (data === "-1") {
                    alert("该账号并未注册，请先进行注册");
                } else if (data === "2") {
                    alert("后台异常，请稍后重试");
                } else {
                    window.location = data;
                }
            },
            error: function () {
                alert("请求异常：请重新登录");
            }
        });
    }

    //Ajax发送修改密码请求验证
    function ajaxVerify(num, password, answer, identity) {
        $.ajax({
            type: 'POST',
            url: 'change',
            async: false,
            data: {'num': num, 'password': password, 'answer': answer, 'identity': identity},
            success: function (data) {
                if (data === "-1") {
                    alert("账号不存在，请重新输入");
                } else if (data === "0") {
                    alert("密保答案错误，请重新输入");
                } else if (data == "2") {
                    alert("后台异常，请稍后重试");
                } else {
                    window.location = data;
                    alert("修改成功");
                }
            },
            error: function () {
                alert("请求异常：请重新修改");
            }
        })
    }
};
