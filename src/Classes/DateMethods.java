package Classes;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by User on 18/6/14.
 */
final class DateMethods {
    private final static String ISO8601Long = "yyyy-MM-dd'T'HH:mm:ssZ";
    private final static String ISO8601Medium = "yyyy-MM-dd'T'HH:mm:ss";
    private final static String ISO8601Short= "yyyy-MM-dd'T'";




    public static Date getDate( String date){
        SimpleDateFormat dateFormat;
        StringBuilder sb = new StringBuilder(date);
        Date newDate = null;
        try {
            if (sb.length() == 25) {
                dateFormat = new SimpleDateFormat(ISO8601Long);
                sb.deleteCharAt(22);
                newDate = dateFormat.parse(sb.toString());
                return newDate;
            } else if (sb.length() == 19) {
                dateFormat = new SimpleDateFormat(ISO8601Medium);
                newDate = dateFormat.parse(sb.toString());
                return newDate;

            } else if (sb.length() == 10) {
                dateFormat = new SimpleDateFormat(ISO8601Short);
                newDate = dateFormat.parse(sb.toString());
                return newDate;
            }
        }catch (Exception e){
            System.out.println("Error parsing date: "+e.getMessage());
        }

        return null;
    }

    public static String toString(Date date){
        SimpleDateFormat ISO8601DATEFORMAT;
        StringBuilder sb;
        ISO8601DATEFORMAT = new SimpleDateFormat(ISO8601Long);
        sb = new StringBuilder(ISO8601DATEFORMAT.format(date));
        sb.insert(22,':');

        return sb.toString();


    }
}
