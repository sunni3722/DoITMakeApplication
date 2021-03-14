package com.cookandroid.bottom_setting;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.cookandroid.bottom_setting.MainActivity.List_DB;

public class History extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.history, container, false);

        final ListView listview ;
        //코드를 잘 못짜서 List_Custom~~ 을 불러와서 사용
        //final History_CustomChoiceListViewAdapter adapter;
        final List_CustomChoiceListViewAdapter adapter;
        // 체크박스
        boolean mClick = false;

        /*
        // 빈 데이터 리스트 생성.
        final ArrayList<String> items = new ArrayList<String>() ;
        */

        // Adapter 생성
        //adapter = new History_CustomChoiceListViewAdapter() ;
        adapter= new List_CustomChoiceListViewAdapter();

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) view.findViewById(R.id.listview2);
        listview.setAdapter(adapter);
        load_values(adapter);
        /*
        // 첫 번째 아이템 추가.
        adapter.addItem("project1", "2020.09.21.M", "2022.03.20.S", "c++",
                getResources().getDrawable(R.drawable.o)) ;
        // 두 번째 아이템 추가.
        adapter.addItem("project2", "2020.09.20.S", "2020.09.25.F","c#" + '\n' + "windowsprograming",
                getResources().getDrawable(R.drawable.x) ) ;

         */

        // 위에서 생성한 listview에 클릭 이벤트 핸들러 정의.
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // get item
                List_ListviewItem item = (List_ListviewItem) parent.getItemAtPosition(position);

                String Title = item.getGoal();
                Intent intent = new Intent(getActivity(), List_Detail.class);
                intent.putExtra("Title", Title);
                startActivity(intent);

                // TODO : use item data.
            }
        }) ;

        return view;
    }
    public void load_values(List_CustomChoiceListViewAdapter adapter) {

        SQLiteDatabase db = List_DB.getReadableDatabase();//SQLiteDatabase.openOrCreateDatabase("List_20_11_22.db",MODE_PRIVATE,null) ;
        Cursor cursor = db.rawQuery(List_DB_Make.SQL_SELECT, null);
        int RecordCount = cursor.getCount();

        //if (cursor.moveToFirst()) {
        for (int i = 0; i < RecordCount; i++) {

            cursor.moveToNext();
            // List_No (INTEGER) 값 가져오기.
            //int List_No = cursor.getInt(0) ;

            // List_Title (INTEGER) 값 가져오기.
            String List_Title = cursor.getString(0);

            // List_Term_Start (TEXT) 값 가져오기
            String List_Term_Start = cursor.getString(1);

            // List_Term_End (TEXT) 값 가져오기
            String List_Term_End = cursor.getString(2);

            // List_Time_Start (TEXT) 값 가져오기
            String List_Time_Start = cursor.getString(3);

            // List_Time_End (TEXT) 값 가져오기
            String List_Time_End = cursor.getString(4);

            // List_Level (TEXT) 값 가져오기
            String List_Level = cursor.getString(5);

            // List_Category (TEXT) 값 가져오기
            String List_Category = cursor.getString(6);

            // List_Degree_Goal (TEXT) 값 가져오기
            String List_Detail = cursor.getString(7);

            // List_Detail (TEXT) 값 가져오기
            String List_Degree_Goal = cursor.getString(8);

            float total = 0;

            String strFormat = "yyyy/MM/dd";
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat(strFormat);
            String strToday = sdf.format(date);
            {
                try {
                    Date startDate = sdf.parse(List_Term_Start);
                    Date endDate = sdf.parse(List_Term_End);
                    Date today = sdf.parse(strToday);

                    float diffDay = (startDate.getTime() - endDate.getTime()) / (24 * 60 * 60 * 1000) * -1;
                    float nowDay = (today.getTime() - endDate.getTime()) / (24 * 60 * 60 * 1000) * -1;
                    total = (nowDay / diffDay) * 100;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            String per= "X";
            if (total > 100 || total < 0) {
                adapter.addItem(List_Title, List_Term_Start, List_Term_End, List_Detail, "");
                db.close();
            }
        }
    }
}