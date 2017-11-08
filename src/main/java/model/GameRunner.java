package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class GameRunner {
    public static final boolean DEBUG = false;
    public static final boolean CONSOLE_RUN = true;

    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        int[][] input = {
                {0, 0, 2, 0},
                {0, 0, 0, 2},
                {0, 2, 2, 2},
                {0, 0, 0, 0}};
        int timeLimit = 100;
        Layout result = createLayout(input, timeLimit);

        System.out.println("Layout entered: \n" + result);
        int time = 0;
        boolean loopContinue = true;
        while (!result.isGameDone()) {
            result.update();
            System.out.println("Layout at time step: " + time++);
            System.out.println(result);
            if (CONSOLE_RUN) {
                System.out.println("Continue: [n] to end?");
                if (console.nextLine().toLowerCase().contains("n")) {
                    System.out.println("Game forcibly ended");
                    break;
                }

            }
//            System.out.println("Continue: [y/n]?");
            System.out.println("Numb entities: " + result.getNumEntities());
            System.out.println("Entities types and numb: " + result.getNumOfEachEntity());
            System.out.println("\n\n\n");
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
                } else if (input[row][col] == 2) {
                    temp = new TurretEntity(row, col);
                    result.insertEntity(temp, row, col);
                }
//                result.insertEntity(temp, row, col);
            }
        }
        return result;
    }
}
