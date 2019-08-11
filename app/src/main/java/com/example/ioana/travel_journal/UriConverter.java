package com.example.ioana.travel_journal;

import android.net.Uri;

import androidx.room.TypeConverter;

public class UriConverter {

    @TypeConverter
    public static String toString(Uri image){
        return image == null ? null : image.toString();
    }

    @TypeConverter
    public static Uri touri(String image){
        return image == null ? null : Uri.parse(image);
    }
}
