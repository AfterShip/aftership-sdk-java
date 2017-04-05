package com.aftership.sdk;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by User on 18/6/14.
 */
final class DateMethods {
    private final static String ISO8601Long = "yyyy-MM-dd'T'HH:mm:ssZ";



    public static Date getDate( String date) throws AftershipAPIException,ParseException {
        SimpleDateFormat dateFormat;
        StringBuilder sb = new StringBuilder(date);
        Date newDate = null;
        if (sb.length() == 25) {
            dateFormat = new SimpleDateFormat(ISO8601Long);
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            sb.deleteCharAt(22);
            newDate = dateFormat.parse(sb.toString());
            return newDate;
        } else{
            throw new AftershipAPIException("The date receive is not properly formatted yyyy-MM-dd'T'HH:mm:ssZ and is: "
                    +date);
        }
    }

    public static String toString(Date date){
        SimpleDateFormat dateFormat;
        StringBuilder sb;
        dateFormat = new SimpleDateFormat(ISO8601Long);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        sb = new StringBuilder(dateFormat.format(date));
        sb.insert(22,':');

        return sb.toString();


    }
}
