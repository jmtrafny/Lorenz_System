import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import peasy.*; 
import controlP5.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Lorenz_System extends PApplet {

// Original code by Daniel Shiffman
// Modified by James Trafny
// www.github.com/jmtrafny
//
// http://codingtra.in
// http://patreon.com/codingtrain
// Video tutorial for base code: https://youtu.be/f0lkz2gSsIk
// Original code at: https://github.com/CodingTrain/Rainbow-Code/tree/master/challenges/CC_12_LorenzAttractor
// More information on the Lorenz System can be found at https://en.wikipedia.org/wiki/Lorenz_system
//
// Rho = 14, 13, 15, 28




float x = 0.01f;
float y = 0;
float z = 0;
float dt = 0.01f;

float sigma = 10;
float rho = 28;
float beta = 8.0f/3.0f;

ArrayList<PVector> points = new ArrayList<PVector>();

PeasyCam cam;
ControlP5 cp5;
RadioButton r;

public void setup() {
  
  colorMode(HSB);
  
  cam = new PeasyCam(this, 500);
  cp5 = new ControlP5(this);
  
  cp5.setAutoDraw(false);
  r = cp5.addRadioButton("radio", 50, 80);
  r.addItem("28", 28);
  r.addItem("15", 15);
  r.addItem("14", 14);
  r.addItem("13", 13);
  r.activate(0);
}

public void draw() {

  background(0);
 
  translate(0, 0, 0);
  line(0, 100, 0, 0, 0, 0);
  text("+X", 110, 0, 0);
  line(0, 0, 0, 100, 0, 0);
  text("+Y", 0, 110, 0);
  line(0, 0, 0, 0, 0, 100);
  text("+Z", 0, 0, 110);

  cam.beginHUD();
  drawGUI();
  cam.endHUD();

  // The model is a system of three ordinary differential 
  // equations now known as the Lorenz equations:

  float dx = (sigma * (y - x)) * dt;
  float dy = (x * (rho - z) - y) * dt;
  float dz = (x * y - beta * z) * dt;

  //  Here x, y, and z make up the system state, t is time,
  //  and sigma, rho, and beta are the system parameters

  x = x + dx;
  y = y + dy;
  z = z + dz;

  points.add(new PVector(x, y, z));

  scale(5);
  stroke(255);
  noFill();

  float hu = 0;
  beginShape();
  for (PVector v : points) {
    stroke(hu, 255, 255);
    vertex(v.x, v.y, v.z);
    hu += 0.1f;
    if (hu > 255) {
      hu = 0;
    }
  }
  endShape();
}

public void controlEvent(ControlEvent e) {
  if (e.isFrom(r)) {
    x = 0.01f;
    y = 0;
    z = 0;
    rho = PApplet.parseInt(e.getGroup().getValue());
    for (int i = points.size() - 1; i >= 0; i--) {
      points.remove(i);
    }
  }
}

public void drawGUI() {
  cp5.draw();
  textSize(18);
  text("Rho: ", 50, 60);
  fill(0, 255, 255);
}
  public void settings() {  size(800, 600, P3D); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Lorenz_System" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
