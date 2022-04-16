package com.nuj.wordbookproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.GregorianCalendar;

public class TotalActivity extends AppCompatActivity {
    EditText totalView, wordTotalView, meanTotalView;
    TextView saveNumber;
    static String lineStrN, lineStrW, lineStrM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total);
        totalView = findViewById(R.id.idtotalView);
        saveNumber = findViewById(R.id.idSaveNumber);
        lineStrN = new String("");
        int size = MainActivity.group.size();
        for (int i = 0; i < size; i++) {
            lineStrN += ("  " + (i+1) + "  " + MainActivity.group.get(i).getWord() + "  " + MainActivity.group.get(i).getMeaning() + "\n");
        }
        totalView.setText(lineStrN);
        String saveSize = String.valueOf(size);
        saveNumber.setText(saveSize);
    }
    public void onBackClicked(View v) {
        finish();
    }
}