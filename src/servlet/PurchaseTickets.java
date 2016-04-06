package servlet;

import beans.ReserveInfo;
import beans.SortingBean;
import beans.Trip;
import util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by pianobean on 4/13/15.
 */
@WebServlet(name = "PurchaseTickets", urlPatterns = "/purchase")
public class PurchaseTickets extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String seatType = (String) session.getAttribute("seat");
        Trip trip = (Trip) session.getAttribute("tripInfo");
        int passengerNum = trip.getPassenger();
        Map airNames = (Map) session.getAttribute("airNames");
        String type = (String) session.getAttribute("type");

        Map.Entry<SortingBean, Object> entry = (Map.Entry) session.getAttribute("checkout");

        String fname = request.getParameter("firstname");
        String lname = request.getParameter("lastname");
        String email = request.getParameter("email");
        String flightNums = request.getParameter("flightNum");
        String[] numbers = flightNums.split(",");
        String time = request.getParameter("time");
//        System.out.println(time);
        String[] timePair = time.split(",");

        List<ReserveInfo> list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("Dear "+fname+" "+lname+",\nThank you for purchasing tickets at Kayak.\n");
        sb.append("Flight Detail: \n");
        sb.append("From "+airNames.get(trip.getDepartCode())+" to "+airNames.get(trip.getArriveCode())+"\n");

        if(type.equals("oneWay")){
            sb.append("Depart time: "+DateFormater.formatDate(trip.getGoDate(), trip.getDepartCode())+"\n");
        }else {
            sb.append("Depart time: "+DateFormater.formatDate(trip.getGoDate(),trip.getDepartCode())+
            " ; Return time: "+DateFormater.formatDate(trip.getBackDate(),trip.getArriveCode())+"\n");
        }

        for(int x=0; x<numbers.length; x++){
            sb.append("Flight "+numbers[x]+": "+timePair[x].split("-")[0]+"---"+timePair[x].split("-")[1]+"\n");
        }


        if(seatType.equals("Coach")) {
            for (String s : numbers) {
                ReserveInfo info = new ReserveInfo(s, "Coach");
                list.add(info);
            }
        }else {
            sb.append("Passenger number: "+trip.getPassenger()+"\n");
            DecimalFormat df = new DecimalFormat("#.00");
            sb.append("Total price: " + df.format(trip.getPassenger()* entry.getKey().getTotalPrice()));
            for (String s : numbers) {
                ReserveInfo info = new ReserveInfo(s, "FirstClass");
                list.add(info);
            }
        }

        String query = ReserveParams.reservationCreator(list);
//        System.out.println(query);
        String param = QueryFactory.reserve(query);
        for(int i=0; i<passengerNum; i++) {
            PostConnection.DBOperate(param);
        }
        JavaMail.send(email, "kayak conformation",sb.toString());
        PostConnection.DBOperate(QueryFactory.unlock());
        session.invalidate();
        request.getRequestDispatcher("/thankyou.html").forward(request,response);
//        System.out.println("params:"+seatType);
    }
}
