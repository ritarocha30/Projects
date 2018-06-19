/** **************************** **/
/**								 **/
/** 2º Projeto de IAED 2016/2017 **/
/**	        Grupo nº 27			 **/
/**								 **/
/**	   Ana Rita Rocha - 79779	 **/
/**	   Andre Batista  - 80908	 **/
/**                              **/
/** **************************** **/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "avl.h"
#include "item.h"

/* Prototipos das funcoes */
link funcaoA(link h, Key* ptr_maxCode, int* ptr_maxCount, int* ptr_num, int* ptr_procura);
void funcaoL(link h);
void funcaoM(link h, Key* ptr_maxCode, int* ptr_maxCount, int* ptr_inc, int* ptr_num, int* ptr_procura);
link funcaoR(link h, Key* ptr_maxCode, int* ptr_maxCount, int* ptr_num, int* ptr_procura);
void funcaoX(link h, Key* ptr_maxCode, int* ptr_maxCount, int* ptr_num, int* ptr_procura);

/* MAIN */
int main() {
	
	link h;
	STinit(&h);

    Key maxCode = ffffffff; /* Variavel que guarda o codigo do produto com maior numero de unidades */
	Key* ptr_maxCode = &maxCode;

	int maxCount = 0; /* Variavel que guarda o numero de unidades do produto com maior numero de unidades */
	int* ptr_maxCount = &maxCount;

	int inc; /* Variavel que incrementa na funcaM() por cada No da Arvore atravessado */
	int* ptr_inc = &inc;

	int num = 0; /* Variavel que guarda o numero de Nos da Arvore e consequentemente o numero de chaves diferentes no programa */
	int* ptr_num = &num;

	int procura = 0; /* Variavel que ativa a opcao de procura na Arvore se estiver a 1 */
	int* ptr_procura = &procura;

	char command;

    while(1){
    	command = getchar(); /* Le 'command' */
		switch(command) {
		    case 'a':
		    	h = funcaoA(h, ptr_maxCode, ptr_maxCount, ptr_num, ptr_procura);
		   		getchar();
		      	break;
		    case 'l':
		   		funcaoL(h);
		   		getchar();
		      	break;
		    case 'm':
		    	/* Procura na Arvore quando a opcao de procura esta ativa */
		    	if ((eq(maxCode, ffffffff) && eq(maxCount, 0)) || (*ptr_procura) == 1){ /* Opcao de procura fica desativada para que possa entrar */
			    	procura = 0;		  												/* na funcao update_max() que compara os productos para   */
			    	inc = 0;			  												/* achar o producto com maior numero de unidades em stock */
			    	funcaoM(h, ptr_maxCode, ptr_maxCount, ptr_inc, ptr_num, ptr_procura);
			   	} else
					printf("%08lx %d\n", (*ptr_maxCode), (*ptr_maxCount)); /* Para que o codigo do produto seja sempre impresso */
 												   						   /* com tamanho 8, mesmo que o seu valor hexadecimal  */
												   						   /* nao ocupe 8 casas, utiliza-se "%08lx" no printf() */
			   	getchar();
		      	break;
		    case 'r':
		      	h = funcaoR(h, ptr_maxCode, ptr_maxCount, ptr_num, ptr_procura);
		   		getchar();
		      	break;
		    case 'x':
		    	funcaoX(h, ptr_maxCode, ptr_maxCount, ptr_num, ptr_procura);
		       	return EXIT_SUCCESS; /* Termina o programa com sucesso */
		    default:
		    	while(getchar() != '\n');
		   		printf("ERRO: Comando desconhecido\n");
		}
	}
	return EXIT_FAILURE; /* Algo correu mal */
}

/* 
funcaoA - Executa as operações para o comando 'a'
*/
link funcaoA(link h, Key* ptr_maxCode, int* ptr_maxCount, int* ptr_num, int* ptr_procura){
	Key code; 
	int count;
	scanf("%lx %d", &code, &count);
	Item item = NEWitem(code, count);
	STinsert(&h, item, ptr_maxCode, ptr_maxCount, ptr_num, ptr_procura);
	return h;
}

/* 
funcaoL - Executa as operações para o comando 'l'
 */
void funcaoL(link h){
	STsort(h, visit);
}

/* 
funcaoM - Executa as operações para o comando 'm'
*/
void funcaoM(link h, Key* ptr_maxCode, int* ptr_maxCount, int* ptr_inc, int* ptr_num, int* ptr_procura){
	if (h == NULL)
		return;
	update_max(h, ptr_maxCode, ptr_maxCount, ptr_procura);
	(*ptr_inc)++;
	funcaoM(h->l, ptr_maxCode, ptr_maxCount, ptr_inc, ptr_num, ptr_procura);
	funcaoM(h->r, ptr_maxCode, ptr_maxCount, ptr_inc, ptr_num, ptr_procura);
	if ((*ptr_inc) == (*ptr_num)){
		printf("%08lx %d\n", (*ptr_maxCode), (*ptr_maxCount));
		(*ptr_inc)++; /* Como se trata de uma funcao recursiva, atraves do incremento */
					  /* de uma variavel auxiliar que rege a entrada nesta porcao de  */
					  /* codigo, asseguramos que o printf() so se chama uma vez       */
	}
}

/* 
funcaoR - Executa as operações para o comando 'r'
*/
link funcaoR(link h, Key* ptr_maxCode, int* ptr_maxCount, int* ptr_num, int* ptr_procura){
	Key code;
	scanf("%lx", &code);
	STdelete(&h, code, ptr_maxCode, ptr_maxCount, ptr_num, ptr_procura);
	return h;
}

/* 
funcaoX -  Executa as operações para o comando 'x'
*/
void funcaoX(link h, Key* ptr_maxCode, int* ptr_maxCount, int* ptr_num, int* ptr_procura){
	printf("%d\n", (*ptr_num));
	STfree(&h, ptr_maxCode, ptr_maxCount, ptr_num, ptr_procura);
}