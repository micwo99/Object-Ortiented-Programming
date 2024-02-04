package oop.ex6.main;

import oop.ex6.main.exception.GeneralException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Sjavac {
    private static final int NUM_ARGS=1;
    private static final int IO_ERROR=2;
    private static final int CODE_ERROR=1;
    private static final int SUCCESS=0;
    private static final String IO_ERROR_MSG="Happened an error when trying to open the file";
    private static final String REGEX_WHITESPACE="\\s*";
    ArrayList<String> codes_line;

    /**
     * the function check if we receive an only argument
     * @param args argument that the user enter
     * @return true if there is just one argument, false otherwise
     */
    public boolean checksArgs(String[] args) throws IOException {
        if (args.length != NUM_ARGS){
            throw new IOException();
        }
        return true;
    }

    /** the function try to open and then to read in the file
     * @param path path of the file we want to open
     * @return true if we succed to read into the file, false otherwise
     */
    public boolean openReader(String path){
        String line;
        try (FileReader fileReader =new FileReader(path);
             BufferedReader bufferedReader =new BufferedReader(fileReader);){
            codes_line= new ArrayList<>();
            while ((line = bufferedReader.readLine()) != null) {
                if(line.matches(REGEX_WHITESPACE)){
                    continue;
                }
                codes_line.add(line);
            }
                return true;
        }
        catch (IOException e) {
            System.out.println(IO_ERROR);
            System.err.println(IO_ERROR_MSG);
            return false;
        }

    }
    /**
     * the function read all the file and check if we receive a good input from the user
     * @param args input from the user  a file sJava
     */
    public void Parser(String[] args) throws IOException {
        if(checksArgs(args)){
            String path =args[0];
            openReader(path);
        }
    }

    public static void main(String[] args) throws IOException, GeneralException {
        Sjavac sJavaReader = new Sjavac();
        sJavaReader.Parser(args);
        JavaVerifier javaVerifier =new JavaVerifier(sJavaReader.codes_line);
        try {
            javaVerifier.codesVerifier();
            System.out.println(SUCCESS);
        }

        catch (GeneralException e) {
            System.out.println(CODE_ERROR);
            System.err.println(e.getMessage());
        }



    }
}
