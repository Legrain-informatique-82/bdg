
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
  SUSPEND;
END
^


CREATE PROCEDURE AUTORISE_MODIF_GENERE (
    NOMTABLE VARCHAR(255),
    NOMCHAMP VARCHAR(255),
    VALEUR VARCHAR(255),
    VERIF_CONNECTION INTEGER)
RETURNS (
    RETOUR INTEGER)
AS
BEGIN
  SUSPEND;
END
^


CREATE PROCEDURE ENREGISTRE_ACCES
AS
BEGIN
  EXIT;
END
^


CREATE PROCEDURE MAJ_GENERATEUR
AS
BEGIN
  EXIT;
END
^

CREATE PROCEDURE MISEAJOUR_PARAM_ARTICLE (
    NUM_PIECE INTEGER)
AS
BEGIN
  EXIT;
END
^


CREATE PROCEDURE NETTOYAGE (
    TEMP_MIN INTEGER)
AS
BEGIN
  EXIT;
END
^


CREATE PROCEDURE RAZ_ACCES
AS
BEGIN
  EXIT;
END
^


CREATE PROCEDURE RECALCULPRIXTTC (
    ID_ARTICLE INTEGER,
    CODE_TVA VARCHAR(100))
AS
BEGIN
  EXIT;
END
^


CREATE PROCEDURE RECORD_MODIFIABLE (
    NOM_TABLE VARCHAR(100),
    IDTABLE INTEGER)
RETURNS (
    NB INTEGER)
AS
BEGIN
  SUSPEND;
END
^


CREATE PROCEDURE RECUP_IP_ACCES
RETURNS (
    IP_ACCES VARCHAR(50))
AS
BEGIN
  SUSPEND;
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


CREATE PROCEDURE SUPPRESSION_ACCES
AS
BEGIN
  EXIT;
END
^


/******************************************************************************/
/*                             Stored Procedures                              */
/******************************************************************************/


ALTER PROCEDURE ANNULE_MODIFICATION (
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


ALTER PROCEDURE AUTORISE_MODIF (
    NOMTABLE VARCHAR(255),
    NOMCHAMP VARCHAR(255),
    VALEUR VARCHAR(255))
RETURNS (
    RETOUR INTEGER)
AS
begin
  select count(*) from ta_modif modif where upper(modif.table_modif) = upper(:nomtable)
  and upper(modif.champ_modif) =upper(:nomchamp)
  and  upper(modif.valeur_modif) =upper(:valeur)
  and upper(ip_acces)<>current_connection into :retour;
  suspend;
end
^


ALTER PROCEDURE AUTORISE_MODIF_GENERE (
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


ALTER PROCEDURE ENREGISTRE_ACCES
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

CREATE OR ALTER PROCEDURE MAJ_GENERATEUR 
as
declare variable v_gen integer;
declare variable v_max integer;
begin

select max(alias.id) from TA_VISUALISATION alias into :v_max; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_visualisation, 0); V_GEN = gen_id(num_id_visualisation, V_MAX - V_GEN);

end
^


ALTER PROCEDURE MISEAJOUR_PARAM_ARTICLE (
    NUM_PIECE INTEGER)
AS
begin
if (:num_piece=0) then
    begin  /*travail sur toutes les factures*/
        Update ta_l_facture lf set lf.compte_l_DOCUMENT = (select A.numcpt_article
        from ta_article A where a.id_article=lf.id_article)
        where lf.compte_l_DOCUMENT is null or (lf.compte_l_DOCUMENT not in(select A.numcpt_article
        from ta_article A where a.id_article=lf.id_article));
    end
else
    begin /*travail sur une facture particuli�re*/
        Update ta_l_facture lf set lf.compte_l_DOCUMENT = (select A.numcpt_article
        from ta_article A where a.id_article=lf.id_article)
        where (lf.id_DOCUMENT = :num_piece) and  (lf.compte_l_DOCUMENT is null or (lf.compte_l_DOCUMENT not in(select A.numcpt_article
        from ta_article A where a.id_article=lf.id_article)));
    end
  suspend;
end
^


ALTER PROCEDURE NETTOYAGE (
    TEMP_MIN INTEGER)
AS
begin
/*delete from ta_modif modif where (((extract(minute from modif.quand_cree_modif)) - (extract(minute from current_DATE))) < :temp_min)*/
delete from ta_modif modif where
/*((current_DATE - modif.quand_cree_modif) > :temp_min) and*/
not exists  (select mon.mon$attachment_id from mon$attachments mon  where mon.mon$attachment_id=modif.ip_acces);
end
^


ALTER PROCEDURE RAZ_ACCES
AS
begin
delete from ta_acces acces where
       not exists (select ip_acces from ta_acces acces, mon$attachments mon where acces.ip_acces=mon.mon$attachment_id);
suspend;
end
^


ALTER PROCEDURE RECALCULPRIXTTC (
    ID_ARTICLE INTEGER,
    CODE_TVA VARCHAR(100))
AS
DECLARE VARIABLE TAUX_TVA NUMERIC(15,2);
DECLARE VARIABLE TTC NUMERIC(15,2);
begin
  for select prix.prixttc_prix from ta_prix prix where prix.id_article =:id_article into :ttc
  do begin
    select  tva.taux_tva from ta_tva tva where tva.code_tva = :code_tva into :taux_tva;
    if( :taux_tva is not null)then
    begin
     update ta_prix prix set  prix.prixttc_prix = prix.prix_prix + (prix.prix_prix * :taux_tva)/100
     where prix.id_article = :id_article;
    end
  end
  suspend;
end
^


CREATE OR ALTER PROCEDURE RECORD_MODIFIABLE (
    nom_table varchar(100),
    idtable integer)
returns (
    nb integer)
as
begin
if (upper(:NOM_TABLE) = upper('ta_tiers'))   then
    begin
        nb=0;
        select count(*) from ta_facture doc where doc.id_tiers = :idtable into :nb  ;
        if (:nb<=0)then
            select count(*) from ta_devis doc where doc.id_tiers = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_boncde doc where doc.id_tiers = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_avoir doc where doc.id_tiers = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_apporteur doc where doc.id_tiers = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_proforma doc where doc.id_tiers = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_bonliv doc where doc.id_tiers = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_acompte doc where doc.id_tiers = :idtable into nb  ;

        suspend;
    end
else
if (upper(:NOM_TABLE) = upper('ta_article'))   then
    begin
        nb=0;
        select count(*) from ta_L_facture Ldoc where Ldoc.id_ARTICLE = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_l_devis Ldoc where Ldoc.id_ARTICLE = :idtable into nb  ;

        if (:nb<=0)then
            select count(*) from ta_l_boncde Ldoc where Ldoc.id_ARTICLE = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_l_avoir Ldoc where Ldoc.id_ARTICLE = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_l_apporteur Ldoc where Ldoc.id_ARTICLE = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_l_proforma Ldoc where Ldoc.id_ARTICLE = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_l_bonliv Ldoc where Ldoc.id_ARTICLE = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_l_acompte Ldoc where Ldoc.id_ARTICLE = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_stock Ldoc where Ldoc.id_article_stock = :idtable into nb  ;

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
        if (:nb<=0)then
            select count(*) from ta_boncde doc where doc.ID_T_PAIEMENT = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_avoir doc where doc.ID_T_PAIEMENT = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_apporteur doc where doc.ID_T_PAIEMENT = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_proforma doc where doc.ID_T_PAIEMENT = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_acompte doc where doc.ID_T_PAIEMENT = :idtable into nb  ;
        suspend;
    end
else
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
        if (:nb<=0)then
            select count(*) from ta_l_boncde ldoc where ldoc.id_t_ligne = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_l_avoir ldoc where ldoc.id_t_ligne = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_l_apporteur ldoc where ldoc.id_t_ligne = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_l_proforma ldoc where ldoc.id_t_ligne = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_l_acompte ldoc where ldoc.id_t_ligne = :idtable into nb  ;
        suspend;
    end
if (upper(:NOM_TABLE) = upper('ta_famille'))   then
    begin
        nb=0;
        select count(*) from ta_article article where article.id_famille = :idtable into nb  ;
        suspend;
    end
if (upper(:NOM_TABLE) = upper('ta_TVA'))   then
    begin
        nb=0;
        select count(*) from ta_ARTICLE article where article.id_tva = :idtable into nb  ;
       
        if (:nb<=0)then
            select count(*) from ta_l_facture ldoc,ta_TVA tva where ldoc.CODE_TVA_L_DOCUMENT = tva.code_tva and tva.id_tva = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_l_devis ldoc,ta_TVA tva where ldoc.CODE_TVA_L_DOCUMENT = tva.code_tva and tva.id_tva = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_l_bonliv ldoc,ta_TVA tva where ldoc.CODE_TVA_L_DOCUMENT = tva.code_tva and tva.id_tva = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_l_boncde ldoc,ta_TVA tva where ldoc.CODE_TVA_L_DOCUMENT = tva.code_tva and tva.id_tva = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_l_avoir ldoc,ta_TVA tva where ldoc.CODE_TVA_L_DOCUMENT = tva.code_tva and tva.id_tva = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_l_apporteur ldoc,ta_TVA tva where ldoc.CODE_TVA_L_DOCUMENT = tva.code_tva and tva.id_tva = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_l_proforma ldoc,ta_TVA tva where ldoc.CODE_TVA_L_DOCUMENT = tva.code_tva and tva.id_tva = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_l_acompte ldoc,ta_TVA tva where ldoc.CODE_TVA_L_DOCUMENT = tva.code_tva and tva.id_tva = :idtable into nb  ;

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

if (upper(:NOM_TABLE) = upper('ta_t_tiers'))   then
    begin
        nb=0;
        select count(*) from ta_tiers tiers where tiers.id_t_tiers = :idtable into nb  ;
        suspend;
    end

if (upper(:NOM_TABLE) = upper('ta_t_adr'))   then
    begin
        nb=0;
        select count(*) from ta_adresse doc where doc.id_t_adr = :idtable into nb  ;
        suspend;
    end
if (upper(:NOM_TABLE) = upper('ta_t_email'))   then
    begin
        nb=0;
        select count(*) from ta_email doc where doc.id_t_email = :idtable into nb  ;
        suspend;
    end
if (upper(:NOM_TABLE) = upper('ta_t_web'))   then
    begin
        nb=0;
        select count(*) from ta_web doc where doc.id_t_web = :idtable into nb  ;
        suspend;
    end
if (upper(:NOM_TABLE) = upper('ta_t_tel'))   then
    begin
        nb=0;
        select count(*) from ta_telephone doc where doc.id_t_tel = :idtable into nb  ;
        suspend;
    end
if (upper(:NOM_TABLE) = upper('ta_t_tarif'))   then
    begin
        nb=0;
        select count(*) from ta_tiers doc where doc.id_t_tarif = :idtable into nb  ;
        suspend;
    end
if (upper(:NOM_TABLE) = upper('ta_t_banque'))   then
    begin
        nb=0;
        select count(*) from ta_compte_banque doc where doc.id_t_banque = :idtable into nb  ;
        suspend;
    end
if (upper(:NOM_TABLE) = upper('ta_t_liens'))   then
    begin
        nb=0;
        select count(*) from ta_liens doc where doc.id_t_liens = :idtable into nb  ;
        suspend;
    end
if (upper(:NOM_TABLE) = upper('ta_etablissement'))   then
    begin
        nb=0;
        select count(*) from ta_operation doc where doc.id_etablissement = :idtable into nb  ;
        select count(*) from ta_depot doc where doc.id_etablissement = :idtable into nb  ;
        select count(*) from ta_report_t_paiement doc where doc.id_etablissement = :idtable into nb  ;
        suspend;
    end  
if (upper(:NOM_TABLE) = upper('TA_FAMILLE_TIERS'))   then
    begin
        nb=0;
        select count(*) from TA_R_FAMILLE_TIERS doc where doc.id_famille = :idtable into nb  ;
        suspend;
    end
if (upper(:NOM_TABLE) = upper('TA_FAMILLE_UNITE'))   then
    begin
        nb=0;
        select count(*) from TA_unite doc where doc.id_famille = :idtable into nb  ;
        suspend;
    end    
if (upper(:NOM_TABLE) = upper('TA_C_PAIEMENT'))   then
    begin
        nb=0;
        select count(*) from TA_TIERS doc where doc.ID_C_PAIEMENT = :idtable into nb  ;
        suspend;
    end    

if (upper(:NOM_TABLE) = upper('ta_acompte'))   then
    begin
        nb=0;
        select count(*) from ta_r_acompte r where r.id_acompte = :idtable
            and id_facture is not null into nb  ;
        suspend;
    end
    
if (upper(:NOM_TABLE) = upper('ta_avoir'))   then
    begin
        nb=0;
        select count(*) from ta_r_avoir r where r.id_avoir = :idtable
            and id_facture is not null into nb  ;
        suspend;
    end

suspend;
end
^


ALTER PROCEDURE RECUP_IP_ACCES
RETURNS (
    IP_ACCES VARCHAR(50))
AS
begin
  ip_acces = current_connection;
  suspend;
end
^


ALTER PROCEDURE RENTRE_EN_MODIFICATION (
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


ALTER PROCEDURE SUPPRESSION_ACCES
AS
begin
  delete from ta_acces where ip_acces=current_connection;
end
^

CREATE OR ALTER PROCEDURE GESTION_VISUALISATION_ARTICLES (
    clausewhere varchar(1000),
    clauseselect varchar(5000),
    clausegroupby varchar(1000))
returns (
    code_article varchar(20),
    libellec_article varchar(100),
    numcpt_article varchar(8),
    divers_article varchar(255),
    commentaire_article varchar(255),
    stock_min_article numeric(15,2),
    prix_prix numeric(15,2),
    code_unite varchar(20),
    code_famille varchar(20),
    code_tva varchar(1),
    taux_tva numeric(15,2),
    numcpt_tva varchar(8),
    code_t_tva varchar(20))
as
declare variable requete varchar(1000);
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

CREATE OR ALTER PROCEDURE GESTION_VISUALISATION_TIERS (
    clausewhere varchar(1000),
    clauseselect varchar(5000),
    clausegroupby varchar(1000))
returns (
    code_tiers varchar(20),
    code_compta varchar(7),
    compte varchar(8),
    nom_tiers varchar(100),
    actif_tiers smallint,
    ttc_tiers smallint,
    code_t_civilite varchar(20),
    code_entreprise varchar(20),
    code_t_tiers varchar(20),
    code_banque varchar(20),
    code_t_entite varchar(20))
as
declare variable requete varchar(1000);
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

CREATE OR ALTER PROCEDURE GESTION_EXTRACTION_TIERS (
    clausewhere varchar(5000),
    clauseselect varchar(10000),
    clausegroupby varchar(5000))
returns (
    nom_tiers varchar(100),
    prenom_tiers varchar(100),
    adresse1_adresse varchar(100),
    adresse2_adresse varchar(100),
    adresse3_adresse varchar(100),
    codepostal_adresse varchar(5),
    ville_adresse varchar(100),
    pays_adresse varchar(100),
    departement varchar(2),
    code_t_civilite varchar(20),
    surnom_tiers varchar(20),
    code_tiers varchar(20),
    nom_entreprise varchar(20),
    actif_tiers smallint,
    ttc_tiers smallint,
    code_t_tiers varchar(20),
    libelle_t_tiers varchar(100),
    libl_commentaire varchar(2000),
    code_t_entite varchar(20),
    tva_i_com_compl varchar(50),
    siret_compl varchar(50),
    numero_telephone varchar(20),
    adresse_email varchar(255),
    adresse_web varchar(255),
    quand_cree_tiers timestamp,
    date_dern_fact timestamp,
    date_dern_bonliv timestamp,
    date_dern_devis timestamp)
as
declare variable requete varchar(10000);
begin
if (:clauseselect is not null and :clauseselect<>'') then  requete=:clauseselect;
else
requete = '
select NOM_TIERS,PRENOM_TIERS,ADRESSE1_ADRESSE,  ADRESSE2_ADRESSE,
ADRESSE3_ADRESSE,CODEPOSTAL_ADRESSE,  VILLE_ADRESSE,PAYS_ADRESSE,
cast((cast(adr.CODEPOSTAL_ADRESSE as integer) / 1000)   as varchar(2)) as departement,
  CODE_T_CIVILITE,SURNOM_TIERS,  CODE_TIERS,nom_ENTREPRISE,
ACTIF_TIERS,TTC_TIERS,CODE_T_TIERS,  LIBELLE_T_TIERS,com.commentaire,
CODE_T_ENTITE, tva_i_com_compl,  siret_compl,NUMERO_TELEPHONE,ADRESSE_EMAIL,
ADRESSE_WEB,QUAND_CREE_TIERS,  max(f.date_document) as date_dern_fact,
max(bonliv.date_document)as date_dern_bonliv, max(devis.date_document)as date_dern_devis
From TA_TIERS Tiers
LEFT OUTER join TA_T_ENTITE Ent on Tiers.ID_T_ENTITE = Ent.ID_T_ENTITE
LEFT OUTER join TA_T_CIVILITE Civ on Tiers.ID_T_CIVILITE = Civ.ID_T_CIVILITE
LEFT OUTER join TA_ENTREPRISE E on Tiers.ID_ENTREPRISE = E.ID_ENTREPRISE
LEFT OUTER  join TA_T_TIERS TTiers on Tiers.ID_T_TIERS = TTiers.ID_T_TIERS
LEFT OUTER  join TA_COMMENTAIRE Com on Tiers.id_commentaire = Com.id_commentaire
LEFT OUTER join ta_compl compl on compl.id_compl = Tiers.id_compl
LEFT OUTER join ta_ADRESSE adr on adr.id_tiers = Tiers.id_tiers
LEFT OUTER join ta_EMAIL email on email.id_tiers = Tiers.id_tiers
LEFT OUTER join ta_WEB web on web.id_tiers = Tiers.id_tiers
LEFT OUTER join ta_facture f on f.ID_tiers = Tiers.id_tiers
LEFT OUTER join ta_bonliv bonliv on bonliv.ID_tiers = Tiers.id_tiers
LEFT OUTER join ta_devis devis on devis.ID_tiers = Tiers.id_tiers
LEFT OUTER join TA_TELEPHONE tel on tel.id_tiers = Tiers.id_tiers';

if (:CLAUSEWHERE is not null and :clausewhere<>'') then
begin
  requete = :requete||' '||:CLAUSEWHERE ;
end
if (:clausegroupby is not null and :clausegroupby<>'') then
begin
  requete = :requete||' '||:clausegroupby ;
end
for  execute statement :requete
 into

NOM_TIERS,
PRENOM_TIERS,
ADRESSE1_ADRESSE,
ADRESSE2_ADRESSE ,
ADRESSE3_ADRESSE ,
CODEPOSTAL_ADRESSE ,
VILLE_ADRESSE ,
PAYS_ADRESSE ,
departement ,
CODE_T_CIVILITE,
SURNOM_TIERS,
CODE_TIERS ,
NOM_ENTREPRISE ,
ACTIF_TIERS ,
TTC_TIERS ,
CODE_T_TIERS ,
LIBELLE_T_TIERS ,
LIBL_COMMENTAIRE ,
CODE_T_ENTITE ,
tva_i_com_compl ,
siret_compl ,
NUMERO_TELEPHONE ,
ADRESSE_EMAIL ,
ADRESSE_WEB ,
QUAND_CREE_TIERS ,
date_dern_fact ,
date_dern_bonliv ,
date_dern_devis
do
begin
suspend;
end
end
^

CREATE OR ALTER PROCEDURE CALCULPOINTBONUS3 (
    id_article integer)
returns (
    id_tiers integer,
    points numeric(15,2),
    date_validite date,
    id_l_document integer,
    code_article varchar(25),
    mt_ht_apr_rem_globale numeric(15,2),
    qte_l_document numeric(15,2),
    date_document date,
    prix_reference numeric(15,2),
    pointpotentiel numeric(15,2),
    regle numeric(15,2),
    net numeric(15,2),
    id_article_out integer)
as
declare variable valeurinter numeric(15,2);
declare variable id_document integer;
begin
  if(:id_article is null )then
  begin
    for select distinct t.id_tiers
  from ta_tiers t left
  join ta_facture f on f.id_tiers=t.id_tiers
  join ta_l_facture lf on lf.id_document=f.id_document
  join ta_article_point ap on ap.id_article=lf.id_article
  join ta_article a on a.id_article=ap.id_article
  where t.id_tiers <>1140 and (ap.id_article=220 or ap.id_article=221  or ap.id_article=235 or ap.id_article=236
  or ap.id_article=224 or ap.id_article=225 or ap.id_article=229)or(( ap.id_article=33
  or ap.id_article=40)and f.date_document between cast('01/01/2010'as date) and cast('12/31/2010' as date))
  into :id_tiers do
  begin

  for select f.id_document, f.id_tiers,lf.id_l_document,lf.id_article,a.code_article,lf.mt_ht_apr_rem_globale,
  lf.qte_l_document,  f.date_document,ap.points,ap.prix_reference
  from ta_facture f
  join ta_l_facture lf on lf.id_document=f.id_document
  join ta_article a on a.id_article=lf.id_article
  join ta_article_point ap on ap.id_article=a.id_article
  where f.id_tiers=:id_tiers and f.date_document between cast('01/01/2007' as date) and cast('12/31/2010' as date)
  and f.date_document between ap.debut_periode and ap.fin_periode

  into :id_document,:id_tiers,:id_l_document,:id_article_out,:code_article,
  :mt_ht_apr_rem_globale,:qte_l_document,:date_document,:pointpotentiel,:prix_reference
    do
    begin
    regle=0;
    net=0;
        select sum(rr.affectation) from ta_r_reglement rr where rr.id_facture=:id_document
        into :regle;
        select f1.net_ttc_calc from ta_facture f1 where f1.id_document=:id_document into :net;
        VALEURINTER=0;
        points=0;
        /*:regle+1<:net and*/
        if( :mt_ht_apr_rem_globale is not null and :mt_ht_apr_rem_globale <>0) then
          begin
            points=cast((:mt_ht_apr_rem_globale*:pointpotentiel)/:prix_reference as integer);
                  suspend;
          end

    end
  end
  end
  else
  begin
  /*parcourir tous les tiers qui ont acheté au moins un article générant des points bonus*/
    for select distinct t.id_tiers
  from ta_tiers t left
  join ta_facture f on f.id_tiers=t.id_tiers
  join ta_l_facture lf on lf.id_document=f.id_document
  join ta_article_point ap on ap.id_article=lf.id_article
  join ta_article a on a.id_article=ap.id_article
  where  ((ap.id_article=220 or ap.id_article=221  or ap.id_article=235 or ap.id_article=236
  or ap.id_article=224 or ap.id_article=225 or ap.id_article=229)or(( ap.id_article=33
  or ap.id_article=40)and f.date_document between cast('01/01/2010'as date) and cast('12/31/2010' as date)) )
  and t.id_tiers <>1140 into :id_tiers do
  begin
  /*parcourir tous les tiers qui ont acheté au moins un article générant des points bonus*/
  for select f.id_document, f.id_tiers,lf.id_l_document,lf.id_article,a.code_article,lf.mt_ht_apr_rem_globale,
  lf.qte_l_document,  f.date_document,ap.points,ap.prix_reference
  from ta_facture f
  join ta_l_facture lf on lf.id_document=f.id_document
  join ta_article a on a.id_article=lf.id_article
  join ta_article_point ap on ap.id_article=a.id_article
  where ap.id_article=:id_article
  and f.id_tiers=:id_tiers and f.date_document between cast('01/01/2007' as date) and cast('12/31/2010' as date)
  and f.date_document between ap.debut_periode and ap.fin_periode

  into :id_document,:id_tiers,:id_l_document,:id_article_out,:code_article,
  :mt_ht_apr_rem_globale,:qte_l_document,:date_document,:pointpotentiel,:prix_reference
    do
    begin
    regle=0;
    net=0;
        select sum(rr.affectation) from ta_r_reglement rr where rr.id_facture=:id_document
        into :regle;
        select f1.net_ttc_calc from ta_facture f1 where f1.id_document=:id_document into :net;
        VALEURINTER=0;
        points=0;
        /*:regle+1<:net and*/
        if( :mt_ht_apr_rem_globale is not null and :mt_ht_apr_rem_globale <>0) then
          begin
            points=cast((:mt_ht_apr_rem_globale*:pointpotentiel)/:prix_reference as integer);
                  suspend;
          end

    end
  end
  end
end
^
