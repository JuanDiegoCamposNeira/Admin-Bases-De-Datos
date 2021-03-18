-- Create the user
alter session set "_ORACLE_SCRIPT"=true;
CREATE USER USUARIO1 IDENTIFIED BY "USUARIOPRUEBA";
-- Grant acces 
GRANT DBA TO USUARIO1; 

-- Create second user
CREATE USER USUARIO2 IDENTIFIED BY "USUARIOPRUEBA";
-- Grant access
GRANT CREATE SESSION TO USUARIO2;
GRANT SELECT_CATALOG_ROLE TO USUARIO2;

--------------------------------------------------------------------------------
-----------------------------PUNTO 1--------------------------------------------
--------------------------------------------------------------------------------
--(lista del punto 1)
SELECT USERNAME FROM all_users;

--(1.a).
SELECT table_name FROM all_tables WHERE OWNER = 'USUARIO1';

--(1.a.i)
SELECT SEARCH_CONDITION FROM all_constraints WHERE OWNER = 'USUARIO1' AND TABLE_NAME='ESTUDIANTE' AND SEARCH_CONDITION IS NOT NULL;

--(1.a.ii)
SELECT COMMENTS FROM all_tab_comments WHERE OWNER='USUARIO1' AND TABLE_NAME = 'ESTUDIANTE' AND COMMENTS IS NOT NULL;

--(1.a.iii)
SELECT INDEX_NAME, TABLE_NAME, COLUMN_NAME  FROM ALL_IND_COLUMNS WHERE INDEX_OWNER = 'USUARIO1' AND TABLE_NAME = 'ESTUDIANTE';

--(1.a.iv)
SELECT COLUMN_NAME FROM all_tab_columns WHERE OWNER = 'USUARIO1' AND TABLE_NAME = 'ESTUDIANTE';

--(1.a.iv.2)
SELECT DATA_TYPE FROM all_tab_columns WHERE OWNER = 'USUARIO1' AND TABLE_NAME = 'ESTUDIANTE' AND COLUMN_NAME = 'NOMBRES';

--(1.a.iv.2)
SELECT COMMENTS FROM all_col_comments WHERE OWNER = 'USUARIO1' AND TABLE_NAME='ESTUDIANTE' AND COLUMN_NAME = 'NOMBRES' AND COMMENTS IS NOT NULL;

--------------------------------------------------------------------------------
-----------------------------PUNTO 2--------------------------------------------
--------------------------------------------------------------------------------
--Conseguir los objetos de un usuario especifico, PENSÃ‰ QUE SERVÃ?A PERO NO, IGUAL LA DEJO POR SI LA QUIEREN MIRAR
--select * from ALL_OBJECTS WHERE OWNER ='USUARIO1';

--2 PARA LAS TABLAS
SELECT TABLE_NAME, PRIVILEGE, OWNER FROM USER_TAB_PRIVS WHERE grantee='USUARIO1';

--2 PARA LAS COLUMNAS
SELECT COLUMN_NAME, PRIVILEGE, OWNER FROM USER_COL_PRIVS WHERE grantee='USUARIO1' AND TABLE_NAME = 'MY_TABLE2';

--------------------------------------------------------------------------------
-----------------------------PUNTO 3--------------------------------------------
--------------------------------------------------------------------------------
--Informacion solicitada para el job, SOLO SE DEBEN MOSTRAR LOS QUE ESTAN HABILITADOS
--------------------------------------------------------------------------------
SELECT OWNER, JOB_NAME, JOB_CLASS, COMMENTS, ENABLED, CREDENTIAL_NAME, DESTINATION,
    PROGRAM_NAME, JOB_TYPE, JOB_ACTION, NUMBER_OF_ARGUMENTS, SCHEDULE_OWNER, SCHEDULE_NAME,
    SCHEDULE_TYPE, START_DATE, REPEAT_INTERVAL, END_DATE
FROM ALL_SCHEDULER_JOBS;

--Deshabilitar el job
EXECUTE dbms_scheduler.disable('ORACLE_OCM.MGMT_CONFIG_JOB');
--Habilitar el job
EXECUTE dbms_scheduler.enable('ORACLE_OCM.MGMT_CONFIG_JOB');

--------------------------------------------------------------------------------
-----------------------------PUNTO 4--------------------------------------------
--------------------------------------------------------------------------------
--Informacion de los paquetes de un usuario en especifico
SELECT OBJECT_NAME, STATUS FROM ALL_OBJECTS WHERE OWNER ='USUARIO1' AND OBJECT_TYPE = 'PACKAGE';

--Informacion de las funciones de un usuario en especifico
SELECT OBJECT_NAME, STATUS FROM ALL_OBJECTS WHERE OWNER ='USUARIO1' AND OBJECT_TYPE = 'FUNCTION';

--Informacion de los procedimientos de un usuario en especifico
SELECT OBJECT_NAME, STATUS FROM ALL_OBJECTS WHERE OWNER ='USUARIO1' AND OBJECT_TYPE = 'PROCEDURE';

--------------------------------------------------------------------------------
-----------------------------PUNTO 5--------------------------------------------
--------------------------------------------------------------------------------
--Espacio total
SELECT TABLESPACE_NAME, sum(BYTES) FROM dba_data_files group by TABLESPACE_NAME;

--Espacio libre
SELECT TABLESPACE_NAME, sum(BYTES) FROM dba_free_space group by TABLESPACE_NAME;

--------------------------------------------------------------------------------
-----------------------------PUNTO 6--------------------------------------------
--------------------------------------------------------------------------------
SELECT SUM(BYTES) FROM USER_EXTENTS;