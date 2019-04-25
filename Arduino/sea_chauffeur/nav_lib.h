#include <stdbool.h>
//  #include "bl.h"

#ifndef _NAV_LIB_H
#define _NAV_LIB_H

#define GPS_DATA_CACHE 20

struct _GpsData {
	float lat;
	float lng;
	float heading;
	float knots;
	// Time since startup the data was accquired.
	unsigned long time;
};


struct _NavState {
  // Keep up to 20 seconds of historical data.
  struct _GpsData gps_data[GPS_DATA_CACHE];
};


enum SteerDirection {
	NoSteer,
	Left,
	Right,
};

struct _SteerCommand {
	// Motor steering direction.
	enum SteerDirection direction;
	// How long the steer should be active. 
	// -1 means its a one time adjustment.
	int millis_duration;
	// 0.1 to 1.0.
	float power;
};

typedef struct _NavState NavState;
typedef struct _SteerCommand SteerCommand;
typedef struct _GpsData GpsData;

// Should run at most once a second.
SteerCommand newLocationData(NavState* navState, GpsData* GpsData);
SteerCommand newDestinationData(NavState* navState);
NavState initNavState();
#endif