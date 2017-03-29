package com.sti.carbonfootprintscalculator.model;

/**
 * Created by mykelneds on 06/03/2017.
 */

public class HistoryAllItem {

    String timeStamp;
    int emissionType;
    double emission;
    String area;

    public HistoryAllItem(String timeStamp, int emissionType, double emission, String area) {
        this.timeStamp = timeStamp;
        this.emissionType = emissionType;
        this.emission = emission;
        this.area = area;
    }

    public int getEmissionType() {
        return emissionType;
    }

    public String getArea() {
        return area;
    }

    public double getEmission() {
        return emission;
    }

    public String getTimeStamp() {
        return timeStamp;
    }
}
