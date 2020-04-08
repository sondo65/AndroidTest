package sondo65.com.androidtest.viewmodels;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.daimajia.numberprogressbar.NumberProgressBar;

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

    public void insertCityTask(Context context, NumberProgressBar progressBar){
        mRepo.insertCityTask(context,progressBar);
    }
}
