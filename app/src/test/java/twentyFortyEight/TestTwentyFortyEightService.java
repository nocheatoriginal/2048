package twentyFortyEight;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twentyFortyEight.provider.TwentyFortyEightBoard;
import twentyFortyEight.provider.TwentyFortyEightService;
import twentyFortyEight.provider.TwentyFortyEightDirection;

import static org.junit.jupiter.api.Assertions.*;

class TestTwentyFortyEightService {
  private TwentyFortyEightService service;

  @BeforeEach
  void setup() {
    service = new TwentyFortyEightService();
  }

  private int countNonEmptyCells(TwentyFortyEightBoard board) {
    int count = 0;
    for (int r = 0; r < board.getSize(); r++) {
      for (int c = 0; c < board.getSize(); c++) {
        if (board.getValue(r, c) != 0) {
          count++;
        }
      }
    }
    return count;
  }

  @Test
  void testResetCreatesOneRandomTile() {
    TwentyFortyEightBoard board = service.getBoard();
    int count = countNonEmptyCells(board);

    assertEquals(1, count, "Nach reset() sollte genau 1 Feld nicht leer sein!");
  }

  @Test
  void testMoveLeftMergesTiles() {
    TwentyFortyEightBoard board = service.getBoard();
    board.setValue(0, 0, 2);
    board.setValue(0, 1, 2);
    board.setValue(0, 2, 4);
    board.setValue(0, 3, 0);

    service.move(TwentyFortyEightDirection.LEFT);

    assertEquals(4, board.getValue(0, 0), "Erwartet 4 an Position (0,0)");
    assertEquals(4, board.getValue(0, 1), "Erwartet 4 an Position (0,1)");
    assertEquals(0, board.getValue(0, 2), "Erwartet 0 an Position (0,2)");
    assertEquals(0, board.getValue(0, 3), "Erwartet 0 an Position (0,3)");
  }

  @Test
  void testGameWonCondition() {
    TwentyFortyEightBoard board = service.getBoard();
    board.setValue(1, 1, 2048);
    service.move(TwentyFortyEightDirection.UP);

    String status = service.getStatus();
    assertTrue(status.contains("Gewonnen"), "Nach Erreichen von 2048 sollte der Status 'Gewonnen!' sein.");
  }

  @Test
  void testGameOverCondition() {
    TwentyFortyEightBoard board = service.getBoard();
    int[][] pattern = {
        {2, 4, 2, 4},
        {4, 2, 4, 2},
        {2, 4, 2, 4},
        {4, 2, 4, 2}
    };
    for (int r = 0; r < 4; r++) {
      for (int c = 0; c < 4; c++) {
        board.setValue(r, c, pattern[r][c]);
      }
    }
    service.move(TwentyFortyEightDirection.DOWN);
    String status = service.getStatus();
    assertTrue(status.contains("Game Over"), "Sollte 'Game Over!' im Status sein.");
  }

  @Test
  void testScoreIncrementsAfterMerge() {
    service.reset();
    TwentyFortyEightBoard board = service.getBoard();
    board.setValue(2, 0, 2);
    board.setValue(2, 1, 2);
    service.move(TwentyFortyEightDirection.LEFT);
    String status = service.getStatus();
    assertTrue(status.contains("Score: 4"), "Score sollte nach einem Merge (2+2=4) auf 4 stehen.");
  }
}
