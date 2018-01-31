package com.chaek.example.fragments;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.chaek.android.example.R;

import chaek.com.android.Fragments;

public class MainActivity extends AppCompatActivity implements OnMainSwitchListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainFragment mainFragment = (MainFragment) Fragments.get(MainFragment.class).putString("title", "title").get();
        Fragments.with(this).fragment(MainFragment.class).into(R.id.test);
    }

    @Override
    public void switchFragment(int position) {

        switch (position) {
            case 0:
                Fragments.with(this)
                        .addToBackStack()
                        .putString("title", "item1")
                        .fragment(Fragment1.class)
                        .into(R.id.test);
                break;
            case 1:
                Fragments.with(this)
                        .putString("title", "item2")
                        .fragment(Fragment1.class)
                        .anim()
                        .fade()
                        .addToBackStack()
                        .into(R.id.test);
                break;
            case 2:
                Fragments.with(this)
                        .putString("title", "item3")
                        .fragment(Fragment1.class)
                        .into(R.id.test);
                break;
            case 3:
                android.support.v4.app.Fragment f = Fragments.with(this)
                        .addToBackStack()
                        .putString("title", "item4")
                        .fragment(Fragment1.class)
                        .get();
                getSupportFragmentManager().beginTransaction().replace(R.id.test, f, "test4").commit();

                break;
            default:
        }


    }
}
