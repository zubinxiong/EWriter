package com.zubin.ewriter.ui;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.zubin.ewriter.R;
import com.zubin.ewriter.data.WriterDB;
import com.zubin.ewriter.utils.DateHelper;

/**
 * Created by zubin on 15/6/3.
 */
public class WriterActivity extends ActionBarActivity {

    private Toolbar mToolbar;
    private EditText mEditText;
    private WriterDB writerDB;
    private SQLiteDatabase dbWriter;
    private int Id = -1;
    private ContentValues cv = new ContentValues();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writer);

        mToolbar = (Toolbar) findViewById(R.id.writer_toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mEditText = (EditText) findViewById(R.id.content);

        writerDB = new WriterDB(this);
        dbWriter = writerDB.getWritableDatabase();

        if (getIntent().getStringExtra(WriterDB.CONTENT) != null) {
            Id = getIntent().getIntExtra(WriterDB.ID, 0);
            String content = getIntent().getStringExtra(WriterDB.CONTENT);

            mEditText.setText(content);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_write, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_delete) {
            deleteDB();
            finish();
            return true;
        }
        if (item.getItemId() == R.id.action_save) {
            finish();
            return true;
        }
        if (item.getItemId() == R.id.action_share) {
            String shareStr = mEditText.getText().toString();
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, shareStr);
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
            return true;
        }
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
            return true;
        }

        return false;
    }


    private void deleteDB() {
        dbWriter.delete(WriterDB.EWRITER_TABLE_NAME, "_id=?", new String[]{String.valueOf(Id)});
    }

    private void addDB() {

        if (Id == -1) {
            // Id == -1 时，表示为新建项目
            if (!mEditText.getText().toString().replaceAll("\\s*|t|r|n","").equals("")) {
                cv.put(WriterDB.CONTENT, mEditText.getText().toString());
                cv.put(WriterDB.CREATEDTIME, DateHelper.getWriterDate(this));
                dbWriter.insert(WriterDB.EWRITER_TABLE_NAME, null, cv);
            }
        } else {
            // Id为其他值表示这条数据数据库中有，需要更新
            cv.put(WriterDB.CONTENT, mEditText.getText().toString());
            cv.put(WriterDB.CREATEDTIME, DateHelper.getWriterDate(this));
            dbWriter.update(WriterDB.EWRITER_TABLE_NAME, cv, "_id=?", new String[]{String.valueOf(Id)});
        }
    }

    @Override
    protected void onPause() {
        addDB();
        super.onPause();
    }
}
