package com.gluonapplication;

import java.io.File;
import java.net.InetAddress;

import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

class Menu extends StackPane {

  MenuBox menuBox;
  ListView<String> listView;

  boolean serverIsReady = false;
  boolean clientIsReady = false;

  public Menu(String path) {}

  public void ShowMenu() {
    VBox vboxChangeLevels = new VBox(10);
    VBox vboxChangeIpAddress = new VBox(10);
    MenuItem ButtonStart = new MenuItem("New Game");
    MenuItem ButtonServer = new MenuItem("Server");
    MenuItem ButtonClient = new MenuItem("Client");
    MenuItem ButtonConnect = new MenuItem("Connect");
    MenuItem ButtonBack = new MenuItem("Back");
    MenuItem ButtonChooseLevel = new MenuItem("Choose Level");
    MenuItem ButtonLoadLevel = new MenuItem("Load Level");
    MenuItem ButtonQuit = new MenuItem("Quit");
    menuBox = new MenuBox("Tower Defence", ButtonStart, ButtonServer, ButtonClient,
        ButtonChooseLevel, ButtonQuit);
    getChildren().add(menuBox);
    ButtonStart.setOnMouseClicked(event -> {
      GluonApplication.clearAll();
      if (GluonApplication.connectionType == "Server") {
        serverIsReady = true;
        GluonApplication.server.sendReadyString();
        GluonApplication.server.sendMapNumber();
        menuBox.ChangeText("Waiting for Client");
        Thread thread_2 = new Thread(GluonApplication.server);
        thread_2.start();
        try {
          thread_2.join();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
      if (GluonApplication.connectionType == "Client") {
        clientIsReady = true;
        ButtonChooseLevel.setDisable(true);
        GluonApplication.client.sendReadyString();
        menuBox.ChangeText("Waiting for Server");
        Thread thread_1 = new Thread(GluonApplication.client);
        thread_1.start();
        try {
          thread_1.join();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
      menuBox.ChangeText("Pause (Press ESC to continue)");
      setVisible(false);
      GluonApplication.gameRoot.GameMode = "Normal";
      GluonApplication.gameRoot.setVisible(true);
      GluonApplication.gameRoot.StartGame();
    });
    ButtonServer.setOnMouseClicked(event -> {
      GluonApplication.connectionType = "Server";
      try {
        HttpURLConnectionExample.sendIp();
      } catch (Exception e1) {
        e1.printStackTrace();
      }
      GluonApplication.server = new Server();
      ButtonServer.setDisable(true);
      ButtonClient.setDisable(true);
      menuBox.ChangeText("Server");
    });
    ButtonClient.setOnMouseClicked(event -> {
      GluonApplication.connectionType = "Client";
      menuBox.ChangeText("Client");
      listView = new ListView<>();
      String buffer = null;
      try {
        buffer = HttpURLConnectionExample.getIp();
      } catch (Exception e) {
        e.printStackTrace();
      }
      String adresses[] = buffer.split(" ");

      for (int i = 0; i < adresses.length; i++) {
        listView.getItems().add(adresses[i]);
      }

      Text text = new Text("Choose IP address");
      text.setFont(new Font(40));
      text.setFill(Color.WHITE);
      vboxChangeIpAddress.setAlignment(Pos.CENTER);
      vboxChangeIpAddress.getChildren().addAll(text);
      vboxChangeIpAddress.getChildren().addAll(listView);
      vboxChangeIpAddress.getChildren().addAll(ButtonConnect);
      vboxChangeIpAddress.getChildren().addAll(ButtonBack);
      setAlignment(Pos.CENTER);
      getChildren().addAll(vboxChangeIpAddress);
    });
    ButtonConnect.setOnMouseClicked(event -> {
      String choise;
      choise = listView.getSelectionModel().getSelectedItem();
      GluonApplication.client = new Client(choise);
      ButtonServer.setDisable(true);
      ButtonClient.setDisable(true);
      vboxChangeIpAddress.getChildren().clear();
      getChildren().remove(vboxChangeIpAddress);
    });
    ButtonBack.setOnMouseClicked(event -> {
      vboxChangeIpAddress.getChildren().clear();
      getChildren().remove(vboxChangeIpAddress);
    });
    ButtonChooseLevel.setOnMouseClicked(event -> {
      listView = new ListView<>();
      for (int i = 0; i < LevelData.levels.length; i++) {
        listView.getItems().add("Level " + (i + 1));
      }
      listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
      Text text = new Text("Choose level");
      text.setFont(new Font(40));
      text.setFill(Color.WHITE);
      vboxChangeLevels.setAlignment(Pos.CENTER);
      vboxChangeLevels.getChildren().addAll(text);
      vboxChangeLevels.getChildren().addAll(listView);
      vboxChangeLevels.getChildren().addAll(ButtonLoadLevel);
      setAlignment(Pos.CENTER);
      getChildren().addAll(vboxChangeLevels);
    });
    ButtonLoadLevel.setOnMouseClicked(event -> {
      String choise;
      choise = listView.getSelectionModel().getSelectedItem();
      String arrayBuf[] = choise.split(" ");
      GluonApplication.mapNumber = Integer.parseInt(arrayBuf[1]) - 1;
      String line = LevelData.levels[GluonApplication.mapNumber][0];
      GluonApplication.BLOCK_SIZE_X = GluonApplication.RESOLUTION_X / line.length();
      GluonApplication.BLOCK_SIZE_Y =
          GluonApplication.RESOLUTION_Y / LevelData.levels[GluonApplication.mapNumber].length;
      GluonApplication.gameRoot.CreateMap(GluonApplication.mapNumber);
      GluonApplication.clearAll();
      menuBox.ChangeText("Tower Defence");
      vboxChangeLevels.getChildren().clear();
      getChildren().remove(vboxChangeLevels);
    });
    ButtonQuit.setOnMouseClicked(event -> {
      if (GluonApplication.connectionType == "Server") {
        try {
          HttpURLConnectionExample.deleteIp();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
      System.exit(0);
    });
  }
}



class MenuBox extends StackPane {
  Text text;

  public MenuBox(String title, MenuItem... items) {
    Rectangle bg = new Rectangle(GluonApplication.RESOLUTION_X, GluonApplication.RESOLUTION_Y);
    bg.setOpacity(0.7);
    text = new Text(title);
    text.setFont(new Font(40));
    text.setFill(Color.WHITE);
    VBox vbox = new VBox();
    vbox.setAlignment(Pos.CENTER);
    vbox.getChildren().addAll(text);
    vbox.getChildren().addAll(items);
    setAlignment(Pos.CENTER);
    getChildren().addAll(bg, vbox);
  }

  public void ChangeText(String title) {
    text.setText(title);
  }
}


class MenuItem extends StackPane {
  public MenuItem(String name) {
    Rectangle bg = new Rectangle(300, 50);
    bg.setVisible(false);
    bg.setOpacity(0.6);
    Text text = new Text(name);
    text.setFill(Color.LIGHTGREY);
    text.setFont(Font.font(20));
    setAlignment(Pos.CENTER);
    getChildren().addAll(bg, text);
    this.setOnMouseEntered(event -> {
      bg.setVisible(true);
      text.setFill(Color.LIGHTGRAY);
    });
    this.setOnMouseExited(event -> {
      bg.setVisible(false);
      text.setFill(Color.LIGHTGRAY);
    });
    this.setOnMousePressed(event -> {
      bg.setFill(Color.WHITE);
      text.setFill(Color.BLACK);
    });
    this.setOnMouseReleased(event -> {
      bg.setFill(Color.BLACK);
      text.setFill(Color.WHITE);
    });
  }
}
