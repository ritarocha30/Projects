/*Consultas SQL*/

/*a) Quais os espaços com postos que nunca foram alugados?*/
SELECT distinct e.morada, e.codigo
FROM espaco e, aluga a, posto p
Where e.codigo = p.codigo_espaco and e.morada = p.morada and
	(e.morada,e.codigo) NOT IN(
							SELECT a.morada, a.codigo
								FROM aluga a) and
									(p.morada,p.codigo) NOT IN(
															SELECT a.morada, a.codigo
																FROM aluga a)
	
/*b) Quais edifícios com um número de reservas superior à média?*/
SELECT *
FROM (SELECT morada, COUNT(numero) as reservation
FROM aluga 
	GROUP BY morada) reservas
		HAVING (reservation > (
			SELECT AVG(reservation)
				FROM reservas));
				
/*c) Quais utilizadores cujos alugaveis foram fiscalizados sempre pelo mesmo fiscal?*/
SELECT A.nif, U.nome  
FROM fiscaliza F LEFT JOIN arrenda A ON F.morada = A.morada LEFT JOIN user U ON A.nif = U.nif  
GROUP BY F.morada, A.nif, U.nome 
HAVING count(distinct F.id)=1;
											  
/*d) Qual o montante total realizado (pago) por cada espaço durante o ano de 2016?
Assuma que a tarifa indicada na oferta é diária. Deve considerar os casos em que o
espaço foi alugado totalmente ou por postos.*/
SELECT morada,codigo_espaco,tarifa,datediff(data_inicio,data_fim)
	FROM(
		(SELECT distinct p.morada, p.codigo_espaco
			FROM posto p, aluga a, paga pa
				WHERE p.codigo_espaco = a.codigo and p.morada = a.morada and pa.numero=a.numero)  UNION
		(SELECT distinct e.morada, e.codigo
			FROM espaco e, aluga a, paga pa 
				WHERE e.codigo = a.codigo and e.morada = a.morada and pa.numero=a.numero))  
WHERE(data_fim like '2016%') and (data_inicio like '2016%')
 					
/*e) Quais os espaços de trabalho cujos postos nele contidos foram todos alugados? (Por
alugado entende-se um posto de trabalho que tenha pelo menos uma oferta aceite,
independentemente das suas datas.)*/
SELECT e.morada, e.codigo
FROM posto p, espaco e, aluga a, estado es
WHERE e.morada = p.morada and e.codigo=p.codigo_espaco and 
es.estado = "aceite" where not exists(
						SELECT distinct e.morada, e.codigo
							FROM espaco e, aluga a, posto p
								Where e.codigo = p.codigo_espaco and e.morada = p.morada and
									(e.morada,e.codigo) NOT IN(
										SELECT a.morada, a.codigo
											FROM aluga a) and
												(p.morada,p.codigo) NOT IN(
															SELECT a.morada, a.codigo
																FROM aluga a))