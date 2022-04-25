CREATE PROCEDURE VIDE_TABLES 
AS
begin
  delete from ta_t_adr;
  delete from ta_t_civilite;
  delete from ta_t_doc;
  delete from ta_t_entite;
  delete from ta_t_ligne;
  delete from ta_t_paiement;
  delete from ta_t_tel;
  delete from ta_t_tiers;
  delete from ta_t_tva;

  delete from ta_tiers;
  delete from ta_article;
  delete from ta_avoir;

  delete from ta_banque;

  delete from ta_boncde;
  delete from ta_bonliv;
  delete from ta_c_paiement;
  delete from ta_com_doc;

  delete from ta_const;
  delete from ta_devis;

  delete from ta_entreprise;
  delete from ta_facture;
  delete from ta_famille;
  delete from ta_info_entreprise;

  delete from ta_l_modele;
  delete from ta_mail_maj;
  delete from ta_modele;

  delete from ta_report_stock;
  delete from ta_stock;

  delete from ta_tva;
  delete from ta_unite;
  delete from ta_visualisation;

  suspend;
end
^
