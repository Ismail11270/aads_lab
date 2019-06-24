package com.zoobie.aads.hashing;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Hashing {

    File file = new File("randomnumbers.txt");
    Scanner fileScanner;

    private long[] table;
    private ArrayList<Integer> hits;
    private ArrayList<Integer> misses;
    private Random rand;
    private final int size;
    private float percent = 1;
    private boolean doubleHashing;
    public Hashing(int size, boolean doubleHashing) {
        table = new long[size];
        this.doubleHashing = doubleHashing;
        this.size = size;
        rand = new Random();
        this.hits = new ArrayList<>();
        this.misses = new ArrayList<>();
    }

    private int random() {
        return rand.nextInt(100000000);
    }

    private int hashingFunction(long a) {
        return (int)(a % this.size);
    }

    private int doubleHashingFunction(long a, int i){
        long hash1 = a % size;
        long hash2 = size - a % size;
        return (int)((hash1 + i*hash2) % size);
    }
    public void printTable(){
        for(long i : table){
            System.out.println(i);
        }
    }

    private long[] readIntsFromArray(int n){
        long[] data = new long[n];
        try {
            fileScanner = new Scanner(file);
        }catch(FileNotFoundException e){
            System.err.println(e.getMessage());
            System.exit(-1);
        }
        for(int i = 0; i < n; i++){
            data[i] = fileScanner.nextLong();
        }
        return data;
    }
    public void fillTable(float percent) {
        this.percent = percent;
       // int[] arrayFromFile = getRandomArray((int) (this.size * percent));
        long[] arrayFromFile = readIntsFromArray((int) (this.size * percent));
        int j;
        for (long i : arrayFromFile) {
            j = 1;
            int k;
            if(doubleHashing) k = doubleHashingFunction(i,j);
            else k = hashingFunction(i);
            boolean done = false;
             while (!done) {
                if (table[k] == 0
                ) {
                    table[k] = i;
                    done = true;
                } else {
                    j++;
                    if(doubleHashing) k = doubleHashingFunction(i,j);
                    else k = hashingFunction(k + 1);
                }
            }

        }
    }
    public void searchExecute(){
        int n = (int)(size * percent * 0.1);
        long[] searchArray = new long[n];
        try {
            fileScanner = new Scanner(file);
        }catch(FileNotFoundException e){
            System.err.println(e.getMessage());
            return;
        }
        for(int i = 0; i < size * percent / 2; i++){
            fileScanner.nextLong();
        }
        for(int i = 0; i < n/2; i++){
            searchArray[i] = fileScanner.nextLong();
            System.out.println(searchArray[i]);
        }
        try {
            fileScanner = new Scanner(new File("searchmiss.txt"));
        }catch(FileNotFoundException e){
            System.err.println(e.getMessage());
            return;
        }
        for(int i = n / 2; i < n; i++){
            searchArray[i] = fileScanner.nextLong();
        }
        SearchResult result;
        for(long i : searchArray){
            result = linearProbingSearch(i);
            if(result.hit) hits.add(result.iterations);
            else misses.add(result.iterations);
        }
    }
    public SearchResult linearProbingSearch(long n){
        int k;
        int j = 1;
        if(doubleHashing) k = doubleHashingFunction(n,j);
        else k = hashingFunction(n);
        int i = 1;
        while(table[k]!=0) {
            if (table[k] == n) {
                return new SearchResult(true,i);
            }else{
                j++;
                if(doubleHashing) k = doubleHashingFunction(n,j);
                else k = hashingFunction(k+1);
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
