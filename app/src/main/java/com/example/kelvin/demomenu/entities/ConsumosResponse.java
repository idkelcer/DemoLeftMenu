package com.example.kelvin.demomenu.entities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kelvin on 19/09/16.
 */
public class ConsumosResponse {

    private int status;
    private String message;
    private int previous;
    private int next;
    private int statusCount;

    private List<Consumos> data = new ArrayList<>();

    public ConsumosResponse(JSONObject jsonObject) {

        try {

            status = jsonObject.getInt("status");
            message = jsonObject.getString("message");

            JSONArray array = jsonObject.getJSONArray("data");

            for (int i = 0; i < array.length(); i++) {

                JSONObject jsonObject1 = (JSONObject) array.get(i);

                Consumos consumos = new Consumos();

                consumos.setTipoEstablecimientoId(jsonObject1.getInt("tipo_establecimiento_id"));
                consumos.setNombre(jsonObject1.getString("nombre"));
                consumos.setFechaConsumo(jsonObject1.getString("fecha_consumo"));
                consumos.setMontoDescontado(jsonObject1.getDouble("monto_descontado"));

                data.add(consumos);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public List<Consumos> getData() {
        return data;
    }

    public void setData(List<Consumos> data) {
        this.data = data;
    }
}
