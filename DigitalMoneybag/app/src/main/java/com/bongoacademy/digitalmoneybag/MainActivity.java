package com.bongoacademy.digitalmoneybag;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView TVtotalBalance, TVtotalExpense, addExpense, showAllExpense,addIncome,TVincome,showAllIncome;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TVtotalBalance = findViewById(R.id.TVtotalBalance);
        TVtotalExpense = findViewById(R.id.TVtotalExpense);
        addExpense = findViewById(R.id.addExpense);
        showAllExpense = findViewById(R.id.showAllExpense);
        addIncome = findViewById(R.id.addIncome);
        TVincome= findViewById(R.id.TVincome);
        showAllIncome = findViewById(R.id.showAllIncome);
        dbHelper = new DatabaseHelper(this);


        addExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddDataActivity.Expense = true;
                startActivity(new Intent(MainActivity.this, AddDataActivity.class));
            }
        });
        addIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddDataActivity.Expense=false;
                startActivity(new Intent(MainActivity.this, AddDataActivity.class));
            }
        });
        showAllExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowData.EXPENSES=true;
                startActivity(new Intent(MainActivity.this, ShowData.class));
            }
        });
        showAllIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowData.EXPENSES=false;
                startActivity(new Intent(MainActivity.this, ShowData.class));
            }
        });

        updateUi();

    }


    //===================================================================
    public void updateUi() {

        TVtotalExpense.setText("BDT : " + dbHelper.calculateTotalExpenseAmount());
        TVincome.setText("BDT: "+dbHelper.calculateTotalIncomeAmount());

        double balance = dbHelper.calculateTotalIncomeAmount()-dbHelper.calculateTotalExpenseAmount();
        TVtotalBalance.setText("BDT: "+balance);
    }


    @Override
    protected void onResume() {
        super.onResume();
        updateUi();
    }
}