# Report Adventure Builder

**'Pessimistic concurrency control' - faz com que a base de dados bloqueie o recurso em nome do usuário A, tendo o usuário B de aguardar, até que o usuário A termine, para poder continuar. Não se permite que dois usuários trabalhem no mesmo recurso ao mesmo tempo e evita-se o conflito.**

**'Optimistic concurrency control' - não bloqueia nada, em vez disso, solicita ao usuário que se lembre de como era o recurso quando o viu pela primeira vez para que, quando o for atualizar, o usuário peça à base de dados esse recurso avançando apenas se não existirem alterações. Não se impede um possível conflito, mas pode ser detetado antes que qualquer dano seja causado e assim terminar a execução em segurança - 'roll back'.**


### 30Writes:

Relativamente ao teste de carga composto por 30% de escritas e 70% de leituras foram executados 3 tipos de testes:
50 Threads, 100 Threads e 250 Threads.</br>
À medida que o número de threads foi aumentando, e consequentemente a concorrência, foram observados aumentos no tempo de resposta do servidor como era expectável.
É facilmente observável que a concorrência de escritas tem um impacto muito superior no desempenho comparativamente à concorrência de leituras.</br>
Foi também observado no teste com 250 threads que houve erros registados (5%), isto deveu-se ao facto de estarem a ser utilizados 100 clientes a processar cada um uma aventura e em cada thread são executados 3 process de aventuras, o que leva ao excesso de tentativas de process quando corremos mais de 200 threads. Também nesse teste houve a maior degradação de serviço na leitura de aventuras.</br>
Finalmente podemos observar que a concorrência afecta principalmente a qualidade de serviço nas operações de escrita nas quais com 100 clientes concorrentes
se registou um tempo de resposta de 149.7 segundos para 90% dos pedidos comparativamente aos 49.5 segundos para 90% dos pedidos no teste com 50 clientes.</br>
Comparativamente as operações de leitura são menos afectadas, no entanto podemos observar que as operações de leitura directamente relacionadas com a escrita
são mais afectadas pela concorrência , um aumento de 135% (157ms para 370ms) para 90% dos pedidos de leitura de aventuras versus 57% (101ms para 159ms)
para 90% dos pedidos de leitura de actividades.</br>
Concluindo, o número ideal de utilizadores concorrentes para esta plataforma será de aproximadamente 100 pois a degradação do serviço é significativa para números
muito superiores e poderá levar a erros de serviço por excesso de carga.</br>



### 100Reads:
Relativamente ao teste de carga de leituras, foram efectuados 3 tipos:
1 thread com 1 loop, no qual foram lidas apenas 100 amostras, uma por cada entrada, sem quaisquer erros e de forma bastante rápida;
100 threads com 1 loop, no qual se pode constatar que o período de execução foi bastante maior, mas novamente, sem quaisquer problemas de leitura;
500 threads com 4 loops cada, dando um total de 2000 amostras de leitura, que prolongou ainda mais o tempo de execução para aproximadamente os 3m 30s
Neste ultimo caso foi observado em algumas das vezes que foi testado, alguns erros de leitura (o maior foi de 0.18%) devido a excepões de sockets, estes poderam ter origem devido a sobrecarga de pedidos de leitura e perdas de pedidos/resposta por parte do servidor, originando as tais excepções, mas podemos concluir que há uma certa tendência a aumentar o tempo de resposta e ao mesmo tempo, criar dificuldades em responder a todas as leituras por parte do servidor, mas este tenta ao máximo evitar essa perda de informação, notando que em algumas execuções do teste mais pesado, ele observou 0% de erros de leitura.

### 100Writes:

‘Optimistic Concurrency Control’ presume que várias transações podem ser concluídas com frequência sem interferirem entre si, o que torna esta política eficiente visto que não existe o custo de gerir bloqueios, nem a espera por recursos bloqueados (deadlocks). Quando implementada em ambientes com baixa contenção de dados tem uma excelente performance. No entanto, se a disputa por recursos for frequente, o custo de repetidamente reiniciar as transações prejudica significativamente o desempenho.</br>
No caso em que 100 threads são lançadas para que as 100 aventuras sejam processadas invocando serviços remotos, o número de recursos disponíveis em cada serviço influenciou significativamente o desempenho.</br>
Considerando 1 hotel com 100 quartos, 1 fornecedor de atividade com 100 ofertas, 1 rent a car com 100 carros e 100 clientes, o desempenho foi variado conforme a máquina em que o teste estava a ser feito. Como tal, o importante não é avaliar a performance total em cada máquina na qual o teste foi executado mas sim a sua evolução com a variação do número de recursos que os serviços irão manipular.</br>
Quando o número de clientes passou de 100 para 50, estando cada cliente a adquirir duas aventuras, as solicitações por segundo que o serviço manipula passaram respetivamente de 3.8/sec para 3.4/sec. Esta diferença é notada quando as 100 aventuras levam à execução de 600 ‘process’, sendo a resposta do serviço mais lenta pois passa de cerca de 157,89 segundos para 176,47 segundos (quase 20 segundos de diferença).</br>
O desempenho é principalmente afetado quando a variação do número de recursos disponíveis é feita apenas num serviço que será acedidos por vários clientes. Usando como exemplo a Rent a Car, o desempenho diminui exponencialmente com a gradual diminuição do número de carros disponíveis. Isto deve-se à disputa dos vários clientes pelos mesmos recursos (número cada vez menor de carros disponíveis), que obriga ao constante reinício das transações devido às alterações realizadas por outros clientes na base de dados, neste exemplo, um carro deixa de estar disponível durante o período de tempo do seu aluguer.</br>
O mesmo se verificou quando o número de quartos do hotel e o número de ofertas do fornecedor de atividades eram gradualmente diminuídos.
