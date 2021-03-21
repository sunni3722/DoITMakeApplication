package com.cookandroid.bottom_setting;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;


@IgnoreExtraProperties
public class FirebasePost {
    public String ID;
    public String NAME;
    public String PASSWORD;
    public String level;


    public FirebasePost(){
        // Default constructor required for calls to DataSnapshot.getValue(FirebasePost.class)
    }

    public FirebasePost(String id, String name, String password, String level) {
        this.ID = id;
        this.NAME = name;
        this.PASSWORD = password;
        this.level=level;

    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", NAME);
        result.put("id", ID);
        result.put("password", PASSWORD);
        result.put("level", level);
        return result;
    }
}
