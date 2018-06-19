Enunciado do projecto:
O sistema InstantOffice gere reservas de espaços de co-work de trabalhadores móveis nas capitais europeias para empreendedores e/ou trabalhadores independentes.
Um empreendedor, ou trabalhador independente, pode alugar um espaço de trabalho num piso de um edifício temporariamente para a sua equipa, tornando-se assim um arrendatário do espaço. Pode também alugar um posto de trabalho individual, inserido dentro de um espaço de trabalho. O acesso a espaços de trabalho é efetuado através de uma fechadura eletrónica.
Para destrancar esta fechadura é necessário utilizar a aplicação móvel InstantOffice, que efetuará a autenticação de acesso através da leitura do QR code. Note-se que diversos espaços e, consequentemente, os seus postos de trabalho, podem partilhar a mesma porta de acesso.
Tanto os espaços de trabalho como os postos de trabalho individuais, são identificados por um código numérico sequencial e único dentro do piso. Todos os espaços que podem ser alugados têm uma fotografia. É necessário determinar o número de pisos de cada edifício.
Tanto os espaços de trabalho como os postos de trabalho são oferecidos na plataforma por períodos de tempo semanais ou mensais, especificados previamente. Alugar um espaço ou posto de trabalho corresponde à compra de ofertas de períodos de tempo desse espaço/posto.
Cada oferta tem uma data de criação, a data de início da oferta e a data de fim da oferta e a tarifa semanal ou mensal. As datas de início e fim devem bater certo com os dias de início/fim de mês ou de semana conforme a oferta seja mensal ou semanal.
Cada espaço de trabalho pode oferecer benefícios (perks) tais como água fresca, acesso à cozinha, internet de banda larga, entre outros.
O sistema regista informação acerca dos donos de cada edifício e dos espaços de trabalho, nomeadamente NIF, nome, e telefone de contacto. Além disso, relativamente aos espaços de trabalho o sistema regista as mesmas informações dos senhorios (i.e. quem está a explorar o espaço).
Os arrendatários fazem reservas sobre as ofertas disponíveis. Uma reserva pode estar em estados distintos. É considerada pendente enquanto o senhorio do espaço não aceitar o pedido. É considerada aceite quando o senhorio aceitou o pedido. Pode também ser declinada ou cancelada. As reservas são identificadas por um número de reserva.
Uma vez que uma reserva seja aceite, a oferta correspondente deve ficar indisponível. Para fins de auditoria, o sistema tem de registar todo o histórico de operações de cada reserva, ou seja, além do estado atual, os estados em que esteve e a data e hora em que transitou para esse estado.
As reservas aceites podem estar pagas ou pendentes de pagamento. Uma reserva paga tem associada a data e o método de pagamento.
Cada utilizador tem uma conta no sistema. Uma conta é designada univocamente por um e-mail. Todas as contas têm de ter obrigatoriamente um e-mail primário que as identifica. Uma conta pode ter vários e-mails secundários (que na prática são alternativas de contacto). Um e-mail pode estar validado. O sistema deve manter a password cifrada, de forma não reversível.
Uma conta pode estar no estado ativo ou inativo, mas só pode passar para o estado ativo quando o e-mail estiver validado.
Os utilizadores podem desempenhar diferentes papeis no sistema. Podem ser donos de edifícios, senhorios ou arrendatários. Os arrendatários são os empreendedores/trabalhadores independentes que alugam os espaços.


Parte1: Modelo Entidade-Associação, Restrições de Integridade;
Parte2: Modelo Relacional, Restrições de Integridade, Álgebra Relacional, SQL;
Parte3: Criação da Base de Dados, SQL, Restrições de Integridade, Aplicação;
Parte4: Índices, Data Warehouse.