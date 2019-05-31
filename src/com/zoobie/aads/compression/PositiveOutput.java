package com.zoobie.aads.compression;

public class PositiveOutput implements Output {
    private int offset;
    private int size;

    public PositiveOutput(int offset, int size) {
        this.offset = offset;
        this.size = size;
    }

    @Override
    public String toString(){
        return "0," + offset + "," + size;
    }

    public char getLetter() { return 'a';}
    @Override
    public boolean getFlag() {
        return false;
    }

    public int getOffset() {
        return offset;
    }

    public int getSize() {
        return size;
    }
}