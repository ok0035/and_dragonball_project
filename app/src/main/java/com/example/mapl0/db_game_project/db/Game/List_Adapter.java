package com.example.mapl0.db_game_project.db.Game;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.mapl0.db_game_project.R;
import java.util.ArrayList;

/**
 * Created by mapl0 on 2016-12-01.
 */

public class List_Adapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Activity m_activity;
    private ArrayList<Item> arr;
    public List_Adapter(Activity act, ArrayList<Item> arr_item) {
        this.m_activity = act;
        arr = arr_item;
        mInflater = (LayoutInflater)m_activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return arr.size();
    }
    @Override
    public Object getItem(int position) {
        return arr.get(position);
    }
    public long getItemId(int position){
        return position;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            int res = 0;
            res = R.layout.list_item;
            convertView = mInflater.inflate(res, parent, false);
        }

        ImageView imView = (ImageView)convertView.findViewById(R.id.vi_image);
        int resId = m_activity.getResources().getIdentifier(arr.get(position).Picture, "drawable", m_activity.getPackageName());
        imView.setBackgroundResource(resId);

        TextView rank = (TextView)convertView.findViewById(R.id.vi_rank);
        TextView name = (TextView)convertView.findViewById(R.id.vi_name);
        TextView score = (TextView)convertView.findViewById(R.id.vi_score);
        LinearLayout layout_view =  (LinearLayout)convertView.findViewById(R.id.vi_view);

        rank.setText("Rank : No." + arr.get(position).Rank);
        name.setText("NAME : " + arr.get(position).Name);
        score.setText("SCORE : " + arr.get(position).Score);

		/*	버튼에 이벤트처리를 하기위해선 setTag를 이용해서 사용할 수 있습니다.
		 *
		 * 	Button btn 가 있다면, btn.setTag(position)을 활용해서 각 버튼들의 이벤트처리를 할 수 있습니다.
		 */
        /*
        layout_view.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                GoIntent(position);
            }
        });
        */
        return convertView;
    }
    public void GoIntent(int a){
        Intent intent = new Intent(m_activity, "가고싶은 클래스".getClass());
        //putExtra 로 선택한 아이템의 정보를 인텐트로 넘겨 줄 수 있다.
        intent.putExtra("TITLE", arr.get(a).Name);
        intent.putExtra("EXPLAIN", arr.get(a).Score);
        m_activity.startActivity(intent);
    }
}
