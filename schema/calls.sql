DROP FUNCTION IF EXISTS getRate;
DELIMITER //
CREATE FUNCTION getRate(id_line_origin BIGINT, id_line_destination BIGINT)
RETURNS FLOAT
BEGIN
	DECLARE local_rate FLOAT DEFAULT 5.0;
	DECLARE state_rate FLOAT DEFAULT 10.0;
	DECLARE national_rate FLOAT DEFAULT 15.0;

	DECLARE rate FLOAT;
	DECLARE city_origin INT;
    DECLARE city_destination INT;
    DECLARE city_origin_prefix INT;
    DECLARE city_destination_prefix INT;
    DECLARE city_origin_province INT;
    DECLARE city_destination_province INT;
    DECLARE CONTINUE HANDLER FOR NOT FOUND BEGIN END;

	SELECT id_city INTO city_origin FROM phonelines WHERE id = id_line_origin;
    SELECT id_city INTO city_destination FROM phonelines WHERE id = id_line_destination;
    
    SELECT city_prefix, id_province INTO city_origin_prefix, city_origin_province FROM cities WHERE id = city_origin;
    SELECT city_prefix, id_province INTO city_destination_prefix, city_destination_province FROM cities WHERE id = city_destination;

	SELECT rate_per_minute INTO rate FROM rates WHERE id_origin_city = city_origin AND id_destination_city = city_destination;
	IF rate IS NULL THEN
        IF city_origin_prefix = city_destination_prefix THEN
			INSERT INTO rates(id_origin_city,id_destination_city,rate_per_minute) VALUES (city_origin,city_destination,local_rate);
        ELSEIF city_origin_prefix <> city_destination_prefix AND city_origin_province = city_destination_province THEN
			INSERT INTO rates(id_origin_city,id_destination_city,rate_per_minute) VALUES (city_origin,city_destination,state_rate);
        ELSEIF city_origin_prefix <> city_destination_prefix AND city_origin_province <> city_destination_province THEN
			INSERT INTO rates(id_origin_city,id_destination_city,rate_per_minute) VALUES (city_origin,city_destination,national_rate);
        END IF;        
    END IF;
    SELECT rate_per_minute INTO rate FROM rates WHERE id_origin_city = city_origin AND id_destination_city = city_destination;
    RETURN rate;
END; //
DELIMITER ;

DROP TRIGGER IF EXISTS tbi_calls;
DELIMITER //
CREATE TRIGGER tbi_calls BEFORE INSERT ON calls FOR EACH ROW
BEGIN    
    SET NEW.call_rate = getRate(NEW.id_line_origin,NEW.id_line_destination);
    SET NEW.call_totalprice = (NEW.call_duration * (NEW.call_rate/60));
END; //
DELIMITER ;