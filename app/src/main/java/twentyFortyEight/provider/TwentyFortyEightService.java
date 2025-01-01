package twentyFortyEight.provider;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import twentyFortyEight.config.GameMode;
import twentyFortyEight.config.TwentyFortyEightConfig;
import twentyFortyEight.config.TwentyFortyEightListener;

/**
 * Service for the 2048 game.
 */
public class TwentyFortyEightService {
  private final List<TwentyFortyEightListener> listeners;
  private TwentyFortyEightBoard board;
  private TwentyFortyEightBoard backup;
  private boolean gameOver;
  private boolean gameWon;
  private String status;
  private int score;

  /**
   * Constructor for the 2048 service.
   */
  public TwentyFortyEightService() {
    listeners = new ArrayList<>();
    this.reset();
  }

  /**
   * Resets the game.
   */
  public void reset() {
    board = TwentyFortyEightBoard.getDefault();
    backup = new TwentyFortyEightBoard(board);
    gameOver = false;
    gameWon = false;
    score = 0;

    status = TwentyFortyEightConfig.MODE == GameMode.DEFAULT
        ? TwentyFortyEightConfig.GOAL + " - Score: " + score
        : GameMode.ENDLESS + " - Score: " + score;

    notifyListeners(listener -> listener.updateStatus(status));
    notifyListeners(listener -> listener.updateBoard(board));
    addRandomTile();
  }

  /**
   * Adds a Listener.
   *
   * @param listener TwentyFortyEightListener
   */
  public void addListener(TwentyFortyEightListener listener) {
    if (!listeners.contains(listener)) {
      listeners.add(listener);
    }
  }

  private void notifyListeners(Consumer<TwentyFortyEightListener> consumer) {
    try {
      for (TwentyFortyEightListener listener : listeners) {
        consumer.accept(listener);
      }
    } catch (ConcurrentModificationException e) {
      String err = String.format("Error while notifying listeners: %s", e.getMessage());
      System.err.println(err);
    }
  }

  public TwentyFortyEightBoard getBoard() {
    return board;
  }

  public String getStatus() {
    return status;
  }

  /**
   * Move the board to a specific direction.
   *
   * @param direction TwentyFortyEightDirection
   */
  public void move(TwentyFortyEightDirection direction) {
    if (gameOver || gameWon) {
      return;
    }

    backup = new TwentyFortyEightBoard(board);

    switch (direction) {
      case UP -> moveUp();
      case RIGHT -> moveRight();
      case DOWN -> moveDown();
      case LEFT -> moveLeft();
      default -> throw new IllegalArgumentException("Invalid direction ...");
    }

    if (!board.equals(backup)) {
      addRandomTile();
    }

    checkGameWon();
    checkGameOver();

    if (!gameOver && !gameWon) {
      status = TwentyFortyEightConfig.MODE == GameMode.DEFAULT
          ? TwentyFortyEightConfig.GOAL + " - Score: " + score
          : GameMode.ENDLESS + " - Score: " + score;
    }

    notifyListeners(listener -> listener.updateStatus(status));
    notifyListeners(listener -> listener.updateBoard(board));
  }

  private void moveUp() {
    int size = board.getSize();
    for (int col = 0; col < size; col++) {
      int[] column = board.getColumn(col);
      column = compress(column);
      column = compress(merge(column));
      board.setColumn(col, column);
    }
  }

  private void moveDown() {
    int size = board.getSize();
    for (int col = 0; col < size; col++) {
      int[] column = board.getColumn(col);
      reverseArray(column);
      column = compress(column);
      column = compress(merge(column));
      reverseArray(column);
      board.setColumn(col, column);
    }
  }

  private void moveLeft() {
    int size = board.getSize();
    for (int row = 0; row < size; row++) {
      int[] line = board.getRow(row);
      line = compress(line);
      line = compress(merge(line));
      board.setRow(row, line);
    }
  }

  private void moveRight() {
    int size = board.getSize();
    for (int row = 0; row < size; row++) {
      int[] line = board.getRow(row);
      reverseArray(line);
      line = compress(line);
      line = compress(merge(line));
      reverseArray(line);
      board.setRow(row, line);
    }
  }

  private int[] compress(int[] arr) {
    int[] result = new int[arr.length];
    int idx = 0;
    for (int value : arr) {
      if (value != 0) {
        result[idx++] = value;
      }
    }
    return result;
  }

  private int[] merge(int[] arr) {
    for (int i = 0; i < arr.length - 1; i++) {
      if (arr[i] != 0 && arr[i] == arr[i + 1]) {
        int mergedValue = arr[i] * 2;
        arr[i] = mergedValue;
        arr[i + 1] = 0;
        score += mergedValue;
      }
    }
    return arr;
  }

  private void reverseArray(int[] arr) {
    for (int i = 0; i < arr.length / 2; i++) {
      int tmp = arr[i];
      arr[i] = arr[arr.length - 1 - i];
      arr[arr.length - 1 - i] = tmp;
    }
  }

  private void addRandomTile() {
    List<int[]> emptyFields = new ArrayList<>();
    int size = board.getSize();
    for (int row = 0; row < size; row++) {
      for (int col = 0; col < size; col++) {
        if (board.getValue(row, col) == 0) {
          emptyFields.add(new int[] { row, col });
        }
      }
    }
    if (emptyFields.isEmpty()) {
      return;
    }
    Random random = new Random();
    int[] chosen = emptyFields.get(random.nextInt(emptyFields.size()));
    board.setValue(chosen[0], chosen[1], (random.nextInt(10) < 9) ? 2 : 4);
  }

  private void checkGameWon() {
    if (gameWon || TwentyFortyEightConfig.MODE != GameMode.DEFAULT) {
      return;
    }

    int size = board.getSize();
    for (int row = 0; row < size; row++) {
      for (int col = 0; col < size; col++) {
        if (board.getValue(row, col) == TwentyFortyEightConfig.GOAL) {
          gameWon = true;
          status = TwentyFortyEightConfig.GAME_WON + " Score: " + score;
          notifyListeners(listener -> listener.gameOver(false));
          return;
        }
      }
    }
  }

  private void checkGameOver() {
    if (board.hasEmptyField()) {
      return;
    }

    if (canMergeAnyCell()) {
      return;
    }

    gameOver = true;
    status = TwentyFortyEightConfig.GAME_OVER + " Score: " + score;
    notifyListeners(listener -> listener.gameOver(true));
  }

  private boolean canMergeAnyCell() {
    int size = board.getSize();
    for (int row = 0; row < size; row++) {
      for (int col = 0; col < size; col++) {
        int value = board.getValue(row, col);
        if (col + 1 < size && value == board.getValue(row, col + 1)) {
          return true;
        }
        if (row + 1 < size && value == board.getValue(row + 1, col)) {
          return true;
        }
      }
    }
    return false;
  }
}
