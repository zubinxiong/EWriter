package com.zubin.ewriter.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zubin.ewriter.R;

/**
 * Created by zubin on 15/6/2.
 */
public class NavigationAdapter extends RecyclerView.Adapter<NavigationAdapter.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private String mNavTitles[];
    private int mIcons[];


    onItemClickListener mItemClickListener;

    public NavigationAdapter(String[] titles, int[] icons) {
        mNavTitles = titles;
        mIcons = icons;
    }

    @Override
    public NavigationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_item, parent, false);

            ViewHolder vhItem = new ViewHolder(v, viewType);

            return vhItem;
        } else if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_header, parent, false);

            ViewHolder vhHeader = new ViewHolder(v, viewType);

            return vhHeader;
        }

        return null;
    }

    @Override
    public void onBindViewHolder(NavigationAdapter.ViewHolder holder, int position) {
        if (holder.Holderid == 1) {
            holder.textView.setText(mNavTitles[position - 1]);
            holder.imageView.setImageResource(mIcons[position - 1]);
        } else {

        }
    }

    @Override
    public int getItemCount() {
        // 加上Header的长度
        return mNavTitles.length + 1;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        int Holderid;
        TextView textView;
        ImageView imageView;

        public ViewHolder(View itemView, int ViewType) {
            super(itemView);

            itemView.setClickable(true);
            itemView.setOnClickListener(this);

            if (ViewType == TYPE_ITEM) {
                textView = (TextView) itemView.findViewById(R.id.rowText);
                imageView = (ImageView) itemView.findViewById(R.id.rowIcon);
                Holderid = 1;
            } else {
                Holderid = 0;
            }
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getPosition());
            }
        }
    }

    // RecyclerView 的监听事件
    public interface onItemClickListener {
        public void onItemClick(View view , int position);
    }

    public void setOnItemClickListener(final onItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position)) {
            return TYPE_HEADER;
        } else {
            return TYPE_ITEM;
        }
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

}
