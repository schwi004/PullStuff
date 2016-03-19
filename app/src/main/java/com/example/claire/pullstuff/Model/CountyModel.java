package com.example.claire.pullstuff.Model;
/*
 * Copyright 2016 UW-Parkside AppFactory
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


        import android.util.Log;

        import com.example.claire.pullstuff.API.RbApi;
        import com.example.claire.pullstuff.Entity.CountyEntity;
        import com.example.claire.pullstuff.Entity.RealmCountyEntity;

        import java.text.DateFormat;
        import java.text.ParseException;
        import java.text.SimpleDateFormat;

        import io.realm.Realm;
        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;
        import retrofit2.Retrofit;
        import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nate eisner on 2/2/16.
 *
 */
public class CountyModel {

    private Realm realm;

    //CountyModel constructor with Realm object to pass in
    public CountyModel (Realm realm) {this.realm = realm;}

    //Main string for the API
    private final String API = "http://readybadger.org/api/index.php/";

    //Will eventually push out Counties through realm and BusProvider
    public void getCountyData(){

    }

    /* Method call to pull county data from API with given county key and then store it within the
     * Realm database.
     */
    public void loadCountyData(String county){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RbApi apiService = retrofit.create(RbApi.class);

        Call<CountyEntity[]> call = apiService.getCounty(county);

        call.enqueue(new Callback<CountyEntity[]>() {

            @Override
            public void onResponse(Call<CountyEntity[]> call, Response<CountyEntity[]> response) {
                int statusCode = response.code();
                CountyEntity[] ctyEntity = response.body();
                //Date format the API gives us
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");




                //Loop through the array we've gotten back
                for(int i = 0; i < ctyEntity.length; i++) {
                    realm.beginTransaction();
                    //Create Realm Object
                    RealmCountyEntity realmCty = realm.createObject(RealmCountyEntity.class);

                    //Set the fields
                    realmCty.setCounty(ctyEntity[i].getCounty());
                    realmCty.setCurrentWeatherCode(ctyEntity[i].getCurrentWeatherCode());
                    realmCty.setRegion(ctyEntity[i].getRegion());
                    realmCty.setWeatherAlertCode(ctyEntity[i].getWeatherAlertCode());
                    realmCty.setFacebook(ctyEntity[i].getFacebook());
                    realmCty.setTwitter(ctyEntity[i].getTwitter());
                    realmCty.setWebsite(ctyEntity[i].getWebsite());
                    //try+catch needed for parsing string into Date object
                    try {
                        realmCty.setTimestamp(df.parse(ctyEntity[0].getCountyTimestamp()));
                    } catch (ParseException e) {
                        Log.e("Parse Exception: ", e.getMessage());
                    }
                    realm.commitTransaction();
                }
            }

            @Override
            public void onFailure(Call<CountyEntity[]> call, Throwable t) {

            }
        });
    }

}