#ifndef FUNCOES_H
#define FUNCOES_H

#define MAX_PARTICIPANTES 1000 
#define MAX_MENSAGENS 10000 
#define MAX_FRASE 141

/* Estrutura de uma mensagem */
typedef struct{
	int id;
	char texto[MAX_FRASE];
	int tamanhoLinha;
} Mensagem;

/* Vector de mensagens inseridas */
Mensagem vectorMensagens[MAX_MENSAGENS];

/* Cópia do vector de mensagens que será ordenado */
Mensagem copia[MAX_MENSAGENS];

/* Vector de inteiros onde cada posião do vector corresponde
ao id do user e o inteiro correspondente a essa posição é 
incrementado cada vez que uma mensagem desse utilizador é 
inserida no forúm*/
int vectorOcorrenciasPorId[MAX_PARTICIPANTES];

/* protótipos das funções */
void inicializaVector(int vec[], int max);
int leLinha(char s[]);
void funcaoA(int i);
void funcaoL(int i);
void funcaoU(int i);
void funcaoO(int i);
void funcaoT();
void funcaoC(int i);
void funcaoS(int i);

#endif