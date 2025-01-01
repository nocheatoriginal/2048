package twentyFortyEight.javafx;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import twentyFortyEight.config.TwentyFortyEightConfig;

/**
 * 2048 cell class for JavaFX.
 */
public class TwentyFortyEightCellFx extends StackPane {
  private final Label label;

  /**
   * Creates a new 2048 cell.
   *
   * @param number The number
   */
  public TwentyFortyEightCellFx(int number) {
    super();
    label = new Label();
    label.setMinSize(TwentyFortyEightConfig.SPRITE_SIZE, TwentyFortyEightConfig.SPRITE_SIZE);
    label.setMaxSize(TwentyFortyEightConfig.SPRITE_SIZE, TwentyFortyEightConfig.SPRITE_SIZE);
    label.setFont(TwentyFortyEightConfig.FONT);
    label.setAlignment(Pos.CENTER);
    this.updateLabel(number);
    this.getChildren().addAll(label);
  }

  private Color getColorForNumber(int number) {
    return switch (number) {
      case 0 -> Color.rgb(205, 193, 180);
      case 2 -> Color.rgb(238, 228, 218);
      case 4 -> Color.rgb(237, 224, 200);
      case 8 -> Color.rgb(242, 177, 121);
      case 16 -> Color.rgb(245, 149, 99);
      case 32 -> Color.rgb(246, 124, 95);
      case 64 -> Color.rgb(246, 94, 59);
      case 128 -> Color.rgb(237, 207, 114);
      case 256 -> Color.rgb(237, 204, 97);
      case 512 -> Color.rgb(237, 200, 80);
      case 1024 -> Color.rgb(237, 197, 63);
      case 2048 -> Color.rgb(237, 194, 46);
      case 4096 -> Color.rgb(137, 30, 34);
      case 8192 -> Color.rgb(107, 20, 28);
      case 16384 -> Color.rgb(77, 10, 22);
      case 32768 -> Color.rgb(47, 0, 16);
      default -> Color.rgb(30, 0, 16);
    };
  }

  /**
   * Set the number of a specific cell.
   *
   * @param number new Number
   */
  public void setCell(int number) {
    this.updateLabel(number);
  }

  private void updateLabel(int number) {
    if (number == 0 && !TwentyFortyEightConfig.SHOW_ZERO) {
      label.setText(null);
    } else {
      label.setText(String.valueOf(number));
    }
    label.setBackground(Background.fill(getColorForNumber(number)));
  }
}
