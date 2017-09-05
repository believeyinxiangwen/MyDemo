package com.example.yin.mydemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.yin.mydemo.MaterialDesign.MaterialDesignActivity;
import com.example.yin.mydemo.gson.GsonActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.gson)
    Button gson;
    @BindView(R.id.materialdesign)
    Button materialdesign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.gson, R.id.materialdesign})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.gson:
                startActivity(new Intent(MainActivity.this, GsonActivity.class));
                break;
            case R.id.materialdesign:
                startActivity(new Intent(MainActivity.this, MaterialDesignActivity.class));
                break;
        }
    }
}
