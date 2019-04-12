#include <SoftwareSerial.h>
#include "TinyGPS++.h"
#include "pb_common.h"
#include "pb.h"
#include "pb_encode.h"
#include "messages.pb.h"

SoftwareSerial blueToothSerial(2, 3);
TinyGPSPlus gps;
SeaRequest request = SeaRequest_init_zero;

void setup() {
  uint8_t buffer[128];
  Serial.begin(9600);


  blueToothSerial.begin(9600);

  request.nav_request.lat = 1.0f;
  pb_ostream_t stream = pb_ostream_from_buffer(buffer, sizeof(buffer));
  bool status = pb_encode(&stream, SeaRequest_fields, &request);
 
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
