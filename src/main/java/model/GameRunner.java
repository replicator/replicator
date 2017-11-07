package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class GameRunner {

    public static void main(String[] args) {
        boolean done = false;
        Scanner console = new Scanner(System.in);
//        while (!done) {
        int[][] input = {
                {1, 0, 0, 0},
                {1, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}};
        int timeLimit = 100;
        Layout result = createLayout(input, timeLimit);

        System.out.println("Layout entered: \n" + result);
        int time = 0;
        boolean loopContinue = true;
        while (loopContinue) {
            result.update();
            System.out.println("Layout at time step: " + time++ + result);
            if (result.isGameDone()) {
                break;
            }
            System.out.println("Continue: [y/n]?");
//            loopContinue = console.nextLine().toLowerCase().contains("y");
        }
        System.out.println("Game is done");
//        }
    }

    public static Layout createLayout(int[][] input, int timeLimit) {
        Layout result = new Layout(input.length, input[0].length, timeLimit);
        for (int row = 0; row < input.length; row++) {
            for (int col = 0; col < input[row].length; col++) {
                Entity temp = null;
                if (input[row][col] == 1) {
                    temp = new RandomEntity(row, col);
                    result.insertEntity(temp, row, col);
                }
//                result.insertEntity(temp, row, col);
            }
        }
        return result;
    }
}
