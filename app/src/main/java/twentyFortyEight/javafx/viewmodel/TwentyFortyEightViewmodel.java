package twentyFortyEight.javafx.viewmodel;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import twentyFortyEight.config.TwentyFortyEightConfig;
import twentyFortyEight.config.TwentyFortyEightListener;
import twentyFortyEight.javafx.TwentyFortyEightOverlayFx;
import twentyFortyEight.provider.TwentyFortyEightBoard;
import twentyFortyEight.provider.TwentyFortyEightDirection;
import twentyFortyEight.provider.TwentyFortyEightService;

/**
 * Viewmodel for the 2048 game.
 */
public class TwentyFortyEightViewmodel implements TwentyFortyEightListener {
  private final TwentyFortyEightService service;
  private final ObjectProperty<TwentyFortyEightBoard> board;
  private final ObjectProperty<String> status;
  private final ObjectProperty<TwentyFortyEightOverlayFx> overlay;

  /**
   * Creates a new 2048 viewmodel.
   *
   * @param service The 2048 service.
   */
  public TwentyFortyEightViewmodel(final TwentyFortyEightService service) {
    super();
    this.service = service;
    this.service.addListener(this);
    this.board = new SimpleObjectProperty<>(service.getBoard());
    this.status = new SimpleObjectProperty<>(service.getStatus());
    this.overlay = new SimpleObjectProperty<>(new TwentyFortyEightOverlayFx(this));
  }

  /**
   * Returns the board property.
   *
   * @return The board property.
   */
  public ObjectProperty<TwentyFortyEightBoard> boardProperty() {
    return board;
  }

  /**
   * Returns the status property.
   *
   * @return The status property.
   */
  public ObjectProperty<String> statusProperty() {
    return status;
  }

  /**
   * Returns the overlay property.
   *
   * @return The overlay property.
   */
  public ObjectProperty<TwentyFortyEightOverlayFx> overlayProperty() {
    return overlay;
  }

  /**
   * Resets the game.
   */
  @Override
  public void updateBoard(final TwentyFortyEightBoard board) {
    Platform.runLater(() -> {
      this.board.set(this.getNewBoard(board));
    });
  }

  /**
   * Updates the status.
   *
   * @param status The new status.
   */
  @Override
  public void updateStatus(final String status) {
    Platform.runLater(() -> {
      this.status.set(status);
    });
  }

  /**
   * Returns a new board.
   *
   * @param oldBoard The old board.
   * @return The new board.
   */
  private TwentyFortyEightBoard getNewBoard(TwentyFortyEightBoard oldBoard) {
    TwentyFortyEightBoard newBoard = new TwentyFortyEightBoard();
    for (int i = 0; i < TwentyFortyEightConfig.MAP_SIZE; i++) {
      for (int j = 0; j < TwentyFortyEightConfig.MAP_SIZE; j++) {
        newBoard.setCell(i, j, oldBoard.getCell(i, j));
      }
    }
    return newBoard;
  }

  /**
   * Executes a move in 'UP' direction.
   */
  public void moveUp() {
    service.move(TwentyFortyEightDirection.UP);
  }

  /**
   * Executes a move in 'DOWN' direction.
   */
  public void moveDown() {
    service.move(TwentyFortyEightDirection.DOWN);
  }

  /**
   * Executes a move in 'LEFT' direction.
   */
  public void moveLeft() {
    service.move(TwentyFortyEightDirection.LEFT);
  }

  /**
   * Executes a move in 'RIGHT' direction.
   */
  public void moveRight() {
    service.move(TwentyFortyEightDirection.RIGHT);
  }

  /**
   * Resets the game.
   */
  public void resetGame() {
    service.reset();
    overlay.get().hideOverlay();
  }

  /**
   * Show the game over overlay.
   */
  @Override
  public void gameOver(boolean lost) {
    Platform.runLater(() -> {
      overlay.get().showOverlay(lost);
    });
  }
}