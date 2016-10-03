package com.example.kelvin.demomenu;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kelvin on 29/09/16.
 */

public class BenefitActivity extends AppCompatActivity {

    private static final String TAG = "BenefitActivity";
    Benefit benefit;
    private PopupWindow popupFavorite;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_benefit);

        setUpToolbar();

        /*getBenefitFromServer();

        getRelatedBenefitsFromServer();*/

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

        View popupLayout = getLayoutInflater().inflate(R.layout.popup_benefit_favorite, null);
        popupFavorite = new PopupWindow(popupLayout, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, false);
        popupFavorite.setOutsideTouchable(true);

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
                            benefit = benefitResponse.getData();
                            setUpActivityViews();

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

    private void setUpActivityViews() {

        loadImage();

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


        ViewPager viewPager = (ViewPager) findViewById(R.id.vwpRelatedBenefit);
    }

    private void loadImage() {

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

        Toolbar.LayoutParams params =  new Toolbar.LayoutParams(Toolbar.LayoutParams.MATCH_PARENT,
                                                            Toolbar.LayoutParams.MATCH_PARENT);
        View view = getLayoutInflater().inflate(R.layout.benefit_custom_toolbar, null);

        toolbar.addView(view, params);

        ImageView igvFavorite = (ImageView) view.findViewById(R.id.igvFavorite);
        igvFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupFavorite.showAsDropDown(findViewById(R.id.igvFavorite));
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
}
