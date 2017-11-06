package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class GameRunner {

    public static void main(String[] args) {
        boolean done = false;
        Scanner term = new Scanner(System.in);
        while (!done) {
            System.out.println("Enter layout: ");
            String layout = "";
            while (term.hasNextLine()) {
                String nextLine = term.nextLine();
                if (nextLine.contains("DONE")) {
                    break;
                }
                layout += term.nextLine();
            }
            System.out.println("Layout entered: " + layout);


        }
    }

    public Layout createLayout(String input) {

        Scanner scanLine = new Scanner(input);
        List<String[]> inputList = new ArrayList<String[]>();

        while (scanLine.hasNextLine()) {
            String nextLine = scanLine.nextLine();
            String[] stuff = nextLine.split(",");
            inputList.add(stuff);
        }
        Layout result = new Layout(inputList.size(), inputList.get(0).length);
        for (int row = 0; row < inputList.size(); row++) {
            for (int col = 0; col < inputList.get(row).length; col++) {
                result.insertEntity(null, row, col);
            }
        }
        return result;
    }
}
