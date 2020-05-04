DROP DATABASE IF EXISTS UTNPhones;
CREATE DATABASE IF NOT EXISTS UTNPhones;
USE UTNPhones;

CREATE TABLE Provinces(
	id INT AUTO_INCREMENT PRIMARY KEY,
	province_name VARCHAR(30) UNIQUE NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE Cities(
	id INT AUTO_INCREMENT PRIMARY KEY,
	id_province INT,
	city_name VARCHAR(50) NOT NULL,
	city_prefix INT NOT NULL,
	CONSTRAINT fk_cities_province FOREIGN KEY (id_province) REFERENCES Provinces(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE Rates(
	id INT AUTO_INCREMENT PRIMARY KEY,
	id_origin_city INT,
	id_destination_city INT,
	rate_per_minute FLOAT NOT NULL DEFAULT 0,
	CONSTRAINT fk_rates_city_origin FOREIGN KEY (id_origin_city) REFERENCES Cities(id),
	CONSTRAINT fk_rates_city_destination FOREIGN KEY (id_destination_city) REFERENCES Cities(id),
	-- Permite una unica relación entre origen y destino
	UNIQUE key unq_rates_origin_destination (id_origin_city,id_destination_city)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE Users(
	id INT AUTO_INCREMENT PRIMARY KEY,
	id_city INT,
	user_name VARCHAR(45) NOT NULL,
	user_lastname VARCHAR(45) NOT NULL,
	user_idcard INT NOT NULL UNIQUE,
	user_password VARCHAR(30) NOT NULL,
	user_type enum("user", "employee") NOT NULL,
	CONSTRAINT fk_users_city FOREIGN KEY(id_city) REFERENCES Cities(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE Phonelines(
	id INT AUTO_INCREMENT PRIMARY KEY,
	id_user INT,
	id_city INT,
	phoneline_type enum("mobile", "landline") NOT NULL,
	phoneline_active BOOLEAN,
	CONSTRAINT fk_phonelines_user FOREIGN KEY(id_user) REFERENCES Users(id),
	CONSTRAINT fk_phonelines_city FOREIGN KEY(id_city) REFERENCES Cities(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE Invoices(
	id INT AUTO_INCREMENT PRIMARY KEY,
    id_phoneline INT,
    invoice_calls_quantity INT NOT NULL,
    invoice_cost_price FLOAT NOT NULL,
    invoice_totalprice FLOAT NOT NULL,
    invoice_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    invoice_is_paid BOOLEAN,
    invoice_expiration_date DATETIME,
    CONSTRAINT fk_users_phoneline FOREIGN KEY(id_phoneline) REFERENCES Phonelines(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE Calls(
	id INT AUTO_INCREMENT PRIMARY KEY,
    id_rate INT,
	id_invoice INT,
	id_line_origin INT NOT NULL,
	id_line_destination INT NOT NULL,
    call_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,	
    call_price INT,
    call_duration INT,
    call_totalprice INT,
    CONSTRAINT fk_calls_rate FOREIGN KEY(id_rate) REFERENCES Rates(id),
	CONSTRAINT fk_calls_invoice FOREIGN KEY(id_invoice) REFERENCES Invoices(id),
	CONSTRAINT fk_calls_line_origin FOREIGN KEY(id_line_origin) REFERENCES Phonelines(id),
	CONSTRAINT fk_calls_line_destination FOREIGN KEY(id_line_destination) REFERENCES Phonelines(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;