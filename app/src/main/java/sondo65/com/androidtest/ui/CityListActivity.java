package sondo65.com.androidtest.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ProgressBar;
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
import static sondo65.com.androidtest.utils.Constants.LOAD_MORE_ITEM;

public class CityListActivity extends AppCompatActivity {

    private static final String TAG = "CityListActivity";

    //ui components
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;

    //var
    private CityRecyclerAdapter mCityRecyclerAdapter;
    private CityListActivityViewModel mCityListActivityViewModel;
    private LinearLayoutManager mLinearLayoutManager;
    private int mCurrentItems, mTotalItems, mScrollOutItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities_list);

        findViewById();

        mCityListActivityViewModel = new ViewModelProvider(this).get(CityListActivityViewModel.class);

        observeDataCitiesChanged();

        initRecyclerView();

    }

    private void findViewById(){
        mRecyclerView = findViewById(R.id.recycler_view);
        mProgressBar = findViewById(R.id.progress_bar);
    }

    private void initRecyclerView() {
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        mCityRecyclerAdapter = new CityRecyclerAdapter();
        mRecyclerView.setAdapter(mCityRecyclerAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            Boolean isScrolling = false;

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull final RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                mCurrentItems = mLinearLayoutManager.getChildCount();
                mTotalItems = mLinearLayoutManager.getItemCount();
                mScrollOutItems = mLinearLayoutManager.findFirstVisibleItemPosition();

                if (isScrolling && (mCurrentItems + mScrollOutItems == mTotalItems)) {
                    showProgressBar();
                    isScrolling = false;
                    mCityListActivityViewModel.fetchData(mTotalItems, LOAD_MORE_ITEM);
                }
            }
        });
    }


    private void observeDataCitiesChanged() {
        mCityListActivityViewModel.getListCities().observe(this, new Observer<List<City>>() {
            @Override
            public void onChanged(List<City> cities) {
                hideProgressBar();
                mCityRecyclerAdapter.addData(cities);
            }
        });
    }

    private void showProgressBar(){
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar(){
        mProgressBar.setVisibility(View.INVISIBLE);
    }

}
