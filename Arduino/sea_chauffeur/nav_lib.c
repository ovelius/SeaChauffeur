#include <stdbool.h>
#include <math.h>
#include "nav_lib.h"

float calculateCourseDeviation(NavState* navState, int time_period_millis) {
	unsigned long time_reference = navState->gps_data[0].time;
	float course_reference = navState->gps_data[0].course;
	float course_deviation = 0.0f;
	int samples = 0;
	
	for (int i = 0; i < GPS_DATA_CACHE; i++) {
		GpsData item = navState->gps_data[i];
		// Item too old to be used for course correction.
		if (!item.time || (time_reference - item.time > time_period_millis)) {
			break;
		}
		course_deviation -= (item.course - course_reference);
		samples++;
	}
	
	// Not enough samples.
	if (samples <= 2) {
		return 0.0f;
	}
	
	// TODO: Should we returnt this as degrees per second?
	return course_deviation;
}

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
	unsigned long current_time = gpsData->time;
	pushLocationData(navState, *gpsData, 0);

	SteerCommand command;
	command.direction = NoSteer;

	float course_deviation = calculateCourseDeviation(navState, COURSE_CORRECTION_MILLIS);

	if (fabs(course_deviation) > COURSE_DEVIATION_THRESHOLD && current_time > navState->next_command_possible) {
		command.direction = course_deviation > 0 ? Left : Right;
		command.reverse_duration = -1;
		command.power = COURSE_DEVIATION_POWER_MULTIPLIER * course_deviation;
		command.millis_duration = COURSE_DEVIATION_TIME_MULTIPLIER * course_deviation;
		
		navState->next_command_possible = current_time + command.millis_duration;
	}
	return command;
}

SteerCommand newDestinationData(NavState* navState) {
}

NavState initNavState() {
	NavState s;
	for (int i = 0; i < GPS_DATA_CACHE; i++) {
		s.gps_data[i].time = 0;
	}
	s.next_command_possible = 0;
	return s;
}