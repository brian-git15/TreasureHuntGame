import java.util.Random;
import java.util.Scanner;

class GamePiece {
  char symbol;
  int location;
  boolean isVisible;

  public GamePiece(char symbol, int location) {
    this.symbol = symbol;
    this.location = location;
    this.isVisible = false;
  }
}

public class TreasureHuntGame {
  private GamePiece[][] gameBoard = new GamePiece[4][4];
  private char[][] playerBoard = new char[4][4];
  private GamePiece scallywag;
  private GamePiece escape;
  private int totalDoubloons;



  public TreasureHuntGame() {
    for (int i = 0; i < 4; i++) {
      for (int k = 0; k < 4; k++) {
        gameBoard[i][k] = null;
        playerBoard[i][k] = '_';
      }
    }
  }

  private void initializeBoard() {
    //this places the pirates randomly
    for (int k = 0; k < (k + new Random().nextInt(3)); k++) {
      placePiece('P');
    }

    //places treasures randomly
    for (int k = 0 ; k < (k + new Random().nextInt(3)); k++) {
      placePiece('T');
    }

    //places scallywags randomly
    placePiece('S');

    //places escape randomly
    placePiece('E');

    //places ambush randomly
    placePiece('A');
  }

  private void placePiece(char symbol) {
    int location;
    do {
      location = 1 + new Random().nextInt(16);
    } while (gameBoard[(location - 1) / 4][location % 4] != null); //W REPLIT

    GamePiece piece = new GamePiece(symbol, location);
    gameBoard[(location - 1) /4][(location - 1) % 4] = piece;

    if (symbol == 'S') {
      scallywag = piece;
    } else if (symbol == 'E') {
      escape = piece;
    }
  }

  private void movePiece(GamePiece piece, int newLocation) {
    int oldRow = (piece.location - 1) /4;
    int oldCol = (piece.location - 1) % 4; //W REPLIT
    int newRow = (newLocation - 1) /4;
    int newCol = (newLocation -1) %4;

    gameBoard[oldRow][oldCol] = null;
    gameBoard[newRow][newCol] = piece;
    piece.location = newLocation;

  }

  private void printBoard() {
    System.out.println("Board: ");
    for (int i = 0; i < 4; i++) {
      System.out.println("Positions [" + (4 * i + 1) + " - " + (4 * i + 4) + "]");
      for (int k = 0; k < 4; k++) {
        if (gameBoard[i][k] == null) {
          System.out.print(playerBoard[i][k] + " ");
        } else {
          if (gameBoard[i][k].isVisible) {
            System.out.print(gameBoard[i][k].symbol + " ");
          } else {
            System.out.print("_ ");
          }
        }
      }
      System.out.println();
    }
  }

  private void printBoardLegend() { //this will only display once at the start
    System.out.println("Legend:");
    System.out.println("t - empty treasures");
    System.out.println("P - pirate, YIKES");
    System.out.println("X - escape the island");
    System.out.println("S - the scallywag...YOU!");
  }
  

  public void playGame() {
    initializeBoard();
    Scanner input = new Scanner(System.in);

    //displays legend board once
    printBoardLegend();

    while (true) {
      printBoard();
      System.out.println("Total doubloons: " + totalDoubloons);
      System.out.print("Enter your move (1-16): ");
      int move = input.nextInt();
      input.nextLine();

      if (move < 1 || move > 16) {
        System.out.println("Invalid input, please enter a number between 1-16");
        continue;
      }

      GamePiece piece = gameBoard[(move - 1) / 4][(move - 1) % 4];
      if (piece != null) {
        if (piece.symbol == 'P') {
          //this handles pirate encounter
          if (totalDoubloons > 0) {
            totalDoubloons /= 2; //lose half
            System.out.println("You lost half your doubloons!");

          } else if (piece.symbol == 'T') {
            //handles treasure encounter
            int treasureValue = new Random().nextInt(400) + 5;
            totalDoubloons += treasureValue;
            System.out.println("You found " + treasureValue + " doubloons!");

          } else if (piece.symbol == 'A') {
            //handles ambush encounter
            totalDoubloons = 0; //you lose everything 
            System.out.println("You were ambushed and lost all your doubloons!");
            break; //you died
          } else if (piece.symbol == 'E') {
            //handles escape encounter
            System.out.print("You found the escape. Do you want to leave the island? (Y/N): ");
            String choice = input.nextLine().trim();
            if (choice.equalsIgnoreCase("Y")) {
              System.out.println("You escaped the island with " + totalDoubloons + "!");
              break;

            }
          }
          piece.isVisible = true;
          movePiece(scallywag, move);

        } else {
          System.out.println("Nothing found, keep exploring!");
          movePiece(scallywag, move);
        }
      }
    }
  }

    public static void main(String[]args) {
      TreasureHuntGame game = new TreasureHuntGame();
      game.playGame();

  }
}