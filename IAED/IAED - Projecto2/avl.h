#ifndef AVL_H
#define AVL_H

#include "item.h"

/* Estrutura de Dados - Arvore Binaria de Pesquisa Equilibrada */

typedef struct STnode {
	Item item;
	struct STnode *l;
	struct STnode *r;
	int height;
} *link;

/* Prototipos das funcoes */
int height(link h);
void update_height(link h);
link rotL(link h);
link rotR(link h);
link rotLR(link h);
link rotRL(link h);
int Balance(link h);
link AVLbalance(link h);
link max(link h);
link NEWnode(Item item, link l, link r, Key* ptr_maxCode, int* ptr_maxCount, int* ptr_procura);
void STinit(link *h);
void visit(Item item);
void sortR(link h, void (*visit)(Item));
void STsort(link h, void (*visit)(Item));
link freeR(link h, Key* ptr_maxCode, int* ptr_maxCount, int* ptr_num, int* ptr_procura);
void STfree(link *h, Key* ptr_maxCode, int* ptr_maxCount, int* ptr_num, int* ptr_procura);
link insertR(link h, Item item, Key* ptr_maxCode, int* ptr_maxCount, int* ptr_num, int* ptr_procura);
void STinsert(link *h, Item item, Key* ptr_maxCode, int* ptr_maxCount, int* ptr_num, int* ptr_procura);
link deleteR(link h, Key code, Key* ptr_maxCode, int* ptr_maxCount, int* ptr_num, int* ptr_procura);
void STdelete(link *h, Key code, Key* ptr_maxCode, int* ptr_maxCount, int* ptr_num, int* ptr_procura);
void update_max(link h, Key* ptr_maxCode, int* ptr_maxCount, int* ptr_procura);

#endif

