import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Snake extends PApplet {

public void setup() {
  PImage titlebaricon = loadImage("sketch.png");
  surface.setIcon(titlebaricon);
  
  noStroke();
  frameRate(10);
  pickLocation();
  score_font = loadFont("3dumb-48.vlw");
  
  tailX = new IntList();
  tailY = new IntList();
}

int direction = 1;
int SnakeX = 0;
int SnakeY = height/2-10;
int tailLength = 1;
IntList tailX;
IntList tailY;
float FoodX;
float FoodY;
int score;
PFont score_font;
boolean result = false;

public void reset() {
  direction = 1;
  SnakeX = 0;
  SnakeY = height/2;
  tailLength = 1;
  tailX.clear();
  tailY.clear();
  pickLocation();
  score = 0;
}

public void drawSnake() {
  rect(SnakeX, SnakeY, 20,20);
}

public void drawTail() {
  for(int i = 0; i < tailLength; i++) {
    rect(tailX.get(i),tailY.get(i), 20,20);
  }
}

public void pickLocation() {
  int cols = floor(width/20);
  int rows = floor(height/20);
  FoodX = floor(random(cols));
  FoodY = floor(random(rows));
  FoodX *= 20;
  FoodY *= 20;
}

public boolean eatFood() {
  if(dist(FoodX, FoodY, SnakeX, SnakeY) < 1) {
    return true;
  } else {
    return false;
  }
}

public Boolean dead() {
  for(int i = 0; i < tailX.size(); i++) {
    if(SnakeX == tailX.get(i) && SnakeY == tailY.get(i)) {
      return true;
    }
  }
  return false;
}

public void updateSnake() {
  for(int i = 0; i < tailX.size()-1; i++) {
    tailX.set(i, tailX.get(i+1));
    tailY.set(i, tailY.get(i+1));
  }
  tailX.set(tailLength-1, SnakeX);
  tailY.set(tailLength-1, SnakeY);
  
  if(abs(direction) == 1){
    SnakeX += direction*20;
  } else if(direction == 2){
    SnakeY += 20;
  } else if(direction == -2){
    SnakeY += -20;
  }
  
  drawTail();
  drawSnake();
}

public void draw() {
  background(0);
  fill(255);
  rect(FoodX, FoodY, 20,20);
  textSize(24);
  textFont(score_font);
  text("Score: " + score, width-225,height-25);
  fill(31, 219, 32);
  
  if(SnakeX == -20) {
    SnakeX = width-20;
  } else if(SnakeX == width) {
    SnakeX = 0;
  } else if(SnakeY == -20) {
    SnakeY = height-20;
  } else if(SnakeY == height) {
    SnakeY = 0;
  }
  
  if(eatFood()) {
    pickLocation();
    tailLength++;
    score++;
  }
  
  if(dead()) {
    reset();
  }
  
  updateSnake();
}

public void keyPressed() {
  if(keyCode == LEFT && direction != 1){
    direction = -1;
  } else if(keyCode == RIGHT && direction != -1){
    direction = 1;
  } else if(keyCode == UP && direction != 2){
    direction = -2;
  } else if(keyCode == DOWN && direction != -2){
    direction = 2;
  }
}
  public void settings() {  fullScreen(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#666666", "--hide-stop", "Snake" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
