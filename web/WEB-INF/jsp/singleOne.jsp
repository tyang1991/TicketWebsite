<c:set value="${my:formater(showItem.value['0'].arriveCode)}" var="midZone"/>
<div class="cell">
  <%--左半部分--%>
  <div class="left">
    <div class="price">
      <fmt:setLocale value="en_US"/>
      <fmt:formatNumber type="currency" value="${showItem.key.totalPrice}"/>
    </div>
    <div class="select">
      <input type="button" onclick="goToCheckout(this)" value="Select"/>
    </div>
  </div>
  <%--右半部分--%>
  <div class="right">
    <table class="flightDetail">
      <tr>
        <td>${showItem.value['0'].departCode} <b><fmt:formatDate value="${showItem.value['0'].departTime}" pattern="HH:mm" timeZone="${deZone}"/></b></td>
        <td>${showItem.value['1'].arriveCode} <b><fmt:formatDate value="${showItem.value['1'].arriveTime}" pattern="HH:mm" timeZone="${arZone}"/></b></td>
        <td class="minorInfo"><fmt:parseNumber integerOnly="true" value="${showItem.key.totalTime/60}"/>h <fmt:formatNumber value="${showItem.key.totalTime%60}" pattern="#" type="number"/>m </td>
        <%--connect['0'].flightTime + connect['1'].flightTime--%>
        <td class="minorInfo">1 stop(${showItem.value['0'].arriveCode})</td>
      </tr>
      <tr>
        <td colspan="3"><button onclick="showDetail(this)" class="detailLink">Show Detail</button></td>
      </tr>
    </table>
  </div>
  <%--飞行转机细节--%>
  <div class="transferDetail">
    <%--出发航程--%>
    <div class="departDetail">
      <table>
        <tr>
          <td colspan="5"><div class="subTitle">Depart :&nbsp;${airNames[showItem.value['0'].departCode]}(${showItem.value['0'].departCode})</div></td>
        </tr>
        <tr>
          <td>
            <div class="floatTitle">Flight <span class="flightNum">${showItem.value['0'].number}</span></div>
          </td>
          <td>
            <div class="floatTitle">${showItem.value['0'].departCode} <b><fmt:formatDate value="${showItem.value['0'].departTime}" pattern="HH:mm" timeZone="${deZone}"/> </b></div>
            <div class="minorInfo"><fmt:formatDate value="${showItem.value['0'].departTime}" pattern="EEEEEE"/></div>
          </td>
          <td>
            <div class="floatTitle">${showItem.value['0'].arriveCode} <b><fmt:formatDate value="${showItem.value['0'].arriveTime}" pattern="HH:mm" timeZone="${midZone}"/></b></div>
            <div class="minorInfo"><fmt:formatDate value="${showItem.value['0'].arriveTime}" pattern="EEEEEE"/></div>
          </td>
          <td class="minorInfo"><fmt:parseNumber integerOnly="true" value="${showItem.value['0'].flightTime/60}"/>h <fmt:formatNumber value="${showItem.value['0'].flightTime%60}" pattern="#" type="number"/>m </td>
        </tr>
        <tr>
          <c:set value="${(showItem.value['1'].departTime.time-showItem.value['0'].arriveTime.time)/(1000*60)}" var="transTime"/>
          <td colspan="5"><div class="subTitle">Connection : ${airNames[showItem.value['0'].arriveCode]}(${showItem.value['0'].arriveCode}) for <fmt:parseNumber integerOnly="true" value="${transTime/60}"/>h <fmt:formatNumber value="${transTime%60}" pattern="#" type="number"/>m</div></td>
        </tr>
        <tr>
          <td>
            <div class="floatTitle">Flight <span class="flightNum">${showItem.value['1'].number}</span></div>
          </td>
          <td>
            <div class="floatTitle">${showItem.value['1'].departCode} <b><fmt:formatDate value="${showItem.value['1'].departTime}" pattern="HH:mm" timeZone="${midZone}"/> </b></div>
            <div class="minorInfo"><fmt:formatDate value="${showItem.value['1'].departTime}" pattern="EEEEEE"/></div>
          </td>
          <td>
            <div class="floatTitle">${showItem.value['1'].arriveCode} <b><fmt:formatDate value="${showItem.value['1'].arriveTime}" pattern="HH:mm" timeZone="${arZone}"/> </b></div>
            <div class="minorInfo"><fmt:formatDate value="${showItem.value['1'].arriveTime}" pattern="EEEEEE"/></div>
          </td>
          <td class="minorInfo"><fmt:parseNumber integerOnly="true" value="${showItem.value['1'].flightTime/60}"/>h <fmt:formatNumber value="${showItem.value['1'].flightTime%60}" pattern="#" type="number"/>m</td>
        </tr>
        <tr>
          <td colspan="5"><div class="subTitle">Arrive : ${airNames[showItem.value['1'].arriveCode]}(${showItem.value['1'].arriveCode})</div></td>
        </tr>
      </table>
    </div>
    <%--返回航程--%>
    <div class="departDetail">
    </div>
  </div>
</div>
