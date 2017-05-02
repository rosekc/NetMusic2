package com.android.netmusic.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.netmusic.R;
import com.android.netmusic.adapter.viewholder.FriendsItemHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 朋友列表Adapter
 * Created by Android on 2017/3/25.
 */

public class FriendsListAdapter extends BaseAdapter implements View.OnClickListener {
    private LayoutInflater inflater;
    public static List<String> mUserAccountList = new ArrayList<>();
    private FriendsItemHolder mFriendsItemHolder;
    private FriendsItemMoreClickListener mFriendsItemMoreClickListener;


    public FriendsListAdapter(Context context) {
        inflater = LayoutInflater.from(context);


    }

    @Override
    public int getCount() {
        return mUserAccountList.size();
    }

    @Override
    public Object getItem(int position) {
        return mUserAccountList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.e(position + "", mUserAccountList.get(position).toString());
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.view_friends_item, null);
            mFriendsItemHolder = new FriendsItemHolder();
            mFriendsItemHolder.headIcon = (ImageView) convertView.findViewById(R.id.friends_item_headicon);
            mFriendsItemHolder.name = (TextView) convertView.findViewById(R.id.friends_item_name);
            mFriendsItemHolder.more = (ImageView) convertView.findViewById(R.id.friends_item_more);
            convertView.setTag(mFriendsItemHolder);
        } else {
            mFriendsItemHolder = (FriendsItemHolder) convertView.getTag();
        }
        mFriendsItemHolder.name.setText(mUserAccountList.get(position).toString());
        //More点击事件
        mFriendsItemHolder.more.setOnClickListener(this);
        mFriendsItemHolder.more.setTag(position);

        return convertView;
    }

    public interface FriendsItemMoreClickListener {
        public abstract void clickMore(View v);
    }

    public void setOnFriendsItemMoreClickListener(FriendsItemMoreClickListener mFriendsItemMoreClickListener) {
        this.mFriendsItemMoreClickListener = mFriendsItemMoreClickListener;
    }

    @Override
    public void onClick(View v) {
        mFriendsItemMoreClickListener.clickMore(v);
    }
}
