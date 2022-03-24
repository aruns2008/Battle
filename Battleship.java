import java.util.ArrayList;
import java.util.Arrays;
import java.util.*;

public class Battleship {

    final static String TEXTRESET = "\u001B[0m";
    final static String TEXT_RED = "\u001B[31m";
    final static String TEXT_GREEN = "\u001B[32m";

    public static int row = 5;
    public static int col = 5;
    public static int computerShips;
    public static int playerShips;
    public static int playerStrikeCount = 0;
    public static int computerStrikeCount = 0;
    public static int playerMissedGuess = 0;
    public static int playerHits = 0;
    public static int computerMissedGuess = 0;
    public static int computerHits = 0;
    public static ArrayList<String>[][] PlayerBoard = new ArrayList[row][col];
    public static ArrayList<String>[][] ComputerBoard = new ArrayList[row][col];

    public static void deployPlayerShips() {

        System.out.println("Deploy Your Ships... ");
        playerShips = 5;
        for (int i = 1; i <= playerShips;) {
            Scanner input1 = new Scanner(System.in);
            System.out.println("Enter the coordinates for your " + i + " ship");

            int[] array = new int[2];
            for (int j = 0; j < array.length; j++) {
                array[j] = input1.nextInt();
            }
            int x = array[0];
            int y = array[1];
            if ((x >= 0 && x < row) && (y >= 0 && y < col) && (Battleship.PlayerBoard[x][y].contains("."))) {
                Battleship.PlayerBoard[x][y].remove(".");
                Battleship.PlayerBoard[x][y].add("P");
                i++;
            } else if ((x >= 0 && x < row) && (y >= 0 && y < col) && (PlayerBoard[x][y].contains("P"))) {
                System.out.println("You cannot place two ships on the same location");
            } else if ((x < 0 || x > row - 1) || (y < 0 || y > col - 1)) {
                System.out.println("Invalid Entry. You cannot place the ships outside the grid ...");
            }
            printOceanMapPlayer();
        }
    }

    public static void deployComputerShips() {
        System.out.println("Computer Is deploying ships");
        computerShips = 5;

        for (int i = 1; i <= computerShips;) {
            int x = (int) (Math.random() * 5);
            int y = (int) (Math.random() * 5);

            if ((x >= 0 && x < row) && (y > 0 && y < col) && (Battleship.ComputerBoard[x][y].contains("."))) {
                ComputerBoard[x][y].remove(".");
                ComputerBoard[x][y].add("C");
                i++;
            }
        }
        System.out.println("Computer Ship is deployed...");
        System.out.println();
        printOceanMapComputer();
    }

    public static void battle() {
        playerTurn();
        computerTurn();
        printOceanMapPlayer();
        printOceanMapComputer();
        System.out.println();
        System.out.println(
                "Player Ships : " + playerShips + "   Computer Ships : " + computerShips);
        System.out.println();
    }

    public static void playerTurn() {
        System.out.println("Your turn... ");
        int x = -1, y = -1;
        do {
            BattleshipGame obj = new BattleshipGame();
            int[] arr = obj.getUserGuess();
            x = arr[0];
            y = arr[1];
            if ((x >= 0 && x <= row - 1) && (y >= 0 && y <= col - 1)) {
                playerStrikeCount++;
                switch (Battleship.ComputerBoard[x][y].toString().replaceAll("(^\\[|\\]$)", "")) {
                    case "C":
                        System.out.println(TEXT_GREEN + "Computer Got Hit" + TEXTRESET);
                        Battleship.playerTurnAction("C", "*", x, y);
                        playerHits++;
                        --computerShips;
                        break;
                    case ".":
                        System.out.println(TEXT_RED + "Player Missed" + TEXTRESET);
                        Battleship.playerTurnAction(".", "_", x, y);
                        playerMissedGuess++;
                        System.out.println("p Miss" + playerMissedGuess);
                        break;
                    case "*":
                        System.out.println("Already Hit");
                        playerMissedGuess++;
                        System.out.println("p Miss" + playerMissedGuess);
                        break;
                    case "_":
                        System.out.println("Already Hit");
                        playerMissedGuess++;
                        System.out.println("p Miss" + playerMissedGuess);
                        break;
                    default:
                        break;
                }
            } else if ((x > row - 1) || (y > col - 1))
                System.out.println("Cannot hit outside of the board");
        } while ((x < 0 || x >= row) || (y < 0 || y >= col));
    }

    public static void playerTurnAction(String remove, String add, int x, int y) {
        Battleship.ComputerBoard[x][y].remove(remove);
        Battleship.ComputerBoard[x][y].add(add);
    }

    public static void computerTurnAction(String remove, String add, int x, int y) {
        Battleship.PlayerBoard[x][y].remove(remove);
        Battleship.PlayerBoard[x][y].add(add);
    }

    public static void computerTurn() {
        System.out.println("Computer turn");
        int x = -1;
        int y = -1;
        int max = 4;
        int min = 0;
        int range = max - min + 1;
        do {
            x = (int) (Math.random() * range) + min;
            y = (int) (Math.random() * range) + min;
            if ((x >= 0 && x <= row - 1) && (y >= 0 && y <= col - 1)) {
                computerStrikeCount++;
                switch (Battleship.PlayerBoard[x][y].toString().replaceAll("(^\\[|\\]$)", "")) {
                    case "P":
                        System.out.println(TEXT_GREEN + "You got hit" + TEXTRESET);
                        Battleship.computerTurnAction("P", "*", x, y);
                        computerHits++;
                        --playerShips;
                        break;
                    case ".":
                        System.out.println(TEXT_RED + "Computer Missed" + TEXTRESET);
                        Battleship.computerTurnAction(".", "_", x, y);
                        computerMissedGuess++;
                        break;
                    case "*":
                        computerMissedGuess++;
                        System.out.println("Already Hit");
                        break;
                    case "_":
                        computerMissedGuess++;
                        System.out.println("Already Hit");
                        break;
                    default:
                        break;
                }
            }
        } while ((x < 0 || x >= row) || (y < 0 || y >= col));
    }

    public static void gameOver() {
        System.out.println();
        if (Battleship.playerShips > 0 && computerShips <= 0) {
            System.out.println("Congratulations.. You have won!");
            System.out.println();
            System.out.println(TEXT_RED + "Misess" + TEXTRESET);
            System.out.println();
            System.out.println("Computer : " + TEXT_RED + computerMissedGuess + TEXTRESET);
            System.out.println("Player : " + TEXT_RED + playerMissedGuess + TEXTRESET);
            System.out.println();
            System.out.println(TEXT_GREEN + "Hits" + TEXTRESET);
            System.out.println();
            System.out.println("Player : " + TEXT_GREEN + playerHits + TEXTRESET);
            System.out.println("Computer : " + TEXT_GREEN + computerHits + TEXTRESET);
        } else if (computerShips > 0 && playerShips <= 0) {
            System.out.println("Sorry...You lost the battle...");
            System.out.println();
            System.out.println(TEXT_RED + "Misess" + TEXTRESET);
            System.out.println();
            System.out.println("Computer : " + TEXT_RED + computerMissedGuess + TEXTRESET);
            System.out.println("Player : " + TEXT_RED + playerMissedGuess + TEXTRESET);
            System.out.println();
            System.out.println(TEXT_GREEN + "Hits" + TEXTRESET);
            System.out.println();
            System.out.println("Player : " + TEXT_GREEN + playerHits + TEXTRESET);
            System.out.println("Computer : " + TEXT_GREEN + computerHits + TEXTRESET);
        }
        if (playerShips != 0 && computerShips != 0) {
            if (playerShips > computerShips) {
                System.out.println("The maximum strike limit has been exceeded.");
                System.out.println();
                System.out.println("Congratulations.. You have won!");
                System.out.println();
                System.out.println(TEXT_RED + "Misess" + TEXTRESET);
                System.out.println();
                System.out.println("Computer : " + TEXT_RED + computerMissedGuess + TEXTRESET);
                System.out.println("Player : " + TEXT_RED + playerMissedGuess + TEXTRESET);
                System.out.println();
                System.out.println(TEXT_GREEN + "Hits" + TEXTRESET);
                System.out.println();
                System.out.println("Player : " + TEXT_GREEN + playerHits + TEXTRESET);
                System.out.println("Computer : " + TEXT_GREEN + computerHits + TEXTRESET);
            } else if (playerShips == computerShips) {
                System.out.println("The maximum strike limit has been exceeded.");
                System.out.println();
                System.out.println("The war comes to a draw...");
                System.out.println();
                System.out.println(TEXT_RED + "Misess" + TEXTRESET);
                System.out.println();
                System.out.println("Computer : " + TEXT_RED + computerMissedGuess + TEXTRESET);
                System.out.println("Player : " + TEXT_RED + playerMissedGuess + TEXTRESET);
                System.out.println();
                System.out.println(TEXT_GREEN + "Hits" + TEXTRESET);
                System.out.println();
                System.out.println("Player : " + TEXT_GREEN + playerHits + TEXTRESET);
                System.out.println("Computer : " + TEXT_GREEN + computerHits + TEXTRESET);
            } else {
                System.out.println("The maximum strike limit has been exceeded.");
                System.out.println();
                System.out.println("Sorry...You lost the battle...");
                System.out.println();
                System.out.println(TEXT_RED + "Misess" + TEXTRESET);
                System.out.println();
                System.out.println("Computer : " + TEXT_RED + computerMissedGuess + TEXTRESET);
                System.out.println("Player : " + TEXT_RED + playerMissedGuess + TEXTRESET);
                System.out.println();
                System.out.println(TEXT_GREEN + "Hits" + TEXTRESET);
                System.out.println();
                System.out.println("Player : " + TEXT_GREEN + playerHits + TEXTRESET);
                System.out.println("Computer : " + TEXT_GREEN + computerHits + TEXTRESET);
            }
        }
    }

    public static void printOceanMapPlayer() {
        System.out.println("Player Board");
        System.out.println();
        System.out.print("  ");
        for (int i = 0; i < col; i++)
            System.out.print(i);
        System.out.println();
        for (int x = 0; x < PlayerBoard.length; x++) {
            System.out.print(x + "|");
            for (int y = 0; y < PlayerBoard[x].length; y++) {
                String RemoveBracket = Battleship.PlayerBoard[x][y].toString().replaceAll("(^\\[|\\]$)", "");
                System.out.print(RemoveBracket);
            }
            System.out.println("|" + x);
        }
        System.out.print("  ");
        for (int i = 0; i < col; i++)
            System.out.print(i);
        System.out.println();
    }

    public static void printOceanMapComputer() {
        System.out.println("Computer Board");
        System.out.println();
        System.out.print("  ");
        for (int i = 0; i < col; i++)
            System.out.print(i);
        System.out.println();
        for (int x = 0; x < ComputerBoard.length; x++) {
            System.out.print(x + "|");
            for (int y = 0; y < ComputerBoard[x].length; y++) {
                String RemoveBracket = Battleship.ComputerBoard[x][y].toString().replaceAll("(^\\[|\\]$)", "");
                System.out.print(RemoveBracket);
            }
            System.out.println("|" + x);
        }
        System.out.print("  ");
        for (int i = 0; i < col; i++)
            System.out.print(i);
        System.out.println();
    }

}

class BattleshipGame {

    public static void GridBuilderPlayer() {
        System.out.println();
        System.out.println("           ****Battle Ship game****");
        System.out.println();
        System.out.println("Player Board");
        System.out.print("  ");
        for (int i = 0; i < Battleship.col; i++)
            System.out.print(i);
        System.out.println();
        for (int i = 0; i < Battleship.PlayerBoard.length; i++) {
            for (int j = 0; j < Battleship.PlayerBoard[i].length; j++) {
                Battleship.PlayerBoard[i][j] = new ArrayList<String>();
                Battleship.PlayerBoard[i][j].add(".");
                String RemoveBracket = Battleship.PlayerBoard[i][j].toString().replaceAll("(^\\[|\\]$)", "");
                if (j == 0)
                    System.out.print(i + "|" + RemoveBracket);
                else if (j == Battleship.PlayerBoard[i].length - 1)
                    System.out.print(RemoveBracket + "|" + i);
                else
                    System.out.print(RemoveBracket);
            }
            System.out.println();
        }
        System.out.print("  ");
        for (int i = 0; i < Battleship.col; i++)
            System.out.print(i);
        System.out.println();

    }

    public static void GridBuilderComputer() {

        System.out.println();
        System.out.println("Computer Board");
        System.out.print("  ");
        for (int i = 0; i < Battleship.col; i++)
            System.out.print(i);
        System.out.println();
        for (int i = 0; i < Battleship.ComputerBoard.length; i++) {
            for (int j = 0; j < Battleship.ComputerBoard[i].length; j++) {
                Battleship.ComputerBoard[i][j] = new ArrayList<String>();
                Battleship.ComputerBoard[i][j].add(".");
                String RemoveBracket = Battleship.ComputerBoard[i][j].toString().replaceAll("(^\\[|\\]$)", "");
                if (j == 0)
                    System.out.print(i + "|" + RemoveBracket);
                else if (j == Battleship.ComputerBoard[i].length - 1)
                    System.out.print(RemoveBracket + "|" + i);
                else
                    System.out.print(RemoveBracket);
            }
            System.out.println();
        }
        System.out.print("  ");
        for (int i = 0; i < Battleship.col; i++)
            System.out.print(i);
        System.out.println();
    }

    public static int[] getUserGuess() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter your Coordinates for the hit: ");
        int[] array = new int[2];
        for (int j = 0; j < array.length; j++) {
            array[j] = input.nextInt();
        }
        return array;
    }
}

class Main {

    public static void main(String[] args) {

        Battleship obj = new Battleship();
        BattleshipGame obj2 = new BattleshipGame();
        obj2.GridBuilderPlayer();
        obj2.GridBuilderComputer();
        obj.deployPlayerShips();
        obj.deployComputerShips();
        do {
            obj.battle();
        } while ((Battleship.playerShips != 0 && Battleship.computerShips != 0)
                && (Battleship.playerStrikeCount < 5 && Battleship.computerStrikeCount < 5));
        obj.gameOver();
    }

}
