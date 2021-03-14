package com.cookandroid.bottom_setting;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.cookandroid.bottom_setting.MainActivity.List_DB;

public class List extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void Refresh_Fragment() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list, container, false);

        final ListView listview ;
        final List_CustomChoiceListViewAdapter adapter;
        final Intent intent = new Intent(getActivity(), List_SelectGoal.class);
        // 체크박스
        boolean mClick = false;

        // Adapter 생성
        adapter = new List_CustomChoiceListViewAdapter() ;

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) view.findViewById(R.id.listview1);
        listview.setAdapter(adapter);

        load_values(adapter);

        // add button에 대한 이벤트 처리. (미완성)
        ImageButton addButton = (ImageButton) view.findViewById(R.id.add);
        addButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                startActivityForResult(intent, 0);

                Refresh_Fragment();
                //startActivity(intent);//액티비티 띄우기
            }


        });

        // delete button에 대한 이벤트 처리. (미완성)
        // 선택해제 기능뿐.
        final ImageButton deleteButton = (ImageButton)view.findViewById(R.id.delete) ;
        deleteButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                SparseBooleanArray checkedItems = listview.getCheckedItemPositions();
                SQLiteDatabase db = List_DB.getWritableDatabase();
                int count = adapter.getCount() ;
                //adapter.it
                for (int i = count-1; i >= 0; i--) {
                    if (checkedItems.get(i)) {
                        //adapter.getItem(i).getGoal();
                        String SQLdelete="DELETE FROM List_20_11_22 WHERE TITLE = '"+adapter.getItem(i).getGoal()+"'";
                        Log.d("index",adapter.getItem(i).getGoal());
                        db.execSQL(SQLdelete);

                        //if(listview.isItemChecked(i);

                        //adapter.removeItem(1);
                    }
                }
                db.close();
                // 모든 선택 상태 초기화.
                listview.clearChoices() ;
                Refresh_Fragment();
                //adapter.notifyDataSetChanged();
            }
        }) ;

        // selectAll button에 대한 이벤트 처리.
        final ImageButton selectAllButton = (ImageButton)view.findViewById(R.id.selectAll) ;
        selectAllButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                int count = 0 ;
                count = adapter.getCount() ;
                int AllChecked = 0;

                for (int i=0; i<count; i++) {
                    // 전체선택되었는지 확인
                    if(listview.isItemChecked(i) == false) {
                        AllChecked = 1;
                        break;
                    }
                }

                // 전체선택
                if (AllChecked == 1) {
                    for (int i=0; i<count; i++) {
                        if (listview.isItemChecked(i) == false) {
                            listview.setItemChecked(i, true);
                        }
                    }
                }
                else { // 전체해제
                    for (int i=0; i<count; i++) {
                        listview.setItemChecked(i, false) ;
                    }
                }
            }
        }) ;

        // modify button에 대한 이벤트 처리. (미완성)
        final ImageButton modifyButton = (ImageButton)view.findViewById(R.id.modify) ;
        modifyButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                // 모든 선택 상태 초기화.
                listview.clearChoices() ;

                if(selectAllButton.getVisibility() == View.GONE) {
                    selectAllButton.setVisibility(View.VISIBLE);
                    deleteButton.setVisibility(View.VISIBLE);
                    adapter.toggleCheckBox(true);
                } else {
                    selectAllButton.setVisibility(View.GONE);
                    deleteButton.setVisibility(View.GONE);
                    adapter.toggleCheckBox(false);
                }
            }
        }) ;

        // 위에서 생성한 listview에 클릭 이벤트 핸들러 정의.
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                if (selectAllButton.getVisibility() == View.GONE) {
                    // get item
                    List_ListviewItem item = (List_ListviewItem) parent.getItemAtPosition(position);

                    String Title = item.getGoal();
                    Intent intent = new Intent(getActivity(), List_Detail.class);
                    intent.putExtra("Title", Title);
                    startActivity(intent);

                    // TODO : use item data.
                }
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

            String per = (100 - (int) total) + "%";

            Log.d("k2", per);

            //Title, 시작기간, 끝난 기간, 디테일 , 퍼센트
            if (total <= 100 && total >= 0) {
                adapter.addItem(List_Title, List_Term_Start, List_Term_End, List_Detail, per);
                db.close();
            }

        }
    }
}