package twentyFortyEight.javafx;

import javafx.scene.control.Label;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorInput;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import twentyFortyEight.config.TwentyFortyEightConfig;
import twentyFortyEight.provider.TwentyFortyEightTile;

/**
 * 2048 cell class for JavaFX.
 */
public class TwentyFortyEightCellFx extends StackPane {
  private final ImageView imageView;
  private int number;
  private final int xpos;
  private final int ypos;
  private Label label;

  /**
   * Creates a new 2048 cell.
   *
   * @param number The number
   * @param xpos The x position.
   * @param ypos The y position.
   */
  public TwentyFortyEightCellFx(int number, int xpos, int ypos) {
    super();
    this.number = number;
    this.xpos = xpos;
    this.ypos = ypos;
    imageView = new ImageView(TwentyFortyEightTile.BACKGROUND.getSprite());
    Color color = getColorForNumber(number);
    ColorInput colorInput = new
        ColorInput(0, 0, imageView.getImage().getWidth(), imageView.getImage().getHeight(), color);
    Blend blend = new Blend(BlendMode.MULTIPLY, null, colorInput);
    imageView.setEffect(blend);

    label = new Label();
    this.setLabelText(number);
    label.setFont(TwentyFortyEightConfig.FONT);
    this.getChildren().addAll(imageView, label);
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

  public int getCell() {
    return number;
  }

  /**
   * Set the number of a specific cell.
   *
   * @param number new Number
   */
  public void setCell(int number) {
    this.setLabelText(number);
    Color color = getColorForNumber(number);
    ColorInput colorInput = new
        ColorInput(0, 0, imageView.getImage().getWidth(), imageView.getImage().getHeight(), color);
    Blend blend = new Blend(BlendMode.MULTIPLY, null, colorInput);
    imageView.setEffect(blend);
  }

  private void setLabelText(int number) {
    if (number == 0) {
      label.setText(null);
    } else {
      label.setText(String.valueOf(number));
    }
  }

  public int getX() {
    return xpos;
  }

  public int getY() {
    return ypos;
  }
}
