// 管理员登录
function adminLogin() {
    var loginId = document.getElementById("adminLoginId").value.trim();
    var password = document.getElementById("adminPassword").value.trim();

    // 获取错误提示元素
    var loginIdError = document.getElementById("adminLoginIdError");
    var passwordError = document.getElementById("adminPasswordError");

    // 重置错误提示
    loginIdError.textContent = "";
    passwordError.textContent = "";
    loginIdError.style.display = "none";
    passwordError.style.display = "none";

    // 验证账号和密码（复用原验证规则）
    var isValid = true;
    if (!validateLoginId(loginId, "adminLoginIdError")) {
        isValid = false;
    }
    if (!validatePassword(password, "adminPasswordError")) {
        isValid = false;
    }

    if (!isValid) {
        return;
    }

    // 调用管理员登录接口（需后端新增）
    $.ajax({
        dataType: 'json',
        type: 'GET',
        data: {loginId: loginId, password: password},
        url: 'http://localhost:8089/admin/query-admin-by-loginId', // 管理员登录接口（与用户接口区分）
        success: function (res) {
            console.log(res);
            if (res && res.code == 1) {
                // 登录成功，存储管理员信息并跳转后台管理页
                localStorage.setItem("admin", JSON.stringify(res.data));
                window.location.href = 'admin-manage.html'; // 跳转至后台管理页面
            } else {
                var errorMsg = res && res.msg ? res.msg : "管理员账号或密码错误！";
                loginIdError.textContent = errorMsg;
                loginIdError.style.display = "block";
            }
        }, error: function (XMLHttpRequest, textStatus, errorThrown) {
            console.error("登录请求错误:", textStatus, errorThrown);
            loginIdError.textContent = "网络错误，请重试！";
            loginIdError.style.display = "block";
        }
    });
}

// 复用原验证方法（账号和密码校验规则）
function validateLoginId(value, errorElementId) {
    var errorEl = document.getElementById(errorElementId);
    var trimmedValue = value.trim();

    if (trimmedValue === "") {
        errorEl.textContent = "账号不能为空！";
        errorEl.style.display = 'block';
        return false;
    } else if (trimmedValue.length < 5) {
        errorEl.textContent = "账号长度不能少于5位！";
        errorEl.style.display = 'block';
        return false;
    } else {
        errorEl.style.display = 'none';
        return true;
    }
}

function validatePassword(value, errorElementId) {
    var errorEl = document.getElementById(errorElementId);
    var trimmedValue = value.trim();

    if (trimmedValue === "") {
        errorEl.textContent = "密码不能为空！";
        errorEl.style.display = 'block';
        return false;
    } else if (trimmedValue.length < 5) {
        errorEl.textContent = "密码长度不能少于5位！";
        errorEl.style.display = 'block';
        return false;
    } else {
        errorEl.style.display = 'none';
        return true;
    }
}