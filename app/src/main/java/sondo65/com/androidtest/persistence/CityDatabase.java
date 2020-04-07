package sondo65.com.androidtest.persistence;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import sondo65.com.androidtest.models.City;

@Database(entities = {City.class},version = 1)
public abstract class CityDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "cities_db";

    private static CityDatabase instance;

    public static CityDatabase getInstance(final Context context){
        if(instance == null){
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    CityDatabase.class,
                    DATABASE_NAME
            ).build();
        }
        return instance;
    }

    public abstract CityDao getCityDao();
}
