package twentyFortyEight.javafx;

import javafx.scene.layout.Pane;
import twentyFortyEight.javafx.viewmodel.TwentyFortyEightViewmodel;
import twentyFortyEight.provider.TwentyFortyEightService;

/**
 * 2048 window class for JavaFX.
 */
public class TwentyFortyEightWindowFx extends Pane {
  private TwentyFortyEightService service;

  /**
   * Creates a new 2048 window.
   */
  public TwentyFortyEightWindowFx() {
    super();
    service = new TwentyFortyEightService();
    TwentyFortyEightViewmodel viewModel = new TwentyFortyEightViewmodel(service);
    TwentyFortyEightBoardFx board = new TwentyFortyEightBoardFx(viewModel);
    this.getChildren().add(board);
  }

  public TwentyFortyEightService getService() {
    return service;
  }
}
