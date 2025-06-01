package com.example.garage_car_app.model;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Car.class, Repair.class, Inspection.class, FuelEntry.class}, version = 6, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract CarDao carDao();
    public abstract RepairDao repairDao();
    public abstract InspectionDao inspectionDao();
    public abstract FuelEntryDao fuelEntryDao();
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE Car ADD COLUMN registration TEXT");
        }
    };

    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS Repair (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, carId INTEGER NOT NULL, description TEXT, date TEXT)");
            database.execSQL("CREATE TABLE IF NOT EXISTS Inspection (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, carId INTEGER NOT NULL, date TEXT, notes TEXT)");
        }
    };

    public static synchronized AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "car_database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }
}



