package servlet;

import beans.Trip;
import factory.ServiceFactory;
import org.dom4j.Document;
import service.AirportService;
import service.NonStopService;
import service.OneStopService;
import service.PairFlights;
import util.AirportNames;
import util.ConstantVariable;
import util.DateFormater;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author  pianobean on 3/2/15.
 */
@WebServlet(name = "SearchFlight")
public class SearchFlight extends HttpServlet {
    private AirportService service = ServiceFactory.getInstance().getAirportService();
    private OneStopService oneStop = ServiceFactory.getInstance().getOneStopService();
    private NonStopService nonStop = ServiceFactory.getInstance().getNonStopService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
       //先将保存机场名字的map存入session
        Map airportNames = AirportNames.airportNames;
        session.setAttribute("airNames",airportNames);

        //获取提交参数
        String deCode = request.getParameter("deCode");
        String departCode = deCode.substring(deCode.indexOf("(") + 1, deCode.length() - 1);
        String arCode = request.getParameter("arCode");
        String arriveCode = arCode.substring(arCode.indexOf("(")+1,arCode.length()-1);

//        System.out.println(departCode);
//        System.out.println(arriveCode);
        //判断机场名称是否有效
        if(!airportNames.keySet().contains(departCode) || !airportNames.keySet().contains(arriveCode)){
            request.setAttribute("erro", "Please type valid airport name");
            request.getRequestDispatcher("/").forward(request,response);
            return;
        }

        String type = request.getParameter("type");
        String deDate = request.getParameter("deDate");
        String arDate = request.getParameter("arDate");
        String seat = request.getParameter("seat");
        int passenger = Integer.parseInt(request.getParameter("passenger"));

        //将座位信息告知下一层
        session.setAttribute("seat", seat);

        //告知下一层是单程还是往返
        session.setAttribute("type",type);
        System.out.println(type);
        //分页信息
//        String pageNumber = request.getParameter("pageNumber");
//        if(pageNumber!=null){
//            request.setAttribute("pageNumber", pageNumber);
//        }

       //获取出发时间
        Date depart = DateFormater.format(deDate);

        //将航程信息封装到Trip对象中
        Trip trip = new Trip();
        trip.setDepartCode(departCode);
        trip.setArriveCode(arriveCode);
        trip.setGoDate(depart);
        trip.setSeat(seat);
        trip.setPassenger(passenger);

        //获取出发机场dom文件
        Document deDom =service.getDepartDom(departCode, depart);
        //获取当天到达机场dom文件
        Document arDom =service.getArriveDom(arriveCode, depart);

        //获取第二天到达机场的dom文件
        Calendar cal = Calendar.getInstance();
        cal.setTime(depart);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        Date nextDay = cal.getTime();
        Document nextDom = service.getArriveDom(arriveCode, nextDay);



        //获取一次转机的匹配集合
        Map flightsOne = oneStop.validOneStop1(seat.equals("First") ? ConstantVariable.FIRST : ConstantVariable.COACH, passenger, deDom, arDom, nextDom);
        //获取直达航班
        Map flightsNon = nonStop.findNonStopFlights1(seat.equals("First") ? ConstantVariable.FIRST : ConstantVariable.COACH, passenger, arriveCode, deDom);
        /*
            返程航班
         */
        if(arDate!=null){
            //获取返航时间
            Date returnDepart = DateFormater.format(arDate);

            //将返航日期封装入Trip
            trip.setBackDate(returnDepart);

            //获取出发机场dom文件
            Document returnDeDom = service.getDepartDom(arriveCode, returnDepart);
            //获取当天到达机场dom文件
            Document returnArDom = service.getArriveDom(departCode,returnDepart);
            //获取第二天到达机场的dom文件
            cal.setTime(returnDepart);
            cal.add(Calendar.DAY_OF_MONTH, 1);
            Date returnNext = cal.getTime();
            Document returnNextDom = service.getArriveDom(departCode, returnNext);

            Map reFlightsOne = oneStop.validOneStop1(seat.equals("First") ? ConstantVariable.FIRST : ConstantVariable.COACH, passenger, returnDeDom, returnArDom, returnNextDom);
            Map reFlightsNon = nonStop.findNonStopFlights1(seat.equals("First") ? ConstantVariable.FIRST : ConstantVariable.COACH, passenger, departCode, returnDeDom);

            //将出发航班与返回航班进行匹配
            PairFlights pairUp = ServiceFactory.getInstance().getPairFlights();
            Map pairOne = pairUp.pairOneStop1(flightsOne, reFlightsOne);
//            System.out.println(pairOne);
            Map pairNon = pairUp.pairNonStop1(flightsNon, reFlightsNon);

            Map pairMix1= pairUp.pairMix1(flightsNon, reFlightsOne);
            Map pairMix2 = pairUp.pairMix2(flightsOne,reFlightsNon);
//            System.out.println(pairNon);
            //将搜索结果传给下一层
            session.setAttribute("pairOne",pairOne);
            session.setAttribute("pairNon",pairNon);
            session.setAttribute("pairMix1", pairMix1);
            session.setAttribute("pairMix2", pairMix2);
        }else {
            //将搜索结果传给下一层
            session.setAttribute("goOneStop",flightsOne);
            session.setAttribute("goNonStop",flightsNon);
        }
        //将trip存入session
        session.setAttribute("tripInfo", trip);

        String[] flightType = {"NON","ONE"};
        session.setAttribute("showType",flightType);
        request.getRequestDispatcher("/page").forward(request,response);
    }

}
