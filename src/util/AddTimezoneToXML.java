package util;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;

/**
 * Created by pianobean on 4/28/15.
 * @author Han Wang
 *
 */


public class AddTimezoneToXML {
    public static void add(){
        SAXReader reader = new SAXReader();
        Class clazz = AddTimezoneToXML.class;
        URL url =clazz.getClassLoader().getResource("Xml/airports.xml");
        Document document = null;
        try {
            document = reader.read(url);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Element root = document.getRootElement();
        for ( Iterator i = root.elementIterator( "Airport" ); i.hasNext(); ) {
            Element airport = (Element) i.next();
            String code = airport.attribute("Code").getValue();
            String timeId = TimeZoneCreater.findTimeZoneId(code);
//            System.out.println(timeId);
            Element timeElement = airport.addElement("TimeZoneId");
            timeElement.setText(timeId);
            try {
                XMLWriter writer = new XMLWriter(new FileWriter("src/Xml/airports.xml"));
                writer.write( document );
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        add();
    }
}
