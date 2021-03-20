DROP TABLE Nota; 
CREATE TABLE Nota (
    Id NUMBER(3,0) NOT NULL UNIQUE, 
    Nota NUMBER(5,0) 
); 
-- Se agregan comentarios a la tabla
COMMENT ON TABLE Nota IS 'Tabla que refleja la nota de un alumno';
-- Se agregan comentarios a una columna
COMMENT ON COLUMN Nota.id IS 'Columna que muestra la nota obtenida';
-- Se agregan comentarios a una fila 
commit; 

-- All tables inside my user
=======
DROP TABLE Nota; 
CREATE TABLE Nota (
    Id NUMBER(3,0) NOT NULL UNIQUE, 
    Nota NUMBER(5,0) 
); 
-- Se agregan comentarios a la tabla
COMMENT ON TABLE Nota IS 'Tabla que refleja la nota de un alumno';
-- Se agregan comentarios a una columna
COMMENT ON COLUMN Nota.id IS 'Columna que muestra la nota obtenida';
-- Se agregan comentarios a una fila 
commit; 

-- All tables inside my user
SELECT * FROM ALL_TAB_COMMENTS WHERE owner = 'USUARIO1' AND COMMENTS IS NOT NULL; 

CREATE TABLE MY_TABLE2 (
    val NUMBER(3), 
    des VARCHAR(20)
); 

GRANT INSERT (val) ON MY_TABLE2 TO USUARIO1; 

-- Grant to create packages and procedures
GRANT CREATE PROCEDURE TO USUARIO2; 


insert into nota values (112, 123); 
commit; 










