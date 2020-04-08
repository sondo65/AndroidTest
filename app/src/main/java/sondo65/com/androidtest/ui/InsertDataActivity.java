package sondo65.com.androidtest.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.daimajia.numberprogressbar.NumberProgressBar;

import sondo65.com.androidtest.R;
import sondo65.com.androidtest.models.City;
import sondo65.com.androidtest.utils.SharedPreferencesUtils;
import sondo65.com.androidtest.viewmodels.InsertDataViewModel;

import static sondo65.com.androidtest.utils.Constants.DEFINED_ROW;


public class InsertDataActivity extends AppCompatActivity {

    //ui components
    Button btnInsertData;
    private NumberProgressBar mProgressBar1;

    //var
    private InsertDataViewModel mInsertDataViewModel;
    private Boolean mShouldInsertFakeData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_data);

        SharedPreferencesUtils.init(getApplicationContext());

        findViewById();

        mShouldInsertFakeData = SharedPreferencesUtils.getShouldInsert(SharedPreferencesUtils.SHOULD_INSERT,true);

        if(!mShouldInsertFakeData){
            moveToListActivity();
        }

        mInsertDataViewModel = new ViewModelProvider(this).get(InsertDataViewModel.class);

        btnInsertData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnInsertData.setEnabled(false);
                insertFakeCities();
            }
        });
    }

    private void findViewById(){
        btnInsertData = findViewById(R.id.button_insert);
        mProgressBar1 = findViewById(R.id.progress_bar_1);
    }

    private void moveToListActivity(){
        Intent intent = new Intent(this,CityListActivity.class);
        startActivity(intent);
        finish();
    }

    private void insertFakeCities(){
        mInsertDataViewModel.insertCityTask(this,mProgressBar1);
        mShouldInsertFakeData = false;
        SharedPreferencesUtils.setShouldInsert(SharedPreferencesUtils.SHOULD_INSERT,mShouldInsertFakeData);
    }

}
