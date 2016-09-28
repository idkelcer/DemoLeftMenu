package com.example.kelvin.demomenu;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.kelvin.demomenu.adapters.ConsumptionsAdapter;
import com.example.kelvin.demomenu.adapters.MenuListAdapter;
import com.example.kelvin.demomenu.entities.AnualSavingResponse;
import com.example.kelvin.demomenu.entities.Category;
import com.example.kelvin.demomenu.entities.CategoryResponse;
import com.example.kelvin.demomenu.entities.MonthSaving;
import com.example.kelvin.demomenu.entities.MonthSavingResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ArrayList<DrawerItem> mDrawerItemList;
    private View selectedFooterView;
    private MonthSavingResponse monthSavingResponse;
    private View menuSavingHead;
    private PopupWindow popupDetails;
    private int currentMonth = 5;
    public static Typeface typeFace;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        typeFace = Typeface.createFromAsset(getAssets(), "OpenSansRegular.ttf");

        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                //TODO Add main_menu_footer action here
                //Executed when drawer closes
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //TODO Add main_menu_footer action here
                //executes when drawer open
                //drawerView.fi
            }
        };


        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        getCategoriesFromServer();
        getConsumosPorMesFromServer(currentMonth);
        getTotalAnnualSavingsFromServer();

        setFooterMenuOnItemClickListener();

        /*if (findViewById(R.id.footerLayoutContact) != null)
            setFooterMenuOnItemClickListener();
        else
            setFooterImageOnItemClickListener();*/

        if (getScreenWidth() <= 720)
            setMenuTextBottomGone();
        else
            setMenuTextBottomFonts();

        View popupLayout = getLayoutInflater().inflate(R.layout.details_consumptions, null);
        popupDetails = new PopupWindow(popupLayout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, false);

        setUpPopupViews();
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
                        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
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

    private void setupDrawerItemList() {

        mDrawerItemList = new ArrayList<>();

        DrawerItem item = new DrawerItem();
        item.setIcon(R.drawable.gastronomia);
        item.setTitle("GASTRONOMÍA");
        item.setColor(ContextCompat.getColor(getApplicationContext(), R.color.orange));
        mDrawerItemList.add(item);

        item = new DrawerItem();
        item.setIcon(R.drawable.cultura_y_entretenimiento);
        item.setTitle("CUTLTURA Y ENTRETENIMIENTO");
        item.setColor(ContextCompat.getColor(getApplicationContext(), R.color.blue));
        mDrawerItemList.add(item);

        item = new DrawerItem();
        item.setIcon(R.drawable.turismo);
        item.setTitle("TURISMO");
        item.setColor(ContextCompat.getColor(getApplicationContext(), R.color.lightBlue));
        mDrawerItemList.add(item);

        item = new DrawerItem();
        item.setIcon(R.drawable.moda_y_belleza);
        item.setTitle("MODA Y BELLEZA");
        item.setColor(ContextCompat.getColor(getApplicationContext(), R.color.purple));
        mDrawerItemList.add(item);

        item = new DrawerItem();
        item.setIcon(R.drawable.productos_y_servicios);
        item.setTitle("PRODUCTOS Y SERVICIOS");
        item.setColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
        mDrawerItemList.add(item);

        item = new DrawerItem();
        item.setIcon(R.drawable.educacion);
        item.setTitle("EDUCACIÓN");
        item.setColor(ContextCompat.getColor(getApplicationContext(), R.color.gray));
        mDrawerItemList.add(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (popupDetails.isShowing())
            popupDetails.dismiss();
    }

    private void getCategoriesFromServer() {

        String url = Constants.serviceUrl + "listTipobeneficio";

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
        String fullUrl = url + "?session=d480d0d9db8ef474ac54a0af0ebe71a1" + "&mes=" + month;//getCurrentMonth();

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

        if(menuSavingHead != null)
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

    private void setUpPopupViews() {

        popupDetails.setBackgroundDrawable(new ColorDrawable());

        View popup = popupDetails.getContentView();

        popupDetails.setOutsideTouchable(true);
        popupDetails.setFocusable(true);


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
        String fullUrl = url + "?session=d480d0d9db8ef474ac54a0af0ebe71a1";

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
