CREATE or ALTER  PROCEDURE GESTION_CREATION_1
as    
DECLARE VARIABLE QUERY VARCHAR(255);
BEGIN
query = 'ALTER DOMAIN DLGR_2 TYPE VARCHAR(2)';
IF (not exists( select RDB$FIELD_NAME from RDB$FIELDS where RDB$FIELD_NAME = 'DLGR_2')) THEN
query = 'CREATE DOMAIN DLGR_2 AS VARCHAR(2) NOT NULL';

EXECUTE STATEMENT :query;

END
^

EXECUTE PROCEDURE GESTION_CREATION_1 
^
drop procedure GESTION_CREATION_1
^


CREATE or ALTER  PROCEDURE GESTION_CREATION_2
AS
DECLARE VARIABLE QUERY VARCHAR(255);
BEGIN
query = 'update RDB$RELATION_FIELDS set
RDB$FIELD_SOURCE = ''DLIB8''
where (RDB$FIELD_NAME = ''COMPTE_L_FACTURE'') and
(RDB$RELATION_NAME = ''TA_L_FACTURE'')';
IF (not exists( select RDB$FIELD_NAME from RDB$RELATION_FIELDS
where RDB$RELATION_NAME = 'TA_L_FACTURE' and
RDB$FIELD_NAME = 'COMPTE_L_FACTURE')) THEN
query = 'ALTER TABLE TA_L_FACTURE ADD "COMPTE_L_FACTURE" DLIB8';

EXECUTE STATEMENT :query;

END
^
EXECUTE PROCEDURE GESTION_CREATION_2 
^
drop procedure GESTION_CREATION_2
^


CREATE or ALTER  PROCEDURE GESTION_CREATION_3
AS
DECLARE VARIABLE QUERY VARCHAR(1000);
BEGIN
query = 'update RDB$RELATION_FIELDS set
RDB$FIELD_SOURCE = ''DLIB8''
where (RDB$FIELD_NAME = ''COMPTE_L_FACTURE'') and
(RDB$RELATION_NAME = ''TA_L_FACTURE_TEMP'')';
IF (not exists( select RDB$FIELD_NAME from RDB$RELATION_FIELDS
where RDB$RELATION_NAME = 'TA_L_FACTURE_TEMP' and
RDB$FIELD_NAME = 'COMPTE_L_FACTURE')) THEN
query = 'ALTER TABLE TA_L_FACTURE_TEMP ADD "COMPTE_L_FACTURE" DLIB8';

EXECUTE STATEMENT :query;

END
^
EXECUTE PROCEDURE GESTION_CREATION_3 
^
drop procedure GESTION_CREATION_3
^


CREATE or ALTER  PROCEDURE GESTION_CREATION_4
AS
DECLARE VARIABLE QUERY VARCHAR(1000);
DECLARE VARIABLE valeur VARCHAR(2);
BEGIN
select substring(CODEXO_INFO_ENTREPRISE from  strlen(CODEXO_INFO_ENTREPRISE)-1 for
strlen(CODEXO_INFO_ENTREPRISE)) from TA_INFO_ENTREPRISE into :valeur;
query = 'update TA_INFO_ENTREPRISE set CODEXO_INFO_ENTREPRISE = '':valeur''';

EXECUTE STATEMENT :query;

END
^
EXECUTE PROCEDURE GESTION_CREATION_4 
^
drop procedure GESTION_CREATION_4
^

update RDB$RELATION_FIELDS set
RDB$FIELD_SOURCE = 'DLGR_2'
where (RDB$FIELD_NAME = 'CODEXO_INFO_ENTREPRISE') and
(RDB$RELATION_NAME = 'TA_INFO_ENTREPRISE')
^


update RDB$RELATION_FIELDS set
RDB$FIELD_SOURCE = 'DLGR_2'
where (RDB$FIELD_NAME = 'CODEXO_INFO_ENTREPRISE') and
(RDB$RELATION_NAME = 'V_INFO_ENTREPRISE')
^



CREATE OR ALTER PROCEDURE RECUP_LIGNES_FACTURE (
    CODE_FACTURE_RECUP VARCHAR(50))
RETURNS (
    ID_FACTURE_RECUP INTEGER)
AS
DECLARE VARIABLE V_ID_L_FACTURE INTEGER;
DECLARE VARIABLE V_ID_FACTURE INTEGER;
DECLARE VARIABLE V_ID_T_LIGNE INTEGER;
DECLARE VARIABLE V_ID_ARTICLE INTEGER;
DECLARE VARIABLE V_NUM_LIGNE_L_FACTURE INTEGER;
DECLARE VARIABLE V_LIB_L_FACTURE VARCHAR(255) CHARACTER SET NONE;
DECLARE VARIABLE V_QTE_L_FACTURE NUMERIC(15,2);
DECLARE VARIABLE V_U1_L_FACTURE VARCHAR(20) CHARACTER SET NONE;
DECLARE VARIABLE V_U2_L_FACTURE VARCHAR(20) CHARACTER SET NONE;
DECLARE VARIABLE V_PRIX_U_L_FACTURE NUMERIC(15,2);
DECLARE VARIABLE V_TAUX_TVA_L_FACTURE NUMERIC(15,4);
DECLARE VARIABLE V_CODE_TVA_L_FACTURE VARCHAR(20) CHARACTER SET NONE;
DECLARE VARIABLE V_MT_HT_L_FACTURE NUMERIC(15,2);
DECLARE VARIABLE V_MT_TTC_L_FACTURE NUMERIC(15,2);
DECLARE VARIABLE V_REM_TX_L_FACTURE NUMERIC(15,2);
DECLARE VARIABLE V_REM_HT_L_FACTURE NUMERIC(15,2);
DECLARE VARIABLE V_CODE_T_TVA_L_FACTURE VARCHAR(1);
DECLARE VARIABLE V_COMPTE_L_FACTURE VARCHAR(8);
begin
execute procedure VIDE_TA_L_FACTURE_TEMP;

select ID_FACTURE from ta_facture where CODE_FACTURE = :CODE_FACTURE_RECUP into :id_facture_recup;

FOR select lfacture.id_l_facture,
   lfacture.id_facture,
    lfacture.id_t_ligne, lfacture.id_article, lfacture.num_ligne_l_facture,
lfacture.lib_l_facture, lfacture.qte_l_facture, lfacture.u1_l_facture, lfacture.u2_l_facture,
lfacture.prix_u_l_facture, lfacture.taux_tva_l_facture,lfacture.compte_l_facture, lfacture.code_tva_l_facture,lfacture.code_t_tva_l_facture,
 lfacture.mt_ht_l_facture,
lfacture.mt_ttc_l_facture, lfacture.rem_tx_l_facture, lfacture.rem_ht_l_facture from ta_l_facture lfacture
where lfacture.id_facture = :id_facture_recup
into
    :V_ID_L_FACTURE,
    :V_ID_FACTURE,
    :V_ID_T_LIGNE,
    :V_ID_ARTICLE,
    :V_NUM_LIGNE_L_FACTURE,
    :V_LIB_L_FACTURE,
    :V_QTE_L_FACTURE,
    :V_U1_L_FACTURE,
    :V_U2_L_FACTURE,
    :V_PRIX_U_L_FACTURE,
    :V_TAUX_TVA_L_FACTURE,
    :v_compte_l_facture,
    :V_CODE_TVA_L_FACTURE,
    :V_CODE_T_TVA_L_FACTURE,
    :V_MT_HT_L_FACTURE,
    :V_MT_TTC_L_FACTURE,
    :V_REM_TX_L_FACTURE,
    :V_REM_HT_L_FACTURE
do
  begin

     insert into ta_l_facture_temp (
    ID_L_FACTURE,
    ID_FACTURE,
    ID_T_LIGNE,
    ID_ARTICLE,
    NUM_LIGNE_L_FACTURE,
    LIB_L_FACTURE,
    QTE_L_FACTURE,
    U1_L_FACTURE,
    U2_L_FACTURE,
    PRIX_U_L_FACTURE,
    TAUX_TVA_L_FACTURE,
    compte_l_facture,
    CODE_TVA_L_FACTURE,
    CODE_T_TVA_L_FACTURE,
    MT_HT_L_FACTURE,
    MT_TTC_L_FACTURE,
    REM_TX_L_FACTURE,
    REM_HT_L_FACTURE,
    IP_ACCES
    ) values(
    :V_ID_L_FACTURE,
    :V_ID_FACTURE,
    :V_ID_T_LIGNE,
    :V_ID_ARTICLE,
    :V_NUM_LIGNE_L_FACTURE,
    :V_LIB_L_FACTURE,
    :V_QTE_L_FACTURE,
    :V_U1_L_FACTURE,
    :V_U2_L_FACTURE,
    :V_PRIX_U_L_FACTURE,
    :V_TAUX_TVA_L_FACTURE,
    :v_compte_l_facture,
    :V_CODE_TVA_L_FACTURE,
    :V_CODE_T_TVA_L_FACTURE,
    :V_MT_HT_L_FACTURE,
    :V_MT_TTC_L_FACTURE,
    :V_REM_TX_L_FACTURE,
    :V_REM_HT_L_FACTURE,
    Current_Connection) ;
    /*suspend; */
  end
  suspend;
end
^

CREATE OR ALTER PROCEDURE RECUP_LIGNES_EXPORTATION (
    ID_FACTURE_RECUP INTEGER,
    TTC INTEGER,
    TAUXREMISE NUMERIC(15,2))
RETURNS (
    ID_L_FACTURE INTEGER,
    ID_FACTURE INTEGER,
    ID_T_LIGNE INTEGER,
    ID_ARTICLE INTEGER,
    NUM_LIGNE_L_FACTURE INTEGER,
    LIB_L_FACTURE VARCHAR(255),
    QTE_L_FACTURE NUMERIC(15,2),
    U1_L_FACTURE VARCHAR(20),
    U2_L_FACTURE VARCHAR(20),
    PRIX_U_L_FACTURE NUMERIC(15,2),
    TAUX_TVA_L_FACTURE NUMERIC(15,4),
    CODE_TVA_L_FACTURE VARCHAR(20),
    CODE_T_TVA_L_FACTURE VARCHAR(1),
    MT_HT_L_FACTURE NUMERIC(15,2),
    MT_TTC_L_FACTURE NUMERIC(15,2),
    REM_TX_L_FACTURE NUMERIC(15,2),
    REM_HT_L_FACTURE NUMERIC(15,2),
    TVALIGNE NUMERIC(15,2),
    COMPTE_L_FACTURE VARCHAR(8))
AS
DECLARE VARIABLE CODE_TVA VARCHAR(20);
DECLARE VARIABLE TAUX NUMERIC(15,4);
DECLARE VARIABLE TOTAL_TVA NUMERIC(15,2);
DECLARE VARIABLE TOTALHT NUMERIC(15,2);
DECLARE VARIABLE NBLIGNE INTEGER;
DECLARE VARIABLE LIGNEPASSE INTEGER;
DECLARE VARIABLE TVA_LIGNE NUMERIC(15,2);
DECLARE VARIABLE TVATMP NUMERIC(15,2);
DECLARE VARIABLE TYPELIGNE INTEGER;
DECLARE VARIABLE TOTALTTC NUMERIC(15,2);
DECLARE VARIABLE TOTAL_TTC_REMISE NUMERIC(15,2);
DECLARE VARIABLE TOTAL_HT_REMISE NUMERIC(15,2);
begin
TYPELIGNE=0;
select typeL.id_t_ligne from  ta_t_ligne typeL where typeL.code_t_ligne = 'H' into :typeligne;
  FOR select calc.code_tva_recup, calc.taux_recup, calc.mt_tva_recup,calc.mt_ht,
  calc.mt_ttc,calc.mt_ht_remise,calc.mt_ttc_remise
  FROM  calcul_tva_ta_l_facture(:ID_FACTURE_RECUP,1)calc into
    :code_tva,:taux,:total_tva,:totalht,
    :totalttc,:total_ht_remise,:total_ttc_remise
  do
  begin
    select  count(*) from ta_l_facture lfacture where
    lfacture.id_facture = :id_facture_recup and
    lfacture.code_tva_l_facture = :CODE_TVA and lfacture.taux_tva_l_facture = :TAUX
    and lfacture.id_t_ligne = :typeligne
    into :NbLigne;

 lignepasse=1;
 TVATMP=0;
 TVATMP=:total_tva;

    FOR select lfacture2.id_l_facture,
    lfacture2.id_facture,
    lfacture2.id_t_ligne, lfacture2.id_article, lfacture2.num_ligne_l_facture,
    lfacture2.lib_l_facture, lfacture2.qte_l_facture, lfacture2.u1_l_facture, lfacture2.u2_l_facture,
    lfacture2.prix_u_l_facture, lfacture2.taux_tva_l_facture,lfacture2.compte_l_facture, lfacture2.code_tva_l_facture,lfacture2.code_t_tva_l_facture,
    lfacture2.mt_ht_l_facture,lfacture2.mt_ttc_l_facture, lfacture2.rem_tx_l_facture, lfacture2.rem_ht_l_facture
    from ta_l_facture lfacture2 where
        lfacture2.id_facture = :id_facture_recup and
        lfacture2.code_tva_l_facture = :CODE_TVA and lfacture2.taux_tva_l_facture = :TAUX
        and lfacture2.id_t_ligne = :typeligne 
         into
        :ID_L_FACTURE,
        :ID_FACTURE,
        :ID_T_LIGNE,
        :ID_ARTICLE,
        :NUM_LIGNE_L_FACTURE,
        :LIB_L_FACTURE,
        :QTE_L_FACTURE,
        :U1_L_FACTURE,
        :U2_L_FACTURE,
        :PRIX_U_L_FACTURE,
        :TAUX_TVA_L_FACTURE,
        :compte_l_facture,
        :CODE_TVA_L_FACTURE,
        :CODE_T_TVA_L_FACTURE,
        :MT_HT_L_FACTURE,
        :MT_TTC_L_FACTURE,
        :REM_TX_L_FACTURE,
        :REM_HT_L_FACTURE
    do
            begin
                 if (:ttc=1)then
                    begin
                       if (:totalttc=0 or (total_ttc_remise=0) ) then
                           begin
                             mt_ttc_l_facture=0;
                             tva_ligne =0;
                           end
                       else
                           begin
                               mt_ttc_l_facture = (:total_ttc_remise * :mt_ttc_l_facture) / :totalttc ;
                               if  (lignepasse>= :NbLigne) then
                                 tva_ligne = :TVATMP;
                               else
                                 tva_ligne = (:total_tva * :mt_ttc_l_facture) / :total_ttc_remise;
                           end
                       TVATMP =  :TVATMP -  :tva_ligne;
                       mt_ht_l_facture = :mt_ttc_l_facture - :tva_ligne ;
                       tvaligne = :tva_ligne;
                       lignepasse = :lignepasse + 1;
                       suspend;
                    end
                  else
                    begin
                       if (:totalht=0 or (:total_ht_remise=0) ) then
                         begin
                             mt_ht_l_facture =0;
                             tva_ligne =0;
                         end
                       else
                         begin
                            mt_ht_l_facture = (:total_ht_remise * :mt_ht_l_facture) / :totalht ;
                            if  (lignepasse>= :NbLigne) then
                              tva_ligne = :TVATMP;
                            else
                              tva_ligne = (:total_tva * :mt_ht_l_facture) / :total_ht_remise;
                         end
                       TVATMP =  :TVATMP -  :tva_ligne;

                       mt_ttc_l_facture = :mt_ht_l_facture + :tva_ligne;
                       tvaligne = :tva_ligne;
                       lignepasse = :lignepasse + 1;
                       suspend;
                    end
            end
  end
end
^


CREATE OR ALTER procedure EXPORT_EPICEA (
    CPT_ESCOMPTE_DEBIT VARCHAR(8),
    CPT_ESCOMPTE_CREDIT VARCHAR(8),
    FACTURE INTEGER,
    REEXPORT INTEGER)
RETURNS (
    NUM_PIECE INTEGER,
    NUM_LIGNE_PIECE INTEGER,
    TYPE_PIECE VARCHAR(1),
    CODE_PIECE VARCHAR(20),
    DATE_PIECE TIMESTAMP,
    NUM_CPT_LIGNE VARCHAR(8),
    LIBELLE_LIGNE VARCHAR(255),
    MT_DEBIT_LIGNE NUMERIC(18,2),
    MT_CREDIT_LIGNE NUMERIC(18,2),
    QTE_1 NUMERIC(18,2),
    QTE_2 NUMERIC(18,2),
    CODE_TVA VARCHAR(20),
    TAUX_TVA NUMERIC(18,2),
    MT_DEBIT_TVA NUMERIC(18,2),
    MT_CREDIT_TVA NUMERIC(18,2),
    DATE_ECHEANCE TIMESTAMP,
    CPT_COLLECTIF VARCHAR(8),
    NOM_TIERS VARCHAR(100),
    ADRESSE_1_TIERS VARCHAR(100),
    ADRESSE_2_TIERS VARCHAR(100),
    CODE_POSTAL_TIERS VARCHAR(5),
    VILLE_TIERS VARCHAR(100),
    EXIGIBILITE_TVA VARCHAR(1),
    DATE_LIVRAISON_LIGNE TIMESTAMP,
    TTC INTEGER,
    TX_REM_HT_FACTURE NUMERIC(15,2))
AS
DECLARE VARIABLE ID_FACT_COURANT INTEGER;
DECLARE VARIABLE ID_ART INTEGER;
DECLARE VARIABLE ESCOMPTE NUMERIC(15,2);
DECLARE VARIABLE EXPORT_COMPTA INTEGER;
begin
 num_piece = 0 ;
 for select
   f.id_facture,f.code_facture, f.date_facture, f.date_ech_facture, f.date_liv_facture, f.libelle_facture,
   infosF.code_compta,infosF.compte,  infosF.nom_tiers, infosF.adresse1, infosF.adresse2, infosF.codepostal,
   infosF.ville,
   f.ttc,f.tx_rem_ht_facture, f.export
   from v_facture f, v_infos_facture infosF
   where /*f.id_facture between :id_deb and :id_fin*/ f.id_facture = :facture  and infosF.id_facture=f.id_facture
   into  :id_fact_courant, :code_piece ,:date_piece ,:date_echeance ,:date_livraison_ligne ,:libelle_ligne ,
   :num_cpt_ligne ,:cpt_collectif,
   :nom_tiers ,:adresse_1_tiers ,:adresse_2_tiers ,:code_postal_tiers ,:ville_tiers,:ttc,:tx_rem_ht_facture, :export_compta
 do begin
   if (export_compta!=1 or reexport=1 ) then begin /* facture pas deja exportee ou reexportation */
     execute procedure recup_lignes_facture(:code_piece) returning_values :id_fact_courant;
     num_piece = num_piece+1;
     select calc.mt_ttc-calc.mtnetttc,calc.mtnetttc from CALCUL_TOTAL_DIRECT(:id_fact_courant) calc
     into :escompte,:mt_debit_ligne;
     num_cpt_ligne = '+'||num_cpt_ligne;
     num_ligne_piece = 1;
     type_piece = 'V';
     mt_credit_ligne = 0;
     qte_1 = 0;
     qte_2 = 0;
     code_tva ='';
     taux_tva = 0;
     mt_debit_tva = 0;
     mt_credit_tva = 0;
     exigibilite_tva = '';
     suspend;
     if (:escompte > 0) then
       begin
           num_cpt_ligne = :cpt_escompte_debit;
           num_ligne_piece = num_ligne_piece + 1;
           type_piece = 'V';
           libelle_ligne = 'Escompte '||libelle_ligne;
           mt_debit_ligne = :escompte;
           mt_credit_ligne = 0;
           qte_1 = 0;
           qte_2 = 0;
           code_tva = '';
           taux_tva = 0;
           mt_debit_tva = 0;
           mt_credit_tva = 0;
           exigibilite_tva = '';
           suspend;
       end
     /*Récupération des lignes*/
     for select
       lf.id_article,lf.lib_l_facture, lf.qte_l_facture , lf.qte_l_facture ,lf.compte_l_facture, lf.code_tva_l_facture , lf.code_t_tva_l_facture ,
       lf.taux_tva_l_facture , lf.mt_ttc_l_facture-lf.mt_ht_l_facture,  lf.mt_ht_l_facture
       from  recup_lignes_exportation(:id_fact_courant,:ttc,:tx_rem_ht_facture) lf
       where lf.id_facture = :id_fact_courant
       into :id_art,:libelle_ligne, :qte_1, :qte_2 ,:num_cpt_ligne,:code_tva, :exigibilite_tva, :taux_tva, :mt_credit_tva, :mt_credit_ligne
     do begin
       num_ligne_piece = num_ligne_piece + 1;
       mt_debit_ligne = 0;
       mt_debit_tva = 0;
       cpt_collectif = '';
       nom_tiers = '';
       adresse_1_tiers = '';
       adresse_2_tiers = '';
       code_postal_tiers = '';
       ville_tiers = '';
       /*select art.numcpt_article from v_article art where art.id_article = :id_art into :num_cpt_ligne;  */
       suspend;
     end /*fin boucle sur lignes facture*/
     update ta_facture f set f.export=1 where f.id_facture=:id_fact_courant;
   end /* fin test deja exportee */
 end /* fin boucle sur facture */
end
^

CREATE OR ALTER PROCEDURE ENREGISTRE_LIGNES_FACTURE (
    NEW_ID_FACTURE INTEGER,
    TTC INTEGER)
AS
DECLARE VARIABLE V_ID_L_FACTURE INTEGER;
DECLARE VARIABLE V_ID_FACTURE INTEGER;
DECLARE VARIABLE V_ID_T_LIGNE INTEGER;
DECLARE VARIABLE V_ID_ARTICLE INTEGER;
DECLARE VARIABLE V_NUM_LIGNE_L_FACTURE INTEGER;
DECLARE VARIABLE V_LIB_L_FACTURE VARCHAR(255) CHARACTER SET NONE;
DECLARE VARIABLE V_QTE_L_FACTURE NUMERIC(15,2);
DECLARE VARIABLE V_U1_L_FACTURE VARCHAR(20) CHARACTER SET NONE;
DECLARE VARIABLE V_U2_L_FACTURE VARCHAR(20) CHARACTER SET NONE;
DECLARE VARIABLE V_PRIX_U_L_FACTURE NUMERIC(15,2);
DECLARE VARIABLE V_TAUX_TVA_L_FACTURE NUMERIC(15,4);
DECLARE VARIABLE V_CODE_TVA_L_FACTURE VARCHAR(20) CHARACTER SET NONE;
DECLARE VARIABLE V_MT_HT_L_FACTURE NUMERIC(15,2);
DECLARE VARIABLE V_MT_TTC_L_FACTURE NUMERIC(15,2);
DECLARE VARIABLE V_REM_TX_L_FACTURE NUMERIC(15,2);
DECLARE VARIABLE V_REM_HT_L_FACTURE NUMERIC(15,2);
DECLARE VARIABLE TYPELIGNE INTEGER;
DECLARE VARIABLE V_CODE_T_TVA_L_FACTURE VARCHAR(1);
DECLARE VARIABLE V_COMPTE_L_FACTURE VARCHAR(8);
begin
TYPELIGNE=0;
select typeL.id_t_ligne from  ta_t_ligne typeL where typeL.code_t_ligne = 'H' into :typeligne;
select f.tx_rem_ht_facture from ta_facture f where f.id_facture=:new_id_facture into :V_REM_HT_L_FACTURE;
execute procedure dispatcher_tva(0,:ttc,:V_REM_HT_L_FACTURE);
FOR select lfacture.id_l_facture,
   lfacture.id_facture,
    lfacture.id_t_ligne, lfacture.id_article, lfacture.num_ligne_l_facture,
lfacture.lib_l_facture, lfacture.qte_l_facture, lfacture.u1_l_facture, lfacture.u2_l_facture,
lfacture.prix_u_l_facture, lfacture.taux_tva_l_facture,lfacture.compte_l_facture, lfacture.code_tva_l_facture,lfacture.code_t_tva_l_facture, lfacture.mt_ht_l_facture,
lfacture.mt_ttc_l_facture, lfacture.rem_tx_l_facture, lfacture.rem_ht_l_facture
from ta_l_facture_temp lfacture
where lfacture.ip_acces = current_connection
into
    :V_ID_L_FACTURE,
    :V_ID_FACTURE,
    :V_ID_T_LIGNE,
    :V_ID_ARTICLE,
    :V_NUM_LIGNE_L_FACTURE,
    :V_LIB_L_FACTURE,
    :V_QTE_L_FACTURE,
    :V_U1_L_FACTURE,
    :V_U2_L_FACTURE,
    :V_PRIX_U_L_FACTURE,
    :V_TAUX_TVA_L_FACTURE,
    :v_compte_l_facture,
    :V_CODE_TVA_L_FACTURE,
    :V_CODE_T_TVA_L_FACTURE,
    :V_MT_HT_L_FACTURE,
    :V_MT_TTC_L_FACTURE,
    :V_REM_TX_L_FACTURE,
    :V_REM_HT_L_FACTURE
do
  begin
     if (:V_ID_T_LIGNE <> typeligne) then
        begin
            V_QTE_L_FACTURE=null;
            V_PRIX_U_L_FACTURE=null;
            V_TAUX_TVA_L_FACTURE=null;
            v_compte_l_facture=null;
            V_CODE_TVA_L_FACTURE=null;
            V_CODE_T_TVA_L_FACTURE=null;
            V_MT_HT_L_FACTURE=null;
            V_MT_TTC_L_FACTURE=null;
            V_REM_TX_L_FACTURE=null;
            V_REM_HT_L_FACTURE=null;
        end
    select art.numcpt_article from ta_article art where art.id_article = :v_id_article into :v_compte_l_facture;
     insert into ta_l_facture (
    ID_L_FACTURE,
    ID_FACTURE,
    ID_T_LIGNE,
    ID_ARTICLE,
    NUM_LIGNE_L_FACTURE,
    LIB_L_FACTURE,
    QTE_L_FACTURE,
    U1_L_FACTURE,
    U2_L_FACTURE,
    PRIX_U_L_FACTURE,
    TAUX_TVA_L_FACTURE,
    compte_l_facture,
    CODE_TVA_L_FACTURE,
    CODE_T_TVA_L_FACTURE,
    MT_HT_L_FACTURE,
    MT_TTC_L_FACTURE,
    REM_TX_L_FACTURE,
    REM_HT_L_FACTURE
    ) values(
    :V_ID_L_FACTURE,
    :new_id_facture,
    :V_ID_T_LIGNE,
    :V_ID_ARTICLE,
    :V_NUM_LIGNE_L_FACTURE,
    :V_LIB_L_FACTURE,
    :V_QTE_L_FACTURE,
    :V_U1_L_FACTURE,
    :V_U2_L_FACTURE,
    :V_PRIX_U_L_FACTURE,
    :V_TAUX_TVA_L_FACTURE,
    :v_compte_l_facture,
    :V_CODE_TVA_L_FACTURE,
    :V_CODE_T_TVA_L_FACTURE,
    :V_MT_HT_L_FACTURE,
    :V_MT_TTC_L_FACTURE,
    :V_REM_TX_L_FACTURE,
    :V_REM_HT_L_FACTURE) ;
    delete from ta_l_facture_temp where ID_L_FACTURE = :V_ID_L_FACTURE and
    ip_acces=current_connection;
  end
  suspend;
end
^


CREATE OR ALTER PROCEDURE MAJ_LIGNES_FACTURE (
    ID_FACTURE_RECUP INTEGER,
    TTC INTEGER)
AS
DECLARE VARIABLE ID_L_FACTURE_COURANT INTEGER;
DECLARE VARIABLE V_ID_L_FACTURE INTEGER;
DECLARE VARIABLE V_ID_FACTURE INTEGER;
DECLARE VARIABLE V_ID_T_LIGNE INTEGER;
DECLARE VARIABLE V_ID_ARTICLE INTEGER;
DECLARE VARIABLE V_NUM_LIGNE_L_FACTURE INTEGER;
DECLARE VARIABLE V_LIB_L_FACTURE VARCHAR(255) CHARACTER SET NONE;
DECLARE VARIABLE V_QTE_L_FACTURE NUMERIC(15,2);
DECLARE VARIABLE V_U1_L_FACTURE VARCHAR(20) CHARACTER SET NONE;
DECLARE VARIABLE V_U2_L_FACTURE VARCHAR(20) CHARACTER SET NONE;
DECLARE VARIABLE V_PRIX_U_L_FACTURE NUMERIC(15,2);
DECLARE VARIABLE V_TAUX_TVA_L_FACTURE NUMERIC(15,4);
DECLARE VARIABLE V_CODE_TVA_L_FACTURE VARCHAR(20) CHARACTER SET NONE;
DECLARE VARIABLE V_MT_HT_L_FACTURE NUMERIC(15,2);
DECLARE VARIABLE V_MT_TTC_L_FACTURE NUMERIC(15,2);
DECLARE VARIABLE V_REM_TX_L_FACTURE NUMERIC(15,2);
DECLARE VARIABLE V_REM_HT_L_FACTURE NUMERIC(15,2);
DECLARE VARIABLE TYPELIGNE INTEGER;
DECLARE VARIABLE V_CODE_T_TVA_L_FACTURE VARCHAR(1);
DECLARE VARIABLE V_COMPTE_L_FACTURE VARCHAR(8);
begin
TYPELIGNE=0;
    delete from ta_l_facture lfacture where lfacture.id_facture=:id_facture_recup  and
    not (lfacture.id_l_facture in (
    select id_l_facture from ta_l_facture_temp where (id_facture=:id_facture_recup or (id_facture=-1))and
    ip_acces=current_connection ));
select typeL.id_t_ligne from  ta_t_ligne typeL where typeL.code_t_ligne = 'H' into :typeligne;
select f.tx_rem_ht_facture from ta_facture f where f.id_facture=:ID_FACTURE_RECUP into :V_REM_HT_L_FACTURE;
execute procedure dispatcher_tva(:id_facture_recup,:ttc,:V_REM_HT_L_FACTURE);
  FOR select lfacture.id_l_facture,
    lfacture.id_facture,
    lfacture.id_t_ligne, lfacture.id_article, lfacture.num_ligne_l_facture,
    lfacture.lib_l_facture, lfacture.qte_l_facture, lfacture.u1_l_facture, lfacture.u2_l_facture,
    lfacture.prix_u_l_facture, lfacture.taux_tva_l_facture,lfacture.compte_l_facture, lfacture.code_tva_l_facture,lfacture.code_t_tva_l_facture, lfacture.mt_ht_l_facture,
    lfacture.mt_ttc_l_facture, lfacture.rem_tx_l_facture, lfacture.rem_ht_l_facture from ta_l_facture_temp lfacture
    where lfacture. id_facture = :id_facture_recup and lfacture.ip_acces = current_connection into
        :V_ID_L_FACTURE,
        :V_ID_FACTURE,
        :V_ID_T_LIGNE,
        :V_ID_ARTICLE,
        :V_NUM_LIGNE_L_FACTURE,
        :V_LIB_L_FACTURE,
        :V_QTE_L_FACTURE,
        :V_U1_L_FACTURE,
        :V_U2_L_FACTURE,
        :V_PRIX_U_L_FACTURE,
        :V_TAUX_TVA_L_FACTURE,
        :v_compte_l_facture,
        :V_CODE_TVA_L_FACTURE,
        :V_CODE_T_TVA_L_FACTURE,
        :V_MT_HT_L_FACTURE,
        :V_MT_TTC_L_FACTURE,
        :V_REM_TX_L_FACTURE,
        :V_REM_HT_L_FACTURE
    do begin
     if (:V_ID_T_LIGNE <> :typeligne) then
        begin
            V_QTE_L_FACTURE=null;
            V_PRIX_U_L_FACTURE=null;
            V_TAUX_TVA_L_FACTURE=null;
            v_compte_l_facture = null;
            V_CODE_TVA_L_FACTURE=null;
            V_CODE_T_TVA_L_FACTURE=null;
            V_MT_HT_L_FACTURE=null;
            V_MT_TTC_L_FACTURE=null;
            V_REM_TX_L_FACTURE=null;
            V_REM_HT_L_FACTURE=null;
        end
        select lfacture.id_l_facture from ta_l_facture lfacture
        where lfacture.id_l_facture = :V_ID_L_FACTURE into :id_l_facture_courant;
        if (:id_l_facture_courant is not null) then
        begin
            update ta_l_facture set
            ID_L_FACTURE = :V_ID_L_FACTURE,
            ID_FACTURE = :V_ID_FACTURE,
            ID_T_LIGNE = :V_ID_T_LIGNE,
            ID_ARTICLE = :V_ID_ARTICLE,
            NUM_LIGNE_L_FACTURE = :V_NUM_LIGNE_L_FACTURE,
            LIB_L_FACTURE = :V_LIB_L_FACTURE,
            QTE_L_FACTURE = :V_QTE_L_FACTURE,
            U1_L_FACTURE = :V_U1_L_FACTURE,
            U2_L_FACTURE = :V_U2_L_FACTURE,
            PRIX_U_L_FACTURE = :V_PRIX_U_L_FACTURE,
            TAUX_TVA_L_FACTURE = :V_TAUX_TVA_L_FACTURE,
            compte_l_facture = :v_compte_l_facture,
            CODE_TVA_L_FACTURE = :V_CODE_TVA_L_FACTURE,
            CODE_T_TVA_L_FACTURE = :V_CODE_T_TVA_L_FACTURE,
            MT_HT_L_FACTURE = :V_MT_HT_L_FACTURE,
            MT_TTC_L_FACTURE = :V_MT_TTC_L_FACTURE,
            REM_TX_L_FACTURE = :V_REM_TX_L_FACTURE,
            REM_HT_L_FACTURE = :V_REM_HT_L_FACTURE
            where id_l_facture = :V_ID_L_FACTURE;
            update ta_l_facture_temp set id_facture = -1 where  id_l_facture=:id_l_facture_courant ;
        end
       end
  FOR select lfacture.id_l_facture,
    lfacture.id_facture,
    lfacture.id_t_ligne, lfacture.id_article, lfacture.num_ligne_l_facture,
    lfacture.lib_l_facture, lfacture.qte_l_facture, lfacture.u1_l_facture, lfacture.u2_l_facture,
    lfacture.prix_u_l_facture, lfacture.taux_tva_l_facture,lfacture.compte_l_facture, lfacture.code_tva_l_facture,lfacture.code_t_tva_l_facture, lfacture.mt_ht_l_facture,
    lfacture.mt_ttc_l_facture, lfacture.rem_tx_l_facture, lfacture.rem_ht_l_facture from ta_l_facture_temp lfacture
    where lfacture.id_facture = :id_facture_recup  and lfacture.ip_acces = current_connection into
        :V_ID_L_FACTURE,
        :V_ID_FACTURE,
        :V_ID_T_LIGNE,
        :V_ID_ARTICLE,
        :V_NUM_LIGNE_L_FACTURE,
        :V_LIB_L_FACTURE,
        :V_QTE_L_FACTURE,
        :V_U1_L_FACTURE,
        :V_U2_L_FACTURE,
        :V_PRIX_U_L_FACTURE,
        :V_TAUX_TVA_L_FACTURE,
        :v_compte_l_facture,
        :V_CODE_TVA_L_FACTURE,
        :V_CODE_T_TVA_L_FACTURE,
        :V_MT_HT_L_FACTURE,
        :V_MT_TTC_L_FACTURE,
        :V_REM_TX_L_FACTURE,
        :V_REM_HT_L_FACTURE
    do begin
     if (:V_ID_T_LIGNE <> :typeligne) then
        begin
            V_QTE_L_FACTURE=null;
            V_PRIX_U_L_FACTURE=null;
            V_TAUX_TVA_L_FACTURE=null;
            v_compte_l_facture=null;
            V_CODE_TVA_L_FACTURE=null;
            V_CODE_T_TVA_L_FACTURE=null;
            V_MT_HT_L_FACTURE=null;
            V_MT_TTC_L_FACTURE=null;
            V_REM_TX_L_FACTURE=null;
            V_REM_HT_L_FACTURE=null;
        end
        select art.numcpt_article from ta_article art where art.id_article = :v_id_article into :v_compte_l_facture;
            insert into ta_l_facture (
            ID_L_FACTURE,
            ID_FACTURE,
            ID_T_LIGNE,
            ID_ARTICLE,
            NUM_LIGNE_L_FACTURE,
            LIB_L_FACTURE,
            QTE_L_FACTURE,
            U1_L_FACTURE,
            U2_L_FACTURE,
            PRIX_U_L_FACTURE,
            TAUX_TVA_L_FACTURE,
            compte_l_facture,
            CODE_TVA_L_FACTURE,
            CODE_T_TVA_L_FACTURE,
            MT_HT_L_FACTURE,
            MT_TTC_L_FACTURE,
            REM_TX_L_FACTURE,
            REM_HT_L_FACTURE
            ) values(
            :V_ID_L_FACTURE,
            :V_ID_FACTURE,
            :V_ID_T_LIGNE,
            :V_ID_ARTICLE,
            :V_NUM_LIGNE_L_FACTURE,
            :V_LIB_L_FACTURE,
            :V_QTE_L_FACTURE,
            :V_U1_L_FACTURE,
            :V_U2_L_FACTURE,
            :V_PRIX_U_L_FACTURE,
            :V_TAUX_TVA_L_FACTURE,
            :v_compte_l_facture,
            :V_CODE_TVA_L_FACTURE,
            :V_CODE_T_TVA_L_FACTURE,
            :V_MT_HT_L_FACTURE,
            :V_MT_TTC_L_FACTURE,
            :V_REM_TX_L_FACTURE,
            :V_REM_HT_L_FACTURE);
       end 

   execute procedure VIDE_TA_L_FACTURE_TEMP;
    suspend;
end
^


CREATE OR ALTER PROCEDURE RECALCULPRIXTTC (
    ID_ARTICLE INTEGER,
    CODE_TVA VARCHAR(100))
AS
DECLARE VARIABLE TAUX_TVA NUMERIC(15,2);
DECLARE VARIABLE TTC NUMERIC(15,2);
begin
  for select prix.prixttc_prix from ta_prix prix where prix.id_article =:id_article into :ttc
  do begin
    select  tva.taux_tva from ta_tva tva where tva.code_tva = :code_tva into :taux_tva;
     update ta_prix prix set  prix.prixttc_prix = prix.prix_prix + (prix.prix_prix * :taux_tva)/100
     where prix.id_article = :id_article;
  end
  suspend;
end
^

CREATE OR ALTER PROCEDURE DISPATCHER_TVA (
    ID_FACTURE INTEGER,
    TTC INTEGER,
    TAUXREMISE NUMERIC(15,2))
AS
DECLARE VARIABLE CODE_TVA VARCHAR(20);
DECLARE VARIABLE TAUX NUMERIC(15,4);
DECLARE VARIABLE TOTAL_TVA NUMERIC(15,2);
DECLARE VARIABLE TOTALHT NUMERIC(15,2);
DECLARE VARIABLE NBLIGNE INTEGER;
DECLARE VARIABLE HT_LIGNE NUMERIC(15,2);
DECLARE VARIABLE ID_LIGNE INTEGER;
DECLARE VARIABLE LIGNEPASSE INTEGER;
DECLARE VARIABLE TVA_LIGNE NUMERIC(15,2);
DECLARE VARIABLE TVATMP NUMERIC(15,2);
DECLARE VARIABLE TYPELIGNE INTEGER;
DECLARE VARIABLE TOTALTTC NUMERIC(15,2);
DECLARE VARIABLE TTC_LIGNE NUMERIC(15,2);
begin
TYPELIGNE=0;
select typeL.id_t_ligne from  ta_t_ligne typeL where typeL.code_t_ligne = 'H' into :typeligne;
  if (:ttc=1)then
    begin
      FOR select CODE_TVA_RECUP, TAUX_RECUP, MT_TVA_RECUP FROM
           calcul_tva_sur_mt_ht(:id_facture,:tauxremise,:ttc) into :code_tva,taux,total_tva
      do
      begin
        select (sum(lfacture.mt_ttc_l_facture)/*-(sum(lfacture.mt_ttc_l_facture)*(:tauxremise/100))*/),
         count(*) from ta_l_facture_temp lfacture where
        lfacture.code_tva_l_facture = :CODE_TVA and lfacture.taux_tva_l_facture = :TAUX
        and lfacture.id_t_ligne = :typeligne  and lfacture.ip_acces = current_connection
        into :totalttc, :NbLigne;
        lignepasse=1;
        TVATMP=0;
        TVATMP=:total_tva;
        For  select lfacture2.mt_ttc_l_facture,
         lfacture2.id_l_facture from ta_l_facture_temp lfacture2 where
        lfacture2.code_tva_l_facture = :CODE_TVA and lfacture2.taux_tva_l_facture = :TAUX
        and lfacture2.id_t_ligne = :typeligne and lfacture2.ip_acces = current_connection
             into :ttc_ligne, :id_ligne
        do 
            begin
              if (:totalttc=0) then tva_ligne=0;
              else
                  begin
                       if  (lignepasse>= :NbLigne) then
                       tva_ligne = :TVATMP;
                       else
                       tva_ligne = (:total_tva * :ttc_ligne) / :totalttc;
                  end
               TVATMP =  :TVATMP -  :tva_ligne;
               Update ta_l_facture_temp lfacture3 set lfacture3.mt_ht_l_facture = (:ttc_ligne - :tva_ligne)
                  where lfacture3.id_l_facture=:id_ligne and lfacture3.ip_acces = current_connection and lfacture3.id_t_ligne = :typeligne;
               lignepasse = :lignepasse + 1;
            end
      end
    end
  else
    begin
      FOR select CODE_TVA_RECUP, TAUX_RECUP, MT_TVA_RECUP FROM
            calcul_tva_sur_mt_ht(:id_facture,:tauxremise,:ttc) into :code_tva,taux,total_tva
      do
      begin
        select (sum(lfacture.mt_ht_l_facture)/*-(sum(lfacture.mt_ht_l_facture)*(:tauxremise/100))*/), count(*) from ta_l_facture_temp lfacture where
        lfacture.code_tva_l_facture = :CODE_TVA and lfacture.taux_tva_l_facture = :TAUX
        and lfacture.id_t_ligne = :typeligne  and lfacture.ip_acces = current_connection
        into :TotalHT, :NbLigne;
        lignepasse=1;
        TVATMP=0;
        TVATMP=:total_tva;
        For  select lfacture2.mt_ht_l_facture,
         lfacture2.id_l_facture from ta_l_facture_temp lfacture2 where
        lfacture2.code_tva_l_facture = :CODE_TVA and lfacture2.taux_tva_l_facture = :TAUX
        and lfacture2.id_t_ligne = :typeligne and lfacture2.ip_acces = current_connection
             into :ht_ligne, :id_ligne
        do begin
           if (:totalht=0) then tva_ligne=0 ;
           else
               begin
                   if  (lignepasse>= :NbLigne) then
                   tva_ligne = :TVATMP;
                else
                   tva_ligne = (:total_tva * :ht_ligne) / :totalht;
               end
           TVATMP =  :TVATMP -  :tva_ligne;
           Update ta_l_facture_temp lfacture3 set lfacture3.mt_ttc_l_facture = (:ht_ligne + :tva_ligne)
              where lfacture3.id_l_facture=:id_ligne and lfacture3.ip_acces = current_connection and lfacture3.id_t_ligne = :typeligne;
           lignepasse = :lignepasse + 1;
        end
      end
    end
    suspend;
end
^

CREATE OR ALTER PROCEDURE DISPATCHER_TVA_SUR_CODETVA (
    ID_FACTURE INTEGER,
    CODE_TVA_IN VARCHAR(20),
    TAUX_IN NUMERIC(15,4),
    TTC INTEGER,
    TAUXREMISE NUMERIC(15,2))
AS
DECLARE VARIABLE CODE_TVA VARCHAR(20);
DECLARE VARIABLE TAUX NUMERIC(15,4);
DECLARE VARIABLE TOTAL_TVA NUMERIC(15,2);
DECLARE VARIABLE TOTALHT NUMERIC(15,2);
DECLARE VARIABLE NBLIGNE INTEGER;
DECLARE VARIABLE HT_LIGNE NUMERIC(15,2);
DECLARE VARIABLE ID_LIGNE INTEGER;
DECLARE VARIABLE LIGNEPASSE INTEGER;
DECLARE VARIABLE TVA_LIGNE NUMERIC(15,2);
DECLARE VARIABLE TVATMP NUMERIC(15,2);
DECLARE VARIABLE TYPELIGNE NUMERIC(15,2);
DECLARE VARIABLE TOTALTTC NUMERIC(15,2);
DECLARE VARIABLE TTC_LIGNE NUMERIC(15,2);
begin
TYPELIGNE=0;
select typeL.id_t_ligne from  ta_t_ligne typeL where typeL.code_t_ligne = 'H' into :typeligne;
  FOR select CODE_TVA_RECUP, TAUX_RECUP, MT_TVA_RECUP FROM
       CALCUL_TVA_SUR_CODETVA(:id_facture,:CODE_TVA_in,:TAUX_in,:ttc,:TauxRemise) into :code_tva,taux,total_tva
  do
  begin
      if (:ttc=1) then
          begin
                select (sum(lfacture.mt_ttc_l_facture)-(sum(lfacture.mt_ttc_l_facture)*(:tauxremise/100))), count(*) from ta_l_facture_temp lfacture where
                lfacture.code_tva_l_facture = :CODE_TVA and lfacture.taux_tva_l_facture = :TAUX
                and lfacture.id_t_ligne = :typeligne and lfacture.ip_acces = current_connection
                into :totalttc, :NbLigne;
                lignepasse=1;
                TVATMP=0;
                TVATMP=:total_tva;
                For  select lfacture2.mt_ttc_l_facture,
                 lfacture2.id_l_facture from ta_l_facture_temp lfacture2 where
                lfacture2.code_tva_l_facture = :CODE_TVA and lfacture2.taux_tva_l_facture = :TAUX
                and lfacture2.id_t_ligne = :typeligne and lfacture2.ip_acces = current_connection
                     into :ttc_ligne, :id_ligne
                do begin
                   if (:totalttc=0) then tva_ligne=0;
                   else
                       begin
                           if  (lignepasse>= :NbLigne) then
                           tva_ligne = :TVATMP;
                        else
                           tva_ligne = (:total_tva * :ttc_ligne) / :totalttc;
                       end
                   TVATMP =  :TVATMP -  :tva_ligne;
                   Update ta_l_facture_temp lfacture3 set lfacture3.mt_ht_l_facture = (:ttc_ligne - :tva_ligne)
                      where lfacture3.id_l_facture=:id_ligne and lfacture3.ip_acces = current_connection and lfacture3.id_t_ligne = :typeligne;
                   lignepasse = :lignepasse + 1;
                end
          end
      else
          begin
                select (sum(lfacture.mt_ht_l_facture)-(sum(lfacture.mt_ht_l_facture)*(:tauxremise/100))), count(*) from ta_l_facture_temp lfacture where
                lfacture.code_tva_l_facture = :CODE_TVA and lfacture.taux_tva_l_facture = :TAUX
                and lfacture.id_t_ligne = :typeligne and lfacture.ip_acces = current_connection
                into :TotalHT, :NbLigne;
                lignepasse=1;
                TVATMP=0;
                TVATMP=:total_tva;
                For  select lfacture2.mt_ht_l_facture,
                 lfacture2.id_l_facture from ta_l_facture_temp lfacture2 where
                lfacture2.code_tva_l_facture = :CODE_TVA and lfacture2.taux_tva_l_facture = :TAUX
                and lfacture2.id_t_ligne = :typeligne and lfacture2.ip_acces = current_connection
                     into :ht_ligne, :id_ligne
                do begin
                   if (:totalht=0) then tva_ligne=0;
                   else
                       begin
                           if  (lignepasse>= :NbLigne) then
                           tva_ligne = :TVATMP;
                        else
                           tva_ligne = (:total_tva * :ht_ligne) / :totalht;
                       end
                   TVATMP =  :TVATMP -  :tva_ligne;
                   Update ta_l_facture_temp lfacture3 set lfacture3.mt_ttc_l_facture = (:ht_ligne + :tva_ligne)
                      where lfacture3.id_l_facture=:id_ligne and lfacture3.ip_acces = current_connection and lfacture3.id_t_ligne = :typeligne;
                   lignepasse = :lignepasse + 1;
                end
          end
  end
    suspend;
end
^


CREATE OR ALTER PROCEDURE CALCUL_TVA_SUR_MT_HT (
    ID_FACTURE INTEGER,
    TAUXREMISE NUMERIC(15,2),
    TTC INTEGER)
RETURNS (
    ID_FACTURE_RECUP INTEGER,
    TAUX_RECUP NUMERIC(15,4),
    CODE_TVA_RECUP VARCHAR(20),
    MT_TVA_RECUP NUMERIC(15,2),
    LIBELLE_RECUP VARCHAR(100),
    VALEURTTC NUMERIC(15,2))
AS
DECLARE VARIABLE TYPELIGNE INTEGER;
DECLARE VARIABLE SUMHT NUMERIC(15,2);
DECLARE VARIABLE SUMTTC NUMERIC(15,2);
begin
TYPELIGNE=0;
select typeL.id_t_ligne from  ta_t_ligne typeL  where typeL.code_t_ligne = 'H' into :typeligne;
if (:ttc=1) then
    begin
        FOR select sum(lfacture.mt_ttc_l_facture),
        sum(lfacture.mt_ttc_l_facture)-((sum(lfacture.mt_ttc_l_facture)*100)/(100+lfacture.TAUX_TVA_L_FACTURE)),
        lfacture.CODE_TVA_L_FACTURE,lfacture.TAUX_TVA_L_FACTURE
          from ta_l_facture_temp lfacture where lfacture.id_facture = :ID_FACTURE
        and lfacture.id_t_ligne = :typeligne /*and lfacture.ip_acces = current_connection */
          group by lfacture.CODE_TVA_L_FACTURE,lfacture.TAUX_TVA_L_FACTURE
          order by lfacture.CODE_TVA_L_FACTURE,lfacture.TAUX_TVA_L_FACTURE
          into : sumttc,mt_tva_recup,code_tva_recup,taux_recup
          do
          begin
          if (:tauxremise is not null and :tauxremise > 0 ) then
            begin
                sumttc=:sumttc-(:sumttc*(:tauxremise/100));
                mt_tva_recup=cast( sumttc-(:sumttc*100) / (100+:taux_recup)as numeric (15,2));
            end
            select tva.LIBELLE_TVA from TA_TVA tva where tva.CODE_TVA = :code_tva_recup into :libelle_recup;
            id_facture_recup=:id_facture;
            valeurttc=:sumttc;
            Suspend;
          end
    end
else
    begin
        FOR select sum(lfacture.mt_ht_l_facture),
         sum(lfacture.mt_ht_l_facture)*(lfacture.TAUX_TVA_L_FACTURE/100),
        lfacture.CODE_TVA_L_FACTURE,lfacture.TAUX_TVA_L_FACTURE
          from ta_l_facture_temp lfacture where lfacture.id_facture = :ID_FACTURE
        and lfacture.id_t_ligne = :typeligne /*and lfacture.ip_acces = current_connection */
          group by lfacture.CODE_TVA_L_FACTURE,lfacture.TAUX_TVA_L_FACTURE
          order by lfacture.CODE_TVA_L_FACTURE,lfacture.TAUX_TVA_L_FACTURE
          into : sumht,mt_tva_recup,code_tva_recup,taux_recup
          do
          begin
          if (:tauxremise is not null and :tauxremise > 0 ) then
            begin
                sumht=:sumht-(:sumht*(:tauxremise/100));
                mt_tva_recup= cast( :sumht * (:taux_recup/100)as numeric (15,2));
            end
            select tva.LIBELLE_TVA from TA_TVA tva where tva.CODE_TVA = :code_tva_recup into :libelle_recup;
            id_facture_recup=:id_facture;
            valeurttc=:sumht;
            Suspend;
          end
    end
end
^

CREATE OR ALTER PROCEDURE INVERSE_NUM_LIGNE (
    NUM_LIGNE INTEGER,
    NEWLIGNE INTEGER,
    ID_LIGNE_DEPART INTEGER)
AS
DECLARE VARIABLE ID_LIGNE INTEGER;
DECLARE VARIABLE OLDLIGNE INTEGER;
DECLARE VARIABLE DESCENDANT INTEGER;
begin
select NUM_LIGNE_L_FACTURE from ta_l_facture_temp where ID_L_facture = :ID_LIGNE_DEPART
and ip_acces=current_connection  into :oldligne;
if((:oldligne < :NEWLIGNE))then
    begin
    for select id_l_facture from ta_l_facture_temp where num_ligne_l_facture between :oldligne
    and :NEWLIGNE and ip_acces=current_connection
    order by num_ligne_l_facture desc into :id_ligne  do
        begin
               if(:id_ligne <> :ID_LIGNE_DEPART) then
                 update ta_l_facture_temp set NUM_LIGNE_L_FACTURE=(NUM_LIGNE_L_FACTURE-1) where ID_L_facture = :id_ligne;
               else
                 update ta_l_facture_temp set NUM_LIGNE_L_FACTURE=(:NEWLIGNE) where ID_L_facture = :id_ligne;
        end
    end
else
    begin
    for select id_l_facture from ta_l_facture_temp where num_ligne_l_facture between :NEWLIGNE
    and :oldligne  and ip_acces=current_connection
    order by num_ligne_l_facture asc into :id_ligne  do
        begin
               if(:id_ligne <> :ID_LIGNE_DEPART) then  /*  */
                 update ta_l_facture_temp set NUM_LIGNE_L_FACTURE=(NUM_LIGNE_L_FACTURE+1) where ID_L_facture = :id_ligne;
               else
                 update ta_l_facture_temp set NUM_LIGNE_L_FACTURE=(:NEWLIGNE) where ID_L_facture = :id_ligne;
        end
    end
/*select NUM_LIGNE_L_FACTURE from ta_l_facture_temp where ID_L_facture = :ID_LIGNE_DEPART into :oldligne;
update ta_l_facture_temp set NUM_LIGNE_L_FACTURE=(:NEWLIGNE) where ID_L_facture = :ID_LIGNE_DEPART;
update ta_l_facture_temp set NUM_LIGNE_L_FACTURE=(:oldligne) where NUM_LIGNE_L_FACTURE = :NEWLIGNE
and ID_L_facture <> :ID_LIGNE_DEPART; */
suspend;
end
^


CREATE OR ALTER PROCEDURE MAJ_NUM_LIGNE (
    NUM_LIGNE INTEGER,
    ID_LIGNE INTEGER,
    SUPPR INTEGER)
AS
DECLARE VARIABLE ID INTEGER;
DECLARE VARIABLE NUM INTEGER;
DECLARE VARIABLE MAJ INTEGER;
DECLARE VARIABLE INC INTEGER;
begin
 maj=0;
 inc=0;

 if(:suppr=0) then begin
 for select ID_L_FACTURE, NUM_LIGNE_L_FACTURE from ta_l_facture_temp lfacttemp 
 where ip_acces=current_connection
 order by lfacttemp.num_ligne_l_facture asc,
 lfacttemp.quand_cree_l_facture desc
 into :id, :num
 do begin
    if(:maj=1) then begin
        update ta_l_facture_temp set NUM_LIGNE_L_FACTURE=(NUM_LIGNE_L_FACTURE+1) where ID_L_FACTURE = :id;
    end
    if(:num_ligne=:num) then begin
        maj= 1;
    end
 end
 end 
 else begin
     for select ID_L_FACTURE, NUM_LIGNE_L_FACTURE from ta_l_facture_temp lfacttemp 
     where ip_acces=current_connection
     order by lfacttemp.num_ligne_l_facture asc,
 lfacttemp.quand_cree_l_facture desc
 into :id, :num
 do begin
        update ta_l_facture_temp set NUM_LIGNE_L_FACTURE=(:inc) where ID_L_FACTURE = :id;
        inc=inc+1;
    end
 end
 maj=0;
 for select ID_L_FACTURE, NUM_LIGNE_L_FACTURE from ta_l_facture_temp lfacttemp 
 where ip_acces=current_connection
 order by lfacttemp.num_ligne_l_facture asc
 into :id, :num
 do begin
    update ta_l_facture_temp set NUM_LIGNE_L_FACTURE=:maj where ID_L_FACTURE = :id;
    maj=maj+1;
 end
suspend;
end
^

create or  ALTER TRIGGER TA_FACTURE_AU0  FOR TA_FACTURE
INACTIVE AFTER UPDATE POSITION 0
AS
begin
execute procedure maj_lignes_facture(old.id_facture,new.ttc);
end
^

CREATE OR ALTER TRIGGER TBUID_PRIX_REF FOR TA_REF_PRIX
ACTIVE BEFORE UPDATE POSITION 0
as
begin
     delete from TA_REF_PRIX  WHERE ID_ARTICLE = New.ID_ARTICLE
     and new.id_ref_prix<> id_ref_prix;
end
^


CREATE OR ALTER PROCEDURE INFOSFACTURE (
    ID_FACTURE_RECUP INTEGER,
    ID_TIERS_RECUP INTEGER,
    LIVRAISON INTEGER)
RETURNS (
    ID_INFOS_FACTURE INTEGER,
    ID_ADRESSE INTEGER,
    ID_FACTURE INTEGER,
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

for select

  infos.ID_INFOS_FACTURE,
  cast (null as INTEGER),
  infos.ID_FACTURE,
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
  
   
  from TA_INFOS_FACTURE infos where infos.ID_FACTURE = :id_facture_recup into :ID_INFOS_FACTURE ,:id_adresse,
  :ID_FACTURE ,:ADRESSE1 ,:ADRESSE2 ,:ADRESSE3 ,:CODEPOSTAL ,:VILLE ,:PAYS ,:ADRESSE1_LIV ,
  :ADRESSE2_LIV ,:ADRESSE3_LIV ,:CODEPOSTAL_LIV ,:VILLE_LIV ,:PAYS_LIV ,:CODE_COMPTA ,
  :COMPTE ,:ID_TIERS ,:NOM_TIERS ,:PRENOM_TIERS ,:SURNOM_TIERS, :CODE_T_CIVILITE, :CODE_T_ENTITE,:TVA_I_COM_COMPL,
  :CODE_C_PAIEMENT,:LIB_C_PAIEMENT,:REPORT_C_PAIEMENT,:FIN_MOIS_C_PAIEMENT
do begin

  suspend;   
end
if (livraison=0) then
    begin
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
      where tiers.id_tiers = :id_tiers_recup  and (tadr.code_t_adr = 'FACT')
      into :ID_INFOS_FACTURE ,:id_adresse, 
      :ID_FACTURE ,:ADRESSE1 ,:ADRESSE2 ,:ADRESSE3 ,:CODEPOSTAL ,:VILLE ,:PAYS ,:ADRESSE1_LIV ,
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
      where tiers.id_tiers = :id_tiers_recup  and ((tadr.code_t_adr is null) or (tadr.code_t_adr = 'LIV'))
      into :ID_INFOS_FACTURE ,:id_adresse, 
      :ID_FACTURE ,:ADRESSE1 ,:ADRESSE2 ,:ADRESSE3 ,:CODEPOSTAL ,:VILLE ,:PAYS ,:ADRESSE1_LIV ,
      :ADRESSE2_LIV ,:ADRESSE3_LIV ,:CODEPOSTAL_LIV ,:VILLE_LIV ,:PAYS_LIV ,:CODE_COMPTA ,
      :COMPTE ,:ID_TIERS ,:NOM_TIERS ,:PRENOM_TIERS ,:SURNOM_TIERS, :CODE_T_CIVILITE,:CODE_T_ENTITE,:TVA_I_COM_COMPL,
      :CODE_C_PAIEMENT,:LIB_C_PAIEMENT,:REPORT_C_PAIEMENT,:FIN_MOIS_C_PAIEMENT
      do begin
          suspend;
         end
    end

if (livraison=1) then
    begin
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
      where tiers.id_tiers = :id_tiers_recup  and (tadr.code_t_adr = 'LIV')
      into :ID_INFOS_FACTURE ,:id_adresse, 
      :ID_FACTURE ,:ADRESSE1 ,:ADRESSE2 ,:ADRESSE3 ,:CODEPOSTAL ,:VILLE ,:PAYS ,:ADRESSE1_LIV ,
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
      where tiers.id_tiers = :id_tiers_recup  and ((tadr.code_t_adr is null) or (tadr.code_t_adr = 'FACT'))
      into :ID_INFOS_FACTURE ,:id_adresse, 
      :ID_FACTURE ,:ADRESSE1 ,:ADRESSE2 ,:ADRESSE3 ,:CODEPOSTAL ,:VILLE ,:PAYS ,:ADRESSE1_LIV ,
      :ADRESSE2_LIV ,:ADRESSE3_LIV ,:CODEPOSTAL_LIV ,:VILLE_LIV ,:PAYS_LIV ,:CODE_COMPTA ,
      :COMPTE ,:ID_TIERS ,:NOM_TIERS ,:PRENOM_TIERS ,:SURNOM_TIERS, :CODE_T_CIVILITE,:CODE_T_ENTITE,:TVA_I_COM_COMPL,
      :CODE_C_PAIEMENT,:LIB_C_PAIEMENT,:REPORT_C_PAIEMENT,:FIN_MOIS_C_PAIEMENT
      do begin
          suspend;
         end
    end
end
^
