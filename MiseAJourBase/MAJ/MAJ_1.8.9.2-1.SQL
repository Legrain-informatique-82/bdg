insert into dependances select * FROM RDB$DEPENDENCIES
^


delete  FROM RDB$DEPENDENCIES
^

RECREATE VIEW V_TIERS(
    ID_TIERS,
    CODE_TIERS,
    CODE_COMPTA,
    COMPTE,
    NOM_TIERS,
    PRENOM_TIERS,
    SURNOM_TIERS,
    ACTIF_TIERS,
    TTC_TIERS,
    ID_T_CIVILITE,
    CODE_T_CIVILITE,
    ID_ENTREPRISE,
    CODE_ENTREPRISE,
    LIBL_ENTREPRISE,
    ID_T_ENTITE_ENTREPRISE,
    CODE_T_ENTITE_ENTREPRISE,
    ID_T_TIERS,
    CODE_T_TIERS,
    LIBELLE_T_TIERS,
    ID_COMMENTAIRE,
    LIBL_COMMENTAIRE,
    ID_I_BANQUE,
    CODE_BANQUE,
    LIBC_BANQUE,
    LIBL_BANQUE,
    ID_T_ENTITE,
    CODE_T_ENTITE,
    TVA_I_COM_COMPL,
    SIRET_COMPL)
AS
select
  Tiers.ID_TIERS,
  Tiers.CODE_TIERS,
  Tiers.CODE_COMPTA,
  Tiers.COMPTE,
  Tiers.NOM_TIERS,
  Tiers.PRENOM_TIERS,
  Tiers.SURNOM_TIERS,
  Tiers.ACTIF_TIERS,
  Tiers.TTC_TIERS,
  Tiers.ID_T_CIVILITE,
  Civilite.CODE_T_CIVILITE,
  Tiers.ID_ENTREPRISE,
  Entreprise.CODE_ENTREPRISE,
  Entreprise.LIBL_ENTREPRISE,
  Entreprise.id_t_entite,
  Entite_entreprise.code_t_entite,
  Tiers.ID_T_TIERS,
  TTiers.CODE_T_TIERS,
  TTiers.LIBELLE_T_TIERS,
  Com.ID_COMMENTAIRE,
  Com.LIBL_COMMENTAIRE,
  Tiers.ID_I_BANQUE,
  Banque.CODE_BANQUE,
  Banque.LIBC_BANQUE,
  Banque.LIBL_BANQUE,
  Entite.ID_T_ENTITE,
  Entite.CODE_T_ENTITE,
  compl.tva_i_com_compl,
  compl.siret_compl
  
  from TA_TIERS Tiers
  
  LEFT OUTER join TA_T_ENTITE Entite on Tiers.ID_T_ENTITE = Entite.ID_T_ENTITE
  LEFT OUTER join TA_T_CIVILITE Civilite on Tiers.ID_T_CIVILITE = Civilite.ID_T_CIVILITE
  LEFT OUTER join TA_ENTREPRISE Entreprise on Tiers.ID_ENTREPRISE = Entreprise.ID_ENTREPRISE
  LEFT OUTER join TA_T_ENTITE Entite_entreprise on Entreprise.id_t_entite = Entite_entreprise.id_t_entite
  LEFT OUTER join TA_T_TIERS TTiers on Tiers.ID_T_TIERS = TTiers.ID_T_TIERS
  LEFT OUTER join TA_COMMENTAIRE Com on Tiers.ID_TIERS = Com.ID_TIERS
  LEFT OUTER join TA_BANQUE Banque on Tiers.ID_I_BANQUE = Banque.ID_BANQUE
  LEFT OUTER join ta_compl compl on compl.id_tiers = Tiers.id_tiers
;

CREATE or ALTER  TRIGGER TBIID_ARTICLE_L_FACTURE FOR TA_L_FACTURE
ACTIVE BEFORE INSERT POSITION 1
as
DECLARE variable numrows integer;
declare variable typeligneH integer;
declare variable typeligneC integer;
declare variable commentaire integer;
begin
  /* E_XAKID_ARTICLE */
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
    select id_article from ta_article where id_famille=:commentaire and ta_article.id_article = new.id_article)) ) then
     begin
        new.id_article=null;
        new.qte_l_facture=null;
        new.u1_l_facture=null;
        new.u2_l_facture=null;
        new.prix_u_l_facture=null;
        new.taux_tva_l_facture=null;
        new.compte_l_facture=null;
        new.code_tva_l_facture=null;
        new.code_t_tva_l_facture=null;
        new.mt_ht_l_facture=null;
        new.mt_ttc_l_facture=null;
        new.rem_tx_l_facture=null;
        new.rem_ht_l_facture=null;
     end
end
^

CREATE OR ALTER TRIGGER TBUID_ARTICLE_L_FACTURE FOR TA_L_FACTURE
ACTIVE BEFORE UPDATE POSITION 0
as
DECLARE variable numrows integer;
declare variable typeligneH integer;
declare variable typeligneC integer;
declare variable commentaire integer;
begin
  /* E_XAKID_ARTICLE */
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
    select id_article from ta_article where id_famille=:commentaire and ta_article.id_article = new.id_article)) ) then
     begin
        new.id_article=null;
        new.qte_l_facture=null;
        new.u1_l_facture=null;
        new.u2_l_facture=null;
        new.prix_u_l_facture=null;
        new.taux_tva_l_facture=null;
        new.compte_l_facture=null;
        new.code_tva_l_facture=null;
        new.code_t_tva_l_facture=null;
        new.mt_ht_l_facture=null;
        new.mt_ttc_l_facture=null;
        new.rem_tx_l_facture=null;
        new.rem_ht_l_facture=null;
     end
end
^


CREATE OR ALTER TRIGGER TBIID_ARTICLE_L_DEVIS FOR TA_L_DEVIS
ACTIVE BEFORE INSERT POSITION 1
as
DECLARE variable numrows integer;
declare variable typeligneH integer;
declare variable typeligneC integer;
declare variable commentaire integer;
begin
  /* E_XAKID_ARTICLE */
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
    select id_article from ta_article where id_famille=:commentaire and ta_article.id_article = new.id_article)) ) then
     begin
        new.id_article=null;
        new.qte_l_devis=null;
        new.u1_l_devis=null;
        new.u2_l_devis=null;
        new.prix_u_l_devis=null;
        new.taux_tva_l_devis=null;
        new.compte_l_devis=null;
        new.code_tva_l_devis=null;
        new.code_t_tva_l_devis=null;
        new.mt_ht_l_devis=null;
        new.mt_ttc_l_devis=null;
        new.rem_tx_l_devis=null;
        new.rem_ht_l_devis=null;
     end
end
^

CREATE or ALTER TRIGGER TBUID_ARTICLE_L_DEVIS FOR TA_L_DEVIS
ACTIVE BEFORE UPDATE POSITION 0
as
DECLARE variable numrows integer;
declare variable typeligneH integer;
declare variable typeligneC integer;
declare variable commentaire integer;
begin
  /* E_XAKID_ARTICLE */
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
    select id_article from ta_article where id_famille=:commentaire and ta_article.id_article = new.id_article)) ) then
     begin
        new.id_article=null;
        new.qte_l_devis=null;
        new.u1_l_devis=null;
        new.u2_l_devis=null;
        new.prix_u_l_devis=null;
        new.taux_tva_l_devis=null;
        new.compte_l_devis=null;
        new.code_tva_l_devis=null;
        new.code_t_tva_l_devis=null;
        new.mt_ht_l_devis=null;
        new.mt_ttc_l_devis=null;
        new.rem_tx_l_devis=null;
        new.rem_ht_l_devis=null;
     end
end
^

CREATE or ALTER TRIGGER TBIID_ARTICLE_L_BONLIV FOR TA_L_BONLIV
ACTIVE BEFORE INSERT POSITION 1
as
DECLARE variable numrows integer;
declare variable typeligneH integer;
declare variable typeligneC integer;
declare variable commentaire integer;
begin
  /* E_XAKID_ARTICLE */
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
    select id_article from ta_article where id_famille=:commentaire and ta_article.id_article = new.id_article)) ) then
     begin
        new.id_article=null;
        new.qte_l_BONLIV=null;
        new.u1_l_BONLIV=null;
        new.u2_l_BONLIV=null;
        new.prix_u_l_BONLIV=null;
        new.taux_tva_l_BONLIV=null;
        new.compte_l_BONLIV=null;
        new.code_tva_l_BONLIV=null;
        new.code_t_tva_l_BONLIV=null;
        new.mt_ht_l_BONLIV=null;
        new.mt_ttc_l_BONLIV=null;
        new.rem_tx_l_BONLIV=null;
        new.rem_ht_l_BONLIV=null;
     end
end
^

CREATE or ALTER TRIGGER TBIID_T_LIGNE_L_BONLIV FOR TA_L_BONLIV
ACTIVE BEFORE INSERT POSITION 1
as
DECLARE variable numrows integer;
begin
  /* E_XAKID_T_LIGNE */
   if (NEW.ID_T_LIGNE is not null) then
       begin
       select count(*) from TA_T_LIGNE
       where ID_T_LIGNE= NEW.ID_T_LIGNE
       into :numrows;
       if (numrows = 0) then
          exception E_XAKID_T_LIGNE;
       end   
end
^

CREATE or ALTER TRIGGER TBI_L_BONLIV FOR TA_L_BONLIV
ACTIVE BEFORE INSERT POSITION 2
as
begin
   If (New.ID_L_BONLIV  is null) Then
      New.ID_L_BONLIV  = GEN_ID(NUM_ID_L_BONLIV,1);
   New.QUI_CREE_L_BONLIV  = USER;
   New.QUAND_CREE_L_BONLIV = 'NOW';
   New.QUI_MODIF_L_BONLIV = USER;
   New.QUAND_MODIF_L_BONLIV = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^

CREATE or ALTER TRIGGER TBUID_ARTICLE_L_BONLIV FOR TA_L_BONLIV
ACTIVE BEFORE UPDATE POSITION 1
as
DECLARE variable numrows integer;
declare variable typeligneH integer;
declare variable typeligneC integer;
declare variable commentaire integer;
begin
  /* E_XAKID_ARTICLE */
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
    select id_article from ta_article where id_famille=:commentaire and ta_article.id_article = new.id_article)) ) then
     begin
        new.id_article=null;
        new.qte_l_BONLIV=null;
        new.u1_l_BONLIV=null;
        new.u2_l_BONLIV=null;
        new.prix_u_l_BONLIV=null;
        new.taux_tva_l_BONLIV=null;
        new.compte_l_BONLIV=null;
        new.code_tva_l_BONLIV=null;
        new.code_t_tva_l_BONLIV=null;
        new.mt_ht_l_BONLIV=null;
        new.mt_ttc_l_BONLIV=null;
        new.rem_tx_l_BONLIV=null;
        new.rem_ht_l_BONLIV=null;
     end
end
^

CREATE or ALTER TRIGGER TBUID_T_LIGNE_L_BONLIV FOR TA_L_BONLIV
ACTIVE BEFORE UPDATE POSITION 1
as
DECLARE variable numrows integer;
begin
  /* E_XAKID_T_LIGNE */
   if ((OLD.ID_T_LIGNE <> NEW.ID_T_LIGNE)) then
   begin
     select count(*) from TA_T_LIGNE
     where ID_T_LIGNE = NEW.ID_T_LIGNE
     into :numrows;
     if (numrows = 0) then
        exception E_XAKID_T_LIGNE;
   end
end
^

CREATE or ALTER TRIGGER TBU_L_BONLIV FOR TA_L_BONLIV
ACTIVE BEFORE UPDATE POSITION 2
as
begin
   new.QUI_MODIF_L_BONLIV = USER;
   new.QUAND_MODIF_L_BONLIV = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


CREATE or ALTER  PROCEDURE GESTION_DROP_TRIGGER1
as    
DECLARE VARIABLE QUERY VARCHAR(500);
BEGIN
query ='';
IF (exists( SELECT a.RDB$TRIGGER_NAME
FROM RDB$TRIGGERS a
where a.RDB$TRIGGER_NAME ='TBUID_L_DEVIS_ETRANGER')) THEN
  begin
    query = 'DROP TRIGGER TBUID_L_DEVIS_ETRANGER';
    EXECUTE STATEMENT :query;
  end
END
^
EXECUTE PROCEDURE GESTION_DROP_TRIGGER1
^
drop procedure GESTION_DROP_TRIGGER1
^


CREATE or ALTER  PROCEDURE GESTION_ALTER_TABLE1
as    
DECLARE VARIABLE QUERY VARCHAR(255);
BEGIN
query ='';
IF (not exists( SELECT a.RDB$FIELD_NAME, a.RDB$RELATION_NAME FROM RDB$RELATION_FIELDS a
where a.RDB$RELATION_NAME = 'TA_INFOS_FACTURE' and  a.RDB$FIELD_NAME ='LIBL_ENTREPRISE')) THEN
  begin
    query = 'ALTER TABLE TA_INFOS_FACTURE  ADD LIBL_ENTREPRISE DLIB100';
    EXECUTE STATEMENT :query;
  end
END
^
EXECUTE PROCEDURE GESTION_ALTER_TABLE1
^
drop procedure GESTION_ALTER_TABLE1
^

CREATE or ALTER  PROCEDURE GESTION_ALTER_TABLE2
as    
DECLARE VARIABLE QUERY VARCHAR(255);
BEGIN
query ='';
IF (not exists( SELECT a.RDB$FIELD_NAME, a.RDB$RELATION_NAME FROM RDB$RELATION_FIELDS a
where a.RDB$RELATION_NAME = 'TA_INFOS_FACTURE' and  a.RDB$FIELD_NAME ='CODE_T_ENTITE_ENTREPRISE')) THEN
  begin
    query = 'ALTER TABLE TA_INFOS_FACTURE  ADD CODE_T_ENTITE_ENTREPRISE DLGR_CODEL';
    EXECUTE STATEMENT :query;
  end
END
^
EXECUTE PROCEDURE GESTION_ALTER_TABLE2
^
drop procedure GESTION_ALTER_TABLE2
^

CREATE or ALTER  PROCEDURE GESTION_ALTER_TABLE3
as    
DECLARE VARIABLE QUERY VARCHAR(255);
BEGIN
query ='';
IF (not exists( SELECT a.RDB$FIELD_NAME, a.RDB$RELATION_NAME FROM RDB$RELATION_FIELDS a
where a.RDB$RELATION_NAME = 'TA_INFOS_FACTURE' and  a.RDB$FIELD_NAME ='CODE_ENTREPRISE')) THEN
  begin
    query = 'ALTER TABLE TA_INFOS_FACTURE  ADD CODE_ENTREPRISE DLGR_CODEL';
    EXECUTE STATEMENT :query;
  end
END
^
EXECUTE PROCEDURE GESTION_ALTER_TABLE3
^
drop procedure GESTION_ALTER_TABLE3
^



CREATE or ALTER  PROCEDURE GESTION_ALTER_TABLE4
as    
DECLARE VARIABLE QUERY VARCHAR(255);
BEGIN
query ='';
IF (not exists( SELECT a.RDB$FIELD_NAME, a.RDB$RELATION_NAME FROM RDB$RELATION_FIELDS a
where a.RDB$RELATION_NAME = 'TA_INFOS_DEVIS' and  a.RDB$FIELD_NAME ='LIBL_ENTREPRISE')) THEN
  begin
    query = 'ALTER TABLE TA_INFOS_DEVIS  ADD LIBL_ENTREPRISE DLIB100';
    EXECUTE STATEMENT :query;
  end
END
^
EXECUTE PROCEDURE GESTION_ALTER_TABLE4
^
drop procedure GESTION_ALTER_TABLE4
^

CREATE or ALTER  PROCEDURE GESTION_ALTER_TABLE5
as    
DECLARE VARIABLE QUERY VARCHAR(255);
BEGIN
query ='';
IF (not exists( SELECT a.RDB$FIELD_NAME, a.RDB$RELATION_NAME FROM RDB$RELATION_FIELDS a
where a.RDB$RELATION_NAME = 'TA_INFOS_DEVIS' and  a.RDB$FIELD_NAME ='CODE_T_ENTITE_ENTREPRISE')) THEN
  begin
    query = 'ALTER TABLE TA_INFOS_DEVIS  ADD CODE_T_ENTITE_ENTREPRISE DLGR_CODEL';
    EXECUTE STATEMENT :query;
  end
END
^
EXECUTE PROCEDURE GESTION_ALTER_TABLE5
^
drop procedure GESTION_ALTER_TABLE5
^

CREATE or ALTER  PROCEDURE GESTION_ALTER_TABLE6
as    
DECLARE VARIABLE QUERY VARCHAR(255);
BEGIN
query ='';
IF (not exists( SELECT a.RDB$FIELD_NAME, a.RDB$RELATION_NAME FROM RDB$RELATION_FIELDS a
where a.RDB$RELATION_NAME = 'TA_INFOS_DEVIS' and  a.RDB$FIELD_NAME ='CODE_ENTREPRISE')) THEN
  begin
    query = 'ALTER TABLE TA_INFOS_DEVIS  ADD CODE_ENTREPRISE DLGR_CODEL';
    EXECUTE STATEMENT :query;
  end
END
^
EXECUTE PROCEDURE GESTION_ALTER_TABLE6
^
drop procedure GESTION_ALTER_TABLE6
^


CREATE or ALTER  PROCEDURE GESTION_ALTER_TABLE7
as    
DECLARE VARIABLE QUERY VARCHAR(255);
BEGIN
query ='';
IF (not exists( SELECT a.RDB$FIELD_NAME, a.RDB$RELATION_NAME FROM RDB$RELATION_FIELDS a
where a.RDB$RELATION_NAME = 'TA_INFOS_BONLIV' and  a.RDB$FIELD_NAME ='LIBL_ENTREPRISE')) THEN
  begin
    query = 'ALTER TABLE TA_INFOS_BONLIV  ADD LIBL_ENTREPRISE DLIB100';
    EXECUTE STATEMENT :query;
  end
END
^
EXECUTE PROCEDURE GESTION_ALTER_TABLE7
^
drop procedure GESTION_ALTER_TABLE7
^

CREATE or ALTER  PROCEDURE GESTION_ALTER_TABLE8
as    
DECLARE VARIABLE QUERY VARCHAR(255);
BEGIN
query ='';
IF (not exists( SELECT a.RDB$FIELD_NAME, a.RDB$RELATION_NAME FROM RDB$RELATION_FIELDS a
where a.RDB$RELATION_NAME = 'TA_INFOS_BONLIV' and  a.RDB$FIELD_NAME ='CODE_T_ENTITE_ENTREPRISE')) THEN
  begin
    query = 'ALTER TABLE TA_INFOS_BONLIV  ADD CODE_T_ENTITE_ENTREPRISE DLGR_CODEL';
    EXECUTE STATEMENT :query;
  end
END
^
EXECUTE PROCEDURE GESTION_ALTER_TABLE8
^
drop procedure GESTION_ALTER_TABLE8
^

CREATE or ALTER  PROCEDURE GESTION_ALTER_TABLE9
as    
DECLARE VARIABLE QUERY VARCHAR(255);
BEGIN
query ='';
IF (not exists( SELECT a.RDB$FIELD_NAME, a.RDB$RELATION_NAME FROM RDB$RELATION_FIELDS a
where a.RDB$RELATION_NAME = 'TA_INFOS_BONLIV' and  a.RDB$FIELD_NAME ='CODE_ENTREPRISE')) THEN
  begin
    query = 'ALTER TABLE TA_INFOS_BONLIV  ADD CODE_ENTREPRISE DLGR_CODEL';
    EXECUTE STATEMENT :query;
  end
END
^
EXECUTE PROCEDURE GESTION_ALTER_TABLE9
^
drop procedure GESTION_ALTER_TABLE9
^

CREATE or ALTER  PROCEDURE GESTION_INSERT_TABLE1
as    
DECLARE VARIABLE QUERY VARCHAR(255);
BEGIN
query ='';
IF (not exists( SELECT CODE_T_TIERS FROM TA_T_TIERS WHERE CODE_T_TIERS = 'P')) THEN
  begin
    query = 'Insert into TA_T_TIERS  (CODE_T_TIERS,LIBELLE_T_TIERS,COMPTE_T_TIERS)values(''P'',''Prospects'',''411'')';
    EXECUTE STATEMENT :query;
  end
END
^
EXECUTE PROCEDURE GESTION_INSERT_TABLE1
^
drop procedure GESTION_INSERT_TABLE1
^

CREATE or ALTER  PROCEDURE GESTION_CREATION_1
as    
DECLARE VARIABLE QUERY VARCHAR(255);
BEGIN
query = 'ALTER DOMAIN DCODPOS TYPE VARCHAR(25)';
IF (not exists( select RDB$FIELD_NAME from RDB$FIELDS where RDB$FIELD_NAME = 'DCODPOS')) THEN
query = 'CREATE DOMAIN DCODPOS AS VARCHAR(25)';

EXECUTE STATEMENT :query;

END
^

EXECUTE PROCEDURE GESTION_CREATION_1 
^
drop procedure GESTION_CREATION_1
^

delete from TA_VISUALISATION
^

INSERT INTO TA_VISUALISATION (ID, CODE_REQUETE, REQUETE, CHAMPS, TITRE_CHAMPS, TAILLE_CHAMPS, MODULE, IMPRESSION, IDENTIFIANT, ID_EDITEUR, ID_PLUGIN, GROUPBY, PROC, PARAM) VALUES (1, 'Liste des articles', 'select CODE_ARTICLE,LIBELLEC_ARTICLE,NUMCPT_ARTICLE,DIVERS_ARTICLE,COMMENTAIRE_ARTICLE,STOCK_MIN_ARTICLE,PRIX_PRIX,CODE_UNITE,CODE_FAMILLE,CODE_TVA,TAUX_TVA,NUMCPT_TVA,CODE_T_TVA  from v_article', 'CODE_ARTICLE;LIBELLEC_ARTICLE;NUMCPT_ARTICLE;DIVERS_ARTICLE;COMMENTAIRE_ARTICLE;STOCK_MIN_ARTICLE;PRIX_PRIX;CODE_UNITE;CODE_FAMILLE;CODE_TVA;TAUX_TVA;NUMCPT_TVA;CODE_T_TVA', 'CODE;LIBELLE;COMPTE;DIVERS;COMMENTAIRE;STOCK MINI;PRIX;UNITE;FAMILLE;CODE TVA;TAUX TVA;COMPTE TVA;TYPE TVA', '50;100;50;100;100;20;50;20;50;50;50;50;50', 'article', '/report/article.rptdesign', 'CODE_ARTICLE', 'fr.legrain.articles.editor.EditorArticle', 'Articles', NULL, 0, NULL)
^
INSERT INTO TA_VISUALISATION (ID, CODE_REQUETE, REQUETE, CHAMPS, TITRE_CHAMPS, TAILLE_CHAMPS, MODULE, IMPRESSION, IDENTIFIANT, ID_EDITEUR, ID_PLUGIN, GROUPBY, PROC, PARAM) VALUES (2, 'liste des Factures', 'select CODE_FACTURE,DATE_FACTURE,CODE_TIERS,NOM_TIERS,(MTNETTTC)as MTNETTTC ,EXPORT FROM  V_facture , calcul_total_direct(''facture'',id_facture)', 'CODE_FACTURE;DATE_FACTURE;CODE_TIERS;NOM_TIERS;MTNETTTC;EXPORT', 'CODE;DATE FACTURE;CODE TIERS;NOM TIERS;NET TTC;EXPORTEE', '100;100;100;100;200;100;100;100', 'facture', '/report/visu_facture.rptdesign', 'CODE_FACTURE', 'fr.legrain.editor.facture.swt.multi', 'Facture', NULL, 0, NULL)
^
INSERT INTO TA_VISUALISATION (ID, CODE_REQUETE, REQUETE, CHAMPS, TITRE_CHAMPS, TAILLE_CHAMPS, MODULE, IMPRESSION, IDENTIFIANT, ID_EDITEUR, ID_PLUGIN, GROUPBY, PROC, PARAM) VALUES (3, 'Liste des tiers', 'select CODE_TIERS,CODE_COMPTA,COMPTE,NOM_TIERS,ACTIF_TIERS,TTC_TIERS,CODE_T_CIVILITE,CODE_ENTREPRISE,CODE_T_TIERS,CODE_BANQUE,CODE_T_ENTITE from v_tiers', 'CODE_TIERS;CODE_COMPTA;COMPTE;NOM_TIERS;ACTIF_TIERS;TTC_TIERS;CODE_T_CIVILITE;CODE_ENTREPRISE;CODE_T_TIERS;CODE_BANQUE;CODE_T_ENTITE', 'CODE_TIERS;CODE_COMPTA;COMPTE;NOM_TIERS;ACTIF_TIERS;TTC_TIERS;CODE_T_CIVILITE;CODE_ENTREPRISE;CODE_T_TIERS;CODE_BANQUE;CODE_T_ENTITE', '100;50;50;100;30;30;50;50;50;50;50', 'tiers', '/report/tiers.rptdesign', 'CODE_TIERS', 'fr.legrain.tiers.editor.EditorTiers', 'Tiers', NULL, 0, NULL)
^
INSERT INTO TA_VISUALISATION (ID, CODE_REQUETE, REQUETE, CHAMPS, TITRE_CHAMPS, TAILLE_CHAMPS, MODULE, IMPRESSION, IDENTIFIANT, ID_EDITEUR, ID_PLUGIN, GROUPBY, PROC, PARAM) VALUES (4, 'Etat des stocks', 'select CODE_ARTICLE,LIBELLE_ARTICLE,QTE,UNITE,PERIODE_DEB,PERIODE_FIN,REPORT_QTE from VISU_ETAT_STOCKS(null,null,null,null,null,null)', 'CODE_ARTICLE;LIBELLE_ARTICLE;QTE;UNITE;PERIODE_DEB;PERIODE_FIN', 'Code article;Libell� de l''article;Quantit�;Unit�;Date d�but p�riode;Date fin p�riode', '100;100;100;100;100;100', 'stocks', '/report/EtatStocks.rptdesign', 'CODE_ARTICLE', 'fr.legrain.stocks.editor.EditorStocks', 'Stocks', NULL, 1, NULL);
^
INSERT INTO TA_VISUALISATION (ID, CODE_REQUETE, REQUETE, CHAMPS, TITRE_CHAMPS, TAILLE_CHAMPS, MODULE, IMPRESSION, IDENTIFIANT, ID_EDITEUR, ID_PLUGIN, GROUPBY, PROC, PARAM) VALUES (5, 'Liste des bons de livraison', ' select b.code_bonliv,b.date_bonliv,CODE_TIERS,NOM_TIERS,(MTNETTTC)as MTNETTTC  FROM v_bonliv b , calcul_total_direct(''BonLiv'',b.id_bonliv)', 'code_bonliv;date_bonliv;CODE_TIERS;NOM_TIERS;MTNETTTC', 'CODE;DATE BONLIV;CODE TIERS;NOM TIERS;NET TTC', '100;100;100;100;100', 'BONLIV', '/report/visu_BonLiv.rptdesign', 'CODE_BONLIV', 'fr.legrain.editor.Bl.swt.multi', 'BonLiv', NULL, 0, NULL)
^
INSERT INTO TA_VISUALISATION (ID, CODE_REQUETE, REQUETE, CHAMPS, TITRE_CHAMPS, TAILLE_CHAMPS, MODULE, IMPRESSION, IDENTIFIANT, ID_EDITEUR, ID_PLUGIN, GROUPBY, PROC, PARAM) VALUES (6, 'Liste des devis', ' select b.code_devis,b.date_devis,CODE_TIERS,NOM_TIERS,(MTNETTTC)as MTNETTTC  FROM v_devis b , calcul_total_direct(''devis'',b.id_devis)', 'code_devis;date_devis;CODE_TIERS;NOM_TIERS;MTNETTTC', 'CODE;DATE DEVIS;CODE TIERS;NOM TIERS;NET TTC', '100;100;100;100;100', 'DEVIS', '/report/visu_devis.rptdesign', 'CODE_DEVIS', 'fr.legrain.editor.devis.swt.multi', 'Devis', NULL, 0, NULL)
^
INSERT INTO TA_VISUALISATION (ID, CODE_REQUETE, REQUETE, CHAMPS, TITRE_CHAMPS, TAILLE_CHAMPS, MODULE, IMPRESSION, IDENTIFIANT, ID_EDITEUR, ID_PLUGIN, GROUPBY, PROC, PARAM) VALUES (7, 'Liste des articles Facture', 'select V.date_facture,V.code_tiers,V.code_facture,vl.code_article,vl.u1_l_facture,(vl.qte_l_facture),(MTNETHT)as ht,(MTNETTVA)as tva,(MTNETTTC)as ttc from V_l_facture vl,calcul_total_ligne_direct(''facture'',vl.id_l_facture),v_facture V where V.ID_FACTURE=vl.ID_FACTURE and vl.code_article is not null  order by V.date_facture,V.code_tiers,V.code_facture,vl.code_article,vl.u1_l_facture', 'date_facture;code_tiers;code_facture;code_article;u1_l_facture;qte_l_facture;ht;tva;ttc', 'date facture;code tiers;code facture;code article;unit�;qt�;ht;tva;ttc', '100;100;100;100;100;100;100;100;100', 'tiers', NULL, 'CODE_facture', 'fr.legrain.editor.facture.swt.multi', 'Facture', NULL, 0, NULL)
^
INSERT INTO TA_VISUALISATION (ID, CODE_REQUETE, REQUETE, CHAMPS, TITRE_CHAMPS, TAILLE_CHAMPS, MODULE, IMPRESSION, IDENTIFIANT, ID_EDITEUR, ID_PLUGIN, GROUPBY, PROC, PARAM) VALUES (8, 'Chiffre d''affaires maintenance synth�se', 'select  extract ( year from (cast (v_facture.date_facture as date))) as annee,  case when extract ( year from (cast (v_facture.date_facture as date))) <= 2001 then  cast(sum(MTNETHT)/6.55957 as numeric(15,2)) else sum(MTNETHT)end as ht,  case when extract ( year from (cast (v_facture.date_facture as date))) <= 2001 then  cast(sum(MTNETTVA)/6.55957 as numeric(15,2)) else sum(MTNETTVA)end as tva,  case when extract ( year from (cast (v_facture.date_facture as date))) <= 2001 then  cast(sum(MTNETTTC)/6.55957 as numeric(15,2)) else sum(MTNETTTC)end as ttc  from  v_l_facture,  calcul_total_ligne_direct(''facture'',v_l_facture.id_l_facture),  ta_article,  v_facture  where  v_facture.id_facture = v_l_facture.id_facture  and v_facture.code_facture like(''%'')  and v_l_facture.id_article = ta_article.id_article  and ta_article.id_famille between 21 and 21', 'annee�extract ( year from (cast (v_facture.date_facture as date)));  ht�sum(MTNETHT)�having;  tva�sum(MTNETVA)�having;  ttc�sum(MTNETTTC)�having', 'ann�e;HT;TVA;TTC', '100;100;100;100', 'article', NULL, 'CODE_ARTICLE', 'fr.legrain.articles.editor.EditorArticle', 'Articles', 'group by extract ( year from (cast (v_facture.date_facture as date)))', 0, NULL)
^
INSERT INTO TA_VISUALISATION (ID, CODE_REQUETE, REQUETE, CHAMPS, TITRE_CHAMPS, TAILLE_CHAMPS, MODULE, IMPRESSION, IDENTIFIANT, ID_EDITEUR, ID_PLUGIN, GROUPBY, PROC, PARAM) VALUES (9, 'Chiffre d''affaires maintenance (D�tail)', 'select  extract ( year from (cast (v_facture.date_facture as date)))as annee,  (v_l_facture.code_article) as codeArticleLigne,  case when extract ( year from (cast (v_facture.date_facture as date))) <= 2001 then  cast(sum(MTNETHT)/6.55957 as numeric(15,2)) else sum(MTNETHT)end as ht,  case when extract ( year from (cast (v_facture.date_facture as date))) <= 2001 then  cast(sum(MTNETTVA)/6.55957 as numeric(15,2)) else sum(MTNETTVA)end as tva,  case when extract ( year from (cast (v_facture.date_facture as date))) <= 2001 then  cast(sum(MTNETTTC)/6.55957 as numeric(15,2)) else sum(MTNETTTC)end as ttc  from  v_l_facture,  calcul_total_ligne_direct(''facture'',v_l_facture.id_l_facture),  ta_article,  v_facture  where  v_facture.id_facture = v_l_facture.id_facture  and v_facture.code_facture like(''%'')  and v_l_facture.id_article = ta_article.id_article  and ta_article.id_famille between 21 and 21', 'annee�extract ( year from (cast (v_facture.date_facture as date)));  codeArticleLigne�v_l_facture.code_article;  ht�sum(MTNETHT)�having;  tva�sum(MTNETVA)�having;  ttc�sum(MTNETTTC)�having', 'ann�e;code article;HT;TVA;TTC', '100;100;100;100;100', 'article', NULL, 'CODE_ARTICLE', 'fr.legrain.articles.editor.EditorArticle', 'Articles', 'group by extract ( year from (cast (v_facture.date_facture as date))),v_l_facture.code_article', 0, NULL)
^
INSERT INTO TA_VISUALISATION (ID, CODE_REQUETE, REQUETE, CHAMPS, TITRE_CHAMPS, TAILLE_CHAMPS, MODULE, IMPRESSION, IDENTIFIANT, ID_EDITEUR, ID_PLUGIN, GROUPBY, PROC, PARAM) VALUES (10, 'Liste des articles factur�s', 'select  V_FACTURE.date_facture,  v_l_facture.code_article,  sum(MTNETHT)as ht,  sum(MTNETTVA)as tva,  sum(MTNETTTC)as ttc  from   v_l_facture,  calcul_total_ligne_direct(''facture'',v_l_facture.id_l_facture),  V_FACTURE  where V_FACTURE.ID_FACTURE=v_l_facture.ID_FACTURE   and v_l_facture.code_article is not null  order by  v_l_facture.code_article,  V_FACTURE.date_facture', 'date_facture;  code_article;  ht;  tva;  ttc', 'date facture;  code article;  ht;  tva;  ttc', '100;100;100;100;100', 'article', NULL, 'code_article', 'fr.legrain.articles.editor.EditorArticle', 'Articles', 'group by v_l_facture.code_article,V_FACTURE.date_facture', 0, NULL)
^
INSERT INTO TA_VISUALISATION (ID, CODE_REQUETE, REQUETE, CHAMPS, TITRE_CHAMPS, TAILLE_CHAMPS, MODULE, IMPRESSION, IDENTIFIANT, ID_EDITEUR, ID_PLUGIN, GROUPBY, PROC, PARAM) VALUES (11, 'Extraction tiers', 'select NOM_TIERS,PRENOM_TIERS,ADRESSE1_ADRESSE,  ADRESSE2_ADRESSE,ADRESSE3_ADRESSE,CODEPOSTAL_ADRESSE,  VILLE_ADRESSE,PAYS_ADRESSE,cast((cast(adr.CODEPOSTAL_ADRESSE as integer) / 1000)   as varchar(2)) as departement,  CODE_T_CIVILITE,SURNOM_TIERS,LIBL_ENTREPRISE,  CODE_TIERS,CODE_ENTREPRISE,ACTIF_TIERS,TTC_TIERS,CODE_T_TIERS,  LIBELLE_T_TIERS,LIBL_COMMENTAIRE,CODE_T_ENTITE, tva_i_com_compl,  siret_compl,NUMERO_TELEPHONE,ADRESSE_EMAIL,ADRESSE_WEB,QUAND_CREE_TIERS,  max(f.Date_facture) as date_dern_fact,max(bonliv.Date_bonliv)as date_dern_bonliv,     max(devis.Date_devis)as date_dern_devis From TA_TIERS Tiers LEFT OUTER   join TA_T_ENTITE Ent on Tiers.ID_T_ENTITE = Ent.ID_T_ENTITE LEFT OUTER   join TA_T_CIVILITE Civ on Tiers.ID_T_CIVILITE = Civ.ID_T_CIVILITE LEFT OUTER   join TA_ENTREPRISE E on Tiers.ID_ENTREPRISE = E.ID_ENTREPRISE LEFT OUTER   join TA_T_TIERS TTiers on Tiers.ID_T_TIERS = TTiers.ID_T_TIERS LEFT OUTER   join TA_COMMENTAIRE Com on Tiers.ID_TIERS = Com.ID_TIERS LEFT OUTER   join ta_compl compl on compl.id_tiers = Tiers.id_tiers LEFT OUTER   join TA_R_ADR radr on radr.ID_TIERS = Tiers.id_tiers LEFT OUTER   join TA_R_EMAIL remail on remail.id_tiers = Tiers.id_tiers LEFT OUTER   join TA_R_WEB rweb on rweb.id_tiers = Tiers.id_tiers LEFT OUTER   join ta_ADRESSE adr on adr.ID_ADRESSE = radr.ID_ADRESSE LEFT OUTER   join ta_EMAIL email on email.ID_EMAIL = remail.ID_EMAIL LEFT OUTER   join ta_WEB web on web.ID_WEB = rweb.ID_WEB LEFT OUTER   join ta_facture f on f.ID_tiers = Tiers.id_tiers LEFT OUTER   join ta_bonliv bonliv on bonliv.ID_tiers = Tiers.id_tiers LEFT OUTER   join ta_devis devis on devis.ID_tiers = Tiers.id_tiers LEFT OUTER   join TA_R_TEL rtel on rtel.ID_TIERS = Tiers.id_tiers LEFT OUTER   join TA_TELEPHONE tel on tel.ID_TELEPHONE = rtel.ID_TELEPHONE', 'NOM_TIERS;  PRENOM_TIERS;  ADRESSE1_ADRESSE;  ADRESSE2_ADRESSE;  ADRESSE3_ADRESSE;  CODEPOSTAL_ADRESSE;  VILLE_ADRESSE;  PAYS_ADRESSE;  departement�cast((cast(adr.CODEPOSTAL_ADRESSE as integer) / 1000) as varchar(2));  CODE_T_CIVILITE;  SURNOM_TIERS;  LIBL_ENTREPRISE;  CODE_TIERS;  CODE_ENTREPRISE;  ACTIF_TIERS;  TTC_TIERS;  CODE_T_TIERS;  LIBELLE_T_TIERS;  LIBL_COMMENTAIRE;  CODE_T_ENTITE;  tva_i_com_compl;  siret_compl;  NUMERO_TELEPHONE;  ADRESSE_EMAIL;  ADRESSE_WEB;  QUAND_CREE_TIERS;  date_dern_fact�max(f.Date_facture)�having;  date_dern_bonliv�max(bonliv.Date_bonliv)�having;  date_dern_devis�max(devis.Date_devis)�having', 'Nom;  Pr�nom;  Adresse 1;  Adresse 2;  Adresse 3;  Code postal;  Ville;Pays;  D�partement;  Civilite;  Surnom;  Entreprise;  Code tiers;  Code entreprise;  Actif;  TTC;  Type tiers;  Libell� type;  Commentaire;  Type entit�;  Tva intra;  Siret;  T�l;  Email;  Web;  Date cr�ation;  Date derni�re facture;  Date dernier bonliv;  Date dernier devis', '50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50', 'tiers', '/report/extraction_Tiers.rptdesign', 'CODE_TIERS', 'fr.legrain.tiers.editor.EditorTiers', 'Tiers', 'group by  NOM_TIERS,PRENOM_TIERS,ADRESSE1_ADRESSE,ADRESSE2_ADRESSE,ADRESSE3_ADRESSE,CODEPOSTAL_ADRESSE,VILLE_ADRESSE,PAYS_ADRESSE,CODE_T_CIVILITE,SURNOM_TIERS,LIBL_ENTREPRISE,CODE_TIERS,CODE_ENTREPRISE,ACTIF_TIERS,TTC_TIERS,CODE_T_TIERS,LIBELLE_T_TIERS,LIBL_COMMENTAIRE,CODE_T_ENTITE,tva_i_com_compl,siret_compl,NUMERO_TELEPHONE,ADRESSE_EMAIL,ADRESSE_WEB,QUAND_CREE_TIERS', 0, NULL)
^
INSERT INTO TA_VISUALISATION (ID, CODE_REQUETE, REQUETE, CHAMPS, TITRE_CHAMPS, TAILLE_CHAMPS, MODULE, IMPRESSION, IDENTIFIANT, ID_EDITEUR, ID_PLUGIN, GROUPBY, PROC, PARAM) VALUES (12, 'Stocks d�but', 'select code_article,libellec_article,qte_report_stock,unite_report_stock,date_deb_report_stock,date_fin_report_stock from ta_report_stock R left join ta_Article Article on(Article.id_article=R.id_article_report_stock)', 'code_article;libellec_article;qte_report_stock;unite_report_stock;date_deb_report_stock;date_fin_report_stock', 'Article;Libell� de article;Quantit�;Unit�;Date deb;Date fin', '100;200;100;100;100;100', 'stocks', '/report/StocksDebut.rptdesign', 'CODE_ARTICLE', 'fr.legrain.stocks.editor.EditorStocks', 'Stocks', '', 0, NULL)
^
INSERT INTO TA_VISUALISATION (ID, CODE_REQUETE, REQUETE, CHAMPS, TITRE_CHAMPS, TAILLE_CHAMPS, MODULE, IMPRESSION, IDENTIFIANT, ID_EDITEUR, ID_PLUGIN, GROUPBY, PROC, PARAM) VALUES (13, 'Chiffre d''affaires synth�se', 'select  extract ( year from (cast (v_facture.date_facture as date))) as annee,  case when extract ( year from (cast (v_facture.date_facture as date))) <= 2001 then  cast(sum(MTNETHT)/6.55957 as numeric(15,2)) else sum(MTNETHT)end as ht,  case when extract ( year from (cast (v_facture.date_facture as date))) <= 2001 then  cast(sum(MTNETTVA)/6.55957 as numeric(15,2)) else sum(MTNETTVA)end as tva,  case when extract ( year from (cast (v_facture.date_facture as date))) <= 2001 then  cast(sum(MTNETTTC)/6.55957 as numeric(15,2)) else sum(MTNETTTC)end as ttc  from  v_l_facture,  calcul_total_ligne_direct(''facture'',v_l_facture.id_l_facture),  ta_article,  v_facture  where  v_facture.id_facture = v_l_facture.id_facture  and v_facture.code_facture like(''%'')  and v_l_facture.id_article = ta_article.id_article', 'annee�extract ( year from (cast (v_facture.date_facture as date)));  ht�sum(MTNETHT)�having;  tva�sum(MTNETVA)�having;  ttc�sum(MTNETTTC)�having', 'ann�e;HT;TVA;TTC', '100;100;100;100', 'article', NULL, 'CODE_FACTURE', 'fr.legrain.articles.editor.EditorArticle', 'Facture', 'group by extract ( year from (cast (v_facture.date_facture as date)))', 0, NULL)
^
INSERT INTO TA_VISUALISATION (ID, CODE_REQUETE, REQUETE, CHAMPS, TITRE_CHAMPS, TAILLE_CHAMPS, MODULE, IMPRESSION, IDENTIFIANT, ID_EDITEUR, ID_PLUGIN, GROUPBY, PROC, PARAM) VALUES (14, 'Chiffre d''affaires (D�tail)', 'select  extract ( year from (cast (v_facture.date_facture as date)))as annee,  (v_l_facture.code_article) as codeArticleLigne,  case when extract ( year from (cast (v_facture.date_facture as date))) <= 2001 then  cast(sum(MTNETHT)/6.55957 as numeric(15,2)) else sum(MTNETHT)end as ht,  case when extract ( year from (cast (v_facture.date_facture as date))) <= 2001 then  cast(sum(MTNETTVA)/6.55957 as numeric(15,2)) else sum(MTNETTVA)end as tva,  case when extract ( year from (cast (v_facture.date_facture as date))) <= 2001 then  cast(sum(MTNETTTC)/6.55957 as numeric(15,2)) else sum(MTNETTTC)end as ttc  from  v_l_facture,  calcul_total_ligne_direct(''facture'',v_l_facture.id_l_facture),  ta_article,  v_facture  where  v_facture.id_facture = v_l_facture.id_facture  and v_facture.code_facture like(''%'')  and v_l_facture.id_article = ta_article.id_article', 'annee�extract ( year from (cast (v_facture.date_facture as date)));  codeArticleLigne�v_l_facture.code_article;  ht�sum(MTNETHT)�having;  tva�sum(MTNETVA)�having;  ttc�sum(MTNETTTC)�having', 'ann�e;code article;HT;TVA;TTC', '100;100;100;100;100', 'article', NULL, 'CODE_ARTICLE', 'fr.legrain.articles.editor.EditorArticle', 'Articles', 'group by extract ( year from (cast (v_facture.date_facture as date))),v_l_facture.code_article', 0, NULL)
^
INSERT INTO TA_VISUALISATION (ID, CODE_REQUETE, REQUETE, CHAMPS, TITRE_CHAMPS, TAILLE_CHAMPS, MODULE, IMPRESSION, IDENTIFIANT, ID_EDITEUR, ID_PLUGIN, GROUPBY, PROC, PARAM) VALUES (15, 'Liste des articles Facture (avec code facture)', 'select   V.date_facture,  V.code_tiers,  V.code_facture,  vl.code_article,  (a.code_famille)as famille,  vl.u1_l_facture,  vl.qte_l_facture,  (MTNETHT)as ht,  (MTNETTVA)as tva,  (MTNETTTC)as ttc  from V_l_facture vl,   calcul_total_ligne_direct(''facture'',vl.id_l_facture),  v_facture V,  v_article a  where V.ID_FACTURE=vl.ID_FACTURE  and a.id_article = vl.id_article   and vl.code_article is not null  order by V.date_facture,V.code_tiers,V.code_facture,vl.code_article   ,vl.u1_l_facture', 'date_facture;code_tiers;code_facture;code_article;famille�a.code_famille;u1_l_facture;qte_l_facture;ht;tva;ttc', 'date facture;code tiers;code facture;code article;famille;unit�;qt�;ht;tva;ttc', '100;100;100;100;100;100;100;100;100;100', 'article', NULL, 'CODE_facture', 'fr.legrain.editor.facture.swt.multi', 'Facture', NULL, 0, NULL)
^

INSERT INTO TA_VISUALISATION (ID,CODE_REQUETE,REQUETE,CHAMPS,TITRE_CHAMPS,TAILLE_CHAMPS,MODULE,IMPRESSION,IDENTIFIANT,ID_EDITEUR,ID_PLUGIN,GROUPBY,PROC,PARAM) VALUES (16,'Abandon client','select distinct(code_tiers),max(extract (year from date_facture))as annee,1 as Nb
 from v_facture','code_tiers;annee�max(extract (year from date_facture))�having;Nb�1','code tiers;ann�e;Nb','100;100;100','tiers',NULL,'CODE_TIERS','fr.legrain.tiers.editor.EditorTiers','Tiers','group by code_tiers',0,NULL)
^

INSERT INTO TA_VISUALISATION (ID,CODE_REQUETE,REQUETE,CHAMPS,TITRE_CHAMPS,TAILLE_CHAMPS,MODULE,IMPRESSION,IDENTIFIANT,ID_EDITEUR,ID_PLUGIN,GROUPBY,PROC,PARAM) VALUES (17,'CA tiers par d�partement','select case when (cast(v_adresse.codepostal_adresse as integer) / 1000)<=9 then
''0''||cast((cast(v_adresse.codepostal_adresse as integer) / 1000)   as varchar(2))
else cast((cast(v_adresse.codepostal_adresse as integer) / 1000)   as varchar(2)) end as departement,
extract(year from V_FACTURE.date_facture)as annee,
V_FACTURE.code_tiers as codeTiersFacture,
V_FACTURE.nom_tiers as nomTiersFacture,
cast(case when extract(year from V_FACTURE.date_facture)<=2001 then
sum(MTNETHT)/6.55957 else sum(MTNETHT) end as numeric(15,2) )as ht,
cast(case when extract(year from V_FACTURE.date_facture)<=2001 then
sum(MTNETTVA)/6.55957 else sum(MTNETTVA) end as numeric(15,2) )as tva,
cast(case when extract(year from V_FACTURE.date_facture)<=2001 then
sum(MTNETTTC)/6.55957 else sum(MTNETTTC) end as numeric(15,2) )as ttc
from 
V_FACTURE,
calcul_total_direct(''facture'',v_facture.id_facture),
v_adresse
where V_FACTURE.id_tiers >0
and v_adresse.id_tiers = v_facture.id_tiers','departement�case when (cast(v_adresse.codepostal_adresse as integer) / 1000)<=9 then
''0''||cast((cast(v_adresse.codepostal_adresse as integer) / 1000)   as varchar(2))
else cast((cast(v_adresse.codepostal_adresse as integer) / 1000)   as varchar(2)) end;
annee�extract(year from V_FACTURE.date_facture);
codeTiersFacture�V_FACTURE.code_tiers;
nomTiersFacture�V_FACTURE.nom_tiers;
ht�cast(case when extract(year from V_FACTURE.date_facture)<=2001 then
sum(MTNETHT)/6.55957 else sum(MTNETHT) end as numeric(15,2) )�having;
tva�cast(case when extract(year from V_FACTURE.date_facture)<=2001 then
sum(MTNETTVA)/6.55957 else sum(MTNETTVA) end as numeric(15,2) )�having;
ttc�cast(case when extract(year from V_FACTURE.date_facture)<=2001 then
sum(MTNETTTC)/6.55957 else sum(MTNETTTC) end as numeric(15,2) )�having;','departement;annee;code tiers;nom tiers; ht;tva; ttc','100;100;100;100;100;100;100','tiers',NULL,'CODE_TIERS','fr.legrain.tiers.editor.EditorTiers','Tiers','group by case when (cast(v_adresse.codepostal_adresse as integer) / 1000)<=9 then
''0''||cast((cast(v_adresse.codepostal_adresse as integer) / 1000)   as varchar(2))
else cast((cast(v_adresse.codepostal_adresse as integer) / 1000)   as varchar(2)) end ,
V_FACTURE.code_tiers,
V_FACTURE.nom_tiers,
extract(year from V_FACTURE.date_facture)',0,NULL)
^


INSERT INTO TA_VISUALISATION (ID,CODE_REQUETE,REQUETE,CHAMPS,TITRE_CHAMPS,TAILLE_CHAMPS,MODULE,IMPRESSION,IDENTIFIANT,ID_EDITEUR,ID_PLUGIN,GROUPBY,PROC,PARAM) VALUES (18,'CA tiers par departement, ann�e, article et famille','select case when (cast(v_adresse.codepostal_adresse as integer) / 1000)<=9
then ''0''||cast((cast(v_adresse.codepostal_adresse as integer) / 1000)
as varchar(2)) else cast((cast(v_adresse.codepostal_adresse as integer) / 1000)
  as varchar(2)) end as departement, extract(year from V_FACTURE.date_facture)as annee,
   V_FACTURE.code_tiers as codeTiersFacture,V_FACTURE.nom_tiers as nomTiersFacture, cast(case when extract(year from V_FACTURE.date_facture)<=2001
   then sum(MTNETHT)/6.55957 else sum(MTNETHT) end as numeric(15,2) )as ht,
   cast(case when extract(year from V_FACTURE.date_facture)<=2001 then
   sum(MTNETTVA)/6.55957 else sum(MTNETTVA) end as numeric(15,2) )as tva,
   cast(case when extract(year from V_FACTURE.date_facture)<=2001 then sum(MTNETTTC)/6.55957
   else sum(MTNETTTC) end as numeric(15,2) )as ttc,
v_l_facture.code_article,v_article.code_famille

from 
v_l_facture,
calcul_total_ligne_direct(''facture'',v_l_facture.id_l_facture),
V_FACTURE,v_adresse ,v_article
where V_FACTURE.ID_FACTURE=v_l_facture.ID_FACTURE 
and v_l_facture.code_article is not null
and V_FACTURE.id_tiers >0 and v_adresse.id_tiers = v_facture.id_tiers
and v_l_facture.id_article = v_article.id_article','departement�case when (cast(v_adresse.codepostal_adresse as integer) / 1000)<=9
then ''0''||cast((cast(v_adresse.codepostal_adresse as integer) / 1000)
as varchar(2)) else cast((cast(v_adresse.codepostal_adresse as integer) / 1000)
  as varchar(2)) end;
annee�extract(year from V_FACTURE.date_facture);
codeTiersFacture�V_FACTURE.code_tiers;
nomTiersFacture�V_FACTURE.nom_tiers; 
ht�cast(case when extract(year from V_FACTURE.date_facture)<=2001
  then sum(MTNETHT)/6.55957 else sum(MTNETHT) end as numeric(15,2) ;
tva� cast(case when extract(year from V_FACTURE.date_facture)<=2001 then
   sum(MTNETTVA)/6.55957 else sum(MTNETTVA) end as numeric(15,2) );
ttc�cast(case when extract(year from V_FACTURE.date_facture)<=2001 
then sum(MTNETTTC)/6.55957   else sum(MTNETTTC) end as numeric(15,2) );
articleLigne�v_l_facture.code_article;
famille�v_article.code_famille','d�partement;ann�e;code tiers;nom tiers;ht;tva;ttc;code article;famille','40;50;100;100;100;100;100;100;100','tiers',NULL,'CODE_TIERS','dd','Tiers','group by case when (cast(v_adresse.codepostal_adresse as integer) / 1000)<=9
then ''0''||cast((cast(v_adresse.codepostal_adresse as integer) / 1000)
as varchar(2)) else cast((cast(v_adresse.codepostal_adresse as integer) / 1000)
  as varchar(2)) end , extract(year from V_FACTURE.date_facture),
   V_FACTURE.code_tiers ,V_FACTURE.nom_tiers , v_l_facture.code_article,v_article.code_famille',0,NULL)
^



/*** mise a jour BD pour oscommerce***/
CREATE or ALTER  PROCEDURE GESTION_CREATION_TABLEOSC1
as    
DECLARE VARIABLE QUERY VARCHAR(5000);
BEGIN
query = '';
IF (not exists( SELECT  a.RDB$RELATION_NAME
FROM RDB$RELATIONS a
where a.RDB$RELATION_NAME='TA_INFOS_FACTURE_OSC')) THEN
  begin
    query = 'CREATE TABLE TA_INFOS_FACTURE_OSC (
    ID_INFOS_FACTURE           DID3 NOT NULL,
    ID_FACTURE                 DID3,
    ADRESSE1                   DLIB100,
    ADRESSE2                   DLIB100,
    ADRESSE3                   DLIB100,
    CODEPOSTAL                 DCODPOS,
    VILLE                      DLIB100,
    PAYS                       DLIB100,
    ADRESSE1_LIV               DLIB100,
    ADRESSE2_LIV               DLIB100,
    ADRESSE3_LIV               DLIB100,
    CODEPOSTAL_LIV             DCODPOS,
    VILLE_LIV                  DLIB100,
    PAYS_LIV                   DLIB100,
    CODE_COMPTA                DLIB8NN,
    COMPTE                     DLIB8NN,
    NOM_TIERS                  DLIB100,
    PRENOM_TIERS               DLIB100,
    SURNOM_TIERS               DLIB20,
    CODE_T_CIVILITE            DLGR_CODEL,
    CODE_T_ENTITE              DLGR_CODEL,
    TVA_I_COM_COMPL            DLIB50,
    CODE_C_PAIEMENT            DLGR_CODEL,
    LIB_C_PAIEMENT             DLIB255,
    REPORT_C_PAIEMENT          DID_FACULTATIF,
    FIN_MOIS_C_PAIEMENT        DID_FACULTATIF,
    QUI_CREE_INFOS_FACTURE     DLIB50,
    QUAND_CREE_INFOS_FACTURE   TIMESTAMP DEFAULT ''NOW'',
    QUI_MODIF_INFOS_FACTURE    DLIB50,
    QUAND_MODIF_INFOS_FACTURE  TIMESTAMP DEFAULT ''NOW'',
    "VERSION"                  NUM_VERSION,
    IP_ACCES                   DLIB50NN DEFAULT 0,
    ID_INFOS_FACTURE_OSC       DID4
)';
    EXECUTE STATEMENT :query;
    
    query = 'ALTER TABLE TA_INFOS_FACTURE_OSC ADD PRIMARY KEY (ID_INFOS_FACTURE)';
    EXECUTE STATEMENT :query;
  end
END
^
EXECUTE PROCEDURE GESTION_CREATION_TABLEOSC1 
^
drop procedure GESTION_CREATION_TABLEOSC1
^

CREATE or ALTER  PROCEDURE GESTION_CREATION_TABLEOSC2
as    
DECLARE VARIABLE QUERY VARCHAR(5000);
BEGIN
query = '';
IF (not exists( SELECT  a.RDB$RELATION_NAME
FROM RDB$RELATIONS a
where a.RDB$RELATION_NAME='TA_ARTICLE_OSC')) THEN
  begin
    query = 'CREATE TABLE TA_ARTICLE_OSC (
    ID_ARTICLE           DID3 NOT NULL,
    CODE_ARTICLE         DLGR_CODE,
    LIBELLEC_ARTICLE     DLGR_LIBCODE,
    LIBELLEL_ARTICLE     DLIB255,
    ID_FAMILLE           DID_FACULTATIF,
    ID_TVA               DID_FACULTATIF,
    ID_T_TVA             DID_FACULTATIF,
    ID_PRIX              DID_FACULTATIF,
    NUMCPT_ARTICLE       DLIB8,
    DIVERS_ARTICLE       DLIB255,
    COMMENTAIRE_ARTICLE  DLIB_COMMENTAIRE,
    STOCK_MIN_ARTICLE    DID9,
    QUI_CREE_ARTICLE     DLIB50,
    QUAND_CREE_ARTICLE   TIMESTAMP DEFAULT ''NOW'',
    QUI_MODIF_ARTICLE    DLIB50,
    QUAND_MODIF_ARTICLE  TIMESTAMP DEFAULT ''NOW'',
    "VERSION"            NUM_VERSION,
    IP_ACCES             DLIB50NN DEFAULT 0,
    ID_ARTICLE_BDG	 DID4
)';
    EXECUTE STATEMENT :query;

    query = 'ALTER TABLE TA_ARTICLE_OSC ADD PRIMARY KEY (ID_ARTICLE)';
    EXECUTE STATEMENT :query;
  end
END
^
EXECUTE PROCEDURE GESTION_CREATION_TABLEOSC2 
^
drop procedure GESTION_CREATION_TABLEOSC2
^

CREATE or ALTER  PROCEDURE GESTION_CREATION_TABLEOSC3
as    
DECLARE VARIABLE QUERY VARCHAR(5000);
BEGIN
query = '';
IF (not exists( SELECT  a.RDB$RELATION_NAME
FROM RDB$RELATIONS a
where a.RDB$RELATION_NAME='TA_TIERS_OSC')) THEN
  begin
    query = 'CREATE TABLE TA_TIERS_OSC (
    ID_TIERS           DID3 NOT NULL,
    CODE_TIERS         VARCHAR(20) CHARACTER SET UTF8 NOT NULL,
    CODE_COMPTA        VARCHAR(7) CHARACTER SET UTF8,
    COMPTE             DLIB8NN,
    NOM_TIERS          DLGR_LIB,
    PRENOM_TIERS       DLIB100,
    SURNOM_TIERS       DLIB20,
    ACTIF_TIERS        DBOOL DEFAULT '1',
    TTC_TIERS          DBOOL DEFAULT '0',
    ID_T_CIVILITE      DID_FACULTATIF,
    ID_ENTREPRISE      DID_FACULTATIF,
    ID_T_TIERS         DID3,
    ID_T_ENTITE        DID_FACULTATIF,
    ID_I_BANQUE        DID_FACULTATIF,
    QUI_CREE_TIERS     DLIB50,
    QUAND_CREE_TIERS   TIMESTAMP DEFAULT ''NOW'',
    QUI_MODIF_TIERS    DLIB50,
    QUAND_MODIF_TIERS  TIMESTAMP DEFAULT ''NOW'',
    "VERSION"          NUM_VERSION,
    IP_ACCES           DLIB50NN DEFAULT 0,
    ID_TIERS_BDG       DID4,
    ID_ADRESSE_BDG     DID4,
    ID_ADRESSE_OSC     DID4			
)';
    EXECUTE STATEMENT :query;

    query = 'ALTER TABLE TA_TIERS_OSC ADD PRIMARY KEY (ID_TIERS)';
    EXECUTE STATEMENT :query;
  end
END
^
EXECUTE PROCEDURE GESTION_CREATION_TABLEOSC3 
^
drop procedure GESTION_CREATION_TABLEOSC3
^

CREATE or ALTER  PROCEDURE GESTION_CREATION_TABLEOSC4
as    
DECLARE VARIABLE QUERY VARCHAR(5000);
BEGIN
query = '';
IF (not exists( SELECT  a.RDB$RELATION_NAME
FROM RDB$RELATIONS a
where a.RDB$RELATION_NAME='TA_FACTURE_OSC')) THEN
  begin
    query = 'CREATE TABLE TA_FACTURE_OSC (
    ID_FACTURE           DID3 NOT NULL,
    CODE_FACTURE         DLGR_CODE,
    DATE_FACTURE         TIMESTAMP DEFAULT ''NOW'',
    DATE_ECH_FACTURE     TIMESTAMP DEFAULT ''NOW'',
    DATE_LIV_FACTURE     TIMESTAMP DEFAULT ''NOW'',
    LIBELLE_FACTURE      DLIB255NN,
    ID_ADRESSE           DID_FACULTATIF,
    ID_ADRESSE_LIV       DID_FACULTATIF,
    ID_TIERS_OSC         DID3,
    ID_T_PAIEMENT        DID_FACULTATIF,
    ID_C_PAIEMENT        DID_FACULTATIF,
    REGLE_FACTURE        DID9 DEFAULT 0,
    REM_HT_FACTURE       DID9 DEFAULT 0,
    TX_REM_HT_FACTURE    DID9 DEFAULT 0,
    REM_TTC_FACTURE      DID9 DEFAULT 0,
    TX_REM_TTC_FACTURE   DID9 DEFAULT 0,
    NB_E_FACTURE         DID4 DEFAULT 0,
    TTC                  DBOOL DEFAULT 0,
    EXPORT               DBOOL DEFAULT 0,
    COMMENTAIRE          DLIB_COMMENTAIRE,
    QUI_CREE_FACTURE     DLIB50,
    QUAND_CREE_FACTURE   TIMESTAMP DEFAULT ''NOW'',
    QUI_MODIF_FACTURE    DLIB50,
    QUAND_MODIF_FACTURE  TIMESTAMP DEFAULT ''NOW'',
    "VERSION"            NUM_VERSION,
    IP_ACCES             DLIB50NN DEFAULT 0,
    ID_FACTURE_BDG       DID4,
    ID_TIERS_BDG         DID4
)';
    EXECUTE STATEMENT :query;

    query = 'ALTER TABLE TA_FACTURE_OSC ADD PRIMARY KEY (ID_FACTURE)';
    EXECUTE STATEMENT :query;
  end
END
^
EXECUTE PROCEDURE GESTION_CREATION_TABLEOSC4
^
drop procedure GESTION_CREATION_TABLEOSC4
^
CREATE or ALTER  PROCEDURE GESTION_CREATION_TABLEOSC5
as    
DECLARE VARIABLE QUERY VARCHAR(5000);
BEGIN
query = '';
IF (not exists( SELECT  a.RDB$RELATION_NAME
FROM RDB$RELATIONS a
where a.RDB$RELATION_NAME='TA_L_FACTURE_OSC')) THEN
  begin
    query = 'CREATE TABLE TA_L_FACTURE_OSC (
    ID_L_FACTURE           DID3 NOT NULL,
    ID_FACTURE             DID3,
    ID_T_LIGNE             DID3,
    ID_ARTICLE             DID4,
    NUM_LIGNE_L_FACTURE    DID3,
    LIB_L_FACTURE          DLIB255,
    QTE_L_FACTURE          DID9FACULT,
    U1_L_FACTURE           DLIB20,
    U2_L_FACTURE           DLIB20,
    PRIX_U_L_FACTURE       DID9FACULT,
    TAUX_TVA_L_FACTURE     DLONG,
    COMPTE_L_FACTURE       DLIB8,
    CODE_TVA_L_FACTURE     DLIB20,
    CODE_T_TVA_L_FACTURE   DLIB1L,
    MT_HT_L_FACTURE        DID9FACULT,
    MT_TTC_L_FACTURE       DID9FACULT,
    REM_TX_L_FACTURE       DID9FACULT,
    REM_HT_L_FACTURE       DID9FACULT,
    QUI_CREE_L_FACTURE     DLIB50,
    QUAND_CREE_L_FACTURE   TIMESTAMP DEFAULT ''NOW'',
    QUI_MODIF_L_FACTURE    DLIB50,
    QUAND_MODIF_L_FACTURE  TIMESTAMP DEFAULT ''NOW'',
    "VERSION"              NUM_VERSION,
    IP_ACCES               DLIB50NN DEFAULT 0,
    ID_L_FACTURE_BDG	   DID4
)';
    EXECUTE STATEMENT :query;

    query = 'ALTER TABLE TA_L_FACTURE_OSC ADD PRIMARY KEY (ID_L_FACTURE)';
    EXECUTE STATEMENT :query;
  end
END
^
EXECUTE PROCEDURE GESTION_CREATION_TABLEOSC5
^
drop procedure GESTION_CREATION_TABLEOSC5
^

CREATE or ALTER  PROCEDURE GESTION_CREATION_TABLEOSC6
as    
DECLARE VARIABLE QUERY VARCHAR(5000);
BEGIN
query = '';
IF (not exists( SELECT  a.RDB$RELATION_NAME
FROM RDB$RELATIONS a
where a.RDB$RELATION_NAME='TA_ADRESSE_OSC')) THEN
  begin
    query = 'CREATE TABLE TA_ADRESSE_OSC (
    ID_ADRESSE           DID3 NOT NULL,
    ADRESSE1_ADRESSE     DLIB100,
    ADRESSE2_ADRESSE     DLIB100,
    ADRESSE3_ADRESSE     DLIB100,
    CODEPOSTAL_ADRESSE   DCODPOS,
    VILLE_ADRESSE        DLGR_LIB,
    PAYS_ADRESSE         DLGR_LIB DEFAULT ''FRANCE'',
    QUI_CREE_ADRESSE     DLIB50,
    QUAND_CREE_ADRESSE   TIMESTAMP DEFAULT ''NOW'',
    QUI_MODIF_ADRESSE    DLIB50,
    QUAND_MODIF_ADRESSE  TIMESTAMP DEFAULT ''NOW'',
    "VERSION"            NUM_VERSION,
    IP_ACCES             DLIB50NN DEFAULT 0,
    ID_ADRESSE_BDG       DID4,
    ID_TIER_OSC          DID4,
    ID_TIER_BDG          DID4,
    CODE_T_ADR_BDG       DLGR_CODE
    
)';
    EXECUTE STATEMENT :query;

    query = 'ALTER TABLE TA_ADRESSE_OSC ADD PRIMARY KEY (ID_ADRESSE)';
    EXECUTE STATEMENT :query;
  end
END
^
EXECUTE PROCEDURE GESTION_CREATION_TABLEOSC6
^
drop procedure GESTION_CREATION_TABLEOSC6
^

CREATE or ALTER  PROCEDURE GESTION_CREATION_TABLEOSC7
as    
DECLARE VARIABLE QUERY VARCHAR(5000);
BEGIN
query = '';
IF (not exists( SELECT  a.RDB$RELATION_NAME
FROM RDB$RELATIONS a
where a.RDB$RELATION_NAME='TA_EMAIL_OSC')) THEN
  begin
    query = 'CREATE TABLE TA_EMAIL_OSC (
    ID_EMAIL           DID3 NOT NULL,
    ADRESSE_EMAIL      DLIB255NN,
    QUI_CREE_EMAIL     DLIB50,
    QUAND_CREE_EMAIL   TIMESTAMP DEFAULT ''NOW'',
    QUI_MODIF_EMAIL    DLIB50,
    QUAND_MODIF_EMAIL  TIMESTAMP DEFAULT ''NOW'',
    "VERSION"          NUM_VERSION,
    IP_ACCES           DLIB50NN DEFAULT 0,
    ID_EMAIL_BDG       DID4,
    ID_TIERS_OSC       DID4
)';
    EXECUTE STATEMENT :query;

    query = 'ALTER TABLE TA_EMAIL_OSC ADD PRIMARY KEY (ID_EMAIL)';
    EXECUTE STATEMENT :query;
  end
END
^
EXECUTE PROCEDURE GESTION_CREATION_TABLEOSC7
^
drop procedure GESTION_CREATION_TABLEOSC7
^

CREATE or ALTER  PROCEDURE GESTION_CREATION_TABLEOSC8
as    
DECLARE VARIABLE QUERY VARCHAR(5000);
BEGIN
query = '';
IF (not exists( SELECT  a.RDB$RELATION_NAME
FROM RDB$RELATIONS a
where a.RDB$RELATION_NAME='TA_TELEPHONE_OSC')) THEN
  begin
    query = 'CREATE TABLE TA_TELEPHONE_OSC (
    ID_TELEPHONE           DID3 NOT NULL,
    NUMERO_TELEPHONE       DLGR_CODE,
    POSTE_TELEPHONE        DLGR_CODEL,
    ID_I_TIERS             DID_FACULTATIF,
    QUI_CREE_TELEPHONE     DLIB50,
    QUAND_CREE_TELEPHONE   TIMESTAMP DEFAULT ''NOW'',
    QUI_MODIF_TELEPHONE    DLIB50,
    QUAND_MODIF_TELEPHONE  TIMESTAMP DEFAULT ''NOW'',
    "VERSION"              NUM_VERSION,
    IP_ACCES               DLIB50NN DEFAULT 0,
    ID_TELEPHONE_BDG	   DID4,
    ID_TIERS_OSC           DID4,
    CODE_T_TEL             DLGR_CODE 	
)';
    EXECUTE STATEMENT :query;

    query = 'ALTER TABLE TA_TELEPHONE_OSC ADD PRIMARY KEY (ID_TELEPHONE)';
    EXECUTE STATEMENT :query;

  end
END
^
EXECUTE PROCEDURE GESTION_CREATION_TABLEOSC8
^
drop procedure GESTION_CREATION_TABLEOSC8
^




INSERT INTO TA_VISUALISATION (ID,CODE_REQUETE,REQUETE,CHAMPS,TITRE_CHAMPS,TAILLE_CHAMPS,MODULE,IMPRESSION,IDENTIFIANT,ID_EDITEUR,ID_PLUGIN,GROUPBY,PROC,PARAM) VALUES (19,'liste des Factures pour le(s) tiers','select CODE_FACTURE,DATE_FACTURE,CODE_TIERS,NOM_TIERS,(MTNETTTC)as MTNETTTC ,EXPORT FROM  V_facture , calcul_total_direct(''facture'',id_facture)','CODE_FACTURE;DATE_FACTURE;CODE_TIERS;NOM_TIERS;MTNETTTC;EXPORT','CODE;DATE FACTURE;CODE TIERS;NOM TIERS;NET TTC;EXPORTEE','100;100;100;100;200;100;100;100','tiers','','CODE_FACTURE','fr.legrain.editor.facture.swt.multi','Facture',NULL,0,NULL)
^

INSERT INTO TA_VISUALISATION (ID,CODE_REQUETE,REQUETE,CHAMPS,TITRE_CHAMPS,TAILLE_CHAMPS,MODULE,IMPRESSION,IDENTIFIANT,ID_EDITEUR,ID_PLUGIN,GROUPBY,PROC,PARAM) VALUES (20,'liste des Devis pour le(s) tiers','select CODE_devis,DATE_devis,CODE_TIERS,NOM_TIERS,(MTNETTTC)as MTNETTTC
 FROM  V_devis , calcul_total_direct(''devis'',id_devis)','CODE_DEVIS;DATE_DEVIS;CODE_TIERS;NOM_TIERS;MTNETTTC','CODE;DATE DEVIS;CODE TIERS;NOM TIERS;NET TTC','100;100;100;100;200;100;100','tiers','','CODE_DEVIS','fr.legrain.editor.devis.swt.multi','Devis',NULL,0,NULL)
 ^
 
INSERT INTO TA_VISUALISATION (ID,CODE_REQUETE,REQUETE,CHAMPS,TITRE_CHAMPS,TAILLE_CHAMPS,MODULE,IMPRESSION,IDENTIFIANT,ID_EDITEUR,ID_PLUGIN,GROUPBY,PROC,PARAM) VALUES (21,'liste des Bons de livraisons pour le(s) tiers','select CODE_BonLiv,DATE_BonLiv,CODE_TIERS,NOM_TIERS,(MTNETTTC)as MTNETTTC
 FROM  V_BonLiv , calcul_total_direct(''BonLiv'',id_BonLiv)','CODE_BONLIV;DATE_BONLIV;CODE_TIERS;NOM_TIERS;MTNETTTC','CODE;DATE BONLIV;CODE TIERS;NOM TIERS;NET TTC','100;100;100;100;200;100;100','tiers','','CODE_BONLIV','fr.legrain.editor.Bl.swt.multi','BonLiv',NULL,0,NULL)
 ^
