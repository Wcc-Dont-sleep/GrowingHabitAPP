package com.example.habittest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class Fragment5 extends Fragment{
    public int wishnum;
    public int habitnum;
    private View v;

    private GridView gview;
    private DBManager mgr;
    public void setDBManager(DBManager mgr) {
        this.mgr = mgr;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        v = inflater.inflate(R.layout.personal, container, false);
        // 在 onCreate() 方法中设置 TextView 的内容
        TextView textView8 = v.findViewById(R.id.textView8);
        textView8.setText("当前完成计划总数：");

        TextView totalHabitTextView = v.findViewById(R.id.totalhabit);
        totalHabitTextView.setText(String.valueOf(mgr.countotalhabit()));
        TextView totalWishTextView = v.findViewById(R.id.totalwish);
        totalWishTextView.setText(String.valueOf(mgr.countotalwish()));

        TextView totalPointTextView = v.findViewById(R.id.totalpoint);
        totalPointTextView.setText(String.valueOf(mgr.getUserPoint()));

        CalendarView calendarView = v.findViewById(R.id.personalCal);
    // 获取 Calendar 对象并设置标记
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.JUNE, 13); // 设置要标记的日期
//        calendarView.addDecorator(new EventDecorator(Color.RED, calendar.getTime())); // 添加标记装饰器
        return v;
    }

}
