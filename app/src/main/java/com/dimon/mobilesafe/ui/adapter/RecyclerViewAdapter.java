package com.dimon.mobilesafe.ui.adapter;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dimon.mobilesafe.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Dimon on 2016/3/20.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private  Context mContext;
    private  String[] Items ;
    private  int[] Pics ;

    public RecyclerViewAdapter(Context context,String[] mItems, int[] mPics) {

        this.mContext = context;
        this.Items = mItems;
        this.Pics = mPics;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_list_item, parent, false);
        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // 给ViewHolder设置元素

        holder.mTextView.setText(Items[position]);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            holder.mImageView.setImageDrawable(mContext.getDrawable(Pics[position]));
        }
    }


    @Override
    public int getItemCount() {
        // 返回数据总数
        return Items == null ? 0 : Items.length;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_list_item)
        TextView mTextView;
        @Bind(R.id.iv_list_item)
        ImageView mImageView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("ImageViewHolder", "onClick--> position = " + getPosition());
                }
            });
        }
    }
}
