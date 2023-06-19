package com.example.habittest;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;


import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;

import java.util.Calendar;
import java.util.Date;

public class AddWish extends AppCompatActivity {
    private MySqliteHelper helper;
    private SQLiteDatabase db;
    private DBManager mgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wish);

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

    public void addWish(View view)
    {
        EditText etName = (EditText) findViewById(R.id.editText_wishname);
        EditText etText = (EditText) findViewById(R.id.editText_wishtext);
        EditText etPNum = (EditText) findViewById(R.id.editText_wishnum);

        String name = etName.getText().toString();
        String text = etText.getText().toString();
        String pNum = etPNum.getText().toString();


        if (pNum.equals(""))
        {
            Toast.makeText(this, "心愿花费点数不得为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (text.equals("")) {
            Toast.makeText(this, "心愿名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (name.equals("")) {
            Toast.makeText(this, "心愿名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        int num = Integer.parseInt(pNum);
        Date date = new Date();
        Wish wish = new Wish(name,"wish",0,Utils.date2String(date),text,num);
        if(mgr.insertWishDB(wish)){
            finish();
            return;
        }
        else
        {
            Toast.makeText(this, "心愿名不能重复", Toast.LENGTH_SHORT).show();
            return;
        }
    }
}
