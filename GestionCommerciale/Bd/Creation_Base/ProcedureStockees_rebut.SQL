CREATE PROCEDURE DISPATCHER_TVA (
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
  EXIT;
END
^


CREATE PROCEDURE DISPATCHER_TVA_SUR_CODETVA (
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
  EXIT;
END
^


CREATE PROCEDURE CALCUL_TVA_SUR_CODETVA (
    ID_FACTURE INTEGER,
    CODE_TVA VARCHAR(20),
    TAUX NUMERIC(15,4),
    TTC INTEGER,
    TAUXREMISE NUMERIC(15,2))
RETURNS (
    ID_FACTURE_RECUP INTEGER,
    TAUX_RECUP NUMERIC(15,4),
    CODE_TVA_RECUP VARCHAR(20),
    MT_TVA_RECUP NUMERIC(15,2),
    LIBELLE_RECUP VARCHAR(100))
AS
DECLARE VARIABLE TYPELIGNE INTEGER;
DECLARE VARIABLE SUMTTC NUMERIC(15,2);
DECLARE VARIABLE SUMHT NUMERIC(15,2);
begin
  EXIT;
END
^



CREATE OR ALTER PROCEDURE CALCUL_TVA_SUR_CODETVA (
    ID_FACTURE INTEGER,
    CODE_TVA VARCHAR(20),
    TAUX NUMERIC(15,4),
    TTC INTEGER,
    TAUXREMISE NUMERIC(15,2))
RETURNS (
    ID_FACTURE_RECUP INTEGER,
    TAUX_RECUP NUMERIC(15,4),
    CODE_TVA_RECUP VARCHAR(20),
    MT_TVA_RECUP NUMERIC(15,2),
    LIBELLE_RECUP VARCHAR(100))
AS
DECLARE VARIABLE TYPELIGNE INTEGER;
DECLARE VARIABLE SUMTTC NUMERIC(15,2);
DECLARE VARIABLE SUMHT NUMERIC(15,2);
begin
TYPELIGNE=0;
select typeL.id_t_ligne from  ta_t_ligne typeL where typeL.code_t_ligne = 'H' into :typeligne;
if (:ttc=1) then
begin
    FOR select sum(lfacture.mt_ttc_l_facture),
    lfacture.CODE_TVA_L_FACTURE,lfacture.TAUX_TVA_L_FACTURE
      from ta_l_facture_temp lfacture where lfacture.id_facture = :ID_FACTURE
      and lfacture.CODE_TVA_L_FACTURE = :CODE_TVA and lfacture.TAUX_TVA_L_FACTURE = :Taux
      and lfacture.id_t_ligne = :typeligne  and lfacture.ip_acces = current_connection
      group by lfacture.CODE_TVA_L_FACTURE,lfacture.TAUX_TVA_L_FACTURE
      order by lfacture.CODE_TVA_L_FACTURE,lfacture.TAUX_TVA_L_FACTURE
      into : sumttc,code_tva_recup,taux_recup
      do
      begin
        sumttc=:sumttc-(:sumttc*(:tauxremise/100));
        mt_tva_recup=cast( :sumttc-((:sumttc*100)/(100+:taux_recup))as numeric (15,2));
        select tva.LIBELLE_TVA from TA_TVA tva where tva.CODE_TVA = :code_tva_recup into :libelle_recup;
        id_facture_recup=:id_facture;
               Suspend;
      end
end
else
begin
    FOR select sum(lfacture.mt_ht_l_facture),
    lfacture.CODE_TVA_L_FACTURE,lfacture.TAUX_TVA_L_FACTURE
      from ta_l_facture_temp lfacture where lfacture.id_facture = :ID_FACTURE
      and lfacture.CODE_TVA_L_FACTURE = :CODE_TVA and lfacture.TAUX_TVA_L_FACTURE = :Taux
      and lfacture.id_t_ligne = :typeligne  and lfacture.ip_acces = current_connection
      group by lfacture.CODE_TVA_L_FACTURE,lfacture.TAUX_TVA_L_FACTURE
      order by lfacture.CODE_TVA_L_FACTURE,lfacture.TAUX_TVA_L_FACTURE
      into : sumht,code_tva_recup,taux_recup
      do
      begin
        sumht=:sumht-(:sumht*(:tauxremise/100));
        mt_tva_recup=cast( :sumht*(:taux_recup/100)as numeric (15,2));
        select tva.LIBELLE_TVA from TA_TVA tva where tva.CODE_TVA = :code_tva_recup into :libelle_recup;
        id_facture_recup=:id_facture;
               Suspend;
      end
end
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
              if (:totalttc=0) then tva_ligne=(:ttc_ligne * :taux)/100;
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
           if (:totalht=0) then tva_ligne=(:ht_ligne * :taux)/100;
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
                   if (:totalttc=0) then tva_ligne=(:ttc_ligne * :taux)/100;
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
                   if (:totalht=0) then tva_ligne=(:ht_ligne * :taux)/100;
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




CREATE PROCEDURE GENERECODEFACTURE
RETURNS (
    NEWCODE VARCHAR(20))
AS
DECLARE VARIABLE CODESTRING VARCHAR(20) CHARACTER SET NONE;
DECLARE VARIABLE CODE INTEGER;
begin
  EXIT;
END
^



CREATE PROCEDURE VIDE_TA_L_FACTURE_TEMP
AS
BEGIN
  EXIT;
END
^


/*************************  ALTER  **********************************/

CREATE OR ALTER PROCEDURE GENERECODEFACTURE
RETURNS (
    NEWCODE VARCHAR(20))
AS
DECLARE VARIABLE CODESTRING VARCHAR(20) CHARACTER SET NONE;
DECLARE VARIABLE CODE INTEGER;
begin
code=0;
newcode=1;
   select max(cast (facture.code_facture as numeric)) from ta_facture facture into :code ;
  code =  :code +1;
  if (code>0) then newcode = cast( :code as varchar(20));
  suspend;
end
^



/*************************************  VIDE_TA_L_FACTURE_TEMP  *****************************************/

CREATE OR ALTER PROCEDURE VIDE_TA_L_FACTURE_TEMP
AS
begin
  delete from ta_l_facture_temp lfacture where lfacture.ip_acces = current_connection;
  suspend;
end
^








CREATE OR ALTER PROCEDURE P_TIERS 
RETURNS (
    ID_TIERS INTEGER,
    CODE_TIERS VARCHAR(20),
    CODE_COMPTA VARCHAR(7),
    COMPTE VARCHAR(8),
    NOM_TIERS VARCHAR(100),
    PRENOM_TIERS VARCHAR(100),
    SURNOM_TIERS VARCHAR(20),
    ACTIF_TIERS INTEGER,
    ID_T_CIVILITE INTEGER,
    CODE_T_CIVILITE VARCHAR(20),
    ID_ENTREPRISE INTEGER,
    CODE_ENTREPRISE VARCHAR(20),
    LIBL_ENTREPRISE VARCHAR(100),
    ID_T_TIERS INTEGER,
    CODE_T_TIERS VARCHAR(20),
    LIBELLE_T_TIERS VARCHAR(100),
    ID_COMMENTAIRE INTEGER,
    LIBL_COMMENTAIRE VARCHAR(255),
    ID_I_BANQUE INTEGER,
    CODE_BANQUE VARCHAR(20),
    LIBC_BANQUE VARCHAR(100),
    LIBL_BANQUE VARCHAR(100),
    ID_T_ENTITE INTEGER,
    CODE_T_ENTITE VARCHAR(20),
    TVA_I_COM_COMPL VARCHAR(50),
    SIRET_COMPL VARCHAR(50))
AS
begin
for select Tiers.ID_TIERS,
Tiers.CODE_TIERS,
Tiers.CODE_COMPTA,
Tiers.COMPTE,
Tiers.NOM_TIERS,
Tiers.PRENOM_TIERS,
Tiers.SURNOM_TIERS,
Tiers.ACTIF_TIERS,
Tiers.ID_T_CIVILITE,
Civilite.CODE_T_CIVILITE,
Tiers.ID_ENTREPRISE,
Entreprise.CODE_ENTREPRISE,
Entreprise.LIBL_ENTREPRISE,
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
LEFT OUTER join TA_T_TIERS TTiers on Tiers.ID_T_TIERS = TTiers.ID_T_TIERS
LEFT OUTER join TA_COMMENTAIRE Com on Tiers.ID_TIERS = Com.ID_TIERS
LEFT OUTER join TA_BANQUE Banque on Tiers.ID_I_BANQUE = Banque.ID_BANQUE
LEFT OUTER join ta_compl compl on compl.id_tiers = Tiers.id_tiers
into :ID_TIERS,
:CODE_TIERS,
:CODE_COMPTA,
:COMPTE,
:NOM_TIERS,
:PRENOM_TIERS,
:SURNOM_TIERS,
:ACTIF_TIERS,
:ID_T_CIVILITE,
:CODE_T_CIVILITE,
:ID_ENTREPRISE,
:CODE_ENTREPRISE,
:LIBL_ENTREPRISE,
:ID_T_TIERS,
:CODE_T_TIERS,
:LIBELLE_T_TIERS,
:ID_COMMENTAIRE,
:LIBL_COMMENTAIRE,
:ID_I_BANQUE,
:CODE_BANQUE,
:LIBC_BANQUE,
:LIBL_BANQUE,
:ID_T_ENTITE,
:CODE_T_ENTITE,
:tva_i_com_compl,
:siret_compl
do begin
suspend;
end
end
^
