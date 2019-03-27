package com.translation.component.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.translation.androidlib.observer.EventManager;
import com.translation.androidlib.observer.EventMsg;
import com.translation.androidlib.observer.OnEventMsgListener;

/**
 * Created by Darren on 2018/12/13.
 */
public abstract class BaseFragment extends Fragment
        implements View.OnClickListener, OnEventMsgListener {


    protected BaseActivity mActivity;
    /**
     * fragment生命周期标志位，表示fragment是否已经执行onViewCreated()方法
     */
    protected boolean isViewCreated = false;
    public View parentView;

    protected abstract int getLayoutId();

    protected abstract void initViews();

    protected void initArguments(Bundle savedInstanceState) {

    }

    protected void initViewsData(){

    }

    protected void setListener(){

    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 获取宿主Activity
     */
    protected BaseActivity getHostActivity() {
        return mActivity;
    }

    @Override
    public void onEventMsg(EventMsg msg) {}

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = (BaseActivity) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mActivity = (BaseActivity) getActivity();
        super.onCreate(savedInstanceState);
        initArguments(savedInstanceState);
    }


    @Override
    public View onCreateView(final LayoutInflater inflater,
                             final ViewGroup container,
                             final Bundle savedInstanceState) {
        parentView = inflater.inflate(getLayoutId(), container, false);
        initViews();
        return parentView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated = true;
        initViewsData();
        setListener();
        EventManager.getInstance().register(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isViewCreated = false;
        EventManager.getInstance().register(this);
    }

}
