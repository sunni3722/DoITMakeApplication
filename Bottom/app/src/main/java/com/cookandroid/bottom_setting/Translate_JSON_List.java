package com.cookandroid.bottom_setting;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class Translate_JSON_List {

    private JSONObject Object;
    private String info;
    private JSONObject cleanObject;
    private String Title;
    private String List_Term_Start;
    private String List_Term_End;
    private String List_Time_Start;
    private String List_Time_End;
    private String List_Level;
    private String List_Category;
    private String List_Detail;
    private String List_Degree_Goal;

    Translate_JSON_List(String a) throws JSONException {
        try {
            this.Object = new JSONObject(a);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        info = Object.getString("naver_profile");

        info = info.replace("[", "");
        info = info.replace("]", "");
        cleanObject = new JSONObject(info);
        info = cleanObject.getString("list_contents");
        cleanObject = new JSONObject(info);
        info = info.replace("\\", "");
        Title = cleanObject.getString("title");
        List_Term_Start = cleanObject.getString("list_term_start");
        List_Term_End = cleanObject.getString("list_term_end");
        List_Term_Start = List_Term_Start.replace("\\", "");
        List_Term_End = List_Term_End.replace("\\", "");
        List_Time_Start = cleanObject.getString("list_time_start");
        List_Time_End = cleanObject.getString("list_time_end");
        List_Level = cleanObject.getString("list_level");
        List_Category = cleanObject.getString("list_category");
        List_Detail = cleanObject.getString("list_detail");
        List_Degree_Goal = cleanObject.getString("list_degree_goal");

    }

    public String getTitle() {

        return Title;
    }

    public String getList_Term_Start() {

        return List_Term_Start;
    }

    public String getList_Term_End() {

        return List_Term_End;
    }

    public String getList_Time_Start() {

        return List_Time_Start;
    }

    public String getList_Time_End() {

        return List_Time_End;
    }

    public String getList_Level() {

        return List_Level;
    }

    public String getList_Category() {

        return List_Category;
    }

    public String getList_Detail() {

        return List_Detail;
    }

    public String getList_Degree_Goal() {

        return List_Degree_Goal;
    }
}
