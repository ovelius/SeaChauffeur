#include <SoftwareSerial.h>
#include "TinyGPS++.h"
#include "pb_common.h"
#include "pb.h"
#include "pb_encode.h"
#include "pb_decode.h"
#include "messages.pb.h"

SoftwareSerial blueToothSerial(2, 3);
TinyGPSPlus gps;

void setup() {
  Serial.begin(9600);


  blueToothSerial.begin(9600);

//  request.nav_request.lat = 1.0f;
//  pb_ostream_t stream = pb_ostream_from_buffer(buffer, sizeof(buffer));
  //bool status = pb_encode(&stream, SeaRequest_fields, &request);
 
}

SeaRequest request = SeaRequest_init_zero;
uint8_t buffer[128];
uint8_t buffer_pos = 0;
uint8_t command_size = 0;

bool readCommand() {
  while (blueToothSerial.available() > 0) {
    if (command_size <= 0) {
      buffer_pos = 0;
      command_size = blueToothSerial.read();
      Serial.print("Reading request of size ");
      Serial.print(command_size);
      Serial.println();
    } else {
      buffer[buffer_pos] = blueToothSerial.read();
      buffer_pos++;
    }
  }
  if (command_size > 0 && buffer_pos >= command_size) {
    pb_istream_t stream = pb_istream_from_buffer(buffer, command_size);
    bool status = pb_decode(&stream, SeaRequest_fields, &request);
    if (!status) {
      Serial.println("Failed to decode proto message");
    }
    // Not decoding anymore, message ready.
    command_size = 0;
    return true;
  }
  return false;
}

void loop () {
  if (readCommand()) {
     Serial.print("Got command lat/lng ");
     Serial.print(request.nav_request.lat);
     Serial.println();
     delay(50);
     //blueToothSerial.println("hello world");
  }
}
