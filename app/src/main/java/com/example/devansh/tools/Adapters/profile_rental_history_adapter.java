package com.example.devansh.tools.Adapters;

import android.content.Context;
import android.graphics.BitmapShader;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.devansh.tools.R;

import static android.R.attr.width;
import static android.support.design.R.attr.height;

/**
 * Created by Devansh on 7/24/2017.
 */

public class profile_rental_history_adapter extends BaseAdapter {

    public LayoutInflater inflater;

    public profile_rental_history_adapter(Context c){
        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return 2;
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
        if(v==null)
                v=inflater.inflate(R.layout.profile_rental_history_adapter,null);

        return v;
    }
}
