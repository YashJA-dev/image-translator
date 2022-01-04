package com.example.imagetranslater;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;


public class Scanner_F extends Fragment {
    Activity activity;
    Context context;
    ViewPager viewPager;
    private Button cameraBtn;
    private FragmentManager fragmentManager;
    public Scanner_F(Activity activity, Context context, ViewPager viewPager, FragmentManager supportFragmentManager) {
        this.activity=activity;
        this.context=context;
        this.viewPager=viewPager;
        this.fragmentManager=supportFragmentManager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_scanner_, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        Toast.makeText(context,"resume scanner",Toast.LENGTH_SHORT);
        Camerasetup camerasetup=new Camerasetup(context,activity,viewPager,fragmentManager);
        camerasetup.initCamera();

    }
}