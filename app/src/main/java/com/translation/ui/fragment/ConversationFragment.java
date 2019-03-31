package com.translation.ui.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.translation.R;
import com.translation.androidlib.utils.LogUtil;
import com.translation.component.base.BaseFragment;
import com.translation.component.base.BaseRcvAdapter;
import com.translation.component.constant.ContactCons;
import com.translation.model.db.dao.ConversationDao;
import com.translation.model.entity.ChatMsg;
import com.translation.model.entity.FriendInfo;
import com.translation.ui.activity.MainChatActivity;
import com.translation.ui.adapter.ConversationListRcvAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConversationFragment extends BaseFragment {

    private RecyclerView containerRcv;
    private ConversationListRcvAdapter rcvAdapter;
    private List<ChatMsg> msgList = new ArrayList<>();
    private FriendInfo chatInfo;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_conversation;
    }

    @Override
    protected void initViews() {
        containerRcv = parentView.findViewById(R.id.rcv_conversation_container);
    }

    @Override
    protected void initViewsData() {
        initRecyclerView();
        setViewByCache();
    }

    private void initRecyclerView() {
        rcvAdapter = new ConversationListRcvAdapter(getHostActivity(), msgList);
        final LinearLayoutManager manager = new LinearLayoutManager(getHostActivity());
        containerRcv.setAdapter(rcvAdapter);
        containerRcv.setLayoutManager(manager);
        rcvAdapter.setOnItemClickListener(new BaseRcvAdapter.OnItemClickListener() {
            @Override
            public void itemClick(int position) {
                ChatMsg chatMsg = msgList.get(position);
                chatInfo = new FriendInfo();
//                chatInfo.setId(chatMsg.getChatId());
                chatInfo.setUsername(chatMsg.getChatName());
                Intent intent = new Intent(getHostActivity(), MainChatActivity.class);
                intent.putExtra(ContactCons.EXTRA_CONTACT_FRIEND_INFO, chatInfo);
                if (chatMsg.getOffMsgNum() > 0) {
                    intent.putExtra(ContactCons.EXTRA_MESSAGE_UNREAD_COUNT, chatMsg.getOffMsgNum());
                    startActivityForResult(intent, ContactCons.REQ_CONVERSATION_LIST_CLICK);
                } else {
                    startActivity(intent);
                }

            }
        });
    }

    private void updateRcv(){
        if (rcvAdapter != null) {
            Collections.sort(msgList, new ChatMsg.TopComparator());
            rcvAdapter.notifyDataSetChanged();
        }
    }

    private void setViewByCache() {
        List<ChatMsg> msgDataList = ConversationDao.getConversationList(getHostActivity());
        LogUtil.i("setViewByCache", msgDataList);
        if (msgDataList != null) {
            msgList.clear();
            msgList.addAll(msgDataList);
            updateRcv();
        }
    }


}
