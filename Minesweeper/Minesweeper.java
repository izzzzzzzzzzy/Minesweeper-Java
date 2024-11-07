package Minesweeper;

import java.util.Scanner;

public class Minesweeper {
    public static void main(String[] args) {
        // Constant for preset level options
        final int[][] LEVELS = { { 8, 8, 8 }, { 12, 10, 10 }, { 20, 16, 50 } };

        // Creating all needed variables
        int level;
        int[] levelPresets;

        Scanner in = new Scanner(System.in);

        Grid board;

        int status = 0;

        String action;
        int row, col;

        String choice;

        boolean replay = false;

        do {
            // Reseting values of anything that needs it
            level = -1;
            replay = false;
            status = 0;

            System.out.println("\nSelect your difficulty:\n1: Beginner\n2: Intermediate\n3: Expert");

            // Loops until the user selects a valid level to play at
            do {
                try {
                    level = Integer.parseInt(in.nextLine()) - 1;
                } catch (Exception e) {
                    System.out.print("Please enter 1, 2 or 3: ");
                }
            } while (level < 0 || level > 2);

            levelPresets = LEVELS[level];

            // Creates the board using the specified level preset
            board = new Grid(levelPresets[0], levelPresets[1], levelPresets[2]);

            // Loops until the status changes, signifying the end of the game
            do {
                System.out.println();
                System.out.println(board);

                // Get input for the next move
                System.out.print("What Next?\nOptions: (U)ncover r c, (F)lag r c, (Q)uit\n");
                action = in.next().toUpperCase();
                try {
                    switch (action) {
                        case "U":
                            row = in.nextInt();
                            col = in.nextInt();

                            status = board.uncoverSquare(row, col); // If uncoverSquare returns 0, status stays the
                                                                    // same, otherwise it changes and the game ends
                            break;

                        case "F":
                            row = in.nextInt();
                            col = in.nextInt();

                            board.flagSquare(row, col);
                            break;

                        case "Q":
                            status = 3;
                            break;

                        default:
                            System.out.println("Invalid Input!"); // Anything starting with a character besides U, F,
                                                                  // and Q is invalid
                    }
                } catch (Exception e) {
                    System.out.println("Invalid Input!"); // Catches both errors in input and errors stemming from the
                                                          // game such as RevealFlaggedSquareExceptions
                }

                in.nextLine(); // Clears the buffer for the next move
            } while (status == 0); // Loop ends when the status is changes

            board.exposeMines();

            System.out.println();
            System.out.println(board);

            // Prints a response based on the status
            if (status == 1)
                System.out.println("You Win!");
            else
                System.out.println("Better luck next time!");

            // Prompts the user for whether or not they want to try again
            do {
                System.out.print("\nWould you like to play again? (Y/N) ");
                choice = in.nextLine().toUpperCase();
            } while (!choice.equals("Y") && !choice.equals("N"));

            if (choice.equals("Y"))
                replay = true;

        } while (replay == true);

        in.close(); // Closing Scanner once everything is done
    }
}
