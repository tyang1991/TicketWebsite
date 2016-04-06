function showDetail(node){
    var detailLink = $(node).parent().parent().parent().parent().parent().next();
    //alert(detailLink);
    detailLink.toggleClass("transferDetail");
}

function changeFunc(){
    var selectBox = document.getElementById("sortBy");
    var selectedValue = selectBox.options[selectBox.selectedIndex].value;
    //alert(selectedValue);
    if(selectedValue==="1"){
        window.location.href ="/sort?type=1";
    }else if(selectedValue==="2"){
        window.location.href ="/sort?type=2";
    }else if(selectedValue==="3"){
        window.location.href ="/sort?type=3";
    }else{
        window.location.href ="/sort?type=4";
    }
}

function isChecked(){
    var one;
    var non;
    document.getElementById("oneStop").checked ? one="checked" : one = "unchecked";
    document.getElementById("nonStop").checked ? non="checked" : non = "unchecked";

    //alert(one + non);
    //
    //alert($("#nonStop").val());
    //alert($("#oneStop").val());
    window.location.href ="/type?one="+one+"&non="+non;
}

function goToCheckout(btn){
    var index = $(btn).parent().parent().parent().index();
    //alert("index="+index);
    window.location.href ="/checkout?index="+index;
}

function goBack(){
    window.location.href ="/goback";
}

function changeTime(){
    var goDe1 = $("#goDepartTime1").text();
    var goDe2 = $("#goDepartTime2").text();
    var goAr1 = $("#goArriveTime1").text();
    var goAr2 = $("#goArriveTime2").text();

    var comeDe1 = $("#comeDepartTime1").text();
    var comeDe2 = $("#comeDepartTime2").text();
    var comeAr1 = $("#comeArriveTime1").text();
    var comeAr2 = $("#comeArriveTime2").text();

    var param = goDe1+"|"+goDe2+"|"+goAr1+"|"+goAr2;
    if(comeDe1!=null && comeDe1.length>0){
        param += "|"+comeDe1+"|"+comeDe2+"|"+comeAr1+"|"+comeAr2;
    }
    //alert(param);
    window.location.href ="/timeWindow?param="+param;
}
