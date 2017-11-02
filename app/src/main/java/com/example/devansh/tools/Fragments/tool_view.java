package com.example.devansh.tools.Fragments;


import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.devansh.tools.Activities.Toolbox;
import com.example.devansh.tools.Activities.home;
import com.example.devansh.tools.POJO.add_tool_pojo;
import com.example.devansh.tools.R;
import com.example.devansh.tools.Utility.database_utils;

import java.util.ArrayList;

/**
 * Created by Devansh on 7/18/2017.
 */

public class tool_view extends Fragment {

    public String make,model,price,img,tool_type,cond,specs,deposit;
    public Button rent_button;
    public TextView make_name,tool_price,model_name,brand_name,tool_type_name,cond_name,specs_name,deposit_name,tool_view_dp_name;
    public ImageView tool_img;
    public add_tool_pojo obj ;
    public Boolean frame_decide;
    public ArrayList<String> name_list=new ArrayList<>();

    public tool_view(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tool_view,null);

        make_name=(TextView) view.findViewById(R.id.tool_view_name);
        tool_img = (ImageView) view.findViewById(R.id.tool_view_pic);
        tool_price = (TextView) view.findViewById(R.id.tool_view_price);
        model_name = (TextView) view.findViewById(R.id.tool_view_model);
        brand_name = (TextView) view.findViewById(R.id.tool_view_brand);
        tool_type_name =(TextView) view.findViewById(R.id.tool_view_type);
        cond_name=(TextView) view.findViewById(R.id.tool_view_cond);
        specs_name=(TextView) view.findViewById(R.id.tool_view_about_content);
        deposit_name=(TextView) view.findViewById(R.id.tool_view_deposit);
        tool_view_dp_name =(TextView) view.findViewById(R.id.tool_view_dp_name);

        //Toolbar
        Toolbar toolbar =(Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Details");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        //Retrieving data
        final Cursor cursor = home.db.rawQuery("SELECT * from "+ database_utils.signin_table_name+";",null);
        if (cursor != null)
            cursor.moveToFirst();
        do {
            try {
                name_list.add(cursor.getString(cursor.getColumnIndex(database_utils.toolbox_col_fname))+" "
                        +cursor.getString(cursor.getColumnIndex(database_utils.toolbox_col_lname)));
            }catch (Exception e){}
        } while (cursor.moveToNext());

        Bundle bundle = getArguments();
        if(bundle!=null){
            obj = new add_tool_pojo();
            obj = bundle.getParcelable("object");
        }


        if(obj!=null){
            make = obj.getMake_name();
            model = obj.getModel_name();
            price = obj.getPrice();
            img = obj.getImage();
            tool_type = obj.getTool_type();
            deposit = obj.getDeposit();
            cond = obj.getCond();
            specs = obj.getSpecs();
            ((Toolbox)getActivity()).hideFb();
        }

        if(!TextUtils.isEmpty(make)&&!TextUtils.isEmpty(img)&&!TextUtils.isEmpty(model)&&!TextUtils.isEmpty(price)){
            make_name.setText(model+" "+make);
            tool_price.setText(price);
            model_name.setText(model);
            brand_name.setText(make);
            tool_type_name.setText(tool_type);
            cond_name.setText(cond);
            specs_name.setText(specs);
            deposit_name.setText(deposit);
            byte [] encodeByte= Base64.decode(img, Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            tool_img.setImageBitmap(bitmap);

            if(name_list.size()>0){
                for(int i = 0;i<name_list.size();i++)
                tool_view_dp_name.setText(name_list.get(i));
            }
        }

        rent_button = (Button) view.findViewById(R.id.tool_view_rent);
        rent_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(obj!=null){
                    frame_decide = true;
                    Fragment fragment = new sign_in();
                    Bundle bundle1 = new Bundle();
                    bundle1.putBoolean("toolbox",frame_decide);
                    fragment.setArguments(bundle1);
                    getFragmentManager().beginTransaction().addToBackStack(null)
                            .setCustomAnimations(R.anim.slide_in_up,R.anim.slide_out_down).add(R.id.toolbox_add_tools_fragment,fragment).commit();
                }else {
                    frame_decide = false;
                    Fragment fragment = new sign_in();
                    Bundle bundle1 = new Bundle();
                    bundle1.putBoolean("toolbox",frame_decide);
                    fragment.setArguments(bundle1);
                    getFragmentManager().beginTransaction().addToBackStack(null)
                            .setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_down).add(R.id.home_frame, fragment).commit();
                }
            }
        });
        return view;
    }

}