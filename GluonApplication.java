package com.gluonapplication;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GluonApplication extends Application {
  public static int BLOCK_SIZE_X = 50;
  public static int BLOCK_SIZE_Y = 50;
  public static int RESOLUTION_X = 650;
  public static int RESOLUTION_Y = 355;
  public static int BASE_RESOLUTION_X = 1000;
  public static int BASE_RESOLUTION_Y = 800;
  static GameRoot gameRoot;
  static Menu menu;
  static Pane All = new Pane();
  static Scene scene;

  static Server server;
  static Client client;

  static int mapNumber = 1;

  static String connectionType;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    String line = LevelData.levels[mapNumber][0];
    RESOLUTION_X = 650;
    RESOLUTION_Y = 355;
    BLOCK_SIZE_X = RESOLUTION_X / line.length();
    BLOCK_SIZE_Y = RESOLUTION_Y / LevelData.levels[mapNumber].length;

    menu = new Menu("menu.mp3");
    gameRoot = new GameRoot("wave1.mp3");
    scene = new Scene(All, RESOLUTION_X, RESOLUTION_Y);
    All.getChildren().addAll(menu, gameRoot);
    menu.ShowMenu();
    primaryStage.setTitle("TowerDefence");
    primaryStage.setScene(scene);
    primaryStage.show();
    gameRoot.CreateMap(mapNumber);
  }

  public static void clearAll() {
    if (!GluonApplication.gameRoot.Towers.isEmpty()) {
      for (int i = 0; i < GluonApplication.gameRoot.Towers.size(); i++) {
        GluonApplication.gameRoot.getChildren().remove(GluonApplication.gameRoot.Towers.get(i));
      }
      GluonApplication.gameRoot.Towers.clear();
    }
    if (GluonApplication.gameRoot.Spawn[0].INDEX > 0) {
      for (int i = 0; i < GluonApplication.gameRoot.Spawn.length; i++) {
        for (int j = 0; j < GluonApplication.gameRoot.Spawn[i].enemies.size(); j++) {
          GluonApplication.gameRoot.getChildren()
              .remove(GluonApplication.gameRoot.Spawn[i].enemies.get(j));
        }
        GluonApplication.gameRoot.Spawn[i].enemies.clear();
      }
    }
  }
}
