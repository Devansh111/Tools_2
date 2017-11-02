package com.example.devansh.tools.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.devansh.tools.Activities.home;
import com.example.devansh.tools.Activities.profile;
import com.example.devansh.tools.POJO.sign_up_pojo;
import com.example.devansh.tools.R;
import com.example.devansh.tools.Utility.EncryptDecrypt;
import com.example.devansh.tools.Utility.cons;
import com.example.devansh.tools.Utility.database_utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import static android.R.attr.value;

/**
 * Created by Devansh on 7/19/2017.
 */

public class sign_up extends Fragment {

    public Button sign_up_confirm;
    public EditText sign_up_email,sign_up_pass,sign_up_fname,sign_up_lname,sign_up_re_pass,sign_up_address;
    public sign_up_pojo pojo;
    public CheckBox sign_up_pricing_terms_cond;

    public sign_up(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.sign_up,null);

        Toolbar toolbar =(Toolbar) view.findViewById(R.id.toolbar);
        sign_up_confirm = (Button) view.findViewById(R.id.sign_up_confirm);
        sign_up_email = (EditText) view.findViewById(R.id.sign_up_email);
        sign_up_pass = (EditText) view.findViewById(R.id.sign_up_password);
        sign_up_fname = (EditText) view.findViewById(R.id.sign_up_fname);
        sign_up_lname = (EditText) view.findViewById(R.id.sign_up_lname);
        sign_up_re_pass = (EditText) view.findViewById(R.id.sign_up_re_password);
        sign_up_address = (EditText) view.findViewById(R.id.sign_up_address);
        sign_up_pricing_terms_cond = (CheckBox) view.findViewById(R.id.sign_up_pricing_terms_cond);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.close);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Sign up");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });


        sign_up_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            validate();
            }
        });
        return view;
    }

    public void validate() {

        SharedPreferences.Editor editor = cons.sharedpreferences.edit();

        pojo = new sign_up_pojo();
        if (TextUtils.isEmpty(sign_up_email.getText().toString())) {
            sign_up_email.setError("Required!");
        } else if (TextUtils.isEmpty(sign_up_fname.getText().toString())) {
            sign_up_fname.setError("Required!");
        } else if (TextUtils.isEmpty(sign_up_lname.getText().toString())) {
            sign_up_lname.setError("Required!");
        } else if (TextUtils.isEmpty(sign_up_pass.getText().toString())) {
            sign_up_pass.setError("Required!");
        } else if (TextUtils.isEmpty(sign_up_re_pass.getText().toString())) {
            sign_up_re_pass.setError("Required!");
        } else if (TextUtils.isEmpty(sign_up_address.getText().toString())) {
            sign_up_address.setError("Required!");
        } else {
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(sign_up_email.getText().toString()).matches()) {
                if (sign_up_pass.getText().toString().equals(sign_up_re_pass.getText().toString())) {
                    if (sign_up_pricing_terms_cond.isChecked()) {

                        pojo = new sign_up_pojo();
                        if (TextUtils.isEmpty(sign_up_email.getText().toString())) {
                            sign_up_email.setError("Required!");
                        } else if (TextUtils.isEmpty(sign_up_fname.getText().toString())) {
                            sign_up_fname.setError("Required!");
                        } else if (TextUtils.isEmpty(sign_up_lname.getText().toString())) {
                            sign_up_lname.setError("Required!");
                        } else if (TextUtils.isEmpty(sign_up_pass.getText().toString())) {
                            sign_up_pass.setError("Required!");
                        } else if (TextUtils.isEmpty(sign_up_re_pass.getText().toString())) {
                            sign_up_re_pass.setError("Required!");
                        } else if (TextUtils.isEmpty(sign_up_address.getText().toString())) {
                            sign_up_address.setError("Required!");
                        } else {
                            String enc_pass = EncryptDecrypt.encryption(sign_up_pass.getText().toString());
                            pojo.setEmail(sign_up_email.getText().toString());
                            pojo.setPassword(enc_pass);
                            pojo.setAddress(sign_up_address.getText().toString());

                            home.db.execSQL("INSERT INTO " + database_utils.signin_table_name + " ("
                                    + database_utils.toolbox_col_email + ","
                                    + database_utils.toolbox_col_fname + ","
                                    + database_utils.toolbox_col_lname + ","
                                    + database_utils.toolbox_col_address + ","
                                    + database_utils.toolbox_col_password + ") values('"
                                    + sign_up_email.getText().toString() + "','"
                                    + sign_up_fname.getText().toString() + "','"
                                    + sign_up_lname.getText().toString() + "','"
                                    + sign_up_address.getText().toString() + "','"
                                    + enc_pass + "');");
                            Toast.makeText(getActivity(), "Login created", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getActivity(), profile.class);
                            i.putExtra("email", sign_up_email.getText().toString());

                            editor.putString(cons.email_name,sign_up_email.getText().toString());
                            editor.putString(cons.pass_name,sign_up_pass.getText().toString());
                            editor.putString(cons.state_name, cons.logIn_state);
                            editor.commit();

                            startActivity(i);
                        }
                    }else
                        Toast.makeText(getActivity(), "Please agree to terms and conditions to proceed.", Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(getActivity(), "Password doesn't match", Toast.LENGTH_LONG).show();
            } else
                Toast.makeText(getActivity(), "Invalid format. Please enter email address correctly", Toast.LENGTH_LONG).show();
        }

        }
    }
