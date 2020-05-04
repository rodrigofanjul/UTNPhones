INSERT INTO Rates(id_origin_city,id_destination_city) SELECT c1.id, c2.id FROM cities c1 JOIN cities c2 ON c1.id <= c2.id;

-- Local calls
UPDATE Rates r 
JOIN Cities c1 ON r.id_origin_city = c1.id
JOIN Cities c2 ON r.id_destination_city = c2.id
SET rate_per_minute = 5.0
WHERE c1.city_prefix = c2.city_prefix;

-- State calls
UPDATE Rates r 
JOIN Cities c1 ON r.id_origin_city = c1.id
JOIN Cities c2 ON r.id_destination_city = c2.id
SET rate_per_minute = 10.0
WHERE c1.city_prefix <> c2.city_prefix AND c1.id_province = c2.id_province;

-- National calls
UPDATE Rates r 
JOIN Cities c1 ON r.id_origin_city = c1.id
JOIN Cities c2 ON r.id_destination_city = c2.id
SET rate_per_minute = 20.0
WHERE c1.city_prefix <> c2.city_prefix AND c1.id_province <> c2.id_province;