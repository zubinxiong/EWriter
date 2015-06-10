package com.zubin.ewriter.ui.contents;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.melnykov.fab.FloatingActionButton;
import com.wangjie.wavecompat.WaveCompat;
import com.wangjie.wavecompat.WaveDrawable;
import com.wangjie.wavecompat.WaveTouchHelper;
import com.zubin.ewriter.R;
import com.zubin.ewriter.adapter.NotesAdapter;
import com.zubin.ewriter.data.WriterDB;
import com.zubin.ewriter.ui.WriterActivity;


/**
 * Created by zubin on 15/6/4.
 */
public class ContentFragment extends Fragment implements AdapterView.OnItemClickListener, WaveTouchHelper.OnWaveTouchHelperListener {

    private ListView mListView;
    private NotesAdapter mNotesAdapter;
    private WriterDB writerDB;
    private SQLiteDatabase dbReader;
    private SQLiteDatabase dbWriter;
    private Cursor cursor;
    private FloatingActionButton fab;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.conten_fragment, container, false);

        mListView = (ListView) v.findViewById(R.id.notes_listview);
        registerForContextMenu(mListView);

        fab = (FloatingActionButton) v.findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), WriterActivity.class);
//                startActivity(intent);
//            }
//        });

        fab.attachToListView(mListView);
        WaveTouchHelper.bindWaveTouchHelper(fab, this);

        writerDB = new WriterDB(getActivity());
        dbReader = writerDB.getReadableDatabase();

        return v;
    }

    public void selectDB() {
        cursor = dbReader.query(WriterDB.EWRITER_TABLE_NAME, null, null, null, null, null, null, null);
        mNotesAdapter = new NotesAdapter(getActivity(), cursor);
        mListView.setAdapter(mNotesAdapter);

        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        selectDB();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        cursor.moveToPosition(position);
        Intent intent = new Intent(getActivity(), WriterActivity.class);
        intent.putExtra(WriterDB.ID, cursor.getInt(cursor.getColumnIndex("_id")));
        intent.putExtra(WriterDB.CONTENT, cursor.getString(cursor.getColumnIndex("content")));
        intent.putExtra(WriterDB.STARTED, cursor.getInt(cursor.getColumnIndex("started")));

        startActivity(intent);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.list_item_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;

        switch (item.getItemId()) {
            case R.id.menu_item_delete:
                cursor.moveToPosition(position);
                dbWriter = writerDB.getWritableDatabase();
                int id = cursor.getInt(cursor.getColumnIndex("_id"));
                dbWriter.delete(WriterDB.EWRITER_TABLE_NAME, "_id=?", new String[]{String.valueOf(id)});
                selectDB();
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public void onWaveTouchUp(View view, Point locationInView, Point locationInScreen) {
        WaveCompat.startWaveFilter(getActivity(), new WaveDrawable()
                .setColor(getResources().getColor(R.color.colorPrimary))
                .setTouchPoint(locationInScreen), new Intent(getActivity(), WriterActivity.class));
    }
}
