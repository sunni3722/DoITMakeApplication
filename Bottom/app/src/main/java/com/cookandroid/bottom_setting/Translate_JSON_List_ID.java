package com.cookandroid.bottom_setting;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class Translate_JSON_List_ID {

    private int length;
    private JSONObject Object;
    private String info;
    private String[] info_array;
    private JSONObject[] cleanObject;
    private String ID[];
    private String List_ID[];

    Translate_JSON_List_ID(String a) throws JSONException {
        try {
            this.Object = new JSONObject(a);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        info = Object.getString("naver_profile");

        info = info.replace("[", "");
        info = info.replace("]", "");
        info = info.replace("},", "}, ");
        info_array = info.split(", ");
        length = info_array.length;
        cleanObject = new JSONObject[length];
        ID = new String[length];
        List_ID = new String[length];
        for (int i = 0; i < length; i++) {
            cleanObject[i] = new JSONObject(info_array[i]);
            ID[i] = cleanObject[i].getString("id");
            List_ID[i] = cleanObject[i].getString("list_id");
        }
    }

    public String[] getList_ID() {

        return List_ID;
    }

    public String[] getID() {

        return ID;
    }

    public int getlength() {

        return length;
    }
}
