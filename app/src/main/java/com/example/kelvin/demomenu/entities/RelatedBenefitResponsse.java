package com.example.kelvin.demomenu.entities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kelvin on 29/09/16.
 */

public class RelatedBenefitResponsse {


    private Integer status;

    private String message;

    private List<Benefit> data = new ArrayList<>();

    public RelatedBenefitResponsse(JSONObject jsonObject) {


        try {
            status = jsonObject.getInt("status");
            message = jsonObject.getString("message");

            JSONArray array = jsonObject.getJSONArray("data");

            for (int i = 0; i < array.length(); i++) {

                JSONObject jsonObject1 = (JSONObject) array.get(i);

                Benefit benefit = new Benefit();

                benefit.setId(jsonObject1.getInt("id"));
                benefit.setCreadoPor(jsonObject1.getInt("creado_por"));
                benefit.setActualizadoPor(jsonObject1.getInt("actualizado_por"));
                //data.setPublicadoPor();
                //data.setRetiradoPor();
                //data.setDeBajaPor();
                benefit.setEstablecimientoId(jsonObject1.getInt("establecimiento_id"));
                benefit.setTipoBeneficioId(jsonObject1.getInt("tipo_beneficio_id"));
                benefit.setTitulo(jsonObject1.getString("titulo"));
                benefit.setDescripcion(jsonObject1.getString("descripcion"));
                benefit.setDescripcionCorta(jsonObject1.getString("descripcion_corta"));
                benefit.setValor(jsonObject1.getString("valor"));
                benefit.setCuando(jsonObject1.getString("cuando"));
                benefit.setDireccion(jsonObject1.getString("direccion"));
                benefit.setWeb(jsonObject1.getString("web"));
                benefit.setEmailInfo(jsonObject1.getString("email_info"));
                benefit.setTelefonoInfo(jsonObject1.getString("telefono_info"));
                benefit.setInformacionAdicional(jsonObject1.getString("informacion_adicional"));
                benefit.setPathLogo(jsonObject1.getString("path_logo"));
                benefit.setVideo(jsonObject1.getString("video"));
                benefit.setMaximoPorSubscriptor(jsonObject1.getInt("maximo_por_subscriptor"));
                benefit.setSinLimitePorSuscriptor(jsonObject1.getString("sin_limite_por_suscriptor"));
                benefit.setSinStock(jsonObject1.getString("sin_stock"));
                benefit.setEsDestacado(jsonObject1.getInt("es_destacado"));
                benefit.setEsDestacadoPrincipal(jsonObject1.getInt("es_destacado_principal"));
                benefit.setActivo(jsonObject1.getInt("activo"));
                benefit.setPublicado(jsonObject1.getInt("publicado"));
                benefit.setElog(jsonObject1.getInt("elog"));
                benefit.setGenerarCupon(jsonObject1.getInt("generar_cupon"));
                //****benefit.setVecesVisto(jsonObject1.getInt("veces_visto"));
                benefit.setCodigo(jsonObject1.getString("codigo"));
                benefit.setFechaRegistro(jsonObject1.getString("fecha_registro"));
                benefit.setFechaActualizacion(jsonObject1.getString("fecha_actualizacion"));
                //data.setFechaPublicacion();
                //data.setFechaRetiro();
                //data.setFechaDeBaja();
                benefit.setChapita(jsonObject1.getString("chapita"));
                benefit.setChapitaColor(jsonObject1.getString("chapita_color"));
                benefit.setSlug(jsonObject1.getString("slug"));
               //*** benefit.setNcuponesconsumidos(jsonObject1.getInt("ncuponesconsumidos"));
                //data.setDSCTOSUSCRIPTORVIP();
                benefit.setIframeH(jsonObject1.getInt("iframeH"));
                //data.setPdfFile();
                benefit.setComo(jsonObject1.getString("como"));
                benefit.setTipoMonedaId(jsonObject1.getInt("tipo_moneda_id"));
                benefit.setEsDestacadoBanner(jsonObject1.getInt("es_destacado_banner"));
                benefit.setDescripcionCupon(jsonObject1.getString("descripcion_cupon"));
                //data.setPdfInfoFile();
                benefit.setPdfInfoDescrip(jsonObject1.getString("pdf_info_descrip"));
                benefit.setTerminosCondicionesWeb(jsonObject1.getString("terminos_condiciones_web"));
                benefit.setTerminosCondicionesCupon(jsonObject1.getString("terminos_condiciones_cupon"));
                benefit.setTipoRedencion(jsonObject1.getString("tipo_redencion"));
                benefit.setAnuncianteId(jsonObject1.getInt("anunciante_id"));
                //data.setPathLogo();
                benefit.setTipoEdad(jsonObject1.getString("tipo_edad"));
                benefit.setTags(jsonObject1.getString("tags"));
                benefit.setVisualizar(jsonObject1.getInt("visualizar"));
                benefit.setGeolocalizacion(jsonObject1.getInt("geolocalizacion"));
                benefit.setVisa(jsonObject1.getInt("visa"));
                benefit.setDescuentoAdicional(jsonObject1.getInt("descuento_adicional"));
                benefit.setComentario(jsonObject1.getInt("comentario"));
                benefit.setValoracionPromedio(jsonObject1.getInt("valoracion_promedio"));
                benefit.setIndPdfAJpg(jsonObject1.getInt("ind_pdf_a_jpg"));
               //*** benefit.setCatNombre(jsonObject1.getString("cat_nombre"));
                //****benefit.setCatSlug(jsonObject1.getString("cat_slug"));
                benefit.setFechaInicioVigencia(jsonObject1.getString("fecha_inicio_vigencia"));
                benefit.setFechaFinVigencia(jsonObject1.getString("fecha_fin_vigencia"));
                benefit.setStockActual(jsonObject1.getInt("stock_actual"));
                benefit.setStock(jsonObject1.getInt("stock"));
                //***benefit.setTbenefSlug(jsonObject1.getString("tbenef_slug"));
              //***  benefit.setTbenefId(jsonObject1.getInt("tbenef_id"));
                benefit.setAbreviado(jsonObject1.getString("abreviado"));
                benefit.setEstNombre(jsonObject1.getString("est_nombre"));

                data.add(benefit);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public List<Benefit> getData() {
        return data;
    }

    public void setData(List<Benefit> data) {
        this.data = data;
    }
}
