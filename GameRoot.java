package com.gluonapplication;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class GameRoot extends Pane {
  Spawner[] Spawn = new Spawner[2];
  ArrayList<Tower> Towers = new ArrayList<Tower>();
  static String GameMode;
  int gameOver = 10;
  AnimationTimer timer;
  int startPull = 40;
  int killIterator = 0;

  public GameRoot(String path) {
    Towers = new ArrayList<Tower>();
    this.setVisible(false);
  }

  public void StartGame() {
    Spawn[0] = new Spawner(startPull,
        LevelData.startCoordinates[GluonApplication.mapNumber][0] * GluonApplication.BLOCK_SIZE_X
            + ((GluonApplication.BLOCK_SIZE_X - Enemy.sizeWidth) / 2),
        LevelData.startCoordinates[GluonApplication.mapNumber][1] * GluonApplication.BLOCK_SIZE_Y
        + (GluonApplication.BLOCK_SIZE_Y - Enemy.sizeHeight) / 2);
    Spawn[1] = new Spawner(startPull,
        LevelData.startCoordinates[GluonApplication.mapNumber][2] * GluonApplication.BLOCK_SIZE_X
            + ((GluonApplication.BLOCK_SIZE_X - Enemy.sizeWidth) / 2),
        LevelData.startCoordinates[GluonApplication.mapNumber][3] * GluonApplication.BLOCK_SIZE_Y
        + (GluonApplication.BLOCK_SIZE_Y - Enemy.sizeHeight) / 2);
    final LongProperty CheckForShootTimer = new SimpleLongProperty();
    final LongProperty FrameTimer = new SimpleLongProperty(0);
    timer = new AnimationTimer() {
      long EveryTick = 0;
      long EveryTickForBot = 0;

      @Override
      public void handle(long now) {
        EveryTick++;
        if (EveryTick > 55) {
          EveryTick = 0;
          if (Spawn[0].iterator < Spawn[0].count)
            Spawn[0].CreateMonster();
          if (Spawn[1].iterator < Spawn[1].count)
            Spawn[1].CreateMonster();
        }
        if (now / 100000000 != CheckForShootTimer.get()) {
          if (GluonApplication.connectionType == "Client") {
            Thread thread_1 = new Thread(GluonApplication.client);
            thread_1.start();
          }
          if (GluonApplication.connectionType == "Server") {
            Thread thread_2 = new Thread(GluonApplication.server);
            thread_2.start();
          }
          CheckForShooting();
          for (int i = 0; i < Towers.size(); i++) {
            Towers.get(i).TimeToShoot -= 0.1;
          }
        }
        if (now / 10000000 != FrameTimer.get()) {
          Spawn[0].update();
          Spawn[1].update();
        }
        FrameTimer.set(now / 10000000);
        CheckForShootTimer.set(now / 100000000);
      }
    };
    timer.start();
    FadeTransition FT_Menu = new FadeTransition(Duration.seconds(1), GluonApplication.menu);
    FT_Menu.setFromValue(0);
    FT_Menu.setToValue(1);
    FadeTransition FT_FromGame = new FadeTransition(Duration.seconds(0.5), this);
    FT_FromGame.setFromValue(0);
    FT_FromGame.setToValue(1);
    GluonApplication.scene.setOnKeyPressed(event -> {
      if ((event.getCode() == KeyCode.ESCAPE) && (!GluonApplication.menu.isVisible())) {
        timer.stop();
        FT_Menu.play();
        this.setVisible(false);
        GluonApplication.menu.setVisible(true);
      } else if ((event.getCode() == KeyCode.ESCAPE) && (GluonApplication.menu.isVisible())) {
        timer.start();
        FT_FromGame.play();
        this.setVisible(true);
        GluonApplication.menu.setVisible(false);
      }
    });
  }

  public void CreateMap(int number) {
    for (int i = 0; i < LevelData.levels[number].length; i++) {
      String line = LevelData.levels[number][i];
      for (int j = 0; j < line.length(); j++) {
        switch (line.charAt(j)) {
          case 'T':
            Block tree = new Block(Block.BlockType.Tree, j * GluonApplication.BLOCK_SIZE_X,
                i * GluonApplication.BLOCK_SIZE_Y);
            break;
          case '0':
            Block grass = new Block(Block.BlockType.Grass, j * GluonApplication.BLOCK_SIZE_X,
                i * GluonApplication.BLOCK_SIZE_Y);
            break;
          default:
            Block road = new Block(Block.BlockType.Road, j * GluonApplication.BLOCK_SIZE_X,
                i * GluonApplication.BLOCK_SIZE_Y);
            break;
        }
      }
    }
  }

  public void CheckForShooting() {
    int SpawnersCount = 2;
    for (int i = 0; i < SpawnersCount; i++) {
      for (int j = 0; j < Spawn[i].enemies.size(); j++) {
        if (Spawn[i].enemies.get(j).Health <= 0) {
          continue;
        }
        for (int k = 0; k < Towers.size(); k++) {
          double EnemyPosX = Spawn[i].enemies.get(j).getTranslateX();
          double EnemyPosY = Spawn[i].enemies.get(j).getTranslateY();
          double TowerPosX = Towers.get(k).getTranslateX();
          double TowerPosY = Towers.get(k).getTranslateY();
          if (Spawn[i].enemies.get(j).Health > 0) {
            if (Towers.get(k).TimeToShoot <= 0) {
              if (Math.pow(Math.pow(EnemyPosX - TowerPosX, 2) + Math.pow(EnemyPosY - TowerPosY, 2),
                  0.5) < Towers.get(k).attackRange) {
                Towers.get(k).TimeToShoot = Towers.get(k).ShootCooldown;
                Towers.get(k).Shots = new Shot(Spawn[i].enemies.get(j),
                    Towers.get(k).posX + GluonApplication.BLOCK_SIZE_X / 2, Towers.get(k).posY);
              }
            }
          }
        }
      }
    }
  }
}
