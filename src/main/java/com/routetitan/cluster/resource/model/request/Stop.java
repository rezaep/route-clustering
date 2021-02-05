package com.routetitan.cluster.resource.model.request;

public class Stop {
    private String id;
    private String city;
    private String zip;
    private String lat;
    private String lng;

    public Stop() {
    }

    public Stop(String id, String city, String zip, String lat, String lng) {
        this.id = id;
        this.city = city;
        this.zip = zip;
        this.lat = lat;
        this.lng = lng;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}
