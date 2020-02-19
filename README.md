#About
This is code written in Processing which receives an OSC stream and prints it out.

#How it Works
An OscP5 object will listen on a certain port via UDP for an OSC message. If the OSC message has a desired address pattern, it will be broken down to its arguments. An incoming osc message with more than one argument consists of an address stream and an Object list.

#What You Will Need
-The latest processing IDE, which can be found here: https://processing.org/download/
-The oscP5 libarary
  -After installation, import the oscP5 library by going up to top bar and going to Sketch --> Import Library --> Add Library... --> and type in oscP5 into the search bar. Then install the library.
-An device transmitting an OSC stream

#Customization
-The port is set at 12000 but change it to whatever port the OCR stream you want to receive is being broadcasted at.
-The address pattern is set at "/muse/elements/alpha_absolute" to measure the Alpha Absolute values but change it to whatever it is that you want to get data for.
-Check the OSC type tag of the data that you are receiving: the Alpha Absolute data is ffff, meaning that each measurement will consist of four floating point numbers. Based on the type tag of the data, parse the Object list accordingly.

#Essentials
-Make sure the receiving device and the sending device are on the same WiFi network. However, don't connect to school WiFi since many university and college networks block UDP traffic. Therefore, opt for a home WiFi network or a WiFi hotspot.
-Make sure to type in the IP address of the device receiving the OSC stream into the device transmitting the stream and make sure the port it is emitting at is the same port that the device receiving the stream is listening at.

#Credits
The code here was compiled almost entirely from Andreas Schlegel's examples for implementing his oscP5 library: http://www.sojamo.de/libraries/oscP5/#examples
