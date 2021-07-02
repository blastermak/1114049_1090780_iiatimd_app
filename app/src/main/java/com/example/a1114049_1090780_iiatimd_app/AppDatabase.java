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




@Database(entities = {Recipe.class, Instruction.class}, version = 3)
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

    static AppDatabase getDatabase(final Context context){
        if (instance == null){
            synchronized (AppDatabase.class){
                if (instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "recipe_database")
                            .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                            .build();
                }
            }
        }
        return instance;
    }
}

