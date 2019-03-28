package com.translation.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.translation.R;
import com.translation.component.base.BaseRcvAdapter;
import com.translation.model.entity.FriendInfo;
import java.util.List;

public class ContactRcvAdapter extends BaseRcvAdapter {

    private Context context;
    private List<FriendInfo> friendInfoList;

    public ContactRcvAdapter(Context context, List<FriendInfo> friendInfoList){
        this.context = context;
        this.friendInfoList = friendInfoList;
    }


    @Override
    protected Context getContext() {
        return context;
    }

    @Override
    protected List getObjectList() {
        return friendInfoList;
    }

    @Override
    protected RecyclerView.ViewHolder getItemViewHolder(int viewType) {
        return new ItemViewHolder(View.inflate(context, R.layout.item_contact_friend_list, null));
    }

    @Override
    protected void bindViewData(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ItemViewHolder){
            ItemViewHolder iHolder = (ItemViewHolder)holder;
            iHolder.nameTv.setText(friendInfoList.get(position).getNickname());

            iHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemClickListener != null){
                        itemClickListener.itemClick(position);
                    }
                }
            });

        }
    }

    private static class ItemViewHolder extends RecyclerView.ViewHolder{
        private TextView nameTv;

        private ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.tv_item_contact_friend_name);
        }
    }

}
