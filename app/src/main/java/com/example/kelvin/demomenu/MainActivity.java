package com.example.kelvin.demomenu;

import android.content.DialogInterface;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

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
    private double currentMonthSavings, currentYearSavings;
    private Toolbar toolbar;
    private MonthSavingResponse monthSavingResponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //setupDrawerItemList();

        /*RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.drawerRecyclerView);

        MenuAdapter adapter = new MenuAdapter(mDrawerItemList);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);*/

        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                //TODO Add some action here
                //Executed when drawer closes
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //TODO Add some action here
                //executes when drawer open
                //drawerView.fi
            }
        };

        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        setFooterMenuOnItemClickListener();

        getCategoriesFromServer();
        getConsumosPorMesFromServer();
        getTotalAnnualSavingsFromServer();
    }

    private void setFooterMenuOnItemClickListener() {

        int[] data = new int[]{R.id.footerLayoutContact, R.id.footerLayoutFavourite, R.id.footerLayoutSearch, R.id.footerLayoutSettings};

        for (int id : data) {

            findViewById(id).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (selectedFooterView != null)
                        selectedFooterView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));

                    v.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBlackTransparent));
                    selectedFooterView = v;
                }
            });
        }
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
        mRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new MenuListAdapter.OnItemSelecteListener() {

            View selectedView;

            @Override
            public void onItemSelected(View v, int position) {

                if (selectedView != null)
                    selectedView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));

                if (selectedFooterView != null)
                    selectedFooterView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));

                v.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBlackTransparent));

                selectedView = v;
            }
        });
    }


    private void getConsumosPorMesFromServer() {

        String url = Constants.serviceUrl + "consumoxmes";
        String fullUrl = url + "?session=d480d0d9db8ef474ac54a0af0ebe71a1" + "&mes=" + 5;//getCurrentMonth();

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
                            currentMonthSavings = getTotalMonthSavings(monthSavingResponse);

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

    private void addHeaderToolbar() {

        int retval = Double.compare(currentYearSavings, 0);

        View head = null;

        if (retval > 0) {

            int retval1 = Double.compare(currentMonthSavings, 0);

            if (retval1 > 0) {

                int retval2 = Double.compare(currentMonthSavings, 35);

                if (retval2 > 0) {

                    head = getLayoutInflater().inflate(R.layout.toolbar_head_ahorro, null);
                } else if (retval2 < 0) {

                    head = getLayoutInflater().inflate(R.layout.toolbar_head_sigue_ahorrando, null);
                } else {

                    head = getLayoutInflater().inflate(R.layout.toolbar_head_ahorro, null);
                }


            } else if (retval1 < 0) {

            } else {

                head = getLayoutInflater().inflate(R.layout.toolbar_head_empieza, null);
            }

        } else if (retval < 0) {


        } else {

            head = getLayoutInflater().inflate(R.layout.toolbar_head_empieza, null);
        }

        if (head != null) {
            Toolbar.LayoutParams params = new Toolbar.LayoutParams(
                    Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT);

            head.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.i(TAG, "on click");
                    showPopup();
                }
            });

            toolbar.addView(head, params);
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

    private void showPopup() {

        View popupLayout = getLayoutInflater().inflate(R.layout.details_consumptions, null);
        PopupWindow popup = new PopupWindow(popupLayout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, false);

       /* PopupWindow popup = new PopupWindow(MainActivity.this.getParent());
        View popupLayout = getLayoutInflater().inflate(R.layout.details_consumptions, null);
        popup.setContentView(popupLayout);
        //dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);

        //popup.setWindowLayoutType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        // Set content width and height
        popup.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        popup.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);*/
        // Closes the popup window when touch outside of it - when looses focus
        setUpViews(popupLayout);

        //popup.setOutsideTouchable(true);
        //popup.setFocusable(true);
        //popup.setBackgroundDrawable(new ColorDrawable());

        popup.showAsDropDown(toolbar);
    }

    private void setUpViews(View popup) {

        RecyclerView recyclerView = (RecyclerView) popup.findViewById(R.id.recyclerDetails);

        if (monthSavingResponse.getData().size() == 0) {

            recyclerView.setVisibility(View.GONE);
        } else {

            ConsumptionsAdapter adapter = new ConsumptionsAdapter(monthSavingResponse.getData());
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        }
    }

    private int getCurrentMonth() {

        Calendar c = Calendar.getInstance();

        return (c.get(Calendar.MONTH) + 1);
    }

    private double getTotalMonthSavings(MonthSavingResponse monthSavingResponse) {

        List<MonthSaving> consumos = monthSavingResponse.getData();

        double totalSavings = 0;

        for (MonthSaving c : consumos) {
            totalSavings += c.getMontoDescontado();
        }

        return totalSavings;
    }

    private void getTotalAnnualSavingsFromServer() {

        String url = Constants.serviceUrl + "totalAhorroAnual";
        String fullUrl = url + "?session=c37c7c360dfe687c058120640592063f";

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
                            currentYearSavings = anual.getData().getAhorroAnual();

                            addHeaderToolbar();
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
}
