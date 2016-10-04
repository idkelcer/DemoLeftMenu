package com.example.kelvin.demomenu;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.kelvin.demomenu.adapters.RelatedBenefitAdapter;
import com.example.kelvin.demomenu.entities.Benefit;
import com.example.kelvin.demomenu.entities.BenefitResponse;
import com.example.kelvin.demomenu.entities.RelatedBenefitResponsse;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kelvin on 29/09/16.
 */

public class BenefitActivity extends AppCompatActivity {

    private static final String TAG = "BenefitActivity";
    private PopupWindow popupFavorite;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_benefit);

        setUpToolbar();

        getBenefitFromServer();

        getRelatedBenefitsFromServer();

        setOnClickListenerToTxt();

        inflatePopupFavorite();
    }

    private void inflatePopupFavorite() {

        View popupLayout = getLayoutInflater().inflate(R.layout.popup_benefit_favorite, null);
        popupFavorite = new PopupWindow(popupLayout, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, false);
        popupFavorite.setOutsideTouchable(true);
        popupFavorite.setBackgroundDrawable(new ColorDrawable());
        popupFavorite.setFocusable(true);

        setUpBenefitPopup(popupLayout);
    }

    private void setOnClickListenerToTxt() {

        TextView txtContact = (TextView) findViewById(R.id.txtContact);
        txtContact.setOnClickListener(new View.OnClickListener() {

            boolean isOpen = false;

            @Override
            public void onClick(View v) {

                if (isOpen) {

                    findViewById(R.id.lrlContact).setVisibility(View.GONE);
                    isOpen = false;

                } else {

                    findViewById(R.id.lrlContact).setVisibility(View.VISIBLE);
                    isOpen = true;
                }
            }
        });

        TextView txtTerminos = (TextView) findViewById(R.id.txtTerminos);
        txtTerminos.setOnClickListener(new View.OnClickListener() {

            boolean isOpen = false;

            @Override
            public void onClick(View v) {

                if (isOpen) {

                    findViewById(R.id.txtTerminosText).setVisibility(View.GONE);
                    isOpen = false;

                } else {

                    findViewById(R.id.txtTerminosText).setVisibility(View.VISIBLE);
                    isOpen = true;
                }
            }
        });
    }

    private void setUpBenefitPopup(View view) {

        DatePicker datePicker = (DatePicker) view.findViewById(R.id.datePicker);
        colorizeDatePicker(datePicker);

        TextView txtAdd = (TextView) view.findViewById(R.id.txtAddBen);
        txtAdd.setTypeface(MainActivity.typeFace);

        TextView txtRec = (TextView) view.findViewById(R.id.txtRecord);
        txtRec.setTypeface(MainActivity.typeFace);

        Button btnSave = (Button) view.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showAlertDialog();
            }
        });

        Button btnCancel = (Button) view.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popupFavorite.dismiss();
            }
        });
    }

    private void showAlertDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialogStyle);
        //builder.setTitle("AppCompatDialog");
        builder.setMessage("Beneficio añadido a \"Recordatorios\"");
        builder.setPositiveButton("Ver Recordatorios", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void getBenefitFromServer() {

        String url = Constants.serviceUrl + "detalleBeneficio";
        String fullUrl = url + "?idBeneficio=3027&session=8d1f0c0c975be23cd963ce24b3ffddc7";

        ClubRequestManager.getInstance(this).performJsonRequest(Request.Method.GET, fullUrl, null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d(TAG, response.toString());

                        if (response.has("error")) {

                            try {
                                showError(response.getString("error"));
                            } catch (JSONException e) {
                                Log.i(TAG, e.getLocalizedMessage());
                            }
                        } else {

                            BenefitResponse benefitResponse = new BenefitResponse(response);
                            Benefit benefit = benefitResponse.getData();
                            setUpActivityViews(benefit);

                            Log.i(TAG, benefitResponse.getData().getWeb());
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        //showError(VolleyErrorHelper.getMessage(error, MainActivity.this));
                        Log.i(TAG, "message error " + error.getLocalizedMessage());
                    }
                });

    }

    private void setUpActivityViews(Benefit benefit) {

        loadImage(benefit);

        TextView txtName = (TextView) findViewById(R.id.txtTitle);
        txtName.setText(benefit.getTitulo());

        TextView txtDiscount = (TextView) findViewById(R.id.txtDiscount);
        txtDiscount.setText("50%");

        TextView txtInfo = (TextView) findViewById(R.id.txtDescription);
        txtInfo.setText(benefit.getDescripcion());

        TextView txtUbication = (TextView) findViewById(R.id.txtUbication);
        txtUbication.setText(benefit.getDireccion());

        TextView txtCalendar = (TextView) findViewById(R.id.txtCalendar);
        txtCalendar.setText(benefit.getCuando());

        TextView txtQuestion = (TextView) findViewById(R.id.txtQuestion);
        txtQuestion.setText(benefit.getComo());

        TextView txtPhone = (TextView) findViewById(R.id.txtPhone);
        txtPhone.setText(benefit.getTelefonoInfo());

        TextView txtEmail = (TextView) findViewById(R.id.txtEmail);
        txtEmail.setText(benefit.getEmailInfo());

        TextView txtWeb = (TextView) findViewById(R.id.txtWeb);
        txtWeb.setText(benefit.getWeb());

        TextView txtTerminosText = (TextView) findViewById(R.id.txtTerminosText);
        txtTerminosText.setText(benefit.getTerminosCondicionesWeb());
    }

    private void loadImage(Benefit benefit) {

       /* ImageLoader imageLoader = ClubRequestManager.getInstance(getApplicationContext()).getImageLoader();

        imageLoader.get(benefit.getPathLogo(), new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                Bitmap bitmap = response.getBitmap();
                if (bitmap != null) {

                    ImageView imageView = (ImageView) findViewById(R.id.imgMain);
                    imageView.setImageBitmap(bitmap);
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });*/

        ImageLoader imageLoader = ClubRequestManager.getInstance(getApplicationContext()).getImageLoader();
        NetworkImageView imageView = (NetworkImageView) findViewById(R.id.imgMain);
        imageView.setImageUrl(benefit.getPathLogo(), imageLoader);
    }

    private void setUpToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        Toolbar.LayoutParams params = new Toolbar.LayoutParams(Toolbar.LayoutParams.MATCH_PARENT,
                Toolbar.LayoutParams.MATCH_PARENT);
        View view = getLayoutInflater().inflate(R.layout.benefit_custom_toolbar, null);

        toolbar.addView(view, params);

        ImageView igvFavorite = (ImageView) view.findViewById(R.id.igvFavorite);
        igvFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImageView image = (ImageView) v;

                if (image.getDrawable().getConstantState() == ContextCompat.getDrawable(getApplicationContext(), R.drawable.favorite).getConstantState()){

                    image.setImageResource(R.drawable.favorite_on);
                }else
                    image.setImageResource(R.drawable.favorite);

                if (popupFavorite.isShowing())
                    popupFavorite.dismiss();
                else
                    popupFavorite.showAsDropDown(findViewById(R.id.igvFavorite));
            }
        });

        ImageView igvShare = (ImageView) view.findViewById(R.id.igvShare);
        igvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message = "Text I want to share.";
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, message);

                startActivity(Intent.createChooser(share, "Title of the dialog the system will open"));
            }
        });
    }

    private void showError(String errorMessage) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(errorMessage);
        alertDialog.setPositiveButton("Reintentar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();
    }

    private void getRelatedBenefitsFromServer() {

        String url = Constants.serviceUrl + "beneficiosRelacionados";
        String fullUrl = url + "?idBeneficio=2912&tipoBeneficioId=2&session=8d1f0c0c975be23cd963ce24b3ffddc7";

        ClubRequestManager.getInstance(this).performJsonRequest(Request.Method.GET, fullUrl, null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d(TAG, response.toString());

                        if (response.has("error")) {

                            try {
                                showError(response.getString("error"));
                            } catch (JSONException e) {
                                Log.i(TAG, e.getLocalizedMessage());
                            }
                        } else {

                            RelatedBenefitResponsse relatedBenefitResponsse = new RelatedBenefitResponsse(response);

                            List<Benefit> benefits = relatedBenefitResponsse.getData();

                            setUpViewPager(fillBenefitList(benefits));

                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        //showError(VolleyErrorHelper.getMessage(error, MainActivity.this));
                        Log.i(TAG, "message error " + error.getLocalizedMessage());
                    }
                });
    }

    private List<Fragment> fillBenefitList(List<Benefit> benefits) {

        List<Fragment> fList = new ArrayList<>();

        for (int i = 0; i < benefits.size(); i++) {

            fList.add(RelatedBenefitFragment.newInstance(benefits.get(i)));
        }

        return fList;
    }

    private void setUpViewPager(List<Fragment> fragments) {

        RelatedBenefitAdapter relatedBenefitAdapter = new RelatedBenefitAdapter(getSupportFragmentManager(), fragments);
        ViewPager pager = (ViewPager) findViewById(R.id.vwpRelatedBenefit);
        pager.setAdapter(relatedBenefitAdapter);

        relatedBenefitAdapter.notifyDataSetChanged();

        if (relatedBenefitAdapter.getCount() != 1)
            pager.setPageMargin(20);
    }

    public void colorizeDatePicker(DatePicker datePicker) {
        Resources system = Resources.getSystem();
        int dayId = system.getIdentifier("day", "id", "android");
        int monthId = system.getIdentifier("month", "id", "android");
        int yearId = system.getIdentifier("year", "id", "android");

        NumberPicker dayPicker = (NumberPicker) datePicker.findViewById(dayId);
        NumberPicker monthPicker = (NumberPicker) datePicker.findViewById(monthId);
        NumberPicker yearPicker = (NumberPicker) datePicker.findViewById(yearId);

        setDividerColor(dayPicker);
        setDividerColor(monthPicker);
        setDividerColor(yearPicker);
    }

    private void setDividerColor(NumberPicker picker) {
        if (picker == null)
            return;

        final int count = picker.getChildCount();
        for (int i = 0; i < count; i++) {
            try {
                Field dividerField = picker.getClass().getDeclaredField("mSelectionDivider");
                dividerField.setAccessible(true);
                ColorDrawable colorDrawable = new ColorDrawable(ContextCompat.getColor(getApplicationContext(), R.color.colorDatePickerDivider));
                dividerField.set(picker, colorDrawable);
                picker.invalidate();
            } catch (Exception e) {
                Log.w("setDividerColor", e);
            }
        }
    }
}
