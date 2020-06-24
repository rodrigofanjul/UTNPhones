-- Sin index realiza scan all
-- Con index realiza scan index, ya que el índice ordena los registros
-- A su vez puede recorrerse de forma ascendente o descendente
-- localhost:8080/api/users/{id}/invoices/between
-- localhost:8080/api/users/{id}/calls/between
-- localhost:8080/api/users/{id}/calls
CREATE INDEX idx_invoice_date ON
invoices (invoice_date DESC) 
USING BTREE;

CREATE INDEX idx_call_date ON
calls (call_date DESC) 
USING BTREE;

CREATE INDEX idx_id_line_origin ON
calls (id_line_origin ASC) 
USING BTREE;