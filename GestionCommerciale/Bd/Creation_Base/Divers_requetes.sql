/* vérifie si des factures comporte des articles pas configurés avec les paramètres actuels de l'article*/
select lf.id_facture,lf.id_l_facture,lf.compte_l_facture,A.numcpt_article from ta_l_facture lf
join ta_article A on(A.id_article=lf.id_article)
where lf.compte_l_facture <> A.numcpt_article or (lf.compte_l_facture is null)
