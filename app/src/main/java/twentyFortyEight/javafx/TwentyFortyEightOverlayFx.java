package twentyFortyEight.javafx;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import twentyFortyEight.config.TwentyFortyEightConfig;
import twentyFortyEight.javafx.viewmodel.TwentyFortyEightViewmodel;

public class TwentyFortyEightOverlayFx extends StackPane {
  private final Label displayText;

  public TwentyFortyEightOverlayFx(TwentyFortyEightViewmodel viewModel) {
    super();
    displayText = new Label();
    initializeComponents();
  }

  private void initializeComponents() {
    this.setBackground(new Background(new BackgroundFill(
        Color.rgb(38, 37, 38, 0.7),
        null,
        null
    )));

    displayText.setFont(TwentyFortyEightConfig.FONT);
    displayText.setTextFill(Color.WHITE);

    Label pressRlabel = new Label("Press 'r' to restart");
    pressRlabel.setFont(TwentyFortyEightConfig.FONT);
    pressRlabel.setTextFill(Color.WHITE);
    VBox textBox = new VBox(20, displayText, pressRlabel);
    textBox.setAlignment(Pos.CENTER);
    this.getChildren().add(textBox);
    this.setAlignment(Pos.CENTER);
    this.setVisible(false);
  }

  public void showOverlay(boolean gameOver) {
    switchOverlay(gameOver);
    super.setVisible(true);
  }

  public void hideOverlay() {
    super.setVisible(false);
  }

  private void switchOverlay(boolean gameOver) {
    displayText.setText(gameOver ?
        TwentyFortyEightConfig.GAME_OVER : TwentyFortyEightConfig.GAME_WON);
    displayText.setTextFill(gameOver ?
        Color.RED : Color.GREEN);
  }
}
