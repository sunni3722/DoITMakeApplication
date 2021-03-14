package com.cookandroid.bottom_setting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class List_CustomChoiceListViewAdapter extends BaseAdapter {
     String Goal=null;
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    ArrayList<List_ListviewItem> listViewItemListList = new  ArrayList<List_ListviewItem>() ;

    // 체크박스
    private boolean mClick = false;

    // ListViewAdapter의 생성자
    public List_CustomChoiceListViewAdapter() {

    }

    @Override
    public int getCount() {
        return listViewItemListList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_listview_item, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView goalTextView = (TextView) convertView.findViewById(R.id.goal) ;
        TextView sdateTextView = (TextView) convertView.findViewById(R.id.sdate) ;
        TextView edateTextView = (TextView) convertView.findViewById(R.id.edate) ;
        TextView etcTextView = (TextView) convertView.findViewById(R.id.etc) ;
        TextView perTextView = (TextView) convertView.findViewById(R.id.per) ;

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        List_ListviewItem listListViewItem = listViewItemListList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        goalTextView.setText(listListViewItem.getGoal());
        sdateTextView.setText(listListViewItem.getSdate());
        edateTextView.setText(listListViewItem.getEdate());
        etcTextView.setText(listListViewItem.getEtc());
        perTextView.setText(listListViewItem.getPer());


        // 체크박스
        CheckBox checkBox =  (CheckBox) convertView.findViewById(R.id.checkBox1);
        if(mClick) {
            checkBox.setVisibility(View.VISIBLE);
        } else {
            checkBox.setVisibility(View.GONE);
        }

        return convertView;
    }

    // 체크박스
    public void toggleCheckBox(boolean bClick) {

        mClick = bClick;
        notifyDataSetChanged();
    }
    public String getGoal(int position) {

        return Goal;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {

        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public List_ListviewItem getItem(int position) {

        return listViewItemListList.get(position);
    }

    // 아이템 데이터 추가를 위한 함수.
    public void addItem(String goal, String sdate, String edate, String etc, String per) {
        List_ListviewItem item = new List_ListviewItem();
        Goal=goal;
        item.setGoal(goal);
        item.setSdate(sdate);
        item.setEdate(edate);
        item.setEtc(etc);
        item.setPer(per);

        listViewItemListList.add(item);
    }

    // 아이템 데이터 삭제를 위한 함수. (미사용. 삭제시 확인 필요.)
    public void removeItem(int num) {
        List_ListviewItem item = new List_ListviewItem();


        listViewItemListList.remove(item);
    }
}

