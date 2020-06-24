-- Sin index realiza scan 1 por 1, con index hace una busqueda basada en el.
-- localhost:8080/api/users/{id}/invoices/between
CREATE INDEX idx_invoice_date ON
invoices (invoice_date DESC) 
USING BTREE;

-- Sin index realiza scan 1 por 1, con index hace una busqueda basada en el.
-- localhost:8080/api/users/{id}/calls/between
CREATE INDEX idx_call_date ON
calls (call_date DESC) 
USING BTREE;

-- Sin index realiza scan 1 por 1, con index hace una busqueda basada en el.
-- localhost:8080/api/users/{id}/calls
CREATE INDEX idx_id_line_origin ON
calls (id_line_origin ASC) 
USING BTREE;