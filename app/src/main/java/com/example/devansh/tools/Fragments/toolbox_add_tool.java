package com.example.devansh.tools.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.devansh.tools.Activities.toolbox;
import com.example.devansh.tools.R;

/**
 * Created by Devansh on 7/11/2017.
 */

public class toolbox_add_tool extends Fragment {

    public toolbox_add_tool(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.toolbox_add_tool,null);
        ((toolbox)getActivity()).hideFb();
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        final ImageView toolbox_add_tool_image_upload = (ImageView) view.findViewById(R.id.toolbox_add_tool_image_upload);
        final ImageView toolbox_add_tool_image_upload_2 = (ImageView) view.findViewById(R.id.toolbox_add_tool_image_upload_2);
        final TextView toolbox_add_tool_image_upload_2_text = (TextView) view.findViewById(R.id.toolbox_add_tool_image_upload_2_text);
        final TextView toolbox_add_tool_image_upload_text = (TextView) view.findViewById(R.id.toolbox_add_tool_image_upload_text);
        final Button toolbox_add_tool_next =(Button) view.findViewById(R.id.toolbox_add_tool_next);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.close);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Add tool");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        //Adding static image
        toolbox_add_tool_image_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toolbox_add_tool_image_upload.setImageResource(R.drawable.firewood);
                toolbox_add_tool_image_upload_text.setVisibility(View.GONE);
                toolbox_add_tool_image_upload_2.setVisibility(View.VISIBLE);
                toolbox_add_tool_image_upload_2_text.setVisibility(View.VISIBLE);
                toolbox_add_tool_next.setEnabled(true);
                toolbox_add_tool_next.setAlpha(1f);
            }
        });

        toolbox_add_tool_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.toolbox_add_tools_fragment,new add_tool_info()).commit();
            }
        });

        return view;
    }

}