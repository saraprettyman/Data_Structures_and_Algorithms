import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.Color;

public class Assignment6Driver {
    public static void main(String[] args) {
//        testGame();
        playGame("moves1.txt");
         System.out.println();
         playGame("moves2.txt");
    }

    private static void playGame(String filename) {
        File file = new File(filename);
        HexGame game = new HexGame(11);
        boolean isBlueTurn = false;
        boolean play = false;
        try (Scanner input = new Scanner(file)) {
            // TODO: Write some good stuff here
            while (input.hasNextInt()) {
                int move = input.nextInt();
                if (isBlueTurn) {
                    play = game.playRed(move, true);
                    if (play){
                        System.out.println("Red wins with a move at position " + move + "!!");
                        printGrid(game);
                        return;
                    }
                }else {
                    play = game.playBlue(move, true);
                    if (play){
                        System.out.println("Blue wins with a move at position " + move + "!!");
                        printGrid(game);
                        return;
                    }
                }
                isBlueTurn = !isBlueTurn;
            }
        } catch (java.io.IOException ex) {
            System.out.println("An error occurred trying to read the moves file: " + ex);
        }
        printGrid(game);

    }

    // TODO: You can use this to compare with the output show in the assignment
    // while working on your code
    private static void testGame() {
        HexGame game = new HexGame(11);
        System.out.println("--- red ---");
        game.playRed(1, true);
        game.playRed(11, true);
        game.playRed(122 - 12, true);
        game.playRed(122 - 11, true);
        game.playRed(122 - 10, true);
        game.playRed(121, true);
        game.playRed(61, true);

         System.out.println("--- blue ---");
         game.playBlue(1, true);
         game.playBlue(2, true);
         game.playBlue(11, true);
         game.playBlue(12, true);
         game.playBlue(121, true);
         game.playBlue(122 - 11, true);
         game.playBlue(62, true);

        printGrid(game);
    }

    // TODO: Complete this method
    private static void printGrid(HexGame game) {

        for (int i = 1; i <= game.dim; i++) {
            String ANSI_RESET = "\u001B[0m";
            String ANSI_RED = "\u001B[31m";
            String ANSI_BLUE = "\u001B[34m";
            // print leading space for rows
            System.out.print(" ".repeat(i - 1));
            for (int j = 1; j <= game.dim; j++) {
                int position = (i - 1) * game.dim + j;
                if (game.color[position] == Color.RED) {
                    System.out.printf(ANSI_RED + "R " + ANSI_RESET);
                } else if (game.color[position] == Color.BLUE) {
                    System.out.printf(ANSI_BLUE + "B " + ANSI_RESET);
                } else {
                    System.out.print("0 ");
                }
            }
            System.out.println();
        }
    }
}
