CREATE PROCEDURE CA_ARTICLES_DETAIL (
    CODEFACTURE VARCHAR(9),
    ARTICLE INTEGER)
RETURNS (
    CODE_ARTICLE VARCHAR(20),
    HT NUMERIC(15,2),
    TVA NUMERIC(15,2),
    TTC NUMERIC(15,2))
AS
DECLARE VARIABLE ID_ARTICLE_DEB INTEGER;
DECLARE VARIABLE ID_ARTICLE_FIN INTEGER;
begin
if (article is null) then
    begin
      id_article_deb = 0;
      select max(id_article) from ta_article into :id_article_fin ;
    end
 else begin
    id_article_deb=article;
    id_article_fin=article;
 end
for select
v_l_facture.code_article,
sum(MTNETTVA),
sum(MTNETHT),
sum(MTNETTTC)
from 
v_l_facture,
calcul_total_ligne_direct('facture',v_l_facture.id_l_facture)
where
v_l_facture.id_article  between :id_article_deb and :id_article_fin
and v_l_facture.id_facture in (select id_facture from ta_facture where code_facture like(:codefacture||'%'))
group by v_l_facture.code_article
into  :code_article,ht,tva,ttc
do begin
        if (:codefacture = '96' or
            :codefacture = '97' or
            :codefacture = '98' or
            :codefacture = '99' or
            :codefacture = 'A0' or
            :codefacture = 'A1' ) then
            begin
             ht = ht/6.55957;
             tva = tva/6.55957;
             ttc = ttc/6.55957;
            end
  suspend;
end
end
^


CREATE PROCEDURE CA_ARTICLES_SYNTHESE (
    CODEFACTURE VARCHAR(9),
    ARTICLE INTEGER)
RETURNS (
    HT NUMERIC(15,2),
    TVA NUMERIC(15,2),
    TTC NUMERIC(15,2))
AS
DECLARE VARIABLE ID_ARTICLE_DEB INTEGER;
DECLARE VARIABLE ID_ARTICLE_FIN INTEGER;
begin
if (article is null) then
    begin
      id_article_deb = 0;
      select max(id_article) from ta_article into :id_article_fin ;
    end
 else begin
    id_article_deb=article;
    id_article_fin=article;
 end
select
cast(sum (MTNETTVA)/6.55957 as numeric(15,2)),
cast(sum (MTNETHT)/6.55957 as numeric(15,2)),
cast(sum (MTNETTTC)/6.55957 as numeric(15,2))
from 
v_l_facture,
calcul_total_ligne_direct('facture',v_l_facture.id_l_facture)
where
v_l_facture.id_article  between :id_article_deb and :id_article_fin
and v_l_facture.id_facture in (select id_facture from ta_facture where code_facture like(:CODEFACTURE||'%'))
into  :ht,tva,ttc;
  suspend;
end
^


CREATE PROCEDURE CA_ARTICLE_FAMILLE_SYNTHESE (
    CODEFACTURE VARCHAR(9),
    FAMILLE INTEGER)
RETURNS (
    HT NUMERIC(15,2),
    TVA NUMERIC(15,2),
    TTC NUMERIC(15,2))
AS
DECLARE VARIABLE ID_FAMILLE_DEB INTEGER;
DECLARE VARIABLE ID_FAMILLE_FIN INTEGER;
begin
if (FAMILLE is null) then
    begin
      id_FAMILLE_deb = 0;
      select max(id_FAMILLE) from ta_famille into :id_FAMILLE_fin ;
    end
 else begin
    id_FAMILLE_deb=FAMILLE;
    id_FAMILLE_fin=FAMILLE;
 end
select
cast(sum (MTNETTVA)/6.55957 as numeric(15,2)),
cast(sum (MTNETHT)/6.55957 as numeric(15,2)),
cast(sum (MTNETTTC)/6.55957 as numeric(15,2))
from 
v_l_facture,
calcul_total_ligne_direct('facture',v_l_facture.id_l_facture)
where
v_l_facture.id_article in(select id_article from ta_article where id_famille  between :id_FAMILLE_deb and :id_FAMILLE_fin)
and v_l_facture.id_facture in (select id_facture from ta_facture where code_facture like(:CODEFACTURE||'%'))
into  :ht,tva,ttc;
  suspend;
end
^

CREATE PROCEDURE CA_ARTICLE_FAMILLE_DETAIL (
    CODEFACTURE VARCHAR(9),
    FAMILLE INTEGER)
RETURNS (
    CODE_ARTICLE VARCHAR(20),
    HT NUMERIC(15,2),
    TVA NUMERIC(15,2),
    TTC NUMERIC(15,2))
AS
DECLARE VARIABLE ID_FAMILLE_DEB INTEGER;
DECLARE VARIABLE ID_FAMILLE_FIN INTEGER;
begin
if (FAMILLE is null) then
    begin
      id_FAMILLE_deb = 0;
      select max(id_FAMILLE) from ta_famille into :id_FAMILLE_fin ;
    end
 else begin
    id_FAMILLE_deb=FAMILLE;
    id_FAMILLE_fin=FAMILLE;
 end
for select
v_l_facture.code_article,
sum(MTNETTVA),
sum(MTNETHT),
sum(MTNETTTC)
from 
v_l_facture,
calcul_total_ligne_direct('facture',v_l_facture.id_l_facture)
where
v_l_facture.id_article in(select id_article from ta_article where id_famille
between :id_FAMILLE_deb and :id_FAMILLE_fin)
and v_l_facture.id_facture in (select id_facture from ta_facture where code_facture like(:codefacture||'%'))
group by v_l_facture.code_article
into  :code_article,ht,tva,ttc
do begin
        if (:codefacture = '96' or
            :codefacture = '97' or
            :codefacture = '98' or
            :codefacture = '99' or
            :codefacture = 'A0' or
            :codefacture = 'A1' ) then
            begin
             ht = ht/6.55957;
             tva = tva/6.55957;
             ttc = ttc/6.55957;
            end
  suspend;
end
end
^


/*Liste des articles facturés pour un tiers sur une période*/
select
V_FACTURE.code_tiers,
V_FACTURE.code_facture,
v_l_facture.code_article,
v_l_facture.u1_l_facture,
(v_l_facture.qte_l_facture),
(MTNETHT)as ht,
(MTNETTVA)as tva,
(MTNETTTC)as ttc
from 
v_l_facture,
calcul_total_ligne_direct('facture',v_l_facture.id_l_facture),
V_FACTURE
where V_FACTURE.ID_FACTURE=v_l_facture.ID_FACTURE 
and v_l_facture.code_article is not null
and cast ( V_FACTURE.DATE_FACTURE as date )
between
( select cast (TA_INFO_ENTREPRISE.DATEDEB_INFO_ENTREPRISE as date ) from TA_INFO_ENTREPRISE)
and ( select cast (TA_INFO_ENTREPRISE.DATEFIN_INFO_ENTREPRISE as date ) from TA_INFO_ENTREPRISE)
order by
V_FACTURE.code_tiers,
V_FACTURE.code_facture,
v_l_facture.code_article,
v_l_facture.u1_l_facture
