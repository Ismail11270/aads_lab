package com.zoobie.aads.compression;

public interface Output {
    int getOffset();
    int getSize();
    char getLetter();
    @Override
    String toString();
    boolean getFlag();
}