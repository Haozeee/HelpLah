package com.example.helplah.models;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.cometchat.pro.constants.CometChatConstants;
import com.cometchat.pro.core.CometChat;
import com.cometchat.pro.exceptions.CometChatException;
import com.cometchat.pro.uikit.ui_components.messages.message_list.CometChatMessageListActivity;
import com.cometchat.pro.uikit.ui_resources.constants.UIKitConstants;

import java.util.Date;

public class ChatMessage {

    private static final String TAG = "Chat message";

    public static final String FIELD_DATE = "date";
    public static final String FIELD_STATUS = "status";

    public static final int STATUS_UNREAD = 1;
    public static final int STATUS_READ = 2;


    private String messageText;
    private String messageId;
    private String imageURL = null;
    private MessageAuthor user;
    private Date date;
    private int status;

    public ChatMessage() {}

    public ChatMessage(String senderId, String senderName, String messageText) {
        this.user = new MessageAuthor(senderId, senderName);
        this.messageText = messageText;
        this.date = new Date();
        this.user = new MessageAuthor(senderId, senderName);
    }

    public static void createChat(String receiverId, String receiverName, Context context) {
        com.cometchat.pro.models.User receiver = new com.cometchat.pro.models.User();
        receiver.setUid(receiverId);
        receiver.setName(receiverName);
        CometChat.getUser(receiverId, new CometChat.CallbackListener<com.cometchat.pro.models.User>() {
            @Override
            public void onSuccess(com.cometchat.pro.models.User user) {
                Log.d(TAG, "onSuccess: Found" + user.getName());
                Intent intent = new Intent(context, CometChatMessageListActivity.class);
                intent.putExtra(UIKitConstants.IntentStrings.UID, user.getUid());
                intent.putExtra(UIKitConstants.IntentStrings.AVATAR, user.getAvatar());
                intent.putExtra(UIKitConstants.IntentStrings.STATUS, user.getStatus());
                intent.putExtra(UIKitConstants.IntentStrings.NAME, user.getName());
                intent.putExtra(UIKitConstants.IntentStrings.LINK,user.getLink());
                intent.putExtra(UIKitConstants.IntentStrings.TYPE, CometChatConstants.RECEIVER_TYPE_USER);
                context.startActivity(intent);
            }

            @Override
            public void onError(CometChatException e) {
                Log.d(TAG, "onError: Failed. This user may be a mock data and has no account to chat with. " + e.getMessage());
            }
        });
    }

}