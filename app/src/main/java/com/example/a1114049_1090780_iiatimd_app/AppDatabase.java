package com.example.a1114049_1090780_iiatimd_app;

import android.content.Context;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


//Database klasse waarin de vorm van de database vorm gegeven wordt.
//Migratie 1 naar 2 voegt de instructies tabel toe
//Migratie 2 naar 3 verandert een kolom zodat die niet null is
//Migratie 3 naar 4 voegt de ingrediënten tabel toe

@Database(entities = {Recipe.class, Instruction.class, Ingredient.class}, version = 4)
public abstract class AppDatabase extends RoomDatabase {

    public abstract RecipeDAO recipeDAO();

    private static AppDatabase instance;

    private static final int numberOfThreads = 4;
    static final ExecutorService databaseWriterExecutor = Executors.newFixedThreadPool(numberOfThreads);

    static final Migration MIGRATION_1_2 = new Migration(1,2) {
        @Override
        public void migrate(SupportSQLiteDatabase database){
            database.execSQL("CREATE TABLE 'Instruction' (`uuid` INTEGER NOT NULL, `step_number` INTEGER NOT NULL, `description` TEXT , PRIMARY KEY(`uuid`))");
        }
    };
    static final Migration MIGRATION_2_3 = new Migration(2,3) {
        @Override
        public void migrate(SupportSQLiteDatabase database){
            database.execSQL("ALTER TABLE 'Instruction' ADD `recipe_id` INTEGER DEFAULT 1 NOT NULL");
        }
    };
    static final Migration MIGRATION_3_4 = new Migration(3,4) {
        @Override
        public void migrate(SupportSQLiteDatabase database){
            database.execSQL("CREATE TABLE 'Ingredient' (`uuid` INTEGER NOT NULL, `recipe_id` INTEGER NOT NULL, `name` TEXT ,`amount` TEXT , PRIMARY KEY(`uuid`))");
        }
    };

    static AppDatabase getDatabase(final Context context){
        if (instance == null){
            synchronized (AppDatabase.class){
                if (instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "recipe_database")
                            .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4)
                            .build();
                }
            }
        }
        return instance;
    }
}

