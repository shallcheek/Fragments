package com.chaek.example.fragments;

import android.widget.TextView;

import com.chaek.android.example.R;

/**
 * Auth: Chaek
 * Date: 2017/12/11
 */

public class Fragment1 extends BaseFragment {
    private TextView mTest1;

    private String message;
    @Override
    public int getLayoutViewId() {
        return R.layout.fragment1;
    }

    @Override
    public void initView() {

        mTest1 = (TextView) findViewById( R.id.test1);
        mTest1.setText(getArguments().getString("title"));
    }

    @Override
    protected void initData() {

    }
}
