/* Trigger: TA_BONLIV_AIU0 */
CREATE TRIGGER TA_BONLIV_AIU0 FOR TA_BONLIV
INACTIVE AFTER INSERT OR UPDATE POSITION 0
AS
begin
  execute procedure annule_modification('ta_BONLIV','code_BONLIV',new.code_BONLIV);
end
^

/* Trigger: TA_BONLIV_AU0 */
CREATE TRIGGER TA_BONLIV_AU0 FOR TA_BONLIV
INACTIVE AFTER UPDATE POSITION 0
AS
begin
execute procedure maj_lignes_BONLIV(old.id_BONLIV,new.ttc);
end
^

/* Trigger: TA_DEVIS_AIU0 */
CREATE TRIGGER TA_DEVIS_AIU0 FOR TA_DEVIS
INACTIVE AFTER INSERT OR UPDATE POSITION 0
AS
begin
  execute procedure annule_modification('ta_devis','code_devis',new.code_devis);
end
^

/* Trigger: TA_DEVIS_AU0 */
CREATE TRIGGER TA_DEVIS_AU0 FOR TA_DEVIS
INACTIVE AFTER UPDATE POSITION 0
AS
begin
execute procedure maj_lignes_devis(old.id_devis,new.ttc);
end
^

/* Trigger: TA_ENTREPRISE_AIU0 */
CREATE TRIGGER TA_ENTREPRISE_AIU0 FOR TA_ENTREPRISE
INACTIVE AFTER INSERT OR UPDATE POSITION 0
AS
begin
  execute procedure annule_modification('ta_entreprise','NOM_entreprise',new.NOM_entreprise);
end
^

/* Trigger: TA_FACTURE_AIU0 */
CREATE TRIGGER TA_FACTURE_AIU0 FOR TA_FACTURE
INACTIVE AFTER INSERT OR UPDATE POSITION 0
AS
begin
  execute procedure annule_modification('ta_facture','code_facture',new.code_facture);
end
^

/* Trigger: TA_FACTURE_AU0 */
CREATE TRIGGER TA_FACTURE_AU0 FOR TA_FACTURE
INACTIVE AFTER UPDATE POSITION 0
AS
begin
execute procedure maj_lignes_facture(old.id_facture,new.ttc);
end
^

/* Trigger: TA_FAMILLE_AIU0 */
CREATE TRIGGER TA_FAMILLE_AIU0 FOR TA_FAMILLE
INACTIVE AFTER INSERT OR UPDATE POSITION 0
AS
begin
  execute procedure annule_modification('ta_famille','code_famille',new.code_famille);
end
^

/* Trigger: TA_TIERS_AI0 */
CREATE TRIGGER TA_TIERS_AI0 FOR TA_TIERS
INACTIVE AFTER INSERT OR UPDATE POSITION 0
AS
begin
  execute procedure annule_modification('ta_tiers','code_tiers',new.code_tiers);
end
^

/* Trigger: TA_T_CIVILITE_AI0 */
CREATE TRIGGER TA_T_CIVILITE_AI0 FOR TA_T_CIVILITE
INACTIVE AFTER INSERT OR UPDATE POSITION 0
AS
begin
  execute procedure annule_modification('ta_t_civilite','code_t_civilite',new.code_t_civilite);
end
^

/* Trigger: TA_T_DOC_AIU0 */
CREATE TRIGGER TA_T_DOC_AIU0 FOR TA_T_DOC
INACTIVE AFTER INSERT OR UPDATE POSITION 0
AS
begin
  execute procedure annule_modification('ta_t_doc','code_t_doc',new.code_t_doc);
end
^

/* Trigger: TA_T_ENTITE_AIU0 */
CREATE TRIGGER TA_T_ENTITE_AIU0 FOR TA_T_ENTITE
INACTIVE AFTER INSERT OR UPDATE POSITION 0
AS
begin
  execute procedure annule_modification('ta_t_entite','code_t_entite',new.code_t_entite);
end
^

/* Trigger: TA_T_LIGNE_AIU0 */
CREATE TRIGGER TA_T_LIGNE_AIU0 FOR TA_T_LIGNE
INACTIVE AFTER INSERT OR UPDATE POSITION 0
AS
begin
  execute procedure annule_modification('ta_t_ligne','code_t_ligne',new.code_t_ligne);
end
^

/* Trigger: TA_T_PAIEMENT_AI0 */
CREATE TRIGGER TA_T_PAIEMENT_AI0 FOR TA_T_PAIEMENT
INACTIVE AFTER INSERT OR UPDATE POSITION 0
AS
begin
  execute procedure annule_modification('ta_t_paiement','code_t_paiement',new.code_t_paiement);
end
^

/* Trigger: TA_T_TEL_AIU0 */
CREATE TRIGGER TA_T_TEL_AIU0 FOR TA_T_TEL
INACTIVE AFTER INSERT OR UPDATE POSITION 0
AS
begin
  execute procedure annule_modification('ta_t_tel','code_t_tel',new.code_t_tel);
end
^

/* Trigger: TA_T_TIERS_AIU0 */
CREATE TRIGGER TA_T_TIERS_AIU0 FOR TA_T_TIERS
INACTIVE AFTER INSERT OR UPDATE POSITION 0
AS
begin
  execute procedure annule_modification('ta_t_tiers','code_t_tiers',new.code_t_tiers);
end
^


/* Trigger: TA_T_TVA_AIU0 */
CREATE TRIGGER TA_T_TVA_AIU0 FOR TA_T_TVA
INACTIVE AFTER INSERT OR UPDATE POSITION 0
AS
begin
  execute procedure annule_modification('ta_t_tva','code_t_tva',new.code_t_tva);
end
^


/* Trigger: TA_UNITE_AIU0 */
CREATE TRIGGER TA_UNITE_AIU0 FOR TA_UNITE
INACTIVE AFTER INSERT OR UPDATE POSITION 0
AS
begin
  execute procedure annule_modification('ta_unite','code_unite',new.code_unite);
end
^


/* Trigger: TBIID_ADRESSE_BONLIV */
CREATE TRIGGER TBIID_ADRESSE_BONLIV FOR TA_BONLIV
INACTIVE BEFORE INSERT POSITION 1
as
DECLARE variable numrows integer;
begin
  /* E_XAKID_ADRESSE */
   if (NEW.ID_ADRESSE is not null) then
       begin
       select count(*) from TA_ADRESSE
       where ID_ADRESSE = NEW.ID_ADRESSE
       into :numrows;
       if (numrows = 0) then
          exception E_XAKID_ADRESSE;
       end   
end
^

/* Trigger: TBIID_ADRESSE_DEVIS */
CREATE TRIGGER TBIID_ADRESSE_DEVIS FOR TA_DEVIS
INACTIVE BEFORE INSERT POSITION 1
as
DECLARE variable numrows integer;
begin
  /* E_XAKID_ADRESSE */
   if (NEW.ID_ADRESSE is not null) then
       begin
       select count(*) from TA_ADRESSE
       where ID_ADRESSE = NEW.ID_ADRESSE
       into :numrows;
       if (numrows = 0) then
          exception E_XAKID_ADRESSE;
       end   
end
^


/* Trigger: TBIID_ADRESSE_FACTURE */
CREATE TRIGGER TBIID_ADRESSE_FACTURE FOR TA_FACTURE
INACTIVE BEFORE INSERT POSITION 1
as
DECLARE variable numrows integer;
begin
  /* E_XAKID_ADRESSE */
   if (NEW.ID_ADRESSE is not null) then
       begin
       select count(*) from TA_ADRESSE
       where ID_ADRESSE = NEW.ID_ADRESSE
       into :numrows;
       if (numrows = 0) then
          exception E_XAKID_ADRESSE;
       end   
end
^



/* Trigger: TBIID_ADRESSE_LIV_BONLIV */
CREATE TRIGGER TBIID_ADRESSE_LIV_BONLIV FOR TA_BONLIV
INACTIVE BEFORE INSERT POSITION 1
as
DECLARE variable numrows integer;
begin
  /* E_XAKID_ADRESSE_L */
   if (NEW.ID_ADRESSE_LIV is not null) then
       begin
       select count(*) from TA_ADRESSE
       where ID_ADRESSE= NEW.ID_ADRESSE_LIV
       into :numrows;
       if (numrows = 0) then
          exception E_XAKID_ADRESSE_L;
       end   
end
^


/* Trigger: TBIID_ADRESSE_LIV_DEVIS */
CREATE TRIGGER TBIID_ADRESSE_LIV_DEVIS FOR TA_DEVIS
INACTIVE BEFORE INSERT POSITION 1
as
DECLARE variable numrows integer;
begin
  /* E_XAKID_ADRESSE_L */
   if (NEW.ID_ADRESSE_LIV is not null) then
       begin
       select count(*) from TA_ADRESSE
       where ID_ADRESSE= NEW.ID_ADRESSE_LIV
       into :numrows;
       if (numrows = 0) then
          exception E_XAKID_ADRESSE_L;
       end   
end
^


/* Trigger: TBIID_ADRESSE_LIV_FACTURE */
CREATE TRIGGER TBIID_ADRESSE_LIV_FACTURE FOR TA_FACTURE
INACTIVE BEFORE INSERT POSITION 1
as
DECLARE variable numrows integer;
begin
  /* E_XAKID_ADRESSE_L */
   if (NEW.ID_ADRESSE_LIV is not null) then
       begin
       select count(*) from TA_ADRESSE
       where ID_ADRESSE= NEW.ID_ADRESSE_LIV
       into :numrows;
       if (numrows = 0) then
          exception E_XAKID_ADRESSE_L;
       end   
end
^


/* Trigger: TBIID_ENTREPRISE_TA_TIERS */
CREATE TRIGGER TBIID_ENTREPRISE_TA_TIERS FOR TA_TIERS
INACTIVE BEFORE INSERT POSITION 1
as
DECLARE variable numrows integer;
begin
  /* XAKID_ENTREPRISE */
   if (NEW.ID_ENTREPRISE is not null) then
       begin
       select count(*) from TA_ENTREPRISE
       where ID_ENTREPRISE = NEW.ID_ENTREPRISE
       into :numrows;
       if (numrows = 0) then
          exception E_XAKID_ENTREPRISE;
       end   
end
^


/* Trigger: TBIID_T_CIVILITE_TA_TIERS */
CREATE TRIGGER TBIID_T_CIVILITE_TA_TIERS FOR TA_TIERS
INACTIVE BEFORE INSERT POSITION 1
as
DECLARE variable numrows integer;
begin
  /* XAKID_T_CIVILITE */
   if (NEW.ID_T_CIVILITE is not null) then
       begin
       select count(*) from TA_T_CIVILITE
       where ID_T_CIVILITE = NEW.ID_T_CIVILITE
       into :numrows;
       if (numrows = 0) then
          exception E_XAKID_T_CIVILITE;
       end   
end
^


/* Trigger: TBIID_T_TIERS_TA_TIERS */
CREATE TRIGGER TBIID_T_TIERS_TA_TIERS FOR TA_TIERS
INACTIVE BEFORE INSERT POSITION 1
as
DECLARE variable numrows integer;
begin
  /* XAKID_T_TIERS */
   if (NEW.ID_T_TIERS is not null) then
     begin
    select count(*) from TA_T_TIERS
    where ID_T_TIERS = NEW.ID_T_TIERS
    into :numrows;
    if (numrows = 0) then
       exception E_XAKID_T_TIERS;
     end    
end
^


/* Trigger: TBI_XAKCODE_ENTREPRISE */
CREATE TRIGGER TBI_XAKCODE_ENTREPRISE FOR TA_ENTREPRISE
INACTIVE BEFORE INSERT POSITION 1
as
DECLARE variable numrows integer;
begin
   select count(*) from TA_ENTREPRISE
   where NOM_ENTREPRISE = NEW.NOM_ENTREPRISE
   into :numrows;
   if (numrows > 0) then
      exception E_XAKNOM_ENTREPRISE ;
end
^


/* Trigger: TBI_XAKCODE_TIERS */
CREATE TRIGGER TBI_XAKCODE_TIERS FOR TA_TIERS
INACTIVE BEFORE INSERT POSITION 1
as
DECLARE variable numrows integer;
begin
  /* XAKCODE_TIERS */
   select count(*) from TA_TIERS
   where CODE_TIERS = NEW.CODE_TIERS
   into :numrows;
   if (numrows > 0) then
      exception E_XAKCODE_TIERS;
end
^


/* Trigger: TBI_XAKCODE_TVA */
CREATE TRIGGER TBI_XAKCODE_TVA FOR TA_TVA
INACTIVE BEFORE INSERT POSITION 0
as
DECLARE variable numrows integer;
begin
  /* XAKCODE_TVA */
   select count(*) from TA_TVA
   where CODE_TVA = NEW.CODE_TVA
   into :numrows;
   if (numrows > 0) then
      exception E_XAKCODE_TVA;
end
^


/* Trigger: TBI_XAKCODE_T_ADR */
CREATE TRIGGER TBI_XAKCODE_T_ADR FOR TA_T_ADR
INACTIVE BEFORE INSERT POSITION 1
as
DECLARE variable numrows integer;
begin
  /* E_XAKCODE_T_ADR  */
   select count(*) from TA_T_ADR
   where CODE_T_ADR = NEW.CODE_T_ADR
   into :numrows;
   if (numrows > 0) then
      exception E_XAKCODE_T_ADR ;
end
^


/* Trigger: TBI_XAKCODE_T_BANQUE */
CREATE TRIGGER TBI_XAKCODE_T_BANQUE FOR TA_T_BANQUE
INACTIVE BEFORE INSERT POSITION 1
as
DECLARE variable numrows integer;
begin
  /* E_XAKCODE_T_BANQUE  */
   select count(*) from TA_T_BANQUE
   where CODE_T_BANQUE = NEW.CODE_T_BANQUE
   into :numrows;
   if (numrows > 0) then
      exception E_XAKCODE_T_BANQUE ;
end
^


/* Trigger: TBI_XAKCODE_T_CIVILITE */
CREATE TRIGGER TBI_XAKCODE_T_CIVILITE FOR TA_T_CIVILITE
INACTIVE BEFORE INSERT POSITION 1
as
DECLARE variable numrows integer;
begin
  /* E_XAKCODE_T_CIVILITE  */   
   select count(*) from TA_T_CIVILITE
   where CODE_T_CIVILITE = NEW.CODE_T_CIVILITE
   into :numrows;
   if (numrows > 0) then
      exception E_XAKCODE_T_CIVILITE ;
end
^


/* Trigger: TBI_XAKCODE_T_DOC */
CREATE TRIGGER TBI_XAKCODE_T_DOC FOR TA_T_DOC
INACTIVE BEFORE INSERT POSITION 0
as
DECLARE variable numrows integer;
begin
  /* XAKCODE_T_DOC */
   select count(*) from TA_T_DOC
   where CODE_T_DOC = NEW.CODE_T_DOC
   into :numrows;
   if (numrows > 0) then
      exception E_XAKCODE_T_DOC;
end
^


/* Trigger: TBI_XAKCODE_T_EMAIL */
CREATE TRIGGER TBI_XAKCODE_T_EMAIL FOR TA_T_EMAIL
INACTIVE BEFORE INSERT POSITION 1
as
DECLARE variable numrows integer;
begin
  /* E_XAKCODE_T_EMAIL  */
   select count(*) from TA_T_EMAIL
   where CODE_T_EMAIL = NEW.CODE_T_EMAIL
   into :numrows;
   if (numrows > 0) then
      exception E_XAKCODE_T_EMAIL ;
end
^


/* Trigger: TBI_XAKCODE_T_ENTITE */
CREATE TRIGGER TBI_XAKCODE_T_ENTITE FOR TA_T_ENTITE
INACTIVE BEFORE INSERT POSITION 1
as
DECLARE variable numrows integer;
begin
  /* E_XAKCODE_T_ENTITE  */
   select count(*) from TA_T_ENTITE
   where CODE_T_ENTITE = NEW.CODE_T_ENTITE
   into :numrows;
   if (numrows > 0) then
      exception E_XAKCODE_T_ENTITE ;
end
^


/* Trigger: TBI_XAKCODE_T_LIENS */
CREATE TRIGGER TBI_XAKCODE_T_LIENS FOR TA_T_LIENS
INACTIVE BEFORE INSERT POSITION 1
as
DECLARE variable numrows integer;
begin
  /* E_XAKCODE_T_LIENS  */
   select count(*) from TA_T_LIENS
   where CODE_T_LIENS = NEW.CODE_T_LIENS
   into :numrows;
   if (numrows > 0) then
      exception E_XAKCODE_T_LIENS ;
end
^


/* Trigger: TBI_XAKCODE_T_LIGNE */
CREATE TRIGGER TBI_XAKCODE_T_LIGNE FOR TA_T_LIGNE
INACTIVE BEFORE INSERT POSITION 0
as
DECLARE variable numrows integer;
begin
  /* XAKCODE_T_LIGNE */
   select count(*) from TA_T_LIGNE
   where CODE_T_LIGNE = NEW.CODE_T_LIGNE
   into :numrows;
   if (numrows > 0) then
      exception E_XAKCODE_T_LIGNE;
end
^


/* Trigger: TBI_XAKCODE_T_PAIEMENT */
CREATE TRIGGER TBI_XAKCODE_T_PAIEMENT FOR TA_T_PAIEMENT
INACTIVE BEFORE INSERT POSITION 0
as
DECLARE variable numrows integer;
begin
  /* XAKCODE_T_PAIEMENT */
   select count(*) from TA_T_PAIEMENT
   where CODE_T_PAIEMENT = NEW.CODE_T_PAIEMENT
   into :numrows;
   if (numrows > 0) then
      exception E_XAKCODE_T_PAIEMENT;
end
^


/* Trigger: TBI_XAKCODE_T_TARIF */
CREATE TRIGGER TBI_XAKCODE_T_TARIF FOR TA_T_TARIF
INACTIVE BEFORE INSERT POSITION 1
as
DECLARE variable numrows integer;
begin
  /* E_XAKCODE_T_TARIF  */
   select count(*) from TA_T_TARIF
   where CODE_T_TARIF = NEW.CODE_T_TARIF
   into :numrows;
   if (numrows > 0) then
      exception E_XAKCODE_T_TARIF ;
end
^


/* Trigger: TBI_XAKCODE_T_TEL */
CREATE TRIGGER TBI_XAKCODE_T_TEL FOR TA_T_TEL
INACTIVE BEFORE INSERT POSITION 1
as
DECLARE variable numrows integer;
begin
  /* E_XAKCODE_T_TEL  */
   select count(*) from TA_T_TEL
   where CODE_T_TEL = NEW.CODE_T_TEL
   into :numrows;
   if (numrows > 0) then
      exception E_XAKCODE_T_TEL ;
end
^


/* Trigger: TBI_XAKCODE_T_TIERS */
CREATE TRIGGER TBI_XAKCODE_T_TIERS FOR TA_T_TIERS
INACTIVE BEFORE INSERT POSITION 1
as
DECLARE variable numrows integer;
begin
  /* E_XAKCODE_T_TIERS  */
   select count(*) from TA_T_TIERS
   where CODE_T_TIERS = NEW.CODE_T_TIERS
   into :numrows;
   if (numrows > 0) then
      exception E_XAKCODE_T_TIERS ;
end
^


/* Trigger: TBI_XAKCODE_T_TVA */
CREATE TRIGGER TBI_XAKCODE_T_TVA FOR TA_T_TVA
INACTIVE BEFORE INSERT POSITION 0
as
DECLARE variable numrows integer;
begin
  /* XAKCODE_T_TVA  */
   select count(*) from TA_T_TVA
   where CODE_T_TVA = NEW.CODE_T_TVA
   into :numrows;
   if (numrows > 0) then
      exception XAKCODE_T_TVA;
end
^


/* Trigger: TBI_XAKCODE_T_WEB */
CREATE TRIGGER TBI_XAKCODE_T_WEB FOR TA_T_WEB
INACTIVE BEFORE INSERT POSITION 1
as
DECLARE variable numrows integer;
begin
  /* E_XAKCODE_T_WEB  */
   select count(*) from TA_T_WEB
   where CODE_T_WEB = NEW.CODE_T_WEB
   into :numrows;
   if (numrows > 0) then
      exception E_XAKCODE_T_WEB ;
end
^


/* Trigger: TBI_XAKCODE_UNITE */
CREATE TRIGGER TBI_XAKCODE_UNITE FOR TA_UNITE
INACTIVE BEFORE INSERT POSITION 0
as
DECLARE variable numrows integer;
begin
  /* XAKCODE_UNITE */
   select count(*) from TA_UNITE
   where CODE_UNITE = NEW.CODE_UNITE
   into :numrows;
   if (numrows > 0) then
      exception E_XAKCODE_UNITE;
end
^


/* Trigger: TBI_XAKCOMPTE_BANQUE */
CREATE TRIGGER TBI_XAKCOMPTE_BANQUE FOR TA_COMPTE_BANQUE
INACTIVE BEFORE INSERT POSITION 1
as
DECLARE variable numrows integer;
begin
  /* E_XAKCOmpte_BANQUE  */
   select count(*) from TA_COMPTE_BANQUE
   where COMPTE = NEW.COMPTE
   into :numrows;
   if (numrows > 0) then
      exception E_XAKCOMPTE_BANQUE ;
end
^


/* Trigger: TBUID_ADRESSE_BONLIV */
CREATE TRIGGER TBUID_ADRESSE_BONLIV FOR TA_BONLIV
INACTIVE BEFORE UPDATE POSITION 1
as
DECLARE variable numrows integer;
begin
  /* XAKID_ADRESSE */
   if ((OLD.ID_ADRESSE <> NEW.ID_ADRESSE)) then
   begin
     select count(*) from TA_ADRESSE
     where ID_ADRESSE = NEW.ID_ADRESSE
     into :numrows;
     if (numrows = 0) then
        exception E_XAKID_ADRESSE;
   end
end
^


/* Trigger: TBUID_ADRESSE_DEVIS */
CREATE TRIGGER TBUID_ADRESSE_DEVIS FOR TA_DEVIS
INACTIVE BEFORE UPDATE POSITION 1
as
DECLARE variable numrows integer;
begin
  /* XAKID_ADRESSE */
   if ((OLD.ID_ADRESSE <> NEW.ID_ADRESSE)) then
   begin
     select count(*) from TA_ADRESSE
     where ID_ADRESSE = NEW.ID_ADRESSE
     into :numrows;
     if (numrows = 0) then
        exception E_XAKID_ADRESSE;
   end
end
^


/* Trigger: TBUID_ADRESSE_FACTURE */
CREATE TRIGGER TBUID_ADRESSE_FACTURE FOR TA_FACTURE
INACTIVE BEFORE UPDATE POSITION 1
as
DECLARE variable numrows integer;
begin
  /* XAKID_ADRESSE */
   if ((OLD.ID_ADRESSE <> NEW.ID_ADRESSE)) then
   begin
     select count(*) from TA_ADRESSE
     where ID_ADRESSE = NEW.ID_ADRESSE
     into :numrows;
     if (numrows = 0) then
        exception E_XAKID_ADRESSE;
   end
end
^


/* Trigger: TBUID_ADRESSE_LIV_BONLIV */
CREATE TRIGGER TBUID_ADRESSE_LIV_BONLIV FOR TA_BONLIV
INACTIVE BEFORE UPDATE POSITION 1
as
DECLARE variable numrows integer;
begin
  /* E_XAKID_ADRESSE_L */
   if ((OLD.ID_ADRESSE_LIV <> NEW.ID_ADRESSE_LIV)) then
   begin
     select count(*) from TA_ADRESSE
     where ID_ADRESSE = NEW.ID_ADRESSE_LIV
     into :numrows;
     if (numrows = 0) then
        exception E_XAKID_ADRESSE_L;
   end
end
^


/* Trigger: TBUID_ADRESSE_LIV_DEVIS */
CREATE TRIGGER TBUID_ADRESSE_LIV_DEVIS FOR TA_DEVIS
INACTIVE BEFORE UPDATE POSITION 1
as
DECLARE variable numrows integer;
begin
  /* E_XAKID_ADRESSE_L */
   if ((OLD.ID_ADRESSE_LIV <> NEW.ID_ADRESSE_LIV)) then
   begin
     select count(*) from TA_ADRESSE
     where ID_ADRESSE = NEW.ID_ADRESSE_LIV
     into :numrows;
     if (numrows = 0) then
        exception E_XAKID_ADRESSE_L;
   end
end
^


/* Trigger: TBUID_ADRESSE_LIV_FACTURE */
CREATE TRIGGER TBUID_ADRESSE_LIV_FACTURE FOR TA_FACTURE
INACTIVE BEFORE UPDATE POSITION 1
as
DECLARE variable numrows integer;
begin
  /* E_XAKID_ADRESSE_L */
   if ((OLD.ID_ADRESSE_LIV <> NEW.ID_ADRESSE_LIV)) then
   begin
     select count(*) from TA_ADRESSE
     where ID_ADRESSE = NEW.ID_ADRESSE_LIV
     into :numrows;
     if (numrows = 0) then
        exception E_XAKID_ADRESSE_L;
   end
end
^


/* Trigger: TBUID_ENTREPRISE_TA_TIERS */
CREATE TRIGGER TBUID_ENTREPRISE_TA_TIERS FOR TA_TIERS
INACTIVE BEFORE UPDATE POSITION 1
as
DECLARE variable numrows integer;
begin
  /* XAKID_ENTREPRISE */
   if ((OLD.ID_ENTREPRISE <> NEW.ID_ENTREPRISE)) then
   begin
     select count(*) from TA_ENTREPRISE
     where ID_ENTREPRISE = NEW.ID_ENTREPRISE
     into :numrows;
     if (numrows = 0) then
        exception E_XAKID_ENTREPRISE;
   end
end
^


/* Trigger: TBUID_T_CIVILITE_TA_TIERS */
CREATE TRIGGER TBUID_T_CIVILITE_TA_TIERS FOR TA_TIERS
INACTIVE BEFORE UPDATE POSITION 1
as
DECLARE variable numrows integer;
begin
  /* XAKID_T_CIVILITE */
   if ((OLD.ID_T_CIVILITE <> NEW.ID_T_CIVILITE)) then
   begin
     select count(*) from TA_T_CIVILITE
     where ID_T_CIVILITE = NEW.ID_T_CIVILITE
     into :numrows;
     if (numrows = 0) then
        exception E_XAKID_T_CIVILITE;
   end
end
^


/* Trigger: TBUID_T_TIERS_TA_TIERS */
CREATE TRIGGER TBUID_T_TIERS_TA_TIERS FOR TA_TIERS
INACTIVE BEFORE UPDATE POSITION 1
as
DECLARE variable numrows integer;
begin
  /* XAKID_T_TIERS */
   if ((OLD.ID_T_TIERS <> NEW.ID_T_TIERS)) then
   begin
     select count(*) from TA_T_TIERS
     where ID_T_TIERS = NEW.ID_T_TIERS
     into :numrows;
     if (numrows = 0) then
        exception E_XAKID_T_TIERS;
   end
end
^


/* Trigger: TBU_XAKCODE_ENTREPRISE */
CREATE TRIGGER TBU_XAKCODE_ENTREPRISE FOR TA_ENTREPRISE
INACTIVE BEFORE UPDATE POSITION 1
as
DECLARE variable numrows integer;
begin
  /* E_XAKnom_ENTREPRISE  */
   If  ((OLD.nom_ENTREPRISE <> NEW.nom_ENTREPRISE)) then
   begin
     select count(*) from TA_ENTREPRISE
     where nom_ENTREPRISE = NEW.nom_ENTREPRISE
     into :numrows;
     if (numrows > 0) then
        exception e_xaknom_entreprise ;
   end
end
^


/* Trigger: TBU_XAKCODE_TIERS */
CREATE TRIGGER TBU_XAKCODE_TIERS FOR TA_TIERS
INACTIVE BEFORE UPDATE POSITION 1
as
DECLARE variable numrows integer;
begin
  /* XAKCODE_TIERS */
   if ((OLD.CODE_TIERS <> NEW.CODE_TIERS)) then
   begin
     select count(*) from TA_TIERS
     where CODE_TIERS = NEW.CODE_TIERS
     into :numrows;
     if (numrows > 0) then
        exception E_XAKCODE_TIERS;
   end
end
^


/* Trigger: TBU_XAKCODE_TVA */
CREATE TRIGGER TBU_XAKCODE_TVA FOR TA_TVA
INACTIVE BEFORE UPDATE POSITION 0
as
DECLARE variable numrows integer;
begin
  /* XAKCODE_TVA */
   if ((OLD.CODE_TVA <> NEW.CODE_TVA)) then
   begin
   select count(*) from TA_TVA
   where CODE_TVA = NEW.CODE_TVA
   into :numrows;
   if (numrows > 0) then
      exception E_XAKCODE_TVA;
   end
end
^


/* Trigger: TBU_XAKCODE_T_ADR */
CREATE TRIGGER TBU_XAKCODE_T_ADR FOR TA_T_ADR
INACTIVE BEFORE UPDATE POSITION 1
as
DECLARE variable numrows integer;
begin
  /* E_XAKCODE_T_ADR  */
   If  ((OLD.ID_T_ADR <> NEW.ID_T_ADR)) then
   begin
     select count(*) from TA_T_ADR
     where CODE_T_ADR = NEW.CODE_T_ADR
     into :numrows;
     if (numrows > 0) then
        exception E_XAKCODE_T_ADR ;
   end
end
^


/* Trigger: TBU_XAKCODE_T_BANQUE */
CREATE TRIGGER TBU_XAKCODE_T_BANQUE FOR TA_T_BANQUE
INACTIVE BEFORE UPDATE POSITION 1
as
DECLARE variable numrows integer;
begin
  /* E_XAKCODE_T_BANQUE  */
   If  ((OLD.ID_T_BANQUE <> NEW.ID_T_BANQUE)) then
   begin
     select count(*) from TA_T_BANQUE
     where CODE_T_BANQUE = NEW.CODE_T_BANQUE
     into :numrows;
     if (numrows > 0) then
        exception E_XAKCODE_T_BANQUE ;
   end
end
^


/* Trigger: TBU_XAKCODE_T_CIVILITE */
CREATE TRIGGER TBU_XAKCODE_T_CIVILITE FOR TA_T_CIVILITE
INACTIVE BEFORE UPDATE POSITION 1
as
DECLARE variable numrows integer;
begin
  /* E_XAKCODE_T_CIVILITE  */
   If  ((OLD.CODE_T_CIVILITE <> NEW.CODE_T_CIVILITE)) then
   begin
     select count(*) from TA_T_CIVILITE
     where CODE_T_CIVILITE = NEW.CODE_T_CIVILITE
     into :numrows;
     if (numrows > 0) then
        exception E_XAKCODE_T_CIVILITE ;
   end
end
^


/* Trigger: TBU_XAKCODE_T_DOC */
CREATE TRIGGER TBU_XAKCODE_T_DOC FOR TA_T_DOC
INACTIVE BEFORE UPDATE POSITION 0
as
DECLARE variable numrows integer;
begin
  /* XAKCODE_T_DOC */
   if ((OLD.CODE_T_DOC <> NEW.CODE_T_DOC)) then
   begin
   select count(*) from TA_T_DOC
   where CODE_T_DOC = NEW.CODE_T_DOC
   into :numrows;
   if (numrows > 0) then
      exception E_XAKCODE_T_DOC;
   end
end
^


/* Trigger: TBU_XAKCODE_T_EMAIL */
CREATE TRIGGER TBU_XAKCODE_T_EMAIL FOR TA_T_EMAIL
INACTIVE BEFORE UPDATE POSITION 1
as
DECLARE variable numrows integer;
begin
  /* E_XAKCODE_T_EMAIL  */
   If  ((OLD.ID_T_EMAIL <> NEW.ID_T_EMAIL)) then
   begin
     select count(*) from TA_T_EMAIL
     where CODE_T_EMAIL = NEW.CODE_T_EMAIL
     into :numrows;
     if (numrows > 0) then
        exception E_XAKCODE_T_EMAIL ;
   end
end
^


/* Trigger: TBU_XAKCODE_T_ENTITE */
CREATE TRIGGER TBU_XAKCODE_T_ENTITE FOR TA_T_ENTITE
INACTIVE BEFORE UPDATE POSITION 1
as
DECLARE variable numrows integer;
begin
  /* E_XAKCODE_T_ENTITE  */
   If  ((OLD.CODE_T_ENTITE <> NEW.CODE_T_ENTITE)) then
   begin
     select count(*) from TA_T_ENTITE
     where CODE_T_ENTITE = NEW.CODE_T_ENTITE
     into :numrows;
     if (numrows > 0) then
        exception E_XAKCODE_T_ENTITE ;
   end
end
^


/* Trigger: TBU_XAKCODE_T_LIENS */
CREATE TRIGGER TBU_XAKCODE_T_LIENS FOR TA_T_LIENS
INACTIVE BEFORE UPDATE POSITION 1
as
DECLARE variable numrows integer;
begin
  /* E_XAKCODE_T_LIENS  */
   If  ((OLD.ID_T_LIENS <> NEW.ID_T_LIENS)) then
   begin
     select count(*) from TA_T_LIENS
     where CODE_T_LIENS = NEW.CODE_T_LIENS
     into :numrows;
     if (numrows > 0) then
        exception E_XAKCODE_T_LIENS ;
   end
end
^


/* Trigger: TBU_XAKCODE_T_LIGNE */
CREATE TRIGGER TBU_XAKCODE_T_LIGNE FOR TA_T_LIGNE
INACTIVE BEFORE UPDATE POSITION 0
as
DECLARE variable numrows integer;
begin
  /* XAKCODE_T_LIGNE */
   if ((OLD.CODE_T_LIGNE <> NEW.CODE_T_LIGNE)) then
   begin
   select count(*) from TA_T_LIGNE
   where CODE_T_LIGNE = NEW.CODE_T_LIGNE
   into :numrows;
   if (numrows > 0) then
      exception E_XAKCODE_T_LIGNE;
   end
end
^


/* Trigger: TBU_XAKCODE_T_PAIEMENT */
CREATE TRIGGER TBU_XAKCODE_T_PAIEMENT FOR TA_T_PAIEMENT
INACTIVE BEFORE UPDATE POSITION 0
as
DECLARE variable numrows integer;
begin
  /* XAKCODE_T_PAIEMENT */
   if ((OLD.CODE_T_PAIEMENT <> NEW.CODE_T_PAIEMENT)) then
   begin
   select count(*) from TA_T_PAIEMENT
   where CODE_T_PAIEMENT = NEW.CODE_T_PAIEMENT
   into :numrows;
   if (numrows > 0) then
      exception E_XAKCODE_T_PAIEMENT;
   end
end
^


/* Trigger: TBU_XAKCODE_T_TARIF */
CREATE TRIGGER TBU_XAKCODE_T_TARIF FOR TA_T_TARIF
INACTIVE BEFORE UPDATE POSITION 1
as
DECLARE variable numrows integer;
begin
  /* E_XAKCODE_T_TARIF  */
   If  ((OLD.ID_T_TARIF <> NEW.ID_T_TARIF)) then
   begin
     select count(*) from TA_T_TARIF
     where CODE_T_TARIF = NEW.CODE_T_TARIF
     into :numrows;
     if (numrows > 0) then
        exception E_XAKCODE_T_TARIF ;
   end
end
^


/* Trigger: TBU_XAKCODE_T_TEL */
CREATE TRIGGER TBU_XAKCODE_T_TEL FOR TA_T_TEL
INACTIVE BEFORE UPDATE POSITION 1
as
DECLARE variable numrows integer;
begin
  /* E_XAKCODE_T_TEL  */
   If  ((OLD.ID_T_TEL <> NEW.ID_T_TEL)) then
   begin
     select count(*) from TA_T_TEL
     where CODE_T_TEL = NEW.CODE_T_TEL
     into :numrows;
     if (numrows > 0) then
        exception E_XAKCODE_T_TEL ;
   end
end
^


/* Trigger: TBU_XAKCODE_T_TIERS */
CREATE TRIGGER TBU_XAKCODE_T_TIERS FOR TA_T_TIERS
INACTIVE BEFORE UPDATE POSITION 1
as
DECLARE variable numrows integer;
begin
  /* E_XAKCODE_T_TIERS  */
   If  ((OLD.ID_T_TIERS <> NEW.ID_T_TIERS)) then
   begin
     select count(*) from TA_T_TIERS
     where CODE_T_TIERS = NEW.CODE_T_TIERS
     into :numrows;
     if (numrows > 0) then
        exception E_XAKCODE_T_TIERS ;
   end
end
^


/* Trigger: TBU_XAKCODE_T_TVA */
CREATE TRIGGER TBU_XAKCODE_T_TVA FOR TA_T_TVA
INACTIVE BEFORE UPDATE POSITION 0
as
DECLARE variable numrows integer;
begin
  /* XAKCODE_T_TVA */
   if ((OLD.CODE_T_TVA <> NEW.CODE_T_TVA)) then
   begin
   select count(*) from TA_T_TVA
   where CODE_T_TVA= NEW.CODE_T_TVA
   into :numrows;
   if (numrows > 0) then
      exception XAKCODE_T_TVA;
   end
end
^


/* Trigger: TBU_XAKCODE_T_WEB */
CREATE TRIGGER TBU_XAKCODE_T_WEB FOR TA_T_WEB
INACTIVE BEFORE UPDATE POSITION 1
as
DECLARE variable numrows integer;
begin
  /* E_XAKCODE_T_WEB  */
   If  ((OLD.ID_T_WEB <> NEW.ID_T_WEB)) then
   begin
     select count(*) from TA_T_WEB
     where CODE_T_WEB = NEW.CODE_T_WEB
     into :numrows;
     if (numrows > 0) then
        exception E_XAKCODE_T_WEB ;
   end
end
^


/* Trigger: TBU_XAKCODE_UNITE */
CREATE TRIGGER TBU_XAKCODE_UNITE FOR TA_UNITE
INACTIVE BEFORE UPDATE POSITION 0
as
DECLARE variable numrows integer;
begin
  /* XAKCODE_UNITE */
   if ((OLD.CODE_UNITE <> NEW.CODE_UNITE)) then
   begin
   select count(*) from TA_UNITE
   where CODE_UNITE = NEW.CODE_UNITE
   into :numrows;
   if (numrows > 0) then
      exception E_XAKCODE_UNITE;
   end
end
^


/* Trigger: TBU_XAKCOMPTE_BANQUE */
CREATE TRIGGER TBU_XAKCOMPTE_BANQUE FOR TA_COMPTE_BANQUE
INACTIVE BEFORE UPDATE POSITION 1
as
DECLARE variable numrows integer;
begin
  /* E_XAKCOMPTE_BANQUE  */
   If  ((OLD.COMPTE <> NEW.COMPTE)) then
   begin
     select count(*) from TA_COMPTE_BANQUE
     where COMPTE = NEW.COMPTE
     into :numrows;
     if (numrows > 0) then
        exception E_XAKCOMPTE_BANQUE ;
   end
end
^

