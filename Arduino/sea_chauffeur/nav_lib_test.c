#include <check.h>
#include "nav_lib.h"

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

int main(void) {
    Suite *s1 = suite_create("Core");
    TCase *tc1_1 = tcase_create("Core");
    SRunner *sr = srunner_create(s1);
    int nf;

    suite_add_tcase(s1, tc1_1);
    tcase_add_test(tc1_1, base_feed_location);

    srunner_run_all(sr, CK_ENV);
    nf = srunner_ntests_failed(sr);
    srunner_free(sr);

    return nf == 0 ? 0 : 1;
}

