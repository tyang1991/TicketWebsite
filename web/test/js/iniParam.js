var xx;
$.post("/timeWindow",function(data){
    //alert(data.length);
    xx = parseInt(data);
},"text");

alert(xx);
        