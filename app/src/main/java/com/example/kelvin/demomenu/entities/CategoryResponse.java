package com.example.kelvin.demomenu.entities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kelvin on 13/09/16.
 */
public class CategoryResponse {


    private Integer status;

    private String message;

    private List<Category> data = new ArrayList<>();

    public CategoryResponse(JSONObject jsonObject) {

        try {

            status = jsonObject.getInt("status");
            message = jsonObject.getString("message");

            JSONArray array = jsonObject.getJSONArray("data");

            for (int i = 0; i < array.length(); i++) {

                JSONObject jsonObject1 = (JSONObject) array.get(i);

                Category category = new Category();

                category.setId(jsonObject1.getInt("id"));
                category.setNombre(jsonObject1.getString("nombre"));
                category.setDescripcion(jsonObject1.getString("descripcion"));
                category.setAbreviado(jsonObject1.getString("abreviado"));
                category.setSlug(jsonObject1.getString("slug"));
                category.setIcono(jsonObject1.getString("icono"));

                data.add(category);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public List<Category> getData() {
        return data;
    }

    public void setData(List<Category> data) {
        this.data = data;
    }
}
