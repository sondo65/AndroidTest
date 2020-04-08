package sondo65.com.androidtest.async;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.daimajia.numberprogressbar.NumberProgressBar;

import sondo65.com.androidtest.models.City;
import sondo65.com.androidtest.persistence.CityDao;
import sondo65.com.androidtest.ui.CityListActivity;
import sondo65.com.androidtest.utils.Constants;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class InsertAsyncTask extends AsyncTask<Void, Integer, Void> {

    private CityDao mCityDao;
    private Context mContext;
    private NumberProgressBar mProgressBar;

    public InsertAsyncTask(CityDao cityDao, Context context, NumberProgressBar progressBar){
        this.mCityDao = cityDao;
        this.mContext = context;
        this.mProgressBar = progressBar;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressBar.setProgress(0);
        mProgressBar.setMax(100);
    }

    @Override
    protected Void doInBackground(Void... avoid) {
        for(int i = 0; i < Constants.DEFINED_ROW; i++){
            City city = new City();
            city.setName("City " + i);
            city.setCountry("Country " + i);
            city.setPopulation("999,999,999");
            mCityDao.insert(city);
            publishProgress(i);
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        int percent = (int) ((values[0]/Constants.DEFINED_ROW)* 100);
        mProgressBar.setProgress(percent);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Intent intent = new Intent(mContext, CityListActivity.class);
        mContext.startActivity(intent);
        ((Activity) mContext).finish();
    }
}
