#include <SoftwareSerial.h>
#include "TinyGPS++.h"

SoftwareSerial blueToothSerial(2, 3);
TinyGPSPlus gps;

void setup() {
  Serial.begin(9600);

  blueToothSerial.begin(9600);
}

void loop () {
  bool read = false;
  // while there is data coming in, read it
  // and send to the hardware serial port:
  while (blueToothSerial.available() > 0) {
    char inByte = blueToothSerial.read();
    Serial.write(inByte);
    read = true;
  }
  if (read)  {
     Serial.println();
     delay(50);
     blueToothSerial.println("hello world");
  }
}
