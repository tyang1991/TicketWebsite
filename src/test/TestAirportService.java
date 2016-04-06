package test;

import org.dom4j.Document;
import org.junit.Test;
import service.AirportService;
import serviceImpl.AirportServiceImpl;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by pianobean on 4/30/15.
 */
public class TestAirportService {
    AirportService service = new AirportServiceImpl();

    @Test
    public void testGenerateName(){
        String re = service.generateName("bos");
        System.out.println(re);
    }

    @Test
    public void testGetDepartDom(){
        GregorianCalendar calendar = new GregorianCalendar(2015, 04, 9);
        Date date = calendar.getTime();
        Document document = service.getDepartDom("BOS", date);
        System.out.println(document);
    }

    @Test
    public void testGetArriveDom(){
        GregorianCalendar calendar = new GregorianCalendar(2015, 04, 9);
        Date date = calendar.getTime();
        Document document = service.getArriveDom("BOS", date);
        System.out.println(document);
    }

}
