insert into dependances select * FROM RDB$DEPENDENCIES
^


delete  FROM RDB$DEPENDENCIES
^


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


create or alter PROCEDURE INFOSBONLIV (
    ID_DOCUMENT_RECUP INTEGER,
    ID_TIERS_RECUP INTEGER,
    TYPE_ADRESSE VARCHAR(20))
RETURNS (
    ID_INFOS_BONLIV INTEGER,
    ID_ADRESSE INTEGER,
    ID_BONLIV INTEGER,
    ADRESSE1 VARCHAR(100),
    ADRESSE2 VARCHAR(100),
    ADRESSE3 VARCHAR(100),
    CODEPOSTAL VARCHAR(5),
    VILLE VARCHAR(100),
    PAYS VARCHAR(100),
    ADRESSE1_LIV VARCHAR(100),
    ADRESSE2_LIV VARCHAR(100),
    ADRESSE3_LIV VARCHAR(100),
    CODEPOSTAL_LIV VARCHAR(5),
    VILLE_LIV VARCHAR(100),
    PAYS_LIV VARCHAR(100),
    CODE_COMPTA VARCHAR(7),
    COMPTE VARCHAR(8),
    ID_TIERS INTEGER,
    NOM_TIERS VARCHAR(100),
    PRENOM_TIERS VARCHAR(100),
    SURNOM_TIERS VARCHAR(20),
    CODE_T_CIVILITE VARCHAR(20),
    CODE_T_ENTITE VARCHAR(20),
    TVA_I_COM_COMPL VARCHAR(50),
    CODE_C_PAIEMENT VARCHAR(50),
    LIB_C_PAIEMENT VARCHAR(255),
    REPORT_C_PAIEMENT INTEGER,
    FIN_MOIS_C_PAIEMENT INTEGER)
AS
begin
begin
if (TYPE_ADRESSE is null) then TYPE_ADRESSE='????%';
for select

  infos.ID_INFOS_BONLIV,
  cast (null as INTEGER),
  infos.ID_BONLIV,
  infos.ADRESSE1,
  infos.ADRESSE2,
  infos.ADRESSE3,
  infos.CODEPOSTAL,
  infos.VILLE,
  infos.PAYS,         
  infos.ADRESSE1_LIV,
  infos.ADRESSE2_LIV,
  infos.ADRESSE3_LIV,
  infos.CODEPOSTAL_LIV,
  infos.VILLE_LIV,
  infos.PAYS_LIV,         
  infos.CODE_COMPTA,
  infos.COMPTE,
  cast (null as INTEGER),
  infos.NOM_TIERS,
  infos.PRENOM_TIERS,
  infos.SURNOM_TIERS,
  infos.CODE_T_CIVILITE,
  infos.CODE_T_ENTITE,
  infos.TVA_I_COM_COMPL,
  infos.CODE_C_PAIEMENT,
  infos.LIB_C_PAIEMENT ,
  infos.REPORT_C_PAIEMENT ,
  infos.FIN_MOIS_C_PAIEMENT 
  
   
  from ta_infos_BONLIV infos where infos.id_BONLIV = :id_DOCUMENT_recup into :ID_INFOS_BONLIV ,:id_adresse,
  :id_BONLIV ,:ADRESSE1 ,:ADRESSE2 ,:ADRESSE3 ,:CODEPOSTAL ,:VILLE ,:PAYS ,:ADRESSE1_LIV ,
  :ADRESSE2_LIV ,:ADRESSE3_LIV ,:CODEPOSTAL_LIV ,:VILLE_LIV ,:PAYS_LIV ,:CODE_COMPTA ,
  :COMPTE ,:ID_TIERS ,:NOM_TIERS ,:PRENOM_TIERS ,:SURNOM_TIERS, :CODE_T_CIVILITE, :CODE_T_ENTITE,:TVA_I_COM_COMPL,
  :CODE_C_PAIEMENT,:LIB_C_PAIEMENT,:REPORT_C_PAIEMENT,:FIN_MOIS_C_PAIEMENT
do begin

  suspend;   
end
      for select
      cast (null as INTEGER),
      adresse.id_adresse,
      cast (null as INTEGER),
      adresse.ADRESSE1_ADRESSE,
      adresse.ADRESSE2_ADRESSE,
      adresse.ADRESSE3_ADRESSE,
      adresse.CODEPOSTAL_ADRESSE,
      adresse.VILLE_ADRESSE,
      adresse.PAYS_ADRESSE,         
      adresse.ADRESSE1_ADRESSE,
      adresse.ADRESSE2_ADRESSE,
      adresse.ADRESSE3_ADRESSE,
      adresse.CODEPOSTAL_ADRESSE,
      adresse.VILLE_ADRESSE,
      adresse.PAYS_ADRESSE,         
      cast(null as varchar(8)),
      cast(null as varchar(8)),
      tiers.id_tiers,
      cast(null as varchar(100)),
      cast(null as varchar(100)),
      cast(null as varchar(20)),
      civilite.CODE_T_CIVILITE,
      entite.CODE_T_ENTITE,
      cast (null as varchar(50)),
      cast (null as varchar(50)),
      cast (null as varchar(255)),
      cast (null as INTEGER) ,
      cast (null as INTEGER) 
      
      from ta_adresse adresse  
      left join ta_r_adr rAdr on rAdr.ID_ADRESSE = adresse.ID_ADRESSE
      left join ta_r_adr_t_adr rAdrTAdr on rAdrTAdr.id_adresse = adresse.ID_ADRESSE
      left join ta_t_adr tAdr on tAdr.id_t_adr = rAdrTAdr.id_t_adr
      left join Ta_tiers tiers on tiers.ID_TIERS = rAdr.ID_TIERS
      left join TA_T_CIVILITE civilite on civilite.id_T_civilite = tiers.id_T_civilite
      left join ta_t_entite entite on entite.id_T_entite = tiers.id_t_entite
      where tiers.id_tiers = :id_tiers_recup  and (tadr.code_t_adr = :TYPE_ADRESSE)
      into :ID_INFOS_BONLIV ,:id_adresse,
      :id_BONLIV ,:ADRESSE1 ,:ADRESSE2 ,:ADRESSE3 ,:CODEPOSTAL ,:VILLE ,:PAYS ,:ADRESSE1_LIV ,
      :ADRESSE2_LIV ,:ADRESSE3_LIV ,:CODEPOSTAL_LIV ,:VILLE_LIV ,:PAYS_LIV ,:CODE_COMPTA ,
      :COMPTE ,:ID_TIERS ,:NOM_TIERS ,:PRENOM_TIERS ,:SURNOM_TIERS, :CODE_T_CIVILITE,:CODE_T_ENTITE,:TVA_I_COM_COMPL,
      :CODE_C_PAIEMENT,:LIB_C_PAIEMENT,:REPORT_C_PAIEMENT,:FIN_MOIS_C_PAIEMENT
      do begin
          suspend;
         end
      for select
      
      cast (null as INTEGER),
      adresse.id_adresse,
      cast (null as INTEGER),
      adresse.ADRESSE1_ADRESSE,
      adresse.ADRESSE2_ADRESSE,
      adresse.ADRESSE3_ADRESSE,
      adresse.CODEPOSTAL_ADRESSE,
      adresse.VILLE_ADRESSE,
      adresse.PAYS_ADRESSE,         
      adresse.ADRESSE1_ADRESSE,
      adresse.ADRESSE2_ADRESSE,
      adresse.ADRESSE3_ADRESSE,
      adresse.CODEPOSTAL_ADRESSE,
      adresse.VILLE_ADRESSE,
      adresse.PAYS_ADRESSE,         
      cast(null as varchar(8)),
      cast(null as varchar(8)),
      tiers.id_tiers,
      cast(null as varchar(100)),
      cast(null as varchar(100)),
      cast(null as varchar(20)),
      civilite.CODE_T_CIVILITE,
      entite.CODE_T_ENTITE,
      cast (null as varchar(50)),
      cast (null as varchar(50)),
      cast (null as varchar(255)),
      cast (null as INTEGER) ,
      cast (null as INTEGER) 
      
      from ta_adresse adresse  
      left join ta_r_adr rAdr on rAdr.ID_ADRESSE = adresse.ID_ADRESSE
      left join ta_r_adr_t_adr rAdrTAdr on rAdrTAdr.id_adresse = adresse.ID_ADRESSE
      left join ta_t_adr tAdr on tAdr.id_t_adr = rAdrTAdr.id_t_adr
      left join Ta_tiers tiers on tiers.ID_TIERS = rAdr.ID_TIERS
      left join TA_T_CIVILITE civilite on civilite.id_T_civilite = tiers.id_T_civilite
      left join ta_t_entite entite on entite.id_T_entite = tiers.id_t_entite
      where tiers.id_tiers = :id_tiers_recup  and ((tadr.code_t_adr is null) or (tadr.code_t_adr <> :TYPE_ADRESSE))
      into :ID_INFOS_BONLIV ,:id_adresse,
      :id_BONLIV ,:ADRESSE1 ,:ADRESSE2 ,:ADRESSE3 ,:CODEPOSTAL ,:VILLE ,:PAYS ,:ADRESSE1_LIV ,
      :ADRESSE2_LIV ,:ADRESSE3_LIV ,:CODEPOSTAL_LIV ,:VILLE_LIV ,:PAYS_LIV ,:CODE_COMPTA ,
      :COMPTE ,:ID_TIERS ,:NOM_TIERS ,:PRENOM_TIERS ,:SURNOM_TIERS, :CODE_T_CIVILITE,:CODE_T_ENTITE,:TVA_I_COM_COMPL,
      :CODE_C_PAIEMENT,:LIB_C_PAIEMENT,:REPORT_C_PAIEMENT,:FIN_MOIS_C_PAIEMENT
      do begin
          suspend;
         end
end
end;
^







delete from TA_VISUALISATION
^

INSERT INTO TA_VISUALISATION (ID, CODE_REQUETE, REQUETE, CHAMPS, TITRE_CHAMPS, TAILLE_CHAMPS, MODULE, IMPRESSION, IDENTIFIANT, ID_EDITEUR, ID_PLUGIN, GROUPBY, PROC, PARAM) VALUES (1, 'Liste des articles', 'select CODE_ARTICLE,LIBELLEC_ARTICLE,NUMCPT_ARTICLE,DIVERS_ARTICLE,COMMENTAIRE_ARTICLE,STOCK_MIN_ARTICLE,PRIX_PRIX,CODE_UNITE,CODE_FAMILLE,CODE_TVA,TAUX_TVA,NUMCPT_TVA,CODE_T_TVA  from v_article', 'CODE_ARTICLE;LIBELLEC_ARTICLE;NUMCPT_ARTICLE;DIVERS_ARTICLE;COMMENTAIRE_ARTICLE;STOCK_MIN_ARTICLE;PRIX_PRIX;CODE_UNITE;CODE_FAMILLE;CODE_TVA;TAUX_TVA;NUMCPT_TVA;CODE_T_TVA', 'CODE;LIBELLE;COMPTE;DIVERS;COMMENTAIRE;STOCK MINI;PRIX;UNITE;FAMILLE;CODE TVA;TAUX TVA;COMPTE TVA;TYPE TVA', '50;100;50;100;100;20;50;20;50;50;50;50;50', 'article', '/report/article.rptdesign', 'CODE_ARTICLE', 'fr.legrain.articles.editor.EditorArticle', 'Articles', NULL, 0, NULL)
^
INSERT INTO TA_VISUALISATION (ID, CODE_REQUETE, REQUETE, CHAMPS, TITRE_CHAMPS, TAILLE_CHAMPS, MODULE, IMPRESSION, IDENTIFIANT, ID_EDITEUR, ID_PLUGIN, GROUPBY, PROC, PARAM) VALUES (2, 'liste des Factures', 'select CODE_FACTURE,DATE_FACTURE,CODE_TIERS,NOM_TIERS,(MTNETTTC)as MTNETTTC ,EXPORT FROM  V_facture , calcul_total_direct(''facture'',id_facture)', 'CODE_FACTURE;DATE_FACTURE;CODE_TIERS;NOM_TIERS;MTNETTTC;EXPORT', 'CODE;DATE FACTURE;CODE TIERS;NOM TIERS;NET TTC;EXPORTEE', '100;100;100;100;200;100;100;100', 'facture', '/report/visu_facture.rptdesign', 'CODE_FACTURE', 'fr.legrain.editor.facture.swt.multi', 'Facture', NULL, 0, NULL)
^
INSERT INTO TA_VISUALISATION (ID, CODE_REQUETE, REQUETE, CHAMPS, TITRE_CHAMPS, TAILLE_CHAMPS, MODULE, IMPRESSION, IDENTIFIANT, ID_EDITEUR, ID_PLUGIN, GROUPBY, PROC, PARAM) VALUES (3, 'Liste des tiers', 'select CODE_TIERS,CODE_COMPTA,COMPTE,NOM_TIERS,ACTIF_TIERS,TTC_TIERS,CODE_T_CIVILITE,CODE_ENTREPRISE,CODE_T_TIERS,CODE_BANQUE,CODE_T_ENTITE from v_tiers', 'CODE_TIERS;CODE_COMPTA;COMPTE;NOM_TIERS;ACTIF_TIERS;TTC_TIERS;CODE_T_CIVILITE;CODE_ENTREPRISE;CODE_T_TIERS;CODE_BANQUE;CODE_T_ENTITE', 'CODE_TIERS;CODE_COMPTA;COMPTE;NOM_TIERS;ACTIF_TIERS;TTC_TIERS;CODE_T_CIVILITE;CODE_ENTREPRISE;CODE_T_TIERS;CODE_BANQUE;CODE_T_ENTITE', '100;50;50;100;30;30;50;50;50;50;50', 'tiers', '/report/tiers.rptdesign', 'CODE_TIERS', 'fr.legrain.tiers.editor.EditorTiers', 'Tiers', NULL, 0, NULL)
^
INSERT INTO TA_VISUALISATION (ID, CODE_REQUETE, REQUETE, CHAMPS, TITRE_CHAMPS, TAILLE_CHAMPS, MODULE, IMPRESSION, IDENTIFIANT, ID_EDITEUR, ID_PLUGIN, GROUPBY, PROC, PARAM) VALUES (4, 'Etat des stocks', 'select CODE_ARTICLE,LIBELLE_ARTICLE,QTE,UNITE,PERIODE_DEB,PERIODE_FIN,REPORT_QTE from VISU_ETAT_STOCKS(null,null,null,null,null,null)', 'CODE_ARTICLE;LIBELLE_ARTICLE;QTE;UNITE;PERIODE_DEB;PERIODE_FIN', 'Code article;Libellé de l''article;Quantité;Unité;Date début période;Date fin période', '100;100;100;100;100;100', 'stocks', '/report/EtatStocks.rptdesign', 'CODE_ARTICLE', 'fr.legrain.stocks.editor.EditorStocks', 'Stocks', NULL, 1, NULL)
^
INSERT INTO TA_VISUALISATION (ID, CODE_REQUETE, REQUETE, CHAMPS, TITRE_CHAMPS, TAILLE_CHAMPS, MODULE, IMPRESSION, IDENTIFIANT, ID_EDITEUR, ID_PLUGIN, GROUPBY, PROC, PARAM) VALUES (5, 'Liste des bons de livraison', ' select b.code_bonliv,b.date_bonliv,CODE_TIERS,NOM_TIERS,(MTNETTTC)as MTNETTTC  FROM v_bonliv b , calcul_total_direct(''BonLiv'',b.id_bonliv)', 'code_bonliv;date_bonliv;CODE_TIERS;NOM_TIERS;MTNETTTC', 'CODE;DATE BONLIV;CODE TIERS;NOM TIERS;NET TTC', '100;100;100;100;100', 'BONLIV', '/report/visu_BonLiv.rptdesign', 'CODE_BONLIV', 'fr.legrain.editor.Bl.swt.multi', 'BonLiv', NULL, 0, NULL)
^
INSERT INTO TA_VISUALISATION (ID, CODE_REQUETE, REQUETE, CHAMPS, TITRE_CHAMPS, TAILLE_CHAMPS, MODULE, IMPRESSION, IDENTIFIANT, ID_EDITEUR, ID_PLUGIN, GROUPBY, PROC, PARAM) VALUES (6, 'Liste des devis', ' select b.code_devis,b.date_devis,CODE_TIERS,NOM_TIERS,(MTNETTTC)as MTNETTTC  FROM v_devis b , calcul_total_direct(''devis'',b.id_devis)', 'code_devis;date_devis;CODE_TIERS;NOM_TIERS;MTNETTTC', 'CODE;DATE DEVIS;CODE TIERS;NOM TIERS;NET TTC', '100;100;100;100;100', 'DEVIS', '/report/visu_devis.rptdesign', 'CODE_DEVIS', 'fr.legrain.editor.devis.swt.multi', 'Devis', NULL, 0, NULL)
^
INSERT INTO TA_VISUALISATION (ID, CODE_REQUETE, REQUETE, CHAMPS, TITRE_CHAMPS, TAILLE_CHAMPS, MODULE, IMPRESSION, IDENTIFIANT, ID_EDITEUR, ID_PLUGIN, GROUPBY, PROC, PARAM) VALUES (7, 'Liste des articles Facture', 'select V.date_facture,V.code_tiers,V.code_facture,vl.code_article,vl.u1_l_facture,(vl.qte_l_facture),(MTNETHT)as ht,(MTNETTVA)as tva,(MTNETTTC)as ttc from V_l_facture vl,calcul_total_ligne_direct(''facture'',vl.id_l_facture),v_facture V where V.ID_FACTURE=vl.ID_FACTURE and vl.code_article is not null  order by V.date_facture,V.code_tiers,V.code_facture,vl.code_article,vl.u1_l_facture', 'date_facture;code_tiers;code_facture;code_article;u1_l_facture;qte_l_facture;ht;tva;ttc', 'date facture;code tiers;code facture;code article;unité;qté;ht;tva;ttc', '100;100;100;100;100;100;100;100;100', 'tiers', NULL, 'CODE_facture', 'fr.legrain.editor.facture.swt.multi', 'Facture', NULL, 0, NULL)
^
INSERT INTO TA_VISUALISATION (ID, CODE_REQUETE, REQUETE, CHAMPS, TITRE_CHAMPS, TAILLE_CHAMPS, MODULE, IMPRESSION, IDENTIFIANT, ID_EDITEUR, ID_PLUGIN, GROUPBY, PROC, PARAM) VALUES (8, 'Chiffre d''affaires maintenance synthèse', 'select  extract ( year from (cast (v_facture.date_facture as date))) as annee,  case when extract ( year from (cast (v_facture.date_facture as date))) <= 2001 then  cast(sum(MTNETHT)/6.55957 as numeric(15,2)) else sum(MTNETHT)end as ht,  case when extract ( year from (cast (v_facture.date_facture as date))) <= 2001 then  cast(sum(MTNETTVA)/6.55957 as numeric(15,2)) else sum(MTNETTVA)end as tva,  case when extract ( year from (cast (v_facture.date_facture as date))) <= 2001 then  cast(sum(MTNETTTC)/6.55957 as numeric(15,2)) else sum(MTNETTTC)end as ttc  from  v_l_facture,  calcul_total_ligne_direct(''facture'',v_l_facture.id_l_facture),  ta_article,  v_facture  where  v_facture.id_facture = v_l_facture.id_facture  and v_facture.code_facture like(''%'')  and v_l_facture.id_article = ta_article.id_article  and ta_article.id_famille between 21 and 21', 'annee¤extract ( year from (cast (v_facture.date_facture as date)));  ht¤sum(MTNETHT)¤having;  tva¤sum(MTNETVA)¤having;  ttc¤sum(MTNETTTC)¤having', 'année;HT;TVA;TTC', '100;100;100;100', 'article', NULL, 'CODE_ARTICLE', 'fr.legrain.articles.editor.EditorArticle', 'Articles', 'group by extract ( year from (cast (v_facture.date_facture as date)))', 0, NULL)
^
INSERT INTO TA_VISUALISATION (ID, CODE_REQUETE, REQUETE, CHAMPS, TITRE_CHAMPS, TAILLE_CHAMPS, MODULE, IMPRESSION, IDENTIFIANT, ID_EDITEUR, ID_PLUGIN, GROUPBY, PROC, PARAM) VALUES (9, 'Chiffre d''affaires maintenance (Détail)', 'select  extract ( year from (cast (v_facture.date_facture as date)))as annee,  (v_l_facture.code_article) as codeArticleLigne,  case when extract ( year from (cast (v_facture.date_facture as date))) <= 2001 then  cast(sum(MTNETHT)/6.55957 as numeric(15,2)) else sum(MTNETHT)end as ht,  case when extract ( year from (cast (v_facture.date_facture as date))) <= 2001 then  cast(sum(MTNETTVA)/6.55957 as numeric(15,2)) else sum(MTNETTVA)end as tva,  case when extract ( year from (cast (v_facture.date_facture as date))) <= 2001 then  cast(sum(MTNETTTC)/6.55957 as numeric(15,2)) else sum(MTNETTTC)end as ttc  from  v_l_facture,  calcul_total_ligne_direct(''facture'',v_l_facture.id_l_facture),  ta_article,  v_facture  where  v_facture.id_facture = v_l_facture.id_facture  and v_facture.code_facture like(''%'')  and v_l_facture.id_article = ta_article.id_article  and ta_article.id_famille between 21 and 21', 'annee¤extract ( year from (cast (v_facture.date_facture as date)));  codeArticleLigne¤v_l_facture.code_article;  ht¤sum(MTNETHT)¤having;  tva¤sum(MTNETVA)¤having;  ttc¤sum(MTNETTTC)¤having', 'année;code article;HT;TVA;TTC', '100;100;100;100;100', 'article', NULL, 'CODE_ARTICLE', 'fr.legrain.articles.editor.EditorArticle', 'Articles', 'group by extract ( year from (cast (v_facture.date_facture as date))),v_l_facture.code_article', 0, NULL)
^
INSERT INTO TA_VISUALISATION (ID, CODE_REQUETE, REQUETE, CHAMPS, TITRE_CHAMPS, TAILLE_CHAMPS, MODULE, IMPRESSION, IDENTIFIANT, ID_EDITEUR, ID_PLUGIN, GROUPBY, PROC, PARAM) VALUES (10, 'Liste des articles facturés', 'select  V_FACTURE.date_facture,  v_l_facture.code_article,  sum(MTNETHT)as ht,  sum(MTNETTVA)as tva,  sum(MTNETTTC)as ttc  from   v_l_facture,  calcul_total_ligne_direct(''facture'',v_l_facture.id_l_facture),  V_FACTURE  where V_FACTURE.ID_FACTURE=v_l_facture.ID_FACTURE   and v_l_facture.code_article is not null  order by  v_l_facture.code_article,  V_FACTURE.date_facture', 'date_facture;  code_article;  ht;  tva;  ttc', 'date facture;  code article;  ht;  tva;  ttc', '100;100;100;100;100', 'article', NULL, 'code_article', 'fr.legrain.articles.editor.EditorArticle', 'Articles', 'group by v_l_facture.code_article,V_FACTURE.date_facture', 0, NULL)
^
INSERT INTO TA_VISUALISATION (ID, CODE_REQUETE, REQUETE, CHAMPS, TITRE_CHAMPS, TAILLE_CHAMPS, MODULE, IMPRESSION, IDENTIFIANT, ID_EDITEUR, ID_PLUGIN, GROUPBY, PROC, PARAM) VALUES (11, 'Extraction tiers', 'select NOM_TIERS,PRENOM_TIERS,ADRESSE1_ADRESSE,  ADRESSE2_ADRESSE,ADRESSE3_ADRESSE,CODEPOSTAL_ADRESSE,  VILLE_ADRESSE,PAYS_ADRESSE,cast((cast(adr.CODEPOSTAL_ADRESSE as integer) / 1000)   as varchar(2)) as departement,  CODE_T_CIVILITE,SURNOM_TIERS,LIBL_ENTREPRISE,  CODE_TIERS,CODE_ENTREPRISE,ACTIF_TIERS,TTC_TIERS,CODE_T_TIERS,  LIBELLE_T_TIERS,LIBL_COMMENTAIRE,CODE_T_ENTITE, tva_i_com_compl,  siret_compl,NUMERO_TELEPHONE,ADRESSE_EMAIL,ADRESSE_WEB,QUAND_CREE_TIERS,  max(f.Date_facture) as date_dern_fact,max(bonliv.Date_bonliv)as date_dern_bonliv,     max(devis.Date_devis)as date_dern_devis From TA_TIERS Tiers LEFT OUTER   join TA_T_ENTITE Ent on Tiers.ID_T_ENTITE = Ent.ID_T_ENTITE LEFT OUTER   join TA_T_CIVILITE Civ on Tiers.ID_T_CIVILITE = Civ.ID_T_CIVILITE LEFT OUTER   join TA_ENTREPRISE E on Tiers.ID_ENTREPRISE = E.ID_ENTREPRISE LEFT OUTER   join TA_T_TIERS TTiers on Tiers.ID_T_TIERS = TTiers.ID_T_TIERS LEFT OUTER   join TA_COMMENTAIRE Com on Tiers.ID_TIERS = Com.ID_TIERS LEFT OUTER   join ta_compl compl on compl.id_tiers = Tiers.id_tiers LEFT OUTER   join TA_R_ADR radr on radr.ID_TIERS = Tiers.id_tiers LEFT OUTER   join TA_R_EMAIL remail on remail.id_tiers = Tiers.id_tiers LEFT OUTER   join TA_R_WEB rweb on rweb.id_tiers = Tiers.id_tiers LEFT OUTER   join ta_ADRESSE adr on adr.ID_ADRESSE = radr.ID_ADRESSE LEFT OUTER   join ta_EMAIL email on email.ID_EMAIL = remail.ID_EMAIL LEFT OUTER   join ta_WEB web on web.ID_WEB = rweb.ID_WEB LEFT OUTER   join ta_facture f on f.ID_tiers = Tiers.id_tiers LEFT OUTER   join ta_bonliv bonliv on bonliv.ID_tiers = Tiers.id_tiers LEFT OUTER   join ta_devis devis on devis.ID_tiers = Tiers.id_tiers LEFT OUTER   join TA_R_TEL rtel on rtel.ID_TIERS = Tiers.id_tiers LEFT OUTER   join TA_TELEPHONE tel on tel.ID_TELEPHONE = rtel.ID_TELEPHONE', 'NOM_TIERS;  PRENOM_TIERS;  ADRESSE1_ADRESSE;  ADRESSE2_ADRESSE;  ADRESSE3_ADRESSE;  CODEPOSTAL_ADRESSE;  VILLE_ADRESSE;  PAYS_ADRESSE;  departement¤cast((cast(adr.CODEPOSTAL_ADRESSE as integer) / 1000) as varchar(2));  CODE_T_CIVILITE;  SURNOM_TIERS;  LIBL_ENTREPRISE;  CODE_TIERS;  CODE_ENTREPRISE;  ACTIF_TIERS;  TTC_TIERS;  CODE_T_TIERS;  LIBELLE_T_TIERS;  LIBL_COMMENTAIRE;  CODE_T_ENTITE;  tva_i_com_compl;  siret_compl;  NUMERO_TELEPHONE;  ADRESSE_EMAIL;  ADRESSE_WEB;  QUAND_CREE_TIERS;  date_dern_fact¤max(f.Date_facture)¤having;  date_dern_bonliv¤max(bonliv.Date_bonliv)¤having;  date_dern_devis¤max(devis.Date_devis)¤having', 'Nom;  Prénom;  Adresse 1;  Adresse 2;  Adresse 3;  Code postal;  Ville;Pays;  Département;  Civilite;  Surnom;  Entreprise;  Code tiers;  Code entreprise;  Actif;  TTC;  Type tiers;  Libellé type;  Commentaire;  Type entité;  Tva intra;  Siret;  Tél;  Email;  Web;  Date création;  Date dernière facture;  Date dernier bonliv;  Date dernier devis', '50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50', 'tiers', '/report/extraction_Tiers.rptdesign', 'CODE_TIERS', 'fr.legrain.tiers.editor.EditorTiers', 'Tiers', 'group by  NOM_TIERS,PRENOM_TIERS,ADRESSE1_ADRESSE,ADRESSE2_ADRESSE,ADRESSE3_ADRESSE,CODEPOSTAL_ADRESSE,VILLE_ADRESSE,PAYS_ADRESSE,CODE_T_CIVILITE,SURNOM_TIERS,LIBL_ENTREPRISE,CODE_TIERS,CODE_ENTREPRISE,ACTIF_TIERS,TTC_TIERS,CODE_T_TIERS,LIBELLE_T_TIERS,LIBL_COMMENTAIRE,CODE_T_ENTITE,tva_i_com_compl,siret_compl,NUMERO_TELEPHONE,ADRESSE_EMAIL,ADRESSE_WEB,QUAND_CREE_TIERS', 0, NULL)
^
INSERT INTO TA_VISUALISATION (ID, CODE_REQUETE, REQUETE, CHAMPS, TITRE_CHAMPS, TAILLE_CHAMPS, MODULE, IMPRESSION, IDENTIFIANT, ID_EDITEUR, ID_PLUGIN, GROUPBY, PROC, PARAM) VALUES (12, 'Stocks début', 'select code_article,libellec_article,qte_report_stock,unite_report_stock,date_deb_report_stock,date_fin_report_stock from ta_report_stock R left join ta_Article Article on(Article.id_article=R.id_article_report_stock)', 'code_article;libellec_article;qte_report_stock;unite_report_stock;date_deb_report_stock;date_fin_report_stock', 'Article;Libellé de article;Quantité;Unité;Date deb;Date fin', '100;200;100;100;100;100', 'stocks', '/report/StocksDebut.rptdesign', 'CODE_ARTICLE', 'fr.legrain.stocks.editor.EditorStocks', 'Stocks', '', 0, NULL)
^
INSERT INTO TA_VISUALISATION (ID, CODE_REQUETE, REQUETE, CHAMPS, TITRE_CHAMPS, TAILLE_CHAMPS, MODULE, IMPRESSION, IDENTIFIANT, ID_EDITEUR, ID_PLUGIN, GROUPBY, PROC, PARAM) VALUES (13, 'Chiffre d''affaires synthèse', 'select  extract ( year from (cast (v_facture.date_facture as date))) as annee,  case when extract ( year from (cast (v_facture.date_facture as date))) <= 2001 then  cast(sum(MTNETHT)/6.55957 as numeric(15,2)) else sum(MTNETHT)end as ht,  case when extract ( year from (cast (v_facture.date_facture as date))) <= 2001 then  cast(sum(MTNETTVA)/6.55957 as numeric(15,2)) else sum(MTNETTVA)end as tva,  case when extract ( year from (cast (v_facture.date_facture as date))) <= 2001 then  cast(sum(MTNETTTC)/6.55957 as numeric(15,2)) else sum(MTNETTTC)end as ttc  from  v_l_facture,  calcul_total_ligne_direct(''facture'',v_l_facture.id_l_facture),  ta_article,  v_facture  where  v_facture.id_facture = v_l_facture.id_facture  and v_facture.code_facture like(''%'')  and v_l_facture.id_article = ta_article.id_article', 'annee¤extract ( year from (cast (v_facture.date_facture as date)));  ht¤sum(MTNETHT)¤having;  tva¤sum(MTNETVA)¤having;  ttc¤sum(MTNETTTC)¤having', 'année;HT;TVA;TTC', '100;100;100;100', 'article', NULL, 'CODE_FACTURE', 'fr.legrain.articles.editor.EditorArticle', 'Facture', 'group by extract ( year from (cast (v_facture.date_facture as date)))', 0, NULL)
^
INSERT INTO TA_VISUALISATION (ID, CODE_REQUETE, REQUETE, CHAMPS, TITRE_CHAMPS, TAILLE_CHAMPS, MODULE, IMPRESSION, IDENTIFIANT, ID_EDITEUR, ID_PLUGIN, GROUPBY, PROC, PARAM) VALUES (14, 'Chiffre d''affaires (Détail)', 'select  extract ( year from (cast (v_facture.date_facture as date)))as annee,  (v_l_facture.code_article) as codeArticleLigne,  case when extract ( year from (cast (v_facture.date_facture as date))) <= 2001 then  cast(sum(MTNETHT)/6.55957 as numeric(15,2)) else sum(MTNETHT)end as ht,  case when extract ( year from (cast (v_facture.date_facture as date))) <= 2001 then  cast(sum(MTNETTVA)/6.55957 as numeric(15,2)) else sum(MTNETTVA)end as tva,  case when extract ( year from (cast (v_facture.date_facture as date))) <= 2001 then  cast(sum(MTNETTTC)/6.55957 as numeric(15,2)) else sum(MTNETTTC)end as ttc  from  v_l_facture,  calcul_total_ligne_direct(''facture'',v_l_facture.id_l_facture),  ta_article,  v_facture  where  v_facture.id_facture = v_l_facture.id_facture  and v_facture.code_facture like(''%'')  and v_l_facture.id_article = ta_article.id_article', 'annee¤extract ( year from (cast (v_facture.date_facture as date)));  codeArticleLigne¤v_l_facture.code_article;  ht¤sum(MTNETHT)¤having;  tva¤sum(MTNETVA)¤having;  ttc¤sum(MTNETTTC)¤having', 'année;code article;HT;TVA;TTC', '100;100;100;100;100', 'article', NULL, 'CODE_ARTICLE', 'fr.legrain.articles.editor.EditorArticle', 'Articles', 'group by extract ( year from (cast (v_facture.date_facture as date))),v_l_facture.code_article', 0, NULL)
^
INSERT INTO TA_VISUALISATION (ID, CODE_REQUETE, REQUETE, CHAMPS, TITRE_CHAMPS, TAILLE_CHAMPS, MODULE, IMPRESSION, IDENTIFIANT, ID_EDITEUR, ID_PLUGIN, GROUPBY, PROC, PARAM) VALUES (15, 'Liste des articles Facture (avec code facture)', 'select   V.date_facture,  V.code_tiers,  V.code_facture,  vl.code_article,  (a.code_famille)as famille,  vl.u1_l_facture,  vl.qte_l_facture,  (MTNETHT)as ht,  (MTNETTVA)as tva,  (MTNETTTC)as ttc  from V_l_facture vl,   calcul_total_ligne_direct(''facture'',vl.id_l_facture),  v_facture V,  v_article a  where V.ID_FACTURE=vl.ID_FACTURE  and a.id_article = vl.id_article   and vl.code_article is not null  order by V.date_facture,V.code_tiers,V.code_facture,vl.code_article   ,vl.u1_l_facture', 'date_facture;code_tiers;code_facture;code_article;famille¤a.code_famille;u1_l_facture;qte_l_facture;ht;tva;ttc', 'date facture;code tiers;code facture;code article;famille;unité;qté;ht;tva;ttc', '100;100;100;100;100;100;100;100;100;100', 'article', NULL, 'CODE_facture', 'fr.legrain.editor.facture.swt.multi', 'Facture', NULL, 0, NULL)
^
