package com.zoobie.aads.hashing;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class LinearProbing {

    File file = new File("millionints.txt");
    Scanner fileScanner;

    private int[] table;
    private Random rand;
    private final int size;

    public LinearProbing(int size) {
        table = new int[size];
        this.size = size;
        rand = new Random();
    }

    private int random() {
        return rand.nextInt(1000000000);
    }
    private int[] getRandomArray(int n) {
        int data[] = new int[n];

        for(int i = 0; i < n; i++){
            data[i] = random();
        }
        return data;
    }

    private int hashingFunction(int a) {
        return a % this.size;
    }
    public void printTable(){
        for(int i : table){
            System.out.println(i);
        }
    }
    private int[] readIntsFromArray(int n){
        int[] data = new int[n];
        try {
            fileScanner = new Scanner(file);
        }catch(FileNotFoundException e){
            System.err.println(e.getMessage());
            System.exit(-1);
        }
        for(int i = 0; i < n; i++){

            data[i] = fileScanner.nextInt();
        }
        return data;
    }
    public void fillTable(float percent) {
       // int[] arrayFromFile = getRandomArray((int) (this.size * percent));
        int[] arrayFromFile = readIntsFromArray((int) (this.size * percent));
        for (int i : arrayFromFile) {
            int k = hashingFunction(i);
            boolean done = false;
             while (!done) {
                if (table[k] == 0
                ) {
                    table[k] = i;
                    done = true;
                } else {
                    k = hashingFunction(k + 1);
                }
            }

        }
    }

    public SearchResult search(int n){
        int k = hashingFunction(n);
        int i = 1;
        while(table[k]!=0) {
            if (table[k] == n) {
                return new SearchResult(true,i);
            }else{
                k = hashingFunction(k+1);
                i++;
            }
        }
        return new SearchResult(false,i);
    }

    public class SearchResult {
        boolean hit;
        int iterations;

        public SearchResult(boolean hit, int i){
            this.hit = hit;
            this.iterations = i;
        }
    }
}
