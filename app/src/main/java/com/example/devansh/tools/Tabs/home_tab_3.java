package com.example.devansh.tools.Tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.devansh.tools.Adapters.home_tab_3_lv;
import com.example.devansh.tools.R;

/**
 * Created by Devansh on 7/4/2017.
 */
public class home_tab_3 extends Fragment {

    public home_tab_3(){}

    public ListView lv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.home_tab_3, container, false);
        lv = (ListView) view.findViewById(R.id.home_tab_3_listview);
        lv.setAdapter(new home_tab_3_lv(getActivity()));
        return view;
    }
}