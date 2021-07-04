package com.example.a1114049_1090780_iiatimd_app;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


// Recept klasse
// uuid: primaire sleutel
// title: de naam van het recept
// description_short: korte beschrijving van het recept
// description: uitgebreide beschrijving van het recept
// prep_time_min: hoe lang het duurt om te maken

@Entity
public class Recipe {

    @PrimaryKey
    private int uuid;

    @ColumnInfo
    private String title;

    @ColumnInfo
    private String description_short;

    @ColumnInfo
    private String description;

    @ColumnInfo
    private int prep_time_min;

    public Recipe(int uuid, String title, String description_short, String description, int prep_time_min){
        this.uuid = uuid;
        this.title = title;
        this.description_short = description_short;
        this.description = description;
        this.prep_time_min = prep_time_min;
    }

    public String getTitle(){
        return this.title;
    }

    public String getDescription_short() {
        return this.description_short;
    }

    public String getDescription() {
        return this.description;
    }

    public int getPrep_time_min() {
        return this.prep_time_min;
    }

    public int getUuid() {
        return this.uuid;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription_short(String description_short) { this.description_short = description_short; }

    public void setDescription(String description) { this.description = description; }

    public void setPrep_time_min(int prep_time_min) { this.prep_time_min = prep_time_min; }
}
