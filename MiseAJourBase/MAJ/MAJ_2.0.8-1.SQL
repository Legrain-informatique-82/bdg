CREATE or ALTER  PROCEDURE GESTION_INSERTION_DATEANNIV
as    
DECLARE VARIABLE QUERY VARCHAR(5000);
BEGIN
query ='';
IF (not exists( SELECT a.RDB$FIELD_NAME, a.RDB$RELATION_NAME FROM RDB$RELATION_FIELDS a
where a.RDB$RELATION_NAME = 'TA_TIERS' and  a.RDB$FIELD_NAME ='DATE_ANNIV')) THEN
  begin
    query = 'ALTER TABLE TA_TIERS ADD DATE_ANNIV DATE_LGR';
    EXECUTE STATEMENT :query; 
  end
    
END
^
EXECUTE PROCEDURE GESTION_INSERTION_DATEANNIV
^
drop procedure GESTION_INSERTION_DATEANNIV
^


CREATE or ALTER  PROCEDURE GESTION_ALTER_TABLE1
as    
DECLARE VARIABLE QUERY VARCHAR(500);
BEGIN
query ='';
IF (not exists( SELECT a.RDB$FIELD_NAME, a.RDB$RELATION_NAME FROM RDB$RELATION_FIELDS a
where a.RDB$RELATION_NAME = 'TA_T_TVA_DOC' and  a.RDB$FIELD_NAME ='LIBELLE_EDITION')) THEN
  begin
    query = 'ALTER TABLE TA_T_TVA_DOC ADD LIBELLE_EDITION DLIB255';
    EXECUTE STATEMENT :query;
  end
END
^
EXECUTE PROCEDURE GESTION_ALTER_TABLE1
^
drop procedure GESTION_ALTER_TABLE1
^


CREATE or ALTER  PROCEDURE GESTION_VISUALISATION1
as    
DECLARE VARIABLE QUERY VARCHAR(5000);
BEGIN
    query ='';
    query ='delete from TA_VISUALISATION where  lower(CODE_REQUETE)=''liste des tiers''';  
     EXECUTE STATEMENT :query;
    query ='';
    if(not exists(select * from TA_VISUALISATION where lower(CODE_REQUETE) like 'liste des tiers'))then
    begin
         query = 'INSERT INTO TA_VISUALISATION (ID, CODE_REQUETE, REQUETE, CHAMPS, TITRE_CHAMPS, TAILLE_CHAMPS, MODULE, IMPRESSION,
 IDENTIFIANT, ID_EDITEUR, ID_PLUGIN, CLAUSE_WHERE, GROUPBY, CLAUSE_HAVING, PROC, PARAM, TYPES_RETOUR, TOTAUX, QUERY_LANG, CLASSE_RECHERCHE) 
VALUES (gen_id(num_id_visualisation,1), ''Liste des tiers'', ''select t.codeTiers,t.codeCompta,t.compte,t.nomTiers,t.dateAnniv,t.actifTiers,t.ttcTiers,c.codeTCivilite,e.nomEntreprise,
tt.codeTTiers,te.codeTEntite from TaTiers t left join t.taTCivilite c left join t.taEntreprise e left join t.taTTiers tt left join t.taTEntite te'',
 ''t.codeTiers;t.codeCompta;t.compte;t.nomTiers;t.dateAnniv;t.actifTiers;t.ttcTiers;c.codeTCivilite;e.codeEntreprise;tt.codeTTiers;te.codeTEntite'',
 ''Code tiers;Code compta;Compte;Nom;Anniversaire;Actif;TTC;Code Type Civilite;Code Entreprise;Code Type Tiers;Code Type Entite'',
 ''00;50;50;100;50;30;30;50;50;50;50'', ''tiers'', ''aa'', ''t.codeTiers'', ''fr.legrain.tiers.editor.EditorTiers'', ''Tiers'', NULL, NULL, NULL, 0,
 NULL, ''s;s;s;s;d;b;b;s;s;s;s'', '''', ''JPQL'', NULL)';
        EXECUTE STATEMENT :query;
    end
    
END
^
EXECUTE PROCEDURE GESTION_VISUALISATION1
^
drop procedure GESTION_VISUALISATION1
^

 CREATE or ALTER  PROCEDURE GESTION_VISUALISATION2
as    
DECLARE VARIABLE QUERY VARCHAR(8000);
BEGIN
    query ='';
    query ='delete from TA_VISUALISATION where  lower(CODE_REQUETE)=''extraction tiers - facturés''';  
     EXECUTE STATEMENT :query;
    query ='';
    if(not exists(select * from TA_VISUALISATION where lower(CODE_REQUETE) like 'extraction tiers - facturés'))then
    begin
         query = 'INSERT INTO TA_VISUALISATION (ID, CODE_REQUETE, REQUETE, CHAMPS, TITRE_CHAMPS, TAILLE_CHAMPS, MODULE, IMPRESSION, IDENTIFIANT,
 ID_EDITEUR, ID_PLUGIN, CLAUSE_WHERE, GROUPBY, CLAUSE_HAVING, PROC, PARAM, TYPES_RETOUR, TOTAUX, QUERY_LANG, CLASSE_RECHERCHE) VALUES
 (gen_id(num_id_visualisation,1), ''Extraction tiers - Facturés'', ''select t.nomTiers,t.prenomTiers,t.dateAnniv,a.adresse1Adresse, a.adresse2Adresse,a.adresse3Adresse, 
 a.codepostalAdresse, a.villeAdresse, a.paysAdresse,substring(a.codepostalAdresse,1,2),tc.codeTCivilite,t.surnomTiers, t.codeTiers,e.nomEntreprise
 ,t.actifTiers,t.ttcTiers,tt.codeTTiers,  tt.libelleTTiers,c.commentaire,te.codeTEntite, co.tvaIComCompl,  co.siretCompl,tel.numeroTelephone,
 mail.adresseEmail,w.adresseWeb,t.quandCreeTiers,max(f.dateDocument) from fr.legrain.tiers.dao.TaTiers t  left join t.taAdresse a left join
 t.taTCivilite tc left join t.taEntreprise e left join t.taTTiers tt left join t.taCommentaire c left join t.taTEntite te left join t.taCompl co 
 left join t.taTelephone tel left join t.taEmail mail left join t.taWeb w,TaFacture f left join f.taTiers tf where tf.idTiers=t.idTiers'', 
''t.nomTiers;t.prenomTiers;t.dateAnniv;a.adresse1Adresse;a.adresse2Adresse;a.adresse3Adresse;a.codepostalAdresse;a.villeAdresse;a.paysAdresse;
substring(a.codepostalAdresse,1,2);tc.codeTCivilite;t.surnomTiers;t.codeTiers;e.nomEntreprise;t.actifTiers;t.ttcTiers;tt.codeTTiers;
tt.libelleTTiers;c.commentaire;te.codeTEntite;co.tvaIComCompl;co.siretCompl;tel.numeroTelephone;mail.adresseEmail;w.adresseWeb;t.quandCreeTiers
;max(f.dateDocument)!having;max(bonliv.dateDocument)!having;max(devis.dateDocument)!having'', ''Nom;Prénom;Anniversaire;Adresse 1;Adresse 2;
Adresse 3;Code postal;Ville;Pays;Département;Civilité;Surnom;Code tiers;Nom entreprise;Actif;TTC;Type tiers;Libellé type;Commentaire;Type entité;
Tva intra;Siret;Tel;Email;Web;Date création;Date dernière facture;Date dernier bonliv;Date dernier devis'',
 ''50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50'', ''tiers'', ''/report/extraction_Tiers.rptdesign'',
 ''t.codeTiers'', ''fr.legrain.tiers.editor.EditorTiers'', ''Tiers'', NULL, ''group by t.nomTiers,t.prenomTiers,t.dateAnniv,a.adresse1Adresse,
 a.adresse2Adresse,a.adresse3Adresse, a.codepostalAdresse, a.villeAdresse, a.paysAdresse, substring(a.codepostalAdresse,1,2), tc.codeTCivilite,
 t.surnomTiers, t.codeTiers, e.nomEntreprise, t.actifTiers, t.ttcTiers, tt.codeTTiers, tt.libelleTTiers, c.commentaire, te.codeTEntite, 
 co.tvaIComCompl, co.siretCompl, tel.numeroTelephone, mail.adresseEmail, w.adresseWeb, t.quandCreeTiers'', NULL, 0, NULL, 
''s;s;d;s;s;s;s;s;s;i;s;s;s;s;b;b;s;s;s;s;s;s;s;s;s;d;d;d;d'', NULL, ''JPQL'', NULL)';
        EXECUTE STATEMENT :query;
    end
    
END
^
EXECUTE PROCEDURE GESTION_VISUALISATION2
^
drop procedure GESTION_VISUALISATION2
^

CREATE or ALTER  PROCEDURE GESTION_VISUALISATION3
as    
DECLARE VARIABLE QUERY VARCHAR(8000);
BEGIN
    query ='';
    query ='delete from TA_VISUALISATION where  lower(CODE_REQUETE)=''extraction tiers - tous''';  
     EXECUTE STATEMENT :query;
    query ='';
    if(not exists(select * from TA_VISUALISATION where lower(CODE_REQUETE) like 'extraction tiers - tous'))then
    begin
         query = 'INSERT INTO TA_VISUALISATION (ID, CODE_REQUETE, REQUETE, CHAMPS, TITRE_CHAMPS, TAILLE_CHAMPS, MODULE, IMPRESSION, IDENTIFIANT, 
ID_EDITEUR, ID_PLUGIN, CLAUSE_WHERE, GROUPBY, CLAUSE_HAVING, PROC, PARAM, TYPES_RETOUR, TOTAUX, QUERY_LANG, CLASSE_RECHERCHE) VALUES 
(gen_id(num_id_visualisation,1), ''Extraction tiers - Tous'', ''select distinct(t.nomTiers),t.prenomTiers,t.dateAnniv,a.adresse1Adresse, a.adresse2Adresse,a.adresse3Adresse,
a.codepostalAdresse, a.villeAdresse, a.paysAdresse,substring(a.codepostalAdresse,1,2),tc.codeTCivilite,t.surnomTiers, t.codeTiers,e.nomEntreprise
,t.actifTiers,t.ttcTiers,tt.codeTTiers,  tt.libelleTTiers,c.commentaire,te.codeTEntite, co.tvaIComCompl,  co.siretCompl,tel.numeroTelephone,
mail.adresseEmail,w.adresseWeb,t.quandCreeTiers
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
        left join t.taWeb w'', ''t.nomTiers;t.prenomTiers;t.dateAnniv;a.adresse1Adresse;a.adresse2Adresse;a.adresse3Adresse;a.codepostalAdresse
        ;a.villeAdresse;a.paysAdresse;substring(a.codepostalAdresse,1,2);tc.codeTCivilite;t.surnomTiers;t.codeTiers;e.nomEntreprise;t.actifTiers
        ;t.ttcTiers;tt.codeTTiers;tt.libelleTTiers;c.commentaire;te.codeTEntite;co.tvaIComCompl;co.siretCompl;tel.numeroTelephone;mail.adresseEmail
        ;w.adresseWeb;t.quandCreeTiers'', ''Nom;Prénom;Anniversaire;Adresse 1;Adresse 2;Adresse 3;Code poastal;Ville;Pays;Département;Civilité;
        Surnom;Code tiers;Nom entreprise;Actif;TTC;Type tiers;Libellé type;Commentaire;Type entité;Tva intra;Siret;Tel;Email;Web'', 
''50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50'', ''tiers'', ''/report/extraction_Tiers.rptdesign'', ''t.codeTiers'',
 ''fr.legrain.tiers.editor.EditorTiers'', ''Tiers'', NULL, '''', NULL, 0, NULL, ''s;s;d;s;s;s;s;s;s;i;s;s;s;s;b;b;s;s;s;s;s;s;s;s;s'', NULL, ''JPQL'', NULL)';
        EXECUTE STATEMENT :query;
    end
    
END
^
EXECUTE PROCEDURE GESTION_VISUALISATION3
^
drop procedure GESTION_VISUALISATION3
^
