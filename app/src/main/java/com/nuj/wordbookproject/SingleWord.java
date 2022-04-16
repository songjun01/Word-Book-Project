package com.nuj.wordbookproject;

import java.util.GregorianCalendar;

public class SingleWord {
    private String word;
    private String meaning;
    public SingleWord() {
        word = new String("");
        meaning = new String("");
    }
    public SingleWord(String wordStr, String meaningStr) {
        word = new String(wordStr);
        meaning = new String(meaningStr);
    }
    public void setWord(String wordStr, String meaningStr) {
        word = wordStr;
        meaning = meaningStr;
    }
    public void setWordAfterAnalyzing(String line) {
        boolean beforeSpace = true;
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == '-') {
                beforeSpace = false;
            } else {
                if (beforeSpace) {
                    word += line.charAt(i);
                } else {
                    meaning += line.charAt(i);
                }
            }
        }
    }
    public String getWord() {
        return word;
    }
    public String getMeaning() {
        return meaning;
    }
}
