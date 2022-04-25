

/* Trigger: TA_REPORT_STOCK_BI */
CREATE TRIGGER TA_REPORT_STOCK_BI FOR TA_REPORT_STOCK
ACTIVE BEFORE INSERT POSITION 0
AS
BEGIN
  IF (NEW.ID_REPORT_STOCK IS NULL) THEN
    NEW.ID_REPORT_STOCK = GEN_ID(NUM_ID_REPORT_STOCK,1);
   New.QUI_CREE_REPORT_STOCK  = USER;
   New.QUAND_CREE_REPORT_STOCK = 'NOW';
   New.QUI_MODIF_REPORT_STOCK = USER;
   New.QUAND_MODIF_REPORT_STOCK = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";    
END
^


/* Trigger: TA_TIERS_AD0 */
CREATE OR ALTER TRIGGER TA_TIERS_AD0 FOR TA_TIERS
ACTIVE AFTER DELETE POSITION 0
AS
begin
  delete from ta_adresse O where O.id_tiers = old.id_tiers;

  delete from ta_telephone O where O.id_tiers = old.id_tiers;
  delete from ta_email O where O.id_tiers = old.id_tiers;
  delete from ta_web O where O.id_tiers = old.id_tiers;
  delete from ta_compte_banque O where O.id_tiers = old.id_tiers;
  delete from ta_compl O where O.id_compl=old.id_compl;
  delete from ta_liens O where O.id_tiers=old.id_tiers;
  delete from ta_r_famille_tiers O where O.id_tiers=old.id_tiers;
  delete from ta_r_commercial O where O.id_tiers=old.id_tiers or o.id_tiers_com = old.id_tiers;

end
^


/* Trigger: TA_TVA_AIU0 */
CREATE TRIGGER TA_TVA_AIU0 FOR TA_TVA
ACTIVE AFTER INSERT OR UPDATE POSITION 0
AS
declare variable id_article integer;
begin
for select id_article from ta_article where ta_article.id_tva = new.id_tva into :id_article do
begin
  execute procedure RECALCULPRIXTTC(id_article,new.code_tva);
end
  /*execute procedure annule_modification('ta_tva','code_tva',new.code_tva);*/
end
^


/* Trigger: TBDID_APPORTEUR_ETRANGER */
CREATE TRIGGER TBDID_APPORTEUR_ETRANGER FOR TA_APPORTEUR
ACTIVE AFTER DELETE POSITION 1
as
begin
  delete from ta_l_APPORTEUR where id_DOCUMENT = old.id_DOCUMENT;
  delete from TA_COM_DOC where ID_DOCUMENT = old.ID_DOCUMENT;
  delete from ta_infos_APPORTEUR where id_DOCUMENT = old.id_DOCUMENT;
  delete from ta_r_document where id_APPORTEUR = old.id_DOCUMENT;
end
^


/* Trigger: TBDID_AVOIR_ETRANGER */
CREATE TRIGGER TBDID_AVOIR_ETRANGER FOR TA_AVOIR
ACTIVE AFTER DELETE POSITION 1
as
begin
  delete from ta_l_AVOIR where id_DOCUMENT = old.id_DOCUMENT;
  delete from TA_COM_DOC where ID_DOCUMENT = old.ID_DOCUMENT;
  delete from ta_infos_AVOIR where id_DOCUMENT = old.id_DOCUMENT;
  delete from ta_r_document where id_AVOIR = old.id_DOCUMENT;
end
^


/* Trigger: TBDID_BONCDE_ETRANGER */
CREATE TRIGGER TBDID_BONCDE_ETRANGER FOR TA_BONCDE
ACTIVE AFTER DELETE POSITION 1
as
begin
  delete from ta_l_BONCDE where id_DOCUMENT = old.id_DOCUMENT;
  delete from TA_COM_DOC where ID_DOCUMENT = old.ID_DOCUMENT;
  delete from ta_infos_BONCDE where id_DOCUMENT = old.id_DOCUMENT;
  delete from TA_R_DOCUMENT where id_BONCDE = old.id_DOCUMENT;
end
^


/* Trigger: TBDID_BONLIV_ETRANGER */
CREATE TRIGGER TBDID_BONLIV_ETRANGER FOR TA_BONLIV
ACTIVE AFTER DELETE POSITION 1
as
begin
  delete from ta_l_BONLIV where id_DOCUMENT = old.id_DOCUMENT;
  delete from TA_COM_DOC where ID_DOCUMENT = old.ID_DOCUMENT;
  delete from ta_infos_BONLIV where id_DOCUMENT = old.id_DOCUMENT;
  delete from TA_R_DOCUMENT where id_BONLIV = old.id_DOCUMENT;
end
^


/* Trigger: TBDID_DEVIS_ETRANGER */
CREATE TRIGGER TBDID_DEVIS_ETRANGER FOR TA_DEVIS
ACTIVE AFTER DELETE POSITION 1
as
begin
  delete from ta_l_devis where id_DOCUMENT = old.id_DOCUMENT;
  delete from TA_COM_DOC where ID_DOCUMENT = old.ID_DOCUMENT;
  delete from ta_infos_devis where id_DOCUMENT = old.id_DOCUMENT;
  delete from TA_R_DOCUMENT where id_DEVIS = old.id_DOCUMENT;
end
^


/* Trigger: TBDID_FACTURE_ETRANGER */
CREATE TRIGGER TBDID_FACTURE_ETRANGER FOR TA_FACTURE
ACTIVE AFTER DELETE POSITION 1
as
begin
  delete from ta_l_facture where id_DOCUMENT = old.id_DOCUMENT;
  delete from TA_COM_DOC where ID_DOCUMENT = old.ID_DOCUMENT;
  delete from ta_infos_facture where id_DOCUMENT = old.id_DOCUMENT;
  delete from ta_r_document where id_facture = old.id_DOCUMENT;
end
^


/* Trigger: TBDID_PROFORMA_ETRANGER */
CREATE TRIGGER TBDID_PROFORMA_ETRANGER FOR TA_PROFORMA
ACTIVE AFTER DELETE POSITION 1
as
begin
  delete from ta_l_PROFORMA where id_DOCUMENT = old.id_DOCUMENT;
  delete from TA_COM_DOC where ID_DOCUMENT = old.ID_DOCUMENT;
  delete from ta_infos_PROFORMA where id_DOCUMENT = old.id_DOCUMENT;
  delete from ta_r_document where id_PROFORMA = old.id_DOCUMENT;
end
^


/* Trigger: TBID_FAMILLE */
CREATE TRIGGER TBID_FAMILLE FOR TA_FAMILLE
ACTIVE BEFORE INSERT POSITION 1
as
begin
   If (New.ID_FAMILLE  is null) Then
      New.ID_FAMILLE  = GEN_ID(NUM_ID_FAMILLE,1);
   New.CODE_FAMILLE = Upper(New.CODE_FAMILLE);
   New.QUI_CREE_FAMILLE  = USER;
   New.QUAND_CREE_FAMILLE = 'NOW';
   New.QUI_MODIF_FAMILLE = USER;
   New.QUAND_MODIF_FAMILLE = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBID_T_TVA */
CREATE TRIGGER TBID_T_TVA FOR TA_T_TVA
ACTIVE BEFORE INSERT POSITION 1
as
begin
   If (New.ID_T_TVA  is null) Then
      New.ID_T_TVA  = GEN_ID(NUM_ID_T_TVA,1);
   New.CODE_T_TVA = Upper(New.CODE_T_TVA);
   New.QUI_CREE_T_TVA  = USER;
   New.QUAND_CREE_T_TVA = 'NOW';
   New.QUI_MODIF_T_TVA = USER;
   New.QUAND_MODIF_T_TVA = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBIID_TIERS_ID_TIERS_COM */
CREATE TRIGGER TBIID_TIERS_ID_TIERS_COM FOR TA_R_COMMERCIAL
ACTIVE BEFORE INSERT POSITION 1
as
DECLARE variable numrows integer;
begin
  /* E_XAKID_TIERS_ID_TIERS_COM  */
   if ((NEW.ID_TIERS is not null)and (NEW.ID_TIERS_COM is not null)) then
      begin
       select count(*) from TA_R_COMMERCIAL
       where ((ID_TIERS = NEW.ID_TIERS) AND (ID_TIERS_COM= NEW.ID_TIERS_COM))
       into :numrows;
       if (numrows > 0) then
          exception E_XAKID_TIERS_ID_TIERS_COM ;
      end   
end
^





/* Trigger: TBI_ARTICLE */
CREATE TRIGGER TBI_ARTICLE FOR TA_ARTICLE
ACTIVE BEFORE INSERT POSITION 2
as
begin
   If (New.ID_ARTICLE  is null) Then
      New.ID_ARTICLE  = GEN_ID(NUM_ID_ARTICLE,1);
   New.CODE_ARTICLE = Upper(New.CODE_ARTICLE);
   New.QUI_CREE_ARTICLE  = USER;
   New.QUAND_CREE_ARTICLE = 'NOW';
   New.QUI_MODIF_ARTICLE = USER;
   New.QUAND_MODIF_ARTICLE = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^



/* Trigger: TBI_COM_DOC */
CREATE TRIGGER TBI_COM_DOC FOR TA_COM_DOC
ACTIVE BEFORE INSERT POSITION 2
as
begin
   If (New.ID_COM_DOC  is null) Then
      New.ID_COM_DOC  = GEN_ID(NUM_ID_COM_DOC,1);
   New.QUI_CREE_COM_DOC  = USER;
   New.QUAND_CREE_COM_DOC = 'NOW';
   New.QUI_MODIF_COM_DOC = USER;
   New.QUAND_MODIF_COM_DOC = 'NOW';
   new.IP_ACCES=current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBI_C_PAIEMENT */
CREATE TRIGGER TBI_C_PAIEMENT FOR TA_C_PAIEMENT
ACTIVE BEFORE INSERT POSITION 2
as
begin
   If (New.ID_C_PAIEMENT  is null) Then
      New.ID_C_PAIEMENT  = GEN_ID(NUM_ID_C_PAIEMENT,1);
   New.QUI_CREE_C_PAIEMENT  = USER;
   New.QUAND_CREE_C_PAIEMENT = 'NOW';
   New.QUI_MODIF_C_PAIEMENT = USER;
   New.QUAND_MODIF_C_PAIEMENT = 'NOW';
   new.IP_ACCES=current_connection;
   select num_version from ta_version into new."VERSION";
end
^



/* Trigger: TBI_MODIF */
CREATE TRIGGER TBI_MODIF FOR TA_MODIF
ACTIVE BEFORE INSERT POSITION 1
AS
begin
   If (New.id_modif  is null) Then
      New.id_modif  = GEN_ID(num_id_modif,1);
   New.qui_cree_modif  = USER;
   New.quand_cree_modif = 'NOW';
   New.qui_modif_modif = USER;
   New.quand_modif_modif = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBI_PRIX */
CREATE TRIGGER TBI_PRIX FOR TA_PRIX
ACTIVE BEFORE INSERT POSITION 1
as
begin
   If (New.ID_PRIX  is null) Then
      New.ID_PRIX  = GEN_ID(NUM_ID_PRIX,1);
   New.QUI_CREE_PRIX  = USER;
   New.QUAND_CREE_PRIX = 'NOW';
   New.QUI_MODIF_PRIX = USER;
   New.QUAND_MODIF_PRIX = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBI_REF_PRIX */
CREATE TRIGGER TBI_REF_PRIX FOR TA_REF_PRIX
ACTIVE BEFORE INSERT POSITION 1
as
begin
   If (New.ID_REF_PRIX  is null) Then
      New.ID_REF_PRIX  = GEN_ID(NUM_ID_REF_PRIX,1);
   New.QUI_CREE_REF_PRIX  = USER;
   New.QUAND_CREE_REF_PRIX = 'NOW';
   New.QUI_MODIF_REF_PRIX = USER;
   New.QUAND_MODIF_REF_PRIX = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBI_R_DOCUMENT */
CREATE TRIGGER TBI_R_DOCUMENT FOR TA_R_DOCUMENT
ACTIVE BEFORE INSERT POSITION 2
as
begin
   If (New.ID_R_DOCUMENT  is null) Then
      New.ID_R_DOCUMENT  = GEN_ID(NUM_ID_R_DOCUMENT,1);
   New.QUI_CREE_R_DOCUMENT  = USER;
   New.QUAND_CREE_R_DOCUMENT = 'NOW';
   New.QUI_MODIF_R_DOCUMENT = USER;
   New.QUAND_MODIF_R_DOCUMENT = 'NOW';
   new.IP_ACCES=current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBI_STOCK */
CREATE TRIGGER TBI_STOCK FOR TA_STOCK
ACTIVE BEFORE INSERT POSITION 1
as
begin
   If (New.ID_STOCK  is null) Then
      New.ID_STOCK  = GEN_ID(NUM_ID_STOCK,1);
   New.QUI_CREE_STOCK  = USER;
   New.QUAND_CREE_STOCK = 'NOW';
   New.QUI_MODIF_STOCK = USER;
   New.QUAND_MODIF_STOCK = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBI_TA_ACCES */
CREATE TRIGGER TBI_TA_ACCES FOR TA_ACCES
ACTIVE BEFORE INSERT POSITION 0
as
begin
   If (New.ID_ACCES is null) Then
      New.ID_ACCES = GEN_ID(NUM_ID_ACCES,1);
   New.QUI_CREE_ACCES = USER;
   new.QUAND_CREE_ACCES = 'NOW';
   new.QUI_MODIF_ACCES = USER;
   new.QUAND_MODIF_ACCES = 'NOW';
   new.IP_ACCES = current_connection;
   new.password_acces =  'password';
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBI_TA_ADRESSE */
CREATE TRIGGER TBI_TA_ADRESSE FOR TA_ADRESSE
ACTIVE BEFORE INSERT POSITION 0
as
begin
   If (New.ID_ADRESSE is null) Then
      New.ID_ADRESSE = GEN_ID(NUM_ID_ADRESSE,1);
   New.CODEPOSTAL_ADRESSE = Upper(New.CODEPOSTAL_ADRESSE);
   New.VILLE_ADRESSE = Upper(New.VILLE_ADRESSE);
   New.PAYS_ADRESSE = Upper(New.PAYS_ADRESSE);
   New.QUI_CREE_ADRESSE = USER;
   new.QUAND_CREE_ADRESSE = 'NOW';
   new.QUI_MODIF_ADRESSE = USER;
   new.QUAND_MODIF_ADRESSE = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBI_TA_COMMENTAIRE */
CREATE TRIGGER TBI_TA_COMMENTAIRE FOR TA_COMMENTAIRE
ACTIVE BEFORE INSERT POSITION 0
as
begin
   If (New.ID_COMMENTAIRE is null) Then
      New.ID_COMMENTAIRE = GEN_ID(NUM_ID_COMMENTAIRE,1);
   New.QUI_CREE_COMMENTAIRE = USER;
   new.QUAND_CREE_COMMENTAIRE = 'NOW';
   new.QUI_MODIF_COMMENTAIRE = USER;
   new.QUAND_MODIF_COMMENTAIRE = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBI_TA_COMPL */
CREATE TRIGGER TBI_TA_COMPL FOR TA_COMPL
ACTIVE BEFORE INSERT POSITION 0
as
begin
   If (New.ID_COMPL is null) Then
      New.ID_COMPL = GEN_ID(NUM_ID_COMPL,1);
   New.QUI_CREE_COMPL = USER;
   new.QUAND_CREE_COMPL = 'NOW';
   new.QUI_MODIF_COMPL = USER;
   new.QUAND_MODIF_COMPL = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBI_TA_COMPTE_BANQUE */
CREATE TRIGGER TBI_TA_COMPTE_BANQUE FOR TA_COMPTE_BANQUE
ACTIVE BEFORE INSERT POSITION 0
as
begin
   If (New.ID_COMPTE_BANQUE is null) Then
      New.ID_COMPTE_BANQUE = GEN_ID(NUM_ID_COMPTE_BANQUE,1);
   New.QUI_CREE_COMPTE_BANQUE = USER;
   new.QUAND_CREE_COMPTE_BANQUE = 'NOW';
   new.QUI_MODIF_COMPTE_BANQUE = USER;
   new.QUAND_MODIF_COMPTE_BANQUE = 'NOW';
   select num_version from ta_version into new."VERSION";
   new.IP_ACCES = current_connection;
end
^


/* Trigger: TBI_TA_EMAIL */
CREATE TRIGGER TBI_TA_EMAIL FOR TA_EMAIL
ACTIVE BEFORE INSERT POSITION 0
as
begin
   If (New.ID_EMAIL is null) Then
      New.ID_EMAIL = GEN_ID(NUM_ID_EMAIL,1);
   New.QUI_CREE_EMAIL = USER;
   new.QUAND_CREE_EMAIL = 'NOW';
   new.QUI_MODIF_EMAIL = USER;
   new.QUAND_MODIF_EMAIL = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBI_TA_ENTREPRISE */
CREATE TRIGGER TBI_TA_ENTREPRISE FOR TA_ENTREPRISE
ACTIVE BEFORE INSERT POSITION 0
as
begin
   If (New.ID_ENTREPRISE is null) Then
      New.ID_ENTREPRISE = GEN_ID(NUM_ID_ENTREPRISE,1);
   New.nom_entreprise = Upper(New.nom_entreprise);
   New.QUI_CREE_ENTREPRISE = USER;
   new.QUAND_CREE_ENTREPRISE = 'NOW';
   new.QUI_MODIF_ENTREPRISE = USER;
   new.QUAND_MODIF_ENTREPRISE = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^




/* Trigger: TBI_TA_INFO_ENTREPRISE */
CREATE TRIGGER TBI_TA_INFO_ENTREPRISE FOR TA_INFO_ENTREPRISE
ACTIVE BEFORE INSERT POSITION 0
as
begin
   If (New.ID_INFO_ENTREPRISE is null) Then
      New.ID_INFO_ENTREPRISE = GEN_ID(NUM_ID_INFO_ENTREPRISE,1);
   New.CODEPOSTAL_INFO_ENTREPRISE = Upper(New.CODEPOSTAL_INFO_ENTREPRISE);
   New.VILLE_INFO_ENTREPRISE = Upper(New.VILLE_INFO_ENTREPRISE);
   New.PAYS_INFO_ENTREPRISE = Upper(New.PAYS_INFO_ENTREPRISE);
   New.CODEXO_INFO_ENTREPRISE = Upper(New.CODEXO_INFO_ENTREPRISE);
   New.QUI_CREE_INFO_ENTREPRISE = USER;
   new.QUAND_CREE_INFO_ENTREPRISE = 'NOW';
   new.QUI_MODIF_INFO_ENTREPRISE = USER;
   new.QUAND_MODIF_INFO_ENTREPRISE = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBI_TA_LIENS */
CREATE TRIGGER TBI_TA_LIENS FOR TA_LIENS
ACTIVE BEFORE INSERT POSITION 0
as
begin
   If (New.ID_LIENS is null) Then
      New.ID_LIENS = GEN_ID(NUM_ID_LIENS,1);
   New.QUI_CREE_LIENS = USER;
   new.QUAND_CREE_LIENS = 'NOW';
   new.QUI_MODIF_LIENS = USER;
   new.QUAND_MODIF_LIENS = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBI_TA_MAIL_MAJ */
CREATE TRIGGER TBI_TA_MAIL_MAJ FOR TA_MAIL_MAJ
ACTIVE BEFORE INSERT POSITION 1
as
begin
   If (New.ID_MAIL_MAJ  is null) Then
      New.ID_MAIL_MAJ  = GEN_ID(NUM_ID_MAIL_MAJ,1);
   New.QUI_CREE_MAIL_MAJ  = USER;
   New.QUAND_CREE_MAIL_MAJ = 'NOW';
   New.QUI_MODIF_MAIL_MAJ = USER;
   New.QUAND_MODIF_MAIL_MAJ = 'NOW';
   new.IP_ACCES=current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBI_TA_R_COMMERCIAL */
CREATE TRIGGER TBI_TA_R_COMMERCIAL FOR TA_R_COMMERCIAL
ACTIVE BEFORE INSERT POSITION 0
as
begin
   If (New.ID_R_COMMERCIAL is null) Then
      New.ID_R_COMMERCIAL = GEN_ID(NUM_ID_R_COMMERCIAL,1);
   New.QUI_CREE_R_COMMERCIAL = USER;
   new.QUAND_CREE_R_COMMERCIAL = 'NOW';
   new.QUI_MODIF_R_COMMERCIAL = USER;
   new.QUAND_MODIF_R_COMMERCIAL = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBI_TA_T_ADR */
CREATE TRIGGER TBI_TA_T_ADR FOR TA_T_ADR
ACTIVE BEFORE INSERT POSITION 0
as
begin
   If (New.ID_T_ADR is null) Then
      New.ID_T_ADR = GEN_ID(NUM_ID_T_ADR,1);
   New.CODE_T_ADR = Upper(New.CODE_T_ADR);
   New.QUI_CREE_T_ADR = USER;
   new.QUAND_CREE_T_ADR = 'NOW';
   new.QUI_MODIF_T_ADR = USER;
   new.QUAND_MODIF_T_ADR = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBI_TA_T_BANQUE */
CREATE TRIGGER TBI_TA_T_BANQUE FOR TA_T_BANQUE
ACTIVE BEFORE INSERT POSITION 0
as
begin
   If (New.ID_T_BANQUE is null) Then
      New.ID_T_BANQUE = GEN_ID(NUM_ID_T_BANQUE,1);
   New.CODE_T_BANQUE = Upper(New.CODE_T_BANQUE);
   New.QUI_CREE_T_BANQUE = USER;
   new.QUAND_CREE_T_BANQUE = 'NOW';
   new.QUI_MODIF_T_BANQUE = USER;
   new.QUAND_MODIF_T_BANQUE = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBI_TA_T_CIVILITE */
CREATE TRIGGER TBI_TA_T_CIVILITE FOR TA_T_CIVILITE
ACTIVE BEFORE INSERT POSITION 0
as
begin
   If (New.ID_T_CIVILITE is null) Then
      New.ID_T_CIVILITE = GEN_ID(NUM_ID_T_CIVILITE,1);
   New.QUI_CREE_T_CIVILITE = USER;
   new.QUAND_CREE_T_CIVILITE = 'NOW';
   new.QUI_MODIF_T_CIVILITE = USER;
   new.QUAND_MODIF_T_CIVILITE = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBI_TA_T_EMAIL */
CREATE TRIGGER TBI_TA_T_EMAIL FOR TA_T_EMAIL
ACTIVE BEFORE INSERT POSITION 0
as
begin
   If (New.ID_T_EMAIL is null) Then
      New.ID_T_EMAIL = GEN_ID(NUM_ID_T_EMAIL,1);
   New.CODE_T_EMAIL = Upper(New.CODE_T_EMAIL);
   New.QUI_CREE_T_EMAIL = USER;
   new.QUAND_CREE_T_EMAIL = 'NOW';
   new.QUI_MODIF_T_EMAIL = USER;
   new.QUAND_MODIF_T_EMAIL = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBI_TA_T_ENTITE */
CREATE TRIGGER TBI_TA_T_ENTITE FOR TA_T_ENTITE
ACTIVE BEFORE INSERT POSITION 0
as
begin
   If (New.ID_T_ENTITE is null) Then
      New.ID_T_ENTITE = GEN_ID(NUM_ID_T_ENTITE,1);
   New.QUI_CREE_T_ENTITE = USER;
   new.QUAND_CREE_T_ENTITE = 'NOW';
   new.QUI_MODIF_T_ENTITE = USER;
   new.QUAND_MODIF_T_ENTITE = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBI_TA_T_LIENS */
CREATE TRIGGER TBI_TA_T_LIENS FOR TA_T_LIENS
ACTIVE BEFORE INSERT POSITION 0
as
begin
   If (New.ID_T_LIENS is null) Then
      New.ID_T_LIENS = GEN_ID(NUM_ID_T_LIENS,1);
   New.CODE_T_LIENS = Upper(New.CODE_T_LIENS);
   New.QUI_CREE_T_LIENS = USER;
   new.QUAND_CREE_T_LIENS = 'NOW';
   new.QUI_MODIF_T_LIENS = USER;
   new.QUAND_MODIF_T_LIENS = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBI_TA_T_TARIF */
CREATE TRIGGER TBI_TA_T_TARIF FOR TA_T_TARIF
ACTIVE BEFORE INSERT POSITION 0
as
begin
   If (New.ID_T_TARIF is null) Then
      New.ID_T_TARIF = GEN_ID(NUM_ID_T_TARIF,1);
   New.CODE_T_TARIF = Upper(New.CODE_T_TARIF);
   New.QUI_CREE_T_TARIF = USER;
   new.QUAND_CREE_T_TARIF = 'NOW';
   new.QUI_MODIF_T_TARIF = USER;
   new.QUAND_MODIF_T_TARIF = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBI_TA_T_TEL */
CREATE TRIGGER TBI_TA_T_TEL FOR TA_T_TEL
ACTIVE BEFORE INSERT POSITION 0
as
begin
   If (New.ID_T_TEL is null) Then
      New.ID_T_TEL = GEN_ID(NUM_ID_T_TEL,1);
   New.CODE_T_TEL = Upper(New.CODE_T_TEL);
   New.QUI_CREE_T_TEL = USER;
   new.QUAND_CREE_T_TEL = 'NOW';
   new.QUI_MODIF_T_TEL = USER;
   new.QUAND_MODIF_T_TEL = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBI_TA_T_TIERS */
CREATE TRIGGER TBI_TA_T_TIERS FOR TA_T_TIERS
ACTIVE BEFORE INSERT POSITION 0
as
begin
   If (New.ID_T_TIERS is null) Then
      New.ID_T_TIERS = GEN_ID(NUM_ID_T_TIERS,1);
   New.CODE_T_TIERS = Upper(New.CODE_T_TIERS);
   New.QUI_CREE_T_TIERS = USER;
   new.QUAND_CREE_T_TIERS = 'NOW';
   new.QUI_MODIF_T_TIERS = USER;
   new.QUAND_MODIF_T_TIERS = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBI_TA_T_WEB */
CREATE TRIGGER TBI_TA_T_WEB FOR TA_T_WEB
ACTIVE BEFORE INSERT POSITION 0
as
begin
   If (New.ID_T_WEB is null) Then
      New.ID_T_WEB = GEN_ID(NUM_ID_T_WEB,1);
   New.CODE_T_WEB = Upper(New.CODE_T_WEB);
   New.QUI_CREE_T_WEB = USER;
   new.QUAND_CREE_T_WEB = 'NOW';
   new.QUI_MODIF_T_WEB = USER;
   new.QUAND_MODIF_T_WEB = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBI_TA_VERSION */
CREATE TRIGGER TBI_TA_VERSION FOR TA_VERSION
ACTIVE BEFORE INSERT POSITION 0
as
begin
   If (New.ID_VERSION is null) Then
      New.ID_VERSION = GEN_ID(NUM_ID_VERSION,1);
   New.QUI_CREE_VERSION = USER;
   new.QUAND_CREE_VERSION = 'NOW';
   new.QUI_MODIF_VERSION = USER;
   new.QUAND_MODIF_VERSION = 'NOW';
   new.IP_ACCES = current_connection;
end
^


/* Trigger: TBI_TA_WEB */
CREATE TRIGGER TBI_TA_WEB FOR TA_WEB
ACTIVE BEFORE INSERT POSITION 0
as
begin
   If (New.ID_WEB is null) Then
      New.ID_WEB = GEN_ID(NUM_ID_WEB,1);
   New.QUI_CREE_WEB = USER;
   new.QUAND_CREE_WEB = 'NOW';
   new.QUI_MODIF_WEB = USER;
   new.QUAND_MODIF_WEB = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBI_TELEPHONE */
CREATE TRIGGER TBI_TELEPHONE FOR TA_TELEPHONE
ACTIVE BEFORE INSERT POSITION 1
as
begin
   If (New.ID_TELEPHONE is null) Then
      New.ID_TELEPHONE = GEN_ID(NUM_ID_TELEPHONE,1);
   New.QUI_CREE_TELEPHONE = USER;
   New.QUAND_CREE_TELEPHONE = 'NOW';
   New.QUI_MODIF_TELEPHONE = USER;
   New.QUAND_MODIF_TELEPHONE = 'NOW';
   new.ip_acces = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBI_TIERS */
CREATE TRIGGER TBI_TIERS FOR TA_TIERS
ACTIVE BEFORE INSERT POSITION 0
as
begin
   If (New.ID_TIERS is null) Then
      New.ID_TIERS = GEN_ID(NUM_ID_TIERS,1);
   New.code_tiers = upper(New.code_tiers);
   New.CODE_COMPTA = upper(New.CODE_COMPTA);
   New.NOM_TIERS = upper(New.NOM_TIERS);
   New.QUI_CREE_TIERS = USER;
   New.QUAND_CREE_TIERS = 'NOW';
   New.QUI_MODIF_TIERS = USER;
   New.QUAND_MODIF_TIERS = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBI_TVA */
CREATE TRIGGER TBI_TVA FOR TA_TVA
ACTIVE BEFORE INSERT POSITION 1
as
begin
   If (New.ID_TVA  is null) Then
      New.ID_TVA  = GEN_ID(NUM_ID_TVA,1);
   New.CODE_TVA = Upper(New.CODE_TVA);
   New.QUI_CREE_TVA  = USER;
   New.QUAND_CREE_TVA = 'NOW';
   New.QUI_MODIF_TVA = USER;
   New.QUAND_MODIF_TVA = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBI_T_DOC */
CREATE TRIGGER TBI_T_DOC FOR TA_T_DOC
ACTIVE BEFORE INSERT POSITION 2
as
begin
   If (New.ID_T_DOC  is null) Then
      New.ID_T_DOC  = GEN_ID(NUM_ID_T_DOC,1);
   New.QUI_CREE_T_DOC  = USER;
   New.QUAND_CREE_T_DOC = 'NOW';
   New.QUI_MODIF_T_DOC = USER;
   New.QUAND_MODIF_T_DOC = 'NOW';
   new.IP_ACCES=current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBI_T_LIGNE */
CREATE TRIGGER TBI_T_LIGNE FOR TA_T_LIGNE
ACTIVE BEFORE INSERT POSITION 2
as
begin
   If (New.ID_T_LIGNE  is null) Then
      New.ID_T_LIGNE  = GEN_ID(NUM_ID_T_LIGNE,1);
   New.QUI_CREE_T_LIGNE  = USER;
   New.QUAND_CREE_T_LIGNE = 'NOW';
   New.QUI_MODIF_T_LIGNE = USER;
   New.QUAND_MODIF_T_LIGNE = 'NOW';
   new.IP_ACCES=current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBI_T_PAIEMENT */
CREATE TRIGGER TBI_T_PAIEMENT FOR TA_T_PAIEMENT
ACTIVE BEFORE INSERT POSITION 2
as
begin
   If (New.ID_T_PAIEMENT  is null) Then
      New.ID_T_PAIEMENT  = GEN_ID(NUM_ID_T_PAIEMENT,1);
   New.CODE_T_PAIEMENT = Upper(New.CODE_T_PAIEMENT);
   New.QUI_CREE_T_PAIEMENT  = USER;
   New.QUAND_CREE_T_PAIEMENT = 'NOW';
   New.QUI_MODIF_T_PAIEMENT = USER;
   New.QUAND_MODIF_T_PAIEMENT = 'NOW';
   new.IP_ACCES=current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBI_UNITE */
CREATE TRIGGER TBI_UNITE FOR TA_UNITE
ACTIVE BEFORE INSERT POSITION 1
as
begin
   If (New.ID_UNITE  is null) Then
      New.ID_UNITE  = GEN_ID(NUM_ID_UNITE,1);
   New.CODE_UNITE = Upper(New.CODE_UNITE);
   New.QUI_CREE_UNITE  = USER;
   New.QUAND_CREE_UNITE = 'NOW';
   New.QUI_MODIF_UNITE = USER;
   New.QUAND_MODIF_UNITE = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBI_XAKCODE_ARTICLE */
CREATE TRIGGER TBI_XAKCODE_ARTICLE FOR TA_ARTICLE
ACTIVE BEFORE INSERT POSITION 0
as
DECLARE variable numrows integer;
begin
  /* XAKCODE_ARTICLE */
   select count(*) from TA_ARTICLE
   where CODE_ARTICLE = NEW.CODE_ARTICLE
   into :numrows;
   if (numrows > 0) then
      exception E_XAKCODE_ARTICLE;
end
^


/* Trigger: TBI_XAKCODE_FAMILLE */
CREATE TRIGGER TBI_XAKCODE_FAMILLE FOR TA_FAMILLE
ACTIVE BEFORE INSERT POSITION 0
as
DECLARE variable numrows integer;
begin
  /* XAKCODE_FAMILLE */
   select count(*) from TA_FAMILLE
   where CODE_FAMILLE = NEW.CODE_FAMILLE
   into :numrows;
   if (numrows > 0) then
      exception E_XAKCODE_FAMILLE;
end
^


/* Trigger: TBUID_ARTICLE_ETRANGER */
CREATE TRIGGER TBUID_ARTICLE_ETRANGER FOR TA_ARTICLE
ACTIVE AFTER DELETE POSITION 1
as
begin
	/***********pas besoin************/
  /* delete from TA_PRIX where ID_ARTICLE=OLD.ID_ARTICLE;*/
end
^

/* Trigger: TBUID_PRIX_REF_PRIX */
CREATE TRIGGER TBUID_PRIX_REF_PRIX FOR TA_REF_PRIX
ACTIVE BEFORE UPDATE POSITION 1
as
DECLARE variable numrows integer;
begin
  /* XAKID_PRIX */
   if ((OLD.ID_PRIX <> NEW.ID_PRIX)) then
   begin
     select count(*) from TA_PRIX
     where ID_PRIX = NEW.ID_PRIX
     into :numrows;
     if (numrows = 0) then
        exception E_XAKID_PRIX;
   end
end
^


/* Trigger: TBUID_TIERS_COM_R_COMMERCIAL */
CREATE TRIGGER TBUID_TIERS_COM_R_COMMERCIAL FOR TA_R_COMMERCIAL             /*** On n'a pas d'entité correspondante**/
ACTIVE BEFORE UPDATE POSITION 1
as
DECLARE variable numrows integer;
begin
  /* E_XAKID_COMMERCIAL  */
   If  ((OLD.ID_TIERS_COM <> NEW.ID_TIERS_COM)) then
   begin
   select count(*) from TA_TIERS
   where ID_TIERS = NEW.ID_TIERS_COM
   into :numrows;
   if (numrows = 0) then
      exception E_XAKID_COMMERCIAL ;
   end
end
^


/* Trigger: TBUID_TIERS_ID_TIERS_COM */
CREATE TRIGGER TBUID_TIERS_ID_TIERS_COM FOR TA_R_COMMERCIAL             /*** On n'a pas d'entité correspondante**/
ACTIVE BEFORE UPDATE POSITION 1
as
DECLARE variable numrows integer;
begin
  /* E_XAKID_TIERS_ID_TIERS_COM  */
   If  ((OLD.ID_TIERS <> NEW.ID_TIERS)OR (OLD.ID_TIERS_COM <> NEW.ID_TIERS_COM)) then
   begin
       select count(*) from TA_R_COMMERCIAL
       where ((ID_TIERS = NEW.ID_TIERS) AND (ID_TIERS_COM= NEW.ID_TIERS_COM))
       into :numrows;
       if (numrows > 0) then
          exception E_XAKID_TIERS_ID_TIERS_COM ;
   end
end
^


/* Trigger: TBUID_TIERS_R_COMMERCIAL */
CREATE TRIGGER TBUID_TIERS_R_COMMERCIAL FOR TA_R_COMMERCIAL             /*** On n'a pas d'entité correspondante**/
ACTIVE BEFORE UPDATE POSITION 1
as
DECLARE variable numrows integer;
begin
  /* E_XAKID_TIERS  */
   If  ((OLD.ID_TIERS <> NEW.ID_TIERS)) then
   begin
   select count(*) from TA_TIERS
   where ID_TIERS = NEW.ID_TIERS
   into :numrows;
   if (numrows = 0) then
      exception E_XAKID_TIERS ;
   end
end
^


/* Trigger: TBU_ARTICLE */
CREATE TRIGGER TBU_ARTICLE FOR TA_ARTICLE
ACTIVE BEFORE UPDATE POSITION 2
as
begin
   New.CODE_ARTICLE = Upper(New.CODE_ARTICLE);
   new.QUI_MODIF_ARTICLE = USER;
   new.QUAND_MODIF_ARTICLE = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBU_COM_DOC */
CREATE TRIGGER TBU_COM_DOC FOR TA_COM_DOC
ACTIVE BEFORE UPDATE POSITION 2
as
begin
   new.QUI_MODIF_COM_DOC = USER;
   new.QUAND_MODIF_COM_DOC = 'NOW';
   new.IP_ACCES=current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBU_C_PAIEMENT */
CREATE TRIGGER TBU_C_PAIEMENT FOR TA_C_PAIEMENT
ACTIVE BEFORE UPDATE POSITION 2
as
begin
   New.QUI_MODIF_C_PAIEMENT = USER;
   New.QUAND_MODIF_C_PAIEMENT = 'NOW';
   new.IP_ACCES=current_connection;
   select num_version from ta_version into new."VERSION";
end
^



/* Trigger: TBU_FAMILLE */
CREATE TRIGGER TBU_FAMILLE FOR TA_FAMILLE
ACTIVE BEFORE UPDATE POSITION 1
as
begin
   New.CODE_FAMILLE = Upper(New.CODE_FAMILLE);
   new.QUI_MODIF_FAMILLE = USER;
   new.QUAND_MODIF_FAMILLE = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBU_PRIX */
CREATE TRIGGER TBU_PRIX FOR TA_PRIX
ACTIVE BEFORE UPDATE POSITION 1
as
begin
   new.QUI_MODIF_PRIX = USER;
   new.QUAND_MODIF_PRIX = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^



/* Trigger: TBU_REF_PRIX */
CREATE TRIGGER TBU_REF_PRIX FOR TA_REF_PRIX
ACTIVE BEFORE UPDATE POSITION 1
as
begin
   new.QUI_MODIF_REF_PRIX = USER;
   new.QUAND_MODIF_REF_PRIX = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBU_R_DOCUMENT */
CREATE TRIGGER TBU_R_DOCUMENT FOR TA_R_DOCUMENT
ACTIVE BEFORE UPDATE POSITION 2
as
begin
   new.QUI_MODIF_R_DOCUMENT = USER;
   new.QUAND_MODIF_R_DOCUMENT = 'NOW';
   new.IP_ACCES=current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBU_STOCK */
CREATE TRIGGER TBU_STOCK FOR TA_STOCK
ACTIVE BEFORE UPDATE POSITION 1
as
begin
   new.QUI_MODIF_STOCK = USER;
   new.QUAND_MODIF_STOCK = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBU_TA_ACCES */
CREATE TRIGGER TBU_TA_ACCES FOR TA_ACCES
ACTIVE BEFORE UPDATE POSITION 1
as
begin
   new.QUI_MODIF_ACCES = USER;
   new.QUAND_MODIF_ACCES = 'NOW';
   new.IP_ACCES = current_connection;
   new.password_acces =  'password';
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBU_TA_ADRESSE */
CREATE TRIGGER TBU_TA_ADRESSE FOR TA_ADRESSE
ACTIVE BEFORE UPDATE POSITION 1
as
begin
   New.CODEPOSTAL_ADRESSE = Upper(New.CODEPOSTAL_ADRESSE);
   New.VILLE_ADRESSE = Upper(New.VILLE_ADRESSE);
   New.PAYS_ADRESSE = Upper(New.PAYS_ADRESSE);
   new.QUI_MODIF_ADRESSE = USER;
   new.QUAND_MODIF_ADRESSE = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBU_TA_COMMENTAIRE */
CREATE TRIGGER TBU_TA_COMMENTAIRE FOR TA_COMMENTAIRE
ACTIVE BEFORE UPDATE POSITION 0
as
begin
   new.QUI_MODIF_COMMENTAIRE = USER;
   new.QUAND_MODIF_COMMENTAIRE = 'NOW';
   select num_version from ta_version into new."VERSION";
   new.IP_ACCES = current_connection;
end
^


/* Trigger: TBU_TA_COMPL */
CREATE TRIGGER TBU_TA_COMPL FOR TA_COMPL
ACTIVE BEFORE UPDATE POSITION 0
as
begin
   new.QUI_MODIF_COMPL = USER;
   new.QUAND_MODIF_COMPL = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBU_TA_COMPTE_BANQUE */
CREATE TRIGGER TBU_TA_COMPTE_BANQUE FOR TA_COMPTE_BANQUE
ACTIVE BEFORE UPDATE POSITION 0
as
begin
   New.COMPTE = Upper(New.COMPTE);
   new.QUI_MODIF_COMPTE_BANQUE = USER;
   new.QUAND_MODIF_COMPTE_BANQUE = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBU_TA_EMAIL */
CREATE TRIGGER TBU_TA_EMAIL FOR TA_EMAIL
ACTIVE BEFORE UPDATE POSITION 0
as
begin
   new.QUI_MODIF_EMAIL = USER;
   new.QUAND_MODIF_EMAIL = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBU_TA_ENTREPRISE */
CREATE TRIGGER TBU_TA_ENTREPRISE FOR TA_ENTREPRISE
ACTIVE BEFORE UPDATE POSITION 0
as
begin
   New.nom_entreprise = Upper(New.nom_entreprise);
   new.QUI_MODIF_ENTREPRISE = USER;
   new.QUAND_MODIF_ENTREPRISE = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^



/* Trigger: TBU_TA_INFO_ENTREPRISE */
CREATE TRIGGER TBU_TA_INFO_ENTREPRISE FOR TA_INFO_ENTREPRISE
ACTIVE BEFORE UPDATE POSITION 1
as
begin
   New.CODEPOSTAL_INFO_ENTREPRISE = Upper(New.CODEPOSTAL_INFO_ENTREPRISE);
   New.VILLE_INFO_ENTREPRISE = Upper(New.VILLE_INFO_ENTREPRISE);
   New.PAYS_INFO_ENTREPRISE = Upper(New.PAYS_INFO_ENTREPRISE);
   New.CODEXO_INFO_ENTREPRISE = Upper(New.CODEXO_INFO_ENTREPRISE);
   new.QUI_MODIF_INFO_ENTREPRISE = USER;
   new.QUAND_MODIF_INFO_ENTREPRISE = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBU_TA_LIENS */
CREATE TRIGGER TBU_TA_LIENS FOR TA_LIENS
ACTIVE BEFORE UPDATE POSITION 0
as
begin
   new.QUI_MODIF_LIENS = USER;
   new.QUAND_MODIF_LIENS = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBU_TA_MAIL_MAJ */
CREATE TRIGGER TBU_TA_MAIL_MAJ FOR TA_MAIL_MAJ
ACTIVE BEFORE UPDATE POSITION 2
as
begin
   New.QUI_MODIF_MAIL_MAJ = USER;
   New.QUAND_MODIF_MAIL_MAJ = 'NOW';
   new.IP_ACCES=current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBU_TA_R_COMMERCIAL */
CREATE TRIGGER TBU_TA_R_COMMERCIAL FOR TA_R_COMMERCIAL
ACTIVE BEFORE UPDATE POSITION 1
as
begin
   new.QUI_MODIF_R_COMMERCIAL = USER;
   new.QUAND_MODIF_R_COMMERCIAL = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBU_TA_T_ADR */
CREATE TRIGGER TBU_TA_T_ADR FOR TA_T_ADR
ACTIVE BEFORE UPDATE POSITION 0
as
begin
   New.CODE_T_ADR = Upper(New.CODE_T_ADR);
   new.QUI_MODIF_T_ADR = USER;
   new.QUAND_MODIF_T_ADR = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBU_TA_T_BANQUE */
CREATE TRIGGER TBU_TA_T_BANQUE FOR TA_T_BANQUE
ACTIVE BEFORE UPDATE POSITION 0
as
begin
   New.CODE_T_BANQUE = Upper(New.CODE_T_BANQUE);
   new.QUI_MODIF_T_BANQUE = USER;
   new.QUAND_MODIF_T_BANQUE = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBU_TA_T_CIVILITE */
CREATE TRIGGER TBU_TA_T_CIVILITE FOR TA_T_CIVILITE
ACTIVE BEFORE UPDATE POSITION 0
as
begin
   new.QUI_MODIF_T_CIVILITE = USER;
   new.QUAND_MODIF_T_CIVILITE = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBU_TA_T_EMAIL */
CREATE TRIGGER TBU_TA_T_EMAIL FOR TA_T_EMAIL
ACTIVE BEFORE UPDATE POSITION 0
as
begin
   New.CODE_T_EMAIL = Upper(New.CODE_T_EMAIL);
   new.QUI_MODIF_T_EMAIL = USER;
   new.QUAND_MODIF_T_EMAIL = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBU_TA_T_ENTITE */
CREATE TRIGGER TBU_TA_T_ENTITE FOR TA_T_ENTITE
ACTIVE BEFORE UPDATE POSITION 0
as
begin
   new.QUI_MODIF_T_ENTITE = USER;
   new.QUAND_MODIF_T_ENTITE = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBU_TA_T_LIENS */
CREATE TRIGGER TBU_TA_T_LIENS FOR TA_T_LIENS
ACTIVE BEFORE UPDATE POSITION 0
as
begin
   New.CODE_T_LIENS = Upper(New.CODE_T_LIENS);
   new.QUI_MODIF_T_LIENS = USER;
   new.QUAND_MODIF_T_LIENS = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBU_TA_T_TARIF */
CREATE TRIGGER TBU_TA_T_TARIF FOR TA_T_TARIF
ACTIVE BEFORE UPDATE POSITION 0
as
begin
   New.CODE_T_TARIF = Upper(New.CODE_T_TARIF);
   new.QUI_MODIF_T_TARIF = USER;
   new.QUAND_MODIF_T_TARIF = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBU_TA_T_TEL */
CREATE TRIGGER TBU_TA_T_TEL FOR TA_T_TEL
ACTIVE BEFORE UPDATE POSITION 0
as
begin
   New.CODE_T_TEL = Upper(New.CODE_T_TEL);
   new.QUI_MODIF_T_TEL = USER;
   new.QUAND_MODIF_T_TEL = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBU_TA_T_TIERS */
CREATE TRIGGER TBU_TA_T_TIERS FOR TA_T_TIERS
ACTIVE BEFORE UPDATE POSITION 0
as
begin
   New.CODE_T_TIERS = Upper(New.CODE_T_TIERS);
   new.QUI_MODIF_T_TIERS = USER;
   new.QUAND_MODIF_T_TIERS = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBU_TA_T_WEB */
CREATE TRIGGER TBU_TA_T_WEB FOR TA_T_WEB
ACTIVE BEFORE UPDATE POSITION 0
as
begin
   New.CODE_T_WEB = Upper(New.CODE_T_WEB);
   new.QUI_MODIF_T_WEB = USER;
   new.QUAND_MODIF_T_WEB = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBU_TA_VERSION */
CREATE TRIGGER TBU_TA_VERSION FOR TA_VERSION
ACTIVE BEFORE UPDATE POSITION 1
as
begin
   new.QUI_MODIF_VERSION = USER;
   new.QUAND_MODIF_VERSION = 'NOW';
   new.IP_ACCES = current_connection;
end
^


/* Trigger: TBU_TA_WEB */
CREATE TRIGGER TBU_TA_WEB FOR TA_WEB
ACTIVE BEFORE UPDATE POSITION 0
as
begin
   new.QUI_MODIF_WEB = USER;
   new.QUAND_MODIF_WEB = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBU_TELEPHONE */
CREATE TRIGGER TBU_TELEPHONE FOR TA_TELEPHONE
ACTIVE BEFORE UPDATE POSITION 2
as
begin
   new.QUI_MODIF_TELEPHONE = USER;
   new.QUAND_MODIF_TELEPHONE = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBU_TIERS */
CREATE TRIGGER TBU_TIERS FOR TA_TIERS
ACTIVE BEFORE UPDATE POSITION 1
as
begin
   New.code_tiers = upper(New.code_tiers);
   New.CODE_COMPTA = upper(New.CODE_COMPTA);
   New.NOM_TIERS = upper(New.NOM_TIERS);
   new.QUI_MODIF_TIERS = USER;
   new.QUAND_MODIF_TIERS = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBU_TVA */
CREATE TRIGGER TBU_TVA FOR TA_TVA
ACTIVE BEFORE UPDATE POSITION 1
as
begin
   New.CODE_TVA = Upper(New.CODE_TVA);
   new.QUI_MODIF_TVA = USER;
   new.QUAND_MODIF_TVA = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBU_T_DOC */
CREATE TRIGGER TBU_T_DOC FOR TA_T_DOC
ACTIVE BEFORE UPDATE POSITION 2
as
begin
   new.QUI_MODIF_T_DOC = USER;
   new.QUAND_MODIF_T_DOC = 'NOW';
   new.IP_ACCES=current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBU_T_LIGNE */
CREATE TRIGGER TBU_T_LIGNE FOR TA_T_LIGNE
ACTIVE BEFORE UPDATE POSITION 2
as
begin
   new.QUI_MODIF_T_LIGNE = USER;
   new.QUAND_MODIF_T_LIGNE = 'NOW';
   new.IP_ACCES=current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBU_T_PAIEMENT */
CREATE TRIGGER TBU_T_PAIEMENT FOR TA_T_PAIEMENT
ACTIVE BEFORE UPDATE POSITION 2
as
begin
   New.CODE_T_PAIEMENT = Upper(New.CODE_T_PAIEMENT);
   New.QUI_MODIF_T_PAIEMENT = USER;
   New.QUAND_MODIF_T_PAIEMENT = 'NOW';
   new.IP_ACCES=current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBU_T_TVA */
CREATE TRIGGER TBU_T_TVA FOR TA_T_TVA
ACTIVE BEFORE UPDATE POSITION 1
as
begin
   New.CODE_T_TVA = Upper(New.CODE_T_TVA);
   new.QUI_MODIF_T_TVA = USER;
   new.QUAND_MODIF_T_TVA = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBU_UNITE */
CREATE TRIGGER TBU_UNITE FOR TA_UNITE
ACTIVE BEFORE UPDATE POSITION 1
as
begin
   New.CODE_UNITE = Upper(New.CODE_UNITE);
   new.QUI_MODIF_UNITE = USER;
   new.QUAND_MODIF_UNITE = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBU_XAKCODE_ARTICLE */
CREATE TRIGGER TBU_XAKCODE_ARTICLE FOR TA_ARTICLE
ACTIVE BEFORE UPDATE POSITION 0
as
DECLARE variable numrows integer;
begin
  /* XAKCODE_ARTICLE */
   if ((OLD.CODE_ARTICLE <> NEW.CODE_ARTICLE)) then
   begin
   select count(*) from TA_ARTICLE
   where CODE_ARTICLE = NEW.CODE_ARTICLE
   into :numrows;
   if (numrows > 0) then
      exception E_XAKCODE_ARTICLE;
   end
end
^


/* Trigger: TBU_XAKCODE_FAMILLE */
CREATE TRIGGER TBU_XAKCODE_FAMILLE FOR TA_FAMILLE
ACTIVE BEFORE UPDATE POSITION 0
as
DECLARE variable numrows integer;
begin
  /* XAKCODE_FAMILLE */
   if ((OLD.CODE_FAMILLE <> NEW.CODE_FAMILLE)) then
   begin
   select count(*) from TA_FAMILLE
   where CODE_FAMILLE = NEW.CODE_FAMILLE
   into :numrows;
   if (numrows > 0) then
      exception E_XAKCODE_FAMILLE;
   end
end
^


/* Trigger: TBI_TA_R_FAMILLE_TIERS */
CREATE OR ALTER TRIGGER TBI_TA_R_FAMILLE_TIERS FOR TA_R_FAMILLE_TIERS
ACTIVE BEFORE INSERT POSITION 0
as
begin
   If (New.ID is null) Then
      New.ID = GEN_ID(NUM_ID_R_FAMILLE_TIERS,1);
   New.QUI_CREE = USER;
   new.QUAND_CREE = 'NOW';
   new.QUI_MODIF = USER;
   new.QUAND_MODIF = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBU_TA_R_FAMILLE_TIERS */
CREATE OR ALTER TRIGGER TBU_TA_R_FAMILLE_TIERS FOR TA_R_FAMILLE_TIERS
ACTIVE BEFORE UPDATE POSITION 1
as
begin
   new.QUI_MODIF = USER;
   new.QUAND_MODIF = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBID_FAMILLE_TIERS */
CREATE OR ALTER TRIGGER TBID_FAMILLE_TIERS FOR TA_FAMILLE_TIERS
ACTIVE BEFORE INSERT POSITION 1
as
begin
   If (New.ID_FAMILLE  is null) Then
      New.ID_FAMILLE  = GEN_ID(NUM_ID_FAMILLE_TIERS,1);
   New.CODE_FAMILLE = Upper(New.CODE_FAMILLE);
   New.QUI_CREE_FAMILLE  = USER;
   New.QUAND_CREE_FAMILLE = 'NOW';
   New.QUI_MODIF_FAMILLE = USER;
   New.QUAND_MODIF_FAMILLE = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBU_FAMILLE_TIERS */
CREATE OR ALTER TRIGGER TBU_FAMILLE_TIERS FOR TA_FAMILLE_TIERS
ACTIVE BEFORE UPDATE POSITION 1
as
begin
   New.CODE_FAMILLE = Upper(New.CODE_FAMILLE);
   new.QUI_MODIF_FAMILLE = USER;
   new.QUAND_MODIF_FAMILLE = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^

/* Trigger: TBID_FAMILLE_UNITE */
CREATE OR ALTER TRIGGER TBID_FAMILLE_UNITE FOR TA_FAMILLE_UNITE
ACTIVE BEFORE INSERT POSITION 1
as
begin
   If (New.ID_FAMILLE  is null) Then
      New.ID_FAMILLE  = GEN_ID(NUM_ID_FAMILLE_UNITE,1);
   New.CODE_FAMILLE = Upper(New.CODE_FAMILLE);
   New.QUI_CREE_FAMILLE  = USER;
   New.QUAND_CREE_FAMILLE = 'NOW';
   New.QUI_MODIF_FAMILLE = USER;
   New.QUAND_MODIF_FAMILLE = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBU_FAMILLE_UNITE */
CREATE OR ALTER TRIGGER TBU_FAMILLE_UNITE FOR TA_FAMILLE_UNITE
ACTIVE BEFORE UPDATE POSITION 1
as
begin
   New.CODE_FAMILLE = Upper(New.CODE_FAMILLE);
   new.QUI_MODIF_FAMILLE = USER;
   new.QUAND_MODIF_FAMILLE = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^

/* Trigger: TA_RAPPORT_UNITE_BI */
CREATE OR ALTER TRIGGER TA_RAPPORT_UNITE_BI FOR TA_RAPPORT_UNITE
ACTIVE BEFORE INSERT POSITION 0
AS
BEGIN
  IF (NEW.ID IS NULL) THEN
    NEW.ID = GEN_ID(NUM_ID_RAPPORT_UNITE,1);
   New.QUI_CREE  = USER;
   New.QUAND_CREE = 'NOW';
   New.QUI_MODIF = USER;
   New.QUAND_MODIF = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
END
^


/* Trigger: TBU_RAPPORT_UNITE */
CREATE OR ALTER TRIGGER TBU_RAPPORT_UNITE FOR TA_RAPPORT_UNITE
ACTIVE BEFORE UPDATE POSITION 2
as
begin
   New.QUI_MODIF = USER;
   New.QUAND_MODIF = 'NOW';
   new.IP_ACCES=current_connection;
   select num_version from ta_version into new."VERSION";
end
^

/* Trigger: TBID_DEPOT */
CREATE OR ALTER TRIGGER TBID_DEPOT FOR TA_DEPOT
ACTIVE BEFORE INSERT POSITION 1
as
begin
   If (New.ID_DEPOT  is null) Then
      New.ID_DEPOT  = GEN_ID(NUM_ID_DEPOT,1);
   New.QUI_CREE_DEPOT  = USER;
   New.QUAND_CREE_DEPOT = 'NOW';
   New.QUI_MODIF_DEPOT = USER;
   New.QUAND_MODIF_DEPOT = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBU_DEPOT */
CREATE OR ALTER TRIGGER TBU_DEPOT FOR TA_DEPOT
ACTIVE BEFORE UPDATE POSITION 1
as
begin
   new.QUI_MODIF_DEPOT = USER;
   new.QUAND_MODIF_DEPOT = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBID_REPORT_T_PAIEMENT */
CREATE OR ALTER TRIGGER TBID_REPORT_T_PAIEMENT FOR TA_REPORT_T_PAIEMENT
ACTIVE BEFORE INSERT POSITION 1
as
begin
   If (New.ID_REPORT_T_PAIEMENT  is null) Then
      New.ID_REPORT_T_PAIEMENT  = GEN_ID(NUM_ID_REPORT_T_PAIEMENT,1);
   New.QUI_CREE  = USER;
   New.QUAND_CREE = 'NOW';
   New.QUI_MODIF = USER;
   New.QUAND_MODIF = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBU_REPORT_T_PAIEMENT */
CREATE OR ALTER TRIGGER TBU_REPORT_T_PAIEMENT FOR TA_REPORT_T_PAIEMENT
ACTIVE BEFORE UPDATE POSITION 1
as
begin
   new.QUI_MODIF = USER;
   new.QUAND_MODIF = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^

CREATE TRIGGER TBI_TA_T_NOTE_TIERS FOR TA_T_NOTE_TIERS
BEFORE INSERT POSITION 0
as
begin
   If (New.ID_T_NOTE_TIERS is null) Then
      New.ID_T_NOTE_TIERS = GEN_ID(NUM_ID_T_NOTE_TIERS,1);
   New.CODE_T_NOTE_TIERS = Upper(New.CODE_T_NOTE_TIERS);
   New.QUI_CREE_T_NOTE = USER;
   new.QUAND_CREE_T_NOTE = 'NOW';
   new.QUI_MODIF_T_NOTE = USER;
   new.QUAND_MODIF_T_NOTE = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^

CREATE TRIGGER TBU_TA_T_NOTE_TIERS FOR TA_T_NOTE_TIERS
BEFORE UPDATE POSITION 0
as
begin
   New.CODE_T_NOTE_TIERS = Upper(New.CODE_T_NOTE_TIERS);
   new.QUI_MODIF_T_NOTE = USER;
   new.QUAND_MODIF_T_NOTE = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^

CREATE TRIGGER TBI_TA_NOTE_TIERS FOR TA_NOTE_TIERS ACTIVE
BEFORE INSERT POSITION 0
as
begin
   If (New.ID_NOTE_TIERS is null) Then
      New.ID_NOTE_TIERS = GEN_ID(NUM_ID_NOTE_TIERS,1);
   New.QUI_CREE_NOTE = USER;
   new.QUAND_CREE_NOTE = 'NOW';
   new.QUI_MODIF_NOTE = USER;
   new.QUAND_MODIF_NOTE = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^

CREATE TRIGGER TBU_TA_NOTE_TIERS FOR TA_NOTE_TIERS ACTIVE
BEFORE UPDATE POSITION 0
as
begin
   new.QUI_MODIF_NOTE = USER;
   new.QUAND_MODIF_NOTE = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^

CREATE TRIGGER TBI_TA_T_NOTE_ARTICLE FOR TA_T_NOTE_ARTICLE
BEFORE INSERT POSITION 0
as
begin
   If (New.ID_T_NOTE_ARTICLE is null) Then
      New.ID_T_NOTE_ARTICLE = GEN_ID(NUM_ID_T_NOTE_ARTICLE,1);
   New.CODE_T_NOTE_ARTICLE = Upper(New.CODE_T_NOTE_ARTICLE);
   New.QUI_CREE_T_NOTE = USER;
   new.QUAND_CREE_T_NOTE = 'NOW';
   new.QUI_MODIF_T_NOTE = USER;
   new.QUAND_MODIF_T_NOTE = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^

CREATE TRIGGER TBU_TA_T_NOTE_ARTICLE FOR TA_T_NOTE_ARTICLE
BEFORE UPDATE POSITION 0
as
begin
   New.CODE_T_NOTE_ARTICLE = Upper(New.CODE_T_NOTE_ARTICLE);
   new.QUI_MODIF_T_NOTE = USER;
   new.QUAND_MODIF_T_NOTE = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^

CREATE TRIGGER TBI_TA_NOTE_ARTICLE FOR TA_NOTE_ARTICLE ACTIVE
BEFORE INSERT POSITION 0
as
begin
   If (New.ID_NOTE_ARTICLE is null) Then
      New.ID_NOTE_ARTICLE = GEN_ID(NUM_ID_NOTE_ARTICLE,1);
   New.QUI_CREE_NOTE = USER;
   new.QUAND_CREE_NOTE = 'NOW';
   new.QUI_MODIF_NOTE = USER;
   new.QUAND_MODIF_NOTE = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^

CREATE TRIGGER TBU_TA_NOTE_ARTICLE FOR TA_NOTE_ARTICLE ACTIVE
BEFORE UPDATE POSITION 0
as
begin
   new.QUI_MODIF_NOTE = USER;
   new.QUAND_MODIF_NOTE = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^

CREATE OR ALTER TRIGGER TBDID_ACOMPTE_ETRANGER FOR TA_ACOMPTE
ACTIVE AFTER DELETE POSITION 1
as
begin
  delete from ta_l_ACOMPTE where id_DOCUMENT = old.id_DOCUMENT;
  delete from TA_COM_DOC where ID_DOCUMENT = old.ID_DOCUMENT;
  delete from ta_infos_ACOMPTE where id_DOCUMENT = old.id_DOCUMENT;
  delete from ta_r_document where id_ACOMPTE = old.id_DOCUMENT;   
end
^





CREATE OR ALTER TRIGGER TBI_R_ACOMPTE FOR TA_R_ACOMPTE
ACTIVE BEFORE INSERT POSITION 2
as
begin
   If (New.ID  is null) Then
      New.ID  = GEN_ID(num_id_r_acompte,1);
   New.QUI_CREE  = USER;
   New.QUAND_CREE = 'NOW';
   New.QUI_MODIF = USER;
   New.QUAND_MODIF = 'NOW';
   new.IP_ACCES=current_connection;
   select num_version from ta_version into new."VERSION";
end
^


CREATE OR ALTER TRIGGER TBU_R_ACOMPTE FOR TA_R_ACOMPTE
ACTIVE BEFORE UPDATE POSITION 2
as
begin
   new.QUI_MODIF = USER;
   new.QUAND_MODIF = 'NOW';
   new.IP_ACCES=current_connection;
   select num_version from ta_version into new."VERSION";
end
^

CREATE OR ALTER TRIGGER TBI_REGLEMENT FOR TA_REGLEMENT
ACTIVE BEFORE INSERT POSITION 2
as
begin
   If (New.ID_DOCUMENT  is null) Then
      New.ID_DOCUMENT  = GEN_ID(num_id_REGLEMENT,1);
   New.QUI_CREE_DOCUMENT  = USER;
   New.QUAND_CREE_DOCUMENT = 'NOW';
   New.QUI_MODIF_DOCUMENT = USER;
   New.QUAND_MODIF_DOCUMENT = 'NOW';
   new.IP_ACCES=current_connection;
   select num_version from ta_version into new."VERSION";
end
^
 
CREATE OR ALTER TRIGGER TBU_REGLEMENT FOR TA_REGLEMENT
ACTIVE BEFORE UPDATE POSITION 2
as
begin
   new.QUI_MODIF_DOCUMENT = USER;
   new.QUAND_MODIF_DOCUMENT = 'NOW';
   new.IP_ACCES=current_connection;
   select num_version from ta_version into new."VERSION";
end
^
CREATE OR ALTER TRIGGER TBI_R_REGLEMENT FOR TA_R_REGLEMENT
ACTIVE BEFORE INSERT POSITION 2
as
begin
   If (New.ID  is null) Then
      New.ID  = GEN_ID(num_id_r_REGLEMENT,1);
   New.QUI_CREE  = USER;
   New.QUAND_CREE = 'NOW';
   New.QUI_MODIF = USER;
   New.QUAND_MODIF = 'NOW';
   new.IP_ACCES=current_connection;
   select num_version from ta_version into new."VERSION";
end
^


CREATE OR ALTER TRIGGER TBU_R_REGLEMENT FOR TA_R_REGLEMENT
ACTIVE BEFORE UPDATE POSITION 2
as
begin
   new.QUI_MODIF = USER;
   new.QUAND_MODIF = 'NOW';
   new.IP_ACCES=current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBI_TA_T_TVA_DOC */
CREATE OR ALTER TRIGGER TBI_TA_T_TVA_DOC FOR TA_T_TVA_DOC
ACTIVE BEFORE INSERT POSITION 0
as
begin
   If (New.ID_T_TVA_DOC is null) Then
      New.ID_T_TVA_DOC= GEN_ID(NUM_ID_T_TVA_DOC,1);
   New.CODE_T_TVA_DOC = Upper(New.CODE_T_TVA_DOC);
   New.QUI_CREE_T_TVA_DOC = USER;
   new.QUAND_CREE_T_TVA_DOC = 'NOW';
   new.QUI_MODIF_T_TVA_DOC = USER;
   new.QUAND_MODIF_T_TVA_DOC = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBU_TA_T_TVA_DOC */
CREATE OR ALTER TRIGGER TBU_TA_T_TVA_DOC FOR TA_T_TVA_DOC
ACTIVE BEFORE UPDATE POSITION 0
as
begin
   New.CODE_T_TVA_DOC = Upper(New.CODE_T_TVA_DOC);
   new.QUI_MODIF_T_TVA_DOC = USER;
   new.QUAND_MODIF_T_TVA_DOC = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^

/* Trigger: TBI_TA_INFO_JURIDIQUE */
CREATE OR ALTER TRIGGER TBI_TA_INFO_JURIDIQUE FOR TA_INFO_JURIDIQUE
ACTIVE BEFORE INSERT POSITION 0
as
begin
   If (New.ID_INFO_JURIDIQUE is null) Then
      New.ID_INFO_JURIDIQUE = GEN_ID(NUM_ID_INFO_JURIDIQUE,1);
   New.QUI_CREE_INFO_JURIDIQUE = USER;
   new.QUAND_CREE_INFO_JURIDIQUE = 'NOW';
   new.QUI_MODIF_INFO_JURIDIQUE = USER;
   new.QUAND_MODIF_INFO_JURIDIQUE = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^

/* Trigger: TBU_TA_INFO_JURIDIQUE */
CREATE OR ALTER TRIGGER TBU_TA_INFO_JURIDIQUE FOR TA_INFO_JURIDIQUE
ACTIVE BEFORE UPDATE POSITION 1
as
begin
   new.QUI_MODIF_INFO_JURIDIQUE = USER;
   new.QUAND_MODIF_INFO_JURIDIQUE = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^

/* Trigger: TBI_TA_T_RELANCE */
CREATE OR ALTER TRIGGER TBI_TA_T_RELANCE FOR TA_T_RELANCE
ACTIVE BEFORE INSERT POSITION 0
as
begin
   If (New.ID_T_RELANCE is null) Then
      New.ID_T_RELANCE = GEN_ID(NUM_ID_T_RELANCE,1);
   New.CODE_T_RELANCE = Upper(New.CODE_T_RELANCE);
   New.QUI_CREE_T_RELANCE = USER;
   new.QUAND_CREE_T_RELANCE = 'NOW';
   new.QUI_MODIF_T_RELANCE = USER;
   new.QUAND_MODIF_T_RELANCE = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBU_TA_T_RELANCE */
CREATE OR ALTER TRIGGER TBU_TA_T_RELANCE FOR TA_T_RELANCE
ACTIVE BEFORE UPDATE POSITION 0
as
begin
   New.CODE_T_RELANCE = Upper(New.CODE_T_RELANCE);
   new.QUI_MODIF_T_RELANCE = USER;
   new.QUAND_MODIF_T_RELANCE = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^

/* Trigger: TBI_RELANCE */
CREATE OR ALTER TRIGGER TBI_RELANCE FOR TA_RELANCE
ACTIVE BEFORE INSERT POSITION 2
as
begin
   If (New.ID_RELANCE  is null) Then
      New.ID_RELANCE  = GEN_ID(num_id_RELANCE,1);
   New.QUI_CREE  = USER;
   New.QUAND_CREE = 'NOW';
   New.QUI_MODIF = USER;
   New.QUAND_MODIF = 'NOW';
   new.IP_ACCES=current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBU_RELANCE */
CREATE OR ALTER TRIGGER TBU_RELANCE FOR TA_RELANCE
ACTIVE BEFORE UPDATE POSITION 2
as
begin
   new.QUI_MODIF = USER;
   new.QUAND_MODIF = 'NOW';
   new.IP_ACCES=current_connection;
   select num_version from ta_version into new."VERSION";
end
^

/* Trigger: TBI_L_RELANCE */
CREATE OR ALTER TRIGGER TBI_L_RELANCE FOR TA_L_RELANCE
ACTIVE BEFORE INSERT POSITION 2
as
begin
   If (New.ID_L_RELANCE  is null) Then
      New.ID_L_RELANCE  = GEN_ID(num_id_L_RELANCE,1);
   New.QUI_CREE  = USER;
   New.QUAND_CREE = 'NOW';
   New.QUI_MODIF = USER;
   New.QUAND_MODIF = 'NOW';
   new.IP_ACCES=current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBU_L_RELANCE */
CREATE OR ALTER TRIGGER TBU_L_RELANCE FOR TA_L_RELANCE
ACTIVE BEFORE UPDATE POSITION 2
as
begin
   new.QUI_MODIF = USER;
   new.QUAND_MODIF = 'NOW';
   new.IP_ACCES=current_connection;
   select num_version from ta_version into new."VERSION";
end
^

/* Trigger: TBI_TA_PARAM_PUBLIPOSTAGE */
CREATE OR ALTER TRIGGER TBI_TA_PARAM_PUBLIPOSTAGE FOR TA_PARAM_PUBLIPOSTAGE
ACTIVE BEFORE INSERT POSITION 0
as
begin
   New.QUI_CREE = USER;
   new.QUAND_CREE = 'NOW';
   new.QUI_MODIF = USER;
   new.QUAND_MODIF = 'NOW';
   new.IP_ACCES = current_connection;
end
^


/* Trigger: TBU_TA_PARAM_PUBLIPOSTAGE */
CREATE OR ALTER TRIGGER TBU_TA_PARAM_PUBLIPOSTAGE FOR TA_PARAM_PUBLIPOSTAGE
ACTIVE BEFORE UPDATE POSITION 1
as
begin
   new.QUI_MODIF = USER;
   new.QUAND_MODIF = 'NOW';
   new.IP_ACCES = current_connection;
end
^

/* Trigger: TBDID_PRELEVEMENT_ETRANGER */
CREATE OR ALTER TRIGGER TBDID_PRELEVEMENT_ETRANGER FOR TA_PRELEVEMENT
ACTIVE AFTER DELETE POSITION 1
as
begin
  delete from ta_l_PRELEVEMENT where id_DOCUMENT = old.id_DOCUMENT;
  delete from TA_COM_DOC where ID_DOCUMENT = old.ID_DOCUMENT;
  delete from ta_infos_PRELEVEMENT where id_DOCUMENT = old.id_DOCUMENT;
  /*delete from ta_r_document where id_PRELEVEMENT = old.id_DOCUMENT; */
end
^



/* Trigger: TBI_DOSSIER_INTELLIGENT */
CREATE OR ALTER TRIGGER TBI_DOSSIER_INTELLIGENT FOR TA_PARAM_DOSSIER_INTELLIGENT
ACTIVE BEFORE INSERT POSITION 0
as
begin
   If (New.ID  is null) Then
      New.ID  = GEN_ID(num_id_param_doss_intel,1);
   New.QUI_CREE = USER;
   new.QUAND_CREE = 'NOW';
   new.QUI_MODIF = USER;
   new.QUAND_MODIF = 'NOW';
   new.IP_ACCES = current_connection;
end
^


/* Trigger: TBU_DOSSIER_INTELLIGENT */
CREATE OR ALTER TRIGGER TBU_DOSSIER_INTELLIGENT FOR TA_PARAM_DOSSIER_INTELLIGENT
ACTIVE BEFORE UPDATE POSITION 1
as
begin
   new.QUI_MODIF = USER;
   new.QUAND_MODIF = 'NOW';
   new.IP_ACCES = current_connection;
end
^


/* Trigger: TBI_TYPE_DONNEE */
CREATE OR ALTER TRIGGER TBI_TYPE_DONNEE FOR TA_TYPE_DONNEE
ACTIVE BEFORE INSERT POSITION 0
as
begin
   If (New.ID  is null) Then
      New.ID  = GEN_ID(num_id_type_donnee,1);
   New.QUI_CREE = USER;
   new.QUAND_CREE = 'NOW';
   new.QUI_MODIF = USER;
   new.QUAND_MODIF = 'NOW';
   new.IP_ACCES = current_connection;
end
^


/* Trigger: TBU_TYPE_DONNEE */
CREATE OR ALTER TRIGGER TBU_TYPE_DONNEE FOR TA_TYPE_DONNEE
ACTIVE BEFORE UPDATE POSITION 1
as
begin
   new.QUI_MODIF = USER;
   new.QUAND_MODIF = 'NOW';
   new.IP_ACCES = current_connection;
end
^


/* Trigger: TBI_R_PARAM_DOS_INTEL */
CREATE OR ALTER TRIGGER TBI_R_PARAM_DOS_INTEL FOR TA_R_PARAM_DOS_INTEL
ACTIVE BEFORE INSERT POSITION 2
as
begin
   If (New.ID  is null) Then
      New.ID  = GEN_ID(NUM_ID_R_PARAM_DOS_INTEL,1);
   New.QUI_CREE  = USER;
   New.QUAND_CREE = 'NOW';
   New.QUI_MODIF = USER;
   New.QUAND_MODIF = 'NOW';
   new.IP_ACCES=current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBU_R_PARAM_DOS_INTEL */
CREATE OR ALTER TRIGGER TBU_R_PARAM_DOS_INTEL FOR TA_R_PARAM_DOS_INTEL
ACTIVE BEFORE UPDATE POSITION 2
as
begin
   new.QUI_MODIF = USER;
   new.QUAND_MODIF = 'NOW';
   new.IP_ACCES=current_connection;
   select num_version from ta_version into new."VERSION";
end
^

/* Trigger: TBI_ARTICLE_POINT */
CREATE OR ALTER TRIGGER TBI_ARTICLE_POINT FOR TA_ARTICLE_POINT
ACTIVE BEFORE INSERT POSITION 1
as
begin
   If (New.ID_ARTICLE_POINT  is null) Then
      New.ID_ARTICLE_POINT  = GEN_ID(NUM_ID_ARTICLE_POINT,1);
   New.QUI_CREE  = USER;
   New.QUAND_CREE = 'NOW';
   New.QUI_MODIF = USER;
   New.QUAND_MODIF = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBU_ARTICLE_POINT */
CREATE OR ALTER TRIGGER TBU_ARTICLE_POINT FOR TA_ARTICLE_POINT
ACTIVE BEFORE UPDATE POSITION 1
as
begin
   new.QUI_MODIF = USER;
   new.QUAND_MODIF = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^




/* Trigger: TBI_ARTICLE_ACC_POINT */
CREATE OR ALTER TRIGGER TBI_ARTICLE_ACC_POINT FOR TA_ARTICLE_ACC_POINT
ACTIVE BEFORE INSERT POSITION 1
as
begin
   If (New.ID_ARTICLE_ACC_POINT  is null) Then
      New.ID_ARTICLE_ACC_POINT  = GEN_ID(NUM_ID_ARTICLE_ACC_POINT,1);
   New.QUI_CREE  = USER;
   New.QUAND_CREE = 'NOW';
   New.QUI_MODIF = USER;
   New.QUAND_MODIF = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBU_ARTICLE_ACC_POINT */
CREATE OR ALTER TRIGGER TBU_ARTICLE_ACC_POINT FOR TA_ARTICLE_ACC_POINT
ACTIVE BEFORE UPDATE POSITION 1
as
begin
   new.QUI_MODIF = USER;
   new.QUAND_MODIF = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBI_TIERS_POINT */
CREATE OR ALTER TRIGGER TBI_TIERS_POINT FOR TA_TIERS_POINT
ACTIVE BEFORE INSERT POSITION 1
as
begin
   If (New.ID_TIERS_POINT  is null) Then
      New.ID_TIERS_POINT  = GEN_ID(NUM_ID_TIERS_POINT,1);
   New.QUI_CREE  = USER;
   New.QUAND_CREE = 'NOW';
   New.QUI_MODIF = USER;
   New.QUAND_MODIF = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^



/* Trigger: TBU_TIERS_POINT */
CREATE OR ALTER TRIGGER TBU_TIERS_POINT FOR TA_TIERS_POINT
ACTIVE BEFORE UPDATE POSITION 1
as
begin
   new.QUI_MODIF = USER;
   new.QUAND_MODIF = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


create or alter TRIGGER TBID_CATALOGUE_WEB FOR TA_CATALOGUE_WEB ACTIVE
BEFORE INSERT POSITION 1
as
begin
   If (New.ID_CATALOGUE_WEB  is null) Then
      New.ID_CATALOGUE_WEB  = GEN_ID(NUM_ID_CATALOGUE_WEB,1);
   New.QUI_CREE_CATALOGUE_WEB  = USER;
   New.QUAND_CREE_CATALOGUE_WEB = 'NOW';
   New.QUI_MODIF_CATALOGUE_WEB = USER;
   New.QUAND_MODIF_CATALOGUE_WEB = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^

create or alter TRIGGER TBU_CATALOGUE_WEB FOR TA_CATALOGUE_WEB ACTIVE
BEFORE UPDATE POSITION 1
as
begin
   new.QUI_MODIF_CATALOGUE_WEB = USER;
   new.QUAND_MODIF_CATALOGUE_WEB = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^

create or alter TRIGGER TBI_CATEGORIE_ARTICLE FOR TA_CATEGORIE_ARTICLE ACTIVE
BEFORE INSERT POSITION 1
as
begin
   If (New.ID_CATEGORIE_ARTICLE  is null) Then
      New.ID_CATEGORIE_ARTICLE  = GEN_ID(NUM_ID_CATEGORIE_ARTICLE,1);
   New.QUI_CREE_CATEGORIE_ARTICLE  = USER;
   New.QUAND_CREE_CATEGORIE_ARTICLE = 'NOW';
   New.QUI_MODIF_CATEGORIE_ARTICLE = USER;
   New.QUAND_MODIF_CATEGORIE_ARTICLE = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^

create or alter TRIGGER TBU_CATEGORIE_ARTICLE FOR TA_CATEGORIE_ARTICLE ACTIVE
BEFORE UPDATE POSITION 1
as
begin
   new.QUI_MODIF_CATEGORIE_ARTICLE = USER;
   new.QUAND_MODIF_CATEGORIE_ARTICLE = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^

create or alter TRIGGER TBI_TA_R_CATEGORIE_ARTICLE FOR TA_R_CATEGORIE_ARTICLE ACTIVE
BEFORE INSERT POSITION 1
as
begin
   If (New.ID  is null) Then
      New.ID  = GEN_ID(NUM_ID_R_CATEGORIE_ARTICLE,1);
   New.QUI_CREE  = USER;
   New.QUAND_CREE = 'NOW';
   New.QUI_MODIF = USER;
   New.QUAND_MODIF = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^

create or alter TRIGGER TBU_TA_R_CATEGORIE_ARTICLE FOR TA_R_CATEGORIE_ARTICLE ACTIVE
BEFORE UPDATE POSITION 1
as
begin
   new.QUI_MODIF = USER;
   new.QUAND_MODIF = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^

create or alter TRIGGER TBI_LABEL_ARTICLE FOR TA_LABEL_ARTICLE ACTIVE
BEFORE INSERT POSITION 1
as
begin
   If (New.ID_LABEL_ARTICLE  is null) Then
      New.ID_LABEL_ARTICLE  = GEN_ID(NUM_ID_LABEL_ARTICLE,1);
   New.QUI_CREE_LABEL_ARTICLE  = USER;
   New.QUAND_CREE_LABEL_ARTICLE = 'NOW';
   New.QUI_MODIF_LABEL_ARTICLE = USER;
   New.QUAND_MODIF_LABEL_ARTICLE = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^

create or alter TRIGGER TBU_LABEL_ARTICLE FOR TA_LABEL_ARTICLE ACTIVE
BEFORE UPDATE POSITION 1
as
begin
   new.QUI_MODIF_LABEL_ARTICLE = USER;
   new.QUAND_MODIF_LABEL_ARTICLE = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^

create or alter TRIGGER TBI_TA_R_LABEL_ARTICLE FOR TA_R_LABEL_ARTICLE ACTIVE
BEFORE INSERT POSITION 1
as
begin
   If (New.ID  is null) Then
      New.ID  = GEN_ID(NUM_ID_R_LABEL_ARTICLE,1);
   New.QUI_CREE  = USER;
   New.QUAND_CREE = 'NOW';
   New.QUI_MODIF = USER;
   New.QUAND_MODIF = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^

create or alter TRIGGER TBU_TA_R_LABEL_ARTICLE FOR TA_R_LABEL_ARTICLE ACTIVE
BEFORE UPDATE POSITION 1
as
begin
   new.QUI_MODIF = USER;
   new.QUAND_MODIF = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^

create or alter TRIGGER TBID_IMAGE_ARTICLE FOR TA_IMAGE_ARTICLE ACTIVE
BEFORE INSERT POSITION 1
as
begin
   If (New.ID_IMAGE_ARTICLE  is null) Then
      New.ID_IMAGE_ARTICLE  = GEN_ID(NUM_ID_IMAGE_ARTICLE,1);
   New.QUI_CREE_IMAGE_ARTICLE  = USER;
   New.QUAND_CREE_IMAGE_ARTICLE = 'NOW';
   New.QUI_MODIF_IMAGE_ARTICLE = USER;
   New.QUAND_MODIF_IMAGE_ARTICLE = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^

create or alter TRIGGER TBU_IMAGE_ARTICLE FOR TA_IMAGE_ARTICLE ACTIVE
BEFORE UPDATE POSITION 1
as
begin
   new.QUI_MODIF_IMAGE_ARTICLE = USER;
   new.QUAND_MODIF_IMAGE_ARTICLE = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^

create or alter TRIGGER TBI_TA_R_FOURNISSEUR_ARTICLE FOR TA_R_FOURNISSEUR_ARTICLE ACTIVE
BEFORE INSERT POSITION 1
as
begin
   If (New.ID  is null) Then
      New.ID  = GEN_ID(NUM_ID_R_FOURNISSEUR_ARTICLE,1);
   New.QUI_CREE  = USER;
   New.QUAND_CREE = 'NOW';
   New.QUI_MODIF = USER;
   New.QUAND_MODIF = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^

create or alter TRIGGER TBU_TA_R_FOURNISSEUR_ARTICLE FOR TA_R_FOURNISSEUR_ARTICLE ACTIVE
BEFORE UPDATE POSITION 1
as
begin
   new.QUI_MODIF = USER;
   new.QUAND_MODIF = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^

create or alter TRIGGER TBI_TA_T_C_PAIEMENT FOR TA_T_C_PAIEMENT ACTIVE
BEFORE INSERT POSITION 1
as
begin
   If (New.ID_T_C_PAIEMENT  is null) Then
      New.ID_T_C_PAIEMENT  = GEN_ID(NUM_ID_T_C_PAIEMENT,1);
   New.QUI_CREE_T_C_PAIEMENT  = USER;
   New.QUAND_CREE_T_C_PAIEMENT = 'NOW';
   New.QUI_MODIF_T_C_PAIEMENT = USER;
   New.QUAND_MODIF_T_C_PAIEMENT = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^

create or alter TRIGGER TBU_TA_T_C_PAIEMENT FOR TA_T_C_PAIEMENT ACTIVE
BEFORE UPDATE POSITION 1
as
begin
   new.QUI_MODIF_T_C_PAIEMENT = USER;
   new.QUAND_MODIF_T_C_PAIEMENT = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^

/* Trigger: TBI_TA_DOCUMENT_TIERS */
CREATE OR ALTER TRIGGER TBI_TA_DOCUMENT_TIERS FOR TA_DOCUMENT_TIERS
ACTIVE BEFORE INSERT POSITION 0
as
begin
   If (New.ID_DOCUMENT_TIERS is null) Then
      New.ID_DOCUMENT_TIERS = GEN_ID(NUM_ID_DOCUMENT_TIERS,1);
   New.CODE_DOCUMENT_TIERS = Upper(New.CODE_DOCUMENT_TIERS);
   New.QUI_CREE_DOCUMENT_TIERS = USER;
   new.QUAND_CREE_DOCUMENT_TIERS = 'NOW';
   new.QUI_MODIF_DOCUMENT_TIERS = USER;
   new.QUAND_MODIF_DOCUMENT_TIERS = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBU_TA_DOCUMENT_TIERS */
CREATE OR ALTER TRIGGER TBU_TA_DOCUMENT_TIERS FOR TA_DOCUMENT_TIERS
ACTIVE BEFORE UPDATE POSITION 0
as
begin
   New.CODE_DOCUMENT_TIERS = Upper(New.CODE_DOCUMENT_TIERS);
   new.QUI_MODIF_DOCUMENT_TIERS = USER;
   new.QUAND_MODIF_DOCUMENT_TIERS = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^

/* Trigger: TBI_R_AVOIR */
CREATE OR ALTER TRIGGER TBI_R_AVOIR FOR TA_R_AVOIR
ACTIVE BEFORE INSERT POSITION 2
as
begin
   If (New.ID  is null) Then
      New.ID  = GEN_ID(num_id_r_AVOIR,1);
   New.QUI_CREE  = USER;
   New.QUAND_CREE = 'NOW';
   New.QUI_MODIF = USER;
   New.QUAND_MODIF = 'NOW';
   new.IP_ACCES=current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBU_R_AVOIR */
CREATE OR ALTER TRIGGER TBU_R_AVOIR FOR TA_R_AVOIR
ACTIVE BEFORE UPDATE POSITION 2
as
begin
   new.QUI_MODIF = USER;
   new.QUAND_MODIF = 'NOW';
   new.IP_ACCES=current_connection;
   select num_version from ta_version into new."VERSION";
end
^

/* Trigger: TBI_TA_DOCUMENT_DOC */
CREATE OR ALTER TRIGGER TBI_TA_DOCUMENT_DOC FOR TA_DOCUMENT_DOC
ACTIVE BEFORE INSERT POSITION 0
as
begin
   If (New.ID_DOCUMENT_DOC is null) Then
      New.ID_DOCUMENT_DOC = GEN_ID(NUM_ID_DOCUMENT_DOC,1);
   New.CODE_DOCUMENT_DOC = Upper(New.CODE_DOCUMENT_DOC);
   New.QUI_CREE_DOCUMENT_DOC = USER;
   new.QUAND_CREE_DOCUMENT_DOC = 'NOW';
   new.QUI_MODIF_DOCUMENT_DOC = USER;
   new.QUAND_MODIF_DOCUMENT_DOC = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^

/* Trigger: TBU_TA_DOCUMENT_DOC */
CREATE OR ALTER TRIGGER TBU_TA_DOCUMENT_DOC FOR TA_DOCUMENT_DOC
ACTIVE BEFORE UPDATE POSITION 0
as
begin
   New.CODE_DOCUMENT_DOC = Upper(New.CODE_DOCUMENT_DOC);
   new.QUI_MODIF_DOCUMENT_DOC = USER;
   new.QUAND_MODIF_DOCUMENT_DOC = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^

/* Trigger: TBI_TA_R_DOCUMENT_DOC */
CREATE OR ALTER TRIGGER TBI_TA_R_DOCUMENT_DOC FOR TA_R_DOCUMENT_DOC
ACTIVE BEFORE INSERT POSITION 0
as
begin
   If (New.ID_R_DOCUMENT_DOC is null) Then
      New.ID_R_DOCUMENT_DOC = GEN_ID(NUM_ID_R_DOCUMENT_DOC,1);
   New.QUI_CREE = USER;
   new.QUAND_CREE = 'NOW';
   new.QUI_MODIF = USER;
   new.QUAND_MODIF = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^

/* Trigger: TBU_TA_R_DOCUMENT_DOC */
CREATE OR ALTER TRIGGER TBU_TA_R_DOCUMENT_DOC FOR TA_R_DOCUMENT_DOC
ACTIVE BEFORE UPDATE POSITION 0
as
begin
   new.QUI_MODIF = USER;
   new.QUAND_MODIF = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^

/* Trigger: TBID_RQT */
CREATE TRIGGER TBID_RQT FOR TA_REQUETE
ACTIVE BEFORE INSERT POSITION 1
as
begin
   If (New.ID_RQT  is null) Then
      New.ID_RQT  = GEN_ID(NUM_ID_RQT,1);
end
^

CREATE TRIGGER TBID_LIGNE_RQT FOR TA_LIGNE_REQUETE
ACTIVE BEFORE INSERT POSITION 1
as
begin
   If (New.ID  is null) Then
      New.ID  = GEN_ID(NUM_ID_LIGNE_RQT,1);
end
^

/* Trigger: TBID_SYNCHRO_BOUTIQUE */
CREATE TRIGGER TBID_SYNCHRO_BOUTIQUE FOR TA_SYNCHRO_BOUTIQUE
ACTIVE BEFORE INSERT POSITION 1
as
begin
   If (New.ID  is null) Then
      New.ID  = GEN_ID(NUM_ID_SYNCHRO_BOUTIQUE,1);
end
^

CREATE OR ALTER TRIGGER TBDID_REMISE_ETRANGER FOR TA_REMISE
ACTIVE AFTER DELETE POSITION 1
as
begin
  delete from ta_l_REMISE where id_DOCUMENT = old.id_DOCUMENT;
  delete from TA_COM_DOC where ID_DOCUMENT = old.ID_DOCUMENT;
end
^



CREATE OR ALTER TRIGGER TBI_L_REMISE FOR TA_L_REMISE
ACTIVE BEFORE INSERT POSITION 2
as
begin
   New.QUI_CREE_L_DOCUMENT  = USER;
   New.QUAND_CREE_L_DOCUMENT = 'NOW';
   New.QUI_MODIF_L_DOCUMENT = USER;
   New.QUAND_MODIF_L_DOCUMENT = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^





CREATE OR ALTER TRIGGER TBU_L_REMISE FOR TA_L_REMISE
ACTIVE BEFORE UPDATE POSITION 2
as
begin
   new.QUI_MODIF_L_DOCUMENT = USER;
   new.QUAND_MODIF_L_DOCUMENT = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^

create or alter TRIGGER TBI_R_PRIX FOR TA_R_PRIX ACTIVE
BEFORE INSERT POSITION 1
as
begin
   If (New.ID  is null) Then
      New.ID  = GEN_ID(NUM_ID_R_PRIX,1);
   New.QUI_CREE  = USER;
   New.QUAND_CREE = 'NOW';
   New.QUI_MODIF = USER;
   New.QUAND_MODIF = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^

create or alter TRIGGER TBU_R_PRIX FOR TA_R_PRIX ACTIVE
BEFORE UPDATE POSITION 1
as
begin
   new.QUI_MODIF = USER;
   new.QUAND_MODIF = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^

CREATE OR ALTER TRIGGER TBI_TA_PARAM_CREE_DOC_TIERS FOR TA_PARAM_CREE_DOC_TIERS
ACTIVE BEFORE INSERT POSITION 0
as
begin
   If (New.ID_PARAM_CREE_DOC_TIERS is null) Then
      New.ID_PARAM_CREE_DOC_TIERS = GEN_ID(NUM_ID_CREE_DOC_TIERS,1);
   New.QUI_CREE = USER;
   new.QUAND_CREE = 'NOW';
   new.QUI_MODIF = USER;
   new.QUAND_MODIF = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^



CREATE OR ALTER TRIGGER TBU_TA_PARAM_CREE_DOC_TIERS FOR TA_PARAM_CREE_DOC_TIERS
ACTIVE BEFORE UPDATE POSITION 1
as
begin
   new.QUI_MODIF = USER;
   new.QUAND_MODIF = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^

CREATE OR ALTER TRIGGER TBI_TA_R_PARAM_CREAT_DOC FOR TA_R_PARAM_CREAT_DOC
ACTIVE BEFORE INSERT POSITION 0
as
begin
   If (New.ID_R_PARAM_CREAT_DOC is null) Then
      New.ID_R_PARAM_CREAT_DOC = GEN_ID(NUM_ID_R_PARAM_CREAT_DOC,1);
   New.QUI_CREE = USER;
   new.QUAND_CREE = 'NOW';
   new.QUI_MODIF = USER;
   new.QUAND_MODIF = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBU_TA_R_DOCUMENT_DOC */
CREATE OR ALTER TRIGGER TBU_TA_R_PARAM_CREAT_DOC FOR TA_R_PARAM_CREAT_DOC
ACTIVE BEFORE UPDATE POSITION 0
as
begin
   new.QUI_MODIF = USER;
   new.QUAND_MODIF = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^

CREATE OR ALTER TRIGGER TA_R_REGLEMENT_BD0 FOR TA_R_REGLEMENT
ACTIVE BEFORE DELETE POSITION 0
AS
begin
  if(not exists(select * from ta_r_reglement rr where rr.id_reglement = old.id_reglement))then
  delete from ta_reglement r where r.id_document=old.id_reglement;
end
^

CREATE OR ALTER TRIGGER TBI_TA_ETAT FOR TA_ETAT
ACTIVE BEFORE INSERT POSITION 0
as
begin
   If (New.ID_ETAT is null) Then
      New.ID_ETAT = GEN_ID(NUM_ID_ETAT,1);
   New.QUI_CREE = USER;
   new.QUAND_CREE = 'NOW';
   new.QUI_MODIF = USER;
   new.QUAND_MODIF = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^

CREATE OR ALTER TRIGGER TBU_TA_ETAT FOR TA_ETAT
ACTIVE BEFORE UPDATE POSITION 1
as
begin
   new.QUI_MODIF = USER;
   new.QUAND_MODIF = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


CREATE OR ALTER TRIGGER TBI_TA_T_ARTICLE FOR TA_T_ARTICLE
ACTIVE BEFORE INSERT POSITION 0
as
begin
   If (New.ID_T_ARTICLE is null) Then
      New.ID_T_ARTICLE = GEN_ID(NUM_ID_T_ARTICLE,1);
   New.QUI_CREE_T_ARTICLE = USER;
   new.QUAND_CREE_T_ARTICLE = 'NOW';
   new.QUI_MODIF_T_ARTICLE = USER;
   new.QUAND_MODIF_T_ARTICLE = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^



CREATE OR ALTER TRIGGER TBU_TA_T_ARTICLE FOR TA_T_ARTICLE
ACTIVE BEFORE UPDATE POSITION 0
as
begin
   new.QUI_MODIF_T_ARTICLE = USER;
   new.QUAND_MODIF_T_ARTICLE = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^

CREATE OR ALTER TRIGGER TBI_TA_R_T_ARTICLE FOR TA_R_T_ARTICLE
ACTIVE BEFORE INSERT POSITION 0
as
begin
   If (New.ID_R_T_ARTICLE is null) Then
      New.ID_R_T_ARTICLE = GEN_ID(NUM_ID_R_T_ARTICLE,1);
   New.QUI_CREE = USER;
   new.QUAND_CREE = 'NOW';
   new.QUI_MODIF = USER;
   new.QUAND_MODIF = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^



CREATE OR ALTER TRIGGER TBU_TA_R_T_ARTICLE FOR TA_R_T_ARTICLE
ACTIVE BEFORE UPDATE POSITION 1
as
begin
   new.QUI_MODIF = USER;
   new.QUAND_MODIF = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^

CREATE OR ALTER TRIGGER TBI_TA_TITRE_TRANSPORT FOR TA_TITRE_TRANSPORT
ACTIVE BEFORE INSERT POSITION 0
as
begin
   If (New.ID_TITRE_TRANSPORT is null) Then
      New.ID_TITRE_TRANSPORT = GEN_ID(NUM_ID_TITRE_TRANSPORT,1);
   New.QUI_CREE = USER;
   new.QUAND_CREE = 'NOW';
   new.QUI_MODIF = USER;
   new.QUAND_MODIF = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^

CREATE OR ALTER TRIGGER TBU_TA_TITRE_TRANSPORT FOR TA_TITRE_TRANSPORT
ACTIVE BEFORE UPDATE POSITION 1
as
begin
   new.QUI_MODIF = USER;
   new.QUAND_MODIF = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


