set term ^;

CREATE or ALTER  PROCEDURE GESTION_ALTER_TABLE1
as    
DECLARE VARIABLE QUERY VARCHAR(255);
BEGIN
query ='';
IF (not exists( SELECT a.RDB$FIELD_NAME, a.RDB$RELATION_NAME FROM RDB$RELATION_FIELDS a
where a.RDB$RELATION_NAME = 'TA_COMPL' and  a.RDB$FIELD_NAME ='ACCISE')) THEN
  begin
    query = 'ALTER TABLE TA_COMPL ADD ACCISE varchar(13)';
    EXECUTE STATEMENT :query;
  end
END
^
EXECUTE PROCEDURE GESTION_ALTER_TABLE1
^
drop procedure GESTION_ALTER_TABLE1
^

alter TABLE TA_COMPL alter ACCISE position 4 
^


create table articles_fac(
id integer,
ART_COD varchar(14),
ART_DIV varchar(2),
ART_FAM varchar(4),
ART_DES varchar(53),
ART_HT1 numeric(15,2),
ART_TTC1 numeric(15,2),
ART_TVA numeric(15,2),
ART_CPT_TVA varchar(8), 
ART_UN1 varchar(14),
ART_UN2 varchar(14),
ART_1A2 numeric(15,2),
ART_CPT varchar(8),
ART_GTVA varchar(1),
ART_HT2 numeric(15,2),
ART_TTC2 numeric(15,2),
ART_HT3 numeric(15,2),
ART_TTC3 numeric(15,2),
ART_PRF numeric(15,2),
FAM_DES varchar(31),
FAM_NAR integer)
^

create or alter procedure IMPORTATION_ARTICLES_E2FAC
returns (
    ARTICLE varchar(20))
as
declare variable ART_COD varchar(14);
declare variable ART_DIV varchar(2);
declare variable ART_FAM varchar(4);
declare variable ART_DES varchar(53);
declare variable ART_HT1 numeric(15,2);
declare variable ART_TTC1 numeric(15,2);
declare variable ART_TVA numeric(15,2);
declare variable ART_CPT_TVA varchar(8);
declare variable ART_UN1 varchar(14);
declare variable ART_UN2 varchar(14);
declare variable ART_1A2 numeric(15,2);
declare variable ART_CPT varchar(8);
declare variable ART_GTVA varchar(1);
declare variable ART_HT2 numeric(15,2);
declare variable ART_TTC2 numeric(15,2);
declare variable ART_HT3 numeric(15,2);
declare variable ART_TTC3 numeric(15,2);
declare variable ART_PRF numeric(15,2);
declare variable FAM_DES varchar(31);
declare variable FAM_NAR integer;
declare variable ID_FAMILLE integer;
declare variable ID_TVA integer;
declare variable ID_T_TVA integer;
declare variable ID_ARTICLE integer;
declare variable ID_UNITE1 integer;
declare variable ID_UNITE2 integer;
declare variable ID_PRIX integer;
declare variable ID_RAPPORT integer;
declare variable CODEARTICLE varchar(14);
declare variable I integer;
declare variable REF_PRIX integer;
begin
  for select ART_COD,ART_DIV,ART_FAM,ART_DES,ART_HT1,ART_TTC1,ART_TVA,ART_CPT_TVA,ART_UN1,
ART_UN2,ART_1A2,ART_CPT,ART_GTVA,ART_HT2,ART_TTC2,ART_HT3,ART_TTC3,ART_PRF,FAM_DES,FAM_NAR from articles_fac a into
:ART_COD,ART_DIV,ART_FAM,ART_DES,ART_HT1,ART_TTC1,ART_TVA,ART_CPT_TVA,ART_UN1,ART_UN2,ART_1A2,ART_CPT,ART_GTVA,
ART_HT2,ART_TTC2,ART_HT3,ART_TTC3,ART_PRF,FAM_DES,FAM_NAR
  do begin
  /*famille article*/
  REF_PRIX=0;
  id_famille=null;
  id_tva=null;
  id_t_tva=null;
  if (:ART_FAM<>'' and not(:ART_FAM is null) and not exists(select fam.code_famille from ta_famille fam where fam.code_famille = :ART_FAM )) then
    begin
      if (:fam_des is null) then fam_des = 'Famille '||:ART_FAM;
      insert into ta_famille (code_famille,libc_famille,LIBL_FAMILLE,Version_obj) values (:ART_FAM,:fam_des,:fam_des,0);
    end
  select fam.id_famille from ta_famille fam where fam.code_famille = :ART_FAM into :id_famille;
  /*tva article*/
  if (:ART_TVA<>cast(0 as numeric(15,2)) and :ART_TVA is not null and not exists(
     select tva.TAUX_TVA from ta_tva tva where cast(tva.TAUX_TVA as numeric(15,2)) = :ART_TVA )) then
    begin
      if (:ART_CPT_TVA is null) then ART_CPT_TVA='';
      insert into ta_tva values (null,cast('V'||:art_tva as varchar(20)),cast('TVA sur '||:art_tva as varchar(255))
      ,:ART_TVA,:ART_CPT_TVA,null,null, null,null,null,null,0);
    end
  select tva.id_tva from ta_tva tva where tva.TAUX_TVA = :ART_TVA and upper(tva.code_tva) like('V%') into :id_tva;
  /*type tva article*/
  if (:art_gtva<>'' and not(:art_gtva is null) and not exists(select ttva.code_t_tva from ta_t_tva ttva where ttva.code_t_tva = :art_gtva )) then
    begin
      insert into ta_t_tva (code_t_tva,version_obj) values (:art_gtva,0);
    end
  select ttva.id_t_tva from ta_t_tva ttva where ttva.code_t_tva = :art_gtva into :id_t_tva;
  id_article=gen_id(num_id_article,1);
  codearticle=:art_cod;
/*  i=1;
  while (exists(select code_article from ta_article where code_article=:codearticle)) do
  begin
    codearticle=cast(:art_cod||i as varchar(20));
  end */
  if (:art_des is null) then art_des = 'Article '||:codearticle;

  if (:art_div is not null) then
      begin
          while (strlen(:codearticle)<13) do codearticle=:codearticle||' ';
          codearticle=:codearticle||:art_div;
      end
  insert into ta_article values(:id_article,:codearticle,:art_des,:art_des,:id_famille,:id_tva,:id_t_tva,null,null,null,null,null, :art_cpt,
  :art_div,null,0,1,null,null,null,null,null,null,null,null,null,null, null,null,0);
  article = :codearticle;

  /*Prix 1*/
  id_unite1=null;
  if ((:art_ht1 is not null and :art_ht1<> 0)or(trim(:art_un1)<>'' and :art_un1 is not null)) then
    begin
      /*unite article*/
      if (:art_un1<>'' and :art_un1 is not null and not exists(select u.code_unite from ta_unite U
          where upper(u.code_unite) = upper(:art_un1 ))) then
            begin
              insert into ta_unite (code_unite,version_obj) values (:art_un1,0);
            end
      select u.id_unite from ta_unite u where upper(u.code_unite) = upper(:art_un1)  into :id_unite1 ;
       id_prix = gen_id(num_id_prix,1);
       insert into ta_prix values(:id_prix,:id_article,:id_unite1,:art_ht1,:art_ttc1,null,null,null,null,null,null,null,null,0);
       if (:art_prf=0 or (:art_prf is null) or (:art_prf=art_ht1)or(:art_prf<>art_ht2 and :art_prf<>art_ht3)) then
         begin
         REF_PRIX=1;
         insert into ta_ref_prix values(null,:id_article,:id_prix,null,null,null,null,null,null,0);
         update ta_article set id_prix=:id_prix where id_article=:id_article;
         end

    end
  /*Prix 2*/
  id_unite2=null;
  if ((:art_ht2 is not null and :art_ht2<> 0)or(trim(:art_un2)<>'' and :art_un2 is not null)) then
    begin
      /*unite article*/
      if (:art_un2<>'' and not(:art_un2 is null) and not exists(select u.code_unite from ta_unite U
          where upper(u.code_unite) = upper(:art_un2) )) then
            begin
              insert into ta_unite (code_unite,version_obj) values (:art_un2,0);
            end
          select u.id_unite from ta_unite u where upper(u.code_unite) = upper(:art_un2) into :id_unite2;
       id_prix = gen_id(num_id_prix,1);
       insert into ta_prix values(:id_prix,:id_article,:id_unite2,:art_ht2,:art_ttc2,null,null,null,null,null,null,null,null,0);
       if ((:REF_PRIX=0) and ( (:art_prf=art_ht2)or(:art_prf<>art_ht1 and :art_prf<>art_ht3))) then
         begin
         REF_PRIX=2;
         insert into ta_ref_prix values(null,:id_article,:id_prix,null,null,null,null,null,null,0);
         update ta_article set id_prix=:id_prix where id_article=:id_article;
         end
    end
    if(:id_unite1 is not null and :id_unite2 is not null and :ART_1A2 is not null)then
    begin
       id_rapport=gen_id(num_id_rapport_unite,1);
       insert into ta_rapport_unite  values(:id_rapport,:id_article,:id_unite1,:id_unite2,:art_1a2,2,1,null,
          null,null,null,null,null,0);
       update ta_article set  id_rapport_unite=:id_rapport where id_article=:id_article;
    end
  /*Prix 3*/
  if (:art_ht3 is not null and :art_ht3<> 0) then
    begin
       id_prix = gen_id(num_id_prix,1);
       insert into ta_prix values(:id_prix,:id_article,null,:art_ht3,:art_ttc3,null,null,null,null,null,null,null,null,0);
       if ((:REF_PRIX=0) and( (:art_prf=art_ht3)or(:art_prf<>art_ht1 and :art_prf<>art_ht2))) then
         begin
         insert into ta_ref_prix values(null,:id_article,:id_prix,null,null,null,null,null,null,0);
         update ta_article set id_prix=:id_prix where id_article=:id_article;
         end
    end

  suspend;
  end

end
^

create table clients_E2Fac(
id integer,
CLI_COD varchar(8),
CLI_NOM varchar(31),
CLI_CPT varchar(8),
CLI_COL varchar(9),
CLI_AD1 varchar(31),
CLI_AD2 varchar(31),
CLI_CP varchar(6),
CLI_VIL varchar(26),
CLI_PAY varchar(21),
CLI_TVA varchar(15),
CLI_ASS varchar(13),
CLI_OBS varchar(31),
CLI_RSP varchar(31),
CLI_TEL varchar(16),
CLI_FAX varchar(16),
CLI_PMT varchar(2),
CLI_PRI varchar(2),
CLI_BQE varchar(31),
CLI_RIB varchar(24),
CLI_LAD1 varchar(31),
CLI_LAD2 varchar(31),
CLI_LCP varchar(6),
CLI_LVIL varchar(26),
CLI_LPAY varchar(21),
CLI_REP varchar(8),
CLI_CIV varchar(13),
CLI_FAM varchar(4),
CLI_TEL0 varchar(16),
CLI_FAX0 varchar(16),
PMT_INT varchar(31),
PMT_DELAI integer,
PMT_JOUR integer,
PMT_LCR varchar(2),
CLI_LOC integer)
^

create or alter procedure IMPORTATION_CLIENTS_E2FAC
returns (
    NB_CLIENT integer)
as
declare variable CLI_COD varchar(8);
declare variable CLI_NOM varchar(31);
declare variable CLI_CPT varchar(8);
declare variable CLI_COL varchar(9);
declare variable CLI_AD1 varchar(31);
declare variable CLI_AD2 varchar(31);
declare variable CLI_CP varchar(6);
declare variable CLI_VIL varchar(26);
declare variable CLI_PAY varchar(21);
declare variable CLI_TVA varchar(15);
declare variable CLI_ASS varchar(13);
declare variable CLI_OBS varchar(31);
declare variable CLI_RSP varchar(31);
declare variable CLI_TEL varchar(16);
declare variable CLI_FAX varchar(16);
declare variable CLI_PMT varchar(2);
declare variable CLI_PRI varchar(2);
declare variable CLI_BQE varchar(31);
declare variable CLI_RIB varchar(24);
declare variable CLI_LAD1 varchar(31);
declare variable CLI_LAD2 varchar(31);
declare variable CLI_LCP varchar(6);
declare variable CLI_LVIL varchar(26);
declare variable CLI_LPAY varchar(21);
declare variable CLI_REP varchar(8);
declare variable CLI_CIV varchar(13);
declare variable ID_FAMILLE_TIERS integer;
declare variable CLI_FAM1 varchar(4);
declare variable CLI_TEL0 varchar(16);
declare variable CLI_FAX0 varchar(16);
declare variable PMT_INT varchar(31);
declare variable PMT_DELAI integer;
declare variable PMT_JOUR integer;
declare variable PMT_LCR varchar(2);
declare variable CRLF char(2);
declare variable ID_T_TEL_FAX integer;
declare variable ID_COMMENTAIRE integer;
declare variable ID_COMPL integer;
declare variable ID_T_ADR_LIV integer;
declare variable ID_T_ADR_FACT integer;
declare variable ID_T_TIERS integer;
declare variable ID_TELEPHONE integer;
declare variable ID_ADRESSE integer;
declare variable ID_BANQUE integer;
declare variable ID_CIVILITE integer;
declare variable ID_TIERS_COM integer;
declare variable ID_C_PAIEMENT integer;
declare variable ID_TIERS integer;
declare variable CODETMP varchar(7);
declare variable I integer;
declare variable CODETMP2 varchar(7);
declare variable COMMENTAIRE varchar(1000);
declare variable COMPTE_BANQUE varchar(11);
declare variable GUICHET varchar(5);
declare variable CODE_BANQUE varchar(5);
declare variable CLE_RIB varchar(2);
declare variable CLI_LOC integer;
begin
nb_client=0;
CRLF = ASCII_CHAR(13)||ASCII_CHAR(10);
  select ttel.id_t_tel from ta_t_tel ttel where upper(ttel.code_t_tel) = 'FAX' into :id_t_tel_Fax;
  select tadr.id_t_adr from ta_t_adr tadr where upper(tadr.code_t_adr) = 'FACT' into :id_t_adr_Fact;
  select tadr.id_t_adr from ta_t_adr tadr where upper(tadr.code_t_adr) = 'LIV' into :id_t_adr_Liv;

  for select CLI_COD,CLI_NOM,CLI_CPT,CLI_COL,CLI_AD1,CLI_AD2,CLI_CP,CLI_VIL,CLI_PAY,CLI_TVA,
CLI_ASS,CLI_OBS,CLI_RSP,CLI_TEL,CLI_FAX,CLI_PMT,CLI_PRI,CLI_BQE,CLI_RIB,CLI_LAD1,CLI_LAD2,
CLI_LCP,CLI_LVIL,CLI_LPAY,CLI_REP,CLI_CIV,CLI_FAM,CLI_TEL0,CLI_FAX0,PMT_INT,PMT_DELAI,
PMT_JOUR,PMT_LCR,CLI_LOC from clients_e2fac c into
:CLI_COD,:CLI_NOM,:CLI_CPT,:CLI_COL,:CLI_AD1,:CLI_AD2,:CLI_CP,:CLI_VIL,:CLI_PAY,:CLI_TVA,
:CLI_ASS,:CLI_OBS,:CLI_RSP,:CLI_TEL,:CLI_FAX,:CLI_PMT,:CLI_PRI,:CLI_BQE,:CLI_RIB,:CLI_LAD1,:CLI_LAD2,
:CLI_LCP,:CLI_LVIL,:CLI_LPAY,:CLI_REP,:CLI_CIV,:CLI_FAM1,:CLI_TEL0,:CLI_FAX0,:PMT_INT,:PMT_DELAI,
:PMT_JOUR,:PMT_LCR,:CLI_LOC
  do begin
  id_banque=null;
  id_adresse=null;
  id_c_paiement=null;
  id_civilite=null;
  id_telephone=null;
  id_tiers_com=null;
  id_t_tiers=null;
  id_tiers=null;

  /* correspondance localisation TVA*/
  if(:cli_loc = 1)then select td.id_t_tva_doc from ta_t_tva_doc td where upper(td.code_t_tva_doc) like 'F' into :cli_loc;
  if(:cli_loc = 2)then select td.id_t_tva_doc from ta_t_tva_doc td where upper(td.code_t_tva_doc) like 'UE' into :cli_loc;
  if(:cli_loc = 3)then select td.id_t_tva_doc from ta_t_tva_doc td where upper(td.code_t_tva_doc) like 'HUE' into :cli_loc;
  if(:cli_loc = 3)then select td.id_t_tva_doc from ta_t_tva_doc td where upper(td.code_t_tva_doc) like 'N' into :cli_loc;

  if (:cli_pri is null) then cli_pri=0;
  /*if (:cli_cpt is null) then cli_cpt='411';*/
 /* select ttiers.id_t_tiers from ta_t_tiers ttiers where upper(ttiers.code_t_tiers) = UPPER(:cli_fam) into :id_t_tiers;*/
   select ttiers.id_t_tiers from ta_t_tiers ttiers where upper(ttiers.code_t_tiers) = 'C' into :id_t_tiers;
  if (:cli_civ<>'' and not exists(select civ.code_t_civilite from ta_t_civilite civ where civ.code_t_civilite = :cli_civ )) then
    begin
      insert into ta_t_civilite (code_t_civilite,version_obj) values (:cli_civ,0);
    end
  select civ.id_t_civilite from ta_t_civilite civ where civ.code_t_civilite = :cli_civ into :id_civilite;

   ID_C_PAIEMENT=null;
  if (:cli_pmt is not null and not exists(select code_c_paiement from ta_c_paiement where code_c_paiement=:cli_pmt)) then
  begin
  ID_C_PAIEMENT=gen_id(num_id_c_paiement,1);
     insert into ta_c_paiement values(:ID_C_PAIEMENT,1,:cli_pmt,:pmt_int,:pmt_delai,:pmt_jour,null,null,null,null,null,null,0);
  end

  id_commentaire=null;
  commentaire='';
  if (:cli_obs is not null or (:cli_rsp is not null)) then
  begin
  id_commentaire=gen_id(num_id_commentaire,1);
    if (:cli_rsp is not null) then   commentaire =:cli_rsp||CRLF;
    if (:cli_obs is not null) then   commentaire =commentaire||:cli_obs;
    insert into ta_commentaire values(:id_commentaire,:commentaire,null,null,null,null,null,null,0);
  end

  id_compl=null;
  if (:cli_tva is not null or:cli_ass is not null  ) then
  begin
    id_compl=gen_id(num_id_compl,1);
    insert into ta_compl values(:id_compl,:cli_tva,null,:cli_ass,null,null,null,null,null,null,null,0);
  end


  id_tiers=gen_id(num_id_tiers,1);
  if (:cli_cpt is null) then cli_cpt=:cli_cod;
  if (:cli_nom is null) then cli_nom= :cli_cod;
  if (:cli_col is null) then cli_col='411';
  insert into ta_tiers values(:id_tiers,:cli_cod,:cli_cpt,:cli_col,:cli_nom,null,null,1,:cli_pri,null,:id_commentaire,:id_civilite,
    null,:id_t_tiers,null,  :ID_C_PAIEMENT, null,:id_compl,null,null,null,null,null, :cli_loc,null,null,null,null,null,null,null,null,null,null,null,0);

  if(cli_fam1 is not null and cli_fam1<>'')then
  begin
     if(not exists(select ft.code_famille from ta_famille_tiers ft where ft.code_famille like :cli_fam1))then
       begin
        insert into ta_famille_tiers values(gen_id(num_id_famille_tiers,1),:cli_fam1,:cli_fam1,null,null,null,null,null,null,null,0);
       end
     select ft.id_famille from ta_famille_tiers ft where ft.code_famille like :cli_fam1 into :id_famille_tiers;
     insert into ta_r_famille_tiers  values(gen_id(num_id_r_famille_tiers,1),:id_famille_tiers,:id_tiers,null,null,null,null,null,null,0);
  end

compte_banque='00000000000';
  if (:cli_bqe is not null and :cli_bqe<>'' ) then
    begin
    if (:cli_rib is not null and :cli_rib<>'') then
      begin
         code_banque=substr(:cli_rib,1,5);
         guichet=substr(:cli_rib,6,10);
         compte_banque=substr(:cli_rib,11,21);
         cle_rib=substr(:cli_rib,22,24);
      end
      insert into ta_compte_banque values (null,:id_tiers,upper(substr(:cli_bqe,1,20) ),:compte_banque,:code_banque
      ,:guichet,:cle_rib,null, null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,0);
    end
  select b.id_compte_banque from ta_compte_banque b where b.nom_banque = upper(substr(:cli_bqe,1,20) )and
  (b.code_banque||B.code_guichet||b.compte||b.cle_rib like(:code_banque||:guichet||:compte_banque||:cle_rib)) into :id_banque;


/*  if (:cli_rep is not null ) then
  begin
    select t.id_tiers from ta_tiers t where t.code_tiers = :cli_rep into :id_tiers_com;
    if (:id_tiers_com is not null) then
      insert into ta_r_commercial values(null,:id_tiers,:id_tiers_com,null,null,null,null,null,null);
  end
*/
if(:cli_cp is null and(
(:cli_ad1 is not null and trim(:cli_ad1)<>'') or (:cli_ad2 is not null and trim(:cli_ad2)<>'') or (:cli_vil is not null and trim(:cli_vil)<>'')
 or (:cli_pay is not null and trim(:cli_pay)<>'')  )) then cli_cp='-';

  id_adresse=null;
  if (:cli_vil is null) then cli_vil=' ';
  if (:cli_pay is null) then  cli_pay='FRANCE';
  if (:cli_cp is not null)  then begin
    id_adresse=gen_id(num_id_adresse,1);
  insert into ta_adresse values(:id_adresse,:cli_ad1,:cli_ad2,null, :cli_cp,:cli_vil,:cli_pay,:id_tiers,:id_t_adr_Fact,0,0,null,null,
  null,null,null,null, null,null,0);
  update ta_tiers set id_adresse = :id_adresse where id_tiers=:id_tiers;
  end

  id_adresse=null;
  if (:cli_lvil is null) then cli_lvil=' ';
  if (:cli_lpay is null) then  cli_lpay='FRANCE';
  if (:cli_lcp is not null)  then begin
    id_adresse=gen_id(num_id_adresse,1);

  insert into ta_adresse values(:id_adresse,:cli_lad1,:cli_lad2,null, :cli_lcp,:cli_lvil,:cli_lpay,:id_tiers,:id_t_adr_Liv,0,0,null,null,
  null,null,null,null, null,null,0);
  end

  id_telephone = null;
  if (:cli_tel is not null) then begin
  id_telephone = gen_id(num_id_telephone,1);
  insert into ta_telephone values(:id_telephone,:cli_tel,null,null,:id_tiers,null,0,0,null,null,null,null,null,null,null,null,0);
   update ta_tiers set id_telephone = :id_telephone where id_tiers=:id_tiers;
  end

  id_telephone = null;
  if (:cli_fax is not null) then begin
  id_telephone = gen_id(num_id_telephone,1);
  insert into ta_telephone values(:id_telephone,:cli_fax,null,null,:id_tiers,:id_t_tel_Fax,0,0,null,null, null,null,null,null,null,null,0);
  end

  id_telephone = null;
  if (:cli_tel0 is not null) then begin
  id_telephone = gen_id(num_id_telephone,1);
  insert into ta_telephone values(:id_telephone,:cli_tel0,null,null,:id_tiers,null,0,0,null,null,null,null,null,null,null,null,0);
  end

  id_telephone = null;
  if (:cli_fax0 is not null) then begin
  id_telephone = gen_id(num_id_telephone,1);
  insert into ta_telephone values(:id_telephone,:cli_fax0,null,null,:id_tiers,:id_t_tel_Fax,0,0,null,null,null,null,null,null,null,null,0);
  end

  nb_client=:nb_client+1;
  end
  suspend;
end
^

create table Documents_E2Fac(
FAC_NUM varchar(8),
FAC_TYP varchar(2),
FAC_DAT Date,
FAC_ECH Date,
FAC_COD varchar(8),
FAC_NOM varchar(31),
FAC_CPT varchar(8),
FAC_COL varchar(9),
FAC_AD1 varchar(31),
FAC_AD2 varchar(31),
FAC_CP varchar(6),
FAC_VIL varchar(26),
FAC_PAY varchar(21),
FAC_REF varchar(41),
FAC_PMT varchar(2),
FAC_PRI varchar(2),
FAC_HT numeric(15,2),
FAC_TTC numeric(15,2),
FAC_REMP numeric(15,2),
FAC_REM numeric(15,2),
FAC_REMT numeric(15,2),
FAC_ESCP numeric(15,2),
FAC_ESC numeric(15,2),
FAC_MES1 varchar(69),
FAC_MES2 varchar(69),
FAC_MES3 varchar(69),
FAC_CPTA integer,
FAC_EDITE integer,
FAC_FAC varchar(8),
FAC_ACT numeric(15,2),
FAC_CIAL varchar(8),
FAC_MREG numeric(15,2),
fac_loc varchar(10))
^

create table lignes_Docs_E2Fac(
Type_Docs varchar(1),
FLI_NUM varchar(8),
FLI_LIG integer,
FLI_COD varchar(15),
FLI_DES varchar(53),
FLI_QT1 numeric(15,4),    
FLI_QT2     numeric(15,4),
FLI_PHT     numeric(15,2),
FLI_PTC     numeric(15,2),
FLI_REM     numeric(15,2),
FLI_MHT     numeric(15,2),
FLI_MTC     numeric(15,2),
FLI_MTVA numeric(15,2),
FLI_TXTVA numeric(15,2),    
FLI_CPT     varchar(9),
FLI_TMP     varchar(2),
FLI_EXIG varchar(2),
FLI_LOT     varchar(16),
FLI_NBL     varchar(8),
FLI_UN1     varchar(3),
FLI_UN2 varchar(3))
^


CREATE OR ALTER PROCEDURE IMPORTATION_FACTURES_E2FAC 
returns (
    documents varchar(8))
as
declare variable compteur integer;
declare variable fac_num varchar(8);
declare variable fac_typ varchar(2);
declare variable fac_dat date;
declare variable fac_ech date;
declare variable fac_cod varchar(8);
declare variable fac_nom varchar(31);
declare variable fac_cpt varchar(8);
declare variable fac_col varchar(9);
declare variable fac_ad1 varchar(31);
declare variable fac_ad2 varchar(31);
declare variable fac_cp varchar(6);
declare variable fac_vil varchar(26);
declare variable fac_pay varchar(21);
declare variable fac_ref varchar(41);
declare variable fac_pmt varchar(2);
declare variable fac_pri varchar(2);
declare variable fac_ht numeric(15,2);
declare variable fac_ttc numeric(15,2);
declare variable fac_remp numeric(15,2);
declare variable fac_rem numeric(15,2);
declare variable fac_remt numeric(15,2);
declare variable fac_escp numeric(15,2);
declare variable fac_esc numeric(15,2);
declare variable fac_mes1 varchar(69);
declare variable fac_mes2 varchar(69);
declare variable fac_mes3 varchar(69);
declare variable fac_cpta integer;
declare variable fac_edite integer;
declare variable fac_fac varchar(8);
declare variable fac_act numeric(15,2);
declare variable fac_cial varchar(8);
declare variable fac_mreg numeric(15,2);
declare variable fac_loc integer;
declare variable id_tiers integer;
declare variable id_t_paiement integer;
declare variable commentaire varchar(1000);
declare variable crlf char(2);
declare variable id_facture integer;
declare variable civilite varchar(20);
declare variable tvaintra varchar(50);
declare variable code_c_paiement varchar(20);
declare variable lib_c_paiement varchar(255);
declare variable report_c_paiement integer;
declare variable fin_mois_c_paiement integer;
declare variable id_c_paiement integer;
declare variable type_docs varchar(1);
declare variable fli_num varchar(8);
declare variable fli_lig integer;
declare variable fli_cod varchar(15);
declare variable fli_des varchar(53);
declare variable fli_qt1 numeric(15,4);
declare variable fli_qt2 numeric(15,4);
declare variable fli_pht numeric(15,2);
declare variable fli_ptc numeric(15,2);
declare variable fli_rem numeric(15,2);
declare variable fli_mht numeric(15,2);
declare variable fli_mtc numeric(15,2);
declare variable fli_mtva numeric(15,2);
declare variable fli_txtva numeric(15,2);
declare variable fli_cpt varchar(9);
declare variable fli_tmp varchar(2);
declare variable fli_exig varchar(2);
declare variable fli_lot varchar(16);
declare variable fli_nbl varchar(8);
declare variable fli_un1 varchar(3);
declare variable fli_un2 varchar(3);
declare variable t_ligne_h integer;
declare variable id_article integer;
declare variable rang integer;
declare variable remiset numeric(15,2);
declare variable code_tva varchar(20);
declare variable t_ligne_c integer;
declare variable t_ligne integer;
declare variable prix numeric(15,2);
declare variable lignevide integer;
declare variable LOCALISATION_TVA varchar(10);

begin
CRLF = ASCII_CHAR(13)||ASCII_CHAR(10);
execute procedure MAJ_GENERATEUR  ;
select tl.id_t_ligne from ta_t_ligne tl where upper(tl.code_t_ligne) ='H' into :t_ligne_h;
select tl.id_t_ligne from ta_t_ligne tl where upper(tl.code_t_ligne) ='C' into :t_ligne_c;
for select d.fac_num,d.fac_typ,d.fac_dat,d.fac_ech,d.fac_cod,d.fac_nom,d.fac_cpt,d.fac_col
,d.fac_ad1,d.fac_ad2,d.fac_cp,d.fac_vil,d.fac_pay,d.fac_ref,d.fac_pmt,d.fac_pri,d.fac_ht,d.fac_ttc,d.fac_remp
,d.fac_rem,d.fac_remt,d.fac_escp,d.fac_esc,d.fac_mes1,d.fac_mes2,d.fac_mes3,d.fac_cpta
,d.fac_edite,d.fac_fac,d.fac_act,d.fac_cial,d.fac_mreg,d.fac_loc from documents_e2fac d where upper(d.fac_typ)='F'

into
fac_num,fac_typ,fac_dat,fac_ech,fac_cod,fac_nom,fac_cpt,fac_col
,fac_ad1,fac_ad2,fac_cp,fac_vil,fac_pay,fac_ref,fac_pmt,fac_pri,fac_ht,fac_ttc,fac_remp
,fac_rem,fac_remt,fac_escp,fac_esc,fac_mes1,fac_mes2,fac_mes3,fac_cpta
,fac_edite,fac_fac,fac_act,fac_cial,fac_mreg,fac_loc
do begin
commentaire ='';
id_c_paiement=null;
code_c_paiement=null;
lib_c_paiement=null;
report_c_paiement=null;
fin_mois_c_paiement=null;
documents='';
civilite=null;
tvaintra=null;

  /* correspondance localisation TVA*/
  if(:fac_loc = 1)then localisation_tva= 'F';
  if(:fac_loc = 2)then localisation_tva='UE' ;
  if(:fac_loc = 3)then localisation_tva= 'HUE' ;
  if(:fac_loc = 3)then localisation_tva= 'N';

if(:fac_mreg is null)then fac_mreg=0;
select cp.id_c_paiement, cp.code_c_paiement,cp.lib_c_paiement,cp.report_c_paiement,
  cp.fin_mois_c_paiement from ta_c_paiement cp where cp.code_c_paiement = :fac_pmt into
  :id_c_paiement,:code_c_paiement,:lib_c_paiement,:report_c_paiement,:fin_mois_c_paiement;
       select id_tiers from ta_tiers where Upper(code_tiers) = upper(:fac_cod) into :id_tiers;
       if (:fac_ref is not null) then commentaire = :fac_ref ||CRLF;
       if (:fac_mes1 is not null) then commentaire = :commentaire ||:fac_mes1||CRLF;
       if (:fac_mes2 is not null) then commentaire = :commentaire ||:fac_mes2||CRLF;
       if (:fac_mes3 is not null) then commentaire = :commentaire ||:fac_mes3||CRLF;
       id_facture = gen_id(num_id_document,1);
       insert into ta_facture values(:id_facture,:fac_num,:fac_dat,:fac_ech,:fac_dat,'Facture E2-Fac N '||:fac_num,
       :id_tiers,null,null,:fac_mreg,:fac_rem,:fac_remp,:fac_escp,:fac_esc,:fac_edite,:fac_pri,:fac_cpta,
       :commentaire,null,null,null,null,null,null,null,null,null,null,null, null,null,null,null,null,null,0);
       select c.code_t_civilite from ta_tiers v
       join ta_t_civilite c on(c.id_t_civilite=v.id_t_civilite)
       where
       Upper(v.code_tiers) = upper(:fac_cod) into :civilite;

       select cp.tva_i_com_compl from ta_tiers v
       join ta_compl cp on cp.id_compl=v.id_compl
       where
       Upper(v.code_tiers) = upper(:fac_cod) into :tvaintra;

       insert into ta_infos_facture values(gen_id(num_id_infos_document,1),:id_facture,:fac_ad1,:fac_ad2,null,:fac_cp,:fac_vil,:fac_pay,
       :fac_ad1,:fac_ad2,null,:fac_cp,:fac_vil,:fac_pay,:fac_cpt,:fac_col,:fac_nom,null,null,:civilite,null,:tvaIntra,
       :code_c_paiement,:lib_c_paiement,:report_c_paiement,:fin_mois_c_paiement,null,null,:LOCALISATION_TVA,null,null,null,null,null, null,0);
       rang=1;
       for select l.type_docs,l.fli_num,l.fli_lig,l.fli_cod,l.fli_des,l.fli_qt1,l.fli_qt2
       ,l.fli_pht,l.fli_ptc,l.fli_rem,l.fli_mht,l.fli_mtc,l.fli_mtva,l.fli_txtva,l.fli_cpt
       ,l.fli_tmp,l.fli_exig,l.fli_lot,l.fli_nbl,l.fli_un1,l.fli_un2 from lignes_docs_e2fac l
       where l.fli_num = :fac_num and l.type_docs = 'F' into
       type_docs,fli_num,fli_lig,fli_cod,fli_des,fli_qt1,fli_qt2
       ,fli_pht,fli_ptc,fli_rem,fli_mht,fli_mtc,fli_mtva,fli_txtva,fli_cpt
       ,fli_tmp,fli_exig,fli_lot,fli_nbl,fli_un1,fli_un2
       do begin
       remiseT =0;
       code_tva=null;
       id_article=null;
       prix=null;
       if (:fac_pri=0) then prix=:fli_pht;
       else prix=:fli_ptc;
       if (:fli_cpt is null) then t_ligne = :t_ligne_c;
       else t_ligne = :t_ligne_h;
       if (:fli_rem is not null and :fli_rem<>0) then
         remiset = (fli_rem);
       if (:fac_pri=0) then fli_rem= (:prix*:fli_qt1)-:fli_mht;
       else  fli_rem= (:prix*:fli_qt1)-:fli_mtc;
       select t.code_tva from ta_tva t where t.taux_tva = :fli_txtva and t.numcpt_tva like('4457%') into :code_tva;
       if (:code_tva is null) then
          select t.code_tva from ta_tva t where t.taux_tva = :fli_txtva into :code_tva;
       select id_article from ta_article where code_article = :fli_cod into :id_article;
       if (:id_article is null and :fli_des is null) then lignevide=:lignevide+1;
       else lignevide=0;
       if (:lignevide<3) then
       insert into ta_l_facture values(gen_id(num_id_l_document,1),:id_facture,:t_ligne,:id_article,:rang,:fli_des,:fli_qt1,null,:fli_un1,:fli_un2,
            :prix,:fli_txtva,:fli_cpt,:code_tva,:fli_exig,:fli_mht,:fli_mtc,:remiseT,:fli_rem,null,null,null,null,null,null,
            null,null,null, null,0);

            rang=:rang+1;
          end
   documents=:fac_num;
  suspend;
   end
end
^


CREATE OR ALTER PROCEDURE IMPORTATION_AVOIR_E2FAC 
returns (
    documents varchar(8))
as
declare variable compteur integer;
declare variable fac_num varchar(8);
declare variable fac_typ varchar(2);
declare variable fac_dat date;
declare variable fac_ech date;
declare variable fac_cod varchar(8);
declare variable fac_nom varchar(31);
declare variable fac_cpt varchar(8);
declare variable fac_col varchar(9);
declare variable fac_ad1 varchar(31);
declare variable fac_ad2 varchar(31);
declare variable fac_cp varchar(6);
declare variable fac_vil varchar(26);
declare variable fac_pay varchar(21);
declare variable fac_ref varchar(41);
declare variable fac_pmt varchar(2);
declare variable fac_pri varchar(2);
declare variable fac_ht numeric(15,2);
declare variable fac_ttc numeric(15,2);
declare variable fac_remp numeric(15,2);
declare variable fac_rem numeric(15,2);
declare variable fac_remt numeric(15,2);
declare variable fac_escp numeric(15,2);
declare variable fac_esc numeric(15,2);
declare variable fac_mes1 varchar(69);
declare variable fac_mes2 varchar(69);
declare variable fac_mes3 varchar(69);
declare variable fac_cpta integer;
declare variable fac_edite integer;
declare variable fac_fac varchar(8);
declare variable fac_act numeric(15,2);
declare variable fac_cial varchar(8);
declare variable fac_mreg numeric(15,2);
declare variable fac_loc integer;
declare variable id_tiers integer;
declare variable id_t_paiement integer;
declare variable commentaire varchar(1000);
declare variable crlf char(2);
declare variable id_facture integer;
declare variable civilite varchar(20);
declare variable tvaintra varchar(50);
declare variable code_c_paiement varchar(20);
declare variable lib_c_paiement varchar(255);
declare variable report_c_paiement integer;
declare variable fin_mois_c_paiement integer;
declare variable id_c_paiement integer;
declare variable type_docs varchar(1);
declare variable fli_num varchar(8);
declare variable fli_lig integer;
declare variable fli_cod varchar(15);
declare variable fli_des varchar(53);
declare variable fli_qt1 numeric(15,4);
declare variable fli_qt2 numeric(15,4);
declare variable fli_pht numeric(15,2);
declare variable fli_ptc numeric(15,2);
declare variable fli_rem numeric(15,2);
declare variable fli_mht numeric(15,2);
declare variable fli_mtc numeric(15,2);
declare variable fli_mtva numeric(15,2);
declare variable fli_txtva numeric(15,2);
declare variable fli_cpt varchar(9);
declare variable fli_tmp varchar(2);
declare variable fli_exig varchar(2);
declare variable fli_lot varchar(16);
declare variable fli_nbl varchar(8);
declare variable fli_un1 varchar(3);
declare variable fli_un2 varchar(3);
declare variable t_ligne_h integer;
declare variable id_article integer;
declare variable rang integer;
declare variable remiset numeric(15,2);
declare variable code_tva varchar(20);
declare variable t_ligne_c integer;
declare variable t_ligne integer;
declare variable prix numeric(15,2);
declare variable lignevide integer;
declare variable LOCALISATION_TVA varchar(10);
begin
CRLF = ASCII_CHAR(13)||ASCII_CHAR(10);
execute procedure MAJ_GENERATEUR  ;
select tl.id_t_ligne from ta_t_ligne tl where upper(tl.code_t_ligne) ='H' into :t_ligne_h;
select tl.id_t_ligne from ta_t_ligne tl where upper(tl.code_t_ligne) ='C' into :t_ligne_c;
for select d.fac_num,d.fac_typ,d.fac_dat,d.fac_ech,d.fac_cod,d.fac_nom,d.fac_cpt,d.fac_col
,d.fac_ad1,d.fac_ad2,d.fac_cp,d.fac_vil,d.fac_pay,d.fac_ref,d.fac_pmt,d.fac_pri,d.fac_ht,d.fac_ttc,d.fac_remp
,d.fac_rem,d.fac_remt,d.fac_escp,d.fac_esc,d.fac_mes1,d.fac_mes2,d.fac_mes3,d.fac_cpta
,d.fac_edite,d.fac_fac,d.fac_act,d.fac_cial,d.fac_mreg,d.fac_loc from documents_e2fac d where upper(d.fac_typ)='A'

into
fac_num,fac_typ,fac_dat,fac_ech,fac_cod,fac_nom,fac_cpt,fac_col
,fac_ad1,fac_ad2,fac_cp,fac_vil,fac_pay,fac_ref,fac_pmt,fac_pri,fac_ht,fac_ttc,fac_remp
,fac_rem,fac_remt,fac_escp,fac_esc,fac_mes1,fac_mes2,fac_mes3,fac_cpta
,fac_edite,fac_fac,fac_act,fac_cial,fac_mreg,fac_loc
do begin
commentaire ='';
id_c_paiement=null;
code_c_paiement=null;
lib_c_paiement=null;
report_c_paiement=null;
fin_mois_c_paiement=null;
documents='';

  /* correspondance localisation TVA*/
  if(:fac_loc = 1)then localisation_tva= 'F';
  if(:fac_loc = 2)then localisation_tva='UE' ;
  if(:fac_loc = 3)then localisation_tva= 'HUE' ;
  if(:fac_loc = 3)then localisation_tva= 'N';
  
  
if(:fac_mreg is null)then fac_mreg=0;
select cp.id_c_paiement, cp.code_c_paiement,cp.lib_c_paiement,cp.report_c_paiement,
  cp.fin_mois_c_paiement from ta_c_paiement cp where cp.code_c_paiement = :fac_pmt into
  :id_c_paiement,:code_c_paiement,:lib_c_paiement,:report_c_paiement,:fin_mois_c_paiement;
       select id_tiers from ta_tiers where Upper(code_tiers) = upper(:fac_cod) into :id_tiers;
       if (:fac_ref is not null) then commentaire = :fac_ref ||CRLF;
       if (:fac_mes1 is not null) then commentaire = :commentaire ||:fac_mes1||CRLF;
       if (:fac_mes2 is not null) then commentaire = :commentaire ||:fac_mes2||CRLF;
       if (:fac_mes3 is not null) then commentaire = :commentaire ||:fac_mes3||CRLF;
       id_facture = gen_id(num_id_document,1);
       insert into ta_avoir values(:id_facture,:fac_num,:fac_dat,:fac_ech,:fac_dat,'Avoir E2-Fac N '||:fac_num,
       :id_tiers,null,:fac_mreg,:fac_rem,:fac_remp,:fac_escp,:fac_esc,:fac_edite,:fac_pri,:fac_cpta,
       :commentaire,null,null,null,null,null,null,null,null,null,null,null, null,null,null,0);
       select c.code_t_civilite from ta_tiers v
       join ta_t_civilite c on(c.id_t_civilite=v.id_t_civilite)
       where
       Upper(v.code_tiers) = upper(:fac_cod) into :civilite;

       select cp.tva_i_com_compl from ta_tiers v
       join ta_compl cp on cp.id_compl=v.id_compl
       where
       Upper(v.code_tiers) = upper(:fac_cod) into :tvaintra;

       insert into ta_infos_avoir values(gen_id(num_id_infos_document,1),:id_facture,:fac_ad1,:fac_ad2,null,:fac_cp,:fac_vil,:fac_pay,
       :fac_ad1,:fac_ad2,null,:fac_cp,:fac_vil,:fac_pay,:fac_cpt,:fac_col,:fac_nom,null,null,:civilite,null,:tvaIntra,
       :code_c_paiement,:lib_c_paiement,:report_c_paiement,:fin_mois_c_paiement,null,null,:LOCALISATION_TVA,null,null,null,null,null, null,0);
       rang=1;
       for select l.type_docs,l.fli_num,l.fli_lig,l.fli_cod,l.fli_des,l.fli_qt1,l.fli_qt2
       ,l.fli_pht,l.fli_ptc,l.fli_rem,l.fli_mht,l.fli_mtc,l.fli_mtva,l.fli_txtva,l.fli_cpt
       ,l.fli_tmp,l.fli_exig,l.fli_lot,l.fli_nbl,l.fli_un1,l.fli_un2 from lignes_docs_e2fac l
       where l.fli_num = :fac_num and l.type_docs = 'A' into
       type_docs,fli_num,fli_lig,fli_cod,fli_des,fli_qt1,fli_qt2
       ,fli_pht,fli_ptc,fli_rem,fli_mht,fli_mtc,fli_mtva,fli_txtva,fli_cpt
       ,fli_tmp,fli_exig,fli_lot,fli_nbl,fli_un1,fli_un2
       do begin
       remiseT =0;
       code_tva=null;
       id_article=null;
       prix=null;
       if (:fac_pri=0) then prix=:fli_pht;
       else prix=:fli_ptc;
       if (:fli_cpt is null) then t_ligne = :t_ligne_c;
       else t_ligne = :t_ligne_h;
       if (:fli_rem is not null and :fli_rem<>0) then
         remiset = (fli_rem);
       if (:fac_pri=0) then fli_rem= (:prix*:fli_qt1)-:fli_mht;
       else  fli_rem= (:prix*:fli_qt1)-:fli_mtc;
       select t.code_tva from ta_tva t where t.taux_tva = :fli_txtva and t.numcpt_tva like('4457%') into :code_tva;
       if (:code_tva is null) then
          select t.code_tva from ta_tva t where t.taux_tva = :fli_txtva into :code_tva;
       select id_article from ta_article where code_article = :fli_cod into :id_article;
       if (:id_article is null and :fli_des is null) then lignevide=:lignevide+1;
       else lignevide=0;
       if (:lignevide<3) then
       insert into ta_l_avoir values(gen_id(num_id_l_document,1),:id_facture,:t_ligne,:id_article,:rang,:fli_des,:fli_qt1,null,:fli_un1,:fli_un2,
            :prix,:fli_txtva,:fli_cpt,:code_tva,:fli_exig,:fli_mht,:fli_mtc,:remiseT,:fli_rem,null,null,null,null,null,null,
            null,null,null, null,0);

            rang=:rang+1;
          end
   documents=:fac_num;
  suspend;
   end
end
^

CREATE OR ALTER PROCEDURE IMPORTATION_BONLIV_E2FAC 
returns (
    documents varchar(8))
as
declare variable compteur integer;
declare variable fac_num varchar(8);
declare variable fac_typ varchar(2);
declare variable fac_dat date;
declare variable fac_ech date;
declare variable fac_cod varchar(8);
declare variable fac_nom varchar(31);
declare variable fac_cpt varchar(8);
declare variable fac_col varchar(9);
declare variable fac_ad1 varchar(31);
declare variable fac_ad2 varchar(31);
declare variable fac_cp varchar(6);
declare variable fac_vil varchar(26);
declare variable fac_pay varchar(21);
declare variable fac_ref varchar(41);
declare variable fac_pmt varchar(2);
declare variable fac_pri varchar(2);
declare variable fac_ht numeric(15,2);
declare variable fac_ttc numeric(15,2);
declare variable fac_remp numeric(15,2);
declare variable fac_rem numeric(15,2);
declare variable fac_remt numeric(15,2);
declare variable fac_escp numeric(15,2);
declare variable fac_esc numeric(15,2);
declare variable fac_mes1 varchar(69);
declare variable fac_mes2 varchar(69);
declare variable fac_mes3 varchar(69);
declare variable fac_cpta integer;
declare variable fac_edite integer;
declare variable fac_fac varchar(8);
declare variable fac_act numeric(15,2);
declare variable fac_cial varchar(8);
declare variable fac_mreg numeric(15,2);
declare variable fac_loc integer;
declare variable id_tiers integer;
declare variable id_t_paiement integer;
declare variable commentaire varchar(1000);
declare variable crlf char(2);
declare variable id_facture integer;
declare variable civilite varchar(20);
declare variable tvaintra varchar(50);
declare variable code_c_paiement varchar(20);
declare variable lib_c_paiement varchar(255);
declare variable report_c_paiement integer;
declare variable fin_mois_c_paiement integer;
declare variable id_c_paiement integer;
declare variable type_docs varchar(1);
declare variable fli_num varchar(8);
declare variable fli_lig integer;
declare variable fli_cod varchar(15);
declare variable fli_des varchar(53);
declare variable fli_qt1 numeric(15,4);
declare variable fli_qt2 numeric(15,4);
declare variable fli_pht numeric(15,2);
declare variable fli_ptc numeric(15,2);
declare variable fli_rem numeric(15,2);
declare variable fli_mht numeric(15,2);
declare variable fli_mtc numeric(15,2);
declare variable fli_mtva numeric(15,2);
declare variable fli_txtva numeric(15,2);
declare variable fli_cpt varchar(9);
declare variable fli_tmp varchar(2);
declare variable fli_exig varchar(2);
declare variable fli_lot varchar(16);
declare variable fli_nbl varchar(8);
declare variable fli_un1 varchar(3);
declare variable fli_un2 varchar(3);
declare variable t_ligne_h integer;
declare variable id_article integer;
declare variable rang integer;
declare variable remiset numeric(15,2);
declare variable code_tva varchar(20);
declare variable t_ligne_c integer;
declare variable t_ligne integer;
declare variable prix numeric(15,2);
declare variable lignevide integer;
declare variable id_fact_r_doc integer;
declare variable LOCALISATION_TVA varchar(10);
begin
CRLF = ASCII_CHAR(13)||ASCII_CHAR(10);
execute procedure MAJ_GENERATEUR  ;
select tl.id_t_ligne from ta_t_ligne tl where upper(tl.code_t_ligne) ='H' into :t_ligne_h;
select tl.id_t_ligne from ta_t_ligne tl where upper(tl.code_t_ligne) ='C' into :t_ligne_c;
for select d.fac_num,d.fac_typ,d.fac_dat,d.fac_ech,d.fac_cod,d.fac_nom,d.fac_cpt,d.fac_col
,d.fac_ad1,d.fac_ad2,d.fac_cp,d.fac_vil,d.fac_pay,d.fac_ref,d.fac_pmt,d.fac_pri,d.fac_ht,d.fac_ttc,d.fac_remp
,d.fac_rem,d.fac_remt,d.fac_escp,d.fac_esc,d.fac_mes1,d.fac_mes2,d.fac_mes3,d.fac_cpta
,d.fac_edite,d.fac_fac,d.fac_act,d.fac_cial,d.fac_mreg,d.fac_loc from documents_e2fac d where upper(d.fac_typ)='B'

into
fac_num,fac_typ,fac_dat,fac_ech,fac_cod,fac_nom,fac_cpt,fac_col
,fac_ad1,fac_ad2,fac_cp,fac_vil,fac_pay,fac_ref,fac_pmt,fac_pri,fac_ht,fac_ttc,fac_remp
,fac_rem,fac_remt,fac_escp,fac_esc,fac_mes1,fac_mes2,fac_mes3,fac_cpta
,fac_edite,fac_fac,fac_act,fac_cial,fac_mreg,fac_loc
do begin
commentaire ='';
id_c_paiement=null;
code_c_paiement=null;
lib_c_paiement=null;
report_c_paiement=null;
fin_mois_c_paiement=null;
documents='';
civilite=null;
tvaintra=null;

  /* correspondance localisation TVA*/
  if(:fac_loc = 1)then localisation_tva= 'F';
  if(:fac_loc = 2)then localisation_tva='UE' ;
  if(:fac_loc = 3)then localisation_tva= 'HUE' ;
  if(:fac_loc = 3)then localisation_tva= 'N';

if(:fac_mreg is null)then fac_mreg=0;
select cp.id_c_paiement, cp.code_c_paiement,cp.lib_c_paiement,cp.report_c_paiement,
  cp.fin_mois_c_paiement from ta_c_paiement cp where cp.code_c_paiement = :fac_pmt into
  :id_c_paiement,:code_c_paiement,:lib_c_paiement,:report_c_paiement,:fin_mois_c_paiement;
       select id_tiers from ta_tiers where Upper(code_tiers) = upper(:fac_cod) into :id_tiers;
       if (:fac_ref is not null) then commentaire = :fac_ref ||CRLF;
       if (:fac_mes1 is not null) then commentaire = :commentaire ||:fac_mes1||CRLF;
       if (:fac_mes2 is not null) then commentaire = :commentaire ||:fac_mes2||CRLF;
       if (:fac_mes3 is not null) then commentaire = :commentaire ||:fac_mes3||CRLF;
       id_facture = gen_id(num_id_document,1);
       insert into ta_bonliv values(:id_facture,:fac_num,:fac_dat,:fac_dat,:fac_dat,'Bon de livraison E2-Fac N '||:fac_num,
       :id_tiers,null,:fac_rem,:fac_remp,:fac_escp,:fac_esc,:fac_edite,:fac_pri,
       :commentaire,null,null,null,null,null,null,null,null,null,null, null,null,null,0);
       select c.code_t_civilite from ta_tiers v
       join ta_t_civilite c on(c.id_t_civilite=v.id_t_civilite)
       where
       Upper(v.code_tiers) = upper(:fac_cod) into :civilite;

       select cp.tva_i_com_compl from ta_tiers v
       join ta_compl cp on cp.id_compl=v.id_compl
       where
       Upper(v.code_tiers) = upper(:fac_cod) into :tvaintra;

       insert into ta_infos_bonliv values(gen_id(num_id_infos_document,1),:id_facture,:fac_ad1,:fac_ad2,null,:fac_cp,:fac_vil,:fac_pay,
       :fac_ad1,:fac_ad2,null,:fac_cp,:fac_vil,:fac_pay,:fac_cpt,:fac_col,:fac_nom,null,null,:civilite,null,:tvaIntra,
       :code_c_paiement,:lib_c_paiement,:report_c_paiement,:fin_mois_c_paiement,null,null,:LOCALISATION_TVA,null,null,null,null,null, null,0);
       rang=1;
       for select l.type_docs,l.fli_num,l.fli_lig,l.fli_cod,l.fli_des,l.fli_qt1,l.fli_qt2
       ,l.fli_pht,l.fli_ptc,l.fli_rem,l.fli_mht,l.fli_mtc,l.fli_mtva,l.fli_txtva,l.fli_cpt
       ,l.fli_tmp,l.fli_exig,l.fli_lot,l.fli_nbl,l.fli_un1,l.fli_un2 from lignes_docs_e2fac l
       where l.fli_num = :fac_num and l.type_docs = 'B' into
       type_docs,fli_num,fli_lig,fli_cod,fli_des,fli_qt1,fli_qt2
       ,fli_pht,fli_ptc,fli_rem,fli_mht,fli_mtc,fli_mtva,fli_txtva,fli_cpt
       ,fli_tmp,fli_exig,fli_lot,fli_nbl,fli_un1,fli_un2
       do begin
       remiseT =0;
       code_tva=null;
       id_article=null;
       prix=null;
       if (:fac_pri=0) then prix=:fli_pht;
       else prix=:fli_ptc;
       if (:fli_cpt is null) then t_ligne = :t_ligne_c;
       else t_ligne = :t_ligne_h;
       if (:fli_rem is not null and :fli_rem<>0) then
         remiset = (fli_rem);
       if (:fac_pri=0) then fli_rem= (:prix*:fli_qt1)-:fli_mht;
       else  fli_rem= (:prix*:fli_qt1)-:fli_mtc;
       select t.code_tva from ta_tva t where t.taux_tva = :fli_txtva and t.numcpt_tva like('4457%') into :code_tva;
       if (:code_tva is null) then
          select t.code_tva from ta_tva t where t.taux_tva = :fli_txtva into :code_tva;
       select id_article from ta_article where code_article = :fli_cod into :id_article;
       if (:id_article is null and :fli_des is null) then lignevide=:lignevide+1;
       else lignevide=0;
       if (:lignevide<3) then
       insert into ta_l_bonliv values(gen_id(num_id_l_document,1),:id_facture,:t_ligne,:id_article,:rang,:fli_des,:fli_qt1,null,:fli_un1,:fli_un2,
            :prix,:fli_txtva,:fli_cpt,:code_tva,:fli_exig,:fli_mht,:fli_mtc,:remiseT,:fli_rem,null,null,null,null,
            null,null,null, null,0);

            rang=:rang+1;
          end
   if (:fac_fac is not null) then
   begin
      select f.id_document from ta_facture f where f.code_document like :fac_fac into
      :id_fact_r_doc;
      if(:id_fact_r_doc is not null)then
        begin
          insert into ta_r_document (id_r_document,id_facture,id_bonliv,version_obj)
            values(gen_id(num_id_r_document,1),:id_fact_r_doc,:id_facture ,0);
        end
   end
   documents=:fac_num;

  suspend;
   end
end
^


CREATE OR ALTER PROCEDURE IMPORTATION_DEVIS_E2FAC 
returns (
    documents varchar(8))
as
declare variable compteur integer;
declare variable fac_num varchar(8);
declare variable fac_typ varchar(2);
declare variable fac_dat date;
declare variable fac_ech date;
declare variable fac_cod varchar(8);
declare variable fac_nom varchar(31);
declare variable fac_cpt varchar(8);
declare variable fac_col varchar(9);
declare variable fac_ad1 varchar(31);
declare variable fac_ad2 varchar(31);
declare variable fac_cp varchar(6);
declare variable fac_vil varchar(26);
declare variable fac_pay varchar(21);
declare variable fac_ref varchar(41);
declare variable fac_pmt varchar(2);
declare variable fac_pri varchar(2);
declare variable fac_ht numeric(15,2);
declare variable fac_ttc numeric(15,2);
declare variable fac_remp numeric(15,2);
declare variable fac_rem numeric(15,2);
declare variable fac_remt numeric(15,2);
declare variable fac_escp numeric(15,2);
declare variable fac_esc numeric(15,2);
declare variable fac_mes1 varchar(69);
declare variable fac_mes2 varchar(69);
declare variable fac_mes3 varchar(69);
declare variable fac_cpta integer;
declare variable fac_edite integer;
declare variable fac_fac varchar(8);
declare variable fac_act numeric(15,2);
declare variable fac_cial varchar(8);
declare variable fac_mreg numeric(15,2);
declare variable fac_loc integer;
declare variable id_tiers integer;
declare variable id_t_paiement integer;
declare variable commentaire varchar(1000);
declare variable crlf char(2);
declare variable id_facture integer;
declare variable civilite varchar(20);
declare variable tvaintra varchar(50);
declare variable code_c_paiement varchar(20);
declare variable lib_c_paiement varchar(255);
declare variable report_c_paiement integer;
declare variable fin_mois_c_paiement integer;
declare variable id_c_paiement integer;
declare variable type_docs varchar(1);
declare variable fli_num varchar(8);
declare variable fli_lig integer;
declare variable fli_cod varchar(15);
declare variable fli_des varchar(53);
declare variable fli_qt1 numeric(15,4);
declare variable fli_qt2 numeric(15,4);
declare variable fli_pht numeric(15,2);
declare variable fli_ptc numeric(15,2);
declare variable fli_rem numeric(15,2);
declare variable fli_mht numeric(15,2);
declare variable fli_mtc numeric(15,2);
declare variable fli_mtva numeric(15,2);
declare variable fli_txtva numeric(15,2);
declare variable fli_cpt varchar(9);
declare variable fli_tmp varchar(2);
declare variable fli_exig varchar(2);
declare variable fli_lot varchar(16);
declare variable fli_nbl varchar(8);
declare variable fli_un1 varchar(3);
declare variable fli_un2 varchar(3);
declare variable t_ligne_h integer;
declare variable id_article integer;
declare variable rang integer;
declare variable remiset numeric(15,2);
declare variable code_tva varchar(20);
declare variable t_ligne_c integer;
declare variable t_ligne integer;
declare variable prix numeric(15,2);
declare variable lignevide integer;
declare variable LOCALISATION_TVA varchar(10);
begin
CRLF = ASCII_CHAR(13)||ASCII_CHAR(10);
execute procedure MAJ_GENERATEUR  ;
select tl.id_t_ligne from ta_t_ligne tl where upper(tl.code_t_ligne) ='H' into :t_ligne_h;
select tl.id_t_ligne from ta_t_ligne tl where upper(tl.code_t_ligne) ='C' into :t_ligne_c;
for select d.fac_num,d.fac_typ,d.fac_dat,d.fac_ech,d.fac_cod,d.fac_nom,d.fac_cpt,d.fac_col
,d.fac_ad1,d.fac_ad2,d.fac_cp,d.fac_vil,d.fac_pay,d.fac_ref,d.fac_pmt,d.fac_pri,d.fac_ht,d.fac_ttc,d.fac_remp
,d.fac_rem,d.fac_remt,d.fac_escp,d.fac_esc,d.fac_mes1,d.fac_mes2,d.fac_mes3,d.fac_cpta
,d.fac_edite,d.fac_fac,d.fac_act,d.fac_cial,d.fac_mreg,d.fac_loc from documents_e2fac d where upper(d.fac_typ)='P'

into
fac_num,fac_typ,fac_dat,fac_ech,fac_cod,fac_nom,fac_cpt,fac_col
,fac_ad1,fac_ad2,fac_cp,fac_vil,fac_pay,fac_ref,fac_pmt,fac_pri,fac_ht,fac_ttc,fac_remp
,fac_rem,fac_remt,fac_escp,fac_esc,fac_mes1,fac_mes2,fac_mes3,fac_cpta
,fac_edite,fac_fac,fac_act,fac_cial,fac_mreg,fac_loc
do begin
commentaire ='';
id_c_paiement=null;
code_c_paiement=null;
lib_c_paiement=null;
report_c_paiement=null;
fin_mois_c_paiement=null;
documents='';
civilite=null;
tvaintra=null;

  /* correspondance localisation TVA*/
  if(:fac_loc = 1)then localisation_tva= 'F';
  if(:fac_loc = 2)then localisation_tva='UE' ;
  if(:fac_loc = 3)then localisation_tva= 'HUE' ;
  if(:fac_loc = 3)then localisation_tva= 'N';

if(:fac_mreg is null)then fac_mreg=0;
select cp.id_c_paiement, cp.code_c_paiement,cp.lib_c_paiement,cp.report_c_paiement,
  cp.fin_mois_c_paiement from ta_c_paiement cp where cp.code_c_paiement = :fac_pmt into
  :id_c_paiement,:code_c_paiement,:lib_c_paiement,:report_c_paiement,:fin_mois_c_paiement;
       select id_tiers from ta_tiers where Upper(code_tiers) = upper(:fac_cod) into :id_tiers;
       if (:fac_ref is not null) then commentaire = :fac_ref ||CRLF;
       if (:fac_mes1 is not null) then commentaire = :commentaire ||:fac_mes1||CRLF;
       if (:fac_mes2 is not null) then commentaire = :commentaire ||:fac_mes2||CRLF;
       if (:fac_mes3 is not null) then commentaire = :commentaire ||:fac_mes3||CRLF;
       id_facture = gen_id(num_id_document,1);
       insert into ta_devis values(:id_facture,:fac_num,:fac_dat,:fac_ech,:fac_dat,'Devis E2-Fac N '||:fac_num,
       :id_tiers,null,null,:fac_mreg,:fac_rem,:fac_remp,:fac_escp,:fac_esc,:fac_edite,:fac_pri,:fac_cpta,
       :commentaire,null,null,null,null,null,null,null,null,null,null,null, null,null,null,0);
       select c.code_t_civilite from ta_tiers v
       join ta_t_civilite c on(c.id_t_civilite=v.id_t_civilite)
       where
       Upper(v.code_tiers) = upper(:fac_cod) into :civilite;

       select cp.tva_i_com_compl from ta_tiers v
       join ta_compl cp on cp.id_compl=v.id_compl
       where
       Upper(v.code_tiers) = upper(:fac_cod) into :tvaintra;

       insert into ta_infos_devis values(gen_id(num_id_infos_document,1),:id_facture,:fac_ad1,:fac_ad2,null,:fac_cp,:fac_vil,:fac_pay,
       :fac_ad1,:fac_ad2,null,:fac_cp,:fac_vil,:fac_pay,:fac_cpt,:fac_col,:fac_nom,null,null,:civilite,null,:tvaIntra,
       :code_c_paiement,:lib_c_paiement,:report_c_paiement,:fin_mois_c_paiement,null,null,:LOCALISATION_TVA,null,null,null,null,null, null,0);
       rang=1;
       for select l.type_docs,l.fli_num,l.fli_lig,l.fli_cod,l.fli_des,l.fli_qt1,l.fli_qt2
       ,l.fli_pht,l.fli_ptc,l.fli_rem,l.fli_mht,l.fli_mtc,l.fli_mtva,l.fli_txtva,l.fli_cpt
       ,l.fli_tmp,l.fli_exig,l.fli_lot,l.fli_nbl,l.fli_un1,l.fli_un2 from lignes_docs_e2fac l
       where l.fli_num = :fac_num and l.type_docs = 'P' into
       type_docs,fli_num,fli_lig,fli_cod,fli_des,fli_qt1,fli_qt2
       ,fli_pht,fli_ptc,fli_rem,fli_mht,fli_mtc,fli_mtva,fli_txtva,fli_cpt
       ,fli_tmp,fli_exig,fli_lot,fli_nbl,fli_un1,fli_un2
       do begin
       remiseT =0;
       code_tva=null;
       id_article=null;
       prix=null;
       if (:fac_pri=0) then prix=:fli_pht;
       else prix=:fli_ptc;
       if (:fli_cpt is null) then t_ligne = :t_ligne_c;
       else t_ligne = :t_ligne_h;
       if (:fli_rem is not null and :fli_rem<>0) then
         remiset = (fli_rem);
       if (:fac_pri=0) then fli_rem= (:prix*:fli_qt1)-:fli_mht;
       else  fli_rem= (:prix*:fli_qt1)-:fli_mtc;
       select t.code_tva from ta_tva t where t.taux_tva = :fli_txtva and t.numcpt_tva like('4457%') into :code_tva;
       if (:code_tva is null) then
          select t.code_tva from ta_tva t where t.taux_tva = :fli_txtva into :code_tva;
       select id_article from ta_article where code_article = :fli_cod into :id_article;
       if (:id_article is null and :fli_des is null) then lignevide=:lignevide+1;
       else lignevide=0;
       if (:lignevide<3) then
       insert into ta_l_devis values(gen_id(num_id_l_document,1),:id_facture,:t_ligne,:id_article,:rang,:fli_des,:fli_qt1,null,:fli_un1,:fli_un2,
            :prix,:fli_txtva,:fli_cpt,:code_tva,:fli_exig,:fli_mht,:fli_mtc,:remiseT,:fli_rem,null,null,null,null,
            null,null,null, null,0);

            rang=:rang+1;
          end
   documents=:fac_num;
  suspend;
   end
end
^


CREATE OR ALTER PROCEDURE IMP_RELATION_DOCUMENT_E2FAC 
as
declare variable fac_fac varchar(7);
declare variable fac_num varchar(7);
declare variable idfac integer;
declare variable idbonliv integer;
begin
  for select d.fac_num,d.fac_fac from documents_e2fac d where d.fac_typ='B'
  into :fac_num,:fac_fac do
  begin
     if(:fac_fac is not null)then
     begin
       idfac=null;
       idbonliv=null;
       select f.id_document from ta_facture f where f.code_document like 'FV'||:fac_fac into :idfac;
       select f.id_document from ta_bonliv f where f.code_document like 'BL'||:fac_num into :idbonliv;
       if(idfac is not null and idbonliv is not null)  then
             insert into ta_r_document values(gen_id(num_id_r_document,1),:idfac,null,null,:idbonliv,null,null,null,null,null,null,null,null,null,null,null,null,null,null,0) ;
     end
  end
  /*suspend;*/
end
^

CREATE OR ALTER PROCEDURE IMPORTATION_RELATION_COM 
as
declare variable code varchar(10);
declare variable repres varchar(10);
declare variable idtiers integer;
declare variable idcom integer;
begin
  for select c.cli_cod,c.cli_rep from clients_e2fac c where c.cli_rep is not null into :code,:repres do
  begin
     idcom=-1;
     idtiers=-1;
     select t.id_tiers from ta_tiers t where t.code_tiers=:code into :idtiers;
     select t.id_tiers from ta_tiers t where t.code_tiers=:repres into :idcom;
     if(:idcom<>-1 and :idtiers<>-1)then
       insert into ta_r_commercial values(gen_id(num_id_r_commercial,1),:idtiers,:idcom,null,null,null,null,null,null,0);
     /*suspend;*/
  end
end
^


CREATE OR ALTER PROCEDURE IMPORTATION_CLIENTS_EN_DOUBLE 
as
declare variable old_code varchar(8);
declare variable incr integer;
declare variable nom varchar(31);
declare variable id integer;
declare variable new_code varchar(8);
declare variable code varchar(8);
begin
incr=0;
old_code='';
new_code='';
  for select ce.id, ce.cli_cod,ce.cli_nom from clients_e2fac ce where exists(select cli_cod from clients_e2fac
    where id<>ce.id and cli_cod=ce.cli_cod)into :id,:code,:nom do
    begin
         /*suspend;*/
         if(:code=old_code)then
           begin
             incr=incr+1;
             new_code=:code||incr;
           end
           else
              begin
                 incr=0;
                 new_code=:code ;
              end
      /* update documents_e2fac d set d.fac_cod=:new_code where d.fac_cod=:code and d.fac_nom=:nom ;*/
       update clients_e2fac c set c.cli_cod=:new_code where c.id=:id ;
       old_code=:code;
    end
end
^

create or alter procedure IMPORTATION_ARTICLES_EN_DOUBLE
as
declare variable OLD_CODE varchar(14);
declare variable DIV varchar(31);
declare variable ID integer;
declare variable NEW_CODE varchar(14);
declare variable CODE varchar(14);
begin
old_code='';
new_code='';
  for select a.id, a.art_cod,a.art_div from articles_fac a where a.art_div is not null into :id,:code,:div do
    begin
       new_code=:code||'_'||:div;
       old_code=:code;
       while (strlen(:old_code)<13) do
       begin
          old_code=:old_code||' ';
       end
       update articles_fac a2 set a2.art_cod=:new_code where a2.id=:id ;
       update lignes_docs_e2fac l set l.fli_cod=:new_code where (l.fli_cod)=(:old_code||:div)  ;
    end
end
^

create or alter procedure GESTION_DOCUMENT_REGLE
as
declare variable ID_DOCUMENT integer;
declare variable DATE_DOCUMENT date;
declare variable ID_T_PAIEMENT integer;
declare variable LIBELLE_PAIEMENT varchar(255);
declare variable REGLE_DOCUMENT numeric(15,2);
declare variable ID_COMPTE_BANQUE integer;
declare variable COMPTEUR integer;
declare variable CODE_REGLEMENT varchar(9);
declare variable COMPTEUR_LGR varchar(7);
declare variable ID_TIERS integer;
declare variable ID_REGLEMENT integer;
BEGIN
compteur=0;
compteur_lgr='';
select id_compte_banque from ta_compte_banque where id_tiers=-1 rows 1 into :id_compte_banque;
if(id_compte_banque is null)then
begin
   id_compte_banque=gen_id(num_id_compte_banque,1);
   insert into ta_compte_banque (id_compte_banque,id_tiers,nom_banque,compte,code_banque,code_guichet,cle_rib,
   adresse1_banque,adresse2_banque,cp_banque,ville_banque,iban,code_b_i_c,titulaire,cptcomptable,id_t_banque,
   qui_cree_compte_banque,quand_cree_compte_banque,qui_modif_compte_banque,quand_modif_compte_banque,
   version,ip_acces,version_obj) 
   values(:id_compte_banque,-1,'?','?','?','?','?',null,null,null, null,null,null,
   null,null,null,null,null,null,null,null,0,0);
end

for select f.id_document,f.date_document,f.id_t_paiement,f.libelle_paiement,
f.regle_document,f.id_tiers from ta_facture f where (f.regle_document is not null and f.regle_document<>0)
into :id_document,date_document,id_t_paiement,libelle_paiement,regle_document,id_tiers do
 begin
     if(:id_t_paiement is null) then
       select id_t_paiement from ta_t_paiement p where upper(p.code_t_paiement) like 'C' into :id_t_paiement;
     compteur=compteur+1;
   compteur_lgr=:compteur;
   while (strlen(:compteur_lgr)<7) do
     compteur_lgr='0'||:compteur_lgr;

   code_reglement='RG'||:compteur_lgr;
   id_reglement =gen_id(num_id_document,1);
   insert into ta_reglement values(:id_reglement,:code_reglement,:date_document,:date_document,
        :libelle_paiement, :id_tiers,:id_t_paiement,0,:regle_document,:id_compte_banque, 64,null, null,null,null,null,null,0);
   insert into ta_r_reglement values(gen_id(num_id_r_reglement,1),:id_reglement,:id_document,
   null,null,null,null,null,null,:regle_document, 0,null,null,null,null,null,null,0);
 end
update ta_facture set id_t_paiement=null,regle_document=0,libelle_paiement='' where regle_document <>0;

END
^



CREATE TABLE RAPPORT_UNITE_FAC (
    ID       INTEGER,
    ART_COD  VARCHAR(14),
    ART_UN1  VARCHAR(14),
    ART_UN2  VARCHAR(14),
    ART_1A2  NUMERIC(15,2)
)
^


create or alter procedure IMPORTATION_RAPPORT_UNITE_E2FAC
returns (
    ARTICLE varchar(20))
as
declare variable ART_COD varchar(14);
declare variable ART_UN1 varchar(14);
declare variable ART_UN2 varchar(14);
declare variable ART_1A2 numeric(15,2);
declare variable ID_ARTICLE integer;
declare variable ID_UNITE integer;
declare variable ID_PRIX integer;
declare variable CODEARTICLE varchar(14);
declare variable I integer;
declare variable ID_UNITE2 integer;
declare variable ID_RAPPORT integer;
begin
  for select a.ART_COD,a.art_un1,a.art_un2,a.art_1a2 from rapport_unite_fac a
   where a.art_un1 is not null and a.art_un2 is not null
  into :ART_COD,art_un1,art_un2,art_1a2
  do begin
    codearticle=:art_cod;

 id_prix=null;
 id_rapport=null;
 select a.id_article, a.id_prix from ta_article a where a.code_article=:art_cod  into :id_article,id_prix;
 select ra.id from ta_rapport_unite ra where ra.id_article=:id_article into :id_rapport;
 if(:id_rapport is null)then
 begin
  /*Prix 1*/
  id_unite=null;
  id_unite2=null;
      /*unite article*/
      if (not exists(select u.code_unite from ta_unite U where upper(u.code_unite) = upper(:art_un1 ))) then
            begin
              insert into ta_unite (code_unite,version_obj) values (:art_un1,0);
            end
      select u.id_unite from ta_unite u where upper(u.code_unite) = upper(:art_un1)  into :id_unite ;
      if(id_prix is null)then
          begin
            id_prix = gen_id(num_id_prix,1);
            insert into ta_prix values(:id_prix,:id_article,:id_unite,0,0,null,null,null,null,null,null,null,null,0);
          end
         if (not exists(select u.code_unite from ta_unite U where upper(u.code_unite) = upper(:art_un2 ))) then
            begin
              insert into ta_unite (code_unite,version_obj) values (:art_un2,0);
            end
      select u.id_unite from ta_unite u where upper(u.code_unite) = upper(:art_un2)  into :id_unite2 ;
      id_rapport=gen_id(num_id_rapport_unite,1);
     insert into ta_rapport_unite  values(:id_rapport,:id_article,:id_unite,:id_unite2,:art_1a2,2,1,null,
     null,null,null,null,null,0);
     update ta_article set id_prix=:id_prix, id_rapport_unite=:id_rapport where id_article=:id_article;


    end
   end

  --suspend;
 -- end

end
^

set term ;^


update RDB$RELATION_FIELDS set
RDB$FIELD_SOURCE = 'DLONG'
where (RDB$FIELD_NAME = 'QTE_L_DOCUMENT') and
(RDB$RELATION_NAME = 'TA_L_ACOMPTE');

update RDB$RELATION_FIELDS set
RDB$FIELD_SOURCE = 'DLONG'
where (RDB$FIELD_NAME = 'QTE2_L_DOCUMENT') and
(RDB$RELATION_NAME = 'TA_L_ACOMPTE');


update RDB$RELATION_FIELDS set
RDB$FIELD_SOURCE = 'DLONG'
where (RDB$FIELD_NAME = 'QTE_L_DOCUMENT') and
(RDB$RELATION_NAME = 'TA_L_AVOIR');

update RDB$RELATION_FIELDS set
RDB$FIELD_SOURCE = 'DLONG'
where (RDB$FIELD_NAME = 'QTE2_L_DOCUMENT') and
(RDB$RELATION_NAME = 'TA_L_AVOIR');


update RDB$RELATION_FIELDS set
RDB$FIELD_SOURCE = 'DLONG'
where (RDB$FIELD_NAME = 'QTE_L_DOCUMENT') and
(RDB$RELATION_NAME = 'TA_L_APPORTEUR');

update RDB$RELATION_FIELDS set
RDB$FIELD_SOURCE = 'DLONG'
where (RDB$FIELD_NAME = 'QTE2_L_DOCUMENT') and
(RDB$RELATION_NAME = 'TA_L_APPORTEUR');


update RDB$RELATION_FIELDS set
RDB$FIELD_SOURCE = 'DLONG'
where (RDB$FIELD_NAME = 'QTE_L_DOCUMENT') and
(RDB$RELATION_NAME = 'TA_L_BONCDE');

update RDB$RELATION_FIELDS set
RDB$FIELD_SOURCE = 'DLONG'
where (RDB$FIELD_NAME = 'QTE2_L_DOCUMENT') and
(RDB$RELATION_NAME = 'TA_L_BONCDE');

update RDB$RELATION_FIELDS set
RDB$FIELD_SOURCE = 'DLONG'
where (RDB$FIELD_NAME = 'QTE_L_DOCUMENT') and
(RDB$RELATION_NAME = 'TA_L_BONLIV');

update RDB$RELATION_FIELDS set
RDB$FIELD_SOURCE = 'DLONG'
where (RDB$FIELD_NAME = 'QTE2_L_DOCUMENT') and
(RDB$RELATION_NAME = 'TA_L_BONLIV');


update RDB$RELATION_FIELDS set
RDB$FIELD_SOURCE = 'DLONG'
where (RDB$FIELD_NAME = 'QTE_L_DOCUMENT') and
(RDB$RELATION_NAME = 'TA_L_DEVIS');

update RDB$RELATION_FIELDS set
RDB$FIELD_SOURCE = 'DLONG'
where (RDB$FIELD_NAME = 'QTE2_L_DOCUMENT') and
(RDB$RELATION_NAME = 'TA_L_DEVIS');

update RDB$RELATION_FIELDS set
RDB$FIELD_SOURCE = 'DLONG'
where (RDB$FIELD_NAME = 'QTE_L_DOCUMENT') and
(RDB$RELATION_NAME = 'TA_L_FACTURE');

update RDB$RELATION_FIELDS set
RDB$FIELD_SOURCE = 'DLONG'
where (RDB$FIELD_NAME = 'QTE2_L_DOCUMENT') and
(RDB$RELATION_NAME = 'TA_L_FACTURE');

update RDB$RELATION_FIELDS set
RDB$FIELD_SOURCE = 'DLONG'
where (RDB$FIELD_NAME = 'QTE_L_DOCUMENT') and
(RDB$RELATION_NAME = 'TA_L_PRELEVEMENT');

update RDB$RELATION_FIELDS set
RDB$FIELD_SOURCE = 'DLONG'
where (RDB$FIELD_NAME = 'QTE2_L_DOCUMENT') and
(RDB$RELATION_NAME = 'TA_L_PRELEVEMENT');

update RDB$RELATION_FIELDS set
RDB$FIELD_SOURCE = 'DLONG'
where (RDB$FIELD_NAME = 'QTE_L_DOCUMENT') and
(RDB$RELATION_NAME = 'TA_L_PROFORMA');

update RDB$RELATION_FIELDS set
RDB$FIELD_SOURCE = 'DLONG'
where (RDB$FIELD_NAME = 'QTE2_L_DOCUMENT') and
(RDB$RELATION_NAME = 'TA_L_PROFORMA');



