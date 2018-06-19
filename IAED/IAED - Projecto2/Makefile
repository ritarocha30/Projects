CC = gcc
CFLAGS = -Wall -pedantic -ansi -g
EXECS = proj2

OBJECTS1= proj2.o avl.o item.o

all: ${EXECS}

proj2: ${OBJECTS1} avl.h item.h
	${CC} ${CFLAGS} -o $@ ${OBJECTS1}

clean:
	rm -f *.o ${EXECS}
