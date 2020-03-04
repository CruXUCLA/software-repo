/**
 * oscP5sendreceive by andreas schlegel
 * example shows how to send and receive osc messages.
 * oscP5 website at http://www.sojamo.de/oscP5
 */
 
import oscP5.*;
  
OscP5 oscP5;
PrintWriter output;

void setup() {
  /* start oscP5, listening for incoming messages at port 12000 */
  oscP5 = new OscP5(this,12000);
  output = createWriter("positions.txt");
}

/* incoming osc message are forwarded to the oscEvent method. */
void oscEvent(OscMessage theOscMessage) {
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
