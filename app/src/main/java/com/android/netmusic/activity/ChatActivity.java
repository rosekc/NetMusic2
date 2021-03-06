package com.android.netmusic.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.android.netmusic.R;
import com.android.netmusic.adapter.ChatMsgAdapter;
import com.android.netmusic.utils.InputMethodUtils;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.android.netmusic.adapter.ChatMsgAdapter.emojiId;
import static com.android.netmusic.adapter.ChatMsgAdapter.emojiName;
import static com.android.netmusic.constant.Constant.MSG_LIST_UPDATE;

/**
 * 聊天页面
 */
public class ChatActivity extends BaseActivity {
    private EditText msgEditText;
    private ImageView showEmoiconImageView;
    private Button sendBtn;
    private GridView emoiconGridView;
    private ListView messageListView;
    private String contactName = "b";
    private EMConversation conversation;
    private ChatMsgAdapter chatMsgAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent intent = getIntent();
        contactName = intent.getStringExtra("useraccount");

        //设置ActionBar
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(contactName);
        init();
    }

    @Override
    public void publish(int progress) {

    }

    @Override
    public void change(int position) {

    }

    @Override
    public void changeForState(int position) {

    }

    //聊天消息监听
    EMMessageListener msgListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            //收到消息
            for (EMMessage message : messages) {
                if (message.getUserName() == contactName) {
                    Message msg = mHandler.obtainMessage();
                    msg.what = MSG_LIST_UPDATE;
                    msg.obj = message;
                    mHandler.sendMessage(msg);
                }
            }
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            //收到透传消息
        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {
            //收到已读回执
        }

        @Override
        public void onMessageDelivered(List<EMMessage> message) {
            //收到已送达回执
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
            //消息状态变动
        }
    };

    private void init() {
        conversation = EMClient.getInstance().chatManager().getConversation(contactName, null, true);
        if (conversation != null) {
            conversation.markAllMessagesAsRead();
        }

        msgEditText = (EditText) findViewById(R.id.lqm_chat_edit_msg);
        showEmoiconImageView = (ImageView) findViewById(R.id.lqm_chat_iv_showemoicon);
        sendBtn = (Button) findViewById(R.id.lqm_chat_btn_send);
        MyOnClickListener ml = new MyOnClickListener();
        sendBtn.setOnClickListener(ml);
        showEmoiconImageView.setOnClickListener(ml);


        emoiconGridView = (GridView) findViewById(R.id.lqm_chat_gv_emoticon);
        messageListView = (ListView) findViewById(R.id.lqm_chat_lv_message);
        EMClient.getInstance().chatManager().addMessageListener(msgListener);

        chatMsgAdapter = new ChatMsgAdapter(this, contactName, conversation);
        messageListView.setAdapter(chatMsgAdapter);


        // 表情框
        List<Map<String, Object>> emojiList = new ArrayList<Map<String, Object>>();
        Map<String, Object> emojiMap = new HashMap<String, Object>();
        for (int i = 0; i < emojiId.length; i++) {
            emojiMap.put("emoji", emojiId[i]);
            emojiList.add(emojiMap);
            emojiMap = new HashMap<String, Object>();
        }

        SimpleAdapter emojiAdapter = new SimpleAdapter(this, emojiList, R.layout.view_emoji,
                new String[]{"emoji"}, new int[]{R.id.iv_emoji});
        emoiconGridView.setAdapter(emojiAdapter);
        emoiconGridView.setOnItemClickListener(new OnEmojiClickListener());
        // 隐藏表情框
        hideEmotionPanel();
    }

    /**
     * 自定义实现Handler，主要用于刷新UI操作
     */
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LIST_UPDATE:
                    // 将新的消息内容和时间加入到下边
                    //更新adapter中信息数据
                    chatMsgAdapter.notifyDataSetChanged();
                    //设置聊天消息位置(有新信息过来，向下滑动)
                    messageListView.setSelection(messageListView.getCount() - 1);
                    // mContentText.setText(mContentText.getText() + "\n" + body.getMessage() + " <- " + message.getMsgTime());
                    break;
            }
        }
    };

    /**
     * 发送信息
     */
    public void sendMsg() {
        //contactName = editContactName.getText().toString();

        if (msgEditText.getText().toString().length() != 0) {
            //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
            EMMessage message = EMMessage.createTxtSendMessage(msgEditText.getText().toString(), contactName);
            //如果是群聊，设置chattype，默认是单聊

            //发送消息
            EMClient.getInstance().chatManager().sendMessage(message);

            Message msg = mHandler.obtainMessage();
            msg.what = MSG_LIST_UPDATE;
            msg.obj = message;
            mHandler.sendMessage(msg);
        }
    }

    /**
     * 删除文字或表情
     */
    public void deleteMsg() {
        if (msgEditText.getText().length() != 0) {
            // 选择文本的游标位置 全选 或 部分选择 或没选
            int iCursorEnd = Selection.getSelectionEnd(msgEditText.getText());
            int iCursorStart = Selection.getSelectionStart(msgEditText.getText());
            // 输入框中有字符
            if (iCursorEnd > 0) {
                if (iCursorEnd == iCursorStart) {
                    if (isDeletePng(iCursorEnd)) {
                        ((Editable) msgEditText.getText()).delete(iCursorEnd - emojiName[0].length(), iCursorEnd);

                    } else {
                        ((Editable) msgEditText.getText()).delete(iCursorEnd - 1, iCursorEnd);
                    }
                }
                // 输入框中无字符
                else {
                    ((Editable) msgEditText.getText()).delete(iCursorEnd, iCursorEnd);
                }
            }
        }
    }

    /**
     * 判断是否删除表情
     *
     * @param cursor
     * @return
     */
    public boolean isDeletePng(int cursor) {
        String content = msgEditText.getText().toString().substring(0, cursor);
        if (content.length() >= emojiName[0].length()) {
            // 截取与表情字符串相同长度的字符
            String checkStr = content.substring(content.length() - emojiName[0].length(), content.length());
            // 正则表达式 需要匹配的字符串标志
            Pattern p = Pattern.compile("\\[.+?\\]");
            // 判断刚截取的字符串
            Matcher m = p.matcher(checkStr);
            return m.matches();
        }
        return false;
    }

    class MyOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.lqm_chat_btn_send:
                    sendMsg();
                    break;
                case R.id.lqm_chat_iv_showemoicon:
                    if (emoiconGridView.getVisibility() == View.VISIBLE) {
                        hideEmotionPanel();
                        showEmoiconImageView.setImageResource(R.mipmap.ic_keyboard);
                    } else if (emoiconGridView.getVisibility() == View.GONE) {
                        showEmotionPanel();
                        //表情按钮
                        showEmoiconImageView.setImageResource(R.mipmap.ic_showemoji);
                      }
                    break;
            }
        }
    }

    class MsgEditWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    class OnEmojiClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (position == emojiId.length - 1) {
                deleteMsg();
            } else {
                Log.i("���", position + "");
                Editable edit = msgEditText.getEditableText();

                Drawable drawable = getResources().getDrawable(emojiId[position]);
                //表情宽高大小
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth() * 4 / 5, drawable.getIntrinsicHeight() * 4 / 5);
                //需要处理的文本
                SpannableString spannable = new SpannableString(emojiName[position]);
                //使用ImageSpan替换指定文字
                ImageSpan span = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
                //开始替换
                Log.i("Span", span.toString());
                spannable.setSpan(span, 0, emojiName[position].length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                // contentEdit.setText(spannable);
                int index = msgEditText.getSelectionStart();
                //在指定文字位置插入图片
                edit.insert(index, spannable);
                // MainActivity.contentEdit.append(spannable);
            }
        }
    }

    //--------------------------表情面板相关---------------------------------------------------------------------
    public boolean isEmotionPanelShowing() {
        return emoiconGridView.getVisibility() == View.VISIBLE;
    }

    /**
     * 显示表情面板
     */
    public void showEmotionPanel() {
        emoiconGridView.removeCallbacks(mHideEmotionPanelTask);
        InputMethodUtils.updateSoftInputMethod(this, WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        // showEmoiconImageView.setBackgroundResource(R.drawable.expression2);
        emoiconGridView.setVisibility(View.VISIBLE);
        InputMethodUtils.hideKeyboard(getCurrentFocus());
    }

    /**
     * 隐藏表情面板
     */
    public void hideEmotionPanel() {
        if (emoiconGridView.getVisibility() != View.GONE) {
            // showEmoiconImageView.setBackgroundResource(R.drawable.expression1);
            emoiconGridView.setVisibility(View.GONE);
            InputMethodUtils.updateSoftInputMethod(this, WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        }
    }

    /**
     * 更新表情面板高度
     *
     * @param keyboardHeight
     */
    public void updateEmotionPanelHeight(int keyboardHeight) {
        ViewGroup.LayoutParams params = emoiconGridView.getLayoutParams();
        if (params != null && params.height != keyboardHeight) {
            params.height = keyboardHeight;
            emoiconGridView.setLayoutParams(params);
        }
    }

    private Runnable mHideEmotionPanelTask = new Runnable() {
        @Override
        public void run() {
            hideEmotionPanel();
        }
    };
    //---------------------------------------------------------------------------------------------------------


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //移除监听
        EMClient.getInstance().chatManager().removeMessageListener(msgListener);
    }
}