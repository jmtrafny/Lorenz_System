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

import peasy.*;
import controlP5.*;

float x = 0.01;
float y = 0;
float z = 0;
float dt = 0.01;

float sigma = 10;
float rho = 28;
float beta = 8.0/3.0;

ArrayList<PVector> points = new ArrayList<PVector>();

PeasyCam cam;
ControlP5 cp5;
RadioButton r;

void setup() {
  size(800, 600, P3D);
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

void draw() {

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
    hu += 0.1;
    if (hu > 255) {
      hu = 0;
    }
  }
  endShape();
}

void controlEvent(ControlEvent e) {
  if (e.isFrom(r)) {
    x = 0.01;
    y = 0;
    z = 0;
    rho = int(e.getGroup().getValue());
    for (int i = points.size() - 1; i >= 0; i--) {
      points.remove(i);
    }
  }
}

void drawGUI() {
  cp5.draw();
  textSize(18);
  text("Rho: ", 50, 60);
  fill(0, 255, 255);
}