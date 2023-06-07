package com.example.habittest;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fragment4 extends Fragment{

    Wish[] wish;
    Habit[] habit;
    private View v;
    private GridView gview;
    private List<Map<String, Object>> data_list;
    private SimpleAdapter sim_adapter;
    private DBManager mgr;
    public void setDBManager(DBManager mgr) {
        this.mgr = mgr;
    }
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        v = inflater.inflate(R.layout.wish, container, false);
        // habit = mgr.getHabit("任意时间",1);
        wish = mgr.getWish();
        refresh_grid();
        return v;
    }
    private void get_Data()
    {
        for (int i = 0; i < wish.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("imageView8", getResources().getIdentifier(wish[i].pic, "drawable", getContext().getApplicationInfo().packageName));
            map.put("wish_name", wish[i].wname);
            map.put("wish_text", wish[i].wtext);
            data_list.add(map);
        }
    }
    private void refresh_grid()
    {
        gview = (GridView) v.findViewById(R.id._wishdynamic);

        data_list = new ArrayList<Map<String, Object>>();

        get_Data();

        String[] from = {"imageView8","wish_name","wish_text"};
        int[] to = {R.id.imageView8, R.id.wish_name, R.id.wish_text};
        sim_adapter = new SimpleAdapter(getActivity(), data_list, R.layout.wish_item, from, to);
        gview.setAdapter(sim_adapter);
    }
}
