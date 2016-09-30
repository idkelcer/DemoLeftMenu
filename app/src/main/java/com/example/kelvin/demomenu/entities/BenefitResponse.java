package com.example.kelvin.demomenu.entities;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kelvin on 29/09/16.
 */

public class BenefitResponse {

    private Integer status;

    private String message;

    private Benefit data = new Benefit();

    public BenefitResponse(JSONObject jsonObject){


        try {
            status = jsonObject.getInt("status");
            message = jsonObject.getString("message");

            JSONObject jsonObject1 = jsonObject.getJSONObject("data");

            data.setId(jsonObject1.getInt("id"));
            data.setCreadoPor(jsonObject1.getInt("creado_por"));
            data.setActualizadoPor(jsonObject1.getInt("actualizado_por"));
            //data.setPublicadoPor();
            //data.setRetiradoPor();
            //data.setDeBajaPor();
            data.setEstablecimientoId(jsonObject1.getInt("establecimiento_id"));
            data.setTipoBeneficioId(jsonObject1.getInt("tipo_beneficio_id"));
            data.setTitulo(jsonObject1.getString("titulo"));
            data.setDescripcion(jsonObject1.getString("descripcion"));
            data.setDescripcionCorta(jsonObject1.getString("descripcion_corta"));
            data.setValor(jsonObject1.getString("valor"));
            data.setCuando(jsonObject1.getString("cuando"));
            data.setDireccion(jsonObject1.getString("direccion"));
            data.setWeb(jsonObject1.getString("web"));
            data.setEmailInfo(jsonObject1.getString("email_info"));
            data.setTelefonoInfo(jsonObject1.getString("telefono_info"));
            data.setInformacionAdicional(jsonObject1.getString("informacion_adicional"));
            data.setPathLogo(jsonObject1.getString("path_logo"));
            data.setVideo(jsonObject1.getString("video"));
            data.setMaximoPorSubscriptor(jsonObject1.getInt("maximo_por_subscriptor"));
            data.setSinLimitePorSuscriptor(jsonObject1.getString("sin_limite_por_suscriptor"));
            data.setSinStock(jsonObject1.getString("sin_stock"));
            data.setEsDestacado(jsonObject1.getInt("es_destacado"));
            data.setEsDestacadoPrincipal(jsonObject1.getInt("es_destacado_principal"));
            data.setActivo(jsonObject1.getInt("activo"));
            data.setPublicado(jsonObject1.getInt("publicado"));
            data.setElog(jsonObject1.getInt("elog"));
            data.setGenerarCupon(jsonObject1.getInt("generar_cupon"));
            data.setVecesVisto(jsonObject1.getInt("veces_visto"));
            data.setCodigo(jsonObject1.getString("codigo"));
            data.setFechaRegistro(jsonObject1.getString("fecha_registro"));
            data.setFechaActualizacion(jsonObject1.getString("fecha_actualizacion"));
            //data.setFechaPublicacion();
            //data.setFechaRetiro();
            //data.setFechaDeBaja();
            data.setChapita(jsonObject1.getString("chapita"));
            data.setChapitaColor(jsonObject1.getString("chapita_color"));
            data.setSlug(jsonObject1.getString("slug"));
            //*****data.setNcuponesconsumidos(jsonObject1.getInt("ncuponesconsumidos"));
            //data.setDSCTOSUSCRIPTORVIP();
            data.setIframeH(jsonObject1.getInt("iframeH"));
            //data.setPdfFile();
            data.setComo(jsonObject1.getString("como"));
            data.setTipoMonedaId(jsonObject1.getInt("tipo_moneda_id"));
            data.setEsDestacadoBanner(jsonObject1.getInt("es_destacado_banner"));
            data.setDescripcionCupon(jsonObject1.getString("descripcion_cupon"));
            //data.setPdfInfoFile();
            data.setPdfInfoDescrip(jsonObject1.getString("pdf_info_descrip"));
            data.setTerminosCondicionesWeb(jsonObject1.getString("terminos_condiciones_web"));
            data.setTerminosCondicionesCupon(jsonObject1.getString("terminos_condiciones_cupon"));
            data.setTipoRedencion(jsonObject1.getString("tipo_redencion"));
            data.setAnuncianteId(jsonObject1.getInt("anunciante_id"));
            //data.setPathLogo();
            data.setTipoEdad(jsonObject1.getString("tipo_edad"));
            data.setTags(jsonObject1.getString("tags"));
            data.setVisualizar(jsonObject1.getInt("visualizar"));
            data.setGeolocalizacion(jsonObject1.getInt("geolocalizacion"));
            data.setVisa(jsonObject1.getInt("visa"));
            data.setDescuentoAdicional(jsonObject1.getInt("descuento_adicional"));
            data.setComentario(jsonObject1.getInt("comentario"));
            data.setValoracionPromedio(jsonObject1.getInt("valoracion_promedio"));
            data.setIndPdfAJpg(jsonObject1.getInt("ind_pdf_a_jpg"));
            data.setCatNombre(jsonObject1.getString("cat_nombre"));
            data.setCatSlug(jsonObject1.getString("cat_slug"));
            data.setFechaInicioVigencia(jsonObject1.getString("fecha_inicio_vigencia"));
            data.setFechaFinVigencia(jsonObject1.getString("fecha_fin_vigencia"));
            data.setStockActual(jsonObject1.getInt("stock_actual"));
            data.setStock(jsonObject1.getInt("stock"));
            data.setTbenefSlug(jsonObject1.getString("tbenef_slug"));
            data.setTbenefId(jsonObject1.getInt("tbenef_id"));
            data.setAbreviado(jsonObject1.getString("abreviado"));
            data.setEstNombre(jsonObject1.getString("est_nombre"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public Integer getStatus() {
        return status;
    }


    public void setStatus(Integer status) {
        this.status = status;
    }


    public String getMessage() {
        return message;
    }


    public void setMessage(String message) {
        this.message = message;
    }


    public Benefit getData() {
        return data;
    }


    public void setData(Benefit data) {
        this.data = data;
    }
}