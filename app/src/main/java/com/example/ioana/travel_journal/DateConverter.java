package com.example.ioana.travel_journal;

import java.util.Calendar;

import androidx.room.TypeConverter;

public class DateConverter {

    @TypeConverter
    public static Calendar toDate(Long timestamp){
        Calendar calendar = Calendar.getInstance();
        if(timestamp == null){
            calendar = null;
        }
        else{
            calendar.setTimeInMillis(timestamp);
        }
        return calendar;
    }

    @TypeConverter
    public static Long toTimestamp(Calendar calendar){
        return calendar == null ? null : calendar.getTimeInMillis();
    }
}
