package com.gluonapplication;

import java.io.*;
import java.net.*;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class Server implements Runnable {

  static BufferedReader in;
  static ServerSocket servers;
  static PrintWriter out;
  static Socket fromclient;

  public Server() {
    System.out.println("Welcome to Server side");
    try {
      servers = new ServerSocket(2434);
    } catch (IOException e) {
      System.out.println("Couldn't listen to port 2434");
      System.exit(-1);
    }
    try {
      System.out.print("Waiting for a client...");
      fromclient = servers.accept();
      System.out.println("Client connected ");
    } catch (IOException e) {
      System.out.println("Can't accept");
      System.exit(-1);
    }
    try {
      in = new BufferedReader(new InputStreamReader(fromclient.getInputStream()));
    } catch (IOException e) {
      e.printStackTrace();
    }
    try {
      out = new PrintWriter(fromclient.getOutputStream(), true);
    } catch (IOException e1) {
      e1.printStackTrace();
    }
  }

  void sendCoordinates(int x, int y) {
    String output;

    output = Integer.toString(x / GluonApplication.BLOCK_SIZE_X) + " "
        + Integer.toString(y / GluonApplication.BLOCK_SIZE_Y);
    out.println(output);
  }

  void sendKillCreep(int currentSpawnIndex, int currentEnemyIndex) {
    String output;
    output =
        "kill " + Integer.toString(currentSpawnIndex) + " " + Integer.toString(currentEnemyIndex);
    out.println(output);
  }

  void sendReadyString() {
    String output = "serverIsReady";
    out.println(output);
  }
  
  void sendMapNumber(){
    String output = "mapNumber " + GluonApplication.mapNumber;
    out.println(output);
  }

  void recieve() {
    String Body = null;
    String ArrayBody[] = new String[3];
    try {
      Body = in.readLine();
      if (Body.isEmpty())
        return;
    } catch (IOException e) {
      e.printStackTrace();
    }

    if (Body.equals("clientIsReady")) {
      GluonApplication.menu.clientIsReady = true;
      return;
    }

    ArrayBody = Body.split(" ");

    if (ArrayBody[0].equals("kill")) {
      int killSpawnIndex = Integer.parseInt(ArrayBody[1]);
      int killEnemyIndex = Integer.parseInt(ArrayBody[2]);
      if (GluonApplication.gameRoot.Spawn[killSpawnIndex].enemies.size() > killEnemyIndex) {
        Enemy temp = GluonApplication.gameRoot.Spawn[killSpawnIndex].enemies.get(killEnemyIndex);
        if (temp.isVisible()) GluonApplication.gameRoot.killIterator++;
        
        if (GluonApplication.gameRoot.killIterator >= GluonApplication.gameRoot.Spawn.length * GluonApplication.gameRoot.startPull){
          FadeTransition FT_Menu = new FadeTransition(Duration.seconds(1), GluonApplication.menu);
          FT_Menu.setFromValue(0);
          FT_Menu.setToValue(1);
          GluonApplication.gameRoot.timer.stop();
          FT_Menu.play();
          GluonApplication.menu.menuBox.ChangeText("Win!");
          GluonApplication.menu.setVisible(true);
          GluonApplication.gameRoot.setVisible(false);
        }
        
        temp.setVisible(false);
        temp.Health = -10;
        Platform.runLater(() -> GluonApplication.gameRoot.getChildren().remove(temp));
        temp.animation.stop();
      }
    } else {
      int coordX = Integer.parseInt(ArrayBody[0]);
      int coordY = Integer.parseInt(ArrayBody[1]);
      Image img = new Image(getClass().getResourceAsStream("/Tower_Client.png"),
          GluonApplication.BLOCK_SIZE_X, GluonApplication.BLOCK_SIZE_Y, false, true);
      Platform.runLater(() -> GluonApplication.gameRoot.Towers.add(new Tower(
          coordX * GluonApplication.BLOCK_SIZE_X, coordY * GluonApplication.BLOCK_SIZE_Y,
          (double) GluonApplication.RESOLUTION_X * 150 / GluonApplication.BASE_RESOLUTION_X, img)));
    }
  }

  @Override
  public void run() {
    recieve();
  }
}
