package sondo65.com.androidtest.async;

import android.os.AsyncTask;

import sondo65.com.androidtest.models.City;
import sondo65.com.androidtest.persistence.CityDao;

public class InsertAsyncTask extends AsyncTask<City, Void, Void> {

    private CityDao mCityDao;

    public InsertAsyncTask(CityDao cityDao){
        this.mCityDao = cityDao;
    }
    @Override
    protected Void doInBackground(City... cities) {
        mCityDao.insert(cities);
        return null;
    }
}
