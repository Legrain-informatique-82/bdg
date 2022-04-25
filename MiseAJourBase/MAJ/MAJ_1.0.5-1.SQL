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
     execute procedure vide_ta_l_facture_temp;
   end /* fin test deja exportee */
 end /* fin boucle sur facture */
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


