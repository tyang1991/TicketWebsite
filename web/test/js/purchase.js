function checkNull(input){
    var flag;
    var value = $(input).val();
    var spanNode = $("#message");
    if(value==""){
        $(input).addClass("invalideInput");
        spanNode.text("Required field").css("color", "red");
        flag = false;
    }else{
        $(input).removeClass("invalideInput");
        spanNode.text("");
        flag = true;
    }
    return flag;
}

function checkEmail(x){
    var flag;
    if(!checkNull(x)) return false;
    var reg = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/;
    var value = $(x).val();
    var spanNode = $("#message");

    if(!reg.test(value)){
        $(x).addClass("invalideInput");
        spanNode.text("Invalid email format").css("color", "red");
        flag = false;
    }else {
        $(x).removeClass("invalideInput");
        spanNode.text("");
        flag= true;
    }
    return flag;
}

function buyTickets(){
    var firstname = $("#firstname").val();
    var lastname = $("#lastname").val();
    var email = $("#email").val();
    var arriveTime = $(".arriveTime");
    var departTime = $(".departTime");

    if(checkNull($("#firstname")) && checkNull($("#lastname")) && checkEmail($("#email"))) {
        var flightNums = $("span.flightNum");
        var flights = "";
        var time = "";

        for (var x = 0; x < arriveTime.length; x++) {
           time += departTime.eq(x).text()+"-"+arriveTime.eq(x).text()+",";
        }

        time = time.substring(0,time.length-1);

        for (var i = 0; i < flightNums.length; i++) {
            flights += flightNums.eq(i).text() + ",";
        }
        flights = flights.substring(0, flights.length - 1);
        //alert(flights);
        window.location.href = "/purchase?firstname=" + firstname + "&lastname=" + lastname + "&email=" + email + "&flightNum=" + flights+"&time="+time;
    }else{
        return;
    }
}
