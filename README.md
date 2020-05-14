# UTNPhones

Link to PDF with assignments:

[TP INTEGRADOR BASE DE DATOS/ PROGRAMACIÓN AVANZADA I](https://github.com/rodrigofanjul/UTNPhones/blob/master/docs/PAI%20-%20BD2%20-%20TP%20FINAL%20-%20Borrador.pdf)
 
# API Operations

All possible operations are listed below:

### Users  
> GET localhost:8080/users  
> GET localhost:8080/users/{id}  
> GET localhost:8080/users/{id}/invoices  
> GET localhost:8080/users/{id}/invoices/between  
> GET localhost:8080/users/{id}/calls  
> GET localhost:8080/users/{id}/calls/between  
> GET localhost:8080/users/{id}/calls/mostcalled  
> POST localhost:8080/users/register/  
> POST localhost:8080/users/login/  
> PUT localhost:8080/users/{id}  

### Calls  
> GET localhost:8080/calls  
> POST localhost:8080/calls  

### Invoices  
> GET localhost:8080/invoices  

### Phonelines  
> GET localhost:8080/phonelines  
> GET localhost:8080/phonelines/{id}  
> POST localhost:8080/phonelines  

### Rates  
> GET localhost:8080/rates  
> GET localhost:8080/rates/between  