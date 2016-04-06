package util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URL;

/**
 * Created by pianobean on 4/13/15.
 */
public class TimeZoneCreater {
    private static Document airports;
    static {
        SAXReader reader = new SAXReader();
        Class clazz = TimeZoneCreater.class;
        URL url =clazz.getClassLoader().getResource("Xml/airports.xml");
        try {
            airports = reader.read(url);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }
    public static String findTimeZoneId(String code){
        Element airport = (Element) airports.selectSingleNode("//Airport[@Code='"+code+"']");
        String timezoneId = airport.element("TimeZoneId").getText();
        return timezoneId;
    }

    public static void main(String[] args) {
        for(int i=0; i<10; i++) {
            System.out.println(findTimeZoneId("LAX"));
        }
    }
}
