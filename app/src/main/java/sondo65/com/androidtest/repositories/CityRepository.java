package sondo65.com.androidtest.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import sondo65.com.androidtest.async.InsertAsyncTask;
import sondo65.com.androidtest.models.City;
import sondo65.com.androidtest.persistence.CityDao;
import sondo65.com.androidtest.persistence.CityDatabase;

public class CityRepository {

    private static CityRepository instance;
    private CityDao cityDao;

    public static CityRepository getInstance(Context context){
        if(instance == null){
            instance = new CityRepository(context);
        }
        return instance;
    }

    private CityRepository(Context context) {
        cityDao = CityDatabase.getInstance(context).getCityDao();
    }

    public void insertCityTask(City city){
        new InsertAsyncTask(cityDao).execute(city);
    }

    public LiveData<List<City>> retrieveCitiesTask() {
        return cityDao.getCities();
    }

}
