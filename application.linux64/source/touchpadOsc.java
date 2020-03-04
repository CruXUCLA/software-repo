import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import oscP5.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class touchpadOsc extends PApplet {

/**
 * oscP5sendreceive by andreas schlegel
 * example shows how to send and receive osc messages.
 * oscP5 website at http://www.sojamo.de/oscP5
 */
 

  
OscP5 oscP5;
PrintWriter output;

public void setup() {
  /* start oscP5, listening for incoming messages at port 12000 */
  oscP5 = new OscP5(this,12000);
  output = createWriter("positions.txt");
}

/* incoming osc message are forwarded to the oscEvent method. */
public void oscEvent(OscMessage theOscMessage) {
  /* print the address pattern and the typetag of the received OscMessage */
  if(theOscMessage.checkAddrPattern("/syntien/basic/1/touchpad1/press")==true) {
    /* check if the typetag is the right one. */
      /* parse theOscMessage and extract the values from the osc message arguments. */
      float firstValue = theOscMessage.get(0).floatValue();  
      float secondValue = theOscMessage.get(1).floatValue();
      float thirdValue = theOscMessage.get(2).floatValue();
      float fourthValue = theOscMessage.get(3).floatValue();
      int fifthValue = theOscMessage.get(4).intValue();
      int sixthValue = theOscMessage.get(5).intValue();
      output.println(" values: "+firstValue+", "+secondValue+", "+thirdValue+", "+fourthValue+", "+fifthValue+", "+sixthValue);
      output.flush();
      return;
  }
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "touchpadOsc" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
