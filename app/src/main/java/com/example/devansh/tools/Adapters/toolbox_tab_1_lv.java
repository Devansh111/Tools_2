package com.example.devansh.tools.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devansh.tools.Activities.home;
import com.example.devansh.tools.POJO.add_tool_pojo;
import com.example.devansh.tools.R;
import com.example.devansh.tools.Utility.database_utils;

import java.util.ArrayList;

/**
 * Created by Devansh on 7/28/2017.
 */

public class toolbox_tab_1_lv extends BaseAdapter {

    public LayoutInflater inflater;
    public ArrayList<add_tool_pojo> list;

    public toolbox_tab_1_lv(Context c, ArrayList<add_tool_pojo> list){
        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View v=view;
        Holder h;
        if(v==null){
            v= inflater.inflate(R.layout.toolbox_tab_1_adapter,null);
            h = new Holder();
            h.adapter_toolbox_image = (ImageView) v.findViewById(R.id.adapter_toolbox_image);
            h.adapter_toolbox_name = (TextView) v.findViewById(R.id.adapter_toolbox_name);
            h.adapter_toolbox_price = (TextView) v.findViewById(R.id.adapter_toolbox_price);
            h.adapter_toolbox_remove =(ImageView) v.findViewById(R.id.adapter_toolbox_remove);

            byte [] encodeByte=Base64.decode(list.get(i).getImage(), Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);

            h.adapter_toolbox_image.setImageBitmap(bitmap);
            h.adapter_toolbox_name.setText(list.get(i).getMake_name());
            h.adapter_toolbox_price.setText(list.get(i).getPrice());

            h.adapter_toolbox_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    home.db.execSQL("DELETE FROM "+ database_utils.toolbox_table_name+" WHERE "+database_utils.toolbox_col_name+"='"+list.get(i).getMake_name()+"'");
                    list.remove(i);
                    notifyDataSetChanged();
                }
            });


            v.setTag(h);
        }else {
            h =(Holder) v.getTag();
        }

        return v;
    }
    class Holder{
        ImageView adapter_toolbox_image,adapter_toolbox_remove;
        TextView adapter_toolbox_name ;
        TextView adapter_toolbox_price;

    }
}