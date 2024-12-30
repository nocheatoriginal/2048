package twentyFortyEight.provider;

import twentyFortyEight.javafx.TwentyFortyEightSpriteFx;

/**
 * 2048 tile enumeration.
 */
public enum TwentyFortyEightTile {
  BACKGROUND("background.png");

  private final TwentyFortyEightSpriteFx sprite;

  TwentyFortyEightTile(String path) {
    sprite = new TwentyFortyEightSpriteFx(getClass().getResourceAsStream("/sprites/" + path));
  }

  /**
   * Returns the sprite.
   *
   * @return The sprite.
   */
  public TwentyFortyEightSpriteFx getSprite() {
    return sprite;
  }

  /**
   * Checks if the tile is equal to another tile.
   *
   * @param other The other tile.
   * @return True if the tiles are equal, false otherwise.
   */
  public boolean equals(TwentyFortyEightTile other) {
    if (other == null) {
      return false;
    }
    return sprite.equals(other.sprite);
  }
}
