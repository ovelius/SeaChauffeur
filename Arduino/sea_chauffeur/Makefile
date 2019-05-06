HEADERS = nav_lib.h queue_lib.h bl.h
OBJECTS = nav_lib.o queue_lib.o nav_lib_test.o
CFLAGS="-D _UNIT_TEST"

default: program


%.o: %.c $(HEADERS)
	gcc -c $< -o $@

program: $(OBJECTS)
	gcc $(OBJECTS) -lcheck -o $@

clean:
	-rm -f $(OBJECTS)
	-rm -f program