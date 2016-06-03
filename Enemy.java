package com.gluonapplication;

import javafx.animation.FadeTransition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Enemy extends Pane {
  ImageView imageView;
  public double posX;
  public double posY;
  char PrevBlock = 'N';

  int Health = 100;
  static int imageWidth = 32;
  static int imageHeight = 32;
  static int sizeWidth = (int) (GluonApplication.BLOCK_SIZE_X * 0.64);
  static int sizeHeight = (int) (GluonApplication.BLOCK_SIZE_Y * 0.64);
  int offsetX = 0;
  int offsetY = 0;
  int currentEnemyIndex;
  int currentSpawnIndex;
  SpriteAnimation animation;

  public Enemy(double posX, double posY, int count, int columns, int spawnIndex, int enemyIndex) {
    currentSpawnIndex = spawnIndex;
    currentEnemyIndex = enemyIndex;
    Image img = new Image(getClass().getResourceAsStream("/enemy.png"));
    this.imageView = new ImageView(img);
    this.imageView.setFitWidth(sizeWidth);
    this.imageView.setFitHeight(sizeHeight);
    this.imageView.setViewport(new Rectangle2D(offsetX, offsetY, imageWidth, imageHeight));
    animation = new SpriteAnimation(imageView, Duration.millis(200), count, columns, offsetX,
        offsetY, imageWidth, imageHeight);
    this.posX = posX;
    this.posY = posY;
    this.setTranslateX(posX);
    this.setTranslateY(posY);
    getChildren().add(imageView);
    GluonApplication.gameRoot.getChildren().add(this);
  }

  public void moveX(double x) {
    boolean right = true;
    if (x < 0)
      right = false;
    for (int i = 0; i < Math.abs(x); i++) {
      if (right) {
        this.setTranslateX(this.getTranslateX() + x);
        posX += x;
      } else {
        this.setTranslateX(this.getTranslateX() + x);
        posX += x;
      }
    }
  }

  public void moveY(double y) {
    boolean down = true;
    if (y < 0)
      down = false;
    for (int i = 0; i < Math.abs(y); i++) {
      if (down) {
        this.setTranslateY(this.getTranslateY() + y);
        posY += y;
      } else {
        this.setTranslateY(this.getTranslateY() + y);
        posY += y;
      }
    }
  }

  public void EnemyGoalRiched() {
    Health = 0;
    if (this.isVisible())GluonApplication.gameRoot.killIterator++;
    this.setVisible(false);
    FadeTransition FT_Menu = new FadeTransition(Duration.seconds(1), GluonApplication.menu);
    FT_Menu.setFromValue(0);
    FT_Menu.setToValue(1);
    GluonApplication.gameRoot.getChildren().remove(this);
    if (GluonApplication.gameRoot.killIterator >= GluonApplication.gameRoot.Spawn.length * GluonApplication.gameRoot.startPull){
      GluonApplication.gameRoot.timer.stop();
      FT_Menu.play();
      GluonApplication.menu.menuBox.ChangeText("Win!");
      GluonApplication.menu.setVisible(true);
      GluonApplication.gameRoot.setVisible(false);
    }
    this.animation.stop();
    GluonApplication.gameRoot.gameOver--;
    if (GluonApplication.gameRoot.gameOver <= 0){
      GluonApplication.gameRoot.timer.stop();
      FT_Menu.play();
      GluonApplication.menu.menuBox.ChangeText("GameOver");
      GluonApplication.menu.setVisible(true);
      GluonApplication.gameRoot.setVisible(false);
    }
  }

  public void GetDamage(int Damage) {
    FadeTransition FT_Menu = new FadeTransition(Duration.seconds(1), GluonApplication.menu);
    FT_Menu.setFromValue(0);
    FT_Menu.setToValue(1);
    if (Health < 0)
      return;
    Health = Health - Damage;
    if (Health <= 0) {
      if (GluonApplication.connectionType == "Server") {
        GluonApplication.server.sendKillCreep(currentSpawnIndex, currentEnemyIndex);
      }
      if (GluonApplication.connectionType == "Client") {
        GluonApplication.client.sendKillCreep(currentSpawnIndex, currentEnemyIndex);
      }
      if (this.isVisible()) GluonApplication.gameRoot.killIterator++;
      this.setVisible(false);
      GluonApplication.gameRoot.getChildren().remove(this);
      System.out.println(GluonApplication.gameRoot.Spawn.length * GluonApplication.gameRoot.startPull + " " + GluonApplication.gameRoot.killIterator);
      if (GluonApplication.gameRoot.killIterator >= GluonApplication.gameRoot.Spawn.length * GluonApplication.gameRoot.startPull){
        GluonApplication.gameRoot.timer.stop();
        FT_Menu.play();
        GluonApplication.menu.menuBox.ChangeText("Win!");
        GluonApplication.menu.setVisible(true);
        GluonApplication.gameRoot.setVisible(false);
      }
      this.animation.stop();
    }
  }
}
