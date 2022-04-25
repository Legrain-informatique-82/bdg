 create database '.\Gest_Com.fdb' user 'ADMIN' password '###_PASSWORD_FB_BDG_###' page_size 16384;

CONNECT '.\Gest_Com.fdb' user 'ADMIN' password '###_PASSWORD_FB_BDG_###';

SET TERM ^ ;


CREATE VIEW V2_FACTURE(
    ID_FACTURE,
    CODE_FACTURE,
    DATE_FACTURE,
    DATE_ECH_FACTURE,
    DATE_LIV_FACTURE,
    LIBELLE_FACTURE,
    ID_ADRESSE,
    ID_ADRESSE_LIV,
    ID_TIERS,
    CODE_TIERS,
    NOM_TIERS,
    PRENOM_TIERS,
    SURNOM_TIERS,
    CODE_COMPTA,
    COMPTE,
    ID_T_PAIEMENT,
    CODE_T_PAIEMENT,
    ID_C_PAIEMENT,
    REGLE_FACTURE,
    REM_HT_FACTURE,
    TX_REM_HT_FACTURE,
    REM_TTC_FACTURE,
    TX_REM_TTC_FACTURE,
    NB_E_FACTURE,
    MT_TTC_CALC,
    MT_HT_CALC,
    MT_TVA_CALC,
    NET_TTC_CALC,
    NET_HT_CALC,
    NET_TVA_CALC,
    IP_ACCES)
AS
select
Facture.ID_FACTURE,
Facture.CODE_FACTURE,
Facture.DATE_FACTURE,
Facture.DATE_ECH_FACTURE,
Facture.DATE_LIV_FACTURE,
Facture.LIBELLE_FACTURE,
Facture.ID_ADRESSE,
Facture.ID_ADRESSE_LIV,
Facture.ID_TIERS,
Tiers.code_tiers,
Tiers.NOM_TIERS,
Tiers.PRENOM_TIERS,
Tiers.SURNOM_TIERS,
Tiers.CODE_COMPTA,
Tiers.COMPTE,
Facture.ID_T_PAIEMENT,
TPaiement.code_t_paiement,
CPaiement.id_c_paiement,
Facture.REGLE_FACTURE,
Facture.REM_HT_FACTURE,
Facture.TX_REM_HT_FACTURE,
Facture.REM_TTC_FACTURE,
Facture.TX_REM_TTC_FACTURE,
Facture.NB_E_FACTURE,
cast(0 as double precision),
cast(0 as double precision),
cast(0 as double precision),
cast(0 as double precision),
cast(0 as double precision),
cast(0 as double precision),
Facture.IP_ACCES
from TA_FACTURE Facture
left join ta_tiers Tiers on (Tiers.id_tiers = Facture.id_tiers)
left join ta_t_paiement TPaiement on (TPaiement.id_t_paiement = Facture.id_t_paiement)
left join ta_c_paiement CPaiement on (CPaiement.id_c_paiement = Facture.id_c_paiement)
;



DECLARE EXTERNAL FUNCTION ARRONDI
    DOUBLE PRECISION,
    INTEGER
RETURNS DOUBLE PRECISION BY VALUE
ENTRY_POINT 'Arrondi' MODULE_NAME 'GestArrondi.dll';



DECLARE EXTERNAL FUNCTION LTRIM
    CSTRING(255)
RETURNS CSTRING(255) FREE_IT
ENTRY_POINT 'IB_UDF_ltrim' MODULE_NAME 'ib_udf';


DECLARE EXTERNAL FUNCTION RTRIM
    CSTRING(255)
RETURNS CSTRING(255) FREE_IT
ENTRY_POINT 'IB_UDF_rtrim' MODULE_NAME 'ib_udf';


DECLARE EXTERNAL FUNCTION STRLEN
    CSTRING(32767)
RETURNS INTEGER BY VALUE
ENTRY_POINT 'IB_UDF_strlen' MODULE_NAME 'ib_udf';

DECLARE EXTERNAL FUNCTION SUBSTR
    CSTRING(255),
    SMALLINT,
    SMALLINT
RETURNS CSTRING(255) FREE_IT
ENTRY_POINT 'IB_UDF_substr' MODULE_NAME 'ib_udf';


CREATE PROCEDURE TRIM (
    VALEUR VARCHAR(255))
RETURNS (
    RESULTAT VARCHAR(255))
AS
DECLARE VARIABLE LONGUEUR INTEGER;
DECLARE VARIABLE CODETMP VARCHAR(255);
DECLARE VARIABLE I INTEGER;
begin
i=1;
longueur=1;
resultat = :valeur;
codetmp='';
longueur =  strlen(:valeur);
  while (:i<=:longueur)do
  begin
     codetmp = codetmp||ltrim(substr(:resultat,i,i));
     i=i+1;
  end
  resultat = :codetmp;
suspend;
end
^
