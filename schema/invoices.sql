DELIMITER //
CREATE PROCEDURE sp_invoices()
BEGIN
    DECLARE i INT DEFAULT -1;
	DECLARE call_id INT;
	DECLARE phoneline_id INT;
    DECLARE calls_totalprice FLOAT DEFAULT 0;
    DECLARE calls_quantity INT DEFAULT 0;
    DECLARE cost_price FLOAT DEFAULT 4.50;
	DECLARE issue_date DATE;
    DECLARE expiration_date DATE;
    DECLARE new_invoice_id INT;
	DECLARE cur_calls_finished INT DEFAULT 0;
	
    DECLARE cur_calls CURSOR FOR SELECT id, id_line_origin FROM calls WHERE id_invoice IS NULL GROUP BY id;
    DECLARE CONTINUE handler FOR NOT FOUND SET cur_calls_finished = 1;
	
    START TRANSACTION; -- Inicio de la transaccion
	
		SET issue_date = (SELECT current_date());
		SET expiration_date = (SELECT date_add(issue_date, INTERVAL 15 DAY));
			
		open cur_calls;
		
			loop_calls : LOOP
			
				FETCH cur_calls INTO call_id, phoneline_id;
				IF(cur_calls_finished = 1) THEN leave loop_calls; END IF;
				
				IF(i <> phoneline_id) THEN
					SELECT (SUM(call_totalprice) * cost_price), COUNT(*) INTO calls_totalprice, calls_quantity FROM calls WHERE id_invoice IS NULL AND id_line_origin = phoneline_id;

					INSERT INTO invoices(id_phoneline, invoice_calls_quantity, invoice_cost_price, invoice_totalprice, invoice_date, invoice_is_paid, invoice_expiration_date)
					VALUES(phoneline_id, calls_quantity, cost_price, calls_totalprice, issue_date, FALSE, expiration_date);
					
					SET new_invoice_id = last_insert_id();
					SET i = phoneline_id;
				END IF;
				
				UPDATE calls SET id_invoice = new_invoice_id WHERE id_call = call_id;
				
			END LOOP loop_calls;
		
    COMMIT; -- Fin de la transaccion
	
	CLOSE cur_calls;
END //
DELIMITER ;

-- SHOW PROCESSLIST;
-- SET GLOBAL event_scheduler = ON;
DELIMITER //
CREATE EVENT event_invoices ON SCHEDULE EVERY "1" MONTH	STARTS "2020-06-01" DO
BEGIN
	CALL sp_invoices();
END //