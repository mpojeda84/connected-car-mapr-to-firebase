package com.mpojeda84.mapr.connectedcarmaprtofirebase.model;

public class CarDataDto {

    private String _id;
    private String vin;
    private String make;
    private String year;
    private String odometer;
    private String totalFuelEconomy;
    private String bestFuelEconomy;
    private String milesToday;
    private String milesThisWeek;
    private String highestSpeedToday;
    private String highestSpeedThisWeek;
    private String avgSpeed;
    private String avgCommunitySpeed;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getBestFuelEconomy() {
        return bestFuelEconomy;
    }

    public void setBestFuelEconomy(String bestFuelEconomy) {
        this.bestFuelEconomy = bestFuelEconomy;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getOdometer() {
        return odometer;
    }

    public void setOdometer(String odometer) {
        this.odometer = odometer;
    }

    public String getTotalFuelEconomy() {
        return totalFuelEconomy;
    }

    public void setTotalFuelEconomy(String totalFuelEconomy) {
        this.totalFuelEconomy = totalFuelEconomy;
    }

    public String getMilesToday() {
        return milesToday;
    }

    public void setMilesToday(String milesToday) {
        this.milesToday = milesToday;
    }

    public String getMilesThisWeek() {
        return milesThisWeek;
    }

    public void setMilesThisWeek(String milesThisWeek) {
        this.milesThisWeek = milesThisWeek;
    }

    public String getHighestSpeedToday() {
        return highestSpeedToday;
    }

    public void setHighestSpeedToday(String highestSpeedToday) {
        this.highestSpeedToday = highestSpeedToday;
    }

    public String getHighestSpeedThisWeek() {
        return highestSpeedThisWeek;
    }

    public void setHighestSpeedThisWeek(String highestSpeedThisWeek) {
        this.highestSpeedThisWeek = highestSpeedThisWeek;
    }

    public String getAvgSpeed() {
        return avgSpeed;
    }

    public void setAvgSpeed(String avgSpeed) {
        this.avgSpeed = avgSpeed;
    }

    public String getAvgCommunitySpeed() {
        return avgCommunitySpeed;
    }

    public void setAvgCommunitySpeed(String avgCommunitySpeed) {
        this.avgCommunitySpeed = avgCommunitySpeed;
    }
}
