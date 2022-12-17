package com.future.util;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeUtil {

    public static String LoaclDateTimeToStr(LocalDateTime localDateTime) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return df.format(localDateTime);
    }

    public static LocalDateTime StrToLoaclDateTime(String time) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(time,df);
    }
}
