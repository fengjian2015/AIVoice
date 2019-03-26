package com.translation.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.translation.R;

import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;

/**
 * Created by GA on 2018/1/5.
 */
public class MessageAdapter extends RecyclerView.Adapter {
    private int oldClick = -1;
    private final Context mContext;
    private final List<Map> mHashMapList;
    private OnItemClick mOnItemClick;

    public MessageAdapter(Context context, List<Map> mHashMapList) {
        mContext = context;
        this.mHashMapList = mHashMapList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_message, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setData(mHashMapList.get(position), position);
    }

    @Override
    public int getItemCount() {
        if (mHashMapList != null) {
            return mHashMapList.size();
        }
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        int position;
        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (oldClick == position) {
                        oldClick = -1;
                    } else {
                        oldClick = position;
                    }
                    notifyDataSetChanged();
                }
            });
        }

        public void setData(final Map map, int position) {

        }
    }

    public void setOnItemClick(OnItemClick onItemClick){
        this.mOnItemClick=onItemClick;
    }

    public interface OnItemClick{
        void click(int id);
    }
}
