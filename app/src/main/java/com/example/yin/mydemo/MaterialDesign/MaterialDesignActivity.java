package com.example.yin.mydemo.MaterialDesign;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.yin.mydemo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MaterialDesignActivity extends AppCompatActivity {


    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    private Fruit[] fruits = {new Fruit("A", R.mipmap.asa), new Fruit("B", R.mipmap.asaaa)
            , new Fruit("C", R.mipmap.aaaa), new Fruit("F", R.mipmap.asasas),
            new Fruit("D", R.mipmap.asa)
            , new Fruit("E", R.mipmap.asaaa), new Fruit("G", R.mipmap.asasas)};

    private List<Fruit> fruitList = new ArrayList<>();

    private FruitAdapter adapter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.fab)
    FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_design);
        ButterKnife.bind(this);


        setSupportActionBar(toolbar);

        ActionBar action = getSupportActionBar();
        if (action != null) {
            action.setDisplayHomeAsUpEnabled(true);
            action.setHomeAsUpIndicator(R.mipmap.ic_launcher);
        }


        navView.setCheckedItem(R.id.call);//默认选中
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawers();
                return true;
            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "已删除", Snackbar.LENGTH_SHORT).setAction("撤回", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MaterialDesignActivity.this, "已撤回", Toast.LENGTH_SHORT).show();
                    }
                }).show();
            }
        });


        initFruits();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MaterialDesignActivity.this, 2);
        recyclerview.setLayoutManager(gridLayoutManager);
        adapter = new FruitAdapter(fruitList);
        recyclerview.setAdapter(adapter);



        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFruits();
            }
        });
    }


    private void refreshFruits() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initFruits();
                        adapter.notifyDataSetChanged();
                        swipeRefresh.setRefreshing(false);//表示刷新事件结束，并隐藏刷新进度条。
                    }
                });
            }
        }).start();
    }

    private void initFruits() {
        fruitList.clear();
        for (int i = 0; i < 50; i++) {
            Random random = new Random();
            int index = random.nextInt(fruits.length);
            fruitList.add(fruits[index]);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.backup:
                Toast.makeText(MaterialDesignActivity.this, "back", Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                Toast.makeText(MaterialDesignActivity.this, "delete", Toast.LENGTH_SHORT).show();
                break;
            case R.id.set:
                Toast.makeText(MaterialDesignActivity.this, "set", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

}
