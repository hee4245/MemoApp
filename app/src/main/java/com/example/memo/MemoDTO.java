package com.example.memo;

/**
 * Created by ¼¼Èñ on 2015-06-21.
 */
public class MemoDTO {
    String data;
    int id;
    String date;

    public MemoDTO(int id, String data){
        this.id = id;
        this.data = data;
    }

    public MemoDTO(){}

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
