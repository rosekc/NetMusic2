package com.android.netmusic.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.netmusic.R;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 聊天信息Adapter
 * Created by Android on 2017/3/24.
 */


public class ChatMsgAdapter extends BaseAdapter {
    private EMConversation conversation;
    private Context context;
    private LayoutInflater inflater;
    public static int[] emojiId = {
            R.mipmap.emoji_352, R.mipmap.emoji_353, R.mipmap.emoji_354, R.mipmap.emoji_355, R.mipmap.emoji_356,
            R.mipmap.emoji_357, R.mipmap.emoji_358, R.mipmap.emoji_359, R.mipmap.emoji_360, R.mipmap.emoji_361,
            R.mipmap.emoji_362, R.mipmap.emoji_363, R.mipmap.emoji_364, R.mipmap.emoji_365, R.mipmap.emoji_366,
            R.mipmap.emoji_367, R.mipmap.emoji_368, R.mipmap.emoji_369, R.mipmap.emoji_370, R.mipmap.emoji_371,
            R.mipmap.emoji_372, R.mipmap.emoji_373, R.mipmap.emoji_374, R.mipmap.emoji_375, R.mipmap.emoji_376,
            R.mipmap.emoji_377, R.mipmap.emoji_378, R.mipmap.emoji_delete};
    public static String[] emojiName = {
            "[emoji_352]","[emoji_353]","[emoji_354]","[emoji_355]","[emoji_356]","[emoji_357]",
            "[emoji_358]","[emoji_359]","[emoji_360]","[emoji_361]","[emoji_362]","[emoji_363]",
            "[emoji_364]","[emoji_365]","[emoji_366]","[emoji_367]","[emoji_368]","[emoji_369]",
            "[emoji_370]", "[emoji_371]","[emoji_372]","[emoji_373]","[emoji_374]","[emoji_375]",
            "[emoji_376]", "[emoji_377]","[emoji_378]","[emoji_del]"};
    private List<EMMessage> messages;

    public ChatMsgAdapter(Context context, String contactName, EMConversation conversation) {
        this.context = context;
        this.conversation = conversation;// EMClient.getInstance().chatManager().getConversation(userName);
        inflater = LayoutInflater.from(context);

        int count = conversation.getAllMessages().size();
        if (count < conversation.getAllMsgCount() && count < 20) {
            // 获取已经在列表中的最上边的一条消息id
            String msgId = conversation.getAllMessages().get(0).getMsgId();
            // 分页加载更多消息，需要传递已经加载的消息的最上边一条消息的id，以及需要加载的消息的条数
            conversation.loadMoreMsgFromDB(msgId, 20 - count);
        }



    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return conversation.getAllMessages().size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return conversation.getAllMessages().get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //确保messages时刻刷新
        messages = conversation.getAllMessages();
        Log.e("---------",messages.size() + "----" + position);
            EMTextMessageBody body = (EMTextMessageBody) messages.get(position).getBody();
            //     TextMessageBody body = (TextMessageBody) message.getBody();
            if (messages.get(position).direct() == EMMessage.Direct.RECEIVE) {
                if (messages.get(position).getType() == EMMessage.Type.TXT) {
                    convertView = inflater.inflate(R.layout.view_chat_msgitem_left, null);
                    // textViewName = (TextView)
                    // convertView.findViewById(R.id.textName);
                    // textViewName.setText(message.getFrom());
                }
            } else {
                if (messages.get(position).getType() == EMMessage.Type.TXT) {
                    convertView = inflater.inflate(R.layout.view_chat_msgitem_right, null);
                }
            }

            TextView textViewContent = (TextView) convertView.findViewById(R.id.chat_msg_content);
            //
            // ����Ҫƥ��ı���ؼ���
            Pattern pattern = Pattern.compile("\\[.+?\\]");
            SpannableString ss = new SpannableString(body.getMessage());

            Matcher matcher = pattern.matcher(ss);
            while (matcher.find()) {
                //Log.i("�ҵ�����", "true");
                // int emojiPosition = check(matcher.group());
                String tempText = matcher.group();
                int emojiPosition = 0;// emoji�������е�λ��
                for (int i = 0; i < emojiName.length; i++) {
                    if (tempText.equals(emojiName[i])) {
                        emojiPosition = i;
                        //Log.i("ƥ�����", "true");
                        break;
                    }
                }
                // �õ�ͼƬ��id
                Drawable d = context.getResources().getDrawable(emojiId[emojiPosition]);
                // ����ͼƬ
                d.setBounds(0, 0, d.getIntrinsicWidth() * 4 / 5, d.getIntrinsicHeight() * 4 / 5);
                // Ҫ��ͼƬ���ָ�������־�Ҫ��ImageSpan
                ImageSpan is = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);

                ss.setSpan(is, matcher.start(), matcher.end(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            }
            textViewContent.setText(ss);
            return convertView;
    }


}

