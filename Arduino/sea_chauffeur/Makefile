HEADERS = nav_lib.h bl.h
OBJECTS = nav_lib.o nav_lib_test.o
CFLAGS="-D _WIN32"

default: program


%.o: %.c $(HEADERS)
	gcc -c $< -o $@

program: $(OBJECTS)
	gcc $(OBJECTS) -lcheck -o $@

clean:
	-rm -f $(OBJECTS)
	-rm -f program