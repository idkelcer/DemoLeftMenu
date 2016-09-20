package com.example.kelvin.demomenu.entities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kelvin on 19/09/16.
 */
public class AnualSavingResponse {


    private Integer status;

    private String message;

    private AnualSaving data = new AnualSaving();

    public AnualSavingResponse(JSONObject jsonObject){

        try {

            status = jsonObject.getInt("status");
            message = jsonObject.getString("message");

            JSONObject object = jsonObject.getJSONObject("data");

            data.setAhorroAnual(object.getDouble("ahorro_anual"));
            data.setMonthFirst(object.getInt("month_first"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public AnualSaving getData() {
        return data;
    }

    public void setData(AnualSaving data) {
        this.data = data;
    }
}
