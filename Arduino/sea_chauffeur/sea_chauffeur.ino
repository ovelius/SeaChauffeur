#include <SoftwareSerial.h>
#include "TinyGPS++.h"
#include "pb_common.h"
#include "pb.h"
#include "pb_encode.h"
#include "pb_decode.h"
#include "messages.pb.h"

SoftwareSerial blueToothSerial(2, 3);
TinyGPSPlus gps;

SeaResponse response = SeaResponse_init_zero;

void setup() {
  Serial.begin(9600);
  blueToothSerial.begin(9600);
}

SeaRequest request = SeaRequest_init_zero;
uint8_t buffer[128];
uint8_t buffer_pos = 0;
uint8_t command_size = 0;

bool readCommand() {
  while (blueToothSerial.available() > 0) {
    if (command_size <= 0) {
      buffer_pos = 0;
      request = SeaRequest_init_zero;
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
     Serial.print("Got command lat ");
     Serial.print(request.nav_request.location.lat, 8);
     Serial.print(" lng ");
     Serial.print(request.nav_request.location.lng, 8);
     Serial.println();

     uint8_t response_buffer[128];
     response = SeaResponse_init_zero;
     response.response_code = ResponseCode_OK;
     response.current_destination.lat = 123.0f;
     response.current_destination.lng = 321.0f;
     pb_ostream_t ostream = pb_ostream_from_buffer(response_buffer, sizeof(response_buffer));
     // Can't encode optional fields here...
     bool status = pb_encode_delimited(&ostream, SeaResponse_fields, &response);
     if (!status) {
       Serial.println("Failed to encode proto response message! Error is: ");
       Serial.print(ostream.errmsg);
       Serial.println();
       return;
     }

     for (int i = 0; i < ostream.bytes_written; i++) {
       blueToothSerial.write(response_buffer[i]);
     }
     Serial.print("Wrote response of size ");
     Serial.print(ostream.bytes_written);
     Serial.println(" bytes.");
  }
}
