package com.cookandroid.bottom_setting;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class History_CustomChoiceListViewAdapter extends BaseAdapter {

    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<List_ListviewItem> listViewItemListList = new ArrayList<List_ListviewItem>() ;

    // 체크박스
    private boolean mClick = false;

    // ListViewAdapter의 생성자
    public History_CustomChoiceListViewAdapter() {

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
            convertView = inflater.inflate(R.layout.history_listveiw_item, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView goalTextView = (TextView) convertView.findViewById(R.id.goal) ;
        TextView sdateTextView = (TextView) convertView.findViewById(R.id.sdate) ;
        TextView edateTextView = (TextView) convertView.findViewById(R.id.edate) ;
        TextView etcTextView = (TextView) convertView.findViewById(R.id.etc) ;
        ImageView isDoneImageView = (ImageView) convertView.findViewById(R.id.isDone) ;

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        List_ListviewItem listListViewItem = listViewItemListList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        goalTextView.setText(listListViewItem.getGoal());
        sdateTextView.setText(listListViewItem.getSdate());
        edateTextView.setText(listListViewItem.getEdate());
        etcTextView.setText(listListViewItem.getEtc());
        isDoneImageView.setImageDrawable(listListViewItem.getIsDone());

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {

        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {

        return listViewItemListList.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수.
    public void addItem(String goal, String sdate, String edate, String etc, Drawable isDone) {
        List_ListviewItem item = new List_ListviewItem();

        item.setGoal(goal);
        item.setSdate(sdate);
        item.setEdate(edate);
        item.setEtc(etc);
        item.setIsDone(isDone);

        listViewItemListList.add(item);
    }

    // 아이템 데이터 삭제를 위한 함수. (미완성)
    public void removeItem(int num) {
        List_ListviewItem item = new List_ListviewItem();


        listViewItemListList.remove(item);
    }
}