package com.chaek.example.fragments;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.chaek.android.example.R;

/**
 * Auth: Chaek
 * Date: 2017/12/11
 */

public class MainFragment extends BaseFragment {
    private android.widget.Button mItem1;
    private android.widget.Button mItem2;
    private android.widget.Button mItem3;
    private android.widget.Button mItem4;

    private OnMainSwitchListener onMainSwitchListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMainSwitchListener) {
            onMainSwitchListener = (OnMainSwitchListener) context;
        }
    }

    @Override
    public int getLayoutViewId() {
        return  R.layout.main_fragment;
    }

    @Override
    public void initView() {

        mItem1 = (Button) findViewById(R.id.item1);
        mItem2 = (Button) findViewById(R.id.item2);
        mItem3 = (Button) findViewById(R.id.item3);
        mItem4 = (Button) findViewById(R.id.item4);
        mItem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMainSwitchListener.switchFragment(0);
            }
        });
        mItem2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMainSwitchListener.switchFragment(1);
            }
        });
        mItem3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMainSwitchListener.switchFragment(2);
            }
        });
        mItem4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMainSwitchListener.switchFragment(3);
            }
        });
    }

    @Override
    protected void initData() {

    }
}
