package com.zoobie.aads.hashing;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class LinearProbing {

    File file = new File("millionints.txt");
    Scanner fileScanner;

    private int[] table;
    private ArrayList<Integer> hits;
    private ArrayList<Integer> misses;
    private Random rand;
    private final int size;
    private float percent = 1;
    public LinearProbing(int size) {
        table = new int[size];
        this.size = size;
        rand = new Random();
        this.hits = new ArrayList<>();
        this.misses = new ArrayList<>();
    }

    private int random() {
        return rand.nextInt(100000000);
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
        this.percent = percent;
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
    public void searchExecute(){
        int n = (int)(size * percent * 0.1);
        int[] searchArray = new int[n];
        try {
            fileScanner = new Scanner(new File("millionints.txt"));
        }catch(FileNotFoundException e){
            System.err.println(e.getMessage());
            return;
        }
        for(int i = 0; i < size * percent / 2; i++){
            fileScanner.nextInt();
        }
        for(int i = 0; i < n/2; i++){
            searchArray[i] = fileScanner.nextInt();
            System.out.println(searchArray[i]);
        }
        for(int i = n / 2; i < n; i++){
            searchArray[i] = random();
        }
        SearchResult result;
        for(int i : searchArray){
            result = search(i);
            if(result.hit) hits.add(result.iterations);
            else misses.add(result.iterations);
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
    public void getResults(){
        int sum = 0;
        for(int i : hits){
            sum+=i;
        }
        System.out.println("Average hits " + (float)sum/(float)hits.size());
        sum = 0;
        for(int i : misses){
            sum+=i;
        }
        System.out.println("Average misses " + (float)sum/(float)misses.size());

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
