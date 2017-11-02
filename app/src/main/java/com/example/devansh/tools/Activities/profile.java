package com.example.devansh.tools.Activities;

import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devansh.tools.Fragments.profile_rental_history;
import com.example.devansh.tools.R;
import com.example.devansh.tools.Utility.cons;
import com.example.devansh.tools.Utility.database_utils;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class profile extends AppCompatActivity {

    public ArrayList<String> fname_list=new ArrayList<>(),lname_list= new ArrayList<>(),email_list = new ArrayList<>();
    public String fname,lname;
    public TextView profile_name,profile_rental_history,profile_logout;
    public ImageView profile_rental_history_image,profile_logout_image;

    public FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final SharedPreferences.Editor editor = cons.sharedpreferences.edit();

        profile_rental_history = (TextView) findViewById(R.id.profile_rental_history);
        profile_rental_history_image = (ImageView) findViewById(R.id.profile_rental_history_image);
        profile_name = (TextView) findViewById(R.id.profile_name);
        profile_logout_image = (ImageView) findViewById(R.id.profile_logout_image);
        profile_logout = (TextView) findViewById(R.id.profile_logout);

        fragmentManager = getFragmentManager();

        profile_rental_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction().addToBackStack(null).add(R.id.profile_frame,new profile_rental_history()).commit();
            }
        });

        profile_rental_history_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction().addToBackStack(null).add(R.id.profile_frame,new profile_rental_history()).commit();
            }
        });

        profile_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cons.sharedpreferences.getString(cons.state_name, "").toString().equals(cons.logIn_state)) {
                    cons.sharedpreferences.edit().putString("state", "").apply();
                    Toast.makeText(profile.this, "Log out successful", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(profile.this, "You are not logged in", Toast.LENGTH_SHORT).show();
            }
        });

        //Set Profile after sign in or sign up
        setProfileData();

    }

    public void setProfileData(){
        Bundle bundle = getIntent().getExtras();
        String received_email="";

        if(bundle!=null)
            received_email = bundle.getString("email").toString();

        final Cursor cursor = home.db.rawQuery("SELECT * from "+database_utils.signin_table_name+";",null);
        if (cursor != null)
            cursor.moveToFirst();
        do {
            try {
                email_list.add(cursor.getString(cursor.getColumnIndex(database_utils.toolbox_col_email)));
                fname_list.add(cursor.getString(cursor.getColumnIndex(database_utils.toolbox_col_fname)));
                lname_list.add(cursor.getString(cursor.getColumnIndex(database_utils.toolbox_col_lname)));
            }catch (Exception e){}
        } while (cursor.moveToNext());

        if(!TextUtils.isEmpty(received_email)) {
            if (email_list.contains(received_email)) {
                fname = fname_list.get(email_list.indexOf(received_email)).toString();
                lname = lname_list.get(email_list.indexOf(received_email)).toString();
            }
        }
        else if(!TextUtils.isEmpty(cons.sharedpreferences.getString(cons.email_name,""))){
                fname = fname_list.get(email_list.indexOf(cons.sharedpreferences.getString(cons.email_name,""))).toString();
                lname = fname_list.get(email_list.indexOf(cons.sharedpreferences.getString(cons.email_name,""))).toString();
        }

        if(!TextUtils.isEmpty(fname)&&!TextUtils.isEmpty(lname))
        profile_name.setText(fname+" ");
    }

    @Override
    public void onBackPressed() {

        if(getFragmentManager().getBackStackEntryCount()>0)
            getFragmentManager().popBackStack();
        else
            super.onBackPressed();
    }
}