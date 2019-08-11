package com.example.ioana.travel_journal;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.TypeConverters;

@Dao
@TypeConverters({TypeConverter.class,UriConverter.class,DateConverter.class})
public interface TripDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertTrip(Trip trip);

    @Delete
    void deleteTrip(Trip trip);

    @Query("SELECT * FROM Trips")
    List<Trip> getAllTrips();




}
