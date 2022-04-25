

/* View: V_ADRESSE */
RECREATE VIEW V_ADRESSE(
    ID_ADRESSE,
    ADRESSE1_ADRESSE,
    ADRESSE2_ADRESSE,
    ADRESSE3_ADRESSE,
    CODEPOSTAL_ADRESSE,
    VILLE_ADRESSE,
    PAYS_ADRESSE,
    CODE_T_ADR,
    ID_T_ADR,
    ID_TIERS)
AS
select
  Adresse_m.ID_ADRESSE,
  Adresse_m.ADRESSE1_ADRESSE,
  Adresse_m.ADRESSE2_ADRESSE,
  Adresse_m.ADRESSE3_ADRESSE,
  Adresse_m.CODEPOSTAL_ADRESSE,
  Adresse_m.VILLE_ADRESSE,
  Adresse_m.PAYS_ADRESSE,
  TAdr.CODE_T_ADR,
  TAdr.ID_T_ADR,
  RAdr.id_tiers

  from TA_ADRESSE Adresse_m
  left Join TA_R_ADR_T_ADR RAdrTAdr on (RAdrTAdr.id_Adresse=Adresse_m.id_Adresse)
  left Join TA_T_ADR TAdr on (TAdr.id_T_adr=RAdrTAdr.id_T_Adr)
  left Join Ta_R_Adr RAdr on (RAdr.id_Adresse=Adresse_m.id_Adresse)
^



/* View: V_ARTICLE */
RECREATE VIEW V_ARTICLE(
    ID_ARTICLE,
    CODE_ARTICLE,
    LIBELLEC_ARTICLE,
    LIBELLEL_ARTICLE,
    NUMCPT_ARTICLE,
    DIVERS_ARTICLE,
    COMMENTAIRE_ARTICLE,
    ID_T_TVA,
    STOCK_MIN_ARTICLE,
    ID_FAMILLE,
    ID_PRIX,
    PRIX_PRIX,
    ID_UNITE,
    CODE_UNITE,
    CODE_FAMILLE,
    LIBC_FAMILLE,
    ID_TVA,
    CODE_TVA,
    TAUX_TVA,
    NUMCPT_TVA,
    CODE_T_TVA,
    LIB_T_TVA)
AS
select
  Article.ID_ARTICLE,
  Article.CODE_ARTICLE,
  Article.LIBELLEC_ARTICLE,
  Article.LIBELLEL_ARTICLE,
  Article.NUMCPT_ARTICLE,
  Article.DIVERS_ARTICLE,
  Article.COMMENTAIRE_ARTICLE,
  Article.ID_T_TVA,
  Article.STOCK_MIN_ARTICLE,
  Article.ID_FAMILLE,
  RefPrix.ID_PRIX,
  Prix.prix_prix,
  Prix.ID_UNITE,
  Unite.CODE_UNITE,
  Famille.CODE_FAMILLE,
  Famille.LIBC_FAMILLE,
  Article.ID_TVA,
  Tva.CODE_TVA,
  Tva.TAUX_TVA,
  Tva.NUMCPT_TVA,
  TTva.CODE_T_TVA,
  TTva.LIB_T_TVA  
    
  from TA_ARTICLE Article
  left join TA_Famille Famille on (Famille.id_famille=Article.id_famille)
  LEFT JOIN TA_TVA Tva on (Tva.id_tva=Article.id_tva)
  left join TA_T_TVA TTva on (TTva.id_T_Tva = Article.id_T_Tva)
  left join  TA_REF_PRIX RefPrix on (RefPrix.id_article = Article.id_article)
  left join TA_PRIX Prix on (Prix.id_Prix=RefPrix.id_Prix)
  left join TA_UNITE Unite on (Prix.id_unite=Unite.id_unite)
^



/* View: V_BANQUE */
RECREATE VIEW V_BANQUE(
    ID_BANQUE,
    CODE_BANQUE,
    LIBC_BANQUE,
    LIBL_BANQUE)
AS
select
  Banque.ID_BANQUE,
  Banque.CODE_BANQUE,
  Banque.LIBC_BANQUE,
  Banque.LIBL_BANQUE
  
  from  TA_BANQUE Banque
^



/* View: V_BONLIV */
RECREATE VIEW V_BONLIV(
    ID_BONLIV,
    CODE_BONLIV,
    DATE_BONLIV,
    DATE_LIV_BONLIV,
    LIBELLE_BONLIV,
    ID_ADRESSE,
    ADRESSE1_ADRESSE,
    ADRESSE2_ADRESSE,
    ADRESSE3_ADRESSE,
    CODEPOSTAL_ADRESSE,
    VILLE_ADRESSE,
    PAYS_ADRESSE,
    ID_ADRESSE_LIV,
    ADRESSE1_ADRESSELIV,
    ADRESSE2_ADRESSELIV,
    ADRESSE3_ADRESSELIV,
    CODEPOSTAL_ADRESSELIV,
    VILLE_ADRESSELIV,
    PAYS_LIV,
    ID_TIERS,
    CODE_TIERS,
    NOM_TIERS,
    PRENOM_TIERS,
    SURNOM_TIERS,
    CODE_COMPTA,
    COMPTE,
    ID_T_PAIEMENT,
    CODE_T_PAIEMENT,
    ID_C_PAIEMENT,
    REM_HT_BONLIV,
    TX_REM_HT_BONLIV,
    REM_TTC_BONLIV,
    TX_REM_TTC_BONLIV,
    NB_E_BONLIV,
    MT_TTC_CALC,
    MT_HT_CALC,
    MT_TVA_CALC,
    NET_TTC_CALC,
    NET_HT_CALC,
    NET_TVA_CALC,
    IP_ACCES,
    TTC,
    COMMENTAIRE)
AS
select
  BONLIV.ID_BONLIV,
  BONLIV.CODE_BONLIV,
  BONLIV.DATE_BONLIV,
  BONLIV.date_liv_BONLIV,
  BONLIV.LIBELLE_BONLIV,
  BONLIV.ID_ADRESSE,
  infos.adresse1,
  infos.adresse2,
  infos.adresse3,
  infos.codepostal,
  infos.ville,
  infos.pays,
  BONLIV.ID_ADRESSE_LIV,
  infos.adresse1_liv,
  infos.adresse2_liv,
  infos.adresse3_liv,
  infos.codepostal_liv,
  infos.ville_liv,
  infos.PAYS_LIV,
  BONLIV.ID_TIERS,
  Tiers.code_tiers,
  infos.NOM_TIERS,
  infos.PRENOM_TIERS,
  infos.SURNOM_TIERS,
  infos.CODE_COMPTA,
  infos.COMPTE,
  BONLIV.ID_T_PAIEMENT,
  TPaiement.code_t_paiement,
  CPaiement.id_c_paiement,
  BONLIV.REM_HT_BONLIV,
  BONLIV.TX_REM_HT_BONLIV,
  BONLIV.REM_TTC_BONLIV,
  BONLIV.TX_REM_TTC_BONLIV,
  BONLIV.nb_e_BONLIV,
  cast(0 as double precision),
  cast(0 as double precision),
  cast(0 as double precision),
  cast(0 as double precision),
  cast(0 as double precision),
  cast(0 as double precision),
  BONLIV.IP_ACCES,
  BONLIV.TTC ,
  bonliv.commentaire
  from TA_BONLIV BONLIV
  left join ta_infos_BONLIV infos on (infos.ID_BONLIV = BONLIV.id_BONLIV )
  left join ta_tiers Tiers on (Tiers.id_tiers = BONLIV.id_tiers)
  left join ta_t_paiement TPaiement on (TPaiement.id_t_paiement = BONLIV.id_t_paiement)
  left join ta_c_paiement CPaiement on (CPaiement.id_c_paiement = BONLIV.id_c_paiement)
^



/* View: V_C_PAIEMENT */
RECREATE VIEW V_C_PAIEMENT(
    ID_C_PAIEMENT,
    CODE_C_PAIEMENT,
    LIB_C_PAIEMENT,
    ID_TIERS,
    REPORT_C_PAIEMENT,
    FIN_MOIS_C_PAIEMENT,
    IP_ACCES)
AS
select
  CPaiement.ID_C_PAIEMENT,
  CPaiement.CODE_C_PAIEMENT ,
  CPaiement.LIB_C_PAIEMENT ,
  CPaiement.ID_TIERS,
  CPaiement.REPORT_C_PAIEMENT,
  CPaiement.FIN_MOIS_C_PAIEMENT,
    CPaiement.IP_ACCES

from TA_C_PAIEMENT CPaiement
^



/* View: V_COM_DOC */
RECREATE VIEW V_COM_DOC(
    ID_COM_DOC,
    ID_T_DOC,
    ID_DOCUMENT,
    NOTE_COM_DOC,
    IP_ACCES)
AS
select
  ComDoc.ID_COM_DOC,
  ComDoc.ID_T_DOC,
  ComDoc.ID_DOCUMENT,
  ComDoc.NOTE_COM_DOC,
    ComDoc.IP_ACCES

from TA_COM_DOC ComDoc
^



/* View: V_COMMENTAIRE */
RECREATE VIEW V_COMMENTAIRE(
    ID_COMMENTAIRE,
    LIBL_COMMENTAIRE,
    ID_TIERS,
    CODE_TIERS)
AS
select
  Com.ID_COMMENTAIRE,
  Com.LIBL_COMMENTAIRE,
  Com.ID_TIERS,
  Tiers.Code_Tiers
  
  from TA_COMMENTAIRE Com
  left join TA_TIERS Tiers on (Tiers.id_tiers=Com.id_tiers)
^



/* View: V_COMMERCIAL */
RECREATE VIEW V_COMMERCIAL(
    ID_R_COMMERCIAL,
    ID_TIERS,
    CODE_TIERS,
    ID_TIERS_COM,
    COMMERCIAL,
    NOM_TIERS,
    CODE_T_TIERS)
AS
select Commercial.id_r_commercial,
 Commercial.id_tiers,
 tiers2.code_tiers,
 Commercial.id_tiers_com,
 TiersCom.code_tiers,
 TiersCom.nom_tiers,TTiers.code_t_tiers
 from ta_r_commercial Commercial
 LEFT outer join ta_tiers TiersCom on TiersCom.id_tiers = Commercial.id_tiers_com
 LEFT outer join ta_tiers Tiers2 on Tiers2.id_tiers = Commercial.id_tiers
 LEFT outer join TA_T_TIERS TTiers on TiersCom.ID_T_TIERS = TTiers.ID_T_TIERS
^



/* View: V_COMPL */
RECREATE VIEW V_COMPL(
    ID_COMPL,
    ID_TIERS,
    TVA_I_COM_COMPL,
    SIRET_COMPL,
    CODE_TIERS,
    NOM_TIERS,
    PRENOM_TIERS)
AS
select
  Compl.ID_COMPL,
  Compl.ID_TIERS,
  Compl.TVA_I_COM_COMPL,
  Compl.SIRET_COMPL,
  Tiers.CODE_TIERS,
  Tiers.NOM_TIERS,
  Tiers.PRENOM_TIERS


  from TA_COMPL Compl
  Join TA_TIERS Tiers on Tiers.ID_TIERS = Compl.ID_TIERS
^



/* View: V_DEVIS */
RECREATE VIEW V_DEVIS(
    ID_DEVIS,
    CODE_DEVIS,
    DATE_DEVIS,
    DATE_ECH_DEVIS,
    DATE_LIV_DEVIS,
    LIBELLE_DEVIS,
    ID_ADRESSE,
    ADRESSE1_ADRESSE,
    ADRESSE2_ADRESSE,
    ADRESSE3_ADRESSE,
    CODEPOSTAL_ADRESSE,
    VILLE_ADRESSE,
    PAYS_ADRESSE,
    ID_ADRESSE_LIV,
    ADRESSE1_ADRESSELIV,
    ADRESSE2_ADRESSELIV,
    ADRESSE3_ADRESSELIV,
    CODEPOSTAL_ADRESSELIV,
    VILLE_ADRESSELIV,
    PAYS_LIV,
    ID_TIERS,
    CODE_TIERS,
    NOM_TIERS,
    PRENOM_TIERS,
    SURNOM_TIERS,
    CODE_COMPTA,
    COMPTE,
    ID_T_PAIEMENT,
    CODE_T_PAIEMENT,
    ID_C_PAIEMENT,
    REM_HT_DEVIS,
    TX_REM_HT_DEVIS,
    REM_TTC_DEVIS,
    TX_REM_TTC_DEVIS,
    NB_E_DEVIS,
    MT_TTC_CALC,
    MT_HT_CALC,
    MT_TVA_CALC,
    NET_TTC_CALC,
    NET_HT_CALC,
    NET_TVA_CALC,
    IP_ACCES,
    TTC,
    COMMENTAIRE)
AS
select
  devis.ID_DEVIS,
  devis.CODE_DEVIS,
  devis.DATE_DEVIS,
  devis.DATE_ECH_DEVIS,
  devis.date_liv_devis,
  devis.LIBELLE_DEVIS,
  devis.ID_ADRESSE,
  infos.adresse1,
  infos.adresse2,
  infos.adresse3,
  infos.codepostal,
  infos.ville,
  infos.pays,
  devis.ID_ADRESSE_LIV,
  infos.adresse1_liv,
  infos.adresse2_liv,
  infos.adresse3_liv,
  infos.codepostal_liv,
  infos.ville_liv,
  infos.PAYS_LIV,
  devis.ID_TIERS,
  Tiers.code_tiers,
  infos.NOM_TIERS,
  infos.PRENOM_TIERS,
  infos.SURNOM_TIERS,
  infos.CODE_COMPTA,
  infos.COMPTE,
  devis.ID_T_PAIEMENT,
  TPaiement.code_t_paiement,
  CPaiement.id_c_paiement,
  devis.REM_HT_DEVIS,
  devis.TX_REM_HT_DEVIS,
  devis.REM_TTC_DEVIS,
  devis.TX_REM_TTC_DEVIS,
  devis.nb_e_devis,
  cast(0 as double precision),
  cast(0 as double precision),
  cast(0 as double precision),
  cast(0 as double precision),
  cast(0 as double precision),
  cast(0 as double precision),
  devis.IP_ACCES,
  devis.TTC,
  devis.COMMENTAIRE
  from TA_DEVIS devis
  left join ta_infos_DEVIS infos on (infos.ID_DEVIS = devis.ID_DEVIS )
  left join ta_tiers Tiers on (Tiers.id_tiers = devis.id_tiers)
  left join ta_t_paiement TPaiement on (TPaiement.id_t_paiement = devis.id_t_paiement)
  left join ta_c_paiement CPaiement on (CPaiement.id_c_paiement = devis.id_c_paiement)
^



/* View: V_EMAIL */
RECREATE VIEW V_EMAIL(
    ID_EMAIL,
    ADRESSE_EMAIL,
    CODE_TIERS,
    ID_TIERS)
AS
select
  Email.ID_EMAIL,
  Email.ADRESSE_EMAIL,
  Tiers.code_tiers,
  REmail.id_tiers

  from TA_EMAIL Email
  left join ta_R_Email REmail on (REmail.id_email=Email.id_email)
  left join ta_tiers Tiers on (Tiers.id_tiers=REmail.id_tiers)
^



/* View: V_ENTREPRISE */
RECREATE VIEW V_ENTREPRISE(
    ID_ENTREPRISE,
    ID_I_RESP_TIERS,
    ID_T_ENTITE,
    CODE_ENTREPRISE,
    LIBL_ENTREPRISE,
    CODE_T_ENTITE,
    CODE_TIERS,
    NOM_TIERS,
    PRENOM_TIERS)
AS
select
  Entreprise.ID_ENTREPRISE,
  Entreprise.ID_I_RESP_TIERS,
  Entreprise.ID_T_ENTITE,
  Entreprise.CODE_ENTREPRISE,
  Entreprise.LIBL_ENTREPRISE,
  entite.code_t_entite,
  Tiers.CODE_TIERS,
  Tiers.NOM_TIERS,
  Tiers.PRENOM_TIERS

  from TA_ENTREPRISE Entreprise
  LEFT OUTER join TA_TIERS Tiers on Tiers.ID_TIERS = Entreprise.ID_I_RESP_TIERS
  LEFT OUTER join  ta_t_entite entite on entite.id_t_entite=Entreprise.id_t_entite
^



/* View: V_FACTURE */
RECREATE VIEW V_FACTURE(
    ID_FACTURE,
    CODE_FACTURE,
    DATE_FACTURE,
    DATE_ECH_FACTURE,
    DATE_LIV_FACTURE,
    LIBELLE_FACTURE,
    ID_ADRESSE,
    ADRESSE1_ADRESSE,
    ADRESSE2_ADRESSE,
    ADRESSE3_ADRESSE,
    CODEPOSTAL_ADRESSE,
    VILLE_ADRESSE,
    PAYS_ADRESSE,
    ID_ADRESSE_LIV,
    ADRESSE1_ADRESSELIV,
    ADRESSE2_ADRESSELIV,
    ADRESSE3_ADRESSELIV,
    CODEPOSTAL_ADRESSELIV,
    VILLE_ADRESSELIV,
    PAYS_LIV,
    ID_TIERS,
    CODE_TIERS,
    NOM_TIERS,
    PRENOM_TIERS,
    SURNOM_TIERS,
    CODE_COMPTA,
    COMPTE,
    ID_T_PAIEMENT,
    CODE_T_PAIEMENT,
    ID_C_PAIEMENT,
    REGLE_FACTURE,
    REM_HT_FACTURE,
    TX_REM_HT_FACTURE,
    REM_TTC_FACTURE,
    TX_REM_TTC_FACTURE,
    NB_E_FACTURE,
    MT_TTC_CALC,
    MT_HT_CALC,
    MT_TVA_CALC,
    NET_TTC_CALC,
    NET_HT_CALC,
    NET_TVA_CALC,
    IP_ACCES,
    TTC,
    EXPORT,
    COMMENTAIRE)
AS
select
  Facture.ID_FACTURE,
  Facture.CODE_FACTURE,
  Facture.DATE_FACTURE,
  Facture.DATE_ECH_FACTURE,
  Facture.DATE_LIV_FACTURE,
  Facture.LIBELLE_FACTURE,
  Facture.ID_ADRESSE,
  infos.adresse1,
  infos.adresse2,
  infos.adresse3,
  infos.codepostal,
  infos.ville,
  infos.pays,
  Facture.ID_ADRESSE_LIV,
  infos.adresse1_liv,
  infos.adresse2_liv,
  infos.adresse3_liv,
  infos.codepostal_liv,
  infos.ville_liv,
  infos.PAYS_LIV,
  Facture.ID_TIERS,
  Tiers.code_tiers,
  infos.NOM_TIERS,
  infos.PRENOM_TIERS,
  infos.SURNOM_TIERS,
  infos.CODE_COMPTA,
  infos.COMPTE,
  Facture.ID_T_PAIEMENT,
  TPaiement.code_t_paiement,
  CPaiement.id_c_paiement,
  Facture.REGLE_FACTURE,
  Facture.REM_HT_FACTURE,
  Facture.TX_REM_HT_FACTURE,
  Facture.REM_TTC_FACTURE,
  Facture.TX_REM_TTC_FACTURE,
  Facture.NB_E_FACTURE,
  cast(0 as double precision),
  cast(0 as double precision),
  cast(0 as double precision),
  cast(0 as double precision),
  cast(0 as double precision),
  cast(0 as double precision),
  Facture.IP_ACCES,
  Facture.TTC,
  Facture.export,
  Facture.COMMENTAIRE
  from TA_FACTURE Facture
  left join ta_infos_facture infos on (infos.ID_FACTURE = Facture.ID_FACTURE )
  left join ta_tiers Tiers on (Tiers.id_tiers = Facture.id_tiers)
  left join ta_t_paiement TPaiement on (TPaiement.id_t_paiement = Facture.id_t_paiement)
  left join ta_c_paiement CPaiement on (CPaiement.id_c_paiement = Facture.id_c_paiement)
^



/* View: V_FAMILLE */
RECREATE VIEW V_FAMILLE(
    ID_FAMILLE,
    CODE_FAMILLE,
    LIBC_FAMILLE,
    LIBL_FAMILLE)
AS
select
  Famille.ID_FAMILLE,
  Famille.CODE_FAMILLE,
  Famille.LIBC_FAMILLE,
  Famille.LIBL_FAMILLE
      
  from TA_FAMILLE Famille
^



/* View: V_INFO_ENTREPRISE */
RECREATE VIEW V_INFO_ENTREPRISE(
    ID_INFO_ENTREPRISE,
    NOM_INFO_ENTREPRISE,
    ADRESSE1_INFO_ENTREPRISE,
    ADRESSE2_INFO_ENTREPRISE,
    ADRESSE3_INFO_ENTREPRISE,
    CODEPOSTAL_INFO_ENTREPRISE,
    VILLE_INFO_ENTREPRISE,
    PAYS_INFO_ENTREPRISE,
    SIRET_INFO_ENTREPRISE,
    CAPITAL_INFO_ENTREPRISE,
    APE_INFO_ENTREPRISE,
    TVA_INTRA_INFO_ENTREPRISE,
    TEL_INFO_ENTREPRISE,
    FAX_INFO_ENTREPRISE,
    EMAIL_INFO_ENTREPRISE,
    WEB_INFO_ENTREPRISE,
    RCS_INFO_ENTREPRISE,
    DATEDEB_INFO_ENTREPRISE,
    DATEFIN_INFO_ENTREPRISE,
    CODEXO_INFO_ENTREPRISE)
AS
select
ID_INFO_ENTREPRISE,
    InfoEntreprise.NOM_INFO_ENTREPRISE,
    InfoEntreprise.ADRESSE1_INFO_ENTREPRISE,
    InfoEntreprise.ADRESSE2_INFO_ENTREPRISE,
    InfoEntreprise.ADRESSE3_INFO_ENTREPRISE,
    InfoEntreprise.CODEPOSTAL_INFO_ENTREPRISE,
    InfoEntreprise.VILLE_INFO_ENTREPRISE,
    InfoEntreprise.PAYS_INFO_ENTREPRISE,
    InfoEntreprise.SIRET_INFO_ENTREPRISE,
    InfoEntreprise.CAPITAL_INFO_ENTREPRISE,
    InfoEntreprise.APE_INFO_ENTREPRISE,
    InfoEntreprise.TVA_INTRA_INFO_ENTREPRISE,
    InfoEntreprise.TEL_INFO_ENTREPRISE,
    InfoEntreprise.FAX_INFO_ENTREPRISE,
    InfoEntreprise.EMAIL_INFO_ENTREPRISE,
    InfoEntreprise.WEB_INFO_ENTREPRISE,
    InfoEntreprise.RCS_INFO_ENTREPRISE,
    InfoEntreprise.DATEDEB_INFO_ENTREPRISE,
    InfoEntreprise.DATEFIN_INFO_ENTREPRISE,
    InfoEntreprise.CODEXO_INFO_ENTREPRISE    
    
    from TA_INFO_ENTREPRISE InfoEntreprise
^



/* View: V_INFOS_BONLIV */
RECREATE VIEW V_INFOS_BONLIV(
    ID_INFOS_BONLIV,
    ID_BONLIV,
    ADRESSE1,
    ADRESSE2,
    ADRESSE3,
    CODEPOSTAL,
    VILLE,
    PAYS,
    ADRESSE1_LIV,
    ADRESSE2_LIV,
    ADRESSE3_LIV,
    CODEPOSTAL_LIV,
    VILLE_LIV,
    PAYS_LIV,
    CODE_COMPTA,
    COMPTE,
    ID_TIERS,
    NOM_TIERS,
    PRENOM_TIERS,
    SURNOM_TIERS,
    CODE_T_CIVILITE,
    CODE_T_ENTITE,
    TVA_I_COM_COMPL,
    CODE_ENTREPRISE,
    LIBL_ENTREPRISE,
    CODE_T_ENTITE_ENTREPRISE)
AS
select
  
  infos.ID_INFOS_BONLIV,
  infos.ID_BONLIV,
  infos.ADRESSE1,
  infos.ADRESSE2,
  infos.ADRESSE3,
  infos.CODEPOSTAL,
  infos.VILLE,
  infos.PAYS,        
  infos.ADRESSE1_LIV,
  infos.ADRESSE2_LIV,
  infos.ADRESSE3_LIV,
  infos.CODEPOSTAL_LIV,
  infos.VILLE_LIV,
  infos.PAYS_LIV,
  infos.CODE_COMPTA,
  infos.COMPTE,
  cast (null as INTEGER),
  infos.NOM_TIERS,
  infos.PRENOM_TIERS,
  infos.SURNOM_TIERS,
  infos.CODE_T_CIVILITE,
  infos.CODE_T_ENTITE,
  infos.TVA_I_COM_COMPL,
  infos.code_entreprise,
  infos.libl_entreprise,
  infos.code_t_entite_entreprise
  
   
  from TA_INFOS_BONLIV infos 
  
  union   

  select
  
  cast (null as INTEGER),
  cast (null as INTEGER),
  adresse.ADRESSE1_ADRESSE,
  adresse.ADRESSE2_ADRESSE,
  adresse.ADRESSE3_ADRESSE,
  adresse.CODEPOSTAL_ADRESSE,
  adresse.VILLE_ADRESSE,
  adresse.PAYS_ADRESSE,         
  cast(null as varchar(100)),
  cast(null as varchar(100)),
  cast(null as varchar(100)),
  cast(null as varchar(5)),
  cast(null as varchar(100)),
  cast(null as varchar(100)),
  cast(null as varchar(8)),
  cast(null as varchar(8)),
  tiers.id_tiers,
  cast(null as varchar(100)),
  cast(null as varchar(100)),
  cast(null as varchar(20)),
  civilite.CODE_T_CIVILITE,
  cast (null as varchar(20)),
  cast (null as varchar(50)),
  cast (null as varchar(20)),
  cast (null as varchar(100)),
  cast (null as varchar(20))
  
  from ta_adresse adresse 
  left join ta_r_adr rAdr on rAdr.ID_ADRESSE = adresse.ID_ADRESSE  
  left join Ta_tiers tiers on tiers.ID_TIERS = rAdr.ID_TIERS
  left join TA_T_CIVILITE civilite on civilite.id_T_civilite = tiers.id_T_civilite
^



/* View: V_INFOS_DEVIS */
RECREATE VIEW V_INFOS_DEVIS(
    ID_INFOS_DEVIS,
    ID_DEVIS,
    ADRESSE1,
    ADRESSE2,
    ADRESSE3,
    CODEPOSTAL,
    VILLE,
    PAYS,
    ADRESSE1_LIV,
    ADRESSE2_LIV,
    ADRESSE3_LIV,
    CODEPOSTAL_LIV,
    VILLE_LIV,
    PAYS_LIV,
    CODE_COMPTA,
    COMPTE,
    ID_TIERS,
    NOM_TIERS,
    PRENOM_TIERS,
    SURNOM_TIERS,
    CODE_T_CIVILITE,
    CODE_T_ENTITE,
    TVA_I_COM_COMPL,
    CODE_ENTREPRISE,
    LIBL_ENTREPRISE,
    CODE_T_ENTITE_ENTREPRISE)
AS
select
  infos.ID_INFOS_DEVIS,
  infos.ID_DEVIS,
  infos.ADRESSE1,
  infos.ADRESSE2,
  infos.ADRESSE3,
  infos.CODEPOSTAL,
  infos.VILLE,
  infos.PAYS,        
  infos.ADRESSE1_LIV,
  infos.ADRESSE2_LIV,
  infos.ADRESSE3_LIV,
  infos.CODEPOSTAL_LIV,
  infos.VILLE_LIV,
  infos.PAYS_LIV,
  infos.CODE_COMPTA,
  infos.COMPTE,
  cast (null as INTEGER),
  infos.NOM_TIERS,
  infos.PRENOM_TIERS,
  infos.SURNOM_TIERS,
  infos.CODE_T_CIVILITE,
  infos.CODE_T_ENTITE,
  infos.TVA_I_COM_COMPL,
  infos.code_entreprise,
  infos.libl_entreprise,
  infos.code_t_entite_entreprise
  from TA_INFOS_DEVIS infos 
  union   
  select
  cast (null as INTEGER),
  cast (null as INTEGER),
  adresse.ADRESSE1_ADRESSE,
  adresse.ADRESSE2_ADRESSE,
  adresse.ADRESSE3_ADRESSE,
  adresse.CODEPOSTAL_ADRESSE,
  adresse.VILLE_ADRESSE,
  adresse.PAYS_ADRESSE,         
  cast(null as varchar(100)),
  cast(null as varchar(100)),
  cast(null as varchar(100)),
  cast(null as varchar(5)),
  cast(null as varchar(100)),
  cast(null as varchar(100)),
  cast(null as varchar(8)),
  cast(null as varchar(8)),
  tiers.id_tiers,
  cast(null as varchar(100)),
  cast(null as varchar(100)),
  cast(null as varchar(20)),
  civilite.CODE_T_CIVILITE,
  cast (null as varchar(20)),
  cast (null as varchar(50)),
  cast (null as varchar(20)),
  cast (null as varchar(100)),
  cast (null as varchar(20))
  
  from ta_adresse adresse 
  left join ta_r_adr rAdr on rAdr.ID_ADRESSE = adresse.ID_ADRESSE  
  left join Ta_tiers tiers on tiers.ID_TIERS = rAdr.ID_TIERS
  left join TA_T_CIVILITE civilite on civilite.id_T_civilite = tiers.id_T_civilite
^



/* View: V_INFOS_FACTURE */
RECREATE VIEW V_INFOS_FACTURE(
    ID_INFOS_FACTURE,
    ID_FACTURE,
    ADRESSE1,
    ADRESSE2,
    ADRESSE3,
    CODEPOSTAL,
    VILLE,
    PAYS,
    ADRESSE1_LIV,
    ADRESSE2_LIV,
    ADRESSE3_LIV,
    CODEPOSTAL_LIV,
    VILLE_LIV,
    PAYS_LIV,
    CODE_COMPTA,
    COMPTE,
    ID_TIERS,
    NOM_TIERS,
    PRENOM_TIERS,
    SURNOM_TIERS,
    CODE_T_CIVILITE,
    CODE_T_ENTITE,
    TVA_I_COM_COMPL,
    CODE_ENTREPRISE,
    LIBL_ENTREPRISE,
    CODE_T_ENTITE_ENTREPRISE)
AS
select
  
  infos.ID_INFOS_FACTURE,
  infos.ID_FACTURE,
  infos.ADRESSE1,
  infos.ADRESSE2,
  infos.ADRESSE3,
  infos.CODEPOSTAL,
  infos.VILLE,
  infos.PAYS,        
  infos.ADRESSE1_LIV,
  infos.ADRESSE2_LIV,
  infos.ADRESSE3_LIV,
  infos.CODEPOSTAL_LIV,
  infos.VILLE_LIV,
  infos.PAYS_LIV,
  infos.CODE_COMPTA,
  infos.COMPTE,
  cast (null as INTEGER),
  infos.NOM_TIERS,
  infos.PRENOM_TIERS,
  infos.SURNOM_TIERS,
  infos.CODE_T_CIVILITE,
  infos.CODE_T_ENTITE,
  infos.TVA_I_COM_COMPL,
  infos.code_entreprise,
  infos.libl_entreprise,
  infos.code_t_entite_entreprise
   
  from TA_INFOS_FACTURE infos 
  
  union   

  select
  
  cast (null as INTEGER),
  cast (null as INTEGER),
  adresse.ADRESSE1_ADRESSE,
  adresse.ADRESSE2_ADRESSE,
  adresse.ADRESSE3_ADRESSE,
  adresse.CODEPOSTAL_ADRESSE,
  adresse.VILLE_ADRESSE,
  adresse.PAYS_ADRESSE,         
  cast(null as varchar(100)),
  cast(null as varchar(100)),
  cast(null as varchar(100)),
  cast(null as varchar(5)),
  cast(null as varchar(100)),
  cast(null as varchar(100)),
  cast(null as varchar(8)),
  cast(null as varchar(8)),
  tiers.id_tiers,
  cast(null as varchar(100)),
  cast(null as varchar(100)),
  cast(null as varchar(20)),
  civilite.CODE_T_CIVILITE,
  cast (null as varchar(20)),
  cast (null as varchar(50)),
  cast (null as varchar(20)),
  cast (null as varchar(100)),
  cast (null as varchar(20))
  
  from ta_adresse adresse 
  left join ta_r_adr rAdr on rAdr.ID_ADRESSE = adresse.ID_ADRESSE  
  left join Ta_tiers tiers on tiers.ID_TIERS = rAdr.ID_TIERS
  left join TA_T_CIVILITE civilite on civilite.id_T_civilite = tiers.id_T_civilite
^



/* View: V_L_BONLIV */
RECREATE VIEW V_L_BONLIV(
    ID_L_BONLIV,
    ID_BONLIV,
    ID_T_LIGNE,
    ID_ARTICLE,
    NUM_LIGNE_L_BONLIV,
    CODE_ARTICLE,
    LIB_L_BONLIV,
    QTE_L_BONLIV,
    U1_L_BONLIV,
    U2_L_BONLIV,
    PRIX_U_L_BONLIV,
    TAUX_TVA_L_BONLIV,
    COMPTE_L_BONLIV,
    CODE_TVA_L_BONLIV,
    CODE_T_TVA_L_BONLIV,
    MT_HT_L_BONLIV,
    MT_TTC_L_BONLIV,
    REM_TX_L_BONLIV,
    REM_HT_L_BONLIV,
    IP_ACCES)
AS
select

  LBONLIV.ID_L_BONLIV,
  LBONLIV.ID_BONLIV,
  LBONLIV.ID_T_LIGNE,
  LBONLIV.ID_ARTICLE,
  LBONLIV.NUM_LIGNE_L_BONLIV,
  article.CODE_ARTICLE,
  LBONLIV.LIB_L_BONLIV,
  LBONLIV.QTE_L_BONLIV,
  LBONLIV.U1_L_BONLIV,
  LBONLIV.U2_L_BONLIV,
  LBONLIV.PRIX_U_L_BONLIV,
  LBONLIV.TAUX_TVA_L_BONLIV,
  LBONLIV.COMPTE_L_BONLIV,
  LBONLIV.CODE_TVA_L_BONLIV,
  LBONLIV.CODE_T_TVA_L_BONLIV,
  LBONLIV.MT_HT_L_BONLIV,
  LBONLIV.MT_TTC_L_BONLIV,
  LBONLIV.REM_TX_L_BONLIV,
  LBONLIV.REM_HT_L_BONLIV,
    LBONLIV.IP_ACCES
  
  from TA_L_BONLIV LBONLIV
  left join TA_ARTICLE article on (article.id_article = LBONLIV.id_article)
^



/* View: V_L_BONLIV_TEMP */
RECREATE VIEW V_L_BONLIV_TEMP(
    ID_L_BONLIV,
    ID_BONLIV,
    ID_T_LIGNE,
    ID_ARTICLE,
    NUM_LIGNE_L_BONLIV,
    CODE_ARTICLE,
    LIB_L_BONLIV,
    QTE_L_BONLIV,
    U1_L_BONLIV,
    U2_L_BONLIV,
    PRIX_U_L_BONLIV,
    TAUX_TVA_L_BONLIV,
    COMPTE_L_BONLIV,
    CODE_TVA_L_BONLIV,
    CODE_T_TVA_L_BONLIV,
    MT_HT_L_BONLIV,
    MT_TTC_L_BONLIV,
    REM_TX_L_BONLIV,
    REM_HT_L_BONLIV,
    IP_ACCES)
AS
select
  LBONLIV.ID_L_BONLIV,
  LBONLIV.ID_BONLIV,
  LBONLIV.ID_T_LIGNE,
  LBONLIV.ID_ARTICLE,
  LBONLIV.NUM_LIGNE_L_BONLIV,
  article.CODE_ARTICLE,
  LBONLIV.LIB_L_BONLIV,
  LBONLIV.QTE_L_BONLIV,
  LBONLIV.U1_L_BONLIV,
  LBONLIV.U2_L_BONLIV,
  LBONLIV.PRIX_U_L_BONLIV,
  LBONLIV.TAUX_TVA_L_BONLIV,
  LBONLIV.COMPTE_L_BONLIV,
  LBONLIV.CODE_TVA_L_BONLIV,
  LBONLIV.CODE_T_TVA_L_BONLIV,
  LBONLIV.MT_HT_L_BONLIV,
  LBONLIV.MT_TTC_L_BONLIV,
  LBONLIV.REM_TX_L_BONLIV,
  LBONLIV.REM_HT_L_BONLIV,
  LBONLIV.IP_ACCES
  from TA_L_BONLIV_TEMP LBONLIV 
  left join TA_ARTICLE article on (article.id_article = LBONLIV.id_article)
^



/* View: V_L_DEVIS */
RECREATE VIEW V_L_DEVIS(
    ID_L_DEVIS,
    ID_DEVIS,
    ID_T_LIGNE,
    ID_ARTICLE,
    NUM_LIGNE_L_DEVIS,
    CODE_ARTICLE,
    LIB_L_DEVIS,
    QTE_L_DEVIS,
    U1_L_DEVIS,
    U2_L_DEVIS,
    PRIX_U_L_DEVIS,
    TAUX_TVA_L_DEVIS,
    COMPTE_L_DEVIS,
    CODE_TVA_L_DEVIS,
    CODE_T_TVA_L_DEVIS,
    MT_HT_L_DEVIS,
    MT_TTC_L_DEVIS,
    REM_TX_L_DEVIS,
    REM_HT_L_DEVIS,
    IP_ACCES)
AS
select
  LDEVIS.ID_L_DEVIS,
  LDEVIS.ID_DEVIS,
  LDEVIS.ID_T_LIGNE,
  LDEVIS.ID_ARTICLE,
  LDEVIS.NUM_LIGNE_L_DEVIS,
  article.CODE_ARTICLE,
  LDEVIS.LIB_L_DEVIS,
  LDEVIS.QTE_L_DEVIS,
  LDEVIS.U1_L_DEVIS,
  LDEVIS.U2_L_DEVIS,
  LDEVIS.PRIX_U_L_DEVIS,
  LDEVIS.TAUX_TVA_L_DEVIS,
  LDEVIS.COMPTE_L_DEVIS,
  LDEVIS.CODE_TVA_L_DEVIS,
  LDEVIS.CODE_T_TVA_L_DEVIS,
  LDEVIS.MT_HT_L_DEVIS,
  LDEVIS.MT_TTC_L_DEVIS,
  LDEVIS.REM_TX_L_DEVIS,
  LDEVIS.REM_HT_L_DEVIS,
    LDEVIS.IP_ACCES
  from TA_L_DEVIS LDEVIS 
  left join TA_ARTICLE article on (article.id_article = LDEVIS.id_article)
^



/* View: V_L_DEVIS_TEMP */
RECREATE VIEW V_L_DEVIS_TEMP(
    ID_L_DEVIS,
    ID_DEVIS,
    ID_T_LIGNE,
    ID_ARTICLE,
    NUM_LIGNE_L_DEVIS,
    CODE_ARTICLE,
    LIB_L_DEVIS,
    QTE_L_DEVIS,
    U1_L_DEVIS,
    U2_L_DEVIS,
    PRIX_U_L_DEVIS,
    TAUX_TVA_L_DEVIS,
    COMPTE_L_DEVIS,
    CODE_TVA_L_DEVIS,
    CODE_T_TVA_L_DEVIS,
    MT_HT_L_DEVIS,
    MT_TTC_L_DEVIS,
    REM_TX_L_DEVIS,
    REM_HT_L_DEVIS,
    IP_ACCES)
AS
select
  LDEVIS.ID_L_DEVIS,
  LDEVIS.ID_DEVIS,
  LDEVIS.ID_T_LIGNE,
  LDEVIS.ID_ARTICLE,
  LDEVIS.NUM_LIGNE_L_DEVIS,
  article.CODE_ARTICLE,
  LDEVIS.LIB_L_DEVIS,
  LDEVIS.QTE_L_DEVIS,
  LDEVIS.U1_L_DEVIS,
  LDEVIS.U2_L_DEVIS,
  LDEVIS.PRIX_U_L_DEVIS,
  LDEVIS.TAUX_TVA_L_DEVIS,
  LDEVIS.COMPTE_L_DEVIS,
  LDEVIS.CODE_TVA_L_DEVIS,
  LDEVIS.CODE_T_TVA_L_DEVIS,
  LDEVIS.MT_HT_L_DEVIS,
  LDEVIS.MT_TTC_L_DEVIS,
  LDEVIS.REM_TX_L_DEVIS,
  LDEVIS.REM_HT_L_DEVIS,
    LDEVIS.IP_ACCES
  from TA_L_DEVIS_TEMP LDEVIS 
  left join TA_ARTICLE article on (article.id_article = LDEVIS.id_article)
^



/* View: V_L_FACTURE */
RECREATE VIEW V_L_FACTURE(
    ID_L_FACTURE,
    ID_FACTURE,
    ID_T_LIGNE,
    ID_ARTICLE,
    NUM_LIGNE_L_FACTURE,
    CODE_ARTICLE,
    LIB_L_FACTURE,
    QTE_L_FACTURE,
    U1_L_FACTURE,
    U2_L_FACTURE,
    PRIX_U_L_FACTURE,
    TAUX_TVA_L_FACTURE,
    COMPTE_L_FACTURE,
    CODE_TVA_L_FACTURE,
    CODE_T_TVA_L_FACTURE,
    MT_HT_L_FACTURE,
    MT_TTC_L_FACTURE,
    REM_TX_L_FACTURE,
    REM_HT_L_FACTURE,
    IP_ACCES)
AS
select

  LFacture.ID_L_FACTURE,
  LFacture.ID_FACTURE,
  LFacture.ID_T_LIGNE,
  LFacture.ID_ARTICLE,
  LFacture.NUM_LIGNE_L_FACTURE,
  article.CODE_ARTICLE,
  LFacture.LIB_L_FACTURE,
  LFacture.QTE_L_FACTURE,
  LFacture.U1_L_FACTURE,
  LFacture.U2_L_FACTURE,
  LFacture.PRIX_U_L_FACTURE,
  LFacture.TAUX_TVA_L_FACTURE,
  LFacture.COMPTE_L_FACTURE,
  LFacture.CODE_TVA_L_FACTURE,
  LFacture.CODE_T_TVA_L_FACTURE,
  LFacture.MT_HT_L_FACTURE,
  LFacture.MT_TTC_L_FACTURE,
  LFacture.REM_TX_L_FACTURE,
  LFacture.REM_HT_L_FACTURE,
    LFacture.IP_ACCES
  
  from TA_L_FACTURE LFacture 
  left join TA_ARTICLE article on (article.id_article = LFacture.id_article)
^



/* View: V_L_FACTURE_TEMP */
RECREATE VIEW V_L_FACTURE_TEMP(
    ID_L_FACTURE,
    ID_FACTURE,
    ID_T_LIGNE,
    ID_ARTICLE,
    NUM_LIGNE_L_FACTURE,
    CODE_ARTICLE,
    LIB_L_FACTURE,
    QTE_L_FACTURE,
    U1_L_FACTURE,
    U2_L_FACTURE,
    PRIX_U_L_FACTURE,
    TAUX_TVA_L_FACTURE,
    COMPTE_L_FACTURE,
    CODE_TVA_L_FACTURE,
    CODE_T_TVA_L_FACTURE,
    MT_HT_L_FACTURE,
    MT_TTC_L_FACTURE,
    REM_TX_L_FACTURE,
    REM_HT_L_FACTURE,
    IP_ACCES)
AS
select

  LFacture.ID_L_FACTURE,
  LFacture.ID_FACTURE,
  LFacture.ID_T_LIGNE,
  LFacture.ID_ARTICLE,
  LFacture.NUM_LIGNE_L_FACTURE,
  article.CODE_ARTICLE,
  LFacture.LIB_L_FACTURE,
  LFacture.QTE_L_FACTURE,
  LFacture.U1_L_FACTURE,
  LFacture.U2_L_FACTURE,
  LFacture.PRIX_U_L_FACTURE,
  LFacture.TAUX_TVA_L_FACTURE,
  LFacture.COMPTE_L_FACTURE,
  LFacture.CODE_TVA_L_FACTURE,
  LFacture.CODE_T_TVA_L_FACTURE,
  LFacture.MT_HT_L_FACTURE,
  LFacture.MT_TTC_L_FACTURE,
  LFacture.REM_TX_L_FACTURE,
  LFacture.REM_HT_L_FACTURE,
    LFacture.IP_ACCES
  
  from TA_L_FACTURE_TEMP LFacture 
  left join TA_ARTICLE article on (article.id_article = LFacture.id_article)
^



/* View: V_L_STOCK_DOCUMENT */
RECREATE VIEW V_L_STOCK_DOCUMENT(
    DATE_DOCUMENT,
    CODE_ARTICLE,
    QTE,
    UNITE)
AS
select
   Facture.date_facture,
   article.CODE_ARTICLE,
   sum(-1*LFacture.QTE_L_FACTURE),
   LFacture.U1_L_FACTURE
  
  from TA_L_FACTURE LFacture 
  left join TA_ARTICLE article on (article.id_article = LFacture.id_article)
  left join ta_facture facture on (facture.id_facture = LFacture.id_facture)
  where ((LFacture.id_article is not null)
  and((LFacture.u1_l_facture='')or exists (select code_unite from ta_unite where code_unite=LFacture.u1_l_facture)))
  and not exists(select rdoc.id_facture from ta_r_document rdoc where rdoc.id_facture = lfacture.id_facture
  and rdoc.id_bonliv is not null)
  group by  Facture.date_facture,article.CODE_ARTICLE, LFacture.U1_L_FACTURE

  union

  select
   Facture.date_facture,
   article.CODE_ARTICLE,
   sum(-1*LFacture.QTE_L_FACTURE),
   LFacture.U2_L_FACTURE
  
  from TA_L_FACTURE LFacture 
  left join TA_ARTICLE article on (article.id_article = LFacture.id_article)
  left join ta_facture facture on (facture.id_facture = LFacture.id_facture)
  where ((LFacture.id_article is not null)
    and(exists(select code_unite from ta_unite where code_unite=LFacture.u2_l_facture)))
  group by     Facture.date_facture,article.CODE_ARTICLE, LFacture.u2_l_facture

  union

  select
  Bl.date_bonliv,
  article.CODE_ARTICLE,
  sum(-1*LBL.qte_l_bonliv),
  LBL.u1_l_bonliv
  
  from ta_l_bonliv LBL
  left join TA_ARTICLE article on (article.id_article = LBL.id_article)
  left join ta_bonliv Bl on (Bl.id_bonliv = LBL.id_bonliv)
    where ((LBL.id_article is not null)
  and((LBL.u1_l_bonliv='')or exists (select code_unite from ta_unite where code_unite=LBL.u1_l_bonliv)))
   group by Bl.date_bonliv,article.CODE_ARTICLE,LBL.u1_l_bonliv

   union

   select
  Bl.date_bonliv,
  article.CODE_ARTICLE,
  sum(-1*LBL.qte_l_bonliv),
  LBL.u2_l_bonliv
  
  from ta_l_bonliv LBL
  left join TA_ARTICLE article on (article.id_article = LBL.id_article)
  left join ta_bonliv Bl on (Bl.id_bonliv = LBL.id_bonliv)
    where ((LBL.id_article is not null)
  and(exists (select code_unite from ta_unite where code_unite=LBL.u2_l_bonliv)))
   group by Bl.date_bonliv,article.CODE_ARTICLE,LBL.u2_l_bonliv
^



/* View: V_MAIL_MAJ */
RECREATE VIEW V_MAIL_MAJ(
    ID_MAIL_MAJ,
    SUJET_MAIL_MAJ,
    NOM_SITE_MAIL_MAJ,
    URL_MAIL_MAJ,
    FAIT_MAIL_MAJ,
    A_FAIRE_MAIL_MAJ,
    FROM_MAIL_MAJ,
    DATE_MAIL_MAJ)
AS
select
     mailMaj.ID_MAIL_MAJ,
    mailMaj.SUJET_MAIL_MAJ,
    mailMaj.NOM_SITE_MAIL_MAJ,
    mailMaj.URL_MAIL_MAJ,
    mailMaj.FAIT_MAIL_MAJ,
    mailMaj.A_FAIRE_MAIL_MAJ,
    mailMaj.FROM_MAIL_MAJ,
    mailMaj.DATE_MAIL_MAJ
  from TA_MAIL_MAJ mailMaj
^



/* View: V_PRIX */
RECREATE VIEW V_PRIX(
    ID_PRIX,
    PRIX_PRIX,
    PRIXTTC_PRIX,
    ID_ARTICLE,
    CODE_ARTICLE,
    LIBELLEC_ARTICLE,
    TAUX_TVA,
    ID_UNITE,
    CODE_UNITE,
    LIBL_UNITE,
    ID_REF_PRIX)
AS
select
Prix.ID_PRIX,
Prix.PRIX_PRIX,
Prix.PRIXTTC_PRIX,
Prix.ID_ARTICLE,
Article.CODE_ARTICLE,
Article.LIBELLEC_ARTICLE,
tva.taux_tva, Prix.ID_UNITE,
Unite.CODE_UNITE,
Unite.LIBL_UNITE,
(select 1 from ta_ref_prix RefPrix where RefPrix.id_article=Prix.id_article
and RefPrix.id_prix = Prix.ID_PRIX)
from TA_PRIX Prix
left join ta_Article Article on (Article.id_article=Prix.id_article)
left join ta_tva tva on(Article.id_tva=tva.id_tva)
left join ta_Unite Unite on (Unite.id_Unite=Prix.id_Unite)
^



/* View: V_R_ADR */
RECREATE VIEW V_R_ADR(
    ID_R_ADR,
    ID_TIERS,
    CODE_TIERS,
    NOM_TIERS,
    PRENOM_TIERS,
    ID_ADRESSE,
    ADRESSE1_ADRESSE,
    ADRESSE2_ADRESSE,
    ADRESSE3_ADRESSE,
    CODEPOSTAL_ADRESSE,
    VILLE_ADRESSE,
    PAYS_ADRESSE)
AS
select
  R_Adresse.ID_R_ADR,
  R_Adresse.ID_TIERS,
  Tiers.CODE_TIERS,
  Tiers.NOM_TIERS,
  Tiers.PRENOM_TIERS,
  R_Adresse.ID_ADRESSE,
  Adresse.ADRESSE1_ADRESSE,
  Adresse.ADRESSE2_ADRESSE,
  Adresse.ADRESSE3_ADRESSE,
  Adresse.CODEPOSTAL_ADRESSE,
  Adresse.VILLE_ADRESSE,
  Adresse.PAYS_ADRESSE

  from TA_R_ADR R_Adresse
  join TA_TIERS Tiers on Tiers.ID_TIERS= R_Adresse.ID_TIERS
  Join TA_ADRESSE Adresse on Adresse.ID_ADRESSE = R_Adresse.ID_ADRESSE
^



/* View: V_R_ADR_T_ADR */
RECREATE VIEW V_R_ADR_T_ADR(
    ID_R_ADR_T_ADR,
    ID_T_ADR,
    CODE_T_ADR,
    LIBL_T_ADR,
    ID_ADRESSE,
    ADRESSE1_ADRESSE,
    ADRESSE2_ADRESSE,
    ADRESSE3_ADRESSE,
    CODEPOSTAL_ADRESSE,
    VILLE_ADRESSE,
    PAYS_ADRESSE)
AS
select
  RAdrTAdr.ID_R_ADR_T_ADR,
  TAdresse.ID_T_ADR,
  TAdresse.CODE_T_ADR,
  TAdresse.LIBL_T_ADR,
  Adresse.ID_ADRESSE,
  Adresse.ADRESSE1_ADRESSE,
  Adresse.ADRESSE2_ADRESSE,
  Adresse.ADRESSE3_ADRESSE,
  Adresse.CODEPOSTAL_ADRESSE,
  Adresse.VILLE_ADRESSE,
  Adresse.PAYS_ADRESSE

  from TA_R_ADR_T_ADR RAdrTAdr
  join TA_T_ADR TAdresse on TAdresse.ID_T_ADR = RAdrTAdr.ID_T_ADR
  join TA_ADRESSE Adresse on Adresse.ID_ADRESSE = RAdrTAdr.ID_ADRESSE
^



/* View: V_R_DOCUMENT */
RECREATE VIEW V_R_DOCUMENT(
    ID_R_DOCUMENT,
    ID_FACTURE,
    ID_DEVIS,
    ID_BONCDE,
    ID_AVOIR,
    ID_BONLIV,
    IP_ACCES)
AS
Select

  RDocument.ID_R_DOCUMENT,
  RDocument.ID_FACTURE,
  RDocument.ID_DEVIS,
  RDocument.ID_BONCDE,
  RDocument.ID_AVOIR,
  RDocument.ID_BONLIV,
    RDocument.IP_ACCES
  
from   TA_R_DOCUMENT RDocument
^



/* View: V_R_EMAIL */
RECREATE VIEW V_R_EMAIL(
    ID_R_EMAIL,
    ID_TIERS,
    CODE_TIERS,
    NOM_TIERS,
    PRENOM_TIERS,
    ID_EMAIL,
    ADRESSE_EMAIL)
AS
select
  REmail.ID_R_EMAIL,
  REmail.ID_TIERS,
  Tiers.CODE_TIERS,
  Tiers.NOM_TIERS,
  Tiers.PRENOM_TIERS,
  REmail.ID_EMAIL,
  Email.ADRESSE_EMAIL

  from TA_R_EMAIL REmail
  Join TA_TIERS Tiers on Tiers.ID_TIERS = REmail.ID_TIERS
  Join TA_EMAIL Email on Email.ID_EMAIL = REmail.ID_EMAIL
^



/* View: V_R_TEL */
RECREATE VIEW V_R_TEL(
    ID_R_TEL,
    ID_TIERS,
    CODE_TIERS,
    NOM_TIERS,
    PRENOM_TIERS,
    ID_TELEPHONE,
    NUMERO_TELEPHONE,
    POSTE_TELEPHONE)
AS
select
  RTel.ID_R_TEL,
  RTel.ID_TIERS,
  Tiers.CODE_TIERS,
  Tiers.NOM_TIERS,
  Tiers.PRENOM_TIERS,
  RTel.ID_TELEPHONE,
  Tel.NUMERO_TELEPHONE,
  Tel.POSTE_TELEPHONE

  from TA_R_TEL RTel
  Join TA_TIERS Tiers on Tiers.ID_TIERS = RTel.ID_TIERS
  Join TA_TELEPHONE Tel on Tel.ID_TELEPHONE = RTel.ID_TELEPHONE
^



/* View: V_R_TEL_T_TEL */
RECREATE VIEW V_R_TEL_T_TEL(
    ID_R_TEL_T_TEL,
    ID_TELEPHONE,
    NUMERO_TELEPHONE,
    POSTE_TELEPHONE,
    ID_T_TEL,
    CODE_T_TEL,
    LIBL_T_TEL)
AS
select
  RTel_TTel.ID_R_TEL_T_TEL,
  RTel_TTel.ID_TELEPHONE,
  Tel.NUMERO_TELEPHONE,
  Tel.POSTE_TELEPHONE,
  RTel_TTel.ID_T_TEL,
  TTel.CODE_T_TEL,
  TTel.LIBL_T_TEL

  from TA_R_TEL_T_TEL RTel_TTel
  Join TA_TELEPHONE Tel on Tel.ID_TELEPHONE = RTel_TTel.ID_TELEPHONE
  Join TA_T_TEL TTel on TTel.ID_T_TEL = RTel_TTel.ID_T_TEL
^



/* View: V_R_WEB */
RECREATE VIEW V_R_WEB(
    ID_R_WEB,
    ID_TIERS,
    CODE_TIERS,
    NOM_TIERS,
    PRENOM_TIERS,
    ID_WEB,
    ADRESSE_WEB)
AS
select
  RWeb.ID_R_WEB,
  RWeb.ID_TIERS,
  Tiers.CODE_TIERS,
  Tiers.NOM_TIERS,
  Tiers.PRENOM_TIERS,
  RWeb.ID_WEB,
  Web.ADRESSE_WEB

  from TA_R_WEB RWeb
  Join TA_TIERS Tiers on Tiers.ID_TIERS = RWeb.ID_TIERS
  Join TA_WEB Web on Web.ID_WEB = RWeb.ID_WEB
^



/* View: V_REF_PRIX */
RECREATE VIEW V_REF_PRIX(
    ID_REF_PRIX,
    ID_ARTICLE,
    ID_PRIX,
    CODE_ARTICLE,
    PRIX_PRIX,
    PRIXTTC_PRIX)
AS
Select
RefPrix.ID_REF_PRIX,
RefPrix.ID_ARTICLE,
RefPrix.ID_PRIX,
Article.CODE_ARTICLE,
Prix.PRIX_PRIX,
Prix.prixttc_prix
from TA_REF_PRIX RefPrix
left join TA_ARTICLE Article on(Article.id_article=RefPrix.id_article)
left join TA_PRIX Prix on (Prix.id_Prix=RefPrix.id_Prix)
^



/* View: V_REPORT_STOCK */
RECREATE VIEW V_REPORT_STOCK(
    ID_REPORT_STOCK,
    ID_ARTICLE_REPORT_STOCK,
    CODE_ARTICLE,
    DATE_DEB_REPORT_STOCK,
    DATE_FIN_REPORT_STOCK,
    QTE_REPORT_STOCK,
    UNITE_REPORT_STOCK)
AS
select
    R.id_report_stock,
    R.id_article_report_stock,
    article.code_article,
    R.date_deb_report_stock,
    R.date_fin_report_stock,
    R.qte_report_stock,
    R.unite_report_stock
from ta_report_stock R
left join ta_Article Article on (Article.id_article=R.id_article_report_stock)
^



/* View: V_STOCK */
RECREATE VIEW V_STOCK(
    ID_STOCK,
    ID_ARTICLE_STOCK,
    CODE_ARTICLE,
    LIBELLE_ARTICLE,
    MOUV_STOCK,
    DATE_STOCK,
    LIBELLE_STOCK,
    QTE1_STOCK,
    UN1_STOCK,
    QTE2_STOCK,
    UN2_STOCK)
AS
select
    Stock.ID_STOCK,
    Stock.id_article_stock,
    article.code_article,
    article.libellec_article,
    Stock.mouv_stock,
    Stock.date_stock,
    Stock.libelle_stock,
    Stock.qte1_stock,
    Stock.uN1_stock,
    Stock.qte2_stock,
    Stock.uN2_stock
from ta_stock Stock
left join ta_Article Article on (Article.id_article=Stock.id_article_stock)
^



/* View: V_SUM_STOCK */
RECREATE VIEW V_SUM_STOCK(
    CODE_ARTICLE,
    MOUV_STOCK,
    DATE_STOCK,
    QTE_STOCK,
    UN_STOCK)
AS
select
    article.code_article,
    stock.mouv_stock,
    Stock.date_stock,
    sum(Stock.qte1_stock),
    Stock.uN1_stock
from ta_stock Stock
join ta_Article Article on (Article.id_article=Stock.id_article_stock)
where upper(stock.mouv_stock) = 'E' and Stock.qte1_stock is not null
group by article.code_article,stock.mouv_stock,
    Stock.date_stock,
    Stock.uN1_stock

union

select
    article.code_article,
    stock.mouv_stock,
    Stock.date_stock,
    sum(Stock.qte2_stock),
    Stock.uN2_stock
from ta_stock Stock
join ta_Article Article on (Article.id_article=Stock.id_article_stock)
where upper(stock.mouv_stock) = 'E' and Stock.qte2_stock is not null
group by article.code_article,stock.mouv_stock,
    Stock.date_stock,
    Stock.uN2_stock

union

select
    article.code_article,
    stock.mouv_stock,
    Stock.date_stock,
    sum(-1*Stock.qte1_stock),
    Stock.uN1_stock
from ta_stock Stock
join ta_Article Article on (Article.id_article=Stock.id_article_stock)
where upper(stock.mouv_stock) = 'S'   and Stock.qte1_stock is not null
group by article.code_article,stock.mouv_stock,
    Stock.date_stock,
    Stock.uN1_stock

union

select
    article.code_article,
    stock.mouv_stock,
    Stock.date_stock,
    sum(-1*Stock.qte2_stock),
    Stock.uN2_stock
from ta_stock Stock
join ta_Article Article on (Article.id_article=Stock.id_article_stock)
where upper(stock.mouv_stock) = 'S'   and Stock.qte2_stock is not null
group by article.code_article,stock.mouv_stock,
    Stock.date_stock,
    Stock.uN2_stock
^



/* View: V_T_ADR */
RECREATE VIEW V_T_ADR(
    ID_T_ADR,
    CODE_T_ADR,
    LIBL_T_ADR)
AS
select
  T_Adresse.ID_T_ADR,
  T_Adresse.CODE_T_ADR,
  T_Adresse.LIBL_T_ADR

  from TA_T_ADR T_Adresse
^



/* View: V_T_CIVILITE */
RECREATE VIEW V_T_CIVILITE(
    ID_T_CIVILITE,
    CODE_T_CIVILITE)
AS
select
  Civilite.ID_T_CIVILITE,
  Civilite.CODE_T_CIVILITE
  
  from TA_T_CIVILITE Civilite
^



/* View: V_T_DOC */
RECREATE VIEW V_T_DOC(
    ID_T_DOC,
    CODE_T_DOC,
    LIB_T_DOC,
    IP_ACCES)
AS
select
  TDoc.ID_T_DOC,
  TDoc.CODE_T_DOC,
  TDoc.LIB_T_DOC,
    TDoc.IP_ACCES

from TA_T_DOC TDoc
^



/* View: V_T_ENTITE */
RECREATE VIEW V_T_ENTITE(
    ID_T_ENTITE,
    CODE_T_ENTITE,
    LIBL_T_ENTITE)
AS
select
  Entite.ID_T_ENTITE,
  Entite.CODE_T_ENTITE,
  Entite.LIBL_T_ENTITE
  
  from TA_T_ENTITE Entite
^



/* View: V_T_LIGNE */
RECREATE VIEW V_T_LIGNE(
    ID_T_LIGNE,
    CODE_T_LIGNE,
    LIB_T_LIGNE,
    IP_ACCES)
AS
select
  TLigne.ID_T_LIGNE,
  TLigne.CODE_T_LIGNE,
  TLigne.LIB_T_LIGNE,
    TLigne.IP_ACCES

from TA_T_LIGNE TLigne
^



/* View: V_T_PAIEMENT */
RECREATE VIEW V_T_PAIEMENT(
    ID_T_PAIEMENT,
    CODE_T_PAIEMENT,
    LIB_T_PAIEMENT,
    IP_ACCES)
AS
select
  TPaiement.ID_T_PAIEMENT,
  TPaiement.CODE_T_PAIEMENT,
  TPaiement.LIB_T_PAIEMENT,
    TPaiement.IP_ACCES

from TA_T_PAIEMENT TPaiement
^



/* View: V_T_TEL */
RECREATE VIEW V_T_TEL(
    ID_T_TEL,
    CODE_T_TEL,
    LIBL_T_TEL)
AS
select
  TTel.ID_T_TEL,
  TTel.CODE_T_TEL,
  TTel.LIBL_T_TEL

  from TA_T_TEL TTel
^



/* View: V_T_TIERS */
RECREATE VIEW V_T_TIERS(
    ID_T_TIERS,
    CODE_T_TIERS,
    LIBELLE_T_TIERS,
    COMPTE_T_TIERS)
AS
select
  TTiers.ID_T_TIERS,
  TTiers.CODE_T_TIERS,
  TTiers.LIBELLE_T_TIERS ,
  TTiers.COMPTE_T_TIERS

  from TA_T_TIERS TTiers
^



/* View: V_T_TVA */
RECREATE VIEW V_T_TVA(
    ID_T_TVA,
    CODE_T_TVA,
    LIB_T_TVA)
AS
select
  TTva.ID_T_TVA,
  TTva.CODE_T_TVA,
  TTva.LIB_T_TVA

  from TA_T_TVA TTva
^



/* View: V_TELEPHONE */
RECREATE VIEW V_TELEPHONE(
    ID_TELEPHONE,
    NUMERO_TELEPHONE,
    POSTE_TELEPHONE,
    ID_I_TIERS,
    CODE_TIERS,
    NOM_TIERS,
    PRENOM_TIERS,
    ID_T_TEL,
    CODE_T_TEL,
    ID_TIERS)
AS
select
  Tel.ID_TELEPHONE,
  Tel.NUMERO_TELEPHONE,
  Tel.POSTE_TELEPHONE,
  Tel.ID_I_TIERS,
  Tiers.CODE_TIERS,
  Tiers.NOM_TIERS,
  Tiers.PRENOM_TIERS,
  Ttel.id_T_Tel,
  Ttel.CODE_T_TEL,
  Rtel.id_tiers

  from TA_TELEPHONE Tel
  LEFT OUTER join TA_TIERS Tiers on Tiers.ID_TIERS = Tel.ID_I_TIERS
  left Join TA_R_Tel_T_Tel RTelTTel on (RTelTTel.id_Telephone=Tel.id_Telephone)
  left Join TA_T_Tel Ttel on (Ttel.id_T_tel=RTelTTel.id_T_tel)
  left Join Ta_R_tel Rtel on (Rtel.id_telephone=tel.id_telephone)
^



/* View: V_TIERS */
RECREATE VIEW V_TIERS(
    ID_TIERS,
    CODE_TIERS,
    CODE_COMPTA,
    COMPTE,
    NOM_TIERS,
    PRENOM_TIERS,
    SURNOM_TIERS,
    ACTIF_TIERS,
    TTC_TIERS,
    ID_T_CIVILITE,
    CODE_T_CIVILITE,
    ID_ENTREPRISE,
    CODE_ENTREPRISE,
    LIBL_ENTREPRISE,
    ID_T_ENTITE_ENTREPRISE,
    CODE_T_ENTITE_ENTREPRISE,
    ID_T_TIERS,
    CODE_T_TIERS,
    LIBELLE_T_TIERS,
    ID_COMMENTAIRE,
    LIBL_COMMENTAIRE,
    ID_I_BANQUE,
    CODE_BANQUE,
    LIBC_BANQUE,
    LIBL_BANQUE,
    ID_T_ENTITE,
    CODE_T_ENTITE,
    TVA_I_COM_COMPL,
    SIRET_COMPL)
AS
select
  Tiers.ID_TIERS,
  Tiers.CODE_TIERS,
  Tiers.CODE_COMPTA,
  Tiers.COMPTE,
  Tiers.NOM_TIERS,
  Tiers.PRENOM_TIERS,
  Tiers.SURNOM_TIERS,
  Tiers.ACTIF_TIERS,
  Tiers.TTC_TIERS,
  Tiers.ID_T_CIVILITE,
  Civilite.CODE_T_CIVILITE,
  Tiers.ID_ENTREPRISE,
  Entreprise.CODE_ENTREPRISE,
  Entreprise.LIBL_ENTREPRISE,
  Entreprise.id_t_entite,
  Entite_entreprise.code_t_entite,
  Tiers.ID_T_TIERS,
  TTiers.CODE_T_TIERS,
  TTiers.LIBELLE_T_TIERS,
  Com.ID_COMMENTAIRE,
  Com.LIBL_COMMENTAIRE,
  Tiers.ID_I_BANQUE,
  Banque.CODE_BANQUE,
  Banque.LIBC_BANQUE,
  Banque.LIBL_BANQUE,
  Entite.ID_T_ENTITE,
  Entite.CODE_T_ENTITE,
  compl.tva_i_com_compl,
  compl.siret_compl
  
  from TA_TIERS Tiers
  
  LEFT OUTER join TA_T_ENTITE Entite on Tiers.ID_T_ENTITE = Entite.ID_T_ENTITE
  LEFT OUTER join TA_T_CIVILITE Civilite on Tiers.ID_T_CIVILITE = Civilite.ID_T_CIVILITE
  LEFT OUTER join TA_ENTREPRISE Entreprise on Tiers.ID_ENTREPRISE = Entreprise.ID_ENTREPRISE
  LEFT OUTER join TA_T_ENTITE Entite_entreprise on Entreprise.id_t_entite = Entite_entreprise.id_t_entite
  LEFT OUTER join TA_T_TIERS TTiers on Tiers.ID_T_TIERS = TTiers.ID_T_TIERS
  LEFT OUTER join TA_COMMENTAIRE Com on Tiers.ID_TIERS = Com.ID_TIERS
  LEFT OUTER join TA_BANQUE Banque on Tiers.ID_I_BANQUE = Banque.ID_BANQUE
  LEFT OUTER join ta_compl compl on compl.id_tiers = Tiers.id_tiers
^


/* View: V_TIERS_COMMERCIAL */
RECREATE VIEW V_TIERS_COMMERCIAL(
    ID_TIERS,
    CODE_TIERS,
    CODE_COMPTA,
    COMPTE,
    NOM_TIERS,
    PRENOM_TIERS,
    SURNOM_TIERS,
    ACTIF_TIERS,
    TTC_TIERS,
    ID_T_CIVILITE,
    CODE_T_CIVILITE,
    ID_ENTREPRISE,
    CODE_ENTREPRISE,
    LIBL_ENTREPRISE,
    ID_T_TIERS,
    CODE_T_TIERS,
    LIBELLE_T_TIERS,
    ID_COMMENTAIRE,
    LIBL_COMMENTAIRE,
    ID_I_BANQUE,
    CODE_BANQUE,
    LIBC_BANQUE,
    LIBL_BANQUE,
    ID_T_ENTITE,
    CODE_T_ENTITE,
    TVA_I_COM_COMPL,
    SIRET_COMPL)
AS
select Tiers.ID_TIERS, Tiers.CODE_TIERS,
Tiers.CODE_COMPTA, Tiers.COMPTE, Tiers.NOM_TIERS,
Tiers.PRENOM_TIERS, Tiers.SURNOM_TIERS, Tiers.ACTIF_TIERS,
Tiers.TTC_TIERS, Tiers.ID_T_CIVILITE, Civilite.CODE_T_CIVILITE,
Tiers.ID_ENTREPRISE, Entreprise.CODE_ENTREPRISE, Entreprise.LIBL_ENTREPRISE,
Tiers.ID_T_TIERS, TTiers.CODE_T_TIERS, TTiers.LIBELLE_T_TIERS, Com.ID_COMMENTAIRE,
Com.LIBL_COMMENTAIRE, Tiers.ID_I_BANQUE, Banque.CODE_BANQUE, Banque.LIBC_BANQUE,
Banque.LIBL_BANQUE, Entite.ID_T_ENTITE, Entite.CODE_T_ENTITE, compl.tva_i_com_compl, compl.siret_compl
from TA_TIERS Tiers  LEFT OUTER join TA_T_ENTITE Entite on Tiers.ID_T_ENTITE = Entite.ID_T_ENTITE
LEFT OUTER join TA_T_CIVILITE Civilite on Tiers.ID_T_CIVILITE = Civilite.ID_T_CIVILITE
LEFT OUTER join TA_ENTREPRISE Entreprise on Tiers.ID_ENTREPRISE = Entreprise.ID_ENTREPRISE
LEFT OUTER join TA_T_TIERS TTiers on Tiers.ID_T_TIERS = TTiers.ID_T_TIERS
LEFT OUTER join TA_COMMENTAIRE Com on Tiers.ID_TIERS = Com.ID_TIERS
LEFT OUTER join TA_BANQUE Banque on Tiers.ID_I_BANQUE = Banque.ID_BANQUE
LEFT OUTER join ta_compl compl on compl.id_tiers = Tiers.id_tiers
where  upper(TTiers.Code_T_TIERS) = 'COM'
^



/* View: V_TVA */
RECREATE VIEW V_TVA(
    ID_TVA,
    CODE_TVA,
    LIBELLE_TVA,
    TAUX_TVA,
    NUMCPT_TVA)
AS
select
  Tva.ID_TVA,
  Tva.CODE_TVA,
  Tva.LIBELLE_TVA,
  Tva.TAUX_TVA,
  Tva.NUMCPT_TVA

  from TA_TVA Tva
^



/* View: V_UNITE */
RECREATE VIEW V_UNITE(
    ID_UNITE,
    CODE_UNITE,
    LIBL_UNITE)
AS
select
  Unite.ID_UNITE,
  Unite.CODE_UNITE,
  Unite.LIBL_UNITE
      
  from TA_UNITE Unite
^



/* View: V_WEB */
RECREATE VIEW V_WEB(
    ID_WEB,
    ADRESSE_WEB,
    CODE_TIERS,
    ID_TIERS)
AS
select
  Web.ID_WEB,
  Web.ADRESSE_WEB,
  Tiers.code_tiers,
  Rweb.id_tiers

  from TA_WEB Web
  left join ta_R_Web RWeb on (RWeb.id_web=Web.id_web)
  left join ta_tiers Tiers on (Tiers.id_tiers=Rweb.id_tiers)
^



/* View: V2_BONLIV */
RECREATE VIEW V2_BONLIV(
    ID_BONLIV,
    CODE_BONLIV,
    DATE_BONLIV,
    DATE_LIV_BONLIV,
    LIBELLE_BONLIV,
    CODE_TIERS,
    NOM_TIERS,
    CODE_COMPTA,
    COMPTE,
    TTC,
    NB_E_BONLIV,
    COMMENTAIRE)
AS
select
bonLiv.ID_BONLIV,
bonLiv.CODE_BONLIV,
bonLiv.DATE_BONLIV,
bonLiv.DATE_LIV_BONLIV,
bonLiv.LIBELLE_BONLIV,
Tiers.code_tiers,
infos.NOM_TIERS,
infos.CODE_COMPTA,
infos.COMPTE,
bonLiv.ttc,
bonLiv.nb_e_BONLIV,
bonliv.commentaire
from TA_BONLIV bonLiv
left join ta_tiers Tiers on (Tiers.id_tiers = bonLiv.id_tiers)
left join ta_infos_BONLIV infos on (infos.id_BONLIV = bonLiv.id_BONLIV)
^



/* View: V2_DEVIS */
RECREATE VIEW V2_DEVIS(
    ID_DEVIS,
    CODE_DEVIS,
    DATE_DEVIS,
    DATE_ECH_DEVIS,
    DATE_LIV_DEVIS,
    LIBELLE_DEVIS,
    CODE_TIERS,
    NOM_TIERS,
    CODE_COMPTA,
    COMPTE,
    TTC,
    NB_E_DEVIS,
    EXPORT,
    COMMENTAIRE)
AS
select
Devis.ID_DEVIS,
Devis.CODE_DEVIS,
Devis.DATE_DEVIS,
Devis.DATE_ECH_DEVIS,
Devis.DATE_LIV_DEVIS,
Devis.LIBELLE_DEVIS,
Tiers.code_tiers,
infos.NOM_TIERS,
infos.CODE_COMPTA,
infos.COMPTE,
Devis.ttc,
Devis.nb_e_DEVIS,
Devis.export,
Devis.COMMENTAIRE


from TA_DEVIS Devis
left join ta_tiers Tiers on (Tiers.id_tiers = Devis.id_tiers)
left join ta_infos_devis infos on (infos.id_devis = devis.id_devis)
^



/* View: V2_FACTURE */
RECREATE VIEW V2_FACTURE(
    ID_FACTURE,
    CODE_FACTURE,
    DATE_FACTURE,
    DATE_ECH_FACTURE,
    DATE_LIV_FACTURE,
    LIBELLE_FACTURE,
    CODE_TIERS,
    NOM_TIERS,
    CODE_COMPTA,
    COMPTE,
    REGLE_FACTURE,
    TTC,
    NB_E_FACTURE,
    EXPORT,
    COMMENTAIRE)
AS
select
Facture.ID_FACTURE,
Facture.CODE_FACTURE,
Facture.DATE_FACTURE,
Facture.DATE_ECH_FACTURE,
Facture.DATE_LIV_FACTURE,
Facture.LIBELLE_FACTURE,
Tiers.code_tiers,
infos.NOM_TIERS,
infos.CODE_COMPTA,
infos.COMPTE,
Facture.REGLE_FACTURE,
Facture.ttc,
Facture.nb_e_facture,
Facture.export,
Facture.COMMENTAIRE


from TA_FACTURE Facture
left join ta_tiers Tiers on (Tiers.id_tiers = Facture.id_tiers)
left join ta_infos_facture infos on (infos.id_facture = facture.id_facture)
^

