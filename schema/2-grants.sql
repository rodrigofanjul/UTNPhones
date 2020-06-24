CREATE USER 'backoffice'@'localhost';
CREATE USER 'clientes'@'localhost';
CREATE USER 'infraestructura'@'localhost';
SET PASSWORD FOR 'backoffice'@'localhost' = PASSWORD('1234');
SET PASSWORD FOR 'clientes'@'localhost' = PASSWORD('1234');
SET PASSWORD FOR 'infraestructura'@'localhost' = PASSWORD('1234');

-- Permisos backoffice

GRANT INSERT ON UTNPhones.phonelines TO 'backoffice'@'localhost';
GRANT INSERT ON UTNPhones.users TO 'backoffice'@'localhost';
GRANT INSERT ON UTNPhones.rates TO 'backoffice'@'localhost';
GRANT SELECT ON UTNPhones.* TO 'backoffice'@'localhost';
GRANT UPDATE(id_city, user_name, user_lastname, user_idcard, user_password, user_role) ON UTNPhones.users TO 'backoffice'@'localhost';
GRANT UPDATE(rate_per_minute) ON UTNPhones.rates TO 'backoffice'@'localhost';
GRANT UPDATE(phoneline_status) ON UTNPhones.phonelines TO 'backoffice'@'localhost';
GRANT UPDATE(invoice_is_paid) ON UTNPhones.invoices TO 'backoffice'@'localhost';

-- Permisos clientes

GRANT SELECT ON UTNPhones.users TO 'clientes'@'localhost';
GRANT SELECT ON UTNPhones.invoices TO 'clientes'@'localhost';
GRANT SELECT ON UTNPhones.calls TO 'clientes'@'localhost';
GRANT UPDATE(user_name, user_lastname, password) ON UTNPhones TO 'clientes'@'localhost';

-- Permisos infraestructura

GRANT INSERT ON UTNPhones.calls TO 'infraestructura'@'localhost';
GRANT INSERT ON UTNPhones.rates TO 'infraestructura'@'localhost';
GRANT UPDATE(rate_per_minute) ON UTNPhones.rates TO 'infraestructura'@'localhost';

-- Permisos facturacion

-- La facturacion es realizada por un evento automático ejecutado por la base de datos. 
-- No necesita ni usuario ni permisos especiales.