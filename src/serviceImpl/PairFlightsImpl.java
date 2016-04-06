package serviceImpl;

import beans.Flight;
import beans.SortingBean;
import service.PairFlights;
import util.ConstantVariable;

import java.util.*;

/**
 * Created by pianobean on 3/13/15.
 */
public class PairFlightsImpl implements PairFlights{
    public List<List<List<Flight>>> pairOneStop(List<List<Flight>> goFlights, List<List<Flight>> comeFlights){
        List list = new ArrayList();
        Iterator it = goFlights.iterator();
        while (it.hasNext()){
            List go = (List) it.next();
            for(int i=0; i<comeFlights.size(); i++){
                List pair = new ArrayList();
                List come = comeFlights.get(i);
                pair.add(go);
                pair.add(come);
                list.add(pair);
            }
        }
        return list;
    }

    @Override
    public Map<SortingBean, List<List<Flight>>> pairOneStop1(Map<SortingBean, List<Flight>> goFlights, Map<SortingBean, List<Flight>> comeFlights) {
        Map<SortingBean, List<List<Flight>>> map = new HashMap<SortingBean, List<List<Flight>>>();

        for(Map.Entry<SortingBean, List<Flight>> entryGo: goFlights.entrySet()){

            for(Map.Entry<SortingBean, List<Flight>> entryCome: comeFlights.entrySet()){
                SortingBean goKey = entryGo.getKey();
                SortingBean comeKey = entryCome.getKey();
                int totalTime = goKey.getTotalTime()+comeKey.getTotalTime();
                double totalPrice = goKey.getTotalPrice()+comeKey.getTotalPrice();
                SortingBean sort = new SortingBean();
                sort.setTotalPrice(totalPrice);
                sort.setTotalTime(totalTime);
                Map timeCriteria = new HashMap<String, Date>();
                timeCriteria.put(ConstantVariable.GODEPART, entryGo.getValue().get(0).getDepartTime());
                timeCriteria.put(ConstantVariable.GOARRIVE, entryGo.getValue().get(1).getArriveTime());
                timeCriteria.put(ConstantVariable.COMEDEPART, entryCome.getValue().get(0).getDepartTime());
                timeCriteria.put(ConstantVariable.COMEARRIVE, entryCome.getValue().get(1).getArriveTime());
                sort.setTimeCriteria(timeCriteria);

                List<List<Flight>> list = new ArrayList();
                list.add(entryGo.getValue());
                list.add(entryCome.getValue());
                map.put(sort, list);
            }

        }

        return map;
    }

    public List<List<Flight>> pairNonStop(List<Flight> goFlights, List<Flight> comeFlights){
        List list = new ArrayList();
        Iterator it = goFlights.iterator();
        while (it.hasNext()){
            Flight go = (Flight) it.next();
            for(int i=0; i<comeFlights.size(); i++){
                List pair = new ArrayList();
                Flight come = comeFlights.get(i);
                pair.add(go);
                pair.add(come);
                list.add(pair);
            }
        }
        return list;
    }

    @Override
    public Map<SortingBean, List<Flight>> pairNonStop1(Map<SortingBean, Flight> goFlights, Map<SortingBean, Flight> comeFlights) {

        Map<SortingBean, List<Flight>> map = new HashMap<SortingBean, List<Flight>>();

        for(Map.Entry<SortingBean, Flight> entryGo: goFlights.entrySet()){

            for(Map.Entry<SortingBean, Flight> entryCome: comeFlights.entrySet()){
                SortingBean goKey = entryGo.getKey();
                SortingBean comeKey = entryCome.getKey();
                int totalTime = goKey.getTotalTime()+comeKey.getTotalTime();
                double totalPrice = goKey.getTotalPrice()+comeKey.getTotalPrice();
                SortingBean sort = new SortingBean();
                sort.setTotalPrice(totalPrice);
                sort.setTotalTime(totalTime);
                Map timeCriteria = new HashMap<String, Date>();
                timeCriteria.put(ConstantVariable.GODEPART, entryGo.getValue().getDepartTime());
                timeCriteria.put(ConstantVariable.GOARRIVE, entryGo.getValue().getArriveTime());
                timeCriteria.put(ConstantVariable.COMEDEPART, entryCome.getValue().getDepartTime());
                timeCriteria.put(ConstantVariable.COMEARRIVE, entryCome.getValue().getArriveTime());
                sort.setTimeCriteria(timeCriteria);


                List<Flight> list = new ArrayList();
                list.add(entryGo.getValue());
                list.add(entryCome.getValue());
                map.put(sort, list);
            }

        }

        return map;
    }

    @Override
    public Map<SortingBean, List> pairMix1(Map<SortingBean, Flight> goNon, Map<SortingBean, List<Flight>> comeOne) {
        Map<SortingBean, List> map = new HashMap<SortingBean, List>();
        //将nonstop里面每个集合的第一个元素与onestop里面每个集合的第二个元素匹配, 并加入map集合
        for(Map.Entry<SortingBean, Flight> entry : goNon.entrySet()){
            Flight goFlight = entry.getValue();
            SortingBean sortGo = entry.getKey();
            for(Map.Entry<SortingBean, List<Flight>> backEntry: comeOne.entrySet()){
                SortingBean sortCome = backEntry.getKey();
                List<Flight> comeFlights = backEntry.getValue();

                SortingBean newSort = new SortingBean();
                newSort.setTotalTime(sortGo.getTotalTime()+sortCome.getTotalTime());
                newSort.setTotalPrice(sortGo.getTotalPrice()+sortCome.getTotalPrice());
                Map timeCriteria = new HashMap<String, Date>();
                timeCriteria.put(ConstantVariable.GODEPART, goFlight.getDepartTime());
                timeCriteria.put(ConstantVariable.GOARRIVE, goFlight.getArriveTime());
                timeCriteria.put(ConstantVariable.COMEDEPART, comeFlights.get(0).getDepartTime());
                timeCriteria.put(ConstantVariable.COMEARRIVE, comeFlights.get(1).getArriveTime());
                newSort.setTimeCriteria(timeCriteria);

                List list = new ArrayList();
                list.add(goFlight);
                list.add(comeFlights);

                map.put(newSort,list);
            }
        }
        //{SortingBean=[Flight,[Flight,Flight]],SortingBean=[Flight,[Flight,Flight]].....}
        return map;
    }

    @Override
    public Map<SortingBean, List> pairMix2(Map<SortingBean, List<Flight>> oneGo, Map<SortingBean, Flight> nonCome) {
        Map<SortingBean, List> map = new HashMap<SortingBean, List>();
        for(Map.Entry<SortingBean, List<Flight>> entryGo: oneGo.entrySet()){
            SortingBean sortGo = entryGo.getKey();
            List<Flight> goFlights = entryGo.getValue();
            for(Map.Entry<SortingBean, Flight> entryCome: nonCome.entrySet()){
                SortingBean sortCome = entryCome.getKey();
                Flight comeFlight = entryCome.getValue();

                SortingBean newSort = new SortingBean();
                newSort.setTotalPrice(sortGo.getTotalPrice()+sortCome.getTotalPrice());
                newSort.setTotalTime(sortGo.getTotalTime()+sortCome.getTotalTime());
                Map timeCriteria = new HashMap<String, Date>();
                timeCriteria.put(ConstantVariable.GODEPART, goFlights.get(0).getDepartTime());
                timeCriteria.put(ConstantVariable.GOARRIVE, goFlights.get(1).getArriveTime());
                timeCriteria.put(ConstantVariable.COMEDEPART, comeFlight.getDepartTime());
                timeCriteria.put(ConstantVariable.COMEARRIVE, comeFlight.getArriveTime());
                newSort.setTimeCriteria(timeCriteria);

                List list = new ArrayList();
                list.add(goFlights);
                list.add(comeFlight);

                map.put(newSort,list);
            }
        }
        return map;
    }
}
