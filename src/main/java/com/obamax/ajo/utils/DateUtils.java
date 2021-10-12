package com.obamax.ajo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static String getCurrentTime(){
        Date date = new Date();

        //time format
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

        return dateFormat.format(date);
    }
}
