package com.translation.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.translation.R;
import com.translation.ui.adapter.MainAdapter;
import com.translation.ui.fragment.MessageFragment;
import com.translation.ui.widget.CustomScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.viewpager)
    CustomScrollViewPager mViewpager;
    @BindView(R.id.ll_menu)
    LinearLayout llMenu;
    private MainAdapter adapter;
    private List<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initPage();
        initMenu();
    }

    private void initMenu() {
        for (int i = 0; i < llMenu.getChildCount(); i++) {
            final int finalI = i;
            llMenu.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewpager.setCurrentItem(finalI, false);
                    selectFragment(finalI);
                }
            });
        }
    }

    private void selectFragment(int po) {
        for (int i = 0; i < llMenu.getChildCount(); i++) {
            llMenu.getChildAt(i).setSelected(false);
        }
        llMenu.getChildAt(po).setSelected(true);
    }

    private void initPage() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new MessageFragment());
        fragmentList.add(new MessageFragment());
        fragmentList.add(new MessageFragment());
        adapter = new MainAdapter(getSupportFragmentManager(), fragmentList);
        mViewpager.setAdapter(adapter);
        selectFragment(0);
    }
}
