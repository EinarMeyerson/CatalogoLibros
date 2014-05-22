Hay que crear los usuarios sql siguientes:

create user 'libros'@'localhost' identified by 'libros'; grant all privileges on librosdb.* to 'libros'@'localhost'; flush privileges; exit;

create user 'realm'@'localhost' identified by 'realm'; grant all privileges on realmdb.* to 'realm'@'localhost'; flush privileges; exit;

Luego cargamos las bases de datos con las tablas:

mysql -u libros -p libros source (path del projecto)/librosdb-schema.sql; 
source (path del projecto)/librosdb-data.sql; 
exit;

mysql -u realm -p realm source (path del projecto)/realmdb-schema.sql; 
source (path projecto)/realmdb-data.sql; 
exit;

Hacemos maven build del paquete libros-api 
Cargamos en Tomcat el .war de libros-api

Y ya podemos comprobar en POSTMAN 
En atenea junto con la direccion de este projecto pongo la direccion de la colleccion de POSTMAN como comprobacion de todo.
