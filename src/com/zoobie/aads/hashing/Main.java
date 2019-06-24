package com.zoobie.aads.hashing;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Main {
    public static void main(String[] args) {

        Hashing hashing = new Hashing(1000003,false);
        hashing.fillTable(0.5f);
        System.out.println("table filled");
//        hashing.printTable();

//        int x = 21391434;
//        Hashing.SearchResult a = hashing.linearProbingSearch(x);
//
//        System.out.println(x);
//
//        if (a.hit) {
//            System.out.println("Search hit after " + a.iterations + " iterations");
//        } else System.out.println("Search miss after " + a.iterations + " iterations");

        hashing.searchExecute();
        hashing.getResults();
    }

    public static void fillIntsFile(){
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(new File("millionints.txt"));

            for (int i = 0; i < 1000000; i++) {
                fileWriter.write(random() + "\n");
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    public static int random() {
        Random rand = new Random();
        return rand.nextInt(100000000);
    }
}
