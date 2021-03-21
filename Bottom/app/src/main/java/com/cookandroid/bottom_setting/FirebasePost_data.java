package com.cookandroid.bottom_setting;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class FirebasePost_data {
    public String ID;
    public String Title;
    public String Start_term;
    public String End_term;
    public String degree;

    public FirebasePost_data(String id, String Title, String Start_term, String End_term, String degree){
        this.ID = id;
        this.Title=Title;
        this.Start_term=Start_term;
        this.End_term=End_term;
        this.degree=degree;

    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", ID);
        result.put("title", Title);
        result.put("start_term", Start_term);
        result.put("end_term", End_term);
        result.put("degree", degree);
        return result;
    }

}
