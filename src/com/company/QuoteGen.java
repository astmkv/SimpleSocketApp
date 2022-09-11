package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class QuoteGen {

    public static String getQuote() throws IOException {
        Random r = new Random();
        int x = r.nextInt(35);

        FileReader fr = null;
        BufferedReader reader = null;
        String line;

        try {
            fr = new FileReader("D:\\JAVA ОБУЧЕНИЕ\\JAVA_SERVERS\\SimpleSocketApp\\src\\com\\company\\quotes.txt");
            reader = new BufferedReader(fr);
            line = reader.readLine();
            for(int i = 1; i <= 35; i++) {
                reader.readLine();
                if (i == x) {
                   line = "\"" +  reader.readLine() + "\"";
                   break;
                }
            }
        } finally {
            if (fr != null ) {
            fr.close();
            }
            if(reader != null) {
                reader.close();
            }
        } return line;
        }
        }