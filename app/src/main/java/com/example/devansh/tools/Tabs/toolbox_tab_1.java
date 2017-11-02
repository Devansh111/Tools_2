package com.example.devansh.tools.Tabs;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.devansh.tools.Adapters.toolbox_tab_1_lv;
import com.example.devansh.tools.Fragments.tool_view;
import com.example.devansh.tools.POJO.add_tool_pojo;
import com.example.devansh.tools.R;

import java.util.ArrayList;

/**
 * Created by Devansh on 7/28/2017.
 */

public class toolbox_tab_1 extends Fragment {

    public ListView listView;
    public add_tool_pojo obj;
    public ImageView adapter_toolbox_remove;
    public Fragment fragment;
    public ArrayList<add_tool_pojo> list = new ArrayList<add_tool_pojo>();

    public toolbox_tab_1(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.toolbox_tab_1,container,false);

        Bundle bundle = getArguments();
        ArrayList<String> name_list = bundle.getStringArrayList("make");
        ArrayList<String> model_name_list = bundle.getStringArrayList("model");
        ArrayList<String> price_list = bundle.getStringArrayList("price");
        ArrayList<String> image_list = bundle.getStringArrayList("image");
        ArrayList<String> cond_list = bundle.getStringArrayList("cond");
        ArrayList<String> type_list = bundle.getStringArrayList("type");
        ArrayList<String> specs_list = bundle.getStringArrayList("specs");
        ArrayList<String> deposit_list = bundle.getStringArrayList("deposit");

        for(int i = 0; i<name_list.size()&&i<price_list.size();i++) {
            obj = new add_tool_pojo();
            obj.setMake_name(name_list.get(i));
            obj.setPrice(price_list.get(i));
            obj.setImage(image_list.get(i));
            obj.setModel_name(model_name_list.get(i));
            obj.setTool_type(type_list.get(i));
            obj.setCond(cond_list.get(i));
            obj.setDeposit(deposit_list.get(i));
            obj.setSpecs(specs_list.get(i));
            list.add(obj);
        }
        listView=(ListView) view.findViewById(R.id.toolbox_tab_tools);
        listView.setAdapter(new toolbox_tab_1_lv(getActivity(),list));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle1 = new Bundle();
                bundle1.putParcelable("object",list.get(i));
                fragment = new tool_view();
                fragment.setArguments(bundle1);
                getFragmentManager().beginTransaction().addToBackStack(null).
                        setCustomAnimations(R.anim.slide_in_up,R.anim.slide_out_down).
                        add(R.id.toolbox_add_tools_fragment,fragment).commit();
            }
        });
        return view;
    }
}