package com.example.devansh.tools.Tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.devansh.tools.Adapters.home_tab_1_lv;
import com.example.devansh.tools.Fragments.tool_view;
import com.example.devansh.tools.R;

public class home_tab_1 extends Fragment {

    public ListView lv;

    public home_tab_1(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.home_tab_1, container, false);
        lv= (ListView) view.findViewById(R.id.home_tab_1_listview);
        lv.setAdapter(new home_tab_1_lv(getActivity()));
        final FragmentManager fragmentManager = getFragmentManager();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                fragmentManager.beginTransaction().addToBackStack(null).setCustomAnimations(R.anim.slide_in_up,R.anim.slide_out_down).add(R.id.home_frame,new tool_view()).commit();
            }
        });
        return view;
    }
}