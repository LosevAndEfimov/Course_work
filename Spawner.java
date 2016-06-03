package com.gluonapplication;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.animation.SequentialTransition;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Spawner{
	int count;
	
	int startPosX;
	int startPosY;
	int index;
	
	int iterator;
	
	static int INDEX = 0;
	
	ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	
	public Spawner(int count, int startPosX, int startPosY){
		this.startPosX = startPosX;
		this.startPosY = startPosY;
		this.count = count;
		this.index = INDEX;
		INDEX++;
		iterator = 0;
		enemies = new ArrayList<Enemy>();
	}
	
	public void CreateMonster(){
		enemies.add(new Enemy(startPosX, startPosY, 3, 3, this.index , iterator));
		iterator++;
	}
	
	public void update(){
		int BlockX, BlockY;
		double BlockCenterX, BlockCenterY;
			for (int j=0; j<enemies.size(); j++){
			    double speedX = (double)GluonApplication.RESOLUTION_X / GluonApplication.BASE_RESOLUTION_X;
			    double speedY = (double)GluonApplication.RESOLUTION_Y / GluonApplication.BASE_RESOLUTION_Y;
				BlockX = (int)(enemies.get(j).posX / GluonApplication.BLOCK_SIZE_X);
				BlockY = (int)(enemies.get(j).posY / GluonApplication.BLOCK_SIZE_Y);
				String line = LevelData.levels[GluonApplication.mapNumber][BlockY];
				BlockCenterX = enemies.get(j).posX + Enemy.sizeWidth/2; 
				BlockCenterY = enemies.get(j).posY + Enemy.sizeHeight/2;
				if ((enemies.get(j).PrevBlock=='U')
						&&(BlockCenterY>BlockY * GluonApplication.BLOCK_SIZE_Y +
					GluonApplication.BLOCK_SIZE_Y / 2)){
					enemies.get(j).moveY(-speedY);
					continue;
				}
				if ((enemies.get(j).PrevBlock=='D')
						&&(BlockCenterY<BlockY * GluonApplication.BLOCK_SIZE_Y +
						GluonApplication.BLOCK_SIZE_Y / 2)){
					enemies.get(j).moveY(speedY);
					continue;
				}
				if ((enemies.get(j).PrevBlock=='R')
						&&(BlockCenterX<BlockX * GluonApplication.BLOCK_SIZE_X + 
						GluonApplication.BLOCK_SIZE_X / 2)){
					enemies.get(j).moveX(speedX);
					continue;
				}
				if ((enemies.get(j).PrevBlock=='L')
						&&(BlockCenterX>BlockX * GluonApplication.BLOCK_SIZE_X + 
						GluonApplication.BLOCK_SIZE_X / 2)){
					enemies.get(j).moveX(-speedX);
					continue;
				}
				if (line.charAt(BlockX)=='U'){
					enemies.get(j).animation.play();
					enemies.get(j).animation.setOffsetY(96);
					enemies.get(j).PrevBlock = 'U';
					enemies.get(j).moveY(-speedY);
				}
				if (line.charAt(BlockX)=='D'){
					enemies.get(j).animation.play();
					enemies.get(j).animation.setOffsetY(0);
					enemies.get(j).PrevBlock = 'D';
					enemies.get(j).moveY(speedY);
				}	
				if (line.charAt(BlockX)=='R'){
					enemies.get(j).animation.play();
					enemies.get(j).animation.setOffsetY(64);
					enemies.get(j).PrevBlock = 'R';
					enemies.get(j).moveX(speedX);
				}
				if (line.charAt(BlockX)=='L') {
					enemies.get(j).animation.play();
					enemies.get(j).animation.setOffsetY(32);
					enemies.get(j).PrevBlock = 'L';
					enemies.get(j).moveX(-speedX);
				}
				
				if (line.charAt(BlockX)=='E'){
					if (enemies.get(j).Health > 0) enemies.get(j).EnemyGoalRiched();
				}
			}
		}
}
