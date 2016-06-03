package com.gluonapplication;

import java.util.ArrayList;

import com.sun.prism.paint.Color;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

public class Tower extends Pane {
  int Damage;
  ImageView imageView;

  double ShootCooldown;

  double TimeToShoot = 0;
  Image img;
  double posX;
  double posY;
  double attackRange;
  int height;
  int width;
  Shot Shots;

  public Tower(double posX, double posY, double attackRange, Image image) {
    width = GluonApplication.BLOCK_SIZE_X;
    height = GluonApplication.BLOCK_SIZE_Y;
    ShootCooldown = 0.5;
    this.attackRange = attackRange;
    this.posX = posX;
    this.posY = posY;
    img = image;
    /*if (GluonApplication.connectionType == "Server") {
      img = new Image(getClass().getResourceAsStream("/Tower_Server.png"), width, height, false,
          true);
    } else if (GluonApplication.connectionType == "Client") {
      img = new Image(getClass().getResourceAsStream("/Tower_Client.png"), width, height, false,
          true);
    } else {
      img = new Image(getClass().getResourceAsStream("/Tower.png"), width, height, false, true);
    }*/
    this.imageView = new ImageView(img);
    this.imageView.setViewport(new Rectangle2D(0, 0, width, height));
    this.setTranslateX(posX);
    this.setTranslateY(posY);
    getChildren().add(imageView);
    GluonApplication.gameRoot.getChildren().add(this);

    Circle AttackRangeCircle = new Circle(posX + GluonApplication.BLOCK_SIZE_X / 2,
        posY + GluonApplication.BLOCK_SIZE_Y / 2, attackRange);
    AttackRangeCircle.setOpacity(0.1);
    GluonApplication.gameRoot.getChildren().add(AttackRangeCircle);
    AttackRangeCircle.setVisible(false);
    this.setOnMouseEntered(event -> {
      AttackRangeCircle.setVisible(true);
    });
    this.setOnMouseExited(event -> {
      AttackRangeCircle.setMouseTransparent(true);
      AttackRangeCircle.setVisible(false);
    });
  }
}
