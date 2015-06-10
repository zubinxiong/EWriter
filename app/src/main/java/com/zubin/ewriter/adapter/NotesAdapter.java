package com.zubin.ewriter.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.zubin.ewriter.R;


/**
 * Created by zubin on 15/6/4.
 */
public class NotesAdapter extends BaseAdapter {

    private Context context;
    private Cursor cursor;
    private String more;

    public NotesAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public Object getItem(int position) {
        return cursor.getPosition();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.notes_item, null);
        TextView tvTitle = (TextView) v.findViewById(R.id.tv_title);
        TextView tvDate = (TextView) v.findViewById(R.id.tv_time);
        ImageView fileIcon = (ImageView) v.findViewById(R.id.file_icon);

        cursor.moveToPosition(position);
        String content = cursor.getString(cursor.getColumnIndex("content"));
        String time = cursor.getString(cursor.getColumnIndex("createdtime"));

        if (content.length() <= 14) {
            tvTitle.setText(content.replaceAll("\\s*|t|r|n",""));
        } else {
            more = "_(:з」∠)_";
            tvTitle.setText(content.substring(0, 14).replaceAll("\\s*|t|r|n ","") + more);
        }


        fileIcon.setImageResource(R.drawable.file_icon);
        tvDate.setText("最后修改时间:" + time);

        return v;
    }
}
