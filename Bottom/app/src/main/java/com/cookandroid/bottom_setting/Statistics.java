package com.cookandroid.bottom_setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class Statistics extends Fragment {

    // Bundle에서 ID정보 받아오기
    String id;
    String find;
    Bundle bundle;
    String title;
    String start;
    String end;
    String detail;

    // ID & List_ID 받아오기
    String[] List_ID;
    String final_list_id;
    int length;

    // List_ID로 List Data 받아오기
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

    // Statistics에 추가할 데이터
    int[] week = { 0, 0, 0, 0, 0, 0 };



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.statistics, container, false);

        bundle = getArguments();

        String IP = getString(R.string.web_IP);

        // 그래프 종류 설정
        BarChart barchart = (BarChart) view.findViewById(R.id.chart);
        ArrayList<BarEntry> entries = new ArrayList<>();

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
                final_list_id = List_ID[length-1];
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
                    String strFormat = "yyyy/MM/dd";
                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat(strFormat);
                    String strToday = sdf.format(date);
                    Date endDate;
                    Date today;
                    Calendar cal = Calendar.getInstance();
                    {
                        try {
                            // 과거의 성공 이력 저장
                            endDate = sdf.parse(List_Term_End[i]);
                            today = sdf.parse(strToday);
                            for (int l = 5; l >= 0; l--) {
                                cal.setTime(today);
                                cal.add(Calendar.DATE, -l * 7);
                                String before = sdf.format(cal.getTime());
                                Date befor = sdf.parse(before);
                                if (endDate.before(befor)) {
                                    week[l] = week[l] + 1;
                                    break;
                                }
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            // 그래프에 넣을 점 설정
            entries.add(new BarEntry(0, week[5]));
            entries.add(new BarEntry(1, week[4]));
            entries.add(new BarEntry(2, week[3]));
            entries.add(new BarEntry(3, week[2]));
            entries.add(new BarEntry(4, week[1]));
            entries.add(new BarEntry(5, week[0]));
        }
        else {
        // 그래프에 넣을 점 설정
        entries.add(new BarEntry(0, 1));
        entries.add(new BarEntry(1, 3));
        entries.add(new BarEntry(2, 2));
        entries.add(new BarEntry(3, 1));
        entries.add(new BarEntry(4, 4));
        entries.add(new BarEntry(5, 8));
        }
        // 세로축 설정
        BarDataSet dataset = new BarDataSet(entries, "달성 목표 수");
        dataset.setAxisDependency(YAxis.AxisDependency.RIGHT);

        // 차트에 데이터 입력
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        BarData data = new BarData(dataset);
        barchart.setData(data);
        barchart.setDrawGridBackground(false);

        // 애니메이션 설정
        // 설정시 그래프가 한번에 나타나는 것이 아니라 창 이동시 그래프의 선이 제자리 찾아가면서 나타남
        barchart.animateY(1500);
        barchart.invalidate();


        return view;
    }
}