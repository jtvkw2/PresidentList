package cpts132.files.PresidentList.PresidentsList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * PresidentList class takes files and creates and sorts lists and saves them to txt files
 */
public class PresidentsList{
    ArrayList<String> presList;

    /**
     * Constructor makes the main ArrayList to be sorted from
     */
    public PresidentsList(){
        presList = new ArrayList<>();
    }

    /**
     * Adds different files line to constructor arrayList and then sorts and writes to new files
     * @param files - args for different filenames
     */
    public void run(String[] files){
        if(files.length == 0){
            System.out.println("No files added");
            return;
        }
        try {
            for (String file : files) {
                if(file == null)
                    file = "presidents";
                File myObj = new File(file + ".txt");
                Scanner myReader = new Scanner(myObj);
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine().trim();
                    data = data.replaceAll("\t", " ");
                    presList.add(data);
                }
                myReader.close();
            }
            ArrayList<String> lastSorted = new ArrayList<>(presList);
            Collections.sort(lastSorted);

            ArrayList<String> officeSorted = new ArrayList<>(presList);
            SelectSort(officeSorted);


            FileWriter sameOrder = new FileWriter("same_order.txt");
            FileWriter lastName = new FileWriter("last_name.txt");
            FileWriter officeOrder = new FileWriter("inauguration.txt");

            for(int i = 0; i < presList.size(); i++){
                sameOrder.write(presList.get(i)+"\n");
                lastName.write(lastSorted.get(i)+"\n");
                officeOrder.write(officeSorted.get(i)+"\n");
            }
            sameOrder.close();
            lastName.close();
            officeOrder.close();
            System.out.println("Successfully wrote to the file.");
        }catch (FileNotFoundException e) {
            System.out.println("An error occurred. File Not Found;");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("An error occurred. IOException.");
            e.printStackTrace();
        }

    }

    /**
     * Takes an array and sorts it based on the dates listed in array
     * @param arr - input array to sort
     */
    public void SelectSort(ArrayList<String> arr) {
        for (int i = 0; i < arr.size(); i++) {
            int pos = i;
            for (int j = i; j < arr.size(); j++) {
                if (getDate(arr, j) < getDate(arr, pos))
                    pos = j;
            }
            String min = arr.get(pos);
            arr.set(pos, arr.get(i));
            arr.set(i, min);
        }
    }

    /**
     * Takes an array and integer and finds the date field and returns date
     * @param inputArray - Array to find the date in
     * @param i - index of line to parse
     * @return the date in int form
     */
    public int getDate(ArrayList<String> inputArray, int i){
        String[] x = inputArray.get(i).split(" ");
        for (String s : x) {
            boolean flag = Character.isDigit(s.charAt(0));
            if (flag) {
                String lastFourDigits = s.substring(s.length() - 4);
                return Integer.parseInt(lastFourDigits);
            }
        }
        return 0;
    }

    /**
     * Main method creates new instance and runs president.txt if none specified
     * @param args - used for input files
     */
    public static void main(String[] args) {
        PresidentsList lists = new PresidentsList();
        if (args.length == 0) {
            String[] files = new String[1];
            files[0] = "presidents";
            lists.run(files);
        } else{
            lists.run(args);
        }
    }
}