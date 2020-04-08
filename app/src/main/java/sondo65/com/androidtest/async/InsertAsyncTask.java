package sondo65.com.androidtest.async;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import sondo65.com.androidtest.models.City;
import sondo65.com.androidtest.persistence.CityDao;
import sondo65.com.androidtest.ui.CityListActivity;

public class InsertAsyncTask extends AsyncTask<City, Void, Void> {

    private CityDao mCityDao;
    private Context mContext;

    public InsertAsyncTask(CityDao cityDao, Context context){
        this.mCityDao = cityDao;
        this.mContext = context;
    }
    @Override
    protected Void doInBackground(City... cities) {
        mCityDao.insert(cities);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Intent intent = new Intent(mContext, CityListActivity.class);
        mContext.startActivity(intent);
    }
}
