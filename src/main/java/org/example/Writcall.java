package org.example;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class Writcall {
    public void writeCall(int call) {
        int post = call;
        try {
            FileWriter myWriter = new FileWriter("call.txt");
            myWriter.write(Integer.toString(post));
            myWriter.close();
            //System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            //System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public void writeDate(String call) {
        String post = call;
        try {
            FileWriter myWriter = new FileWriter("date.txt");
            myWriter.write(post);
            myWriter.close();
            //System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            //System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public int readCall() {
        try {
            File myObj = new File("call.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                int data = Integer.parseInt(myReader.nextLine());
                //System.out.println(data);
                return data;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            // System.out.println("An error occurred.");
            System.exit(0);
        }
        return 0;
    }
    public String readDate() {
        try {
            File myObj = new File("date.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                //System.out.println(data);
                return data;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            // System.out.println("An error occurred.");
            System.exit(0);
        }
        return " ";
    }
}
