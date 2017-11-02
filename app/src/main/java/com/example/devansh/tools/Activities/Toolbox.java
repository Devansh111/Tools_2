package com.example.devansh.tools.Activities;

import android.app.FragmentManager;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devansh.tools.Adapters.ViewPagerAdapter;
import com.example.devansh.tools.Fragments.toolbox_add_tool;
import com.example.devansh.tools.R;
import com.example.devansh.tools.Tabs.home_tab_2;
import com.example.devansh.tools.Tabs.toolbox_tab_1;
import com.example.devansh.tools.Tabs.toolbox_tab_2;
import com.example.devansh.tools.Utility.cons;
import com.example.devansh.tools.Utility.database_utils;

import java.util.ArrayList;

public class Toolbox extends AppCompatActivity {

    public FragmentManager fragmentManager;
    public toolbox_add_tool obj;
    public FloatingActionButton add_tool;
    public TabLayout toolbox_tab;
    public ViewPager viewPager;
    public TextView toolbox_empty,toolbox_add_tools;
    public ImageView toolbox_img;
    public String make_name,price;
    public ArrayList<String> make_name_list=new ArrayList<String>(),price_list=new ArrayList<String>(),model_name_list = new ArrayList<>();
    public ArrayList<String> uploaded_image = new ArrayList<String>(),type_list=new ArrayList<>(),cond_list=new ArrayList<>();
    public ArrayList<String> specs_list = new ArrayList<>(),deposit_list = new ArrayList<>();
    public Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbox);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Toolbox");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        add_tool = (FloatingActionButton) findViewById(R.id.toolbox_add_tool);
        add_tool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cons.sharedpreferences.getString("state", "").toString().equals("")){
                    Toast.makeText(Toolbox.this,"Please log in to continue",Toast.LENGTH_SHORT).show();
                    intent = new Intent(Toolbox.this,home.class);
                    startActivity(intent);
                }
                else {
                    fragmentManager = getFragmentManager();
                    obj = new toolbox_add_tool();
                    fragmentManager.beginTransaction().setCustomAnimations(R.animator.slide_up, R.animator.slide_out).addToBackStack(null)
                            .add(R.id.toolbox_add_tools_fragment, obj).commit();
                    add_tool.setVisibility(View.GONE);
                }
            }
        });

        toolbox_empty = (TextView) findViewById(R.id.toolbox_empty);
        toolbox_add_tools = (TextView) findViewById(R.id.toolbox_add_tools);
        toolbox_img = (ImageView) findViewById(R.id.toolbox_img);

        toolbox_tab = (TabLayout) findViewById(R.id.toolbox_tab);
        viewPager = (ViewPager) findViewById(R.id.toolbox_tab_view_pager);

        checkTools(toolbox_tab,viewPager);
    }

    @Override
    public void onBackPressed() {
        add_tool.setVisibility(View.VISIBLE);
        if(getFragmentManager().getBackStackEntryCount()>0)
            getFragmentManager().popBackStack();
        else
            super.onBackPressed();
    }

    public void checkTools(TabLayout tabLayout,ViewPager viewPager){

        Fragment fragment;
        String name="",model="",specs="",cond="",type="";
        String price = "",deposit="";
        String img_src="";

        init();

        try {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                name = bundle.getString("make").toString();
                price = bundle.getString("price").toString();
                img_src = bundle.getString("image").toString();
                model = bundle.getString("model").toString();
                specs = bundle.getString("specs").toString();
                cond = bundle.getString("cond").toString();
                type = bundle.getString("type").toString();
                deposit = bundle.getString("deposit").toString();
                System.out.print(name);

                if (!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(price)) {
                    tabLayout.setVisibility(View.VISIBLE);
                    viewPager.setVisibility(View.VISIBLE);
                    toolbox_empty.setVisibility(View.GONE);
                    toolbox_add_tools.setVisibility(View.GONE);
                    toolbox_img.setVisibility(View.GONE);
                }
            }

            if (!TextUtils.isEmpty(make_name)) {
                Bundle args = new Bundle();
                args.putStringArrayList("make", make_name_list);
                args.putStringArrayList("price", this.price_list);
                args.putStringArrayList("image",uploaded_image);
                args.putStringArrayList("model",model_name_list);
                args.putStringArrayList("type",type_list);
                args.putStringArrayList("cond",cond_list);
                args.putStringArrayList("specs",specs_list);
                args.putStringArrayList("deposit",deposit_list);

                fragment = new toolbox_tab_1();
                fragment.setArguments(args);
                tabLayout.setVisibility(View.VISIBLE);
                viewPager.setVisibility(View.VISIBLE);
                toolbox_empty.setVisibility(View.GONE);
                toolbox_add_tools.setVisibility(View.GONE);
                toolbox_img.setVisibility(View.GONE);

            } else {
                Bundle args = new Bundle();
                args.putString("make", name);
                args.putString("price", price);
                args.putString("image",img_src);
                args.putString("model",model);
                args.putString("type",type);
                args.putString("cond",cond);
                args.putString("specs",specs);
                args.putString("deposit",deposit);

                fragment = new toolbox_tab_1();
                fragment.setArguments(args);

            }

            ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
            adapter.addFragment(fragment, "My Tools");
            adapter.addFragment(new toolbox_tab_2(), "My Rentals");
            viewPager.setAdapter(adapter);
            toolbox_tab.setupWithViewPager(viewPager);

        }catch (Exception e ){
            Toast.makeText(this,"Some error occured",Toast.LENGTH_SHORT);
        }
    }

    public void init(){

        Cursor cursor= home.db.rawQuery("SELECT * FROM "+ database_utils.toolbox_table_name,null);
        if(cursor!=null) {
            cursor.moveToFirst();
        }

        if(cursor.getCount()>0) {
            do{

                make_name = cursor.getString(cursor.getColumnIndex(database_utils.toolbox_col_name));
                model_name_list.add(cursor.getString(cursor.getColumnIndex(database_utils.toolbox_col_model)));
                price = cursor.getString(cursor.getColumnIndex(database_utils.toolbox_col_price));
                make_name_list.add(make_name);
                price_list.add(price);
                uploaded_image.add(cursor.getString(cursor.getColumnIndex(database_utils.toolbox_col_img)));
                type_list.add(cursor.getString(cursor.getColumnIndex(database_utils.toolbox_col_type)));
                cond_list.add(cursor.getString(cursor.getColumnIndex(database_utils.toolbox_col_cond)));
                deposit_list.add(cursor.getString(cursor.getColumnIndex(database_utils.toolbox_col_deposit)));
                specs_list.add(cursor.getString(cursor.getColumnIndex(database_utils.toolbox_col_specs)));

            }while (cursor.moveToNext());
        }
    }

    public void hideFb(){
        add_tool.setVisibility(View.INVISIBLE);
    }
}