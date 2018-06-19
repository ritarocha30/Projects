/**********************************/
/**								 **/
/** 1º Projeto de IAED 2016/2017 **/
/**	        Grupo nº 27			 **/
/**								 **/
/**	   Ana Rita Rocha - 79779	 **/
/**	   André Batista  - 80908	 **/
/**                              **/
/**********************************/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "funcoes.h"

int main() {
	
	int numeroMensagens = 0;
    char command, nada;
    inicializaVector(vectorOcorrenciasPorId, MAX_PARTICIPANTES);

    while(1){
    	command = getchar(); /* Le 'command' */
		switch(command) {
		    case 'A':
		    	if ((nada = getchar()) == ' '){
		    		funcaoA(numeroMensagens); /* O '\n' é lido na funcao "leLinha()", 
		    								  logo nao é preciso um "getchar()" no fim de 
		    								  executar a funcaoA() */
		   			numeroMensagens++;
		    		break;
		    	} else { /* Comando mal inserido */
		    		printf("ERRO: Comando desconhecido\n");
		    		if (nada == '\n')
			    		break;
		    		while(getchar() != '\n');
			   		break;
		    	}
		    case 'L':
		   		funcaoL(numeroMensagens);
		   		getchar();
		      	break;
		    case 'U':
		    	if ((nada = getchar()) == ' '){
		    		funcaoU(numeroMensagens);
		    		getchar();
		    		break;
		    	} else { /* Comando mal inserido */
		    		printf("ERRO: Comando desconhecido\n");
		    		if (nada == '\n')
			    		break;
		    		while(getchar() != '\n');
			   		break;
		    	}
		    case 'O':
		      	funcaoO(numeroMensagens);
		   		getchar();
		      	break;
		    case 'T':
		      	funcaoT();
		   		getchar();
		      	break;
		    case 'S':
		      	funcaoS(numeroMensagens);
		   		getchar();;
		      	break;
		    case 'C':
		    	if ((nada = getchar()) == ' '){
		    		funcaoC(numeroMensagens);
		    		getchar();
		    		break;
		    	} else { /* Comando mal inserido */
		    		printf("ERRO: Comando desconhecido\n");
		    		if (nada == '\n')
			    		break;
		    		while(getchar() != '\n');
			   		break;
		    	}
		    case 'X': /* Sai do programa e retorna o número total de mensagens*/
		   		printf("%d\n", numeroMensagens);
		       	return EXIT_SUCCESS; /* Termina o programa com sucesso */
		    default:
		    	while(getchar() != '\n');
		   		printf("ERRO: Comando desconhecido\n");
		}
	}
	return EXIT_FAILURE; /* Algo correu mal */
}