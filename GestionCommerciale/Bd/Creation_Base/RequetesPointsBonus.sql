select  sum(points) from calculpointbonus3(null) cal;

select sum(points)from ta_tiers_point;





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
  date_validite=cast('06/01/2012'as date);
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
  points=0;

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
        /*:regle+1<:net and*/
        if( :mt_ht_apr_rem_globale is not null and :mt_ht_apr_rem_globale <>0) then
          begin
            points=:points+cast((:mt_ht_apr_rem_globale*:pointpotentiel)/:prix_reference as integer);
                  /*suspend;*/
          end

    end
    if(:points>0)then
      insert into ta_tiers_point values(gen_id(num_id_tiers_point,1),:id_tiers,:date_validite,:points,null,null,null,null,null,null,0);
      suspend;
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
  points=0;

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
        /*:regle+1<:net and*/
        if( :mt_ht_apr_rem_globale is not null and :mt_ht_apr_rem_globale <>0) then
          begin
            points=:points+cast((:mt_ht_apr_rem_globale*:pointpotentiel)/:prix_reference as integer);
                  /*suspend;  */
          end

    end
      if(:points>0)then
      insert into ta_tiers_point values(gen_id(num_id_tiers_point,1),:id_tiers,:date_validite,:points,null,null,null,null,null,null,0);
       suspend;

  end
  end
end
