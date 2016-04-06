<%--
  Created by IntelliJ IDEA.
  User: pianobean
  Date: 4/12/15
  Time: 2:24 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="my" uri="/my" %>
<html>
<head>
    <title>checkout</title>
  <%--<link type="text/css" href="/test/css/result.css" rel="stylesheet">--%>
  <link type="text/css" href="/test/css/checkout.css" rel="stylesheet">
  <script type="text/javascript" src="/test/js/jquery-2.1.3.min.js"></script>
  <script type="text/javascript" src="/test/js/purchase.js"></script>
  <style type="text/css">
    .invalideInput{
      border: 3px solid red;
    }
  </style>
</head>
<body>
  <div id="frame">
    <!--机票信息-->
    <div id="tripDetail">
      <%--机票概括--%>
      <div id="ticketTitle">
        <div class="userTile"><b>${airNames[tripInfo.departCode]}(${tripInfo.departCode}) to ${airNames[tripInfo.arriveCode]}(${tripInfo.arriveCode})</b></div>
        <fmt:setLocale value="en_US"/>
        <c:choose>
          <c:when test="${type=='oneWay'}">
            <div class="subinfo"><b><fmt:formatDate value="${tripInfo.goDate}" pattern="EEE, MMM dd"/></b> </div>
          </c:when>
          <c:otherwise>
            <div class="subinfo"><b><fmt:formatDate value="${tripInfo.goDate}" pattern="EEE, MMM dd"/> - <fmt:formatDate value="${tripInfo.backDate}" pattern="EEE, MMM dd"/></b></div>
          </c:otherwise>
        </c:choose>

        <div class="subinfo">Per person: <fmt:formatNumber type="currency" value="${checkout.key.totalPrice}"/> , ${tripInfo.passenger} traveler</div>
        <div class="subinfo">Total Cost: <b><fmt:formatNumber type="currency" value="${checkout.key.totalPrice * tripInfo.passenger}"/> </b></div>
      </div>
       <%--机票细节 --%>
        <%--${checkout}--%>
        <fmt:setLocale value="en_US"/>
        <c:choose>
          <c:when test="${type=='oneWay'}">
            <c:set var="showItem" value="${checkout}" scope="request"/>
              <c:choose>
                <%--单程航班直飞--%>
                <c:when test="${showItem.value.getClass().name=='beans.Flight'}">
                  <%@include file="/WEB-INF/jsp/paySingleNon.jsp"%>
                </c:when>
                <%--单程航班并且有转机--%>
                <c:otherwise>
                  <%@include file="/WEB-INF/jsp/paySingleOne.jsp"%>
                </c:otherwise>
              </c:choose>
          </c:when>

          <c:otherwise>
            <c:set var="showItem" value="${checkout}" scope="session"/>
              <c:choose>
                <%--往返航班直飞--%>
                <c:when test="${showItem.value['0'].getClass().name=='beans.Flight' && showItem.value['1'].getClass().name=='beans.Flight'}">
                  <%@include file="/WEB-INF/jsp/payRoundNon.jsp"%>
                </c:when>


                <%--出发单程,返回转机--%>
                <c:when test="${showItem.value['0'].getClass().name=='beans.Flight' && showItem.value['1'].getClass().name!='beans.Flight'}">
                  <div id="transferDetail">
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
                            <div class="floatTitle">${showItem.value['0'].departCode} <b class="departTime"><fmt:formatDate value="${showItem.value['0'].departTime}" pattern="HH:mm" timeZone="${deZone}"/> </b></div>
                            <div class="minorInfo"><fmt:formatDate value="${showItem.value['0'].departTime}" pattern="EEEEEE"/></div>
                          </td>
                          <td>
                            <div class="floatTitle">${showItem.value['0'].arriveCode} <b class="arriveTime"><fmt:formatDate value="${showItem.value['0'].arriveTime}" pattern="HH:mm" timeZone="${arZone}"/></b></div>
                            <div class="minorInfo"><fmt:formatDate value="${showItem.value['0'].arriveTime}" pattern="EEEEEE"/></div>
                          </td>
                          <td class="minorInfo"><fmt:parseNumber integerOnly="true" value="${showItem.value['0'].flightTime/60}"/>h <fmt:formatNumber value="${showItem.value['0'].flightTime%60}" pattern="#" type="number"/>m </td>
                        </tr>
                        <tr>
                          <td colspan="5"><div class="subTitle">Arrive : ${airNames[showItem.value['0'].arriveCode]}(${showItem.value['0'].arriveCode})</div></td>
                        </tr>
                      </table>
                    </div>
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
                            <div class="floatTitle">${showItem.value['1']['0'].departCode} <b class="departTime"><fmt:formatDate value="${showItem.value['1']['0'].departTime}" pattern="HH:mm" timeZone="${arZone}"/> </b></div>
                            <div class="minorInfo"><fmt:formatDate value="${showItem.value['1']['0'].departTime}" pattern="EEEEEE"/></div>
                          </td>
                          <td>
                            <c:set value="${my:formater(showItem.value['1']['0'].arriveCode)}" var="midZone2"/>
                            <div class="floatTitle">${showItem.value['1']['0'].arriveCode} <b class="arriveTime"><fmt:formatDate value="${showItem.value['1']['0'].arriveTime}" pattern="HH:mm" timeZone="${midZone2}"/></b></div>
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
                            <div class="floatTitle">${showItem.value['1']['1'].departCode} <b class="departTime"><fmt:formatDate value="${showItem.value['1']['1'].departTime}" pattern="HH:mm" timeZone="${midZone2}"/> </b></div>
                            <div class="minorInfo"><fmt:formatDate value="${showItem.value['1']['1'].departTime}" pattern="EEEEEE"/></div>
                          </td>
                          <td>
                            <div class="floatTitle">${showItem.value['1']['1'].arriveCode} <b class="arriveTime"><fmt:formatDate value="${showItem.value['1']['1'].arriveTime}" pattern="HH:mm" timeZone="${deZone}"/> </b></div>
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
                </c:when>
                <%--出发转机,返回单程--%>
                <c:when test="${showItem.value['0'].getClass().name!='beans.Flight' && showItem.value['1'].getClass().name=='beans.Flight'}">
                  <div id="transferDetail">
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
                            <div class="floatTitle">${showItem.value['0']['0'].departCode} <b class="departTime"><fmt:formatDate value="${showItem.value['0']['0'].departTime}" pattern="HH:mm" timeZone="${deZone}"/> </b></div>
                            <div class="minorInfo"><fmt:formatDate value="${showItem.value['0']['0'].departTime}" pattern="EEEEEE"/></div>
                          </td>
                          <td>
                            <c:set value="${my:formater(showItem.value['0']['0'].arriveCode)}" var="midZone1"/>
                            <div class="floatTitle">${showItem.value['0']['0'].arriveCode} <b class="arriveTime"><fmt:formatDate value="${showItem.value['0']['0'].arriveTime}" pattern="HH:mm" timeZone="${midZone1}"/></b></div>
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
                            <div class="floatTitle">${showItem.value['0']['1'].departCode} <b class="departTime"><fmt:formatDate value="${showItem.value['0']['1'].departTime}" pattern="HH:mm" timeZone="${midZone1}"/> </b></div>
                            <div class="minorInfo"><fmt:formatDate value="${showItem.value['0']['1'].departTime}" pattern="EEEEEE"/></div>
                          </td>
                          <td>
                            <div class="floatTitle">${showItem.value['0']['1'].arriveCode} <b class="arriveTime"><fmt:formatDate value="${showItem.value['0']['1'].arriveTime}" pattern="HH:mm" timeZone="${arZone}"/> </b></div>
                            <div class="minorInfo"><fmt:formatDate value="${showItem.value['0']['1'].arriveTime}" pattern="EEEEEE"/></div>
                          </td>
                          <td class="minorInfo"><fmt:parseNumber integerOnly="true" value="${showItem.value['0']['1'].flightTime/60}"/>h <fmt:formatNumber value="${showItem.value['0']['1'].flightTime%60}" pattern="#" type="number"/>m</td>
                        </tr>
                        <tr>
                          <td colspan="5"><div class="subTitle">Arrive : ${airNames[showItem.value['0']['1'].arriveCode]}(${showItem.value['0']['1'].arriveCode})</div></td>
                        </tr>
                      </table>
                    </div>
                    <div class="departDetail">
                      <hr>
                      <table>
                        <tr>
                          <td colspan="5"><div class="subTitle">Depart :&nbsp;${airNames[showItem.value['1'].departCode]}(${showItem.value['1'].departCode})</div></td>
                        </tr>
                        <tr>
                          <td>
                            <div class="floatTitle">Flight <span class="flightNum">${showItem.value['1'].number}</span></div>
                          </td>
                          <td>
                            <div class="floatTitle">${showItem.value['1'].departCode} <b class="departTime"><fmt:formatDate value="${showItem.value['1'].departTime}" pattern="HH:mm" timeZone="${arZone}"/> </b></div>
                            <div class="minorInfo"><fmt:formatDate value="${showItem.value['1'].departTime}" pattern="EEEEEE"/></div>
                          </td>
                          <td>
                            <div class="floatTitle">${showItem.value['1'].arriveCode} <b class="arriveTime"><fmt:formatDate value="${showItem.value['1'].arriveTime}" pattern="HH:mm" timeZone="${deZone}"/></b></div>
                            <div class="minorInfo"><fmt:formatDate value="${showItem.value['1'].arriveTime}" pattern="EEEEEE"/></div>
                          </td>
                          <td class="minorInfo"><fmt:parseNumber integerOnly="true" value="${showItem.value['1'].flightTime/60}"/>h <fmt:formatNumber value="${showItem.value['1'].flightTime%60}" pattern="#" type="number"/>m </td>
                        </tr>
                        <tr>
                          <td colspan="5"><div class="subTitle">Arrive : ${airNames[showItem.value['1'].arriveCode]}(${showItem.value['1'].arriveCode})</div></td>
                        </tr>
                      </table>
                    </div>
                  </div>
                </c:when>


                <%--往返航班并且有转机--%>
                <c:otherwise>
                  <%@include file="/WEB-INF/jsp/payRoundOne.jsp"%>
                </c:otherwise>
              </c:choose>

          </c:otherwise>
        </c:choose>
    </div>

    <%--用户信息--%>
    <div id="userInfo">
        <div class="userTile"><b>User Infomation</b></div>
        <table id="userTable">
          <tr>
            <td class="text"><p class="textTile">First Name:</p></td>
            <td class="text"><p class="textTile">Last Name:</p></td>
            <td class="text"><p class="textTile">Email Address:</p></td>
          </tr>
          <tr>
            <td class="text"><input id="firstname" class="inputFrame" onblur="checkNull(this)" type="text"/></td>
            <td class="text"><input id="lastname" class="inputFrame" onblur="checkNull(this)"  type="text"/></td>
            <td class="text"><input id="email" class="inputFrame" onblur="checkEmail(this)"  type="text"/></td>
          </tr>
        </table>
        <button id="btnSubmit" onclick="buyTickets()">Buy Now</button>
    </div>
    <span id="message"></span>
  </div>
</body>
</html>
