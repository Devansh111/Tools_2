package com.example.devansh.tools.Fragments;

import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.devansh.tools.Activities.toolbox;
import com.example.devansh.tools.R;

/**
 * Created by Devansh on 7/14/2017.
 */

public class add_tool_info extends Fragment {

    public add_tool_info(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_tool_info,null);
        ((toolbox)getActivity()).hideFb();
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        Button button = (Button) view.findViewById(R.id.add_tool_next_step);

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).setTitle("Add tool");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.toolbox_add_tools_fragment,new add_tool_pricing()).commit();
            }
        });

        return view;
    }
}
