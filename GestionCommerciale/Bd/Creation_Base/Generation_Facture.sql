CREATE PROCEDURE GENERATIONFACTURE (
    MODELEFACTURE VARCHAR(9),
    CODEFIXE VARCHAR(10),
    LENCOMPTEUR INTEGER,
    IGNORE_TIERS_MODEL INTEGER,
    CODE_TIERS_INPUT VARCHAR(8))
RETURNS (
    NB_FACTURE INTEGER)
AS
DECLARE VARIABLE REQUETE VARCHAR(10000);
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
select max(SUBSTR(code_facture,:lencompteur , 9)) from ta_facture into :COMPTEUR;
COMPTEUR=:COMPTEUR+1;
nb_facture=0;
if (:code_tiers_input is null or (:code_tiers_input = '') ) then code_tiers_input = '%';
select f.id_facture,f.id_tiers from ta_facture f where f.code_facture = :modelefacture into :id_fact_courant,id_tiers_tmp;
/*Récupérer les infos facture fixes*/
select code_c_paiement,lib_c_paiement,report_c_paiement,fin_mois_c_paiement from ta_infos_facture i
where i.id_facture =:id_fact_courant into :code_c_paiement,:lib_c_paiement,:report_c_paiement,:fin_mois_c_paiement;
requete='select t.id_tiers,t.code_tiers,t.code_compta,t.compte,t.nom_tiers,t.prenom_tiers,t.surnom_tiers
  ,t.code_t_civilite,t.code_t_entite,t.tva_i_com_compl from v_tiers T where T.code_tiers like (:code_tiers_input)';
  if(:ignore_tiers_model=0)then
requete= requete||'  and T.id_tiers<>:id_tiers_tmp';
for execute statement :requete into:id_tiers_tmp,code_tiers,code_compta,compte,nom_tiers,prenom_tiers,surnom_tiers
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


        CODEFACTURE_TMP=rtrim(cast(:compteur as varchar(9)));
        while  (strlen(:CODEFACTURE_TMP)<lencompteur)  do
        begin
          CODEFACTURE_TMP='0'||:CODEFACTURE_TMP;
        end
        CODEFACTURE_TMP= CODEFIXE||:CODEFACTURE_TMP;

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



/******************************* Variantes de la génération des factures **************/
CREATE PROCEDURE GENERATIONFACTURE (
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

