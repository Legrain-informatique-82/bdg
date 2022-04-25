select code_client from ta_wlgr w where w.total_ht_s2=35.99;


select code_client,code_doc from ta_wlgr w where w.traite=1;

select * from ta_wlgr w1
join ta_facture f on f.code_document=w1.code_doc
where w1.ttc<>f.net_ttc_calc;

select cast(w1.participation_cogere/(w1.duree_e2+w1.duree_e2_retard)as numeric(15,4)) from ta_wlgr w1
where w1.participation_cogere<0;

select
distinct(w.code_client) ,
w.licence,w.ttc
from  ta_wlgr_en_maintenance w
where w.code_client not in(select
distinct(t.code_tiers)
from 
ta_l_facture lf
join ta_FACTURE f on f.id_document=lf.id_document
join ta_tiers  t on t.id_tiers=f.id_tiers
join ta_article a  on a.id_article = lf.id_article
where f.id_document=lf.id_document
and a.id_article=a.id_article
and a.id_famille =21
and cast ( f.date_document as date )
between cast('01/01/2010' as date) and cast('12/31/2010' as date)
and w.code_client=t.code_tiers)
order by 1;

delete from ta_wlgr_en_maintenance  w where w.code_client is null;


select sum( p.points), t.code_tiers,w.licence,w.web,w.cogere,w.saisie,w.email,t.prenom_tiers,c.code_t_civilite,e.code_t_entite,ent.nom_entreprise
from ta_wlgr_relance_fin2010 w
join ta_tiers t on t.code_tiers=W.code_client
left join ta_t_civilite c on c.id_t_civilite=t.id_t_civilite
left join ta_t_entite E on E.id_t_entite=t.id_t_entite
left join ta_entreprise ent on ent.id_entreprise=t.id_entreprise

left join ta_tiers_point   p on p.id_tiers=t.id_tiers
group by t.code_tiers,w.licence,w.web,w.cogere,w.saisie,w.email,t.prenom_tiers,c.code_t_civilite,e.code_t_entite,ent.nom_entreprise

