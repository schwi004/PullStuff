package com.example.claire.pullstuff.Model;

import android.util.Log;

import com.example.claire.pullstuff.API.RbApi;
import com.example.claire.pullstuff.Entity.ContactEntity;
import com.example.claire.pullstuff.Entity.CountyEntity;
import com.example.claire.pullstuff.Entity.LocationsEntity;
import com.example.claire.pullstuff.Entity.RealmContactEntity;
import com.example.claire.pullstuff.Entity.RealmCountyEntity;
import com.example.claire.pullstuff.Entity.RealmLocationsEntity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Gets the most recent info from Realm DB and re-populates the database with any new information
 * from the API
 */


public class RecentModel {
    //Realm Object we will bring in from constructor
    private Realm realm;

    public RecentModel (Realm realm) {this.realm = realm;}

    //Main string for the API
    private final String API = "http://readybadger.org/api/index.php/";


    public void loadCountyData(){
        //Set up date format that works with the API
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");

        //pull the newest time from the DB (for county objects)
        Date newTime = realm.where(RealmCountyEntity.class).maximumDate(RealmCountyEntity.FIELD_TIMESTAMP);

        //In case there are currently no fields in the realm DB
        if(newTime == null){
            //If its null just set it to current time
            try {
                newTime = df.parse("2016-02-29 24:24:51");
            }
            catch (ParseException e){
                Log.e("Parse Exception ", e.getMessage());
            }


        }


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RbApi apiService = retrofit.create(RbApi.class);

        //makes call to the API for everything past the timestamp
        //Call<CountyEntity[]> call = apiService.getCountyTimestamp(df.format(newTime));
        Log.d("Bad String: ", df.format(newTime));
        //hardcoding for testing
        Call<CountyEntity[]> call = apiService.getCountyTimestamp("2016-02-29 24:24:51");
        call.enqueue(new Callback<CountyEntity[]>() {

            @Override
            public void onResponse(Call<CountyEntity[]> call, Response<CountyEntity[]> response) {
                int statusCode = response.code();
                //Log.d("Status Code: " + statusCode, "");
                CountyEntity[] ctyEntity = response.body();

                //The k for hour is 1-24 hours. ex: 01 is 12:00AM
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");

                //Create Realm Object
                if (ctyEntity == null){
                    //Do Nothing, since there's no new data to pull in
                }
                else {
                    for (int i = 0; i < ctyEntity.length; i++) {
                        realm.beginTransaction();
                        RealmCountyEntity realmCty = realm.createObject(RealmCountyEntity.class);
                        realmCty.setCounty(ctyEntity[i].getCounty());
                        realmCty.setCurrentWeatherCode(ctyEntity[i].getCurrentWeatherCode());
                        realmCty.setRegion(ctyEntity[i].getRegion());
                        realmCty.setWeatherAlertCode(ctyEntity[i].getWeatherAlertCode());
                        realmCty.setFacebook(ctyEntity[i].getFacebook());
                        realmCty.setTwitter(ctyEntity[i].getTwitter());
                        realmCty.setWebsite(ctyEntity[i].getWebsite());
                        //try catch block for parsing timestamp into date object
                        try {
                            realmCty.setTimestamp(df.parse(ctyEntity[i].getCountyTimestamp()));
                        } catch (ParseException e) {
                            Log.e("Parse Exception: ", e.getMessage());
                        }
                        realm.commitTransaction();
                    }
                }
            }

            @Override
            public void onFailure(Call<CountyEntity[]> call, Throwable t) {
                Log.e("Retrofit Failure: ", t.getMessage());
            }
        });
    }

    public void loadLocationData(){
        RealmLocationsEntity myLocation = new RealmLocationsEntity();

        Date newTime = myLocation.getTimestamp();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RbApi apiService = retrofit.create(RbApi.class);

        Call<LocationsEntity[]> call = apiService.getLocationTimestamp(newTime.toString());

        call.enqueue(new Callback<LocationsEntity[]>() {

            @Override
            public void onResponse(Call<LocationsEntity[]> call, Response<LocationsEntity[]> response) {
                int statusCode = response.code();
                LocationsEntity[] locEntity = response.body();

                //Create Realm Object

                for(int i = 0; i < locEntity.length; i++){
                    realm.beginTransaction();
                        RealmLocationsEntity realmLoc = realm.createObject(RealmLocationsEntity.class);
                        realmLoc.setCounty(locEntity[i].getCounty());
                        realmLoc.setName(locEntity[i].getName());
                        realmLoc.setStreetAddress(locEntity[i].getStreetAddress());
                        realmLoc.setCity(locEntity[i].getCity());
                        realmLoc.setState(locEntity[i].getState());
                        realmLoc.setZipcode(locEntity[i].getZipcode());
                        realmLoc.setPhoneNumber(locEntity[i].getPhoneNumber());
                        realmLoc.setType(locEntity[i].getType());
                        realmLoc.setFacebook(locEntity[i].getFacebook());
                        realmLoc.setTwitter(locEntity[i].getTwitter());
                        realmLoc.setWebsite(locEntity[i].getWebsite());
                        realmLoc.setTimestamp(locEntity[i].getLocationTimestamp());
                    realm.commitTransaction();
                }
            }

            @Override
            public void onFailure(Call<LocationsEntity[]> call, Throwable t) {

            }

        });
    }

    public void loadContacts(){
        RealmContactEntity myContact = new RealmContactEntity();

        Date newTime = myContact.getTimestamp();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RbApi apiService = retrofit.create(RbApi.class);

        Call<ContactEntity[]> call = apiService.getContactTimestamp(newTime.toString());

        call.enqueue(new Callback<ContactEntity[]>() {


            @Override
            public void onResponse(Call<ContactEntity[]> call, Response<ContactEntity[]> response) {
                int statusCode = response.code();
                ContactEntity[] contEntity = response.body();

                //Create Realm Object

                for(int i = 0; i < contEntity.length; i++){
                    realm.beginTransaction();
                    RealmContactEntity realmCont = realm.createObject(RealmContactEntity.class);
                    realmCont.setAdminArea(contEntity[i].getAdminArea());
                    realmCont.setName(contEntity[i].getName());
                    realmCont.setStreetAddress(contEntity[i].getStreetAddress());
                    realmCont.setCity(contEntity[i].getCity());
                    realmCont.setState(contEntity[i].getState());
                    realmCont.setTitle(contEntity[i].getTitle());
                    realmCont.setZipCode(contEntity[i].getZipCode());
                    realmCont.setEmail(contEntity[i].getEmail());
                    realmCont.setPhoneNumber(contEntity[i].getPhoneNumber());
                    realmCont.setRegion((contEntity[i].getRegion()));
                    realmCont.setFacebook(contEntity[i].getFacebook());
                    realmCont.setTwitter(contEntity[i].getTwitter());
                    realmCont.setWebsite(contEntity[i].getWebsite());
                    realmCont.setTimestamp(contEntity[i].getContactTimestamp());
                    realm.commitTransaction();
                }
            }

            @Override
            public void onFailure(Call<ContactEntity[]> call, Throwable t) {

            }

        });
    }

}




