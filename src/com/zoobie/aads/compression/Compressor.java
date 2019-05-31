package com.zoobie.aads.compression;


import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Compressor {

    private final int dictionarySize = 8;
    private final int bufferSize = 4;
    private String decompressedString;
    private Vector<Character> inputVec;
    private Vector<Character> dictAndBuff;
    private String dictionary;

    public Compressor(String inputString) {
        this.decompressedString = inputString;
        inputVec = new Vector<>();
        dictAndBuff = new Vector<>();
    }

    /**
     * Constructor for decompressor
     * @param result
     * @param dictionary
     */
    public Compressor(List<Output> result, Character dictionary){
        this.result = result;
        this.dictionary = "";
        for(int i = 0; i < dictionarySize; i++){
            this.dictionary += dictionary;
        }
        this.decompressedString = "";
    }

    private List<Output> result = new ArrayList<>();


    public void compress() {

        char[] input;

        input = decompressedString.toCharArray();

        for (Character i : input) {
            inputVec.add(i);
        }
        //Initialize the dictionary
        for (int i = 0; i < dictionarySize; i++) {
            dictAndBuff.add(input[0]);
        }
        //Buffer
        for (int i = 0; i < bufferSize; i++) {
            dictAndBuff.add(inputVec.get(0));
            inputVec.removeElementAt(0);
        }

        System.out.print(dictAndBuff);
        System.out.println(inputVec);


        while(dictAndBuff.size() > dictionarySize + 1){
            Output output = contains(dictAndBuff);
            System.out.println(output);
            moveLense(output.getSize());
            result.add(output);
            System.out.println(dictAndBuff);
        }

        printResults();
    }

    public String decompress(){
        for(Output i : result){
//            Output i = result.get(0);
            if(!i.getFlag()){
                String a = dictionary.substring(dictionarySize - i.getOffset() - 1,dictionarySize - i.getOffset() + i.getSize() - 1);
                System.out.println(a);
                dictionary = dictionary.substring(0+i.getSize(),dictionarySize);
                dictionary += a;
                decompressedString +=a;
            }else{
                dictionary = dictionary.substring(1,dictionarySize);
                dictionary+=i.getLetter();
                System.out.println(i.getLetter());
                decompressedString +=i.getLetter();
            }
        }
        System.out.println(decompressedString);
        return decompressedString;
    }
    private Output contains(Vector<Character> dictAndBuff) {
        String dict = "";
        String buff = "";
        for (int i = 0; i < dictionarySize; i++) {
            dict += dictAndBuff.get(i);
        }
        boolean canAddToBuffer = true;
        for (int i = dictionarySize; i < bufferSize + dictionarySize && canAddToBuffer; i++) {
            try {
                buff += dictAndBuff.get(i);
            }catch(ArrayIndexOutOfBoundsException e){
                canAddToBuffer = false;
            }
        }
//        System.out.println("dict " + dict);
//        System.out.println("buffer " + buff);
        for (int i = 0; i < buff.length(); i++) {
            StringBuffer subString = new StringBuffer(buff.substring(0, buff.length() - i));
            subString.reverse();
            String reSubString = subString.toString();
            StringBuffer dictTwo = new StringBuffer(dict);
            dictTwo.reverse();
            String reDict = dictTwo.toString();
            if (reDict.contains(reSubString)) {
                System.out.println("CONTAINS " + subString.reverse());
                return new PositiveOutput(reDict.indexOf(reSubString) + buff.length() - i - 1, buff.length() - i);
            }
        }
        return new NegativeOutput(buff.charAt(0));
    }


    public void moveLense(int size) {
        for (int i = 0; i < size; i++) {
            dictAndBuff.removeElementAt(0);
            if (inputVec.size() != 0) {
                dictAndBuff.add(inputVec.get(0));
                inputVec.removeElementAt(0);
            }
        }
    }

    public void printResults(){
        for(Output i : result){
            System.out.println(i);
        }
    }

    public List<Output> getResult(){
        return result;
    }


    public String getBinaryOutput(){
        String binaryResult = "";
        for(Output i : result){
            if(i.getFlag()){
                binaryResult+="1" + Integer.toBinaryString(i.getLetter()) + " ";
            }else{
                binaryResult+="0" + intToBinaryString(i.getOffset()) + intToBinaryString(i.getSize()) + " ";
            }
        }
        return binaryResult;
    }
    private String intToBinaryString(int a){
        String binary = "";
        if(a >= 4){
            binary += '1';
            a -= 4;
        }else binary+='0';
        if(a >= 2){
            binary+='1';
            a-=2;
        }else binary+='0';
        if(a >= 1){
            binary+='1';
            a-=1;
        }else binary+='0';
        return binary;
    }


}