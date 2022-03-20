import java.util.*;

public class BattleShips {
    public static int row = 5;
    public static int col = 5;
    public static int computerShips;
    public static int playerShips;
    public static int playerStrikeCount;
    public static int computerStrikeCount;
    public static int playerMissedGuess;
    public static int playerHits;
    public static int computerMissedGuess;
    public static int computerHits;
    public static String[][] Board = new String[row][col];
    public static int[][] MissedGuess = new int[row][col];

    public static void main(String[] args) {
        System.out.println();
        System.out.println("           ****Battle Ship game****");
        createOceanMap();
        deployPlayerShips();
        deployComputerShips();
        do {
            battle();
        } while ((BattleShips.playerShips != 0 && BattleShips.computerShips != 0)
                && (BattleShips.playerStrikeCount < 5 && BattleShips.computerStrikeCount < 5));
        gameOver();
    }

    public static void createOceanMap() {
        System.out.println();
        System.out.print("  ");
        for (int i = 0; i < col; i++)
            System.out.print(i);
        System.out.println();
        for (int i = 0; i < Board.length; i++) {
            for (int j = 0; j < Board[i].length; j++) {
                Board[i][j] = ".";
                if (j == 0)
                    System.out.print(i + "|" + Board[i][j]);
                else if (j == Board[i].length - 1)
                    System.out.print(Board[i][j] + "|" + i);
                else
                    System.out.print(Board[i][j]);
            }
            System.out.println();
        }
        System.out.print("  ");
        for (int i = 0; i < col; i++)
            System.out.print(i);
        System.out.println();
    }

    public static void deployPlayerShips() {

        System.out.println("Deploy Your Ships... ");
        BattleShips.playerShips = 2;
        for (int i = 1; i <= BattleShips.playerShips;) {
            Scanner input1 = new Scanner(System.in);
            System.out.println("Enter the x coordinate for your " + i + " ship");
            int x = input1.nextInt();
            System.out.println("Enter the y coordinate for your " + i + " ship");
            int y = input1.nextInt();

            if ((x >= 0 && x < row) && (y >= 0 && y < col) && (Board[x][y] == ".")) {
                Board[x][y] = "P";
                i++;
            } else if ((x >= 0 && x < row) && (y >= 0 && y < col) && (Board[x][y] == "P")) {
                System.out.println("You cannot place two ships on the same location");
            } else if ((x < 0 && x > row) && (y < 0 && y > col)) {
                System.out.println("Invalid Entry. You cannot place the ships outside the grid ...");
            }
            printOceanMap();
        }
    }

    public static void deployComputerShips() {
        System.out.println("Computer Is deploying ships");
        BattleShips.computerShips = 2;

        for (int i = 1; i <= BattleShips.computerShips;) {
            int x = (int) (Math.random() * 5);
            int y = (int) (Math.random() * 5);

            if ((x >= 0 && x < row) && (y > 0 && y < col) && (Board[x][y] == ".")) {
                Board[x][y] = "C";
                System.out.println("Computer Ship is deployed...");
                i++;
            }
        }
        printOceanMap();
    }

    public static void battle() {

        playerTurn();

        computerTurn();

        printOceanMap();

        System.out.println();

        System.out.println(
                "Player Ships : " + BattleShips.playerShips + "   Computer Ships : " + BattleShips.computerShips);
        System.out.println();
    }

    public static void playerTurn() {
        System.out.println("Your turn... ");
        int x = -1, y = -1;
        do {
            Scanner input = new Scanner(System.in);
            System.out.println("Enter your X Coordinate: ");
            x = input.nextInt();
            System.out.println("Enter your Y Coordinate: ");
            y = input.nextInt();
            if ((x >= 0 && x < row) && (y >= 0 && y < col)) {
                BattleShips.playerStrikeCount++;
                if (Board[x][y] == "C") {
                    System.out.println("Hit");
                    Board[x][y] = "*";
                    BattleShips.playerHits++;
                    --BattleShips.computerShips;
                } else if (Board[x][y] == "P") {
                    System.out.println("You hit your own ship...!");
                    Board[x][y] = "*";
                    --BattleShips.playerShips;

                } else if (Board[x][y] == ".") {
                    System.out.println("Player Missed");
                    Board[x][y] = "_";
                    BattleShips.playerMissedGuess++;
                } else if ((x < 0 || x >= row) || (y < 0 || y >= col))
                    System.out.println(" You cannot hit out side of the grid");
            }
        } while ((x < 0 || x >= row) || (y < 0 || y >= col));
    }

    public static void computerTurn() {
        System.out.println("Computer turn");
        int x = -1;
        int y = -1;
        do {
            x = (int) (Math.random() * 5);
            y = (int) (Math.random() * 5);

            if ((x >= 0 && x < row) && (y >= 0 && y < col)) {
                BattleShips.computerStrikeCount++;
                if (Board[x][y] == "P") {
                    System.out.println("You got hit by computer...");
                    Board[x][y] = "#";
                    BattleShips.computerHits++;
                    --BattleShips.playerShips;
                } else if (Board[x][y] == "C") {
                    System.out.println("Computer hit their own ship...");
                    Board[x][y] = "*";
                    --BattleShips.computerShips;

                } else if (Board[x][y] == ".") {
                    System.out.println("Computer Missed... ");
                    BattleShips.computerMissedGuess++;
                }
            }
        } while ((x < 0 || x >= row) || (y < 0 || y >= col));
    }

    public static void gameOver() {
        System.out.println();
        if (BattleShips.playerShips > 0 && BattleShips.computerShips <= 0) {
            System.out.println("Congratulations.. You have won!");
            System.out.println();
            System.out.println("Misess");
            System.out.println();
            System.out.println("Computer : " + BattleShips.computerMissedGuess);
            System.out.println("Player : " + BattleShips.playerMissedGuess);
            System.out.println();
            System.out.println("Hits");
            System.out.println();
            System.out.println("Player : " + BattleShips.computerHits);
            System.out.println("Computer : " + BattleShips.playerHits);
        } else if (BattleShips.computerShips > 0 && BattleShips.playerShips <= 0) {
            System.out.println("Sorry...You lost the battle...");
            System.out.println();
            System.out.println("Misess");
            System.out.println();
            System.out.println("Computer : " + BattleShips.computerMissedGuess);
            System.out.println("Player : " + BattleShips.playerMissedGuess);
            System.out.println();
            System.out.println("Hits");
            System.out.println();
            System.out.println("Player : " + BattleShips.computerHits);
            System.out.println("Computer : " + BattleShips.playerHits);
        }
        if (BattleShips.playerShips != 0 && BattleShips.computerShips != 0) {
            if (BattleShips.playerShips > BattleShips.computerShips) {
                System.out.println("The maximum strike limit has been exceeded.");
                System.out.println();
                System.out.println("Congratulations.. You have won!");
                System.out.println();
                System.out.println("Misess");
                System.out.println();
                System.out.println("Computer : " + BattleShips.computerMissedGuess);
                System.out.println("Player : " + BattleShips.playerMissedGuess);
                System.out.println();
                System.out.println("Hits");
                System.out.println();
                System.out.println("Player : " + BattleShips.computerHits);
                System.out.println("Computer : " + BattleShips.playerHits);
            } else if (BattleShips.playerShips == BattleShips.computerShips) {
                System.out.println("The maximum strike limit has been exceeded.");
                System.out.println();
                System.out.println("The war comes to a draw...");
                System.out.println();
                System.out.println("Misess");
                System.out.println();
                System.out.println("Computer : " + BattleShips.computerMissedGuess);
                System.out.println("Player : " + BattleShips.playerMissedGuess);
                System.out.println();
                System.out.println("Hits");
                System.out.println();
                System.out.println("Player : " + BattleShips.computerHits);
                System.out.println("Computer : " + BattleShips.playerHits);
            } else {
                System.out.println("The maximum strike limit has been exceeded.");
                System.out.println();
                System.out.println("Sorry...You lost the battle...");
                System.out.println();
                System.out.println("Misess");
                System.out.println();
                System.out.println("Computer : " + BattleShips.computerMissedGuess);
                System.out.println("Player : " + BattleShips.playerMissedGuess);
                System.out.println();
                System.out.println("Hits");
                System.out.println();
                System.out.println("Player : " + BattleShips.computerHits);
                System.out.println("Computer : " + BattleShips.playerHits);
            }
        }
    }

    public static void printOceanMap() {
        System.out.println();
        System.out.print("  ");
        for (int i = 0; i < col; i++)
            System.out.print(i);
        System.out.println();
        for (int x = 0; x < Board.length; x++) {
            System.out.print(x + "|");
            for (int y = 0; y < Board[x].length; y++) {
                System.out.print(Board[x][y]);
            }
            System.out.println("|" + x);
        }
        System.out.print("  ");
        for (int i = 0; i < col; i++)
            System.out.print(i);
        System.out.println();
    }
}