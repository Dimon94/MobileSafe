package com.dimon.mobilesafe.ui.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dimon.mobilesafe.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Dimon on 2016/4/12.
 */
public class MyRecycleHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.tv_list_item)
    TextView mTextView;
    @Bind(R.id.iv_list_item)
    ImageView mImageView;
    /**
     * 用于存储当前item当中的View
     */
    private SparseArray<View> mViews;

    public MyRecycleHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mViews = new SparseArray<View>();
    }

    public MyRecycleHolder setText( String text) {
        mTextView.setText(text);
        return this;
    }

    public MyRecycleHolder setText( int text) {
        mTextView.setText(text);
        return this;
    }

    public MyRecycleHolder setImageResource( int ImageId) {
        mImageView.setImageResource(ImageId);
        return this;
    }

    public MyRecycleHolder setImageBitmap( Bitmap bitmap) {
        mImageView.setImageBitmap(bitmap);
        return this;
    }

    public MyRecycleHolder setImageNet( String url) {
        //使用你所用的网络框架等
        return this;
    }
}
