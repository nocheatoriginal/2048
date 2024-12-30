package twentyFortyEight.provider;

import twentyFortyEight.config.TwentyFortyEightConfig;

/**
 * 2048board class.
 */
public class TwentyFortyEightBoard {
  private final int size;
  private int[][] numbers;

  /**
   * Erzeugt ein neues 2048-Board mit Standardgröße aus der Config.
   */
  public TwentyFortyEightBoard() {
    size = TwentyFortyEightConfig.MAP_SIZE;
    numbers = new int[size][size];
  }

  /**
   * Copy-Konstruktor.
   *
   * @param other TwentyFortyEightBoard
   */
  public TwentyFortyEightBoard(TwentyFortyEightBoard other) {
    this.size = other.size;
    this.numbers = new int[size][size];
    for (int r = 0; r < size; r++) {
      System.arraycopy(other.numbers[r], 0, this.numbers[r], 0, size);
    }
  }

  public int getSize() {
    return size;
  }

  /**
   * Returns the number of a specific cell.
   *
   * @param row int
   * @param column int
   * @return the number
   */
  public int getCell(int row, int column) {
    return numbers[row][column];
  }

  /**
   * Set the number for a specific cell.
   *
   * @param row int
   * @param column int
   * @param number int
   */
  public void setCell(int row, int column, int number) {
    numbers[row][column] = number;
  }

  /**
   * Generate the default board for 2048.
   *
   * @return The default 2048 board
   */
  public static TwentyFortyEightBoard getDefault() {
    int size = TwentyFortyEightConfig.MAP_SIZE;
    TwentyFortyEightBoard board = new TwentyFortyEightBoard();
    for (int row = 0; row < size; row++) {
      for (int col = 0; col < size; col++) {
        board.setCell(row, col, 0);
      }
    }
    return board;
  }

  /**
   * Returns a specific row as array.
   *
   * @param row int
   * @return int[]
   */
  public int[] getRow(int row) {
    int[] line = new int[size];
    System.arraycopy(numbers[row], 0, line, 0, size);
    return line;
  }

  /**
   * Set the array for a specific row.
   *
   * @param row int
   * @param line int[]
   */
  public void setRow(int row, int[] line) {
    if (size >= 0) {
      System.arraycopy(line, 0, numbers[row], 0, size);
    }
  }

  /**
   * Returns the array for a specific column.
   *
   * @param col int
   * @return int[]
   */
  public int[] getColumn(int col) {
    int[] column = new int[size];
    for (int row = 0; row < size; row++) {
      column[row] = numbers[row][col];
    }
    return column;
  }

  /**
   * Sets the array for a specific column.
   *
   * @param col int
   * @param column int[]
   */
  public void setColumn(int col, int[] column) {
    for (int row = 0; row < size; row++) {
      numbers[row][col] = column[row];
    }
  }

  /**
   * Tells if the board has empty fields.
   *
   * @return boolean
   */
  public boolean hasEmptyField() {
    for (int r = 0; r < size; r++) {
      for (int c = 0; c < size; c++) {
        if (numbers[r][c] == 0) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Returns the value of a specific coordinate.
   *
   * @param row int
   * @param column int
   * @return int
   */
  public int getValue(int row, int column) {
    return getCell(row, column);
  }

  /**
   * Sets the value for a specific coordinate.
   *
   * @param row int
   * @param column int
   * @param number int
   */
  public void setValue(int row, int column, int number) {
    setCell(row, column, number);
  }
}
