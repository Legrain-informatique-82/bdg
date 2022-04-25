CREATE or ALTER  PROCEDURE CREATION_TA_R_TITRE_TRANSPORT
as    
DECLARE VARIABLE QUERY VARCHAR(5000);
BEGIN
query = '';
IF (not exists( SELECT  a.RDB$RELATION_NAME
FROM RDB$RELATIONS a
where a.RDB$RELATION_NAME='TA_R_TITRE_TRANSPORT')) THEN
  begin
    query = 'CREATE TABLE TA_R_TITRE_TRANSPORT
(
  ID_R_TITRE_TRANSPORT DID3 NOT NULL,
  ID_TITRE_TRANSPORT DID4,
  ID_ARTICLE DID4,
  ID_UNITE DID4,
  QTE_TITRE_TRANSPORT NUMERIC(15,2),
  QUI_CREE DLIB50,
  QUAND_CREE TIMESTAMP DEFAULT ''NOW'',
  QUI_MODIF DLIB50,
  QUAND_MODIF TIMESTAMP DEFAULT ''NOW'',
  "VERSION" NUM_VERSION,
  IP_ACCES DLIB50NN DEFAULT 0,
  VERSION_OBJ INTEGER
  )';
    EXECUTE STATEMENT :query;
    
    query = 'ALTER TABLE TA_R_TITRE_TRANSPORT ADD PRIMARY KEY (ID_R_TITRE_TRANSPORT)';
    EXECUTE STATEMENT :query;
    
    query = 'ALTER TABLE TA_R_TITRE_TRANSPORT ADD FOREIGN KEY (ID_TITRE_TRANSPORT) REFERENCES TA_TITRE_TRANSPORT (ID_TITRE_TRANSPORT)';
    EXECUTE STATEMENT :query;
    
    query = 'ALTER TABLE TA_R_TITRE_TRANSPORT ADD FOREIGN KEY (ID_ARTICLE) REFERENCES TA_ARTICLE (ID_ARTICLE)';
    EXECUTE STATEMENT :query;
    
    query = 'ALTER TABLE TA_R_TITRE_TRANSPORT ADD FOREIGN KEY (ID_UNITE) REFERENCES TA_UNITE (ID_UNITE)';
    EXECUTE STATEMENT :query;

  end
END
^
EXECUTE PROCEDURE CREATION_TA_R_TITRE_TRANSPORT
^
drop procedure CREATION_TA_R_TITRE_TRANSPORT
^

CREATE or ALTER  PROCEDURE GEST_CREAT_GEN_R_TITRE_TRANS
as    
DECLARE VARIABLE QUERY VARCHAR(255);
BEGIN
query = '';
IF (not exists( SELECT a.RDB$GENERATOR_NAME
FROM RDB$GENERATORS a
where a.RDB$GENERATOR_NAME='NUM_ID_R_TITRE_TRANSPORT')) THEN
  begin
    query = 'CREATE GENERATOR NUM_ID_R_TITRE_TRANSPORT';
    EXECUTE STATEMENT :query;
    
    query = 'SET GENERATOR NUM_ID_R_TITRE_TRANSPORT TO 0';
    EXECUTE STATEMENT :query;
  end
END
^
EXECUTE PROCEDURE GEST_CREAT_GEN_R_TITRE_TRANS
^
drop procedure GEST_CREAT_GEN_R_TITRE_TRANS
^

CREATE OR ALTER TRIGGER TBI_TA_R_TITRE_TRANSPORT FOR TA_R_TITRE_TRANSPORT
ACTIVE BEFORE INSERT POSITION 0
as
begin
   If (New.ID_R_TITRE_TRANSPORT is null) Then
      New.ID_R_TITRE_TRANSPORT = GEN_ID(NUM_ID_R_TITRE_TRANSPORT,1);
   New.QUI_CREE = USER;
   new.QUAND_CREE = 'NOW';
   new.QUI_MODIF = USER;
   new.QUAND_MODIF = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^

CREATE OR ALTER TRIGGER TBU_TA_R_TITRE_TRANSPORT FOR TA_R_TITRE_TRANSPORT
ACTIVE BEFORE UPDATE POSITION 1
as
begin
   new.QUI_MODIF = USER;
   new.QUAND_MODIF = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^

CREATE or ALTER  PROCEDURE GEST_INSERT_TITRE_TRANS_ART
as    
DECLARE VARIABLE QUERY VARCHAR(5000);
BEGIN
query ='';
IF (not exists( SELECT a.RDB$FIELD_NAME, a.RDB$RELATION_NAME FROM RDB$RELATION_FIELDS a
where a.RDB$RELATION_NAME = 'TA_TITRE_TRANSPORT' and  a.RDB$FIELD_NAME ='LIBELLE_TITRE_TRANSPORT')) THEN
  begin
    query = 'ALTER TABLE TA_TITRE_TRANSPORT ADD LIBELLE_TITRE_TRANSPORT DLGR_LIB';
    EXECUTE STATEMENT :query; 
    
    query = 'ALTER TABLE TA_TITRE_TRANSPORT ADD QTE_MIN_TITRE_TRANSPORT INTEGER';
    EXECUTE STATEMENT :query; 
  end
    
END
^
EXECUTE PROCEDURE GEST_INSERT_TITRE_TRANS_ART
^
drop procedure GEST_INSERT_TITRE_TRANS_ART
^

CREATE or ALTER  PROCEDURE CREAT_TA_TA_STOCK_CAPSULES
as    
DECLARE VARIABLE QUERY VARCHAR(5000);
BEGIN
query = '';
IF (not exists( SELECT  a.RDB$RELATION_NAME
FROM RDB$RELATIONS a
where a.RDB$RELATION_NAME='TA_STOCK_CAPSULES')) THEN
  begin
    query = 'CREATE TABLE TA_STOCK_CAPSULES
(
  ID_STOCK_CAPSULES DID3 NOT NULL,
  ID_TITRE_TRANSPORT DID_FACULTATIF,
  MOUV_STOCK DLIB_MOUV DEFAULT ''E'',
  DATE_STOCK DATE_LGR DEFAULT ''NOW'',
  LIBELLE_STOCK DLIB255,
  QTE1_STOCK NUMERIC(15,2),
  UN1_STOCK DLGR_CODEL,
  QTE2_STOCK NUMERIC(15,2),
  UN2_STOCK DLGR_CODEL,
  QUI_CREE DLIB50,
  QUAND_CREE TIMESTAMP DEFAULT ''NOW'',
  QUI_MODIF DLIB50,
  QUAND_MODIF TIMESTAMP DEFAULT ''NOW'',
  "VERSION" NUM_VERSION,
  IP_ACCES DLIB50NN DEFAULT 0,
  VERSION_OBJ INTEGER
)';
    EXECUTE STATEMENT :query;
    
    query = 'ALTER TABLE TA_STOCK_CAPSULES ADD PRIMARY KEY (ID_STOCK_CAPSULES)';
    EXECUTE STATEMENT :query;
    
    query = 'ALTER TABLE TA_STOCK_CAPSULES ADD FOREIGN KEY (ID_TITRE_TRANSPORT) REFERENCES TA_TITRE_TRANSPORT (ID_TITRE_TRANSPORT)';
    EXECUTE STATEMENT :query;

  end
END
^
EXECUTE PROCEDURE CREAT_TA_TA_STOCK_CAPSULES
^
drop procedure CREAT_TA_TA_STOCK_CAPSULES
^

CREATE or ALTER  PROCEDURE CREAT_GEN_TA_STOCK_CAPSULES
as    
DECLARE VARIABLE QUERY VARCHAR(255);
BEGIN
query = '';
IF (not exists( SELECT a.RDB$GENERATOR_NAME
FROM RDB$GENERATORS a
where a.RDB$GENERATOR_NAME='NUM_ID_STOCK_CAPSULES')) THEN
  begin
    query = 'CREATE GENERATOR NUM_ID_STOCK_CAPSULES';
    EXECUTE STATEMENT :query;
    
    query = 'SET GENERATOR NUM_ID_STOCK_CAPSULES TO 0';
    EXECUTE STATEMENT :query;
  end
END
^
EXECUTE PROCEDURE CREAT_GEN_TA_STOCK_CAPSULES
^
drop procedure CREAT_GEN_TA_STOCK_CAPSULES
^

CREATE OR ALTER TRIGGER TBI_TA_STOCK_CAPSULES FOR TA_STOCK_CAPSULES
ACTIVE BEFORE INSERT POSITION 0
as
begin
   If (New.ID_STOCK_CAPSULES is null) Then
      New.ID_STOCK_CAPSULES = GEN_ID(NUM_ID_STOCK_CAPSULES,1);
   New.QUI_CREE = USER;
   new.QUAND_CREE = 'NOW';
   new.QUI_MODIF = USER;
   new.QUAND_MODIF = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^

CREATE OR ALTER TRIGGER TBU_TA_STOCK_CAPSULES FOR TA_STOCK_CAPSULES
ACTIVE BEFORE UPDATE POSITION 1
as
begin
   new.QUI_MODIF = USER;
   new.QUAND_MODIF = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^

CREATE or ALTER  PROCEDURE CREAT_TA_REPORT_STOCK_CAPSULES
as    
DECLARE VARIABLE QUERY VARCHAR(5000);
BEGIN
query = '';
IF (not exists( SELECT  a.RDB$RELATION_NAME
FROM RDB$RELATIONS a
where a.RDB$RELATION_NAME='TA_REPORT_STOCK_CAPSULES')) THEN
  begin
    query = 'CREATE TABLE TA_REPORT_STOCK_CAPSULES
(
  ID_REPORT_STOCK_CAPSULES DID3 NOT NULL,
  ID_TITRE_TRANSPORT_REPORT_STOCK DID_FACULTATIF,
  DATE_DEB_REPORT_STOCK DATE_LGR DEFAULT ''NOW'',
  DATE_FIN_REPORT_STOCK DATE_LGR DEFAULT ''NOW'',
  QTE1_REPORT_STOCK DID9FACULT,
  UNITE1_REPORT_STOCK DLGR_CODEL,
  QTE2_REPORT_STOCK DID9FACULT,
  UNITE2_REPORT_STOCK DLGR_CODEL,
  QUI_CREE DLIB50,
  QUAND_CREE TIMESTAMP DEFAULT ''NOW'',
  QUI_MODIF DLIB50,
  QUAND_MODIF TIMESTAMP DEFAULT ''NOW'',
  "VERSION" NUM_VERSION,
  IP_ACCES DLIB50NN DEFAULT 0,
  VERSION_OBJ INTEGER
)';
    EXECUTE STATEMENT :query;

    query = 'ALTER TABLE TA_REPORT_STOCK_CAPSULES ADD PRIMARY KEY (ID_REPORT_STOCK_CAPSULES)';
    EXECUTE STATEMENT :query;
    
    query = 'ALTER TABLE TA_REPORT_STOCK_CAPSULES ADD FOREIGN KEY (ID_TITRE_TRANSPORT_REPORT_STOCK) REFERENCES TA_TITRE_TRANSPORT (ID_TITRE_TRANSPORT)';
    EXECUTE STATEMENT :query;

  end
END
^
EXECUTE PROCEDURE CREAT_TA_REPORT_STOCK_CAPSULES
^
drop procedure CREAT_TA_REPORT_STOCK_CAPSULES
^

CREATE or ALTER  PROCEDURE GEN_TA_REPORT_STOCK_CAPSULES
as    
DECLARE VARIABLE QUERY VARCHAR(255);
BEGIN
query = '';
IF (not exists( SELECT a.RDB$GENERATOR_NAME
FROM RDB$GENERATORS a
where a.RDB$GENERATOR_NAME='NUM_ID_REPORT_STOCK_CAPSULES')) THEN
  begin
    query = 'CREATE GENERATOR NUM_ID_REPORT_STOCK_CAPSULES';
    EXECUTE STATEMENT :query;
    
    query = 'SET GENERATOR NUM_ID_REPORT_STOCK_CAPSULES TO 0';
    EXECUTE STATEMENT :query;
  end
END
^
EXECUTE PROCEDURE GEN_TA_REPORT_STOCK_CAPSULES
^
drop procedure GEN_TA_REPORT_STOCK_CAPSULES
^

CREATE OR ALTER TRIGGER TBI_TA_REPORT_STOCK_CAPSULES FOR TA_REPORT_STOCK_CAPSULES
ACTIVE BEFORE INSERT POSITION 0
as
begin
   If (New.ID_REPORT_STOCK_CAPSULES is null) Then
      New.ID_REPORT_STOCK_CAPSULES = GEN_ID(NUM_ID_REPORT_STOCK_CAPSULES,1);
   New.QUI_CREE = USER;
   new.QUAND_CREE = 'NOW';
   new.QUI_MODIF = USER;
   new.QUAND_MODIF = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^

CREATE OR ALTER TRIGGER TBU_TA_REPORT_STOCK_CAPSULES FOR TA_REPORT_STOCK_CAPSULES
ACTIVE BEFORE UPDATE POSITION 1
as
begin
   new.QUI_MODIF = USER;
   new.QUAND_MODIF = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^

CREATE or ALTER  PROCEDURE GEST_INSERT_TITRE_TRANS_ART_ID
as    
DECLARE VARIABLE QUERY VARCHAR(5000);
BEGIN
query ='';
IF (not exists( SELECT a.RDB$FIELD_NAME, a.RDB$RELATION_NAME FROM RDB$RELATION_FIELDS a
where a.RDB$RELATION_NAME = 'TA_ARTICLE' and  a.RDB$FIELD_NAME ='ID_R_TITRE_TRANSPORT')) THEN
  begin
    query = 'ALTER TABLE TA_ARTICLE ADD ID_R_TITRE_TRANSPORT DID_FACULTATIF';
    EXECUTE STATEMENT :query; 
    
    query = 'ALTER TABLE TA_ARTICLE ADD FOREIGN KEY (ID_R_TITRE_TRANSPORT) REFERENCES TA_R_TITRE_TRANSPORT (ID_R_TITRE_TRANSPORT)';
    EXECUTE STATEMENT :query; 
  end
    
END
^
EXECUTE PROCEDURE GEST_INSERT_TITRE_TRANS_ART_ID
^
drop procedure GEST_INSERT_TITRE_TRANS_ART_ID
^

CREATE or ALTER  PROCEDURE GEST_INSERT_TITRE_TRANS_FACT
as    
DECLARE VARIABLE QUERY VARCHAR(5000);
BEGIN
query ='';
IF (not exists( SELECT a.RDB$FIELD_NAME, a.RDB$RELATION_NAME FROM RDB$RELATION_FIELDS a
where a.RDB$RELATION_NAME = 'TA_L_FACTURE' and  a.RDB$FIELD_NAME ='QTE_TITRE_TRANSPORT_L_DOCUMENT')) THEN
  begin
    query = 'ALTER TABLE TA_L_FACTURE ADD QTE_TITRE_TRANSPORT_L_DOCUMENT INTEGER';
    EXECUTE STATEMENT :query; 
    
    query = 'ALTER TABLE TA_L_FACTURE ADD TITRE_TRANSPORT_L_DOCUMENT DLIB50';
    EXECUTE STATEMENT :query; 
  end
END
^
EXECUTE PROCEDURE GEST_INSERT_TITRE_TRANS_FACT
^
drop procedure GEST_INSERT_TITRE_TRANS_FACT
^

CREATE or ALTER  PROCEDURE GEST_INSERT_TITRE_TRANS_AVOIR
as    
DECLARE VARIABLE QUERY VARCHAR(5000);
BEGIN
query ='';
IF (not exists( SELECT a.RDB$FIELD_NAME, a.RDB$RELATION_NAME FROM RDB$RELATION_FIELDS a
where a.RDB$RELATION_NAME = 'TA_L_AVOIR' and  a.RDB$FIELD_NAME ='QTE_TITRE_TRANSPORT_L_DOCUMENT')) THEN
  begin
    query = 'ALTER TABLE TA_L_AVOIR ADD QTE_TITRE_TRANSPORT_L_DOCUMENT INTEGER';
    EXECUTE STATEMENT :query; 
    
    query = 'ALTER TABLE TA_L_AVOIR ADD TITRE_TRANSPORT_L_DOCUMENT DLIB50';
    EXECUTE STATEMENT :query; 
  end
END
^
EXECUTE PROCEDURE GEST_INSERT_TITRE_TRANS_AVOIR
^
drop procedure GEST_INSERT_TITRE_TRANS_AVOIR
^




create or alter procedure GESTION_MAJ_CODE_T_TVA_DOC
as
declare variable EXISTENCE integer;
declare variable IDTIERS integer;
declare variable IDINFOSDOC integer;
declare variable IDDOC integer;
declare variable CODETVADOC varchar(20);
begin
  for select inf.id_infos_document ,inf.id_document,f.id_tiers  from ta_infos_acompte inf join ta_acompte f on f.id_document=inf.id_document
  into :idinfosdoc,:iddoc,:idtiers do
  begin
       codetvadoc='F';
       existence=0;
      if( exists(select lf.id_l_document from ta_l_acompte lf  where (lf.id_document=:iddoc)
      and(lf.code_tva_l_document is not null and lf.code_tva_l_document<>'')))then
      begin
        existence=1;
      end
      if(existence=1)then
        UPDATE ta_infos_acompte I set CODE_T_TVA_DOC =:codetvadoc where i.id_infos_document= :idinfosdoc;
      else
        UPDATE TA_INFOS_ACOMPTE I set CODE_T_TVA_DOC = (select CODE_T_TVA_DOC from TA_T_TVA_DOC TVA join TA_TIERS T on T.id_t_tva_doc=tva.id_t_tva_doc
        join  TA_ACOMPTE DOC on Doc.id_tiers=T.id_tiers  where I.ID_DOCUMENT = DOC.ID_DOCUMENT);
  end

  for select inf.id_infos_document ,inf.id_document,f.id_tiers  from ta_infos_apporteur inf join ta_apporteur f on f.id_document=inf.id_document
  into :idinfosdoc,:iddoc,:idtiers do
  begin
       codetvadoc='F';
       existence=0;
      if( exists(select lf.id_l_document from ta_l_apporteur lf  where (lf.id_document=:iddoc)
      and(lf.code_tva_l_document is not null and lf.code_tva_l_document<>'')))then
      begin
        existence=1;
      end
      if(existence=1)then
        UPDATE ta_infos_apporteur I set CODE_T_TVA_DOC =:codetvadoc where i.id_infos_document= :idinfosdoc;
      else
        UPDATE ta_infos_apporteur I set CODE_T_TVA_DOC = (select CODE_T_TVA_DOC from TA_T_TVA_DOC TVA join TA_TIERS T on T.id_t_tva_doc=tva.id_t_tva_doc
        join  ta_apporteur DOC on Doc.id_tiers=T.id_tiers  where I.ID_DOCUMENT = DOC.ID_DOCUMENT);
  end

  for select inf.id_infos_document ,inf.id_document,f.id_tiers  from TA_INFOS_avoir inf join ta_avoir f on f.id_document=inf.id_document
  into :idinfosdoc,:iddoc,:idtiers do
  begin
       codetvadoc='F';
       existence=0;
      if( exists(select lf.id_l_document from ta_l_avoir lf  where (lf.id_document=:iddoc)
      and(lf.code_tva_l_document is not null and lf.code_tva_l_document<>'')))then
      begin
        existence=1;
      end
      if(existence=1)then
        UPDATE TA_INFOS_avoir I set CODE_T_TVA_DOC =:codetvadoc where i.id_infos_document= :idinfosdoc;
      else
        UPDATE ta_infos_avoir I set CODE_T_TVA_DOC = (select CODE_T_TVA_DOC from TA_T_TVA_DOC TVA join TA_TIERS T on T.id_t_tva_doc=tva.id_t_tva_doc
        join  ta_avoir DOC on Doc.id_tiers=T.id_tiers  where I.ID_DOCUMENT = DOC.ID_DOCUMENT);
  end

  for select inf.id_infos_document ,inf.id_document,f.id_tiers  from ta_infos_boncde inf join ta_boncde f on f.id_document=inf.id_document
  into :idinfosdoc,:iddoc,:idtiers do
  begin
       codetvadoc='F';
       existence=0;
      if( exists(select lf.id_l_document from ta_l_boncde lf  where (lf.id_document=:iddoc)
      and(lf.code_tva_l_document is not null and lf.code_tva_l_document<>'')))then
      begin
        existence=1;
      end
      if(existence=1)then
        UPDATE ta_infos_boncde I set CODE_T_TVA_DOC =:codetvadoc where i.id_infos_document= :idinfosdoc;
      else
        UPDATE ta_infos_boncde I set CODE_T_TVA_DOC = (select CODE_T_TVA_DOC from TA_T_TVA_DOC TVA join TA_TIERS T on T.id_t_tva_doc=tva.id_t_tva_doc
        join  ta_boncde DOC on Doc.id_tiers=T.id_tiers  where I.ID_DOCUMENT = DOC.ID_DOCUMENT);
  end

  for select inf.id_infos_document ,inf.id_document,f.id_tiers  from ta_infos_bonliv inf join ta_bonliv f on f.id_document=inf.id_document
  into :idinfosdoc,:iddoc,:idtiers do
  begin
       codetvadoc='F';
       existence=0;
      if( exists(select lf.id_l_document from ta_l_bonliv lf  where (lf.id_document=:iddoc)
      and(lf.code_tva_l_document is not null and lf.code_tva_l_document<>'')))then
      begin
        existence=1;
      end
      if(existence=1)then
        UPDATE ta_infos_bonliv I set CODE_T_TVA_DOC =:codetvadoc where i.id_infos_document= :idinfosdoc;
      else
        UPDATE ta_infos_bonliv I set CODE_T_TVA_DOC = (select CODE_T_TVA_DOC from TA_T_TVA_DOC TVA join TA_TIERS T on T.id_t_tva_doc=tva.id_t_tva_doc
        join  ta_bonliv DOC on Doc.id_tiers=T.id_tiers  where I.ID_DOCUMENT = DOC.ID_DOCUMENT);
  end

  for select inf.id_infos_document ,inf.id_document,f.id_tiers  from TA_INFOS_devis inf join ta_devis f on f.id_document=inf.id_document
  into :idinfosdoc,:iddoc,:idtiers do
  begin
       codetvadoc='F';
       existence=0;
      if( exists(select lf.id_l_document from ta_l_devis lf  where (lf.id_document=:iddoc)
      and(lf.code_tva_l_document is not null and lf.code_tva_l_document<>'')))then
      begin
        existence=1;
      end
      if(existence=1)then
        UPDATE ta_infos_devis I set CODE_T_TVA_DOC =:codetvadoc where i.id_infos_document= :idinfosdoc;
      else
        UPDATE ta_infos_devis I set CODE_T_TVA_DOC = (select CODE_T_TVA_DOC from TA_T_TVA_DOC TVA join TA_TIERS T on T.id_t_tva_doc=tva.id_t_tva_doc
        join  ta_devis DOC on Doc.id_tiers=T.id_tiers  where I.ID_DOCUMENT = DOC.ID_DOCUMENT);
  end

  for select inf.id_infos_document ,inf.id_document,f.id_tiers  from TA_INFOS_FACTURE inf join ta_facture f on f.id_document=inf.id_document
  into :idinfosdoc,:iddoc,:idtiers do
  begin
       codetvadoc='F';
       existence=0;
      if( exists(select lf.id_l_document from ta_l_facture lf  where (lf.id_document=:iddoc)
      and(lf.code_tva_l_document is not null and lf.code_tva_l_document<>'')))then
      begin
        existence=1;
      end
      if(existence=1)then
        UPDATE TA_INFOS_FACTURE I set CODE_T_TVA_DOC =:codetvadoc where i.id_infos_document= :idinfosdoc;
      else
        UPDATE ta_infos_facture I set CODE_T_TVA_DOC = (select CODE_T_TVA_DOC from TA_T_TVA_DOC TVA join TA_TIERS T on T.id_t_tva_doc=tva.id_t_tva_doc
        join  ta_facture DOC on Doc.id_tiers=T.id_tiers  where I.ID_DOCUMENT = DOC.ID_DOCUMENT);
  end

  for select inf.id_infos_document ,inf.id_document,f.id_tiers  from TA_INFOS_prelevement inf join ta_prelevement f on f.id_document=inf.id_document
  into :idinfosdoc,:iddoc,:idtiers do
  begin
       codetvadoc='F';
       existence=0;
      if( exists(select lf.id_l_document from ta_l_prelevement lf  where (lf.id_document=:iddoc)
      and(lf.code_tva_l_document is not null and lf.code_tva_l_document<>'')))then
      begin
        existence=1;
      end
      if(existence=1)then
        UPDATE ta_infos_prelevement I set CODE_T_TVA_DOC =:codetvadoc where i.id_infos_document= :idinfosdoc;
      else
        UPDATE ta_infos_prelevement I set CODE_T_TVA_DOC = (select CODE_T_TVA_DOC from TA_T_TVA_DOC TVA join TA_TIERS T on T.id_t_tva_doc=tva.id_t_tva_doc
        join  ta_prelevement DOC on Doc.id_tiers=T.id_tiers  where I.ID_DOCUMENT = DOC.ID_DOCUMENT);
  end

  for select inf.id_infos_document ,inf.id_document,f.id_tiers  from ta_infos_proforma inf join ta_proforma f on f.id_document=inf.id_document
  into :idinfosdoc,:iddoc,:idtiers do
  begin
       codetvadoc='F';
       existence=0;
      if(exists(select lf.id_l_document from ta_l_proforma lf  where (lf.id_document=:iddoc)
      and(lf.code_tva_l_document is not null and lf.code_tva_l_document<>'')))then
      begin
        existence=1;
      end
      if(existence=1)then
        UPDATE TA_INFOS_PROFORMA I set CODE_T_TVA_DOC =:codetvadoc where i.id_infos_document= :idinfosdoc;
      else
        UPDATE ta_infos_proforma I set CODE_T_TVA_DOC = (select CODE_T_TVA_DOC from TA_T_TVA_DOC TVA join TA_TIERS T on T.id_t_tva_doc=tva.id_t_tva_doc
        join  ta_proforma DOC on Doc.id_tiers=T.id_tiers  where I.ID_DOCUMENT = DOC.ID_DOCUMENT);
  end
  suspend;
end
^

EXECUTE PROCEDURE GESTION_MAJ_CODE_T_TVA_DOC
^
drop procedure GESTION_MAJ_CODE_T_TVA_DOC
^