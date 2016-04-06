package util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by pianobean on 4/12/15.
 */
public class PlaneSeat {
    private static Document airplanes;
    static {
        SAXReader reader = new SAXReader();
        Class clazz = PlaneSeat.class;
        URL url =clazz.getClassLoader().getResource("Xml/airplanes.xml");
        try {
            airplanes = reader.read(url);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getSeatNum(String model, String seatType){
        Node node = airplanes.selectSingleNode("//Airplane[@Model='"+model+"']");
        Element airplane = (Element) node;
        if(seatType.equals(ConstantVariable.FIRST)){
            return Integer.parseInt(airplane.element("FirstClassSeats").getText());
        }else {
            return Integer.parseInt(airplane.element("CoachSeats").getText());
        }
    }

    public static void main(String[] args) {
        Map map = new HashMap();
        map.put("sss","sss");
        System.out.println(map);
    }

}
