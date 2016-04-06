package test;

import beans.SearchKey;
import dao.ConnectDao;
import daoImpl.ConnectDaoImpl;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.junit.Test;
import util.ConstantVariable;
import util.QueryFactory;
import util.XmlConnection;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by pianobean on 4/30/15.
 */
public class TestConnectDao {
    ConnectDao dao = new ConnectDaoImpl();
    @Test
    public void testAirportSearchInfo() throws DocumentException {
        GregorianCalendar calendar = new GregorianCalendar(2015, 04, 9);
        Date date = calendar.getTime();
        String domStr = XmlConnection.getXmlInfo(QueryFactory.getDepartAirplanes("BOS", date));
        Document document = DocumentHelper.parseText(domStr);
        List<SearchKey> searchKeys = dao.airportSearchInfo(document, ConstantVariable.DEPART);
        System.out.println(searchKeys);
    }

}
