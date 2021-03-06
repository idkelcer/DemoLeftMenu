package com.example.kelvin.demomenu.entities;

/**
 * Created by kelvin on 19/09/16.
 */
public class MonthSaving {

    private int tipoEstablecimientoId;
    private String nombre;
    private String fechaConsumo;
    private double montoDescontado;
    private String iconoUrl;

    public int getTipoEstablecimientoId() {
        return tipoEstablecimientoId;
    }

    public void setTipoEstablecimientoId(int tipoEstablecimientoId) {
        this.tipoEstablecimientoId = tipoEstablecimientoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaConsumo() {
        return fechaConsumo;
    }

    public void setFechaConsumo(String fechaConsumo) {
        this.fechaConsumo = fechaConsumo;
    }

    public double getMontoDescontado() {
        return montoDescontado;
    }

    public void setMontoDescontado(double montoDescontado) {
        this.montoDescontado = montoDescontado;
    }

    public String getIconoUrl() {
        return iconoUrl;
    }

    public void setIconoUrl(String iconoUrl) {
        this.iconoUrl = iconoUrl;
    }
}
