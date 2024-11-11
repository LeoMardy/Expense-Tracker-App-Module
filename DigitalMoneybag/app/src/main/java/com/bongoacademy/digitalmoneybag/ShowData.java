package com.bongoacademy.digitalmoneybag;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class ShowData extends AppCompatActivity {

    TextView tv_title;
    ListView listView;
    DatabaseHelper databaseHelper;
    HashMap<String, String> hashMap;
    ArrayList<HashMap<String, String>> arrayList;

    public static boolean EXPENSES = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);
        tv_title = findViewById(R.id.tv_title);
        listView = findViewById(R.id.listView);
        databaseHelper = new DatabaseHelper(this);

        if (EXPENSES == true) tv_title.setText("All Expenses");
        else tv_title.setText("All Incomes");

        loadData();
    }

    public void loadData() {
        Cursor cursor = null;

        if (EXPENSES == true) cursor = databaseHelper.getAlExpenselDAta();
        else cursor = databaseHelper.getAlIncomeDAta();

        if (cursor != null && cursor.getCount() > 0) {

            arrayList = new ArrayList<>();

            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                double amount = cursor.getDouble(1);
                String reason = cursor.getString(2);
                double time = cursor.getDouble(3);

                hashMap = new HashMap<>();
                hashMap.put("id", "" + id);
                hashMap.put("amount", "" + amount);
                hashMap.put("reason", reason);
                hashMap.put("time", "" + time);
                arrayList.add(hashMap);
            }
            MyAdapter myAdapter = new MyAdapter();
            listView.setAdapter(myAdapter);

        } else {
            tv_title.append("\nNo data found!");
        }
    }

    public class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View myView = inflater.inflate(R.layout.item, parent, false);

            Button deleteButton = myView.findViewById(R.id.buttonDelete);
            TextView TVreaseon = myView.findViewById(R.id.TVreason);
            TextView TVAmount = myView.findViewById(R.id.TVAmount);
            RelativeLayout layExpense = myView.findViewById(R.id.layExpense);

            hashMap = arrayList.get(position);

            String id = hashMap.get("id");
            String amount = hashMap.get("amount");
            String reason = hashMap.get("reason");
            String time = hashMap.get("time");

            TVAmount.setText(amount);
            TVreaseon.setText(reason);

            layExpense.setBackgroundResource(getRandomColor());
            setAnimation(layExpense, position);

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (EXPENSES == true) databaseHelper.deleteExpenseByID(id);
                    else databaseHelper.deleteIncomeByID(id);

                    loadData();
                }
            });


            return myView;
        }
    }

    private int getRandomColor() {
        List<Integer> colorCode = new ArrayList<>();
        colorCode.add(R.color.cyan_200);
        colorCode.add(R.color.orange_50);
        colorCode.add(R.color.yellow_A100);
        colorCode.add(R.color.cyan_100);
        colorCode.add(R.color.purple_100);
        colorCode.add(R.color.light_green_200);



        Random random = new Random();
        int num = random.nextInt(colorCode.size());

        return colorCode.get(num);
    }

    private void setAnimation(View viewToAnimate, int position) {
        Animation animation = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
        viewToAnimate.setAnimation(animation);
    }

}