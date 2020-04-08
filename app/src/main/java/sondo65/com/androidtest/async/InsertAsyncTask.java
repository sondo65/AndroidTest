package sondo65.com.androidtest.async;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import sondo65.com.androidtest.models.City;
import sondo65.com.androidtest.persistence.CityDao;
import sondo65.com.androidtest.ui.CityListActivity;

public class InsertAsyncTask extends AsyncTask<Void, Integer, Void> {

    private CityDao mCityDao;
    private Context mContext;
    private ProgressBar mProgressBar;

    public InsertAsyncTask(CityDao cityDao, Context context, ProgressBar progressBar){
        this.mCityDao = cityDao;
        this.mContext = context;
        this.mProgressBar = progressBar;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected Void doInBackground(Void... avoid) {
        for(int i = 0; i < 100000; i++){
            City city = new City();
            city.setName("City " + i);
            city.setCountry("Country " + i);
            city.setPopulation("999,999,999");
            mCityDao.insert(city);
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        mProgressBar.setVisibility(View.INVISIBLE);
        Intent intent = new Intent(mContext, CityListActivity.class);
        mContext.startActivity(intent);
    }
}
