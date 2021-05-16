package com.cookandroid.bottom_setting;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class FirebasePost_data {
    public String Key;
    public String ID;
    public String Title;
    public String list_term_start;
    public String list_term_end;
    public String list_time_start;
    public String list_time_end;
    public String list_level;
    public String list_category;
    public String list_detail;
    public String list_degree_goal;

    public FirebasePost_data(String id, String Title, String Start_term, String End_term, String Start_time, String End_time,
                             String level, String category, String detail, String degree){

        this.ID = id;
        this.Title=Title;
        this.list_term_start=Start_term;
        this.list_term_end=End_term;
        this.list_time_start=Start_time;
        this.list_time_end=End_time;
        this.list_level=level;
        this.list_category=category;
        this.list_detail=detail;
        this.list_degree_goal=degree;

    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", ID);
        result.put("title", Title);
        result.put("start_term", list_term_start);
        result.put("end_term", list_term_end);
        result.put("start_term", list_time_start);
        result.put("end_term", list_time_end);
        result.put("level", list_level);
        result.put("category", list_category);
        result.put("detail", list_detail);
        result.put("degree", list_degree_goal);
        return result;
    }

}
