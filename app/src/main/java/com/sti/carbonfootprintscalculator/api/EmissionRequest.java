package com.sti.carbonfootprintscalculator.api;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mykelneds on 28/03/2017.
 */

public class EmissionRequest {

    @SerializedName("municipality_id")
    String municipalityId;

    @SerializedName("value")
    double value;

    public EmissionRequest(String municipalityId, double value) {
        this.municipalityId = municipalityId;
        this.value = value;
    }


}
