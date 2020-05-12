DROP DATABASE IF EXISTS UTNPhones;
CREATE DATABASE IF NOT EXISTS UTNPhones;
USE UTNPhones;

CREATE TABLE provinces(
	id INT AUTO_INCREMENT PRIMARY KEY,
	province_name VARCHAR(30) UNIQUE NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE cities(
	id INT AUTO_INCREMENT PRIMARY KEY,
	id_province INT,
	city_name VARCHAR(50) NOT NULL,
	city_prefix INT NOT NULL,
	CONSTRAINT fk_cities_province FOREIGN KEY (id_province) REFERENCES provinces(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE rates(
	id INT AUTO_INCREMENT PRIMARY KEY,
	id_origin_city INT,
	id_destination_city INT,
	rate_per_minute FLOAT NOT NULL DEFAULT 0,
	CONSTRAINT fk_rates_city_origin FOREIGN KEY (id_origin_city) REFERENCES cities(id),
	CONSTRAINT fk_rates_city_destination FOREIGN KEY (id_destination_city) REFERENCES cities(id),
	-- Permite una unica relación entre origen y destino
	UNIQUE key unq_rates_origin_destination (id_origin_city,id_destination_city)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE users(
	id INT AUTO_INCREMENT PRIMARY KEY,
	id_city INT,
	user_name VARCHAR(45) NOT NULL,
	user_lastname VARCHAR(45) NOT NULL,
	user_idcard INT NOT NULL UNIQUE,
	user_password VARCHAR(200) NOT NULL,
	user_role ENUM("USER", "EMPLOYEE") NOT NULL,
	CONSTRAINT fk_users_city FOREIGN KEY(id_city) REFERENCES cities(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE phonelines(
	id BIGINT PRIMARY KEY,
	id_user INT,
	id_city INT,
	phoneline_type ENUM("MOBILE", "LANDLINE") NOT NULL,
	phoneline_status ENUM("ACTIVE", "SUSPENDED", "CANCELLED") NOT NULL,
	CONSTRAINT fk_phonelines_user FOREIGN KEY(id_user) REFERENCES users(id),
	CONSTRAINT fk_phonelines_city FOREIGN KEY(id_city) REFERENCES cities(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE invoices(
	id INT AUTO_INCREMENT PRIMARY KEY,
    id_phoneline BIGINT,
    invoice_calls_quantity INT NOT NULL,
    invoice_cost_price FLOAT NOT NULL,
    invoice_totalprice FLOAT NOT NULL,
    invoice_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    invoice_is_paid BOOLEAN,
    invoice_expiration_date DATETIME,
    CONSTRAINT fk_users_phoneline FOREIGN KEY(id_phoneline) REFERENCES phonelines(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE calls(
	id INT AUTO_INCREMENT PRIMARY KEY,
	id_rate INT,
	id_invoice INT,
	id_line_origin BIGINT NOT NULL,
	id_line_destination BIGINT NOT NULL,
    call_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    call_duration INT NOT NULL,
    call_rate FLOAT,
    call_totalprice FLOAT,
	CONSTRAINT fk_calls_rate FOREIGN KEY(id_rate) REFERENCES rates(id),
	CONSTRAINT fk_calls_invoice FOREIGN KEY(id_invoice) REFERENCES invoices(id),
	CONSTRAINT fk_calls_line_origin FOREIGN KEY(id_line_origin) REFERENCES phonelines(id),
	CONSTRAINT fk_calls_line_destination FOREIGN KEY(id_line_destination) REFERENCES phonelines(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;