package serviceImpl;

import beans.SortingBean;
import factory.ServiceFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import service.OneStopService;
import service.PairFlights;
import service.SortFlights;
import util.ConstantVariable;
import util.QueryFactory;
import util.XmlConnection;

import java.util.*;

/**
 * Created by pianobean on 4/9/15.
 */
public class SortFlightsImpl implements SortFlights {
    @Override
    public List sortByPrice(List showInfo) {
        showInfo.sort(new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                 Map.Entry set1 = (Map.Entry) o1;
                 Map.Entry set2 = (Map.Entry) o2;
                 SortingBean key1 = (SortingBean) set1.getKey();
                 SortingBean key2 = (SortingBean) set2.getKey();
                return (int) (key1.getTotalPrice()-key2.getTotalPrice());
            }
        });

        return showInfo;
    }

    @Override
    public List sortByPriceDec(List showInfo) {
        showInfo.sort(new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Map.Entry set1 = (Map.Entry) o1;
                Map.Entry set2 = (Map.Entry) o2;
                SortingBean key1 = (SortingBean) set1.getKey();
                SortingBean key2 = (SortingBean) set2.getKey();
                return (int) (key2.getTotalPrice()-key1.getTotalPrice());
            }
        });

        return showInfo;
    }

    @Override
    public List sortByTime(List showInfo) {
        showInfo.sort(new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Map.Entry set1 = (Map.Entry) o1;
                Map.Entry set2 = (Map.Entry) o2;
                SortingBean key1 = (SortingBean) set1.getKey();
                SortingBean key2 = (SortingBean) set2.getKey();
                return key1.getTotalTime()-key2.getTotalTime();
            }
        });

        return showInfo;
    }

    @Override
    public List sortByTimeDec(List showInfo) {
        showInfo.sort(new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Map.Entry set1 = (Map.Entry) o1;
                Map.Entry set2 = (Map.Entry) o2;
                SortingBean key1 = (SortingBean) set1.getKey();
                SortingBean key2 = (SortingBean) set2.getKey();
                return key2.getTotalTime()-key1.getTotalTime();
            }
        });

        return showInfo;
    }

//    @Override
//    public List sortForTimeWindow(List showInfo, final String key) {
//        showInfo.sort(new Comparator() {
//            @Override
//            public int compare(Object o1, Object o2) {
//                Map.Entry set1 = (Map.Entry) o1;
//                Map.Entry set2 = (Map.Entry) o2;
//                SortingBean key1 = (SortingBean) set1.getKey();
//                SortingBean key2 = (SortingBean) set2.getKey();
//                Map<String, Date> map1 = key1.getTimeCriteria();
//                Map<String, Date> map2 = key2.getTimeCriteria();
//                Date date1 = map1.get(key);
//                Date date2 = map2.get(key);
//                return (int) (date2.getTime()-date1.getTime());
//            }
//        });
//        return showInfo;
//    }

//    public static void main(String[] args) {
//        GregorianCalendar calendar = new GregorianCalendar(2015, 04, 9);
//        GregorianCalendar calendar1 = new GregorianCalendar(2015, 04, 9);
//        GregorianCalendar calendar2 = new GregorianCalendar(2015, 04, 10);
//        Date date = calendar.getTime();
//        Date date1 = calendar1.getTime();
//        Date date2 = calendar2.getTime();
//        String s = XmlConnection.getXmlInfo(QueryFactory.getDepartAirplanes("BOS", date));
//        String s1 = XmlConnection.getXmlInfo(QueryFactory.getArriveAirplanes("SLC", date1));
//        String s2 = XmlConnection.getXmlInfo(QueryFactory.getArriveAirplanes("SLC", date2));
//        Document departdoc = null;
//        Document arrivedoc = null;
//        Document arrivedoc1 = null;
//        try {
//            departdoc = DocumentHelper.parseText(s);
//            arrivedoc = DocumentHelper.parseText(s1);
//            arrivedoc1 = DocumentHelper.parseText(s2);
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        }
//        OneStopService stopService = ServiceFactory.getInstance().getOneStopService();
//        Map map = stopService.validOneStop1(ConstantVariable.COACH, 1, departdoc, arrivedoc, arrivedoc1);
//        System.out.println(map);
//        Map map1 = stopService.validOneStop1(ConstantVariable.FIRST, 1, departdoc, arrivedoc, arrivedoc1);
//        System.out.println(map1);
//        PairFlights pair = new PairFlightsImpl();
//        Map re = pair.pairOneStop1(map, map1);
//
//        List list = new ArrayList(map1.entrySet());
//
//        SortFlightsImpl x = new SortFlightsImpl();
//        List result = x.sortForTimeWindow(list, "goArriveTime");
//        System.out.println(result);
//    }
}
