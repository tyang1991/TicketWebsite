<script type="text/javascript">
  $(window).load(function() {
    $.post("/timeWindow", function (data) {
      var str =data.split("|");
      var goarrive1 = parseInt(str[6]);
      var goarrive2 = parseInt(str[7]);
      $("#comeArriveSlider").slider({
        range: true,
        min: 0,
        max: 1440,
        step: 15,
        values: [goarrive1, goarrive2],
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


          $('#comeArriveTime1').html(hours1 + ':' + minutes1);

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

          $('#comeArriveTime2').html(hours2 + ':' + minutes2);
        }
      });
    });
  });
</script>

<div class="time-range">
  <p>Arrive Time: <span id="comeArriveTime1">${outputComeAr1!=null?outputComeAr1:"12:00 AM"}</span> - <span id="comeArriveTime2">${outputComeAr2!=null?outputComeAr2:"11:59 PM"}</span>

  </p>
  <div class="sliders_step1">
    <div id="comeArriveSlider"></div>
  </div>
</div>