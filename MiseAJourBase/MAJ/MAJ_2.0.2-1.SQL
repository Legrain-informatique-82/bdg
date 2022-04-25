EXECUTE PROCEDURE MAJ_GENERATEUR
^
CREATE or ALTER  PROCEDURE GESTION_ALTER_TABLE01
as    
DECLARE VARIABLE QUERY VARCHAR(255);
BEGIN
query ='';
IF (not exists( SELECT a.RDB$FIELD_NAME, a.RDB$RELATION_NAME FROM RDB$RELATION_FIELDS a
where a.RDB$RELATION_NAME = 'TA_R_DOCUMENT' and  a.RDB$FIELD_NAME ='ID_ACOMPTE')) THEN
  begin
    query = 'ALTER TABLE TA_R_DOCUMENT ADD ID_ACOMPTE DID_FACULTATIF';
    EXECUTE STATEMENT :query;
  end
END
^
EXECUTE PROCEDURE GESTION_ALTER_TABLE01
^
drop procedure GESTION_ALTER_TABLE01
^



CREATE or ALTER  PROCEDURE GESTION_ALTER_TABLE02
as    
DECLARE VARIABLE QUERY VARCHAR(255);
BEGIN
query ='';
IF (not exists( SELECT a.RDB$FIELD_NAME, a.RDB$RELATION_NAME FROM RDB$RELATION_FIELDS a
where a.RDB$RELATION_NAME = 'TA_COMPL' and  a.RDB$FIELD_NAME ='ACCISE')) THEN
  begin
    query = 'ALTER TABLE TA_COMPL ADD ACCISE varchar(13)';
    EXECUTE STATEMENT :query;
  end
END
^
EXECUTE PROCEDURE GESTION_ALTER_TABLE02
^
drop procedure GESTION_ALTER_TABLE02
^

alter TABLE TA_COMPL alter ACCISE position 4 
^

/****************************************************************************************************************************/

/* NOTE TIERS */
CREATE or ALTER  PROCEDURE GESTION_CREATION_GENERATOR1
as    
DECLARE VARIABLE QUERY VARCHAR(255);
BEGIN
query = '';
IF (not exists( SELECT a.RDB$GENERATOR_NAME
FROM RDB$GENERATORS a
where a.RDB$GENERATOR_NAME='NUM_ID_T_NOTE_TIERS')) THEN
  begin
    query = 'CREATE GENERATOR NUM_ID_T_NOTE_TIERS;';
    EXECUTE STATEMENT :query;
    
    query = 'SET GENERATOR NUM_ID_T_NOTE_TIERS TO 0;';
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
where a.RDB$GENERATOR_NAME='NUM_ID_NOTE_TIERS')) THEN
  begin
    query = 'CREATE GENERATOR NUM_ID_NOTE_TIERS;';
    EXECUTE STATEMENT :query;
    
    query = 'SET GENERATOR NUM_ID_NOTE_TIERS TO 0;';
    EXECUTE STATEMENT :query;
  end
END
^
EXECUTE PROCEDURE GESTION_CREATION_GENERATOR2 
^
drop procedure GESTION_CREATION_GENERATOR2
^

CREATE or ALTER  PROCEDURE GESTION_CREATION_TABLE1
as    
DECLARE VARIABLE QUERY VARCHAR(5000);
BEGIN
query = '';
IF (not exists( SELECT  a.RDB$RELATION_NAME
FROM RDB$RELATIONS a
where a.RDB$RELATION_NAME='TA_T_NOTE_TIERS')) THEN
  begin
    query = 'CREATE TABLE TA_T_NOTE_TIERS (
    ID_T_NOTE_TIERS           DID3 NOT NULL,
    CODE_T_NOTE_TIERS         DLGR_CODE,
    LIBL_T_NOTE_TIERS         DLGR_LIB,
    QUI_CREE_T_NOTE     DLIB50,
    QUAND_CREE_T_NOTE   TIMESTAMP DEFAULT ''NOW'',
    QUI_MODIF_T_NOTE    DLIB50,
    QUAND_MODIF_T_NOTE  TIMESTAMP DEFAULT ''NOW'',
    "VERSION"          NUM_VERSION,
    IP_ACCES           DLIB50NN DEFAULT 0,
    VERSION_OBJ        INTEGER
    )';
    EXECUTE STATEMENT :query;
    
    query = 'ALTER TABLE TA_T_NOTE_TIERS ADD CONSTRAINT UNQ1_TA_T_NOTE_TIERS UNIQUE (CODE_T_NOTE_TIERS)';
    EXECUTE STATEMENT :query;
    
    query = 'ALTER TABLE TA_T_NOTE_TIERS ADD PRIMARY KEY (ID_T_NOTE_TIERS)';
    EXECUTE STATEMENT :query;
    
    query = 'CREATE INDEX TA_T_NOTE_CODE_TIERS ON TA_T_NOTE_TIERS (CODE_T_NOTE_TIERS)';
    EXECUTE STATEMENT :query;
  end
END
^
EXECUTE PROCEDURE GESTION_CREATION_TABLE1 
^
drop procedure GESTION_CREATION_TABLE1
^

CREATE or ALTER  PROCEDURE GESTION_CREATION_TABLE2
as    
DECLARE VARIABLE QUERY VARCHAR(5000);
BEGIN
query = '';
IF (not exists( SELECT  a.RDB$RELATION_NAME
FROM RDB$RELATIONS a
where a.RDB$RELATION_NAME='TA_NOTE_TIERS')) THEN
  begin
    query = 'CREATE TABLE TA_NOTE_TIERS (
    ID_NOTE_TIERS           DID3 NOT NULL,
    NOTE_TIERS      DLIB255NN,
    ID_TIERS         DID_FACULTATIF,
    ID_T_NOTE_TIERS         DID_FACULTATIF,
    DATE_NOTE_TIERS        DATE_LGR DEFAULT ''NOW'',
    QUI_CREE_NOTE     DLIB50,
    QUAND_CREE_NOTE   TIMESTAMP DEFAULT ''NOW'',
    QUI_MODIF_NOTE    DLIB50,
    QUAND_MODIF_NOTE  TIMESTAMP DEFAULT ''NOW'',
    "VERSION"        NUM_VERSION,
    IP_ACCES         DLIB50NN DEFAULT 0,
    VERSION_OBJ      INTEGER
    )';
    EXECUTE STATEMENT :query;
    
    query = 'ALTER TABLE TA_NOTE_TIERS ADD PRIMARY KEY (ID_NOTE_TIERS)';
    EXECUTE STATEMENT :query;
    
    query = 'ALTER TABLE TA_NOTE_TIERS ADD CONSTRAINT FK_TA_NOTE_1 FOREIGN KEY (ID_T_NOTE_TIERS) REFERENCES TA_T_NOTE_TIERS (ID_T_NOTE_TIERS)';
    EXECUTE STATEMENT :query;
    
    query = 'ALTER TABLE TA_NOTE_TIERS ADD CONSTRAINT FK_TA_NOTE_2 FOREIGN KEY (ID_TIERS) REFERENCES TA_TIERS (ID_TIERS)';
    EXECUTE STATEMENT :query;
  end
END
^
EXECUTE PROCEDURE GESTION_CREATION_TABLE2
^
drop procedure GESTION_CREATION_TABLE2
^

CREATE OR ALTER TRIGGER TBI_TA_T_NOTE_TIERS FOR TA_T_NOTE_TIERS
BEFORE INSERT POSITION 0
as
begin
   If (New.ID_T_NOTE_TIERS is null) Then
      New.ID_T_NOTE_TIERS = GEN_ID(NUM_ID_T_NOTE_TIERS,1);
   New.CODE_T_NOTE_TIERS = Upper(New.CODE_T_NOTE_TIERS);
   New.QUI_CREE_T_NOTE = USER;
   new.QUAND_CREE_T_NOTE = 'NOW';
   new.QUI_MODIF_T_NOTE = USER;
   new.QUAND_MODIF_T_NOTE = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^

CREATE OR ALTER TRIGGER TBU_TA_T_NOTE_TIERS FOR TA_T_NOTE_TIERS
BEFORE UPDATE POSITION 0
as
begin
   New.CODE_T_NOTE_TIERS = Upper(New.CODE_T_NOTE_TIERS);
   new.QUI_MODIF_T_NOTE = USER;
   new.QUAND_MODIF_T_NOTE = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^

CREATE OR ALTER TRIGGER TBI_TA_NOTE_TIERS FOR TA_NOTE_TIERS ACTIVE
BEFORE INSERT POSITION 0
as
begin
   If (New.ID_NOTE_TIERS is null) Then
      New.ID_NOTE_TIERS = GEN_ID(NUM_ID_NOTE_TIERS,1);
   New.QUI_CREE_NOTE = USER;
   new.QUAND_CREE_NOTE = 'NOW';
   new.QUI_MODIF_NOTE = USER;
   new.QUAND_MODIF_NOTE = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^

CREATE OR ALTER TRIGGER TBU_TA_NOTE_TIERS FOR TA_NOTE_TIERS ACTIVE
BEFORE UPDATE POSITION 0
as
begin
   new.QUI_MODIF_NOTE = USER;
   new.QUAND_MODIF_NOTE = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^

/* NOTE ARTICLE */
CREATE or ALTER  PROCEDURE GESTION_CREATION_GENERATOR3
as    
DECLARE VARIABLE QUERY VARCHAR(255);
BEGIN
query = '';
IF (not exists( SELECT a.RDB$GENERATOR_NAME
FROM RDB$GENERATORS a
where a.RDB$GENERATOR_NAME='NUM_ID_NOTE_ARTICLE')) THEN
  begin
    query = 'CREATE GENERATOR NUM_ID_NOTE_ARTICLE;';
    EXECUTE STATEMENT :query;
    
    query = 'SET GENERATOR NUM_ID_NOTE_ARTICLE TO 0;';
    EXECUTE STATEMENT :query;
  end
END
^
EXECUTE PROCEDURE GESTION_CREATION_GENERATOR3 
^
drop procedure GESTION_CREATION_GENERATOR3
^

CREATE or ALTER  PROCEDURE GESTION_CREATION_GENERATOR4
as    
DECLARE VARIABLE QUERY VARCHAR(255);
BEGIN
query = '';
IF (not exists( SELECT a.RDB$GENERATOR_NAME
FROM RDB$GENERATORS a
where a.RDB$GENERATOR_NAME='NUM_ID_T_NOTE_ARTICLE')) THEN
  begin
    query = 'CREATE GENERATOR NUM_ID_T_NOTE_ARTICLE;';
    EXECUTE STATEMENT :query;
    
    query = 'SET GENERATOR NUM_ID_T_NOTE_ARTICLE TO 0;';
    EXECUTE STATEMENT :query;
  end
END
^
EXECUTE PROCEDURE GESTION_CREATION_GENERATOR4 
^
drop procedure GESTION_CREATION_GENERATOR4
^

CREATE or ALTER  PROCEDURE GESTION_CREATION_TABLE3
as    
DECLARE VARIABLE QUERY VARCHAR(5000);
BEGIN
query = '';
IF (not exists( SELECT  a.RDB$RELATION_NAME
FROM RDB$RELATIONS a
where a.RDB$RELATION_NAME='TA_T_NOTE_ARTICLE')) THEN
  begin
    query = 'CREATE TABLE TA_T_NOTE_ARTICLE (
    ID_T_NOTE_ARTICLE           DID3 NOT NULL,
    CODE_T_NOTE_ARTICLE         DLGR_CODE,
    LIBL_T_NOTE_ARTICLE         DLGR_LIB,
    QUI_CREE_T_NOTE     DLIB50,
    QUAND_CREE_T_NOTE   TIMESTAMP DEFAULT ''NOW'',
    QUI_MODIF_T_NOTE    DLIB50,
    QUAND_MODIF_T_NOTE  TIMESTAMP DEFAULT ''NOW'',
    "VERSION"          NUM_VERSION,
    IP_ACCES           DLIB50NN DEFAULT 0,
    VERSION_OBJ        INTEGER
    )';
    EXECUTE STATEMENT :query;
    
    query = 'ALTER TABLE TA_T_NOTE_ARTICLE ADD CONSTRAINT UNQ1_TA_T_NOTE_ARTICLE UNIQUE (CODE_T_NOTE_ARTICLE)';
    EXECUTE STATEMENT :query;
    
    query = 'ALTER TABLE TA_T_NOTE_ARTICLE ADD PRIMARY KEY (ID_T_NOTE_ARTICLE)';
    EXECUTE STATEMENT :query;
    
    query = 'CREATE INDEX TA_T_NOTE_CODE_ARTICLE ON TA_T_NOTE_ARTICLE (CODE_T_NOTE_ARTICLE)';
    EXECUTE STATEMENT :query;
  end
END
^
EXECUTE PROCEDURE GESTION_CREATION_TABLE3
^
drop procedure GESTION_CREATION_TABLE3
^

CREATE or ALTER  PROCEDURE GESTION_CREATION_TABLE4
as    
DECLARE VARIABLE QUERY VARCHAR(5000);
BEGIN
query = '';
IF (not exists( SELECT  a.RDB$RELATION_NAME
FROM RDB$RELATIONS a
where a.RDB$RELATION_NAME='TA_NOTE_ARTICLE')) THEN
  begin
    query = 'CREATE TABLE TA_NOTE_ARTICLE (
    ID_NOTE_ARTICLE           DID3 NOT NULL,
    NOTE_ARTICLE      DLIB255NN,
    ID_ARTICLE         DID_FACULTATIF,
    ID_T_NOTE_ARTICLE         DID_FACULTATIF,
    DATE_NOTE_ARTICLE        DATE_LGR DEFAULT ''NOW'',
    QUI_CREE_NOTE     DLIB50,
    QUAND_CREE_NOTE   TIMESTAMP DEFAULT ''NOW'',
    QUI_MODIF_NOTE    DLIB50,
    QUAND_MODIF_NOTE  TIMESTAMP DEFAULT ''NOW'',
    "VERSION"        NUM_VERSION,
    IP_ACCES         DLIB50NN DEFAULT 0,
    VERSION_OBJ      INTEGER
    )';
    EXECUTE STATEMENT :query;
    
    query = 'ALTER TABLE TA_NOTE_ARTICLE ADD PRIMARY KEY (ID_NOTE_ARTICLE)';
    EXECUTE STATEMENT :query;
    
    query = 'ALTER TABLE TA_NOTE_ARTICLE ADD CONSTRAINT FK_TA_NOTE_ARTICLE_1 FOREIGN KEY (ID_T_NOTE_ARTICLE) REFERENCES TA_T_NOTE_ARTICLE (ID_T_NOTE_ARTICLE)';
    EXECUTE STATEMENT :query;
    
    query = 'ALTER TABLE TA_NOTE_ARTICLE ADD CONSTRAINT FK_TA_NOTE_ARTICLE_2 FOREIGN KEY (ID_ARTICLE) REFERENCES TA_ARTICLE (ID_ARTICLE)';
    EXECUTE STATEMENT :query;
  end
END
^
EXECUTE PROCEDURE GESTION_CREATION_TABLE4
^
drop procedure GESTION_CREATION_TABLE4
^

CREATE OR ALTER TRIGGER TBI_TA_T_NOTE_ARTICLE FOR TA_T_NOTE_ARTICLE
BEFORE INSERT POSITION 0
as
begin
   If (New.ID_T_NOTE_ARTICLE is null) Then
      New.ID_T_NOTE_ARTICLE = GEN_ID(NUM_ID_T_NOTE_ARTICLE,1);
   New.CODE_T_NOTE_ARTICLE = Upper(New.CODE_T_NOTE_ARTICLE);
   New.QUI_CREE_T_NOTE = USER;
   new.QUAND_CREE_T_NOTE = 'NOW';
   new.QUI_MODIF_T_NOTE = USER;
   new.QUAND_MODIF_T_NOTE = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^

CREATE OR ALTER TRIGGER TBU_TA_T_NOTE_ARTICLE FOR TA_T_NOTE_ARTICLE
BEFORE UPDATE POSITION 0
as
begin
   New.CODE_T_NOTE_ARTICLE = Upper(New.CODE_T_NOTE_ARTICLE);
   new.QUI_MODIF_T_NOTE = USER;
   new.QUAND_MODIF_T_NOTE = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^

CREATE OR ALTER TRIGGER TBI_TA_NOTE_ARTICLE FOR TA_NOTE_ARTICLE ACTIVE
BEFORE INSERT POSITION 0
as
begin
   If (New.ID_NOTE_ARTICLE is null) Then
      New.ID_NOTE_ARTICLE = GEN_ID(NUM_ID_NOTE_ARTICLE,1);
   New.QUI_CREE_NOTE = USER;
   new.QUAND_CREE_NOTE = 'NOW';
   new.QUI_MODIF_NOTE = USER;
   new.QUAND_MODIF_NOTE = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^

CREATE OR ALTER TRIGGER TBU_TA_NOTE_ARTICLE FOR TA_NOTE_ARTICLE ACTIVE
BEFORE UPDATE POSITION 0
as
begin
   new.QUI_MODIF_NOTE = USER;
   new.QUAND_MODIF_NOTE = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/****************************************************************************************************************************/

/* GESTION DES ACOMPTES */


CREATE or ALTER  PROCEDURE GESTION_ALTER_TABLE03
as    
DECLARE VARIABLE QUERY VARCHAR(255);
BEGIN
query ='';
IF (not exists( SELECT a.RDB$FIELD_NAME, a.RDB$RELATION_NAME FROM RDB$RELATION_FIELDS a
where a.RDB$RELATION_NAME = 'TA_FACTURE' and  a.RDB$FIELD_NAME ='ACOMPTES')) THEN
  begin
    query = 'ALTER TABLE TA_FACTURE ADD ACOMPTES DID9FACULT';
    EXECUTE STATEMENT :query;
  end
  
IF (not exists( SELECT a.RDB$FIELD_NAME, a.RDB$RELATION_NAME FROM RDB$RELATION_FIELDS a
where a.RDB$RELATION_NAME = 'TA_FACTURE' and  a.RDB$FIELD_NAME ='RESTE_A_REGLER')) THEN
  begin
    query = 'ALTER TABLE TA_FACTURE ADD RESTE_A_REGLER DID9FACULT';
    EXECUTE STATEMENT :query;
  end
IF (not exists( SELECT a.RDB$FIELD_NAME, a.RDB$RELATION_NAME FROM RDB$RELATION_FIELDS a
where a.RDB$RELATION_NAME = 'TA_FACTURE' and  a.RDB$FIELD_NAME ='LIBELLE_PAIEMENT')) THEN
  begin
    query = 'ALTER TABLE TA_FACTURE ADD LIBELLE_PAIEMENT DLIB255';
    EXECUTE STATEMENT :query;
  end  
END
^
EXECUTE PROCEDURE GESTION_ALTER_TABLE03
^
drop procedure GESTION_ALTER_TABLE03
^



CREATE or ALTER  PROCEDURE GESTION_CREATION_GENERATOR5
as    
DECLARE VARIABLE QUERY VARCHAR(255);
BEGIN
query = '';
IF (not exists( SELECT a.RDB$GENERATOR_NAME
FROM RDB$GENERATORS a
where a.RDB$GENERATOR_NAME='NUM_ID_R_ACOMPTE')) THEN
  begin
    query = 'CREATE GENERATOR NUM_ID_R_ACOMPTE';
    EXECUTE STATEMENT :query;
    
    query = 'SET GENERATOR NUM_ID_R_ACOMPTE TO 0';
    EXECUTE STATEMENT :query;
  end
END
^
EXECUTE PROCEDURE GESTION_CREATION_GENERATOR5 
^
drop procedure GESTION_CREATION_GENERATOR5
^

CREATE or ALTER  PROCEDURE GESTION_CREATION_TABLE5
as    
DECLARE VARIABLE QUERY VARCHAR(5000);
BEGIN
query = '';
IF (not exists( SELECT  a.RDB$RELATION_NAME
FROM RDB$RELATIONS a
where a.RDB$RELATION_NAME='TA_ACOMPTE')) THEN
  begin
    query = 'CREATE TABLE TA_ACOMPTE (
    ID_DOCUMENT                  DID3 NOT NULL ,
    CODE_DOCUMENT                DLGR_CODE ,
    DATE_DOCUMENT                DATE_LGR DEFAULT ''NOW''  ,
    DATE_ECH_DOCUMENT            DATE_LGR DEFAULT ''NOW'',
    DATE_LIV_DOCUMENT            DATE_LGR DEFAULT ''NOW'',
    LIBELLE_DOCUMENT             DLIB255NN  ,
    ID_TIERS                     DID_FACULTATIF  ,
    ID_T_PAIEMENT                DID_FACULTATIF  ,
    REGLE_DOCUMENT               DID9 DEFAULT 0,
    REM_HT_DOCUMENT              DID9 DEFAULT 0  ,
    TX_REM_HT_DOCUMENT           DID9 DEFAULT 0  ,
    REM_TTC_DOCUMENT             DID9 DEFAULT 0  ,
    TX_REM_TTC_DOCUMENT          DID9 DEFAULT 0  ,
    NB_E_DOCUMENT                DID_FACULTATIF DEFAULT 0  ,
    TTC                          DBOOL DEFAULT 0  ,
    EXPORT                       DBOOL DEFAULT 0  ,
    COMMENTAIRE                  DLIB_COMMENTAIRE  ,
    MT_TTC_CALC                  DID9FACULT  ,
    MT_HT_CALC                   DID9FACULT  ,
    MT_TVA_CALC                  DID9FACULT  ,
    NET_TTC_CALC                 DID9FACULT  ,
    NET_HT_CALC                  DID9FACULT  ,
    NET_TVA_CALC                 DID9FACULT  ,
    NET_A_PAYER                  DID9FACULT  ,
    MT_TTC_AVT_REM_GLOBALE_CALC  DID9FACULT  ,
	RESTE_A_REGLER               DID9FACULT ,    
	QUI_CREE_DOCUMENT            DLIB50  ,
    QUAND_CREE_DOCUMENT          TIMESTAMP DEFAULT ''NOW'',
    QUI_MODIF_DOCUMENT           DLIB50  ,
    QUAND_MODIF_DOCUMENT         TIMESTAMP DEFAULT ''NOW'',
    "VERSION"                    NUM_VERSION  ,
    IP_ACCES                     DLIB50NN DEFAULT 0  ,
    VERSION_OBJ                  INTEGER
)';
    EXECUTE STATEMENT :query;
    
    query = 'ALTER TABLE TA_ACOMPTE ADD CONSTRAINT UNQ1_TA_ACOMPTE UNIQUE (CODE_DOCUMENT)';
    EXECUTE STATEMENT :query;
    
    query = 'ALTER TABLE TA_ACOMPTE ADD PRIMARY KEY (ID_DOCUMENT)';
    EXECUTE STATEMENT :query;
    
    query = 'ALTER TABLE TA_ACOMPTE ADD FOREIGN KEY (ID_TIERS) REFERENCES TA_TIERS (ID_TIERS)';
    EXECUTE STATEMENT :query;
    
    query = 'ALTER TABLE TA_ACOMPTE ADD FOREIGN KEY (ID_T_PAIEMENT) REFERENCES TA_T_PAIEMENT (ID_T_PAIEMENT)';
    EXECUTE STATEMENT :query;
    
    query = 'CREATE INDEX TA_ACOMPTE_CODE ON TA_ACOMPTE (CODE_DOCUMENT)';
    EXECUTE STATEMENT :query;
    
    query = 'CREATE INDEX TA_ACOMPTE_ID_TIERS ON TA_ACOMPTE (ID_TIERS)';
    EXECUTE STATEMENT :query;
  end
END
^
EXECUTE PROCEDURE GESTION_CREATION_TABLE5 
^
drop procedure GESTION_CREATION_TABLE5
^


CREATE or ALTER  PROCEDURE GESTION_CREATION_TABLE6
as    
DECLARE VARIABLE QUERY VARCHAR(5000);
BEGIN
query = '';
IF (not exists( SELECT  a.RDB$RELATION_NAME
FROM RDB$RELATIONS a
where a.RDB$RELATION_NAME='TA_L_ACOMPTE')) THEN
  begin
    query = 'CREATE TABLE TA_L_ACOMPTE (
    ID_L_DOCUMENT           DID3 NOT NULL ,
    ID_DOCUMENT             DID_FACULTATIF  ,
    ID_T_LIGNE              DID3  ,
    ID_ARTICLE              DID_FACULTATIF  ,
    NUM_LIGNE_L_DOCUMENT    DID3  ,
    LIB_L_DOCUMENT          DLIB255  ,
    QTE_L_DOCUMENT          DID9FACULT ,
    QTE2_L_DOCUMENT         DID9FACULT ,
    U1_L_DOCUMENT           DLIB20  ,
    U2_L_DOCUMENT           DLIB20  ,
    PRIX_U_L_DOCUMENT       DID9FACULT ,
    TAUX_TVA_L_DOCUMENT     DLONG  ,
    COMPTE_L_DOCUMENT       DLIB8  ,
    CODE_TVA_L_DOCUMENT     DLIB20  ,
    CODE_T_TVA_L_DOCUMENT   DLIB1L  ,
    MT_HT_L_DOCUMENT        DID9FACULT  ,
    MT_TTC_L_DOCUMENT       DID9FACULT  ,
    REM_TX_L_DOCUMENT       DID9FACULT  ,
    REM_HT_L_DOCUMENT       DID9FACULT  ,
    MT_HT_APR_REM_GLOBALE   DID9FACULT  ,
    MT_TTC_APR_REM_GLOBALE  DID9FACULT  ,
    QUI_CREE_L_DOCUMENT     DLIB50  ,
    QUAND_CREE_L_DOCUMENT   TIMESTAMP DEFAULT ''NOW'',
    QUI_MODIF_L_DOCUMENT    DLIB50  ,
    QUAND_MODIF_L_DOCUMENT  TIMESTAMP DEFAULT ''NOW'',
    "VERSION"               NUM_VERSION  ,
    IP_ACCES                DLIB50NN DEFAULT 0  ,
    VERSION_OBJ             INTEGER
)';
    EXECUTE STATEMENT :query;
    
    query = 'ALTER TABLE TA_L_ACOMPTE ADD PRIMARY KEY (ID_L_DOCUMENT)';
    EXECUTE STATEMENT :query;
    
    query = 'ALTER TABLE TA_L_ACOMPTE ADD CONSTRAINT FK_TA_L_ACOMPTE_1 FOREIGN KEY (ID_DOCUMENT) REFERENCES TA_ACOMPTE (ID_DOCUMENT)';
    EXECUTE STATEMENT :query;
    
    query = ' ALTER TABLE TA_L_ACOMPTE ADD CONSTRAINT FK_TA_L_ACOMPTE_2 FOREIGN KEY (ID_ARTICLE) REFERENCES TA_ARTICLE (ID_ARTICLE)';
    EXECUTE STATEMENT :query;
    
    query = 'ALTER TABLE TA_L_ACOMPTE ADD FOREIGN KEY (ID_T_LIGNE) REFERENCES TA_T_LIGNE (ID_T_LIGNE)';
    EXECUTE STATEMENT :query;
    
    query = 'CREATE INDEX TA_L_ACOMPTE_ID_ACOMPTE ON TA_L_ACOMPTE (ID_DOCUMENT)';
    EXECUTE STATEMENT :query;
    
    query = 'CREATE INDEX TA_L_ACOMPTE_ID_T_LIGNE ON TA_L_ACOMPTE (ID_T_LIGNE)';
    EXECUTE STATEMENT :query;
  end
END
^
EXECUTE PROCEDURE GESTION_CREATION_TABLE6 
^
drop procedure GESTION_CREATION_TABLE6
^

CREATE or ALTER  PROCEDURE GESTION_CREATION_TABLE7
as    
DECLARE VARIABLE QUERY VARCHAR(5000);
BEGIN
query = '';
IF (not exists( SELECT  a.RDB$RELATION_NAME
FROM RDB$RELATIONS a
where a.RDB$RELATION_NAME='TA_INFOS_ACOMPTE')) THEN
  begin
    query = 'CREATE TABLE TA_INFOS_ACOMPTE (
    ID_INFOS_DOCUMENT           DID3 NOT NULL ,
    ID_DOCUMENT                 DID_FACULTATIF ,
    ADRESSE1                    DLIB100,
    ADRESSE2                    DLIB100 ,
    ADRESSE3                    DLIB100,
    CODEPOSTAL                  DCODPOS ,
    VILLE                       DLIB100 ,
    PAYS                        DLIB100 ,
    ADRESSE1_LIV                DLIB100 ,
    ADRESSE2_LIV                DLIB100 ,
    ADRESSE3_LIV                DLIB100 ,
    CODEPOSTAL_LIV              DCODPOS ,
    VILLE_LIV                   DLIB100 ,
    PAYS_LIV                    DLIB100 ,
    CODE_COMPTA                 DLIB8NN ,
    COMPTE                      DLIB8NN ,
    NOM_TIERS                   DLIB100 ,
    PRENOM_TIERS                DLIB100,
    SURNOM_TIERS                DLIB20 ,
    CODE_T_CIVILITE             DLGR_CODEL ,
    CODE_T_ENTITE               DLGR_CODEL ,
    TVA_I_COM_COMPL             DLIB50 ,
    CODE_C_PAIEMENT             DLGR_CODEL ,
    LIB_C_PAIEMENT              DLIB255 ,
    REPORT_C_PAIEMENT           DID_FACULTATIF ,
    FIN_MOIS_C_PAIEMENT         DID_FACULTATIF ,
    LIBL_ENTREPRISE             DLIB100 ,
    NOM_ENTREPRISE              DLIB100 ,
    QUI_CREE_INFOS_DOCUMENT     DLIB50 ,
    QUAND_CREE_INFOS_DOCUMENT   TIMESTAMP DEFAULT ''NOW'',
    QUI_MODIF_INFOS_DOCUMENT    DLIB50 ,
    QUAND_MODIF_INFOS_DOCUMENT  TIMESTAMP DEFAULT ''NOW'',
    "VERSION"                   NUM_VERSION ,
    IP_ACCES                    DLIB50NN DEFAULT 0 ,
    VERSION_OBJ                 INTEGER
)';
    EXECUTE STATEMENT :query;
    
    query = 'ALTER TABLE TA_INFOS_ACOMPTE ADD PRIMARY KEY (ID_INFOS_DOCUMENT)';
    EXECUTE STATEMENT :query;
    
    query = 'ALTER TABLE TA_INFOS_ACOMPTE ADD CONSTRAINT FK_TA_INFOS_ACOMPTE_1 FOREIGN KEY (ID_DOCUMENT) REFERENCES TA_ACOMPTE (ID_DOCUMENT)';
    EXECUTE STATEMENT :query;
    
    query = ' CREATE INDEX TA_INFOS_ACOMPTE_ID_ACOMPTE ON TA_INFOS_ACOMPTE (ID_DOCUMENT)';
    EXECUTE STATEMENT :query;
    
  end
END
^
EXECUTE PROCEDURE GESTION_CREATION_TABLE7 
^
drop procedure GESTION_CREATION_TABLE7
^





CREATE or ALTER  PROCEDURE GESTION_CREATION_TABLE8
as    
DECLARE VARIABLE QUERY VARCHAR(5000);
BEGIN
query = '';
IF (not exists( SELECT  a.RDB$RELATION_NAME
FROM RDB$RELATIONS a
where a.RDB$RELATION_NAME='TA_R_ACOMPTE')) THEN
  begin
    query = 'CREATE TABLE TA_R_ACOMPTE (
    ID            INTEGER NOT NULL,
    ID_ACOMPTE    DID_FACULTATIF ,
    ID_FACTURE    DID_FACULTATIF ,
    ID_DEVIS      DID_FACULTATIF ,
    ID_BONCDE     DID_FACULTATIF ,
    ID_PROFORMA   DID_FACULTATIF  ,
    ID_APPORTEUR  DID_FACULTATIF ,
    ID_AVOIR      DID_FACULTATIF  ,
    ID_BONLIV     DID_FACULTATIF  ,
    AFFECTATION   DID9FACULT  ,
    ETAT          BIGINT,
    QUI_CREE      DLIB50 ,
    QUAND_CREE    TIMESTAMP DEFAULT ''NOW'',
    QUI_MODIF     DLIB50  ,
    QUAND_MODIF   TIMESTAMP DEFAULT ''NOW'',
    "VERSION"     NUM_VERSION  ,
    IP_ACCES      DLIB50NN DEFAULT 0  ,
    VERSION_OBJ   INTEGER
)';
    EXECUTE STATEMENT :query;
        
    query = 'ALTER TABLE TA_R_ACOMPTE ADD CONSTRAINT PK_TA_R_ACOMPTE PRIMARY KEY (ID)';
    EXECUTE STATEMENT :query;
    
    query = 'ALTER TABLE TA_R_ACOMPTE ADD CONSTRAINT FK_TA_R_ACOMPTE_1 FOREIGN KEY (ID_FACTURE) REFERENCES TA_FACTURE (ID_DOCUMENT)';
    EXECUTE STATEMENT :query;
    
    query = 'ALTER TABLE TA_R_ACOMPTE ADD CONSTRAINT FK_TA_R_ACOMPTE_2 FOREIGN KEY (ID_DEVIS) REFERENCES TA_DEVIS (ID_DOCUMENT)';
    EXECUTE STATEMENT :query;

    query = 'ALTER TABLE TA_R_ACOMPTE ADD CONSTRAINT FK_TA_R_ACOMPTE_3 FOREIGN KEY (ID_BONCDE) REFERENCES TA_BONCDE (ID_DOCUMENT)';
    EXECUTE STATEMENT :query;

    query = 'ALTER TABLE TA_R_ACOMPTE ADD CONSTRAINT FK_TA_R_ACOMPTE_4 FOREIGN KEY (ID_PROFORMA) REFERENCES TA_PROFORMA (ID_DOCUMENT)';
    EXECUTE STATEMENT :query;

    query = 'ALTER TABLE TA_R_ACOMPTE ADD CONSTRAINT FK_TA_R_ACOMPTE_5 FOREIGN KEY (ID_ACOMPTE) REFERENCES TA_ACOMPTE (ID_DOCUMENT)';
    EXECUTE STATEMENT :query;

        query = 'ALTER TABLE TA_R_ACOMPTE ADD CONSTRAINT FK_TA_R_ACOMPTE_6 FOREIGN KEY (ID_APPORTEUR) REFERENCES TA_APPORTEUR (ID_DOCUMENT)';
    EXECUTE STATEMENT :query;

        query = 'ALTER TABLE TA_R_ACOMPTE ADD CONSTRAINT FK_TA_R_ACOMPTE_7 FOREIGN KEY (ID_AVOIR) REFERENCES TA_AVOIR (ID_DOCUMENT)';
    EXECUTE STATEMENT :query;

        query = 'ALTER TABLE TA_R_ACOMPTE ADD CONSTRAINT FK_TA_R_ACOMPTE_8 FOREIGN KEY (ID_BONLIV) REFERENCES TA_BONLIV (ID_DOCUMENT)';
    EXECUTE STATEMENT :query;
    
  end
END
^
EXECUTE PROCEDURE GESTION_CREATION_TABLE8 
^
drop procedure GESTION_CREATION_TABLE8
^


CREATE OR ALTER TRIGGER TBDID_ACOMPTE_ETRANGER FOR TA_ACOMPTE
ACTIVE AFTER DELETE POSITION 1
as
begin
  delete from ta_l_ACOMPTE where id_DOCUMENT = old.id_DOCUMENT;
  delete from TA_COM_DOC where ID_DOCUMENT = old.ID_DOCUMENT;
  delete from ta_infos_ACOMPTE where id_DOCUMENT = old.id_DOCUMENT;
  delete from ta_r_document where id_ACOMPTE = old.id_DOCUMENT;
  delete from ta_r_acompte where id_ACOMPTE = old.id_DOCUMENT; 
end
^



CREATE OR ALTER TRIGGER TBI_ACOMPTE FOR TA_ACOMPTE
ACTIVE BEFORE INSERT POSITION 2
as
begin
   New.CODE_DOCUMENT = Upper(New.CODE_DOCUMENT);
   New.QUI_CREE_DOCUMENT  = USER;
   New.QUAND_CREE_DOCUMENT = 'NOW';
   New.QUI_MODIF_DOCUMENT = USER;
   New.QUAND_MODIF_DOCUMENT = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


CREATE OR ALTER TRIGGER TBU_ACOMPTE FOR TA_ACOMPTE
ACTIVE BEFORE UPDATE POSITION 2
as
begin
   New.CODE_DOCUMENT = Upper(New.CODE_DOCUMENT);
   new.QUI_MODIF_DOCUMENT = USER;
   new.QUAND_MODIF_DOCUMENT = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^

CREATE OR ALTER TRIGGER TBIID_ARTICLE_L_ACOMPTE FOR TA_L_ACOMPTE
ACTIVE BEFORE INSERT POSITION 1
as
DECLARE variable numrows integer;
declare variable typeligneH integer;
declare variable typeligneC integer;
declare variable commentaire integer;
begin
  select typeL.id_t_ligne from ta_t_ligne typeL where typeL.code_t_ligne = 'H' into :typeligneh;
    select typeL.id_t_ligne from ta_t_ligne typeL where typeL.code_t_ligne = 'C' into :typelignec;
      select f.id_famille from ta_famille f where f.code_famille like ('&&&') into commentaire;
   if (NEW.ID_ARTICLE is not null and new.id_t_ligne =:typeligneh ) then
       begin
     select count(*) from TA_ARTICLE
     where ID_ARTICLE= NEW.ID_ARTICLE
     into :numrows;
     if (numrows = 0) then
        exception E_XAKID_ARTICLE;
       end
   else if (new.id_t_ligne=:typelignec or (exists(
    select id_article from ta_article where id_famille=:commentaire  and ta_article.id_article = new.id_article)) ) then
     begin
        new.id_article=null;
        new.qte_l_DOCUMENT=null;
        new.u1_l_DOCUMENT=null;
        new.u2_l_DOCUMENT=null;
        new.prix_u_l_DOCUMENT=null;
        new.taux_tva_l_DOCUMENT=null;
        new.compte_l_DOCUMENT=null;
        new.code_tva_l_DOCUMENT=null;
        new.code_t_tva_l_DOCUMENT=null;
        new.mt_ht_l_DOCUMENT=null;
        new.mt_ttc_l_DOCUMENT=null;
        new.rem_tx_l_DOCUMENT=null;
        new.rem_ht_l_DOCUMENT=null;
     end
end
^


CREATE OR ALTER TRIGGER TBI_L_ACOMPTE FOR TA_L_ACOMPTE
ACTIVE BEFORE INSERT POSITION 2
as
begin
   New.QUI_CREE_L_DOCUMENT  = USER;
   New.QUAND_CREE_L_DOCUMENT = 'NOW';
   New.QUI_MODIF_L_DOCUMENT = USER;
   New.QUAND_MODIF_L_DOCUMENT = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


CREATE OR ALTER TRIGGER TBUID_ARTICLE_L_ACOMPTE FOR TA_L_ACOMPTE
ACTIVE BEFORE UPDATE POSITION 1
as
DECLARE variable numrows integer;
declare variable typeligneH integer;
declare variable typeligneC integer;
declare variable commentaire integer;
begin
  select typeL.id_t_ligne from ta_t_ligne typeL where typeL.code_t_ligne = 'H' into :typeligneh;
    select typeL.id_t_ligne from ta_t_ligne typeL where typeL.code_t_ligne = 'C' into :typelignec;
      select f.id_famille from ta_famille f where f.code_famille like ('&&&') into commentaire;
   if (NEW.ID_ARTICLE is not null and new.id_t_ligne =:typeligneh ) then
       begin
     select count(*) from TA_ARTICLE
     where ID_ARTICLE= NEW.ID_ARTICLE
     into :numrows;
     if (numrows = 0) then
        exception E_XAKID_ARTICLE;
       end
   else if (new.id_t_ligne=:typelignec or (exists(
    select id_article from ta_article where id_famille=:commentaire  and ta_article.id_article = new.id_article)) ) then
     begin
        new.id_article=null;
        new.qte_l_DOCUMENT=null;
        new.u1_l_DOCUMENT=null;
        new.u2_l_DOCUMENT=null;
        new.prix_u_l_DOCUMENT=null;
        new.taux_tva_l_DOCUMENT=null;
        new.compte_l_DOCUMENT=null;
        new.code_tva_l_DOCUMENT=null;
        new.code_t_tva_l_DOCUMENT=null;
        new.mt_ht_l_DOCUMENT=null;
        new.mt_ttc_l_DOCUMENT=null;
        new.rem_tx_l_DOCUMENT=null;
        new.rem_ht_l_DOCUMENT=null;
     end
end
^


CREATE OR ALTER TRIGGER TBU_L_ACOMPTE FOR TA_L_ACOMPTE
ACTIVE BEFORE UPDATE POSITION 2
as
begin
   new.QUI_MODIF_L_DOCUMENT = USER;
   new.QUAND_MODIF_L_DOCUMENT = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^

CREATE OR ALTER TRIGGER TBI_TA_INFOS_ACOMPTE FOR TA_INFOS_ACOMPTE
ACTIVE BEFORE INSERT POSITION 0
as
begin
   New.QUI_CREE_INFOS_DOCUMENT = USER;
   new.QUAND_CREE_INFOS_DOCUMENT = 'NOW';
   new.QUI_MODIF_INFOS_DOCUMENT = USER;
   new.QUAND_MODIF_INFOS_DOCUMENT = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


CREATE OR ALTER TRIGGER TBU_TA_INFOS_ACOMPTE FOR TA_INFOS_ACOMPTE
ACTIVE BEFORE UPDATE POSITION 0
as
begin
   new.QUI_MODIF_INFOS_DOCUMENT = USER;
   new.QUAND_MODIF_INFOS_DOCUMENT = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^

CREATE OR ALTER TRIGGER TBDID_FACTURE_ETRANGER FOR TA_FACTURE
ACTIVE AFTER DELETE POSITION 1
as
begin
  delete from ta_l_facture where id_DOCUMENT = old.id_DOCUMENT;
  delete from TA_COM_DOC where ID_DOCUMENT = old.ID_DOCUMENT;
  delete from ta_infos_facture where id_DOCUMENT = old.id_DOCUMENT;
  delete from ta_r_document where id_facture = old.id_DOCUMENT;
  delete from ta_r_acompte where id_facture = old.id_DOCUMENT;
end
^

/* Trigger: TBI_R_ACOMPTE */
CREATE OR ALTER TRIGGER TBI_R_ACOMPTE FOR TA_R_ACOMPTE
ACTIVE BEFORE INSERT POSITION 2
as
begin
   If (New.ID  is null) Then
      New.ID  = GEN_ID(num_id_r_acompte,1);
   New.QUI_CREE  = USER;
   New.QUAND_CREE = 'NOW';
   New.QUI_MODIF = USER;
   New.QUAND_MODIF = 'NOW';
   new.IP_ACCES=current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBU_R_ACOMPTE */
CREATE OR ALTER TRIGGER TBU_R_ACOMPTE FOR TA_R_ACOMPTE
ACTIVE BEFORE UPDATE POSITION 2
as
begin
   new.QUI_MODIF = USER;
   new.QUAND_MODIF = 'NOW';
   new.IP_ACCES=current_connection;
   select num_version from ta_version into new."VERSION";
end
^


CREATE or ALTER  PROCEDURE GESTION_INSERTION_VALEUR1
as    
DECLARE VARIABLE QUERY VARCHAR(5000);
BEGIN
query ='';
    delete from TA_VISUALISATION where CODE_REQUETE like 'Liste des articles acompte';
    query = 'INSERT INTO TA_VISUALISATION (ID, CODE_REQUETE, REQUETE, CHAMPS, TITRE_CHAMPS, TAILLE_CHAMPS, MODULE, IMPRESSION, IDENTIFIANT, ID_EDITEUR, ID_PLUGIN, CLAUSE_WHERE, GROUPBY, CLAUSE_HAVING, PROC, PARAM, TYPES_RETOUR, TOTAUX, QUERY_LANG, CLASSE_RECHERCHE) VALUES (gen_id(num_id_visualisation,1), ''Liste des articles acompte'', ''select pf.dateDocument,pf.taTiers.codeTiers,pf.codeDocument,lpf.taArticle.codeArticle,lpf.u1LDocument,lpf.qteLDocument,lpf.mtHtLApresRemiseGlobaleDocument,lpf.mtTtcLApresRemiseGlobaleDocument-lpf.mtHtLApresRemiseGlobaleDocument,lpf.mtTtcLApresRemiseGlobaleDocument from TaAcompte pf left join pf.lignes lpf order by pf.dateDocument,pf.taTiers.codeTiers,pf.codeDocument,lpf.taArticle.codeArticle,lpf.u1LDocument'', ''pf.dateDocument;pf.taTiers.codeTiers;pf.codeDocument;lpf.taArticle.codeArticle;lpf.u1LDocument;lpf.qteLDocument;lpf.mtHtLApresRemiseGlobaleDocument;lpf.mtTtcLApresRemiseGlobaleDocument-lpf.mtHtLApresRemiseGlobaleDocument;lpf.mtTtcLApresRemiseGlobaleDocument'', ''Date Acompte;Code tiers;Code Acompte;Code article;Unité;Qté;HT;TVA;TTC'', ''100;100;100;100;100;100;100;100;100'', ''acompte'', ''aa'', ''pf.codeDocument'', ''fr.legrain.editor.acompte.swt.multi'', ''Acompte'', NULL, NULL, NULL, 0, NULL, ''d;s;s;s;s;f;f;f;f'', ''5;6;7;8'', ''JPQL'', '''')';
    EXECUTE STATEMENT :query;
END
^
EXECUTE PROCEDURE GESTION_INSERTION_VALEUR1
^
drop procedure GESTION_INSERTION_VALEUR1
^


CREATE or ALTER  PROCEDURE GESTION_INSERTION_VALEUR2
as    
DECLARE VARIABLE QUERY VARCHAR(5000);
BEGIN
query ='';
    delete from TA_VISUALISATION where CODE_REQUETE like 'synthèse des articles Facturés (par mois)';
    query = 'INSERT INTO TA_VISUALISATION (ID, CODE_REQUETE, REQUETE, CHAMPS, TITRE_CHAMPS, TAILLE_CHAMPS, MODULE, IMPRESSION, IDENTIFIANT, ID_EDITEUR, ID_PLUGIN, CLAUSE_WHERE, GROUPBY, CLAUSE_HAVING, PROC, PARAM, TYPES_RETOUR, TOTAUX, QUERY_LANG, CLASSE_RECHERCHE) VALUES (gen_id(num_id_visualisation,1), ''synthèse des articles Facturés (par mois)'', ''select  a.codeArticle,sum(lf.qteLDocument)as Qte1,sum(lf.qte2LDocument)as Qte2,sum(lf.mtHtLApresRemiseGlobaleDocument)as HT,sum(lf.mtTtcLApresRemiseGlobaleDocument-lf.mtHtLApresRemiseGlobaleDocument)as TVA,sum(lf.mtTtcLApresRemiseGlobaleDocument)as TTC,extract(month from f.dateDocument)as Mois,extract(year from f.dateDocument) as Annee from TaFacture f left join f.lignes lf join lf.taArticle a left join a.taFamille fa'', ''a.codeArticle;sum(lf.qteLDocument)!having;sum(lf.qte2LDocument)!having;sum(lf.mtHtLApresRemiseGlobaleDocument)!having;sum(lf.mtTtcLApresRemiseGlobaleDocument-lf.mtHtLApresRemiseGlobaleDocument)!having;sum(lf.mtTtcLApresRemiseGlobaleDocument)!having;extract(month from f.dateDocument);extract(year from f.dateDocument)'', ''Code article;Qté 1;Qté 2;HT;TVA;TTC;Mois;Année'', ''100;100;100;100;100;100;100;100'', ''article'', ''aa'', ''a.codeArticle'', ''fr.legrain.articles.editor.EditorArticle'', ''Articles'', NULL, ''group by a.codeArticle,extract(month from f.dateDocument),extract(year from f.dateDocument)'', NULL, 0, NULL, ''s;f;f;f;f;f;f;f'', ''1;2;3;4;5'', ''JPQL'', '''')';
    EXECUTE STATEMENT :query;
END
^
EXECUTE PROCEDURE GESTION_INSERTION_VALEUR2
^
drop procedure GESTION_INSERTION_VALEUR2
^

