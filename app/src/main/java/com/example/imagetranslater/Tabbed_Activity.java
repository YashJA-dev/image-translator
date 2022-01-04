package com.example.imagetranslater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.imagetranslater.Essists.FragmentsData;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class Tabbed_Activity extends AppCompatActivity {
    private TabLayout tablayout;
    private ViewPager viewPager;
    private Activity activity;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        context = this;
        setContentView(R.layout.activity_tabbed);
        tablayout = activity.findViewById(R.id.tabb_layout);
        viewPager = activity.findViewById(R.id.viewPagger);

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpTabLayout();
    }

    private void setUpTabLayout() {
        tablayout.setupWithViewPager(viewPager);
        ViewPagerAdapter adpater = new ViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        List<FragmentsData> fragList = getFagList();
        adpater.addFragments(fragList);
        viewPager.setAdapter(adpater);
        setTitlesOfTab(tablayout);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("finish","f");
        finish();
    }

    private List<FragmentsData> getFagList() {
        Scanner_F scanner_f = new Scanner_F(activity, context,viewPager,getSupportFragmentManager());
        Translator translator = new Translator(activity, context,getSupportFragmentManager());
        List<FragmentsData> fragList = new ArrayList<>();
        fragList.add(new FragmentsData(scanner_f, "Scanner"));
        fragList.add(new FragmentsData(translator, "Translator"));
        return fragList;
    }

    public static void setTitlesOfTab(TabLayout tabLayout) {
        TabLayout.Tab tab;

        tab = tabLayout.getTabAt(0);
        tab.setIcon(R.drawable.ic_baseline_scanner_24);
        tab.setText("Scanner");

        tab = tabLayout.getTabAt(1);
        tab.setIcon(R.drawable.ic_baseline_g_translate_24);
        tab.setText("Translator");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}