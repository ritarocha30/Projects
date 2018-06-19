#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "avl.h"

int height(link h){ 
	if (h == NULL) 
		return 0;
	return h->height;
}

void update_height(link h){
	int height_left = height(h->l);
	int height_right = height(h->r);
	h->height = height_left > height_right ? height_left + 1 : height_right + 1;
} 

link rotL(link h){
	link x = h->r;
	h->r = x->l;
	x->l = h;
	update_height(h);
	update_height(x);
	return x;
}

link rotR(link h){
	link x = h->l;
	h->l = x->r;
	x->r = h;
	update_height(h);
	update_height(x);
	return x;
} 

link rotLR(link h){
	if (h == NULL) 
		return h;
	h->l = rotL(h->l);
	return rotR(h);
}

link rotRL(link h){
	if (h == NULL) 
		return h;
	h->r = rotR(h->r);
	return rotL(h);
}

int Balance(link h){
	if(h == NULL)
		return 0;
	return height(h->l)-height(h->r);
} 

link AVLbalance(link h){
	int balanceFactor;
	if (h == NULL)
		return h;
	balanceFactor = Balance(h);
	if(balanceFactor > 1){
		if (Balance(h->l) >= 0)
			h = rotR(h);
		else
			h = rotLR(h);
	} else if(balanceFactor < -1){
		if (Balance(h->r) <= 0)
			h = rotL(h);
		else
			h = rotRL(h);
	} else
		update_height(h);
	return h;
} 

link max(link h){
	if (h == NULL || h->r == NULL) 
		return h;
	else
		return max(h->r);
}

link NEWnode(Item item, link l, link r, Key* ptr_maxCode, int* ptr_maxCount, int* ptr_procura){
	link x = (link)malloc(sizeof(struct STnode));
	x->item = item;
	x->l = l;
	x->r = r;
	x->height = 1;
	/* Apos criar um novo No na Arvore, verifica se tem mais unidades em stock do que o producto que atualmente ocupa esse lugar */
	update_max(x, ptr_maxCode, ptr_maxCount, ptr_procura);
	return x;
}

void STinit(link *h){
	*h = NULL;
}

void visit (Item item){
	printf("%08lx %d\n", item->code, item->count); /* Para que o codigo do produto seja sempre impresso */
} 												   /* com tamanho 8, mesmo que o seu valor hexadecimal  */
												   /* nao ocupe 8 casas, utiliza-se "%08lx" no printf() */
void sortR(link h, void (*visit)(Item)){
	if (h == NULL)
		return;
	sortR(h->l, visit);
	visit(h->item);
	sortR(h->r, visit);
}

void STsort(link h, void (*visit)(Item))
{
	sortR(h, visit);
}

link freeR(link h, Key* ptr_maxCode, int* ptr_maxCount, int* ptr_num, int* ptr_procura){
 if (h == NULL)
 	return h;
 h->l = freeR(h->l, ptr_maxCode, ptr_maxCount, ptr_num, ptr_procura);
 h->r = freeR(h->r, ptr_maxCode, ptr_maxCount, ptr_num, ptr_procura);
 return deleteR(h, key(h->item), ptr_maxCode, ptr_maxCount, ptr_num, ptr_procura);
}

void STfree(link *h, Key* ptr_maxCode, int* ptr_maxCount, int* ptr_num, int* ptr_procura){
	*h = freeR(*h, ptr_maxCode, ptr_maxCount, ptr_num, ptr_procura);
}

link insertR(link h, Item item, Key* ptr_maxCode, int* ptr_maxCount, int* ptr_num, int* ptr_procura){
	if(h != NULL && eq(key(h->item), key(item))){
		h->item->count += item->count;
		
		/* Apos atualizar o numero de unidades de um producto ja existente na Arvore, verifica */
		/* se tem mais unidades em stock do que o producto que atualmente ocupa esse lugar     */
		update_max(h, ptr_maxCode, ptr_maxCount, ptr_procura);
		
		if (h->item->count < 0) /* Se o numero de unidades decrementadas for maior */
			h->item->count = 0; /* que o numero de unidades que existiam, o numero */
								/* de unidades desse produto passa para zero       */

		/* Se forem decrementadas unidades ao que atualmente tem o maior numero de unidades   */
		/* em stock, tem de se procurar na Arvore para sabermos o produto com maior numero    */
		/* de unidades, daí se inicializar o valor do maximo e se "ativar" a opcao de procura */
		if((item->count < 0) && eq(key(item), (*ptr_maxCode))){ 
			(*ptr_maxCode) = ffffffff;
			(*ptr_maxCount) = 0;
			(*ptr_procura) = 1;

		}

		/* Quando o item ja existe na Arvore nao se insere, logo a memoria alocada tem de ser libertada */
		FREEitem(item);	
		return h;
	}
	if (h == NULL){
		(*ptr_num)++; /* Cada vez que uma chave diferente se insere no programa, o numero de chaves incrementa uma unidade */
		return NEWnode(item, NULL, NULL, ptr_maxCode, ptr_maxCount, ptr_procura);
	}
	if (less(key(item), key(h->item)))
		h->l = insertR(h->l, item, ptr_maxCode, ptr_maxCount, ptr_num, ptr_procura);
	else
		h->r = insertR(h->r, item, ptr_maxCode, ptr_maxCount, ptr_num, ptr_procura);
	h = AVLbalance(h);
	return h;
} 

void STinsert(link* h, Item item, Key* ptr_maxCode, int* ptr_maxCount, int* ptr_num, int* ptr_procura){
	*h = insertR(*h, item, ptr_maxCode, ptr_maxCount, ptr_num, ptr_procura);
}

link deleteR(link h, Key code, Key* ptr_maxCode, int* ptr_maxCount, int* ptr_num, int* ptr_procura){
	if (h == NULL){
		return h;
	} else if (less(code, key(h->item))){
		h->l = deleteR(h->l, code, ptr_maxCode, ptr_maxCount, ptr_num, ptr_procura);
	} else if (less(key(h->item), code)){
		h->r = deleteR(h->r, code, ptr_maxCode, ptr_maxCount, ptr_num, ptr_procura) ;
	} else{
		if (h->l != NULL && h->r != NULL){
			link aux = max(h->l);
			{
				Item x; 
				x = h->item; 
				h->item = aux->item; 
				aux->item = x;
			}
			h->l = deleteR(h->l, key(aux->item), ptr_maxCode, ptr_maxCount, ptr_num, ptr_procura);
		} else {
			link aux = h;
			if (h->l == NULL && h->r == NULL) 
				h = NULL;
			else if (h->l == NULL) 
					h = h->r;
				else 
					h = h->l;

			/* Se for removido o producto que atualmente tem o maior numero de unidades em stock, */
			/* tem de se procurar na Arvore para sabermos o novo produto com maior numero de      */
			/* unidades, daí se inicializar o valor do maximo e se "ativar" a opcao de procura    */	
			if(eq(key(aux->item), (*ptr_maxCode)) && eq(key(aux->item), (*ptr_maxCode))){
				(*ptr_maxCode) = ffffffff;
				(*ptr_maxCount) = 0;
				(*ptr_procura) = 1;
			}

			/* A memoria alocada para o item e para o No da Arvore, que esta a ser removido, fica livre */
			FREEitem(aux->item);
			free(aux);
			(*ptr_num)--; /* Cada vez que uma chave se remove do programa, o numero de chaves decrementa uma unidade */
		}
	}
	h = AVLbalance(h);
	return h;
}

void STdelete(link* h, Key code, Key* ptr_maxCode, int* ptr_maxCount, int* ptr_num, int* ptr_procura){
	*h = deleteR(*h, code, ptr_maxCode, ptr_maxCount, ptr_num, ptr_procura);
}

/* Nota: Utiliza-se "<=" no 3º if() para abranger o caso em que o codigo do producto   */
/* corresponde a 'ffffffff', visto a variavel maxCode ser inicializada com este valor  */
void update_max(link h, Key* ptr_maxCode, int* ptr_maxCount, int* ptr_procura){
	/* Se a opcao de procura nao estiver ativa, verifica se o producto    */
	/* tem maior numero de unidades que o que atualmente ocupa esse lugar */
	if((*ptr_procura) == 0){ 
		if (less((*ptr_maxCount), h->item->count)){ /* O producto tem maior numero de unidades*/
			(*ptr_maxCode) = key(h->item);
			(*ptr_maxCount) = h->item->count;
		} else if(eq((*ptr_maxCount), h->item->count) && key(h->item) <= (*ptr_maxCode)){ /* O producto tem igual numero de unidades */
			(*ptr_maxCode) = key(h->item);												  /* mas menor chave lexicografica.          */
			(*ptr_maxCount) = h->item->count;
		}
	}	
}