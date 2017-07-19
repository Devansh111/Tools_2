package com.example.devansh.tools.Activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;

import com.example.devansh.tools.Fragments.toolbox_add_tool;
import com.example.devansh.tools.R;

public class toolbox extends AppCompatActivity {

    public FragmentManager fragmentManager;
    public toolbox_add_tool obj;
    public FloatingActionButton add_tool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbox);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        add_tool = (FloatingActionButton) findViewById(R.id.toolbox_add_tool);

        add_tool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragmentManager=getFragmentManager();
                obj=new toolbox_add_tool();
                fragmentManager.beginTransaction().setCustomAnimations(R.animator.slide_up,R.animator.slide_out).addToBackStack(null)
                        .add(R.id.toolbox_add_tools_fragment,obj).commit();
                add_tool.setVisibility(View.GONE);

            }
        });
    }

    @Override
    public void onBackPressed() {
        add_tool.setVisibility(View.VISIBLE);
        if(getFragmentManager().getBackStackEntryCount()>0)
            getFragmentManager().popBackStack();
        else
            super.onBackPressed();
    }

    public void hideFb(){
        add_tool.setVisibility(View.INVISIBLE);
    }
}
