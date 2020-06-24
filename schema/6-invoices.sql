DROP PROCEDURE IF EXISTS sp_invoices;
DELIMITER //
CREATE PROCEDURE sp_invoices()
BEGIN
	DECLARE last_phoneline INT;
	DECLARE phoneline_id BIGINT;
    DECLARE calls_quantity INT DEFAULT 0;
    DECLARE calls_totalprice FLOAT DEFAULT 0;
    DECLARE invoice_totalprice FLOAT;
	DECLARE issue_date DATE;
    DECLARE expiration_date DATE;
    DECLARE new_invoice_id INT;
	DECLARE cur_calls_finished INT DEFAULT 0;
	
    -- Creamos una factura solo para las líneas que tienen llamadas
    DECLARE cur_calls CURSOR FOR SELECT id_line_origin FROM calls WHERE id_invoice IS NULL GROUP BY id_line_origin;
    DECLARE CONTINUE handler FOR NOT FOUND SET cur_calls_finished = 1;
	
    #Inicio de la transaccion
    START TRANSACTION; 
	
		SET issue_date = (SELECT current_date());
		SET expiration_date = (SELECT date_add(issue_date, INTERVAL 15 DAY));
			
		OPEN cur_calls;
		
			loop_calls : LOOP
			
				FETCH cur_calls INTO phoneline_id;
				IF(cur_calls_finished = 1) THEN LEAVE loop_calls; END IF;
                
                #Primero creo una factura vacía y obtengo su ID.
                INSERT INTO invoices(id_phoneline,invoice_date,invoice_is_paid,invoice_expiration_date) VALUES (phoneline_id,issue_date,FALSE,expiration_date);
                SET new_invoice_id = last_insert_id();
                
                #Actualizo todas las llamadas de la línea que estan sin factura, a ese número de factura
                UPDATE calls SET id_invoice = new_invoice_id WHERE id_line_origin = phoneline_id AND id_invoice IS NULL;
				
                #Obtengo los valores necesarios para rellenar la factura.
				SELECT SUM(call_price), COUNT(DISTINCT id) INTO calls_totalprice, calls_quantity FROM calls WHERE id_line_origin = phoneline_id AND id_invoice = new_invoice_id;
                SET invoice_totalprice = calls_totalprice * 1.21;

				#Actualizo la factura con los datos.
				UPDATE invoices SET invoice_calls_quantity = calls_quantity, invoice_cost_price = calls_totalprice, invoice_totalprice = invoice_totalprice WHERE id = new_invoice_id;
                
			END LOOP loop_calls;
            
	#Fin de la transaccion
    COMMIT; 
	
	CLOSE cur_calls;
END //
DELIMITER ;

-- SHOW PROCESSLIST;
-- SET GLOBAL event_scheduler = ON;
DROP EVENT IF EXISTS event_invoices;
DELIMITER //
CREATE EVENT event_invoices ON SCHEDULE EVERY "1" MONTH	STARTS "2020-06-01" DO
BEGIN
	CALL sp_invoices();
END //
