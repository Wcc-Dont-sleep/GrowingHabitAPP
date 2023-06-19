package com.example.habittest;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Date;

public class DBManager {
    private static MySqliteHelper helper;
    private SQLiteDatabase db;

    public static MySqliteHelper getIntance(Context context) {
        if (helper == null) {
            helper = new MySqliteHelper(context);
        }
        return helper;
    }

    public DBManager(SQLiteDatabase db) {
        this.db = db;
    }

    //数据库创建函数
    public void createTableOrNot() {

        String dsql = "drop table daka";
        String dsql2 = "drop table habits";
        String dsql3 = "drop table user";

        String sql10 = "update wishes set is_finish=0 where wname='测试心愿1'";
        String sql11 = "update wishes set pic='wish' where wname='测试心愿1'";
        String sql12 = "update wishes set time='' where wname='测试心愿1'";

        String sql13 = "update wishes set is_finish=0 where wname='测试心愿2'";
        String sql14 = "update wishes set pic='wish' where wname='测试心愿2'";
        String sql15 = "update wishes set time='' where wname='测试心愿2'";

        //db.execSQL(dsql);
        //db.execSQL(dsql2);
        //db.execSQL(dsql3);

        db.execSQL(sql10);
        db.execSQL(sql11);
        db.execSQL(sql12);
        db.execSQL(sql13);
        db.execSQL(sql14);
        db.execSQL(sql15);
/*
        String sql16 = "update notes set pic='note' where ntitle='测试便签1'";
        String sql17 = "update notes set time='' where ntitle='测试便签1'";
        String sql18 = "update notes set hname='跑' where ntitle='测试便签1'";
        db.execSQL(sql16);
        db.execSQL(sql17);
        db.execSQL(sql18);
*/
        boolean notable = true;
        int count = -1;
        //先判断表是否存在
        String sql = "select count(*) as c from sqlite_master where type ='table' and name ='habits' ";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToNext()) {
            count = cursor.getInt(0);
            if (count > 0) {
                notable = false;
            }
        }
        if (notable) {//不存在则创建
            String sql1 = "create table habits (hname text primary key,pic text, total_num integer,finished_num integer,time text, fre text, htext text,days integer, curdays integer, highdays integer, credate text , swit integer,spoint integer)";
            String sql2 = "create table daka (hname text,dakadate date)";
            db.execSQL(sql1);
            db.execSQL(sql2);
            this.insertTestRecord();
        }
        //调试用Log.i("tag",Integer.toString(count));
        //调试用Log.i("tag",Boolean.toString(notable));

        //创建心愿单表格
        notable = true;
        count = -1;
        sql = "select count(*) as c from sqlite_master where type ='table' and name ='wishes' ";
        cursor = db.rawQuery(sql, null);
        if (cursor.moveToNext()) {
            count = cursor.getInt(0);
            if (count > 0) {
                notable = false;
            }
        }
        if (notable) {//不存在则创建
            String sql1 = "create table wishes (wname text primary key,pic text, is_finish integer,time text, wtext text,spoint integer)";

            db.execSQL(sql1);
            this.insertTestWishRecord();


        }
        notable = true;
        count = -1;
        //创建便签表格
        sql = "select count(*) as c from sqlite_master where type ='table' and name ='notes' ";
        cursor = db.rawQuery(sql, null);
        if (cursor.moveToNext()) {
            count = cursor.getInt(0);
            if (count > 0) {
                notable = false;
            }
        }
        if (notable) {//不存在则创建
            String sql1 = "create table notes (ntitle text primary key,pic text, ntext text,time text, hname text)";

            db.execSQL(sql1);
            this.insertTestNoteRecord();


        }

        //创建用户信息
        notable = true;
        count = -1;
        sql = "select count(*) as c from sqlite_master where type ='table' and name ='user' ";
        cursor = db.rawQuery(sql, null);
        if (cursor.moveToNext()) {
            count = cursor.getInt(0);
            if (count > 0) {
                notable = false;
            }
        }
        if (notable) {//不存在则创建
            String sql1 = "create table user (uname text primary key,spoint integer)";

            db.execSQL(sql1);
            initUser();

        }
    }

    public void initUser()
    {
        Wish[] w = getWish(1);
        Habit[] h = getHabit("任意时间",0);
        int point=50;
        for(int i=0;i<h.length;i++)
        {
            point+=h[i].getsPoint();
        }
        for(int i=0;i<w.length;i++)
        {
            point-=w[i].spoint;
        }
        String sql = "insert into user values('admin'," +point+ ")";
        db.execSQL(sql);
    }
    public void UpdateUser()
    {
        Wish[] w = getWish(1);
        Habit[] h = getHabit("任意时间",0);
        int point=0;
        for(int i=0;i<h.length;i++)
        {
            point+=h[i].getsPoint();
        }
        for(int i=0;i<w.length;i++)
        {
            point-=w[i].spoint;
        }
        String sql = "update user set spoint=" + point + " where uname = 'admin'";
        db.execSQL(sql);
    }

    public void insertTestWishRecord()
    {
        String sql9 = "insert into wishes values ('测试心愿1','wish',0,'20230604','七月前完成10公里打卡',25)";
        String sql10 = "insert into wishes values ('测试心愿2','wish_finish',1,'20230604','完成专业综合测试项目',25)";
        String sql1 = "insert into wishes values ('测试心愿3','wish',0,'20230604','早睡早起10天',40)";
        String sql2 = "insert into wishes values ('测试心愿4','wish',0,'20230604','背完六级单词',35)";
        String sql3 = "insert into wishes values ('测试心愿5','wish',0,'20230604','完成租房',10)";
        db.execSQL(sql9);
        db.execSQL(sql10);
        db.execSQL(sql1);
        db.execSQL(sql2);
        db.execSQL(sql3);

    }
    public void insertTestNoteRecord()
    {
        String sql14 = "insert into notes values ('测试便签1','note','测试便签1的内容','20230610','测试便签1')";
        String sql15 = "insert into notes values ('测试便签2','note','测试便签2的内容','20230610','测试便签2')";
        String sql16 = "insert into notes values ('测试便签3','note','测试便签3的内容','20230610','测试便签3')";
        String sql17 = "insert into notes values ('测试便签4','note','测试便签4的内容','20230610','测试便签4')";
        String sql18 = "insert into notes values ('测试便签5','note','测试便签5的内容','20230610','测试便签5')";
        db.execSQL(sql14);
        db.execSQL(sql15);
        db.execSQL(sql16);
        db.execSQL(sql17);
        db.execSQL(sql18);
    }

    public void insertTestRecord() {


        String sql1 = "insert into habits values ('测试习惯1','habit_1',2,0,'晨间习惯','每天','只有千锤百炼，才能成为好钢。',5,0,3,'20230604',1,20)";
        String sql2 = "insert into habits values ('测试习惯2','habit_2',3,0,'午间习惯','每天','只有千锤百炼，才能成为好钢。',3,0,2,'20230604',1,30)";
        String sql3 = "insert into habits values ('测试习惯3','habit_3',1,0,'晚间习惯','每天','只有千锤百炼，才能成为好钢。',4,0,2,'20230604',1,25)";
        String sql4 = "insert into habits values ('测试习惯4','habit_4',1,0,'任意时间','每天','只有千锤百炼，才能成为好钢。',6,0,3,'20230604',1,45)";

        String sql5 = "insert into daka values ('测试习惯1','20230604'),('测试习惯1','20230604'),('测试习惯1','20230604'),('测试习惯1','20230604'),('测试习惯1','20230604')";
        String sql6 = "insert into daka values ('测试习惯2','20230604'),('测试习惯2','20230604'),('测试习惯2','20230604')";
        String sql7 = "insert into daka values ('测试习惯3','20230604'),('测试习惯3','20230604'),('测试习惯3','20230604'),('测试习惯3','20230604')";
        String sql8 = "insert into daka values ('测试习惯4','20230604'),('测试习惯4','20230604'),('测试习惯4','20230604'),('测试习惯4','20230604'),('测试习惯4','20230604'),('测试习惯4','20230604')";



        db.execSQL(sql1);
        db.execSQL(sql2);
        db.execSQL(sql3);
        db.execSQL(sql4);
        db.execSQL(sql5);
        db.execSQL(sql6);
        db.execSQL(sql7);
        db.execSQL(sql8);

    }
    public void clockinUpdateDB(String h) {
        //点击签到键更新数据库

        //非必做操作 筛选更新
        String sql3 = "update habits set days = days+1 where hname='" + h + "' and finished_num=0";
        String sql4 = "update habits set curdays = curdays+1 where hname='" + h + "' and finished_num=0 and curdays=0";
        String sql5 = "update habits set highdays = highdays+1 where hname='" + h + "' and finished_num=0 and highdays=0";
        db.execSQL(sql3);
        db.execSQL(sql4);
        db.execSQL(sql5);
        //必做操作：finished_num++ 、插入打卡记录
        Date date = new Date();     ///获取当前日期
        String date_s = Utils.date2String(date);
        String sql1 = "update habits set finished_num = finished_num+1 where hname ='" + h + "'";
        String sql2 = "insert into daka values ('" + h + "','" + date_s + "')";
        db.execSQL(sql1);
        db.execSQL(sql2);
    }
    public int getUserPoint()
    {
        String usql = "select * from user";
        Cursor cursor = db.rawQuery(usql, null);
        cursor.moveToNext();
        int spoint = cursor.getInt(1);
        return spoint;
    }
    public boolean wishUpdateDB(String w)
    {
        UpdateUser();
        int spoint = getUserPoint();

        String wsql = "select * from wishes where wname = '" + w + "'";
        Cursor cursor = db.rawQuery(wsql, null);
        cursor.moveToNext();

        int wpoint = cursor.getInt(5);
        if(spoint<wpoint)
        {
            return false;
        }
        String sql = "update wishes set is_finish=1 where wname='"+w+"'";
        String sql2 = "update wishes set pic='wish_finish' where wname='"+w+"'";
        db.execSQL(sql);
        Date date = new Date();     ///获取当前日期
        String date_s = Utils.date2String(date);
        String sql1 = "update wishes set time='"+date_s+"' where wname='"+w+"'";
        db.execSQL(sql1);
        db.execSQL(sql2);
        return true;
    }
    public void noteUpdateDB(String n)
    {
        String sql = "update notes set time='' where ntitle='"+n+"'";
        db.execSQL(sql);
    }
    public int countotalwish(){
        Log.e("DB","IntoGetWish");
        String sql = "select count(*) from wishes where is_finish="+1;
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToNext();
        int count = cursor.getInt(0);//获取心愿总数'
        return count;
    }
    public int countotalnote(){
        Log.e("DB","IntoGetNote");
        String sql = "select count(*) from notes";
        Cursor cursor = db.rawQuery(sql,null);
        cursor.moveToNext();
        int count = cursor.getInt(0);//获取全部便签总数
        return count;
    }
    public int countotalhabit(){
        String sql = "select sum(finished_num) from habits";
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToNext();
        int count = cursor.getInt(0);//获取以完成的计划总数'
        return count;
    }
    //is_finish=0表示未完成，为1为完成
    public Wish[] getWish(int is_finish)
    {
        Log.e("DB","IntoGetWish");
        String sql = "select count(*) from wishes where is_finish="+is_finish;
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToNext();
        int count = cursor.getInt(0);//获取心愿总数'

        Log.i("tag",Integer.toString(count));

        Wish[] w = new Wish[count];
        String sq2 = "select * from wishes where is_finish="+is_finish;
        Cursor c = db.rawQuery(sq2, null);
        c.moveToFirst();
        int i = 0;
        while (!c.isAfterLast()) {
            Wish w1 = new Wish(c.getString(0), c.getString(1), c.getInt(2), c.getString(3), c.getString(4), c.getInt(5));
            w[i++] = w1;
            c.moveToNext();
        }
        ///应当有count == h.length;
        return w;
    }
    public Note[] getNote() {
        Log.e("DB", "IntoGetNote");
        String sql = "select count(*) from notes where time!=''";
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToNext();
        int count = cursor.getInt(0);//获取便签总数

        Log.i("tag", Integer.toString(count));

        Note[] n = new Note[count];
        String sql2 = "select * from notes where time!=''";
        Cursor c = db.rawQuery(sql2, null);
        c.moveToFirst();
        int i = 0;
        while (!c.isAfterLast()) {
            Note n1 = new Note(c.getString(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4));
            n[i++] = n1;
            c.moveToNext();
        }
        return n;
    }
    //返回给定时间段下的习惯
    //isNotFinished为1时返回未结束的,为0返回已结束的
    public Habit[] getHabit(String time, int isNotFinished) {
        String[] Time = new String[]{time};
        if (time == "任意时间") {//选中的是任意时间habits，返回全部habits
            String sql = "select count(*) from habits where swit=" + isNotFinished;
            Cursor cursor = db.rawQuery(sql, null);
            cursor.moveToNext();
            int count = cursor.getInt(0);//获取习惯总数
            Habit[] h = new Habit[count];//创建habit数组
            String sq2 = "select * from habits where swit=" + isNotFinished;
            Cursor c = db.rawQuery(sq2, null);
            c.moveToFirst();
            int i = 0;
            while (!c.isAfterLast()) {
                Habit h1 = new Habit(c.getString(0), c.getString(1), c.getInt(2), c.getInt(3), c.getString(4), c.getString(5), c.getString(6), c.getInt(7), c.getInt(8), c.getInt(9), c.getString(10), c.getInt(11),c.getInt(12));
                h[i++] = h1;
                c.moveToNext();
            }
            ///应当有count == h.length;
            return h;
        } else {//其它情况相似
            String sql1 = "select count(*) from habits where time=? and swit =" + isNotFinished;
            Cursor cursor = db.rawQuery(sql1, Time);
            cursor.moveToNext();
            int count = cursor.getInt(0);//获取习惯总数
            Habit[] h = new Habit[count];//创建habit数组
            String sql2 = "select * from habits where time=? and swit =" + isNotFinished;
            Cursor c = db.rawQuery(sql2, Time);
            c.moveToFirst();
            int i = 0;
            while (!c.isAfterLast()) {
                Habit h1 = new Habit(c.getString(0), c.getString(1), c.getInt(2), c.getInt(3), c.getString(4), c.getString(5), c.getString(6), c.getInt(7), c.getInt(8), c.getInt(9), c.getString(10), c.getInt(11),c.getInt(12));
                h[i++] = h1;
                c.moveToNext();
            }
            ///应当有count == h.length ==i;实际h数组的下标应该为0至i-1
            //调试用Log.i("tag##",Integer.toString(i));
            return h;
        }
    }

    //用于在添加新的习惯时更新数据库，成功返回ture，失败返回false（表示该习惯已经存在）。
    public boolean insertHabitDB(Habit habit) {

        //第一步先看要添加的habit名称是否已经存在

        String sql1 = "select count(*) from habits where hname = '" + habit.getHname() + "'";    //sql语句查询是否存在该名字
        Cursor cursor = db.rawQuery(sql1, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        if (count == 1)      //若已经存在这个名字的习惯则直接返回false
            return false;
        //否则创建这个习惯。
        String sql2 = "insert into habits values('" + habit.getHname() + "','" + habit.getPic() + "'," + habit.getTotal_num() + "," + habit.getFinished_num() + ",'" + habit.getTime() + "','" + habit.getFre() + "','" + habit.getHtext() + "'," + habit.getDays() + "," + habit.getCurdays() + "," + habit.getHighdays() + ",'" + habit.getCredate() + "'," + habit.getSwit() + ")";
        db.execSQL(sql2);
        return true;    ///添加则返回true
    }
    public boolean insertWishDB(Wish wish)
    {
        String sql1 = "select count(*) from wishes where wname = '" + wish.wname + "'";    //sql语句查询是否存在该名字
        Cursor cursor = db.rawQuery(sql1, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        if (count == 1)      //若已经存在这个名字的习惯则直接返回false
            return false;
        String sql2 = "insert into wishes values ('" + wish.wname + "','" + wish.pic + "'," + wish.is_finish + ",'" +wish.time + "','" + wish.wtext + "'," + wish.spoint  + ")";
        db.execSQL(sql2);
        return true;    ///添加则返回true
    }
    public void switchHabit(String hname,int swit) {
        String sql = "update habits set swit = "+swit+" where hname='" + hname + "'";
        db.execSQL(sql);
    }

    //获取查看的习惯已经打卡的日期,返回存放已打卡日期的int数组
    public int[] getDates(String hname) {
        int[] dates;//存放结果的int数组
        int count;//数组长度 通过查询记录获得
        String sql1 = "select count(*) from daka where hname = '" + hname + "'";
        Cursor cursor = db.rawQuery(sql1, null);
        cursor.moveToFirst();
        count = cursor.getInt(0);
        dates = new int[count];  ///获取该习惯打卡总数后，实例化int数组，准备存数据

        String sql2 = "select dakadate from daka where hname = '" + hname + "'";
        Cursor c = db.rawQuery(sql2, null);
        c.moveToFirst();
        int i = 0;
        while (!c.isAfterLast()) {//获取日期
            String s1 = c.getString(0);
            dates[i++] = Integer.parseInt(s1.substring(6, 8));
            c.moveToNext();
        }
        //while过后，应当有count==i==h.length，且s[]下标范围0至i-1

        return dates;
    }
}
