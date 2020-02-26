package OscApp;

import processing.core.PApplet;
import oscP5.*;

public class pro extends PApplet{
	OscP5 osc;
	
	public static void main (String[] args){
		//I made project/package name = "OscApp" and class name = "pro"
		PApplet.main("OscApp.pro");
	}
	
	public void setup() {
		  osc = new OscP5(this, 12000);// start oscP5, listening for incoming messages at port 12000
		}


		// incoming osc message are forwarded to the oscEvent method.
	public void oscEvent(OscMessage theOscMessage) {
		if(theOscMessage.checkAddrPattern("/syntien/touchpad/1/touchpad1/press")==true) {
		    // check if the typetag is the right one.
		      // parse theOscMessage and extract the values from the osc message arguments.
		      float firstValue = theOscMessage.get(0).floatValue();  
		      float secondValue = theOscMessage.get(1).floatValue();
		      float thirdValue = theOscMessage.get(2).floatValue();
		      float fourthValue = theOscMessage.get(3).floatValue();
		      int fifthValue = theOscMessage.get(4).intValue();
		      int sixthValue = theOscMessage.get(5).intValue();
		      println(" values: "+firstValue+", "+secondValue+", "+thirdValue+", "+fourthValue+", "+fifthValue+", "+sixthValue);
		      return;
	 
    }
	}
}
