package com.example.farmogoapp.ui.main;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.example.farmogoapp.R;
import com.google.android.material.tabs.TabLayout;

public class Unregistercow extends AppCompatActivity {

    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unregistercow);

        viewPager = findViewById(R.id.viewPager);

        addTabs(viewPager);
        ((TabLayout) findViewById(R.id.tabLayout)).setupWithViewPager( viewPager );
    }

    private void addTabs(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new FragmentOne(), "Defuncion");
        adapter.addFrag(new FragmentTwo(), "Venta");
        adapter.addFrag(new FragmentThree(), "Matadero");
        viewPager.setAdapter(adapter);
    }
}
