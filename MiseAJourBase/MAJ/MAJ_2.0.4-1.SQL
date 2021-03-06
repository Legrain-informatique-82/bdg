CREATE or ALTER  PROCEDURE GESTION_CREATION_GENERATOR1
as    
DECLARE VARIABLE QUERY VARCHAR(255);
BEGIN
query = '';
IF (not exists( SELECT a.RDB$GENERATOR_NAME
FROM RDB$GENERATORS a
where a.RDB$GENERATOR_NAME='NUM_ID_RQT')) THEN
  begin
    query = 'CREATE GENERATOR NUM_ID_RQT';
    EXECUTE STATEMENT :query;
    
    query = 'SET GENERATOR NUM_ID_RQT TO 0';
    EXECUTE STATEMENT :query;
  end
END
^
EXECUTE PROCEDURE GESTION_CREATION_GENERATOR1 
^
drop procedure GESTION_CREATION_GENERATOR1
^


CREATE or ALTER  PROCEDURE GESTION_CREATION_GENERATOR2
as    
DECLARE VARIABLE QUERY VARCHAR(255);
BEGIN
query = '';
IF (not exists( SELECT a.RDB$GENERATOR_NAME
FROM RDB$GENERATORS a
where a.RDB$GENERATOR_NAME='NUM_ID_LIGNE_RQT')) THEN
  begin
    query = 'CREATE GENERATOR NUM_ID_LIGNE_RQT';
    EXECUTE STATEMENT :query;
    
    query = 'SET GENERATOR NUM_ID_LIGNE_RQT TO 0';
    EXECUTE STATEMENT :query;
  end
END
^
EXECUTE PROCEDURE GESTION_CREATION_GENERATOR2 
^
drop procedure GESTION_CREATION_GENERATOR2
^


CREATE or ALTER  PROCEDURE CREATION_TABLE_TA_DEPARTEMENTS
as    
DECLARE VARIABLE QUERY VARCHAR(5000);
BEGIN
query = '';
IF (not exists( SELECT  a.RDB$RELATION_NAME
FROM RDB$RELATIONS a
where a.RDB$RELATION_NAME='TA_DEPARTEMENTS')) THEN
  begin
    query = 'CREATE TABLE TA_DEPARTEMENTS (
  NUMDEP Char(2) NOT NULL,
  CODEDEP Char(5) NOT NULL,
  NOMDEP Varchar(30) NOT NULL,
  CODEREGION Char(4) NOT NULL,
  NOMREGION Varchar(30) NOT NULL,
  QUI_CREE DLIB50,
  QUAND_CREE Timestamp DEFAULT ''NOW'',
  QUI_MODIF DLIB50,
  QUAND_MODIF Timestamp DEFAULT ''NOW'',
  "VERSION" NUM_VERSION,
  IP_ACCES DLIB50NN DEFAULT 0,
  VERSION_OBJ Integer
)';
    EXECUTE STATEMENT :query;
    
    query = 'ALTER TABLE TA_DEPARTEMENTS ADD PRIMARY KEY (NUMDEP)';
    EXECUTE STATEMENT :query;
    
  end
END
^
EXECUTE PROCEDURE CREATION_TABLE_TA_DEPARTEMENTS
^
drop procedure CREATION_TABLE_TA_DEPARTEMENTS
^


CREATE or ALTER  PROCEDURE CREATION_TABLE_TA_REQUETE
as    
DECLARE VARIABLE QUERY VARCHAR(5000);
BEGIN
query = '';
IF (not exists( SELECT  a.RDB$RELATION_NAME
FROM RDB$RELATIONS a
where a.RDB$RELATION_NAME='TA_REQUETE')) THEN
  begin
    query = 'CREATE TABLE TA_REQUETE (
    ID_RQT          DID3,
    LIB_RQT     	DLIB255,
	DESC_RQT		DLIB255,
	TYPE_RESULTAT	DID3
)';
    EXECUTE STATEMENT :query;
    
    query = 'ALTER TABLE TA_REQUETE ADD PRIMARY KEY (ID_RQT)';
    EXECUTE STATEMENT :query;
    
  end
END
^
EXECUTE PROCEDURE CREATION_TABLE_TA_REQUETE
^
drop procedure CREATION_TABLE_TA_REQUETE
^

CREATE or ALTER  PROCEDURE CREATION_TABLE_TA_LIGNE_REQUETE
as    
DECLARE VARIABLE QUERY VARCHAR(5000);
BEGIN
query = '';
IF (not exists( SELECT  a.RDB$RELATION_NAME
FROM RDB$RELATIONS a
where a.RDB$RELATION_NAME='TA_LIGNE_REQUETE')) THEN
  begin
    query = 'CREATE TABLE TA_LIGNE_REQUETE (
    ID          DID3,
    ID_RQT          DID3,
	ID_GROUPE		DID3,
	ANDOR_GROUPE	DLGR_2,
	ID_LIGNE		DID3,
	TYPE_LIGNE		DLIB50 NOT NULL,
	ANDOR_LIGNE		DLGR_2,
	COMBO_1			DLIB50 NOT NULL,
	COMBO_2			DLIB50 NOT NULL,
	VALEUR_1		DLIB50,
	VALEUR_2		DLIB50
	
)';
    EXECUTE STATEMENT :query;
    
    query = 'ALTER TABLE TA_LIGNE_REQUETE ADD PRIMARY KEY (ID)';
    EXECUTE STATEMENT :query;
      query = 'ALTER TABLE TA_LIGNE_REQUETE ADD FOREIGN KEY (ID_RQT) REFERENCES TA_REQUETE (ID_RQT)';
    EXECUTE STATEMENT :query;  
  end
END
^
EXECUTE PROCEDURE CREATION_TABLE_TA_LIGNE_REQUETE
^
drop procedure CREATION_TABLE_TA_LIGNE_REQUETE
^

/* Trigger: TBID_RQT */
CREATE or alter TRIGGER TBID_RQT FOR TA_REQUETE
ACTIVE BEFORE INSERT POSITION 1
as
begin
   If (New.ID_RQT  is null) Then
      New.ID_RQT  = GEN_ID(NUM_ID_RQT,1);
end
^

CREATE or alter TRIGGER TBID_LIGNE_RQT FOR TA_LIGNE_REQUETE
ACTIVE BEFORE INSERT POSITION 1
as
begin
   If (New.ID  is null) Then
      New.ID  = GEN_ID(NUM_ID_LIGNE_RQT,1);
end
^

