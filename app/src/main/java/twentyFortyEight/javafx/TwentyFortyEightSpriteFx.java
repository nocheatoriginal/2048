package twentyFortyEight.javafx;

import java.io.InputStream;
import javafx.scene.image.Image;
import twentyFortyEight.config.TwentyFortyEightConfig;

/**
 * 2048 sprite class for JavaFX.
 */
public class TwentyFortyEightSpriteFx extends Image {
  /**
   * Creates the background Sprite.
   *
   * @param spriteName InputStream (path to the file)
   */
  public TwentyFortyEightSpriteFx(InputStream spriteName) {
    super(spriteName, TwentyFortyEightConfig.SPRITE_SIZE,
        TwentyFortyEightConfig.SPRITE_SIZE, false, false);
  }
}