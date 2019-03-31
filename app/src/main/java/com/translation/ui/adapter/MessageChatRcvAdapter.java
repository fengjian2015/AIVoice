package com.translation.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.translation.R;
import com.translation.component.base.BaseRcvTopLoadAdapter;
import com.translation.model.entity.ChatMsg;

import java.util.List;

public class MessageChatRcvAdapter extends BaseRcvTopLoadAdapter {

    private Context context;
    private List<ChatMsg> chatMsgList;
    private OnMsgClickListener msgClickListener;
    private OnAvatarClickListener avatarClickListener;


    public MessageChatRcvAdapter(Context context, List<ChatMsg> chatMsgList) {
        this.context = context;
        this.chatMsgList = chatMsgList;
    }

    public interface OnMsgClickListener {
        void voiceRecordClick(int position);//点击录音
    }

    public void setOnMsgClickListener(OnMsgClickListener msgClickListener) {
        this.msgClickListener = msgClickListener;
    }

    public interface OnAvatarClickListener {
        void avatarClick(int position);
    }

    public void setOnAvatarClickListener(OnAvatarClickListener avatarClickListener) {
        this.avatarClickListener = avatarClickListener;
    }

    @Override
    protected Context getContext() {
        return context;
    }

    @Override
    protected List getObjectList() {
        return chatMsgList;
    }

    @Override
    protected RecyclerView.ViewHolder getItemViewHolder(int viewType) {
        return new ItemViewHolder(View.inflate(context, R.layout.item_conversaton_chat, null));
    }

    @Override
    protected void bindViewData(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder iHolder = (ItemViewHolder) holder;

            if (chatMsgList.get(position).isMine()) {
                iHolder.leftContentTv.setVisibility(View.GONE);
                iHolder.leftVoiceRecordLl.setVisibility(View.GONE);

                if (chatMsgList.get(position).getContentType() == ChatMsg.TYPE_CONTENT_TEXT) {
                    iHolder.rightContentTv.setVisibility(View.VISIBLE);
                    iHolder.rightVoiceRecordLl.setVisibility(View.GONE);
                } else if (chatMsgList.get(position).getContentType() == ChatMsg.TYPE_CONTENT_TAPE_RECORD) {
                    iHolder.rightContentTv.setVisibility(View.GONE);
                    iHolder.rightVoiceRecordLl.setVisibility(View.VISIBLE);
                }
            } else {
                iHolder.rightContentTv.setVisibility(View.GONE);
                iHolder.rightVoiceRecordLl.setVisibility(View.GONE);

                if (chatMsgList.get(position).getContentType() == ChatMsg.TYPE_CONTENT_TEXT) {
                    iHolder.leftContentTv.setVisibility(View.VISIBLE);
                    iHolder.leftVoiceRecordLl.setVisibility(View.GONE);
                } else if (chatMsgList.get(position).getContentType() == ChatMsg.TYPE_CONTENT_TAPE_RECORD) {
                    iHolder.leftContentTv.setVisibility(View.GONE);
                    iHolder.leftVoiceRecordLl.setVisibility(View.VISIBLE);
                }
            }


        }
    }

    private static class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView leftContentTv, rightContentTv;
        LinearLayout leftVoiceRecordLl, rightVoiceRecordLl;


        private ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            leftContentTv = itemView.findViewById(R.id.tv_conversation_chat_left_content);
            leftVoiceRecordLl = itemView.findViewById(R.id.ll_message_chat_voice_record_container_left);
            rightContentTv = itemView.findViewById(R.id.tv_message_chat_right_content);
            rightVoiceRecordLl = itemView.findViewById(R.id.ll_message_chat_voice_record_container_right);
        }
    }

}
