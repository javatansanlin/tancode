function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg); //匹配目标参数
    if (r != null) return unescape(r[2]);
    return null; //返回参数值
}
$(function(){
    var oid  = getQueryString("oid");
    $("#id").attr("href","index.html?oid="+oid);
    $("#jfdh").attr("href","jfgoods.html?oid="+oid);
    $("#me").attr("href","user.html?oid="+oid);
})

