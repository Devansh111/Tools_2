package com.example.devansh.tools.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.devansh.tools.R;

/**
 * Created by Devansh on 7/5/2017.
 */

public class home_tab_1_lv extends BaseAdapter implements Filterable{

    public LayoutInflater inflater;
    public static TextView tool_name;

    public static TextView getTextviewName(){
        return tool_name;
    }

    public home_tab_1_lv(Context c){
        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return 3;
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v=view;
        if(v==null) {
            v = inflater.inflate(R.layout.home_tab_1_listview_adapter, null);
            tool_name = (TextView) v.findViewById(R.id.adapter_tool_name);
        }

        return v;
    }

    @Override
    public Filter getFilter() {
        return null;
    }
}