package test;

import beans.Flight;
import dao.AirportDao;
import daoImpl.AirportDaoImpl;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.junit.Test;
import util.QueryFactory;
import util.XmlConnection;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

/**
 * Created by pianobean on 4/30/15.
 */
public class TestAirportDao {
    AirportDao airportDao = new AirportDaoImpl();
    @Test
    public void testFindCodesAndName(){
        Map<String, String> map = airportDao.findCodesAndNames();
        for(Map.Entry<String, String> entry: map.entrySet()){
            System.out.println(entry.getKey()+"="+entry.getValue());
        }
    }

    @Test
    public void testFindDepartNumbers(){
        GregorianCalendar calendar = new GregorianCalendar(2015, 04, 9);
        Date date = calendar.getTime();
        List<String> list = airportDao.findDepartNumbers("BOS", date);
        System.out.println(list);
    }

    @Test
    public void testFindArriveNumbers(){
        GregorianCalendar calendar = new GregorianCalendar(2015, 04, 9);
        Date date = calendar.getTime();
        List<String> list = airportDao.findArriveNumbers("BOS", date);
        System.out.println(list);
    }

    @Test
    public void testFindFlightByNumber() throws DocumentException {
        GregorianCalendar calendar = new GregorianCalendar(2015, 04, 9);
        Date date = calendar.getTime();
        String domStr = XmlConnection.getXmlInfo(QueryFactory.getDepartAirplanes("BOS",date));
        Document document = DocumentHelper.parseText(domStr);

        Flight flight = airportDao.findFlightByNumber("1735",document);
        System.out.println(flight);
    }
}
