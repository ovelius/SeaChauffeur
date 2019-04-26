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

int enA = 10;
int in1 = 5;
int in2 = 6;

SeaResponse response = SeaResponse_init_zero;
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

float course = 10.0f;
float courseChanger = 0.1;

int id = 0;

void loop2 () {
  GpsData gpsData;
  gpsData.time = millis();
  gpsData.course = course;

  Serial.print(F("Simulated course as "));
  Serial.print(gpsData.course);
  Serial.print(F(" for tick "));
  Serial.print(id++);
  Serial.print(F(" at time "));
  Serial.print(gpsData.time);
  Serial.println();

  SteerCommand cmd = newLocationData(&state, &gpsData);

  delay(800);

  course += courseChanger;


  if (cmd.direction != NoSteer) {

    
    Serial.print(F("Got SteerCommand "));
    Serial.print(cmd.direction);
    Serial.print(F(" power "));
    Serial.print(cmd.power);
    Serial.print(F(" duration "));
    Serial.print(cmd.millis_duration);
    Serial.println();
    
    steer(cmd);
  }
}

void steer(SteerCommand cmd) {
  if (cmd.direction == Left) {
    digitalWrite(in1, LOW);
    digitalWrite(in2, HIGH);  
    courseChanger -= ((cmd.millis_duration * cmd.power) / 10000.0f);
  } else  if (cmd.direction == Right) {
    digitalWrite(in1, HIGH);
    digitalWrite(in2, LOW);   
    courseChanger += ((cmd.millis_duration * cmd.power) / 10000.0f);
  }

  analogWrite(enA, cmd.power);
  delay(cmd.millis_duration);

  digitalWrite(enA, LOW);
  digitalWrite(in1, LOW);
  digitalWrite(in2, LOW);  
}

void updateNavLibConfig() {
  nav_lib_configuration.millis_duration_per_degree_second = request.update_configuration.millis_duration_per_degree_second;
  nav_lib_configuration.power_low_mode = request.update_configuration.power_low_mode;
  nav_lib_configuration.power_medium_mode = request.update_configuration.power_medium_mode;
  nav_lib_configuration.knots_to_lock_course = request.update_configuration.knots_to_lock_course;
}
    
void loop () {
  if (readCommand()) {
     if (request.has_update_configuration) {
       Serial.print(F("Got configuration update request"));
       updateNavLibConfig();
       return;
     }
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
     // Can't encode optional fields here... see https://github.com/nanopb/nanopb/issues/198
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
     Serial.print(F("Wrote response of size "));
     Serial.print(ostream.bytes_written);
     Serial.println(" bytes.");
  }
}
