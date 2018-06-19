#ifndef ITEM_H
#define ITEM_H

#define key(A) (A->code)
#define less(A, B) (A < B)
#define eq(A,B) (A == B)
#define ffffffff 4294967295 /* 'ffffffff' na base hexadecimal é 4294967295 na base decimal */

typedef unsigned long int Key;

struct product{
	Key code;
	int count;
};

typedef struct product* Item;

Item NEWitem(Key code, int count);
void FREEitem(Item a);

#endif