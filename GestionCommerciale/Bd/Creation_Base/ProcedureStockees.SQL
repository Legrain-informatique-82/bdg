/******************************************************************************/
/****                          Stored Procedures                           ****/
/******************************************************************************/



CREATE PROCEDURE CALCUL_TOTAL_TEMP (
    MODULE VARCHAR(255),
    ID_DOCUMENT INTEGER,
    TAUXREMISEHT NUMERIC(15,2),
    TTC INTEGER,
    TAUXESCOMPTE NUMERIC(15,2))
RETURNS (
    MT_HT NUMERIC(15,2),
    MT_TVA NUMERIC(15,2),
    MT_TTC NUMERIC(15,2),
    NETHT NUMERIC(15,2),
    NETTTC NUMERIC(15,2),
    NETTVA NUMERIC(15,2))
AS
DECLARE VARIABLE TYPELIGNE INTEGER;
begin
  EXIT;
END
^


CREATE PROCEDURE CALCUL_TOTAL_DIRECT (
    MODULE VARCHAR(255),
    ID_DOCUMENT INTEGER)
RETURNS (
    MT_HT NUMERIC(15,2),
    MT_TVA NUMERIC(15,2),
    MT_TTC NUMERIC(15,2),
    TAUX_R_HT NUMERIC(15,4),
    TAUX_R_TTC NUMERIC(15,4),
    REGLE NUMERIC(15,2),
    REMISE_HT NUMERIC(15,2),
    REMISE_TTC NUMERIC(15,2),
    MTNETHT NUMERIC(15,2),
    MTNETTTC NUMERIC(15,2),
    MTNETTVA NUMERIC(15,2),
    NETAPAYER NUMERIC(15,2),
    TVA NUMERIC(15,2))
AS
DECLARE VARIABLE TYPELIGNE INTEGER;
DECLARE VARIABLE TTC INTEGER;
DECLARE VARIABLE TOTALTTC NUMERIC(15,2);
begin
  EXIT;
END
^






CREATE PROCEDURE CALCUL_TVA_SUR_MT_HT (
    MODULE VARCHAR(255),
    ID_DOCUMENT INTEGER,
    TAUXREMISE NUMERIC(15,2),
    TTC INTEGER)
RETURNS (
    ID_DOCUMENT_RECUP INTEGER,
    TAUX_RECUP NUMERIC(15,4),
    CODE_TVA_RECUP VARCHAR(20),
    MT_TVA_RECUP NUMERIC(15,2),
    LIBELLE_RECUP VARCHAR(100),
    VALEURTTC NUMERIC(15,2))
AS
DECLARE VARIABLE QUERY VARCHAR(1000);
DECLARE VARIABLE TYPELIGNE INTEGER;
DECLARE VARIABLE SUMHT NUMERIC(15,2);
DECLARE VARIABLE SUMTTC NUMERIC(15,2);
DECLARE VARIABLE TVA NUMERIC(15,4);
begin
  EXIT;
END
^




CREATE PROCEDURE CALCUL_TVA_TA_L_FACTURE (
    MODULE VARCHAR(255),
    ID_DOCUMENT INTEGER,
    EXPORT INTEGER)
RETURNS (
    ID_DOCUMENT_RECUP SMALLINT,
    TAUX_RECUP NUMERIC(15,4),
    CODE_TVA_RECUP VARCHAR(20),
    MT_TVA_RECUP NUMERIC(15,2),
    LIBELLE_RECUP VARCHAR(100),
    MT_HT NUMERIC(15,2),
    MT_TTC NUMERIC(15,2),
    TAUXREMISE NUMERIC(15,2),
    MT_HT_REMISE NUMERIC(15,2),
    MT_TTC_REMISE NUMERIC(15,2))
AS
DECLARE VARIABLE QUERY1 VARCHAR(1000);
DECLARE VARIABLE QUERY2 VARCHAR(1000);
DECLARE VARIABLE ID INTEGER;
DECLARE VARIABLE TYPELIGNE INTEGER;
DECLARE VARIABLE TTC SMALLINT;
begin
  EXIT;
END
^





CREATE PROCEDURE ENREGISTRE_LIGNES_FACTURE (
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
  EXIT;
END
^



CREATE PROCEDURE MAJ_GENERATEUR
AS
BEGIN
  EXIT;
END
^


CREATE PROCEDURE MAJ_LIGNES_FACTURE (
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
  EXIT;
END
^




CREATE PROCEDURE RECUP_LIGNES_FACTURE (
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
  EXIT;
END
^



CREATE PROCEDURE RECUP_IP_ACCES
RETURNS (
    IP_ACCES VARCHAR(50))
AS
BEGIN
  EXIT;
END
^


CREATE PROCEDURE ANNULE_MODIFICATION (
    NOMTABLE VARCHAR(255),
    NOMCHAMP VARCHAR(255),
    VALEUR VARCHAR(255))
AS
BEGIN
  EXIT;
END
^


CREATE PROCEDURE AUTORISE_MODIF (
    NOMTABLE VARCHAR(255),
    NOMCHAMP VARCHAR(255),
    VALEUR VARCHAR(255))
RETURNS (
    RETOUR INTEGER)
AS
BEGIN
  EXIT;
END
^



CREATE PROCEDURE RENTRE_EN_MODIFICATION (
    NOMTABLE VARCHAR(255),
    NOMCHAMP VARCHAR(255),
    VALEUR VARCHAR(255))
AS
BEGIN
  EXIT;
END
^



CREATE PROCEDURE ENREGISTRE_ACCES
AS
BEGIN
  EXIT;
END
^




CREATE PROCEDURE NETTOYAGE
AS
BEGIN
  EXIT;
END
^




CREATE PROCEDURE SUPPRESSION_ACCES
AS
BEGIN
  EXIT;
END
^



CREATE PROCEDURE AUTORISE_MODIF_GENERE (
    NOMTABLE VARCHAR(255),
    NOMCHAMP VARCHAR(255),
    VALEUR VARCHAR(255))
RETURNS (
    RETOUR INTEGER)
AS
begin
  exit;
end
^


CREATE PROCEDURE RAZ_ACCES
AS
BEGIN
  EXIT;
END
^



CREATE PROCEDURE REF_PRIX_DELETABLE (
    ID_RECUP INTEGER)
RETURNS (
    NB INTEGER)
AS
BEGIN
  EXIT;
END
^

CREATE OR ALTER PROCEDURE DISPATCHER_TVA_DOCUMENT (
    MODULE VARCHAR(255),
    ID_DOCUMENT INTEGER,
    TTC INTEGER,
    TAUXREMISE NUMERIC(15,2))
AS
DECLARE VARIABLE QUERY1 VARCHAR(1000);
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
  exit;
end
^

   



CREATE PROCEDURE VIDE_DOCUMENT_TEMP(
module VARCHAR(255))
as
begin
  EXIT;
end
^


CREATE OR ALTER PROCEDURE RECORD_MODIFIABLE (
    NOM_TABLE VARCHAR(100),
    IDTABLE INTEGER)
RETURNS (
    NB INTEGER)
AS
begin
  exit;
end
^

CREATE OR ALTER PROCEDURE MAJ_NUM_LIGNE (
    MODULE VARCHAR(255),
    NUM_LIGNE INTEGER,
    ID_LIGNE INTEGER,
    SUPPR INTEGER)
AS
DECLARE VARIABLE ID INTEGER;
DECLARE VARIABLE NUM INTEGER;
DECLARE VARIABLE MAJ INTEGER;
DECLARE VARIABLE INC INTEGER;
begin
  exit;
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
  exit;
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
  exit;
end
^
 
 
CREATE OR ALTER PROCEDURE CALCUL_TVA_DIRECT (
    MODULE VARCHAR(255),
    ID_DOCUMENT INTEGER,
    TAUXREMISE NUMERIC(15,2),
    TTC INTEGER)
RETURNS (
    ID_DOCUMENT_RECUP INTEGER,
    TAUX_RECUP NUMERIC(15,4),
    CODE_TVA_RECUP VARCHAR(20),
    MT_TVA_RECUP NUMERIC(15,2),
    LIBELLE_RECUP VARCHAR(100),
    VALEURTTC NUMERIC(15,2))
AS
DECLARE VARIABLE TYPELIGNE INTEGER;
DECLARE VARIABLE SUMHT NUMERIC(15,2);
DECLARE VARIABLE SUMTTC NUMERIC(15,2);
DECLARE VARIABLE QUERY VARCHAR(1000);
DECLARE VARIABLE TVA NUMERIC(15,4);
begin
  exit;
end
^

CREATE OR ALTER PROCEDURE INVERSE_NUM_LIGNE (
    MODULE VARCHAR(255),
    NUM_LIGNE INTEGER,
    NEWLIGNE INTEGER,
    ID_LIGNE_DEPART INTEGER)
AS
DECLARE VARIABLE ID_LIGNE INTEGER;
DECLARE VARIABLE OLDLIGNE INTEGER;
DECLARE VARIABLE DESCENDANT INTEGER;
begin
  exit;
end
^


CREATE OR ALTER PROCEDURE ENREGISTRE_LIGNES_DEVIS (
    NEW_ID_DEVIS INTEGER,
    TTC INTEGER)
AS
DECLARE VARIABLE V_ID_L_DOCUMENT INTEGER;
DECLARE VARIABLE V_ID_DOCUMENT INTEGER;
DECLARE VARIABLE V_ID_T_LIGNE INTEGER;
DECLARE VARIABLE V_ID_ARTICLE INTEGER;
DECLARE VARIABLE V_NUM_LIGNE_L_DOCUMENT INTEGER;
DECLARE VARIABLE V_LIB_L_DOCUMENT VARCHAR(255) CHARACTER SET NONE;
DECLARE VARIABLE V_QTE_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE V_U1_L_DOCUMENT VARCHAR(20) CHARACTER SET NONE;
DECLARE VARIABLE V_U2_L_DOCUMENT VARCHAR(20) CHARACTER SET NONE;
DECLARE VARIABLE V_PRIX_U_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE V_TAUX_TVA_L_DOCUMENT NUMERIC(15,4);
DECLARE VARIABLE V_CODE_TVA_L_DOCUMENT VARCHAR(20) CHARACTER SET NONE;
DECLARE VARIABLE V_MT_HT_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE V_MT_TTC_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE V_REM_TX_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE V_REM_HT_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE TYPELIGNE INTEGER;
DECLARE VARIABLE V_CODE_T_TVA_L_DOCUMENT VARCHAR(1);
DECLARE VARIABLE V_COMPTE_L_DOCUMENT VARCHAR(8);
begin
  exit;
end
^

CREATE OR ALTER PROCEDURE MAJ_LIGNES_DEVIS (
    ID_DOCUMENT_RECUP INTEGER,
    TTC INTEGER)
AS
DECLARE VARIABLE ID_L_DOCUMENT_COURANT INTEGER;
DECLARE VARIABLE V_ID_L_DOCUMENT INTEGER;
DECLARE VARIABLE V_ID_DOCUMENT INTEGER;
DECLARE VARIABLE V_ID_T_LIGNE INTEGER;
DECLARE VARIABLE V_ID_ARTICLE INTEGER;
DECLARE VARIABLE V_NUM_LIGNE_L_DOCUMENT INTEGER;
DECLARE VARIABLE V_LIB_L_DOCUMENT VARCHAR(255) CHARACTER SET NONE;
DECLARE VARIABLE V_QTE_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE V_U1_L_DOCUMENT VARCHAR(20) CHARACTER SET NONE;
DECLARE VARIABLE V_U2_L_DOCUMENT VARCHAR(20) CHARACTER SET NONE;
DECLARE VARIABLE V_PRIX_U_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE V_TAUX_TVA_L_DOCUMENT NUMERIC(15,4);
DECLARE VARIABLE V_CODE_TVA_L_DOCUMENT VARCHAR(20) CHARACTER SET NONE;
DECLARE VARIABLE V_MT_HT_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE V_MT_TTC_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE V_REM_TX_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE V_REM_HT_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE TYPELIGNE INTEGER;
DECLARE VARIABLE V_CODE_T_TVA_L_DOCUMENT VARCHAR(1);
DECLARE VARIABLE V_COMPTE_L_DOCUMENT VARCHAR(8);
begin
  exit;
end
^


CREATE OR ALTER PROCEDURE RECUP_LIGNES_DEVIS (
    CODE_DOCUMENT_RECUP VARCHAR(50))
RETURNS (
    ID_DOCUMENT_RECUP INTEGER)
AS
DECLARE VARIABLE V_ID_L_DOCUMENT INTEGER;
DECLARE VARIABLE V_ID_DOCUMENT INTEGER;
DECLARE VARIABLE V_ID_T_LIGNE INTEGER;
DECLARE VARIABLE V_ID_ARTICLE INTEGER;
DECLARE VARIABLE V_NUM_LIGNE_L_DOCUMENT INTEGER;
DECLARE VARIABLE V_LIB_L_DOCUMENT VARCHAR(255) CHARACTER SET NONE;
DECLARE VARIABLE V_QTE_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE V_U1_L_DOCUMENT VARCHAR(20) CHARACTER SET NONE;
DECLARE VARIABLE V_U2_L_DOCUMENT VARCHAR(20) CHARACTER SET NONE;
DECLARE VARIABLE V_PRIX_U_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE V_TAUX_TVA_L_DOCUMENT NUMERIC(15,4);
DECLARE VARIABLE V_CODE_TVA_L_DOCUMENT VARCHAR(20) CHARACTER SET NONE;
DECLARE VARIABLE V_MT_HT_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE V_MT_TTC_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE V_REM_TX_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE V_REM_HT_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE V_CODE_T_TVA_L_DOCUMENT VARCHAR(1);
DECLARE VARIABLE V_COMPTE_L_DOCUMENT VARCHAR(8);
begin
  exit;
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
exit;
end
^

CREATE OR ALTER PROCEDURE INFOSCPAIEMENT (
    ID_FACTURE_RECUP INTEGER,
    ID_TIERS_RECUP INTEGER)
RETURNS (
    ID_INFOS_FACTURE INTEGER,
    ID_C_PAIEMENT INTEGER,
    ID_FACTURE INTEGER,
    ID_TIERS INTEGER,
    CODE_C_PAIEMENT  varchar(50),
    LIB_C_PAIEMENT varchar(255),
    REPORT_C_PAIEMENT INTEGER,
    FIN_MOIS_C_PAIEMENT INTEGER
    
    )
AS    
begin
exit;     
end
^


    
/*************************************  ALTER  *****************************************/
/*************************************    *****************************************/


/*************************************  REF_PRIX_DELETABLE  *****************************************/
CREATE OR ALTER PROCEDURE REF_PRIX_DELETABLE (
    ID_RECUP INTEGER)
RETURNS (
    NB INTEGER)
AS
begin
nb=0;
  select count(*) from ta_prix prix where prix.id_article = :id_recup into  :nb;
  suspend;
end
^


CREATE OR ALTER PROCEDURE AUTORISE_MODIF_GENERE (
    NOMTABLE VARCHAR(255),
    NOMCHAMP VARCHAR(255),
    VALEUR VARCHAR(255))
RETURNS (
    RETOUR INTEGER)
AS
begin
  select count(*) from ta_modif modif where upper(modif.table_modif) = upper(:nomtable)
  and upper(modif.champ_modif) =upper(:nomchamp)
  and  upper(modif.valeur_modif) =upper(:valeur) and modif.ip_acces<>current_connection into :retour;
  suspend;
end
^



CREATE OR ALTER PROCEDURE CALCUL_TVA_SUR_MT_HT (
    MODULE VARCHAR(255),
    ID_DOCUMENT INTEGER,
    TAUXREMISE NUMERIC(15,2),
    TTC INTEGER)
RETURNS (
    ID_DOCUMENT_RECUP INTEGER,
    TAUX_RECUP NUMERIC(15,4),
    CODE_TVA_RECUP VARCHAR(20),
    MT_TVA_RECUP NUMERIC(15,2),
    LIBELLE_RECUP VARCHAR(100),
    VALEURTTC NUMERIC(15,2))
AS
DECLARE VARIABLE QUERY VARCHAR(1000);
DECLARE VARIABLE TYPELIGNE INTEGER;
DECLARE VARIABLE SUMHT NUMERIC(15,2);
DECLARE VARIABLE SUMTTC NUMERIC(15,2);
DECLARE VARIABLE TVA NUMERIC(15,4);
begin
-- je passe par une variable :tva en numeric(15,4) car autrement la valeur est tronqué sans arrondi
TYPELIGNE=0;
mt_tva_recup=0;
Query='';
select typeL.id_t_ligne from  ta_t_ligne typeL  where typeL.code_t_ligne = 'H' into :typeligne;
if (upper(:MODULE) = 'FACTURE') Then
   begin
        if (:ttc=1) then
            begin
                query ='select sum(ldoc.mt_ttc_l_facture),
                sum(ldoc.mt_ttc_l_facture)-((sum(ldoc.mt_ttc_l_facture)*100)/(100+ldoc.TAUX_TVA_L_FACTURE)),';
            end
        else
            begin
                query ='select sum(ldoc.mt_ht_l_facture),
                 sum(ldoc.mt_ht_l_facture)*(ldoc.TAUX_TVA_L_FACTURE/100),' ;
            end
        query = :query || ' ldoc.CODE_TVA_L_FACTURE,ldoc.TAUX_TVA_L_FACTURE
          from ta_l_facture_temp ldoc where ldoc.id_facture =' ||:id_document||
        'and ldoc.id_t_ligne = '||:typeligne||
          'group by ldoc.CODE_TVA_L_FACTURE,ldoc.TAUX_TVA_L_FACTURE
          order by ldoc.CODE_TVA_L_FACTURE,ldoc.TAUX_TVA_L_FACTURE';
   end
if (upper(:MODULE) = 'DEVIS') Then
   begin
        if (:ttc=1) then
            begin
                query ='select sum(ldoc.mt_ttc_l_devis),
                sum(ldoc.mt_ttc_l_devis)-((sum(ldoc.mt_ttc_l_devis)*100)/(100+ldoc.TAUX_TVA_L_devis)),';
            end
        else
            begin
                query ='select sum(ldoc.mt_ht_l_devis),
                 sum(ldoc.mt_ht_l_devis)*(ldoc.TAUX_TVA_L_devis/100),' ;
            end
        query = :query || ' ldoc.code_tva_l_devis,ldoc.taux_tva_l_devis
          from ta_l_devis_temp ldoc where ldoc.id_devis =' ||:id_document||
        'and ldoc.id_t_ligne = '||:typeligne||
          'group by ldoc.code_tva_l_devis,ldoc.taux_tva_l_devis
          order by ldoc.code_tva_l_devis,ldoc.taux_tva_l_devis';
   end
if (:ttc=1) then
    begin
        FOR execute statement :query
          into : sumttc,tva,code_tva_recup,taux_recup
          do
          begin
          if (:tauxremise is not null and :tauxremise > 0 ) then
            begin
                sumttc=:sumttc-(:sumttc*(:tauxremise/100));
                mt_tva_recup=cast( sumttc-(:sumttc*100) / (100+:taux_recup)as numeric (15,2));
            end
            select tva.LIBELLE_TVA from TA_TVA tva where tva.CODE_TVA = :code_tva_recup into :libelle_recup;
            id_document_recup=:id_document;
            valeurttc=:sumttc;
            mt_tva_recup = :mt_tva_recup + :tva;
            Suspend;
            mt_tva_recup = 0;
          end
    end
else
    begin
        FOR execute statement :query
          into : sumht,tva,code_tva_recup,taux_recup
          do
          begin
          if (:tauxremise is not null and :tauxremise > 0 ) then
            begin
                sumht=:sumht-(:sumht*(:tauxremise/100));
                mt_tva_recup= cast( :sumht * (:taux_recup/100)as numeric (15,2));
            end
            select tva.LIBELLE_TVA from TA_TVA tva where tva.CODE_TVA = :code_tva_recup into :libelle_recup;
            id_document_recup=:id_document;
            valeurttc=:sumht;
            mt_tva_recup = :mt_tva_recup + :tva;
            Suspend;
            mt_tva_recup = 0;
          end
    end
end
^




CREATE OR ALTER PROCEDURE CALCUL_TOTAL_TEMP (
    MODULE VARCHAR(255),
    ID_DOCUMENT INTEGER,
    TAUXREMISEHT NUMERIC(15,2),
    TTC INTEGER,
    TAUXESCOMPTE NUMERIC(15,2))
RETURNS (
    MT_HT NUMERIC(15,2),
    MT_TVA NUMERIC(15,2),
    MT_TTC NUMERIC(15,2),
    NETHT NUMERIC(15,2),
    NETTTC NUMERIC(15,2),
    NETTVA NUMERIC(15,2))
AS
DECLARE VARIABLE TYPELIGNE INTEGER;
begin
TYPELIGNE=0;
select typeL.id_t_ligne from  ta_t_ligne typeL where typeL.code_t_ligne = 'H' into :typeligne;
if (upper(:module)='FACTURE') then
begin
  select sum(ldoc.mt_ht_l_facture), sum(ldoc.mt_ttc_l_facture)
  from ta_l_facture_temp ldoc
  where ldoc.id_facture=:id_document and ldoc.id_t_ligne = :typeligne
  and ldoc.ip_acces = current_connection
   into :mt_ht, :mt_ttc;
end

if (upper(:module)='DEVIS') then
begin
  select sum(ldoc.mt_ht_l_devis), sum(ldoc.mt_ttc_l_devis)
  from ta_l_devis_temp ldoc
  where ldoc.id_devis=:id_document and ldoc.id_t_ligne = :typeligne
  and ldoc.ip_acces = current_connection
   into :mt_ht, :mt_ttc;
end
if (:mt_ttc<> 0 ) then
/* mt_ttc = :mt_ht;*/
mt_tva=0;
else
mt_tva=:mt_ttc -:mt_ht;
select sum(f.mt_tva_recup) from calcul_tva_sur_mt_ht(:module,:id_document,:tauxremiseht,:ttc) f into :nettva;

if (:ttc=1) then
    begin
       netttc=:mt_ttc - (:mt_ttc*(:tauxremiseht/100));
       netht=:netttc - :nettva;
    end
else
    begin
      netht=:mt_ht-(:mt_ht*(:tauxremiseht/100));
      netttc=:netht + :nettva;
    end
netttc=:netttc - (:netttc*(:tauxescompte/100));
suspend;
end
^





CREATE OR ALTER PROCEDURE CALCUL_TVA_TA_L_FACTURE (
    MODULE VARCHAR(255),
    ID_DOCUMENT INTEGER,
    EXPORT INTEGER)
RETURNS (
    ID_DOCUMENT_RECUP SMALLINT,
    TAUX_RECUP NUMERIC(15,4),
    CODE_TVA_RECUP VARCHAR(20),
    MT_TVA_RECUP NUMERIC(15,2),
    LIBELLE_RECUP VARCHAR(100),
    MT_HT NUMERIC(15,2),
    MT_TTC NUMERIC(15,2),
    TAUXREMISE NUMERIC(15,2),
    MT_HT_REMISE NUMERIC(15,2),
    MT_TTC_REMISE NUMERIC(15,2))
AS
DECLARE VARIABLE QUERY1 VARCHAR(1000);
DECLARE VARIABLE QUERY2 VARCHAR(1000);
DECLARE VARIABLE ID INTEGER;
DECLARE VARIABLE TYPELIGNE INTEGER;
DECLARE VARIABLE TTC SMALLINT;
begin 
TYPELIGNE=0;
select typeL.id_t_ligne from  ta_t_ligne typeL where typeL.code_t_ligne = 'H' into :typeligne;
if (upper(:module)='FACTURE') then
begin
query1 = 'select doc.id_facture,doc.tx_rem_ht_facture,doc.ttc from ta_facture doc
         where id_facture='||:id_document;
end
if (upper(:module)='DEVIS') then
begin
query1 = 'select doc.id_devis,doc.tx_rem_ht_devis,doc.ttc from ta_devis doc
         where id_devis='||:id_document;
end
    for  execute statement :query1
    into : id,tauxremise,ttc do
        begin
            if (upper(:module)='FACTURE') then
            begin
            query2 = 'select sum(ldoc.mt_ht_l_facture),sum(ldoc.mt_ttc_l_facture),
            ((sum(ldoc.mt_ht_l_facture)*ldoc.TAUX_TVA_L_FACTURE) /100),
                        ldoc.CODE_TVA_L_FACTURE,ldoc.TAUX_TVA_L_FACTURE
                        from ta_l_facture ldoc  where ldoc.id_facture ='|| :id||
                        'and ldoc.id_t_ligne = '||:typeligne||
                        'group by ldoc.CODE_TVA_L_FACTURE,ldoc.TAUX_TVA_L_FACTURE
                        order by ldoc.CODE_TVA_L_FACTURE,ldoc.TAUX_TVA_L_FACTURE';
            end
            if (upper(:module)='DEVIS') then
            begin
            query2 = 'select sum(ldoc.mt_ht_l_devis),sum(ldoc.mt_ttc_l_devis),
            ((sum(ldoc.mt_ht_l_devis)*ldoc.taux_tva_l_devis) /100),
                        ldoc.code_tva_l_devis,ldoc.taux_tva_l_devis
                        from ta_l_devis ldoc  where ldoc.id_devis ='|| :id||
                        'and ldoc.id_t_ligne = '||:typeligne||
                        'group by ldoc.code_tva_l_devis,ldoc.taux_tva_l_devis
                        order by ldoc.code_tva_l_devis,ldoc.taux_tva_l_devis';
            end
            FOR execute statement :query2
            into : mt_ht,mt_ttc,mt_tva_recup,code_tva_recup,taux_recup
            do begin
            mt_tva_recup = cast(:mt_tva_recup as numeric(15,2));
            if (:ttc=1) then
                begin
                    mt_ttc_remise=cast(:mt_ttc-(:mt_ttc*(:tauxremise/100)) as numeric(15,2));
                   /* if (:export = 1) then*/
                      mt_tva_recup=cast( :mt_ttc_remise-((:mt_ttc_remise*100)/(100+:taux_recup))as numeric (15,2));
                    mt_ht_remise=:mt_ttc_remise - :mt_tva_recup ;
                end
            else
                begin
                    mt_ht_remise=cast(:mt_ht-(:mt_ht*(:tauxremise/100)) as numeric(15,2));
                   /* if (:export = 1) then*/
                      mt_tva_recup=:mt_ht_remise * :taux_recup / 100;
                    mt_ttc_remise = :mt_ht_remise+ :mt_tva_recup ;
                end
        select tva.LIBELLE_TVA from TA_TVA tva where tva.CODE_TVA = :code_tva_recup
        into :libelle_recup; id_document_recup=:id;
        suspend;
        end
        end
end
^






CREATE OR ALTER PROCEDURE DISPATCHER_TVA_DOCUMENT (
    MODULE VARCHAR(255),
    ID_DOCUMENT INTEGER,
    TTC INTEGER,
    TAUXREMISE NUMERIC(15,2))
AS
DECLARE VARIABLE QUERY1 VARCHAR(1000);
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
           calcul_tva_sur_mt_ht(:module,:id_document,:tauxremise,:ttc) into :code_tva,taux,total_tva
      do
      begin
      if (upper(:module)='FACTURE') then
      begin
          select (sum(ldoc.mt_ttc_l_facture)),
          count(*) from ta_l_facture_temp ldoc where
          ldoc.code_tva_l_facture = :CODE_TVA and ldoc.taux_tva_l_facture = :TAUX
          and ldoc.id_t_ligne = :typeligne  and ldoc.ip_acces = current_connection
          into :totalttc, :NbLigne;
      end
      if (upper(:module)='DEVIS') then
      begin
          select (sum(ldoc.mt_ttc_l_devis)),
          count(*) from ta_l_devis_temp ldoc where
          ldoc.code_tva_l_devis = :CODE_TVA and ldoc.taux_tva_l_devis = :TAUX
          and ldoc.id_t_ligne = :typeligne  and ldoc.ip_acces = current_connection
          into :totalttc, :NbLigne;
      end
        lignepasse=1;
        TVATMP=0;
        TVATMP=:total_tva;
          if (upper(:module)='FACTURE') then
          begin
              query1 = 'select ldoc2.mt_ttc_l_facture,
                       ldoc2.id_l_facture from ta_l_facture_temp ldoc2 where
                       ldoc2.code_tva_l_facture = '''||:CODE_TVA||''' and ldoc2.taux_tva_l_facture ='|| :TAUX;
          end
          if (upper(:module)='DEVIS') then
          begin
              query1 = 'select ldoc2.mt_ttc_l_devis,
                       ldoc2.id_l_devis from ta_l_devis_temp ldoc2 where
                       ldoc2.code_tva_l_devis = '''||:CODE_TVA||''' and ldoc2.taux_tva_l_devis ='|| :TAUX;
          end
          query1 = :query1 || ' and ldoc2.id_t_ligne =' ||:typeligne||' and ldoc2.ip_acces = current_connection';
        For  execute statement :query1
             into :ttc_ligne, :id_ligne
        do 
            begin
              if (:totalttc=0) then tva_ligne=(:ttc_ligne * :taux)/100;
              else
                  begin
                       if  (lignepasse>= :NbLigne) then
                       tva_ligne = :TVATMP;
                       else
                       tva_ligne = (:total_tva * :ttc_ligne) / :totalttc;
                  end
               TVATMP =  :TVATMP -  :tva_ligne;
               if (upper(:module)='FACTURE') then
               begin
                  Update ta_l_facture_temp ldoc3 set ldoc3.mt_ht_l_facture = (:ttc_ligne - :tva_ligne)
                  where ldoc3.id_l_facture=:id_ligne and ldoc3.ip_acces = current_connection and ldoc3.id_t_ligne = :typeligne;
               end
               if (upper(:module)='DEVIS') then
               begin
                  Update ta_l_devis_temp ldoc3 set ldoc3.mt_ht_l_devis = (:ttc_ligne - :tva_ligne)
                  where ldoc3.id_l_devis=:id_ligne and ldoc3.ip_acces = current_connection and ldoc3.id_t_ligne = :typeligne;
               end
               lignepasse = :lignepasse + 1;
            end
      end
    end
  else
    begin
      FOR select CODE_TVA_RECUP, TAUX_RECUP, MT_TVA_RECUP FROM
            calcul_tva_sur_mt_ht(:module,:id_document,:tauxremise,:ttc) into :code_tva,taux,total_tva
      do
      begin
      if (upper(:module)='FACTURE') then
      begin
        select (sum(ldoc.mt_ht_l_facture)), count(*) from ta_l_facture_temp ldoc where
        ldoc.code_tva_l_facture = :CODE_TVA and ldoc.taux_tva_l_facture = :TAUX
        and ldoc.id_t_ligne = :typeligne  and ldoc.ip_acces = current_connection
        into :TotalHT, :NbLigne;
      end
      if (upper(:module)='DEVIS') then
      begin
        select (sum(ldoc.mt_ht_l_devis)), count(*) from ta_l_devis_temp ldoc where
        ldoc.code_tva_l_devis = :CODE_TVA and ldoc.taux_tva_l_devis = :TAUX
        and ldoc.id_t_ligne = :typeligne  and ldoc.ip_acces = current_connection
        into :TotalHT, :NbLigne;
      end
        lignepasse=1;
        TVATMP=0;
        TVATMP=:total_tva;
      if (upper(:module)='FACTURE') then
      begin
        query1 = 'select ldoc2.mt_ht_l_facture,
                  ldoc2.id_l_facture from ta_l_facture_temp ldoc2 where
                  ldoc2.code_tva_l_facture = '''||:CODE_TVA ||''' and ldoc2.taux_tva_l_facture = '|| :TAUX;
      end
      if (upper(:module)='DEVIS') then
      begin
        query1 = 'select ldoc2.mt_ht_l_devis,
                  ldoc2.id_l_devis from ta_l_devis_temp ldoc2 where
                  ldoc2.code_tva_l_devis = '''||:CODE_TVA ||''' and ldoc2.taux_tva_l_devis = '|| :TAUX;
      end
        query1 = :query1 || ' and ldoc2.id_t_ligne = '||:typeligne||' and ldoc2.ip_acces = current_connection';
        For execute statement :query1
        into :ht_ligne, :id_ligne
        do begin
           if (:totalht=0) then tva_ligne=(:ht_ligne * :taux)/100;
           else
               begin
                   if  (lignepasse>= :NbLigne) then
                   tva_ligne = :TVATMP;
                else
                   tva_ligne = (:total_tva * :ht_ligne) / :totalht;
               end
           TVATMP =  :TVATMP -  :tva_ligne;
          if (upper(:module)='FACTURE') then
          begin
           Update ta_l_facture_temp ldoc3 set ldoc3.mt_ttc_l_facture = (:ht_ligne + :tva_ligne)
              where ldoc3.id_l_facture=:id_ligne and ldoc3.ip_acces = current_connection and ldoc3.id_t_ligne = :typeligne;

          end
          if (upper(:module)='DEVIS') then
          begin
           Update ta_l_devis_temp ldoc3 set ldoc3.mt_ttc_l_devis = (:ht_ligne + :tva_ligne)
              where ldoc3.id_l_devis=:id_ligne and ldoc3.ip_acces = current_connection and ldoc3.id_t_ligne = :typeligne;
          end
           lignepasse = :lignepasse + 1;
        end
      end
    end
    suspend;
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
execute procedure dispatcher_tva_document('facture',0,:ttc,:V_REM_HT_L_FACTURE);
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




CREATE OR ALTER PROCEDURE MAJ_GENERATEUR 
AS
DECLARE VARIABLE V_GEN INTEGER;
DECLARE VARIABLE V_MAX INTEGER;
begin
select max(alias.ID_TIERS) from TA_TIERS alias into :V_MAX;if (v_max is null)
then v_max = 0; V_GEN = gen_id(NUM_ID_TIERS, 0); V_GEN = gen_id(NUM_ID_TIERS, V_MAX - V_GEN);
select max(alias.id_adresse) from ta_adresse alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_adresse, 0); V_GEN = gen_id(num_id_adresse, V_MAX - V_GEN);
select max(alias.id_article) from ta_article alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_article, 0); V_GEN = gen_id(num_id_article, V_MAX - V_GEN);
select max(alias.id_avoir) from ta_avoir alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_avoir, 0); V_GEN = gen_id(num_id_avoir, V_MAX - V_GEN);
select max(alias.id_banque) from ta_banque alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_banque, 0); V_GEN = gen_id(num_id_banque, V_MAX - V_GEN);
select max(alias.id_boncde) from ta_boncde alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_boncde, 0); V_GEN = gen_id(num_id_boncde, V_MAX - V_GEN);
select max(alias.id_bonliv) from ta_bonliv alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_bonliv, 0); V_GEN = gen_id(num_id_bonliv, V_MAX - V_GEN);
select max(alias.id_c_paiement) from ta_c_paiement alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_c_paiement, 0); V_GEN = gen_id(num_id_c_paiement, V_MAX - V_GEN);
select max(alias.id_com_doc) from ta_com_doc alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_com_doc, 0); V_GEN = gen_id(num_id_com_doc, V_MAX - V_GEN);
select max(alias.id_commentaire) from ta_commentaire alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_commentaire, 0); V_GEN = gen_id(num_id_commentaire, V_MAX - V_GEN);
select max(alias.id_compl) from ta_compl alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_compl, 0); V_GEN = gen_id(num_id_compl, V_MAX - V_GEN);
select max(alias.id_devis) from ta_devis alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_devis, 0); V_GEN = gen_id(num_id_devis, V_MAX - V_GEN);
select max(alias.id_email) from ta_email alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_email, 0); V_GEN = gen_id(num_id_email, V_MAX - V_GEN);
select max(alias.id_entreprise) from ta_entreprise alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_entreprise, 0); V_GEN = gen_id(num_id_entreprise, V_MAX - V_GEN);
select max(alias.id_facture) from ta_facture alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_facture, 0); V_GEN = gen_id(num_id_facture, V_MAX - V_GEN);
select max(alias.id_famille) from ta_famille alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_famille, 0); V_GEN = gen_id(num_id_famille, V_MAX - V_GEN);
select max(alias.id_l_avoir) from ta_l_avoir alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_l_avoir, 0); V_GEN = gen_id(num_id_l_avoir, V_MAX - V_GEN);
select max(alias.id_l_boncde) from ta_l_boncde alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_l_boncde, 0); V_GEN = gen_id(num_id_l_boncde, V_MAX - V_GEN);
select max(alias.id_l_bonliv) from ta_l_bonliv alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_l_bonliv, 0); V_GEN = gen_id(num_id_l_bonliv, V_MAX - V_GEN);
select max(alias.id_l_devis) from ta_l_devis alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_l_devis, 0); V_GEN = gen_id(num_id_l_devis, V_MAX - V_GEN);
select max(alias.id_l_facture) from ta_l_facture alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_l_facture, 0); V_GEN = gen_id(num_id_l_facture, V_MAX - V_GEN);
select max(alias.id_l_modele) from ta_l_modele alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_l_modele, 0); V_GEN = gen_id(num_id_l_modele, V_MAX - V_GEN);
select max(alias.id_prix) from ta_prix alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_prix, 0); V_GEN = gen_id(num_id_prix, V_MAX - V_GEN);
select max(alias.id_r_adr) from ta_r_adr alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_r_adr, 0); V_GEN = gen_id(num_id_r_adr, V_MAX - V_GEN);
select max(alias.id_r_adr_t_adr) from ta_r_adr_t_adr alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_r_adr_t_adr, 0); V_GEN = gen_id(num_id_r_adr_t_adr, V_MAX - V_GEN);
select max(alias.id_r_document) from ta_r_document alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_r_document, 0); V_GEN = gen_id(num_id_r_document, V_MAX - V_GEN);
select max(alias.id_r_email) from ta_r_email alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_r_email, 0); V_GEN = gen_id(num_id_r_email, V_MAX - V_GEN);
select max(alias.id_r_tel) from ta_r_tel alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_r_tel, 0); V_GEN = gen_id(num_id_r_tel, V_MAX - V_GEN);
select max(alias.id_r_tel_t_tel) from ta_r_tel_t_tel alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_r_tel_t_tel, 0); V_GEN = gen_id(num_id_r_tel_t_tel, V_MAX - V_GEN);
select max(alias.id_r_web) from ta_r_web alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_r_web, 0); V_GEN = gen_id(num_id_r_web, V_MAX - V_GEN);
select max(alias.id_ref_prix) from ta_ref_prix alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_ref_prix, 0); V_GEN = gen_id(num_id_ref_prix, V_MAX - V_GEN);
select max(alias.id_t_adr) from ta_t_adr alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_t_adr, 0); V_GEN = gen_id(num_id_t_adr, V_MAX - V_GEN);
select max(alias.id_t_civilite) from ta_t_civilite alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_t_civilite, 0); V_GEN = gen_id(num_id_t_civilite, V_MAX - V_GEN);
select max(alias.id_t_doc) from ta_t_doc alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_t_doc, 0); V_GEN = gen_id(num_id_t_doc, V_MAX - V_GEN);
select max(alias.id_t_entite) from ta_t_entite alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_t_entite, 0); V_GEN = gen_id(num_id_t_entite, V_MAX - V_GEN);
select max(alias.id_t_ligne) from ta_t_ligne alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_t_ligne, 0); V_GEN = gen_id(num_id_t_ligne, V_MAX - V_GEN);
select max(alias.id_t_paiement) from ta_t_paiement alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_t_paiement, 0); V_GEN = gen_id(num_id_t_paiement, V_MAX - V_GEN);
select max(alias.id_t_tel) from ta_t_tel alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_t_tel, 0); V_GEN = gen_id(num_id_t_tel, V_MAX - V_GEN);
select max(alias.id_t_tiers) from ta_t_tiers alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_t_tiers, 0); V_GEN = gen_id(num_id_t_tiers, V_MAX - V_GEN);
select max(alias.id_t_tva) from ta_t_tva alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_t_tva, 0); V_GEN = gen_id(num_id_t_tva, V_MAX - V_GEN);
select max(alias.id_telephone) from ta_telephone alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_telephone, 0); V_GEN = gen_id(num_id_telephone, V_MAX - V_GEN);
select max(alias.id_tva) from ta_tva alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_tva, 0); V_GEN = gen_id(num_id_tva, V_MAX - V_GEN);
select max(alias.id_acces) from ta_acces alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_acces, 0); V_GEN = gen_id(num_id_acces, V_MAX - V_GEN);
select max(alias.id_infos_facture) from ta_infos_facture alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_infos_facture, 0); V_GEN = gen_id(num_id_infos_facture, V_MAX - V_GEN);
select max(alias.id_mail_maj) from ta_mail_maj alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_mail_maj, 0); V_GEN = gen_id(num_id_mail_maj, V_MAX - V_GEN);
select max(alias.id_modele) from ta_modele alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_modele, 0); V_GEN = gen_id(num_id_modele, V_MAX - V_GEN);
select max(alias.id_modif) from ta_modif alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_modif, 0); V_GEN = gen_id(num_id_modif, V_MAX - V_GEN);
select max(alias.id_unite) from ta_unite alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_unite, 0); V_GEN = gen_id(num_id_unite, V_MAX - V_GEN);
select max(alias.id_web) from ta_web alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_web, 0); V_GEN = gen_id(num_id_web, V_MAX - V_GEN);
select max(alias.id_R_COMMERCIAL) from ta_R_COMMERCIAL alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_R_COMMERCIAL, 0); V_GEN = gen_id(num_ID_R_COMMERCIAL, V_MAX - V_GEN);
end
^




CREATE or ALTER  PROCEDURE VIDE_DOCUMENT_TEMP(
module VARCHAR(255))
as
begin
if (upper(:module) = 'FACTURE') then
  delete from ta_l_facture_temp ta where ta.ip_acces = current_connection;
if (upper(:module) = 'DEVIS') then
  delete from ta_l_devis_temp ta where ta.ip_acces = current_connection ;
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
execute procedure dispatcher_tva_document('facture',:id_facture_recup,:ttc,:V_REM_HT_L_FACTURE);
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

   execute procedure vide_document_temp('facture');
    suspend;
end
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
execute procedure vide_document_temp('facture');

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



CREATE OR ALTER PROCEDURE RECUP_IP_ACCES
RETURNS (
    IP_ACCES VARCHAR(50))
AS
begin
  ip_acces = current_connection;
  suspend;
end
^



CREATE OR ALTER PROCEDURE ANNULE_MODIFICATION (
    NOMTABLE VARCHAR(255),
    NOMCHAMP VARCHAR(255),
    VALEUR VARCHAR(255))
AS
begin 
delete from ta_modif where
upper(table_modif) =upper(:nomtable) and
upper(champ_modif) =upper(:nomchamp) and
upper(valeur_modif) =upper(:valeur)and
upper(ip_acces)=current_connection;
suspend;
end
^



CREATE OR ALTER PROCEDURE AUTORISE_MODIF (
    NOMTABLE VARCHAR(255),
    NOMCHAMP VARCHAR(255),
    VALEUR VARCHAR(255))
RETURNS (
    RETOUR INTEGER)
AS
begin
  select count(*) from ta_modif modif where upper(modif.table_modif) = upper(:nomtable)
  and upper(modif.champ_modif) =upper(:nomchamp)
  and  upper(modif.valeur_modif) =upper(:valeur) into :retour;
  suspend;
end
^



CREATE OR ALTER PROCEDURE RENTRE_EN_MODIFICATION (
    NOMTABLE VARCHAR(255),
    NOMCHAMP VARCHAR(255),
    VALEUR VARCHAR(255))
AS
begin
if (:valeur is not null) then
    if (not exists(select * from ta_modif modif where table_modif=upper(:nomtable) and
    champ_modif=upper(:nomchamp)and valeur_modif=upper(:valeur))) then
        insert into ta_modif
         (table_modif,champ_modif,valeur_modif)
         values (upper(:nomtable),upper(:nomchamp),upper(:valeur))  ;
  suspend;
end
^




CREATE OR ALTER PROCEDURE ENREGISTRE_ACCES
AS
begin
  if (not exists
  (select acces2.ip_acces from ta_acces acces2 where acces2.ip_acces=current_connection
  and acces2.user_acces=user)) then
  begin
    insert into ta_acces (user_acces)
    values (user) ;
  end
end
^



CREATE OR ALTER PROCEDURE NETTOYAGE 
AS
begin
delete from ta_modif modif where not exists
  (select ip_acces from ta_acces acces where acces.ip_acces=modif.ip_acces);
delete from ta_l_facture_temp temp where  not exists
  (select ip_acces from ta_acces acces where acces.ip_acces=temp.ip_acces);
delete from ta_l_devis_temp temp where  not exists
  (select ip_acces from ta_acces acces where acces.ip_acces=temp.ip_acces);
end
^



CREATE OR ALTER PROCEDURE SUPPRESSION_ACCES
AS
begin
  delete from ta_acces where ip_acces=current_connection;
end
^


CREATE OR ALTER PROCEDURE RAZ_ACCES
AS
begin
delete from ta_acces acces ;
suspend;
end
^





CREATE OR ALTER PROCEDURE RECORD_MODIFIABLE (
    NOM_TABLE VARCHAR(100),
    IDTABLE INTEGER)
RETURNS (
    NB INTEGER)
AS
begin
if (upper(:NOM_TABLE) = upper('ta_adresse'))   then
    begin
        nb=0;
        select count(*) from ta_facture facture where facture.ID_ADRESSE = :idtable into nb;
        if (:nb<=0)then
           select count(*) from ta_facture facture where facture.ID_ADRESSE_LIV = :idtable into nb;
        suspend;
    end
else
if (upper(:NOM_TABLE) = upper('ta_tiers'))   then
    begin
        nb=0;
        select count(*) from ta_facture facture where facture.id_tiers = :idtable into nb  ;
        suspend;
    end
else
if (upper(:NOM_TABLE) = upper('ta_article'))   then
    begin
        nb=0;
        select count(*) from ta_L_facture Lfacture where Lfacture.id_ARTICLE = :idtable into nb  ;
        suspend;
    end
else
if (upper(:NOM_TABLE) = upper('ta_T_PAIEMENT'))   then
    begin
        nb=0;
        select count(*) from ta_facture facture where facture.ID_T_PAIEMENT = :idtable into nb  ;
        suspend;
    end
else
if (upper(:NOM_TABLE) = upper('ta_C_PAIEMENT'))   then
    begin
        nb=0;
        select count(*) from ta_facture facture where facture.ID_C_PAIEMENT = :idtable into nb  ;
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
        select count(*) from ta_l_facture lfacture where lfacture.id_t_ligne = :idtable into nb  ;
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





CREATE OR ALTER PROCEDURE MAJ_NUM_LIGNE (
    MODULE VARCHAR(255),
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
if (upper(:module)='FACTURE') then
    begin
     if(:suppr=0) then
         begin
         for select ID_L_FACTURE, NUM_LIGNE_L_FACTURE from ta_l_facture_temp lfacttemp 
         where ip_acces=current_connection
         order by lfacttemp.num_ligne_l_facture asc,
         lfacttemp.quand_cree_l_facture desc
         into :id, :num do
             begin
                if(:maj=1) then
                    begin
                        update ta_l_facture_temp set NUM_LIGNE_L_FACTURE=(NUM_LIGNE_L_FACTURE+1) where ID_L_FACTURE = :id;
                    end
                if(:num_ligne=:num) then
                    begin
                        maj= 1;
                    end
             end
         end 
     else
         begin
             for select ID_L_FACTURE, NUM_LIGNE_L_FACTURE from ta_l_facture_temp lfacttemp 
             where ip_acces=current_connection
             order by lfacttemp.num_ligne_l_facture asc,
         lfacttemp.quand_cree_l_facture desc
         into :id, :num do
            begin
                update ta_l_facture_temp set NUM_LIGNE_L_FACTURE=(:inc) where ID_L_FACTURE = :id;
                inc=inc+1;
            end
         end
    
     maj=0;
     for select ID_L_FACTURE, NUM_LIGNE_L_FACTURE from ta_l_facture_temp lfacttemp 
     where ip_acces=current_connection
     order by lfacttemp.num_ligne_l_facture asc
     into :id, :num do
         begin
            update ta_l_facture_temp set NUM_LIGNE_L_FACTURE=:maj where ID_L_FACTURE = :id;
            maj=maj+1;
         end
    end
if (upper(:module)='DEVIS') then
    begin
     if(:suppr=0) then
         begin
         for select ID_L_DEVIS, NUM_LIGNE_L_DEVIS from ta_l_DEVIS_temp ldoc
         where ip_acces=current_connection
         order by ldoc.num_ligne_l_devis asc,
         ldoc.quand_cree_l_devis desc
         into :id, :num do
             begin
                if(:maj=1) then
                    begin
                        update ta_l_devis_temp set NUM_LIGNE_L_devis=(NUM_LIGNE_L_devis+1) where ID_L_devis = :id;
                    end
                if(:num_ligne=:num) then
                    begin
                        maj= 1;
                    end
             end
         end 
     else
         begin
             for select ID_L_devis, NUM_LIGNE_L_devis from ta_l_devis_temp ldoc
             where ip_acces=current_connection
             order by ldoc.num_ligne_l_devis asc,
         ldoc.quand_cree_l_devis desc
         into :id, :num do
            begin
                update ta_l_devis_temp set NUM_LIGNE_L_devis=(:inc) where ID_L_devis = :id;
                inc=inc+1;
            end
         end
    
     maj=0;
     for select ID_L_devis, NUM_LIGNE_L_devis from ta_l_devis_temp ldoc
     where ip_acces=current_connection
     order by ldoc.num_ligne_l_devis asc
     into :id, :num do
         begin
            update ta_l_devis_temp set NUM_LIGNE_L_devis=:maj where ID_L_devis = :id;
            maj=maj+1;
         end
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
  FROM  calcul_tva_ta_l_facture('facture',:ID_FACTURE_RECUP,1)calc into
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
                             if (:total_ttc_remise=0 and :totalttc<>0) then
                             begin
                                 mt_ttc_l_facture=0;
                                 tva_ligne =0;
                             end
                             else
                               tva_ligne = (:mt_ttc_l_facture * :taux)/100 ;
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
                             if (:total_ht_remise=0 and :totalht<>0) then
                             begin
                                 mt_ht_l_facture=0;
                                 tva_ligne =0;
                             end
                             else
                               tva_ligne = (:mt_ht_l_facture * :taux)/100;
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
     select calc.mt_ttc-calc.mtnetttc,calc.mtnetttc from CALCUL_TOTAL_DIRECT('facture',:id_fact_courant) calc
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
     cpt_collectif ='';
     nom_tiers ='';
     adresse_1_tiers ='';
     adresse_2_tiers ='';
     code_postal_tiers ='';
     ville_tiers='';
     
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
     execute procedure vide_document_temp('facture');
   end /* fin test deja exportee */
 end /* fin boucle sur facture */
end
^



CREATE OR ALTER PROCEDURE CALCUL_TVA_DIRECT (
    MODULE VARCHAR(255),
    ID_DOCUMENT INTEGER,
    TAUXREMISE NUMERIC(15,2),
    TTC INTEGER)
RETURNS (
    ID_DOCUMENT_RECUP INTEGER,
    TAUX_RECUP NUMERIC(15,4),
    CODE_TVA_RECUP VARCHAR(20),
    MT_TVA_RECUP NUMERIC(15,2),
    LIBELLE_RECUP VARCHAR(100),
    VALEURTTC NUMERIC(15,2))
AS
DECLARE VARIABLE TYPELIGNE INTEGER;
DECLARE VARIABLE SUMHT NUMERIC(15,2);
DECLARE VARIABLE SUMTTC NUMERIC(15,2);
DECLARE VARIABLE QUERY VARCHAR(1000);
DECLARE VARIABLE TVA NUMERIC(15,4);
begin
TYPELIGNE=0;
mt_tva_recup = 0;
query = '';
select typeL.id_t_ligne from  ta_t_ligne typeL  where typeL.code_t_ligne = 'H' into :typeligne;
if (upper(:module) = 'FACTURE') then
begin
if (:ttc=1) then
    begin
     query = 'select sum(ldoc.mt_ttc_l_facture),
        sum(ldoc.mt_ttc_l_facture)-((sum(ldoc.mt_ttc_l_facture)*100)/(100+ldoc.TAUX_TVA_L_FACTURE)),
        ldoc.CODE_TVA_L_FACTURE,ldoc.TAUX_TVA_L_FACTURE';
    end
else
    begin
     query = 'select sum(ldoc.mt_ht_l_facture),
         sum(ldoc.mt_ht_l_facture)*(ldoc.TAUX_TVA_L_FACTURE/100),
        ldoc.CODE_TVA_L_FACTURE,ldoc.TAUX_TVA_L_FACTURE';
    end
query = :query || ' from ta_l_facture ldoc where ldoc.id_facture =' ||:id_document||
        'and ldoc.id_t_ligne =' ||:typeligne||
          'group by ldoc.CODE_TVA_L_FACTURE,ldoc.TAUX_TVA_L_FACTURE
          order by ldoc.CODE_TVA_L_FACTURE,ldoc.TAUX_TVA_L_FACTURE';
end

if (upper(:module) = 'DEVIS') then
begin
if (:ttc=1) then
    begin
     query = 'select sum(ldoc.mt_ttc_l_devis),
        sum(ldoc.mt_ttc_l_devis)-((sum(ldoc.mt_ttc_l_devis)*100)/(100+ldoc.TAUX_TVA_L_devis)),
        ldoc.CODE_TVA_L_devis,ldoc.TAUX_TVA_L_devis';
    end
else
    begin
     query = 'select sum(ldoc.mt_ht_l_devis),
         sum(ldoc.mt_ht_l_devis)*(ldoc.TAUX_TVA_L_devis/100),
        ldoc.CODE_TVA_L_devis,ldoc.TAUX_TVA_L_devis';
    end
query = :query || ' from ta_l_devis ldoc where ldoc.id_devis =' ||:id_document||
        'and ldoc.id_t_ligne =' ||:typeligne||
          'group by ldoc.code_tva_l_devis,ldoc.taux_tva_l_devis
          order by ldoc.code_tva_l_devis,ldoc.taux_tva_l_devis';
end
if (:ttc=1) then
    begin
        FOR execute statement :query
          into : sumttc,tva,code_tva_recup,taux_recup
          do
          begin
          if (:tauxremise is not null and :tauxremise > 0 ) then
            begin
                sumttc=:sumttc-(:sumttc*(:tauxremise/100));
                mt_tva_recup=cast( sumttc-(:sumttc*100) / (100+:taux_recup)as numeric (15,2));
            end
            select tva.LIBELLE_TVA from TA_TVA tva where tva.CODE_TVA = :code_tva_recup into :libelle_recup;
            id_document_recup=:id_document;
            valeurttc=:sumttc;
            mt_tva_recup = :mt_tva_recup + :tva;
            Suspend;
            mt_tva_recup=0;
          end
    end
else
    begin
        FOR execute statement :query
          into : sumht,tva,code_tva_recup,taux_recup
          do
          begin
          if (:tauxremise is not null and :tauxremise > 0 ) then
            begin
                sumht=:sumht-(:sumht*(:tauxremise/100));
                mt_tva_recup= cast( :sumht * (:taux_recup/100)as numeric (15,2));
            end
            select tva.LIBELLE_TVA from TA_TVA tva where tva.CODE_TVA = :code_tva_recup into :libelle_recup;
            id_document_recup=:id_document;
            valeurttc=:sumht;
            mt_tva_recup = :mt_tva_recup + :tva;
            Suspend;
            mt_tva_recup=0;
          end
    end
end
^



CREATE OR ALTER PROCEDURE CALCUL_TOTAL_DIRECT (
    MODULE VARCHAR(255),
    ID_DOCUMENT INTEGER)
RETURNS (
    MT_HT NUMERIC(15,2),
    MT_TVA NUMERIC(15,2),
    MT_TTC NUMERIC(15,2),
    TAUX_R_HT NUMERIC(15,4),
    TAUX_R_TTC NUMERIC(15,4),
    REGLE NUMERIC(15,2),
    REMISE_HT NUMERIC(15,2),
    REMISE_TTC NUMERIC(15,2),
    MTNETHT NUMERIC(15,2),
    MTNETTTC NUMERIC(15,2),
    MTNETTVA NUMERIC(15,2),
    NETAPAYER NUMERIC(15,2),
    TVA NUMERIC(15,2))
AS
DECLARE VARIABLE TYPELIGNE INTEGER;
DECLARE VARIABLE TTC INTEGER;
DECLARE VARIABLE TOTALTTC NUMERIC(15,2);
begin
TYPELIGNE=0;
select typeL.id_t_ligne from  ta_t_ligne typeL where typeL.code_t_ligne = 'H' into :typeligne;
if (upper(:module) = 'FACTURE') then
begin
select sum(ldoc.mt_ht_l_facture),sum(ldoc.mt_ttc_l_facture),doc.regle_facture,
  doc.tx_rem_ht_facture, doc.tx_rem_ttc_facture, doc.rem_ht_facture, doc.rem_ttc_facture,
  sum(ldoc.mt_ht_l_facture)-doc.rem_ht_facture,doc.ttc
  from ta_l_facture ldoc, ta_facture doc
  where ldoc.id_facture=:id_document and  doc.id_facture=:id_document
  and ldoc.id_t_ligne = :typeligne
  group by doc.regle_facture,
  doc.tx_rem_ht_facture, doc.tx_rem_ttc_facture, doc.rem_ht_facture, doc.rem_ttc_facture
  ,doc.ttc
   into :mt_ht ,:totalttc ,:regle,:taux_r_ht,:taux_r_ttc,:remise_ht,:remise_ttc,:mtnetht,:ttc;
end
if (upper(:module) = 'DEVIS') then
begin
select sum(ldoc.mt_ht_l_devis),sum(ldoc.mt_ttc_l_devis),'false',
  doc.tx_rem_ht_devis, doc.tx_rem_ttc_devis, doc.rem_ht_devis, doc.rem_ttc_devis,
  sum(ldoc.mt_ht_l_devis)-doc.rem_ht_devis,doc.ttc
  from ta_l_devis ldoc, ta_devis doc
  where ldoc.id_devis=:id_document and  doc.id_devis=:id_document
  and ldoc.id_t_ligne = :typeligne
  group by 'false',
  doc.tx_rem_ht_devis, doc.tx_rem_ttc_devis, doc.rem_ht_devis, doc.rem_ttc_devis
  ,doc.ttc
   into :mt_ht ,:totalttc ,:regle,:taux_r_ht,:taux_r_ttc,:remise_ht,:remise_ttc,:mtnetht,:ttc;
end

select sum(f.mt_tva_recup) from calcul_tva_direct(:module,:id_document,:taux_r_ht,:ttc) f into :MTNETTVA;
tva=:mtnettva;
mt_ttc=:totalttc;
mt_tva=:mt_ttc-:mt_ht;

if (:ttc=1) then
    begin
       mt_ttc=:mt_ttc - (:mt_ttc*(:taux_r_ht/100));
       MTNETHT=:mt_ttc - :MTNETTVA;
       remise_ht =  :totalttc - :mt_ttc ;
    end
else
    begin
      MTNETHT=:mt_ht-(:mt_ht*(:taux_r_ht/100));
      mt_ttc=:MTNETHT + :MTNETTVA;
      remise_ht = mt_ht - mtnetht;
    end
  mtnetttc = :mt_ttc-:remise_ttc;
  netapayer = :mtnetttc - :regle;

suspend;
end
^




CREATE OR ALTER PROCEDURE INVERSE_NUM_LIGNE (
    MODULE VARCHAR(255),
    NUM_LIGNE INTEGER,
    NEWLIGNE INTEGER,
    ID_LIGNE_DEPART INTEGER)
AS
DECLARE VARIABLE ID_LIGNE INTEGER;
DECLARE VARIABLE OLDLIGNE INTEGER;
DECLARE VARIABLE DESCENDANT INTEGER;
begin
if (upper(:module)='FACTURE') then
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
    end
if (upper(:module)='DEVIS') then
    begin
    select NUM_LIGNE_L_DEVIS from ta_l_devis_temp where ID_L_DEVIS = :ID_LIGNE_DEPART
    and ip_acces=current_connection  into :oldligne;
    if((:oldligne < :NEWLIGNE))then
        begin
        for select id_l_DEVIS from ta_l_DEVIS_temp where num_ligne_l_DEVIS between :oldligne
        and :NEWLIGNE and ip_acces=current_connection
        order by num_ligne_l_DEVIS desc into :id_ligne  do
            begin
                   if(:id_ligne <> :ID_LIGNE_DEPART) then
                     update ta_l_DEVIS_temp set NUM_LIGNE_L_DEVIS=(NUM_LIGNE_L_DEVIS-1) where ID_L_DEVIS = :id_ligne;
                   else
                     update ta_l_DEVIS_temp set NUM_LIGNE_L_DEVIS=(:NEWLIGNE) where ID_L_DEVIS = :id_ligne;
            end
        end
    else
        begin
        for select id_l_DEVIS from ta_l_DEVIS_temp where num_ligne_l_DEVIS between :NEWLIGNE
        and :oldligne  and ip_acces=current_connection
        order by num_ligne_l_DEVIS asc into :id_ligne  do
            begin
                   if(:id_ligne <> :ID_LIGNE_DEPART) then  /*  */
                     update ta_l_DEVIS_temp set NUM_LIGNE_L_DEVIS=(NUM_LIGNE_L_DEVIS+1) where ID_L_DEVIS = :id_ligne;
                   else
                     update ta_l_DEVIS_temp set NUM_LIGNE_L_DEVIS=(:NEWLIGNE) where ID_L_DEVIS = :id_ligne;
            end
        end
    end
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


/* Rajouté par isa le 19/10/2007 */
CREATE OR ALTER PROCEDURE MISEAJOUR_PARAM_ARTICLE (
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




CREATE OR ALTER PROCEDURE ENREGISTRE_LIGNES_DEVIS (
    NEW_ID_DEVIS INTEGER,
    TTC INTEGER)
AS
DECLARE VARIABLE V_ID_L_DOCUMENT INTEGER;
DECLARE VARIABLE V_ID_DOCUMENT INTEGER;
DECLARE VARIABLE V_ID_T_LIGNE INTEGER;
DECLARE VARIABLE V_ID_ARTICLE INTEGER;
DECLARE VARIABLE V_NUM_LIGNE_L_DOCUMENT INTEGER;
DECLARE VARIABLE V_LIB_L_DOCUMENT VARCHAR(255) CHARACTER SET NONE;
DECLARE VARIABLE V_QTE_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE V_U1_L_DOCUMENT VARCHAR(20) CHARACTER SET NONE;
DECLARE VARIABLE V_U2_L_DOCUMENT VARCHAR(20) CHARACTER SET NONE;
DECLARE VARIABLE V_PRIX_U_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE V_TAUX_TVA_L_DOCUMENT NUMERIC(15,4);
DECLARE VARIABLE V_CODE_TVA_L_DOCUMENT VARCHAR(20) CHARACTER SET NONE;
DECLARE VARIABLE V_MT_HT_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE V_MT_TTC_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE V_REM_TX_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE V_REM_HT_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE TYPELIGNE INTEGER;
DECLARE VARIABLE V_CODE_T_TVA_L_DOCUMENT VARCHAR(1);
DECLARE VARIABLE V_COMPTE_L_DOCUMENT VARCHAR(8);
begin
TYPELIGNE=0;
select typeL.id_t_ligne from  ta_t_ligne typeL where typeL.code_t_ligne = 'H' into :typeligne;
select f.tx_rem_ht_devis from ta_devis f where f.id_devis=:new_id_devis into :v_rem_ht_l_document;
execute procedure dispatcher_tva_document('devis',0,:ttc,:v_rem_ht_l_document);
FOR select ldevis.id_l_devis,
   ldevis.id_devis,
    ldevis.id_t_ligne, ldevis.id_article, ldevis.num_ligne_l_devis,
ldevis.lib_l_devis, ldevis.qte_l_devis, ldevis.u1_l_devis, ldevis.u2_l_devis,
ldevis.prix_u_l_devis, ldevis.taux_tva_l_devis,ldevis.compte_l_devis, ldevis.code_tva_l_devis,
ldevis.code_t_tva_l_devis, ldevis.mt_ht_l_devis,
ldevis.mt_ttc_l_devis, ldevis.rem_tx_l_devis, ldevis.rem_ht_l_devis
from ta_l_devis_temp ldevis
where ldevis.ip_acces = current_connection
into
    :v_id_l_document,
    :v_id_document,
    :V_ID_T_LIGNE,
    :V_ID_ARTICLE,
    :v_num_ligne_l_document,
    :v_lib_l_document,
    :v_qte_l_document,
    :v_u1_l_document,
    :v_u2_l_document,
    :v_prix_u_l_document,
    :v_taux_tva_l_document,
    :v_compte_l_document,
    :v_code_tva_l_document,
    :v_code_t_tva_l_document,
    :v_mt_ht_l_document,
    :v_mt_ttc_l_document,
    :v_rem_tx_l_document,
    :v_rem_ht_l_document
do
  begin
     if (:V_ID_T_LIGNE <> :typeligne) then
        begin
            v_qte_l_document=null;
            v_prix_u_l_document=null;
            v_taux_tva_l_document=null;
            v_compte_l_document=null;
            v_code_tva_l_document=null;
            v_code_t_tva_l_document=null;
            v_mt_ht_l_document=null;
            v_mt_ttc_l_document=null;
            v_rem_tx_l_document=null;
            v_rem_ht_l_document=null;
        end
    select art.numcpt_article from ta_article art where art.id_article = :v_id_article
    into :v_compte_l_document;
     insert into ta_l_devis (
    ID_L_devis,
    ID_devis,
    ID_T_LIGNE,
    ID_ARTICLE,
    NUM_LIGNE_L_devis,
    LIB_L_devis,
    QTE_L_devis,
    U1_L_devis,
    U2_L_devis,
    PRIX_U_L_devis,
    TAUX_TVA_L_devis,
    compte_l_devis,
    CODE_TVA_L_devis,
    CODE_T_TVA_L_devis,
    MT_HT_L_devis,
    MT_TTC_L_devis,
    REM_TX_L_devis,
    REM_HT_L_devis
    ) values(
    :v_id_l_document,
    :new_id_devis,
    :V_ID_T_LIGNE,
    :V_ID_ARTICLE,
    :v_num_ligne_l_document,
    :v_lib_l_document,
    :v_qte_l_document,
    :v_u1_l_document,
    :v_u2_l_document,
    :v_prix_u_l_document,
    :v_taux_tva_l_document,
    :v_compte_l_document,
    :v_code_tva_l_document,
    :v_code_t_tva_l_document,
    :v_mt_ht_l_document,
    :v_mt_ttc_l_document,
    :v_rem_tx_l_document,
    :v_rem_ht_l_document) ;
    delete from ta_l_devis_temp where ID_L_devis = :v_id_l_document and
    ip_acces=current_connection;
  end
  suspend;
end
^



CREATE OR ALTER PROCEDURE MAJ_LIGNES_DEVIS (
    ID_DOCUMENT_RECUP INTEGER,
    TTC INTEGER)
AS
DECLARE VARIABLE ID_L_DOCUMENT_COURANT INTEGER;
DECLARE VARIABLE V_ID_L_DOCUMENT INTEGER;
DECLARE VARIABLE V_ID_DOCUMENT INTEGER;
DECLARE VARIABLE V_ID_T_LIGNE INTEGER;
DECLARE VARIABLE V_ID_ARTICLE INTEGER;
DECLARE VARIABLE V_NUM_LIGNE_L_DOCUMENT INTEGER;
DECLARE VARIABLE V_LIB_L_DOCUMENT VARCHAR(255) CHARACTER SET NONE;
DECLARE VARIABLE V_QTE_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE V_U1_L_DOCUMENT VARCHAR(20) CHARACTER SET NONE;
DECLARE VARIABLE V_U2_L_DOCUMENT VARCHAR(20) CHARACTER SET NONE;
DECLARE VARIABLE V_PRIX_U_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE V_TAUX_TVA_L_DOCUMENT NUMERIC(15,4);
DECLARE VARIABLE V_CODE_TVA_L_DOCUMENT VARCHAR(20) CHARACTER SET NONE;
DECLARE VARIABLE V_MT_HT_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE V_MT_TTC_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE V_REM_TX_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE V_REM_HT_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE TYPELIGNE INTEGER;
DECLARE VARIABLE V_CODE_T_TVA_L_DOCUMENT VARCHAR(1);
DECLARE VARIABLE V_COMPTE_L_DOCUMENT VARCHAR(8);
begin
TYPELIGNE=0;
    delete from ta_l_devis ldevis where ldevis.id_devis=:id_document_recup  and
    not (ldevis.id_l_devis in (
    select id_l_devis from ta_l_devis_temp where (id_devis=:id_document_recup or (id_devis=-1))and
    ip_acces=current_connection ));
select typeL.id_t_ligne from  ta_t_ligne typeL where typeL.code_t_ligne = 'H' into :typeligne;
select D.tx_rem_ht_devis from ta_devis D where d.id_devis=:id_document_recup into :v_rem_ht_l_document;
execute procedure dispatcher_tva_document('devis',:id_document_recup,:ttc,:v_rem_ht_l_document);
  FOR select ldevis.id_l_devis,
    ldevis.id_devis,
    ldevis.id_t_ligne, ldevis.id_article, ldevis.num_ligne_l_devis,
    ldevis.lib_l_devis, ldevis.qte_l_devis, ldevis.u1_l_devis, ldevis.u2_l_devis,
    ldevis.prix_u_l_devis, ldevis.taux_tva_l_devis,ldevis.compte_l_devis, ldevis.code_tva_l_devis,
    ldevis.code_t_tva_l_devis, ldevis.mt_ht_l_devis,
    ldevis.mt_ttc_l_devis, ldevis.rem_tx_l_devis, ldevis.rem_ht_l_devis from ta_l_devis_temp ldevis
    where ldevis.id_devis = :id_document_recup and ldevis.ip_acces = current_connection into
        :v_id_l_document,
        :v_id_document,
        :V_ID_T_LIGNE,
        :V_ID_ARTICLE,
        :v_num_ligne_l_document,
        :v_lib_l_document,
        :v_qte_l_document,
        :v_u1_l_document,
        :v_u2_l_document,
        :v_prix_u_l_document,
        :v_taux_tva_l_document,
        :v_compte_l_document,
        :v_code_tva_l_document,
        :v_code_t_tva_l_document,
        :v_mt_ht_l_document,
        :v_mt_ttc_l_document,
        :v_rem_tx_l_document,
        :v_rem_ht_l_document
    do begin
     if (:V_ID_T_LIGNE <> :typeligne) then
        begin
            v_qte_l_document=null;
            v_prix_u_l_document=null;
            v_taux_tva_l_document=null;
            v_compte_l_document = null;
            v_code_tva_l_document=null;
            v_code_t_tva_l_document=null;
            v_mt_ht_l_document=null;
            v_mt_ttc_l_document=null;
            v_rem_tx_l_document=null;
            v_rem_ht_l_document=null;
        end
        select ldevis.id_l_devis from ta_l_devis ldevis
        where ldevis.id_l_devis = :v_id_l_document into :id_l_document_courant;
        if (:id_l_document_courant is not null) then
        begin
            update ta_l_devis doc set
            doc.id_l_devis = :v_id_l_document,
            doc.id_devis = :v_id_document,
            doc.ID_T_LIGNE = :V_ID_T_LIGNE,
            doc.ID_ARTICLE = :V_ID_ARTICLE,
            doc.num_ligne_l_devis = :v_num_ligne_l_document,
            doc.lib_l_devis = :v_lib_l_document,
            doc.qte_l_devis = :v_qte_l_document,
            doc.u1_l_devis = :v_u1_l_document,
            doc.u2_l_devis = :v_u2_l_document,
            doc.prix_u_l_devis = :v_prix_u_l_document,
            doc.taux_tva_l_devis = :v_taux_tva_l_document,
            doc.compte_l_devis = :v_compte_l_document,
            doc.code_tva_l_devis = :v_code_tva_l_document,
            doc.code_t_tva_l_devis = :v_code_t_tva_l_document,
            doc.mt_ht_l_devis = :v_mt_ht_l_document,
            doc.mt_ttc_l_devis = :v_mt_ttc_l_document,
            doc.rem_tx_l_devis = :v_rem_tx_l_document,
            doc.rem_ht_l_devis = :v_rem_ht_l_document
            where doc.id_l_devis = :v_id_l_document;
            update ta_l_devis_temp set id_devis = -1 where  id_l_devis=:id_l_document_courant ;
        end
       end
  FOR select ldevis.id_l_devis,
    ldevis.id_devis,
    ldevis.id_t_ligne, ldevis.id_article, ldevis.num_ligne_l_devis,
    ldevis.lib_l_devis, ldevis.qte_l_devis, ldevis.u1_l_devis, ldevis.u2_l_devis,
    ldevis.prix_u_l_devis, ldevis.taux_tva_l_devis,ldevis.compte_l_devis, ldevis.code_tva_l_devis,
    ldevis.code_t_tva_l_devis, ldevis.mt_ht_l_devis,
    ldevis.mt_ttc_l_devis, ldevis.rem_tx_l_devis, ldevis.rem_ht_l_devis from ta_l_devis_temp ldevis
    where ldevis.id_devis = :id_document_recup  and ldevis.ip_acces = current_connection into
        :v_id_l_document,
        :v_id_document,
        :V_ID_T_LIGNE,
        :V_ID_ARTICLE,
        :v_num_ligne_l_document,
        :v_lib_l_document,
        :v_qte_l_document,
        :v_u1_l_document,
        :v_u2_l_document,
        :v_prix_u_l_document,
        :v_taux_tva_l_document,
        :v_compte_l_document,
        :v_code_tva_l_document,
        :v_code_t_tva_l_document,
        :v_mt_ht_l_document,
        :v_mt_ttc_l_document,
        :v_rem_tx_l_document,
        :v_rem_ht_l_document
    do begin
     if (:V_ID_T_LIGNE <> :typeligne) then
        begin
            v_qte_l_document=null;
            v_prix_u_l_document=null;
            v_taux_tva_l_document=null;
            v_compte_l_document=null;
            v_code_tva_l_document=null;
            v_code_t_tva_l_document=null;
            v_mt_ht_l_document=null;
            v_mt_ttc_l_document=null;
            v_rem_tx_l_document=null;
            v_rem_ht_l_document=null;
        end
        select art.numcpt_article from ta_article art where art.id_article = :v_id_article into :v_compte_l_document;
            insert into ta_l_devis  (
            id_l_devis,
            id_devis,
            ID_T_LIGNE,
            ID_ARTICLE,
            num_ligne_l_devis,
            lib_l_devis,
            qte_l_devis,
            u1_l_devis,
            u2_l_devis,
            prix_u_l_devis,
            taux_tva_l_devis,
            compte_l_devis,
            code_tva_l_devis,
            code_t_tva_l_devis,
            mt_ht_l_devis,
            mt_ttc_l_devis,
            rem_tx_l_devis,
            rem_ht_l_devis
            ) values(
            :v_id_l_document,
            :v_id_document,
            :V_ID_T_LIGNE,
            :V_ID_ARTICLE,
            :v_num_ligne_l_document,
            :v_lib_l_document,
            :v_qte_l_document,
            :v_u1_l_document,
            :v_u2_l_document,
            :v_prix_u_l_document,
            :v_taux_tva_l_document,
            :v_compte_l_document,
            :v_code_tva_l_document,
            :v_code_t_tva_l_document,
            :v_mt_ht_l_document,
            :v_mt_ttc_l_document,
            :v_rem_tx_l_document,
            :v_rem_ht_l_document);
       end 

   execute procedure vide_document_temp('devis');
    suspend;
end
^

CREATE OR ALTER PROCEDURE RECUP_LIGNES_DEVIS (
    CODE_DOCUMENT_RECUP VARCHAR(50))
RETURNS (
    ID_DOCUMENT_RECUP INTEGER)
AS
DECLARE VARIABLE V_ID_L_DOCUMENT INTEGER;
DECLARE VARIABLE V_ID_DOCUMENT INTEGER;
DECLARE VARIABLE V_ID_T_LIGNE INTEGER;
DECLARE VARIABLE V_ID_ARTICLE INTEGER;
DECLARE VARIABLE V_NUM_LIGNE_L_DOCUMENT INTEGER;
DECLARE VARIABLE V_LIB_L_DOCUMENT VARCHAR(255) CHARACTER SET NONE;
DECLARE VARIABLE V_QTE_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE V_U1_L_DOCUMENT VARCHAR(20) CHARACTER SET NONE;
DECLARE VARIABLE V_U2_L_DOCUMENT VARCHAR(20) CHARACTER SET NONE;
DECLARE VARIABLE V_PRIX_U_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE V_TAUX_TVA_L_DOCUMENT NUMERIC(15,4);
DECLARE VARIABLE V_CODE_TVA_L_DOCUMENT VARCHAR(20) CHARACTER SET NONE;
DECLARE VARIABLE V_MT_HT_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE V_MT_TTC_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE V_REM_TX_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE V_REM_HT_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE V_CODE_T_TVA_L_DOCUMENT VARCHAR(1);
DECLARE VARIABLE V_COMPTE_L_DOCUMENT VARCHAR(8);
begin
execute procedure vide_document_temp('DEVIS');

select ID_DEVIS from ta_DEVIS where CODE_DEVIS = :code_document_recup into :id_document_recup;

FOR select ldoc.id_l_devis,
   ldoc.id_devis,
    ldoc.id_t_ligne, ldoc.id_article, ldoc.num_ligne_l_devis,
ldoc.lib_l_devis, ldoc.qte_l_devis, ldoc.u1_l_devis, ldoc.u2_l_devis,
ldoc.prix_u_l_devis, ldoc.taux_tva_l_devis,ldoc.compte_l_devis, ldoc.code_tva_l_devis,
ldoc.code_t_tva_l_devis, ldoc.mt_ht_l_devis,ldoc.mt_ttc_l_devis, ldoc.rem_tx_l_devis,
ldoc.rem_ht_l_devis from ta_l_DEVIS ldoc
where ldoc.id_devis = :id_document_recup
into
    :v_id_l_document,
    :V_ID_DOCUMENT,
    :V_ID_T_LIGNE,
    :V_ID_ARTICLE,
    :V_NUM_LIGNE_L_DOCUMENT,
    :V_LIB_L_DOCUMENT,
    :V_QTE_L_DOCUMENT,
    :V_U1_L_DOCUMENT,
    :V_U2_L_DOCUMENT,
    :V_PRIX_U_L_DOCUMENT,
    :V_TAUX_TVA_L_DOCUMENT,
    :v_compte_l_DOCUMENT,
    :V_CODE_TVA_L_DOCUMENT,
    :V_CODE_T_TVA_L_DOCUMENT,
    :V_MT_HT_L_DOCUMENT,
    :V_MT_TTC_L_DOCUMENT,
    :V_REM_TX_L_DOCUMENT,
    :V_REM_HT_L_DOCUMENT
do
  begin

     insert into ta_l_DEVIS_temp (
    ID_L_DEVIS,
    ID_DEVIS,
    ID_T_LIGNE,
    ID_ARTICLE,
    NUM_LIGNE_L_DEVIS,
    LIB_L_DEVIS,
    QTE_L_DEVIS,
    U1_L_DEVIS,
    U2_L_DEVIS,
    PRIX_U_L_DEVIS,
    TAUX_TVA_L_DEVIS,
    compte_l_DEVIS,
    CODE_TVA_L_DEVIS,
    CODE_T_TVA_L_DEVIS,
    MT_HT_L_DEVIS,
    MT_TTC_L_DEVIS,
    REM_TX_L_DEVIS,
    REM_HT_L_DEVIS,
    IP_ACCES
    ) values(
    :V_ID_L_DOCUMENT,
    :V_ID_DOCUMENT,
    :V_ID_T_LIGNE,
    :V_ID_ARTICLE,
    :V_NUM_LIGNE_L_DOCUMENT,
    :V_LIB_L_DOCUMENT,
    :V_QTE_L_DOCUMENT,
    :V_U1_L_DOCUMENT,
    :V_U2_L_DOCUMENT,
    :V_PRIX_U_L_DOCUMENT,
    :V_TAUX_TVA_L_DOCUMENT,
    :v_compte_l_DOCUMENT,
    :V_CODE_TVA_L_DOCUMENT,
    :V_CODE_T_TVA_L_DOCUMENT,
    :V_MT_HT_L_DOCUMENT,
    :V_MT_TTC_L_DOCUMENT,
    :V_REM_TX_L_DOCUMENT,
    :V_REM_HT_L_DOCUMENT,
    Current_Connection) ;
    /*suspend; */
  end
  suspend;
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

CREATE OR ALTER PROCEDURE INFOSCPAIEMENT (
    ID_FACTURE_RECUP INTEGER,
    ID_TIERS_RECUP INTEGER)
RETURNS (
    ID_INFOS_FACTURE INTEGER,
    ID_C_PAIEMENT INTEGER,
    ID_FACTURE INTEGER,
    ID_TIERS INTEGER,
    CODE_C_PAIEMENT  varchar(50),
    LIB_C_PAIEMENT varchar(255),
    REPORT_C_PAIEMENT INTEGER,
    FIN_MOIS_C_PAIEMENT INTEGER
    
    )
AS    
begin
for select

  infos.ID_INFOS_FACTURE,
  cast (null as INTEGER),
  infos.ID_FACTURE,
  cast (null as INTEGER),
  infos.CODE_C_PAIEMENT,
  infos.LIB_C_PAIEMENT ,
  infos.REPORT_C_PAIEMENT ,
  infos.FIN_MOIS_C_PAIEMENT 
  
   
  from TA_INFOS_FACTURE infos where infos.ID_FACTURE = :id_facture_recup into :ID_INFOS_FACTURE ,:ID_C_PAIEMENT,:ID_FACTURE,
  :ID_TIERS,   :CODE_C_PAIEMENT,:LIB_C_PAIEMENT,:REPORT_C_PAIEMENT,:FIN_MOIS_C_PAIEMENT
do begin
  suspend;   
end
  for select
  
  cast (null as INTEGER),
  cPaiement.id_c_paiement,
  cast (null as INTEGER),
  cPaiement.id_tiers,
  cPaiement.CODE_C_PAIEMENT,
  cPaiement.LIB_C_PAIEMENT,
  cPaiement.REPORT_C_PAIEMENT,
  cPaiement.FIN_MOIS_C_PAIEMENT 
  
  from ta_c_paiement cPaiement  
  where cPaiement.id_tiers = :id_tiers_recup
  into :ID_INFOS_FACTURE ,:ID_C_PAIEMENT,:ID_FACTURE,:ID_TIERS , :CODE_C_PAIEMENT,:LIB_C_PAIEMENT,:REPORT_C_PAIEMENT,:FIN_MOIS_C_PAIEMENT
  do begin
      suspend;
     end
     
end
^



/**/

/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Rajout des Triggers contenant des procédures stockées ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */



CREATE OR ALTER TRIGGER TA_FACTURE_AI0 FOR TA_FACTURE
ACTIVE AFTER INSERT POSITION 0
AS
begin
execute procedure enregistre_lignes_facture(new.id_facture,new.ttc);
end
^

/*Je rends ce trigger inactif car il pose un problème lors de l'exportation d'une
 * facture à cause de la mise à jour de la valeur "Exporter"  
 * du coups, la mise à jour des lignes est déclenché manuellement après l'enregistrement de
 * l'entête dans le module facture*/
CREATE OR ALTER TRIGGER TA_FACTURE_AU0 FOR TA_FACTURE
INACTIVE AFTER UPDATE POSITION 0
AS
begin
execute procedure maj_lignes_facture(old.id_facture,new.ttc);
end
^




CREATE OR ALTER TRIGGER TA_ARTICLE_AIU0 FOR TA_ARTICLE
ACTIVE AFTER INSERT OR UPDATE POSITION 0
AS
begin
  execute procedure annule_modification('ta_article','code_article',new.code_article);
end
^



CREATE OR ALTER TRIGGER TA_BANQUE_AIU0 FOR TA_BANQUE
ACTIVE AFTER INSERT OR UPDATE POSITION 0
AS
begin
  execute procedure annule_modification('ta_banque','code_banque',new.code_banque);
end
^

CREATE OR ALTER TRIGGER TA_C_PAIEMENT_AIU0 FOR TA_C_PAIEMENT
ACTIVE AFTER INSERT OR UPDATE POSITION 0
AS
begin
  execute procedure annule_modification('ta_c_paiement','code_c_paiement',new.code_c_paiement);
end
^


CREATE OR ALTER TRIGGER TA_ENTREPRISE_AIU0 FOR TA_ENTREPRISE
ACTIVE AFTER INSERT OR UPDATE POSITION 0
AS
begin
  execute procedure annule_modification('ta_entreprise','code_entreprise',new.code_entreprise);
end
^


CREATE OR ALTER TRIGGER TA_FACTURE_AIU0 FOR TA_FACTURE
ACTIVE AFTER INSERT OR UPDATE POSITION 0
AS
begin
  execute procedure annule_modification('ta_facture','code_facture',new.code_facture);
end
^

CREATE OR ALTER TRIGGER TA_FAMILLE_AIU0 FOR TA_FAMILLE
ACTIVE AFTER INSERT OR UPDATE POSITION 0
AS
begin
  execute procedure annule_modification('ta_famille','code_famille',new.code_famille);
end
^

CREATE OR ALTER TRIGGER TA_TIERS_AI0 FOR TA_TIERS
ACTIVE AFTER INSERT OR UPDATE POSITION 0
AS
begin
  execute procedure annule_modification('ta_tiers','code_tiers',new.code_tiers);
end
^

CREATE OR ALTER TRIGGER TA_TVA_AIU0 FOR TA_TVA
ACTIVE AFTER INSERT OR UPDATE POSITION 0
AS
begin
  execute procedure annule_modification('ta_tva','code_tva',new.code_tva);
end
^

CREATE OR ALTER TRIGGER TA_T_CIVILITE_AI0 FOR TA_T_CIVILITE
ACTIVE AFTER INSERT OR UPDATE POSITION 0
AS
begin
  execute procedure annule_modification('ta_t_civilite','code_t_civilite',new.code_t_civilite);
end
^

CREATE OR ALTER TRIGGER TA_T_DOC_AIU0 FOR TA_T_DOC
ACTIVE AFTER INSERT OR UPDATE POSITION 0
AS
begin
  execute procedure annule_modification('ta_t_doc','code_t_doc',new.code_t_doc);
end
^

CREATE OR ALTER TRIGGER TA_T_ENTITE_AIU0 FOR TA_T_ENTITE
ACTIVE AFTER INSERT OR UPDATE POSITION 0
AS
begin
  execute procedure annule_modification('ta_t_entite','code_t_entite',new.code_t_entite);
end
^

CREATE OR ALTER TRIGGER TA_T_LIGNE_AIU0 FOR TA_T_LIGNE
ACTIVE AFTER INSERT OR UPDATE POSITION 0
AS
begin
  execute procedure annule_modification('ta_t_ligne','code_t_ligne',new.code_t_ligne);
end
^

CREATE OR ALTER TRIGGER TA_T_PAIEMENT_AI0 FOR TA_T_PAIEMENT
ACTIVE AFTER INSERT OR UPDATE POSITION 0
AS
begin
  execute procedure annule_modification('ta_t_paiement','code_t_paiement',new.code_t_paiement);
end
^

CREATE OR ALTER TRIGGER TA_T_TEL_AIU0 FOR TA_T_TEL
ACTIVE AFTER INSERT OR UPDATE POSITION 0
AS
begin
  execute procedure annule_modification('ta_t_tel','code_t_tel',new.code_t_tel);
end
^

CREATE OR ALTER TRIGGER TA_T_TIERS_AIU0 FOR TA_T_TIERS
ACTIVE AFTER INSERT OR UPDATE POSITION 0
AS
begin
  execute procedure annule_modification('ta_t_tiers','code_t_tiers',new.code_t_tiers);
end
^

CREATE OR ALTER TRIGGER TA_T_TVA_AIU0 FOR TA_T_TVA
ACTIVE AFTER INSERT OR UPDATE POSITION 0
AS
begin
  execute procedure annule_modification('ta_t_tva','code_t_tva',new.code_t_tva);
end
^


CREATE OR ALTER TRIGGER TA_UNITE_AIU0 FOR TA_UNITE
ACTIVE AFTER INSERT OR UPDATE POSITION 0
AS
begin
  execute procedure annule_modification('ta_unite','code_unite',new.code_unite);
end
^
 
 
 
CREATE OR ALTER TRIGGER TA_DEVIS_AI0 FOR TA_DEVIS
ACTIVE AFTER INSERT POSITION 0
AS
begin
execute procedure enregistre_lignes_devis(new.id_devis,new.ttc);
end
^


CREATE OR ALTER TRIGGER TA_DEVIS_AIU0 FOR TA_DEVIS
ACTIVE AFTER INSERT OR UPDATE POSITION 0
AS
begin
  execute procedure annule_modification('ta_devis','code_devis',new.code_devis);
end
^



CREATE OR ALTER TRIGGER TA_DEVIS_AU0 FOR TA_DEVIS
INACTIVE AFTER UPDATE POSITION 0
AS
begin
execute procedure maj_lignes_devis(old.id_devis,new.ttc);
end
^


CREATE INDEX Ta_Commentaire_id_tiers ON Ta_commentaire (id_tiers)
^
CREATE INDEX Ta_Compl_id_tiers ON Ta_compl (id_tiers)
^
CREATE INDEX Ta_facture_id_tiers ON Ta_facture (id_tiers)
^
CREATE INDEX Ta_Prix_id_article ON Ta_prix (id_article)
^
CREATE INDEX Ta_Prix_id_unite ON Ta_prix (id_unite)
^
CREATE INDEX Ta_ref_Prix_id_article ON Ta_ref_Prix (id_article)
^
CREATE INDEX Ta_ref_Prix_id_Prix ON Ta_ref_Prix (id_prix)
^
CREATE INDEX Ta_R_Adr_id_tiers ON  Ta_R_Adr (id_tiers)
^
CREATE INDEX Ta_R_Adr_id_adresse ON  Ta_R_Adr (id_adresse)
^
CREATE INDEX Ta_R_Adr_T_adr_id_adresse ON Ta_R_Adr_T_adr (id_adresse)
^
CREATE INDEX Ta_R_Adr_T_adr_id_t_Adr ON Ta_R_Adr_T_adr (id_t_adr)
^
CREATE INDEX Ta_R_tel_id_tiers ON Ta_R_tel (id_tiers)
^
CREATE INDEX Ta_R_tel_id_telephone ON Ta_R_tel (id_telephone)
^
CREATE INDEX Ta_R_tel_t_tel_id_telephone ON Ta_R_tel_t_tel (id_telephone)
^
CREATE INDEX Ta_R_tel_t_tel_id_t_tel ON Ta_R_tel_t_tel (id_t_tel)
^
CREATE INDEX Ta_telephone_id_i_tiers ON Ta_telephone (id_i_tiers)
^
CREATE INDEX Ta_L_facture_id_t_ligne ON Ta_l_facture (id_t_ligne)
^
CREATE INDEX Ta_l_facture_id_facture ON Ta_l_facture (id_facture)
^
CREATE INDEX Ta_tva_code_tva ON Ta_tva (code_tva)
^
CREATE INDEX Ta_t_ligne_code_t_ligne ON Ta_t_ligne (code_t_ligne)
^
CREATE INDEX Ta_infos_facture_id_facture ON Ta_infos_facture(id_facture)
^
CREATE INDEX Ta_Article_id_tva ON Ta_article (id_tva)
^



DECLARE EXTERNAL FUNCTION ARRONDI
    DOUBLE PRECISION,
    INTEGER
RETURNS DOUBLE PRECISION BY VALUE
ENTRY_POINT 'Arrondi' MODULE_NAME 'GestArrondi.dll'
^



DECLARE EXTERNAL FUNCTION LTRIM
    CSTRING(255)
RETURNS CSTRING(255) FREE_IT
ENTRY_POINT 'IB_UDF_ltrim' MODULE_NAME 'ib_udf'
^


DECLARE EXTERNAL FUNCTION RTRIM
    CSTRING(255)
RETURNS CSTRING(255) FREE_IT
ENTRY_POINT 'IB_UDF_rtrim' MODULE_NAME 'ib_udf'
^


DECLARE EXTERNAL FUNCTION STRLEN
    CSTRING(32767)
RETURNS INTEGER BY VALUE
ENTRY_POINT 'IB_UDF_strlen' MODULE_NAME 'ib_udf'
^

DECLARE EXTERNAL FUNCTION SUBSTR
    CSTRING(255),
    SMALLINT,
    SMALLINT
RETURNS CSTRING(255) FREE_IT
ENTRY_POINT 'IB_UDF_substr' MODULE_NAME 'ib_udf'
^


/*************************************  GENERATIONFACTURE  *****************************************/
CREATE OR ALTER PROCEDURE GENERATIONFACTURE (
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



/*************************************  INSERTION_VALEURS  *****************************************/
CREATE OR ALTER PROCEDURE INSERTION_VALEURS 
AS
DECLARE VARIABLE NB2 INTEGER;
DECLARE VARIABLE NB INTEGER;
DECLARE VARIABLE NEWVALEUR VARCHAR(30);
DECLARE VARIABLE adresse VARCHAR(255);
DECLARE VARIABLE adresse2 VARCHAR(255);
DECLARE VARIABLE adresse3 VARCHAR(255);
DECLARE VARIABLE cp VARCHAR(255);
DECLARE VARIABLE ville VARCHAR(255);
DECLARE VARIABLE pays VARCHAR(255);
DECLARE VARIABLE code_compta VARCHAR(255);
DECLARE VARIABLE compte VARCHAR(255);
DECLARE VARIABLE nom_tiers VARCHAR(255);
DECLARE VARIABLE prenom_tiers VARCHAR(255);
DECLARE VARIABLE surnom_tiers VARCHAR(255);
DECLARE VARIABLE idFacture INTEGER;
DECLARE VARIABLE codefacture VARCHAR(255);
begin
NB = 1;
newvaleur='';
select adr.adresse1_adresse,adr.adresse2_adresse,adr.adresse3_adresse,adr.codepostal_adresse,
adr.ville_adresse,adr.pays_adresse from ta_adresse adr where id_adresse=1 into
:adresse,:adresse2,:adresse3,:cp,:ville,:pays;

select tiers.code_compta,tiers.compte, tiers.nom_tiers,tiers.prenom_tiers,tiers.surnom_tiers from ta_tiers tiers where id_tiers=1 into
  :code_compta,:compte, :nom_tiers,:prenom_tiers,:surnom_tiers  ;

  while (:nb<1000) do
  begin
/*     newvaleur='article '||cast(nb as varchar(20));
     Insert into TA_ARTICLE (CODE_ARTICLE,LIBELLEC_ARTICLE,LIBELLEL_ARTICLE,ID_FAMILLE,ID_TVA,NUMCPT_ARTICLE,DIVERS_ARTICLE,COMMENTAIRE_ARTICLE,STOCK_MIN_ARTICLE,ID_T_TVA) 
               values(cast(:nb as numeric),:newvaleur,:newvaleur,'1','1','654','','','0','1');
     INSERT INTO TA_TIERS ( CODE_TIERS, NOM_TIERS, PRENOM_TIERS, SURNOM_TIERS, ACTIF_TIERS, ID_T_CIVILITE, ID_ENTREPRISE, ID_T_TIERS, ID_T_ENTITE, ID_I_BANQUE, QUI_CREE_TIERS, QUAND_CREE_TIERS, QUI_MODIF_TIERS, QUAND_MODIF_TIERS, VERSION)
     VALUES ( '+ISA'||:nb, 'AYE'||:nb, 'Isabelle'||:nb, 'Isa', 1, 1, 1, 1, NULL, 2, 'SYSDBA', '2006-02-23 14:56:25', 'SYSDBA', '2006-02-23 14:56:25', NULL);

*/
idFacture=GEN_ID(NUM_ID_FACTURE,1);
codefacture=cast(:nb as varchar(5));
while  (strlen(:codefacture)<5)  do
begin
  codefacture='0'||:codefacture;
end
newvaleur= 'FVA7'||:codefacture;

     insert into TA_FACTURE (id_facture,DATE_FACTURE,REGLE_FACTURE,REM_HT_FACTURE,REM_TTC_FACTURE,DATE_ECH_FACTURE,
     ID_TIERS,ID_ADRESSE_LIV,ID_ADRESSE,DATE_LIV_FACTURE,CODE_FACTURE,TX_REM_HT_FACTURE,LIBELLE_FACTURE,
     TX_REM_TTC_FACTURE,NB_E_FACTURE,ID_T_PAIEMENT,ID_C_PAIEMENT)
     values(:idfacture,'2007/05/18','0.0','0.0','0.0','2007/05/18','1','1','1','2007/05/18',:newvaleur,
     '0.0','Facture N°'||:newvaleur,'0.0','0','1','3');

     insert into ta_infos_facture
     values(null,:idfacture,:adresse,:adresse2,:adresse3,:cp,:ville,:pays,:adresse,:adresse2,:adresse3,
     :cp,:ville,:pays,:code_compta,:compte,:nom_tiers,:prenom_tiers,:surnom_tiers,
     null,null,null,null,null,null,null,null,null,null,null,null,null);
         
     nb2=0;
    while (nb2 <10) do
     begin
     insert into TA_L_FACTURE (num_ligne_l_facture,U1_L_FACTURE,PRIX_U_L_FACTURE,MT_HT_L_FACTURE,ID_ARTICLE,QTE_L_FACTURE,
     ID_FACTURE,CODE_TVA_L_FACTURE,TAUX_TVA_L_FACTURE,MT_TTC_L_FACTURE,REM_HT_L_FACTURE,ID_T_LIGNE,
     REM_TX_L_FACTURE,LIB_L_FACTURE,U2_L_FACTURE)
     values(:nb2,'Kg','150.0','150.0','105','1.0',:idFacture,'A1','19.6','0.0','0.0','1','0.0','article 1','');
     nb2=:nb2+1;
     end
     
     nb=:nb+1;

  end

  suspend;
end
^
