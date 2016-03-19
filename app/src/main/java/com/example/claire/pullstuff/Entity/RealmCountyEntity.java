package com.example.claire.pullstuff.Entity;

/**
 * Created by Claire on 3/12/2016.
 */
/**
 * Realm Object for the County data imported from Ready Badger API
 */

import java.util.Date;

import io.realm.RealmObject;

public class RealmCountyEntity extends RealmObject {
    private String county;
    private String region;
    private String weatherAlertCode;
    private String currentWeatherCode;
    private String website;
    private String facebook;
    private String twitter;
    private Date timestamp;
    public static final String FIELD_TIMESTAMP = "timestamp";

    //getters and setters
    public void setTimestamp(Date t){
        timestamp = t;
    }

    public Date getTimestamp(){
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
}
