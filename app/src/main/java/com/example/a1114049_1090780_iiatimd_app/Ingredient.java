package com.example.a1114049_1090780_iiatimd_app;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Ingredient {

    @PrimaryKey
    private int uuid;

    @ColumnInfo
    private int recipe_id;

    @ColumnInfo
    private String name;

    @ColumnInfo
    private String amount;

    public Ingredient(int uuid, int recipe_id, String name, String amount){
        this.uuid = uuid;
        this.recipe_id = recipe_id;
        this.name = name;
        this.amount = amount;
    }

    public int getUuid(){ return this.uuid; }

    public int getRecipe_id(){ return this.recipe_id; }

    public String getName(){ return this.name; }

    public String getAmount() { return this.amount; }

    public void setName(String name){
        this.name = name;
    }

    public void setAmount(String amount){
        this.amount = amount;
    }
}
