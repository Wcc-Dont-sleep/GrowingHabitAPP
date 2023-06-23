package com.example.habittest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Date;

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
        //totalWishTextView.setText(String.valueOf(mgr.getdatelog("2023-06-12")));
        TextView totalPointTextView = v.findViewById(R.id.totalpoint);
        totalPointTextView.setText(String.valueOf(mgr.getUserPoint()));
        BarChart barChart = v.findViewById(R.id.bc1);

        ArrayList<String> xVals=new ArrayList<>();
        ArrayList<BarEntry> yVals=new ArrayList<>();
        ArrayList<Integer> pre=new ArrayList<>();
        ArrayList<Entry> entries = new ArrayList<>();
        ArrayList<String> linexVals=new ArrayList<>();
// 计算函数的值并添加到数据点中

        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String todayString = dateFormat.format(today);
        Log.d("fragement5",todayString);
        int count=0;
//        xVals.add(todayString);
//        yVals.add(new BarEntry(mgr.getdatelog(todayString),count));

        for(int i =3;i>0;i--)
        {
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(new Date());
            calendar1.add(Calendar.DAY_OF_YEAR, -i);
            Date threeDaysAgo = calendar1.getTime();
            String threeDaysAgoString = dateFormat.format(threeDaysAgo);
            xVals.add(threeDaysAgoString);
            yVals.add(new BarEntry(mgr.getdatelog(threeDaysAgoString),count));
            pre.add(count);
            pre.add(mgr.getdatelog(threeDaysAgoString));
            count++;
        }
        xVals.add(todayString);
        yVals.add(new BarEntry(mgr.getdatelog(todayString),count));
        pre.add(count);
        pre.add(mgr.getdatelog(todayString));
        count++;
        PolynomialFunction fittedFunction=DateUtils.getline(pre,4);
        for(int i =1;i<2;i++)
        {
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(new Date());
            calendar1.add(Calendar.DAY_OF_YEAR, i);
            Date threeDaysAgo = calendar1.getTime();
            String threeDaysAgoString = dateFormat.format(threeDaysAgo);
            xVals.add(threeDaysAgoString);
            yVals.add(new BarEntry((float) DateUtils.getpredict(fittedFunction,3.3),count));
            count++;
        }

        int linecount=0;
        //曲线图
        int totaldays=6;
        for (float x = 0; x <= totaldays; x += 0.5) {
            linexVals.add(String.valueOf(x));
            float y = (float) DateUtils.getpredict(fittedFunction,x);
            entries.add(new Entry(y, linecount));
            linecount+=1;
        }
        LineDataSet dataSet = new LineDataSet(entries, "总计划完成情况");
        dataSet.setDrawCubic(true);
        LineData lineData = new LineData(linexVals,dataSet);
        LineChart lineChart = v.findViewById(R.id.lineChartView);
// 配置曲线图的样式和属性
        lineChart.setData(lineData);
        lineChart.invalidate();
        lineChart.setBackgroundColor(Color.WHITE);
//是否显示边界
        lineChart.setDrawBorders(false);
        lineChart.setDrawGridBackground(false);
// 获取之前三天的日期
        //y轴数值集合
        BarDataSet set = new BarDataSet(yVals," ");
        int[] colors = new int[]{Color.CYAN, Color.CYAN,Color.CYAN,Color.CYAN, Color.YELLOW};  // 定义不同颜色的数组
        set.setColors(colors);
        //x轴与y轴对应
        BarData data=new BarData(xVals,set);
        //添加到控件中
        barChart.setData(data);
        barChart.setBackgroundColor(Color.WHITE);
        barChart.setDrawGridBackground(false);
//是否显示边界
        barChart.setDrawBorders(false);
//        CalendarView calendarView = v.findViewById(R.id.personalCal);
    // 获取 Calendar 对象并设置标记
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(2023, Calendar.JUNE, 13); // 设置要标记的日期
//        calendarView.addDecorator(new EventDecorator(Color.RED, calendar.getTime())); // 添加标记装饰器
        return v;
    }

}
