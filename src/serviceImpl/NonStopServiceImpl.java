package serviceImpl;

import beans.Flight;

import beans.SortingBean;
import dao.AirportDao;
import factory.DaoFactory;
import factory.ServiceFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import service.NonStopService;
import service.OneStopService;
import service.PairFlights;
import util.ConstantVariable;
import util.PlaneSeat;
import util.QueryFactory;
import util.XmlConnection;

import java.util.*;

/**
 * @author pianobean on 3/12/15.
 */
public class NonStopServiceImpl implements NonStopService{
    public List<Flight> findNonStopFlights(String seatType, int numOfPassenger, String arCode, Document document){
        boolean flag = true;
        if (seatType== ConstantVariable.FIRST) flag=false;
        AirportDao info = DaoFactory.getInstance().getAirportFunctions();
        List<Flight> list = new ArrayList<Flight>();
        List choices = document.selectNodes("//Arrival/Code");
        if(flag){//经济舱
            for (Iterator iter = choices.iterator(); iter.hasNext(); ) {
                Element element = (Element) iter.next();
                String code = element.getText();
                if(code.equals(arCode)){
                    Element flightElement = element.getParent().getParent();
                    int coachLeft = Integer.parseInt(flightElement.element("Seating").element("Coach").getText());
                    //判断经济舱是否有足够的位置
                    if(coachLeft>=numOfPassenger){
                        String num = flightElement.attribute("Number").getValue();
                        Flight flight = info.findFlightByNumber(num, document);
                        list.add(flight);
                    }
                }
            }
        }else {//头等舱
            for (Iterator iter = choices.iterator(); iter.hasNext(); ) {
                Element element = (Element) iter.next();
                String code = element.getText();
                if(code.equals(arCode)){
                    Element flightElement = element.getParent().getParent();
                    int firstLeft = Integer.parseInt(flightElement.element("Seating").element("FirstClass").getText());
                    //判断经济舱是否有足够的位置
                    if(firstLeft>=numOfPassenger){
                        String num = flightElement.attribute("Number").getValue();
                        Flight flight = info.findFlightByNumber(num, document);
                        list.add(flight);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public Map<SortingBean, Flight> findNonStopFlights1(String seatType, int numOfPassenger, String arCode, Document document) {
        boolean flag = true;
        if (seatType== ConstantVariable.FIRST) flag=false;
        AirportDao info = DaoFactory.getInstance().getAirportFunctions();
        Map<SortingBean, Flight> map = new HashMap<SortingBean, Flight>();
        List choices = document.selectNodes("//Arrival/Code");
        if(flag){//经济舱
            for (Iterator iter = choices.iterator(); iter.hasNext(); ) {
                Element element = (Element) iter.next();
                String code = element.getText();
                if(code.equals(arCode)){
                    Element flightElement = element.getParent().getParent();
                    //获取飞机类型
                    String planeType = flightElement.attribute("Airplane").getValue();
                    //获取该类型飞机的作为总数
                    int totalSeat = PlaneSeat.getSeatNum(planeType,ConstantVariable.COACH);
                    //获取该飞机当前座位数
                    int currentSeat = Integer.parseInt(flightElement.element("Seating").element("Coach").getText());

                    int coachLeft = totalSeat-currentSeat;

                    //判断经济舱是否有足够的位置
                    if(coachLeft>=numOfPassenger){
                        String num = flightElement.attribute("Number").getValue();
                        Flight flight = info.findFlightByNumber(num, document);
                        int totalTimt =flight.getFlightTime();
                        double price = flight.getFirstPrice();
                        SortingBean sort = new SortingBean();
                        sort.setTotalPrice(price);
                        sort.setTotalTime(totalTimt);
                        Map timeCriteria = new HashMap<String, Date>();
                        timeCriteria.put(ConstantVariable.GODEPART, flight.getDepartTime());
                        timeCriteria.put(ConstantVariable.GOARRIVE, flight.getArriveTime());

//                        System.out.println("hereaaa:");

                        sort.setTimeCriteria(timeCriteria);
                        map.put(sort,flight);
                    }
                }
            }
        }else {//头等舱
            for (Iterator iter = choices.iterator(); iter.hasNext(); ) {
                Element element = (Element) iter.next();
                String code = element.getText();
                if(code.equals(arCode)){
                    Element flightElement = element.getParent().getParent();
                    //获取飞机类型
                    String planeType = flightElement.attribute("Airplane").getValue();
                    //获取该类型飞机的作为总数
                    int totalSeat = PlaneSeat.getSeatNum(planeType,ConstantVariable.FIRST);

                    int currentLeft = Integer.parseInt(flightElement.element("Seating").element("FirstClass").getText());

                    int firstLeft = totalSeat-currentLeft;

                    //判断经济舱是否有足够的位置
                    if(firstLeft>=numOfPassenger){
                        String num = flightElement.attribute("Number").getValue();
                        Flight flight = info.findFlightByNumber(num, document);
                        int totalTimt = flight.getFlightTime();
                        double price = flight.getFirstPrice();
                        SortingBean sort = new SortingBean();
                        sort.setTotalPrice(price);
                        sort.setTotalTime(totalTimt);
                        Map timeCriteria = new HashMap<String, Date>();
                        timeCriteria.put(ConstantVariable.GODEPART, flight.getDepartTime());
                        timeCriteria.put(ConstantVariable.GOARRIVE, flight.getArriveTime());
                        sort.setTimeCriteria(timeCriteria);
                        map.put(sort, flight);
                    }
                }
            }
        }
        return map;
//        return null;
    }

    public static void main(String[] args) {
        GregorianCalendar calendar = new GregorianCalendar(2015, 04, 9);
        Date date = calendar.getTime();
        String s = XmlConnection.getXmlInfo(QueryFactory.getDepartAirplanes("BOS", date));
        Document document = null;
        try {
            document = DocumentHelper.parseText(s);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        NonStopServiceImpl nn = new NonStopServiceImpl();
        Map map = nn.findNonStopFlights1(ConstantVariable.COACH,1,"ATL",document);

        GregorianCalendar calendar0 = new GregorianCalendar(2015, 04, 9);
        GregorianCalendar calendar1 = new GregorianCalendar(2015, 04, 9);
        GregorianCalendar calendar2 = new GregorianCalendar(2015, 04, 10);
        Date date0 = calendar0.getTime();
        Date date1 = calendar1.getTime();
        Date date2 = calendar2.getTime();
        String s0 = XmlConnection.getXmlInfo(QueryFactory.getDepartAirplanes("BOS", date0));
        String s1 = XmlConnection.getXmlInfo(QueryFactory.getArriveAirplanes("SLC", date1));
        String s2 = XmlConnection.getXmlInfo(QueryFactory.getArriveAirplanes("SLC", date2));
        Document departdoc = null;
        Document arrivedoc = null;
        Document arrivedoc1 = null;
        try {
            departdoc = DocumentHelper.parseText(s0);
            arrivedoc = DocumentHelper.parseText(s1);
            arrivedoc1 = DocumentHelper.parseText(s2);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        OneStopService stopService = ServiceFactory.getInstance().getOneStopService();
        Map map1 = stopService.validOneStop1(ConstantVariable.COACH, 1, departdoc, arrivedoc, arrivedoc1);
//        Map map1 = nn.findOneStopFlights1(ConstantVariable.COACH, 1, "LAX", document);
        PairFlights pair = new PairFlightsImpl();
        Map re = pair.pairMix2(map1,map);
        System.out.println(re);
    }
}
