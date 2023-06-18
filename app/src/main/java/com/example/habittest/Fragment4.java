package com.example.habittest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

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

        refresh_grid();
        gview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                change_data(i);
            }
        });
        return v;
    }


    private void get_Data()
    {
        wish = mgr.getWish(0);
        for (int i = 0; i < wish.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("imageView8", getResources().getIdentifier(wish[i].pic, "drawable", getContext().getApplicationInfo().packageName));
            map.put("wish_name", wish[i].wname);
            map.put("wish_text", wish[i].wtext);
            map.put("wish_points",wish[i].spoint);
            data_list.add(map);
        }
        wish = mgr.getWish(1);
        for (int i = 0; i < wish.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("imageView8", getResources().getIdentifier(wish[i].pic, "drawable", getContext().getApplicationInfo().packageName));
            map.put("wish_name", wish[i].wname);
            map.put("wish_text", wish[i].wtext);
            map.put("wish_points",wish[i].spoint);
            data_list.add(map);
        }
    }
    private void refresh_grid()
    {
        gview = (GridView) v.findViewById(R.id._wishdynamic);

        data_list = new ArrayList<Map<String, Object>>();

        get_Data();

        String[] from = {"imageView8","wish_name","wish_text","wish_points","wish_button"};
        int[] to = {R.id.imageView8, R.id.wish_name, R.id.wish_text,R.id.wish_points,R.id.wish_button};
        //sim_adapter = new SimpleAdapter(getActivity(), data_list, R.layout.wish_item, from, to);

        //gview.setAdapter(sim_adapter);
        WishAdapter wishAdapter = new WishAdapter(getActivity(),data_list);
        gview.setAdapter(wishAdapter);
    }

    private void change_data(int item_id){
        if(wish[item_id].is_finish==0)
        {
            wish[item_id].is_finish=1;
            wish[item_id].pic="wish_finish";
            refresh_grid();
            Toast.makeText(getActivity(),wish[item_id].wname +"心愿完成", Toast.LENGTH_SHORT).show();

            mgr.clockinUpdateDB(wish[item_id].wname);
        }
        else {
            Toast.makeText(getActivity(),wish[item_id].wname + "已经完成，请勿重复点击", Toast.LENGTH_SHORT).show();
        }

    }
    public class WishAdapter extends BaseAdapter {
        private List<Map<String, Object>> data;
        private Context context;

        public WishAdapter(Context context, List<Map<String, Object>> data) {
            this.context = context;
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.wish_item, parent, false);
                holder = new ViewHolder();
                holder.imageView = convertView.findViewById(R.id.imageView8);
                holder.nameTextView = convertView.findViewById(R.id.wish_name);
                holder.textTextView = convertView.findViewById(R.id.wish_text);
                holder.pointsTextView = convertView.findViewById(R.id.wish_points);
                holder.button = convertView.findViewById(R.id.wish_button);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Map<String, Object> item = data.get(position);
            // 绑定数据到视图
            holder.imageView.setImageResource((int) item.get("imageView8"));
            holder.nameTextView.setText((String) item.get("wish_name"));
            holder.textTextView.setText((String) item.get("wish_text"));
            int points = (int) item.get("wish_points");
            holder.pointsTextView.setText(String.valueOf(points));

            // 设置按钮点击事件监听器
            final int buttonPosition = position; // 保存按钮所在的单元格位置
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    wish = mgr.getWish(0);
                    int l = wish.length;
                    if(position>=wish.length)
                    {
                        wish = mgr.getWish(1);
                        Toast.makeText(context,wish[position-l].wname + "已经完成，请勿重复点击", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        wish[position].is_finish = 1;
                        wish[position].pic = "wish_finish";
                        if (mgr.wishUpdateDB(wish[position].wname))
                        {
                            mgr.UpdateUser();
                            Toast.makeText(context,wish[position].wname +"心愿完成"+"消耗"+wish[position].spoint+"点数", Toast.LENGTH_SHORT).show();
                            refresh_grid();
                            //holder.button.setVisibility(View.GONE);
                        }
                        else
                        {
                            Toast.makeText(context,"当前点数不足兑换"+wish[position].wname +"心愿", Toast.LENGTH_SHORT).show();
                        }
                    }
                    //Toast.makeText(context, "Wish Button Clicked: position " + buttonPosition, Toast.LENGTH_SHORT).show();
                }
            });

            return convertView;
        }

        private  class ViewHolder {
            ImageView imageView;
            TextView nameTextView;
            TextView textTextView;
            TextView pointsTextView;
            Button button;
        }
    }
}
