CREATE or ALTER PROCEDURE GESTION_VISUALISATION_ARTICLES (
    CLAUSEWHERE VARCHAR(1000))
RETURNS (
    CODE_ARTICLE VARCHAR(20),
    LIBELLEC_ARTICLE VARCHAR(100),
    NUMCPT_ARTICLE VARCHAR(8),
    DIVERS_ARTICLE VARCHAR(255),
    COMMENTAIRE_ARTICLE VARCHAR(255),
    STOCK_MIN_ARTICLE NUMERIC(15,2),
    PRIX_PRIX NUMERIC(15,2),
    CODE_UNITE VARCHAR(20),
    CODE_FAMILLE VARCHAR(20),
    CODE_TVA VARCHAR(1),
    TAUX_TVA NUMERIC(15,2),
    NUMCPT_TVA VARCHAR(8),
    CODE_T_TVA VARCHAR(20))
AS
BEGIN
  EXIT;
END
^


CREATE or ALTER PROCEDURE GESTION_VISUALISATION_DOCUMENT (
    CLAUSEWHERE VARCHAR(1000))
RETURNS (
    CODE VARCHAR(20),
    DATE_DOC TIMESTAMP,
    DATE_ECH TIMESTAMP,
    DATE_LIV TIMESTAMP,
    LIBELLE VARCHAR(255),
    ADRESSE1_ADRESSE VARCHAR(100),
    ADRESSE2_ADRESSE VARCHAR(100),
    ADRESSE3_ADRESSE VARCHAR(100),
    CODEPOSTAL_ADRESSE VARCHAR(5),
    VILLE_ADRESSE VARCHAR(100),
    PAYS_ADRESSE VARCHAR(100),
    CODE_TIERS VARCHAR(20),
    NOM_TIERS VARCHAR(100),
    CODE_COMPTA VARCHAR(8),
    COMPTE VARCHAR(8),
    CODE_T_PAIEMENT VARCHAR(20),
    REGLE NUMERIC(15,2),
    TX_REM_HT NUMERIC(15,2),
    TX_REM_TTC NUMERIC(15,2),
    NB_E INTEGER,
    MT_TTC_CALC NUMERIC(15,2),
    MT_HT_CALC NUMERIC(15,2),
    MT_TVA_CALC NUMERIC(15,2),
    TTC SMALLINT,
    EXPORT SMALLINT)
AS
BEGIN
  EXIT;
END
^


CREATE or ALTER PROCEDURE GESTION_VISUALISATION_TIERS (
    CLAUSEWHERE VARCHAR(1000))
RETURNS (
    CODE_TIERS VARCHAR(20),
    CODE_COMPTA VARCHAR(7),
    COMPTE VARCHAR(8),
    NOM_TIERS VARCHAR(100),
    ACTIF_TIERS SMALLINT,
    TTC_TIERS SMALLINT,
    CODE_T_CIVILITE VARCHAR(20),
    CODE_ENTREPRISE VARCHAR(20),
    CODE_T_TIERS VARCHAR(20))
AS
BEGIN
  EXIT;
END
^



CREATE or ALTER PROCEDURE GESTION_VISUALISATION_ARTICLES (
    CLAUSEWHERE VARCHAR(1000),
    CLAUSESELECT VARCHAR(5000),
    CLAUSEGROUPBY VARCHAR(1000))
RETURNS (
    CODE_ARTICLE VARCHAR(20),
    LIBELLEC_ARTICLE VARCHAR(100),
    NUMCPT_ARTICLE VARCHAR(8),
    DIVERS_ARTICLE VARCHAR(255),
    COMMENTAIRE_ARTICLE VARCHAR(255),
    STOCK_MIN_ARTICLE NUMERIC(15,2),
    PRIX_PRIX NUMERIC(15,2),
    CODE_UNITE VARCHAR(20),
    CODE_FAMILLE VARCHAR(20),
    CODE_TVA VARCHAR(1),
    TAUX_TVA NUMERIC(15,2),
    NUMCPT_TVA VARCHAR(8),
    CODE_T_TVA VARCHAR(20))
AS
DECLARE VARIABLE REQUETE VARCHAR(1000);
begin
if (:clauseselect is not null and :clauseselect<>'') then  requete=:clauseselect;
else
requete = 'select  CODE_ARTICLE, LIBELLEC_ARTICLE,  NUMCPT_ARTICLE, DIVERS_ARTICLE, COMMENTAIRE_ARTICLE,
STOCK_MIN_ARTICLE, PRIX_PRIX,  CODE_UNITE, CODE_FAMILLE, CODE_TVA, TAUX_TVA, NUMCPT_TVA, CODE_T_TVA
FROM V_ARTICLE' ;
if (:CLAUSEWHERE is not null and :clausewhere<>'') then
begin
  requete = :requete||' '||:CLAUSEWHERE ;
end
if (:clausegroupby is not null and :clausegroupby<>'') then
begin
  requete = :requete||' '||:clausegroupby ;
end
for  execute statement :requete into
CODE_ARTICLE,
LIBELLEC_ARTICLE,
NUMCPT_ARTICLE,
DIVERS_ARTICLE,
COMMENTAIRE_ARTICLE,
STOCK_MIN_ARTICLE,
PRIX_PRIX,
CODE_UNITE,
CODE_FAMILLE,
CODE_TVA,
TAUX_TVA,
NUMCPT_TVA,
CODE_T_TVA
do
begin
suspend;
end
end
^

CREATE or ALTER PROCEDURE GESTION_VISUALISATION_DOCUMENT (
    CLAUSEWHERE VARCHAR(1000),
    CLAUSESELECT VARCHAR(5000),
    CLAUSEGROUPBY VARCHAR(1000))
RETURNS (
    CODE VARCHAR(20),
    DATE_DOC TIMESTAMP,
    CODE_TIERS VARCHAR(20),
    NOM_TIERS VARCHAR(100),
    MT_TTC_CALC NUMERIC(15,2),
    EXPORT SMALLINT)
AS
DECLARE VARIABLE REQUETE VARCHAR(1000);
begin
if (:clauseselect is not null and :clauseselect<>'') then  requete=:clauseselect;
else
requete = 'select
CODE_FACTURE,
DATE_FACTURE,
CODE_TIERS,
NOM_TIERS,
T.MTNETTTC,
EXPORT
FROM V_facture f, calcul_total_direct(''facture'',f.id_facture) T    ' ;

if (:CLAUSEWHERE is not null and :clausewhere<>'') then
begin
  requete = :requete||' '||:CLAUSEWHERE ;
end
if (:clausegroupby is not null and :clausegroupby<>'') then
begin
  requete = :requete||' '||:clausegroupby ;
end
for  execute statement :requete into
CODE,
DATE_DOC,
CODE_TIERS,
NOM_TIERS,
MT_TTC_CALC,
EXPORT
do
begin
suspend;
end
end
^

CREATE or ALTER PROCEDURE GESTION_VISUALISATION_TIERS (
    CLAUSEWHERE VARCHAR(1000),
    CLAUSESELECT VARCHAR(5000),
    CLAUSEGROUPBY VARCHAR(1000))
RETURNS (
    CODE_TIERS VARCHAR(20),
    CODE_COMPTA VARCHAR(7),
    COMPTE VARCHAR(8),
    NOM_TIERS VARCHAR(100),
    ACTIF_TIERS SMALLINT,
    TTC_TIERS SMALLINT,
    CODE_T_CIVILITE VARCHAR(20),
    CODE_ENTREPRISE VARCHAR(20),
    CODE_T_TIERS VARCHAR(20),
    CODE_BANQUE VARCHAR(20),
    CODE_T_ENTITE VARCHAR(20))
AS
DECLARE VARIABLE REQUETE VARCHAR(1000);
begin
if (:clauseselect is not null and :clauseselect<>'') then  requete=:clauseselect;
else
requete = 'select CODE_TIERS,CODE_COMPTA,COMPTE,NOM_TIERS,ACTIF_TIERS,TTC_TIERS,
CODE_T_CIVILITE,CODE_ENTREPRISE,CODE_T_TIERS,CODE_BANQUE,CODE_T_ENTITE
  from v_tiers ';

if (:CLAUSEWHERE is not null and :clausewhere<>'') then
begin
  requete = :requete||' '||:CLAUSEWHERE ;
end
if (:clausegroupby is not null and :clausegroupby<>'') then
begin
  requete = :requete||' '||:clausegroupby ;
end
for  execute statement :requete into

    CODE_TIERS,
    CODE_COMPTA,
    COMPTE,
    NOM_TIERS,
    ACTIF_TIERS,
    TTC_TIERS,
    CODE_T_CIVILITE,
    CODE_ENTREPRISE,
    CODE_T_TIERS,
    CODE_BANQUE,
    CODE_T_ENTITE
do
begin
suspend;
end
end
^

CREATE or ALTER PROCEDURE MISEAJOUR_PARAM_ARTICLE (
    NUM_PIECE INTEGER)
AS
begin
if (:num_piece=0) then
    begin  /*travail sur toutes les factures*/
        Update ta_l_facture lf set lf.compte_l_facture = (select A.numcpt_article
        from ta_article A where a.id_article=lf.id_article)
        where lf.compte_l_facture is null or (lf.compte_l_facture not in(select A.numcpt_article
        from ta_article A where a.id_article=lf.id_article));
    end
else
    begin /*travail sur une facture particulière*/
        Update ta_l_facture lf set lf.compte_l_facture = (select A.numcpt_article
        from ta_article A where a.id_article=lf.id_article)
        where (lf.id_facture = :num_piece) and  (lf.compte_l_facture is null or (lf.compte_l_facture not in(select A.numcpt_article
        from ta_article A where a.id_article=lf.id_article)));
    end
  suspend;
end
^


CREATE or ALTER PROCEDURE GENERATIONFACTURE (
    MODELEFACTURE VARCHAR(9),
    COMPTEURDEPART INTEGER)
RETURNS (
    NB_FACTURE INTEGER)
AS
DECLARE VARIABLE COMPTEUR INTEGER = 0;
DECLARE VARIABLE CODEFACTURE_TMP VARCHAR(9);
DECLARE VARIABLE ID_TIERS_TMP INTEGER;
DECLARE VARIABLE ID_FACT_COURANT INTEGER;
DECLARE VARIABLE ADRESSE1 VARCHAR(100);
DECLARE VARIABLE ADRESSE2 VARCHAR(100);
DECLARE VARIABLE ADRESSE3 VARCHAR(100);
DECLARE VARIABLE CODEPOSTAL VARCHAR(5);
DECLARE VARIABLE VILLE VARCHAR(100);
DECLARE VARIABLE PAYS VARCHAR(100);
DECLARE VARIABLE ADRESSE1_LIV VARCHAR(100);
DECLARE VARIABLE ADRESSE2_LIV VARCHAR(100);
DECLARE VARIABLE ADRESSE3_LIV VARCHAR(100);
DECLARE VARIABLE CODEPOSTAL_LIV VARCHAR(5);
DECLARE VARIABLE VILLE_LIV VARCHAR(100);
DECLARE VARIABLE PAYS_LIV VARCHAR(100);
DECLARE VARIABLE CODE_COMPTA VARCHAR(8);
DECLARE VARIABLE COMPTE VARCHAR(8);
DECLARE VARIABLE NOM_TIERS VARCHAR(100);
DECLARE VARIABLE PRENOM_TIERS VARCHAR(100);
DECLARE VARIABLE SURNOM_TIERS VARCHAR(20);
DECLARE VARIABLE CODE_T_CIVILITE VARCHAR(20);
DECLARE VARIABLE CODE_T_ENTITE VARCHAR(20);
DECLARE VARIABLE TVA_I_COM_COMPL VARCHAR(50);
DECLARE VARIABLE CODE_C_PAIEMENT VARCHAR(20);
DECLARE VARIABLE LIB_C_PAIEMENT VARCHAR(255);
DECLARE VARIABLE REPORT_C_PAIEMENT INTEGER = 0;
DECLARE VARIABLE FIN_MOIS_C_PAIEMENT INTEGER = 0;
DECLARE VARIABLE ID_ADRESSE INTEGER = 0;
DECLARE VARIABLE ID_ADRESSE_LIV INTEGER = 0;
DECLARE VARIABLE ID_C_PAIEMENT INTEGER;
DECLARE VARIABLE CODE_TIERS VARCHAR(8);
DECLARE VARIABLE VERIF INTEGER = 0;
begin
compteur=COMPTEURDEPART;
select id_facture from ta_facture f where f.code_facture = :modelefacture into :id_fact_courant;
/*Récupérer les infos facture fixes*/
select code_c_paiement,lib_c_paiement,report_c_paiement,fin_mois_c_paiement from ta_infos_facture i
where i.id_facture =:id_fact_courant into :code_c_paiement,:lib_c_paiement,:report_c_paiement,:fin_mois_c_paiement;

for select t.id_tiers,t.code_tiers,t.code_compta,t.compte,t.nom_tiers,t.prenom_tiers,t.surnom_tiers
  ,t.code_t_civilite,t.code_t_entite,t.tva_i_com_compl from v_tiers T 
  into:id_tiers_tmp,code_tiers,code_compta,compte,nom_tiers,prenom_tiers,surnom_tiers
  ,code_t_civilite,code_t_entite,tva_i_com_compl
  do
  begin
      /*Récupérer toutes les infos du tiers*/
      /*Adresse facturation*/
      select id_adresse,va.adresse1_adresse,va.adresse2_adresse,va.adresse3_adresse,va.codepostal_adresse
      ,va.ville_adresse,va.pays_adresse from V_ADRESSE Va where Va.id_tiers = :id_tiers_tmp and va.code_t_adr='FACT'
      into :verif,adresse1,adresse2,adresse3,codepostal,ville,pays;
      if (:verif=0 or (:verif is null) ) then
          begin
              select va.adresse1_adresse,va.adresse2_adresse,va.adresse3_adresse,va.codepostal_adresse
              ,va.ville_adresse,va.pays_adresse from V_ADRESSE Va where Va.id_tiers = :id_tiers_tmp
              order by id_adresse into :adresse1,adresse2,adresse3,codepostal,ville,pays;
          end
if (:adresse1 is null) then adresse1='';
if (:adresse2 is null) then adresse2='';
if (:adresse3 is null) then adresse3='';
if (:codepostal is null) then codepostal='';
if (:ville is null) then ville='';
if (:pays is null) then pays='';

        verif=0;
      /*Adresse livraison*/
      select id_adresse,va.adresse1_adresse,va.adresse2_adresse,va.adresse3_adresse,va.codepostal_adresse
      ,va.ville_adresse,va.pays_adresse from V_ADRESSE Va where Va.id_tiers = :id_tiers_tmp and va.code_t_adr='LIV'
      into :verif,adresse1_liv,adresse2_liv,adresse3_liv,codepostal_liv,ville_liv,pays_liv;
      if (:verif=0 or (:verif is null) ) then
      begin
         adresse1_liv=:adresse1;
         adresse2_liv=:adresse2;
         adresse3_liv=:adresse3;
         codepostal_liv=:codepostal;
         ville_liv=:ville;
         pays_liv=:pays;
      end
if (:adresse1_liv is null) then adresse1_liv='';
if (:adresse2_liv is null) then adresse2_liv='';
if (:adresse3_liv is null) then adresse3_liv='';
if (:codepostal_liv is null) then codepostal_liv='';
if (:ville_liv is null) then ville_liv='';
if (:pays_liv is null) then pays_liv='';

        CODEFACTURE_TMP=cast(:compteur as varchar(5));
        while  (strlen(:CODEFACTURE_TMP)<5)  do
        begin
          CODEFACTURE_TMP='0'||:CODEFACTURE_TMP;
        end
        CODEFACTURE_TMP= 'FV08'||:CODEFACTURE_TMP;

     execute procedure recup_lignes_facture(:modelefacture) returning_values :id_fact_courant;

    update ta_l_facture_temp set id_l_facture =GEN_ID(num_id_l_facture,1) ;
    update ta_l_facture_temp set id_facture =0 ;

    id_fact_courant=GEN_ID(num_id_facture,1) ;
    insert into ta_facture  (id_facture,code_facture,date_facture,date_ech_facture,
    date_liv_facture,libelle_facture,id_adresse,id_adresse_liv,id_tiers,id_t_paiement
    ,id_c_paiement,regle_facture,rem_ht_facture,tx_rem_ht_facture,rem_ttc_facture,tx_rem_ttc_facture
    ,nb_e_facture,ttc,export)select :id_fact_courant,:CODEFACTURE_TMP,f2.date_facture,f2.date_ech_facture,
    f2.date_liv_facture,f2.libelle_facture,:id_adresse,:id_adresse_liv,:id_tiers_tmp,f2.id_t_paiement
    ,f2.id_c_paiement,f2.regle_facture,f2.rem_ht_facture,f2.tx_rem_ht_facture,f2.rem_ttc_facture,f2.tx_rem_ttc_facture
    ,f2.nb_e_facture,f2.ttc,f2.export from ta_facture f2 where f2.code_facture = :modelefacture;


    insert into ta_infos_facture  (id_infos_facture,id_facture,adresse1,adresse2
    ,adresse3,codepostal,ville,pays,adresse1_liv,adresse2_liv,adresse3_liv,codepostal_liv,
    ville_liv,pays_liv,code_compta,compte,nom_tiers,prenom_tiers,surnom_tiers,code_t_civilite,
    code_t_entite,tva_i_com_compl,code_c_paiement,lib_c_paiement,report_c_paiement,fin_mois_c_paiement)
    values(GEN_ID(num_id_infos_facture,1),:id_fact_courant,:adresse1,:adresse2
    ,:adresse3,:codepostal,:ville,:pays,:adresse1_liv,:adresse2_liv,:adresse3_liv,:codepostal_liv,
    :ville_liv,:pays_liv,:code_compta,:compte,:nom_tiers,:prenom_tiers,:surnom_tiers,:code_t_civilite,
    :code_t_entite,:tva_i_com_compl,:code_c_paiement,:lib_c_paiement,:report_c_paiement,:fin_mois_c_paiement);
    nb_facture = :nb_facture+1;
    compteur = compteur +1;
  end
nb_facture = :nb_facture;
suspend;
end
^



CREATE or ALTER  PROCEDURE GESTION_DROP_PROCEDURE1
as    
DECLARE VARIABLE QUERY VARCHAR(500);
BEGIN
query ='';
IF (exists( SELECT a.RDB$PROCEDURE_NAME
FROM RDB$PROCEDURES a
where a.RDB$PROCEDURE_NAME ='DISPATCHER_TVA_SUR_CODETVA')) THEN
  begin
    query = 'DROP PROCEDURE DISPATCHER_TVA_SUR_CODETVA';
    EXECUTE STATEMENT :query;
  end
END
^
EXECUTE PROCEDURE GESTION_DROP_PROCEDURE1
^
drop procedure GESTION_DROP_PROCEDURE1
^

CREATE or ALTER  PROCEDURE GESTION_DROP_PROCEDURE2
as    
DECLARE VARIABLE QUERY VARCHAR(500);
BEGIN
query ='';
IF (exists( SELECT a.RDB$PROCEDURE_NAME
FROM RDB$PROCEDURES a
where a.RDB$PROCEDURE_NAME ='DISPATCHER_TVA')) THEN
  begin
    query = 'DROP PROCEDURE DISPATCHER_TVA';
    EXECUTE STATEMENT :query;
  end
END
^
EXECUTE PROCEDURE GESTION_DROP_PROCEDURE2
^
drop procedure GESTION_DROP_PROCEDURE2
^


CREATE or ALTER  PROCEDURE GESTION_DROP_PROCEDURE3
as    
DECLARE VARIABLE QUERY VARCHAR(500);
BEGIN
query ='';
IF (exists( SELECT a.RDB$PROCEDURE_NAME
FROM RDB$PROCEDURES a
where a.RDB$PROCEDURE_NAME ='CALCUL_TVA_SUR_CODETVA')) THEN
  begin
    query = 'DROP PROCEDURE CALCUL_TVA_SUR_CODETVA';
    EXECUTE STATEMENT :query;
  end
END
^
EXECUTE PROCEDURE GESTION_DROP_PROCEDURE3
^
drop procedure GESTION_DROP_PROCEDURE3
^

CREATE or ALTER  PROCEDURE GESTION_DROP_PROCEDURE4
as    
DECLARE VARIABLE QUERY VARCHAR(500);
BEGIN
query ='';
IF (exists( SELECT a.RDB$PROCEDURE_NAME
FROM RDB$PROCEDURES a
where a.RDB$PROCEDURE_NAME ='P_TIERS')) THEN
  begin
    query = 'DROP PROCEDURE P_TIERS';
    EXECUTE STATEMENT :query;
  end
END
^
EXECUTE PROCEDURE GESTION_DROP_PROCEDURE4
^
drop procedure GESTION_DROP_PROCEDURE4
^

CREATE or ALTER  PROCEDURE GESTION_DROP_PROCEDURE5
as    
DECLARE VARIABLE QUERY VARCHAR(500);
BEGIN
query ='';
IF (exists( SELECT a.RDB$PROCEDURE_NAME
FROM RDB$PROCEDURES a
where a.RDB$PROCEDURE_NAME ='TRIM')) THEN
  begin
    query = 'DROP PROCEDURE "TRIM"';
    EXECUTE STATEMENT :query;
  end
END
^
EXECUTE PROCEDURE GESTION_DROP_PROCEDURE5
^
drop procedure GESTION_DROP_PROCEDURE5
^

CREATE or ALTER  PROCEDURE GESTION_DROP_PROCEDURE6
as    
DECLARE VARIABLE QUERY VARCHAR(500);
BEGIN
query ='';
IF (exists( SELECT a.RDB$PROCEDURE_NAME
FROM RDB$PROCEDURES a
where a.RDB$PROCEDURE_NAME ='VIDE_TA_L_FACTURE_TEMP')) THEN
  begin
    query = 'DROP PROCEDURE VIDE_TA_L_FACTURE_TEMP';
    EXECUTE STATEMENT :query;
  end
END
^
EXECUTE PROCEDURE GESTION_DROP_PROCEDURE6
^
drop procedure GESTION_DROP_PROCEDURE6
^

CREATE or ALTER  PROCEDURE GESTION_DROP_PROCEDURE7
as    
DECLARE VARIABLE QUERY VARCHAR(500);
BEGIN
query ='';
IF (exists( SELECT a.RDB$PROCEDURE_NAME
FROM RDB$PROCEDURES a
where a.RDB$PROCEDURE_NAME ='GENERECODEFACTURE')) THEN
  begin
    query = 'DROP PROCEDURE GENERECODEFACTURE';
    EXECUTE STATEMENT :query;
  end
END
^
EXECUTE PROCEDURE GESTION_DROP_PROCEDURE7
^
drop procedure GESTION_DROP_PROCEDURE7
^

CREATE or ALTER  PROCEDURE GESTION_INSERTION_VALEUR1
as    
DECLARE VARIABLE QUERY VARCHAR(2000);
BEGIN
query ='';
IF (not exists( SELECT CODE_REQUETE
FROM TA_VISUALISATION
where CODE_REQUETE ='article')) THEN
  begin
    query = 'INSERT INTO TA_VISUALISATION (ID, CODE_REQUETE, REQUETE, CHAMPS, TITRE_CHAMPS, TAILLE_CHAMPS, MODULE, IMPRESSION, IDENTIFIANT, ID_EDITEUR, ID_PLUGIN, GROUPBY) VALUES (GEN_ID(NUM_ID_visualisation,1), ''article'', ''select CODE_ARTICLE,LIBELLEC_ARTICLE,NUMCPT_ARTICLE,DIVERS_ARTICLE,COMMENTAIRE_ARTICLE,STOCK_MIN_ARTICLE,PRIX_PRIX,CODE_UNITE,CODE_FAMILLE,CODE_TVA,TAUX_TVA,NUMCPT_TVA,CODE_T_TVA  from v_article'', ''CODE_ARTICLE;LIBELLEC_ARTICLE;NUMCPT_ARTICLE;DIVERS_ARTICLE;COMMENTAIRE_ARTICLE;STOCK_MIN_ARTICLE;PRIX_PRIX;CODE_UNITE;CODE_FAMILLE;CODE_TVA;TAUX_TVA;NUMCPT_TVA;CODE_T_TVA'', ''CODE;LIBELLE;COMPTE;DIVERS;COMMENTAIRE;STOCK MINI;PRIX;UNITE;FAMILLE;CODE TVA;TAUX TVA;COMPTE TVA;TYPE TVA'', ''50;100;50;100;100;20;50;20;50;50;50;50;50'', ''article'', ''/report/article.rptdesign'', ''CODE_ARTICLE'', ''fr.legrain.articles.editor.EditorArticle'', ''Articles'', NULL)';
    EXECUTE STATEMENT :query;
  end
END
^
EXECUTE PROCEDURE GESTION_INSERTION_VALEUR1
^
drop procedure GESTION_INSERTION_VALEUR1
^


CREATE or ALTER  PROCEDURE GESTION_INSERTION_VALEUR2
as    
DECLARE VARIABLE QUERY VARCHAR(2000);
BEGIN
query ='';
IF (not exists( SELECT CODE_REQUETE
FROM TA_VISUALISATION
where CODE_REQUETE ='Factures exportées')) THEN
  begin
    query = 'INSERT INTO TA_VISUALISATION (ID, CODE_REQUETE, REQUETE, CHAMPS, TITRE_CHAMPS, TAILLE_CHAMPS, MODULE, IMPRESSION, IDENTIFIANT, ID_EDITEUR, ID_PLUGIN, GROUPBY) VALUES (GEN_ID(NUM_ID_visualisation,1), ''Factures exportées'', ''select CODE_FACTURE,DATE_FACTURE,CODE_TIERS,NOM_TIERS,(MTNETTTC)as MTNETTTC ,EXPORT FROM  V_facture , calcul_total_direct(''''facture'''',id_facture)'', ''CODE_FACTURE;DATE_FACTURE;CODE_TIERS;NOM_TIERS;MTNETTTC;EXPORT'', ''CODE;DATE FACTURE;CODE TIERS;NOM TIERS;NET TTC;EXPORTEE'', ''100;100;100;100;200;100;100;100'', ''facture'', ''/report/visu_facture.rptdesign'', ''CODE_FACTURE'', ''fr.legrain.editor.facture.swt.multi'', ''Facture'', NULL)';
    EXECUTE STATEMENT :query;
  end
END
^
EXECUTE PROCEDURE GESTION_INSERTION_VALEUR2
^
drop procedure GESTION_INSERTION_VALEUR2
^



CREATE or ALTER  PROCEDURE GESTION_INSERTION_VALEUR4
as    
DECLARE VARIABLE QUERY VARCHAR(2000);
BEGIN
query ='';
IF (not exists( SELECT CODE_REQUETE
FROM TA_VISUALISATION
where CODE_REQUETE ='tiers')) THEN
  begin
    query = 'INSERT INTO TA_VISUALISATION (ID, CODE_REQUETE, REQUETE, CHAMPS, TITRE_CHAMPS,TAILLE_CHAMPS, 
    MODULE, IMPRESSION, IDENTIFIANT, ID_EDITEUR, ID_PLUGIN, GROUPBY) VALUES(GEN_ID(NUM_ID_visualisation,1), ''tiers'',
     ''select CODE_TIERS,CODE_COMPTA,COMPTE,NOM_TIERS,ACTIF_TIERS,TTC_TIERS,CODE_T_CIVILITE,CODE_ENTREPRISE,
     CODE_T_TIERS,CODE_BANQUE,CODE_T_ENTITE from v_tiers'', ''CODE_TIERS;CODE_COMPTA;COMPTE;NOM_TIERS;ACTIF_TIERS;TTC_TIERS;
     CODE_T_CIVILITE;CODE_ENTREPRISE;CODE_T_TIERS;CODE_BANQUE;CODE_T_ENTITE'', ''CODE_TIERS;CODE_COMPTA;COMPTE;NOM_TIERS;
     ACTIF_TIERS;TTC_TIERS;CODE_T_CIVILITE;CODE_ENTREPRISE;CODE_T_TIERS;CODE_BANQUE;CODE_T_ENTITE'', 
     ''100;50;50;100;30;30;50;50;50;50;50'', ''tiers'', ''/report/tiers.rptdesign'', ''CODE_TIERS'', 
     ''fr.legrain.tiers.editor.EditorTiers'', ''Tiers'', NULL)';
    EXECUTE STATEMENT :query;
  end
END
^
EXECUTE PROCEDURE GESTION_INSERTION_VALEUR4
^
drop procedure GESTION_INSERTION_VALEUR4
^


CREATE or ALTER  PROCEDURE AUTORISE_MODIF_GENERE (
    NOMTABLE VARCHAR(255),
    NOMCHAMP VARCHAR(255),
    VALEUR VARCHAR(255),
    VERIF_CONNECTION INTEGER)
RETURNS (
    RETOUR INTEGER)
AS
begin
  select count(*) from ta_modif modif where upper(modif.table_modif) = upper(:nomtable)
  and upper(modif.champ_modif) =upper(:nomchamp)
  and  upper(modif.valeur_modif) =upper(:valeur)  into :retour;
  if ((:retour>0) and (:verif_connection=1)) then
  begin
       select count(*) from ta_modif modif where upper(modif.table_modif) = upper(:nomtable)
        and upper(modif.champ_modif) =upper(:nomchamp)
        and  upper(modif.valeur_modif) =upper(:valeur) and modif.ip_acces<>current_connection into :retour;
  end
  suspend;
end
^

CREATE or ALTER  PROCEDURE RECORD_MODIFIABLE (
    NOM_TABLE VARCHAR(100),
    IDTABLE INTEGER)
RETURNS (
    NB INTEGER)
AS
begin
if (upper(:NOM_TABLE) = upper('ta_adresse'))   then
    begin
        nb=0;
        select count(*) from ta_facture doc where doc.ID_ADRESSE = :idtable into nb;
        if (:nb<=0)then
           select count(*) from ta_facture doc where doc.ID_ADRESSE_LIV = :idtable into nb;
        if (:nb<=0)then
            select count(*) from ta_devis doc where doc.ID_ADRESSE = :idtable into nb;
        if (:nb<=0)then
           select count(*) from ta_devis doc where doc.ID_ADRESSE_LIV = :idtable into nb;
        if (:nb<=0)then
            select count(*) from ta_bonliv doc where doc.ID_ADRESSE = :idtable into nb;
        if (:nb<=0)then
           select count(*) from ta_bonliv  doc where doc.ID_ADRESSE_LIV = :idtable into nb;
        suspend;
    end
else
if (upper(:NOM_TABLE) = upper('ta_tiers'))   then
    begin
        nb=0;
        select count(*) from ta_facture doc where doc.id_tiers = :idtable into :nb  ;
        if (:nb<=0)then
            select count(*) from ta_devis doc where doc.id_tiers = :idtable into nb  ;
        /*if (:nb<=0)then
            select count(*) from ta_boncde doc where doc.id_tiers = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_avoir doc where doc.id_tiers = :idtable into nb  ;*/
        if (:nb<=0)then
            select count(*) from ta_bonliv doc where doc.id_tiers = :idtable into nb  ;

        suspend;
    end
else
if (upper(:NOM_TABLE) = upper('ta_article'))   then
    begin
        nb=0;
        select count(*) from ta_L_facture Ldoc where Ldoc.id_ARTICLE = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_l_devis Ldoc where Ldoc.id_ARTICLE = :idtable into nb  ;
        /*
        if (:nb<=0)then
            select count(*) from ta_l_boncde Ldoc where Ldoc.id_ARTICLE = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_l_avoir Ldoc where Ldoc.id_ARTICLE = :idtable into nb  ;*/
        if (:nb<=0)then
            select count(*) from ta_l_bonliv Ldoc where Ldoc.id_ARTICLE = :idtable into nb  ;
        suspend;
    end
else
if (upper(:NOM_TABLE) = upper('ta_T_PAIEMENT'))   then
    begin
        nb=0;
        select count(*) from ta_facture doc where doc.ID_T_PAIEMENT = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_devis doc where doc.ID_T_PAIEMENT = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_bonliv doc where doc.ID_T_PAIEMENT = :idtable into nb  ;
        suspend;
    end
else
if (upper(:NOM_TABLE) = upper('ta_C_PAIEMENT'))   then
    begin
        nb=0;
        select count(*) from ta_facture doc where doc.ID_C_PAIEMENT = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_devis doc where doc.ID_C_PAIEMENT = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_bonliv doc where doc.ID_C_PAIEMENT = :idtable into nb  ;
        suspend;
    end
if (upper(:NOM_TABLE) = upper('ta_T_civilite'))   then
    begin
        nb=0;
        select count(*) from ta_tiers tiers where tiers.id_t_civilite = :idtable into nb  ;
        suspend;
    end
if (upper(:NOM_TABLE) = upper('ta_Unite'))   then
    begin
        nb=0;
        select count(*) from ta_Prix prix where prix.id_unite = :idtable into nb  ;
        suspend;
    end
if (upper(:NOM_TABLE) = upper('ta_T_Ligne'))   then
    begin
        nb=0;
        select count(*) from ta_l_facture ldoc where ldoc.id_t_ligne = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_l_devis ldoc where ldoc.id_t_ligne = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_l_bonliv ldoc where ldoc.id_t_ligne = :idtable into nb  ;
        suspend;
    end
if (upper(:NOM_TABLE) = upper('ta_famille'))   then
    begin
        nb=0;
        select count(*) from ta_article article where article.id_famille = :idtable into nb  ;
        suspend;
    end
if (upper(:NOM_TABLE) = upper('ta_banque'))   then
    begin
        nb=0;
        select count(*) from ta_tiers tiers where tiers.id_i_banque = :idtable into nb  ;
        suspend;
    end
if (upper(:NOM_TABLE) = upper('ta_TVA'))   then
    begin
        nb=0;
        select count(*) from ta_ARTICLE article where article.id_tva = :idtable into nb  ;
        suspend;
    end
if (upper(:NOM_TABLE) = upper('ta_T_TVA'))   then
    begin
        nb=0;
        select count(*) from ta_Article article where article.id_T_Tva = :idtable into nb  ;
        suspend;
    end
if (upper(:NOM_TABLE) = upper('ta_T_Entite'))   then
    begin
        nb=0;
        select count(*) from ta_tiers tiers where tiers.id_t_entite = :idtable into nb  ;
        suspend;
    end
if (upper(:NOM_TABLE) = upper('ta_Entreprise'))   then
    begin
        nb=0;
        select count(*) from ta_tiers tiers where tiers.id_entreprise = :idtable into nb  ;
        suspend;
    end
if (upper(:NOM_TABLE) = upper('ta_T_adr'))   then
    begin
        nb=0;
        select count(*) from ta_r_adr_t_adr adresse where adresse.id_t_adr = :idtable into nb  ;
        suspend;
    end            
if (upper(:NOM_TABLE) = upper('ta_t_tel'))   then
    begin
        nb=0;
        select count(*) from ta_r_tel_t_tel tel where tel.id_t_tel = :idtable into nb  ;
        suspend;
    end
if (upper(:NOM_TABLE) = upper('ta_t_tiers'))   then
    begin
        nb=0;
        select count(*) from ta_tiers tiers where tiers.id_t_tiers = :idtable into nb  ;
        suspend;
    end
suspend;
end
^

CREATE or ALTER  PROCEDURE AUTORISE_MODIF_GENERE (
    NOMTABLE VARCHAR(255),
    NOMCHAMP VARCHAR(255),
    VALEUR VARCHAR(255),
    VERIF_CONNECTION INTEGER)
RETURNS (
    RETOUR INTEGER)
AS
DECLARE VARIABLE QUERY VARCHAR(1000);
begin
      query='select count(*) from '|| :nomtable ||' where upper('|| :nomchamp || ') = upper('''||:valeur||''')';
      execute statement :query into :retour;

 if(retour <= 0)then
  begin
  select count(*) from ta_modif modif where upper(modif.table_modif) = upper(:nomtable)
  and upper(modif.champ_modif) =upper(:nomchamp)
  and  upper(modif.valeur_modif) =upper(:valeur)  into :retour;
  if ((:retour>0) and (:verif_connection=1)) then
  begin
       select count(*) from ta_modif modif where upper(modif.table_modif) = upper(:nomtable)
        and upper(modif.champ_modif) =upper(:nomchamp)
        and  upper(modif.valeur_modif) =upper(:valeur) and modif.ip_acces<>current_connection into :retour;
  end
  end
  suspend;
end
^



update RDB$RELATION_FIELDS set
RDB$FIELD_SOURCE = 'DLIB_COMMENTAIRE'
where (RDB$FIELD_NAME = 'COMMENTAIRE_ARTICLE') and
(RDB$RELATION_NAME = 'TA_ARTICLE')
^

update RDB$RELATION_FIELDS set
RDB$FIELD_SOURCE = 'DLIB_COMMENTAIRE'
where (RDB$FIELD_NAME = 'COMMENTAIRE') and
(RDB$RELATION_NAME = 'TA_BONLIV')
^
update RDB$RELATION_FIELDS set
RDB$FIELD_SOURCE = 'DLIB_COMMENTAIRE'
where (RDB$FIELD_NAME = 'LIBL_COMMENTAIRE') and
(RDB$RELATION_NAME = 'TA_COMMENTAIRE')
^

update RDB$RELATION_FIELDS set
RDB$FIELD_SOURCE = 'DLIB_COMMENTAIRE'
where (RDB$FIELD_NAME = 'COMMENTAIRE') and
(RDB$RELATION_NAME = 'TA_DEVIS')
^

update RDB$RELATION_FIELDS set
RDB$FIELD_SOURCE = 'DLIB_COMMENTAIRE'
where (RDB$FIELD_NAME = 'COMMENTAIRE') and
(RDB$RELATION_NAME = 'TA_FACTURE')
^

update RDB$RELATION_FIELDS set
RDB$FIELD_SOURCE = 'DLIB255NN'
where (RDB$FIELD_NAME = 'CODE_REQUETE') and
(RDB$RELATION_NAME = 'TA_VISUALISATION')
^
update RDB$RELATION_FIELDS set
RDB$FIELD_SOURCE = 'DLIB_COMMENTAIRE'
where (RDB$FIELD_NAME = 'REQUETE') and
(RDB$RELATION_NAME = 'TA_VISUALISATION')
^
update RDB$RELATION_FIELDS set
RDB$FIELD_SOURCE = 'DLIB_COMMENTAIRE'
where (RDB$FIELD_NAME = 'CHAMPS') and
(RDB$RELATION_NAME = 'TA_VISUALISATION')
^
update RDB$RELATION_FIELDS set
RDB$FIELD_SOURCE = 'DLIB_COMMENTAIRE'
where (RDB$FIELD_NAME = 'TITRE_CHAMPS') and
(RDB$RELATION_NAME = 'TA_VISUALISATION')
^
update RDB$RELATION_FIELDS set
RDB$FIELD_SOURCE = 'DLIB255'
where (RDB$FIELD_NAME = 'TAILLE_CHAMPS') and
(RDB$RELATION_NAME = 'TA_VISUALISATION')
^
update RDB$RELATION_FIELDS set
RDB$FIELD_SOURCE = 'DLIB255'
where (RDB$FIELD_NAME = 'MODULE') and
(RDB$RELATION_NAME = 'TA_VISUALISATION')
^
update RDB$RELATION_FIELDS set
RDB$FIELD_SOURCE = 'DLIB255'
where (RDB$FIELD_NAME = 'IMPRESSION') and
(RDB$RELATION_NAME = 'TA_VISUALISATION')
^
update RDB$RELATION_FIELDS set
RDB$FIELD_SOURCE = 'DLIB255NN'
where (RDB$FIELD_NAME = 'IDENTIFIANT') and
(RDB$RELATION_NAME = 'TA_VISUALISATION')
^
update RDB$RELATION_FIELDS set
RDB$FIELD_SOURCE = 'DLIB255NN'
where (RDB$FIELD_NAME = 'ID_EDITEUR') and
(RDB$RELATION_NAME = 'TA_VISUALISATION')
^
update RDB$RELATION_FIELDS set
RDB$FIELD_SOURCE = 'DLIB255NN'
where (RDB$FIELD_NAME = 'ID_PLUGIN') and
(RDB$RELATION_NAME = 'TA_VISUALISATION')
^
update RDB$RELATION_FIELDS set
RDB$FIELD_SOURCE = 'DLIB_COMMENTAIRE'
where (RDB$FIELD_NAME = 'GROUPBY') and
(RDB$RELATION_NAME = 'TA_VISUALISATION')
^

CREATE or ALTER  PROCEDURE GESTION_ALTER_TABLE1
as    
DECLARE VARIABLE QUERY VARCHAR(255);
BEGIN
query ='';
IF (not exists( SELECT a.RDB$FIELD_NAME, a.RDB$RELATION_NAME FROM RDB$RELATION_FIELDS a
where a.RDB$RELATION_NAME = 'TA_VERSION' and  a.RDB$FIELD_NAME ='DATE_VERSION')) THEN
  begin
    query = 'ALTER TABLE TA_VERSION  ADD DATE_VERSION TIMESTAMP,alter DATE_VERSION position 4';
    EXECUTE STATEMENT :query;
  end
END
^
EXECUTE PROCEDURE GESTION_ALTER_TABLE1
^
drop procedure GESTION_ALTER_TABLE1
^



update RDB$RELATION_FIELDS set
RDB$FIELD_SOURCE = 'DLIB_COMMENTAIRE'
where (RDB$FIELD_NAME = 'COMMENTAIRE_ARTICLE') and
(RDB$RELATION_NAME = 'TA_ARTICLE')
^

update RDB$RELATION_FIELDS set
RDB$FIELD_SOURCE = 'DLIB_COMMENTAIRE'
where (RDB$FIELD_NAME = 'COMMENTAIRE') and
(RDB$RELATION_NAME = 'TA_BONLIV')
^
update RDB$RELATION_FIELDS set
RDB$FIELD_SOURCE = 'DLIB_COMMENTAIRE'
where (RDB$FIELD_NAME = 'LIBL_COMMENTAIRE') and
(RDB$RELATION_NAME = 'TA_COMMENTAIRE')
^

update RDB$RELATION_FIELDS set
RDB$FIELD_SOURCE = 'DLIB_COMMENTAIRE'
where (RDB$FIELD_NAME = 'COMMENTAIRE') and
(RDB$RELATION_NAME = 'TA_DEVIS')
^

update RDB$RELATION_FIELDS set
RDB$FIELD_SOURCE = 'DLIB_COMMENTAIRE'
where (RDB$FIELD_NAME = 'COMMENTAIRE') and
(RDB$RELATION_NAME = 'TA_FACTURE')
^

update RDB$RELATION_FIELDS set
RDB$FIELD_SOURCE = 'DLIB255NN'
where (RDB$FIELD_NAME = 'CODE_REQUETE') and
(RDB$RELATION_NAME = 'TA_VISUALISATION')
^
update RDB$RELATION_FIELDS set
RDB$FIELD_SOURCE = 'DLIB_COMMENTAIRE'
where (RDB$FIELD_NAME = 'REQUETE') and
(RDB$RELATION_NAME = 'TA_VISUALISATION')
^
update RDB$RELATION_FIELDS set
RDB$FIELD_SOURCE = 'DLIB_COMMENTAIRE'
where (RDB$FIELD_NAME = 'CHAMPS') and
(RDB$RELATION_NAME = 'TA_VISUALISATION')
^
update RDB$RELATION_FIELDS set
RDB$FIELD_SOURCE = 'DLIB_COMMENTAIRE'
where (RDB$FIELD_NAME = 'TITRE_CHAMPS') and
(RDB$RELATION_NAME = 'TA_VISUALISATION')
^
update RDB$RELATION_FIELDS set
RDB$FIELD_SOURCE = 'DLIB255'
where (RDB$FIELD_NAME = 'TAILLE_CHAMPS') and
(RDB$RELATION_NAME = 'TA_VISUALISATION')
^
update RDB$RELATION_FIELDS set
RDB$FIELD_SOURCE = 'DLIB255'
where (RDB$FIELD_NAME = 'MODULE') and
(RDB$RELATION_NAME = 'TA_VISUALISATION')
^
update RDB$RELATION_FIELDS set
RDB$FIELD_SOURCE = 'DLIB255'
where (RDB$FIELD_NAME = 'IMPRESSION') and
(RDB$RELATION_NAME = 'TA_VISUALISATION')
^
update RDB$RELATION_FIELDS set
RDB$FIELD_SOURCE = 'DLIB255NN'
where (RDB$FIELD_NAME = 'IDENTIFIANT') and
(RDB$RELATION_NAME = 'TA_VISUALISATION')
^
update RDB$RELATION_FIELDS set
RDB$FIELD_SOURCE = 'DLIB255NN'
where (RDB$FIELD_NAME = 'ID_EDITEUR') and
(RDB$RELATION_NAME = 'TA_VISUALISATION')
^
update RDB$RELATION_FIELDS set
RDB$FIELD_SOURCE = 'DLIB255NN'
where (RDB$FIELD_NAME = 'ID_PLUGIN') and
(RDB$RELATION_NAME = 'TA_VISUALISATION')
^
update RDB$RELATION_FIELDS set
RDB$FIELD_SOURCE = 'DLIB_COMMENTAIRE'
where (RDB$FIELD_NAME = 'GROUPBY') and
(RDB$RELATION_NAME = 'TA_VISUALISATION')
^

CREATE or ALTER  PROCEDURE GESTION_ALTER_TABLE1
as    
DECLARE VARIABLE QUERY VARCHAR(255);
BEGIN
query ='';
IF (not exists( SELECT a.RDB$FIELD_NAME, a.RDB$RELATION_NAME FROM RDB$RELATION_FIELDS a
where a.RDB$RELATION_NAME = 'TA_VERSION' and  a.RDB$FIELD_NAME ='DATE_VERSION')) THEN
  begin
    query = 'ALTER TABLE TA_VERSION  ADD DATE_VERSION TIMESTAMP,alter DATE_VERSION position 4';
    EXECUTE STATEMENT :query;
  end
END
^
EXECUTE PROCEDURE GESTION_ALTER_TABLE1
^
drop procedure GESTION_ALTER_TABLE1
^
