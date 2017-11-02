package com.example.devansh.tools.Activities;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devansh.tools.Adapters.ViewPagerAdapter;
import com.example.devansh.tools.Adapters.home_tab_1_lv;
import com.example.devansh.tools.Fragments.sign_in;
import com.example.devansh.tools.R;
import com.example.devansh.tools.Tabs.home_tab_1;
import com.example.devansh.tools.Tabs.home_tab_2;
import com.example.devansh.tools.Tabs.home_tab_3;
import com.example.devansh.tools.Utility.cons;
import com.example.devansh.tools.Utility.database_utils;

public class home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TabLayout home_tab;
    private ViewPager home_view_pager;
    public static SQLiteDatabase db ;
    private int tabpos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
    }

    private void init(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Setting home tabs
        home_tab = (TabLayout) findViewById(R.id.home_tab);
        home_view_pager = (ViewPager) findViewById(R.id.home_tab_view_pager);
        setupViewPager(home_view_pager);
        home_tab.setupWithViewPager(home_view_pager);

        //Login state handling
        cons.sharedpreferences = getSharedPreferences(cons.MyPREFERENCES, Context.MODE_PRIVATE);

        //Customizing Search view
        SearchView s = (SearchView) findViewById(R.id.home_search);
        EditText e = (EditText) s.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        e.setTextColor(getResources().getColor(R.color.colorBlack));
        ImageView i = (ImageView) s.findViewById(android.support.v7.appcompat.R.id.search_button);
        i.setImageResource(R.drawable.search);
        ImageView i1 = (ImageView) s.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
        i1.setImageResource(R.drawable.cancel);

        s.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if(tabpos==0){
                    if(query.equalsIgnoreCase(home_tab_1_lv.getTextviewName().getText().toString()))
                        return true;
                    else
                        return false;
                }else
                    return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(tabpos==0){
                    if(newText.equalsIgnoreCase(home_tab_1_lv.getTextviewName().getText().toString()))
                        return true;
                    else
                        return false;
                }else
                    return false;
            }
        });

        //Drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //Menu menu = (Menu) navigationView.getMenu();

        //Toolbox activity table
        db  = this.openOrCreateDatabase(database_utils.toolbox_db_name,MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS "
                +database_utils.toolbox_table_name+"( "
                +database_utils.toolbox_col_name+" VARCHAR(50),"
                +database_utils.toolbox_col_model+" VARCHAR(50),"
                +database_utils.toolbox_col_specs+" VARCHAR(50),"
                +database_utils.toolbox_col_cond+" VARCHAR(50),"
                +database_utils.toolbox_col_type+" VARCHAR(50),"
                +database_utils.toolbox_col_deposit+" VARCHAR(50), "
                +database_utils.toolbox_col_price+" VARCHAR(50), "
                +database_utils.toolbox_col_img+" VARCHAR(50));");

        //Sign in table
        db.execSQL("CREATE TABLE IF NOT EXISTS "+database_utils.signin_table_name+" ("
                +database_utils.toolbox_col_email+" VARCHAR(50),"
                +database_utils.toolbox_col_fname+" VARCHAR(50),"
                +database_utils.toolbox_col_lname+" VARCHAR(50),"
                +database_utils.toolbox_col_address+" VARCHAR(50),"
                +database_utils.toolbox_col_password+" VARCHAR(50));");
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new home_tab_1(), "Around You");
        adapter.addFragment(new home_tab_2(), "Favorites");
        adapter.addFragment(new home_tab_3(), "Categories");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Intent i = new Intent(this,Toolbox.class);
            startActivity(i);

        } else if (id == R.id.nav_slideshow) {
            Intent i = new Intent(this,messages.class);
            startActivity(i);
        } else if (id == R.id.nav_manage) {
            Intent i = new Intent(this,profile.class);
            startActivity(i);
        } else if (id == R.id.nav_logout) {
            if (cons.sharedpreferences.getString("state", "").toString().equals("")) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_down)
                        .add(R.id.home_frame, new sign_in()).addToBackStack(null).commit();
            }
            else
                Toast.makeText(this,"Already Logged In",Toast.LENGTH_SHORT).show();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}