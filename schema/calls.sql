DELIMITER //
CREATE TRIGGER tbi_calls BEFORE INSERT ON calls FOR EACH ROW
BEGIN
	DECLARE local_rate FLOAT DEFAULT 5.0;
	DECLARE state_rate FLOAT DEFAULT 10.0;
	DECLARE national_rate FLOAT DEFAULT 15.0;

	DECLARE line_origin_id INT;
    DECLARE line_destination_id INT;
    DECLARE rate FLOAT;
    DECLARE totalprice FLOAT;
	DECLARE city_origin INT;
    DECLARE city_destination INT;
	DECLARE CONTINUE HANDLER FOR NOT FOUND BEGIN END;
    
    SELECT id_phone_line, id_city INTO line_origin_id, city_origin FROM phonelines WHERE id = NEW.id_line_origin;
    SELECT id_phone_line, id_city INTO line_destination_id, city_destination FROM phonelines WHERE id = NEW.id_line_destination;
	SET NEW.id_phone_line_from = line_origin_id;
    
	SELECT rate_per_minute INTO rate FROM rates WHERE id_origin_city = city_origin AND id_destination_city = city_destination;
	IF rate IS NULL THEN
        IF city_origin.city_prefix = city_destination.city_prefix THEN
		INSERT INTO rates(id_origin_city,id_destination_city,rate_per_minute) VALUES (city_origin,city_destination,local_rate);
		ELSE IF city_origin.city_prefix <> city_destination.city_prefix AND city_origin.id_province = city_destination.id_province THEN
		INSERT INTO rates(id_origin_city,id_destination_city,rate_per_minute) VALUES (city_origin,city_destination,state_rate);
		ELSE IF city_origin.city_prefix <> city_destination.city_prefix AND city_origin.id_province <> city_destination.id_province THEN
		INSERT INTO rates(id_origin_city,id_destination_city,rate_per_minute) VALUES (city_origin,city_destination,national_rate);
		END IF;		
    END IF;
	
    SET NEW.call_rate = rate;
    SET NEW.call_totalprice = (NEW.duration * (rate/60));
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE sp_call(IN origin LONG, IN destination LONG, IN duration INT)
BEGIN
	DECLARE line_origin_id INT;
    DECLARE line_destination_id INT;
    DECLARE rate FLOAT;
    DECLARE totalprice FLOAT;
	DECLARE city_origin INT;
    DECLARE city_destination INT;
    DECLARE date_call DATETIME;
	
    SELECT id_phone_line, id_city INTO line_origin_id, city_origin FROM phonelines WHERE id = origin;
    SELECT id_phone_line, id_city INTO line_destination_id, city_destination FROM phonelines WHERE id = destination;
    
	SELECT rate_per_minute INTO rate FROM rates WHERE id_origin_city = city_origin AND id_destination_city = city_destination;
    
    SET totalprice = (duration * (rate/60));
    SET date_call = (SELECT NOW());
    
	INSERT INTO calls(id_line_origin, id_line_destination, call_rate, call_duration, call_totalprice, call_date) 
    VALUES(line_origin_id, line_destination_id, rate, duration, totalprice, date_call);
END //
DELIMITER ;