package com.chaek.example.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by W on 2016/11/14.
 * Base
 */
public abstract class BaseFragment extends Fragment {

    public View fragmentView;
    /**
     * 是否初始
     */
    private boolean isInitView;
    public Context mContext;
    public boolean isInitData;


    public boolean isInitView() {
        return isInitView;
    }

    public boolean isInitData() {
        return isInitData;
    }


    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        this.mContext = context;
    }




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (fragmentView == null) {
            setFragmentView();
        }
        isInitView = true;



        return fragmentView;
    }


    protected void setFragmentView() {
        fragmentView = View.inflate(getActivity(), getLayoutViewId(), null);
        initModel();
        initView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getUserVisibleHint() && isInitView && !isInitData) {
            initData();
            isInitData = true;
        }
    }

    public void initModel() {
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isInitView && !isInitData) {
            initData();
            isInitData = true;
        }
    }

    /**
     * get view widget
     *
     * @param viewId view Id
     * @param <T>    view Type
     * @return view
     */
    public <T extends View> T findViewById(int viewId) {
        return (T) fragmentView.findViewById(viewId);
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


    public abstract int getLayoutViewId();


    public abstract void initView();


    protected abstract void initData();


}