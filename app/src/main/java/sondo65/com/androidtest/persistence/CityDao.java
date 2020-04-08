package sondo65.com.androidtest.persistence;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import sondo65.com.androidtest.models.City;


@Dao
public interface CityDao {

    @Insert
    long[] insert(City... cities);

    @Query("SELECT * FROM City LIMIT :offset,:limit")
    List<City> getCities(int offset,int limit);

}
