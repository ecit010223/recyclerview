package com.huier.custom_recyclerview;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    private Button btnSimpleRecycler;
    private Button btnSwipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        btnSimpleRecycler = (Button)findViewById(R.id.btn_simple);
        btnSimpleRecycler.setOnClickListener(this);

        btnSwipeRefresh = (Button)findViewById(R.id.btn_swipe);
        btnSwipeRefresh.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_simple:
                SimpleActivity.entry(mContext);
                break;
            case R.id.btn_swipe:
                SwipeRefreshActivity.entry(mContext);
                break;
        }
    }
}
