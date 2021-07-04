package com.example.a1114049_1090780_iiatimd_app;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Instruction {

    @PrimaryKey
    private int uuid;

    @ColumnInfo
    private int recipe_id;

    @ColumnInfo
    private int step_number;

    @ColumnInfo
    private String description;

    public Instruction(int uuid, int recipe_id, int step_number, String description){
        this.uuid = uuid;
        this.recipe_id = recipe_id;
        this.step_number = step_number;
        this.description = description;
    }

    public int getUuid(){ return this.uuid; }

    public int getRecipe_id(){ return this.recipe_id; }

    public int getStep_number(){ return this.step_number; }

    public String getDescription() { return this.description; }

    public void setStep_number(int step_number){
        this.step_number = step_number;
    }

    public void setDescription(String description){
        this.description = description;
    }
}
