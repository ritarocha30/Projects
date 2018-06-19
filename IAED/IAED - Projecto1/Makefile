CC = gcc
CFLAGS = -Wall -pedantic -ansi -g
EXECS = proj1

OBJECTS1= proj1.o funcoes.o

all: ${EXECS}

proj1: ${OBJECTS1} funcoes.h
	${CC} ${CFLAGS} -o $@ ${OBJECTS1}

clean:
	rm -f *.o ${EXECS}
