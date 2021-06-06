package com.example.a1114049_1090780_iiatimd_app;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Recipe {

    @PrimaryKey
    private int uuid;

    @ColumnInfo
    private String name;

    @ColumnInfo
    private String summary;

    public Recipe(int uuid, String name, String summary){
        this.uuid = uuid;
        this.name = name;
        this.summary = summary;
    }

    public String getName(){
        return this.name;
    }

    public String getSummary() {
        return this.summary;
    }

    public int getUuid() {
        return this.uuid;
    }
}
