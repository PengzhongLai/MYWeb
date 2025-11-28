// 页面初始化
$("document").ready(function() {
    // 验证管理员登录状态
    if (!localStorage.getItem("admin")) {
        window.location.href = "admin-login.html";
        return;
    }
    // 加载商品列表
    queryProducts();
});

// 退出登录
function loginOut() {
    localStorage.removeItem("admin");
    window.location.href = "admin-login.html";
}

// 查询商品列表
function queryProducts() {
    // 收集搜索条件
    const params = {};
    const name = $("#searchName").val().trim();
    const placeSale = $("#searchPlace").val().trim();
    const publishState = $("#searchState").val();

    if (name) params.name = name;         // 仅当name非空时传递
    if (placeSale) params.placeSale = placeSale; // 仅当placeSale非空时传递
    if (publishState) params.publishState = publishState;
    $.ajax({
        type: "GET",
        url: "http://localhost:8089/product/query-product-info",
        data: params, // 传递过滤后的参数
        dataType: "json",
        success: function(res) {
            if (res.data && res.data.length > 0) {
                renderProductTable(res.data);
            } else {
                $("#productTableBody").html("<tr><td colspan='8' style='text-align:center'>暂无商品数据</td></tr>");
            }
        },
        error: function() {
            alert("查询商品失败");
        }
    });
}

// 渲染商品表格
function renderProductTable(products) {
    let html = "";
    products.forEach(product => {
        html += `
        <tr>
            <td>${product.id}</td>
            <td>${product.name}</td>
            <td>${product.description || '-'}</td>
            <td><img src="${product.imgSrc}" alt="${product.name}"></td>
            <td>${product.price.toFixed(2)}</td>
            <td>${product.placeSale || '-'}</td>
            <td>${product.publishState === 1 ? '<span style="color:green">已发布</span>' : '<span style="color:gray">未发布</span>'}</td>
            <td>
                <button class="operate-btn edit-btn" onclick="showEditModal(${product.id})">编辑</button>
                <button class="operate-btn delete-btn" onclick="showDeleteModal(${product.id})">删除</button>
            </td>
        </tr>`;
    });
    $("#productTableBody").html(html);
}

// 显示编辑模态框
function showEditModal(id) {
    // 获取商品详情
    $.ajax({
        type: "GET",
        url: "http://localhost:8089/product/query-product-info",
        data: { id: id },
        dataType: "json",
        success: function(res) {
            if (res.data && res.data.length > 0) {
                const product = res.data[0];
                // 填充表单
                $("#editId").val(product.id);
                $("#editName").val(product.name);
                $("#editDescription").val(product.description);
                $("#editSource").val(product.source);
                $("#editManufacturer").val(product.manufacturer);
                $("#editPlaceSale").val(product.placeSale);
                $("#editPrice").val(product.price);
                $("#editShelfLife").val(product.shelfLife);
                $("#editQuantityNum").val(product.quantityNum);
                $("#editPublishState").val(product.publishState);
                $("#editCurrentImg").attr("src", product.imgSrc);
                // 显示模态框
                $("#editModal").addClass("active");
            }
        }
    });
}

// 关闭编辑模态框
function closeEditModal() {
    $("#editModal").removeClass("active");
}

// 提交编辑
function submitEdit() {
    const formData = new FormData();
    formData.append("id", $("#editId").val());
    formData.append("name", $("#editName").val());
    formData.append("description", $("#editDescription").val());
    formData.append("source", $("#editSource").val());
    formData.append("manufacturer", $("#editManufacturer").val());
    formData.append("placeSale", $("#editPlaceSale").val());
    formData.append("price", $("#editPrice").val());
    formData.append("shelfLife", $("#editShelfLife").val());
    formData.append("quantityNum", $("#editQuantityNum").val());
    formData.append("publishState", $("#editPublishState").val());

    // 添加图片（如果有选择）
    const imgFile = $("#editImgFile")[0].files[0];
    if (imgFile) {
        formData.append("imgFile", imgFile);
    }

    $.ajax({
        type: "POST",
        url: "http://localhost:8089/product/update",
        data: formData,
        contentType: false,
        processData: false,
        dataType: "json",
        success: function(res) {
            if (res.code === 1) {
                closeEditModal();
                queryProducts(); // 刷新列表
                alert("修改成功");
            } else {
                alert(res.msg || "修改失败");
            }
        },
        error: function() {
            alert("修改请求失败");
        }
    });
}

// 显示删除模态框
function showDeleteModal(id) {
    $("#deleteId").val(id);
    $("#deleteModal").addClass("active");
}

// 关闭删除模态框
function closeDeleteModal() {
    $("#deleteModal").removeClass("active");
}

// 提交删除
function submitDelete() {
    const id = $("#deleteId").val();
    $.ajax({
        type: "DELETE",
        url: `http://localhost:8089/product/delete/${id}`,
        dataType: "json",
        success: function(res) {
            if (res.code === 1) {
                closeDeleteModal();
                queryProducts(); // 刷新列表
                alert("删除成功");
            } else {
                alert(res.msg || "删除失败");
            }
        },
        error: function() {
            alert("删除请求失败");
        }
    });
}

// 显示新增模态框
function showAddModal() {
    $("#addForm")[0].reset(); // 重置表单
    $("#addModal").addClass("active");
}

// 检查新增取消
function checkAddCancel() {
    // 判断是否有输入内容
    const hasContent = [
        $("#addName").val(),
        $("#addDescription").val(),
        $("#addSource").val(),
        $("#addManufacturer").val(),
        $("#addPlaceSale").val(),
        $("#addPrice").val(),
        $("#addShelfLife").val(),
        $("#addQuantityNum").val()
    ].some(val => val.trim() !== "");

    if (hasContent) {
        if (confirm("已有输入内容，确定要取消吗？")) {
            $("#addModal").removeClass("active");
        }
    } else {
        $("#addModal").removeClass("active");
    }
}

// 提交新增
function submitAdd() {
    const formData = new FormData();
    formData.append("name", $("#addName").val());
    formData.append("description", $("#addDescription").val());
    formData.append("source", $("#addSource").val());
    formData.append("manufacturer", $("#addManufacturer").val());
    formData.append("placeSale", $("#addPlaceSale").val());
    formData.append("price", $("#addPrice").val());
    formData.append("shelfLife", $("#addShelfLife").val());
    formData.append("quantityNum", $("#addQuantityNum").val());
    formData.append("publishState", $("#addPublishState").val());

    // 添加图片（如果有选择，否则用默认图）
    const imgFile = $("#addImgFile")[0].files[0];
    if (imgFile) {
        formData.append("imgFile", imgFile);
    } else {
        formData.append("useDefaultImg", "true"); // 标记使用默认图
    }

    $.ajax({
        type: "POST",
        url: "http://localhost:8089/product/add",
        data: formData,
        contentType: false,
        processData: false,
        dataType: "json",
        success: function(res) {
            if (res.code === 1) {
                $("#addModal").removeClass("active");
                queryProducts(); // 刷新列表
                alert("新增成功");
            } else {
                alert(res.msg || "新增失败");
            }
        },
        error: function() {
            alert("新增请求失败");
        }
    });
}