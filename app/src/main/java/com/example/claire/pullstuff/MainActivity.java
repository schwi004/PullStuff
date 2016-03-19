package com.example.claire.pullstuff;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.claire.pullstuff.API.RbApi;
import com.example.claire.pullstuff.Entity.CountyEntity;
import com.example.claire.pullstuff.Entity.RealmCountyEntity;
import com.example.claire.pullstuff.Model.RecentModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get the interface stuff
        final TextView textBox = (TextView) findViewById(R.id.textView);
        textBox.setMovementMethod(new ScrollingMovementMethod());
        Button button = (Button) findViewById(R.id.button);
        final String API = "http://readybadger.org/api/index.php/";
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //date format?




                //I think I want to use application context here?
                Realm myRealm = Realm.getInstance(getApplicationContext());
                //new up RecentModel and pass in realm object
                RecentModel rm = new RecentModel(myRealm);

                //Should pull in county data from DB
                rm.loadCountyData();

                DateFormat df = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");

                //get the most recent date again
                //Date newTime = myRealm.where(RealmCountyEntity.class).maximumDate(RealmCountyEntity.FIELD_TIMESTAMP);
                Date newTime = new Date();
                try {
                    newTime = df.parse("2016-02-29 24:24:51");
                }
                catch (ParseException e){

                }
                //Log.d("Date is: ", newTime.toString());
                //In case there are currently no fields in the realm DB
                if(newTime == null){
                    //no arg constructor should just get current date/time.
                    //newTime = new Date(116, 2, 29, 3, 3);
                    Log.d("Time: " , newTime.toString());
                    //may have to use date format here?
            }

                //Should throw everything from the most recent date we got into a RealmResults object.
                RealmResults<RealmCountyEntity> countyData = myRealm.where(RealmCountyEntity.class).greaterThanOrEqualTo("timestamp", newTime ).findAll();
                StringBuilder info = new StringBuilder();
                for(int i = 0; i < countyData.size(); i++){
                    info.append(" " + countyData.get(i) + " \n");
                }

                textBox.setText(info.toString());

                Log.d("On Click: ", info.toString());

                //Date d = new Date();

                //textBox.setText(df.format(d));



            }
        }



         );
    }
}
