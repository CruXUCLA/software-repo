import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import oscP5.*; 
import java.net.InetAddress; 
import java.util.Iterator; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class OSC_Data_Monitor extends PApplet {

/**
 * OSC DATA MONITOR. By Kasper Kamperman
 * 23-08-2011
 * 17-06-2017 (update Processing 3 and controlP5)
 * 04-07-2017 (fix clear view button)
 * based on the excellent controlP5 and oscP5 examples and libraries from Andreas Schlegel.
 * http://www.sojamo.de/libraries/controlP5/
 * http://www.sojamo.de/libraries/oscP5/
 *
 * http://www.kasperkamperman.com
 *
 * Licensed under the MIT license:
 * http://www.opensource.org/licenses/mit-license.php
 *
 * Processing 3 or higher and ControlP5 2.2.6
 *
**/

final String version = "17-06-2017";

// get IP address from this computer
InetAddress inet;
String myIP;

// store oscP5 objects
HashMap oscP5Objects;

// variabels to display incoming data
ArrayList monitorList;
HashMap monitorHash;
boolean monitorValueView = true; // switch between list and update view

int monitorListLength = 32;
String monitor;
String typetag;
char tag;
String monitorDisplay;

// variabels to filter incoming data
HashMap   oscaddressesHash;
ArrayList oscaddressesList;
int oscaddressCounter = 0;
int countFilteredaddresses; // to check if addresses are filtered

public void startListeningToPort(int port)
{ // triggered when clicking item in listCommonOscPorts.

  // check if we are not already listening by checking the Hashmap
  // add new OscListener object to the oscP5Objects HashMap
  if(!oscP5Objects.containsKey(port))
  { println(oscP5Objects.size());
    // check if HashMap size() < 10
    if(oscP5Objects.size()<10)
    { MyOSCListener o = new MyOSCListener(port);

      oscP5Objects.put(port,o);
      // add item to listActiveOscPorts
      listActiveOscPorts.addItem(str(port), port);
      listCommonOscPorts.removeItem(str(port));
      monitorList.add((String) "- Listening to port: "+port);
    }
    else
    { monitorList.add((String) "- Cannot listen to more than 10 ports");
    }
  }
  else
  { monitorList.add((String) "- Already listening to port: "+port);
  }
}

public void stopListeningToPort(int port)
{ // triggered when clicking item in listActiveOscPorts

  if(oscP5Objects.containsKey(port))
  { // get the port from the HashMap
    MyOSCListener o = (MyOSCListener) oscP5Objects.get(port);

    o.stop();                   // stop listening
    oscP5Objects.remove(port);  // remove port from HashMap

    // remove item from listActiveOscPorts
    String item = Integer.toString(port);
    listActiveOscPorts.removeItem(item);
    //listActiveOscPorts.addItem(str(port), port);
    listCommonOscPorts.addItem(str(port), port);
    monitorList.add((String) "- Stopped listening to port: "+port);
  }
}

public void addOSCaddress(String s) {


   if (!oscaddressesHash.containsKey(s))
   { // Hash used to check if the adres already was used before.
     //println("not in list yet: "+s+" "+oscaddressCounter);
     oscaddressesHash.put(s,new Integer(0));

     // show in list.
     listReceivedOscAddresses.addItem(s,oscaddressCounter);
     oscaddressesList.add(new String(s)); // index will be the same as the oscaddressCounter
     oscaddressCounter++;
   }

}
// == OscListener ===============================================

class MyOSCListener {
  
  int port;
  OscP5 osc;
  boolean filterMessage;
  
  MyOSCListener(int thePort) {
    port = thePort;
    osc = new OscP5(this,port);
  }
  
  public void stop() { 
    osc.stop();  
  }
  
  public void oscEvent(OscMessage theOscMessage) {
    
    // store address in oscaddressesHash
    addOSCaddress(theOscMessage.addrPattern());
    
    // normally all messages are filtered (pass through filter)
    // if there is an item in the oscFilteraddressedHash its 
    // compared with the incoming message if the addresses in in the Hash list
    // the message is passed through. 
    
    if(countFilteredaddresses>0)
    { filterMessage = false;
      
      if((Integer) oscaddressesHash.get(theOscMessage.addrPattern()) == 1)
      { filterMessage = true;
      }    
    }
    else filterMessage = true;
   
    // only monitor message if filter pass through is true   
    if(filterMessage)
    { // empty the monitor string
      monitor = "";
      
      // dadd address pattern to string
      monitor = "["+port+"] " + theOscMessage.addrPattern();
      
      typetag = theOscMessage.typetag();
      
      // display each data item in the monitor.   
      for(int i=0;i<typetag.length();i++)
      { tag = typetag.charAt(i);
        
        if(tag == 'i') // message has integer
        { monitor = monitor + " " + theOscMessage.get(i).intValue() + " (int)";    
        }
        else if(tag == 'f') // message has float
        { // use nfc to lmit the float to 3 decimals and convert it to a string
          monitor = monitor + " " + nfc(theOscMessage.get(i).floatValue(),3) + " (float)";
        }
        else if(tag == 's') // message has string
        { monitor = monitor + " " + theOscMessage.get(i).stringValue() + " (string)";
        }  
        else
        { // other type see documentation:
          // http://www.sojamo.de/libraries/oscP5/reference/oscP5/OscArgument.html
          // print the type tag but not the value
          monitor = monitor + " type:" + typetag;
        }
      }
      
      monitorHash.put(theOscMessage.addrPattern(),new String(monitor));
      
      //println(monitor);
      
      // add the string to the monitor list array. 
      // will be displayed in the draw() loop.
      // if statement prevents adding to much data
      // incase of a data overflow no new data is added...
      if(monitorList.size()<monitorListLength+25)
      { monitorList.add((String) monitor);          
      }
      else 
      { //println("overflow...");
      }
    }
  }
}
  public void settings() {  size(800,630,P2D); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "OSC_Data_Monitor" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}