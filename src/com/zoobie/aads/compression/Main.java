package com.zoobie.aads.compression;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception{
//        Controller controller = new Controller("compressed_standard.txt","decompressed.txt");
      Controller controller = new Controller("decompressed.txt","compressed_standard.txt");
        controller.runCompressor();
//        controller.runDecompressor();

    }
}