package com.example.imagetranslater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.imagetranslater.Essists.FragmentsData;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private List<FragmentsData> fragList=new ArrayList<>();

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }



    @Override
    public int getCount() {
        return fragList.size();
    }
    @Override
    public Fragment getItem(int position) {

        return fragList.get(position).getFragment();
    }

//    @Override
//    public CharSequence getPageTitle(int position) {
//        return fragName.get(position);
//    }


    public void addFragments(List<FragmentsData> listFrag) {
        this.fragList=listFrag;
    }
}
