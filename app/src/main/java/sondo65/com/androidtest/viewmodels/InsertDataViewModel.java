package sondo65.com.androidtest.viewmodels;

import android.app.Application;
import android.content.Context;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import sondo65.com.androidtest.models.City;
import sondo65.com.androidtest.repositories.CityRepository;

public class InsertDataViewModel extends AndroidViewModel {

    private CityRepository mRepo;

    public InsertDataViewModel(@NonNull Application application) {
        super(application);

        init(application);
    }

    public void init(Application application){
        mRepo = CityRepository.getInstance(application);
    }

    public void insertCityTask(Context context, ProgressBar progressBar){
        mRepo.insertCityTask(context,progressBar);
    }
}
