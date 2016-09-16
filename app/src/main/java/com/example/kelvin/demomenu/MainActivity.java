package com.example.kelvin.demomenu;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView.LayoutManager layoutManager;
    private DrawerAdapter mDrawerAdapter;
    private RecyclerView mRecyclerView;
    private ArrayList<DrawerItem> mDrawerItemList;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        View logo = getLayoutInflater().inflate(R.layout.toolbar_ahorro_custom_view, null);
        Toolbar.LayoutParams params = new Toolbar.LayoutParams(
                Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT);
        toolbar.addView(logo, params);

        setupDrawerItemList();

        mRecyclerView = (RecyclerView) findViewById(R.id.drawerRecyclerView);

        DrawerAdapter adapter = new DrawerAdapter(mDrawerItemList);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                //TODO Add some action here
                //Executed when drawer closes

                Toast.makeText(MainActivity.this, "Closed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //TODO Add some action here
                //executes when drawer open
                Toast.makeText(MainActivity.this, "Opened", Toast.LENGTH_SHORT).show();
            }
        };

        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        adapter.setOnItemClickLister(new DrawerAdapter.OnItemSelecteListener() {
            @Override
            public void onItemSelected(View v, int position) {
                Toast.makeText(MainActivity.this, "You clicked at position: " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupDrawerItemList() {

        mDrawerItemList = new ArrayList<DrawerItem>();

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
}
