package twentyFortyEight;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import twentyFortyEight.config.OperatingMode;
import twentyFortyEight.config.TwentyFortyEightConfig;
import twentyFortyEight.javafx.TwentyFortyEightWindowFx;

/**
 * 2048 launcher class for JavaFX.
 */
public class TwentyFortyEightLauncher extends Application {
  @Override
  public void start(Stage primaryStage) {
    TwentyFortyEightWindowFx window = new TwentyFortyEightWindowFx();

    Scene scene = new Scene(window);
    primaryStage.setTitle("2048");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  /**
   * Start the game.
   *
   * @param args String[]
   */
  public static void main(String[] args) {
    if (args.length > 0 && args[0].equals("DEBUG")) {
      TwentyFortyEightConfig.MODE = OperatingMode.DEBUG;
    }

    launch(args);
  }
}