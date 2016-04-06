package beans;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pianobean on 4/9/15.
 */
public class SortingBean {

    private int totalTime;
    private double totalPrice;
    private Map<String, Date> timeCriteria = new HashMap<String, Date>();

    public Map<String, Date> getTimeCriteria() {
        return timeCriteria;
    }

    public void setTimeCriteria(Map<String, Date> timeCriteria) {
        this.timeCriteria = timeCriteria;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    @Override
    public String toString() {
        return getTotalPrice()+":"+totalTime+":"+getTimeCriteria();
    }
}
