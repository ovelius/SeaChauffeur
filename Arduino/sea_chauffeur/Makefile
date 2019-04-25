HEADERS = nav_lib.h bl.h
OBJECTS = nav_lib.o nav_lib_test.o

default: program


%.o: %.c $(HEADERS)
	gcc -c $< -o $@



program: $(OBJECTS)
	gcc $(OBJECTS) -lcheck -o $@

clean:
	-rm -f $(OBJECTS)
	-rm -f program