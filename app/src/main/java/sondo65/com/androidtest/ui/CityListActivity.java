package sondo65.com.androidtest.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader;
import com.bumptech.glide.util.ViewPreloadSizeProvider;

import java.util.ArrayList;
import java.util.List;

import sondo65.com.androidtest.R;
import sondo65.com.androidtest.adapters.CityRecyclerAdapter;
import sondo65.com.androidtest.models.City;
import sondo65.com.androidtest.repositories.CityRepository;
import sondo65.com.androidtest.utils.SharedPreferencesUtils;
import sondo65.com.androidtest.utils.SimpleDividerItemDecoration;
import sondo65.com.androidtest.viewmodels.CityListActivityViewModel;

import static sondo65.com.androidtest.utils.Constants.DEFINED_ROW;

public class CityListActivity extends AppCompatActivity {

    private static final String TAG = "CityListActivity";

    //ui components
    private RecyclerView mRecyclerView;

    //var
    private CityRecyclerAdapter mCityRecyclerAdapter;
    private CityListActivityViewModel mCityListActivityViewModel;
    private Boolean mShouldInsertFakeData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities_list);

        SharedPreferencesUtils.init(getApplicationContext());

        mRecyclerView = findViewById(R.id.recycler_view);

        mCityListActivityViewModel = new ViewModelProvider(this).get(CityListActivityViewModel.class);

        mCityListActivityViewModel.init(CityListActivity.this.getApplication());

        mShouldInsertFakeData = SharedPreferencesUtils.getShouldInsert(SharedPreferencesUtils.SHOULD_INSERT,true);

        if(mShouldInsertFakeData){
            insertFakeCities();
        }

        retrieveCities();

        initRecyclerView();

    }

    private void initRecyclerView(){
        ViewPreloadSizeProvider<String> viewPreloader = new ViewPreloadSizeProvider<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        mCityRecyclerAdapter = new CityRecyclerAdapter(viewPreloader);

        RecyclerViewPreloader<String> preloader = new RecyclerViewPreloader<String>(
                Glide.with(this),
                mCityRecyclerAdapter,
                viewPreloader,
                30);

        mRecyclerView.addOnScrollListener(preloader);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(!mRecyclerView.canScrollVertically(1)){
                    Log.d(TAG, "onScrollStateChanged: Loadmore");
                }
            }
        });


        mRecyclerView.setAdapter(mCityRecyclerAdapter);
    }

    private void retrieveCities(){
        mCityListActivityViewModel.getListCities().observe(this, new Observer<List<City>>() {
            @Override
            public void onChanged(@Nullable List<City> cities) {
                /*if(mListCity.size() > 0){
                    mListCity.clear();
                }
                if(cities != null){
                    mListCity.addAll(cities);
                }*/
                //mCityRecyclerAdapter.notifyDataSetChanged();

                mCityRecyclerAdapter.replaceData(cities);
            }
        });
    }

    private void insertFakeCities(){
        for(int i = 0; i < DEFINED_ROW; i++){
            City city = new City();
            city.setName("City " + i);
            city.setCountry("Country " + i);
            city.setPopulation("999,999,999");
            mCityListActivityViewModel.insertCityTask(city);
        }
        //Make mShouldInsertFakeData = false to prevent insert fake data again when relaunch app
        SharedPreferencesUtils.setShouldInsert(SharedPreferencesUtils.SHOULD_INSERT,false);
    }
}
