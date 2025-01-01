package twentyFortyEight.javafx;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import twentyFortyEight.config.TwentyFortyEightConfig;
import twentyFortyEight.javafx.viewmodel.TwentyFortyEightViewmodel;
import twentyFortyEight.provider.TwentyFortyEightBoard;

/**
 * The 2048 board.
 */
public class TwentyFortyEightBoardFx extends Pane implements ChangeListener<TwentyFortyEightBoard> {
  private final TwentyFortyEightCellFx[][] cells;
  private final TwentyFortyEightViewmodel viewModel;
  private final HBox topbar;
  /**
   * The visual representation of the 2048 board.
   *
   * @param viewModel TwentyFortyEightViewmodel
   */
  public TwentyFortyEightBoardFx(TwentyFortyEightViewmodel viewModel) {
    super();
    this.viewModel = viewModel;
    viewModel.boardProperty().addListener(this);
    TwentyFortyEightBoard internalBoard = viewModel.boardProperty().get();

    cells = new
        TwentyFortyEightCellFx[TwentyFortyEightConfig.MAP_SIZE][TwentyFortyEightConfig.MAP_SIZE];
    topbar = new HBox();
    initTopbar();

    StackPane rootStack = new StackPane();
    rootStack.setBackground(Background.fill(TwentyFortyEightConfig.BACKGROUND_COLOR));

    GridPane grid = new GridPane();
    int gap = (int)(10 / TwentyFortyEightConfig.getMultiplier());

    grid.setHgap(gap);
    grid.setVgap(gap);
    grid.setAlignment(Pos.CENTER);

    this.setFocusTraversable(true);
    this.requestFocus();

    for (int row = 0; row < TwentyFortyEightConfig.MAP_SIZE; row++) {
      for (int col = 0; col < TwentyFortyEightConfig.MAP_SIZE; col++) {
        int value = internalBoard.getCell(row, col);
        cells[row][col] = new TwentyFortyEightCellFx(value);
        grid.add(cells[row][col], col, row);
      }
    }

    VBox screen = new VBox();
    screen.getChildren().addAll(topbar, grid);
    screen.setAlignment(Pos.CENTER);

    rootStack.getChildren().addAll(screen, viewModel.overlayProperty().get());
    this.getChildren().add(rootStack);

    this.setOnKeyPressed(event -> {
      switch (event.getCode()) {
        case UP, W    -> viewModel.moveUp();
        case DOWN, S  -> viewModel.moveDown();
        case LEFT, A  -> viewModel.moveLeft();
        case RIGHT, D -> viewModel.moveRight();
        case R -> {
          viewModel.resetGame();
        }
        default -> { /* do nothing ... */ }
      }
    });
  }

  /**
   * Initialize the top bar (Status).
   */
  private void initTopbar() {
    Label status = new Label();
    status.textProperty().bindBidirectional(viewModel.statusProperty());
    status.setFont(TwentyFortyEightConfig.FONT);
    status.setTextFill(TwentyFortyEightConfig.STATUS_COLOR);
    topbar.getChildren().add(status);
    topbar.setAlignment(Pos.CENTER);
  }

  /**
   * This method os called when the board property changes.
   */
  @Override
  public void changed(final ObservableValue<? extends TwentyFortyEightBoard> observable,
                      final TwentyFortyEightBoard oldValue, final TwentyFortyEightBoard newValue) {
    if (Platform.isFxApplicationThread()) {
      updateBoard(newValue);
    } else {
      Platform.runLater(() -> updateBoard(newValue));
    }
  }

  /**
   * Update the board.
   */
  private void updateBoard(final TwentyFortyEightBoard newBoard) {
    for (int column = 0; column < newBoard.getSize(); column++) {
      for (int row = 0; row < newBoard.getSize(); row++) {
        int number = newBoard.getCell(row, column);
        updateCell(number, row, column);
      }
    }
  }

  private void updateCell(int number, int row, int column) {
    cells[row][column].setCell(number);
  }
}
