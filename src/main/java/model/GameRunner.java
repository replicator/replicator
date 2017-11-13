package model;
import java.util.Scanner;
import model.Entity.Coordinate;

public class GameRunner {
    public static final boolean DEBUG = false;
    public static final boolean CONSOLE_RUN = false;

    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        int[][] input = {
                {1, 0, 2, 0},
                {0, 0, 0, 2},
                {0, 2, 2, 2},
                {0, 1, 0, 0}};
        int timeLimit = 100;
        Layout result = createLayout(input, timeLimit);

        System.out.println("Layout entered: \n" + result);
        while (!result.isGameDone()) {
            result.update();
            System.out.println("Layout at time step: " + result.getTime());
            System.out.println(result);
            if (CONSOLE_RUN) {
                System.out.println("Continue: [n] to end?");
                if (console.nextLine().toLowerCase().contains("n")) {
                    System.out.println("Game forcibly ended");
                    break;
                }

            }
            System.out.println("Numb entities: " + result.getNumEntities());
            System.out.println("Entities types and numb: " + result.getNumOfEachEntity());
            System.out.println("\n\n\n");
        }
        System.out.println("Game is done");
    }

    public static Layout createLayout(int[][] input, int timeLimit) {
        Layout result = new Layout(input.length, input[0].length, timeLimit);
        for (int row = 0; row < input.length; row++) {
            for (int col = 0; col < input[row].length; col++) {
                Entity e;
                Coordinate coord = new Coordinate(row, col);
                if (input[row][col] == 1) {
                    e = new RandomEntity(coord);
                    result.insertEntity(e);
                } else if (input[row][col] == 2) {
                    e = new TurretEntity(coord);
                    result.insertEntity(e);
                }
            }
        }
        return result;
    }
}
