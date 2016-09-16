package com.example.kelvin.demomenu;

import android.content.Context;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Developer1 on 1/09/16.
 */
public class EditTextLayout {

    int icon;
    String hint;
    Context context;
    View view;
    int type;
    boolean enabled;
    TextView line;
    public EditTextLayout(int icon, String hint, Context context, int type) {
        this.icon = icon;
        this.hint = hint;
        this.context = context;
        this.type=type;
        this.enabled=true;
    }
    public EditTextLayout(int icon, String hint, Context context, int type, boolean enabled) {
        this.icon = icon;
        this.hint = hint;
        this.context = context;
        this.type=type;
        this.enabled=enabled;
    }
    public View CreateLayout() {
        view = (View) LayoutInflater.from(context).inflate(R.layout.adapter_fields, null);
        final EditText texto = (EditText) view.findViewById(R.id.texto);
              ImageView icon=(ImageView)view.findViewById(R.id.icono);
              TextView linea=(TextView)view.findViewById(R.id.LineField);
              icon.setBackgroundResource(this.icon);
              texto.setHint(this.hint);
              texto.setEnabled(this.enabled);
              texto.setTextColor(this.context.getResources().getColor(this.enabled?R.color.amarillo_letras:R.color.gris_letras));
              //texto.setTypeface(SplashActivity.myFont);
        linea.setBackgroundColor(this.context.getResources().getColor(this.enabled?R.color.amarillo_letras:R.color.gris_letras));
              AgregarInputType(texto,this.type);
        return view;
    }
    public void AgregarInputType(EditText edt, int type){
        switch(type){
            case 1://Numeros
                edt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER);
                break;
            case 2://AlfaNumerico
                break;
            case 3://Email
                edt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                break;
            case 4://Telefono
                edt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_PHONE);
                break;
            case 5://Letras
                edt.setInputType(InputType.TYPE_CLASS_TEXT);
                break;
            case 6://Clave
                edt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                break;
        }
    }
    public String ObtenerDocumento(){
        return ((EditText)view.findViewById(R.id.texto)).getText().toString();
    }
    public void SetearDocumento(String cadena){
        ((EditText)view.findViewById(R.id.texto)).setText(cadena);
    }
}

