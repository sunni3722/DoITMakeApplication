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

import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import static com.cookandroid.bottom_setting.MainActivity.List_DB;

public class History extends Fragment {

    // Bundle에서 ID정보 받아오기
    String id;
    String find;
    Bundle bundle;

    // ID & List_ID 받아오기
    String[] List_ID;
    String final_list_id;
    int length;

    //List_ID로 List Data 받아오기
    selectDatabase_list data_list[];
    String find_list[];
    String Title[];
    String List_Term_Start[];
    String List_Term_End[];
    String List_Time_Start[];
    String List_Time_End[];
    String List_Level[];
    String List_Category[];
    String List_Detail[];
    String List_Degree_Goal[];

    // List Detail 확인용 List
    String List_Detail_List_ID[];
    String List_Detail_Title[];
    String List_Detail_Term_Start[];
    String List_Detail_Term_End[];
    String List_Detail_Time_Start[];
    String List_Detail_Time_End[];
    String List_Detail_Level[];
    String List_Detail_Category[];
    String List_Detail_Detail[];
    String List_Detail_Degree_Goal[];

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.history, container, false);

        // List Detail 전용 변수 (Naver)
        int a = 0;

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

        bundle = getArguments();

        String IP = getString(R.string.web_IP);

        // Naver 로그인인 경우
        if (bundle != null) {
            id = bundle.getString("id");

            // 이용자의 고유 Naver ID 값을 이용해 list_id 정보 불러오기
            selectDatabase_list_id list_id = new selectDatabase_list_id(IP, null, getContext());
            try {
                find = list_id.execute(id).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // List ID JSON Parsing
            try {
                if (!find.equals("null")) {
                    Translate_JSON_List_ID user_list_id = new Translate_JSON_List_ID(find);
                    List_ID = user_list_id.getList_ID();
                    length = user_list_id.getlength();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                List_ID = null;
                length = 0;
            }
            // List ID로 배열로 List Data 정보 불러오기
            if (List_ID != null) {
                final_list_id = List_ID[length - 1];
                data_list = new selectDatabase_list[length];
                find_list = new String[length];
                Title = new String[length];
                List_Term_Start = new String[length];
                List_Term_End = new String[length];
                List_Time_Start = new String[length];
                List_Time_End = new String[length];
                List_Level = new String[length];
                List_Category = new String[length];
                List_Detail = new String[length];
                List_Degree_Goal = new String[length];

                List_Detail_List_ID = new String[length];
                List_Detail_Title = new String[length];
                List_Detail_Term_Start = new String[length];
                List_Detail_Term_End = new String[length];
                List_Detail_Time_Start = new String[length];
                List_Detail_Time_End = new String[length];
                List_Detail_Level = new String[length];
                List_Detail_Category = new String[length];
                List_Detail_Detail = new String[length];
                List_Detail_Degree_Goal = new String[length];

                Translate_JSON_List user_list_data[] = new Translate_JSON_List[length];
                for (int i = 0; i < length; i++) {
                    data_list[i] = new selectDatabase_list(IP, null, getContext());
                    try {
                        find_list[i] = data_list[i].execute(List_ID[i]).get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        user_list_data[i] = new Translate_JSON_List(find_list[i]);
                        Title[i] = user_list_data[i].getTitle();
                        List_Term_Start[i] = user_list_data[i].getList_Term_Start();
                        List_Term_End[i] = user_list_data[i].getList_Term_End();
                        List_Time_Start[i] = user_list_data[i].getList_Time_Start();
                        List_Time_End[i] = user_list_data[i].getList_Time_End();
                        List_Level[i] = user_list_data[i].getList_Level();
                        List_Category[i] = user_list_data[i].getList_Category();
                        List_Detail[i] = user_list_data[i].getList_Detail();
                        List_Degree_Goal[i] = user_list_data[i].getList_Degree_Goal();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Title[i] = "";
                        List_Term_Start[i] = "";
                        List_Term_End[i] = "";
                        List_Time_Start[i] = "";
                        List_Time_End[i] = "";
                        List_Level[i] = "";
                        List_Category[i] = "";
                        List_Detail[i] = "";
                        List_Degree_Goal[i] = "";
                    }
                    float total = 0;

                    String strFormat = "yyyy/MM/dd";
                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat(strFormat);
                    String strToday = sdf.format(date);
                    {
                        try {
                            Date startDate = sdf.parse(List_Term_Start[i]);
                            Date endDate = sdf.parse(List_Term_End[i]);
                            Date today = sdf.parse(strToday);

                            float diffDay = (startDate.getTime() - endDate.getTime()) / (24 * 60 * 60 * 1000) * -1;
                            float nowDay = (today.getTime() - endDate.getTime()) / (24 * 60 * 60 * 1000) * -1;
                            total = (nowDay / diffDay) * 100;
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    String sign = "";
                    double per = (100 - (int) total);
                    if (per > 100) {
                        sign = "성공";
                    }
                    else {
                        sign = "실패";
                    }

                    // 제목, 시작기간, 끝난 기간, 디테일 , 퍼센트
                    if (total > 100 || total < 0) {
                        adapter.addItem(Title[i], List_Term_Start[i], List_Term_End[i], List_Detail[i], sign);
                        List_Detail_List_ID[a] = List_ID[i];
                        List_Detail_Title[a] = Title[i];
                        List_Detail_Term_Start[a] = List_Term_Start[i];
                        List_Detail_Term_End[a] = List_Term_End[i];
                        List_Detail_Time_Start[a] = List_Time_Start[i];
                        List_Detail_Time_End[a] = List_Time_End[i];
                        List_Detail_Level[a] = List_Level[i];
                        List_Detail_Category[a] = List_Category[i];
                        List_Detail_Detail[a] = List_Detail[i];
                        List_Detail_Degree_Goal[a] = List_Degree_Goal[i];
                        a++;
                    }
                }
            }
        }
        // Naver  Login이 아닌 경우
        else {
            load_values(adapter);
        }

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

                // Naver Login일 경우
                if (bundle != null) {
                    Intent intent = new Intent(getActivity(), List_Detail.class);
                    intent.putExtra("id", List_Detail_List_ID[position]);
                    intent.putExtra("Title", List_Detail_Title[position]);
                    intent.putExtra("Term_Start", List_Detail_Term_Start[position]);
                    intent.putExtra("Term_End", List_Detail_Term_End[position]);
                    intent.putExtra("Time_Start", List_Detail_Time_Start[position]);
                    intent.putExtra("Time_End", List_Detail_Time_End[position]);
                    intent.putExtra("Level", List_Detail_Level[position]);
                    intent.putExtra("Category", List_Detail_Category[position]);
                    intent.putExtra("Detail", List_Detail_Detail[position]);
                    intent.putExtra("Degree_Goal", List_Detail_Degree_Goal[position]);
                    startActivity(intent);
                }
                else {
                    // get item
                    List_ListviewItem item = (List_ListviewItem) parent.getItemAtPosition(position);
                    String Title = item.getGoal();

                    Intent intent = new Intent(getActivity(), List_Detail.class);
                    intent.putExtra("Title", Title);
                    startActivity(intent);
                }

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