package com.dimon.mobilesafe.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.dimon.mobilesafe.R;
import com.dimon.mobilesafe.ui.adapter.RecyclerViewAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Dimon on 2016/2/26.
 */
public class HomeActivity extends AppCompatActivity {

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private RecyclerViewAdapter mRecyclerViewAdapter;
    Toolbar toolbar;
    private String[] mItems = new String[]{"手机防盗", "通讯卫士", "软件管理"
            , "进程管理", "流量管理", "病毒查杀", "垃圾清理", "高级工具", "设置中心"};

    private  int[] mPics = new int[]{R.drawable.ic_home_safe, R.drawable.ic_call_64
            , R.drawable.ic_ruanjianguanli, R.drawable.ic_jincheng, R.drawable.ic_liuliang
            , R.drawable.ic_bingdu, R.drawable.ic_lajiqingli, R.drawable.ic_gaojigongju
            , R.drawable.ic_shezhizhongxin};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //getActionBar().setTitle("手机管家");

        // 设置GridLayoutManager 这里用线性宫格显示 类似于grid view
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
        // 设置ItemAnimator
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // 设置固定大小
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setNestedScrollingEnabled(false);
        // 初始化自定义的适配器
        mRecyclerViewAdapter = new RecyclerViewAdapter(this, mItems, mPics);
        // 为mRecyclerView设置适配器
        mRecyclerView.setAdapter(mRecyclerViewAdapter);

    }


}
