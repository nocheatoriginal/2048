package twentyFortyEight.config;

import twentyFortyEight.provider.TwentyFortyEightBoard;

/**
 * Listener interface for 2048.
 */
public interface TwentyFortyEightListener {
  void updateBoard(TwentyFortyEightBoard board);

  void updateStatus(String status);

  void gameOver(boolean lost);
}
