package com.example.kelvin.demomenu.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kelvin on 08/09/16.
 */
public class Category {

    private Integer id;

    private String nombre;

    private Object descripcion;

    private String abreviado;

    private String slug;

    private String icono;

    private int drawable;

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     *
     * @param nombre
     * The nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     *
     * @return
     * The descripcion
     */
    public Object getDescripcion() {
        return descripcion;
    }

    /**
     *
     * @param descripcion
     * The descripcion
     */
    public void setDescripcion(Object descripcion) {
        this.descripcion = descripcion;
    }

    /**
     *
     * @return
     * The abreviado
     */
    public String getAbreviado() {
        return abreviado;
    }

    /**
     *
     * @param abreviado
     * The abreviado
     */
    public void setAbreviado(String abreviado) {
        this.abreviado = abreviado;
    }

    /**
     *
     * @return
     * The slug
     */
    public String getSlug() {
        return slug;
    }

    /**
     *
     * @param slug
     * The slug
     */
    public void setSlug(String slug) {
        this.slug = slug;
    }

    /**
     *
     * @return
     * The icono
     */
    public String getIcono() {
        return icono;
    }

    /**
     *
     * @param icono
     * The icono
     */
    public void setIcono(String icono) {
        this.icono = icono;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }
}