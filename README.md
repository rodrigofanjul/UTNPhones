# UTNPhones

Acceso a PDF con consignas del TP:

[TP INTEGRADOR BASE DE DATOS/ PROGRAMACIÓN AVANZADA I](https://github.com/rodrigofanjul/UTNPhones/blob/master/docs/PAI%20-%20BD2%20-%20TP%20FINAL%20-%20Borrador.pdf)
 
## Registrar un nuevo usuario  

Se puede registrar un nuevo usuario enviando la siguiente Request al servidor:

> POST localhost:8080/user/register/

    {
   	    "name": "Rodrigo",
   	    "lastname": "Fanjul",  
   	    "idcard": 38831866,  
   	    "password": "123",
   	    "type": "user",
   	    "city":
   	    {
	        "id": 16501
   	    }
    }

Se puede loguearse enviando la siguiente Request al servidor:

> POST localhost:8080/user/login/

    {
   	    "idcard": 38831866,  
   	    "password": "123"
    }