package it.sevenbits.FacultySite.web.service;


import java.util.Calendar;

public class Helper {
    public static String getDate(){
        Calendar calendar = Calendar.getInstance();
        String year = "" + calendar.get(Calendar.YEAR);
        String month = "" + preparingOfNumber(calendar.get(Calendar.MONTH) + 1);
        String day = "" + preparingOfNumber(calendar.get(Calendar.DAY_OF_MONTH));
        return year + "-" + month + "-" + day;
    }

    public static String getTime(){
        Calendar calendar = Calendar.getInstance();
        String hour = "" + preparingOfNumber(calendar.get(Calendar.HOUR_OF_DAY));
        String minute = "" + preparingOfNumber(calendar.get(Calendar.MINUTE));
        String seconds = "" + preparingOfNumber(calendar.get(Calendar.SECOND));
        return hour + ":" + minute + ":" + seconds;
    }

    private static String preparingOfNumber(int num){
        return (num > 10 ? ""+num : "0" + num);
    }

}
