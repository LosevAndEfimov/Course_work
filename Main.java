package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Класс содержит основную информацию, сцену, Root-ы
 * 
 * @author pixxx
 * @version 1.0 Alpha
 */
public class Main extends Application {
  /** Размер блока в пикселях и размеры окна */
  public static final int BLOCK_SIZE = 50;
  public static final int RESOLUTION_X = 1000;
  public static final int RESOLUTION_Y = 800;
<<<<<<< HEAD
=======

>>>>>>> ff06d166cd1817378e54935ef818335307b5dcf4
  /** Сцены и Root-ы */
  static GameRoot gameRoot;
  static Menu menu;
  static Pane All = new Pane();
  static Scene scene;

  static Server server;
  static Client client;

  /** Type of connection: Server or Client */
  static String connectionType;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
<<<<<<< HEAD
=======
    connectionType = "Client";
    if (connectionType == "Server") {
      server = new Server();
    }
    if (connectionType == "Client") {
      client = new Client();
    }
>>>>>>> ff06d166cd1817378e54935ef818335307b5dcf4
    menu = new Menu("menu.mp3");
    gameRoot = new GameRoot("wave1.mp3");
    scene = new Scene(All, RESOLUTION_X, RESOLUTION_Y);
    All.getChildren().addAll(menu, gameRoot);
    menu.ShowMenu();
    primaryStage.setTitle(connectionType);
    primaryStage.setScene(scene);
    primaryStage.show();
  }
}
