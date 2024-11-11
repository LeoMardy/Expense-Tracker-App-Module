package com.bongoacademy.digitalmoneybag;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context) {
        super(context, "digitalMoneyBag", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table expense (id integer primary key autoincrement, amount double, reason text, time double)");
        db.execSQL("create table income (id integer primary key autoincrement, amount double, reason text, time double)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table expense");
        db.execSQL("drop table income");
    }

    public void addExpense(double amount, String reason) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("amount", amount);
        cv.put("reason", reason);
        cv.put("time", System.currentTimeMillis());
        db.insert("expense", null, cv);
        db.close();
    }

    public void addIncome(double amount, String reason) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("amount", amount);
        cv.put("reason", reason);
        cv.put("time", System.currentTimeMillis());
        db.insert("income", null, cv);
        db.close();
    }

    public double calculateTotalExpenseAmount() {

        double TotalExpenseAmount = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select* from expense", null);

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                double amount = cursor.getDouble(1);
                TotalExpenseAmount = TotalExpenseAmount + amount;
            }

        }
        return TotalExpenseAmount;
    }

    public double calculateTotalIncomeAmount() {
        double totalIncomeAmount = 0;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select* from income", null);

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                double amount = cursor.getDouble(1);
                totalIncomeAmount = totalIncomeAmount + amount;
            }
        }
        return totalIncomeAmount;
    }

    public Cursor getAlExpenselDAta() {
        SQLiteDatabase db1 = this.getReadableDatabase();
        Cursor cursor = db1.rawQuery("select* from expense order by id desc", null);
        return cursor;
    }
    public Cursor getAlIncomeDAta() {
        SQLiteDatabase db1 = this.getReadableDatabase();
        Cursor cursor = db1.rawQuery("select* from income order by id desc", null);
        return cursor;
    }
    public void deleteExpenseByID (String id){
    SQLiteDatabase db = getWritableDatabase();
    db.execSQL("delete from expense where id like "+id);

    }
    public void deleteIncomeByID (String id){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from income where id like "+id);

    }
}
