package twentyFortyEight.javafx;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import twentyFortyEight.config.TwentyFortyEightConfig;
import twentyFortyEight.javafx.viewmodel.TwentyFortyEightViewmodel;
import twentyFortyEight.provider.TwentyFortyEightBoard;

/**
 * The 2048 board.
 */
public class TwentyFortyEightBoardFx extends Pane implements ChangeListener<TwentyFortyEightBoard> {
  private TwentyFortyEightBoard internalBoard;
  private TwentyFortyEightCellFx[][] cells;
  private TwentyFortyEightViewmodel viewModel;
  private HBox topbar;
  private Label status;
  private StackPane gameOverOverlay;

  /**
   * The visual representation of the 2048 board.
   *
   * @param viewModel TwentyFortyEightViewmodel
   */
  public TwentyFortyEightBoardFx(TwentyFortyEightViewmodel viewModel) {
    super();
    this.viewModel = viewModel;
    viewModel.boardProperty().addListener(this);
    internalBoard = viewModel.boardProperty().get();

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
        cells[row][col] = new TwentyFortyEightCellFx(value, row, col);
        grid.add(cells[row][col], col, row);
      }
    }

    VBox screen = new VBox();
    screen.getChildren().addAll(topbar, grid);
    screen.setAlignment(Pos.CENTER);

    gameOverOverlay = createGameOverOverlay();
    gameOverOverlay.setVisible(false);
    rootStack.getChildren().addAll(screen, gameOverOverlay);
    this.getChildren().add(rootStack);

    this.setOnKeyPressed(event -> {
      switch (event.getCode()) {
        case UP, W    -> viewModel.moveUp();
        case DOWN, S  -> viewModel.moveDown();
        case LEFT, A  -> viewModel.moveLeft();
        case RIGHT, D -> viewModel.moveRight();
        case R -> {
          viewModel.resetGame();
          gameOverOverlay.setVisible(false);
        }
        default -> { /* andere Tasten ignorieren */ }
      }
    });
  }

  /**
   * Creates an overlay with "Game Over" Text.
   */
  private StackPane createGameOverOverlay() {
    StackPane overlay = new StackPane();
    overlay.setBackground(new Background(new BackgroundFill(
        Color.rgb(38, 37, 38, 0.7),
        null,
        null
    )));

    Label gameOverLabel = new Label("Game Over");
    gameOverLabel.setFont(TwentyFortyEightConfig.FONT);
    gameOverLabel.setTextFill(Color.RED);

    Label pressRlabel = new Label("Press 'r' to restart");
    pressRlabel.setFont(TwentyFortyEightConfig.FONT);
    pressRlabel.setTextFill(Color.WHITE);
    VBox textBox = new VBox(20, gameOverLabel, pressRlabel);
    textBox.setAlignment(Pos.CENTER);
    overlay.getChildren().add(textBox);
    overlay.setAlignment(Pos.CENTER);

    return overlay;
  }

  /**
   * Initialize the top bar (Status).
   */
  private void initTopbar() {
    status = new Label();
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

    if (viewModel.statusProperty().get().contains("Game Over")) {
      gameOverOverlay.setVisible(true);
    }
  }

  private void updateCell(int number, int row, int column) {
    cells[row][column].setCell(number);
  }
}
