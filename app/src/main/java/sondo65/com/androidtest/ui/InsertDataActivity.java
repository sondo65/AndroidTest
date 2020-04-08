package sondo65.com.androidtest.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import sondo65.com.androidtest.R;
import sondo65.com.androidtest.models.City;
import sondo65.com.androidtest.utils.SharedPreferencesUtils;
import sondo65.com.androidtest.viewmodels.InsertDataViewModel;

import static sondo65.com.androidtest.utils.Constants.DEFINED_ROW;


public class InsertDataActivity extends AppCompatActivity {

    //ui components
    Button btnInsertData;

    //var
    private InsertDataViewModel mInsertDataViewModel;
    private Boolean mShouldInsertFakeData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_data);

        SharedPreferencesUtils.init(getApplicationContext());

        btnInsertData = findViewById(R.id.button_insert);

        mShouldInsertFakeData = SharedPreferencesUtils.getShouldInsert(SharedPreferencesUtils.SHOULD_INSERT,true);

        if(!mShouldInsertFakeData)
            moveToListActivity();

        mInsertDataViewModel = new ViewModelProvider(this).get(InsertDataViewModel.class);

        btnInsertData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertFakeCities();
            }
        });
    }

    private void moveToListActivity(){
        Intent intent = new Intent(this,CityListActivity.class);
        startActivity(intent);
        finish();
    }

    private void insertFakeCities(){
        for(int i = 0; i < 200; i++){
            City city = new City();
            city.setName("City " + i);
            city.setCountry("Country " + i);
            city.setPopulation("999,999,999");
            mInsertDataViewModel.insertCityTask(city,this);
        }
        //Make mShouldInsertFakeData = false to prevent insert fake data again when relaunch app
        SharedPreferencesUtils.setShouldInsert(SharedPreferencesUtils.SHOULD_INSERT,false);
    }
}
