package util;

import service.PairFlights;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author  pianobean
 * This class help to format the input date to given format.
 */
public class DateFormater {
    public static Date format(String input){
        SimpleDateFormat format = new SimpleDateFormat("M/d/yyyy");
        Date date = null;
        try {
            date = format.parse(input);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return date;
    }

    public static Date formatTime(String input){
        SimpleDateFormat format = new SimpleDateFormat("yyyy MMM dd HH:mm zzz");
        Date date = null;
        try{
            date = format.parse(input);
        }catch (ParseException e){
            e.printStackTrace();
        }
        return date;
    }

    public static String formatDate(Date date, String timeId){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd, EEE");
        sdf.setTimeZone(TimeZone.getTimeZone(timeId));
        return sdf.format(date);
    }

    public int timeCompare(String time1, String time2){
        String[] t1 = time1.split(" ");
        String[] t2 = time2.split(" ");

        if(t1[1].equals(t2[1])){
            String[] numTime1 = t1[0].split(":");
            String[] numTime2 = t2[0].split(":");

            String num1 = numTime1[0];
            String num2 = numTime2[0];

            if(num1.equals("12")) num1="0";
            if(num2.equals("12")) num2="0";

            if(Integer.parseInt(num1)<Integer.parseInt(num2)) return -1;
            else if(Integer.parseInt(num1)>Integer.parseInt(num2)) return 1;
            else {
                if(Integer.parseInt(numTime1[1])<Integer.parseInt(numTime2[1])) return -1;
                else if(Integer.parseInt(numTime1[1])>Integer.parseInt(numTime2[1])) return 1;
            }
        }else if (t1[1].equals("AM")){
            return -1;
        }else return 1;

        return 0;
    }

    public String outputTime(String inputTime){
        String[] splitTime = inputTime.split(" ");
        String timeVal = splitTime[0];
        String[] values = timeVal.split(":");
        int x = Integer.parseInt(values[0]);
        if(x==12) x = 0;
        int y = Integer.parseInt(values[1]);
        if(splitTime[1].equals("AM")){
            return String.valueOf(60*x+y);
        }else {
            return String.valueOf(720+(60*x+y));
        }
    }

    public static void main(String[] args) {

//        Date date = new Date();
//        System.out.println(formatDate(date,TimeZoneCreater.findTimeZoneId("BOS")));
        DateFormater formater = new DateFormater();
        String t1 = "12:30 PM";
        String t2 = "12:33 AM";
        System.out.println(formater.outputTime(t1));
    }
}
