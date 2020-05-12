package com.aftership.sdk.lib;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class DateUtil {
    public static final String FORMAT_WITH_T = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String FORMAT_WITH_Z = "yyyy-MM-dd'T'HH:mm:ssZ";
    public static final String FORMAT_WITH_X = "yyyy-MM-dd'T'HH:mm:ssXXX";
    public static final String FORMAT_WITHOUT_T = "yyyy-MM-dd HH:mm:ss";

    public static Optional<Date> parse(String dateFormat, String dateString) {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        try {
            Date date = format.parse(dateString);
            return Optional.of(date);
        } catch (ParseException e) {
            //e.printStackTrace();
            return Optional.empty();
        }
    }

    public static String format(String dateFormat, Date date) {
        if (date == null) {
            return StrUtil.EMPTY;
        }
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        return format.format(date);
    }
}
