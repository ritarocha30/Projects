#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "item.h"

Item NEWitem(Key code, int count) {
	Item new = (Item)malloc(sizeof(struct product));
	new->code = code;
	new->count = count;
	return new;
}

void FREEitem(Item a) {
	free(a);
} 