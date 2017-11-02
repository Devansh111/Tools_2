package com.example.devansh.tools.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devansh.tools.Activities.home;
import com.example.devansh.tools.Activities.profile;
import com.example.devansh.tools.R;
import com.example.devansh.tools.Utility.EncryptDecrypt;
import com.example.devansh.tools.Utility.cons;
import com.example.devansh.tools.Utility.database_utils;

import java.util.ArrayList;

/**
 * Created by Devansh on 7/19/2017.
 */

public class sign_in extends Fragment {

    public ArrayList email_list=new ArrayList();
    public ArrayList password_list = new ArrayList();
    public boolean isRegistered=false;
    public TextView sign_up_link,login;
    public EditText sign_in_email,sign_in_password;
    public Button sign_in_confirm;

    String database_email,database_pass;
    public sign_in(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_in,null);

        Toolbar toolbar =(Toolbar) view.findViewById(R.id.toolbar);
        sign_in_email = (EditText) view.findViewById(R.id.sign_in_email);
        sign_in_password = (EditText) view.findViewById(R.id.sign_in_password);
        sign_in_confirm =(Button) view.findViewById(R.id.sign_in_confirm);
        login = (TextView) view.findViewById(R.id.nav_logout);

        //Retrieving data
        final Cursor cursor = home.db.rawQuery("SELECT * from "+database_utils.signin_table_name+";",null);
        if (cursor != null)
            cursor.moveToFirst();
        do {
            try {
                database_email = cursor.getString(cursor.getColumnIndex(database_utils.toolbox_col_email));
                database_pass = cursor.getString(cursor.getColumnIndex(database_utils.toolbox_col_password));
                email_list.add(database_email);
                password_list.add(database_pass);
            }catch (Exception e){}
        } while (cursor.moveToNext());

        //Sign_in Clicked
        sign_in_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();

            }
        });

        //Toolbar
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.close);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Sign in");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        //Sign up link clicked
        final Bundle bundle = getArguments();
        sign_up_link = (TextView) view.findViewById(R.id.sign_in_linkto_signup);
        sign_up_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bundle!=null) {
                    if(bundle.getBoolean("toolbox"))
                        getFragmentManager().beginTransaction().addToBackStack(null).add(R.id.toolbox_add_tools_fragment, new sign_up()).commit();
                    else
                        getFragmentManager().beginTransaction().addToBackStack(null).add(R.id.home_frame, new sign_up()).commit();
                }
                else
                    getFragmentManager().beginTransaction().addToBackStack(null).add(R.id.home_frame, new sign_up()).commit();
            }
        });

        return view;
    }


    //Every validation done in this method
    public void validate(){

        SharedPreferences.Editor editor = cons.sharedpreferences.edit();

        if(android.util.Patterns.EMAIL_ADDRESS.matcher(sign_in_email.getText().toString()).matches()) {

            if (email_list.size() > 0) {

//                for (int i = 0; i < email_list.size() && i < password_list.size(); i++) {

                    if (email_list.contains(sign_in_email.getText().toString()) &&
                            sign_in_password.getText().toString().equals(EncryptDecrypt.decryptIt(password_list.get(email_list.indexOf(sign_in_email.getText().toString())).toString()))) {

                        Intent in = new Intent(getActivity(), profile.class);
                        in.putExtra("email",sign_in_email.getText().toString());

                        editor.putString(cons.email_name,sign_in_email.getText().toString());
                        editor.putString(cons.pass_name,sign_in_password.getText().toString());
                        editor.putString(cons.state_name, cons.logIn_state);
                        editor.commit();

                        startActivity(in);
                    } else if (email_list.contains(sign_in_email.getText().toString())){

                        InputMethodManager inputManager = (InputMethodManager)
                                getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

                        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                        if(!sign_in_password.getText().toString().equals(EncryptDecrypt.decryptIt(password_list.get(email_list.indexOf(sign_in_email.getText().toString())).toString())))
                        Toast.makeText(getActivity(), "Password incorrect.", Toast.LENGTH_SHORT).show();
                    } else {
                        InputMethodManager inputManager = (InputMethodManager)
                                getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

                        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                        Toast.makeText(getActivity(), "Account not recognized, please sign up to proceed.", Toast.LENGTH_SHORT).show();
                    }
//                }
            }
            else{
                if(TextUtils.isEmpty(sign_in_password.getText().toString())){
                    sign_in_password.setError("Required!");
                }else {
                    InputMethodManager inputManager = (InputMethodManager)
                            getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

                    inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                    Toast.makeText(getActivity(), "Account not recognized, please sign up to proceed.", Toast.LENGTH_SHORT).show();
                }
            }
        }
        else {
            sign_in_email.setError("Invalid format. Please enter email address correctly");
        }
    }
}