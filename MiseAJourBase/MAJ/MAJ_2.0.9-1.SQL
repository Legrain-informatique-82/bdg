CREATE or ALTER  PROCEDURE GESTION_INSERTION_VISUAL1
as    
DECLARE VARIABLE QUERY VARCHAR(5000);
BEGIN
query ='';
    delete from TA_VISUALISATION where CODE_REQUETE like 'Extraction tiers - Tous';
    query = 'INSERT INTO TA_VISUALISATION (ID, CODE_REQUETE, REQUETE, CHAMPS, TITRE_CHAMPS, TAILLE_CHAMPS, MODULE, IMPRESSION, IDENTIFIANT, ID_EDITEUR, ID_PLUGIN, CLAUSE_WHERE, GROUPBY, CLAUSE_HAVING, PROC, PARAM, TYPES_RETOUR, TOTAUX, QUERY_LANG, CLASSE_RECHERCHE) VALUES 
(gen_id(num_id_visualisation,1), ''Extraction tiers - Tous'', ''select distinct(t.nomTiers),t.prenomTiers,t.dateAnniv,a.adresse1Adresse, a.adresse2Adresse,a.adresse3Adresse,
a.codepostalAdresse, a.villeAdresse, a.paysAdresse,substring(a.codepostalAdresse,1,2),tc.codeTCivilite,t.surnomTiers, t.codeTiers,e.nomEntreprise
,t.actifTiers,t.ttcTiers,tt.codeTTiers,  tt.libelleTTiers,c.commentaire,te.codeTEntite, co.tvaIComCompl,  co.siretCompl,tel.numeroTelephone,
mail.adresseEmail,w.adresseWeb,t.quandCreeTiers,ft.codeFamille
        from fr.legrain.tiers.dao.TaTiers t  
        left join t.taAdresse a 
        left join t.taTCivilite tc 
        left join t.taEntreprise e 
        left join t.taTTiers tt 
        left join t.taCommentaire c 
        left join t.taTEntite te 
        left join t.taCompl co 
        left join t.taTelephone tel 
        left join t.taEmail mail 
        left join t.taWeb w
        left join t.taFamilleTierses ft
order by ft.codeFamille'', ''t.nomTiers;t.prenomTiers;t.dateAnniv;a.adresse1Adresse;a.adresse2Adresse;a.adresse3Adresse;a.codepostalAdresse
        ;a.villeAdresse;a.paysAdresse;substring(a.codepostalAdresse,1,2);tc.codeTCivilite;t.surnomTiers;t.codeTiers;e.nomEntreprise;t.actifTiers
        ;t.ttcTiers;tt.codeTTiers;tt.libelleTTiers;c.commentaire;te.codeTEntite;co.tvaIComCompl;co.siretCompl;tel.numeroTelephone;mail.adresseEmail
        ;w.adresseWeb;t.quandCreeTiers;ft.codeFamille'', ''Nom;Prénom;Anniversaire;Adresse 1;Adresse 2;Adresse 3;Code postal;Ville;Pays;Département;Civilité;
        Surnom;Code tiers;Nom entreprise;Actif;TTC;Type tiers;Libellé type;Commentaire;Type entité;Tva intra;Siret;Tel;Email;Web;date creation;codeFamille'', ''50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50'', ''tiers'', ''/report/extraction_Tiers.rptdesign'', ''t.codeTiers'', ''fr.legrain.tiers.editor.EditorTiers'', ''Tiers'', NULL, '''', NULL, 0, NULL, ''s;s;d;s;s;s;s;s;s;i;s;s;s;s;b;b;s;s;s;s;s;s;s;s;s;s;s'', NULL, ''JPQL'', NULL)';
    EXECUTE STATEMENT :query;
END
^
EXECUTE PROCEDURE GESTION_INSERTION_VISUAL1
^
drop procedure GESTION_INSERTION_VISUAL1
^

CREATE or ALTER  PROCEDURE GESTION_TA_SYNCHRO_BOUTIQUE
as    
DECLARE VARIABLE QUERY VARCHAR(5000);
BEGIN
query ='';
IF (not exists( SELECT  a.RDB$RELATION_NAME
FROM RDB$RELATIONS a
where a.RDB$RELATION_NAME='TA_SYNCHRO_BOUTIQUE')) THEN
  begin
query='create table TA_SYNCHRO_BOUTIQUE (
	ID					DID3,
	DERNIERE_SYNCHRO	TIMESTAMP,
	DERNIERE_EXPORT TIMESTAMP)';
EXECUTE STATEMENT :query; 

query='ALTER TABLE TA_SYNCHRO_BOUTIQUE ADD PRIMARY KEY (ID)';
EXECUTE STATEMENT :query; 
  end
END
^
EXECUTE PROCEDURE GESTION_TA_SYNCHRO_BOUTIQUE
^
drop procedure GESTION_TA_SYNCHRO_BOUTIQUE
^





CREATE or ALTER  PROCEDURE GESTION_INSERTION_DATEEXPORT
as    
DECLARE VARIABLE QUERY VARCHAR(5000);
BEGIN
query ='';
IF (not exists( SELECT a.RDB$FIELD_NAME, a.RDB$RELATION_NAME FROM RDB$RELATION_FIELDS a
where a.RDB$RELATION_NAME = 'TA_SYNCHRO_BOUTIQUE' and  a.RDB$FIELD_NAME ='DERNIERE_EXPORT')) THEN
  begin
    query = 'ALTER TABLE TA_SYNCHRO_BOUTIQUE ADD DERNIERE_EXPORT TIMESTAMP';
    EXECUTE STATEMENT :query; 
  end
    
END
^
EXECUTE PROCEDURE GESTION_INSERTION_DATEEXPORT
^
drop procedure GESTION_INSERTION_DATEEXPORT
^

CREATE or ALTER  PROCEDURE CREATION_TA_CORR_ID_BOUTIQUE
as    
DECLARE VARIABLE QUERY VARCHAR(5000);
BEGIN
query = '';
IF (not exists( SELECT  a.RDB$RELATION_NAME
FROM RDB$RELATIONS a
where a.RDB$RELATION_NAME='TA_CORRESPONDANCE_ID_BOUTIQUE')) THEN
  begin
    query = 'CREATE TABLE TA_CORRESPONDANCE_ID_BOUTIQUE (
    ID			DID3 NOT NULL,
    ID_BDG      DID3 NOT NULL,
	ID_BOUTIQUE	DID3 NOT NULL,
	TYPE_TABLE  DLIB255NN,
	CREATION    TIMESTAMP DEFAULT ''NOW'',
    "VERSION"            		NUM_VERSION,
    IP_ACCES             		DLIB50NN DEFAULT 0,
    VERSION_OBJ          		INTEGER
	)';
    EXECUTE STATEMENT :query;
    
    query = 'ALTER TABLE TA_CORRESPONDANCE_ID_BOUTIQUE ADD PRIMARY KEY (ID)';
    EXECUTE STATEMENT :query;

  end
END
^
EXECUTE PROCEDURE CREATION_TA_CORR_ID_BOUTIQUE
^
drop procedure CREATION_TA_CORR_ID_BOUTIQUE
^

CREATE or ALTER  PROCEDURE GEST_GEN_CORRESP_ID_BOUT
as    
DECLARE VARIABLE QUERY VARCHAR(255);
BEGIN
query = '';
IF (not exists( SELECT a.RDB$GENERATOR_NAME
FROM RDB$GENERATORS a
where a.RDB$GENERATOR_NAME='NUM_ID_CORRESP_ID_BOUTIQUE')) THEN
  begin
    query = 'CREATE GENERATOR NUM_ID_CORRESP_ID_BOUTIQUE';
    EXECUTE STATEMENT :query;
    
    query = 'SET GENERATOR NUM_ID_CORRESP_ID_BOUTIQUE TO 0';
    EXECUTE STATEMENT :query;
  end
END
^
EXECUTE PROCEDURE GEST_GEN_CORRESP_ID_BOUT
^
drop procedure GEST_GEN_CORRESP_ID_BOUT
^

create or alter TRIGGER TBI_CORRESPONDANCE_ID_BOUTIQUE FOR TA_CORRESPONDANCE_ID_BOUTIQUE ACTIVE
BEFORE INSERT POSITION 1
as
begin
   If (New.ID  is null) Then
      New.ID  = GEN_ID(NUM_ID_CORRESP_ID_BOUTIQUE,1);
      New.CREATION = 'NOW';
end
^

CREATE or ALTER  PROCEDURE GESTION_ALTER_TA_BONCDE
as    
DECLARE VARIABLE QUERY VARCHAR(500);
BEGIN
query ='';
IF (not exists( SELECT a.RDB$FIELD_NAME, a.RDB$RELATION_NAME FROM RDB$RELATION_FIELDS a
where a.RDB$RELATION_NAME = 'TA_BONCDE' and  a.RDB$FIELD_NAME ='ORIGINE_IMPORT')) THEN
  begin
    query = 'ALTER TABLE TA_BONCDE ADD ORIGINE_IMPORT DLIB100';
    EXECUTE STATEMENT :query;
    
    query = 'ALTER TABLE TA_BONCDE ADD ID_IMPORT DLIB100';
    EXECUTE STATEMENT :query;
  end
END
^
EXECUTE PROCEDURE GESTION_ALTER_TA_BONCDE
^
drop procedure GESTION_ALTER_TA_BONCDE
^
