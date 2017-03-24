package com.android.netmusic.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
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
    public static int[] emojiId = {R.mipmap.emoji_1f600, R.mipmap.emoji_1f601, R.mipmap.emoji_1f602,
            R.mipmap.emoji_1f603, R.mipmap.emoji_1f604, R.mipmap.emoji_1f605, R.mipmap.emoji_1f606,
            R.mipmap.emoji_1f607, R.mipmap.emoji_1f608, R.mipmap.emoji_1f609, R.mipmap.emoji_1f610,
            R.mipmap.emoji_1f611, R.mipmap.emoji_1f612, R.mipmap.emoji_1f612, R.mipmap.emoji_1f614,
            R.mipmap.emoji_1f615, R.mipmap.emoji_1f616, R.mipmap.emoji_1f617, R.mipmap.emoji_1f618,
            R.mipmap.emoji_1f619, R.mipmap.emoji_1f620, R.mipmap.emoji_352, R.mipmap.emoji_353,
            R.mipmap.emoji_354, R.mipmap.emoji_355, R.mipmap.emoji_356, R.mipmap.emoji_357,
            R.mipmap.emoji_delete};
    public static String[] emojiName = {"[emoji_1f600]", "[emoji_1f601]", "[emoji_1f602]", "[emoji_1f603]", " [emoji_1f604]",
            "[emoji_1f605]", "[emoji_1f606]", "[emoji_1f607]", "[emoji_1f608]", "[emoji_1f609]", "[emoji_1f610]",
            "[emoji_1f611]", "[emoji_1f612]", "[emoji_1f612]", "[emoji_1f614]", "[emoji_1f615]", "[emoji_1f616]",
            "[emoji_1f617]", "[emoji_1f618]", "[emoji_1f619]", "[emoji_1f620]", "[emoji_352]", "[emoji_353]", "[emoji_354]",
            "[emoji_355]", "[emoji_356]", "[emoji_357]", "[emoji_delete]"};
    private List<EMMessage> messages;

    public ChatMsgAdapter(Context context, String contactName, EMConversation conversation) {
        this.context = context;
        this.conversation = conversation;// EMClient.getInstance().chatManager().getConversation(userName);
        if(conversation!=null)
        messages = conversation.getAllMessages();
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
        if (conversation != null) {

            EMTextMessageBody body = (EMTextMessageBody) messages.get(position).getBody();
            //     TextMessageBody body = (TextMessageBody) message.getBody();
            if (messages.get(position).direct() == EMMessage.Direct.RECEIVE) {
                if (messages.get(position).getType() == EMMessage.Type.TXT) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.view_chat_msgitem_left, null);
                    // textViewName = (TextView)
                    // convertView.findViewById(R.id.textName);
                    // textViewName.setText(message.getFrom());
                }
            } else {
                if (messages.get(position).getType() == EMMessage.Type.TXT) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.view_chat_msgitem_right, null);
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
        return null;
    }
}

