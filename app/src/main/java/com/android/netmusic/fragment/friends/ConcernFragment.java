package com.android.netmusic.fragment.friends;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.netmusic.R;
import com.android.netmusic.activity.ChatActivity;
import com.android.netmusic.adapter.FriendsListAdapter;
import com.hyphenate.EMContactListener;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMClient;

import java.util.List;

import static android.R.id.message;
import static com.android.netmusic.adapter.FriendsListAdapter.mUserAccountList;
import static com.android.netmusic.constant.Constant.FRIENDS_LIST_UPDATE;

/**
 * 我的好友、关心页面
 * Created by Android on 2017/3/26.
 */

public class ConcernFragment extends Fragment {
    private View view;
    private FriendsListAdapter mFriendsListAdapter;
    private ListView mFriendsListView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_friends_concern, null);

        init();

        return view;
    }

    private void init() {
        mFriendsListView = (ListView) view.findViewById(R.id.friends_listview);
        mFriendsListAdapter = new FriendsListAdapter(getContext());
        mFriendsListView.setAdapter(mFriendsListAdapter);
        mFriendsListView.setOnItemClickListener(new FriendsItemShortClickListenr());
        //好友信息监听、添加好友
        EMClient.getInstance().contactManager().setContactListener(new EMContactListener() {

            @Override
            public void onContactInvited(String username, String reason) {
                //收到好友邀请
            }

            @Override
            public void onFriendRequestAccepted(String s) {
                Message msg = mHandler.obtainMessage();
                msg.what = FRIENDS_LIST_UPDATE;
                msg.obj = message;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onFriendRequestDeclined(String s) {

            }

            @Override
            public void onContactDeleted(String username) {
                //被删除时回调此方法
            }


            @Override
            public void onContactAdded(String username) {
                //增加了联系人时回调此方法
            }
        });
    }

    /**
     * 自定义实现Handler，主要用于刷新UI操作
     */
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case FRIENDS_LIST_UPDATE:
                    //更新好友姓名信息

                    EMClient.getInstance().contactManager().aysncGetAllContactsFromServer(new EMValueCallBack<List<String>>() {
                        @Override
                        public void onSuccess(List<String> strings) {
                            mUserAccountList = strings;
                        }

                        @Override
                        public void onError(int i, String s) {

                        }
                    });


                    //更新adapter中信息数据
                    //刷新列表
                    mFriendsListAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    class FriendsItemShortClickListenr implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(getContext(), "好友:"+mUserAccountList.get(position), Toast.LENGTH_LONG).show();

            Intent intent = new Intent(getActivity(), ChatActivity.class);
            intent.putExtra("useraccount", mUserAccountList.get(position));
           startActivity(intent);

        }
    }
}
