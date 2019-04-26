#include <stdbool.h>

#ifndef _NAV_LIB_H
#define _NAV_LIB_H

#define GPS_DATA_CACHE 10
#define COURSE_CORRECTION_MILLIS 5000
#define COURSE_DEVIATION_THRESHOLD 1.0f
#define COURSE_DEVIATION_POWER_MULTIPLIER 100
#define COURSE_DEVIATION_TIME_MULTIPLIER 30

#ifdef __cplusplus
extern "C" {
#endif

struct NavLibConfiguration {
  short millis_duration_per_degree_second;
  unsigned char power_low_mode;
  unsigned char power_medium_mode;
  float knots_to_lock_course;
};

extern struct NavLibConfiguration nav_lib_configuration;

struct _GpsData {
	float lat;
	float lng;
	float course;
	// Meters per second because SI :D
	float meters_per_second;
	// Time since startup the data was accquired.
	unsigned long time;
};

struct _CurrentDestination {
	// TODO: Add lat/lng here?
	// Course we are steering after.
	float course;
};

typedef struct _GpsData GpsData;
typedef struct _CurrentDestination CurrentDestination;

struct _NavState {
  // Keep up to GPS_DATA_CACHE seconds of historical data.
  GpsData gps_data[GPS_DATA_CACHE];
  // The point in time which new commands can be issued. 
  unsigned long next_command_possible;
  CurrentDestination current_destination;
};


enum SteerDirection {
	NoSteer,
	Left,
	Right,
};

struct _SteerCommand {
	// Motor steering direction.
	enum SteerDirection direction;
	// How long the steer motor should engage. 
	int millis_duration;
	// 0 to 255
	unsigned char power;
	// Reverse the steer after the millis. -1 means the steer should not be reversed.	
	int reverse_duration;
};

typedef struct _NavState NavState;
typedef struct _SteerCommand SteerCommand;


// Should run at most once a second. We assume location data is uniformly distributed.
SteerCommand newLocationData(NavState* navState, GpsData* GpsData);
SteerCommand newDestinationData(NavState* navState);
NavState initNavState();

#ifdef __cplusplus
}
#endif

#endif