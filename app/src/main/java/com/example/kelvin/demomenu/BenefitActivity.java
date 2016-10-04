package com.example.kelvin.demomenu;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.kelvin.demomenu.adapters.ConsumptionsAdapter;
import com.example.kelvin.demomenu.adapters.MenuListAdapter;
import com.example.kelvin.demomenu.adapters.RelatedBenefitAdapter;
import com.example.kelvin.demomenu.entities.AnualSavingResponse;
import com.example.kelvin.demomenu.entities.Benefit;
import com.example.kelvin.demomenu.entities.BenefitResponse;
import com.example.kelvin.demomenu.entities.Category;
import com.example.kelvin.demomenu.entities.CategoryResponse;
import com.example.kelvin.demomenu.entities.MonthSaving;
import com.example.kelvin.demomenu.entities.MonthSavingResponse;
import com.example.kelvin.demomenu.entities.RelatedBenefitResponsse;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by kelvin on 29/09/16.
 */

public class BenefitActivity extends AppCompatActivity {

    private static final String TAG = "BenefitActivity";
    private PopupWindow popupFavorite;
    private MonthSavingResponse monthSavingResponse;
    private View menuSavingHead;
    private PopupWindow popupDetails;
    private int currentMonth = 5;
    public static Typeface typeFace;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        typeFace = Typeface.createFromAsset(getAssets(), "OpenSansRegular.ttf");

        setUpToolbar();

        getBenefitFromServer();
        getRelatedBenefitsFromServer();

        setOnClickListenerToTxt();
        inflatePopupFavorite();

        getCategoriesFromServer();
        getConsumosPorMesFromServer(currentMonth);
        getTotalAnnualSavingsFromServer();

        setFooterMenuOnItemClickListener();

        if (getScreenWidth() <= 720)
            setMenuTextBottomGone();
        else
            setMenuTextBottomFonts();

        inflatePopupDetails();


        setUpPopupViews();
    }

    private void inflatePopupDetails() {

        View popupLayout = getLayoutInflater().inflate(R.layout.details_consumptions, null);
        popupDetails = new PopupWindow(popupLayout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, false);
        popupDetails.setBackgroundDrawable(new ColorDrawable());
        popupDetails.setOutsideTouchable(true);
        popupDetails.setFocusable(true);
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

                if (image.getDrawable().getConstantState() == ContextCompat.getDrawable(getApplicationContext(), R.drawable.favorite).getConstantState()) {

                    image.setImageResource(R.drawable.favorite_on);
                } else
                    image.setImageResource(R.drawable.favorite);

                if (popupFavorite.isShowing())
                    popupFavorite.dismiss();
                else
                    popupFavorite.showAsDropDown(findViewById(R.id.igvFavorite));
            }
        });

        /*ImageView igvShare = (ImageView) view.findViewById(R.id.igvShare);
        igvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message = "Text I want to share.";
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, message);

                startActivity(Intent.createChooser(share, "Title of the dialog the system will open"));
            }
        });*/

        ImageView igvMenu = (ImageView) findViewById(R.id.igvMenu);
        igvMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
                mDrawerLayout.openDrawer(GravityCompat.START);
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

    private void setMenuTextBottomGone() {

        int[] data = new int[]{R.id.txtFavourite, R.id.txtContact, R.id.txtSearch, R.id.txtSettings};
        for (int id : data) {

            TextView tv = (TextView) findViewById(id);
            tv.setVisibility(View.GONE);
        }
    }

    private void setMenuTextBottomFonts() {

        int[] data = new int[]{R.id.txtFavourite, R.id.txtContact, R.id.txtSearch, R.id.txtSettings};

        for (int id : data) {
            TextView text = (TextView) findViewById(id);
            if (text != null)
                text.setTypeface(typeFace);
        }
    }

    private void setFooterMenuOnItemClickListener() {

        int[] ids = new int[]{R.id.footerLayoutContact, R.id.footerLayoutFavourite, R.id.footerLayoutSearch, R.id.footerLayoutSettings};

        for (int id : ids) {

            View view = findViewById(id);

            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                   /* if (selectedFooterView != null)
                        selectedFooterView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));

                    v.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBlackTransparent));
                    selectedFooterView = v;*/

                    if (v.getId() == R.id.footerLayoutSettings) {
                        Intent intent = new Intent(BenefitActivity.this, SettingsActivity.class);
                        startActivity(intent);
                    }
                }
            });
        }
    }

    /*private void setFooterImageOnItemClickListener() {

        int[] ids = new int[]{R.id.imgFavourite, R.id.imgContact, R.id.imgSearch, R.id.imgSettings};

        for (int id : ids) {

            View view = findViewById(id);

            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (selectedFooterView != null)
                        selectedFooterView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));

                    v.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBlackTransparent));
                    selectedFooterView = v;

                }
            });
        }
    }*/

    private int getScreenWidth() {

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;

        return width;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (popupDetails.isShowing())
            popupDetails.dismiss();
    }

    private void getCategoriesFromServer() {

        String url = Constants.serviceUrl + "listTipobeneficio?session=8d1f0c0c975be23cd963ce24b3ffddc7";

        ClubRequestManager.getInstance(this).performJsonRequest(Request.Method.GET, url, null,
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

                            CategoryResponse categoryResponse = new CategoryResponse(response);
                            setUpMenuList(categoryResponse.getData());
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        //showError(VolleyErrorHelper.getMessage(error, CercaDeMiActivity.this));
                        Log.i(TAG, "message error " + error.getLocalizedMessage());
                    }
                });
    }

    private void setUpMenuList(List<Category> categories) {

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.drawerRecyclerView);

        MenuListAdapter adapter = new MenuListAdapter(categories);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        mRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new MenuListAdapter.OnItemSelecteListener() {

            View selectedView;

            @Override
            public void onItemSelected(View v, int position) {

                if (selectedView != null)
                    selectedView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));

                /*if (selectedFooterView != null)
                    selectedFooterView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                */
                v.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.gray));

                selectedView = v;
            }
        });
    }


    private void getConsumosPorMesFromServer(int month) {

        String url = Constants.serviceUrl + "consumoxmes";
        String fullUrl = url + "?session=8d1f0c0c975be23cd963ce24b3ffddc7" + "&mes=" + month;//getCurrentMonth();

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

                            monthSavingResponse = new MonthSavingResponse(response);

                            addHeaderToMenu();

                            setUpPopupRecyclerView();
                            Log.i(TAG, "no problems");
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

    private void addHeaderToMenu() {

        if (menuSavingHead != null)
            return;

        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.menu_header);
        int status = monthSavingResponse.getStatusCount();

       /* if (No ha iniciado sesion){

            menuSavingHead = getLayoutInflater().inflate(R.layout.menu_head_subscribe, null);
        }else{

            if (Selecciono no en ajustes){

                menuSavingHead = getLayoutInflater().inflate(R.layout.menu_head_activa_ahorro, null);

            }else{*/

        if (status == 0 || status == 3) {

            menuSavingHead = getLayoutInflater().inflate(R.layout.menu_head_empieza, null);
        } else if (status == 2) {

            menuSavingHead = getLayoutInflater().inflate(R.layout.menu_head_empieza, null);

            menuSavingHead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.i(TAG, "menu click");

                    if (popupDetails != null && popupDetails.isShowing())
                        popupDetails.dismiss();
                    else
                        popupDetails.showAsDropDown(linearLayout);
                }
            });
        } else if (status == 1) {

            int retval = Double.compare(getTotalMonthSavings(), 35);

            if (retval > 0) {

                menuSavingHead = getLayoutInflater().inflate(R.layout.menu_head_ahorro, null);
            } else if (retval < 0) {

                menuSavingHead = getLayoutInflater().inflate(R.layout.menu_head_sigue_ahorrando, null);
            } else {

                menuSavingHead = getLayoutInflater().inflate(R.layout.menu_head_ahorro, null);
            }

            menuSavingHead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.i(TAG, "menu click");

                    if (popupDetails != null && popupDetails.isShowing())
                        popupDetails.dismiss();
                    else
                        popupDetails.showAsDropDown(linearLayout);
                }
            });

        } else {
            Log.i(TAG, "error in monthSavingResponse");
        }
        /*    }
        }*/

        if (menuSavingHead != null) {

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

            linearLayout.addView(menuSavingHead, params);

            TextView textView = (TextView) menuSavingHead.findViewById(R.id.txtTotalSaving);

            if (textView != null)
                textView.setText(getResources().getString(R.string.money_format, getTotalMonthSavings()));
        }
    }

    private void setUpPopupViews() {

        View popup = popupDetails.getContentView();

        final Spinner spinner = (Spinner) popup.findViewById(R.id.spinner);

        spinner.setSelection(currentMonth - 1, false);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Log.i(TAG, "on item selected " + position);
                currentMonth = position + 1;
                getConsumosPorMesFromServer(currentMonth);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final TextView nextTxt = (TextView) popup.findViewById(R.id.txtNext);
        final TextView backTxt = (TextView) popup.findViewById(R.id.txtBack);

        nextTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                currentMonth++;

                if (currentMonth == 12)
                    nextTxt.setVisibility(View.INVISIBLE);

                if (currentMonth == 2)
                    backTxt.setVisibility(View.VISIBLE);

                getConsumosPorMesFromServer(currentMonth);
                spinner.setSelection(currentMonth - 1, true);
                Log.i(TAG, "current month " + currentMonth);
            }
        });

        backTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                currentMonth--;

                if (currentMonth == 1)
                    backTxt.setVisibility(View.INVISIBLE);

                if (currentMonth == 11) {
                    nextTxt.setVisibility(View.VISIBLE);
                }

                getConsumosPorMesFromServer(currentMonth);
                spinner.setSelection(currentMonth - 1, true);
                Log.i(TAG, "current month " + currentMonth);
            }
        });
    }

    private void setUpPopupRecyclerView() {

        RecyclerView recyclerView = (RecyclerView) popupDetails.getContentView().findViewById(R.id.recyclerDetails);

        if (monthSavingResponse.getData().size() == 0) {

            recyclerView.setVisibility(View.GONE);
            setBottomButtonsVisibility(View.GONE);
            popupDetails.getContentView().findViewById(R.id.txtNoConsumoInfo).setVisibility(View.VISIBLE);
        } else {

            setBottomButtonsVisibility(View.VISIBLE);
            popupDetails.getContentView().findViewById(R.id.txtNoConsumoInfo).setVisibility(View.GONE);

            if (!recyclerView.isShown())
                recyclerView.setVisibility(View.VISIBLE);

            ConsumptionsAdapter adapter = new ConsumptionsAdapter(monthSavingResponse.getData());
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    private void setBottomButtonsVisibility(int visibility) {

        popupDetails.getContentView().findViewById(R.id.txtNext).setVisibility(visibility);
        popupDetails.getContentView().findViewById(R.id.txtBack).setVisibility(visibility);
    }

    private int getCurrentMonth() {

        Calendar c = Calendar.getInstance();

        return (c.get(Calendar.MONTH) + 1);
    }

    private double getTotalMonthSavings() {

        List<MonthSaving> consumos = monthSavingResponse.getData();

        double totalSavings = 0;

        for (MonthSaving c : consumos) {
            totalSavings += c.getMontoDescontado();
        }

        return totalSavings;
    }

    private void getTotalAnnualSavingsFromServer() {

        String url = Constants.serviceUrl + "totalAhorroAnual";
        String fullUrl = url + "?session=8d1f0c0c975be23cd963ce24b3ffddc7";

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

                            Log.i(TAG, "no problems annual");
                            AnualSavingResponse anual = new AnualSavingResponse(response);
                            double totalAnnualSaving = anual.getData().getAhorroAnual();
                            setAnnualSavingText(totalAnnualSaving);
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

    private void setAnnualSavingText(double savings) {

        TextView annualSaving = (TextView) popupDetails.getContentView().findViewById(R.id.annualSaving);
        annualSaving.setText(getResources().getString(R.string.money_format, savings));
    }
}
