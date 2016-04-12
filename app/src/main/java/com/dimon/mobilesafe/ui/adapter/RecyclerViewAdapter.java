package com.dimon.mobilesafe.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dimon.mobilesafe.R;
import com.socks.library.KLog;

/**
 * Created by Dimon on 2016/3/20.
 */
public abstract class RecyclerViewAdapter extends RecyclerView.Adapter<MyRecycleHolder> {

    private Context mContext;
    private String[] Items;
    private LayoutInflater mInflater;
    private int[] Pics;

    private OnItemClickListener onItemClickListener;

    public RecyclerViewAdapter(Context context, String[] mItems, int[] mPics) {

        this.mContext = context;
        this.Items = mItems;
        this.Pics = mPics;
        mInflater = LayoutInflater.from(mContext);
    }


    @Override
    public MyRecycleHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new MyRecycleHolder(mInflater.inflate(R.layout.home_list_item, parent, false));


    }

    @Override
    public void onBindViewHolder(final MyRecycleHolder holder, final int position) {
        convert(holder, position);
//        // 给ViewHolder设置元素
//        holder.mTextView.setText(Items[position]);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            holder.mImageView.setImageDrawable(mContext.getDrawable(Pics[position]));
//        }
        if (onItemClickListener != null){
            holder.itemView.setBackgroundResource(R.drawable.touch_bg);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //注意，这里的position不要用上面参数中的position，会出现位置错乱
                    onItemClickListener.OnItemClickListener(holder.itemView, holder.getLayoutPosition());
                    Log.d("ImageViewHolder", "onClick--> position ");
                }
            });
        }
    }

    public abstract void convert(MyRecycleHolder holder, int position);
    @Override
    public int getItemCount() {
        // 返回数据总数
        return Items == null ? 0 : Items.length;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClickListener(View view, int position);
    }
}
