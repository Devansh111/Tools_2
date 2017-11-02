package com.example.devansh.tools.Fragments;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.devansh.tools.Activities.Toolbox;
import com.example.devansh.tools.Activities.home;
import com.example.devansh.tools.R;
import com.example.devansh.tools.Utility.database_utils;

import java.util.ArrayList;

/**
 * Created by Devansh on 7/14/2017.
 */

public class add_tool_info extends Fragment {

    public String img_uri;
    public Button next_button;
    public EditText add_tool_make_et,add_tool_model_et,add_tool_specs_et,add_tool_existing_address_et;
    public Spinner add_tool_type_options,add_tool_condition_options;
    public CheckBox add_tool_existing_address;
    public ArrayList<String> address_list = new ArrayList<>();

    public add_tool_info(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_tool_info,null);

        ((Toolbox)getActivity()).hideFb();
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        next_button = (Button) view.findViewById(R.id.add_tool_next_step);
        add_tool_make_et = (EditText) view.findViewById(R.id.add_tool_make_et);
        add_tool_model_et = (EditText) view.findViewById(R.id.add_tool_Model_et);
        add_tool_specs_et = (EditText) view.findViewById(R.id.add_tool_specs_et);
        add_tool_existing_address = (CheckBox) view.findViewById(R.id.add_tool_address_existing_cb);
        add_tool_existing_address_et = (EditText) view.findViewById(R.id.add_tool_address_et);


        //Checking if all the fields are complete
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });

        //Getting image from previous fragment
        Bundle img_bundle = getArguments();
        if(img_bundle!=null)
            img_uri = img_bundle.getString("image");

        //Toolbar
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).setTitle("Add tool");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        //Get address
        add_tool_existing_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(add_tool_existing_address.isChecked()){
                    final Cursor cursor = home.db.rawQuery("SELECT * from "+ database_utils.signin_table_name+";",null);
                    if (cursor != null)
                        cursor.moveToFirst();
                    do {
                        try {
                            address_list.add(cursor.getString(cursor.getColumnIndex(database_utils.toolbox_col_address)));
                        }catch (Exception e){}
                    } while (cursor.moveToNext());

                    if(address_list.size()>0) {
                        for (int i = 0; i < address_list.size(); i++)
                            add_tool_existing_address_et.setText(address_list.get(i));
                    }else
                        Toast.makeText(getActivity(),"No address found. Please sign up to register your address",Toast.LENGTH_LONG).show();
                }
                else
                    add_tool_existing_address_et.setText("");
            }
        });


        add_tool_type_options = (Spinner) view.findViewById(R.id.add_tool_type_options);
        add_tool_condition_options = (Spinner) view.findViewById(R.id.add_tool_condition_options);

        String[] type_options_list  = {"Power tool","Drilling tool","Hand tool"};
        ArrayAdapter<String> type_options = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,type_options_list);
        add_tool_type_options.setAdapter(type_options);

        String[] cond_options_list  = {"Like new","New","Used"};
        ArrayAdapter<String> cond_options = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,cond_options_list);
        add_tool_condition_options.setAdapter(cond_options);
        add_tool_condition_options.getSelectedItem().toString();



        return view;
    }

    public void validate() {
        if (TextUtils.isEmpty(add_tool_make_et.getText())) {
            add_tool_make_et.setError("Required!");
        } else if (TextUtils.isEmpty(add_tool_model_et.getText())) {
            add_tool_model_et.setError("Required!");
        } else if (TextUtils.isEmpty(add_tool_specs_et.getText())) {
            add_tool_specs_et.setError("Required!");
        } else if(TextUtils.isEmpty(add_tool_condition_options.getSelectedItem().toString())){
            Toast.makeText(getActivity(),"Please select tool condition",Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(add_tool_type_options.getSelectedItem().toString())){
            Toast.makeText(getActivity(),"Please select tool type",Toast.LENGTH_SHORT).show();
        }
        else {
            Bundle bundle = new Bundle();
            bundle.putString("make", add_tool_make_et.getText().toString());
            bundle.putString("model",add_tool_model_et.getText().toString());
            bundle.putString("image", img_uri);
            bundle.putString("specs",add_tool_specs_et.getText().toString());
            bundle.putString("cond",add_tool_condition_options.getSelectedItem().toString());
            bundle.putString("type",add_tool_type_options.getSelectedItem().toString());
            Fragment fragment = new add_tool_pricing();
            fragment.setArguments(bundle);
            getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.toolbox_add_tools_fragment, fragment).commit();
        }
    }
}
