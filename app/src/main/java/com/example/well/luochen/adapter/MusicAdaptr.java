package com.example.well.luochen.adapter;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.well.luochen.R;
import com.example.well.luochen.mode.fragment.MusicFragment;

/**
 * Created by Well on 2016/7/28.
 */
public class MusicAdaptr extends BaseAdapter {

    private MusicFragment mMusicFragment;

    public MusicAdaptr(MusicFragment musicFragment) {
        mMusicFragment = musicFragment;
    }

    @Override
    public int getCount() {
        return mMusicFragment.imageList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (null == convertView) {
            holder = new ViewHolder();
            convertView = View.inflate(mMusicFragment.mMainActivity, R.layout.item_music, null);
            holder.iv = (ImageView) convertView.findViewById(R.id.iv);
            holder.tv = (TextView) convertView.findViewById(R.id.tv);
            holder.ll_root = (RelativeLayout) convertView.findViewById(R.id.ll_root);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.ll_root.setBackgroundColor(Color.parseColor(mMusicFragment.colors[position]));
        Integer integer = mMusicFragment.imageList.get(position);
        String s = mMusicFragment.textList.get(position);

        holder.iv.setImageResource(integer);
        holder.tv.setText(s);

        return convertView;
    }

    class ViewHolder {
        ImageView iv;
        TextView tv;
        RelativeLayout ll_root;
    }
}
