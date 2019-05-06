#if defined _UNIT_TEST

#include <check.h>
#include <stdio.h>
#include <math.h>
#include "nav_lib.h"
#include "queue_lib.h"

START_TEST (base_feed_location)
{
	NavState state = initNavState();
	for (int i = 0; i < GPS_DATA_CACHE; i++) {
		ck_assert(!state.gps_data[i].time);
	}
	
	GpsData gpsData;
	gpsData.time = 123;
	
	newLocationData(&state, &gpsData);
	
	ck_assert_int_eq(state.gps_data[0].time, gpsData.time);
	
	gpsData.time = 124;
	
	SteerCommand c = newLocationData(&state, &gpsData);
	ck_assert(c.direction == NoSteer);
	
	ck_assert_int_eq(state.gps_data[0].time, 124);
	ck_assert_int_eq(state.gps_data[1].time, 123);
	
	for (int i = 0; i < GPS_DATA_CACHE * 2; i++) {
	  gpsData.time = gpsData.time + i;
	  ck_assert(newLocationData(&state, &gpsData).direction == NoSteer);		
	}
	ck_assert_int_eq(state.gps_data[0].time, gpsData.time);
}
END_TEST

// Forward declare helper method.
float calculateCourseDeviation(NavState* navState, int time_period_millis);

START_TEST (base_course_correction)
{
	NavState state = initNavState();
	ck_assert(calculateCourseDeviation(&state, COURSE_CORRECTION_MILLIS) == 0.0f);
	
    GpsData gpsData;
	gpsData.time = 3000;
	gpsData.course = 10.f;
	
	// Feed location data.
	state.gps_data[0] = gpsData;
	 
	ck_assert(calculateCourseDeviation(&state, COURSE_CORRECTION_MILLIS) == 0.0f);

    gpsData.time = 2000;
	gpsData.course = 9.f;	
	state.gps_data[1] = gpsData;
	
	ck_assert(calculateCourseDeviation(&state, COURSE_CORRECTION_MILLIS) == 0.0f);
	
	gpsData.time = 1000;
	gpsData.course = 8.f;	
	state.gps_data[2] = gpsData;
	
	fprintf(stderr, "Test course data correction is %f\n", 
		calculateCourseDeviation(&state, COURSE_CORRECTION_MILLIS));
	// Over the last 2 seconds we've slipped about 3.0f off course.
	ck_assert(fabs((calculateCourseDeviation(&state, COURSE_CORRECTION_MILLIS) - 1.5f)) < 0.01f);

    // Insert super old record, not counted.	
	state.gps_data[3].time = gpsData.time - COURSE_CORRECTION_MILLIS - 1;
	state.gps_data[3].course = 7.f;
	
	fprintf(stderr, "Test course data correction with too old item is %f\n",
		calculateCourseDeviation(&state, COURSE_CORRECTION_MILLIS));
	ck_assert(fabs((calculateCourseDeviation(&state, COURSE_CORRECTION_MILLIS) - 1.5f)) < 0.01f);
	
    state.gps_data[3].time = gpsData.time + 1;
	fprintf(stderr, "Test course data is %f\n",
		calculateCourseDeviation(&state, COURSE_CORRECTION_MILLIS));
	ck_assert(fabs((calculateCourseDeviation(&state, COURSE_CORRECTION_MILLIS) - 3.0f)) < 0.01f);
	
	gpsData.time = 2;
	// Item 1 in the array now goes the other way - reducing our course issue slightly.
	gpsData.course = 10.5f;	
	state.gps_data[1] = gpsData;
	
	fprintf(stderr, "Test course data is %f\n", 
		calculateCourseDeviation(&state, COURSE_CORRECTION_MILLIS));
	ck_assert(fabs((calculateCourseDeviation(&state, COURSE_CORRECTION_MILLIS) - 2.25f)) < 0.01f);
}
END_TEST


START_TEST (queue_event_test)
{
	QuedEvent event;
	event.time = 123;
	ck_assert(queEvent(event));
    QuedEvent unQued;
	// Not enough time has passed.
    ck_assert(!unqueEvent(1, &unQued));
	// Now it has.
	ck_assert(unqueEvent(123, &unQued));
	// TODO: Complete this test.
}
END_TEST

int main(void) {
    Suite *s1 = suite_create("Core");
    TCase *tc1_1 = tcase_create("Core");
    SRunner *sr = srunner_create(s1);
    int nf;

    suite_add_tcase(s1, tc1_1);
    tcase_add_test(tc1_1, base_feed_location);
	tcase_add_test(tc1_1, base_course_correction);
	tcase_add_test(tc1_1, queue_event_test);

    srunner_run_all(sr, CK_ENV);
    nf = srunner_ntests_failed(sr);
    srunner_free(sr);

    return nf == 0 ? 0 : 1;
}

#endif