--
-- POPULATE FOR date_dimension
--
DROP PROCEDURE IF EXISTS load_date_dim;
delimiter //
CREATE PROCEDURE load_date_dim()
BEGIN
   DECLARE v_full_date DATETIME;
   SET v_full_date = '2016-01-01 00:00:00';
   WHILE v_full_date < '2018-01-01 00:00:00' DO
       INSERT INTO data_dimension(
          data_key,
   	      dia,
   	      semana,
   	      mes,
   	      semestre,
   	      ano
       ) VALUES (
           YEAR(v_full_date) * 10000 + MONTH(v_full_date)*100 + DAY(v_full_date),
           DAY(v_full_date),
           WEEK(v_full_date),
           MONTH(v_full_date),
           QUARTER(v_full_date),
           YEAR(v_full_date)
       );
       SET v_full_date = DATE_ADD(v_full_date, INTERVAL 1 DAY);
   END WHILE;
END;
//


--
-- POPULATE FOR user_dimension
--
INSERT INTO user_dimension
SELECT nif, nome, telefone 
FROM user;


--
-- POPULATE FOR tempo_dimension
--
DROP PROCEDURE IF EXISTS load_tempo_dim;
delimiter //
CREATE PROCEDURE load_tempo_dim()
BEGIN
   DECLARE v_full_date DATETIME;
   SET v_full_date = '2016-01-01 00:00:00';
   WHILE v_full_date < '2016-01-02 00:00:00' DO
       INSERT INTO tempo_dimension(
          tempo_key,
   	      hora_do_dia,
   	      minuto_do_dia
       ) VALUES (
           HOUR(v_full_date)*100 + MINUTE(v_full_date),
           HOUR(v_full_date),
           MINUTE(v_full_date)
       );
       SET v_full_date = DATE_ADD(v_full_date, INTERVAL 1 MINUTE);
   END WHILE;
END;
//


--
-- POPULATE FOR localizacao_dimension
--
INSERT INTO localizacao_dimension
   SELECT CONCAT(O.morada,O.codigo, COALESCE(P.codigo_espaco, '')) AS localizacao_key, O.morada, O.codigo, P.codigo_espaco  
   FROM oferta O LEFT JOIN posto P ON O.codigo = P.codigo;
 

--
-- POPULATE FOR reserva_informacao
--
DROP PROCEDURE IF EXISTS load_reservas_info;
delimiter //
CREATE PROCEDURE load_reservas_info()
BEGIN
	DECLARE done INT DEFAULT FALSE;	
	DECLARE m_nif_key VARCHAR(9);
	DECLARE m_dataa TIMESTAMP;
	DECLARE data_key INT;
	DECLARE tempo_key INT;
	DECLARE localizacao_key VARCHAR(255);
	DECLARE m_morada VARCHAR(255);
	DECLARE m_codigo VARCHAR(255);
	DECLARE m_codigo_espaco VARCHAR(255);
	DECLARE m_numero VARCHAR(255);
	DECLARE m_montante NUMERIC(19,4);
	DECLARE m_duracao INT;
	
	DECLARE cur1 CURSOR FOR SELECT nif, data, O.morada, O.codigo, P.codigo_espaco, tarifa*DATEDIFF(data_fim, data_inicio) as montante, DATEDIFF(data_fim, data_inicio) as duration, numero
    						FROM paga NATURAL JOIN estado NATURAL JOIN aluga NATURAL JOIN oferta O LEFT JOIN posto P ON O.codigo = P.codigo
							WHERE estado = 'Paga'
							GROUP BY numero;
	
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
	
	OPEN cur1;
    read_loop: LOOP
      FETCH cur1 INTO m_nif_key, m_dataa, m_morada, m_codigo, m_codigo_espaco, m_montante, m_duracao, m_numero;
        IF done THEN
          LEAVE read_loop;
		END IF;
		
	   	  SET data_key = 10000*YEAR(m_dataa) + 100*MONTH(m_dataa) + DAY(m_dataa);
	  	  SET tempo_key = 100*HOUR(m_dataa) + MINUTE(m_dataa);
	  	  SET localizacao_key = CONCAT(m_morada,m_codigo,COALESCE(m_codigo_espaco, ''));
	  
      	  INSERT INTO reservas_informacao VALUES(m_montante, 
     										 	 m_duracao, 
      										     data_key, 
      										     m_nif_key, 
      										     localizacao_key, 
      										     tempo_key);
    END LOOP;

    CLOSE cur1;
END;
//


/*QUERY SQL OLAP*/

select codigo_espaco, codigo, dia, mes,avg(montante)
from localizacao_dimension natural join data_dimension natural join reservas_informacao
group by,codigo_espaco, codigo, dia, mes with ROLLUP
UNION
select codigo_espaco, codigo, dia, mes,avg(montante)
from localizacao_dimension natural join data_dimension natural join reservas_informacao
group by codigo_espaco,codigo,mes with ROLLUP
UNION
select codigo_espaco, codigo, dia, mes,avg(montante)
from localizacao_dimension natural join data_dimension natural join reservas_informacao
group by codigo,dia,mes with ROLLUP
UNION
select codigo_espaco, codigo, dia, mes,avg(montante)
from localizacao_dimension natural join data_dimension natural join reservas_informacao
group by codigo_espaco,mes with ROLLUP
