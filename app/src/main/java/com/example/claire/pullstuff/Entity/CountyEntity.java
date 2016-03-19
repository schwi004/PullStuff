package com.example.claire.pullstuff.Entity;

/**
 * Created by Claire on 3/12/2016.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Claire on 3/9/2016.
 * POJO for county data from the Ready Badger Server
 */
public class CountyEntity {
    @Expose
    private String county;

    @Expose
    private String region;

    @Expose
    private String weatherAlertCode;

    @Expose
    private String currentWeatherCode;

    @Expose
    private String website;


    @Expose
    private String facebook;



    @Expose
    private String twitter;

    @Expose
    private String timestamp;

    //Getters and setters

    public void setTimestamp(String t){
        timestamp = t;
    }

    public String getCountyTimestamp(){
        return timestamp;
    }

    public void setCounty(String c){
        county = c;
    }

    public String getCounty(){
        return county;
    }

    public void setRegion(String r){
        region = r;
    }

    public String getRegion(){
        return region;
    }

    public void setWeatherAlertCode(String w){
        weatherAlertCode = w;
    }

    public String getWeatherAlertCode(){
        return weatherAlertCode;
    }

    public void setCurrentWeatherCode(String c){
        currentWeatherCode = c;
    }

    public String getCurrentWeatherCode(){
        return currentWeatherCode;
    }

    public void setWebsite(String w){
        website = w;
    }

    public String getWebsite(){
        return website;
    }

    public void setFacebook(String f){
        facebook = f;
    }

    public String getFacebook(){
        return facebook;
    }

    public void setTwitter(String t){
        twitter = t;
    }

    public String getTwitter(){
        return twitter;
    }

    /*
    public String toString(){
        return "County: " + county +  ", Region: " + region + ", Weather Alert Code:" +
                weatherAlertCode + ", Current Weather Code: " + currentWeatherCode + ", Website: "
                + website + ", Facebook: " + facebook + ", Twitter: " + twitter;
    }
    */
}

