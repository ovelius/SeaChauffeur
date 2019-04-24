#ifndef _NAV_LIB_h
#define _NAV_LIB_h

typedef struct {
	double lat;
	double lng;
	double heading;
	double knots;
} GpsData;

typedef struct {
} NavState;

typedef struct {
} SteerCommand;

  SteerCommand newLocationData(NavState* navState, GpsData* GpsData);
  SteerCommand newDestinationData(NavState* navState);
#endif