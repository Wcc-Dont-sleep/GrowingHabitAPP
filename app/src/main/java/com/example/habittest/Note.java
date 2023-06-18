package com.example.habittest;

public class Note {

    public String hname;
    //便签对应的生活事件

    public String pic;

    public String ntext;

    public String time;

    public String ntitle;

    public Note(String h,String p,String text,String t,String title){
        hname = h;
        pic = p;
        ntext = text;
        time = t;
        ntitle = title;
    }

    public String getHname() { return this.hname; }

    public void setHname(String h) { this.hname = h; }

    public String getPic() { return this.pic; }

    public void setPic(String p) { this.pic = p; }

    public String getNtext() { return this.ntext; }

    public void setNtext(String text) { this.ntext = text; }

    public String getTime() { return this.time; }

    public void setTime(String t) { this.time = t; }

    public String getNtitle() { return this.ntitle; }

    public void setNtitle(String title) { this.ntitle = title; }
}
