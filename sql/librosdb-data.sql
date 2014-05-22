source librosdb-schema.sql;
insert into users values('alicia','Alicia', 'alicia@acme.com');
insert into users values('blas','Blas', 'blas@acme.com');
insert into users values('test','test', 'test@acme.com');
insert into libros (titulo, autor, lengua, edicion, fecha_edicion, fecha_impresion, editorial) values ('Cumbres Borrascosas', 'Emily Bronte', 'ingles',3,'03-05-21','03-06-21','editorial1');
insert into libros (titulo, autor, lengua, edicion, fecha_edicion, fecha_impresion, editorial) values ('Don Quijote de la Mancha', 'Miguel de Cervantes', 'castellano',1,'03-04-21','02-05-21','editorial2');
insert into libros (titulo, autor, lengua, edicion, fecha_edicion, fecha_impresion, editorial) values ('Odisea', 'Homero', 'Griego',7,'02-07-20','04-09-11','editorial3');
insert into libros (titulo, autor, lengua, edicion, fecha_edicion, fecha_impresion, editorial) values ('Guerra y paz', 'Lev Tolstoi	', 'Ruso',5,'03-05-21','03-06-21','editorial4');
insert into libros (titulo, autor, lengua, edicion, fecha_edicion, fecha_impresion, editorial) values ('Cien años de soledad', 'Gabo', 'castellano',6,'04-05-22','08-09-25','editorial5');
insert into libros (titulo, autor, lengua, edicion, fecha_edicion, fecha_impresion, editorial) values ('Hamlet', 'William Shakespeare', 'ingles',9,'03-05-21','03-06-05','editorial4');
insert into libros (titulo, autor, lengua, edicion, fecha_edicion, fecha_impresion, editorial) values ('El idiota', 'Fiódor Dostoievski', 'Ruso',10,'03-05-21','03-06-11','editorial2');
insert into libros (titulo, autor, lengua, edicion, fecha_edicion, fecha_impresion, editorial) values ('ESDLA: La comunidad del anillo', 'Tolkien', 'ingles',11,'03-05-02','03-06-23','editorial1');
insert into libros (titulo, autor, lengua, edicion, fecha_edicion, fecha_impresion, editorial) values ('ESDLA: Las dos torres', 'Tolkien', 'ingles',2,'03-05-09','03-06-04','editorial5');
insert into libros (titulo, autor, lengua, edicion, fecha_edicion, fecha_impresion, editorial) values ('ESDLA: El retorno del rey', 'Tolkien', 'ingles',1,'09-05-01','03-06-01','editorial1');

insert into resenas (idlibro, username,texto) values ('1', 'alicia','Esta bien1');
insert into resenas (idlibro, username,texto) values ('2', 'blas','Esta bien2');
insert into resenas (idlibro, username,texto) values ('3', 'test', 'Esta bien3');
insert into resenas (idlibro, username, texto) values ('4', 'blas', 'Esta bien4');
insert into resenas (idlibro, username,texto) values ('5', 'alicia', 'Esta bien5');
insert into resenas (idlibro, username, texto) values ('6', 'blas', 'Esta bien6');
insert into resenas (idlibro, username, texto) values ('7', 'alicia','Esta bien7');
insert into resenas (idlibro, username,texto) values ('8', 'blas','Esta bien8');
insert into resenas (idlibro, username,texto) values ('9', 'alicia', 'Esta bien9');
insert into resenas (idlibro, username,texto) values ('10', 'alicia', 'Esta bien10');











