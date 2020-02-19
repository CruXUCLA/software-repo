import oscP5.*;

OscP5 oscP5;

void setup() {
  oscP5 = new OscP5(this, 12000);// start oscP5, listening for incoming messages at port 12000
}


/* incoming osc message are forwarded to the oscEvent method. */
void oscEvent(OscMessage theOscMessage) {
  if (theOscMessage.checkAddrPattern("/muse/elements/alpha_absolute")==true) {// check that the address pattern of incoming osc message is the one you want (in example, we want Alpha Absolute)
    /* parse incoming osc message and extract the values from the osc message arguments (since Alpha Absolute is ffff, we will extract 4 float values). */
    float firstValue = theOscMessage.get(0).floatValue();  
    float secondValue = theOscMessage.get(1).floatValue();
    float thirdValue = theOscMessage.get(2).floatValue();
    float fourthValue = theOscMessage.get(3).floatValue();
    println(" values: "+firstValue+", "+secondValue+", "+thirdValue+", "+fourthValue);// print the 4 floats from incoming osc message
    return;
  }
}
