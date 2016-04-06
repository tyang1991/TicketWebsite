<%--单元格格式--%>
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
        <td>${showItem.value['0']['0'].departCode} <b><fmt:formatDate value="${showItem.value['0']['0'].departTime}" pattern="HH:mm" timeZone="${deZone}"/></b></td>
        <td>${showItem.value['1']['0'].departCode} <b><fmt:formatDate value="${showItem.value['0']['1'].arriveTime}" pattern="HH:mm" timeZone="${arZone}"/></b></td>
        <c:set value="${(showItem.value['0']['1'].departTime.time-showItem.value['0']['0'].arriveTime.time)/(1000*60)+showItem.value['0']['0'].flightTime + showItem.value['0']['1'].flightTime}" var="totalMinGo"/>
        <td class="minorInfo"><fmt:parseNumber integerOnly="true" value="${totalMinGo/60}"/>h <fmt:formatNumber value="${totalMinGo%60}" pattern="#" type="number"/>m </td>
        <td class="minorInfo">1 stop(${showItem.value['0']['1'].departCode})</td>
      </tr>
      <tr>
        <td>${showItem.value['0']['1'].arriveCode} <b><fmt:formatDate value="${showItem.value['1']['0'].departTime}" pattern="HH:mm" timeZone="${arZone}"/></b></td>
        <td>${showItem.value['1']['1'].arriveCode} <b><fmt:formatDate value="${showItem.value['1']['1'].arriveTime}" pattern="HH:mm" timeZone="${deZone}"/></b></td>
        <c:set value="${(showItem.value['1']['1'].departTime.time-showItem.value['1']['0'].arriveTime.time)/(1000*60)+showItem.value['1']['0'].flightTime + showItem.value['1']['1'].flightTime}" var="totalMinCome"/>
        <td class="minorInfo"><fmt:parseNumber integerOnly="true" value="${totalMinCome/60}"/>h <fmt:formatNumber value="${totalMinCome%60}" pattern="#" type="number"/>m </td>
        <td class="minorInfo">1 stop(${showItem.value['1']['1'].departCode})</td>
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
          <td colspan="5"><div class="subTitle">Depart: ${airNames[showItem.value['0']['0'].departCode]}(${showItem.value['0']['0'].departCode})</div></td>
        </tr>
        <tr>
          <td>
            <div class="floatTitle">Flight <span class="flightNum">${showItem.value['0']['0'].number}</span></div>
          </td>
          <td>
            <div class="floatTitle">${showItem.value['0']['0'].departCode} <b><fmt:formatDate value="${showItem.value['0']['0'].departTime}" pattern="HH:mm" timeZone="${deZone}"/> </b></div>
            <div class="minorInfo"><fmt:formatDate value="${showItem.value['0']['0'].departTime}" pattern="EEEEEE"/></div>
          </td>
          <td>
            <c:set value="${my:formater(showItem.value['0']['0'].arriveCode)}" var="midZone1"/>
            <div class="floatTitle">${showItem.value['0']['0'].arriveCode} <b><fmt:formatDate value="${showItem.value['0']['0'].arriveTime}" pattern="HH:mm" timeZone="${midZone1}"/></b></div>
            <div class="minorInfo"><fmt:formatDate value="${showItem.value['0']['0'].arriveTime}" pattern="EEEEEE"/></div>
          </td>
          <td class="minorInfo"><fmt:parseNumber integerOnly="true" value="${showItem.value['0']['0'].flightTime/60}"/>h <fmt:formatNumber value="${showItem.value['0']['0'].flightTime%60}" pattern="#" type="number"/>m </td>
        </tr>
        <tr>
          <c:set value="${(showItem.value['0']['1'].departTime.time-showItem.value['0']['0'].arriveTime.time)/(1000*60)}" var="transTime"/>
          <td colspan="5"><div class="subTitle">Connection : ${airNames[showItem.value['0']['0'].arriveCode]}(${showItem.value['0']['0'].arriveCode}) for <fmt:parseNumber integerOnly="true" value="${transTime/60}"/>h <fmt:formatNumber value="${transTime%60}" pattern="#" type="number"/>m</div></td>
        </tr>
        <tr>
          <td>
            <div class="floatTitle">Flight <span class="flightNum">${showItem.value['0']['1'].number}</span></div>
          </td>
          <td>
            <div class="floatTitle">${showItem.value['0']['1'].departCode} <b><fmt:formatDate value="${showItem.value['0']['1'].departTime}" pattern="HH:mm" timeZone="${midZone1}"/> </b></div>
            <div class="minorInfo"><fmt:formatDate value="${showItem.value['0']['1'].departTime}" pattern="EEEEEE"/></div>
          </td>
          <td>
            <div class="floatTitle">${showItem.value['0']['1'].arriveCode} <b><fmt:formatDate value="${showItem.value['0']['1'].arriveTime}" pattern="HH:mm" timeZone="${arZone}"/> </b></div>
            <div class="minorInfo"><fmt:formatDate value="${showItem.value['0']['1'].arriveTime}" pattern="EEEEEE"/></div>
          </td>
          <td class="minorInfo"><fmt:parseNumber integerOnly="true" value="${showItem.value['0']['1'].flightTime/60}"/>h <fmt:formatNumber value="${showItem.value['0']['1'].flightTime%60}" pattern="#" type="number"/>m</td>
        </tr>
        <tr>
          <td colspan="5"><div class="subTitle">Arrive : ${airNames[showItem.value['0']['1'].arriveCode]}(${showItem.value['0']['1'].arriveCode})</div></td>
        </tr>
      </table>
    </div>
    <%--返回航程--%>
    <div class="departDetail">
      <hr>
      <table>
        <tr>
          <td colspan="5"><div class="subTitle">Depart: ${airNames[showItem.value['1']['0'].departCode]}(${showItem.value['1']['0'].departCode})</div></td>
        </tr>
        <tr>
          <td>
            <div class="floatTitle">Flight <span class="flightNum">${showItem.value['1']['0'].number}</span></div>
          </td>
          <td>
            <div class="floatTitle">${showItem.value['1']['0'].departCode} <b><fmt:formatDate value="${showItem.value['1']['0'].departTime}" pattern="HH:mm" timeZone="${arZone}"/> </b></div>
            <div class="minorInfo"><fmt:formatDate value="${showItem.value['1']['0'].departTime}" pattern="EEEEEE"/></div>
          </td>
          <td>
            <c:set value="${my:formater(showItem.value['1']['0'].arriveCode)}" var="midZone2"/>
            <div class="floatTitle">${showItem.value['1']['0'].arriveCode} <b><fmt:formatDate value="${showItem.value['1']['0'].arriveTime}" pattern="HH:mm" timeZone="${midZone2}"/></b></div>
            <div class="minorInfo"><fmt:formatDate value="${showItem.value['1']['0'].arriveTime}" pattern="EEEEEE"/></div>
          </td>
          <td class="minorInfo"><fmt:parseNumber integerOnly="true" value="${showItem.value['1']['0'].flightTime/60}"/>h <fmt:formatNumber value="${showItem.value['1']['0'].flightTime%60}" pattern="#" type="number"/>m </td>
        </tr>
        <tr>
          <c:set value="${(showItem.value['1']['1'].departTime.time-showItem.value['1']['0'].arriveTime.time)/(1000*60)}" var="transTime"/>
          <td colspan="5"><div class="subTitle">Connection : ${airNames[showItem.value['1']['0'].arriveCode]}(${showItem.value['1']['0'].arriveCode}) for <fmt:parseNumber integerOnly="true" value="${transTime/60}"/>h <fmt:formatNumber value="${transTime%60}" pattern="#" type="number"/>m</div></td>
        </tr>
        <tr>
          <td>
            <div class="floatTitle">Flight <span class="flightNum">${showItem.value['1']['1'].number}</span></div>
          </td>
          <td>
            <div class="floatTitle">${showItem.value['1']['1'].departCode} <b><fmt:formatDate value="${showItem.value['1']['1'].departTime}" pattern="HH:mm" timeZone="${midZone2}"/> </b></div>
            <div class="minorInfo"><fmt:formatDate value="${showItem.value['1']['1'].departTime}" pattern="EEEEEE"/></div>
          </td>
          <td>
            <div class="floatTitle">${showItem.value['1']['1'].arriveCode} <b><fmt:formatDate value="${showItem.value['1']['1'].arriveTime}" pattern="HH:mm" timeZone="${deZone}"/> </b></div>
            <div class="minorInfo"><fmt:formatDate value="${showItem.value['1']['1'].arriveTime}" pattern="EEEEEE"/></div>
          </td>
          <td class="minorInfo"><fmt:parseNumber integerOnly="true" value="${showItem.value['1']['1'].flightTime/60}"/>h <fmt:formatNumber value="${showItem.value['1']['1'].flightTime%60}" pattern="#" type="number"/>m</td>
        </tr>
        <tr>
          <td colspan="5"><div class="subTitle">Arrive : ${airNames[showItem.value['1']['1'].arriveCode]}(${showItem.value['1']['1'].arriveCode})</div></td>
        </tr>
      </table>
    </div>
  </div>
</div>
