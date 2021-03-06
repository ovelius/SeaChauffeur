#include <SoftwareSerial.h>
#include "TinyGPS++.h"
#include "pb_common.h"
#include "pb.h"
#include "pb_encode.h"
#include "nav_lib.h"
#include "pb_decode.h"
#include "messages.pb.h"

SoftwareSerial blueToothSerial(2, 3);
TinyGPSPlus gps;

#define enA 6
#define in1 5
#define in2 7

NavState state;

void setup() {
  Serial.begin(9600);
  pinMode(enA, OUTPUT);
  pinMode(in1, OUTPUT);
  pinMode(in2, OUTPUT);
  blueToothSerial.begin(9600);
  state = initNavState();

  nav_lib_configuration.millis_duration_per_degree_second = Configuration_millis_duration_per_degree_second_default;
  nav_lib_configuration.power_low_mode = Configuration_power_low_mode_default;
  nav_lib_configuration.power_medium_mode = Configuration_power_medium_mode_default;
  nav_lib_configuration.knots_to_lock_course = Configuration_knots_to_lock_course_default;

  // This sequence is to roughly center things. 
  SteerCommand cmd;
  cmd.power = 255;
  cmd.direction = Right;
  cmd.millis_duration = 8000;

  steer(&cmd);

  cmd.direction = Left;
  cmd.millis_duration = 2800;

  steer(&cmd);
}

uint8_t buffer[32];
uint8_t buffer_pos = 0;
uint8_t command_size = 0;

bool readCommand(SeaRequest* request) {
  while (blueToothSerial.available() > 0) {
    if (command_size <= 0) {
      buffer_pos = 0;
      *request = SeaRequest_init_zero;
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
    bool status = pb_decode(&stream, SeaRequest_fields, request);
    if (!status) {
      Serial.println("Failed to decode proto message");
    }
    // Not decoding anymore, message ready.
    command_size = 0;
    return true;
  }
  return false;
}

void loop2 () {
  GpsData gpsData;
  gpsData.time = millis();
  gpsData.course = 34.0f;

 
  SteerCommand cmd = newLocationData(&state, &gpsData);

  if (cmd.direction != NoSteer) {
    steer(&cmd);
  }
}

void steer(SteerCommand* cmd) {
  if (cmd->direction == Left) {
    digitalWrite(in1, LOW);
    digitalWrite(in2, HIGH);  
  } else  if (cmd->direction == Right) {
    digitalWrite(in1, HIGH);
    digitalWrite(in2, LOW);   
  }

  analogWrite(enA, cmd->power);
  delay(cmd->millis_duration);

  digitalWrite(enA, LOW);
  digitalWrite(in1, LOW);
  digitalWrite(in2, LOW);  
}

void updateNavLibConfig(SeaRequest* request) {
  nav_lib_configuration.millis_duration_per_degree_second = request->update_configuration.millis_duration_per_degree_second;
  nav_lib_configuration.power_low_mode = request->update_configuration.power_low_mode;
  nav_lib_configuration.power_medium_mode = request->update_configuration.power_medium_mode;
  nav_lib_configuration.knots_to_lock_course = request->update_configuration.knots_to_lock_course;
}

void populateCommonResponseFields(SeaResponse* response) {
     response->has_current_destination = true;
     response->current_destination.lat = current_destination.lat;
     response->current_destination.lng = current_destination.lng;
     response->has_current_location = true;
     // TODO: Sent back actual location here.
     response->current_location.lat = current_destination.lat + 0.005f;
     response->current_location.lng = current_destination.lng + 0.003f;
}

void loop () {
  // TODO: Reads GPS data here...
  if (gps.location.isUpdated()) {
    // Feed lib with location here...
  }
  SeaRequest request = SeaRequest_init_zero;
  if (readCommand(&request)) {
     if (request.has_update_configuration) {
       Serial.print(F("Got configuration update request"));
       updateNavLibConfig(&request);
     } else if (request.has_trim_request) {
      SteerCommand cmd;
      cmd.direction = Left;
      if (request.trim_request.direction == SteeringDirection_RIGHT) {
        cmd.direction = Right; 
      }
      cmd.power = nav_lib_configuration.power_medium_mode;
      cmd.millis_duration = request.trim_request.millis;
      Serial.print(F("Got trim request of "));
      Serial.print(cmd.millis_duration);
      Serial.println();
      steer(&cmd);
     } else if (request.has_nav_request) {
       current_destination.lat = request.nav_request.location.lat;
       current_destination.lng = request.nav_request.location.lng;
     }
  
       SeaResponse response;
       uint8_t response_buffer[32];
       response = SeaResponse_init_zero;
       response.response_code = ResponseCode_OK;
  
       populateCommonResponseFields(&response);
  
       pb_ostream_t ostream = pb_ostream_from_buffer(response_buffer, sizeof(response_buffer));
       bool status = pb_encode_delimited(&ostream, SeaResponse_fields, &response);
     if (!status) {
       Serial.println(F("Failed to enc response message! Error: "));
       Serial.print(ostream.errmsg);
       Serial.println();
       return;
     }

     for (int i = 0; i < ostream.bytes_written; i++) {
       blueToothSerial.write(response_buffer[i]);
     }
     Serial.print(F("Wrote response of size "));
     Serial.print(ostream.bytes_written);
     Serial.println(F(" bytes."));
  }
}
