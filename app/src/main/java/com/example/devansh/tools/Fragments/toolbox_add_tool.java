package com.example.devansh.tools.Fragments;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;

import android.support.v7.app.AlertDialog;
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
import com.example.devansh.tools.R;
import com.example.devansh.tools.Utility.cons;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;
import static com.example.devansh.tools.R.id.add;
import static com.example.devansh.tools.R.id.toolbox_add_tool_next;

/**
 * Created by Devansh on 7/11/2017.
 */

public class toolbox_add_tool extends Fragment {

    public ImageView toolbox_add_tool_image_upload;
    public Button toolbox_add_tool_next;
    public ImageView toolbox_add_tool_image_upload_2;
    public TextView toolbox_add_tool_image_upload_2_text;
    public TextView toolbox_add_tool_image_upload_text;
    public int chooseImageUpload;
    public Bitmap bitmap;

    public toolbox_add_tool(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.toolbox_add_tool,null);
        ((Toolbox)getActivity()).hideFb();
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbox_add_tool_image_upload = (ImageView) view.findViewById(R.id.toolbox_add_tool_image_upload);
        toolbox_add_tool_image_upload_2 = (ImageView) view.findViewById(R.id.toolbox_add_tool_image_upload_2);
        toolbox_add_tool_image_upload_2_text = (TextView) view.findViewById(R.id.toolbox_add_tool_image_upload_2_text);
        toolbox_add_tool_image_upload_text = (TextView) view.findViewById(R.id.toolbox_add_tool_image_upload_text);
        toolbox_add_tool_next =(Button) view.findViewById(R.id.toolbox_add_tool_next);

        //Toolbar
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

        //Adding image
        toolbox_add_tool_image_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPic();
                /*Selecting the image number to upload  */
                chooseImageUpload=1;
            }
        });

        //Adding Image 2
        toolbox_add_tool_image_upload_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPic();
                chooseImageUpload = 2;
            }
        });


        //Next Button
        toolbox_add_tool_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] b = baos.toByteArray();
                String img = Base64.encodeToString(b, Base64.DEFAULT);
                if(!TextUtils.isEmpty(img))
                    bundle.putString("image", img);

                Fragment fragment = new add_tool_info();
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.toolbox_add_tools_fragment,fragment).commit();
            }
        });

        return view;
    }

    //Adding dynamic image
    public void addPic(){

        final CharSequence[] items = { "Take Photo", "Choose from Gallery"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select photo from");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(items[i].toString().equals("Take Photo")){
                    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);
                }
                else if(items[i].toString().equals("Choose from Gallery")){
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);
                }
                else
                    dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case 0:
                if(resultCode == RESULT_OK) {
                    if(data.getData()==null){
                        bitmap = (Bitmap)data.getExtras().get("data");
                    }else{
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    if(chooseImageUpload==1) {
                        toolbox_add_tool_image_upload.setImageBitmap(bitmap);
                        toolbox_add_tool_image_upload.setScaleType(ImageView.ScaleType.FIT_XY);
                        toolbox_add_tool_next.setEnabled(true);
                        toolbox_add_tool_next.setAlpha(1f);
                        toolbox_add_tool_image_upload_text.setVisibility(View.GONE);
                        toolbox_add_tool_image_upload_2.setVisibility(View.VISIBLE);
                        toolbox_add_tool_image_upload_2_text.setVisibility(View.VISIBLE);
                    }
                    if(chooseImageUpload == 2){
                        toolbox_add_tool_image_upload_2.setImageBitmap(bitmap);
                        toolbox_add_tool_image_upload_2.setScaleType(ImageView.ScaleType.FIT_XY);
                        toolbox_add_tool_next.setEnabled(true);
                        toolbox_add_tool_next.setAlpha(1f);
                    }
                }
                break;
            case 1:
                if(resultCode == RESULT_OK){
                    if(data.getData()==null){
                        bitmap = (Bitmap)data.getExtras().get("data");
                    }else{
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    if(chooseImageUpload==1) {
                        toolbox_add_tool_image_upload.setImageBitmap(bitmap);
                        toolbox_add_tool_image_upload.setScaleType(ImageView.ScaleType.FIT_XY);
                        toolbox_add_tool_next.setEnabled(true);
                        toolbox_add_tool_next.setAlpha(1f);
                        toolbox_add_tool_image_upload_text.setVisibility(View.GONE);
                        toolbox_add_tool_image_upload_2.setVisibility(View.VISIBLE);
                        toolbox_add_tool_image_upload_2_text.setVisibility(View.VISIBLE);
                        SharedPreferences.Editor editor = cons.sharedpreferences.edit();
                        editor.putString("",bitmap.toString());
                    }
                    if(chooseImageUpload == 2){
                        toolbox_add_tool_image_upload_2.setImageBitmap(bitmap);
                        toolbox_add_tool_image_upload_2.setScaleType(ImageView.ScaleType.FIT_XY);
                        toolbox_add_tool_next.setEnabled(true);
                        toolbox_add_tool_next.setAlpha(1f);
                    }
                }
                break;
        }
    }
}