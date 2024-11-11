package com.bongoacademy.digitalmoneybag;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddDataActivity extends AppCompatActivity {

    EditText ediAmount,ediReason;
    TextView TVtextView;
    Button button;
    DatabaseHelper databaseHelper;
    public static boolean Expense = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_data);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        TVtextView = findViewById(R.id.TVtextView);
        ediAmount = findViewById(R.id.ediAmount);
        ediReason = findViewById(R.id.ediReason);
        button = findViewById(R.id.button);
        databaseHelper = new DatabaseHelper(this);

        if (Expense == true) {
            TVtextView.setText("Add Expense");
        }else if(Expense==false){
            TVtextView.setText("Add Income");
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double amount = Double.parseDouble(ediAmount.getText().toString());
                String reaseon = ediReason.getText().toString();

                if (Expense==true){
                    databaseHelper.addExpense(amount,reaseon);
                    TVtextView.setText("Expense Inserted");
                }else {
                    databaseHelper.addIncome(amount,reaseon);
                    TVtextView.setText("Income Inserted");
                }

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}