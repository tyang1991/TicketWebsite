package servlet;

import beans.PageBean;
import beans.SortingBean;
import beans.Trip;
import util.ConstantVariable;
import util.DateFormater;
import util.Pagination;
import util.TimeZoneCreater;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by pianobean on 4/28/15.
 */
@WebServlet(name = "TimeWindow", urlPatterns="/timeWindow")
public class TimeWindow extends HttpServlet {
    public static int count = 0;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        //trip type
        String tripType = (String) session.getAttribute("type");
        Trip trip = (Trip) session.getAttribute("tripInfo");
        String deCode = trip.getDepartCode();
        String arCode = trip.getArriveCode();

//        System.out.println(deCode +"+++"+arCode);
        List finalList = null;
        //all results
        if(count==0) {
            finalList = (List) session.getAttribute("finalList");
            session.setAttribute("searchList", finalList);
            count++;
        }else {
            finalList = (List) session.getAttribute("searchList");
        }
        String param = request.getParameter("param");

        //如果不是ajax请求
        if(param!=null){
            System.out.println("param="+param);
//            System.out.println("final List="+finalList);
            String[] timeParam = param.split("\\|");

            String inputGoDepart1 = timeParam[0];
            String inputGoDepart2 = timeParam[1];
            String inputGoArrive1 = timeParam[2];
            String inputGoArrive2 = timeParam[3];

            //显示时间
            session.setAttribute("outputDe1", inputGoDepart1);
            session.setAttribute("outputDe2", inputGoDepart2);
            session.setAttribute("outputAr1", inputGoArrive1);
            session.setAttribute("outputAr2", inputGoArrive2);

            DateFormater formater = new DateFormater();
            //js拖动球位置
            session.setAttribute("outputGoDepart1", formater.outputTime(inputGoDepart1));
            session.setAttribute("outputGoDepart2", formater.outputTime(inputGoDepart2));
            session.setAttribute("outputGoArrive1", formater.outputTime(inputGoArrive1));
            session.setAttribute("outputGoArrive2", formater.outputTime(inputGoArrive2));


//            System.out.println("inputGoDepart1:="+inputGoDepart1);
//            System.out.println("inputGoDepart2:="+inputGoDepart2);
//            System.out.println("inputGoArrive1:="+inputGoArrive1);
//            System.out.println("inputGoArrive2:="+inputGoArrive2);

            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");

            ArrayList newFinal = new ArrayList();
            //如果是roundTrip
            if(tripType.equals("oneWay")){
                //遍历finalList
                Iterator it = finalList.iterator();
                while (it.hasNext()){
                    Map.Entry entry = (Map.Entry) it.next();
//                    System.out.println("entry="+entry);
                    SortingBean sortBean = (SortingBean) entry.getKey();
                    Map<String, Date> timeMap = sortBean.getTimeCriteria();
//                    System.out.println(timeMap);
                    Date goDepartTime = timeMap.get(ConstantVariable.GODEPART);
//                    System.out.println("original time:="+goDepartTime);
                    Date goArriveTime = timeMap.get(ConstantVariable.GOARRIVE);
//                    System.out.println("original time:="+goArriveTime);
                    sdf.setTimeZone(TimeZone.getTimeZone(TimeZoneCreater.findTimeZoneId(deCode)));
                    String compareGoDe = sdf.format(goDepartTime);
                    sdf.setTimeZone(TimeZone.getTimeZone(TimeZoneCreater.findTimeZoneId(arCode)));
                    String compareGoAr = sdf.format(goArriveTime);
//                    System.out.println("flight time:="+compareGoDe+"---"+compareGoAr);
                    //在约束条件内
                    if(formater.timeCompare(compareGoDe, inputGoDepart1)>=0 && formater.timeCompare(compareGoDe, inputGoDepart2)<=0
                            && formater.timeCompare(compareGoAr, inputGoArrive1)>=0 && formater.timeCompare(compareGoAr, inputGoArrive2)<=0){
                        newFinal.add(entry);
                    }
                }

            }else {
                String inputComeDepart1 = timeParam[4];
                String inputComeDepart2 = timeParam[5];
                String inputComeArrive1 = timeParam[6];
                String inputComeArrive2 = timeParam[7];

//                System.out.println("inputComeDepart1:="+inputComeDepart1);
//                System.out.println("inputComeDepart2:="+inputComeDepart2);
//                System.out.println("inputComeArrive1:="+inputComeArrive1);
//                System.out.println("inputComeArrive2:="+inputComeArrive2);

                session.setAttribute("outputComeDe1", inputComeDepart1);
                session.setAttribute("outputComeDe2", inputComeDepart2);
                session.setAttribute("outputComeAr1", inputComeArrive1);
                session.setAttribute("outputComeAr2", inputComeArrive2);

                session.setAttribute("outputComeDepart1", formater.outputTime(inputComeDepart1));
                session.setAttribute("outputComeDepart2", formater.outputTime(inputComeDepart2));
                session.setAttribute("outputComeArrive1", formater.outputTime(inputComeArrive1));
                session.setAttribute("outputComeArrive2", formater.outputTime(inputComeArrive2));

                Iterator it = finalList.iterator();
                while (it.hasNext()){
                    Map.Entry entry = (Map.Entry) it.next();
//                    System.out.println("entry="+entry);
                    SortingBean sortBean = (SortingBean) entry.getKey();
                    Map<String, Date> timeMap = sortBean.getTimeCriteria();
//                    System.out.println(timeMap);
                    Date goDepartTime = timeMap.get(ConstantVariable.GODEPART);
//                    System.out.println("original time:="+goDepartTime);
                    Date goArriveTime = timeMap.get(ConstantVariable.GOARRIVE);
//                    System.out.println("original time:="+goArriveTime);
                    Date comeDepartTime = timeMap.get(ConstantVariable.COMEDEPART);
//                    System.out.println("comdepart:="+comeDepartTime);
                    Date comeArriveTime = timeMap.get(ConstantVariable.COMEARRIVE);
//                    System.out.println("comarrive:="+comeArriveTime);
                    sdf.setTimeZone(TimeZone.getTimeZone(TimeZoneCreater.findTimeZoneId(deCode)));
                    String compareGoDe = sdf.format(goDepartTime);
                    String compareComeAr = sdf.format(comeArriveTime);
//                    System.out.println(compareGoDe+"|||||"+compareComeAr);
                    sdf.setTimeZone(TimeZone.getTimeZone(TimeZoneCreater.findTimeZoneId(arCode)));
                    String compareGoAr = sdf.format(goArriveTime);
                    String compareComeDe = sdf.format(comeDepartTime);
//                    System.out.println(compareGoAr+"|||||"+compareComeDe);
//                    System.out.println("flight time:="+compareGoDe+"---"+compareGoAr);
                    //在约束条件内
                    if(formater.timeCompare(compareGoDe, inputGoDepart1)>=0 && formater.timeCompare(compareGoDe, inputGoDepart2)<=0
                            && formater.timeCompare(compareGoAr, inputGoArrive1)>=0 && formater.timeCompare(compareGoAr, inputGoArrive2)<=0
                            && formater.timeCompare(compareComeDe, inputComeDepart1)>=0 && formater.timeCompare(compareComeDe, inputComeDepart2)<=0
                            && formater.timeCompare(compareComeAr, inputComeArrive1)>=0 && formater.timeCompare(compareComeAr, inputComeArrive2)<=0){
                        newFinal.add(entry);
                    }
                }

            }
//            System.out.println(newFinal);
            session.setAttribute("finalList", newFinal);
            List showInfo = Pagination.pageResult(newFinal, 1);
            PageBean pageBean = new PageBean();
            pageBean.setCurrentPage(1);
            pageBean.setTotalRecord(newFinal.size());
            session.setAttribute("pagebean", pageBean);
            session.setAttribute("showInfo", showInfo);
            session.setAttribute("selected", null);
            request.getRequestDispatcher("/searchResult.jsp").forward(request,response);
            return;
        }


        if(tripType.equals("oneWay")) {
            String outputGoDepart1 = session.getAttribute("outputGoDepart1") != null ? (String) session.getAttribute("outputGoDepart1") : "0";
            String outputGoArrive1 = session.getAttribute("outputGoArrive1") != null ? (String) session.getAttribute("outputGoArrive1") : "0";
            String outputGoDepart2 = session.getAttribute("outputGoDepart2") != null ? (String) session.getAttribute("outputGoDepart2") : "1440";
            String outputGoArrive2 = session.getAttribute("outputGoArrive2") != null ? (String) session.getAttribute("outputGoArrive2") : "1440";
            response.getWriter().println(outputGoDepart1 + "|" + outputGoDepart2 + "|" + outputGoArrive1 + "|" + outputGoArrive2);
        }else {
            String outputGoDepart1 = session.getAttribute("outputGoDepart1") != null ? (String) session.getAttribute("outputGoDepart1") : "0";
            String outputGoArrive1 = session.getAttribute("outputGoArrive1") != null ? (String) session.getAttribute("outputGoArrive1") : "0";
            String outputGoDepart2 = session.getAttribute("outputGoDepart2") != null ? (String) session.getAttribute("outputGoDepart2") : "1440";
            String outputGoArrive2 = session.getAttribute("outputGoArrive2") != null ? (String) session.getAttribute("outputGoArrive2") : "1440";

            String outputComeDepart1 = session.getAttribute("outputComeDepart1") !=null? (String)session.getAttribute("outputComeDepart1"): "0";
            String outputComeArrive1 = session.getAttribute("outputComeArrive1") !=null? (String)session.getAttribute("outputComeArrive1"): "0";
            String outputComeDepart2 = session.getAttribute("outputComeDepart2") !=null? (String)session.getAttribute("outputComeDepart2"): "1440";
            String outputComeArrive2 = session.getAttribute("outputComeArrive2") !=null? (String)session.getAttribute("outputComeArrive2"): "1440";

            response.getWriter().println(outputGoDepart1 + "|" + outputGoDepart2 + "|" + outputGoArrive1 + "|" + outputGoArrive2+"|"+outputComeDepart1
            +"|"+outputComeDepart2+"|"+outputComeArrive1+"|"+outputComeArrive2);
        }
    }
}
