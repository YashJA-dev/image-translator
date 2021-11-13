package com.example.imagetranslater.Essists;

import androidx.fragment.app.Fragment;

public class FragmentsData {
    private Fragment fragment;
    private String name;
    public FragmentsData(Fragment fragment, String name){
        this.fragment=fragment;
        this.name=name;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
