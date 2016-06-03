package com.gluonapplication;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

/**
 * 
 * @author pixxx
 */
public class Block extends Pane {
  ImageView block;

  Image img_grass = new Image(getClass().getResourceAsStream("/grass.jpg"));

  Image img_tree = new Image(getClass().getResourceAsStream("/tree.png"));

  Image img_road = new Image(getClass().getResourceAsStream("/road.jpg"));

  public enum BlockType {
    Grass, Tree, Road;
  }

  public Block(BlockType type, int x, int y) {
    block = new ImageView();
    block.setFitHeight(GluonApplication.BLOCK_SIZE_Y);
    block.setFitWidth(GluonApplication.BLOCK_SIZE_X);
    setTranslateX(x);
    setTranslateY(y);
    switch (type) {
      case Tree:
        block.setImage(img_tree);
        break;
      case Grass:
        block.setImage(img_grass);
        Line line1 = new Line(x, y, x + GluonApplication.BLOCK_SIZE_X, y);
        Line line2 = new Line(x + GluonApplication.BLOCK_SIZE_X, y,
            x + GluonApplication.BLOCK_SIZE_X, y + GluonApplication.BLOCK_SIZE_Y);
        Line line3 = new Line(x + GluonApplication.BLOCK_SIZE_X, y + GluonApplication.BLOCK_SIZE_Y,
            x, y + GluonApplication.BLOCK_SIZE_Y);
        Line line4 = new Line(x, y + GluonApplication.BLOCK_SIZE_Y, x, y);
        this.setOnMouseEntered(event -> {
          GluonApplication.gameRoot.getChildren().addAll(line1, line2, line3, line4);
        });
        this.setOnMouseExited(event -> {
          GluonApplication.gameRoot.getChildren().removeAll(line1, line2, line3, line4);
        });
        this.setOnMouseClicked(event -> {
          Image img;
          if (GluonApplication.connectionType == "Server") {
            System.out.println(x + " " + y);
            GluonApplication.server.sendCoordinates(x, y);
            img = new Image(getClass().getResourceAsStream("/Tower_Server.png"),
                GluonApplication.BLOCK_SIZE_X, GluonApplication.BLOCK_SIZE_Y, false, true);
          } else if (GluonApplication.connectionType == "Client") {
            System.out.println(x + " " + y);
            GluonApplication.client.sendCoordinates(x, y);
            img = new Image(getClass().getResourceAsStream("/Tower_Client.png"),
                GluonApplication.BLOCK_SIZE_X, GluonApplication.BLOCK_SIZE_Y, false, true);
          } else {
            img = new Image(getClass().getResourceAsStream("/Tower.png"),
                GluonApplication.BLOCK_SIZE_X, GluonApplication.BLOCK_SIZE_Y, false, true);
          }
          Tower tower = new Tower(x, y,
              (double) GluonApplication.RESOLUTION_X * 150 / GluonApplication.BASE_RESOLUTION_X,
              img);
          GluonApplication.gameRoot.Towers.add(tower);
        });
        break;
      case Road:
        block.setImage(img_road);
        break;
    }
    getChildren().add(block);
    GluonApplication.gameRoot.getChildren().add(this);
  }
}
