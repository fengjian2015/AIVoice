package com.translation.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.translation.R;
import com.translation.androidlib.utils.TimeUtil;
import com.translation.component.base.BaseRcvAdapter;
import com.translation.model.entity.ChatMsg;

import java.util.List;

/**
 * Created by Darren on 2018/12/17.
 */
public class ConversationListRcvAdapter extends BaseRcvAdapter {

    private Context context;
    private List<ChatMsg> msgList;
    private boolean isEditMode;

    public ConversationListRcvAdapter(Context context, List<ChatMsg> msgList) {
        this.context = context;
        this.msgList = msgList;
    }

    public void setEditMode(boolean state) {
        isEditMode = state;
    }

    @Override
    protected Context getContext() {
        return context;
    }

    @Override
    protected List getObjectList() {
        return msgList;
    }

    @Override
    protected RecyclerView.ViewHolder getItemViewHolder(int viewType) {
        return new ItemViewHolder(View.inflate(context, R.layout.item_conversation_list, null));
    }

    @Override
    protected void bindViewData(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder iHolder = (ItemViewHolder) holder;
            iHolder.timeTv.setText(TimeUtil.timestampToStr(msgList.get(position).getTimestamp(), "HH:mm"));

            if (isEditMode) {
                iHolder.checkCb.setVisibility(View.VISIBLE);
                iHolder.checkCb.setChecked(msgList.get(position).isChecked());
            } else {
                iHolder.checkCb.setVisibility(View.GONE);
            }

            if (msgList.get(position).getTopMark() == 1) {
                iHolder.topMarkIv.setVisibility(View.VISIBLE);
            } else {
                iHolder.topMarkIv.setVisibility(View.INVISIBLE);
            }

            int offMsgNum = msgList.get(position).getOffMsgNum();
            if (offMsgNum == 0) {
                iHolder.unreadNumTv.setVisibility(View.INVISIBLE);
            } else {
                iHolder.unreadNumTv.setVisibility(View.VISIBLE);
                iHolder.unreadNumTv.setText(offMsgNum + "");
            }



            String friendNote = msgList.get(position).getFriendNote();
            if (TextUtils.isEmpty(friendNote)) {
                iHolder.nameTv.setText(msgList.get(position).getChatName());
            } else {
                iHolder.nameTv.setText(friendNote);
            }

            iHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.itemClick(position);
                }
            });
        }
    }

    private static class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView avatarCiv, topMarkIv, shieldAlphaIv, shieldIconIv;
        private TextView nameTv, contentTv, timeTv, unreadNumTv;
        private CheckBox checkCb;

        private ItemViewHolder(View itemView) {
            super(itemView);
            avatarCiv = itemView.findViewById(R.id.iv_item_conversation_avatar);
            nameTv = itemView.findViewById(R.id.tv_item_conversation_name);
            contentTv = itemView.findViewById(R.id.tv_item_conversation_message_content);
            timeTv = itemView.findViewById(R.id.tv_item_conversation_time);
            topMarkIv = itemView.findViewById(R.id.iv_item_conversation_top_mark);
            shieldAlphaIv = itemView.findViewById(R.id.iv_item_conversation_shield_alpha);
            shieldIconIv = itemView.findViewById(R.id.iv_item_conversation_shield_icon);
        }
    }

}
