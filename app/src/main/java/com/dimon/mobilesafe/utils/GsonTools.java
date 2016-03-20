package com.dimon.mobilesafe.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dimon on 2016/2/25.
 */
public class GsonTools {
    public GsonTools() {
        // TODO Auto-generated constructor stub
    }

    //使用Gson进行解析Update
    public static <T> T getUpdate(String jsonString, Class<T> cls) {
        T t = null;
        try {
            Gson gson = new Gson();
            t = gson.fromJson(jsonString, cls);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return t;
    }


    // 使用Gson进行解析 List<Update>
    public static <T> List<T> getUpdates(String jsonString, Class<T> cls) {
        List<T> list = new ArrayList<T>();
        try {
            Gson gson = new Gson();
            list = gson.fromJson(jsonString, new TypeToken<List<T>>() {
            }.getType());
        } catch (Exception e) {
        }
        return list;
    }
}
