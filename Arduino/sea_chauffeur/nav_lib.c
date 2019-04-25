#include <stdbool.h>
#include "nav_lib.h"

void pushLocationData(NavState* navState, GpsData gpsData, int index) {
	// Out of bounds or no data, give up.
	if (index >= GPS_DATA_CACHE || !gpsData.time) {
		return;
	}
	GpsData t = navState->gps_data[index];
	navState->gps_data[index] = gpsData;
	// Continue pushing downwards.
	pushLocationData(navState, t, index + 1);
}

SteerCommand newLocationData(NavState* navState, GpsData* gpsData) {
	// First push the new data to the location history stack.
	pushLocationData(navState, *gpsData, 0);
	
	SteerCommand command;
	command.direction = NoSteer;
	return command;
}

SteerCommand newDestinationData(NavState* navState) {
}
NavState initNavState() {
	NavState s;
	for (int i = 0; i < GPS_DATA_CACHE; i++) {
		s.gps_data[i].time = 0;
	}
	return s;
}