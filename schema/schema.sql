DROP DATABASE IF EXISTS UTNPhones;
CREATE DATABASE UTNPhones;
USE UTNPhones;

CREATE TABLE provinces(
	id INT AUTO_INCREMENT,
    province_name VARCHAR(30) UNIQUE NOT NULL,
    CONSTRAINT pk_provinces PRIMARY KEY (id)
);

CREATE TABLE cities(
	id INT AUTO_INCREMENT,
    city_name VARCHAR(50) NOT NULL,
    prefix INT UNIQUE NOT NULL,
    id_province INT,
    CONSTRAINT pk_cities PRIMARY KEY (id),
    CONSTRAINT fk_cities_id_province FOREIGN KEY (id_province) REFERENCES provinces(id)
);

CREATE TABLE rates(
	id INT AUTO_INCREMENT,
	id_origin_city INT,
	id_destination_city INT,
	cost_per_minute FLOAT NOT NULL,
    CONSTRAINT pk_rates PRIMARY KEY (id),
	CONSTRAINT fk_rates_city_origin FOREIGN KEY (id_origin_city) REFERENCES cities(id),
    CONSTRAINT fk_rates_city_destination FOREIGN KEY (id_destination_city) REFERENCES cities(id),
    UNIQUE key unq_rates_origin_destination (id_origin_city,id_destination_city)
);

CREATE TABLE users(
	id INT AUTO_INCREMENT,
	name_user VARCHAR(45) NOT NULL,
	lastname VARCHAR(45) NOT NULL,
	identification_card INT NOT NULL UNIQUE,
    password_user VARCHAR(30) NOT NULL,
	type_user enum("user", "employee") NOT NULL,
	id_city integer,	
    CONSTRAINT pk_users PRIMARY KEY (id),
	CONSTRAINT fk_users_city FOREIGN KEY(id_city) REFERENCES cities(id)
);

CREATE TABLE lines(
	id INT AUTO_INCREMENT,
    phone_number INT UNIQUE NOT NULL,
    type_line enum("mobile", "landline") NOT NULL,
    id_user INT,
    id_city INT,
    CONSTRAINT pk_phoneLines PRIMARY KEY (id),
    CONSTRAINT fk_phoneLines_id_user FOREIGN KEY(id_user) REFERENCES users(id),
    CONSTRAINT fk_phoneLines_id_city FOREIGN KEY(id_city) REFERENCES cities(id)
);

CREATE TABLE invoices(
	id INT AUTO_INCREMENT,
    id_phoneline INT,
    calls_quantity INT NOT NULL,
    cost_price FLOAT NOT NULL,
    total_price FLOAT NOT NULL,
    invoice_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_paid boolean,
    invoice_expiration_date datetime,
    CONSTRAINT pk_users PRIMARY KEY (id),
    CONSTRAINT fk_id_phoneline_invoices FOREIGN KEY(id_phoneline) REFERENCES phoneLines(id)
);

CREATE TABLE calls(
	id INT AUTO_INCREMENT,
    id_origin_phone INT,
    id_destination_phone INT,
    id_rate INT,
	id_invoice INT,
    date_call TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    price INT,
    duration INT,
    total_price INT,
    CONSTRAINT pk_calls PRIMARY KEY (id),
    CONSTRAINT fk_calls_id_origin_phone FOREIGN KEY(id_origin_phone) REFERENCES phoneLines(id),
    CONSTRAINT fk_calls_id_destination_phone FOREIGN KEY (id_destination_phone) REFERENCES phoneLines(id),
    CONSTRAINT fk_calls_rate FOREIGN KEY (id_rate) REFERENCES rates(id)
);


