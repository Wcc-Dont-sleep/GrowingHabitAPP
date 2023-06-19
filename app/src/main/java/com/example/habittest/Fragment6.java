package com.example.habittest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
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

public class Fragment6 extends Fragment{

    Note[] note;
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
        v = inflater.inflate(R.layout.note, container, false);

        refresh_grid();

        gview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                //change_data(i);
            }
        });


        return v;
    }


    private void get_Data()
    {
        note = mgr.getNote();
        for (int i = 0; i < note.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("imageView9", getResources().getIdentifier(note[i].pic, "drawable", getContext().getApplicationInfo().packageName));
            map.put("note_title", note[i].ntitle);
            map.put("note_habit", note[i].hname);
            data_list.add(map);
        }
    }
    private void refresh_grid()
    {
        gview = (GridView) v.findViewById(R.id._notedynamic);

        data_list = new ArrayList<Map<String, Object>>();

        get_Data();

        String[] from = {"imageView9","note_title","note_habit","note_button"};
        int[] to = {R.id.imageView9, R.id.note_title, R.id.note_habit,R.id.note_button};
        //sim_adapter = new SimpleAdapter(getActivity(), data_list, R.layout.note_item, from, to);

        //gview.setAdapter(sim_adapter);
        NoteAdapter noteAdapter = new NoteAdapter(getActivity(),data_list);
        gview.setAdapter(noteAdapter);

    }
/*
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

 */
    public class NoteAdapter extends BaseAdapter {
        private List<Map<String, Object>> data;
        private Context context;

        public NoteAdapter(Context context, List<Map<String, Object>> data) {
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
                convertView = LayoutInflater.from(context).inflate(R.layout.note_item, parent, false);
                holder = new ViewHolder();
                holder.imageView = convertView.findViewById(R.id.imageView9);
                holder.titleTextView = convertView.findViewById(R.id.note_title);
                holder.nhabitTextView = convertView.findViewById(R.id.note_habit);
                holder.button = convertView.findViewById(R.id.note_button);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Map<String, Object> item = data.get(position);
            // 绑定数据到视图
            holder.imageView.setImageResource((int) item.get("imageView9"));
            holder.titleTextView.setText((String) item.get("note_title"));
            holder.nhabitTextView.setText((String) item.get("note_habit"));

            //final int buttonPosition = position;//保存按钮所在的单元格位置
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //holder.button.setVisibility(View.GONE);
                    note = mgr.getNote();
                    //Toast.makeText(context, " Button Clicked: position " + buttonPosition, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(),EditNote.class);
                    intent.putExtra("ntitle",note[position].ntitle);
                    intent.putExtra("ntext",note[position].ntext);
                    intent.putExtra("hname",note[position].hname);
                    startActivity(intent);
                }
            });
            return convertView;
        }

        private  class ViewHolder {
            ImageView imageView;
            TextView titleTextView;

            TextView nhabitTextView;
            Button button;
        }
    }
}
