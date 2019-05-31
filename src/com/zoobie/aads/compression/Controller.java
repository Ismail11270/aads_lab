package com.zoobie.aads.compression;


import java.io.*;
import java.util.*;

public class Controller {
    private Compressor compressor;
    private Compressor decompressor;
    private File inputFile;
    private File outputFile;
    private File binaryFile;
    private Scanner scanner;
    private Scanner fileScanner;
    private FileWriter fileWriter = null;
    private FileReader fileReader = null;

    private String readFromFile() {
        try {
            fileScanner = new Scanner(inputFile);
        } catch (FileNotFoundException e) {
            System.err.println("Input file not found, terminating the program!");
            System.exit(-1);
        }
        String input = "";

        while (fileScanner.hasNextLine()) {
            input += fileScanner.nextLine() + "\n";
        }
        return input;
    }

    public List<Output> readStandardResultsFromFile() {
        try {
            fileScanner = new Scanner(inputFile);
            fileScanner.useDelimiter(",");
        } catch (FileNotFoundException e) {
            System.err.println("Input file not found, terminating the program!");
            System.exit(-1);
        }

        List<Output> results = new ArrayList<>();
        String output = "";
        int flag;
        int size = 0;
        int offset = 0;
        String letterString;
        Character letter;
        output.split(",");
        while (fileScanner.hasNextInt()) {
            flag = fileScanner.nextInt();
            try {
                if (flag == 0) {
                    fileScanner.skip(fileScanner.delimiter());
                    offset = fileScanner.nextInt();
                    fileScanner.skip(fileScanner.delimiter());
                    size = Integer.parseInt(fileScanner.nextLine());
                    results.add(new PositiveOutput(offset, size));
                } else if (flag == 1) {
                    fileScanner.skip(fileScanner.delimiter());
                    letterString = fileScanner.nextLine();
                    if(letterString.length() > 0){
                        letter = letterString.charAt(0);
                    }else letter = ' ';
                    results.add(new NegativeOutput(letter));
                }

            } catch (NumberFormatException | InputMismatchException e) {
                System.err.println("Wrong compressed data in file " + inputFile.toString() + "\n" + "Terminating the program!");
                System.exit(-1);
            }
        }
        return results;
    }

    public Controller(String inputFile, String outputFile) {
        this.inputFile = new File(inputFile);
        this.outputFile = new File(outputFile);
    }

    public void writeStandardOutputToFile(List<? extends Output> list) throws IOException {
        fileWriter = new FileWriter(outputFile);
        for (Output a : list) {
            fileWriter.write(a.toString() + "\n");
        }
        fileWriter.close();
    }

    public void writeStringToFile(String text)
            throws IOException {
        fileWriter = new FileWriter(outputFile);
        fileWriter.write(text);
        fileWriter.close();
    }

    public void runCompressor() {
        String input = "";
        input = readFromFile();

        compressor = new Compressor(input);
        compressor.compress();
        try {
            writeStandardOutputToFile(compressor.getResult());
           // writeStringToFile(compressor.getBinaryOutput());
        } catch (IOException e) {
            System.out.println(e);
        }
        System.out.println(compressor.getBinaryOutput());

    }

    public void runDecompressor() {
        List<Output> compressedInput = new ArrayList<>();
        compressedInput = readStandardResultsFromFile();
        System.out.print("Enter the dictionary letter: ");
        scanner = new Scanner(System.in);
        char a = scanner.nextLine().charAt(0);
        compressor = new Compressor(compressedInput, a);
        String result = compressor.decompress();
        try {
            writeStringToFile(result);
        }catch(IOException e){
            System.out.println(e + " file was not found.");
        }finally {
            System.out.println(result);
        }
    }
}