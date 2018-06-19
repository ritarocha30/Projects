/*RI-1: "Nao podem existir ofertas com datas sobrepostas"*/
DELIMITER $$
CREATE TRIGGER verify_dates BEFORE INSERT ON oferta
FOR EACH ROW
BEGIN
        DECLARE a INTEGER;
        SET a= (SELECT count(*) FROM oferta WHERE oferta.morada=new.morada AND oferta.codigo=new.codigo AND (oferta.data_inicio<= new.data_inicio OR oferta.data_fim>1=new.data_fim));

        IF a > 0 THEN
            	CALL raise_application_error(3001, 'oferta existente');
        END IF;
END $$
delimiter ;



/*RI-2: "A data de pagamento de uma reserva paga tem de ser superior ao timestamp do ultimo estado dessa reserva"*/
DROP TRIGGER verify_timestamp;
DELIMITER $$
CREATE TRIGGER verify_timestamp BEFORE INSERT ON paga
FOR EACH ROW
BEGIN
        DECLARE last_stamp TIMESTAMP;
        SET last_stamp = (SELECT MAX(E.time_stamp) FROM estado E WHERE E.numero = new.numero);

        IF NEW.data < last_stamp THEN
                          CALL raise_application_error(3001, 'oferta existente');
        END IF;

END $$
delimiter ;



