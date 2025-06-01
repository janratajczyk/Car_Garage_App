package com.example.garage_car_app.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NhtsaResponse {

    @SerializedName("Results")
    private List<Result> results;

    public List<Result> getResults() {
        return results;
    }

    public static class Result {

        @SerializedName("Variable")
        private String variable;

        @SerializedName("Value")
        private String value;

        public String getVariable() {
            return variable;
        }

        public String getValue() {
            return value;
        }
    }
}
