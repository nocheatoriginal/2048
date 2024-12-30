package twentyFortyEight.config;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Configuration class for 2048.
 */
public class TwentyFortyEightConfig {
  private static final double multiplier = 1;
  public static final int SPRITE_SIZE = (int) (160 / multiplier); //176
  public static final int MAP_SIZE = (int) (4 * multiplier);
  public static final Color BACKGROUND_COLOR = Color.web("#8f8277");
  public static final Color STATUS_COLOR = Color.web("#fcfcfc");
  public static final Font FONT = Font.loadFont(TwentyFortyEightConfig.class
      .getResourceAsStream("/fonts/imprigma-regular.ttf"), 34);
  public static OperatingMode MODE = OperatingMode.PRODUCTION;
}