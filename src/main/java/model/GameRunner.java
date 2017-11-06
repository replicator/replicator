package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class GameRunner {

    public static void main(String[] args) {
        boolean done = false;
        Scanner term = new Scanner(System.in);
//        while (!done) {
        int[][] input = {
                {1, 2, 3, 4},
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {5, 6, 7, 8}};
        Layout result = createLayout(input);

        System.out.println("Layout entered: \n" + result);
//        }
    }

    public static Layout createLayout(int[][] input) {
        Layout result = new Layout(input.length, input[0].length);
        for (int row = 0; row < input.length; row++) {
            for (int col = 0; col < input[row].length; col++) {
                Entity temp = null;
                result.insertEntity(null, row, col);
            }
        }
        return result;
    }
}
