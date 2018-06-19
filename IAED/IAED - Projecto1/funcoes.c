#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "funcoes.h"

int maiorLinha = 0;
int numMensagens = 0;
int maisOcorrencias = 0;

/*
Função Auxiliar - Inicializa com zeros um vector de inteiros.
*/
void inicializaVector(int vec[], int max) {
	int i;
	for (i = 0; i < max; i++)
		vec[i] = 0;
}

/*
Função Auxiliar - Lê linha para uma string.
				- Retorna o tamanho da string.
*/
int leLinha(char s[]) {
	int i = 0;
	char c;
	while((c = getchar()) != '\n'){
		s[i++] = c;
	}
	s[i] = '\0';
	return i;
}

/* 
FuncaoA - Adiciona uma nova mensagem ao fórum de mensagens. Recebe 
		  como argumento um inteiro i que determina a posição no vector
		  onde inserida a nova mensagem.
		- Sem output. 
*/
void funcaoA(int i) {
	scanf("%d", &vectorMensagens[i].id);
	getchar();

	/* O tamanho de cada mensagem lida é guardado para 
	verificar se é a maior frase inserida no forum até ao momento.
	Esta informação é usada na funcaoO() */
	vectorMensagens[i].tamanhoLinha = leLinha(vectorMensagens[i].texto);
  	if(vectorMensagens[i].tamanhoLinha > maiorLinha)
			maiorLinha = vectorMensagens[i].tamanhoLinha;

	/* O número de mensagens inseridas pelo utilizador 
	mais ativo é guardado. Esta informação é util na funcaoT()*/
	vectorOcorrenciasPorId[vectorMensagens[i].id]++;
	if(vectorOcorrenciasPorId[vectorMensagens[i].id] > maisOcorrencias)
			maisOcorrencias = vectorOcorrenciasPorId[vectorMensagens[i].id];
}

/* 
FuncaoL - Lista todas as mensagens existentes no fórum.
   		  As mensagens são listadas pela ordem que foram introduzidas no
   		  sistema.
   		- O output mostrará o total de mensagens (inteiro i) seguido
   		  de uma lista com o formato <user>:<frase> com as mensagens
   		  existentes. 
 */
void funcaoL(int i) {
	int j;
	printf("*TOTAL MESSAGES:%d\n", i);
	for (j = 0; j < i; j++)
		printf("%d:%s\n", vectorMensagens[j].id, vectorMensagens[j].texto);
}

/* 
FuncaoU - Lista todas as mensagens que existam referentes a um dado utilizador.
   		- As mensagens são listadas pela ordem que foram introduzidas no
   		  sistema.
   		- O output mostrará o identificador do utilizador pedido seguido
   		  de uma lista com todas as suas frases submetidas. 
*/
void funcaoU(int i) {
	int j, id;
	scanf("%d", &id);
	printf("*MESSAGES FROM USER:%d\n", id);
	for (j = 0; j < i; j++){
		if (vectorMensagens[j].id == id)
			printf("%s\n", vectorMensagens[j].texto);
	}
}

/* 
FuncaoO - Lista a frase mais longa existente no fórum.
   		- No caso de existir mais do que uma frase com o mesmo tamanho, 
   		  serão apresentadas pela ordem de introdução no fórum.
   		- O output mostrará o/os utilizadores que introduziram a/as mensagem/s
   		  mais longa/s e a/s respectiva/s mensagem/s. 
*/
void funcaoO(int i) {
	int j;
	for (j = 0; j < i; j++){
		if(vectorMensagens[j].tamanhoLinha == maiorLinha)
			printf("*LONGEST SENTENCE:%d:%s\n", vectorMensagens[j].id, vectorMensagens[j].texto);
	}
}

/* 
FuncaoT - Lista o utilizador mais activo no fórum.
   		- Se existirem dois utilizadores com o mesmo número de mensagens 
   		  intoduzidas, serão listados por ordem crescente do seu identificador.
   		- O output mostrará o/os identificador/es do/s utilizador/es mais
   		  activo/s, seguido do número de mensagens deste/s. 
*/
void funcaoT() {
	int j;
	for (j = 0; j < MAX_PARTICIPANTES; j++){
		if(vectorOcorrenciasPorId[j] == maisOcorrencias)
				printf("*MOST ACTIVE USER:%d:%d\n", j, maisOcorrencias);
	}
}

/* 
FuncaoC - Devolve o número de ocorrências de uma palavra.
   		- O output mostrará a palavra em questão, seguida do número de ocorrências
    	  da mesma em qualquer frase introduzida no fórum. 
*/
void funcaoC(int i) {
	int ocorrencias = 0;
	int j, salto;
	char palavra[MAX_FRASE];
	char aux[MAX_FRASE];
	scanf("%s", palavra);
	for (j = 0; j < i; j++){
		salto = 0;
		while(vectorMensagens[j].texto[salto] != '\0'){
			sscanf(vectorMensagens[j].texto + salto, "%s", aux);
			salto += strlen(aux); /* Com o tamanho de cada palavra lida conseguimos saber a 
								  posição do vector onde começa a proxima palavra */
			if (vectorMensagens[j].texto[salto] != '\0')
				salto++;
			if(!strcmp(palavra, aux))
				ocorrencias++;
		}
	}
	printf("*WORD %s:%d\n", palavra, ocorrencias);
}

/*  
Função S - Lista todas as mensagens por ordem alfabética.
		 - Se existirem 2 frases iguais, estas são ordenadas 
		   por ordem crescente do identificador do utilizador. 
		 - Se mesmo assim forem iguais, a ordem é determinada pela
		   introdução no sistema.
		 - O output mostrará o total de mensagens, seguido da lista 
		   ordenada representada por <user>:<frase>. 
		 - O algoritmo de ordenação usado foi uma adaptação do Selection Sort, onde
		   se quer passar sempre o menor valor do vetor para a primeira posição, 
		   depois o de segundo menor valor para a segunda posição, e assim sucessivamente
*/
void funcaoS(int i) {
	int j, k, min, aux2;
	char aux1[MAX_FRASE];
	
	if (numMensagens != i){ /*Só irá ordenar o vector auxiliar "copia" caso 
							tenha sido acrescentada alguma mensagem ao forum, 
							caso contrário este vector já se encontra ordenado.*/
		numMensagens = i;

		for (j = 0; j < i; j++){
			strcpy(copia[j].texto, vectorMensagens[j].texto);
			copia[j].id = vectorMensagens[j].id;
		}
		for (j = 0; j < (i-1); j++){
			aux2 = j;
			min = j; /* posicão para a qual o menor valor vai ser tranferido */
			for(k = j+1; k < i; k++){
				if (strcmp(copia[k].texto, copia[min].texto) < 0)
					min = k;
				if ((strcmp(copia[k].texto, copia[min].texto) == 0) && (copia[k].id < copia[min].id))
					min = k;
			}
			
			/* Troca da mensagem*/
			strcpy(aux1, copia[j].texto);
			/* só copia caso min e j não correspondam à mesma posição no vector, pois
			isso seria copiar para a mesma posição em que já está a mesma mensagem já 
			contida nessa posição */
			if(j != min) 
				strcpy(copia[j].texto, copia[min].texto);
			strcpy(copia[min].texto, aux1);
			
			/* Troca do id*/
			aux2 = copia[j].id;
			copia[j].id = copia[min].id;
			copia[min].id = aux2;
		}
	}
	
	printf("*SORTED MESSAGES:%d\n", i);
	for (j = 0; j < i; j++)
		printf("%d:%s\n", copia[j].id, copia[j].texto);
}