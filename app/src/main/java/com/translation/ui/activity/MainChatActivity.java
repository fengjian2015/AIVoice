package com.translation.ui.activity;

import com.translation.R;
import com.translation.component.base.BaseActivity;
import com.translation.component.constant.ContactCons;
import com.translation.model.entity.FriendInfo;

public class MainChatActivity extends BaseActivity {


    private FriendInfo friendInfo;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_conversation_chat;
    }

    @Override
    protected void getIntentData() {
        friendInfo = (FriendInfo)getIntent().getSerializableExtra(ContactCons.CONTACT_FRIEND);
    }

    @Override
    protected void initViewsData() {
        setTitle(true, friendInfo.getNickname());

    }


}
