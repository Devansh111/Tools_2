package com.example.devansh.tools.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.devansh.tools.Activities.Toolbox;
import com.example.devansh.tools.Activities.home;
import com.example.devansh.tools.R;
import com.example.devansh.tools.Utility.database_utils;

/**
 * Created by Devansh on 7/17/2017.
 */

public class add_tool_pricing extends Fragment {

    public String make_name,model_name,image,type,cond,specs;
    public Button button_next;
    public EditText add_tool_pricing_per_day_et,add_tool_pricing_est_et,add_tool_pricing_deposit_et;
    public CheckBox terms_cond;

    public add_tool_pricing(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.add_tool_pricing,null);
        final Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).setTitle("Add tool");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        //Initialization
        add_tool_pricing_per_day_et = (EditText) view.findViewById(R.id.add_tool_pricing_per_day_et);
        add_tool_pricing_deposit_et = (EditText) view.findViewById(R.id.add_tool_pricing_deposit_et);
        add_tool_pricing_est_et = (EditText) view.findViewById(R.id.add_tool_pricing_est_et);
        button_next = (Button) view.findViewById(R.id.add_tool_pricing_next_step);
        terms_cond = (CheckBox) view.findViewById(R.id.add_tool_pricing_terms_cond);

        //Getting values from previous fragments
        Bundle bundle = getArguments();
        if(bundle!=null) {
            make_name = bundle.getString("make").toString();
            image = bundle.getString("image").toString();
            model_name = bundle.getString("model").toString();
            cond=bundle.getString("cond").toString();
            type=bundle.getString("type").toString();
            specs = bundle.getString("specs").toString();
        }

        //Validation on button pressed
        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });

        return view;
    }

    public void validate(){

        if(TextUtils.isEmpty(add_tool_pricing_est_et.getText().toString())){
            add_tool_pricing_est_et.setError("Required!");
        }
        else if(TextUtils.isEmpty(add_tool_pricing_per_day_et.getText().toString())){
            add_tool_pricing_per_day_et.setError("Required!");
        }
        else if(TextUtils.isEmpty(add_tool_pricing_deposit_et.getText().toString())){
            add_tool_pricing_deposit_et.setError("Required!");
        }
        else{
            if(terms_cond.isChecked()) {
                if(TextUtils.isEmpty(add_tool_pricing_est_et.getText().toString())){
                    add_tool_pricing_est_et.setError("Required!");
                }
                else if(TextUtils.isEmpty(add_tool_pricing_per_day_et.getText().toString())){
                    add_tool_pricing_per_day_et.setError("Required!");
                }
                else if(TextUtils.isEmpty(add_tool_pricing_deposit_et.getText().toString())){
                    add_tool_pricing_deposit_et.setError("Required!");
                }else {

                    Intent i = new Intent(getActivity(), Toolbox.class);
                    i.putExtra("make", make_name);
                    i.putExtra("price", add_tool_pricing_per_day_et.getText().toString());
                    i.putExtra("deposit", add_tool_pricing_deposit_et.getText().toString());
                    i.putExtra("image", image);
                    i.putExtra("model", model_name);
                    i.putExtra("type", type);
                    i.putExtra("cond", cond);
                    i.putExtra("specs", specs);
                    home.db.execSQL("INSERT INTO "
                            + database_utils.toolbox_table_name
                            + " (" + database_utils.toolbox_col_name + ","
                            + database_utils.toolbox_col_model + ","
                            + database_utils.toolbox_col_specs + ","
                            + database_utils.toolbox_col_cond + ","
                            + database_utils.toolbox_col_type + ","
                            + database_utils.toolbox_col_deposit + ","
                            + database_utils.toolbox_col_price + "," + ""
                            + database_utils.toolbox_col_img + ") " +
                            "VALUES('"
                            + make_name + "','"
                            + model_name + "','"
                            + specs + "','"
                            + cond + "','"
                            + type + "','"
                            + add_tool_pricing_deposit_et.getText().toString() + "','"
                            + add_tool_pricing_per_day_et.getText().toString() + "','"
                            + image + "');");
                    startActivity(i);
                }

            }
            else
                Toast.makeText(getActivity(),"Please agree to terms and conditions to proceed.",Toast.LENGTH_LONG).show();
        }
    }
}