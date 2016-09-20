package com.example.kelvin.demomenu.entities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kelvin on 19/09/16.
 */
public class MonthSavingResponse {

    private int status;
    private String message;
    private int previous;
    private int next;
    private int statusCount;

    private List<MonthSaving> data = new ArrayList<>();

    public MonthSavingResponse(JSONObject jsonObject) {

        try {

            status = jsonObject.getInt("status");
            message = jsonObject.getString("message");
            previous = jsonObject.getInt("previous");
            next = jsonObject.getInt("next");
            statusCount = jsonObject.getInt("statusCount");

            JSONArray array = jsonObject.getJSONArray("data");

            for (int i = 0; i < array.length(); i++) {

                JSONObject jsonObject1 = (JSONObject) array.get(i);

                MonthSaving monthSaving = new MonthSaving();

                monthSaving.setTipoEstablecimientoId(jsonObject1.getInt("tipo_establecimiento_id"));
                monthSaving.setNombre(jsonObject1.getString("nombre"));
                monthSaving.setFechaConsumo(jsonObject1.getString("fecha_consumo"));
                monthSaving.setMontoDescontado(jsonObject1.getDouble("monto_descontado"));

                data.add(monthSaving);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getPrevious() {
        return previous;
    }

    public void setPrevious(int previous) {
        this.previous = previous;
    }

    public int getNext() {
        return next;
    }

    public void setNext(int next) {
        this.next = next;
    }

    public int getStatusCount() {
        return statusCount;
    }

    public void setStatusCount(int statusCount) {
        this.statusCount = statusCount;
    }

    public List<MonthSaving> getData() {
        return data;
    }

    public void setData(List<MonthSaving> data) {
        this.data = data;
    }
}
