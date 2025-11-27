//登录和注册表单切换交互
function toggle() {
    //获取登录表单的css属性
    var loginForm = document.querySelector('.login');
    //获取注册表单的css属性
    var registerForm = document.querySelector('.register');
    if (loginForm.classList.contains('active')) {//如果登录表单是激活的
        loginForm.classList.remove('active');//隐藏登录表单
        registerForm.classList.add('active');//激活注册表单
    } else {
        registerForm.classList.remove('active');//隐藏注册表单
        loginForm.classList.add('active');//激活登录表单
    }
    //清空注册表单
    document.getElementById("registerLoginId").value = null;
    document.getElementById("registerName").value = null;
    document.getElementById("registerPassword").value = null;
    document.getElementById("registerEmail").value = null;
    document.getElementById("registerPhone").value = null;
//隐藏提示语
    var loginIdError = document.getElementById("loginIdError");
    var passwordError = document.getElementById("passwordError");
    loginIdError.style.display = 'none';
    passwordError.style.display = 'none';
}

//登录
function login() {
    var loginId = document.getElementById("loginId").value.trim();
    var password = document.getElementById("password").value.trim();

    // 获取错误提示元素
    var loginIdError = document.getElementById("loginIdError");
    var passwordError = document.getElementById("passwordError");

    // 重置错误提示
    loginIdError.textContent = "";
    passwordError.textContent = "";
    loginIdError.style.display = "none";
    passwordError.style.display = "none";

    // 标记是否通过校验
    var isValid = true;

    // 验证账号
    if (!validateLoginId(loginId, "loginIdError")) {
        isValid = false;
    }
    // 验证密码
    if (!validatePassword(password, "passwordError")) {
        isValid = false;
    }

    // // 校验
    // if (loginId === "" && password === "") {
    //     loginIdError.textContent = "请输入账号！";
    //     passwordError.textContent = "请输入密码！";
    //     loginIdError.style.display = "block";
    //     isValid = false;
    // } else if (loginId === "") {
    //     loginIdError.textContent = "请输入账号！";
    //     loginIdError.style.display = 'block';
    //     isValid = false;
    // } else if (password === "") {
    //     passwordError.textContent = "请输入密码！";
    //     passwordError.style.display = 'block';
    //     isValid = false;
    // } else {
    //     passwordError.style.display = 'none';
    // }

    // 如果校验失败，则不执行AJAX请求
    if (!isValid) {
        return;
    }

    $.ajax({
        dataType: 'json',
        type: 'GET',//接口请求类型
        data: {loginId: loginId, password: password},//接口参数
        url: 'http://localhost:8089/user/query-user-by-loginId',//后端接口地址
        success: function (res) {//接口成功返回
            console.log(res);
            if (res && res.code == 1) {
                // alert('登录成功！');

                if (res.data) {
                    //使用localStorage将用户的基本信息查询存储到浏览器缓存中
                    localStorage.setItem("user", JSON.stringify(res.data));
                }
                window.location.href = 'product.html';
            } else {
                // 登录失败，显示后端返回的错误信息
                // alert('登录失败！');
                var errorMsg = res && res.msg ? res.msg : "登录失败！";
                // 可以选择显示在某个特定位置，这里选择显示在账号错误提示处
                loginIdError.textContent = errorMsg;
                loginIdError.style.display = "block";
            }
        }, error: function (XMLHttpRequest, textStatus, errorThrown) {
            console.error("登录请求发生错误:");
            console.error("状态码:", XMLHttpRequest.status);
            console.error("状态文本:", textStatus);
            console.error("错误信息:", errorThrown);
            // 网络错误或服务器错误，显示通用提示
            loginIdError.textContent = "网络错误！";
            loginIdError.style.display = "block";
        }
    });
}


// 注册
function register() {
    // 获取所有输入值
    var loginId = document.getElementById("registerLoginId").value;
    var name = document.getElementById("registerName").value.trim();
    var password = document.getElementById("registerPassword").value;
    var email = document.getElementById("registerEmail").value;
    var phone = document.getElementById("registerPhone").value;

    // 标记是否通过所有校验
    var isValid = true;

    // 1. 验证账号
    if (!validateLoginId(loginId, "registerLoginIdError")) {
        isValid = false;
    }
    // 2. 验证用户名 (新增)
    var nameError = document.getElementById("registerNameError");
    if (name === "") {
        nameError.textContent = "用户名不能为空！";
        nameError.style.display = 'block';
        isValid = false;
    } else {
        nameError.style.display = 'none';
    }
    // 3. 验证密码
    if (!validatePassword(password, "registerPasswordError")) {
        isValid = false;
    }
    // 4. 验证邮箱 (新增)
    if (!validateEmail(email, "registerEmailError")) {
        isValid = false;
    }
    // 5. 验证手机号 (新增)
    if (!validatePhone(phone, "registerPhoneError")) {
        isValid = false;
    }

    // 如果校验失败，则不执行AJAX请求
    if (!isValid) {
        return;
    }

    // 封装数据并发送AJAX请求
    var data = {
        loginId: loginId,
        name: name,
        password: password,
        email: email,
        phone: phone
    };

    $.ajax({
        dataType: 'json',
        contentType: 'application/json',
        type: 'POST',
        data: JSON.stringify(data),
        url: 'http://localhost:8089/user/add-user-info',
        success: function (res) {
            console.log(res);
            if (res && res.code == 1) {
                alert("注册成功！");
                toggle(); // 切换到登录表单
            } else {
                var errorMsg = res && res.msg ? res.msg : "注册失败！";
                // 可以将后端返回的通用错误显示在一个公共位置，这里选择显示在账号错误提示处
                document.getElementById("registerLoginIdError").textContent = errorMsg;
                document.getElementById("registerLoginIdError").style.display = "block";
            }
        }, error: function (XMLHttpRequest, textStatus, errorThrown) {
            console.error("注册请求发生错误:", textStatus, errorThrown);
            alert("网络错误！");
        }
    });
}

// 验证账号
function validateLoginId(value, errorElementId) {
    // 获取错误提示元素
    var errorEl = document.getElementById(errorElementId);

    // 去除首尾空格
    var trimmedValue = value.trim();

    // 1. 检查是否为空
    if (trimmedValue === "") {
        errorEl.textContent = "账号不能为空！";
        errorEl.style.display = 'block';
        return false;
    }
    // 2. 检查长度是否大于等于5位
    else if (trimmedValue.length < 5) {
        errorEl.textContent = "账号长度不能少于5位！";
        errorEl.style.display = 'block';
        return false;
    }
    // 3. 验证通过
    else {
        errorEl.style.display = 'none';
        return true;
    }
}


// 验证密码
function validatePassword(value, errorElementId) {
    // 获取错误提示元素
    var errorEl = document.getElementById(errorElementId);

    // 去除首尾空格
    var trimmedValue = value.trim();

    // 1. 检查是否为空
    if (trimmedValue === "") {
        errorEl.textContent = "密码不能为空！";
        errorEl.style.display = 'block';
        return false;
    }
    // 2. 检查长度是否大于等于5位
    else if (trimmedValue.length < 5) {
        errorEl.textContent = "密码长度不能少于5位！";
        errorEl.style.display = 'block';
        return false;
    }
    // 3. 验证通过
    else {
        errorEl.style.display = 'none';
        return true;
    }
}

// 验证邮箱
function validateEmail(value, errorElementId) {
    var errorEl = document.getElementById(errorElementId);
    var trimmedValue = value.trim();

    if (trimmedValue === "") {
        errorEl.textContent = "邮箱不能为空！";
        errorEl.style.display = 'block';
        return false;
    }

    // 简单的邮箱正则表达式
    var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(trimmedValue)) {
        errorEl.textContent = "请输入有效的邮箱地址！";
        errorEl.style.display = 'block';
        return false;
    } else {
        errorEl.style.display = 'none';
        return true;
    }
}

// 验证手机号
function validatePhone(value, errorElementId) {
    var errorEl = document.getElementById(errorElementId);
    var trimmedValue = value.trim();

    if (trimmedValue === "") {
        errorEl.textContent = "手机号不能为空！";
        errorEl.style.display = 'block';
        return false;
    }

    // 简单的手机号正则表达式（以1开头，共11位数字）
    var phoneRegex = /^1[3-9]\d{9}$/;
    if (!phoneRegex.test(trimmedValue)) {
        errorEl.textContent = "请输入有效的手机号！";
        errorEl.style.display = 'block';
        return false;
    } else {
        errorEl.style.display = 'none';
        return true;
    }
}