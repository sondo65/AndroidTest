package sondo65.com.androidtest.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import sondo65.com.androidtest.models.City;
import sondo65.com.androidtest.repositories.CityRepository;

public class CityListActivityViewModel extends AndroidViewModel {

    private CityRepository mRepo;

    private LiveData<List<City>> mListCity;

    public LiveData<List<City>> getListCities(){
        return mListCity;
    }

    public CityListActivityViewModel(@NonNull Application application) {
        super(application);

        init(application);
    }

    public void init(Application application){

        if(mListCity != null)
            return;

        mRepo = CityRepository.getInstance(application);

        mListCity =  mRepo.retrieveCitiesTask();
    }

    public void insertCityTask(City city){
        mRepo.insertCityTask(city);
    }

}
