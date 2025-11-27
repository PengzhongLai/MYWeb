//初始化方法
$("document").ready(function (){
  if(localStorage.length>0){//如果浏览器缓存中有参数
   const user=JSON.parse(localStorage.getItem('user'));
   // console.log(user);
   //个人中心信息展示
   personInfo(user);
   //查询商品列表数据
   queryProduct();
  }else {
   loginOut();//跳转登录
  }
});
//个人中心
function  personInfo(user){
    document.getElementById("detailLoginId").textContent=user.loginId;
    document.getElementById("detailName").textContent=user.name;
    document.getElementById("detailImgSrc").src=user.userImgSrc;
    document.getElementById("detailPassword").textContent=user.password;
    document.getElementById("detailEmail").textContent=user.email;
    document.getElementById("detailPhone").textContent=user.phone;
    document.getElementById("detailUpdateTime").textContent=user.updateTime;
    document.getElementById("userName").textContent = user.name;

//显示头像
    document.getElementById("userLogo").src=user.userImgSrc;
    //显示用户名
    document.getElementById("userName").textContent = user.name;

}

//退出
function loginOut(){
 localStorage.removeItem('user');//移除缓存参数
 window.location.href='login.html';//跳转登录
}
//查询商品列表数据
function queryProduct(){
 //获取输入的商品名称参数
 var name=document.getElementById("search").value;
    $.ajax({
        dataType:'json',
        type:'GET',//接口请求类型
        data:{name:name},//接口参数
        url:'http://localhost:8089/product/query-product-info',//后端接口地址
        success:function (res){//接口成功返回
            // console.log(res);
            document.getElementById("msg").innerHTML=res.msg;
            var productDiv=document.getElementById("productList");
            productDiv.innerHTML='';
            var html='';
            if(res.data.length>0){
                var list=res.data;
                for(var i=0;i<list.length;i++){//循环获取商品列表数据
                    var json=list[i];//获取每个商品数据放入json变量中
                    html+=' <div class="product">\n' +
                        '           <img onclick="productDetail('+json.id+')" src="'+json.imgSrc+'" alt="商品图片">\n' +
                        '           <h3>'+json.name+'</h3>\n' +
                        '           <p>'+json.description+'</p>\n' +
                        '           <p class="price">价格：￥'+json.price+'</p>\n' +
                        '           <div style="display: flex">\n' +
                        '               <p><a class="evaluate">'+json.evaluateList.length+'</a>条评价</p>\n' +
                        '               <button class="gwc-button">+添加到购物车</button>\n' +
                        '           </div>\n' +
                        '\n' +
                        '          </div>';
                }
                //将循环拼接的html代码嵌入到productDiv中
                productDiv.innerHTML=html;
            }
        },error:function (XMLHttpRequest,textStatus,errorThrown){//接口错误返回
            alert("查询失败！");
        }
    });
}

// 初始化方法中添加精选商品加载
$("document").ready(function (){
    if(localStorage.length>0){
        const user=JSON.parse(localStorage.getItem('user'));
        personInfo(user);
        queryProduct();  // 加载普通商品列表
        loadFeaturedProducts();  // 新增：加载精选商品
    }else {
        loginOut();
    }
});

// 精选商品:加载好评率≥90
function loadFeaturedProducts() {
    $.ajax({
        dataType: 'json',
        type: 'GET',
        url: 'http://localhost:8089/product/query-featured-products',  // 调用新增接口
        success: function (res) {
            // console.log("精选商品：", res.data);
            var featuredDiv = document.getElementById("featuredList");
            featuredDiv.innerHTML = '';
            var html = '';
            if (res.data.length > 0) {
                var list = res.data;
                for (var i = 0; i < list.length; i++) {
                    var json = list[i];
                    // 拼接商品卡片（复用现有样式，新增好评率显示）
                    html += ' <div class="product">\n' +
                        '           <img onclick="productDetail('+json.id+')" src="'+json.imgSrc+'" alt="商品图片">\n' +
                        '           <h3>'+json.name+'</h3>\n' +
                        '           <p>'+json.description+'</p>\n' +
                        '           <p class="price">价格：￥'+json.price+'</p>\n' +
                        '           <p style="color: #22c55e; padding: 0 0.8rem;">好评率：'+json.goodCommentRate+'%</p>\n' +  // 显示好评率
                        '           <div style="display: flex">\n' +
                        '               <p><a class="evaluate">'+json.evaluateList.length+'</a>条评价</p>\n' +
                        '               <button class="gwc-button">+添加到购物车</button>\n' +
                        '           </div>\n' +
                        '          </div>';
                }
                featuredDiv.innerHTML = html;
            } else {
                featuredDiv.innerHTML = '<p style="text-align: center; padding: 2rem;">暂无符合条件的精选商品</p>';
            }
        },
        error: function () {
            alert("精选商品加载失败！");
        }
    });
}