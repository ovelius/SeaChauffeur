#include <stdbool.h>
#include <stdio.h>
#include <math.h>
#include <assert.h> 
#include "nav_lib.h"

struct NavLibConfiguration nav_lib_configuration;

float calculateCourseDeviation(NavState* navState, int time_period_millis) {
	unsigned long time_reference = navState->gps_data[0].time;
	unsigned long oldest = time_reference;
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
		oldest = item.time;
		samples++;
	}
	
	// Not enough samples.
	if (samples <= 2) {
		return 0.0f;
	}
	float timeDiff = (time_reference - oldest) / 1000.0f;
	// This should be in degrees per second.
	return course_deviation / timeDiff;
}

void pushLocationData(NavState* navState, GpsData gpsData, int index) {
	// Out of bounds or no data, give up.
	if (index >= GPS_DATA_CACHE || !gpsData.time) {
		return;
	}
	GpsData t = navState->gps_data[index];
	// Data we are trying to insert must have a newer timestamp than existing data.
	assert(gpsData.time >= t.time);
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
		command.power = fabs(fmin(COURSE_DEVIATION_POWER_MULTIPLIER * course_deviation, 255));
		command.millis_duration = fabs(COURSE_DEVIATION_TIME_MULTIPLIER * course_deviation);
		
		navState->next_command_possible = current_time + command.millis_duration + COURSE_CORRECTION_MILLIS/2;
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
	s.current_destination.course = -1;
	return s;
}