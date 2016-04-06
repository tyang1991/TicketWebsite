<div id="transferDetail">
  <%--出发航程--%>
  <div class="departDetail">
    <table>
      <tr>
        <td colspan="5"><div class="subTitle">Depart :&nbsp;${airNames[showItem.value.departCode]}(${showItem.value.departCode})</div></td>
      </tr>
      <tr>
        <td>
          <div class="floatTitle">Flight <span class="flightNum">${showItem.value.number}</span></div>
        </td>
        <td>
          <div class="floatTitle">${showItem.value.departCode} <b class="departTime"><fmt:formatDate value="${showItem.value.departTime}" pattern="HH:mm" timeZone="${deZone}"/></b></div>
          <div class="minorInfo"><fmt:formatDate value="${showItem.value.departTime}" pattern="EEEEEE"/></div>
        </td>
        <td>
          <div class="floatTitle">${showItem.value.arriveCode} <b class="arriveTime"><fmt:formatDate value="${showItem.value.arriveTime}" pattern="HH:mm" timeZone="${arZone}"/></b></div>
          <div class="minorInfo"><fmt:formatDate value="${showItem.value.arriveTime}" pattern="EEEEEE"/></div>
        </td>
        <td class="minorInfo"><fmt:parseNumber integerOnly="true" value="${showItem.value.flightTime/60}"/>h <fmt:formatNumber value="${showItem.value.flightTime%60}" pattern="#" type="number"/>m </td>
      </tr>
      <tr>
        <td colspan="5"><div class="subTitle">Arrive : ${airNames[showItem.value.arriveCode]}(${showItem.value.arriveCode})</div></td>
      </tr>
    </table>
  </div>
  <%--返回航程--%>
  <div class="departDetail">
  </div>
</div>
