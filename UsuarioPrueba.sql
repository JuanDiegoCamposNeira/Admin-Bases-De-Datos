<<<<<<< HEAD
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
>>>>>>> a1ea9f39413c951c407d431d8733a3ee80a83303
SELECT * FROM ALL_TAB_COMMENTS WHERE owner = 'USUARIO1' AND COMMENTS IS NOT NULL; 