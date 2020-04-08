package sondo65.com.androidtest.viewmodels;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import sondo65.com.androidtest.models.City;
import sondo65.com.androidtest.persistence.CityDao;
import sondo65.com.androidtest.repositories.CityRepository;

import static sondo65.com.androidtest.utils.Constants.LOAD_MORE_ITEM;

public class CityListViewModel extends AndroidViewModel {

    private CityRepository mRepo;

    private MutableLiveData<List<City>> mListCity = new MutableLiveData<>();

    public LiveData<List<City>> getListCities(){
        return mListCity;
    }

    public CityListViewModel(@NonNull Application application) {
        super(application);

        init(application);
    }

    public void init(Application application){

        mRepo = CityRepository.getInstance(application);

        new RetrieveDataAsyncTask(mRepo.getCityDao(),0,LOAD_MORE_ITEM).execute();

    }

    public void fetchData(final int offset, final int limit) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new RetrieveDataAsyncTask(mRepo.getCityDao(),offset,limit).execute();
            }
        },1500);
    }

    public class RetrieveDataAsyncTask extends AsyncTask<Void,Void, List<City>> {

        private CityDao mCityDao;
        private int mOffset,mLimit;

        public RetrieveDataAsyncTask(CityDao cityDao,int offset,int limit) {
            mCityDao = cityDao;
            this.mOffset = offset;
            this.mLimit = limit;
        }


        @Override
        protected List<City> doInBackground(Void... voids) {
            return mCityDao.getCities(mOffset,mLimit);
        }

        @Override
        protected void onPostExecute(List<City> cities) {
            super.onPostExecute(cities);
            mListCity.setValue(cities);
        }
    }

}
