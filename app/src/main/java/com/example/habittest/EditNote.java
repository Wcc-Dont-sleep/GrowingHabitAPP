package com.example.habittest;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class EditNote extends AppCompatActivity{

    private String note_title;

    private String note_text;

    private String note_hname;

    //数据库相关变量
    private MySqliteHelper helper;
    private SQLiteDatabase db;
    private DBManager mgr;

    public void setDBManager(DBManager mgr) {
        this.mgr = mgr;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar9);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent intent = getIntent();
        note_text = intent.getStringExtra("ntext");
        note_title = intent.getStringExtra("ntitle");
        note_hname = intent.getStringExtra("hname");

        TextView noteTitle = (TextView) findViewById(R.id.editNoteTitle);
        TextView noteText = (TextView) findViewById(R.id.editNoteText);
        TextView noteHname = (TextView) findViewById(R.id.noteHabit);
        noteTitle.setText(note_title);
        noteText.setText(note_text);
        noteHname.setText(note_hname);

    }
    public void deleteNote(View view){
        mgr.noteUpdateDB(this.note_title);

    }
}
