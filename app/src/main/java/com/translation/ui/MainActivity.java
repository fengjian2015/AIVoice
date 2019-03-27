package com.translation.ui;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;
import com.translation.R;
import com.translation.component.base.BaseActivity;
import com.translation.ui.adapter.MainAdapter;
import com.translation.ui.fragment.ContactFragment;
import com.translation.ui.fragment.ConversationFragment;
import com.translation.ui.fragment.MineFragment;
import com.translation.ui.widget.CustomScrollViewPager;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {


    @BindView(R.id.viewpager)
    CustomScrollViewPager mViewpager;
    @BindView(R.id.ll_main_bot_container)
    LinearLayout llMenu;
    private MainAdapter adapter;
    private List<Fragment> fragmentList;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViewsData() {
        setTitle(false, 0);
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
        fragmentList.add(new ConversationFragment());
        fragmentList.add(new ContactFragment());
        fragmentList.add(new MineFragment());
        adapter = new MainAdapter(getSupportFragmentManager(), fragmentList);
        mViewpager.setAdapter(adapter);
        selectFragment(0);
    }

}
