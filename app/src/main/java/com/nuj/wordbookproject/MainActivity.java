package com.nuj.wordbookproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.GregorianCalendar;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    EditText wordMeView, wordEdit, meaningEdit;
    Button ok;
    String editWord;
    String editMeaning;
    String groupWord;
    String groupMeaning;
    final int REQUEST_CHANGE = 1001;
    static int count = 1;
    static String lineStr;
    SingleWord single;
    private boolean delete = false;
    public static LinkedList<SingleWord> group;
    public static String filename = "word.book";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wordMeView = findViewById(R.id.idWordMeView);
        wordEdit = findViewById(R.id.idWordEdit);
        meaningEdit = findViewById(R.id.idMeanEdit);
        ok = findViewById(R.id.idDeleteTrue);
        lineStr = new String("");
        group = new LinkedList<SingleWord>();
        ok.setVisibility(View.INVISIBLE);
        loadFromFile();
    }
    public void onEnrollmentClicked(View v) {
        if (wordEdit.getText().toString().length() != 0 && meaningEdit.getText().toString().length() != 0) {
            int size = group.size();
            editWord = wordEdit.getText().toString();
            for (int i = 0; i < size; i++) {
                groupWord = group.get(i).getWord();
                if (editWord.equals(groupWord)) {
                    Toast.makeText(this, "이미 추가한 단어입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            single = new SingleWord();
            single.setWord(wordEdit.getText().toString(), meaningEdit.getText().toString());
            lineStr += ("  " + single.getWord() + "  " + single.getMeaning() + "\n");
            wordMeView.setText(lineStr);
            group.add(single);
            count++;
            Toast.makeText(this, "단어를 추가했습니다.", Toast.LENGTH_SHORT).show();
            wordEdit.setText("");
            meaningEdit.setText("");
        }
        else {
            Toast.makeText(this, "추가할 단어를 입력해 주세요.", Toast.LENGTH_SHORT).show();
        }
    }
    public void onDeleteClicked(View v) {
        if (!delete) {
            delete = true;
            wordEdit.setText("");
            Toast.makeText(this, "입력란에 삭제할 단어를 입력하고 삭제하기를 누르세요. 삭제버튼을 다시 누르면 삭제하기가 사라집니다.", Toast.LENGTH_SHORT).show();
            ok.setVisibility(View.VISIBLE);
        }
        else {
            delete = false;
            ok.setVisibility(View.INVISIBLE);
        }
    }
    public void onDeleteTrueClicked(View v) {
        if (wordEdit.getText().toString().length() != 0) {
            editWord = wordEdit.getText().toString();
            int size = group.size();
            for (int i = 0; i < size; i++) {
                groupWord = group.get(i).getWord();
                if (editWord.equals(groupWord)) {
                    group.remove(i);
                    Toast.makeText(this, "입력하신 단어가 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            Toast.makeText(this, "삭제할 단어가 없습니다.", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "삭제할 단어를 입력해 주세요.", Toast.LENGTH_SHORT).show();
        }
    }
    public void onSearchClicked(View v) {
        if (wordEdit.getText().toString().length() != 0) {
            editWord = wordEdit.getText().toString();
            int size = group.size();
            int searchNumber = 0;
            String announcement = "검색하신 단어는 ";
            for (int i = 0; i < size; i++) {
                groupWord = group.get(i).getWord();
                if (editWord.equals(groupWord)) {
                    searchNumber = i + 1;
                    announcement += String.valueOf(searchNumber);
                    announcement += " 번째에 있습니다.";
                    Toast.makeText(this, announcement, Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }
        Toast.makeText(this, "검색할 단어를 입력해 주세요.", Toast.LENGTH_SHORT).show();
    }
    public void onSaveClicked(View v) {
        saveToFile();
        Toast.makeText(this, "단어가 저장되었습니다", Toast.LENGTH_SHORT).show();
    }
    public void onTotalClicked(View v) {
        Intent intent = new Intent(this, TotalActivity.class);
        startActivityForResult(intent, REQUEST_CHANGE);
    }

    private void saveToFile() {
        File file = new File(getFilesDir(), filename);
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
            bw.write(group.size());
            for (int i = 0; i <group.size(); i++) {
                bw.write(group.get(i).getWord() + "-" + group.get(i).getMeaning() + "\n");
            }
            bw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (bw != null) {
                bw.close();
            }
            if (fw != null) {
                fw.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void loadFromFile() {
        File file = new File(getFilesDir(), filename);
        FileReader fr = null;
        BufferedReader br = null;
        if (file.exists()) {
            try {
                fr = new FileReader(file);
                br = new BufferedReader(fr);
                int size = br.read();
                for (int i = 0; i < size; i++) {
                    single = new SingleWord();
                    single.setWordAfterAnalyzing(br.readLine());
                    group.add(single);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (fr != null) {
                    fr.close();
                }
                if (br != null) {
                    br.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}