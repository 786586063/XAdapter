package com.xadaptersimple;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.xadapter.adapter.XBaseAdapter;
import com.xadapter.adapter.XRecyclerViewAdapter;
import com.xadapter.holder.XViewHolder;
import com.xadapter.progressindicator.ProgressStyle;
import com.xadapter.widget.BaseRefreshHeader;
import com.xadapter.widget.XFooterLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * by y on 2016/11/17
 */

public class LinearLayoutManagerActivity extends AppCompatActivity
        implements XBaseAdapter.OnXBindListener<MainBean>, XBaseAdapter.OnItemLongClickListener<MainBean>,
        XBaseAdapter.OnItemClickListener<MainBean>, XBaseAdapter.FooterListener, XBaseAdapter.LoadingListener {

    private XRecyclerViewAdapter<MainBean> xRecyclerViewAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_layout);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        List<MainBean> mainBeen = new ArrayList<>();
        DataUtils.getData(mainBeen);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        xRecyclerViewAdapter = new XRecyclerViewAdapter<>();
        recyclerView.setAdapter(
                xRecyclerViewAdapter
                        .initXData(mainBeen)
                        .addRecyclerView(recyclerView)
                        .setLayoutId(R.layout.item)
                        .setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader)
                        .setLoadingMoreProgressStyle(ProgressStyle.BallRotate)
                        .setImageView(R.drawable.iconfont_downgrey)
                        .setHeaderBackgroundColor(R.color.colorBlack)
                        .setFooterBackgroundColor(R.color.colorBlack)
                        .setHeaderTextColor(R.color.textColor)
                        .setFooterTextColor(R.color.textColor)
                        .setPullRefreshEnabled(true)
                        .setLoadingMoreEnabled(true)
                        .addHeaderView(LayoutInflater.from(this).inflate(R.layout.item_header_1, (ViewGroup) findViewById(android.R.id.content), false))
                        .addHeaderView(LayoutInflater.from(this).inflate(R.layout.item_header_2, (ViewGroup) findViewById(android.R.id.content), false))
                        .addHeaderView(LayoutInflater.from(this).inflate(R.layout.item_header_3, (ViewGroup) findViewById(android.R.id.content), false))
                        .addFooterView(LayoutInflater.from(this).inflate(R.layout.item_footer_1, (ViewGroup) findViewById(android.R.id.content), false))
                        .addFooterView(LayoutInflater.from(this).inflate(R.layout.item_footer_2, (ViewGroup) findViewById(android.R.id.content), false))
                        .addFooterView(LayoutInflater.from(this).inflate(R.layout.item_footer_3, (ViewGroup) findViewById(android.R.id.content), false))
                        .onXBind(this)
                        .setOnLongClickListener(this)
                        .setOnItemClickListener(this)
                        .setLoadingListener(this)
                        .setFooterListener(this)
                        .setRefreshing(true)
        );

    }

    @Override
    public void onXBind(XViewHolder holder, int position, MainBean mainBean) {
        holder.setTextView(R.id.tv_name, mainBean.getName());
        holder.setTextView(R.id.tv_age, mainBean.getAge() + "");
    }

    @Override
    public void onItemClick(View view, int position, MainBean info) {
        Toast.makeText(getBaseContext(), "name:  " + info.getName() + "  age:  " + info.getAge() + "  position:  " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLongClick(View view, int position, MainBean info) {
        Toast.makeText(getBaseContext(), "onLongClick...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onXFooterClick(View view) {
        Toast.makeText(getBaseContext(), "loadMore error onClick", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                xRecyclerViewAdapter.refreshComplete(BaseRefreshHeader.STATE_DONE);
                Toast.makeText(getBaseContext(), "refresh...", Toast.LENGTH_SHORT).show();
            }
        }, 1500);
    }

    @Override
    public void onLoadMore() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                xRecyclerViewAdapter.loadMoreComplete(XFooterLayout.STATE_ERROR);
                Toast.makeText(getBaseContext(), "loadMore...", Toast.LENGTH_SHORT).show();
            }
        }, 1500);
    }
}
