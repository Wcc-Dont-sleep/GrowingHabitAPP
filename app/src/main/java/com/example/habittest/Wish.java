package com.example.habittest;

public class Wish {
    public String wname;
    public String pic;
    public int is_finish;
    public String time;
    public String wtext;
    public int spoint;

    public Wish(String w, String p, int b, String t, String text, int s) {
        wname = w;
        pic = p;
        is_finish = b;
        time = t;
        wtext = text;
        spoint = s;
    }
}
