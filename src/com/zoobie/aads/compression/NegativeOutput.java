package com.zoobie.aads.compression;


public class NegativeOutput implements Output {
    private char letter;

    public NegativeOutput(char letter) {
        this.letter = letter;
    }


    @Override
    public String toString() {
        return "1," + letter;
    }


    @Override
    public boolean getFlag() {
        return true;
    }

    @Override
    public int getOffset() {
        return 0;
    }

    @Override
    public int getSize() {
        return 1;
    }

    public char getLetter() {
        return this.letter;
    }
}