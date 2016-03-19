package com.example.claire.pullstuff.API;


/**
 * Interface with URLs for the various calls needed from Retrofit to the Ready Badger API
 */

//import com.appfactoryuwp.readybadger.entity.CountyEntity;
//import io.realm.RealmObject;
import com.example.claire.pullstuff.Entity.ContactEntity;
import com.example.claire.pullstuff.Entity.CountyEntity;
import com.example.claire.pullstuff.Entity.LocationsEntity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface RbApi {
    //Gets an array of county entities from the API.(Should only be one item)
    @GET("counties/index/RB-API-KEY/testhi/county/{county}")
    Call<CountyEntity[]> getCounty(@Path("county") String county);

    @GET("contacts/time/RB-API-KEY/testhi/timestamp/{timestamp}")
    Call<ContactEntity[]> getContactTimestamp(@Path("timestamp") String timestamp);

    @GET("locations/time/RB-API-KEY/testhi/timestamp/{timestamp}")
    Call<LocationsEntity[]> getLocationTimestamp(@Path("timestamp") String timestamp);

    @GET("counties/time/RB-API-KEY/testhi/timestamp/{timestamp}")
    Call<CountyEntity[]> getCountyTimestamp(@Path("timestamp") String timestamp);

    //Do location object later
    //@GET("counties/index/RB-API-KEY/testhi/locations")

}