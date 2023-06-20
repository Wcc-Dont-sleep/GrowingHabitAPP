package com.example.habittest;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

public class AddNote extends AppCompatActivity {

    private MySqliteHelper helper;
    private SQLiteDatabase db;
    private DBManager mgr;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        helper = DBManager.getIntance(this);
        db = helper.getWritableDatabase();//创建或打开数据库
        mgr = new DBManager(db);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    public void addNote(View view)
    {
        EditText etTitle = (EditText) findViewById(R.id.editText_notename);
        EditText etHname = (EditText) findViewById(R.id.editText_hname);
        EditText etText = (EditText) findViewById(R.id.editText_notetext);

        String title = etTitle.getText().toString();
        String hname = etHname.getText().toString();
        String text= etText.getText().toString();


        if (title.equals(""))
        {
            Toast.makeText(this, "便签名不得为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (hname.equals("")) {
            Toast.makeText(this, "所属生活事件不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (text.equals("")) {
            Toast.makeText(this, "便签内容不能为空", Toast.LENGTH_SHORT).show();
            return;
        }



        Date date = new Date();
        Note note = new Note(title,"note",text,Utils.date2String(date),hname);
       if(mgr.insertNoteDB(note)){
           finish();
           return;
       }
       else {
           Toast.makeText(this, "便签名不能重复", Toast.LENGTH_SHORT).show();
       }



    }
}
