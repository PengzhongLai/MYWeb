//菜单切换
function showTab(evt,tabName,buttonId){
    var i,tabcontent,tablinks;
    tabcontent=document.getElementsByClassName("tab-content");
    //隐藏所有选项卡内容
    for(i=0;i<tabcontent.length;i++){
        tabcontent[i].classList.remove("active");
    }
    //移除所有选项卡的激活状态
    tablinks=document.getElementsByClassName("tab");
    for(i=0;i<tablinks.length;i++){
        tablinks[i].classList.remove("active");
        //移除所有选项卡选中的样式
        tablinks[i].classList.remove("tab-font-color");
    }
    //指定显示选中的选项卡和选中的样式
    var tabId=document.getElementById(tabName);
    tabId.classList.add("active");
    evt.currentTarget.classList.add("active");
    var buttonId=document.getElementById(buttonId);
    buttonId.classList.add("tab-font-color");
}