// 合并重复的初始化方法，避免重复执行
$("document").ready(function () {
    // 更严谨地检查localStorage中是否存在user信息
    if (localStorage.getItem('user')) {
        try {
            const user = JSON.parse(localStorage.getItem('user'));
            personInfo(user);
            queryProduct();       // 加载普通商品列表
            loadFeaturedProducts(); // 加载精选商品
        } catch (e) {
            console.error("用户信息解析失败：", e);
            loginOut(); // 解析失败时跳转登录
        }
    } else {
        loginOut(); // 无用户信息时跳转登录
    }
});

// 个人中心信息展示
function personInfo(user) {
    // 避免DOM元素不存在导致的报错，添加存在性检查
    const elements = {
        detailLoginId: document.getElementById("detailLoginId"),
        detailName: document.getElementById("detailName"),
        detailImgSrc: document.getElementById("detailImgSrc"),
        detailPassword: document.getElementById("detailPassword"),
        detailEmail: document.getElementById("detailEmail"),
        detailPhone: document.getElementById("detailPhone"),
        detailUpdateTime: document.getElementById("detailUpdateTime"),
        userName: document.getElementById("userName"),
        userLogo: document.getElementById("userLogo")
    };

    // 安全赋值，避免操作null/undefined
    if (elements.detailLoginId) elements.detailLoginId.textContent = user.loginId || '';
    if (elements.detailName) elements.detailName.textContent = user.name || '';
    if (elements.detailImgSrc) elements.detailImgSrc.src = user.userImgSrc || '';
    if (elements.detailPassword) elements.detailPassword.textContent = user.password || '';
    if (elements.detailEmail) elements.detailEmail.textContent = user.email || '';
    if (elements.detailPhone) elements.detailPhone.textContent = user.phone || '';
    if (elements.detailUpdateTime) elements.detailUpdateTime.textContent = user.updateTime || '';
    if (elements.userName) elements.userName.textContent = user.name || '';
    if (elements.userLogo) elements.userLogo.src = user.userImgSrc || '';
}

// 退出登录
function loginOut() {
    localStorage.removeItem('user');
    window.location.href = 'login.html';
}

// 查询商品列表数据
function queryProduct() {
    const name = document.getElementById("search").value || '';
    $.ajax({
        dataType: 'json',
        type: 'GET',
        data: { name: name,
            publishState: 1
        },
        url: 'http://localhost:8089/product/query-product-info',
        success: function (res) {
            const msgEl = document.getElementById("msg");
            if (msgEl) msgEl.innerHTML = res.msg || '查询结果';

            const productDiv = document.getElementById("productList");
            if (!productDiv) return; // 避免DOM不存在导致的报错

            productDiv.innerHTML = '';
            let html = '';

            if (res.data && res.data.length > 0) {
                res.data.forEach(json => {
                    // 处理评价列表可能为null的情况，价格格式化保留两位小数
                    const evaluateCount = (json.evaluateList || []).length;
                    const price = json.price ? json.price.toFixed(2) : '0.00';

                    html += ` <div class="product">
                      <img onclick="productDetail(${json.id || 0})" src="${json.imgSrc || ''}" alt="商品图片">
                      <h3>${json.name || '未知商品'}</h3>
                      <p>${json.description || '无描述'}</p>
                      <p class="price">价格：￥${price}</p>
                      <div style="display: flex">
                          <p><a class="evaluate">${evaluateCount}</a>条评价</p>
                          <button class="gwc-button">+添加到购物车</button>
                      </div>
                    </div>`;
                });
                productDiv.innerHTML = html;
            } else {
                productDiv.innerHTML = '<p style="text-align: center; padding: 2rem;">暂无商品数据</p>';
            }
        },
        error: function (xhr, status, error) {
            console.error("查询失败：", error);
            alert("查询失败，请稍后重试！");
        }
    });
}

// 加载精选商品(好评率≥90)
function loadFeaturedProducts() {
    $.ajax({
        dataType: 'json',
        type: 'GET',
        url: 'http://localhost:8089/product/query-featured-products',
        data: { publishState: 1 },
        success: function (res) {
            const featuredDiv = document.getElementById("featuredList");
            if (!featuredDiv) return;

            featuredDiv.innerHTML = '';
            let html = '';

            if (res.data && res.data.length > 0) {
                res.data.forEach(json => {
                    const evaluateCount = (json.evaluateList || []).length;
                    const price = json.price ? json.price.toFixed(2) : '0.00';
                    const goodRate = json.goodCommentRate || 0; // 处理好评率可能为null的情况

                    html += ` <div class="product">
                      <img onclick="productDetail(${json.id || 0})" src="${json.imgSrc || ''}" alt="商品图片">
                      <h3>${json.name || '未知商品'}</h3>
                      <p>${json.description || '无描述'}</p>
                      <p class="price">价格：￥${price}</p>
                      <p style="color: #22c55e; padding: 0 0.8rem;">好评率：${goodRate}%</p>
                      <div style="display: flex">
                          <p><a class="evaluate">${evaluateCount}</a>条评价</p>
                          <button class="gwc-button">+添加到购物车</button>
                      </div>
                    </div>`;
                });
                featuredDiv.innerHTML = html;
            } else {
                featuredDiv.innerHTML = '<p style="text-align: center; padding: 2rem;">暂无符合条件的精选商品</p>';
            }
        },
        error: function (xhr, status, error) {
            console.error("精选商品加载失败：", error);
            alert("精选商品加载失败，请稍后重试！");
        }
    });
}

// 新增商品详情跳转函数（避免未定义报错）
function productDetail(id) {
    if (id && id > 0) {
        // 示例：跳转到商品详情页，携带商品ID
        window.location.href = `product-detail.html?id=${id}`;
    } else {
        alert("商品信息无效");
    }
}