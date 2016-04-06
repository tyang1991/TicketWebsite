<script type="text/javascript">
  $(window).load(function() {
    $.post("/timeWindow?",function(data){
      //alert(data.length);
      var str =data.split("|");
//            alert(x);
      var godepart1 = parseInt(str[4]);
      var godepart2 = parseInt(str[5]);
      $("#comeDepartSlider").slider({
        range: true,
        min: 0,
        max: 1440,
        step: 15,
        values: [godepart1, godepart2],
        slide: function (e, ui) {
          var hours1 = Math.floor(ui.values[0] / 60);
          var minutes1 = ui.values[0] - (hours1 * 60);

          if (hours1.length == 1) hours1 = '0' + hours1;
          if (minutes1.length == 1) minutes1 = '0' + minutes1;
          if (minutes1 == 0) minutes1 = '00';
          if (hours1 >= 12) {
            if (hours1 == 12) {
              hours1 = hours1;
              minutes1 = minutes1 + " PM";
            } else {
              hours1 = hours1 - 12;
              minutes1 = minutes1 + " PM";
            }
          } else {
            hours1 = hours1;
            minutes1 = minutes1 + " AM";
          }
          if (hours1 == 0) {
            hours1 = 12;
            minutes1 = minutes1;
          }


          $('#comeDepartTime1').html(hours1 + ':' + minutes1);

          var hours2 = Math.floor(ui.values[1] / 60);
          var minutes2 = ui.values[1] - (hours2 * 60);

          if (hours2.length == 1) hours2 = '0' + hours2;
          if (minutes2.length == 1) minutes2 = '0' + minutes2;
          if (minutes2 == 0) minutes2 = '00';
          if (hours2 >= 12) {
            if (hours2 == 12) {
              hours2 = hours2;
              minutes2 = minutes2 + " PM";
            } else if (hours2 == 24) {
              hours2 = 11;
              minutes2 = "59 PM";
            } else {
              hours2 = hours2 - 12;
              minutes2 = minutes2 + " PM";
            }
          } else {
            hours2 = hours2;
            minutes2 = minutes2 + " AM";
          }

          $('#comeDepartTime2').html(hours2 + ':' + minutes2);
        }
      });
    },"text");
  });
</script>

<div class="time-range">
  <p>Depart Time: <span id="comeDepartTime1">${outputComeDe1!=null?outputComeDe1:"12:00 AM"}</span> - <span id="comeDepartTime2">${outputComeDe2!=null?outputComeDe2:"11:59 PM"}</span>

  </p>
  <div class="sliders_step1">
    <div id="comeDepartSlider"></div>
  </div>
</div>
